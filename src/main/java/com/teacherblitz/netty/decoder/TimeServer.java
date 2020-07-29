package com.teacherblitz.netty.decoder;

import com.teacherblitz.netty.decoder.handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 支持TCP粘包的TimeServer‘
 * LineBasedFrameDecoder 和 StringDecoder api说明
 *
 * @author: <a href="mailto:teacherblitz@gmail.com">teacherblitz</a>
 * @since: 2020/7/27
 */
public class TimeServer {

    public static void main(String[] args) throws Exception {
        bind(8000);
    }

    public static void bind(int port) throws Exception {
        // 配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new com.teacherblitz.netty.decoder.ChildChannelHandler());
            // 绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();
            // 等待服务器监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel arg0) throws Exception {
        // 换行符解码器，如有\n或者\t\n则到索引位置组成一行，如果读到最大长度任然没有发现换行符，则抛出异常
        arg0.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 将接受到的对象转换成字符串
        arg0.pipeline().addLast(new StringDecoder());
        // 自定义处理器
        arg0.pipeline().addLast(new TimeServerHandler());
    }
}
