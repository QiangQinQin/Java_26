package collection;

import java.util.Arrays;
import java.util.Iterator;

import org.omg.CORBA.UserException;
/*
 * ArrayList实现
 *add/remove/size/contains/get/set/toString
 
 迭代器
1)定义一个返回迭代器对象的方法
2)调用迭代器对象的hasNext next方法,那么该对象需实现hashext next方法-》该方法定义在Iterator接口里 -》因此，需自定义一 个迭代器的类实现Iterator接口，重写hasNext next方法
 */
//Teacher_1_13_HashMap   263
	
	
class MyArrayList<T>{
	private T[]  elements;//T  泛型;存放数据的容器
	private int size;
	private static  final int  defaultCapactity=5;
	
	public Iterator<T> iterator(){
		return new MyItr();
	}

	class MyItr  implements Iterator{
		int cursor;//定义一个游标     迭代元素返回位置
		//O大写
		@Override
		public boolean hasNext() {
			//判断是否还有下一个可迭代元素
			//elements应在0~size-1
			return cursor<size;
		}

		@Override
		public Object next() {
			// 返回下一个可迭代元素位置
			T result=elements[cursor];
			cursor++;
			return result;
		}
		//源码还多实现了remove方法（846行）
	}
	
	//默认容量
	public MyArrayList() {
		this(defaultCapactity);//调用下面的有参构造
	}
	
	//传容量
	public MyArrayList(int capacity) {
		this.elements = (T[])new Object[capacity];//强转为泛型
	}
	
	
	public  void add(T  value) {
		//判断是否需要扩容
		if(size ==elements.length) {
			elements=Arrays.copyOf(elements,elements.length*2);//返回扩容后的新数组，然后elements指向新数组
		}
		
		//添加元素（索引从0开始，size刚好是下一个空闲的位置）
		elements[size]=value;
		size++;
		
	}
	

	public void checkIndex (int index) {
		if(index<0 || index>size-1) {//size-1
			throw new UnsupportedOperationException("the index is illegal");
		}
	}
	//	public boolean remove(T value) {
	public boolean remove(int index) {
		try{
			checkIndex(index);//抛异常
			//System. arraycopy(原数组，原数组的起始位置，目标数组，目标数组的起始位置，拷贝的长度）
			System. arraycopy(elements,index+1,elements,index,size-1-index);//等价于拷贝部分数组，往前覆盖一位; 画图可理解 (size-1)-index
			elements[size-1]=null;//因为删除后总长度减1，原末尾置空
			size--;
			return true;
		}catch(UnsupportedOperationException e){
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean contains(T target) {
		for(int i=0;i<size;i++) {
			if(target.equals(elements[i])){
				return true;
			}
		}
		return false;
	}
	
	public T get(int index) {
		try{
			checkIndex(index);//抛异常
			
			return elements[index];
		}catch(UnsupportedOperationException e){
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public boolean set(int index,T value) {
		try{
			checkIndex(index);//抛异常
			elements[index]=value;
			return true;
		}catch(UnsupportedOperationException e){
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public String toString() {
		//集合当中的有效元素转换为一-个String字符串返回
		//追加字符串，+是不高效的操作
		StringBuilder strs = new StringBuilder() ;
		for(int i=0; i<size; i++){
			strs.append(elements[i]+" ");
		}
		return strs.toString();
	}
	
	
	
	
}

public class Teacher_1_17_ArrayList_realize {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyArrayList<String>  list=new MyArrayList<>();
		Iterator<String> iterator = list.iterator();//iterator()会返回一个迭代器对象
		
		list.add("a");
		list.add("fda");
		list.add("sfdsaa");
		list.add("f");
		list.add("hgfd");
		list.add("ed");
		
		while(iterator. hasNext()){
			System.out.println(iterator.next());//调用重写的next方法
		}
		
//		System.out.println(list);
//		
//		System.out.println(list.remove( 0));
//		System.out.println(list.remove( 5));
//		System.out.println(list.remove( 10));
//		System.out.println(list);
//		
//		System.out.println(list. contains("a"));
//		System.out.println(list. contains("tulun"));
//		
//		System.out.println(list.get(0));
//		System.out.println(list. get(5));
//		
//		System.out.println(list.set(0,"aaaaa"));
//		System.out.println(list.set(4,"bbbbbbb")) ;
//		System.out.println(list);
//		
//		
	
		
	}
}
