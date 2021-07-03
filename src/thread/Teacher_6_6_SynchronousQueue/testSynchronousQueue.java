package thread.Teacher_6_6_SynchronousQueue;

import java.util.concurrent.SynchronousQueue;

/**
 * @author QiangQin
 * * @date 2021/7/3
 */
public class testSynchronousQueue {
    public static void main(String[] args) {
        SynchronousQueue<Integer>  synchronousQueue = new SynchronousQueue<>();
      //起两个线程，只能先放，再取；并且只能取了再放
        new TakeThread(synchronousQueue).start();
        new PutThread(synchronousQueue).start();
    }
}
