package ollie.wecare.common.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseResponseStatus {
    /**
     * 요청 성공
     */
    SUCCESS(true, HttpStatus.OK, "요청에 성공했습니다."),

    /**
     * Request 오류
     */
    // user
    INVALID_CENTER_IDX(false, HttpStatus.NOT_FOUND, "잘못된 CenterIdx 입니다."),
    INVALID_IDENTIFIER(false, HttpStatus.BAD_REQUEST, "식별번호는 이름+전화번호 뒷자리 4자리 형식이어야 합니다. ex. 홍길동1234"),
    INVALID_USER_IDX(false, HttpStatus.NOT_FOUND, "잘못된 UserIdx 입니다."),
    WRONG_PASSWORD(false, HttpStatus.CONFLICT, "비밀번호가 틀렸습니다."),
    NO_MATCH_USER(false, HttpStatus.NOT_FOUND, "아이디에 맞는 user가 없습니다."),
    DUPLICATED_NICKNAME(false, HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    DUPLICATED_LOGIN_ID(false, HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    INVALID_NICKNAME(false, HttpStatus.BAD_REQUEST, "닉네임은 최대 8글자로 영어(대소문자 구분), 한글, 숫자, 띄어쓰기만 가능합니다."),
    INVALID_PASSWORD(false, HttpStatus.BAD_REQUEST, "잘못된 비밀번호 형식입니다."),
    INVALID_ROLE(false, HttpStatus.FORBIDDEN, "허용되지 않은 권한입니다."),

    // jwt
    INVALID_JWT_SIGNATURE(false, HttpStatus.BAD_REQUEST, "유효하지 않은 JWT 시그니처입니다."),
    INVALID_ACCESS_TOKEN(false, HttpStatus.BAD_REQUEST, "잘못된 access token 입니다."),
    INVALID_REFRESH_TOKEN(false, HttpStatus.BAD_REQUEST, "잘못된 refresh token 입니다."),
    EXPIRED_ACCESS_TOKEN(false, HttpStatus.BAD_REQUEST, "만료된 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN(false, HttpStatus.BAD_REQUEST, "지원하지 않는 JWT 토큰 형식입니다."),
    EMPTY_JWT_CLAIM(false, HttpStatus.BAD_REQUEST, "JWT claims string이 비었습니다."),
    ACCESS_DENIED(false, HttpStatus.BAD_REQUEST, "접근 권한이 없습니다."),
    NULL_ACCESS_TOKEN(false, HttpStatus.BAD_REQUEST, "Access token이 비었습니다."),


    // program
    INVALID_PROGRAM_IDX(false, HttpStatus.BAD_REQUEST, "잘못된 ProgramIdx 입니다."),

    // challenge
    INVALID_ATTENDANCE_CODE(false, HttpStatus.BAD_REQUEST, "잘못된 인증번호입니다."),
    INVALID_CHALLENGE_IDX(false, HttpStatus.NOT_FOUND, "잘못된 ChallengeIdx 입니다."),
    NO_CHALLENGE(false, HttpStatus.NOT_FOUND, "Challenge 가 존재하지 않습니다."),

    /**
     * Response 오류
     */
    // user

    // program

    // challenge


    /**
     * DB, Server 오류
     */
    DATABASE_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 연결에 실패했습니다."),
    DUPLICATED_RESOURCE(false,HttpStatus.CONFLICT, "데이터가 이미 존재합니다");

    private final boolean isSuccess;
    private final HttpStatus httpStatus;
    private final String message;

    BaseResponseStatus(boolean isSuccess, HttpStatus status, String message) {
        this.isSuccess = isSuccess;
        this.httpStatus = status;
        this.message = message;
    }
}
