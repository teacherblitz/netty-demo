package com.teacherblitz.nio;

/**
 * NIO服务端
 *
 * @author: teacherblitz
 * @since: 2020/7/14
 */
public class TimeServer {

    private final static int port = 8000;

    public static void main(String[] args) {
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }
}
