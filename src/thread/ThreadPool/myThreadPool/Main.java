package thread.ThreadPool.myThreadPool;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //定义线程池，初始化线程数为2，核心线程数为4，最大线程数为6，任务队列最 多允许100个任务
        final ThreadPool threadPool = new BasicThreadPool(2, 6, 4, 100);
//定义20个任务并且提交给线程池
        for (int i = 0; i <= 20; i++) {
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + "开始了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        for (; ; ) {
////            不断输出线程池的信息;在输出的最后会发现 active count停留在了core size的位置，
//            System.out.println("getActiveCount:" + threadPool.getActiveCount());
//            System.out.println("getQueueSize:" + threadPool.getQueueSize());
//            System.out.println("getCoreSize:" + threadPool.getCoreSize());
//            System.out.println("getMaxSize:" + threadPool.getMaxSize());
//            System.out.println("======================================");
//            TimeUnit.SECONDS.sleep(5);
            TimeUnit.SECONDS.sleep(12); //单位 s
            //线程池在12秒之后将被shutdown
             threadPool.shutdown();
            // 使main线程join，方便通过工具观察线程堆栈信息
             Thread.currentThread().join();
        }
    }
}