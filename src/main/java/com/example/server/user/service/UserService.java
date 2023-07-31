package com.example.server.user.service;

import com.example.server._core.errors.ErrorMessage;
import com.example.server._core.errors.exception.Exception500;
import com.example.server._core.security.PrincipalUserDetail;
import com.example.server._core.security.jwt.JwtTokenProvider;
import com.example.server.user.dto.UserRequest;
import com.example.server.user.dto.UserResponse;
import com.example.server.user.model.User;
import com.example.server.user.repository.SignUpRepository;
import com.example.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SignUpRepository signUpRepository;
    private final UserRepository userRepository;

    public void saveSignUpRequest(UserRequest.SignUpDTO signUpDTO) {
        if (signUpDTO == null) throw new Exception500(ErrorMessage.EMPTY_DATA_TO_SIGNUP);

        signUpRepository.save(signUpDTO.toEntityWithHashPassword(passwordEncoder));
    }

    public String signIn(UserRequest.SignInDTO signInDTO) {
        if (signInDTO == null) throw new Exception500(ErrorMessage.EMPTY_DATA_TO_SIGNIN);

        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);
        PrincipalUserDetail userDetail = (PrincipalUserDetail) authentication.getPrincipal();
        final User user = userDetail.getUser();

        return JwtTokenProvider.create(user);
    }

    public UserResponse.AvailableEmailDTO checkEmail(UserRequest.CheckEmailDTO checkEmailDTO) {
        if (checkEmailDTO == null) throw new Exception500(ErrorMessage.EMPTY_DATA_TO_CHECK_EMAIL);

        String email = checkEmailDTO.getEmail();

        if (userRepository.existsByEmail(email)) {
            return UserResponse.AvailableEmailDTO.builder()
                    .email(email).available(false).build();
        }

        return UserResponse.AvailableEmailDTO.builder()
                .email(email).available(true).build();
    }

    public User findUserById(Long id) {
        if (id == null) throw new Exception500(ErrorMessage.EMPTY_USER_ID);

        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.USER_NOT_FOUND_WITH_ID + id));
    }
}