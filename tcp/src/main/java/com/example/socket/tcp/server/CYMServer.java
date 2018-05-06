package com.example.socket.tcp.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/5/5 23:46
 */
public class CYMServer {
    public static void main(String[] args) throws IOException {
        //创建服务端socket
        ServerSocket serverSocket = new ServerSocket(8888);
        //调用accept方法开始监听，等待客户端连接
        System.out.println("--服务端即将启动，等待客户端连接---");
        Socket socket = serverSocket.accept();
        //获取输入流，读取客户端信息
        InputStream inputStream = socket.getInputStream();//字节输入流
        //字节转字符
        InputStreamReader is = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(is);
        String info;
        while ((info = reader.readLine()) != null) {
            System.out.println("接收客户端信息：" + info);
        }

        socket.shutdownInput();


        OutputStream os = socket.getOutputStream();

        PrintWriter pw = new PrintWriter(os);
        pw.write("我已经接收到信息了");
        pw.flush();
        pw.close();
        os.close();

        reader.close();
        is.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
    }

}
