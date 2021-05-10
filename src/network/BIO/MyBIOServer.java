package network.BIO;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * jdk1.4之前，IO模型都采用的BIO模型
 *   需要先在服务器端启动一个ServerSockt，
 *   然后在客户端启动Socket与服务器端进行通信，服务器端调用accept方法来接收客户端的连接请求，
 *   一旦接收上连接请求，然后就可以建立套接字，在这个套接字上进行读写操作，
 *   此时不能再接收其他客户端的连接请求，只能等待当前连接的客户端的执行操作完成
 *
 * JDK1.4开始，出现 同步阻塞式IO
 *   要处理多个客户端请求，使用多线程
 *
 * 实现：1个client->1个Server 一次读写操作
 *
 * BIO编程流程：
 * 服务器端
 * 1）创建ServerSocket实例
 * 2）绑定端口
 * 3）通过accept方法监听客户端的连接，有客户端连接会返回socket实例
 * 4）进行读写操作
 * 5）关闭资源
 *
 * 客户端
 * 1）创建socket实例
 * 2）通过connect去指定服务器端的IP和端口
 * 3）进行读写操作
 * 4）关闭资源
 *
 *
 * 服务器端如何设计为可处理很多客户端的连接？
 *    主线程负责接收客户端连接，子线程负责和客户端交互好
 */

//子线程处理事务
class HandlerThread extends Thread{
    private Socket socket;

    public HandlerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //读取客户端的请求信息
            OutputStream output = null;
            BufferedReader reader = null;
            while(true){
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg = reader.readLine();//必须先读客户端发的
                System.out.println("客户端："+socket.getRemoteSocketAddress()+"，发送的信息为："+msg);

                //客户端想退出,自己也退出
                if("".equals(msg) || "exit".equals(msg))
                    break;
                else{//给客户端回复
                    output = socket.getOutputStream();
                    //控制台读取数据
                    reader = new BufferedReader(new InputStreamReader(System.in));
                    String info = reader.readLine();
                    output.write((info+"\n").getBytes());
                    output.flush();
                }
            }

            //关闭该线程对应的资源
            reader.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("客户端："+socket.getRemoteSocketAddress()+"关闭了");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
public class MyBIOServer {
    public static void main(String[] args) {
        ServerSocket ssocket = null;
        try {
            //创建ServerSocket实例
            ssocket = new ServerSocket();
            //绑定端口到 本机(127.0.0.1)
            ssocket.bind(new InetSocketAddress(9999));
            System.out.println("服务器端启动了...");

            while(true){
                //通过accept方法监听客户端的连接，有客户端连接会返回 socket实例
                Socket socket = ssocket.accept(); //这个方法是一个阻塞方法
                System.out.println("有新的客户端连接："+socket.getRemoteSocketAddress());
                //将socket实例交给子线程去处理
                new HandlerThread(socket).start();//传该客户端对应的socket
            }

            ////可以把事务操作 封装 然后交给子线程处理
//            while(true){
//                //进行读写操作
//                //读操作
//                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                //读取客户端的信息
//                String msg = reader.readLine();
//                System.out.println("客户端发送的信息为："+msg);
//                //写操作 回复客户端消息
//                OutputStream output = socket.getOutputStream();
//                //控制台输入
//                reader = new BufferedReader(new InputStreamReader(System.in));
//                output.write((reader.readLine()+"\n").getBytes()); // \n表示结束
//            }

//            //流资源关闭
//            reader.close();
//            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(ssocket != null){
                try {
                    ssocket.close();//关闭server的Socket
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
