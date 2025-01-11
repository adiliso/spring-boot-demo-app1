package az.edu.turing.springbootdemoapp1.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GlobalErrorResponse {

    private UUID requestId;
    private String errorcode = "error_code";
    private String errormessage = "error_message";
    private LocalDateTime timestamp;
}
