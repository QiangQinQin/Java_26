package thread;

import java.util.concurrent.TimeUnit;

/**
 * 线程生命周期
 *new（新建状态）：
 * 		用new关键字创建一个线程对象，它并不是处于一个执行状态，因为还没有start启动线程
 *Runnable(就绪状态) 
 * 		线程对象调用start方法，才是在JVM中真正创建了一个线程，
 * 		线程一经启动 并不会立即执行，该状态的所有线程位于就绪线程池中，等待操作系统的资源，比如：处理器，获得cpu的使用权
 *Blocked(阻塞状态)
 * 		等待一监视器 锁  进入  同步代码块 或者 同步方法，代码块/方法指的是某一时刻只能够有一个线程执行的，其他线程只能等待
 *Waiting(等待状态) 
 *		Object.wait()/Thread.join()/LockSupport.park()都会使得线程阻塞，从Running转换到Waiting状态
 *     （java里叫waiting  操作系统里（Blocked 、Waiting 、TIMED_WAITTING）都叫阻塞）
 *Timed_Waiting(睡眠状态)
 *		 调用加超时参数的Object.wait(long mills)/Thread.slepp(long mills)/LockSupport.parkNano()/LockSupport.parkUntil()
 *Terminated(终止状态) 
 *		是一个线程的最终状态，线程进入到Terminated状态，意味着该线程的生命周期 结束了，
 *      下面这些情况都会使得线程进入Terminated状态
		 * 1）线 程执行正常结束
		 * 2）线 程运行出错意外结束
		 * 3）JVM crash（崩溃）（比如计算机有问题   程序耗内存）
		 *
 *线程六状态之间的转换（wps上有图）
 *
 * 线程中常用方法
 * 1)start()
 * 		启动一个线程，将线程添加一个线程组中，线程状态会从New状态转换到Runnable状态，然后获取Cpu之后进入Running状态执行run
 * 2)sleep()
 * 		sleep是一个静态方法，其中存在两个重载函数
 *			 public static native void sleep(long millis)
 *			 public static void sleep(long millis, int nanos)//毫秒＋纳秒
 *		 使得线程进入睡眠状态，暂停执行。sleep（也会使用synchronizate同步代码块）   不会放弃monitor lock(锁)的所有权（wait会释放）
 * 		jdk1.5之后，jdk引入一个枚举TimeUnit，其对sleep方法做了封装，直接使用从而时间单位  换算  的步骤，比如线程睡眠3h27min8sec88msec
 * 3)yield()(一般不用)
 * 		yield表示  提醒  cpu调度器我愿意放弃当前的cpu资源，（属于启发式方法），
 * 		如果cpu资源不紧张的情况下，会忽略这种提示（从running 到 runnable）
 * 
 * 面试题：yield和sleep的区别
	 * a.jdk1.5之前，yield调用了sleep
	 * b.sleep使得当前线程暂停执行，不会占用cpu资源
	 *   yield只是对于cpu调度器的一个提示
	 * c.sleep会导致线程短暂的阻塞，yield有可能会使得线程Runnable-》Runnable
	 * d.sleep会捕获到中断异常，yield不会捕获到中断异常
	 *
 * 4)join()
 * 		含义：在线程B中  join某个线程A（即调用A的join方法），会使得B线程进入等待，直到线程A结束生命周期，或者达到给定的时间， 才会恢复。 而在这期间线程B会处于等待状态（waiting或 timed_waiting）
 * 
 * 5)wait()
 * 6)notify()
 * 7)notifyAll()
 * 8)线程中断方法
 */

/*
 * 课堂练习：
 * ThreadA打印1-10, ThreadB打印打印1-10,，ThreadC打印1-10, 保证按照A->B->C顺序打印
 */
class ThreadDemo extends Thread{
    private Thread thread;
    private String name;

    public ThreadDemo(Thread thread, String name){
        this.name = name;
        this.thread = thread;
    }
    @Override
    public void run() {
        if(thread != null){
            try {
                thread.join();//即让传进来的参数thread先执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(int i=1; i<=10; i++){
            System.out.println(name +"::" +i);
        }
    }
}
public class Teacher_1_29_life {
    public static void main(String[] args) {
        //A,B,C三个线程  使得A,B,C三个线程顺序打印1～10
    	//本来会交替执行
        ThreadDemo A = new ThreadDemo(null, "线程A");//为null,直接打印，run方法执行完线程就结束了
        ThreadDemo B = new ThreadDemo(A, "线程B");//B让A先行
        ThreadDemo C = new ThreadDemo(B, "线程C");//C让B先行
        A.start();
        B.start();
        C.start();
        
        
        //sleep
        ////lamada表达式的方式创建匿名线程
//        new Thread(()->{
//            try {
//                //Thread.sleep(5000);//只能是毫秒
                  ////省去单位换算
//                TimeUnit.HOURS.sleep(3);
//                TimeUnit.MINUTES.sleep(27);
//                TimeUnit.SECONDS.sleep(8);
//                TimeUnit.MILLISECONDS.sleep(88);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
    }
}
