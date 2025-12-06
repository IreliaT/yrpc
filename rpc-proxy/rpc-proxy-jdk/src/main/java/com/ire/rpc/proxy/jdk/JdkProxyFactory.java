package com.ire.rpc.proxy.jdk;

import com.ire.rpc.proxy.api.BaseProxyFactory;
import com.ire.rpc.proxy.api.ProxyFactory;
import com.ire.rpc.spi.annotation.SPIClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

/**
 * @Date 2025/11/27 18:16
 */
@SPIClass
public class JdkProxyFactory <T> extends BaseProxyFactory<T> implements ProxyFactory {

    private final Logger logger = LoggerFactory.getLogger(JdkProxyFactory.class);

    @Override
    public <T> T getProxy(Class<T> clazz) {
        logger.info("基于JDK动态代理...");
        return (T) Proxy.newProxyInstance(
            clazz.getClassLoader(),
            new Class<?>[]{clazz},
            objectProxy
        );
    }
}
