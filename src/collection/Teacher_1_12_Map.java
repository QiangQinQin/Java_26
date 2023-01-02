package collection;

import java.util.*;

//哈希表  Map接口的简单使用
public class Teacher_1_12_Map {
	public static void main(String[] args) {
		Map<String,Integer> map=new HashMap<>();//Map父类
		map.put("zhangsan", 10);
		map.put("yangsan", 10);
		map.put("lisa", 20);
		map.put("wangwu", 50);
		
		
		System.out.println(map.get("lisi"));
		System.out.println(map.remove("lisi"));
		System.out.println(map.size());
		System.out.println(map.isEmpty());
		System.out.println(map.containsKey("tulun"));
		System.out.println(map.containsValue(50));

		/*  entrySet()返回Map中所包含的键值对(<String,Integer>)所组成的Set集合，每个集合元素都是Map.Entry()对象，其中Entry是Map的内部类
		   如返回[a=2, c=3, d=3] ，其中 2是Map.Entry.getKey()拿到的 a是Map.Entry.getValue()
		    */
		Set<Map.Entry<String,Integer>> entries=map.entrySet();
		System.out.println("keySet"+map.keySet()); // 只返回Map集合中所有的key


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



		// HashMap<Integer, List<Integer>>
		// https://blog.csdn.net/misayaaaaa/article/details/128042802?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522167195530316800192279961%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fblog.%2522%257D&request_id=167195530316800192279961&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~blog~first_rank_ecpm_v1~rank_v31_ecpm-3-128042802-null-null.blog_rank_default&utm_term=%E5%B0%84%E5%87%BB%E6%AF%94%E8%B5%9B%E6%88%90%E7%BB%A9&spm=1018.2226.3001.4450

//		//  写法1
//		HashMap<Integer, List<Integer>> map = new HashMap<>();// id 对应 所有成绩
//		for (int i = 0; i < 5; i++) {
//			if(map.containsKey(ids[i])){
//				map.get(ids[i]).add(scores[i]);
//				map.put(ids[i], map.get(ids[i]));
//			}else{
//				ArrayList<Integer> arrayList = new ArrayList<>();
//				arrayList.add(scores[i]);
//				map.put(ids[i],arrayList);
//			}
//		}
//
////  写法2
//		// 存储选手成绩信息
//		HashMap<Integer, List<Integer>> map = new HashMap<>();
//		for (int i = 0; i < n; i++) {
//			List<Integer> list = map.getOrDefault(ids.get(i), new LinkedList<>());// ！！！有就get，没有就new一个
//			list.add(scores.get(i));
//			map.put(ids.get(i), list);
//		}
	}

}
