package org.luoyuhan.learn.java.niorpc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.luoyuhan.learn.java.serialiazation.HessianUtil;
import org.luoyuhan.learn.java.serialiazation.ProtoStuffUtil;

/**
 * @author luoyuhan
 */
public class RpcEncoder extends MessageToByteEncoder<Object> {

    private final Class<?> genericClass;
    private final int serializationType;

    public RpcEncoder(Class<?> genericClass, int serializationType) {
        this.genericClass = genericClass;
        this.serializationType = serializationType;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object obj, ByteBuf byteBuf) {
        if (genericClass.isInstance(obj)) {
            byte[] data = new byte[0];
            switch (serializationType) {
                case 0:
                    data = ProtoStuffUtil.serializer(obj);
                    break;
                case 1:
                    data = HessianUtil.serializer(obj);
                default:
                    break;
            }
            byteBuf.writeInt(data.length);
            byteBuf.writeBytes(data);
        }
    }
}
