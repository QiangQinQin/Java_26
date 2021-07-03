package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
    public static void linkedBgQueue() throws InterruptedException {
        LinkedBlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();//没有容量限制
        blockingQueue.put(12);//会抛出异常
    }

}
