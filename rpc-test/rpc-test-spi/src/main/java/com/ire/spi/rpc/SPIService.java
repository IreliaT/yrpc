package com.ire.spi.rpc;

import com.ire.rpc.spi.annotation.SPI;

/**
 * @Date 2025/12/3 18:31
 */
@SPI("spiService")
public interface SPIService {

    String hello(String name);
}

