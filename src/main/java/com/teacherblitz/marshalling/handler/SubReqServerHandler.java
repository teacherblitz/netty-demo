package com.teacherblitz.marshalling.handler;

import com.teacherblitz.marshalling.dto.SubscribeReqDTO;
import com.teacherblitz.marshalling.dto.SubscribeRespDTO;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

/**
 * JBoos Marshalling服务端消息处理类
 * 
 * @author: <a href="mailto:teacherblitz@gmail.com">teacherblitz</a>
 * @since: 2020/8/28
 */
public class SubReqServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqDTO reqDTO = (SubscribeReqDTO) msg;
        if ("teacherblitz".equalsIgnoreCase(reqDTO.getUserName())) {
            System.out.println("【JBoos Marshalling服务端】：[" + reqDTO.toString() +"]");
            ctx.writeAndFlush(resp(reqDTO.getSubReqId()));
            System.out.println("【JBoos Marshalling服务端】：已响应...");
        }
    }

    /**
     * 构建服务端响应消息
     *
     * @param subReqId
     * @return
     */
    private SubscribeRespDTO resp(Integer subReqId) {
        return SubscribeRespDTO.builder()
                .subReqId(subReqId)
                .status(1)
                .desc("服务端响应消息...")
                .build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
