package com.example.server._core.errors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessage {

    public static final String UN_AUTHORIZED = "인증되지 않았습니다.";
    public static final String FORBIDDEN = "권한이 없습니다.";

    public static final String TOKEN_UN_AUTHORIZED = "검증되지 않은 토큰입니다.";
    public static final String TOKEN_EXPIRED = "만료된 토큰입니다.";
    public static final String TOKEN_VERIFICATION_FAILED = "토큰 검증에 실패했습니다.";

    public static final String EMPTY_DATA_TO_SIGNUP = "회원 가입을 위한 데이터가 존재하지 않습니다.";
    public static final String EMPTY_DATA_TO_SIGNIN = "로그인을 위한 데이터가 존재하지 않습니다.";
    public static final String EMPTY_DATA_TO_CHECK_EMAIL = "중복 확인을 위한 이메일 정보가 존재하지 않습니다.";
    public static final String LOGIN_FAILED = "회원 정보가 존재하지 않습니다.";
}
