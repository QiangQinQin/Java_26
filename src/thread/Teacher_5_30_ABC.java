package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
题目：ABC三个线程分别打印各自名称，打印结果ABCABC....
思路：
    A线程通知B线程(建一个a、b的Condition)、
    B线程通知C线程（bc的Condition）、
    C线程通知A线程(ca的Condition)

老师是JDK1.7.0_80  ,自己运行会无法输出,是JDK Bug吗？？？？至少掌握思想吧

* */

class ABCThread implements Runnable {//任务体，重写run方法
    private ReentrantLock lock;
    private Condition sCondition;//signal  唤醒其他线程用
    private Condition aCondition;//await   阻塞需要被唤醒时用

    public ABCThread(ReentrantLock lock, Condition sCondition, Condition aCondition) {
        this.lock = lock;
        this.sCondition = sCondition;
        this.aCondition = aCondition;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 10) {
            //加锁
            lock.lock();

                try {
                    //A线程会释放锁并阻塞，直到接收到C线程通知（即拿到ca Condition） 才继续执行
                    aCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.print(Thread.currentThread().getName() + " ");
                //通知B线程（即ab）
                sCondition.signal();
                i++;

            //释放锁
            lock.unlock();
        }

    }
}
public class Teacher_5_30_ABC {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition ab = reentrantLock.newCondition();//即这个condition同时作用于a b线程
        Condition bc = reentrantLock.newCondition();
        Condition ca = reentrantLock.newCondition();

        new Thread(new ABCThread(reentrantLock,ab,ca),"A").start();//对应自定义的构造函数
        new Thread(new ABCThread(reentrantLock,bc,ab),"B").start();//发的是bc，收的是ab
        new Thread(new ABCThread(reentrantLock,ca,bc),"C").start();

        reentrantLock.lock();//加锁
          ca.signal();//唤醒一个等待ca的线程
        reentrantLock.unlock();//释放锁

    }
}
