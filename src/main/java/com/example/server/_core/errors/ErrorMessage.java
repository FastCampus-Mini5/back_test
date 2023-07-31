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
    public static final String USER_NOT_FOUND_WITH_ID = "ID를 가진 사용자를 찾을 수 없습니다. ";
    public static final String EMPTY_USER_ID = "유저 ID 정보가 없습니다.";

    public static final String EMPTY_DATA_TO_REQUEST_VACATION = "연차 요청을 위한 데이터가 존재하지 않습니다.";
    public static final String VACATION_INFO_NOT_FOUND = "연차 정보를 찾을 수 없습니다.";
    public static final String NOT_ENOUGH_REMAINING_VACATION_DAYS = "남은 연차일이 충분하지 않습니다.";
    public static final String DUTY_ALREADY_EXISTS = "해당 날짜에 이미 당직이 있습니다.";

    public static final String EMPTY_DATA_TO_REQUEST_DUTY = "당직 요청 정보가 없습니다.";
    public static final String EMPTY_DATA_TO_CANCEL_DUTY = "당직 취소 정보가 없습니다.";
    public static final String NOT_FOUND_DUTY = "요청한 당직이 존재하지 않습니다.";
    public static final String DUTY_CANNOT_BE_CANCELLED = "이미 승인된 당직은 취소할 수 없습니다.";



}
