package io.kimmking.rpcfx.exception;

/**
 * 自定义rpc异常
 */
public class RpcfxException extends Exception {
    public RpcfxException() {
    }

    public RpcfxException(String message) {
        super(message);
    }

    public RpcfxException(String message, Throwable cause) {
        super(message, cause);
    }
}
