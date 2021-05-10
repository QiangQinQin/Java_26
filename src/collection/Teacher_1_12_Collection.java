package collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;


/* 
 * 集合框架库
 */
public class Teacher_1_12_Collection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Collection<Integer> coll=new ArrayList<>();//Collection是父接口
		Collection<Integer> coll=new LinkedList<>();
		coll.add(10);
		coll.add(20);
		coll.add(30);
		System.out.println(coll.remove(10));
		System.out.println(coll.contains(10));//返回布尔值
		System.out.println(coll.isEmpty());
		System.out.println(coll.size());
		Iterator<Integer> iterator=coll.iterator();//返回迭代器对象
		//利用迭代器对象遍历当前的集合
		//不知道他是数组时，就可以用迭代器将他遍历
		while(iterator.hasNext()){
			//判断当前迭代对象ArrayList集合是否含有下一个可迭代的对象
			Integer next=iterator.next();
			System.out.println(next);
			//iterator.remove(); //即输完就把该next删除
		}
	}

}
