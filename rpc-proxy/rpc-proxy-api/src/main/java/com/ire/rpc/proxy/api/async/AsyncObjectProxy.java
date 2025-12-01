package com.ire.rpc.proxy.api.async;

import com.ire.rpc.proxy.api.future.RPCFuture;

/**
 * @Date 2025/11/28 16:52
 */
public interface AsyncObjectProxy {

    RPCFuture call(String funcName, Object... args);
}
