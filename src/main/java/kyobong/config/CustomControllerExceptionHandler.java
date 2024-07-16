package kyobong.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomControllerExceptionHandler {

    //    @ExceptionHandler({NoSuchElementException.class})
    //    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
    //
    //        ErrorResponse errorResponse = ErrorResponse.builder().message(e.getMessage()).build();
    //
    //        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    //    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        ErrorResponse errorResponse =
                ErrorResponse.builder().message("필수 데이터 " + e.getFieldError().getField() + " 누락").build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {

        e.printStackTrace();

        ErrorResponse errorResponse = ErrorResponse.builder().message(e.getMessage()).build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {NoHandlerFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ErrorResponse> expectedException(Exception e) {

        ErrorResponse errorResponse = ErrorResponse.builder().message("존재 하지 않는 URL 입니다.").build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
