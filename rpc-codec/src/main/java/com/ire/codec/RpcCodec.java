package com.ire.codec;

import com.ire.rpc.spi.loader.ExtensionLoader;
import com.ire.serilalization.api.Serialization;
import com.ire.serilalization.jdk.JdkSerialization;

/**
 * @Date 2025/10/23 16:05
 */
public interface RpcCodec {

    default Serialization getSerialization(String serializationType){
        return ExtensionLoader.getExtension(Serialization.class,serializationType);
    }
}
