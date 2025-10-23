package com.ire.provider.single;

import com.ire.rpc.provider.RpcSingleServer;
import org.junit.Test;

public class RpcSingleServerTest {

    @Test
    public void startRpcSingleServer(){
        RpcSingleServer singleServer = new RpcSingleServer("127.0.0.1:27880", "com.ire.provider.service","cglib");
        singleServer.startNettyServer();
    }
}
