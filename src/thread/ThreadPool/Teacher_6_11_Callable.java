package thread.ThreadPool;

import java.util.concurrent.Callable;

/**
 * @author QiangQin
 * * @date 2021/7/5
 */
public class Teacher_6_11_Callable  implements Callable<String> {//实现接口，并且返回String
    @Override
    public String call() throws Exception {
        String name=Thread.currentThread().getName();
        System.out.println(name+"子线程正在执行");
        return name;
    }
}
