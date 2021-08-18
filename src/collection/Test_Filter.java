package collection;

import java.io.File;
import java.io.FileFilter;

//文件过滤器
class MyFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        // 有可能是文件 或 目录     Teacher_1_20_IO    printJavaFile
        return pathname.getName().endsWith(".txt")&&pathname.isFile();
    }

}

public class Test_Filter {
    public static void main(String[] args) {
        File file = new File("E:\\tulun\\java\\Java_26");
        File[] listFiles = file.listFiles(new MyFilter());
        for(File fi:listFiles) {
            System.out.println(fi.getName());
        }
    }
}
