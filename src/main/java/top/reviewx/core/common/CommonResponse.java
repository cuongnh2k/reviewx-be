package top.reviewx.core.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer errorCode;

    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public static ResponseEntity<CommonResponse> error(String message) {
        return ResponseEntity.ok().body(CommonResponse.builder()
                .success(false)
                .message(message)
                .build());
    }

    public static ResponseEntity<CommonResponse> error(String message, Integer errorCode) {
        return ResponseEntity.ok().body(CommonResponse.builder()
                .success(false)
                .errorCode(errorCode)
                .message(message)
                .build());
    }

    public static ResponseEntity<CommonResponse> error(String message, Integer errorCode, Object data) {
        return ResponseEntity.ok().body(CommonResponse.builder()
                .success(false)
                .errorCode(errorCode)
                .data(data)
                .message(message)
                .build());
    }

    public static ResponseEntity<CommonResponse> success(String message) {
        return ResponseEntity.ok().body(CommonResponse.builder()
                .success(true)
                .message(message)
                .build());
    }

    public static ResponseEntity<CommonResponse> success(Object data) {
        return ResponseEntity.ok().body(CommonResponse.builder()
                .success(true)
                .data(data)
                .build());
    }


    public static ResponseEntity<CommonResponse> success(Object data, String message) {
        return ResponseEntity.ok().body(CommonResponse.builder()
                .success(true)
                .data(data)
                .message(message)
                .build());
    }
}
