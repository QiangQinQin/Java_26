package network.NIO2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

public class MyNIOClient_noBlock {
    public static void main(String[] args) {
        SocketChannel socketChannel = null;

        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);//非阻塞  有可能一下连接不上，先去做其他的，过一会查一下
            Selector selector = Selector.open();//复用器
            //SocketChannel.java  323行channel非阻塞时connect非阻塞
            //此处connect是非阻塞方法，一次连接没成功，就 注册（然后能得到selectedKeys()或interestKeys里就会有你需要的信息）为 可连接事件到复用器上轮询
            if(!socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999))){
                socketChannel.register(selector, SelectionKey.OP_CONNECT);

                //只遍历 socketChannel注册的可连接事件？？？？？？？？？？？？（因为selector是当前类的）
                // 阻塞直到  有 可连接事件 已经就绪
                while(selector.select() > 0){
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while(iterator.hasNext()){//正在处理的这个是自己（因为是当前类的selector的迭代器对象）？？？？？？？？？？？？？？
                        // 他好像只能让 迭代器里 所有就绪链接（其实只有一个）  都连接上？？？？
                        SelectionKey key = iterator.next();
                        iterator.remove();//因为下面就要处里他了，可以把他从iterator里删除掉
                        if(key.isConnectable()){//是可链接的事件
                            SocketChannel channel = (SocketChannel) key.channel();
                            //完成三次握手连接
                            channel.finishConnect();
                            System.out.println("客户端已启动...");//启动完，没有事件了就跳出while循环？？???????
                        }
                    }
                }
            }


            //客户端可以不用注册到写 或 读事件，因为是主动的    而服务器是被动的读，需要注册
            Scanner scanner = new Scanner(System.in);
            while(true){
                //连接成功，给服务器端 发送 消息
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                System.out.println("客户端要发送消息到服务器端：");
                String info = scanner.nextLine();
                if("".equals(info) || "exit".equals(info)){
                    break;
                }
                buffer.put(info.getBytes(StandardCharsets.UTF_8));//将控制台输入的以UTF-8的形式放到buffer里
                //每次用完记得读写切换
                buffer.flip();
                socketChannel.write(buffer);//写到通道里，即发送过去
                buffer.clear();//每次用完记得清空


                // 获取 服务器端的响应消息
                //因为前面定义的是非阻塞，read如果没就绪（需要服务器端再输入台写点东西），就跳过；有就读到buffer里
                int read = socketChannel.read(buffer);
                if(read == -1){
                    break;
                }
                buffer.flip();

                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                System.out.println("服务器端响应消息："+new String(bytes, 0, bytes.length));
                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socketChannel != null){
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
