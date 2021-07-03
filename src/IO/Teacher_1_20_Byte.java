package IO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * 字节流
 *
 * IO是  目的 ，实现这一目的需要一种机制，这个机制叫做  流机制
 * 1)、概念
 * 数据流中传输的数据是  字节
 * 字节流有输入流InputStream 和  输出流OutputStream两大类
 * 输入流：读取数据
 * 输出流：写入数据
 *
 * 2) 输入流 :InputStream(是父类，也是抽象类，定义了部分方法的实现) --》FileInputStream（具体实现类，实现了所有InputStream未实现的方法）
 * 主要构造函数
 * public FileInputStream(String name)
 * public FileInputStream(File file) //提供读取文件数据的方法     同时    在读取文件数据之前，把文件打开
 *
 * 成员方法（重载）
 * public int read()//每次读取一个字节   ，返回下一个字节转为int的数据，如果文件结束，返回-1
 * public int read(byte b[])//返回的是读的字节长度，-1表示文件结束 
 * 每次最多读b.length个字节长度的     文件在磁盘（共享单车    访问磁盘，是由用户态（程序员写出来的代码，运行的状态就叫）转为内核态（从内存访问到磁盘，需要内核配合）的过程） 
 * 程序要读内存（跑车）   byte数组作为缓存，是在内存里
 * public int read(byte b[], int off, int len)//起始偏移量    最大读的字节数     追加操作
 *
 * 知识点：
 * 1）FileInputStream用于读取文件数据，可以在构造函数中传入文件对象，也可以传入文件名， 但是有可能抛出FileNotFoundException
 * 2）read方法使用时
 * a.存在3个重载方法，read每次读取一个字节，read(byte[] b)每次读取b.length个字节， read(byte b[], int off, int len)每次读取len个字节
 * b.有可能在  读取过程  中抛出异常，比如文件某部分数据损坏
 * c.如果读取读到-1就表示已经读到了文件末尾
 *
 * 面试题：
 * 为什么read()读取的是一个字节（8位），要返回int，而不是byte
 * 
 * 文件的中间部分的二进制里刚好有8个1，等于-1，但文件不应该结束，所以前面要补24个0，转为int,即（0000 0000   0000 0000   0000 0000   1111 1111）
 * int的-1为1111 1111   1111 1111   1111 1111   1111 1111表示文件结束
 * 
 * 
 * 3）输出流 OutputStream--》FileOutputStream
 * FileOutputStream用来写入    图片类/视频类   原始 字节流
 * 构造方法
 *  public FileOutputStream(String name)//文件名
 *  public FileOutputStream(File file)文件对象
 *  public FileOutputStream(String name, boolean append)  true为追加；false为覆盖
 * 成员方法
 * public void write(int b)  写一个字节
 * private native void write(int b, boolean append)
 * public void write(byte b[])缓存数组
 * public void write(byte b[], int off, int len)起始位置
 * 
 * */
public class Teacher_1_20_Byte {
    public static void main(String[] args) {
   //写 	
      FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("b.txt");//如果不存在 输出流对象会    自动创建该文件 
            byte[] bytes = {100, 101, 102, 103};
            fos.write(105);//给b.txt写数据    都变成ASCII对应的字符
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();//关闭流对象（相当于1个资源）！！！！！
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        ////给定a.txt，读取a.txt中所有数据并输出
//        try {
//            FileInputStream inputStream = new FileInputStream("testSynchronousQueue.txt");//在Java_26下，和src同级
//
////10 ： 是 LF 即 "\n"   13：是CR 即 "\r"
////在不同的操作系统中，表示的方式是不一样的。在UNIX系统中，换行符使用"\n" , 在 windows 系统中换行使用 "\r\n"，算两个byte; 
//            //一个一个字节读取
//            int tmp;
//            while((tmp = inputStream.read()) != -1){
//                System.out.println(tmp);
//            }          
//            
////            //使用缓存数组
////            int tmp;
////            byte[] bytes = new byte[256];
////            tmp = inputStream.read(bytes);//返回buffer中读取的字节数
////            System.out.println(tmp);
////            System.out.println(Arrays.toString(bytes));//ASCII值   无效值默认为0
//        } catch (FileNotFoundException e) {//文件不存在，抛异常
//            e.printStackTrace();
//        } catch (IOException e) {//文件某部分损坏
//            e.printStackTrace();
//        }
    }
}
