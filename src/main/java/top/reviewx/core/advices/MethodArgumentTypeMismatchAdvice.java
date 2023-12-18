package top.reviewx.core.advices;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import top.reviewx.core.base.BaseAdvice;
import top.reviewx.core.common.CommonResponse;

import java.math.BigDecimal;
import java.util.*;

@ControllerAdvice
public class MethodArgumentTypeMismatchAdvice extends BaseAdvice {

    private static final String MESSAGE_CODE_RESPONSE_MESSAGE = "MethodArgumentTypeMismatchAdvice";

    /**
     * Common logger.
     */
    private final Log LOGGER = LogFactory.getLog(getClass());

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<CommonResponse> exception(MethodArgumentTypeMismatchException exception, WebRequest request) {


        LOGGER.warn(String.format("%S - %s", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
        LOGGER.warn(exception.getMessage());

        Map<String, List<String>> data = new HashMap<>();
        List<String> listErrMsg = new ArrayList<String>();
        data.put(exception.getName(), listErrMsg);

        Class<?> type = exception.getRequiredType();

        if (type.equals(Long.class) || type.equals(Integer.class)) {
            listErrMsg.add(messageSource.getMessage(MESSAGE_CODE_ERROR_MISMATCH,
                    new Object[]{"a integer number"}, LocaleContextHolder.getLocale()));
        } else if (type.equals(Float.class) || type.equals(Double.class) || type.equals(BigDecimal.class)) {
            listErrMsg.add(messageSource.getMessage(MESSAGE_CODE_ERROR_MISMATCH,
                    new Object[]{"a real number"}, LocaleContextHolder.getLocale()));
        } else if (type.equals(Date.class)) {
            listErrMsg.add(messageSource.getMessage(MESSAGE_CODE_ERROR_MISMATCH,
                    new Object[]{"a date"}, LocaleContextHolder.getLocale()));
        } else if (type.isEnum()) {
            listErrMsg.add(messageSource.getMessage(MESSAGE_CODE_ERROR_MISMATCH,
                    new Object[]{Arrays.toString(type.getEnumConstants())}, LocaleContextHolder.getLocale()));
        } else {
            listErrMsg.add(messageSource.getMessage(MESSAGE_CODE_ERROR_MISMATCH,
                    new Object[]{type.getSimpleName()}, LocaleContextHolder.getLocale()));
        }

        return CommonResponse.error(StringUtils.capitalize(
                        messageSource.getMessage(MESSAGE_CODE_RESPONSE_MESSAGE, null, LocaleContextHolder.getLocale())),
                HttpStatus.BAD_REQUEST.value(),
                data);
    }
}
