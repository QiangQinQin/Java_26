package network.TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


//服务器端要先启动，绑定端口，处于listen状态
//一对多
//客户端可以启动，但是无法建立连接
/*
* 服务器端应对多个客户端访问？
*    1）backLog  一个线程处理多个请求（操作系统 来管理，好像要用TCP工具来连接这个服务端   https://blog.csdn.net/SilenceGG/article/details/84658146）
*    2）多线程
* package network.multiThreadTCP;

* */
public class Teacher_3_6_MyTcpServer {
	 public static void main(String[] args) {
		// TODO Auto-generated method stub
		    ServerSocket serverSocket=null;
		    BufferedReader reader=null;
		    PrintWriter output=null;
		    BufferedReader input=null;
		
		  
			try {
					//构造serversocket对象，监听3333
					serverSocket=new ServerSocket(3333);//写了就代表3333已启动，可以访问
					System.out.println("服务器端已启动...\n等待连接客户端");
					//阻塞， 直到等到 客户端的请求
					Socket accept = serverSocket.accept();
					System.out.println("连接建立成功...\n 等待接收数据");
				
				  while(true) {//一直处理数据
				    	
//						//从客户端的socket实例获取网络中数据（字节流 0101 ）
//						InputStream inputStream = accept.getInputStream();
//						//转换为字符流
//						InputStreamReader inputstreamReader = new InputStreamReader(inputStream);
//						//缓冲流
//						BufferedReader reader = new BufferedReader(inputstreamReader);
						
						//一步获取连接对象的输入流
						 reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
						//获取客户端的输入信息
						String strs=reader.readLine();//网络中传输的数据都会有一个\n的结束符
						System.out.println("服务端显示--客户端ip为"+accept.getRemoteSocketAddress()+"的请求数据："+strs);//获取客户端所对应的IP地址
						if("exit".equals(strs)|| "".equals(strs)) {
							break;
						}
							
						
						//回复
						System.out.println("服务器可以对客户端请求做出响应了");
						//获取连接对象的输出流
						output=new PrintWriter(accept.getOutputStream());
						//获取控制台的输入流
						 input=new BufferedReader(new InputStreamReader(System.in));
						String info=null;//清空，避免两次回复中文，中间出现g'f'h'g'f'h'h'g'tg'f'h'g'f'h'h'gg'f'h'g'f'h'hg'f'h'g'f'hg'f'h'g'fg'f'h'gg'f'hg'fg
						info=input.readLine();//会阻塞
//						System.out.println("服务器的返回数据"+info);
						//输出流对象 写入 输出信息(传给客户端了)
						output.println(info);//会加上\n
			
						output.flush();
						
				    }
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					serverSocket.close();
					reader.close();
					if(output!=null) {
						output.close();
					}
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
}
