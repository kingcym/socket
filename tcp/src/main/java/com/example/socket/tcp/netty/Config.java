package com.example.socket.tcp.netty;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/5/6 17:18
 */
//@Configuration
public class Config {

    //@Bean
    public NettyServer nettyServer(){
        NettyServer nettyServer = null;
        try {
            //nettyServer = new NettyServer(9999);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nettyServer;
    }


}
