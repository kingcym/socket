package com.example.socket.tcp.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/5/6 1:23
 */
public class ServerHandler extends ChannelHandlerAdapter {

    //@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //do something msg
//        ByteBuf buf = (ByteBuf)msg;
//        byte[] data = new byte[buf.readableBytes()];
//        buf.readBytes(data);
//        String request = new String(data, "utf-

        System.out.println("Server: " + msg);
        //写给客户端
        String response = "我是反馈的信息";
        ctx.writeAndFlush(Unpooled.copiedBuffer("8888".getBytes()));
        //.addListener(ChannelFutureListener.CLOSE);


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
