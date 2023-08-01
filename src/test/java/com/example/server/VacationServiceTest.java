package com.example.server;


import com.example.server._core.errors.exception.Exception400;
import com.example.server._core.errors.exception.Exception404;
import com.example.server.schedule.Status;
import com.example.server.schedule.vacation.dto.VacationRequest;
import com.example.server.schedule.vacation.dto.VacationResponse;
import com.example.server.schedule.vacation.enums.Reason;
import com.example.server.schedule.vacation.model.Vacation;
import com.example.server.schedule.vacation.model.VacationInfo;
import com.example.server.schedule.vacation.repository.VacationInfoRepository;
import com.example.server.schedule.vacation.repository.VacationRepository;
import com.example.server.schedule.vacation.service.VacationService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacationServiceTest {

    @Mock
    private VacationRepository vacationRepository;

    @Mock
    private VacationInfoRepository vacationInfoRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private VacationService vacationService;

    @DisplayName("연차 신청 성공")
    @Test
    void requestVacation_Success() {
        // given
        Long userId = 1L;
        VacationRequest.AddDTO vacationRequest = createVacationRequest("2023-07-01 00:00:00", "2023-07-03 00:00:00");

        User user = createUser(userId, "user1");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Vacation vacation = createVacation(1L, user, "2023-07-01 00:00:00", "2023-07-03 00:00:00");
        when(vacationRepository.save(any(Vacation.class))).thenReturn(vacation);

        VacationInfo vacationInfo = createVacationInfo(user, 15, 5);
        when(vacationInfoRepository.findByUser(user)).thenReturn(Optional.of(vacationInfo));

        // when
        VacationResponse.VacationDTO result = vacationService.requestVacation(vacationRequest, userId);

        // then
        assertNotNull(result);
        assertEquals(vacation.getId(), result.getId());
        assertEquals(user.getEmail(), result.getUserEmail());
        assertEquals(vacation.getReason(), result.getReason());
        assertEquals(vacation.getStatus(), result.getStatus());
        assertEquals(vacation.getStartDate(), result.getStartDate());
        assertEquals(vacation.getEndDate(), result.getEndDate());
        assertNull(result.getApprovalDate());

        verify(userRepository, times(1)).findById(userId);
        verify(vacationRepository, times(1)).save(any(Vacation.class));
        verify(vacationInfoRepository, times(1)).findByUser(user);
    }

    @DisplayName("연차 신청 실패 - 유효하지 않은 사용자 ID")
    @Test
    void requestVacation_Fail_InvalidUserId() {
        // given
        Long invalidUserId = 999L;
        VacationRequest.AddDTO vacationRequest = createVacationRequest("2023-07-01 00:00:00", "2023-07-03 00:00:00");

        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(Exception404.class, () -> vacationService.requestVacation(vacationRequest, invalidUserId));

        verify(userRepository, times(1)).findById(invalidUserId);
        verify(vacationRepository, never()).save(any(Vacation.class));
        verify(vacationInfoRepository, never()).findByUser(any(User.class));
        verify(vacationInfoRepository, never()).save(any(VacationInfo.class));
    }

    @DisplayName("연차 신청 실패 - 부족한 남은 연차 일수")
    @Test
    void requestVacation_Fail_NotEnoughRemainingDays() {
        // given
        Long userId = 1L;
        VacationRequest.AddDTO vacationRequest = createVacationRequest("2023-07-01 00:00:00", "2023-07-15 00:00:00");

        User user = createUser(userId, "user1");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        VacationInfo vacationInfo = createVacationInfo(user, 15, 10);
        when(vacationInfoRepository.findByUser(user)).thenReturn(Optional.of(vacationInfo));

        // when, then
        assertThrows(Exception400.class, () -> vacationService.requestVacation(vacationRequest, userId));

        verify(userRepository, times(1)).findById(userId);
        verify(vacationRepository, never()).save(any(Vacation.class));
        verify(vacationInfoRepository, times(1)).findByUser(user);
        verify(vacationInfoRepository, never()).save(any(VacationInfo.class));
    }

    private VacationRequest.AddDTO createVacationRequest(String startDate, String endDate) {
        return VacationRequest.AddDTO.builder()
                .reason(Reason.반차)
                .startDate(Timestamp.valueOf(startDate))
                .endDate(Timestamp.valueOf(endDate))
                .build();
    }

    private User createUser(Long id, String username) {
        return User.builder()
                .id(id)
                .email("test" + id + "@example.com")
                .username(username)
                .password("password123")
                .role("user")
                .profileImage("profile" + id + ".jpg")
                .hireDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

    private Vacation createVacation(Long id, User user, String startDate, String endDate) {
        return Vacation.builder()
                .id(id)
                .user(user)
                .reason(Reason.반차)
                .status(Status.PENDING)
                .startDate(Timestamp.valueOf(startDate))
                .endDate(Timestamp.valueOf(endDate))
                .build();
    }

    private VacationInfo createVacationInfo(User user, int totalVacation, int usedVacation) {
        return VacationInfo.builder()
                .user(user)
                .remainVacation(totalVacation - usedVacation)
                .usedVacation(usedVacation)
                .build();
    }
}