package thread.Teacher_6_6_SynchronousQueue;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;

/**
 * @author QiangQin
 * * @date 2021/7/3
 */
//给SynchronousQueue放数据
public class PutThread extends Thread {
    private SynchronousQueue synchronousQueue;//两个线程共享

    public PutThread(SynchronousQueue que) {
        this.synchronousQueue=que;
    }

    @Override
    public void run() {
        Random random = new Random();
        while(true){
            try {
                Thread.sleep(random.nextInt(1000)+1000);//休眠1秒以上
                Integer put= random.nextInt(100);
                 synchronousQueue.put(put);
                System.out.println(Thread.currentThread().getName()+"放入数据"+put);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}


