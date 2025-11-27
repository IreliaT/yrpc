package com.ire.rpc.proxy.api.callback;

/**
 * @Date 2025/11/27 17:23
 */
public interface AsyncRPCCallback {

    void onSuccess(Object result);

    void OnException(Exception e);

}
