package com.teacherblitz.msgpack;

import com.teacherblitz.msgpack.decoder.MsgpackDecoder;
import com.teacherblitz.msgpack.ecodec.MsgpackEncoder;
import com.teacherblitz.msgpack.handler.MsgpackEchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * 示例编解码器客户端功能测试
 * 
 * @author: <a href="mailto:teacherblitz@gmail.com">teacherblitz</a>
 * @since: 2020/8/5
 */
public class MsgpackEchoClient {

    private final String host;
    private final int port;
    private final int sendNumber;

    public static void main(String[] args) {
        try {
            new MsgpackEchoClient("0.0.0.0", 8000, 10).run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public MsgpackEchoClient(String host, int port, int sendNumber) {
        this.host = host;
        this.port = port;
        this.sendNumber = sendNumber;
    }

    public void run() throws InterruptedException {
        // 配置测试的NIO客户端线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                            ch.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
                            ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                            ch.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
                            ch.pipeline().addLast(new MsgpackEchoClientHandler(sendNumber));
                        }
                    });
            // 客户端链接
            ChannelFuture f = b.connect(host, port).sync();

            // 等待链接关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅关闭，释放客户端资源
            group.shutdownGracefully();
        }
    }
}
