package thread.ThreadPool.myThreadPool;

//任务队列，主要用于缓存提交到线程池中的任务
public interface RunnableQueue {
    //新线程进来时,提交任务到缓存队列
    void offer(Runnable runnable);
    //取出线程
    Runnable take();
    //获取队列中线程的数量
    int size();
}