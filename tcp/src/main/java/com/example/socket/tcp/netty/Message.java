package com.example.socket.tcp.netty;

import java.io.Serializable;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/5/6 16:32
 */
public class Message implements Serializable {

    private static final long serialVersionUID = -5756901646411393269L;

    private String clientId;//发送者客户端ID

    private MsgType type;//消息类型

    private String data;//数据

    private String targetId;//目标客户端ID

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Message(){

    }

    public Message(MsgType type){
        this.type = type;
    }


    @Override
    public String toString() {
        return "Message{" +
                "clientId='" + clientId + '\'' +
                ", type=" + type +
                ", data='" + data + '\'' +
                ", targetId='" + targetId + '\'' +
                '}';
    }
}
