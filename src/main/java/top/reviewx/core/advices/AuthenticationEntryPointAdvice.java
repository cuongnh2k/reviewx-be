package top.reviewx.core.advices;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.reviewx.core.base.BaseAdvice;
import top.reviewx.core.common.CommonResponse;

import java.io.IOException;

@Component
public class AuthenticationEntryPointAdvice extends BaseAdvice implements AuthenticationEntryPoint {
    private static final String MESSAGE_CODE_RESPONSE_MESSAGE = "AccessDeniedExceptionAdvice";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        new ObjectMapper().writeValue(response.getOutputStream(), CommonResponse.builder()
                .success(false)
                .errorCode(HttpStatus.FORBIDDEN.value())
                .message(StringUtils.capitalize(
                        messageSource.getMessage(MESSAGE_CODE_RESPONSE_MESSAGE, null, LocaleContextHolder.getLocale())))
                .build());
    }
}
