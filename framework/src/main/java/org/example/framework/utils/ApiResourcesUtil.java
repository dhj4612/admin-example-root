package org.example.framework.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.util.List;

@RequiredArgsConstructor
public class ApiResourcesUtil {

    private final ApplicationContext applicationContext;

    /**
     * 根据注解类型获取Api资源
     */
    public <T extends Annotation> List<String> getApiResourceByAnnotation(Class<T> annotationType) {
        RequestMappingHandlerMapping handlerMapping = applicationContext
                .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);

        return handlerMapping.getHandlerMethods()
                .entrySet()
                .stream()
                .filter(entry -> {
                    boolean methodAnnotationPresent = entry.getValue().getMethod().isAnnotationPresent(annotationType);
                    boolean beanAnnotationPresent = entry.getValue().getBeanType().isAnnotationPresent(annotationType);
                    return methodAnnotationPresent || beanAnnotationPresent;
                })
                .flatMap(entry -> entry.getKey().getPatternValues().stream())
                .map(this::convertToAntPattern)
                .toList();
    }

    private String convertToAntPattern(String path) {
        return path.replaceAll("\\{[^}]+}", "*");
    }
}
