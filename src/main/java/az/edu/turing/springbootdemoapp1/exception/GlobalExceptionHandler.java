package az.edu.turing.springbootdemoapp1.exception;

import az.edu.turing.springbootdemoapp1.model.constant.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<GlobalErrorResponse> handleAlreadyException(AlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(GlobalErrorResponse.builder().
                        errorCode(ErrorCode.ALREADY_EXISTS).
                        errorMessage(e.getMessage()).
                        timestamp(LocalDateTime.now()).
                        requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GlobalErrorResponse> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(GlobalErrorResponse.builder().
                        errorCode(ErrorCode.NOT_FOUND).
                        errorMessage(e.getMessage()).
                        timestamp(LocalDateTime.now()).
                        requestId(UUID.randomUUID()).
                        build());
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<GlobalErrorResponse> handleInvalidInputException(InvalidInputException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(GlobalErrorResponse.builder().
                        errorCode(ErrorCode.INVALID_INPUT).
                        errorMessage(e.getMessage()).
                        timestamp(LocalDateTime.now()).
                        requestId(UUID.randomUUID())
                        .build());
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//
//
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//
//    @ExceptionHandler(MethodArgumentConversionNotSupportedException.class)
//
//    @ExceptionHandler(Method)
}
