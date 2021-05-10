package collection;

import java.util.*;

/**
 * Hashset
 * set接口的实现；   HashSet无序并且不允许重复的元素（其实把值存到key里面，value是无效值---在源码99行   219行）
 * 也是基于基于哈希表实现，和哈希表一样也是无序的
 * 不过HashMap的key不允许重复，value可以重复
 *
 ** LinkedHashSet
 * set接口的实现  基于哈希表和链表，其中 哈希表用来保证元素的唯一性 而 链表保证元素的插入顺序（相当于队列，先进先出）
 * 和LinkedHashMap的node类型一样 ，比hashSet节点类型多了 before after引用   通过这两个引用决定插入和删除的先后顺序

 * TreeSet
 * set接口的实现  ;基于哈希表和红黑树 ,而哈希表用来保证元素的唯一性、 红黑树保证元素的有序。有序可以是：
 * 1）自然顺序
 * 2）比较器接口所定义的顺序
 *（TreeMap讲过这个顺序，类似）
 *
 *
 * 面试：HashSet LinkedHashSet TreeSet 异同：
 * 相同点
 * 1）基于set接口的实现，都是不允许重复的  (可以展开讲Set接口特性)
 * 2）底层都会用到哈希表，把当前元素作为哈希表中的key存储 (可以展开讲HashMap结构、如何作为key值存储)
 * 3）都不是线程安全的。可使用 Collections.synchornizedSet()方法保证线程安全 (可以展开讲为何线程不安全)
 *
 * 不同点：
 * 1）HashSet只存储元素，LinkedHashSet（保证插入顺序，先进先出）,TreeSet（保证有序）还能保证顺序
 * 2）LinkedHashSet用于需要保证FIFO场景
 *    TreeSet用于用于排序场景（可以自定义排序逻辑）
 * 3）HashSet LinkedHashSet允许添加空值 ，而TreeSet添加null值会有NullPoniterExceptiion
 * 4) 性能，HashSet最快 （因只存）> LinkedHashSet（保插入顺序） > TreeSet（要排序）
 * 可以将一定数量的对象添加到 HashSet LinkedHashSet TreeSet，然后获取到来测试性能
 */


public class Teacher_1_20_HashSet {
    public static void main(String[] args) {
        System.out.println("HashSet: =========");
        HashSet<String> set = new HashSet<>();///定义set集合
        //添加元素
        System.out.println(set.add("djhjdh"));
        System.out.println(set.add("abc"));
        System.out.println(set.add("tulun"));
        //添加重复元素
        System.out.println(set.add("abc"));
        System.out.println(set.add("tulun"));//false 表示添加失败，并不是抛出异常
        ////不可以添加元素到指定位置,因为底层基于HashMap实现，用的是key value键值对，利用key自动生成index,所以不可以决定插入到哪个位置
        //set.add(0, "sjhhjs"); //Hashset集合添加元素只有一个方法；ArrayList和LinkedList是可以添加元素到指定位置的

        //获取元素
        //set集合没有获取元素的方法

        //获取元素的个数
        System.out.println(set.size()); //虽然HashSet没有set属性，但是底层使用hashmap去存储元素，hashmap有size属性

        //删除元素
        System.out.println(set.remove("abc"));//其实传的是底层HashMap的Key值
        System.out.println(set.remove("djhhd"));//元素不存在，返回为false 

        //修改元素
        //set集合没有修改元素的方法

        //判空
        System.out.println(set.isEmpty());
        //是否包含     最最常用
        System.out.println(set.contains("abc"));
        //遍历元素，集合框架库，所有的集合都会实现迭代器 
        Iterator<String> iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

        System.out.println("==================");
        for(String s: set){
            System.out.println(s);
        }


    }
}
