package org.example.framework.common.designpattern.chain;

public interface AbstractChainHandler<T> {

    void handler(T param);

    String name();

    int order();
}
