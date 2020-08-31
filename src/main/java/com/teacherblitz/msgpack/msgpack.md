#### 注意
```
1. 注意要在消息类上加上@Message注解，否则服务端接收不到消息，也不会有任何报错信息
2. 如果没有考虑粘包/半包的处理，msgpack是不能正常的工作的
```
#### 粘包/半包解决策略
```
1. 常用的在消息头中新增报文长度字段：Netty提供：LengthFieldPrepender和LengthFieldBasedDecoder
```