package collection;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Java里
 * Stack 栈类(已经不推荐使用，用Deque接口的具体实现类ArrayDeque代替)
 * 
 * Queue接口-》需要使用队列首选ArrayDeque，次选择LinkedList
 *
 * add(E)队尾添加元素
 * offer(E)* 
 * remove()移除队列头部元素
 * poll() *      （移除元素，若队列为空，poll不会返回异常，remove会返回异常）
 * element() 获取头部元素
 * peek() *
 * 
 * 
 * Deque接口     （double ended queue）双端队列（两端都可以插入删除）
 * 
 *   Queue（栈）                             Deque
 *  add(E e)     对应           addLast(E)：             队尾插入元素，失败抛出异常
 * offer(E e)          offerLast(E)    队尾插入元素，失败返回false
 *  remove             removeFirst()   获取并删除队头元素，失败抛出异常
 *  poll                 pollFirst()   获取并删除队头元素，失败返回null
 *  element             getFirst()     获取并不删除队头元素，失败抛出异常
 *  peek                peekFirst      获取并不删除队头元素，失败返回null
 * 
 *  Stack（队列）                         Deque
 *  push(e)   对应                       addFirst：返回void
 *                      offerFirst:返回布尔类型
 *  pop()               removeFirst
 *                      pollFirast
 *  peek()              getFirst
 *                      peekFirst
 *                      
 *ArayDeque和LinkedList是Dequeu两个通用实现。更推荐使用ArrayDeque作为栈和队列使用，LinkedList一般只做队列使用
 *分别向ArayDeque和LinkedList添加删除10000组数据，ArrayDeque快的多
 *
 *ArrayDeque
* ArrayDeque 是Deque接口的一个可变大小数组的实现，我们通常称之为  双端  队列
* 特点:
* 1)队列没有容量限制，可以根据需要自动扩容
* 2)该队列不是线程安全的
* 3)不允许存储null
* 4)作为队列使用比LinkedList快，作为栈使用比Stack快
* 5)使用iterator方法返回的迭代器
 */ 
public class Teacher_1_25_Queue {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//1、创建一个ArrayDeque对象
		Deque<String> queue = new ArrayDeque<>();
		//2、添加元素
		//当作队列使用
		queue.addLast(  "last1");
		System.out.println(queue.offerLast("last2"));
		queue.addLast("last3" );
		System.out.println(queue.offerLast("last4"));
		//当作栈使用
		queue.addFirst("first1");
		System.out.println(queue.offerFirst("first2"));
		queue.addFirst("first3" );
		System.out.println(queue.offerFirst("first4"));
		//3、删除元素
		//当作队列使用
		System.out.println(queue.removeFirst());
		System.out.println(queue.pollFirst());
		//当作栈使用
		System.out.println(queue.removeFirst());
		System.out.println(queue.pollFirst());
		//4、获取元素
		System.out.println(queue.peekFirst());
		System.out.println(queue.getFirst());
		System.out.println(queue.peekLast());
		System.out.println(queue.getLast());
		//5、获取队列大小
		System.out.println(queue.size());
		//6、判断队列是否为空
		System.out.println(queue.isEmpty());
		//Queue接口操作（ArrayDeque->Deque->queue）
		queue.add( "queue-last1");//队尾添加
		queue.offer("queue-last2" );
		System.out.println(queue.remove());//队头删除
		System.out.println(queue.poll());
		System.out.println(queue.element());
		System.out.println(queue.peek());
		//7、使用迭代器遍历队列
		Iterator<String> iterator = queue.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}

	}

}
