package com.teacherblitz.marshalling;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * JBoos Marshalling编解码器工厂类
 * 
 * @author: <a href="mailto:teacherblitz@gmail.com">teacherblitz</a>
 * @since: 2020/8/28
 */
public final class MarshallingCodeFactory {

    /**
     * 创建JBoos Marshalling解码器MarshallingDecoder
     *
     * @return
     */
    public static MarshallingDecoder buildMarshallingDecoder() {
        MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration config = new MarshallingConfiguration();
        config.setVersion(5);
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(factory, config);
        MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024);
        return decoder;
    }

    /**
     * 创建JBoos Marshalling编码器MarshallingEncoder
     *
     * @return
     */
    public static MarshallingEncoder buildMarshallingEncoder() {
        MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration config = new MarshallingConfiguration();
        config.setVersion(5);
        DefaultMarshallerProvider provider = new DefaultMarshallerProvider(factory, config);
        MarshallingEncoder encoder = new MarshallingEncoder(provider);
        return encoder;
    }
}
