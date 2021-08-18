package network.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 1、实例化 ServerSocketChannel
 * 2、绑定端口 通过ServerSocketChannel调用bind()方法
 * 3、设置ServerSocketChannel为非阻塞 configir....
 * 4、实例化Selector选择器
 * 5、将ServerSocketChannel注册到选择器上  ServerSocketChannel.register()
 * 6、监听是否有新的事件 接收(连接)事件/读写事件/     Selector.select()
 * 7、获取已完成事件的集合，对于这个集合进行遍历，如果发现是Accept事件
 * 8、则进行accept调用，获取SocketChannel，注册到Selector上，关注read事件
 * 9、监听是否有read读事件
 * 10、通过SocketChananel通道来读取数据，其中通过buffer作为传输介质
 * 11、关闭资源 SocketChannel Selector ServerSocketChannel
 *
 *
 * 多线程模式
 *      主线程负责接收请求  主线程接收客户端的可接受事件
 *      子线程负责处理请求   子线程注册可读事件，子线程进行处理
 */
public class MyNIOServer {
    public static void main(String[] args) {
        ServerSocketChannel serverSocketChannel = null;
        Selector selector = null;
        try {
            //创建ServerSocket实例
            serverSocketChannel = ServerSocketChannel.open();
            //绑定端口
            serverSocketChannel.bind(new InetSocketAddress(9999));
            System.out.println("服务器端启动了...");
            //设置当前serverSocketChannel为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //实例化Selector
            selector = Selector.open();//和客户端的 是不同的实例  而且一个线程只能有唯一的selector实例
            //将serverSocketChannel  注册到 选择器上
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);// 即服务端  关注 （很多个）可接收事件

            //select是一个阻塞方法，监听：没有事件则阻塞等待，有事件才会返回
            while(selector.select() > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                //遍历已就绪事件的集合
                while(iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    //遍历一个  处理一个(所以把他删了？？？)
                    iterator.remove();//即把上面的iterator.next() 删除掉  注意（不能调用集合的删除方法  多线程  抛异常）  而使用iterator会对monitor做更新
                    //1.先 判断（对服务器来讲）是否是  可接受事件
                    if(selectionKey.isAcceptable()){
                        //表示现在有新的客户端连接请求
                        System.out.println("可接受 请求...");

                        //这个返回 当前事件 所创建的（服务器的）channel
                        ServerSocketChannel channel = (ServerSocketChannel)selectionKey.channel();
                        //接收 这个事件， 获取SocketChannel
                        SocketChannel socketChannel = channel.accept();//（因为客户端不知道啥时会发消息，所以accept是会阻塞下来的）

                        //设置socketChannel为非阻塞的
                        socketChannel.configureBlocking(false);
                        //将  （客户端的）socketChannel 注册到（当前类的）选择器中
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }

                    //2.再 判断是否是读事件
                    //（能到这，链接已经 建立 了）(因为发起读请求，客户端肯定要写，往channel写，而这个 channel肯定是连接上了)
                    if(selectionKey.isReadable()){
                        System.out.println("读请求...");
                        //获取可读事件的channel
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        //进行读取操作
                        ByteBuffer buffer = ByteBuffer.allocate(100);//能放100个字节的缓冲区
                        //通过channel将数据读取到buffer中
                        socketChannel.read(buffer);

                        //进行读写模式切换(因为客户端只发一次数据，不用再等着读入数据了)
                        buffer.flip();
                        //将数据从buffer中读取
                        byte[] bytes = new byte[buffer.remaining()];
                        //获取buffer缓冲区中的数据到byte数组中
                        buffer.get(bytes);
                        System.out.println("客户端："+socketChannel.getRemoteAddress()+" 发送的消息为： "+new String(bytes, 0, bytes.length));

                        buffer.clear();//用完或用前给clear一下
                        if(socketChannel.read(buffer) == -1){
                            socketChannel.close();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(serverSocketChannel != null){
                try {
                    serverSocketChannel.close();
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
