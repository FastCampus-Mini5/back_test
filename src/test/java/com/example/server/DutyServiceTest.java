package com.example.server;

import com.example.server._core.errors.exception.Exception400;
import com.example.server._core.errors.exception.Exception403;
import com.example.server._core.errors.exception.Exception404;
import com.example.server.schedule.Status;
import com.example.server.schedule.duty.dto.DutyRequest;
import com.example.server.schedule.duty.dto.DutyResponse;
import com.example.server.schedule.duty.model.Duty;
import com.example.server.schedule.duty.repository.DutyRepository;
import com.example.server.schedule.duty.service.DutyService;
import com.example.server.user.model.User;
import com.example.server.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DutyServiceTest {

    @Mock
    private DutyRepository dutyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DutyService dutyService;

    @DisplayName("유저 당직 신청 성공")
    @Test
    void requestDuty_Success() {
        // given
        Long userId = 1L;
        DutyRequest.AddDTO dutyRequest = createDutyRequest("2023-07-01 00:00:00");

        User user = createUser(userId, "user1");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Duty duty = createDuty(1L, user, "2023-07-01 00:00:00");
        when(dutyRepository.findByUserAndDutyDate(user, duty.getDutyDate())).thenReturn(Optional.empty());
        when(dutyRepository.save(any(Duty.class))).thenReturn(duty);

        // when
        DutyResponse.DutyDTO result = dutyService.requestDuty(dutyRequest, userId);

        // then
        assertNotNull(result);
        assertEquals(duty.getId(), result.getId());
        assertEquals(user.getEmail(), result.getUserEmail());
        assertEquals(duty.getDutyDate(), result.getDutyDate());
        assertEquals(Status.PENDING, result.getStatus());

        verify(userRepository, times(1)).findById(userId);
        verify(dutyRepository, times(1)).findByUserAndDutyDate(user, duty.getDutyDate());
        verify(dutyRepository, times(1)).save(any(Duty.class));
    }

    @DisplayName("유저 당직 신청 실패 - 유효하지 않은 사용자 ID")
    @Test
    void requestDuty_Fail_InvalidUserId() {
        // given
        Long invalidUserId = 999L;
        DutyRequest.AddDTO dutyRequest = createDutyRequest("2023-07-01 00:00:00");

        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(Exception404.class, () -> dutyService.requestDuty(dutyRequest, invalidUserId));

        verify(userRepository, times(1)).findById(invalidUserId);
        verify(dutyRepository, never()).findByUserAndDutyDate(any(User.class), any(Timestamp.class));
        verify(dutyRepository, never()).save(any(Duty.class));
    }

    @DisplayName("유저 당직 신청 실패 - 이미 당직이 있는 경우")
    @Test
    void requestDuty_Fail_ExistingDuty() {
        // given
        Long userId = 1L;
        DutyRequest.AddDTO dutyRequest = createDutyRequest("2023-07-01 00:00:00");

        User user = createUser(userId, "user1");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Duty existingDuty = createDuty(1L, user, "2023-07-01 00:00:00");
        when(dutyRepository.findByUserAndDutyDate(user, existingDuty.getDutyDate())).thenReturn(Optional.of(existingDuty));

        // when, then
        assertThrows(Exception400.class, () -> dutyService.requestDuty(dutyRequest, userId));

        verify(userRepository, times(1)).findById(userId);
        verify(dutyRepository, times(1)).findByUserAndDutyDate(user, existingDuty.getDutyDate());
        verify(dutyRepository, never()).save(any(Duty.class));
    }

    @DisplayName("유저 당직 취소 성공")
    @Test
    void cancelDuty_Success() {
        // given
        Long userId = 1L;
        Long dutyId = 1L;

        DutyRequest.CancelDTO cancelDTO = DutyRequest.CancelDTO.builder()
                .id(dutyId)
                .build();

        User user = createUser(userId, "user1");
        Duty duty = createDuty(dutyId, user, "2023-07-01 00:00:00");

        when(dutyRepository.findById(dutyId)).thenReturn(Optional.of(duty));
        when(dutyRepository.save(any(Duty.class))).thenReturn(duty);

        // when
        DutyResponse.DutyDTO result = dutyService.cancelDuty(cancelDTO, userId);

        // then
        assertNotNull(result);
        assertEquals(duty.getId(), result.getId());
        assertEquals(user.getEmail(), result.getUserEmail());
        assertEquals(duty.getDutyDate(), result.getDutyDate());
        assertEquals(Status.CANCELLED, result.getStatus());

        verify(dutyRepository, times(1)).findById(dutyId);
        verify(dutyRepository, times(1)).save(any(Duty.class));
    }

    @DisplayName("유저 당직 취소 실패 - 유효하지 않은 당직 ID")
    @Test
    void cancelDuty_Fail_InvalidDutyId() {
        // given
        Long userId = 1L;
        Long invalidDutyId = 999L;

        DutyRequest.CancelDTO cancelDTO = DutyRequest.CancelDTO.builder()
                .id(invalidDutyId)
                .build();

        when(dutyRepository.findById(invalidDutyId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(Exception404.class, () -> dutyService.cancelDuty(cancelDTO, userId));

        verify(dutyRepository, times(1)).findById(invalidDutyId);
        verify(dutyRepository, never()).save(any(Duty.class));
    }

    @DisplayName("유저 당직 취소 실패 - 다른 유저의 당직 취소")
    @Test
    void cancelDuty_Fail_UnauthorizedAccess() {
        // given
        Long userId = 1L;
        Long otherUserId = 2L;
        Long dutyId = 1L;

        DutyRequest.CancelDTO cancelDTO = DutyRequest.CancelDTO.builder()
                .id(dutyId)
                .build();

        User user = createUser(userId, "user1");
        User otherUser = createUser(otherUserId, "user2");
        Duty duty = createDuty(dutyId, otherUser, "2023-07-01 00:00:00");

        when(dutyRepository.findById(dutyId)).thenReturn(Optional.of(duty));

        // when, then
        assertThrows(Exception403.class, () -> dutyService.cancelDuty(cancelDTO, userId));

        verify(dutyRepository, times(1)).findById(dutyId);
        verify(dutyRepository, never()).save(any(Duty.class));
    }

    @DisplayName("유저 당직 취소 실패 - 이미 승인된 당직 취소")
    @Test
    void cancelDuty_Fail_AlreadyApproved() {
        // given
        Long userId = 1L;
        Long dutyId = 1L;

        DutyRequest.CancelDTO cancelDTO = DutyRequest.CancelDTO.builder()
                .id(dutyId)
                .build();

        User user = createUser(userId, "user1");
        Duty approvedDuty = createDuty(dutyId, user, "2023-07-01 00:00:00");
        approvedDuty.updateStatus(Status.APPROVE);

        when(dutyRepository.findById(dutyId)).thenReturn(Optional.of(approvedDuty));

        // when, then
        assertThrows(Exception403.class, () -> dutyService.cancelDuty(cancelDTO, userId));

        verify(dutyRepository, times(1)).findById(dutyId);
        verify(dutyRepository, never()).save(any(Duty.class));
    }
    private DutyRequest.AddDTO createDutyRequest(String dutyDate) {
        return DutyRequest.AddDTO.builder()
                .dutyDate(Timestamp.valueOf(dutyDate))
                .build();
    }

    private User createUser(Long id, String username) {
        return User.builder()
                .id(id)
                .email("test" + id + "@email.com")
                .username(username)
                .password("password123")
                .role("user")
                .profileImage("profile" + id + ".jpg")
                .hireDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

    private Duty createDuty(Long id, User user, String dutyDate) {
        return Duty.builder()
                .id(id)
                .user(user)
                .dutyDate(Timestamp.valueOf(dutyDate))
                .status(Status.PENDING)
                .approvalDate(null)
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .updatedDate(null)
                .build();
    }
}