package com.example.socket.tcp.client;

import java.io.*;
import java.net.Socket;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/5/6 0:04
 */
public class CYMClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8888);
        OutputStream os = socket.getOutputStream();

        PrintWriter pw = new PrintWriter(os);
        pw.write("今天天气会话");
        pw.flush();


        socket.shutdownOutput();

        //获取输入流，读取客户端信息
        InputStream inputStream = socket.getInputStream();//字节输入流
        //字节转字符
        InputStreamReader is = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(is);
        String info;
        while ((info = reader.readLine()) != null) {
            System.out.println("接收服务端信息：" + info);
        }
        reader.close();
        is.close();
        inputStream.close();

        pw.close();
        os.close();
        socket.close();
    }
}
