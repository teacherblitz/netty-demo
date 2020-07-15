package com.teacherblitz.aio.hanlder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author: teacherblitz
 * @since: 2020/7/15
 */
public class AsyncTimeClientHandler implements CompletionHandler<Void, AsyncTimeClientHandler>,Runnable{

    private AsynchronousSocketChannel client;
    private CountDownLatch latch;
    private String host;
    private int port;

    public AsyncTimeClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            client = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        client.connect(new InetSocketAddress(host, port), this, this);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, AsyncTimeClientHandler attachment) {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        writeBuffer.put(req);
        writeBuffer.flip();
        client.write(writeBuffer, writeBuffer,
                new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer buffer) {
                        if(buffer.hasRemaining()){
                            client.write(buffer, buffer, this);
                        }else{
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            client.read(
                                    readBuffer,
                                    readBuffer,
                                    new CompletionHandler<Integer, ByteBuffer>() {
                                        @Override
                                        public void completed(Integer result, ByteBuffer byteBuffer) {
                                            byteBuffer.flip();
                                            byte[] bytes = new byte[byteBuffer.remaining()];
                                            byteBuffer.get(bytes);
                                            String body;
                                            try {
                                                body = new String(bytes, "UTF-8");
                                                System.out.println("Now is :" + body);
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            } finally {
                                                latch.countDown();
                                            }
                                        }

                                        @Override
                                        public void failed(Throwable exc, ByteBuffer byteBuffer) {
                                            try {
                                                client.close();
                                            } catch (IOException e) {
                                                // ignore is close
                                            } finally {
                                                latch.countDown();
                                            }
                                        }
                                    });
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer buffer) {
                        try {
                            client.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    }
                });
    }

    @Override
    public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
        exc.printStackTrace();
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
    }
}
