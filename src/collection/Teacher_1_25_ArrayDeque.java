package collection;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Deque;
import java.util.concurrent.BlockingQueue;

/* 自己实现ArrayDeque
 * 基于  双端  队列  实现(首尾相连)
*  添加(队尾添加）
*  删除(队头删除）
*  获取队头元素
*  toString()
*  
*  ArrayDeque源码分析
*  属性：
*  86行extends AbstractCollection<E> implements Deque<E>, Cloneable, Serializable
*  42行  可以自动改变数组大小(没有容量限制)，实现Deque接口，非线程安全（可加synchronization）,当栈比stack更快，当队列；比LinkedList更快
*  构造函数（202）
*     构造函数初始化时，new一个elements个数组
*  247  addLast添加一个空元素，会抛出异常    (tail = (tail + 1) & (elements.length - 1)等价于(tail + 1)%elements.length 
*  （先插入，再判满和扩容）
*  155  二倍扩容
*  先断言头和尾相等（因为是非线程安全的，如果不相等，就不往下进行）   先复制原数组head右边的元素到新数组，再复制head左边。然后新数组里head=0,tail等于上次的长度
*  remove->底层调用removefirst-->pollFirst如果返回null,removefirst就抛出异常
*  elenment()底层调用getFirst：如果数组里获取为null,抛出异常
*  610 迭代器
*/
class MyArrayDeque<T> {
	private T[] elements;// 存放元素
	private int head;//队头元素   因为是数组，又知道头和尾，可以算出来有效元素个数是多少
	private int tail;// 队尾（下一个要放的位置点）
	private static final int defaultCapacity = 5;

	public MyArrayDeque() {
		this(defaultCapacity);
	}

	public MyArrayDeque(int capacity) {
		elements = (T[]) new Object[capacity];//强转为泛型T[]
	}
	
	//队尾添加元素
	public void add(T value){
		//判断队满
		if((tail+1) % elements.length == head){//当前只剩一个空位置了
			//扩容  和   映射到新数组的下标位置
			//头部没删除过，尾部到数组末尾。后面追加空间
			if(head==0 ) {
				elements=Arrays.copyOf(elements,2*elements.length);
				head=head+elements.length;
			}
			
			//头部删除过元素，数组末尾插完，又从数组头开始插。此时tail在head左边，需要把扩容的空位放在数组中间；不然重新计算每个元素在新数组的位置太费事
			if(tail<head) {	
				T[] tmp=(T[]) new Object[2*elements.length];;//扩容是新开辟一块大空间，把老的复制过来。
				//                src,srcPos,dest,destPos,复制的length
				System.arraycopy(elements,head,tmp,head+elements.length,elements.length-head);
				System.arraycopy(elements,0,tmp,0,tail);
				head=head+elements.length;
				elements=tmp;
				
			}
			
			
		}
		elements[tail] = value;//插在tail位置
		//tail往后走     0-》1->2->3->4->0
		tail = (tail+1)%elements.length;//指向新的队尾元素
	}
	
	//头部删除
	public T remove(){
		//判空（如：初始时都等于0      删除完所有元素时，头++了和尾相等）
		if(head == tail){
			throw new UnsupportedOperationException( "the queue has been empty");
		}
		T result = elements[head];
		elements[head] = null;//方便垃圾回收
		head = (head+1) % elements.length;//指向新的队头元素
		return result;
	}
	
	// 获取但不删除队头元素
	public T element(){
		if(head == tail){
			throw new UnsupportedOperationException("the queue has been empty");
		}
		return elements[head];
	}
	
	public String toString(){
		//输出当前队列的所有元素
		StringBuilder strs = new StringBuilder();
		for(int i=head; i!=tail; i=(i+1)%elements.length){
			strs.append(elements[i]+" ");
		}
		return strs.toString();
	}
}
public class Teacher_1_25_ArrayDeque {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ArrayDeque
		MyArrayDeque<Integer> deque = new MyArrayDeque<>();
		deque.add(10);
		deque.add(20);
		deque.add(30);
		deque.add(40);//10 20 30 40 _,剩一个空位了，要添加，需先扩容
		System.out.println(deque.remove());
		System.out.println(deque.remove());
		deque.add(50);//_  _  30 40 50
		deque.add(60);//60  _  30 40 50
		                
		deque.add(70);//60 70 30 40 50 _ _ _ _ _,此时head等于tail等于2(下标从零开始)
		              //应该 60 70 _ _ _ _ _ 30 40 50 
		System.out.println(deque);

//		BlockingQueue
	}

}
