package collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
		
		//返回Map中所包含的键值对所组成的Set集合，每个集合元素都是Map.Entry()对象，其中Entry是Map的内部类
		Set<Map.Entry<String,Integer>> entries=map.entrySet();
		//Set属于Collection,而COllection含有Iterator方法
		Iterator<Map.Entry<String,Integer>> iterator=entries.iterator();
		while(iterator.hasNext())
		{
			Map.Entry<String,Integer> next=iterator.next();
			System.out.println(next.getKey()+"::"+next.getValue());
		}
		
	}

}
