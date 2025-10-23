package com.ire.proeocol.enums;

/**
 * @Date 2025/10/23 16:45
 */
public enum RpcStatus {
    SUCCESS(0),
    FAIL(1);

    private final int code;

    RpcStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
