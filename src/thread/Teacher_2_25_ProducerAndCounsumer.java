package thread;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 生产者消费者模型(面试)
 *    生产者生产商品(数据)到某个地方(队列)，消费者从某个地方(队列)消费商品(数据)
 *    提供put()/take()方法，
 *       如果队列已满，阻塞put直到有空间可用；
 *       如果队列已空，阻塞take直到有数据可用
 *
 * wait/notify/notifyAll
 */

//阻塞队列
class BlockingQueue<E>{
    //有界队列   存取数据
    private final LinkedList<E> queue = new LinkedList<>();
    
    //有界队列最大值
    private int max;
    private static final int DEFAULT_MAX = 10;
    public BlockingQueue(){
        this(DEFAULT_MAX);
    }
    public BlockingQueue(int max){
        this.max = max;
    }

    
    //生产数据
    public void put(E value){
        //生产者生产数据
        // 原因是  多  个生产者对应  多  个消费者，对queue操作
        synchronized(queue){//LinkedList的queue是非线程安全的
            //如果队列已经满，不允许生产者继续生产，生产者阻塞
           while(queue.size() >= max){
                System.out.println(Thread.currentThread().getName() + ":queue is full!");//提示满了，不能再生产
                try {
                    queue.wait();//阻塞 并 释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //反之，生产者生产数据(队尾)
            System.out.println(Thread.currentThread().getName()+": the new data has been produced");
            queue.addLast(value);
            //期望唤醒  消费者 线程，可以消费了
            queue.notifyAll(); //全部线程都唤醒，公平地竞争queue的monitor lock
        }
    }

    //消费数据
    public void take(){
        //消费者消费数据
        synchronized (queue){
            //如果队列已经空， 不 允许消费者消费数据，消费者一直阻塞
            while(queue.isEmpty()){//注意用while
                System.out.println(Thread.currentThread().getName()+": the queue is empty!");
                try {
                      queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //反之，消费者消费数据（队头）
            E result = queue.removeFirst();
            //期望  唤醒所有线程，其中的生产者就可以抢锁生产了
            queue.notifyAll();

            System.out.println(Thread.currentThread().getName()+": the data： "+result+" has been handled");
            //return result;
        }
    }
}

public class Teacher_2_25_ProducerAndCounsumer {
    public static void main(String[] args) {
    	
        BlockingQueue<Integer> queue = new BlockingQueue<>();//默认大小
        
        for(int i=0; i<3; i++){
            //生产者生产数据
            new Thread("Producer"+i){
                @Override
                public void run() {
                    while(true){
                        queue.put((int)(1+Math.random()*1000));//1 到 1000 ；put是自己写的（得锁 且 不满 才能生产）
                    }
                }
            }.start();
        }

        for(int i=0; i<3; i++){
            //消费者消费数据（3个，每个都在不停的消费）
            new Thread("Consumer"+i){
                @Override
                public void run() {
                    while(true){
                        queue.take();//为何有返回值不用也行
                        try {
                              TimeUnit.MILLISECONDS.sleep(100);//因为生产满  消费快，所以消费者等一会（毫秒）     sleep不会释放监视器锁
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }
}
