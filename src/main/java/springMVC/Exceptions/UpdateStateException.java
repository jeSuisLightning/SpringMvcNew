package springMVC.Exceptions;

public class UpdateStateException extends RuntimeException{
    public UpdateStateException(String message) {
        super(message);
    }
}
