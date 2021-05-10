package collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HashMap的插入和获取是无序的、也没有大小顺序
 *
 * LinkedHashMap
 * 维持插入顺序 （1，"a"） (2, "b")（先插的先访问）
 * 维持访问顺序（将最近访问的数据移到链表的尾部    LRU思想    afterNodeAccess(里面处理了Entry的before after属性)）
 * 主要是底层维护了一个双向链表
 * 不能被克隆和序列化（HashMap可以）
 *  static class Entry<K,V> extends HashMap.Node<K,V> {
 *         Entry<K,V> before, after;//
 *         Entry(int hash, K key, V value, Node<K,V> next) {
 *             super(hash, key, value, next);
 *         }
 *     }
 *
 *LinkedHashMap的put类似于HashMap的
 *LinkedKeyIterator里调用nextNode()  源码716行   modcount  fast-fail
 *
 *实现LRU缓存机制
 * Least Recently Used的缩写，即最近最少使用
 * LRU缓存机制类似LinkedHashMap的双向链表 
 * LRU缓存：内存访问时，设计一个缓存（如内存4G,其中1G设置为缓存），大小固定，读取内存数据，首先会去找缓存是否命中，
 * 如果命中，直接返回；
 * 反之，未命中从内存中读取数据，把数据继续添加到缓存当中，
 * 如果缓存已满，删除访问时间最早的数据
 *
 * 1）使用LinkedHashMap实现LRU缓存
 * 需要重写removeEldestEntry方法，该方法：
 * a. 通过  返回结果  去删除访问  时间最早  的数据
 * b. map的size()与给定缓存的最大size比较，如果map.size > MaxSize，则return true
 * c. 参数Map.Entry<K,V>  eldest
 * 
 * 2）自定义实现（笔试）
 * 头结点   尾节点
 * 详见Teacher_1_15_MyLRUCache
 */

class LRUCache<K,V> extends LinkedHashMap<K,V>{
    private int maxSize; //缓存的大小

    //重写
    public LRUCache(int maxSize){
        super(16, 0.75f, true);
        this.maxSize = maxSize;
    }

    //protected
    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest){
        return size() > maxSize;//根据大小，决定是否删除最早结点
    }
}



public class Teacher_1_15_LinkedHashMap {
    public static void main(String[] args) {
    	//使用LinkedHashMap实现LRU缓存
    	//大小为3
        LRUCache<String, String> cache = new LRUCache<>(3);
        cache.put("a", "jfdshjhf");
        //a
        cache.put("b", "fdjhfjf");
        //a - > b
        cache.put("c", "all");
        //a -> b -> c
        cache.get("a");
        //b -> c -> a
        cache.put("d", "tulun");
        //c -> a -> d
        System.out.println(cache);


        //维护插入顺序
//        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
//        map.put(1, "ajhd");
//        map.put(5, "fsd");
//        map.put(12, "ref");
//        map.put(10, "gdfs");
//        map.put(19, "hgf");
//        map.put(21, "hgf");
//
//        Iterator<Integer> iterator = map.keySet().iterator();
//        while(iterator.hasNext()){
//            System.out.println(iterator.next());
//        }

//        //维持访问顺序                                                                                                                                                                                             加载因子：f类型      accessOrder
//        LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>(16, 0.75f, true);
//        map.put(1, "ajhd");
//        map.put(5, "fsd");
//        map.put(12, "ref");
//        map.put(10, "gdfs");
//        map.put(19, "hgf");
//        map.put(21, "hgf");
//
//        map.get(12);
//        map.get(10);
//
//        Iterator<Integer> iterator = map.keySet().iterator();
//        while(iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
    }
}
