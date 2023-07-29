package com.example.server.schedule.vacation.controller;

import com.example.server.schedule.vacation.dto.VacationRequest;
import com.example.server.schedule.vacation.model.Vacation;
import com.example.server.schedule.vacation.service.VacationService;
import com.example.server.user.model.User;
import com.example.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class VacationController {

    private final VacationService vacationService;
    private final UserService userService;
    @PostMapping("/vacation/add")
    public ResponseEntity<Vacation> add(@RequestBody @Valid VacationRequest vacationRequestDTO,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        Vacation vacation = vacationRequestDTO.toVacationEntity(user);
        vacation = vacationService.requestVacation(vacation);
        return ResponseEntity.ok(vacation);
    }
}
