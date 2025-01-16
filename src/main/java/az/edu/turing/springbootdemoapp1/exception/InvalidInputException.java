package az.edu.turing.springbootdemoapp1.exception;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }
}
