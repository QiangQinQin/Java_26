package network;

/**
 * 网络编程
 *   体系结构:  OSI ->  TCP/IP -> 五层协议
 *   交互信息:
 * 主机1向 主机2发数据     即   应用程序1向 应用程序2 发数据
 * 
 * 主机1的第5层（应用层）     -》  加上必要的控制信息H5 -> 变成下一层所需要数据单元 -》
 * 到达主机1第4层(运输层) -》 加上（运输层所需的）必要的控制信息H4 -》
 * 到达主机1第3层(网络层) -》控制信息分为两部分 H2 T2 （H是头部，T是为尾部）-》
 * 到达主机1第2层(数据链路层) -》比特流 -》
 * 到达主机1第1层(物理层) -》主机2
 * 
 * 到主机2后是一个从下往上的过程
 *
 * 数据传输控制协议(TCP协议)
 *   TCP协议是面向  连接  的  传输层  协议（建立  点对点  一对一的连接）
 *   TCP协议采用  字节流  机制传输数据
 * TCP特点：
 *   1）提供 可靠 交付服务，通过TCP连接传输的数据，无差错、不丢失、不重复
 *   2）提供全双工通信（三次握手操作）（双方都要给对方发送建立连接的请求）
 *   3）面向字节流，  点对点  通信
 *
 * 网络通信两要素：主机 需  绑定  ip
 *                 绑定  端口  port
 *
 * TCP编程
 * Java Socket
 *    socket称之为"套接字"，用于描述IP地址和端口号，是一个通信的句柄。
 *    应用程序通过 套接字向网络中 发送请求或者应答请求 (客户端 《-》 服务器)
 * Socket  用于客户端
 * ServerSocket 用于服务器端
 * 都在 java.net包里
 *
 * 客户端的  Socket常用API：
 * · Accept 用于产生"阻塞"，直到接收一个连接，并且返回一个  客户端  的Socket对象实例
 *                             在服务器端使用，如果该方法有返回值（即socket实例），说明此时有一个客户端请求当前服务器端，接下来的操作都需要使用这样的一个返回值
 * · getInputStream 获取网络连接的输入
 * · getOutputStream 获取网络连接的输出
 *
 * 服务器端
 * 构造ServerSocket方法：
 *   ServerSocket()
 *   ServerSocket(int port)//没有绑定主机，因为默认是当前主机
 *   ServerSocket(int port, int backlog)//请求队列长度（由操作系统管理   服务一个，还可以等backlog个）
 *   ServerSocket(int port, int backlog, InetAddress bindAddr)
 *
 *   backlog参数用来设置连接  请求队列 的长度
 */
public class Teacher_3_6_TCP {
    public static void main(String[] args) {

    }
}
