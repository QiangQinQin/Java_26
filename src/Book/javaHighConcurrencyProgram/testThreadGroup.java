package Book.javaHighConcurrencyProgram;

/**
 * @author QiangQin
 * * @date 2022/10/22
 */
public class testThreadGroup {
    public static void main(String[] args) {
        //① 获取当前线程的group
         ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        System.out.println(currentGroup. getName());

        // ② 定义一个新的group
         ThreadGroup group1 = new ThreadGroup("group1");
        System.out.println(group1. getName());
         System.out.println("默认的父group"+group1.getParent().getName());

        ThreadGroup group2 = new ThreadGroup(group1,"group2");
        System.out.println("指定后的父group"+group2.getParent().getName());

    }
}
