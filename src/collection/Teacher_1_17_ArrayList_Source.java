package collection;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

/*
 * ArraysList源码分析
	类的继承关系
	public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess(基于数组，所以), Cloneable, java.io.Serializable
	类的属性
		允许元素为空     非线程安全
	类中的方法（增删改查  ）
		构造函数：
		1)如果初始化List对象调用无参构造函数，当前第一次调用add方法(224行)才 会给底层数组初始化，每次调用add方法
	都会获取一个最大容量  即462行  max(length, size+1), 判断是否需要扩容，扩容以1. 5倍方式进行扩容
		2)如果初始化List对象调用带参构造函数，每次调用add方法都会获取一个最大容量(length, size+1)
	判断是否需要扩容,扩容以1.5倍(259行)方式进行扩容|
		3).以collection集合底下的任意一个集合（list queue set）,传进来后，转为一个ArrayList
		grow以1.5倍扩容
		 
		 add/remove/get/set(447)
	 
	面试题: ArrayList和Vector之间的区别和联系
	ArrayList是非线程安全、Vector是线程安全

 */
public class Teacher_1_17_ArrayList_Source {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		ArrayList
	}

}
