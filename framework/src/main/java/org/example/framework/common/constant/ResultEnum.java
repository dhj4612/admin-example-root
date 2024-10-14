package org.example.framework.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultEnum {
    OK("200", "success"),
    ERR("500", "fail");

    private final String code;
    private final String msg;
}
