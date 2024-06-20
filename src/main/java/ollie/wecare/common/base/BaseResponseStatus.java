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
    INVALID_CENTER_IDX(false, HttpStatus.NOT_FOUND, "잘못된 Center Idx입니다."),
    INVALID_IDENTIFIER(false, HttpStatus.BAD_REQUEST, "식별번호는 이름+전화번호 뒷자리 4자리 형식이어야 합니다. ex. 홍길동1234"),

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
