package com.example.socket.tcp.netty;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/5/6 16:39
 */
public class NettyClient {
    private static int port;
    private static String host;
    public SocketChannel socketChannel;


    private static final NettyClient single = new NettyClient();
    //静态工厂方法
    public static NettyClient getInstance() {
        return single;
    }

    private NettyClient() {
        this.port = 9999;
        this.host = "localhost";
        start();
    }


    private void start() {
        ChannelFuture future = null;
        EventLoopGroup eventLoopGroup= null;
        try {
             eventLoopGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.group(eventLoopGroup);
            bootstrap.remoteAddress(host, port);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new IdleStateHandler(20, 10, 0));
                    socketChannel.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                    socketChannel.pipeline().addLast("encoder",new StringEncoder(Charset.forName("UTF-8")));
                    socketChannel.pipeline().addLast("decoder",new StringDecoder(Charset.forName("UTF-8")));
                    socketChannel.pipeline().addLast("handler",new NettyClientHandler());
                }
            });
            future = bootstrap.connect(host, port).sync();
            if (future.isSuccess()) {
                socketChannel = (SocketChannel) future.channel();
                System.out.println("connect server  成功---------");
               Channel channel = future.channel();

              // channel.closeFuture().sync();//等待作用，让程序不停止
            } else {
                System.out.println("连接失败！");
                System.out.println("准备重连！");
                start();
            }
        } catch (Exception e) {

        } finally {
            //eventLoopGroup.shutdownGracefully();

        }
    }

    public static void main(String[] args) {
        Message loginMsg=new Message(MsgType.LOGIN);
        loginMsg.setClientId("001");
        loginMsg.setTargetId("192.168.1.38");
        loginMsg.setType(MsgType.LOGIN);
        NettyClient instance1 = NettyClient.getInstance();
        instance1.socketChannel.writeAndFlush(JSON.toJSON(loginMsg)+"\r\n");
    }
}
