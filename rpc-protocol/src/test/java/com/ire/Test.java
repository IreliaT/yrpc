package com.ire;

import com.ire.protocol.RpcProtocol;
import com.ire.protocol.header.RpcHeader;
import com.ire.protocol.header.RpcHeaderFactory;
import com.ire.protocol.request.RpcRequest;

/**
 * @Date 2025/10/23 15:14
 */
public class Test {
    public static RpcProtocol<RpcRequest> getRpcProtocol(){
        RpcHeader header = RpcHeaderFactory.getRequestHeader("jdk");
        RpcRequest body = new RpcRequest();
        body.setOneway(false);
        body.setAsync(false);
        body.setClassName("io.binghe.rpc.demo.RpcProtocol");
        body.setMethodName("hello");
        body.setGroup("binghe");
        body.setParameters(new Object[]{"binghe"});
        body.setParameterTypes(new Class[]{String.class});
        body.setVersion("1.0.0");
        RpcProtocol<RpcRequest> protocol = new RpcProtocol<>();
        protocol.setBody(body);
        protocol.setHeader(header);
        return protocol;
    }

    public static void main(String[] args) {
        RpcProtocol<RpcRequest> rpcProtocol = getRpcProtocol();
        System.out.println(rpcProtocol);
    }
}
