package thread;

import network.Teacher_3_23_AIO;

import java.util.concurrent.TimeUnit;

/**
 * 回顾：
 * 并发知识点
 * 1、并  发  指的是多个线程针对同一个资源，不是同时操作，而是  交替  操作
 * 2、临界资源   同一时间只允许一个线程访问,且不可剥夺 
 *    临界区      访问临街资源的  代码 
 * 3、并发编程的三大特性
 * 		1）原子性 一个操作是不可分割，那就是一个原子操作，就说这个操作具有原子性
 * 		2）可见性 一个变量被多线程共享，如果一个线程修改了这个变量的值，其他线程能够立即得知修改，我们称这个修改具有可见性
 * 		3）有序性 所有操作的执行有序的
 * 4、线程安全
 * 		不考虑耗时和资源消耗，在单线程执行和多线程执行的情况下，最终得到的结果是  相同 的(即不出差错)，那么称这样的操作是   线程安全的
 *
 *
 * synchronized关键字
 * 1）方法
 *		 注意：调用  不同  的对象的同步方法， 非 线程安全的(因为不是一把锁)
 * 2）代码块
 * synchronized(){
 *
 * }
 * 
 * 底层原理
 * 反汇编: 
 * javac   
 * java   
 * javap -v Teacher_2_3_Synchronized (-v会把代码和常量池的信息都打印出来)
 * 
 * 1）同步方法
 * 	常量池中多了ACC_SYNCHRONIZED标识符，标识当前的方法是一个同步方法，当方法被调用，调用指令会检查方法ACC_SYNCHRONIZED标识符是否被设置，
 *  如果设置，执行线程会先去获取monitor，获取成功之后才会去执行方法体，方法体执行完成之后或释放 monitor。
 * 2）同步代码块
 * monitorenter
 *  每一个对象都与一个monitor相关联，一个monitor的lock只能被一个线程在同一时间所拥有，在一个线程尝试获取monitor的使用会发生以下事情：
 *    a.如果monitor的entry count为0，意味monitor的lock还没有被获取，某个线程获取 之后会对entry count+1，从此该线程就是这个monitor的所有者了
 *    b.如果一个已经拥有该monitor使用权的线程再次进入，会导致monitor的entry count进行+1操作 ,表明这种锁是(可重入锁)
 *    (synchronized  监视器锁  独占锁都是可重入锁)(比如wait会释放lock锁,就需要在重入;或者要先调test1.再调test2,而且这俩方法都被synchronized修饰,那么synchronized就得是可重入锁)
 *   
 *    c.如果monitor已经被其他线程所拥有，其他线程尝试获取该monitor的额所有权时，会被陷入 阻塞，直到monitor的entry count为0，才能再次尝试去获取
 *
 * monitorexit
 *   释放对monitor的使用权，要释放对某个对象的monitor的使用权  前提  是首先获取该monintor的所有权，将monitor的entry count进行-1，
 *   如果entry count为0，那就表示该线程不再拥有该monitor的使用
 *   
 * 一个monitorenter对应两个monitorexit，保证代码发生异常，至少可以读取后面的一个monitorexit，释放锁
 *  WPS上monitor结构图,要能串起来
 */
public class Teacher_2_3_Synchronized {
//	 课后练习：启动两个线程，顺序输出5，4，3，2，1，一个线程输出完成  之后  第二个线程输出
    //同步"方法"
	// //synchronzied修饰成员方法，synchornized获取this对象，this对象代表当前对象
    public synchronized void test1(){//不加synchronized就是A B  交替 输出
        //获取test的monitor lock
        int i=5;
        while(i >=1){
            System.out.println(Thread.currentThread().getName()+"::"+i--);
            try {
                TimeUnit.MILLISECONDS.sleep(10);//毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //将test的monitor lock释放
    }
    public void test2(){
        //同步代码块
        synchronized (this){
            //获取test的monitor lock
            int i=5;
            while(i >=1){
                System.out.println(Thread.currentThread().getName()+"::"+i--);
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //释放test的monitor lock
        }
    }
    public static void main(String[] args) {
        Teacher_2_3_Synchronized testSynchronousQueue = new Teacher_2_3_Synchronized();
        Teacher_2_3_Synchronized test0 = new Teacher_2_3_Synchronized();
//    	若下面A 执行 testSynchronousQueue.test1() 和 B 执行 test0.test1(),会交替执行   //因为两个不是一个锁,所以不是一个执行完再下一个
      new Thread("A"){
          @Override
          public void run() {
              testSynchronousQueue.test1();//当前是test对象
              // testSynchronousQueue.test2();
          }
      }.start();

      new Thread("B"){
          @Override
          public void run() {
              testSynchronousQueue.test1();
              // testSynchronousQueue.test2();
          }
      }.start();
    	
    	
    	
//        Object obj  = new Object();
//        //验证synchronized是可重入锁
//        new Thread(){
//            @Override
//            public void run() {
//                synchronized (obj){
//                    System.out.println("entry + 1");
//                    synchronized (obj){
//                        System.out.println("entry + 1 again");
//                    }
//                }
//            }
//        }.start();
    }
}
