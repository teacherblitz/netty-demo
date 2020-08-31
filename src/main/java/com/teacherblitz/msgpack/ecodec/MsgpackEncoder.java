package com.teacherblitz.msgpack.ecodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * msgpack 编码器示例
 * 
 * @author: <a href="mailto:teacherblitz@gmail.com">teacherblitz</a>
 * @since: 2020/8/5
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext arg0, Object arg1, ByteBuf arg2) throws Exception {
        MessagePack msgpack = new MessagePack();
        // 序列化
        byte[] writeBuffer = msgpack.write(arg1);
        arg2.writeBytes(writeBuffer);
    }
}
