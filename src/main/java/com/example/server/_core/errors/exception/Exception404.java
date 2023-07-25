package com.example.server._core.errors.exception;

import com.example.server._core.util.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * not found exception
 */
@Getter
public class Exception404 extends RuntimeException {

    public Exception404(String message) {
        super(message);
    }

    public ApiResponse.Result<Object> body() {
        return ApiResponse.error(getMessage(), status());
    }

    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }
}
