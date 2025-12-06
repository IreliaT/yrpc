package com.ire.codec;

import com.ire.common.utils.SerializationUtils;
import com.ire.protocol.RpcProtocol;
import com.ire.protocol.header.RpcHeader;
import com.ire.serilalization.api.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date 2025/10/23 16:07
 */
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol<Object>> implements RpcCodec {
    private static final Logger logger = LoggerFactory.getLogger(RpcEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol<Object> msg, ByteBuf byteBuf) throws Exception {
        RpcHeader header = msg.getHeader();
        byteBuf.writeShort(header.getMagic());
        byteBuf.writeByte(header.getMsgType());
        byteBuf.writeByte(header.getStatus());
        byteBuf.writeLong(header.getRequestId());
        String serializationType = header.getSerializationType();
        logger.info("RpcEncoder 开始编码, 序列化类型: {}", serializationType);
        //TODO Serialization是扩展点
        Serialization serialization = getSerialization(serializationType);
        if (serialization == null) {
            logger.error("获取序列化实现失败, 类型: {}", serializationType);
            throw new RuntimeException("无法获取序列化实现: " + serializationType);
        }
        logger.info("获取到序列化实现: {}", serialization.getClass().getName());
        byteBuf.writeBytes(SerializationUtils.paddingString(serializationType).getBytes("UTF-8"));
        byte[] data = serialization.serialize(msg.getBody());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
        logger.info("RpcEncoder 编码完成, 数据长度: {}", data.length);
    }
}
