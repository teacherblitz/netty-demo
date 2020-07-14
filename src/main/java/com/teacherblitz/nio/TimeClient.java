package com.teacherblitz.nio;

import com.teacherblitz.nio.handler.TimeClientHandler;

/**
 * NIO 客户端
 *
 * @author: teacherblitz
 * @since: 2020/7/14
 */
public class TimeClient {

    private final static int port = 8000;

    public static void main(String[] args) {
        new Thread(new TimeClientHandler("127.0.0.1", port)).start();
    }
}
