package collection;
import java.util.Iterator;

/**
 * HashMap的实现
 * 自己实现一个哈希表，hash算法类比HashMap中hash算法，解决哈希冲突采用链地址法，
 * 实现put(k key,V value), get(K key), remove(K Key),resize()等方法
 *
 * 回顾：
 * 基于哈希表（或散列表），具体来讲jdk1.8之前采用数组+链表（或开发地址法等）的结构解决哈希冲突，jdk1.8开始 采用数组+链表+红黑树
 * 期望：key->f(key)->index O(1)
 * 链过于长时：key->f(key)->index->LinkedList的时间复杂度接近 O(N)  
 * 对此引入-》红黑树  O(log2 N) （在jdk1.8  链表长》=8时，自动转化为红黑树）
 * 
 * 以put方法为例
 * 自定义put方法
 * 1）自定义key-> 通过hash(key)得到 散列码 -> 通过hash(key) & table.length-1 得到index
 * 2）table[index] == null？ 是否存在节点
 * 3）不存在     直接将key-value键值对封装成为一个Node 直接放到index位置
 * 4）若存在，则注意  key不允许重复。具体如下
 * 5）存在 key 重复       考虑新值去覆盖旧值
 * 6）存在 key不重复     尾插法 将key-value键值对封装成为一个Node 插入新节点
 
 
 HashMap迭代器实现
1）由于哈希表数据分布是不连续的，所以在迭代器初始化的过程中需要找到第一个非空的位置点，避免无效的迭代
2）当迭代器的游标到达某一个桶链表的末尾，迭代器的游标需要跳转到下一个非空的位置点


HashMap面试题整理:
1)JDK1.7与JDK1.8HashMap有什么区别和联系
2)用过HashMap没?说说HashMap的结构（底层数据结构+ put方法描述)
3)说说HashMap的扩容过程
4) HashMap中可以使用自定义类型作为其key和value吗?
5) HashMap中table .length为什么需要是2的幂次方
6) HashMap与HashTable的区别和联系
7) HashMap、LinkedHashMap、TreeMap之间的区别和联系?
8) HashMap与WeakHashMap的区别和联系
9) WeakHashMap中涉及到的强弱软虚四种引用
10) HashMap是线程安全的吗?引入HashTable和ConcurrentHashMap(后面讲)

 *
 */

//<K> the type of keys maintained by this map
//<V> the type of mapped values
class MyHashMap<K,V> {
	//属性
    private int size; //有效节点个数     表示map中有多少个键值对
    private Node<K, V>[]  table;//数组 引用    《==》HashMap底层的桶
    private static final int initalCapacity=16;

    //结点
    class Node<K, V> { 
    	protected K key;
    	protected V value;
        private Node<K, V> next;//相当于链表
        private int hash;//值的哈希地址

        public Node(int hash, K key, V value) {//结点构造函数
            this.hash = hash;
            this.key = key;
            this.value = value;
        }
    }

