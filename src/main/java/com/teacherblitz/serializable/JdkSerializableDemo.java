package com.teacherblitz.serializable;

import com.teacherblitz.pojo.UserInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * Java序列化代码
 * 缺点：
 *  1. 不支持跨平台
 *  2. 字节太长
 *  3. 性能太低
 *
 * @author: <a href="mailto:teacherblitz@gmail.com">teacherblitz</a>
 * @since: 2020-08-04
 */
public class JdkSerializableDemo {

    public static void main(String[] args) {
        try {
            lengthTest(args);
            System.out.println("====================================================================");
            performTest(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 性能测试
     * @param args
     * @throws IOException
     */
    public static void performTest(String[] args) throws IOException {
        UserInfo info = new UserInfo();
        info.buildUserID(100).buildUserName("Welcome to Netty");
        int loop = 10000;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(info);
            os.flush();
            os.close();
            byte[] b = bos.toByteArray();
            bos.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("The jdk serializable cost time is：" + (endTime - startTime) + "ms");

        System.out.println("------------------------------------------------------------------");

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            byte[] b = info.codeC(buffer);
        }
        endTime = System.currentTimeMillis();
        System.out.println("The byte array cost time is：" + (endTime - startTime) + "ms");
    }

    /**
     * 长度测试
     * @param args
     * @throws IOException
     */
    public static void lengthTest(String[] args) throws IOException {
        UserInfo info = new UserInfo();
        info.buildUserID(100).buildUserName("Welcome to Netty");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(info);
        os.flush();
        os.close();
        byte[] b = bos.toByteArray();
        System.out.println("The jdk serializable length is：" + b.length);
        bos.close();
        System.out.println("-----------------------------------------------------");
        System.out.println("The byte array serializable length is：" + info.codeC().length);
    }
}
