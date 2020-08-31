package com.teacherblitz.marshalling.handler;

import com.teacherblitz.marshalling.dto.SubscribeReqDTO;
import com.teacherblitz.marshalling.dto.SubscribeRespDTO;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * JBoos Marshalling客户端消息处理类
 * 
 * @author: <a href="mailto:teacherblitz@gmail.com">teacherblitz</a>
 * @since: 2020/8/31
 */
public class SubReqClientHandler extends ChannelHandlerAdapter {

    public SubReqClientHandler() {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.write(req(i));
        }
        ctx.flush();
    }

    /**
     * 构建客户端请求消息实体
     *
     * @param i
     * @return
     */
    public SubscribeReqDTO req(int i) {
        return SubscribeReqDTO.builder()
                .subReqId(i)
                .userName("teacherblitz")
                .productName("JBoos Marshalling 消息")
                .build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeRespDTO respDTO = (SubscribeRespDTO) msg;
        System.out.println("【客户端】：服务端回应的消息为：" + respDTO.toString());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
