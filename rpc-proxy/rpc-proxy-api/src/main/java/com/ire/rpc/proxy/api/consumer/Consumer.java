package com.ire.rpc.proxy.api.consumer;

import com.ire.protocol.RpcProtocol;
import com.ire.protocol.request.RpcRequest;
import com.ire.rpc.proxy.api.future.RPCFuture;
import com.ire.rpc.registry.api.service.RegistryService;

/**
 * @Date 2025/11/27 18:12
 */
public interface Consumer {

    RPCFuture sendRequest(RpcProtocol<RpcRequest> protocol ,
                          RegistryService registryService) throws Exception;

}
