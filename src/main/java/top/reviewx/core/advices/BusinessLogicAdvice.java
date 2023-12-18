package top.reviewx.core.advices;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import top.reviewx.core.base.BaseAdvice;
import top.reviewx.core.common.CommonResponse;
import top.reviewx.core.exception.BusinessLogicException;

@ControllerAdvice
public class BusinessLogicAdvice extends BaseAdvice {

    /**
     * Common logger.
     */
    private final Log LOGGER = LogFactory.getLog(getClass());

    @ExceptionHandler({BusinessLogicException.class})
    public ResponseEntity<CommonResponse> exception(BusinessLogicException exception, WebRequest request) {

        CommonResponse res = CommonResponse.builder()
                .errorCode(exception.getCode())
                .data(exception.getData())
                .build();

        StackTraceElement[] stackTraceElements = exception.getStackTrace();

        for (StackTraceElement StackTraceElement : stackTraceElements) {
            String controllerName = StackTraceElement.getClassName();
            if (controllerName.endsWith("Controller")) {
                String msgKey = String.join(".",
                        controllerName, StackTraceElement.getMethodName(), exception.getCode() + "");
                try {
                    String msg = messageSource.getMessage(msgKey, exception.getArgs(), LocaleContextHolder.getLocale());
                    res.setMessage(StringUtils.capitalize(msg));
                } catch (Exception ex) {
                    res.setMessage(msgKey);
                }

                break;
            }
        }
        return CommonResponse.error(res.getMessage(), res.getErrorCode(), res.getData());
    }
}
