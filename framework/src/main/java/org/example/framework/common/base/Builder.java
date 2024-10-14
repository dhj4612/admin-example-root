package org.example.framework.common.base;

import java.io.Serializable;

public interface Builder<T> extends Serializable {
    T builder();
}
