package pl.karinawojtek.ztiserver.exception;

public class DuplicateObjectException extends RuntimeException{

    public DuplicateObjectException(String message) {
        super(message);
    }
}
