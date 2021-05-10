package network.NIO2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程NIO实现
 *     主 线程接收到客户端连接（即单线程管理多个网络连接）
 *     连接所对应的socketChannel交给 子 线程处理读写请求
 */
class NIOServerHandler implements Runnable{

    private SocketChannel socketChannel;
    //创建Selector实例（因为线程处理就绪事件会改变selector里的东西）
    private Selector selector;

    public NIOServerHandler(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
        //一个子线程  只能拥有一个selector,关注多个通道的读写请求
        if(selector == null){
            try {
                selector = Selector.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        // 关注 socketChannel通道的 读写就绪事件，进而  处理
        //读是被动关注  写是主动发起的
        try {
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);//注册socketChannel到selector为读事件？？？？???？

            //有 就绪事件(读就绪  写就绪等)
            while(selector.select() > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if(key.isReadable()){
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);//分配空间  相当于new
                        channel.read(buffer);
                        buffer.flip();

                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);
                        String msg = new String(bytes, 0, bytes.length);
                        System.out.println(Thread.currentThread().getName()+", 客户端："+channel.getRemoteAddress()+", 消息: "+msg);

                        //主动回复固定内容
                        buffer.clear();
                        buffer.put(("hello client\n").getBytes());
                        buffer.flip();
                        channel.write(buffer);
                        if("".equals(msg) || "exit".equals(msg)){
                            System.out.println(Thread.currentThread().getName()+"客户端："+channel.getRemoteAddress()+"下了 ");
                            key.cancel();
                            channel.close();
                        }

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class MyMultiThreadNIOServer {
    public static void main(String[] args) {
        ServerSocketChannel serverSocketChannel = null;

        try {
            //创建ServerSocketChannel
            serverSocketChannel = ServerSocketChannel.open();
            //绑定端口（默认是本地主机）
            serverSocketChannel.bind(new InetSocketAddress(9999));
            //将SerSocketChannel置为非阻塞
            serverSocketChannel.configureBlocking(false);
            //创建Selector选择器（也叫选择器）
            Selector selector = Selector.open();
            //将serverSocketChannel注册到选择器上，关注可接受事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//一个channel（相当于是单线程），管理多个网络连接

            //使用固定数量的线程池
            //（他直接给你放3个线程到池里，你就不用启动了，子线程不用实现Thread，只用实现runnable接口，把线程的任务通过sumbit提交给pool,然后pool给你分配线程自动执行任务）
            ExecutorService pool = Executors.newFixedThreadPool(3);
            while(selector.select() > 0){//select是阻塞方法，直到有  事件返回了
                //获取就绪事件的集合
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if(key.isAcceptable()){
                        //是 可接受 事件
                        System.out.println(Thread.currentThread().getName()+"关注可接受事件");
                        ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) key.channel();
                        SocketChannel channel = serverSocketChannel1.accept();//接受 返回客户端通道
                        System.out.println("客户端："+channel.getRemoteAddress()+"已连接");
                        //起4个MyNIOClient_Block，发现第4个起会阻塞，连接了却处理不了？？？？？？？？？？？？
                        //将SocketChannel channel 提交 给子线程
                        pool.submit(new NIOServerHandler(channel));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
