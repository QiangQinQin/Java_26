package thread;

/**
 课堂练习:
 synchornized同步锁实现3个线程循环打印数字，
	 使用线程1，打印1，2，3，4，5.
	 线程2，打印6，7，8，9，10.
	 线程3，打印11，12，13，14，15.
	 依次循环打印，直到打印至60
	 (提示：会使用到wait/notify/notifyAll方法)
 *
 **/

public class Teacher_2_3_Synchronized_Print {
    //static  共用
	public static int num = 1;//要打印的数字
    public static int index = 0 ; //线程的计数器，用于判断当前线程是否为期望线程
    
    //肯定需要是同步的方法
    public synchronized static void print(){
        //需要判断当前线程是否是期望的线程
        int name = Integer.parseInt(Thread.currentThread().getName());//字符串转int
        while((index%3) != name){//不一样,需要将当前线程阻塞
        //if只做一次判断，如果 判断完 (即还没有调wait时)发生线程的上下文切换(又修改了index)则发生问题(切换回来时,不判断,直接进) ,
        //所以用 while不断的判断是否是期望的,不是,就wait的死死的;是,就进入下面打印
            try {
                Teacher_2_3_Synchronized_Print.class.wait();//当前类的锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        //线程1，2，3一次1～60
        System.out.print("线程"+Thread.currentThread().getName()+ ":");
        for(int i=0; i<5; i++)
        {
            if(i > 0){//符合条件的有1 2 3 4 元素的前面加 ,
                System.out.print(",");
            }
            System.out.print(num++);
        }
        System.out.println();

        //一个线程输出完,计数器+1
        index=(index%3)+1; //模3从0开始,       1 2 3
        //唤醒其他等待线程
        Teacher_2_3_Synchronized_Print.class.notifyAll(); //唤醒全部,去争夺监视器锁,是我们期望的抢到了,就往下 执行;不然,又把他wait(同时会释放锁);又开始争夺
                                             //notify(可能唤醒不到想要的,引起不必要的阻塞) 
    }

    public static void main(String[] args) {
        for(int i=0; i<3; i++){//创建3个线程
            new Thread(""+i){//转为字符串类型
                @Override
                public void run() {
                    for(int j=0; j<4; j++){//每次每个线程分别打印5个数字,需要4轮
                        print();
                    }
                }
            }.start();
        }
    }
}
