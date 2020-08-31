package com.teacherblitz.msgpack;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * msgpack序列化示例
 *
 * @author: <a href="mailto:teacherblitz@gmail.com">teacherblitz</a>
 * @since: 2020-08-05
 */
public class MsgPackSerializableDemo {

    public static void main(String[] args) throws IOException {
        // 创建序列化对象
        List<String> src = new ArrayList<>();
        src.add("msgpack");
        src.add("kumofs");
        src.add("viver");
        MessagePack msgPack = new MessagePack();
        // msgpack序列化
        byte[] writeBuffer = msgPack.write(src);
        // msgpack模板反序列化
        List<String> serialList = msgPack.read(writeBuffer, Templates.tList(Templates.TString));
        // 输出结果
        serialList.forEach(System.out::println);
    }
}
