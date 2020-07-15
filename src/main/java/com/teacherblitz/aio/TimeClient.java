package com.teacherblitz.aio;

import com.teacherblitz.aio.hanlder.AsyncTimeClientHandler;

/**
 * @author: teacherblitz
 * @since: 2020/7/15
 */
public class TimeClient {

    public final static int port = 8000;

    public static void main(String[] args) {
        new Thread(new AsyncTimeClientHandler("127.0.0.1", port),
                "NIO-AsyncTimeClientHandler-001").start();
    }
}
