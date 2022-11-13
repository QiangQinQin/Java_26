package Book.javaHighConcurrencyProgram;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for(int i=0; i<10000; i++){
            sum += i;
        }
        return sum;
    }
}

public class testCallable {
    
    public static void main(String[] args) {
////方式4： 实现Callable接口，
        Callable<Integer> callableTask = new MyCallable();
        FutureTask<Integer> task = new FutureTask<>(callableTask);// //需要把callable封装成task，才能传给线程 ；   FutureTask是runnable的一个实现，提供get方法
        Thread thread = new Thread(task);//参数只能是runnable类型或其子类
        thread.start();

        //接受子线程执行之后的结果
        try {
            // FutureTask提供get方法，（等待直到有结果传过来，主线程才能往后执行）
            Integer integer = task.get();
            System.out.println("result: "+integer);
        } catch (InterruptedException e) {//自动生成的
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
