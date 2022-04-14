package pl.karinawojtek.ztiserver.exception.custom;

public class DuplicateObjectException extends RuntimeException{

    public DuplicateObjectException(String message) {
        super(message);
    }
}
