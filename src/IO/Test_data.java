package collection;

import java.util.*;

/**
     统计十万数据出现的次数，第一个重复的数据，出现次数最多的数据，重复性元素只打印一次
     https://www.cxyzjd.com/article/weixin_43356265/88077067

 类比课堂练习：
 随机生成1万个数据(random)保存下来(数据范围：0~1000)
     1、统计每个数据出现的次数
     2、按照数据出现的次数多少从多往少排序：数字2，出现10次
     3、将数据按照生成的先后顺序进行打印且数据不能重复

 */
public class Test_data {
    public static void main(String[] args) {

        //1、统计数据重复出现次数并打印
        //1.1 生成10万数据
        ArrayList<Integer> arrayList = new ArrayList<Integer>(10000000);
        HashMap<Integer,Integer> hashMap = new HashMap<Integer,Integer>();
        Random random = new Random();
        //随机产生数据
        for (int i = 0; i < 100000; i++){
            //存入arrayList中
            arrayList.add(random.nextInt(100)+1);
        }
        System.out.print(arrayList.toString());

         // 1.2 将arraylist的所有元素存入hashmap（键不可重复）中，进行遍历打印
        Iterator<Integer> iterator =  arrayList.iterator();
        while (iterator.hasNext()) {
            //先将arrayLis中的数据按顺序存入hashmap中
            Integer key = iterator.next();//定义键
            //putIfAbsent
            if (hashMap.containsKey(key)){
                //遍历键是否存在
                hashMap.put(key,hashMap.get(key)+1);//有相同的键时，值+1
            }else{
                hashMap.put(key,1);//没有相同的时，值置为1
            }
        }
        //迭代器的使用
        Iterator<Map.Entry<Integer,Integer>> iterator1 = hashMap.entrySet().iterator();
        while (iterator1.hasNext()){

            Map.Entry<Integer,Integer> next = iterator1.next();//重新定义一个节点
            Integer key = next.getKey();//得到key值
            Integer value = next.getValue();//得到value值
            System.out.println(key+":"+value);
        }



         //  2、找出第一个重复的数据是什么并打印
        // 遍历数组，放到HashSet,找到第一个重复的数据进行打印
        HashSet<Integer>hashSet = new HashSet<Integer>();
        Iterator<Integer>iterator2 = arrayList.iterator();
        while (iterator2.hasNext()){
            //与for（int i= 0;iterator2.hasNext();i++）一样
            Integer a = iterator2.next();
            if (hashSet.contains(a)){
                //遍历到a
                System.out.println("第一次重复出现的元素"+a);
                break;
            }else{
                //没有遍历到a
                hashSet.add(a);//将a存入，继续循环
            }
        }


        // 3. 找出 出现次数最多的数据并打印
        Iterator<Map.Entry<Integer,Integer>> iterator3 = hashMap.entrySet().iterator();
        int key = 0;//定义两个变量key，value用来存放目标值
        int value = 0;
        while (iterator3.hasNext()){
            Map.Entry<Integer,Integer> next = iterator3.next();//定义新的节点
            Integer a = next.getKey();//获取节点的key值
            Integer b = next.getValue();//获取节点的value值
            if (b > value){
                //进行比较
                key = a;
                value = b;
            }
        }
        System.out.println("出现次数最多的数是："+key);


        //4. 打印全部数据，重复性元素只打印一次
        Iterator<Integer> iterator4 = hashMap.keySet().iterator();
        while (iterator4.hasNext()){
            //只打印key值，根据键不重复的特性
            Integer a = iterator4.next();
            System.out.println(a+" ");
        }
    }
}
