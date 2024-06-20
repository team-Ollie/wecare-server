package ollie.wecare.common.Base;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 1000: 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공했습니다."),

    /**
     * 2000: Request 오류
     */
    // user(2000-2099)
    INVALID_USER(false, 2001, "존재하지 않는 유저입니다."),
    // program(2100-2199)

    // challenge(2200-2299)
    ATTENDANCE_FAIL(false, 2201, "인증번호가 일치하지 않습니다."),
    INVALID_CHALLENGE(false, 2202, "존재하지 않는 챌린지입니다."),
    /**
     * 3000: Response 오류
     */
    // user(3000-3099)

    // program(3100-3199)

    // challenge(3200-3299)


    /**
     * 4000: DB, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패했습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;
    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
