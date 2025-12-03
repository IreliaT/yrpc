package com.ire.spi.rpc;

import com.ire.rpc.spi.annotation.SPIClass;

/**
 * @Date 2025/12/3 18:32
 */
@SPIClass
public class SPIServiceImpl implements SPIService {
    @Override
    public String hello(String name) {
        return "hello" + name;
    }
}
