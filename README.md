#### Netty成神之路
-
##### tcp粘包/拆包问题说明
- 服务端分别两次读取到两个独立的数据包，分别是D1和D2，没有粘包和拆包；
- 服务端一次接受到了两个数据包，D1和D2粘合在一起，被称为tcp粘包；
- 服务端分两次读取到了两个数据包，第一次读取到了完整的D1和D2的部分内容，第二次读取到了D2包的剩余内容，
  这被称为tcp拆包；
- 服务端分两次读取到了两个数据包，第一次读取到了D1包的部分内容D1_1，第二次读取到了D1包的剩余内容D1_2
  和D2包的整包；

##### tcp粘包/拆包发生的原因
- （1）应用程序write写入的字节大小大于套接口发送缓冲区大小；
- （2）进行MSS大小的TCP分段；
- （3）以太网帧的payload大于MTU进行IP分片；
 
##### 粘包问题的解决策略
- 由于底层的tcp无法理解上层的业务数据，所以在底层是无法保证数据包不被拆分和重组的，这个问题只能通道上
  层的应用协议栈设计来解决，根据业界的主流协议的解决方案如下：
  （1）消息定长，例如每个报文的大小为固定长度200字节，如果不够，空位补空格；
  （2）在包尾增加回车换行符进行分割，例如ftp协议；
  （3）将消息分为消息头和消息体，消息头中包含表示消息总长度（消息体长度）的字段，通常设计思路为消息头
       的第一个字段使用int32来表示消息的总长度；
  （4）更复杂的应用层协议；