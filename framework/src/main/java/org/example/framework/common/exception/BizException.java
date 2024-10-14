package org.example.framework.common.exception;

import lombok.Getter;
import org.example.framework.common.constant.ResultEnum;

/**
 * 业务异常
 */
@Getter
public class BizException extends RuntimeException {

    private final String code;
    private final String msg;

    public BizException() {
        super();
        this.code = ResultEnum.ERR.getCode();
        this.msg = ResultEnum.ERR.getMsg();
    }

    public static BizException valueOfCode(String code) {
        return new BizException(code, ResultEnum.ERR.getMsg());
    }

    public static BizException valueOfMsg(String msg) {
        return new BizException(ResultEnum.ERR.getCode(), msg);
    }

    public BizException(Throwable cause) {
        super(cause);
        this.code = ResultEnum.ERR.getCode();
        this.msg = cause.getMessage();
    }

    public BizException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
