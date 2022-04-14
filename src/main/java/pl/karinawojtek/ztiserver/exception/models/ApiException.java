package pl.karinawojtek.ztiserver.exception.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ApiException {
    private String message;
    private HttpStatus status;
    private ZonedDateTime dateTime;

    public ApiException(String message,  HttpStatus status, ZonedDateTime dateTime) {
        this.message = message;
        this.status = status;
        this.dateTime = dateTime;
    }
    public ApiException(Throwable throwable, HttpStatus status){
        this.message = throwable.getMessage();
        this.status = status;
        this.dateTime = ZonedDateTime.now();
    }
}