    //MyHashMap构造函数
    public MyHashMap() {
        this(initalCapacity);//数组默认初始容量
    }
    public MyHashMap(int capacity) {
        table = new Node[capacity];
    }

    
    //直接复制HasMap源码337行里的
    public int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    //自己简单实现的，没有用到红黑树
    public void put(K key, V value){
        //key->Hash值->index
        int hash = hash(key);//散列码
        int index = table.length-1 & hash;//按位与  达到取余的效果

        //当前数组index位置 不存在节点
        Node<K, V> firstNode = table[index];//当前位置第一个结点
        if(firstNode == null){
            //table[index]位置不存在节点 直接插入
            table[index] = new Node(hash, key, value);
            size++;
            return;//退出
        }
        
        //若结点存在要保证key不重复的
        if(firstNode.key.equals(key)){
            firstNode.value = value;//key同，值覆盖
        }else{
            //遍历当前 链表
            Node<K, V> tmp = firstNode;
            //找到最后一个结点 或者 找到一个与key相等的结点
            while(tmp.next != null && !tmp.key.equals(key)){
                tmp = tmp.next;
            }
            //对应上面循环退出的两种情况
            if(tmp.next == null){
                //表示最后一个节点之前 的所有节点都不包含key
                if(tmp.key.equals(key)){
                    //最后一个节点的key与当前所要插入的key是否相等，考虑新值覆盖旧值
                    tmp.value = value;
                }else{
                    //如果不存在，new Node，尾插法 插入链表当中
                    tmp.next = new Node(hash, key, value);
                    size++;//有效个数
                }
            }else{
                //如果存在，考虑新值覆盖旧值
                tmp.value = value;
            }
        }
    }
    
    
    //获取key所对应的value
    public V get(K key){ 
        //找位置：key->index
        int hash = hash(key);
        int index = table.length-1 & hash;
        
        //在index位置的所有节点中找与当前key相等的key
        Node<K,V> firstNode = table[index];
        //当前位置点是否存在节点  不存在
        if(firstNode == null){
            return null;
        }
        //判断第一个节点
        if(firstNode.key.equals(key)){
              return firstNode.value;
        }else{
            //遍历当前位置点的链表进行判断
            Node<K,V> tmp = firstNode.next;
            while(tmp != null && !tmp.key.equals(key)){
                tmp = tmp.next;
            }
            if(tmp == null){
                return null;
            }else{
                return tmp.value;
            }
        }
    }

    
    
    public boolean remove(K key){
        //key->index
        //当前位置中寻找当前key所对应的节点
        int hash = hash(key);
        int index = table.length-1 & hash;
        Node<K,V> firstNode = table[index];
        if(firstNode == null){
            //表示table桶中的该位置不存在节点
            return false;
        }
        //删除的是第一个节点
        if(firstNode.key.equals(key)){
            table[index] = firstNode.next;
            size--;
            return true;
        }
        //相当于在链表中删除 中间 某一个节点
        while(firstNode.next != null){
            if(firstNode.next.key.equals(key)){
                //firstNode.next是所要删除的节点
                //firstNode是它的前一个节点
                //firstNode.next.next是它的后一个节点
                firstNode.next = firstNode.next.next;
                size--;
                return true;
            }else{
                firstNode = firstNode.next;
            }
        }
        return false;
    }

    
    //HashMap的扩容
    public void resize(){  
        //table进行扩容 2倍的方式 扩容数组
        Node<K, V>[] newTable = new Node[table.length*2];
        //index  -> table.length-1 & hash
        //对原 先哈希表中  每一个 有效节点进行  重哈希
        for(int i=0; i<table.length; i++){
            rehash(i, newTable);
        }
        this.table = newTable;
    }
    public void rehash(int index, Node<K,V>[] newTable){
        //暂存老index对应的结点
    	Node<K,V> currentNode = table[index];
        if(currentNode == null){
            return;
        }
        
        //每调用一次rehash都要重置一次
        //用于标记index位置的链上的头尾，用于链接结点
        Node<K,V> lowHead = null; //低位的头
        Node<K,V> lowTail = null;//低位的尾
        Node<K,V> highHead = null;//高位的头
        Node<K,V> highTail = null;//高位的尾
        
        //index对应 的currentNode结点若为空，不操作
        //不空，则遍历index位置的  所有节点并尾插
        while(currentNode != null){
        	//计算扩容后的index值，看wps示意图
            int newIndex = hash(currentNode.key) & (newTable.length-1);
            //新老index一样，即在原位置 （低  位位置）
            if(newIndex == index){
                //当前节点链到lowTail之后
                if(lowHead == null){//之前在该处没存
                    lowHead = currentNode;
                    lowTail = currentNode;
                }else{//链不空，直接找末尾
                    lowTail.next = currentNode;
                    lowTail = lowTail.next;//指向新尾
                }
            }else{
            //新老index不同，即跑到原位置 + 扩容前长度 （高  位位置）
                //当前节点链到highTail之后
                if(highHead == null){
                    highHead = currentNode;
                    highTail = currentNode;
                }else{
                    highTail.next = currentNode;
                    highTail = highTail.next;
                }
            }
            
            //index位置所在链上的下一个
            currentNode = currentNode.next; 
        }
        
        
        //因为长度扩容为2倍
        //index要么在原位置 （低  位位置）
        if(lowTail != null){//tail不为空是lowHead肯定不为空
            lowTail.next = null;//因为上面没处理.next
            newTable[index] = lowHead;//lowHead初始为空,若进入while循环就有值了。需将链的头结点和index低位置关联，就能从顺着链找了
        }
        //要么跑到原位置 + 扩容前长度 （高  位位置）
        if(highTail != null){
            highTail.next = null;
            newTable[index + table.length] = highHead;
        }
    }
    
    
    
