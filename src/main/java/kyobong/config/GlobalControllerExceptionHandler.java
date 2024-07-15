package kyobong.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Order()
@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {


    @ExceptionHandler(value = {Error.class, Exception.class})
    public ResponseEntity<ErrorResponse> unexpectedException(Exception e) {

        log.error(e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder().message("서버 내부 오류").build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
