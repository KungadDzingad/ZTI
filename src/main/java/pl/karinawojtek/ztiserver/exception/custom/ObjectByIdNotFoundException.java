package pl.karinawojtek.ztiserver.exception.custom;

public class ObjectByIdNotFoundException extends Exception{
    public ObjectByIdNotFoundException(String message) {
        super(message);
    }
}
