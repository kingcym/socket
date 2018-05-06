package com.example.socket.tcp.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.socket.SocketChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;


/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/5/6 17:28
 */
//@RestController
public class COnt {
    private static final NettyServer instance = NettyServer.getInstance();
    private static final  NettyClient instance1 = NettyClient.getInstance();


    @GetMapping("/dd")
    public void aVoid() throws InterruptedException {
        Message loginMsg=new Message(MsgType.LOGIN);
        loginMsg.setClientId("001");
        loginMsg.setTargetId("192.168.1.38");
        loginMsg.setType(MsgType.LOGIN);
        instance1.socketChannel.writeAndFlush(JSON.toJSON(loginMsg)+"\r\n");
    }


    @GetMapping("/dd1")
    public void aVoid1() throws InterruptedException, IOException {
        Map<String , SocketChannel> map = NettyChannelMap.map();
        System.out.println(map.size());
        for(Map.Entry<String,SocketChannel > entry:map.entrySet()){
            SocketChannel socketChannel = entry.getValue();
            socketChannel.writeAndFlush("写入值：www"+"\r\n");

            socketChannel.close();
        }



    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
        process.waitFor();
        process.destroy();
    }
}
