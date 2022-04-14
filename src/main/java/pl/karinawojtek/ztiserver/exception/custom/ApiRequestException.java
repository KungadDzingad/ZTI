package pl.karinawojtek.ztiserver.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

@Getter
public class ApiRequestException extends RuntimeException{

    private final HttpStatus status;

    public ApiRequestException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }
}
