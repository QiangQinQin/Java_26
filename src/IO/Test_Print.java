package IO;

import java.io.File;
import java.io.FileFilter;

/**
 * 类似于 Teacher_1_20_IO
 * 要求：
     * 1、指定起始路径下包含指定词的所有文件（非隐藏文件）
     * 2、打印出全路径
 * 例如：
 * 目录结构如下：
 * /user/a/a1.java
 * /user/a/a2.java
 * /user/java/j1.java
 * /user/java/a.txt
 * /user/java/b.mp4
 * /user/file/java11.txt
 * /user/file/.java34.txt
 * <p>
 * 打印/user目录下含Java的文件，搜索结果如下
 * /user/a/a1.java
 * /user/a/a2.java
 * /user/java/j1.java
 * /user/file/java11.txt
 *
 * @param path:指定路径
 * @param filter：过滤关键词
 */

public class Test_Print {
    //作业：给定指定目录，将该目录下的文件按照树形结构打印
    public static void printTree(String filePath) {
        File file = new File(filePath);
//        1、参数合法性校验
        if (!file.exists()) {
            System.out.println("无效的路径");
            return;
        }

        if (file.isFile()) { //文件： 打印层级表示 和 文件名
            System.out.println("|" + file.getName());
        } else { //目录： 进入
            printDir(file, "|-");
        }
     }

    private static void printDir(File file, String suff) {
        System.out.println(suff + file.getAbsolutePath());//拼接上文件的全名
        // 过滤找到 当前目录下的所有非隐藏文件
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return !pathname.isHidden();
            }
        });

        for (File f : files) {
            if (f.isFile()) {
                System.out.println(suff + f.getName()); // 全路径+文件名
            } else {
                //目录
                printDir(f, suff + "-");//有子目录，就层层深入  ， 加前缀表明 当前是第几级目录
            }
        }
    }


    public static void main(String[] args) {
        printTree("E:\\tulun\\java\\Java_26");
    }
}
