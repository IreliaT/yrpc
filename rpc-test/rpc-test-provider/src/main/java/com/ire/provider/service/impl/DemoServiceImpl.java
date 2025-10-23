package com.ire.provider.service.impl;

import com.ire.annotation.RpcService;
import com.ire.test.api.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RpcService(interfaceClass = DemoService.class, interfaceClassName = "com.ire.test.api.DemoService", group = "binghe")
public class DemoServiceImpl implements DemoService {

    private final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);
    @Override
    public String hello(String name) {
        logger.info("调用hello方法传入的参数为===>>>{}", name);
        return "hello " + name;
    }
}
