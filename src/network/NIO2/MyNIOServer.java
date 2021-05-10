package network.NIO2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * NIO编程
 */

/*
    通道设置成非阻塞的，他就不会等你的读 写事件就绪，按照从上往下业务代码的逻辑：
    比如服务器端先进行一个读 再进行一个写，如果服务端读完了还没进行写，
    客户端已经把写执行完了，读（不会阻塞，等着收响应）也执行完了，那客户端就没有收到服务端（从控制台写）给他发的东西，又返回while循环的刚开始，
    又给服务器端发东西，然后执行完发现上一轮的写事件才过来了
 */



public class MyNIOServer {
    public static void main(String[] args) {
        ServerSocketChannel serverSocketChannel = null;
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(9999));
            System.out.println("服务器端已经启动...");
            //非阻塞，即读写完成就放到channel，否则就继续往后走
            serverSocketChannel.configureBlocking(false);// （之前 BIO等到读写事件完成后才能接着往下执行）

            //NIO底层是多路复用
            Selector selector = Selector.open();
            //将serverSocketChannel注册到selector复用器（复用器可关注多个channel）上，监听里面的  可接收事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            //等待 监听事件 结果，这个select是一个 阻塞 的方法，直到 有就绪事件（...able） 才会返回
            //用while是因为可能有多轮回复 或者 多个主机来交互，直到中selector没有事件也就不用等就绪了???????????
            while(selector.select() > 0){
                //获取事件的集合
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                //遍历这个集合，选择事件
                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    //要处理他了，就可以删掉
                    iterator.remove();//删除当前指针所指向的元素，即next方法返回的元素

                    //一下总共完成客户端发一个，服务器给他回一个；客户端在发一个，服务器再回一个？？？？？
                    //判断key的类型
                    if(key.isAcceptable()){
                        //通过key获取通道channel
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();//因为还没建立连接，所以返回是ServerSocketChanne
                        //接收 连接（通过三次握手），返回客户端通道
                        SocketChannel accept = channel.accept();
                        System.out.println("客户端："+accept.getRemoteAddress()+"已连接...");
                        //设置accept为非阻塞
                        accept.configureBlocking(false);
                        //将 accept通道 注册到 selector选择器 上 关注读事件（即注册为读事件？？？？？？）（因为链接已建立）
                        accept.register(selector, SelectionKey.OP_READ);
                    }

                    if(key.isReadable()){//对应客户端写东西了  所以认为他 读就绪了（可以被读了）
                        //通过key获取通道channel
                        SocketChannel channel = (SocketChannel) key.channel();
                        //进行读取操作（不直接和数据源打交道）
                        int read = channel.read(readBuffer);
                        if(read == -1){//没读到东西，跳出此次循环
                            //通道关闭
                            channel.close();
                            //连接关闭
                            key.cancel();//让key不在选择器上
                            continue;
                        }
                        //进行读写模式（利用limit  position capacity变量 WPS图）切换（上面58行是读，下面70行想写）
                        readBuffer.flip();
                        //将数据从buffer读取
                        byte[] bytes = new byte[readBuffer.remaining()];//创建 可读的数据长度 个大小的byte数组
                        readBuffer.get(bytes);//读到bytes数组里
                        System.out.println("客户端："+channel.getRemoteAddress()+"，发送消息："+new String(bytes, 0, bytes.length));//bytes转为String
                        readBuffer.clear();
                        //将channel通道注册到selector选择上关注写事件(即 该通道已经被读了，可以被写了)
                        channel.register(selector, SelectionKey.OP_WRITE);
                    }

                    //即写事件就绪
                    if(key.isWritable()){
                        //通过key获取通道channel
                        SocketChannel channel = (SocketChannel) key.channel();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        String info = reader.readLine();
                        //将info写入channel
                        writeBuffer.put((info+"\n").getBytes());
                      //记得写一次 模式要切换一下
                        writeBuffer.flip();
                        //buffer写到channel
                        channel.write(writeBuffer);
                        writeBuffer.clear();
                        //将key对应的channel通道 注册 到selector选择上 关注 读事件
                        channel.register(selector, SelectionKey.OP_READ);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(serverSocketChannel != null){
                try {
                    serverSocketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
