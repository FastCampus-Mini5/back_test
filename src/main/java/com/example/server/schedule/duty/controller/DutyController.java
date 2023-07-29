package com.example.server.schedule.duty.controller;


import com.example.server.schedule.duty.dto.DutyRequest;
import com.example.server.schedule.duty.model.Duty;
import com.example.server.schedule.duty.service.DutyService;
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
public class DutyController {
    private final DutyService dutyService;
    private final UserService userService;

    @PostMapping("/duty/add")
    public ResponseEntity<Duty> add(@RequestBody @Valid DutyRequest dutyRequest,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        Duty duty = dutyRequest.toDutyEntity(user);
        duty = dutyService.requestDuty(duty);
        return ResponseEntity.ok(duty);
    }
}
