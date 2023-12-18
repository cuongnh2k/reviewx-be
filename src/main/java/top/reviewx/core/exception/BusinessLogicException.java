package top.reviewx.core.exception;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessLogicException extends RuntimeException {
    private int code;
    private Object[] args;
    private Object data;

    public BusinessLogicException(int code) {
        this.code = code;
    }
}
