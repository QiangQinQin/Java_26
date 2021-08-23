package network;

import java.nio.channels.CompletionHandler;

/**
 * AIO模型（不需要掌握）
 * 异步非阻塞
 * 在java.nio.channels包下
 *      AsychronousServerSocketChannel 服务器数据通道（里面的读写是非阻塞的，基于TCP）
 *      AsychronousSockChannel     客户端通道（里面的读写是非阻塞的，基于TCP）
 *
 *      AsychronousDatagramChannel    基于UDP的通道
 *      AsychronousFileChannel       操作文件
 *
 * 异步非阻塞，
 *         服务器端实现一个一个线程（一个线程可以对应多个请求）， 异步执行（即accept不会阻塞，由操作系统处理） 当前客户端请求，
 *         客户端的请求都是由 操作系统 完成再去通知(回调函数)服务器 进行处理，
 *         所以这个AIO主要用于连接数多同时请求比较 复杂（即链接时间比较长） 的系统架构
 * 服务器端：
 *      一个线程异步处理请求，处理之后执行回调函数
 *
 * 客户端：
 *      一个线程异步进行读写，不需要执行回调函数
 *
 * AsychronousServerSocketChannel里
 *      accept() 非 阻塞的方法。要使用它，需要先创建AsychronousServerSocketChannel，然后调用accept接收来自客户端的连接，
 *      由于异步IO实际的IO操作是由操作系统来做，而（对于服务器端的）用户线程只负责去通知操作系统进行IO操作 和 接收操作系统IO完成之后的通知。
 *
 * accept有两种
 *      Future<AsychronousSocketChannel> accept() //Future可以接受线程执行的结果集（但一般不用）
 *      <A> void accept(A attachment, CompletionHandler<V, A> handler）//A:泛型，要进行IO操作的对象的类型   V是IO操作后返回的结果类型
 *        attachment：（IO操作的对象）通道   CompletionHandler<V, A>回调函数
 *
 *      CompletionHandler接口实现了一下两个方法
 *          void completed(V result, A attachment);accept成功时要做的操作
 *          void failed(Throwable exc, A attachment);accept失败时要做的操作
 *
 *      read()：调用read（）的线程不会阻塞
 *      write()
 *
 *
 * BIO、NIO、AIO三个网络模型的适用场景：
 *      BIO（JDK1.4之前只有他）适用于连接数目小的系统架构，同时也需要服务器资源充足，虽然BIO效率不高，但是BIO程序简单易理解
 *      NIO（（出现在JDK1.4之后）适用于连接数目比较多以及连接时间 短 （即轻操作）的系统架构，例如 聊天服务器 ，编程比较复杂
 *
 *      AIO（JDK1.7之后）适用于连接数目比较多以及连接时间 长 的系统架构，充分调用 操作系统 参与并发，
 * 不管是业务逻辑还是并发逻辑都需要操作系统的支持，所以不同操作系统性能不同
 */

public class Teacher_3_23_AIO {
    public static void main(String[] args) {
    }
}
