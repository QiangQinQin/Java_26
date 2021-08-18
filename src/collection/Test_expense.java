package collection;

import java.io.*;
import java.util.*;

/*

一个文本文件记录了1000条数据数据内容需自己初始化产生。
数据内容为部分用户的银行卡消费记录，消费记录信息包含姓名 消费金额.
找出消费次数最多的用户，并且该用户单笔消费最高的金额是多少？
例：文件内容格式
    张三,666
    李四,7893
    张三,9999
获取结果则为：
    张三消费次数最多，最高消费金额为：9999

https://blog.csdn.net/QQ2899349953/article/details/81409685
* */
public class Test_expense {
    //产生数据
    public static void insertData(List<String> users, String path) {
        Random random = new Random();
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
            int i = 0;
            while (i< 1000) {
                String name = users.get(random.nextInt(users.size()));
                int price = random.nextInt(1000) + 1;
                bufferedWriter.write(name+":"+price+"\n");
                bufferedWriter.flush();
                i++;
            }
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //统计出现  次数最多的 用户信息
    public static void serachUser(String path) {
        HashMap<String, Integer> hashMap = new HashMap <>();// 用户 和 消费次数
        HashMap <String, List <Integer>> priceHashMap = new HashMap <>();// 用户  和 消费记录

        PriorityQueue<Map.Entry <String, Integer>> priorityQueue = new PriorityQueue <>(1, new Comparator <Map.Entry <String, Integer>>() {
            @Override
            public int compare(Map.Entry <String, Integer> o1, Map.Entry <String, Integer> o2) {
                return o1.getValue() - o2.getValue(); // 即从大到小排列
            }
        });

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String msg =  null;
            while ((msg=bufferedReader.readLine()) != null) {
                String[] split = msg.split(":");
                String name = split[0];
                int price = Integer.valueOf(split[1]);

                if (hashMap.containsKey(name)) {
                    hashMap.put(name,hashMap.get(name)+1);// 用户 和 消费次数
                    //将当前的一笔 消费记录 存放到 该用户的消费记录列表中priceHashMap
                    List <Integer> list = priceHashMap.get(name);
                    list.add(price);
                    priceHashMap.put(name,list);
                } else {
                    hashMap.put(name,1);
                    ArrayList <Integer> list = new ArrayList <>();
                    list.add(price);
                    priceHashMap.put(name,list);
                }
            }

            //找到消费次数最多的用户
            Iterator <Map.Entry <String, Integer>> iterator = hashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry <String, Integer> entry = iterator.next(); //当前元素
                if (priorityQueue.size() == 0) {
                    priorityQueue.add(entry);
                } else {
                    Map.Entry <String, Integer> entry1 = priorityQueue.peek();
                    if (entry.getValue() > entry1.getValue()) { //比最大元素大
                        priorityQueue.remove();//删除 头
                        priorityQueue.add(entry);
                    }
                }
            }
            //找到消费记录最多的用户名
            Map.Entry <String, Integer> peek = priorityQueue.peek();
            String name = peek.getKey();

            List <Integer> list = priceHashMap.get(name);
            int max = 0;
            for (Integer i:list) {
                if (i > max)  max = i;
            }
            System.out.println(name+" 消费次数最多，最高的消费记录是"+max);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
