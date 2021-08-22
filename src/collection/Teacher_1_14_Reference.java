package collection;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.WeakHashMap;


/**

 * //https://www.cnblogs.com/nullzx/p/7406151.html
 * Java中四大引用：（强>软>弱>虚）
 * 强引用
 * //A a是创建一个引用，new A()是开辟一块地址空间存放引用的对象
 * A a = new A(); //a是强引用 
 * 只要是强引用，GC(即垃圾回收)就不会回收被引用的对象（即new A()）
 *
 * 软引用SoftReference
 * 一般用于实现Java对象的缓存，缓存可有 可无，一般将有用但是非必须的对象用   软引用关联
 * 只要是软引用关联的对象，在Java发生  内存溢出   异常之前，会将这些对象列入要 回收的范围（即内存不够才回收），如果回收之后发现内存还是不够，才会抛出OOM异常
 * 用软引用关联起来，使用时直接get,就不用每次创建  。例如 假设map频繁被使用 -》可用  SoftReference关联 -》 用 SoftReference.get()获取该对象，以便调用其方法  
 *
 * 弱引用 WeakReference  （WeakHashMap中就用到）
 * 弱引用是用来一些非必须的对象，比软引用更弱一些
 * 只要是被弱引用关联的对象，只能够生存到下一次垃圾回收之前，一旦发生垃圾回收，无论当前内存是否够用，都会回收掉被弱引用关联的对象
 *
 * 虚引用 PhantomReference
 * 别名幽灵引用 最弱的引用关系，
 * 一个对象是否具有虚引用的存在，完全是不会对其生命周期产生影响，也无法通过虚引用获取一个对象的实例，它存在的唯一目的就是(跟踪对象被垃圾回收的状态)在对象被垃圾回收之后收到一个系统通知
 *
 * 特殊的HashMap：WeakHashMap的  
 * 键 是弱引用对象，只能存活到下一次垃圾回收之前；被回收的键放在 ReferenceQueue里，在HashMap里，会 根据 ReferenceQueue里的值，把键对应的value找到，然后把结点移除，同时让当前的value不要引用原先的对象，这些对象才能在下一次垃圾回收时再被回收掉

 * WeakhashMap与HashMap之间的区别和联系：
 * 1）类的继承关系
 * HashMap中的对象可以被拷贝 克隆  序列到磁盘上，WeakhashMap都不行
 * 2）重要方法    put方法逻辑 （key重复，新值覆盖旧值；头插新节点）/get/remove  null值的处理、插入节点的方式、求散列码的方式、扩容的方式、expungexxx等
 * 
 * hash算法不一样（WeakhashMap源码297行 ）
 * put（447行）不能放键为NULL,系统会自动new一个 NULLKEY，HashMap的key和value都可以为null；key重复，新值覆盖旧值；头插新节点
 * 生成index函数不一样
 * put/get/remove/resize等方法均会使用expungeStaleEntries();遍历ReferenceQueue找到里面的key所对应的结点清除掉
 * 
 * 
 * 3）使用场景
 * WeakHashMap的键是弱引用关联对象；HashMap是强引用关联
 *
 * HashTable与HashMap之间的区别和联系？
 *HashTable是线程安全的一个框架集合，类的继承关系 构造函数  属性  扩容方式   hash算法
 */
class MyArray{
    byte[] bytes = new byte[3*1024*1024];  //即占用3M
}

class Test{

}

public class Teacher_1_14_Reference {
    public static void main(String[] args) {
  //  HashMap
 //   	WeakHashMap
//        //引用队列
//    	  //将软引用对象关联成一个key值，添加到队列里，遍历 队列中每个元素分别找到其在 HashMap里的位置，然后把结点（key,value）完全删除掉
//        ReferenceQueue<MyArray> queue  = new ReferenceQueue<>();
//        //创建一个软引用对象
//        SoftReference<MyArray> softReference = new SoftReference<>(new MyArray(), queue);//当对象被回收时，会直接入到queue里
//        //获取软引用对象（softReference）
//        System.out.println(softReference.get());
//        //判断是否被回收(true  被回收  入到队列)
//        System.out.println(softReference.isEnqueued());
//   	   //   -Xms3M  -Xmx5M   即初始给堆3M空间，最大是5M空间     右键-》 run configurations——》设置虚拟机参数
//        //System.gc();//可模拟垃圾回收
//        byte[] array = new byte[3*1024*1024];
//
//        System.out.println(softReference.get());
//        System.out.println(softReference.isEnqueued());
        
        
//        //  弱引用对象
//        ReferenceQueue<Test> queue  = new ReferenceQueue<>();
//        WeakReference<Test> weakReference = new WeakReference<>(new Test(), queue);
//
//        System.out.println(weakReference.get());
//        System.out.println(weakReference.isEnqueued());
//
//        System.gc();
//
//        System.out.println(weakReference.get());
//        System.out.println(weakReference.isEnqueued());
//        
       
        
//        //虚引用对象
//        ReferenceQueue<Test> queue  = new ReferenceQueue<>();
//        PhantomReference<Test> phantomReference = new PhantomReference<>(new Test(), queue);
//        //进程的执行，需要线程的支持
//  	  //垃圾回收作为一个线程（等CPU分配时间片）支持虚拟机的执行
//    	  //JDK（或虚拟机）里垃圾回收线程是随机启动的，调用  System.gc();可以手动回收
//        System.gc();
//        if(phantomReference.isEnqueued()){
//            System.out.println("该对象已经被回收");
//        }else{
//            System.out.println("该对象已经被回收");
//        }
    }
}
