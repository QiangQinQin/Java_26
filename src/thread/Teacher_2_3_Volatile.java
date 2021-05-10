package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * volatile关键字
 *       保证可见性、有序性
 * 1、使用
 * volatile修饰变量
 * 2、volatile特征
 *      1）保存内存可见性
 *           volatile修饰的变量不会  缓 存到工作内存中，每一次读取获取最新volatile变量
 *      2）禁止指令重排序
 *           Java内存不会对volatile指令进行重排序，从而保证对volatile的执行顺序永远是按照书写顺序执行的
 *
 * happens-before规则：
 *         volatile字段的写入操作  发生  在后续同一个字段的读操作  之前
 *
 * volatile修饰的变量产生的  汇编代码 ，会存在一个lock前缀，相当于一个内存屏障
 *     1） 它确保指令重排序的时候不会将其后面的指令排到内存屏障之前的位置，也不会前面的指令排到内存屏障之后。 
 *        也就是执行到内存屏障这一指令时，在它前面的操作已经全部执行完成
 *     2） 它会强制性的将工作内存的修改立即写入主内存中（WPS上工作内存图）
 *     3） 如果是写操作，它会导致其他线程对应的工作内存中的值是无效值
 *
 * volatile使用场景
 *    1）boolean标志位
 *    2）单例模式双重检测锁（重要Teacher_2_3_Singleton）
 *
 *
 *
 * 扩展知识：
 * 1）happens-before原则 8条
 * 2）重排序和禁止重排序介绍（自己再了解一下）
 *（牵扯到） 计算机底层内存模型（volatile借鉴了计算机底层内存模型的处理方式）
 *        在计算机中，所有运算操作都由cpu的寄存器完成，涉及到数据的读取的写入，cpu的处理速度和内存的访问速度差距太大，出现了cpu-cache模型
 *             cpu -> cache -> main memory出现了cpu缓存一致性问题
 *      比如：i++
 *      1)主内存中的数据复制到cache
 *      2）cpu寄存器计算的时候从cache中读取和写入
 *      3）cache刷新到主内存
 * 单线程不会出现问题，多线程就会存在问题（cache和主存内容不一致）
 * 
 * 为了缓存不一致性的问题，主要通过两种方式：
	 * 1）总线加锁（不推荐，影响了cpu对其他组件的访问）
	 * 2）缓存一致性协议 （维持两个状态位，判断是 独占的 共享的 无效的） （下去自己多加了解）
	 *      读取不做任何处理，写入时  不仅   会发出信号通知其他cpu将变量的标志位置为无效状态（然后写入主内存，之后的操作就需要去主内存中去再次获取）。
	 *      还会  使得当前重排序过程发生改变，处理器会在计算之后对乱序执行的代码会进行重组，保证结果的准确性
 *
 *
 * volatile无法保证原子性（面试   wps上图）
 *    例：创建10个线程，分别执行1000次i++操作，期望程序最终的结果是10000同时思考为什么出现这种结果？
 *    
 *       小于10000是因为 volatile保证i的可见性，i++不具备原子性，volatile不保证i++原子性（可以给increase()加个synchronized）
 * 
 * CountDownLatch的使用场景：（用的多，大纲里没）
 *    有一个任务想要继续执行，但是必须要等待其他任务执行完（即计数器值变为0）
 *
 *
 * volatile使用注意点
 *     volatile对应   基本  数据类型才有用（拷贝值可以；拷贝对象是拷贝的引用，对于引用里内部的数据并不能保证可见性）
 *     volatile保证对象引用，无法保证对象内部的数据的可见性，如：
 *     //public volatile static String[] array;  -> new String[10];
                                         //array -> new String[5];//只保证引用，不保证内部对象的立即可见
 *
 *
 *volatile比synchronized内存消耗更低，性能高，可用于要变量可见性的场景
 *
 */
public class Teacher_2_3_Volatile {
    
    public volatile static int i = 0;
    private static CountDownLatch countDownLatch = new CountDownLatch(10);
   
//    public synchronized static void increase(){
    public  static void increase(){
        i++;
    }
   
    public static void main(String[] args) {  //psvm
        
    	
    	for(int i=0; i<10; i++){//创建10个线程
            new Thread(){
                @Override
                public void run() {
                    for(int j=0; j<1000; j++){
                        increase();//加加1000次
                    }
                    countDownLatch.countDown(); //countDownLatch中计数器-1
                }
            }.start();
        }
    	
        try {
            //调用await的主线程阻塞，等到计数器为0才会继续执行     和下面yield类似，没有解决原子性问题
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(i);
        
        
 ////主线程里打印结果的时候保证10个子线程已经执行完，有同学用sleep保证，不可靠      
//        while(Thread.activeCount() > 2){//活的线程数（至少有main和idea俩）
//            //idea main
//            Thread.yield(); //自动让步一次cpu时间片
//        }
//        System.out.println(i);   //大多数情况下输出比10000小

    }
}


////Teacher_1_29_Concurrent
//    private volatile static int value = 0;//volatile保证每次读取获取的是共享变量的最新值
//    public static void main(String[] args) {
//        new Thread("A") {
//            @Override
//            public void run() {
//                int localValue = value;
//                while (localValue < 5) {
//                    if (localValue != value) {
//                        System.out.println("the value is updated to " + value);
//                        localValue = value;
//                    }
//                }
//            }
//        }.start();
//
//        new Thread("B") {
//            @Override
//            public void run() {
//                int localValue = value;
//
//                while (localValue < 5) {
//                    System.out.println("the value will be changed to " + (++localValue));
//                    value = localValue;
//
//                    //短暂睡眠，使得A线程进行输出
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();
//    }
