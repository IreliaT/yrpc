package com.ire.consumer.handler;

import com.ire.proeocol.RpcProtocol;
import com.ire.proeocol.header.RpcHeaderFactory;
import com.ire.proeocol.request.RpcRequest;
import com.ire.rpc.consumer.RpcConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date 2025/10/23 18:59
 */
public class RpcConsumerHandlerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcConsumerHandlerTest.class);

    public static void main(String[] args) throws Exception {
        RpcConsumer consumer = RpcConsumer.getInstance();
        Object result = consumer.sendRequest(getRpcRequestProtocol());
        LOGGER.info("从服务消费者获取到的数据===>>>" + result.toString());
        Thread.sleep(2000);
        consumer.close();
    }

    private static RpcProtocol<RpcRequest> getRpcRequestProtocol(){
        //模拟发送数据
        RpcProtocol<RpcRequest> protocol = new RpcProtocol<RpcRequest>();
        protocol.setHeader(RpcHeaderFactory.getRequestHeader("jdk"));
        RpcRequest request = new RpcRequest();
        request.setClassName("com.ire.test.api.DemoService");
        request.setGroup("binghe");
        request.setMethodName("hello");
        request.setParameters(new Object[]{"binghe"});
        request.setParameterTypes(new Class[]{String.class});
        request.setVersion("1.0.0");
        request.setAsync(false);
        request.setOneway(false);
        protocol.setBody(request);
        return protocol;
    }
}
