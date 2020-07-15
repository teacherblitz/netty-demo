package com.teacherblitz.aio;

import com.teacherblitz.aio.hanlder.AsyncTimeServerHandler;

/**
 * AIO案例
 *
 * @author: teacherblitz
 * @since: 2020/7/15
 */
public class TimeServer {

    private final static int port = 8000;

    public static void main(String[] args) {
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        new Thread(timeServer, "AIO-AsyncTimeServerHandler-001").start();
    }
}
