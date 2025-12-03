package com.ire.rpc.spi.factory;

import com.ire.rpc.spi.annotation.SPI;
import com.ire.rpc.spi.loader.ExtensionLoader;

import java.util.Optional;

/**
 * @Date 2025/12/3 18:13
 */
public class SpiExtensionFactory implements ExtensionFactory{
    @Override
    public <T> T getExtension(String key, Class<T> clazz) {
        return Optional.ofNullable(clazz)
            .filter(Class::isInterface)
            .filter(cls -> cls.isAnnotationPresent(SPI.class))
            .map(ExtensionLoader::getExtensionLoader)
            .map(ExtensionLoader::getDefaultSpiClassInstance)
            .orElse(null);
    }
}
