package com.example.server.user;

import com.example.server._core.errors.ErrorMessage;
import com.example.server._core.errors.exception.Exception500;
import com.example.server._core.security.PrincipalUserDetail;
import com.example.server._core.security.jwt.JwtTokenProvider;
import com.example.server.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public User save(UserRequest.JoinDTO joinDTO) {
        if (joinDTO == null) throw new Exception500(ErrorMessage.EMPTY_DATA_TO_JOIN);

        User user = joinDTO.toEntityWithHashPassword(passwordEncoder);

        return userRepository.save(user);
    }

    public String login(UserRequest.LoginDTO loginDTO) {
        if (loginDTO == null) throw new Exception500(ErrorMessage.EMPTY_DATA_TO_LOGIN);

        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);
        PrincipalUserDetail userDetail = (PrincipalUserDetail) authentication.getPrincipal();
        final User user = userDetail.getUser();

        return JwtTokenProvider.create(user);
    }
}
