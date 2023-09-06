package com.soccer.matchUp.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    // 성공
    OK(HttpStatus.OK,"Success"),
    // 검색 예외
    NO_RESULT_FOUND(HttpStatus.NOT_FOUND, "검색 결과가 없습니다."),
    INVALID_SEARCH_CRITERIA(HttpStatus.NOT_FOUND, "잘못된 검색 조건입니다."),
    // 매칭 상세 정보 예외
    INVALID_INPUT(HttpStatus.NOT_FOUND, "잘못된 형식입니다."),
    INPUT_DATA_REQUIRED(HttpStatus.BAD_REQUEST, "필수 입력 값이 누락되었습니다."),
    INVALID_DATE_TIME_FORMAT(HttpStatus.BAD_REQUEST, "잘못된 날짜 또는 시간입니다."),
    INVALID_FIELD_LOCATION(HttpStatus.BAD_REQUEST, "잘못된 구장 정보입니다."),
    INVALID_SKILL_LEVEL(HttpStatus.BAD_REQUEST, "잘못된 팀 실력입니다."),
    // 로그인 예외
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않은 계정입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다."),
    // 회원가입 예외
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 계정입니다."),
    WEAK_PASSWORD(HttpStatus.BAD_REQUEST, "약한 비밀번호입니다."),
    INVALID_NICKNAME(HttpStatus.BAD_REQUEST, "잘못된 닉네임입니다."),
    INVALID_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "잘못된 전화번호입니다."),

    NOT_FOUND_DATA(HttpStatus.NOT_FOUND, "데이터가 존재하지 않습니다"),
    DATA_ACCESS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "데이터 접근 오류"),
    NO_DATA(HttpStatus.BAD_REQUEST, "데이터가 널 값 입니다."),
    NOT_FOUND_URI(HttpStatus.NOT_FOUND, "요청하신 페이지를 찾을 수 없습니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "검증 요류입니다"),

    // BaseException
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad reqeust"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error"),
    NO_STATUS(HttpStatus.INTERNAL_SERVER_ERROR, "HttpStatus is null");

    private final HttpStatus httpStatus;
    private final String message;

    public static ErrorCode valueOf(HttpStatus status){
        if(status == null) return ErrorCode.NO_STATUS;

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatus() == status)
                .findFirst()
                .orElseGet(()->is4xxOr5xxStatus(status));
    }

    public static ErrorCode is4xxOr5xxStatus(HttpStatus status){
        return status.is4xxClientError() ? ErrorCode.BAD_REQUEST:ErrorCode.INTERNAL_ERROR;
    }
    public static String getDetailMessage(String message){
        return Optional.ofNullable(message)
                .filter(Predicate.not(Strings::isBlank))
                .orElse(message + "-" + message);

    }

}
