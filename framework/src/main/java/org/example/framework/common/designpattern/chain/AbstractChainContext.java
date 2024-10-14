package org.example.framework.common.designpattern.chain;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;

import java.util.*;

@RequiredArgsConstructor
public final class AbstractChainContext<T> implements ApplicationRunner {

    private final ApplicationContext context;

    private final Map<String, List<AbstractChainHandler<T>>> handlerChainContainer = new HashMap<>();

    public void handler(String name, T param) {
        List<AbstractChainHandler<T>> handlers = handlerChainContainer.get(name);
        if (handlers == null || handlers.isEmpty()) {
            return;
        }
        handlers.forEach(handler -> handler.handler(param));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, AbstractChainHandler> chainHandlerMap = context.getBeansOfType(AbstractChainHandler.class);
        chainHandlerMap.values().forEach(abstractChainHandler -> {
            List<AbstractChainHandler<T>> handlers = handlerChainContainer.getOrDefault(abstractChainHandler.name(), new ArrayList<>());
            handlers.add(abstractChainHandler);
            handlerChainContainer.put(abstractChainHandler.name(), handlers);
        });
        handlerChainContainer.values().forEach(handlers -> handlers.sort(Comparator.comparing(AbstractChainHandler::order)));
    }
}
