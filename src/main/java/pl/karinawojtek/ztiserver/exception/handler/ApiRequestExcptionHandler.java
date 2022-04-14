package pl.karinawojtek.ztiserver.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.karinawojtek.ztiserver.exception.custom.ApiRequestException;
import pl.karinawojtek.ztiserver.exception.custom.ObjectByIdNotFoundException;
import pl.karinawojtek.ztiserver.exception.custom.WrongReviewMarkException;
import pl.karinawojtek.ztiserver.exception.models.ApiException;

import java.text.ParseException;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiRequestExcptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        ApiException apiException = new ApiException(
                e.getCause().getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {WrongReviewMarkException.class})
    public ResponseEntity<?> handleBadRequestExceptions(Exception e){
        return new ResponseEntity<>(
                new ApiException(e.getMessage(),HttpStatus.BAD_REQUEST,ZonedDateTime.now()),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(value = {ObjectByIdNotFoundException.class,UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<?> handleNotFoundRequest(Exception e){
        return new ResponseEntity<>(
                new ApiException(e.getMessage(),HttpStatus.NOT_FOUND,ZonedDateTime.now()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {AuthorizationServiceException.class})
    public ResponseEntity<?> handleAuthorizationException(AuthorizationServiceException e){
        return new ResponseEntity<>(
                new ApiException(e.getMessage(),HttpStatus.UNAUTHORIZED,ZonedDateTime.now()),
                HttpStatus.UNAUTHORIZED
        );
    }

//    @ExceptionHandler(value = {})
//    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException e){
//        ApiException apiException = new ApiException(
//                e.getMessage(),
//                HttpStatus.BAD_REQUEST,
//                ZonedDateTime.now()
//        );
//        //do sprawdzenia kod bledu
//        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(value = {ParseException.class})
    public ResponseEntity<?> handleParseException(ParseException e){
        ApiException apiException = new ApiException(e, HttpStatus.BAD_REQUEST);
        apiException.setMessage("Wrong Date Format");
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}
