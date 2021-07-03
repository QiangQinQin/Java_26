package IO;

import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * lambda表达式
 * java1.8出现   让我们用更加简洁流畅（比匿名内部类还 简洁）的代码完成功能（函数式编程能力）
 *
 * eg. TestLambda lambda2 = ()-> System.out.println("testSynchronousQueue lambda");
 * 语法:
	 * =左边   哪个函数式接口的  引用
	 * =右边 lambda表达式
	 * ()     方法的参数
	 * ->     lambda操作符
	 * ->之后是   方法的实现
 *
 * Comparator接口,自定义忽略大小写去进行排序
 * String[] strs = "shhs Ihhjdh iwjjjd AJhjhhhs bjfhdh".split(" ");
 * Arrays.sort(strs, new Comparator<String>(){//仅有一个方法   源码Arrays 1431行
 *     @Override
 *     public int compare(String s1, String s2){
 *         return s1.toLowerCase().compareTo(s2.toLowerCase()); //逐个字符比ASCII
 *     }
 * });
 *
 * 课堂练习：修改上面Comparotor接口的实现为lambda表达式：  
 * (String s1,String s2)-> s1.toLowerCase().compareTo(s2.toLowerCase())
 * compareTo在String.class  1165行
 */


////匿名内部类
//interface TestLambda{
//void func();
////void fun1();
//}

//函数式接口（只有一个方法）（）比如runnable
@FunctionalInterface
interface TestLambda{
    void func();
    //void fun1();
}


@FunctionalInterface
interface TestLambda1{
    void func(String test);
    //void fun1();
}
public class Teacher_1_27_lambda {
    public static void func(Function<Integer, Integer>function){

    }
    public static void main(String[] args) {
    	//匿名内部类
        TestLambda lambda1 = new TestLambda(){
            @Override
            public void func() {
                System.out.println("fun");
            }

//            @Override
//            public void fun1() {
//                System.out.println("fun1");
//            }
        };
        
        
        //lambda表达式
        TestLambda1 lambda2 = (test)-> System.out.println("testSynchronousQueue lambda");
        BinaryOperator<Long>functionAdd=(x,y)->x+y;

      //  Long result=functionAdd(1L,2L);
      
        BinaryOperator<Long> a=(x, y) -> x+y;//https://objcoding.com/2019/03/04/lambda/
     //   Long result=a(1L,2L);
       System.out.println(a);
        String[] strs = "shhs Ihhjdh iwjjjd AJhjhhhs bjfhdh".split(" ");
        Arrays.sort(strs, (String s1, String s2)-> s1.toLowerCase().compareTo(s2.toLowerCase()));//lamuda一步实现自定义排序（减化了上面21行）
        System.out.println(Arrays.toString(strs));

    }
}
