package collection;

import java.util.HashMap;

/**
 * HashMap源码分析(面试)
 * 1）类的继承关系
 * public class HashMap<K,V> extends AbstractMap<K,V>
 *     implements Map<K,V>, Cloneable, Serializable
 * AbstractMap实现Map接口部分方法，剩余方法在HashMap实现
 * Serializable表示HashMap中所有对象是可被序列化，即可永久的保存在磁盘上
 * Cloneable表示HashMap中所有对象是可被克隆（拷贝）的
 * HashMap允许空值和空键
 * HashMap是非线程安全
 * HashMap元素是无序，不能保证每次获取是一样的顺序  ；LinkedHashMap（插入和删除有序） TreeMap（大小排列有序）
 * （HashTable不允许为空 线程安全（即若多个线程操作某一个集合，最终结果和期望结果是相同的））
 * 2）类的属性
 * static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; 即1左移4位，是  16   默认初始容量      用来给table初始化
 * static final int MAXIMUM_CAPACITY = 1 << 30;//最大容量
 * static final float DEFAULT_LOAD_FACTOR = 0.75f; //加载因子，用于扩容机制
 * static final int TREEIFY_THRESHOLD = 8; //链表转为红黑树的节点个数的阈值
 * static final int UNTREEIFY_THRESHOLD = 6;//红黑树转为链表的节点个数
 * static final int MIN_TREEIFY_CAPACITY = 64;
 * static class Node<K,V> implements Map.Entry<K,V>//如里面的get set方法
 * transient是暂时的意思
 * transient Node<K,V>[] table; //哈希表中的数组即桶（看是桶哪个位置以及此位置的链表上的何处）
 * transient Set<Map.Entry<K,V>> entrySet; //用于迭代器遍历。因为Set继承自collection,Collection提供iterator方法。
 * transient int size;   键值对个数
 * transient int modCount;  集合结构的修改次数（如put remove，而set只修改某个节点，对结构没有修改 ）
 * int threshold;   桶大小的一个阈值  
 * final float loadFactor;
 * 3）类中重要的方法 （构造函数 put remove resize）
 * 构造函数中并未给桶（即table）进行初始化，而是在第一次put中初始化
 *
 * put 源码 实现（简单理解）
 * if ((tab = table) == null || (n = tab.length) == 0)
 *             n = (tab = resize()).length;//resize() 可用来初始化（或扩容）
 *             
 * n - 1 & hash   等价于  hash%表长
 * if ((p = tab[i = (n - 1) & hash]) == null) //当前位置不存在节点，创建一个新节点直接放到该位置   
 *             tab[i] = newNode(hash, key, value, null);
 *
 * else{
 *     //当前位置存在节点 判断key是否重复
 *     
 *     //判断第一个节点的key是否与所要插入的key相等，先判断hash,在进一步判断key
 *     //hash中调用了hashCode方法 ,其能将对象的地址转为一个32位的整型返回（Object类里的235行的toString可以证明）         不同对象的hashCode（因为可能方法重载了）有可能相等
 *     //比较hash相比于使用equals更加高效
 *     if (p.hash == hash &&
 *                 ((k = p.key) == key || (key != null && key.equals(k))))
 *         e = p;
 *         
 * 	   //判断当前节点是否是红黑树节点
 *     else if (p instanceof TreeNode)
 *         //是的话，则按照红黑树插入逻辑实现
 *         e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
 *     else {
 *         for (int binCount = 0; ; ++binCount) {  //binCount桶里的个数
 *             //只有一个节点或跑到末尾了
 *             if ((e = p.next) == null) {
 *                 p.next = newNode(hash, key, value, null);
 *                 //桶里结点个数是否大于阈值
 *                 if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
 *                      treeifyBin(tab, hash);//大于8，小于64，先扩容；大于64再转化为红黑树     HashMap 755行
 *                      break;                //在桶的深度（大）和链的长度（短）之间平衡，保证O（1）  https://blog.csdn.net/wo1901446409/article/details/97388971
 *                 }
 *              //判断e是否是key重复的节点
 *              if (e.hash == hash &&((k = e.key) == key || (key != null && key.equals(k))))
 *                      break;//出去后执行新值覆盖旧值
 *                     
 *              p = e;//往后走一个
 *             }
 *         }
 * }
 * 
 * 
 * resize时机
 * 1）table==null
 * 2)tqble需要扩容时
 * 
 * 过程
 * 参数合法检测
 * 1）table进行扩容
 * 2）table原先结点进行重哈希（红黑树）

a.HashMap的扩容指的是数组的扩容，因为数组的空间是连续的，所以需要数组的扩容即开辟一个更大空间的数组，将老数组上的元素全部转移到新数组上来
b.在HashMap中扩容，先新建一个2倍原数组大小的新数组，然后遍历原数组的每一个位置，如果这个位置存在节点，则将该位置的链表   转移到新数组
c.在jdk1.8中，因为涉及到红黑树， jdk1.8实际上还会用到一个双向链表去维护一个红黑树中的元素，所以jdk1.8在转移某个位置的元素时，首先会判断该节点是否是一个红黑树解节点，然后遍历该红黑树所对应的双向链表，将链表中的节点放到新数组的位置当中
d.最后元素转移完之后，会将新数组的对象赋值给HashMap中table属性，老数组将会被回收
 *   
 */

public class Teacher_1_13_HashMap_Source {
//	HashMap  //看为什么要继承和实现这些接口

}
