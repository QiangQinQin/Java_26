package thread.ThreadPool.myThreadPool;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

//继承Thread， 在初始化的时候启动
public class BasicThreadPool extends Thread implements ThreadPool {
    //初始化线程池的数量
    private final int initSize;
    //线程池最大线程数
    private final int maxSize;
    //线程池核心线程数
    private final int coreSize;
    //当前活跃线程的数量
    private int activeCount;
    //创建线程的工厂
    private final ThreadFactory threadFactory;
    //任务队列
    private final RunnableQueue runnableQueue;
    //线程是否被摧毁
    private volatile boolean isShutdown = false;
    //工作队列
    private final Queue<ThreadTask> threadQueue = new ArrayDeque<>();
    //拒绝策略
    private final static DenyPolicy DEFAULT_DENY_POLICY = new DenyPolicy.DiscardDenyPolicy();
    //看下面，自定义线程工厂
    private final static ThreadFactory DEFAULT_THREAD_FACTORY =
            new DefaultThreadFactory();
    private final long keepAliveTime;
    private final TimeUnit timeUnit;


    /*
    构造默认线程池时需要传入的参数：初始线程池的数量，最大线程的数量，核心线程数量，任务队列的最大数
    需进行合法性校验
     */
    public BasicThreadPool(int initSize, int maxSize, int coreSize,
                           int queueSize) {
        this(initSize, maxSize, coreSize, DEFAULT_THREAD_FACTORY,
                queueSize, DEFAULT_DENY_POLICY, 2,
                TimeUnit.SECONDS);
    }

    public BasicThreadPool(int initSize, int maxSize, int coreSize, ThreadFactory threadFactory, int queueSize,
                           DenyPolicy denyPolicy, long keepAliveTime, TimeUnit timeUnit) {
        this.initSize = initSize;
        this.maxSize = maxSize;
        this.coreSize = coreSize;
        this.threadFactory = threadFactory;
        this.runnableQueue = new LinkedRunnableQueue(queueSize, denyPolicy, this);
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.init();//
    }

    //初始化线程池并创建initSize个线程
    private void init() {
        //继承了Thread类，初始化时先启动自己
        start();
        // range返回0  1  2  3 .。。initSize；
        IntStream.range(0, initSize).forEach(i -> newThread());
    }

    //创建新的任务线程并启动
    private void newThread() {
        InternalTask internalTask = new InternalTask(runnableQueue);
        Thread thread = this.threadFactory.creatThread(internalTask);
        ThreadTask threadTask = new ThreadTask(thread, internalTask);
        threadQueue.offer(threadTask);
        this.activeCount++;
        thread.start();
    }

    //从线程池中移除某个线程
    private void removeThread() {
        ThreadTask threadTask = threadQueue.remove();
        threadTask.internalTask.stop();
        this.activeCount--;
    }

    //    提交任务
    @Override
    public void execute(Runnable runnable) {
        if (this.isShutdown) {
            throw new IllegalStateException("这个线程池已经被销毁了");
        }
        this.runnableQueue.offer(runnable);
    }

    @Override
    public void run() {
        //自动维护线程池数量
        while (!isShutdown && !isInterrupted()) {
            try {
                timeUnit.sleep(keepAliveTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                isShutdown = true;
                break;
            }
            synchronized (this) { //以阻止在线程 维护过程中线程池销毁引起的数据不一致问题。
                if (isShutdown) {
                    break;
                }
                //当任务队列大于0，活跃线程小于核心线程的时候，扩容线程
                if (runnableQueue.size() > 0 && activeCount < coreSize) {
                    IntStream.range(initSize, coreSize).forEach(i -> newThread());
                    continue;
                }
                if (runnableQueue.size() > 0 && activeCount < maxSize) {
                    IntStream.range(coreSize, maxSize).forEach(i -> newThread());
                }

//                当前线程池不够繁忙时，则需要回收部分线程， 回收到coreSize数量即可
                if (runnableQueue.size() == 0 && activeCount > coreSize) {
                    IntStream.range(coreSize, activeCount).forEach(i -> removeThread());
                }

            }
        }
    }

    /* 销毁线程池:需要同步机制的保护，主要是 为了防止与线程池本身的维护线程 引起数据冲突
    销毁功能并未返回未被处理的任务， 这样会导致未被处理的任务被丢弃*/
    @Override
    public void shutdown() {
        synchronized (this) {
            if (isShutdown) return;
            isShutdown = true;
            threadQueue.forEach(threadTask -> {
                threadTask.internalTask.stop();
                threadTask.thread.interrupt();
            });
            this.interrupt();
        }
    }

    //这一段方法不是特别重要，就有读者自己写
    @Override
    public int getInitSize() {
        if (isShutdown) throw new IllegalStateException("The thread pool is destroy");
        return this.initSize;
    }

    @Override
    public int getMaxSize() {
        if (isShutdown) throw new IllegalStateException("The thread pool is destroy");
        return this.maxSize;
    }

    @Override
    public int getCoreSize() {
        if (isShutdown) throw new IllegalStateException("The thread pool is destroy");
        return this.coreSize;
    }

    @Override
    public int getActiveCount() {
        synchronized (this) {
            return this.activeCount;
        }
    }

    @Override
    public int getQueueSize() {
        if (isShutdown) throw new IllegalStateException("The thread pool is destroy");
        return runnableQueue.size();
    }

    @Override
    public boolean isShutdown() {
        return this.isShutdown;
    }

    //把线程和internalTask一个组合
    private static class ThreadTask {
        public ThreadTask(Thread thread, InternalTask internalTask) {
            this.thread = thread;
            this.internalTask = internalTask;
        }

        Thread thread;
        InternalTask internalTask;
    }



    private static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger GROUP_COUNTER = new AtomicInteger(1);
        private static final ThreadGroup group = new ThreadGroup("我的线程-" +
                GROUP_COUNTER.getAndDecrement());
        private static final AtomicInteger COUNTER = new AtomicInteger(0);

        @Override
        public Thread creatThread(Runnable runnable) {
            return new Thread(group, runnable, "线程池-" + COUNTER.getAndDecrement());
        }
    }

}