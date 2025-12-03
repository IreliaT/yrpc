package com.ire.rpc.spi.factory;

import com.ire.rpc.spi.annotation.SPI;

/**
 * @Date 2025/12/3 18:04
 */
@SPI("spi")
public interface ExtensionFactory {

    <T> T getExtension(String key, Class<T> clazz);
}
