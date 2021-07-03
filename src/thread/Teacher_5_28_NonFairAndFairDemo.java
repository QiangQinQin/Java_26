package thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author QiangQin
 * * @date 2021/6/29
 */

public class Teacher_5_28_NonFairAndFairDemo implements Runnable {//实现一个任务体
    private static Integer num = 0;
    private ReentrantLock rtl;

    public  Teacher_5_28_NonFairAndFairDemo(ReentrantLock rtl) {//借助main里new中的参数
        this.rtl = rtl;
    }

    @Override
    public void run() {
        while (true) {
            //显性加锁
            rtl.lock();
            num++;
            System.out.println(Thread.currentThread().getName()+":"+num);

            rtl.unlock();
        }
    }

    public static void main(String[] args) {
        //非公平性锁的实现
        ReentrantLock nonFairLock = new ReentrantLock(true);
// 线程A、B 想抢nonFairLock
        new Thread(new  Teacher_5_28_NonFairAndFairDemo(nonFairLock) , "A").start();
        new Thread(new  Teacher_5_28_NonFairAndFairDemo(nonFairLock) , "B").start();
    }
}