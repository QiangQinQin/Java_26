package thread;

import java.util.concurrent.TimeUnit;

/**
 * 并发编程
 * 什么是并发编程？
	 * 并发编程是为了提高程序的执行速度，
	 * 在  宏观  上使得多个任务同时执行，则需要启动多个线程 ，
	 * 但  事实  启动多个线程之后，不管针对单核cpu还是多核cpu线程进行上下文切换.cpu 通过给每一个线程分配时间片，只有说拿到时间片的线程才可以执行，
	 * 通常时间片很短，所以才会感觉到多个线程在并行操作，
	 * 存在线程不安全性(因为要共享资源)。
 * 
 * (我想 为什么要并发,比如说你需要同时看视频  回消息   或者  在等IO的时候,干些其他事)
 * 
 * 一个正确执行的并发程序，需要同时满足并发编程 的三大特性，
 * 			原子性、可见性、有序性
 * 1） 原子性
	 * 所谓  原子性 是指一次或者多次操作中，要么所有的操作  全部  执行要么所有的操作都不执行
	 * 原子操作（可以是1行 或 多行代码）是不可分割的操作，即一个原子操作中间是不会被其他线程所打断的
	 * int a = 10; //0赋值给线程工作内存中的变量a  原子操作
	 * a++;        //拿a  进行a+1      赋值a      非原子操作(任意一步结束后都可以进行中断操作)
	 * int b = a;  //拿a  b=a              非原子操作
     * a = a+1;    //拿a  进行a+1  赋值a      非原子操作
 *
 * 2）可见性
 * 		如果在一个线程对共享变量做了修改，那么另外的线程立即可以看到修改后的最新值
 * 3）有序性
	 * 有序性指的程序代码在执行过程中的先后顺序，由于编译器或计算机的 优化 ，导致代码 的执行未必是开发者编写代码时的顺序
	 * int x = 10; 1    1->3->2
	 * int y = 0; 2
	 * x++; 3
	 *
	 * x++在int y=0之前得到执行，这种情况叫做   指令重排序
	 * 在  单  线程环境中，无论怎么重排序，都不会影响最终的结果
	 * 但在 多  线程环境中，如果有序性得不到保证，最终结果也可能与预期不符(下面load的例子)
 *
 *
 * 线程同步问题
 * 1）为什么需要同步？
 * 		一块资源被多个线程同时操作，如果没有任何的同步操作，就会发生冲突，因为无法保证每个线程的执行结束时机，所以就无法控制最终结果
 * 2）同步需要用到的3个概念:
	 *   临界资源：同一时刻只允许一个线程访问的资源，临界资源是不可剥夺资源
	 *   临界区：访问临界资源的代码块
	 *   临界区特点： 提供线程独占式访问，也就是说若有一个线程正在访问该代码段，其他线程想要访问，只有等待当前线程离开该代码段才可访问，这样就保证了线程安全
 * 3）银行叫号系统(public class Teacher_1_27_Ticket)
 *  a.某个号码被略过
 *  	线程A: index = index++(分3步:拿  加  赋)index=65++ -> 66 (没来的及输出)cpu将使用权转给了B
 *  	线程B:                                               index = index++. index=66++ -> 67 sout(67) 线程A再也没有得到执行，最终66被略过
 *  b.某个号码重复
 *  	线程A：index = index++, 执行65+1后(还没赋值)，cpu将使用权给了B                                                    sout(66)
 * 		 线程B:                                             index = index++, 执行65++ -> 66, sout(66), cpu将使用权给A
 *  c.超过给定的最大值 500
 *  	线程A: index=499,判断完将 cpu将使用权给了B                           500++;sout()最终导致号码超过给定的最大值
 *  	线程B:                            index=499++,sout(500),转A线程
 *
 *  出现以上问题的原因为原先代码并没有满足并发编程三大特性
 * 解决方案1:
 *  synchronzied关键字
 *  		提供一种 排他  机制，在同一时间内去操作synchornized封装的代码块或者方法
 *
 *  用法：
 *  1）同步方法
	 *  public synchornized void func(){
	 *
	 *  }
	 *
	 *  public synchornized static void func(){//静态方法也ok
 *
 *  }
 *  2）同步代码块
//	 * private final Object lock = new Object();//表示常量，只能被赋值一次，赋值后值无法改变！
	 * public void func(){
	 *     synchornized(lock){//获得监视器锁
	 *         ...//此处代码某一时刻只能有一个线程去访问
	 *     }
	 * }
 *
 *解决方案2：
 *		volatile
 *
 * 课后练习：
 *  重写叫号程序
 *  
 * 启动两个线程，顺序输出5，4，3，2，1，一个线程输出完成之后第二个线程输出(用synchornized.   Teacher_1_29_life  用join)
 */
class Test{
    public static Test load(){
        return new Test();
    }
}

public class Teacher_1_29_Concurrent {
    private boolean flag = false; //1
    private Test test; //2
    //希望返回有效test对象
    public Test func() {
        if (!flag) { //3
            test = Test.load(); //4
            flag = true; //5
        }
        return test; //6
    }
    public static void main(String[] args) {
        //thread A: 3 -> 4,5发生指令重排序: 5先执行，4后执行，但在多线程中5执行完之后thread上线切换
        //thread B: 3 -> 6

	 	
    }   
}
//
//private static int value = 0;//共享变量      线程A和B都有一个value副本
//public static void main(String[] args) {
//  //如何将下面改为线程安全???????(好像要用Volatile)
//  new Thread("A"){
//      @Override
//      public void run() {
//          int localValue = value;
//          while(localValue < 5){//有可能while完就上下文切换到另一个进程了
//              if(localValue != value){//想下面改变一次,上面输出一次.
//                  System.out.println("the value is updated to "+value);// 但发现并没有输出updated
//                  localValue = value;
//              }
//          }
//      }
//  }.start();
//
//  new Thread("B"){
//      @Override
//      public void run() {
//          int localValue = value;
//
//          while(localValue < 5){
//              System.out.println("the value will be changed to "+(++localValue));
//              value = localValue;//value副本  的修改并没有立即同步到   value原本
//
//              //短暂睡眠，使得A线程进行输出
//              try {
//                  TimeUnit.SECONDS.sleep(1);
//              } catch (InterruptedException e) {
//                  e.printStackTrace();
//              }
//          }
//      }
//  }.start();