package top.reviewx.core.advices;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import top.reviewx.core.base.BaseAdvice;
import top.reviewx.core.common.CommonResponse;

import java.util.*;

@ControllerAdvice
public class ConstraintViolationAdvice extends BaseAdvice {

    private static final String MESSAGE_CODE_RESPONSE_MESSAGE = "ConstraintViolationAdvice";

    /**
     * Common logger.
     */
    private final Log LOGGER = LogFactory.getLog(getClass());

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<CommonResponse> exception(ConstraintViolationException exception, WebRequest request) {

        LOGGER.warn(String.format("%s - %s", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
        LOGGER.warn(exception.getMessage());

        Map<String, List<String>> data = new HashMap<>();
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        for (ConstraintViolation constraintViolation : constraintViolations) {

            Iterator<Path.Node> iterator = constraintViolation.getPropertyPath().iterator();
            Path.Node node = iterator.next();
            while (iterator.hasNext()) {
                node = iterator.next();
            }
            String name = node.getName();

            if (!data.containsKey(name)) {
                data.put(name, new ArrayList<>());
            }
            List<String> listErrorMessages = data.get(name);
            listErrorMessages.add(constraintViolation.getMessage());
        }

        return CommonResponse.error(StringUtils.capitalize(
                        messageSource.getMessage(MESSAGE_CODE_RESPONSE_MESSAGE, null, LocaleContextHolder.getLocale())),
                HttpStatus.BAD_REQUEST.value(),
                data);
    }
}
