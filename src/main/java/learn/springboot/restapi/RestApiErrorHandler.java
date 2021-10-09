package learn.springboot.restapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class RestApiErrorHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<RestApiErrorResponse> handleAnyException(Exception e, WebRequest request){
        log.error("Handling Exception in Advice" ,e);
        if (e instanceof RuntimeException){
            return ResponseEntity.internalServerError().body(new RestApiErrorResponse("Test this Runtime Exception Advice"));
        }else {
            return ResponseEntity.internalServerError().body(new RestApiErrorResponse("This is general exception"));
        }

    }
}