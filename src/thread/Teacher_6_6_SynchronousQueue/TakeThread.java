package thread.Teacher_6_6_SynchronousQueue;

import java.util.concurrent.SynchronousQueue;

/**
 * @author QiangQin
 * * @date 2021/7/3
 */
//专门获取数据
public class TakeThread extends  Thread{
        private SynchronousQueue synchronousQueue;//两个线程共享

        public TakeThread(SynchronousQueue que) {
                this.synchronousQueue=que;
        }

        @Override
        public void run() {
              while(true){
                      try {
                             Integer take= (Integer) synchronousQueue.take();
                              System.out.println(Thread.currentThread().getName()+"获取数据"+take);
                      } catch (InterruptedException e) {
                              e.printStackTrace();
                      }
              }
        }
}
