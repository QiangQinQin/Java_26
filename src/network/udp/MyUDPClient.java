package network.udp;

import java.io.IOException;
import java.net.*;
//客户端 发送
public class MyUDPClient {
    public static void main(String[] args) {
        //只需将数据进行打包
        String info = "hello world";
        try {
            //构造发送数据的对象
            DatagramSocket socket = new DatagramSocket();
            //将数据打包-> 发送
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
