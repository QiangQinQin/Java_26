package network.NIO;
//https://blog.csdn.net/canot/article/details/51372651
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
//没明白的地方看NIO2   （）里是补充的  猜测的 不重要的


/**
 * 1、实例化 SocketChannel
 * 2、设置 SocketChannel 为非阻塞
 * 3、实例化 Selector
 * 4、连接服务器connect，在这个方法中提供ip地址和端口号，注意：这个方法不是一个阻塞方法，如果连接
 *   失败返回false，连接成功返回true
 * 5、如果是false，则将SocketChannel注册到Selector选择器中，监听connect可连接事件
 * 6、监听selector中是否有可完成事件，遍历可完成事件的集合，判断该事件是否是可连接事件
 * 7、connect方法就会返回true
 * 8、给服务器端发送消息，channel.write
 * 9、关闭资源，selector SocketChannel
 */
public class MyNIOClient {
    public static void main(String[] args) {
        SocketChannel socketChannel = null;
        try {
            //创建SocketChannel通道
            socketChannel = SocketChannel.open();//open方法创建实例
            //设置SocketChananel为非阻塞
            socketChannel.configureBlocking(false);
            //通过open实例化Selector
            Selector selector = Selector.open();
            System.out.println("客户端启动了...");

            //连接有可能没建立成功（网络问题），就要去选择器上监听
            if(!socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999))){
                //将这个 通道 注册到  选择器 上
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
                //监听Selector 选择器
                //由于select操作只管对selectedKeys的集合进行添加而不负责移除，所以当某个消息被处理后我们需要从该集合里去掉
                selector.select();//没有 就绪 事件，就会阻塞到这一步
                //遍历已完成事件的集合
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while(iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();//因为处理一个  删除一个

                    //判断是否存在 可连接 事件
                    if (selectionKey.isConnectable()) {
                        //获取selectionKey（存的是当前迭代元素）的channel
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        //完成建立连接
                        channel.finishConnect();
                    }
                }
            }

            System.out.println("连接建立成功了...");
            //连接成功，给服务器端发送信息（只一次）
            ByteBuffer buffer  = ByteBuffer.allocate(100);
            //将所要的数据写入到buffer中
            //buffer.put(("hello NIOServer\n").getBytes());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String info = reader.readLine();
            buffer.put((info+"\n").getBytes());
            //mark   position    limit    capacity
            //进行读写模式切换
            buffer.flip();
            //使用通道发送buffer
            socketChannel.write(buffer);

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
