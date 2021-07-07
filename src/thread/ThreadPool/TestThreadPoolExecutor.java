package thread.ThreadPool;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author QiangQin
 * * @date 2021/7/6
 */
public class TestThreadPoolExecutor {
    public static void main(String[] args) {
        int COUNT_BITS = Integer.SIZE - 3;//为29，用Integer的高3位，表示线程池的状态
        final int CAPACITY = (1 << COUNT_BITS - 1);//为0001111...1即可表示0 到 2^29-1。
        System.out.println(Integer.toBinaryString(CAPACITY));

//        ScheduledThreadPoolExecutor
    }
}
