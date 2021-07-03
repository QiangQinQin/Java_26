package IO;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
大纲：
 * IO流   操作的东西是文件
 * I表示input
 * O表示output
 * 1、文件 File
 * 2、Java中IO流         涉及到设计模式、节点流、处理流或者称为高级流、低级流
 *    2.1 字节流（读取时以字节为单位）
 *    2.2 字符流
 * 3、Java中序列化和反序列化机制
 * 4、RandomAccessFile的使用
 * 5、Java8新特性： lambda表达式
 * 6、Java8新特点 ： stream的使用(即流操作)
 *
 *
 * 文件 & File类的使用
 * 1、文件的概念
 * 文件可以分为文本文件(理解为字符序列构成、可以看的懂) 、二进制文件（位序列构成0101 ）
 * 2、IO流的概念
 * 流是有顺序、有起点和终点的一个集合，是对数据传输的总称。流的本质就是用来对  数据  进行操作
 *
 * IO是我们实现的目的，实现这个目的需要利用的机制就是流机制
 * (从起点 读取      写入 到终点)
 * 3、流的分类
 * 1）按照流向：  输入流 和 输出流
 * 输入流指的是 从键盘 文件 鼠标  读取  到程序  内存  的过程
 * 输出流指的是 从程序  内存   写入  到磁盘 打印机 文件的过程
 * 2）按照传输类型： 字节流 和 字符流
 * 字节流指的是传输的数据是以字节为单位的
 * 字符流指的是传输的数据是以字符为单位的
 * 3）按照角色：  节点流和处理流（涉及到装饰器设计模式----大公司面试考）
 * 节点流指的是程序直接连接到实际的数据源（即文件）
 * 处理流（也称为高级流。如转换流和缓冲流）指的是，对  节点流  做了一层封装，通过  封装后  的流来实现数据的读取和写入功能（真正处理的是  封装前的节点流）
 *
 * 面试题：（自己总结完善）
 * 字符流和字节流的区别？
 * 1）字 符 数据 ： 字符流将原始数据（二进制序列）解释成字符序列
 *   文本  数据存储依赖平台  编码方式（如utf-8，不同的编码方式，一个字符对应的字节数 是不一样的），字符流的输入和输出需要进行  编码（读入时要把二进制变为字符）和解码
（写到磁盘要解码为二进制）  的过程，不同平台编码方式导致文件不一样大（如将一个字符 解析成2字节或3字节）
 * 2）二进制数据 ：字节流流将原始数据解释成二进制数据
 * 读写都是以字节为单位，不需要编码和解码，比字符流效率更高。在不同平台读字节时，对应的文件大小是一定的
 * 字节流可移植性高
 *
 *
 * File类的使用
 * 位于java.io包下，用于 操作  文件  和  目录 。  File类可以新建/删除/重命名/获取目录下文件...，File类不能访问文件内容本身，
 * 如果需要访问文件  内容  本身，需要用到IO流
 *
 *创建文件的两种方式：
 * 1。 File的275行。  public File(String pathname)
 * 路径：相对路径 绝对路径
 * 相 对路径： windows 不带 盘符的路径表示相对路径； linux 不带/（根目录）的;     .表示当前目录      ..表示上一级目录
 * 绝  对路径： 完整的路径名称。如linux下的/user/lvting ,要带/
 *
 * 2. File源码249  private File(String pathname, int prefixLength)   路径名和前缀
 *
 *File类里常用的方法
 * boolean exist 判断当前路径是否存在（有则true）
 * boolean isFile  当前路径对应是一个文件
 * boolean isDirectory 当前路径对应是一个目录
 * boolean createNewFile 在当前路径下 文件不存在的情况下，创建新文件
 * boolean isHidden 判断是否是隐藏文件（需要这个文件存在，才能判断他类型）（linux下ls -a 指令可以查看所有文件，包括“ .文件名 ”格式的隐藏文件）
 * boolean delete 删除文件
 * 
 * file.mkdir  创建目录
 * file.getName
 * file.getAbsolutePathh
 * file.listFiles 获取当前目录下的所有文件， File[]  1206行
 * file.list 获取当前目录下的所有文件 以String[]返回，File  1114行
 */
public class Teacher_1_20_IO {
  
	//递归删除多级目录
    public static void delete(File file){
        if(file == null || !file.exists()){
            return;
        }
        if(file.isFile()){
            file.delete();
        }else{
            //递归删除多级目录
            deleteDir(file);
        }
    }

