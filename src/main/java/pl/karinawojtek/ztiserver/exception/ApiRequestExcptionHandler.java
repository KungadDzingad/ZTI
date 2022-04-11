package pl.karinawojtek.ztiserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiRequestExcptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        ApiException apiException = new ApiException(
                e.getCause().getMessage(),
                e.getCause(),
                e.getStatus(),
                ZonedDateTime.now());

        return new ResponseEntity<>(apiException, e.getStatus());
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException e){
        ApiException apiException = new ApiException(
                e.getMessage(),
                e.getCause(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        //do sprawdzenia kod bledu
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);

    }
}
