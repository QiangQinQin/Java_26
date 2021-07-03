package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//客户端
public class Teacher_3_6_MyTcpClient {
	 public static void main(String[] args){
	    // 在这里定义，才能在finally里关闭
		Socket socket=null;
		BufferedReader input = null;
	    PrintWriter output = null;
	    BufferedReader reader = null;
		try {
			//构造当前客户端的套接字
			  socket=new Socket("127.0.0.1",3333);//请求的是本地的服务器  以及  3333这个端口的应用程序
			  System.out.println("客户端已经启动..."+"\n"+" 可以发出请求了");//向服务器发出请求
		
			while(true){//一直发
                //发送控制台数据 到 服务器端
                  //获取控制台的输入流
                  input = new BufferedReader(new InputStreamReader(System.in));
                  String info = input.readLine();//加了\n   //会阻塞
                  //获取连接对象的输出流
                  output = new PrintWriter(socket.getOutputStream());
//                  System.out.println("debug-info: "+info);//testSynchronousQueue!!!!!!
                  //通过输出流对象写入info数据
                  output.println(info); //tcp是存在缓冲区，有可能缓冲区没有刷新就导致数据无法传输
                  output.flush();

                //输出服务器端返回的数据
                //获取连接对象的输入流
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String strs = reader.readLine(); //会阻塞 着等      
                System.out.println("客户端显示--服务器端响应的数据为："+strs);//没法得到服务器端的地址
                
                //跳出while循环
                if("exit".equals(strs)|| "".equals(strs)) { //没传东西时strs为null,写成strs.equals("exit")可能会引发空指针异常 
					break;
				}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
            try {
	                socket.close();
	                reader.close();
//	                input.close();
	                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
}
}
