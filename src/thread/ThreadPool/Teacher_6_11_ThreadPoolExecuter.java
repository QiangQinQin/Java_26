package thread.ThreadPool;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author QiangQin
 * * @date 2021/7/5
 */
public class Teacher_6_11_ThreadPoolExecuter {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        创建线程池实例（注意参数含义）
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
//====================执行已经提交的Runnable任务对象=====================================
//        方法1：通过匿名内部类，传递一个任务体
//        threadPoolExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName()+"执行任务中");
////               输出 pool-1-thread-1执行任务中，表示 第一个pool的第一个thread在执行
//            }
//        });

////        方法2:提交6次，相当于给线程池提交6个任务进去！！！！
//        Teacher_6_11_Runnable runnable = new Teacher_6_11_Runnable();
//        threadPoolExecutor.execute(runnable);
//        threadPoolExecutor.execute(runnable);
//        threadPoolExecutor.execute(runnable);
//        threadPoolExecutor.execute(runnable);
//        threadPoolExecutor.execute(runnable);
//        threadPoolExecutor.execute(runnable);
//        /*
//        pool-1-thread-1子线程正在执行  //Thread1来执行Runnable任务
//        pool-1-thread-5子线程正在执行
//        pool-1-thread-2子线程正在执行
//        pool-1-thread-3子线程正在执行
//        pool-1-thread-4子线程正在执行
//        pool-1-thread-1子线程正在执行 //因为当Thread1执行完上面Runnable任务后，并没有被回收,所以又被分配来执行当前的Runnable（达到了线程复用）
//
//        * */

////        ====================测试关闭线程池中任务=======================================================
////        threadPoolExecutor.shutdown();//会将之前提交的任务（包括正在执行的和阻塞队列的）执行完毕，但不接受新任务
//        List<Runnable> runnables= threadPoolExecutor.shutdownNow();//立即停止所有正在执行的任务，暂停处理正在等待的任务，返回一个等待执行的任务列表
//        System.out.println(runnables.size());//打印没执行完的个数

////====================测试获取子线程结果=====================================
////        虽然是同一个Callable实例，提交两次，就相当于提交了两个任务进去
//        Teacher_6_11_Callable callable = new Teacher_6_11_Callable();
//        Future<String> submit = threadPoolExecutor.submit(callable);
//        System.out.println("第一个返回结果："+submit.get());//注意get方法会抛异常，要接一下
//
//        Future<String> submit2 = threadPoolExecutor.submit(callable);
//        System.out.println("第二个返回结果："+submit2.get());//注意get方法会抛异常，要接一下

////====================测试ScheduledExecutor=====================================
//        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
//        Teacher_6_11_Runnable runnable = new Teacher_6_11_Runnable();
////       /*
////        即在10秒之后再开始执行 当前runnable任务
////        注意：runnable任务只执行一次
////        */
////        scheduledThreadPoolExecutor.schedule(runnable,10000,TimeUnit.MILLISECONDS);//单位毫秒
//
////  ====================第一次是5秒后执行，然后是每隔两秒重复执行（如果任何执行的任务超过了周期，随后的执行会 延时）
////        scheduledThreadPoolExecutor.scheduleAtFixedRate(runnable,5000,2000,TimeUnit.MILLISECONDS);//单位毫秒
////=====================以上次结束为起点，delay时间后开始执行===========================
//        scheduledThreadPoolExecutor.scheduleWithFixedDelay(runnable,5000,1000,TimeUnit.MILLISECONDS);
////    ThreadPoolExecutor
//

//=========Executors的new线程不太安全，因为是最大数量是Integer.MAX_VALUE============================
        Teacher_6_11_Runnable runnable = new Teacher_6_11_Runnable();

//// ============================创建固定数量的线程池===================================
//        ExecutorService executorService = Executors.newFixedThreadPool(3);//只有3个线程来轮流处理提交的任务
//// ============================创建单个任务的 线程池===================================
//        ExecutorService  executorService= Executors.newSingleThreadExecutor();//只有3个线程来轮流处理提交的任务

//        //  ==============创建具有缓存功能的线程池================================
//        ExecutorService executorService = Executors.newCachedThreadPool();

//        创建周期性的线程池
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        executorService.scheduleAtFixedRate(runnable,1000,1000,TimeUnit.MILLISECONDS);

        executorService.execute(runnable);
        executorService.execute(runnable);
        executorService.execute(runnable);
        executorService.execute(runnable);
        executorService.execute(runnable);
        executorService.execute(runnable);
        executorService.execute(runnable);


    }
}
