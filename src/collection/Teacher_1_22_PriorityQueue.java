package collection;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/*
 *PriorityQueue（非线程安全，可用于单线程的任务调度    ，JDK1.5以后才引入的） -》PriorityBlockingQueue（是一个线程安全的优先级队列  ，JDK1.6以后才引入的）
 *  队列  是遵循先进先出模式，但是有时候队列基于优先级去处理
 *  
 *任务调度系统
 *  当一任务执行完成，需要所有等待的任务队列中选择一个优先级最高的任务继续执行
 *  
 *  
 *特点∶
 *1)优先级队列不允许null值
 *2)优先级队列可以获取对象根据自然顺序/给定比较器（Treemap用过）的顺序
 *3)优先级队列基于  小  根堆（根小于孩子  基于数组实现）实现，如果是自然排序获取元素获取的一定是当前最小;
 *   如果是比较器排序获取元素获取的一定是当前权值最小
 *4)优先级队列是不受限制的，但是创建时可以指定初始化大小，当我们向优先级队列中增加元素，队列大小会自动增加
 *5)非线程安全，PriorityBlockingQueue用于多线程环境
 *
 *练习
 *使用优先级队列添加整型元素，获取元素，看是否是每一次获取的最小值
 *使用优先级队列添加Person元素，获取元素，看是否每一次获取的是年龄最小
 *
 *总结:
 *PrioriryQueue其中添加的元素必须是可比较元素，该元素对象所对应的类 要么 实现了比较器接口﹔
 * 要么  如果没有实现比较器接口，可以在初始化PriorityQueue对象传一个比较器，按传的这个比较器比
 */

class Person {
	private String name;
	private int age;

	public Person(String name,int  age) {
		this.name = name;
		this.age = age;
	}
	
	
	public  String getName() {
		return name;
	}

	public  int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name=' " + name + '\''+", age=" + age +
				'}';
	}
}

public class Teacher_1_22_PriorityQueue {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Queue<Integer> intQueue= new PriorityQueue<>();
		Queue<Integer> intQueue= new PriorityQueue<>(new Comparator<Integer>() {//这是上面的底层逻辑

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				//return o1-o2;
				return o2-o1;
			}
			
		});
		intQueue.add((int)(Math.random() *100));
		intQueue.add((int)(Math.random() *100));
		intQueue.add((int)(Math.random() * 100));
		intQueue.add((int)(Math.random() *100));
		intQueue.add((int)(Math.random() * 100) );
		System.out.println(intQueue);//不是从小到大的顺序，但是是小根堆的顺序，可以保证每次获取的是最小元素
		System.out.println(intQueue.remove());//删除完以后，把剩余元素调整成小根堆
		System.out.println(intQueue.remove());
		System.out.println(intQueue.remove() );
		System.out.println(intQueue.remove());
		System.out.println(intQueue.remove());
		
		// Person本身是不可比较的对象
		Queue<Person> personQueue= new PriorityQueue<>(   new Comparator(){
			@Override
			public int compare(Object o1,Object o2) {
				return ((Person)o1).getAge() - ((Person)o2).getAge();//小在左  大在右
			}
		}   );
		
		personQueue.add(new Person("jdhs",(int)(Math.random() * 50)));
		personQueue.add(new Person("jhs",(int)(Math.random() * 50)));
		personQueue.add(new Person("jdhs", (int)(Math.random() * 50)));
		personQueue.add(new Person("jdhs",(int)(Math.random() * 50)));
		personQueue.add(new Person("jdhs",(int)(Math.random() * 50)));	
		System.out.println(personQueue);
		System.out.println(personQueue.remove());
		System.out.println(personQueue.remove());
		System.out.println(personQueue.remove());
		System.out.println(personQueue.remove());
		System.out.println(personQueue.remove());
	}

}
