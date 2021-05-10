package collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.TreeSet;

/*
* LinkedHashSet
* set接口的实现  基于哈希表和链表，其中 哈希表用来保证元素的唯一性 而 链表保证元素的插入顺序
* 和LinkedHashMap的node类型一样 ，比hashSet节点类型多了 before after引用   通过这两个引用决定插入和删除的先后顺序
*/
public class Teacher_1_20_LinkedHashSet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        System.out.println("LinkedHashSet: =========");

        //LinkedHashSet使用
        LinkedHashSet<String> linkedSet = new LinkedHashSet<>();//调用HashSet的161行构造器，底层调用LinkedHashMap<>，该类里多了一个before  after属性
        //添加元素
        linkedSet.add("图论");//源码在HashSet的219行
        linkedSet.add("电话");
        linkedSet.add("中国");

        //遍历元素（按照插入顺序输出）
        Iterator<String> itr = linkedSet.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
        System.out.println("===Foreach遍历=======");
        for(String s: linkedSet){// 元素类型    临时变量名  ：要遍历的集合
            System.out.println(s);
        }
        //删除元素
        linkedSet.remove("电话");
        System.out.println(linkedSet);
        
        //判空  包含等方法就不试验了
	}

}
