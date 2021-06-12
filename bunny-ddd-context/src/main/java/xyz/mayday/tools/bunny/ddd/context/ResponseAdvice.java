package xyz.mayday.tools.bunny.ddd.context;


import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.TypeUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException;
import xyz.mayday.tools.bunny.ddd.schema.exception.FrameworkExceptionEnum;
import xyz.mayday.tools.bunny.ddd.schema.http.Response;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;

import java.util.Objects;
import java.util.Optional;

@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(TypeUtils.isAssignable(Objects.requireNonNull(body).getClass(), Optional.class)) {
            return Response.success(((Optional<?>)body).orElseThrow(() -> new BusinessException(FrameworkExceptionEnum.NO_SUCH_ELEMENT)));
        }

        else if(TypeUtils.isAssignable(returnType.getParameterType(), PageableData.class)) {
            return Response.success(body);
        }

        return body;
    }
}
