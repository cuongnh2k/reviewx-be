package top.reviewx.core.advices;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class BasicErrorEntity extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

        Map<String, Object> newAttributes = new HashMap<>();
        newAttributes.put("code", errorAttributes.get("status"));
        newAttributes.put("message", errorAttributes.get("error"));

        return newAttributes;
    }
}
