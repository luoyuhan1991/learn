package org.luoyuhan.learn.java.niorpc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.luoyuhan.learn.serialiazation.HessianUtil;
import org.luoyuhan.learn.serialiazation.ProtoStuffUtil;

import java.util.List;

/**
 * @author luoyuhan
 */
public class RpcDecoder extends ByteToMessageDecoder {
    private final Class<?> genericClass;
    private final int serializationType;

    public RpcDecoder(Class<?> genericClass, int serializationType) {
        // 设置需要解码的类型
        this.genericClass = genericClass;
        this.serializationType = serializationType;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        // 4个字节保存消息长度
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        // 标记byte buff读取位置
        byteBuf.markReaderIndex();
        int dataLength = byteBuf.readInt();
        if (dataLength < 0) {
            channelHandlerContext.close();
        }
        // 可读取长度小于约定长度，重置到标记位置
        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();
        }
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);

        Object obj = new Object();
        switch (serializationType) {
            case 0:
                obj = ProtoStuffUtil.deserializer(data, genericClass);
                break;
            case 1:
                obj = HessianUtil.deserializer(data, genericClass);
            default:
                break;
        }
        list.add(obj);
    }
}