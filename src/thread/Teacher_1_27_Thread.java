package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 多线程
 * 1、进程和线程的认识
 *   1）进程指的一段正在运行的程序
 *     一个程序运行中可以执行多个任务，任务称之为线程
 *   2）进程是程序执行过程中资源分配和管理的基本单位
 *                  线程是cpu执行的最小单位
 * 		进程拥有自己的独立的地址空间（安全），每启动一个进程，系统就会分配地址空间
 * 		进程可以拥有多个线程，各个线程（高效）之间 共享程序的内存空间（如：代码段  数据段  堆空间  进程级别的资源）
 * 
 *地址空间包括：虚拟机栈  程序计数器 本地方法栈   方法区  堆
 *JVM是一个进程，
 *每执行一个线程，都会单独分配一份虚拟机栈   程序计数器   本地方法栈
 * 
 * 为什么出现线程？
 *    每个进程有自己独立的地址空间。对于多并发的请求，如果为每一个请求创建一个进程，导致系统开销大、用户请求效率低
 *
 *发展过程：
 *     串行 -》 批处理 （是批量串行）-》进程 -》 线  程
 * 
 * 面试题：多线程和多进程有哪些区别？
 * 1）每个进程拥有自己独有的数据，线程共享数据
 *   所以线程之间的通信相比于进程之间的通信更有效 更容易
 * 2）线程 相比于 进程创建/销毁开销  更小（所以多线程用的更多，比如浏览器同时下载多个图片）
 * 3）进程是资源分配（分的是地址资源）的最小单位，线程是cpu调度（给他时间片）的最小单位
 * 4）多进程程序更加健壮，多线程程序只要有一个线程挂掉，对其共享资源的其他线程也会产生影响
 * 5）如果追求速度，选择线程
 *   如果频繁创建和销毁，选择线程
 *   如果追求系统更加稳定，选择进程
 * 
 * 线程是轻量级的进程（轻在创建时操作系统不用给他分配单独的地址空间。共享或者分一点所属进程的资源）
 *
 * 所有java程序运行在jvm上，jvm是一个进程
 * 
 * 2、线程的创建的几种方法
	 * 1）继承Thread类，重写run()方法      边吃饭边看电视
	 * 2）实现Runnable接口，重写run方法
	 * 3）匿名线程 匿名内部类//可以不算
	 * 4) 实现Callable接口，重写call方法
			 * Callable接口存在Executor框架中类，相比于Runnable更加强大
			 * a.Callable可以在任务执行结束之后提供一个返回值
			 * b.可在call方法中抛出异常
			 * c.运行Callable任务可以拿到一个Future对象，Future提供get方法拿到返回值(异步)
			 * 
 *通过返回结果，可以判断当前任务是否执行完成了，可以不停的轮询，看他是否有返回结果。
 *	如果有，拿到返回结果后做拿到返回结果之后应该做的事；
 *	如果没有，可做一些其他的事情，同时轮询Callable任务的线程，期待拿到返回结果，
 *这就叫异步
 *
 *同步是一个线程必须 等 到另外一个线程执行完才能做其他事情
 *
 * 通过Callable和FutureTask创建线程的步骤：
	 * a.创建Callable接口的实现类，重写call方法
	 * b.创建Callable实现类的 实例 ，使用FutureTask包装该实例
	 * c.将FutureTask实例作为参数创建线程对象
	 * d.启动该线程
	 * e.调用FutureTask的get方法获取子线程的执行结果
	 * 
 * 面试题：Callable和Runnable接口的区别：
 * 
 * 1）直观上：
	 * 一个实现call,有返回值，可以抛异常               
	 *一个实现run，无返回值，不能抛异常
 * 2）使用上  
	 *  Callable需要封装成FutureTask，然后以FutureTask实例作为参数创建线程对象，然后才能启动 
	 *   使用runnable的一个实例，作为我们创建线程的一个参数，然后启动线程，执行任务
 * 3）使用场景 
	 *	  同步，无返回值
	 *   异步，有返回值
 */
class MyThread extends Thread{
    @Override
    public void run() {
        //线程执行体
        while(true){ //1
            System.out.println("eat food");//2
        }
    }
}
class MyRunnable implements Runnable{
 //可自动生成
    @Override
    public void run() {
        //线程执行体
        while(true){ //1
            System.out.println("eat food");//2
        }
    }
}

class MyCallable implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for(int i=0; i<10000; i++){
            sum += i;
        }
        return sum;
    }
}


public class Teacher_1_27_Thread {
    
    public static void main(String[] args) {
    	////实现Callable接口，
        Callable<Integer> callableTask = new MyCallable();
        //需要做封装，才能传
        FutureTask<Integer> task = new FutureTask<>(callableTask);// FutureTask是runnable的一个实现，提供get方法
        Thread thread = new Thread(task);
        thread.start();

        //接受线程执行之后的结果
        try {
        	// FutureTask提供get方法
            Integer integer = task.get();
            System.out.println("result: "+integer);
        } catch (InterruptedException e) {//自动生成的
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        
        
////使用匿名内部类，（Teacher_1_29_life  91行，用lamada创建）
//      new Thread(){
//      @Override
//      public void run() {
//          System.out.println("thread-0");
//      }
//  }.start();
        
        
        
////implements Runnable创建子线程
//        //创建子线程对象,将Runnable实例作为参数实例化子线程对象
//        Thread thread = new Thread(new MyRunnable());
//        //启动吃饭的thread
//        thread.start(); 
//        //main线程
//        while(true){
//            System.out.println("watch TV"); //3
//        }

        
////extends Thread创建子线程
////吃饭  睡觉两个线程没有主次   先后之分  （就是看谁先抢到cpu使用权）     
////main  1   2  3    1 2  3
////子              1 2             1  2
//      //创建子线程对象
//      Thread thread = new MyThread(); //1
//      //启动吃饭的thread
//      thread.start(); //start -> run //2
//      //main线程
//      while(true){
//          System.out.println("watch TV"); //3
//      }
    }
}
