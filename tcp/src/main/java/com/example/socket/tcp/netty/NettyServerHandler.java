package com.example.socket.tcp.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;

import java.net.InetAddress;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/5/6 16:35
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //channel失效，从Map中移除
        System.out.println("channel失效"+ctx.channel());
        NettyChannelMap.remove((SocketChannel) ctx.channel());
    }

    /*
     * 建立连接时，返回客户端消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
        ctx.writeAndFlush("客户端"+ InetAddress.getLocalHost().getHostName() + "成功与服务端建立连接！ \n");
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("出现异常！");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println(msg);
        Message message = JSON.parseObject(msg + "", Message.class);
        System.out.println("接收到消息-------------：" + message);
        String clientId = message.getClientId();
        if (MsgType.LOGIN.equals(message.getType())) {
            System.out.printf("将%s添加到队列\n", clientId);
            NettyChannelMap.add(clientId, (SocketChannel) ctx.channel());
        } else {
            if (NettyChannelMap.get(clientId) == null) {
                System.out.printf("登录失败，请重新登录!", clientId); //说明未登录，或者连接断了，服务器向客户端发起登录请求，让客户端重新登录
                message = new Message(MsgType.LOGIN);
                ctx.channel().writeAndFlush(JSON.toJSONString(message));
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }


}