package collection;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * TreeMap
 * Map(接口)（源码121行） 有HashMap(实现类   无序  空间换时间)  和  SortMap(接口)
 * SortMap(接口)里有TreeMap
 *
 * 1)可以按照自然顺序从小到大
 * 2)可以按照给定比较器的顺序
 *
 * HashMap、LinkedHashMap、TreeMap之间的区别和联系
 * HashMap：常用；根据键访问值，速度比较快；允许键为空、值为空；非线程安全（可使用ConcurrentHashMap或Collectoins.synchornizedxxx()）;遍历是无序的
 * Collections 集合(Collection是基本接口)访问的工具类
 * Collectoins.synchornizedxxx()可将非线程安全的list set map转化成线程安全的集合，转化方式和Hashtable一样(synchornized)，没有ConcurrentHashMap高效
 *
 * LinkedHashMap:基于HashMap实现，多了一个双向链表（维护插入  访问顺序），可做缓存机制使用（需要重写removeEldestEntry方法）
 * 
 * TreeMap遍历 有序，可将key-value按照键的某种顺序排序，底层维护了有序的红黑树
 */

class P{
    protected String name;
    protected int score;
    public P(String name, int score){
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return "P{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}

public class Teacher_1_15_TreeMap {
    public static void main(String[] args) {
    	    
    	//重写比较器，测试TreeMap
        Map<P, Integer> map = new TreeMap<>(  new Comparator<P>() {
            @Override
            public int compare(P o1, P o2) { 
                if(o1.score == o2.score){
                    return 0;//分数相等
                }
                return o1.score > o2.score ? 1 : -1;//小在左    大在右
            }
        }  );

        map.put(new P("djhsjh", 80), 1);
        map.put(new P("qoje", 78), 2);
        map.put(new P("adf", 100), 3);

        //按照分数 从小到大 输出
        for(P key: map.keySet()){
            System.out.println(key);
        }

        //依靠比较器逻辑，能够得到2
        System.out.println(map.get(new P("qoje", 78)));
        
        
        
//		  //测试Map是按String 的首字母从小到大的顺序输出
//        Map<String, Integer> map = new TreeMap<>();
//        map.put("apple", 1);
//        map.put("orange", 2);
//        map.put("banana", 3);
//        for(String key: map.keySet()){
//            System.out.println(key);
//        }
    }
}
