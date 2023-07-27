package com.example.server.user;

import com.example.server._core.security.PrincipalUserDetail;
import com.example.server._core.util.ApiResponse;
import com.example.server.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/auth")
    public String auth(HttpServletRequest request) {
        log.info(request.getHeader("Authorization"));
        return "토큰 잘 들어와 인증도 잘 됐어!";
    }

    @GetMapping("/auth/test")
    public String test(@AuthenticationPrincipal PrincipalUserDetail userDetail) {
        log.info(userDetail.getUser().toString());
        return "test";
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse.Result<User>> signup(
            @RequestBody @Valid UserRequest.JoinDTO joinDTO, Errors errors) {

        User responseUser = userService.save(joinDTO);

        return ResponseEntity.ok(ApiResponse.success(responseUser));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse.Result<String>> login(
            @RequestBody @Valid UserRequest.LoginDTO loginDTO, Errors errors) {
        String jwt = userService.login(loginDTO);

        return ResponseEntity.ok(ApiResponse.success(jwt));
    }
}
