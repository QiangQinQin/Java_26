package thread;

/**
 * 复习：
 * 1、线程 进程的概念
 *    进程是资源分配的基本单位
 *    线  程是cpu调度的最小单位
 *    JVM是一个进程，所以我们研究线程
 * 2、创建线程
 *   继承Thread
 *   实现Runnable接口
 *   实现Callable接口
 *   （以上三种都可以使用匿名内部类实现）
 * 3、start方法启动
 * 4、守护线程：最常见的是垃圾回收线程
 *    自动结束生命周期的特点
 * 5、线程的生命周期（6）
 * New  Runnable  （ Blocked  Waiting  Timed-Waiting） Terminated
 * 6、线程中常用的方法
 * start
 * sleep 不释放资源 让出cpu
 * yield 提示愿让一次cpu
 * join
 * 
 * wait
 * notify
 * notifyAll
 * 
 * interrupt
 * isInterrupted
 * interrupted
 * 7、并发编程
 *   并发  指的是多个线程操作同一个资源，不是同时操作，而是 交替 操作
 * 同步
 * 1)volatile  volatile修饰一个 变量 ，保证变量的可见性，保证前后有序（书写顺序和执行顺序一样）性（不能保证同步）
 *   使用：单例模式、boolean变量
 *     举例：游戏有一个       事件线程 ，读取/修改boolean类型变量状态（需要立即可见）
 *            还有一个     游戏线程（要根据 事件线程判断boolean状态决定游戏是否继续）
 * 
 * 2)sychornized
 *   synchronized void func(){} 同步方法
 *   void func(){
 *       synchronized(){ 同步代码块
 *          //抛出异常 退出
 *       }
 *   }
 * synchronized 同步 方法 依赖方法修饰符上的ACC_SYNCHRONIZED标志位（表明是同步方法   使用这个方法需要先获取锁）
 * synchronized 同步 代码块 ：用到monitorenter 、 monitorexit两个字节码指令，两个是为了发生异常能执行后面那个monitorexit来释放锁
 * 
 * synchronized使用场景
 */
public class Teacher_2_24_Review {
    public static void main(String[] args) {

    }
}
