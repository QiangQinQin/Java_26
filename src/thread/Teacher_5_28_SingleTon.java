package thread;
/*
非线程安全的
    不是线程安全的！
    通过Single2.getInstance();来调用当前的方法
    调用该方式时，若该类第一次使用，要进行类加载。在整个加载过程中当前类实例的静态变量s还是为null,并完成了类的加载过程。
    在使用阶段，有可能同时两个线程调用了在调用getInstance方式，可能两个线程同时判定s == null 为真，每个线程都会new Single2();意味着当前JVM中会存在两个Single2实例，所以存在线程不安全的风险
*/

 class Single2 {

    private static Single2 s = null;
    private Single2() { }//说明不能通过其他类来new

    public static Single2  getInstance() {//没有加Synchronize
        //两个线程同时进行if (s == null)判断，则都会进入if条件吗，就会创建多个实例
        if (s == null)
            s = new Single2();
        return s;
    }
}


/**
分析：
 当Single4.getInstance()调用时，如果该类没有被加载，就需要进行加载操作。
 在加载的过程中，在链接阶段中准备过程中对当Single4中静态变量singleton4分配内存，
 在 初始化阶段 来执行静态代码块，静态代码块逻辑是来完成对singleton4变量 实例化，
 整个阶段在加载过程完成的在使用阶段不管多个线程也好，都是在 使用 阶段来调用getInstance，此时对象已经被实例化出来了

 所以当前是线程安全的，线程安全的机制是由类加载机制来保证的：
        同一个类只会被jvm加载一次，而当前类的静态变量和静态代码块都是在 加载过程 中完成的
 */
//线程安全
public class Teacher_5_28_SingleTon {
    private final static Teacher_5_28_SingleTon SINGLETON;
    private Teacher_5_28_SingleTon() { }

    //静态代码块
    static {
        SINGLETON = new Teacher_5_28_SingleTon();
    }

    public static Teacher_5_28_SingleTon getInstance() {
        //使用之前将singleton4属性通过静态代码块实现
        return SINGLETON;
    }
    public static void main (String[]args){
        //第一次调用SingleTon，JVM需要负责将SingleTon加载到内存中，在加载的过程处理静态代码块
        Teacher_5_28_SingleTon.getInstance();
        //第二次调用SingleTon，JVM中已经存在SingleTon，直接使用getInstance
        Teacher_5_28_SingleTon.getInstance();
    }
}
