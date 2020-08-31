package com.teacherblitz.msgpack.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * msgpack 解码器示例
 * 
 * @author: <a href="mailto:teacherblitz@gmail.com">teacherblitz</a>
 * @since: 2020/8/5
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext arg0, ByteBuf arg1, List<Object> arg2) throws Exception {
        final byte[] array;
        // 可读字节长度
        final int length = arg1.readableBytes();
        array = new byte[length];
        // 从数据包中获取要解码的byte数组
        arg1.getBytes(arg1.readerIndex(), array, 0, length);
        MessagePack msgpack = new MessagePack();
        // 调用read方法将其反序列化为Object对象
        arg2.add(msgpack.read(array));
    }
}
