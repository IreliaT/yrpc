package com.ire.provider.service.impl;

import com.ire.annotation.RpcService;
import com.ire.provider.service.DemoService;

@RpcService(interfaceClass = DemoService.class, interfaceClassName = "com.ire.provider.service.DemoService", group = "binghe")
public class DemoServiceImpl implements DemoService {
}
