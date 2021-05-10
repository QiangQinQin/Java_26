package collection;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.PriorityQueue;

/* PriorityQueue实现
* 基于优先堆实现-》小根堆-》数组
**add(T value)/remove( ) /remove(T value)/toString()
*
*
* 源码分析：
* 1）类的继承关系(注释第一段话  31行)
* PriorityQueue<E> extends AbstractQueue<E>  implements java.io.Serializable {
* 和ArrayDeque的区别有一个是没有实现Deque接口，所以只有(更多可参考Teacher_1_25_Queue )
 *  add(E)队尾添加元素
 *  offer(E)*(为空，会抛出异常) 
 *  remove()移除队列头部元素   
 *  poll() *      （移除元素，若队列为空，poll不会返回异常，remove会返回异常）
 *  element() 获取头部元素
 *  peek(
*
* 默认容量 11
* 2）类的属性
* 3）类中的方法（构造方法/add/remove/iterator）
* 
* 扩容
* int newCapacity = oldCapacity + ((oldCapacity < 64) ?(oldCapacity + 2) :(oldCapacity >> 1));
* remove() 是在AbstractQueue的112行实现。PriorityQueue<E>里面的唯一一个remove()还是迭代器的
* poll()在PriorityQueue<E>的587行
* */
class MyPriorityQueue<T  extends Comparable<T>> {//保证可比较
	private T[] queue;// 存放元素
	private int size;//有效元素个数
	private static final int defaultcapcacity = 5;

	public MyPriorityQueue() {
		this(defaultcapcacity);
	}

	public MyPriorityQueue(int capacity) {
		queue=(T[])new Comparable[defaultcapcacity ];
	}
	
	public void add(T value){
		if(size == queue.length){
			//满扩容
			queue = Arrays.copyOf(queue,2*queue.length);
		}
		//往小根堆中添加元素
		if(size == 0){
		//特殊情况，size==0
			queue[size++] = value;
		}else{
		//正常情况，添加元素到size位置，调整该堆为一个小根堆
			adjust(size,value);
			size++;
		}
		
		
	}
	
	//wps上26_吕婷文档搜小根堆，有实际例子的图，可帮助理解
	public void adjust(int index,T value){
		//从下往上的过程
		while(index > 0){
			int parentIndex = (index-1) / 2;
			if(queue[parentIndex].compareTo(value) > 0){
			//父节点值>index位置      父节点值往下走     index位置往上走
				queue[index] = queue[parentIndex];
				index = parentIndex;
			}else{
				break;//父节点小，就不用调整
			}
		}
		queue[index] = value;
	}
	
	//删除根节点元素
	public boolean remove(){
		//没有元素
		if(size == 0){
			return false;
		}
		
		int index = --size;//有效元素个数减1   index指向末尾元素
		//特殊情况，仅有一个元素，删除0号位置
		if(index == 0){
			queue[index] = null;
			return true;
		}
		//正常情况下，删除0号位置，删除之后需要调整
		queue[0] = null;
		adjustDown(0,queue[index]);//把size-1位置点移到根位置，从上往下调整
		return true;
	}

	public void adjustDown(int index, T value) {//传入要调整的位置 和 值，最后要把value放在合适的index位置
		//从上往下调整(即从index位置处往下调整)
		while(index <  size/2) {//即要有子结点
			int leftChild=index*2+1;
			int rightChild=index*2+2;
			
			//找左右子节点较小的
			T minValue=queue[leftChild];
			int minIndex = leftChild;
			if(rightChild < size && queue[leftChild].compareTo(queue[rightChild])>=0){//即右不大于左
				minValue = queue[rightChild];
				minIndex = rightChild;
			}
			
			//即比子节点小，小根堆就不用调整了
			if(value.compareTo(minValue) < 0){
				break;
			}
			//否则，因为左右孩子最小值minValue小于value ,所以minValue值往上走到父节点index位置
			queue[index] =minValue;
			//index往下走，找他满足小根堆关系的位置
			index = minIndex;
		}
		//找到位置后，把值放上去
		queue[index] = value;
	}
	
	//删除指定元素
	public boolean remove(T value) {
		int index=-1;//保存value对应的位置点
		for(int i=0; i<size; i++){
			if(value.equals(queue[i])){
				index = i;
				break;
			}
		}
		//说明上面没找到
		if(index == -1){
			return false;
		}
		
		
		//特殊情况，删除最后一个元素
		if(index == size-1) {
			queue[--size]=null;
			return true;
		}else {
			//正常情况下，删除index位置元素，要重新调整成小根堆
			//这里，我们把size-1元素放到index位置
			queue[index]=queue[size-1];
			//从index处往下调整
			adjustDown(index, queue[index]);
			//特殊情况
			if(queue[index]==queue[size-1]) {
				//从index处往上调整
				adjust(index,queue[size-1]);
			}
			
			queue[--size]=null;//总数减1，末尾元素由于移到index位置了，所以可以该位置置空
			return true;
		}
	}
	
	public String toString() {
		StringBuilder strs=new StringBuilder();
		for(int i=0;i<size;i++) {
			strs.append(queue[i]+" ");
		}
		return strs.toString();	
	}
}
	

public class Teacher_1_22_PriorityQueue_Realize {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//PriorityQueue<E>
		//AbstractQueue<E>
		
	}

}
