package org.example.framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ApiResourcesUtil {
    /**
     * 根据注解类型获取Api资源
     */
    public static <T extends Annotation> Set<String> getApiResourceByAnnotation(ApplicationContext applicationContext, Class<T> annotationType) {
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
                .map(ApiResourcesUtil::convertToAntPattern)
                .collect(Collectors.toSet());
    }

    private static String convertToAntPattern(String path) {
        return path.replaceAll("\\{[^}]+}", "*");
    }
}
