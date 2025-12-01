package com.ire.rpc.proxy.jdk;

import com.ire.rpc.proxy.api.BaseProxyFactory;
import com.ire.rpc.proxy.api.ProxyFactory;

import java.lang.reflect.Proxy;

/**
 * @Date 2025/11/27 18:16
 */
public class JdkProxyFactory <T> extends BaseProxyFactory<T> implements ProxyFactory {
    @Override
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
            clazz.getClassLoader(),
            new Class<?>[]{clazz},
            objectProxy
        );
    }
}
