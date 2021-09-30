package thread;

/**
 * @author QiangQin
 * * @date 2021/9/25
 */

class Lock implements Runnable{

    private  Object obj1; //注意此处不能为final/static
    private  Object obj2;
    //如果用final修饰，就不能在下面构造方法中重新赋值；
    // 如果用static修饰，则变成全局变量，所有线程 共享锁 ，无法形成死锁。

    public Lock(Object obj1, Object obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    @Override
    public void run() {
        while(true){
            deadLock();
        }
    }


    private void deadLock() {
        String name = Thread.currentThread().getName();
        synchronized (obj1){
            System.out.println(name+"...lock on "+obj1);
            synchronized (obj2){
                System.out.println(name+"...lock on "+obj2);
            }
        }
    }
}

public class Test_9_25_Deadlock {
//    写出一个死锁的实例。
    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = new Object();

        Thread t1 = new Thread(new Lock(obj1, obj2));
        Thread t2 = new Thread(new Lock(obj2, obj1));

        t1.start();
        t2.start();
    }
}
