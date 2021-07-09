package network.udp;

import java.io.IOException;
import java.net.*;
//服务端发送
public class UDPServer {
    public static void main(String[] args) {
        //只需将数据进行打包
        String info = "hello world";
        try {
            //构造发送数据的对象
            DatagramSocket socket = new DatagramSocket();
            //将数据打包-> 发送   （如果客户端没启动，没绑定6666，那就收不到服务端发的消息）
            DatagramPacket dp = new DatagramPacket(info.getBytes(), info.length(), InetAddress.getByName("localhost"), 6666);//InetAddress本地主机
            socket.send(dp);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
