package IO;

import java.io.*;
import java.util.Arrays;

//课堂练习：拷贝图片或音频


public class Teacher_1_20_ByteCopy {
	//方法一：一个字节一个字节拷贝   并且   打印拷贝所耗时间
    public static void copyByOntByte(File file1, File file2){
        long startTime = System.currentTimeMillis();//计时
        if(file1 == null || !file1.exists() || file2 == null){
            return;
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            //创建输入流对象file1读取数据 创建输出流对象file2写入数据
            fis = new FileInputStream(file1);
            fos = new FileOutputStream(file2);

            //一个一个字节拷贝
            int tmp;
            //输入流对象读一个字节
            while((tmp = fis.read()) != -1){
                //输出流对象写一个字节
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
        long endTime = System.currentTimeMillis();
        System.out.println("一个字节一个字节贝拷贝所耗费的时间： "+(endTime-startTime));
    }

    //方法二：获取当前图片的大小，直接创建一个对应大小的字节数组，使用缓存数组拷贝  并且  打印拷贝所耗时间    	 
    public static void copyByOntByteArray(File file1, File file2) {//传文件对象
        long startTime = System.currentTimeMillis();
        if (file1 == null || !file1.exists() || file2 == null) {
            return;
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            //创建输入流对象读取数据 创建输出流对象写入数据
            fis = new FileInputStream(file1);
            fos = new FileOutputStream(file2);
            int size = fis.available(); //以字节为单位返回文件的长度
            byte[] bytes = new byte[size];
            //输入流对象读取数据到缓存数组中
            fis.read(bytes);
            //输出流写入缓存数组中的数据到输出流对象中
            fos.write(bytes);
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
        long endTime = System.currentTimeMillis();
        System.out.println("使用缓存数组拷贝所耗费的时间： "+(endTime-startTime));
    }

    public static void main(String[] args) {
        File file1 = new File("1.jpg");//原文件
        File file2 = new File("2.jpg");
        copyByOntByte(file1, file2);
        
        File file3 = new File("3.jpg");
        copyByOntByteArray(file1, file3);
    }
}
