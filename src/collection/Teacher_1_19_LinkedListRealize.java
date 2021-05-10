package collection;
/*
 * LinkedList的实现
 */
class MyLinkedList<T> {
	private Node<T> head; // 指向头节点
	private Node<T> tail; // 指向尾节点
	private int size;//有效元素的个数
	
	class Node<T> {
		private T data; // 数据域
		private Node<T> prev; // 指向  当前节点的前一个节点
		private Node<T> next; // 指向  当前节点的后一个节点
		//构造函数
		public Node(T data, Node<T> prev, Node<T> next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}
	
	public void add(T value) {
		// 尾插法添加一个元素
		//第一次插入
		Node<T> newNode = new Node<> (value,tail,  null) ; //即结点前驱结点为空的tail,后继为空，值为value
		if(head == null){
			head = newNode ; //指向newNode
		}else{
		//正常情况下
			tail.next = newNode;//双向链表 
		}
		tail=newNode;
		size++;
	}

	public void add(int index, T value) {
		// 指定位置添加一个元素
		if(index < 0 || index > size){
			return; 
		}
		if(index == size){//index从0到size-1；为size时，指插到末尾
			add(value);//添加到末尾
		}else{
			//双向链表，设置前后结点的双向关系
			Node<T> succ = findNodeByIndex(index); //index对应的结点。插到此处，succ变成插到此处节点的后继结点
			Node<T> succPrev = succ.prev;
			Node<T> newNode = new Node(value,succPrev,succ);//新插入的结点
			succ.prev = newNode;
			if(succPrev == null){
				//说明add至第一个位置
				head = newNode;
			}else{
				succPrev.next = newNode;
			}
		}
		size++;
	}

	//源码 566行（二分法）
	//找到index对应的结点
	public Node<T> findNodeByIndex(int index){
		////遍历
//		for(Node<T> tmp=head; tmp!=null; tmp=tmp.next) {
//		}
		Node<T> tmp = head;
		for(int i=0; i<index; i++){
			tmp = tmp.next;
		}
		return tmp;
	}

	public boolean remove(T value) {
		//删除元素所在的节点
		Node<T> succ = findNodeByValue(value);
		if (succ == null) {
			return false;
		}
		//特殊情况
		//先保存要删除结点的前后节点
		Node<T> succPrev = succ.prev;
		Node<T> succNext = succ.next;
		//处理要删除节点和前一个结点的指向关系
		if (succPrev == null) {
			// 删除第一个节点
			head = succNext;
		}else{
			// 正常情况
			succPrev.next = succNext;//前一个结点往后指一个
			succ.prev = null;//当前结点向前置断开，即置为空
		}
		
		//处理要删除节点和后一个结点的指向关系
		if (succNext == null) {
			//要删除的是最后一个结点
			tail = succPrev;
		} else {
			// 正常情况
			succNext.prev = succPrev;//后一个结点往前指一个
			succ.next = null;//当前结点向后置为空
		}
		succ.data=null;//方便垃圾回收
		size--;
		return true;
	}
	
	//如果值重复？？？返回几个      源码也是若重复，只删除第一个
	public Node<T> findNodeByValue(T value) {
		for (Node<T> tmp = head; tmp != null; tmp = tmp.next) {
			if (tmp.data.equals(value)) {
				return tmp;
			}
		}
		return null;
	}

	public T set(int index, T newValue){
		//修改指定位置元素的值
		if(index < 0 || index >= size){
			return null;
		}
		Node<T> succ = findNodeByIndex(index);
		T result = succ.data;
		succ.data = newValue;
		return result;//返回旧值
	}

	public T get(int index){
		if(index < 0 || index >= size){
			return null;
		}
		return findNodeByIndex(index).data;
	}

	public String toString() {
		//字符串拼接只能使用加号，每加一次产生一个中间对象（StringBuilder）,不如直接创建一个StringBuilder对象
		StringBuilder strs = new StringBuilder();
		Node<T> tmp = head;
		for(int i=0; i<size; i++){
			strs.append(tmp.data+" ");
			tmp = tmp.next;
		}
		return strs.toString();
	}
}


public class Teacher_1_19_LinkedListRealize {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyLinkedList<Integer> list = new MyLinkedList<>();
		//尾插
		list.add(10);
		list.add(1);
		list.add(4);
		list.add(15);
		list.add(100);
		list.add(16);
		//指定位置插
		list.add( 0,100);
		list.add( 5,200);
		System.out.println(list);
		
		list.remove( 10);
		list.remove(100);
		list.remove(200);
		System.out.println(list);
		
		System.out.println(list.set(0,1000));
		System.out.println(list.get(0) );

	}

}
