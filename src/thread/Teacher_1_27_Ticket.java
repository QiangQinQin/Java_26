package thread;

/**
 * start方法剖析
 * public synchronized void start() { //即不能多个线程启动同一个线程
      if(threadStatus!=0)//判断当前线程状态是否为0
            throw new IllegalThreadStateException();//不为0，已启动
      group.add(this); //当前线程加入一个线程组
      boolean started=false;
      try{
          start0(); //start0是一个native本地方法（是用操作系统底层去操作），作用是 调用（用户写的）run执行该线程（线程的执行需要cpu，就需要 操作系统底层的帮助）
          started=true;
      }finally{
          try{
            if(!started){
                group.threadStartFailed(this);
            }
          }catch(Throwable ignore){
      }
  }

 start方法首先将线程加入一个线程组中，这时该线程进入Runnable就绪状态，然后在start0中获取cpu资源执行程序自定义的run方法

 run方法就是一个普通的方法，不会启动一个新的线程

 注意：线程在调用start方法的时候会有一个threadStatus，
	 如果不等于0说明已经启动，不能够重复启动，
	 重复启动抛异常IllegalThreadStateException
 */
// 改为线程安全
class TicketSystem extends Thread {
	private String name;
	private static final int max = 50; // 最多叫到50号
	private static int index = 1; // 当前叫号值
	private static final Object  lock=new Object();
	
	public TicketSystem(String name) {
		this.name = name;
	}

//	@Override
//	public void run() {
//		synchronized (lock) {//获得锁,只能有一个线程访问
//			while (index <= max) {
//			System.out.println("当前办理业务为：" + name + ", 当前号码为：" + index++);
//			}
//		}
//		
//	}
//	@Override
//	public synchronized void run() {
//		synchronized (lock) {//获得锁,只能有一个线程访问
//			while (index <= max) {
//			System.out.println("当前办理业务为：" + name + ", 当前号码为：" + index++);
//			}
//		}
//		
//	}
}
////方法1
//class TicketSystem extends Thread{
//    private String name;
//    private static final int max =50; //最多叫到50号
//    private static int index = 1; //当前叫号值
//    //不加static每个柜台，都独有一个index;
//    //static后，所有柜台共享，当max值比较大时，中间可能有缺失  重复  甚至大于max(后面线程安全再分析)！！！！！！！！！！！
//
//    public TicketSystem(String name){
//        this.name = name;
//    }
//
//    @Override
//    public void run() {
//        while(index <= max){
//            System.out.println("当前办理业务为："+name+", 当前号码为："+ index++);
//        }
//    }
//}

////方法2:所有线程都用同一个runnable
//class TicketSystemTask implements Runnable{
//    private static final int max = 200; //最多叫到50号
//    private static int index = 1; //当前叫号值
//
//
//    @Override
//    public void run() {
//        while(index <= max){
//            System.out.println("当前办理业务为："+Thread.currentThread().getName()+", 当前号码为："+index++);
//        }
//    }
//}
public class Teacher_1_27_Ticket {
    public static void main(String[] args) {
    	//方法1
      TicketSystem t1 = new TicketSystem("1号柜台");
      t1.start();

      TicketSystem t2 = new TicketSystem("2号柜台");
      t2.start();

      TicketSystem t3 = new TicketSystem("3号柜台");
      t3.start();

      TicketSystem t4 = new TicketSystem("4号柜台");
      t4.start();

      TicketSystem t5 = new TicketSystem("5号柜台");
      t5.start();
    	//方法2
//        TicketSystemTask task = new TicketSystemTask();
//        Thread t1 = new Thread(task, "1号柜台");//用同一个task，给定线程名
//        Thread t2 = new Thread(task, "2号柜台");
//        Thread t3 = new Thread(task, "3号柜台");
//        Thread t4 = new Thread(task, "4号柜台");
//        Thread t5 = new Thread(task, "5号柜台");
//        t1.start();
//      //  t1.start();//抛异常
//        t2.start();
//        t3.start();
//        t4.start();
//        t5.start();

    }
}
