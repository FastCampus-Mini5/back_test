package com.example.server.schedule.vacation.controller;

import com.example.server._core.security.PrincipalUserDetail;
import com.example.server._core.util.ApiResponse;
import com.example.server.schedule.vacation.dto.VacationRequest;
import com.example.server.schedule.vacation.dto.VacationResponse;
import com.example.server.schedule.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class VacationController {

    private final VacationService vacationService;

    @PostMapping("/vacation/request")
    public ResponseEntity<ApiResponse.Result<VacationResponse.VacationDTO>> requestVacation(@RequestBody @Valid VacationRequest.AddDTO vacationRequest,
                                                                                            @AuthenticationPrincipal PrincipalUserDetail userDetails) {
        Long userId = userDetails.getUser().getId();
        VacationResponse.VacationDTO vacationDTO = vacationService.requestVacation(vacationRequest, userId);

        return ResponseEntity.ok(ApiResponse.success(vacationDTO));
    }
}