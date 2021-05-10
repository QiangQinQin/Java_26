package thread;

/**
 * 回顾
 * 进程：一段正在运行的程序，资源分配的基本单位
 * 线程：进程中的任务，cpu调度的最小单元
 * 线程的创建：
	 * 1）Thread 继承
	 * 2）Runnable 实现
	 * 3）Callable 实现（一般需要返回值才用）
	 * 4）匿名内部类
 * 启动线程：start （里面调用run call方法，Teacher_1_27_Ticket）
 *
 *
 * 守护线程
 * 		守护线程是一类比较特殊的线程，一般用于处理  后台  的工作（即何时需要守护线程？），比如JDK的垃圾回收线程
 * 什么是守护线程？为什么会有守护线程？何时需要守护线程？
 * 需要明确：JVM什么情况下退出？
	 * 官方：The java virtual machine exits when the only thread running are  all dameon thread.（即运行的线程都是守护线程，才会退出）
	 * 即如果JVM没有守护线程，JVM进程不会退出
	 * 守护 线 程具备 自动 结束 进 程生命周期的特点，非守护线程不具备这样的特点（即为什么会有守护线程？）
 * eg. 如果垃圾回收线程是一个非守护线程，那么某一程序完成工作运行结束，JVM无法退出，那么垃圾回收线程会依旧工作
 *
 * 
 * 注意：setDaemon方法只能线程启动之 前 使用，
 * 如果对于一个线程已经死亡或者未启动，那么设置setDaemon会抛出IllegalThreadStateException
 *
 *
 * 并发与并行概念
	 * 并 发 指的是多个线程操作同一个资源，不是同时执行，而是交替执行（针对单核cpu），只不过cpu时间片很短，执行速度很快，  看起来  同时执行
	 * 并 行 才是  真正  的同时执行，多核cpu。每一个线程使用一个单独的cpu运行
 *
 *并发编程：把多个任务在同一时间交替重复执(wps上并发与并行的图)，
 *
 * QPS: 系统每秒能够  响应  的请求数
 * 吞吐量： 单位时间内能够处理的请求数
 * 平均响应时间： 系统对某一个请求的平均响应时间 QPS = 并发数/平均响应时间
 * 并发用户数： 系统（在某一时刻）可以承载的 最大 用户数（比QPS和吞吐量大一点）
 *
 *（面试） 互联网系统结构如何提高系统的并 发 能力？
	 * 垂直方向（升级 某个 机器的cpu数量  内存 磁盘  缓存）
	 * 水平方向(集群（多个人做同一件事）    分布式（一个复杂的事情拆分步骤去做）)
 */
public class Teacher_1_29_Daemon {
    public static void main(String[] args) {
//    	练习：使用匿名线程的方式创建一个线程，这个线程的任务是   持续睡眠1ms  ，主线程睡眠1000ms输出
//    	 *   主线程main结束
        //匿名线程
//    	 new Thread(){
//            @Override
//            public void run() {
//                while(true){
//                    try {
//                        Thread.sleep(1);//毫秒
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();
        Thread thread = new Thread(){
            @Override
            public void run() {
                while(true){//持续
                    try {
                        Thread.sleep(1);//毫秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        /**
         * 如果thread是一个  非  守护线程，main线程正常运行结束之后，JVM进程并没有退出；
         * 如果我们将thread设置为守护线程，main线程结束生命周期，只剩守护线程了（他能自动结束进程生命周期），所以JVM进程会退出（例如：退出微信   视频聊天也退出）
         */
        //设置thread为守护线程，需要在start之前
        thread.setDaemon(true);
        thread.start();
        
        //睡眠 Thread.sleep(1000)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
   
        //main线程结束
        System.out.println("main thread finished");
    }
}
