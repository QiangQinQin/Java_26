package network.BIO;
/*
和TCP类似，一定要会，先运行服务器
点下面左边的三角可以快速运行
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MyBIOClient {
    public static void main(String[] args) {
        Socket socket = null;
        OutputStream output = null;
        BufferedReader reader = null;
        try {
            //创建Socket实例
            socket = new Socket();
            //连接服务器端，通过connect绑定IP地址和端口号
            socket.connect(new InetSocketAddress("127.0.0.1", 9999));
            System.out.println("客户端启动了...");

            String reply = null;
            String reply2 = null;
            //进行读写操作
            while (true) {//多次读写
                //写操作
                output = socket.getOutputStream();
                reader = new BufferedReader(new InputStreamReader(System.in));
                reply = reader.readLine();

                reply2 = reply + "\n";
                output.write(reply2.getBytes());
                output.flush();
                //发完退出请求自己也关闭
                if ("".equals(reply) || "exit".equals(reply))
                    break;


                //读操作
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg = reader.readLine();
                System.out.println("服务器响应信息为：" + msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("客户端关闭了...");
                //流资源关闭
                output.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (socket != null) {
                try {
                    System.out.println("socket关闭了...");
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
