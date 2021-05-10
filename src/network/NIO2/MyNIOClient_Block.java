package network.NIO2;
//和NIO2/MyNIOClient的区别在于没设置  非阻塞
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

public class MyNIOClient_Block {
    public static void main(String[] args) {
        SocketChannel socketChannel = null;

        try {
            socketChannel = SocketChannel.open();
            Selector selector = Selector.open();

            //socketChannel.configureBlocking(false);  没有设置非阻塞
            //如果connect失败会一直阻塞在这里，直至成功
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
            System.out.println("客户端已启动...");
            Scanner scanner = new Scanner(System.in);
            while(true){
                //连接成功，给服务器端发送消息
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                System.out.println("客户端要发送消息到服务器端：");
                String info = scanner.nextLine();
                if("".equals(info) || "exit".equals(info)){
                    break;
                }
                buffer.put(info.getBytes(StandardCharsets.UTF_8));
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();

                //阻塞的获取服务器端的响应消息（没好就一直等）
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
