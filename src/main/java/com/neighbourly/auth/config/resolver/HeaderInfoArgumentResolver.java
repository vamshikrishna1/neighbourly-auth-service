package com.neighbourly.auth.config.resolver;


import com.neighbourly.auth.constants.REQUIRED_HEADERS;
import com.neighbourly.auth.dto.HeaderInfo;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class HeaderInfoArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return HeaderInfo.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String uuid = webRequest.getHeader("UUID");
        String userId = webRequest.getHeader("USER_ID");

        for (REQUIRED_HEADERS header : REQUIRED_HEADERS.values()) {
            if (webRequest.getHeader(header.getValue()) == null) {
                throw new MissingRequestHeaderException(header.getValue(), parameter);
            }
        }

      return HeaderInfo.builder().userId(userId)
                .uuid(uuid)
                .build();
    }
}
