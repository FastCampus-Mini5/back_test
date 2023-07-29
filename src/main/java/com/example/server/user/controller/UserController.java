package com.example.server.user.controller;

import com.example.server._core.security.PrincipalUserDetail;
import com.example.server._core.util.ApiResponse;
import com.example.server.user.dto.UserRequest;
import com.example.server.user.dto.UserResponse;
import com.example.server.user.model.User;
import com.example.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse.Result<User>> signup(@RequestBody @Valid UserRequest.SignUpDTO signUpDTO, Errors errors) {

        userService.saveSignUpRequest(signUpDTO);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse.Result<String>> signIn(@RequestBody @Valid UserRequest.SignInDTO signInDTO, Errors errors) {

        String jwt = userService.signIn(signInDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);

        log.info("{}", ResponseEntity.ok().headers(headers).body(null));

        return ResponseEntity.ok().headers(headers).body(null);
    }

    @GetMapping("/emailCheck")
    public ResponseEntity<ApiResponse.Result<UserResponse.AvailableEmailDTO>> check(@ModelAttribute(name = "email") @Valid UserRequest.CheckEmailDTO checkEmailDTO, BindingResult bindingResult) {

        log.info("{}", checkEmailDTO);

        UserResponse.AvailableEmailDTO availableEmailDTO = userService.checkEmail(checkEmailDTO);

        return ResponseEntity.ok(ApiResponse.success(availableEmailDTO));
    }

    @GetMapping("/info")
    public ResponseEntity<ApiResponse.Result<UserResponse.UserInfoDTO>> userInfo(@AuthenticationPrincipal PrincipalUserDetail userDetail) {

        UserResponse.UserInfoDTO userInfoDTO = userService.getUserInfoByUserId(userDetail.getUser().getId());

        log.info("조회된 유저 정보 : {}", userInfoDTO);

        return ResponseEntity.ok(ApiResponse.success(userInfoDTO));
    }
}
