package com.example.server._core.errors.exception;


import com.example.server._core.util.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Authentication Failed exception
 */
@Getter
public class Exception401 extends RuntimeException {

    public Exception401(String message) {
        super(message);
    }

    public ApiResponse.Result<Object> body() {
        return ApiResponse.error(getMessage(), status());
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}
