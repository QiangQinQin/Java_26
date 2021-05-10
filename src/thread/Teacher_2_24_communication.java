package thread;

/**
 * 线程间的通信
 *   1)方法1:synchronized加锁的线程的 Object 类的wait/notify/notifyAll
 *   2)方法2：ReentrantLock类加锁的线程的Condition类的await/signal/signalAll(开发班)
 *   
 *   
 * wait   调用某个 对象 的wait方法可以让当前线程阻塞，并且当前线程要拥有某个对象的monitor lock
 * notify 调用某个 对象 的notify方法能够唤醒一个正在等待这个对象monitor lock的线程，
 *        如果有多个线程都在等待这个对象的monitor lock，这个方法只能够唤醒  一个
 * notifyAll 调用某个 对象 的notifyAll方法能够唤醒 所有 正在等待这个对象monitor lock的线程
 * （以上三个方法，都需要先获取  要调用方法的这个对象的 monitor lock）
 * 
 * 思考：这三个方法不是Thread类中声明的方法，而是Object类中声明的方法？
 *    每个对象都有一个自己的锁，当前线程需要等待某个对象的锁，如果用线程来操作，这个线程可能需要等待多个线程所释放的锁（不知道当前线程需要等待哪一个线程的锁,操作变复杂）
 *      String lock = new String("test");
        try {
            synchronized (lock){//需要先获取锁
                lock.wait(); //让当前线程陷入阻塞 A线程陷入阻塞 立即释放锁
                System.out.println("the current running thread is "+Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
 * 
 * 
 * wait作用：
 *    使得当前执行代码的线程进行等待，当前线程置入"预执行队列中"，代码在wait()所在的代码处立即停止执行，同时释放当前线程所拥有的锁。
 *    直到接到通知或者被中断为止。wait使用时必须在同步代码块/同步方法中，
 *    如果使用时没有拥有锁则会抛出IllegalMonitorStateException异常
 *
 * notify作用：
 *    notify使用时必须在同步代码块/同步方法中，如果使用时没有拥有锁则会抛出IllegalMonitorStateException异常。
 *    该方法用来通知哪些等待该对象的monitorlock的其他线程。
 *    如果有多个线程等待，则  线程规划器 随机挑选一个wait状态的线程，对该线程发出通知，并且使得它去获取该对象的对象锁。
 *    需要注意的是，notify调用之后  不会立即释放锁 ，呈wait状态的线程不会马上获取该对象的monitor lock，直到执行otify()的线程将程序执行完，
 *    也就是说退出syncrhonized代码块后，当前线程才会 释放锁
 *
 *
 * notifyAll作用： 
 *       唤醒所有，去竞争
 * 阐述两个概念： 
 *    锁池和等待池
 *    
 * 锁池：假设thread A已经拥有了某个对象的锁，如果其他的线程想  要调用这个对象的synchronized方法或者代码块，那么这些线程会进入到该对象的锁池当中
 * 等待池：假设thread A调用某个对象的 wait方法，线程A就会释放该对象的锁，这个线程  进入到 该对象 的等待池中
 *
 *（WPS图  把锁池和等待池加到线程生命周期转化的图里）
 *
 * 课堂练习：
 * 有三个线程，分别A,B,C线程，需要线程交替打印ABCABCABC...打印10次
 *
 * 课后练习：
 * 生产者消费者模型
 *
 * 
 * 
 */

//有三个线程，分别A,B,C线程，需要线程交替打印A B C  ABC  ABC...打印10次
//对象 （含value标志）
class MyObj{
    private int nextValue;

    public void setNextValue(int nextValue) {
        this.nextValue = nextValue;
    }

    public int getNextValue() {
        return nextValue;
    }
}

class TestThread extends Thread{
    private String[] ABC = {"A", "B", "C"};//线程名字   和  数组里该线程编号下标对应的字符  一致
    private int index; //当前线程的      编号
    private MyObj obj; //三线程的  通信对象（唯一）

    //创建对象时，传通信对象MyObj
    public TestThread(int index, MyObj obj){
        this.index = index;
        this.obj = obj;
    }

    @Override
    public void run() {
        for (int i=0; i<10; i++){//10轮（即每个线程打印10次自己名字）
            synchronized (obj){//先获取通信对象的锁
                //循环判断 是否是要  当前线程去执行，不一致就一直阻塞  
                while(obj.getNextValue() != index){
                    try {
                        obj.wait();//会释放锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //index是当前线程的编号  是不会变的
                //index是啥，就永远打对应的啥
                System.out.println(ABC[index]+" ");
                //设置通信对象obj里  下一个线程  的编号  属性，准备激活他
                obj.setNextValue((index+1)%3);
                //通知其他处于wait状态的线程，竞争，value符合条件就可以激活
                obj.notifyAll();//不用notify，是因为有可能一直唤醒的不对，导致无效操作
            }
        }
    }
}
public class Teacher_2_24_communication {
    public static void main(String[] args) {
        //通信对象（唯一）
    	MyObj obj = new MyObj();

        obj.setNextValue(0);//都用obj对象，并且obj对象的nextValue可以变
        //三个线程同时运行，和index不同的就一直阻塞
        //每输出一轮，A B C都要依次输出其线程对应的名字
        new TestThread(0, obj).start();
        new TestThread(1, obj).start();
        new TestThread(2, obj).start();
    }
}


////      B线程唤醒A线程
//        String lock = new String("test");
//        Thread threadA = new Thread("A"){
//            @Override
//            public void run() {
//                try {
//                    synchronized (lock){//需要先获取锁
//                        lock.wait(); //让当前线程陷入阻塞 A线程陷入阻塞  同时 立即释放锁
//							//等B释放锁给他，才能接着执行
//                        System.out.println("the current running thread is "+Thread.currentThread().getName());
//                    }
//                } catch (InterruptedException e) {
//                    //响应中断
//                    System.out.println("the thread has been interrupted and the state is "+Thread.currentThread().isInterrupted());
//                }
//            }
//        };
//        threadA.start();
//       // threadA.interrupt();
//
//        new Thread("B"){
//            @Override
//            public void run() {
//                synchronized (lock){
//                    System.out.println("开始notify time "+System.currentTimeMillis());
//                    lock.notify(); //需要先获取lock的锁，才能执行唤醒A线程的操作 ;  notify()完不会 立即释放锁，只有B执行完了，才释放锁，A拿到,才能接着执行
//                    System.out.println("结束notify time"+System.currentTimeMillis());
//                }
//            }
//        }.start();
