package network.multiThreadTCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyTcpClient {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader input = null;
        PrintWriter output = null;
        BufferedReader reader = null;
        try {
            //构造Socket
            socket = new Socket("127.0.0.1", 3333);
            System.out.println("客户端已经启动...");
            while(true){
                //发送数据到服务器端
                //获取控制台的输入流
                input = new BufferedReader(new InputStreamReader(System.in));
                String info = input.readLine();
                //获取连接对象的输出流
                output = new PrintWriter(socket.getOutputStream());
                System.out.println("debug-info: "+info);
                //输出流对象写入info数据
                output.println(info); //tcp存在缓冲区，有可能缓冲区没有刷新就导致数据无法传输
                output.flush();

                //输出服务器端返回的数据
                //获取连接对象的输入流
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String strs = reader.readLine(); //阻塞
                System.out.println("客户端显示--服务器端响应的数据："+strs);
                if("".equals(strs) || "exit".equals(strs)){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                reader.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
