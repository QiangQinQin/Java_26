package network.AIO;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

//连接建立成功的处理函数  实现 CompletionHandler接口
                          //CompletionHandler<V, A>回调函数  V是IO操作后返回的结果类型（客户端的socket）   A要进行IO操作的对象的类型
public class ServerHandler implements CompletionHandler<AsynchronousSocketChannel, AIOServer> {//AIOserver中就有AsynchronousServerSocketChannel

    //连接建立成功，可以读客户端了
    @Override
    public void completed(AsynchronousSocketChannel result, AIOServer attachment) {
        //继续监听通道上连接，可以处理下一个  客户端的链接请求，类似递归
        // （你写的服务器端代码 也就是用户程序，不需要等读写操作完成，因为操作系统帮你做，所以用户程序可以做别的事情（即接受连接））
        attachment.getServerChannel().accept(attachment, new ServerHandler());//attachment即serverChannel，他可以accept其他连接请求

        doRead(result);
    }

    //accept失败
    @Override
    public void failed(Throwable exc, AIOServer attachment) {
        exc.printStackTrace();
    }//打印异常调用栈

    //进行异步读操作
    public void doRead(AsynchronousSocketChannel channel) {
        // channel不能直接和数据源打交道
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        /**
         * //异步读操作
         *   public final <A> void read(ByteBuffer dst,进行读操作时使用的 缓存数据
         *                                A attachment, 进行IO操作需要绑定的对象
         *                                CompletionHandler<Integer,? super A> handler)读就绪之后调用的handler
         *
         *         new CompletionHandler<Integer, ByteBuffer>()//CompletionHandler 是一个接口
         *                Integer是执行read后返回的结果（字节数）    ByteBuffer是读时使用的对象类型
         */

        channel.read(  buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {// channel.read是异步,所以需要回调函数（此处写成匿名函数类形式，是为了获取到channel）处理

            //读成功的回调函数进行的操作
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                buffer.flip();//作用像是 重置，从头开始操作？？？？？？？？？？
                System.out.println("来自客户端的数据为：" + new String(attachment.array()));
                doWrite(channel);//用的是44行channel.read的SocketChannel
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        }    );

    }

    public void doWrite(AsynchronousSocketChannel channel) {
        //进行写操作（是主动的，不需要等，不需要new个回调函数去处理其他事情）
        //读和写 不是一个buffer，所以不用清空
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入响应数据：");
        String info = scanner.nextLine();
        buffer.put(info.getBytes(StandardCharsets.UTF_8));
        //buffer每次读完  写完都得flip一下
        buffer.flip();
        channel.write(buffer);

//        想多次一读一写，读里调用write；write里调用read
        doRead(channel);
    }

}
