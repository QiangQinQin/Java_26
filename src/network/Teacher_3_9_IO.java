package network;

/**
 * 1、IO读写原理
 *   文件的读写还是socket读写，都是在Java应用层开发，都是input或者output处理
 *   用户程序进行的IO读写，会用到read&write两大系统调用
 *      read系统调用指的是将数据从 内核缓冲区 复制到 进程缓冲区（磁盘）
 *      write系统调用指的是把数据从进程缓冲区复制到内核缓冲区
 * 2、内核缓冲区和进程缓存区
 *      要了解内核缓冲区和进程缓存区，先了解一下用户进程和操作系统
 *          用户进程(N) ->    处于用户态(用户空间)
 *          系统空间  ->  内核态
 *      
 * 在用户态需要访问  系统资源 ，借助于 内核态，系统资源主要有：
 * 1）cpu：控制一个程序的执行
 * 2）输入输出：一切都是  流，所有流都是需要借助内核态
 * 3）进程管理：进程创建、销毁、阻塞、唤醒之间的调度
 * 4）内存：内存的申请、释放
 * 5）进程间通信：进程之间不能够相互访问（内存），所以进程之间的交互需要通信，通信也是一种  资源
 * 以上所提到的系统资源，在用户进程中是无法被直接访问的，只有通过  操作系统  来访问，所以把操作系统访问这些资源的这一功能称之为   系统调用
 *
 * （内核和进程）缓冲区的目的，是为了减少频繁的系统IO调用（从而提高速度）
 * 
 * 系统调用需要  从用户态切换到内核态 ，切换之后保存用户进程的数据和状态等信息，
 * 结束调用之后需要恢复 之前的信息，为了减少这种损耗的时间，还有损耗性能的时间，
 * 所以出现了缓冲区
 *
 * 有了缓冲区，操作系统使用read函数从内核缓冲区复制到进程缓冲区，
 *                 write函数从进程缓冲区复制到内核缓冲区，
 * 只有缓冲区中的数据达到一定的量，才会进行 IO的系统调用，提升了系统性能（系统决定什么时候读  内核决定什么时候写  用户不需要去关心）
 * Java读写IO的底层流程（WPS上有图）
 *
 *
 * 四种IO模型
 *   同步阻塞IO(Blocking IO 即 BIO)
 *   同步非阻塞IO(Non-Blocking IO)
 *  （最重要）IO多路复用(IO Multiplexing)
 *   异步IO(Asynchronous IO)
 *
 *
 * 阻塞/非阻塞
 *   阻塞IO,指的是需要等内核IO操作  彻底  完成之后，才返回到用户空间，执行用户的操作
 *   非阻塞IO，指的是  不需要 等待内核IO操作彻底完成之后，才返回到用户空间
 * 阻塞/非阻塞指的是  用户空间  程序的执行状态
 *
 *
 * 同步/异步
 *   同步IO，用户空间线程和内核空间线程的交互，
 *          用户空间线程是  主动  发起IO请求的一方（并阻塞到操作完成），内核空间线程指的是  被动接收的一方
 *   异步IO, 与上面刚好相反
 *       用户空间线程是  被 动接受  的一方(不用等，可以去做其他事)，内核空间线程指的是  主动发起请求  的一方（IO交给操作系统，完成后给应用程序一个通知）
 *
 *
 *操作系统底层常见的IO模型：
 * 同步阻塞IO（WPS上有图）
 *   TCP编程(socket都是阻塞的)
 *       优点：用户线程阻塞等待数据期间，不会占用cpu资源
 *       缺点：BIO模型在高并发场景下不可用（需要一个线程才能维护一个IO读写流程，高并发时需要多线程的切换）
 * 同步非阻塞IO
         socket本身是阻塞的，非阻塞IO要求socket被设置NONBLOCK
 * IO多路复用
 *     Java中Selector和linux中epoll都是这种模型
 * 异步IO
 *      类似于Java中的回调模式，用户空间线程向内核空间线程  注册  一些回调函数，内核主动调用这些函数
 *       用户空间线程是  被 动接受  的一方，内核空间线程指的是  主动发起请求  的一方 
 */
public class Teacher_3_9_IO {
    public static void main(String[] args) {

    }
}
