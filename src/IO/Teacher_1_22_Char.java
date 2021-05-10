package IO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Arrays;

/**
 *
 * 字 节 输入流：
 * 主要用于  读取（从数据源读到程序内存）  诸如  图像视频  之类的数据文件
 * 字节输出流：
 * 将字节码  写入（程序内存写到磁盘）  诸如  图像视频 之类的数据文件（二进制文件）
 *
 * IO流&字 符流
 * IO是我们的目的，达到这一目的，需要使用 流机制，更多的处理  文本   文件
 * 字符流是什么？
 * 1）字符流是可以读写字  符  的IO流
 * 2）字符流读取字符：先读取到    字节   数据（01二进制），然后转为  字符
 * 3）字符流写入字符： 把字符转为 字节   写入
 *
 * Reader（抽象类 字符输入流）--》 FileReader（具体实现类）
	 * 构造函数
		 * public FileReader(String fileName)
		 * public FileReader(File file)
	 *  底层是new了一个FileInputStream（字节流的构造函数）对象 ； FileReader继承了InputStreamReader转换流（用来字节转字  符）
	 *
	 * 成员方法
	 * public int read()//底层的字节转为字符 ，然后转为编码值，然后返回（-1表示结束）
	 * public int read(char cbuf[])返回读的长度
	 * public int read(char cbuf[], int off, int len)  缓冲数组  起始位置  读的长度
	 *
	 * 知识点
	 * 1）read读取的是字  符
	 * 2）一个字 符  可能占用1个字节，2个字节，或者3个字节。文件大小是字节数
	 * 3）ASCII   GBK  unicode（JVM用它）  编码
	 * 4）读取的方式与（FileInputStreeam）字节流差不多

 *
 * Writer（抽象类   字符输 出 流） --》 FileWriter（）
 * 构造函数
 * public FileWriter(String fileName)//77行  可选择是否是追加
 * public FileWriter(File file)//也可以选择是否是追加（FileWriter  106行）
 * 底层是new了一个FileOutputStream（字节流的构造函数）对象 ； FileWriter继承了OutputStreamWriter转换流（用来字符转字  节）（往磁盘上写）
 * 
 * 
 * 成员方法
 * public void write(String str)
 * public void write(String str, int off, int len)
 * public void write(char cbuf[])
 * public void write(char cbuf[], int off, int len)//线程安全
 * OutputStreamWriter   193行，可传字符对应的编码值
 *
 *
 * 面试题：
 * 1、谈谈Java IO里面的常见类（如：字节流，   字符流，   它们分别对应的接口/抽象类/实现类   、  读取和写入哪些方法会阻塞）
 * 2、字节流和字符流
 *   概念、使用场景、字符流基于字  节  流实现
 */
public class Teacher_1_22_Char {
    public static void main(String[] args) {
//        //使用字符流拷贝a.txt到c.txt
          //使用缓冲数组拷贝
//        FileReader reader = null;
//        FileWriter writer = null;
//
//        try {
//            //创建字符输入流和输出流对象
//            reader = new FileReader("a.txt");
//            writer = new FileWriter("c.txt");
//
//            int tmp;
//            char[] buf = new char[32];
//            //使用字符输入流读取
//            while((tmp = reader.read(buf)) != -1){//如果没有数据可读，就会卡住，即阻塞
//                //writer.write(buf); //  每次   都会将buf数组的  所有  内容写入。最后一次读取到数据实际可能不足buf.length，也会写后面的空字符，所以不太对
//                writer.write(buf, 0, tmp);//缓冲数组  起始位置  长度    //没有可写的数据，也会阻塞
//            }
//        }catch (FileNotFoundException e){
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                reader.close();
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
  
    	
//        // 拷贝文本文件   a.txt到b.txt
//        //一个字符拷贝
//        FileReader reader = null;
//        FileWriter writer = null;
//
//        try {
//            //创建字符输入流和输出流对象
//            reader = new FileReader("a.txt");
//            writer = new FileWriter("b.txt");
//
////            int tmp;
////            //使用字符输入流读取
////            while((tmp = reader.read()) != -1){//底层读字节，字节转字符，字符再对应编码值，不为-1则写入
////                //使用字符输出流对象写入tmp
////                writer.write(tmp);//write写入一个字符，int（编码值）->查表得对应字符->转为字节写到磁盘
////            }
//                     
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                reader.close();
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    	
    	
    	
//        // 课堂练习：往b.txt写入一些字符  
//        FileWriter writer = null;
//        try {
//            writer = new FileWriter("b.txt",true);
//            writer.write("中国");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                writer.close();//会 刷新内存到磁盘
//                //也可用writer.flush();//将缓冲数组（内存）里的东西刷新到磁盘上    writer180行
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        
//		  // 课堂练习：读取a.txt中的内容并输出
//        FileReader reader = null;
//        try {
//            //创建一个字符输入流
//            reader = new FileReader("a.txt");
//            //调用read读取
////            int read;
////            while((read = reader.read()) != -1){
////                System.out.println((char)read);
////            }
//            char[] chars = new char[32];//因为文件短
//            reader.read(chars);
//            System.out.println(Arrays.toString(chars));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                reader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
