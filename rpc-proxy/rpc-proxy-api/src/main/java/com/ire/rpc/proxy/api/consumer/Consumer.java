package com.ire.rpc.proxy.api.consumer;

import com.ire.protocol.RpcProtocol;
import com.ire.protocol.request.RpcRequest;
import com.ire.rpc.proxy.api.future.RPCFuture;

/**
 * @Date 2025/11/27 18:12
 */
public interface Consumer {

    RPCFuture sendRequest(RpcProtocol<RpcRequest> protocol) throws Exception;

}
