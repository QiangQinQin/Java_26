package network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
//服务器端 接受
public class MyUDPServer {
    public static void main(String[] args) {
        try {
            //构造发送数据的对象
            DatagramSocket socket = new DatagramSocket(6666);
            //接收数据
            byte[] buf = new byte[1024];
            //将数据接收
            DatagramPacket dp = new DatagramPacket(buf, 1024);
            socket.receive(dp);
            String info = new String(dp.getData(), 0, dp.getLength());
            System.out.println("接收到数据为： "+info);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
