package network.AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * 客户端：
 * 1、创建AsynchronousSocketChannel
 * 2、绑定IP和port（即访问哪个主机的哪个端口号），发一个连接请求
 * 3、写操作
 * 4、读操作
 */
public class AIOClient {
    private AsynchronousSocketChannel channel;

    public AIOClient(String host, int port){
        init(host, port);
    }

    public void init(String host, int port){
        try {
            //开启通道
            channel = AsynchronousSocketChannel.open();

            //发起连接请求（（即访问哪个主机的哪个端口号））
            channel.connect(new InetSocketAddress(host, port));
            System.out.println("客户端已启动...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String line){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(line.getBytes(StandardCharsets.UTF_8));//把控制台输入的line 放到buffer里
        //读到buffer里后position变了，要写到channel需要重置位置
        buffer.flip();
        channel.write(buffer);
    }

    public void read(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            channel.read(buffer).get();
            //上面get完，下面想输出，所以要切换
            buffer.flip();
            System.out.println("来自服务器端数据："+new String(buffer.array()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void shutDown(){
        if(channel != null){
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {

        AIOClient client = new AIOClient("127.0.0.1", 9999);
        try {
            //多次一写一读
            while (true) {
                System.out.println("请输入请求数据：");
                Scanner scanner = new Scanner(System.in);
                String info = scanner.nextLine();
                client.write(info);
                client.read();

                if ("".equals(info) || "exit".equals(info)) {
                    break;
                }
            }
        }finally {
            client.shutDown();
        }
    }
}
