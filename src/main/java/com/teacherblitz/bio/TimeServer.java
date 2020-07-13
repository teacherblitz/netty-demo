package com.teacherblitz.bio;

import com.teacherblitz.bio.handler.TimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 同步阻塞BIO
 *
 * @author: teacherblitz
 * @since: 2020/7/13
 */
public class TimeServer {

    /**
     * 同步阻塞
     * @param args
     * @throws IOException
     */
    public static void bioMethod(String[] args) throws IOException {
        int port = 8080;
        if(args != null && args.length >0){
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port：" + port);
            Socket socket = null;
            while(true){
                socket = server.accept();
                // 每次客户端连接都会新建一个线程
                new Thread(new TimeServerHandler(socket)).start();
            }
        } finally {
            if(server != null){
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }


    /**
     * 伪IO
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{
        int port = 8080;
        if(args != null && args.length >0){
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port：" + port);
            Socket socket = null;
            // 创建IO任务线程池
            TimeServerHandlerExecutePool execute = new TimeServerHandlerExecutePool(50, 10000);
            while(true){
                socket = server.accept();
                execute.execute(new TimeServerHandler(socket));
            }
        } finally {
            if(server != null){
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }
}
