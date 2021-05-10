package network.AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * 服务器端：
 * 1、创建AsynchronousServerSocketChannel(open)
 * 2、绑定端口号
 * 3、监听连接请求（异步非阻塞） accept(A attachment, CompletionHandler<V, A> handler)
 * 4、连接建立成功，调用handler自动执行complted方法，可以进行读操作 read()，否则执行fail方法
 * 5、读操作也是异步的（需要调 回调函数）实现completed（即通知服务端可以进行写操作，write()）
 * 6、如果想实现多次一读一写，就要实现completed.，进行读操作.。.
 */
public class AIOServer {
    private static final int port = 12345;
    private AsynchronousServerSocketChannel serverChannel;

    public AIOServer(int port) {
        init(port);
    }

    public AsynchronousServerSocketChannel getServerChannel() {
        return serverChannel;
    }

    public void setServerChannel(AsynchronousServerSocketChannel serverChannel) {
        this.serverChannel = serverChannel;
    }

    public void init(int port) {
        try {
            //创建一个服务器异步通道
            serverChannel = AsynchronousServerSocketChannel.open();
            //绑定监听端口
            serverChannel.bind(new InetSocketAddress(port));
            System.out.println("服务器端已启动...");
            //开始监听，连接建立成功，调用handler
            //this里有要监听的通道（serverChannel）   ServerHandler()是自定义的回调函数
            serverChannel.accept(this, new ServerHandler());

            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);//为了不让主线程退出，观察与客户端的读写交互
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AIOServer(9999);//主线程调用完accept不会阻塞，不会监听读写的结果进行操作，因为是异步的
//       如果想让他必须等待
        //Teacher_2_25_CompareThreaSafe
// CountDownLatch countDownLatch = new CountDownLatch(1);计数器
//            countDownLatch.countDown();  计数器减1
// 主线程  countDownLatch.await();
    }


}
