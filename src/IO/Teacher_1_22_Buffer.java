package IO;

import java.io.*;

/**
 * 缓冲流
 * 为了提高IO流的读写速度
 * 按照流中内容可以分为字  节  缓冲流和字   符  缓冲流
 *
	 * 字节缓冲输入流: BufferInputStream
	 * 字节缓冲输出流 : BufferOutputStream
 *
     * 字符缓冲流：   BufferedReader和BufferedWriter
 * 缓冲流的内部都有一个缓冲区，通过缓冲区进行读写，所以可以提高IO流的读写速度
 *
 * 课堂练习：拷贝文件
 * 1）采用节点流，一次一个字节拷贝
 * 2）采用节点流，一次多个字节拷贝
 * 3）采用处理流，一次一个字节拷贝
 * 4）采用处理流，一次多个字节拷贝
 *
 * 1）3）
 * 2）4）
 * WPS有以上对应  用户态切换内核态的解释图
 * 
 * BufferedInputStream源码
 * 属性：
 * DEFAULT_BUFFER_SIZE缓冲区默认大小
 * MAX_BUFFER_SIZE最大大小
 * count 计算有效字节数
 * pos当前读取的缓冲区位置索引
 * markpos;缓冲区里的标记位置 （如要在  字节输入流里读取字节到缓冲区  会用)
 * marklimit缓冲区标记的最大值
 * 
 * 方法
 * 156行   private InputStream getInIfOpen() throws IOException   字节缓冲区输入流所需要获取的对应的   字节输入流
 *   
 * 167  private byte[] getBufIfOpen() throws IOException       字节缓冲区输入流所需要获取的  缓冲数组  buffer[]=buf
 * 
 * public BufferedInputStream(InputStream in) {   182行    new缓冲流对象   默认会用到8192的一个数组
        this(in, DEFAULT_BUFFER_SIZE);//198行  ,即buf大小
    }
    
   333   public synchronized int read(byte b[], int off, int len)    读8192的缓冲数组
   276   read1  内部方法,从内存缓冲区buffer读内容，在传给用户自定义byte[] b里，读完了再填充 (减少了用户态到内核态切换  访问磁盘次数)
   213   fill方法    给  缓冲区  数组填内容
 */
public class Teacher_1_22_Buffer {
    // 1.采用节点流，一次一个字节拷贝
    public static void copy1(String src, String dest){
        long start = System.currentTimeMillis();
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);

            //读取数据
            int tmp;
            while((tmp = fis.read()) != -1){
                fos.write(tmp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("采用节点流，一次一个字节拷贝所耗费的时间: "+(end-start));
    }

    //  2.采用节点流，一次多个字节拷贝
    public static void copy2(String src, String dest){
        long start = System.currentTimeMillis();
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);

            byte[] bytes = new byte[256];

            int len = 0;
            while((len = fis.read(bytes)) != -1){
                fos.write(bytes, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("采用节点流，一次多个字节拷贝所耗费的时间: "+(end-start));
    }

    //  3.采用缓冲流，一次一个字节拷贝
    public static void copy3(String src, String dest){
        long start = System.currentTimeMillis();
        //基于节点流创建    缓冲  输入/输出流对象（以 节点 流输入/输出流对象作为参数）
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(src));//bis 是 BufferedInputStream首字母缩写
            bos = new BufferedOutputStream(new FileOutputStream(dest));
            
            int tmp;
            while((tmp = bis.read()) != -1){//读取数据
                bos.write(tmp);//写入到输出流对象 （对象是字节，所以不用强制刷新）
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("采用处理流，一次一个字节拷贝所耗费的时间: "+(end-start));
    }

    //  4.采用处理流，一次多个字节拷贝
    public static void copy4(String src, String dest){
        long start = System.currentTimeMillis();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(src));
            bos = new BufferedOutputStream(new FileOutputStream(dest));

            byte[] bytes = new byte[256];

            int len = 0;
            while((len = bis.read(bytes)) != -1){
                bos.write(bytes, 0, len);//看源码 （用缓冲区）
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("采用处理流，一次多个字节拷贝所耗费的时间: "+(end-start));
    }


    public static void main(String[] args) {
        //copy1("/Users/lvting/Desktop/计算机网络-谢希仁.pdf", "./copy1.pdf");//long long time
        //copy2("/Users/lvting/Desktop/计算机网络-谢希仁.pdf", "./copy2.pdf");//1937毫秒
        //copy3("/Users/lvting/Desktop/计算机网络-谢希仁.pdf", "./copy3.pdf");//2212
        copy4("C:\\Users\\Administrator.DESKTOP-PBDNAQD\\Desktop\\达·芬奇-达·芬奇论绘画.戴勉编译.广西师范大学出版社(2003).pdf", "./copy4.pdf");//272
    }
}
