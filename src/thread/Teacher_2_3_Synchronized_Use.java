package thread;

import java.util.concurrent.TimeUnit;

/**
 * synchornized的使用场景
 * 1、(  安全)两个线程同时访问同一个对象的同步方法     
 * 2、(不 安全)两个线程同时访问两个对象的同步方法                           test1.func1()  test2.func1()
 * 3、( 安全)两个线程同时访问(一个或两个)对象的静态同步方法    
 * 4、(不安全)两个线程分别同时访问(一个或两个)对象的同步方法和非同步方法  
 * 5、(不安全)两个线程访问同一个对象中的同步方法，同步方法又调用另外一个非同步方法     
 * 6、( 安全)两个线程同时访问同一个对象的不同的同步方法  (没说是静态)  
 * 7、(不安全)两个线程同时访问静态synchronized和非静态synchornized方法  
 * 8、同步方法抛出异常，JVM会自动释放锁
 * 
 *判断依据
 *    测试若 非 线程安全,就交替打印(原子性);安全,就是先执行完一个,再执行另一个
 *    获取的是同一把锁,则安全;调用非同步就不安全
 *    
 * synchornized的练习
 *课堂练习:
 * 	synchornized同步锁实现3个线程循环打印数字，
	 * 使用线程1，打印1，2，3，4，5.
	 * 线程2，打印6，7，8，9，10.
	 * 线程3，打印11，12，13，14，15.
	 * 依次循环打印，直到打印至60
	 * (提示：会使用到wait/notify/notifyAll方法)
 *
 * synchornized锁优化 后面开发班讲
 */
public class Teacher_2_3_Synchronized_Use {
	
	//同步方法
    public synchronized void fun1(){
        System.out.println(Thread.currentThread().getName()+":: 同步方法fun1开始");
        try {
            TimeUnit.MILLISECONDS.sleep(1000);//模拟结束过程
//        fun3();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+":: 同步方法fun1结束");
    }

    //静态同步方法
    public synchronized static void fun2() throws Exception {
        //static的是 类  锁 (锁 class对象,只一个,Teacher_2_3_Synchronized_Use)
        System.out.println(Thread.currentThread().getName()+":: 静态同步方法func2开始");
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+":: 静态同步方法func2结束");
    }

    //非同步方法
    public void fun3(){
        System.out.println(Thread.currentThread().getName()+":: 非同步方法func3开始");
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+":: 非同步方法func3结束");
    }
    //同步方法(非静态的锁   锁当前创建出来的对象)
    public synchronized void fun4(){
        System.out.println(Thread.currentThread().getName()+":: 同步方法fun4开始");
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+":: 同步方法func4结束");
    }
    
    
    
    
    public static void main(String[] args) {
        Teacher_2_3_Synchronized_Use test1 = new Teacher_2_3_Synchronized_Use();
        Teacher_2_3_Synchronized_Use test2 = new Teacher_2_3_Synchronized_Use();
        new Thread("A"){
            @Override
            public void run() {
                test1.fun1();  //unlock
                test1.fun3();
            }
        }.start();

        new Thread("B"){
            @Override
            public void run() {
                test1.fun1(); //lock
                test1.fun3();
            }
        }.start();

//        Thread threadc = new Thread("C"){
//            @Override
//            public void run() {
//                test1.fun1();
//            }
//        };
//        threadc.start();
//        threadc.interrupt();
//
//        new Thread("D"){
//            @Override
//            public void run() {
//                test1.fun1();
//            }
//        }.start();
    }
}




////1.两个线程同时访问同一个对象的同步方法   
//public synchronized void fun1(){
//    System.out.println(Thread.currentThread().getName()+":: 同步方法fun1开始");
//    try {
//        TimeUnit.MILLISECONDS.sleep(1000);//模拟结束过程
////    fun3();
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    }
//    System.out.println(Thread.currentThread().getName()+":: 同步方法fun1结束");
//}
//Teacher_2_3_Synchronized_Use test1 = new Teacher_2_3_Synchronized_Use();
//new Thread("A"){
//    @Override
//    public void run() {
//        test1.fun1();  
//    }
//}.start();
//
//new Thread("B"){
//    @Override
//    public void run() {
//        test1.fun1(); 
//    }
//}.start();



////2.两个线程同时访问两个对象的同步方法 (获取的不是同一把锁)  
//Teacher_2_3_Synchronized_Use test1 = new Teacher_2_3_Synchronized_Use();
//Teacher_2_3_Synchronized_Use test2 = new Teacher_2_3_Synchronized_Use();
//new Thread("A"){
//    @Override
//    public void run() {
//        test1.fun1(); 
//    }
//}.start();
//
//new Thread("B"){
//    @Override
//    public void run() {
//        test2.fun1();
//    }
//}.start();


////3.两个线程同时访问(一个或两个)对象的  静  态同步方法    
//Teacher_2_3_Synchronized_Use test1 = new Teacher_2_3_Synchronized_Use();
//Teacher_2_3_Synchronized_Use test2 = new Teacher_2_3_Synchronized_Use();
//new Thread("A"){
//  @Override
//  public void run() {
//      test1.fun2(); 
//test2.fun2(); 
//  }
//}.start();
//
//new Thread("B"){
//  @Override
//  public void run() {
//      test1.fun2(); 
//test2.fun2(); 
//  }
//}.start();

//4.两个线程分别同时访问(一个或两个)对象的同步方法和非同步方法  
//Teacher_2_3_Synchronized_Use test1 = new Teacher_2_3_Synchronized_Use();
//new Thread("A"){
//@Override
//public void run() {
//    test1.fun1(); 
//    test1.fun3(); 
//}
//}.start();
//
//new Thread("B"){
//@Override
//public void run() {
//    test1.fun3(); 
//    test1.fun3(); 
//}
//}.start();


//5.两个线程访问同一个对象中的同步方法，同步方法又调用另外一个  非同步方法(非安全)      
//public synchronized void fun1(){
//    System.out.println(Thread.currentThread().getName()+":: 同步方法fun1开始");
//    try {
//        TimeUnit.MILLISECONDS.sleep(1000);//模拟结束过程
//        fun3();
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    }
//    System.out.println(Thread.currentThread().getName()+":: 同步方法fun1结束");
//}
//new Thread("A"){
//    @Override
//    public void run() {
//        test1.fun1();  //unlock
//    }
//}.start();
//
//new Thread("B"){
//    @Override
//    public void run() {
//        test1.fun1(); //lock 
//        }
//}.start();
//new Thread("c"){
//@Override
//public void run() {
//  test1.fun3();//因为fun3不安全  210行也用了
//}
//}.start();

//6.( 安全)两个线程同时访问同一个对象的不同的同步方法   (没说是静态,因为他获取的是同一个对象的monitor lock)
//new Thread("A"){
//    @Override
//    public void run() {
//        test1.fun1(); 
//    }
//}.start();
//
//new Thread("B"){
//    @Override
//    public void run() {
//        test1.fun4();
//    }
//}.start();
//* 7、(不安全)两个线程同时访问静态synchronized和非静态synchornized方法  (一个锁的是类  一个锁当前对象,锁的对象不一样)
//new Thread("A"){
//@Override
//public void run() {
//  test1.fun1(); 
//}
//}.start();
//
//new Thread("B"){
//@Override
//public void run() {
//  fun2();//静态方法
//}
//}.start()

//* 8、同步方法抛出异常，JVM会自动释放锁
//Thread threadc = new Thread("C"){
//@Override
//public void run() {
//  test1.fun1();
//}
//};
//threadc.start();
//threadc.interrupt();//Teacher_1_29_Life_Method  16行
