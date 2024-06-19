package ollie.wecare.common.Base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<?> handleBaseException(BaseException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getStatus());
    }

}
