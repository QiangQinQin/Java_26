package network.multiThreadTCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class SocketHandler extends Thread{
    private Socket socket;
    public SocketHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        OutputStream outputStream = null;
        //进行通信
        //try catch别放while（true）里面，因为finally会执行，关闭资源，就无法进行下次循环了
        try {
            while(true){//可能不是一次就完成，故用while
                //进行读操作
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg = reader.readLine();
                System.out.println("客户端："+socket.getRemoteSocketAddress()+"发送的信息："+msg);
                //进行写操作
                //Teacher_3_6_MyTcpServer 用 了   PrintWriter方法 写操作
                outputStream = socket.getOutputStream();
                outputStream.write(("reponse: "+msg+"\n").getBytes());//加换行   以及 转为字节流

                if("".equals(msg) || "exit".equals(msg)){//结束此线程，但是服务器（没有关闭）可以接受别的客户端的请求
//                    System.out.println("lvting-Debug");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




public class MyTcpServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(3333,2);
            while(true){
                //进行监听，等待多个客户端连接
                Socket accept = serverSocket.accept();
                System.out.println("与客户端："+accept.getRemoteSocketAddress()+ "连接建立成功...");
                //将accpet实例交给  子线程  处理客户端请求
                new SocketHandler(accept).start();//SocketHandler是自己定义的
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
