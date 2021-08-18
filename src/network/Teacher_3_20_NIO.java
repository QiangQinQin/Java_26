package network;

import java.io.File;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * wps上书Java NIO(中文版)
 * NIO中重要组件
 * Channel
 *      类似流，即可以从通道中读取数据，也可以写数据到通道中
 *      通道可以异步（即不用等）地读写，
 *      通道中的数据先要读取到一个Buffer中，或者从一个Buffer中写入
 *
 * 读数据：将数据从channel（铁轨）读到buffer（火车）
 * 写数据：从buffer中写入到channel通道中
 *
 * 常用实现类：
 *      DatagramChannel 通过 UDP 的方式读写网络中的数据通道
 *      SocketChannel  通过 TCP  的方式读写网络中的数据，一般用于客户端（用SocketChannel发起链接请求 以及 与服务器通信的通道）
 *      ServerSocketChanneel  通过TCP的方式读写网络中的数据，一般用于服务器端（用ServerSocketChanneel 接受连接  获取与客户端的通道）
 *      FileChannel 用于读写操作 文件 的通道
 *
 * Buffer（把内存上的一块地址包装成Java NIO中的一个buffer对象）
 *      缓冲区，与NIO Channel交互，数据是从通道读取进入缓冲区，从缓冲区写入到通道中
 *
 * 使用Buffer注意点：
 *      1、写数据到buffer：调用buffer.flip（因为写改变了buffer的三个指针，所以要进行读写模式切换）
 *      2、从Buffer中读取数据：buffer.clear（清空buffer里所有）/buffer.compact（清空buffer里读过了的）
 *       当从buffer中读取数据，要么调用clear方法清空buffer中的数据，
 *       要么调用compact方法啊清空已经读过的数据，任何 未读到 数据会被 移动到缓冲区的起始位置，新写入的数据将放到缓冲区未读数据的后面
 *
 * Buffer实现依赖3个指针
 *      position limit capacity（WPS图）
 * position：取决于Buffer处于读模式还是写模式，
 * · 写数据到Buffer中，position表示当前位置，初始的值为0
 * · 读数据时，从某个特定位置开始去读，需要将buffer从写模式切换为 读模式，position会被重置为0
 * limit:
 * · 写模式下，表示 最多 能往里写多少数据
 * · 读模式下，表示最多能读到的数据
 * capacity：
 *   作为buffer内存块，有一个固定的大小
 *
 * 示例：ByteBuffer.allocate(100);
 * 1、position = 0  limit = capacity = 100
 * 2、写buffer.put("hello\n")  position=6    limit = capacity = 100
 * 3、切换到读buffer.flip()     position=0   limit = position  capacity = 100
 * 4. 读3个 buffer.get() 3    position=3   limit = 6   capacity = 100
 *
 * Buffer的方法：
 *    ByteBuffer.allocate() 分配空间（相当于new）
 *    ByteBuffer.allocateDirect() 在堆外分配空间
 *    ByteBuffer.wrap(byte[] bytes) 通过byte数据创建一个缓冲区
 *      flip()
 *      capacity()
 *      limit()
 *      positio()
 *
 * Selector
 * 选择器，也叫做多路复用器，作用是检查一个或多个channel通道是否处于可读、可写、可连接（即用来管理多个网络请求）
 * 优势：
 *     单线程管理多个网络连接，相比于之前的多线程使用了更少的线程，效率反而更高，同时减少了线程上下文切换
 *
 * Selector的使用
 *  1、Selector.open();//创建一个实例
 *  2、channel.register(xxx, SelectionKey的四种事件)//注册channel到选择器
 *  3、selector.select()  监听注册过来的事件，是一个阻塞方法，如果有事件就绪则返回
 *
 * Selector维护 三种类型的selectionKey集合
 *      selector.selectedKeys() 已选择键的集合
 *      selector.keys() 已注册键的集合
 *      selector.cancelKey() 已取消键的集合（为什么要取消键，因为客户端要取消链接，需要取消键 以及 关闭其通道）
 *
 * SelectionKey的四种事件
 *     一个通道可以注册多个事件 ：比如SelectionKey.OP_READ | SelectionKey.OP_WRITE
 *
 * 思考：NIO和BIO的区别？？？？？？？？？？？？？
 * （开发班需要下去看书，深究）
 */
public class Teacher_3_20_NIO {
    public static void main(String[] args) {
    }
}
