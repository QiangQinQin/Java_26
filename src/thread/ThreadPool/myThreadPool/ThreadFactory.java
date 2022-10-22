package thread.ThreadPool.myThreadPool;

@FunctionalInterface
/*创建线程的工厂,
 以便于个 性化地定制Thread，比如Thread应该被加到哪个Group 中，优先级、线程名字以及是否为守护线程等，
 */
public interface ThreadFactory {
    Thread creatThread(Runnable runnable);
}