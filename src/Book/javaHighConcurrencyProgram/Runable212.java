package Book.javaHighConcurrencyProgram;


//runnable接口中提供了一个run抽象方法，所有新创建的线程要执行的业务逻辑在run方法中实现
// ① 实现一个Runnable接口的实现类
public class Runable212 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " 线程开始执行");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " 线程异常终止");
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 线程结束执行");
    }

    public static void main(String[] args) throws InterruptedException {
        //② 创建一个Runnable的任务体实例
        Runable212 runable212 = new Runable212();
        System.out.println(Thread.currentThread().getName() + " 线程开始执行");
        //③创建一个线程实例来执行任务体
        Thread thread = new Thread(runable212);

        //④启动子线程
        thread.start();
        System.out.println(thread.getName());
//        thread.run();
        //线程同步（main线程 等待 子线程执行完成）
        thread.join();
        System.out.println(Thread.currentThread().getName() + " 线程结束执行");

                /*
main 线程开始执行
Thread-0 线程开始执行
Thread-0 线程结束执行
main 线程结束执行
        * */
    }
}