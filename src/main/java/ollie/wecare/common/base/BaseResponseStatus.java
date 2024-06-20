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
    INVALID_REFRESH_TOKEN(false, HttpStatus.NOT_FOUND, "resresh token이 비어 있습니다."),
    DUPLICATED_NICKNAME(false, HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    DUPLICATED_LOGIN_ID(false, HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    INVALID_NICKNAME(false, HttpStatus.BAD_REQUEST, "잘못된 닉네임 형식입니다."),
    INVALID_PASSWORD(false, HttpStatus.BAD_REQUEST, "잘못된 비밀번호 형식입니다."),



    // program

    // challenge


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
