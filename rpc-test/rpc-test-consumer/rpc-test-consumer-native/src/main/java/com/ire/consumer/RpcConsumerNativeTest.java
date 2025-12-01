package com.ire.consumer;

import com.ire.rpc.consumer.RpcClient;
import com.ire.rpc.proxy.api.async.AsyncObjectProxy;
import com.ire.rpc.proxy.api.future.RPCFuture;
import com.ire.test.api.DemoService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

/**
 * @Date 2025/11/27 18:50
 */
public class RpcConsumerNativeTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcConsumerNativeTest.class);

    public static void main(String[] args){
        RpcClient rpcClient = new RpcClient("1.0.0", "binghe", "jdk", 3000, false, false);
        DemoService demoService = rpcClient.create(DemoService.class);
        String result = demoService.hello("binghe");
        LOGGER.info("返回的结果数据===>>> " + result);
        rpcClient.shutdown();
    }

    @Test
    public void testAsyncInterfaceRpc() throws ExecutionException, InterruptedException {
        RpcClient rpcClient = new RpcClient("1.0.0", "binghe", "jdk", 3000, false, false);
        AsyncObjectProxy async = rpcClient.createAsync(DemoService.class);
        RPCFuture call = async.call("hello", "abc");
        LOGGER.info("返回的结果数据===>>> " + call.get());
        rpcClient.shutdown();
    }
}
