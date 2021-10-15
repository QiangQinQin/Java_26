package collection;

import java.util.*;

//哈希表  Map接口的简单使用
public class Teacher_1_12_Map {
	public static void main(String[] args) {
		Map<String,Integer> map=new HashMap<>();//Map父类
		map.put("zhangsan", 10);
		map.put("lisa", 20);
		map.put("wangwu", 50);
		
		
		System.out.println(map.get("lisi"));
		System.out.println(map.remove("lisi"));
		System.out.println(map.size());
		System.out.println(map.isEmpty());
		System.out.println(map.containsKey("tulun"));
		System.out.println(map.containsValue(50));
		
		//返回Map中所包含的键值对(<String,Integer>)所组成的Set集合，每个集合元素都是Map.Entry()对象，其中Entry是Map的内部类
		Set<Map.Entry<String,Integer>> entries=map.entrySet();
		//Set属于Collection,而COllection含有Iterator方法
		Iterator<Map.Entry<String,Integer>> iterator=entries.iterator();
		while(iterator.hasNext())
		{
			Map.Entry<String,Integer> next=iterator.next();
			System.out.println(next.getKey()+"::"+next.getValue());
		}


		// putIfAbsent() 方法会先判断指定的键（key）是否存在。若不存在  则将键/值对插入到 HashMap 中。
		HashMap<Integer, String> sites = new HashMap<>();

		// 往 HashMap 添加一些元素
		sites.put(1, "Google");
		sites.put(2, "Runoob");
		sites.put(3, "Taobao");
		System.out.println("sites HashMap: " + sites); //sites HashMap: {1=Google, 2=Runoob, 3=Taobao}

		// HashMap 不存在该key
		sites.putIfAbsent(4, "Weibo");

		// HashMap 中  存在  Key,所以  不会执行插入操作
		sites.putIfAbsent(2, "Wiki");
		System.out.println("Updated Languages: " + sites);//Updated Languages: {1=Google, 2=Runoob, 3=Taobao, 4=Weibo}


		/*
		Map.getOrDefault(Object key, V defaultValue)方法的作用是：
		  当Map集合中有这个key时，就使用这个key值；
		  如果没有就使用默认值defaultValue。
		* */

		HashMap<String, String> map1 = new HashMap<>();
		map1.put("name", "cookie");
		map1.put("age", "18");
		map1.put("sex", "女");

		String name = map1.getOrDefault("name", "random");
		System.out.println(name);// cookie，map中存在name,获得name对应的value

		String score = map1.getOrDefault("score", "80");
		System.out.println(score);// 80，map中不存在score,使用默认值80
	}

}
