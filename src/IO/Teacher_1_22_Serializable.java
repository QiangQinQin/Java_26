package IO;

import java.io.*;
import java.util.ArrayList;

/**
 * Java中序列化与反序列化
 * 类中的  对象  会随这程序的终止被垃圾回收器而销毁，
 * 如果在不想要创建对象的情况下调用该类中的  功能 ，考虑使用序列化将原有的  对象  转换为字节流存到磁盘上
 *
 * 对象的序列化：对象-》字节流     永久保存至磁盘文件或者通过网络传送
 * 对象的反序列化：字节流-》对象      一个平台上序列化的对象在不同平台都可以被反序列化
 *
 * 面试题：序列化机制存在的意义？
 * 对象的序列化机制允许将Java对象转换为字节流，这些字节流可以永久地保存在磁盘上，或者从网络中传输，从一个网络节点到另外一个网络节点，
 * 可以使得某一个对象脱离原有的程序能够  独立  存在（跨平台  对象重用），其他程序一旦获取到当前的  字节  流（若是字符串，由于编码方式不同，可能在不同平台被解析为不同结果），都可以将这种字节流恢复为原有的对象。
 *
 * 如何实现对象的序列化：
 * 1）对象所在类实现Serializable接口（源码里面啥都没有，是一个标记接口），使用Serializable接口实现序列化
 * 2) 创建ObjectOutputStream（字节流），这个输出流是一个  处理流  ，所以必须建立在其他节点流的基础上
 * 3）调用ObjectOutputStream对象的writeObject去序列化对象
 *
 * 如何实现对象的反序列化：
 * 1）创建ObjectInputStream，这个输入流是一个处理流，所以必须建立在其他
 * 节点流的基础上
 * 2）调用ObjectInputStream对象的readObject方法读取（字节）流中对象并原样打印（不牵扯编码解码过程。只有在不同平台打开  文件  的时候，才牵扯到以哪种编码方式解码）
 * 注意：
 * 1）反序列读取到的只是原先创建的Java 对象（而不是java类），你需要直到当前对象所属的类
 * 2）Person只有一个构造器，同时反序列化时没有打印当前构造器中的内容，那就说明这里并  没有  初始化对象
 * 3）当一个序列化的类如果有  多个父类 的，这些父类  要么  提供一个无参构造器，  要么 就也得实现序列化接口，如果以上都没有则表示这个父类是不可被序列化的
 *
 *
 * 初始化对象的方式： new   反射      反序列化
 *
 *
 * 什么是serialVersionUID（版本号）？（如：出现在ArrayList源码110行）
 * 如果某个对象保存在磁盘上，那么序列化的时候就会根据该对象的哈希码（根据类名  接口名  成员方法名   属性生成）生成serialVersionUID
 * 用来标记在当前的对象上，用于对象的版本控制。当修改原有的类，反序列化时没有办法将原有的对象恢复，因为serialVersionUID已经为新类重新生成
 *
 * ArrayList中的序列化和反序列化
 * ArrayList重写了writeObject和readObejct方法(保证了只序列化有效元素，不序列化无效位置点)控制序列化过程
 *
 * 注意：tranisent（如:ArrayList源码135行）关键字修饰的属性  不  参与序列化与反序列化的过程
 * 当一个变量前加上这个关键字，在序列化的规程中该变量不会写入磁盘文件（即不能序列化），而反序列化之后该变量会是对应类型的初始值（即因为对象有该属性，所以为初始值，  即elementData反序列化后为初始值）
 *
 * 调用到ArrayList中writeObject的过程？
 * ObjectOutPutStream.writeObject
 * ->writeObject0
 * ->writeOrdinaryObject
 * ->writeSerialData
 * ->invokeWriteObject  反射  机制（对象属于哪个类，就调用他对象的writObject方法；如果没有，就调用默认的）
 */

class Person implements Serializable{
    private String name;
    private int age;

    public Person(String name, int age) {
        System.out.println("实例化Person对象");//创建一次新对象调用一次构造函数，测试  序列化有没有调用构造函数
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
public class Teacher_1_22_Serializable {
    public static void main(String[] args){
        ArrayList<String> stringList = new ArrayList<>();//默认元素容量是10
        stringList.add("hello");
        stringList.add("world");
        stringList.add("hello");
        stringList.add("tulun");

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            //序列化
            oos = new ObjectOutputStream(new FileOutputStream("stringList.txt"));
            oos.writeObject(stringList);//从Object跳到ArrayList的writeObject方法 
            //ObjectOutputStream源码342 write0Object  先判断，如果有子类，可以覆盖原有的，如果是true,调用的是覆盖的Object方法
            //private void writeObject0(Object obj, boolean unshared)  1105到1168是做判断（能否被序列化、只能被序列化一次。。。)   1129获取class信息，保存到desc
            //1170 判断他真是什么类型时，该怎么处理
            //是序列化对象跳转到1143   1429判断他是不是其他类型。。。看上面46行注释
            
            //反序列化
            ois = new ObjectInputStream(new FileInputStream("stringList.txt"));
            ArrayList<String> newStringList = (ArrayList)ois.readObject();
            System.out.println(newStringList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        //序列化一个Person对象
//        ObjectOutputStream oos = null;
//        ObjectInputStream ois = null;
//
//        try {
//            //创建一个ObjectOutputStream
//            oos = new ObjectOutputStream(new FileOutputStream("a.txt"));
//            //创建一个ObjectInputStream
//            ois = new ObjectInputStream(new FileInputStream("a.txt"));
//            //调用writeObject实现序列化
//            Person person = new Person("tulun", 18);
//            oos.writeObject(person);//这个二进制流不受制于任何字符编码格式	
//
//            //通过readObect实现反序列化
//            Person p = (Person)ois.readObject();
//            System.out.println(p.getName()+": "+p.getAge());
//            
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                oos.close();//把流关闭
//                ois.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
