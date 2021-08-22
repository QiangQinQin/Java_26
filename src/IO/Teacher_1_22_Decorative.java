package IO;

/**
 * 装饰器模式特点
 * 1）（被装饰类和装饰类之间）不使用继承（是的关系），使用组合（不是 是 的关系）
 * 2）不改变原类的文件(即不能改变原有的类)
 * 3）可以动态扩展类的功能（可以通过创建一个包装类的对象，来装饰真正的对象。从包装类到真正类，这就叫我们使用了装饰器设计模式）
 * 
 * 动态扩展的常见方式：（如：创建一个类，继承        创建一个接口，实现）
 * 装饰器需要的东西：
 *（1） Component:          InputStream
 *    是统一接口，可以是装饰类/被装饰类的基本接口
 *（2）ConcreteComponent   FileInputStream
 * 具体实现类（被装饰类），它本身是一个比较完整的类
 *（3）Decorator:       如： FilterInputStream（在BufferInputStream的56行）
 * 装饰类，实现Component接口同时还会在内部维护一个ConcreateComponent的实例，并且这个实例是在  构造函数  中初始化
 *（4）ConcreteDecorator   BufferInputStream
 * 具体的装饰类，每一种装饰产品都有不同的装饰效果（例如 人的服饰   首饰  鞋  美容美发）
 *
 * 组合 ： 可利用已存在类的功能，是指在新类中创建一个原有类的  实例
 *       显式的(你看：本页60行，直接定义了原有类的实例)
 *
 * 继承  ：描述一种"是"的关系（其实没那么多是的关系，这个限制有点高。
 *      可 利用已存在类的功能。
 *      新类继承原有类，当父类被修改，会影响到所有继承它的子类，增加维护难度和成本
 *      隐式的(extends关键字底层去实现的)
 *
 *类和对象三大特性：继承    封装   多态
 *
 * 例：Transport（交通工具）   Car Bus Tyre(轮胎)这4个对象，怎么使用继承 组合实现他们的关系
 * Car、Bus  is Transport （三者可维护一个继承关系）
 * Car has many Tyres/ Bus has many Tyres （组合）
 *
 */


import java.io.InputStreamReader;

/**
 * 接口
 */
interface Component{
    void method();
}

/**
 * 被装饰类
 */
class ConcreteComponent implements Component{

    @Override
    public void method() {
        System.out.println("concreteComponent method");
    }
}

/**
 * 装饰类      实现Component接口
 */
abstract class Decorator implements Component{
    //内部会维护一个Component的   实例
    private Component component; //这种方式叫组合

    //这个实例是在构造函数中初始化
    public Decorator(Component component){
        this.component = component;
    }

    @Override
    public void method() {
        component.method();//调用了被装饰类的方法
    }
}

/**
 * 具体装饰类A类
 */
class ConcreteDecoratorA extends Decorator{

    public ConcreteDecoratorA(Component component) {
        super(component);
    }

    public void methodA1(){
        System.out.println("装饰器A所实现的功能1");
    }

    public void methodA2(){
        System.out.println("装饰器A所实现的功能2");
    }
}

/**
 * 具体装饰类B类
 */
class ConcreteDecoratorB extends Decorator{

    public ConcreteDecoratorB(Component component) {
        super(component);
    }

    public void methodB1(){
        System.out.println("装饰器B所实现的功能1");
    }

    public void methodB2(){
        System.out.println("装饰器B所实现的功能2");
    }
}

public class Teacher_1_22_Decorative {
    public static void main(String[] args) {
    	//FilterInputStream
        //被装饰的对象
        Component component = new ConcreteComponent();
        //被装饰的对象本身具有的功能
        component.method();
       
        
        //装饰成A
        ConcreteDecoratorA a = new ConcreteDecoratorA(component);
        a.method();//保持了被装饰对象原有的功能
        a.methodA1();
        a.methodA2();
        //装饰成B
        ConcreteDecoratorB b = new ConcreteDecoratorB(component);
      //ConcreteDecoratorB b = new ConcreteDecoratorB(a);//63行调用构造函数和重写的method方法，无法调用methodA1和methodA2
        b.method();
        b.methodB1();
        b.methodB2();
        
    }
}
