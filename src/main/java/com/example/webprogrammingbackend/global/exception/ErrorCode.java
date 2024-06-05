package com.example.webprogrammingbackend.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public enum ErrorCode {


    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),

    NOT_FOUND_DATA(NOT_FOUND, "조회한 데이터가 없습니다."),


    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
    INVALID_IMAGE_FILE(BAD_REQUEST, "이미지 파일이 잘못되었습니다.");

    private final HttpStatus httpStatus;
    private final String detail;

    ErrorCode(HttpStatus httpStatus, String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }

    public ErrorResponse convertErrorResponse(Exception ex) {
        ErrorResponse.builder(ex,httpStatus,detail)
                .build();
        return ErrorResponse.create(ex,httpStatus,detail);
    }

}