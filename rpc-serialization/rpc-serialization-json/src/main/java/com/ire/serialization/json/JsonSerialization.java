package com.ire.serialization.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ire.common.exception.SerializerException;
import com.ire.rpc.spi.annotation.SPIClass;
import com.ire.serilalization.api.Serialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @Date 2025/12/6 16:27
 */
@SPIClass
public class JsonSerialization implements Serialization {
    private final Logger logger = LoggerFactory.getLogger(JsonSerialization.class);

    private static ObjectMapper objMapper = new ObjectMapper();

    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
        objMapper.setDateFormat(dateFormat);
        objMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        objMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, false);
        objMapper.disable(SerializationFeature.FLUSH_AFTER_WRITE_VALUE);
        objMapper.disable(SerializationFeature.CLOSE_CLOSEABLE);
        objMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
        
        // 注册自定义的Class类型反序列化器
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Class.class, new ClassDeserializer());
        objMapper.registerModule(module);
    }
    
    /**
     * 自定义Class类型反序列化器，将字符串类名转换为Class对象
     */
    private static class ClassDeserializer extends JsonDeserializer<Class<?>> {
        @Override
        public Class<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String className = p.getValueAsString();
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new IOException("Class not found: " + className, e);
            }
        }
    }

    @Override
    public <T> byte[] serialize(T obj){
        logger.info("execute json serialize...");
        if (obj == null){
            throw new SerializerException("serialize object is null");
        }
        byte[] bytes = new byte[0];
        try {
            bytes = objMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            throw new SerializerException(e.getMessage(), e);
        }
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> cls) {
        logger.info("execute json deserialize...");
        if (data == null){
            throw new SerializerException("deserialize data is null");
        }
        T obj = null;
        try {
            obj = objMapper.readValue(data,cls);
        } catch (IOException e) {
            throw new SerializerException(e.getMessage(), e);
        }
        return obj;
    }
}
