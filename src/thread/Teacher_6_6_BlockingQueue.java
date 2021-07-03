package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author QiangQin
 * * @date 2021/7/3
 */
public class Teacher_6_6_BlockingQueue {
    public static void main(String[] args) throws InterruptedException {
        arrayBlockingQueue();
    }

    //    有界队列
    public static void arrayBlockingQueue() throws InterruptedException {
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(5);//容量参数
        blockingQueue.put(12);//会抛出中断异常
    }

    //    无界队列
    public static void linkedQueue() throws InterruptedException {
        LinkedBlockingQueue<Integer> linkedQueue = new LinkedBlockingQueue<>();//没有容量限制
        linkedQueue.put(12);//会抛出异常
    }

    //同步阻塞队列
    public static void  synchronousQueue() throws InterruptedException {
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();//没有容量限制
        synchronousQueue.put(12);//会抛出异常
//        System.out.println(synchronousQueue);
//        synchronousQueue.put(13);//会抛出异常
//        System.out.println(synchronousQueue);
    }

}
