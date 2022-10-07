package thread;

/**
 * 单例  模式  双重检测锁代码（重要）
 * 针对的是懒汉式
 *
 */
class A{

}
class B{

}
//懒汉式的单例
//double-check的方法可能引起的空指针异常
class MySingleton{
    private volatile static MySingleton singleton = null; //实例
    private A a;
    private B b;
   
    //构造函数私有
    private MySingleton(){
        //如果构造函数对中要当前实例a 和 b初始化
        //this.a = xx;
        //this.b = xx;
    	
    	////构造函数做两间事：new一个当前类的实例    和   成员变量初始化
    	//如果线程A执行到47行new一个实例（还没来得及创建A B）；另外一个线程判断到MySingleton此时不为null,想用里面的A B；但A B可能还没初始化好，此时就会抛出空指针异常
        //为解决这种问题，sington用volatile修饰（读要等到写完）
    }
    
    private static final Object lock = new Object();  
////解决多线程问题方案1
//    public static MySingleton getInstance(){
//        synchronized (lock){ //若singleton不为null时 仍会先加锁，然后判断不为null,又解锁 。这样 频繁的加锁解锁会有效率问题。。所以先判断为null,再加锁，再进行new
//            if(singleton == null){
//                singleton = new MySingleton();
//            }
//        }
//        return singleton;
//    }
    
 ////解决多线程问题方案2
    public static MySingleton getInstance(){
        if(singleton == null){//threadA 判断完，切换到thread B，他进行 判断 加锁 实例化后切换回ThreaA,A会接着获取锁 实例化，就会产生两个单例。所以在47行加判断
            synchronized (lock){//为null才加锁
                if(singleton == null){ //此处判断，为了解决多线程环境，A/B线程都同时执行到synchronized上的问题
                    singleton = new MySingleton();//还是会有问题。看上面24行 （volatile）
                }
            }
        }
        return singleton;
    }
}
public class Teacher_2_3_Singleton {
    public static void main(String[] args) {

    }
}


////构造函数私有
//private MySingleton(){
////构造函数对当前实例a 和 b初始化
////new一个当前类的实例   成员变量初始化
////this.a = xx;
////this.b = xx;
//}
//
////只能通过唯一的方法，不能通过构造函数实例化
//public static MySingleton getInstance(){
//if(singleton == null){//懒汉单例模式是只有第一次获取时才给他实例化，后面调用就直接返回     单线程没问题，不支持多线程（因为可能A B示例没new完，就进行线程的切换）
//        singleton = new MySingleton();
//    }
//}
//  return singleton;
//}