    //HashMap源码有迭代器方法，Entry方法（1013行）
    //类名iterator()；返回值为itr类的对象，类型Iterator<  Node<K,V>  >
    public Iterator<Node<K,V>> iterator(){
        return new Itr();
    }

   //实现 Iterator<E>接口
    class Itr implements Iterator<Node<K,V>> {
        private int cursor; //游标  指向当前遍历到的元素所在位置点
        private Node<K,V> currentNode; //具体的元素节点
        private Node<K,V> nextNode; //链表   下一个元素节点

        public Itr(){
            //由于哈希表数据分布是不连续的，所以在迭代器初始化的过程中需要找到第一个非空的位置点，避免无效的迭代
            //currentIndex  currentNode  nextNode 初始化
            if(MyHashMap.this.size <= 0){
                return;
            }

            for(int i=0; i<table.length; i++){
                if(table[i] != null){
                    cursor=i;
                    nextNode = table[i];
                    return;
                }
            }
        }

        
        //重写，下面331行调用
        @Override
        public boolean hasNext() {//下一个非空结点
            return nextNode != null;
        }

        //返回下一个结点（Node<K,V>类型），下面334行调用
        @Override
        public Node<K,V> next() {
            //暂时保存需要返回的元素节点
            currentNode = nextNode;

            //更新下一次要用的nextNode
            nextNode = nextNode.next;
            //迭代器的游标到达某一个桶链表的末尾
            if(nextNode == null){
                //迭代器的游标需要跳转到下一个非空的位置点
                for(int j=cursor+1; j<table.length; j++){
                    if(table[j] != null){
                        //table[j]表示该位置的第一个元素
                        cursor = j;
                        nextNode = table[j];
                        break;
                    }
                    
                }
                //如果仍找不到非空位置，289行hasNext()就知道桶找完了 
             }
            return currentNode;
        }
    }
}

public class Teacher_1_13_HashMap {
    public static void main(String[] args) {
    	 MyHashMap<Integer, String> map = new MyHashMap<>(16);
         map.put(1, "dksjfkjd");
         map.put(17, "jd");
         map.put(43, "tree");
         map.put(21, "hgf");
         map.put(67, "uytr");
         map.put(7, "iiuyt");
         map.put(19, "ygv");
         map.put(25, "rdfc");
         map.put(33, "edx");
         map.put(77, "asdf");

         Iterator< MyHashMap<Integer, String>.Node<Integer, String> > itr = map.iterator();//MyHashMap的iterator()方法返回Iterator<Node<K,V>>类型
         while(itr.hasNext()){//iterator()里new了itr类，该类有hasNext方法
             MyHashMap<Integer, String>.Node<Integer, String> next = itr.next();
             System.out.println(next.key + ":: "+next.value);
         }
//按照哈希后的顺序输出        
//         1:: dksjfkjd
//         17:: jd
//         33:: edx
//         67:: uytr
//         19:: ygv
//         21:: hgf
//         7:: iiuyt
//         25:: rdfc
//         43:: tree
//         77:: asdf

    }
}
