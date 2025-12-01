package com.ire.rpc.proxy.api;

import com.ire.rpc.proxy.api.config.ProxyConfig;
import com.ire.rpc.proxy.api.object.ObjectProxy;

/**
 * @Date 2025/11/28 18:26
 */
public  abstract class BaseProxyFactory<T> implements ProxyFactory {

    protected ObjectProxy<T> objectProxy;

    @Override
    public <T> void init(ProxyConfig<T> proxyConfig) {
        this.objectProxy = new ObjectProxy(proxyConfig.getClazz(),
            proxyConfig.getServiceVersion(),
            proxyConfig.getServiceGroup(),
            proxyConfig.getSerializationType(),
            proxyConfig.getTimeout(),
            proxyConfig.getConsumer(),
            proxyConfig.getAsync(),
            proxyConfig.getOneway(),
            proxyConfig.getRegistryService());
    }
}
