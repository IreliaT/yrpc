package com.ire.rpc.provider.common.handler;

import com.alibaba.fastjson.JSONObject;
import com.ire.common.helper.RpcServiceHelper;
import com.ire.common.threadpool.ServerThreadPool;
import com.ire.proeocol.RpcProtocol;
import com.ire.proeocol.enums.RpcStatus;
import com.ire.proeocol.enums.RpcType;
import com.ire.proeocol.header.RpcHeader;
import com.ire.proeocol.request.RpcRequest;
import com.ire.proeocol.response.RpcResponse;
import constants.RpcConstants;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

public class RpcProviderHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {
    private final Logger logger = LoggerFactory.getLogger(RpcProviderHandler.class);

    private final String reflectType;

    private final Map<String, Object> handlerMap;

    public RpcProviderHandler(String reflectType, Map<String, Object> handlerMap) {
        this.reflectType = reflectType;
        this.handlerMap = handlerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcRequest> protocol) throws Exception {
        ServerThreadPool.submit(() -> {
            RpcHeader header = protocol.getHeader();
            header.setMsgType((byte)RpcType.RESPONSE.getType());
            RpcRequest request = protocol.getBody();
            logger.debug("Receive request " + header.getRequestId());
            RpcProtocol<RpcResponse> responseRpcProtocol = new RpcProtocol<RpcResponse>();
            RpcResponse response = new RpcResponse();
            try {
                Object result = handle(request);
                response.setResult(result);
                response.setAsync(request.getAsync());
                response.setOneway(request.getOneway());
                header.setStatus((byte)RpcStatus.SUCCESS.getCode());
            } catch (Throwable t) {
                response.setError(t.toString());
                header.setStatus((byte)RpcStatus.FAIL.getCode());
                logger.error("RPC Server handle request error", t);
            }
            responseRpcProtocol.setHeader(header);
            responseRpcProtocol.setBody(response);
            ctx.writeAndFlush(responseRpcProtocol).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    logger.debug("Send response for request " + header.getRequestId());
                }
            });
        });
    }

    private Object handle(RpcRequest request) throws Throwable {
        String serviceKey =
            RpcServiceHelper.buildServiceKey(request.getClassName(), request.getVersion(), request.getGroup());
        Object serviceBean = handlerMap.get(serviceKey);
        if (serviceBean == null) {
            throw new RuntimeException(
                String.format("service not exist: %s:%s", request.getClassName(), request.getMethodName()));
        }

        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        logger.debug(serviceClass.getName());
        logger.debug(methodName);
        if (parameterTypes != null && parameterTypes.length > 0) {
            for (int i = 0; i < parameterTypes.length; ++i) {
                logger.debug(parameterTypes[i].getName());
            }
        }

        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; ++i) {
                logger.debug(parameters[i].toString());
            }
        }
        return invokeMethod(serviceBean, serviceClass, methodName, parameterTypes, parameters);
    }

    //TODO 目前使用JDK动态代理方式，此处埋点
    private Object invokeMethod(Object serviceBean, Class<?> serviceClass, String methodName, Class<?>[] parameterTypes,
                                Object[] parameters) throws Throwable {
        return switch (this.reflectType) {
            case RpcConstants.REFLECT_TYPE_JDK ->
                invokeJDKMethod(serviceBean, serviceClass, methodName, parameterTypes, parameters);

            case RpcConstants.REFLECT_TYPE_CGLIB ->
                invokeCGLibMethod(serviceBean, serviceClass, methodName, parameterTypes, parameters);

            default -> throw new IllegalStateException("Unexpected value: " + this.reflectType);
        };

    }

    public Object invokeJDKMethod(Object serviceBean, Class<?> serviceClass, String methodName,
                                  Class<?>[] parameterTypes, Object[] parameters) throws Exception {
        Method method = serviceClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(serviceBean, parameters);
    }

    public Object invokeCGLibMethod(Object serviceBean, Class<?> serviceClass, String methodName,
                                    Class<?>[] parameterTypes, Object[] parameters) throws Exception {
        FastClass fastClass = FastClass.create(serviceClass);
        FastMethod method = fastClass.getMethod(methodName, parameterTypes);
        return method.invoke(serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("server caught exception", cause);
        ctx.close();
    }
}
