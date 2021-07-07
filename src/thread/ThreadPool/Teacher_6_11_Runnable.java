package thread.ThreadPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author QiangQin
 * * @date 2021/7/5
 */
public class Teacher_6_11_Runnable implements  Runnable {//实现接口里的方法
    @Override
    public void run() {
//        System.out.println(Thread.currentThread().getName()+"子线程正在执行");

//        System.out.println(Thread.currentThread().getName()+"子线程开始执行");
//        try {
//            Thread.sleep(new Random().nextInt(1000));//让当前线程睡眠一个随机时间，模拟处理业务
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(Thread.currentThread().getName()+"子线程结束执行");

        System.out.println(Thread.currentThread().getName()+"线程执行时间"+ refFormatNowDate() );
    }

    /*
    String.valueOf(System.currentTimeMillis())
    * */

    public String refFormatNowDate() {
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy年-MM月-dd日-HH时-mm分-ss秒-SS毫秒");
        String retStrFormatNowDate = sdFormatter.format(nowTime);

        return retStrFormatNowDate;
    }
}
