package thread;

import java.util.concurrent.TimeUnit;

/**
 * 下面3个方法（在Object中）可以帮助线程的通信协作
 * wait 调用某个对象的wait()方法可以让当前线程阻塞,并释放监视器锁(sleep不会释放)
 * notify        调用  当前对象notify/notifyAll才能够唤醒这个对象所在的线程，不会释放 监视器锁
 * notifyAll
 * 注意：使用这三个方法需要让当前线程 拥有 当前对象的monitor lock（对象天生就可以拥有一把锁，使用synchronization就可以获取任意对象的这把锁）
 *
 *
 * 线程中断方法
 *		 每个Java线程都会有一个中断状态位，程序可以检测这个中断状态位判读线程是否执行结束
 *
 * 方法1： a.interrupt()
 * 		public void interrupt() 不是静态方法，所以需要由线程对象调用，作用是将中断位置置为true
 *     （如果当前线程中存在可中断方法，那么interrupt才能中断当前这个线程；如果不存在，没法使得线程暂停）
 *
 * 如下方法能够使得当前线程进入阻塞状态，进入阻塞状态之后，调用interrupt方法可以打断阻塞(不是生命周期结束,会   抛出异常)，
 * 因此这种方法被称之为  可中断方法（也称为可响应中断的方法）
 * 		Object.wait()/wait(long参数)
 * 		Thread.sleep(long)/TimUnit.XXX.sleep(long)
 * 		Thread.join()/Thread.join(long)
 *
 * interrupt方法底层做的事：
 * 		如果一个线程被interrupt（即调用了interrupt方法）,会设置interrupt flag；（为false）
 * 		而如果当前线程正在执行可中断方法(使线程进入阻塞)，这时调用interrupt方法，会导致interrupt flag被清除
 *
 * b.isInterrupted判断当前线程的中断状态位是否为true
 *
 * c.interrupted
 *		 public static Boolean interrupted() 静态方法
 * 		  调用interrupted 会 擦除中断状态位的标识
 */
public class Teacher_1_29_Life_Method {
    public static void main(String[] args) {
    	
//    	//静态  擦除
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                while(true){
//                    Thread.interrupted();//擦除
//                }
//            }
//        };
//        thread.setDaemon(true);//设置守护线程
//        thread.start();
//        System.out.println("start:"+thread.isInterrupted());//false
//        try {
//            TimeUnit.MILLISECONDS.sleep(2);//主线程睡,让子线程先执行
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        thread.interrupt();
//        System.out.println("最后:"+thread.isInterrupted());//false
//
        
        
    	
    	
        Thread thread = new Thread(){
            @Override
            public void run() {
                while(true){//
                    try {//sleep是可中断方法。使得子线程进入阻塞状态
                        System.out.println("睡眠前: "+this.isInterrupted());//true
                        TimeUnit.MILLISECONDS.sleep(1); //sleep会擦除中断状态位
                        System.out.println("睡眠后: "+this.isInterrupted());//true
                    //interrupt方法会打断这种阻塞，会抛出异常
    					} catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
//        try {  //好像没用
//            TimeUnit.MILLISECONDS.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("前: "+thread.isInterrupted());//false
//        thread.interrupt();//打断sleep造成的阻塞
//        System.out.println("后: "+thread.isInterrupted());//true

    	
    	
    
    	
    	
//    	 Thread thread = new Thread();
//    	 System.out.println("Thread interrupt flag: "+thread.isInterrupted());//false
//    	 thread.interrupt();//中断
//    	 System.out.println("Thread interrupt flag: "+thread.isInterrupted());//false
//    	
    	
    	
    	
//        String strs = new String("testSynchronousQueue wait");
//        new Thread("A"){
//            @Override
//            public void run() {
//                synchronized (strs){//获取strs的对象锁
//                    //同步代码块
//                    try {
    						////如果多个线程调用同一个对象的wait方法，就都阻塞了
//                        strs.wait(); //释放当前对象的monitor lock ，进入到Waiting状态（WPS上线程六状态之间的转换）
//                        //表示让当前线程进入到阻塞状态
//                        System.out.println("the currend thread name is "+Thread.currentThread().getName());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();
//
//        new Thread("B"){
//            @Override
//            public void run() {
//                synchronized (strs){//获取strs的对象锁
//                    //strs.notify();//随机唤醒其中一个（唤醒的这个还要获取锁，然后接着wait  打印）
//                    strs.notifyAll(); //唤醒所有线程（调用了strs.wait()而阻塞的） 然后所有线程开始抢锁
//                }
//            }
//        }.start();
    }
}
