package com.ire.protocol.header;

import com.ire.common.constants.RpcConstants;
import com.ire.common.id.IdFactory;
import com.ire.protocol.enums.RpcType;

/**
 * @Date 2025/10/23 15:09
 */
public class RpcHeaderFactory {

    public static RpcHeader getRequestHeader(String serializationType){
        RpcHeader header = new RpcHeader();
        long requestId = IdFactory.getId();
        header.setMagic(RpcConstants.MAGIC);
        header.setRequestId(requestId);
        header.setMsgType((byte) RpcType.REQUEST.getType());
        header.setStatus((byte) 0x1);
        header.setSerializationType(serializationType);
        return header;
    }
}
