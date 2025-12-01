package com.ire.registry;

import com.ire.protocol.meta.ServiceMeta;
import com.ire.rpc.registry.api.config.RegistryConfig;
import com.ire.rpc.registry.api.service.RegistryService;
import com.ire.rpc.registry.zookeeper.ZookeeperRegistryService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @Date 2025/12/1 15:17
 */
public class ZookeeperRegistryTest {
    private RegistryService registryService;

    private ServiceMeta serviceMeta;

    @Before
    public void init() throws Exception{
        RegistryConfig registryConfig = new RegistryConfig("127.0.0.1:2181", "zookeeper");
        this.registryService = new ZookeeperRegistryService();
        this.registryService.init(registryConfig);
        this.serviceMeta = new ServiceMeta(ZookeeperRegistryTest.class.getName(), "1.0.0", "binghe", "127.0.0.1", 8080);
    }

    @Test
    public void testRegister() throws Exception {
        this.registryService.register(serviceMeta);
    }

    @Test
    public void testUnRegister() throws Exception {
        this.registryService.unRegister(serviceMeta);
    }

    @Test
    public void testDiscovery() throws Exception {
        this.registryService.discovery(RegistryService.class.getName(), "binghe".hashCode());
    }

    @Test
    public void testDestroy() throws IOException {
        this.registryService.destroy();
    }
}
