package collection;

import java.util.*;

/**
 Map集合没有迭代器(iterator()),遍历集合中的元素可以用以下三种方法:
 1.entrySet();
 实现步骤:
     1.1.使用Map集合中的keySet()方法,把Map集合所有的key取出 来存储到一个Set集合中
     1.2.遍历set集合,获取Map中的每一个key–遍历set集合可以使用迭代器或者forEach();
     1.3.通过Map集合中的方法get(key),通过key找到value
 2.keySet();
     实现步骤:
     2.1.使用Map集合中的entrySet()方法,把Map集合中多个Entry对象取出来存储到一个Set集合中
     2.2.遍历set集合,获取每一个Entry对象
     2.3.使用Entry对象中的getKey()和getValue()方法获取键和值
 3.values();
 */
public class Test_Iterator {
    public static void main(String[] args) {
        //Map的三种遍历方法:keySet()(通过键找值);entrySet();values();
        Map<String, String> map = new HashMap<>();
        map.put("张三", "123456");
        map.put("李四", "456321");
        map.put("王五", "741852");
        map.put("赵六", "963321");

        //遍历方式1: 将map中的key的部分, 单独取出, 成为set, 遍历这个set
        /*实现步骤:
            1.使用Map集合中的keySet()方法,把Map集合所有的key取出来存储到一个Set集合中
            2.遍历set集合,获取Map中的每一个key--遍历set集合可以使用迭代器或者forEach();
            3.通过Map集合中的方法get(key),通过key找到value   */
        Set<String> set = map.keySet();
        //使用迭代器Iterator遍历Set集合
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = map.get(key);
            System.out.println(key + "=" + value);
        }
//        //使用forEach();遍历Set集合
//        for (String key : set) {
//            String value = map.get(key);
//            System.out.println(key + "=" + value);
//        }
//        System.out.println();


        //遍历方式2: entrySet 键值对封装成一个整体-Entry, Set<Entry>
        /*实现步骤:
            1.使用Map集合中的entrySet()方法,把Map集合中多个Entry对象取出来存储到一个Set集合中
            2.遍历set集合,获取每一个Entry对象
            3.使用Entry对象中的getKey()和getValue()方法获取键和值   */
        Set< Map.Entry<String, String> > set1 = map.entrySet(); // HashMap源码1005行
        for (Map.Entry<String, String> en : set1) {
            String key = en.getKey();
            String value = en.getValue();
            System.out.println(key + "=" + value);
        }
        System.out.println();



        // 遍历方式3: 所有的值存储到一个Collection集合中返回
        // 弊端: 就只能遍历值, 不能遍历键, 因为map不能反向映射
        Collection<String> values = map.values();
        for (String value : values) {
            System.out.println(value);
        }
    }
}
