package com.teacherblitz.netty.decoder.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 固定长度解码器客户端通道处理器
 *
 * @author: <a href="mailto:teacherblitz@gmail.com">teacherblitz</a>
 * @since: 2020/8/4
 */
public class FixedLengthFrameEchoClientHandler extends ChannelHandlerAdapter {

    private int counter;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 1; i++) {
            // 指定长度才不会出现粘包
            ctx.writeAndFlush(Unpooled.copiedBuffer("zxcvbn44444masdfghjklqw21".getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("No." + ++counter + "client msg[" + msg + "]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
