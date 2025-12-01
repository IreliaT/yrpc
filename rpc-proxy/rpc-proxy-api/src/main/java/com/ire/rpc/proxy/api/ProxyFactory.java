package com.ire.rpc.proxy.api;

import com.ire.rpc.proxy.api.config.ProxyConfig;

/**
 * @Date 2025/11/28 18:27
 */
public interface ProxyFactory {

    /**
     * 获取代理对象
     */
    <T> T getProxy(Class<T> clazz);

    /**
     * 默认初始化方法
     */
    default <T> void init(ProxyConfig<T> proxyConfig){}
}
