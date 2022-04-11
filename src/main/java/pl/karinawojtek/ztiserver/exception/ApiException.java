package pl.karinawojtek.ztiserver.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ApiException {
    private String message;
    private Throwable cause;
    private HttpStatus status;
    private ZonedDateTime dateTime;

    public ApiException(String message, Throwable cause, HttpStatus status, ZonedDateTime dateTime) {
        this.message = message;
        this.cause = cause;
        this.status = status;
        this.dateTime = dateTime;
    }
}
