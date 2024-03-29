package collection;


import java.util.*;

/*
List接口
允许重复的有序的
ArrayList
*1、ArrayList使用
*2、ArrayList实现
       基于数组实现，可动态扩展（本页34行，看区别）
*3、ArraysList源码分析
	类的继承关系
	类的属性
	类中的方法（增删改查  构造）
*4、ArrayList中迭代器实现
*5、ArrayList中快速失败机制
*非线程安全
*add/remove/grow modCount++  集合结构的修改次数。若迭代的时候，发现别的线程修改了集合结构，就要抛出异常（864  908行 ）
*
*从modCount(版本号)了解Java集合的fast - fail机制
Java中非线程安全的集合（如ArrayList、 HashMap）， 经常可以在一些修改集合结构的操作（add remove等）中可以看到实例变量
modCount++， 以此来统计集合的修改次数
fast-fail机制: 它是 能够 立刻 报告 任何 可能导致失败的 错误检测机制
表现为：当使用迭代器时，当构造迭代器对象时，起初expectedModCount = modCount ,在迭代的过程会判断
expectedModCount和modCount之间的关系，如果modCount与期望值不符合，就说明在迭代的过程
中集合结构发生了修改（由于并发），便会抛出ConcurrentModificationException的异常，避免对数据同步带来的麻烦
*
*
*ArrayList和数组的区别?
1)数组一旦创建不能修改大小，只能新建重新指向
int[]array = new int[10];
ArrayList是一个“动态”的数组，提供一些能够操作的方法，不用自己写，调用就行
2)数组在内存中连续存储的所以它的索引速度比较快但是插入/删除的时候比较麻烦（要移动）  容易造成内存浪费或者内存溢出（定义的大或者小了）
ArrayList可以解决以上问题
*/
public class Teacher_1_17_ArrayList_use {
	public static void main(String[] args) {
//		set转ArrayList
		TreeSet<Integer> set = new TreeSet<>();
		set.add(7);
		set.add(3);
		set.add(5);
		set.add(7);
		ArrayList<Integer> setList = new ArrayList<>(set);
		System.out.println(setList); //[3,5,7]

		// TODO Auto-generated method stub
		//单线程
		ArrayList<Integer> list = new ArrayList<>();
		list. add(1);
		list.add(2);
		list. add(10);
		Iterator<Integer> itr = list.iterator();
		while (itr.hasNext()){
			Integer next = itr.next();//会判断expectedModCount = modCount ？
			System.out.println(next);
			//list.remove(next);//会修改modCount  抛出异常
			itr.remove();//不会出错，迭代完发现集合已经被删除干净     源码879行，重新设置expectedModCount
		}
		System.out.println(list);
	
//		//多线程
//		ArrayList<Integer> list1= new ArrayList<>();
//		list1.add(1); 
//		list1.add(2);
//		list1.add(10);
//		for(Integer i: list1){
//			new Thread(){
//				@Override
//				public void run() {
//					list1.add(4);//迭代器迭代过程中用另外一个线程继续往里面添加元素   //会抛出异常
//		        }
//		     }.start();
//			System.out.println(i);
//		}
		
		
//		//创建一个空的ArrayList对象， list用来存放String类型的数据
//		//ArrayList<String> list=new ArrayList<>();
//		//注意java.util.List，不要导错包
//		List<String>  list=new ArrayList<>();//向上造型
//		//添加list对象中添加三个元素
//		list.add("aaa");//0号位置
//		list.add("bbb");
//		list.add("ccc");
//		//删除
//		list.remove(2);
//		list.remove("ccc");
//		//显示list中的内容
//		System.out.println(list);//内容能否获取，取决于是否实现toString方法，不然就是返回地址
//		//获取某一个元素的索引位置
//		System.out.println(list.indexOf("aaa"));
//		//判断list是否为空
//		System.out.println(list.isEmpty());
//		//获取list的大小
//		System.out.println(list.size());
//		//检查list是否包含某一个元素
//		System.out.println(list.contains("ere")); //true或false
//		//获取指定位置的元素
//		System.out.println(list.get(1));//返回1位置对应的元素bbb
//		//遍历ArrayList中的元素
//		//（重要）1.foreach   ArrayList实现迭代器接口
//		for(String s:list) {
//			System.out.println(s);
//		}
//		
//		//2.底层基于数组实现
//		for(int i=0;i<list.size();i++) {
//			System.out.println(list.get(i));
//		}
//		
//		//（重要）3.直接使用迭代器不需要了解底层结构（Map  数组  链表  队列...）
//		Iterator<String> iterator=list.iterator();
//		while(iterator.hasNext()) {
//			System.out.println(iterator.next());
//		}


//		//将ArrayList转换为Array（数组）  https://www.cnblogs.com/echoing/p/9379424.html
//		System.out.println(Arrays.toString( list.toArray() )); // list先转为 Object数组，然后 添上[]变成字符串打印
//T[] toArray(T[] a)   将list 转到 指定类型的数组a里,并返回

		List<Integer> list2 = new ArrayList<>();
		for (int i = 0; i< 10; i++) {
			list.add(i);
		}
		System.out.println("List<Integer> 类型对象：");
		System.out.println(list);
		System.out.println();

		System.out.println("Object[] 类型对象：");
		Object[] integerObjs = list.toArray();
		System.out.println(Arrays.toString(integerObjs));
		System.out.println();

		System.out.println("Integer[] 类型对象: ");
		Integer[] integers = list.toArray(new Integer[list.size()]);
		System.out.println(Arrays.toString(integers));
		System.out.println();

		System.out.println("int[] 类型对象：");
		int[] ints = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ints[i] = list.get(i);
		}
		System.out.println(Arrays.toString(ints));

/* subList(int fromIndex, int toIndex)
教程： https://www.runoob.com/java/java-arraylist-sublist.html
 不建议用：https://baijiahao.baidu.com/s?id=1637211558024016793&wfr=spider&for=pc
 */

		ArrayList<String> sites = new ArrayList<>();
		sites.add("Google");
		sites.add("Runoob");
		sites.add("Taobao");
		sites.add("Wiki");
		System.out.println("SubList: " + sites.subList(1, 3)); // 截取 [1,3) 号元素， 但 return的是  SubList！！！是ArrayList的一个视图，对于subList子列表的所有操作会反应到原列表上

/*
removeRange(int fromIndex, int toIndex)
protect方法，不常用，如果要使用需要继承 ArrayList 类   https://www.runoob.com/java/java-arraylist-removerange.html
*/

	}
	
}
