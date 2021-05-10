package thread;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 线程安全的  集合 ：Vector HashTalbe  PriorityBlockingQueue
 * 
 * Vector如何解决线程安全？
 *     通过synchornized（是同步锁，不是独占锁）对方法加锁，不高效
 *     public synchronized boolean add(E e) {
 *         modCount++;
 *         ensureCapacityHelper(elementCount + 1);
 *         elementData[elementCount++] = e;
 *         return true;
 *     }
 *     public synchronized E get(int index) {
 *         if (index >= elementCount)
 *             throw new ArrayIndexOutOfBoundsException(index);
 *
 *         return elementData(index);
 *     }
 *     
 *     不高效，（操作前需要获取实例对象的锁）
 *       add(10) -> index 10    
 *       get(2) -> index 2
 *       get(3)
 *       两个 位置 本身不需要加锁互斥，
 *       但因为synchornized锁了对象整体，thread1 thread2操作不同位置的时候还是需   加锁互斥，变成了串行执行过程（加锁 解锁 导致了资源消耗）
 * 
 * 
 * Hashtable
 * 
 *   HashMap非 线程安全，在多线程并发的情况下add/get可能引入死循环，导致cpu利用率趋近于100%
 *          解决方案有HashTable或者Collections.synchronizedMap(map),这两个解决方案 底层对读写方法进行加锁（即一个线程读写元素 其他线程必须等待，性能低）
 *                 ConcurrentHashMap 可高效实现  线程安全
 *
 * 
 *
 *
 * ConcurrentHashMap新概念：(Wps上有结构图)
 * 1）nextTable 默认为null, 是 扩容时新生成的数组（原来的2倍）
 * 2）sizeCtl 默认为0， 用来控制table的初始化和扩容
 *     -1    代表table正在初始化
 *     -N    代表N-1个线程正在进程扩容操作
 *    其他值：如果table未初始化，则代表table需要初始化的大小
 *         如果table初始化完成，表示table的容量，默认是table大小的0.75倍
 * 3）Node保存了key,value,（key的）hash,next, 其中value和next都是volatile修饰（保可见性）
 * 4）ForwarningNode的hash值为-1，存储nextTable的引用，
 *   只有当table发生扩容时，ForwarningNode才会发挥作用（作为占位符放在table里，表示当前node结点为null或已经移到新的table里了）
 *
 * 预习源码：
 * 1）类的继承关系 注释第一段
 * 2）类的属性
 * 3）构造函数
 * 4）put get (remove)
 */

/*
 * 课堂练习：
              分别使用HashTable,Collection.synchornizedMap(map),ConcurrentHashMap这三个集合，
             循环 100次   创建50个线程   往这三个集合中  同时添加5000个元素，获取其中的元素，
             分析使用不同集合put get的效率    System.currentTimeMiles()
  
   */

class PutThread extends Thread{
    private Map<String, Integer> map;
    private CountDownLatch countDownLatch;
    private String key = this.getId()+"";  //key是线程id   

    public PutThread(Map<String, Integer> map, CountDownLatch countDownLatch){
        this.map = map;
        this.countDownLatch = countDownLatch;
    }

    public void run(){
        for(int i=0; i<5000; i++){ //因为是同一个map（且都是线程安全的map）  所以多线程抢锁，总共放5000个         //value是i
            map.put(key, i);//放到 map里
        }
        countDownLatch.countDown(); //-1
    }
}

class GetThread extends Thread{
    private Map<String, Integer> map;//是GetThread自己的map
    private CountDownLatch countDownLatch;
    private String key = this.getId()+"";//线程的id作为key    并转成String

    public GetThread(Map<String, Integer> map, CountDownLatch countDownLatch){//
        this.map = map;
        this.countDownLatch = countDownLatch;
    }

    public void run(){
        for(int i=0; i<5000; i++){
            map.get(key);//获取key对应的value
        }
        countDownLatch.countDown();//计数器-1   Teacher_2_3_Volatile用过
    }
}



public class Teacher_2_25_CompareThreaSafe {
    private static final int THREADNUM = 50;
    public static long put(Map<String, Integer> map){
        long start = System.currentTimeMillis();
        //起50个线程      总共添加5000个元素
        CountDownLatch countDownLatch = new CountDownLatch(THREADNUM);
        for(int i=0; i<THREADNUM; i++){
            new PutThread(map, countDownLatch).start();
        }
        try {
            countDownLatch.await(); //计数器为0  才打破阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis()-start;
    }

    public static long get(Map<String, Integer> map){
        
        long start = System.currentTimeMillis();
      
        CountDownLatch countDownLatch = new CountDownLatch(THREADNUM);  //初始值，保证子线程需要执行完

        for(int i=0; i<THREADNUM; i++){//起50个线程 
            new GetThread(map, countDownLatch).start();//GetThread里面有计数器减1
        }
        try {
            countDownLatch.await();//主线程阻塞     直到THREADNUM为零   才能执行下面统计时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //时间差 即  持续时间
        return System.currentTimeMillis()-start;
    }

    public static void main(String[] args) {
    	//三种方式  传Map进去，最后里面放  线程号  和  vlaue值
        Map<String, Integer> hashmapSync = Collections.synchronizedMap(new HashMap<String, Integer>());
        Map<String, Integer> hashtable = new Hashtable<>();
        Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();

        //存  取或放的时间
        long totalA = 0L; //Collections.synchronizedMap
        long totalB = 0L; //HashTable
        long totalC = 0L; //ConcurrentHashMap
        for(int i=0; i<100; i++){
            totalA += put(hashmapSync);
            totalB += put(hashtable);
            totalC += put(concurrentMap);
        }
        System.out.println("put time Collections.synchronizedMap = " +totalA+".ms");
        System.out.println("put time Hashtable = " +totalB+".ms");
        System.out.println("put time ConcurrentHashMap = " +totalC+".ms");

        
        totalA = 0L; //Collections.synchronizedMap
        totalB = 0L; //HashTable
        totalC = 0L; //ConcurrentHashMap
        for(int i=0; i<100; i++){//100轮     每一次get是起50个线程，累加100轮各自的时间
            totalA += get(hashmapSync);
            totalB += get(hashtable);//上一条语句执行完了才执行这里
            totalC += get(concurrentMap);
        }
        
        System.out.println("get time Collections.synchronizedMap = " +totalA+".ms");
        System.out.println("get time Hashtable = " +totalB+".ms");
        System.out.println("get time ConcurrentHashMap = " +totalC+".ms");
    }
}