    public static void deleteDir(File file){
        //终止条件
        File[] files = file.listFiles();//获取目录下所有文件，存到file数组
        if(files == null){//表示当前目录是一个空目录  
            return;  //满足终止条件的返回结果
        }
        //相同逻辑
        //继续遍历该  文件对象  的集合
        for(File f: files){
            if(f.isFile()){
                //该文件对象是一个文件 直接删文件
                f.delete();
            }else{
                deleteDir(f);//递归
            }
        }        
        file.delete(); //删除当前空的目录     
    }
    
    //获取某一个目录下的所有文件
    public static void printFile(File dir){
        if(dir == null || !dir.exists()){
            return;
        }

        File[] subFiles = dir.listFiles();//子文件
        if(subFiles == null){
            //表示dir是一个空目录
            return;
        }

        //遍历文件对象的集合，要么该文件对象对应的就是一个文件，要么对应的还是一个目录
        for(File f: subFiles){
            if(f.isFile()){
                System.out.println(f.getName());
            }else{
                printFile(f);//递归
            }
        }
    }
    
    
    //打印指定路径下 的所有java文件
    public static void printJavaFile(File dir){
        if(dir == null || !dir.exists()){
            return;
        }

        File[] subFiles = dir.listFiles();//子文件
        if(subFiles == null){
            //dir的子文件为空，表示dir是一个空目录
            return;
        }

        //遍历文件对象的集合，要么该文件对象对应的就是一个文件，要么对应的还是一个目录
        for(File f: subFiles){
            if(f.isFile() && f.getName().endsWith(".java")){//名字以.java结尾
                System.out.println(f.getName());
            }else{
                printJavaFile(f);//递归
            }
        }
    }

    public static void main(String[] args) {
//        //练习1： 创建两个文件对象，分别使用相对路径和绝对路径
//    	//相对路径
////    	File file1_1 = new File("./a.txt"); //创建了一个  能访问  物理文件、目录的对象，但对应不会创建这样一个物理文件              位置和IO包同级，等价于a.txt
//        File file1 = new File("src/IO/a.txt"); //在project中，相对路径的根目录是Java_26,相当于.
//        //绝对路径
////      File file2_1 = new File("/Users/lvting/tulun/code/JAVA26/b.txt");//斜线(linux)
//        File file2 = new File("D:\\Tulun\\Java_26\\src\\IO\\b.txt");//加盘符（windows）
//        //练习2：检查文件  是否存在  ，不存在则创建文件
//        if(!file1.exists()){
//            try {
//                file1.createNewFile();//refresh一下，就能看到
//            } catch (IOException e) {
//                e.printStackTrace();//在一个.java文件里要么在方法添加里抛出异常声明(134行 throws IOException)，要么用try catch块捕获，只能用一种
//            }
//        }
//        if(!file2.exists()){
//            try {
//                file2.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        //练习3：创建单级  目录 （即文件夹底下只有文件，没有文件夹）          包下面不能再建文件夹
//        File file3 = new File("./aaa");
//        if(!file3.exists()){
//            file3.mkdir();
//        }
        //练习4：创建多级目录
        File file4 = new File("./bbb");//手动给bbb(和src同级)下面创建一些文件，利用file4可以访问对应的物理文件，可以把多级目录bbb删空
        if(!file4.exists()){
            file4.mkdirs();
        }
//        //练习5：删除文件 目录(该目录下的所有文件都删除)（笔试题）
//        file1.delete();//删除文件
//        file2.delete();
//        
//        file3.delete(); //只能删除单级空目录
//   //   file4.delete(); //实测无法删除多级目录，只删除了最底层的空目录 
//        delete(file4);//调用方法，递归删除

//        //练习6：获取文件名、文件大小、文件的绝对路径、父路径
//        File file5 = new File("./testSynchronousQueue.txt");
//        if(!file5.exists()){
//            try {
//                file5.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println(file5.getName());
//        System.out.println(file5.length());//内容字节长度
//        System.out.println(file5.getAbsolutePath());
//        System.out.println(file5.getParentFile());
//        //练习7：文件 和 目录的判断
//        if(file5.isFile()){
//            System.out.println("fild5是一个file");
//        }else{
//            System.out.println("file5是一个directory");
//        }
        //练习8：获取某一个目录下的所有文件
        //listFiles->返回文件  对象  的集合 ,File[]   而       list->文件   pathName  的集合,String[]
        printFile(new File("bbb"));
        ////练习9：从键盘上输入一个路径，打印出该路径下所有.java文件（笔试题）
        //System.out.println("output the .java file of JAVA_26");
        //printJavaFile(new File("/Users/lvting/tulun/code/JAVA26/"));
      ////printJavaFile(new File("src/collection"));//以后把源代码复制到其他电脑，相对路径也不出错
    }
}
