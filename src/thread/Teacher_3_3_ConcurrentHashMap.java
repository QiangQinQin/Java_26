package thread;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ConcurrentHashMap源码分析（线程安全）
 * 1、类的继承关系
 *     extends（继承） AbstractMap<K,V>  implements（实现） ConcurrentMap<K,V>, Serializable {
 * 2、类的属性
 *    TREEIFY_THRESHOLD = 8 树形化时所需  节点 个数
 *    MIN_TREEIFY_CAPACITY = 64;  转为最小红黑树需要 表 的容量
 *    
 *    UNTREEIFY_THRESHOLD  树形化转为链表所需要的结点个数
 *    RESIZE_STAMP_BITS 设置sizeCtl所需要的比特位数
 *    MAX_RESIZERS  最大的线程数（代码注释有解释）
 *    源码603  segments只用来序列化
 *    cellsBusy 旋转锁（自旋锁）
 *    
 *    619 Node结点里 val 和 next 用volatile修饰
 *   
 *    
 *    sizeCtl  table的初始化和扩容需要用到的变量
 *        -1 代表table正在初始化
 *        -N 代表N-1个线程在进行扩容操作
 *        其他情况：
 * 			1）如果table未初始化，表示table初始化的大小（正数）
 * 			2）如果table初始化完成，表示table的容量，默认0.75*table.size（2235行）
 *
 * 
 * 
 * 3、构造函数
 *     做的事只是sizeCtl初始化，为了表示table初始化大小
 *     
 *     
 *     823行默认初始化一张空的表。      初始化操作在第一次 put 完成
 * concurrencyLevel    在jdk1.8的意义改变，并不再代表当前所允许的并发数，
 *                     只是在初始化时用来确定sizeCtl大小（896），因为在jdk1.8的并发控制针对具体的桶而言，所以有多少个桶（segement元素   WPS图）就有多少个并发数
 * 
 *    839 将   给的当前容量  变为  大于等于当前数的   2^n值
 *   int cap = ((initialCapacity >= (MAXIMUM_CAPACITY >>> 1)) ?
                   MAXIMUM_CAPACITY :
                   tableSizeFor(initialCapacity + (initialCapacity >>> 1) + 1));
                   
 * 4、put/get
 *   get方法无需加锁
 *   final V putVal(K key, V value, boolean onlyIfAbsent) {//onlyIfAbsent 为false表示新值替换旧值
 *         //在ConcurrentHashMap中 键和值不能为空
 *         if (key == null || value == null) throw new NullPointerException();
 *         int hash = spread(key.hashCode());
 *         int binCount = 0;//  //当前桶里的节点个数
 *         
 *         for (Node<K,V>[] tab = table;;) {
 *         //无线循环 （因为多线程环境，有可能失败，要不停尝试）
 *             Node<K,V> f; int n, i, fh;
 *             if (tab == null || (n = tab.length) == 0)
 *             //表为空  或者  表长度为0
 *                 //初始化表（需要满足线程安全）
 *                 tab = initTable();//2225，初始化时还要  判断（防止别的线程先他一步给表初始化了）还要用 while（防止切换回来时 不判断直接往下执行）
 *                                     2227 已经在构造函数（823）初始化为正，了不可能为-1。所以肯定是有多个线程在执行，提示cpu让时间片 一定会生效）
 *                                     2228  U是C++写的，访问操作系统底层 （Java是应用层的）
 *                                           U.compareAndSwapInt(this, SIZECTL, sc, -1))//判断this位置是不是SIZECTL,和SC是否相等，如果相等，再把-1给这个位置
 *                                                                                        相当于原值SC,位置是SIZECTL所在的位置,新值-1
 *             else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {//没有用table[i]而是tabAt（可能是想访问操作系统底层的 某个内存中的值）
 *                 //表不为空，该桶位置为空时
 *                 if (casTabAt(tab, i, null,   //casTabAt（线程安全   开销小）： 原位置是i指向的   null是原位置的元素      new Node是即将写到这个位置的元素
 *                              new Node<K,V>(hash, key, value, null)))
 *                     //CAS方式插入一个新的Node到table[i]
 *                     break;                   // no lock when adding to empty bin
 *             }
 *             else if ((fh = f.hash) == MOVED)
 *                 //该节点的hash值为Moved，说明当前节点是ForwardingNode（594行），意味着有其他线程在进行扩容，则一起进行扩容操作
 *                 tab = helpTransfer(tab, f);//扩容要进行结点的转移
 *             else {
 *             //当前位置存在结点（key存在，则新值覆盖旧值，否则尾插法）
 *                 V oldVal = null;
 *                 synchronized (f) {
 *                     //加锁同步，针对  首个节点（粒度小）  进行加锁操作（行锁）
 *                     if (tabAt(tab, i) == f) {
 *                         //找到table表下标为i的节点
 *                         if (fh >= 0) {//  fh是f的哈希值
 *                             //正常节点（即没有 扩容 或者  序列化）
 *                             binCount = 1;//桶位置的结点个数
 *                             for (Node<K,V> e = f;; ++binCount) {//无线循环 相当于自旋
 *                                 K ek;
 *                                 if (e.hash == hash &&
 *                                     ((ek = e.key) == key ||
 *                                      (ek != null && key.equals(ek)))) {
 *                                     oldVal = e.val;
 *                                     if (!onlyIfAbsent)
 *                                         e.val = value;
 *                                     break;
 *                                 }
 *                                 //上面if执行完，接着执行下面  （每一次for循环，把这些都会执行一遍）
 *                                 Node<K,V> pred = e;
 *                                 if ((e = e.next) == null) {
 *                                     //遍历至最后一个节点
 *                                     pred.next = new Node<K,V>(hash, key,//因为上面79行已经加锁了，所以不用再以线程安全方式加锁
 *                                                               value, null);
 *                                     //尾插法插入一个新节点
 *                                     break;
 *                                 }
 *                             }
 *                         }
 *                         else if (f instanceof TreeBin) {
 *                             //判断节点类型是否是红黑树类型
 *                             Node<K,V> p;
 *                             binCount = 2;
 *                             if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
 *                                                            value)) != null) {
 *                                 oldVal = p.val;
 *                                 if (!onlyIfAbsent)
 *                                     p.val = value;
 *                             }
 *                         }
 *                     }
 *                 }
 *                 if (binCount != 0) {
 *                     if (binCount >= TREEIFY_THRESHOLD)//大于8转为树形结构
 *                         treeifyBin(tab, i);
 *                     if (oldVal != null)
 *                         return oldVal;
 *                     break;
 *                 }
 *             }
 *         }
 *         
 *         
 *         //增加binCount容量，检查当前容量是否需要进行扩容
 *         addCount(1L, binCount);//加1操作
 *         return null;
 *     }
 *
 *
 * Unsafe类（C++写的）是用于帮助Java访问操作系统资源的类，如分配内存、释放内存，
 *                 里面全部都是native方法，通过unsafe，Java才具有了底层操作的能力
 *
 *
 * 2228行  compareAndSwapInt用到   CAS操作
 *       CAS是乐观锁技术：
 *          当多个线程尝试CAS同时更新同一个变量时，只有其中一个线程能够成功更新变量的值，
 *          其他线程都失败，失败的线程并不会阻塞，而是被告知这次竞争失败，并且可以再次尝试
 *   CAS操作是原子性操作（一旦开始，要么全成功  要么全失败，不会被打断）
 *   CAS操作逻辑：（WPS图）
 *    如果内存位置V的值与预期原值A相匹配，那么处理器自动更新该位置为新值B。 否则处理器不会做任何操作。
 *
 *
 * 获取某一位置的元素没有直接使用table[index]，而是tabAt(table, index)？？？？？？
 *   在Java内存模型里，每一个线程都有一个自己的工作内存，里面存table的副本，
 *   table本身用volatile，但是不能够保证线程每次拿到的table里面都是最新元素，因为volatile只能够保证table引用可见，（不能保证里面某个位置元素的修改 立即可见）
 *   tabAt底层调用Unsafe.getObjectVolatile可以直接获取指定内存的数据，保证每次拿到的数据都是最新的
 *
 *   public V get(Object key) {
 *         Node<K,V>[] tab; Node<K,V> e, p; int n, eh; K ek;
 *         int h = spread(key.hashCode());
 *         if ((tab = table) != null && (n = tab.length) > 0 &&
 *             (e = tabAt(tab, (n - 1) & h)) != null) 
 *         {
 *             if ((eh = e.hash) == h) {
 *                 if ((ek = e.key) == key || (ek != null && key.equals(ek)))
 *                     return e.val;
 *             }
 *             else if (eh < 0)
 *                 return (p = e.find(h, key)) != null ? p.val : null;//在链表或红黑树里进行查找
 *            
 *             while ((e = e.next) != null) {//桶后面的链表进行查找
 *                 if (e.hash == h &&
 *                     ((ek = e.key) == key || (ek != null && key.equals(ek))))
 *                     return e.val;
 *             }
 *         }
 *         return null;
 *     }
 * get没有加锁，如何保证读到的数据不是脏数据呢？（不怕万一读的时候别人写了）
 *     value和next用volatile修饰，在多线程环境下某一个线程A修饰节点的value或者新增节点对其他线程都是可见的
 *
 *
 */
public class Teacher_3_3_ConcurrentHashMap {
    public static void main(String[] args) {
    	 //ConcurrentHashMap
    }
}
