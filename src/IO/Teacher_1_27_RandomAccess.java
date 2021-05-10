package IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
 * RandomAccessFile的 使用
 * 支持对文件的随机访问（读和写）
 * 
 * 构造函数
 * 121   public RandomAccessFile(String name, String mode)
 * 203   public RandomAccessFile(File file, String mode)
 * 
 * mode:文件的访问模式
	 * r：以只读打开
	 * rw：可读和可写
	 * rws：可读和可写     对文件内容  文件元数据的更新都会 同步到基础存储设备（即底层磁盘或硬盘文件）
	 * rwd：可读和可写     对文件内容   更新后会同步到基础的存储设备
	 * 
	 * 
	 * 
 * void getFilePointer()返回文件记录指针的位置
 * void seek(long pos)文件指针定位到pos位置
 * 
 * 练习：
 *1)随机读取文件中某一部分的数据
 *2)在指定位置去追加一段数据   50   图论教育 
 *
 *
 *RandomAccessFile 和  输入输出流的区别
 *1）RandomAccessFile可以自由访问文件中任意位置  可以访问文件中部分内容
 *2）RandomAccessFile对象进行读写的时候，文件记录的指针自动往后移动
 *3）输出流：只有在flush（刷新）/close（关闭）时文件内容才能同步修改到基础设备
 *  RandomAccessFile以rwd rws方式打开时，文件内容同步修改到基础设备 。注意:更改文件内容是杏立即同步取决于系统底层实现 ！！！
 * 
 */
public class Teacher_1_27_RandomAccess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileInputStream fis =null;//必须把定义写在try外面，不然finally里面不认
		FileOutputStream fos =null;
		RandomAccessFile file =null;
		//RandomAccessFile
		try {
			//在指定位置去追加一段数据
			file= new RandomAccessFile("test.txt","rw");//可读可写
			//移动文件指针
			file.seek((long)(Math.random() * file.length()));
			//获取随机文件指针的位置
			long filepointer=file.getFilePointer();
			System.out.println("init"+filepointer);
//			file.write("图论软件".getBytes());//转化为字节数组    发现把原位置的东西覆盖掉了
//解决方案
//			保存文件指针之后的数据写入一个临时  文件(因为有可能后面多，数组放不下)
//			（如50位置处）追加 
//			将临时文件的数据追加到原文件之后（如在54位置后恢复）
			//可参考Teacher_1_22_Buffer	
			
			//创建临时文件
//			fileName: 临时文件的名字, 生成后的文件名字将会是【fileName + 随机数】
//			suffix： 文件后缀，例如.txt, .tmp
//			parentFile: 临时文件目录，如果不指定，则默认把临时文件存储于系统临时文件目录上 
			File tmp=File.createTempFile("tmp", ".txt");
//			System.out.println("Debug: "+tmp.getAbsolutePath());
			//File tmp=File.createTempFile("tmp", "txt",new File("D:\\\\Tulun\\\\Java_26"));
			
			tmp.deleteOnExit();//在JVM退出时删除文件
			   
			//创建输入、输出流（都是tmp）
			fis=new FileInputStream(tmp);//使用的是否用tmp对象，不要tmp.txt用名字
			fos= new FileOutputStream(tmp );
			//保存文件指针之   后   的数据到临时文件（tmp）中
			byte[] bytes = new byte[256];
			int hasRead = 0;
			while((hasRead = file.read(bytes)) != -1){
				fos.write(bytes,0,hasRead);
			}
			//使用RandomAccessFile对象在读的过程中文件指针往后走(不然他咋知道你每次读到哪儿了)
			System.out.println("after read"+file.getFilePointer());
			//在指定位置追加内容
			file.seek(filepointer);//复位
			file.write("图论软件".getBytes()); //一个汉字3个字节
			//将临时数据的文件(tmp)追加到原文件之后
			System.out.println("after write"+file.getFilePointer());//在写的过程中文件指针往后走，所以不需要移动指针
			while((hasRead = fis.read(bytes)) != -1){
				file.write(bytes,0,hasRead);
			}
			
		
//			//随机读取文件中某一部分的数据
//			File f = new File("test.txt");
//			RandomAccessFile file = new RandomAccessFile(f,"r");
//			file.seek( (long)(Math.random() * f.length()));
//			
//			//获取文件指针的位置
//			System.out.println(file.getFilePointer());
//			byte[ ] bytes = new byte[256];
//			int hasRead = file.read(bytes);//  <=256  可能读不满缓存数组
//			System.out.println(new String(bytes,0,hasRead));
//			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) 
		{
			e.printStackTrace();
		}finally {
			try {
				fis.close();//关闭资源
				fos.close();
				file.close();//保证把内容同步
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

}
