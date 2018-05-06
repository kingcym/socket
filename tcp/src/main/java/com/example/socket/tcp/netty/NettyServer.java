package com.example.socket.tcp.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/5/6 16:33
 */
public class NettyServer {

    private static int port;
    public SocketChannel socketChannel;

    private static final NettyServer single = new NettyServer();
    //静态工厂方法
    public static NettyServer getInstance() {
        return single;
    }

    private NettyServer() {
        this.port = 9999;
        try {
            bind();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void bind() throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 128);
        //通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        //保持长连接状态
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline p = socketChannel.pipeline();
                //字符串类解析
                // 以("\n")为结尾分割的 解码器
                p.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                p.addLast("encoder",new StringEncoder(Charset.forName("UTF-8")));
                p.addLast("decoder",new StringDecoder(Charset.forName("UTF-8")));
                p.addLast("handler",new NettyServerHandler());
            }
        });
        ChannelFuture f = bootstrap.bind(port).sync();
        if (f.isSuccess()) {
            System.out.println("server start---------------");
            //Channel channel = f.channel();
           //channel.closeFuture().sync();//等待作用，让程序不停止
        }
    }


    public static void main(String[] args) {
        NettyServer instance = NettyServer.getInstance();

    }

}
