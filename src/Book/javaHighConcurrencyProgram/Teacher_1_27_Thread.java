package Book.javaHighConcurrencyProgram;

class MyThread extends Thread{
    @Override
    public void run() {
        //线程执行体
        while(true){ //1
            System.out.println("eat food");//2
        }
    }
}

public class Teacher_1_27_Thread {
    
    public static void main(String[] args) {

//方式1： extends Thread创建子线程
//吃饭  睡觉两个线程没有主次   先后之分  （就是看谁先抢到cpu使用权）
//main  1   2  3       1 2  3
//子              1 2             1  2
//      //创建子线程对象
//      Thread thread = new MyThread(); //1
//      //启动吃饭的thread
//      thread.start(); //start -> run //2
//      //main线程
//      while(true){
//          System.out.println("watch TV"); //3
//      }
//


//方式3： 使用匿名内部类，（Teacher_1_29_life  91行，用lamada创建）
      new Thread(){
      @Override
      public void run() {
          System.out.println("thread-0");
      }
  }.start();
        //优化Lambda
        new Thread(()-> System.out.println(Thread.currentThread().getName()+"已经创建")).start();

    }
}
