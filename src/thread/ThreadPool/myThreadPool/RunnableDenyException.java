package thread.ThreadPool.myThreadPool;

//自定义异常:主要用于通知任务提交者，任务队列已无法再接 收新的任务。
public class RunnableDenyException extends RuntimeException {
    public RunnableDenyException(String msg){
        super(msg);
    }
}