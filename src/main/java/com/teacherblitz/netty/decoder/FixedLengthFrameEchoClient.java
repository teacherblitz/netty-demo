package com.teacherblitz.netty.decoder;

import com.teacherblitz.netty.decoder.handler.FixedLengthFrameEchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 固定长度解码器客户端
 *
 * @author: <a href="mailto:teacherblitz@gmail.com">teacherblitz</a>
 * @since: 2020/8/4
 */
public class FixedLengthFrameEchoClient {

    public static void main(String[] args) {
        try {
            connect("0.0.0.0", 8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void connect(String host, int port) throws InterruptedException {
        // 创建客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new FixedLengthFrameDecoder(20));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new FixedLengthFrameEchoClientHandler());
                        }
                    });
            // 客户端链接
            ChannelFuture f = b.connect(host, port).sync();
            // 等待客户端链接完成关闭
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
