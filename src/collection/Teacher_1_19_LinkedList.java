package collection;

import java.util.AbstractSequentialList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
/*
 *

ArrayList和LinkedList的区别:
1)数组和链表数据结构的区别
2)结构上的区别：一个基于数组，一个基于 链表实现的
3)使用上的区别：数组添加删除只能使用add\remove方法；链表可以头/尾  添加/删除

 */
/*
 * LinkedList
可以当链表或队列使用
 * 1.使用
链表  在内存上是非连续的地址空间，靠 next(后继)/prev(前驱)访问，复杂度   O(N); 删除/插入是O(1 )，重新绑定关系即可
数组  在内存上都是一块连续的地址空间   ，可以靠index（索引、下标）访问 ，复杂度 O（1; 删除/插入是O(N ),因要移动大量元素
队列：先进先出。有addLast（尾部添加）和removeFirst（头部删除）方法和element(获取队列头部的元素)
 * 2.实现
 * 3.源码
类的继承关系
	比ArrayList多实现了一个Deque<E>接口（双端队列），即可以把LinkedList当队列使用
	继承AbstractSequentialList<E>，而ArrayList继承AbstractList<E>
	和ArrayLis t都允许所有的元素为空
	List底下的LinkedList和ArrayList一样，支持快速失败（即不允许边迭代，边修改）而
	CopyOnWriteArrayList   不支持fast-fail
类的属性
方法（增删改查    构造函数  迭代器）
  构造函数：1.没有界限，非连续的地址空间，不需要容量参数 106行
  		2.可以将collection集合转化为Linkedlist
 add
  linkBefore(要插入的元素，要插入的位置对应的元素);源码159,是将e指向succ
  
 add ->调用 linkLast(尾部添加元素)
 remove ->调用 unlink   

  迭代器(本页65行)在AbstractSequentialList的238行-》AbstractList的298行--》Linklist的866行（自己重写了AbstractList的324行的listIterator(0)）
 迭代器也实现remove()，928行，删除lastReturn即上次返回元素（892行有赋值）
  迭代器953   forEachRemaining  表示以拉姆达表达式的方式进行遍历
 *
 *
 *
 *
 课后:
CopyOnWriteArrayList如何实现不支持fast-fail?
Vector不讲，自己总结 Vector和ArrayList的区别?
LinkedList和ArrayList的区别和联系?
 *
 */
public class Teacher_1_19_LinkedList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//让当前创立的引用指向当前所new的对象
		//创建一个LinkedList（基于双向链表实现的）       可当作链表或队列使用
		LinkedList<String> list = new LinkedList<>();
		//List<String> list = new LinkedList<>();
		//添加元素
		list.add("str"); //添加到尾节点
		list.add("djdh");
		list.add("ijdjd");
		//添加元素到头
		list.addFirst("tulun");
		//添加元素到尾
		list.addLast("123");
		list.addLast("123");
		//删除某一个元素
		list.remove(); //删除头节点 tulun
		//删除指定元素
		list.remove("123");//源码是若重复，只删除第一个
		//删除指定位置元素
		list.remove(0);//删除新的头结点str
		//打印
		System.out.println(list);//因他重写了toString方法； 以后自己写对象时，最好也重写toString方法，以便输出当前对象所包含的内容
//		//迭代器  不用关心底层结构
//		Iterator<String> iterator = list.iterator( ) ;//在AbstractSequentialList的238行
//		while(iterator.hasNext()){
//		System.out.println(iterator.next());
//		}
//		//foreach
//		for(String s:list){
//		System.out.println(s);
//		}
//		//修改指定索引位置的元素
//		list.set(0,"aaaa");
//		//获取
//		System.out.println(list.get(0));
//		//查
//		System.out.println(list.isEmpty());
//		System.out.println(list.size());
//		System.out.println(list.contains("aaaa"));
	}
}
