package collection;

import java.util.HashMap;

import javax.swing.text.html.HTMLDocument.Iterator;

/**
 * 自定义实现LRU缓存机制
 *
 * 存数据的容器：HashMap
 * 维护双向链表，确保 数据按照访问时间存储
 * 
 * 让自己完善
 * 
 */

class MyLRUCache<K,V>{

    private Node<K,V> head = new Node<>();//头结点    无参构造
    private Node<K,V> tail = new Node<>();//尾
    private final HashMap<Integer,Node> hashMap = new HashMap<>();//new了，作为容器
    private int size;  //实际大小
    private int capacity;//最大容量

    class Node<K, V>{
        private K key;
        private V value;
        private Node pre;//双向链表
        private Node next;

        //构造器
        public Node(){

        }
        public Node(K key, V value){
            this.key = key;
            this.value = value;
        }
    }
    
    //初始化
    public MyLRUCache(int capacity){
        head.next = tail;//先指向无效的结点，专门指向 头和尾
        tail.pre = head;
        this.capacity = capacity;
        size = 0;
    }
    
    //添加某个节点（尾插法）
    private void add(Node node){
    	//put里已判断key是否存在，这里只管添加
    	//空表
    	if(tail.pre==head) {
    		head.next=node;//由head指向node
    		//node为首结点，前面再无，故不用设置node.pre
    		
    		tail.pre=node;

    	}else {
    		//Tail之后添加
    		node.pre=tail.pre;
    		tail.pre.next=node;
    		
    		tail.pre=node;
    	}	
    }

    //删除某个节点
    private void remove(Node node){
    	//非空表
    	if(head.next!=tail) {//比地址
    		//头结点
    		if(node==head.next) {
        		head.next=head.next.next; //新头结点
        		//head.next.pre=null; //新头结点不需要设置pre
        	}else if(node==tail.pre) {
        		//尾结点
        		tail.pre=tail.pre.pre;//新尾结点
        		//tail.pre.pre.next=null;
        	}else {
        		//其他
        		node.pre.next=node.next;//要删除结点的前面结点的next指针往后指一个
            	node.next.pre=node.pre;//要删除结点的后面结点的 pre指针往前指一个
        	}   
    	}	
    }

    //添加key-value键值对
    public void put(K key, V value){
        Node node = hashMap.get(key);//hashMap里已存有相同key的结点
        if(node != null){
            node.value = value;
            //从链表中删除node节点
            remove(node);
            //再将node节点尾插法重新插入链表（因为它刚被访问）
            add(node);
            
        }else{
            if(size < capacity){
                size++;
            }else{
                //超容量(1个)，删除缓存中最近最少使用的节点head.next
            	//注意先从HashMap里删除，不然remove会改变head.next值
            	hashMap.remove(head.next.key);//删除指定key的结点
            	remove(head.next);//head.next变为指向head.next.next了
 	
            }
            //将newNode尾插法插入链表
            Node newNode = new Node(key, value);//将输入的key value包装成node
            hashMap.put((Integer) key, newNode);//存到hashMap,键为随机值，值为node结点        (int)(Math.random()*100)
            add(newNode);
    
        }
    }

    //获取value（返回8表示成功）（将访问的元素移到链表尾）
    public int get(K key){
        Node node = hashMap.get(key);
        if(node == null){
            return -1;
        }
        //删除node
        hashMap.remove(node.key);//删除指定key的结点
        remove(node);
        //尾插法重新插入
        add(node);
        hashMap.put((Integer) key, node);
        //等价于个数没变
        
        return 8;
    }
    
    
    public void show() {
    	Node<K,V> temp = head;
    	while(temp!=tail.pre){//tail.pre指向的是末尾节点
    		temp=temp.next;
    		System.out.print(temp.key+"  ");
    	}
    	System.out.println();
    }
    
}
public class Teacher_1_15_MyLRUCache {
    public static void main(String[] args) {

      //维护插入顺序
      MyLRUCache<Integer, String> cache = new MyLRUCache<>(3);
      cache.put(1, "ajhd");
      cache.put(5, "fsd");
      cache.put(12, "ref");
      cache.show();//应该1 5  12
      System.out.println(cache.get(5));//成功则返回8
      cache.show(); //应该1 12 5
      cache.put(10, "gdfs");
      cache.show();//应该12 5 10
      //如何打印双向链表里的顺序
      System.out.println(cache.get(1));
     

    }
}
