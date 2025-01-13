package az.edu.turing.springbootdemoapp1.model.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorCode {

    public static final String NOT_FOUND = "not_found";
    public static final String ALREADY_EXISTS = "already_exists";
    public static final String INVALID_INPUT = "invalid_input";
    public static final String METHOD_ARGUMENT_NOT_VALID = "method_argument_not_valid";
    public static final String CONSTRAINT_VIOLATION = "constraint_violation";
}
