package az.edu.turing.springbootdemoapp1.exception;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String message) {
        super(message);
    }
}
