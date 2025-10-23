package com.ire.consumer.handler;

import com.alibaba.fastjson.JSONObject;
import com.ire.proeocol.RpcProtocol;
import com.ire.proeocol.header.RpcHeaderFactory;
import com.ire.proeocol.request.RpcRequest;
import com.ire.proeocol.response.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date 2025/10/23 16:22
 */
public class RpcTestConsumerHandler  extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {
    private final Logger logger = LoggerFactory.getLogger(RpcTestConsumerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("发送数据开始...");
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
        logger.info("服务消费者发送的数据===>>>{}", JSONObject.toJSONString(protocol));
        ctx.writeAndFlush(protocol);
        logger.info("发送数据完毕...");

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol<RpcResponse> protocol) throws Exception {
        logger.info("服务消费者接收到的数据===>>>{}", JSONObject.toJSONString(protocol));
    }
}