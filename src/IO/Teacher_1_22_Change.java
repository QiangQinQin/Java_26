package IO;

import java.io.*;

/**
 * 转换流
	 * InputStreamReader
	 * 字节流（Stream）通向字符(reader)流的桥梁          使用指定的   编码表   读取到字 节 后会将其解析为字  符 
	 * OutputStreamWriter
	 * 字符流通向字节流的桥梁          使用指定的编码表将字符 转为字  节 ，使用字节流将这些字节写入
 *
 * 处理流
 *    基于节点流(与数据源直接相联)之上做了一层封装
 */
public class Teacher_1_22_Change {
    public static void main(String[] args) {
        //字符转为字节
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            //节点流
            fos = new FileOutputStream("b.txt");
            //处理流
            osw = new OutputStreamWriter(fos, "utf-8");
            osw.write("你好");//写入字符，转成字节写入
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                osw.close();//先关闭转换流
                fos.close();//再关闭写的流
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        //字  节  转字   符（可以指定编码方式）
//        FileInputStream fis = null;
//        InputStreamReader isr = null;
//
//        try {
//            //创建节点流
//            fis = new FileInputStream("a.txt");
//            //创建处理流
//            isr = new InputStreamReader(fis, "utf-8");//Utf-8 一个中文占3个字节            GBK中文占两个字节
//
//            int tmp;
//            while((tmp = isr.read()) != -1){
//                System.out.println((char)tmp);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fis.close();
//                isr.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
    }
}
