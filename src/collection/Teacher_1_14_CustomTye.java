package collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 使用自定义类型作为HashMap的Key
 * 重写hashCode和equals
 */
/**
 * K;千      M：百万      G：十亿
* HashMap思想  用来处理海量数据问题  （不容算法、哈希、归并与堆排序、分治思想）
*
* 1、海量日志数据，提取出某日访问百度次数最多的那个IP（32位） -》共2^32（即4G）种ip地址         
* hash(ip)%1000     1000个小文件
* 0，1，2，..., 999 把数据加载到内存中，找出每一个ip最大,
* HashMap<ip, count> 1000个小文件中出现频度的ip
* 1000个ip最大找Top1 （建大根堆，O（nlogn））
*
* 2、给定a、b 两  个文件，各存放50亿个url，每个url各占64字节， 内存限制是4G，让你找出a、b文件共同的url
* -》预估每个文件大小: 5G*每个url占64 = 一共占320G，不可能全部加载到内存当中
*步骤1： 每个大文件分为1000个  小文件    hash(url)%1000
*      a-> a0,a1,a2,...,a999
*      b-> b0,b1,b2,...,b999
*注意:相同url对应到 相同 的 小文件 中，a0的哈希    和  b0的哈希相同,....
*   对每个小文件逐个去找相同的url,HashSet(key, value)
*                        HashSet基于HashMap实现，key是 有效值（不允许重复），value是一个 常 量(key重复，新值覆盖旧值)
*步骤2: a0  全放入一个 HashSet
*      b0去逐个遍历HashSet,若发现key已经有了，就是相同的URL
*/
class Student{
    private String name;
    private int age;

    public Student(){

    }
    public Student(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;//和自己
        if (o == null || getClass() != o.getClass()) return false;//一个为空 或 类型 不一样
        Student student = (Student) o;
        return this.age == student.age &&
                this.name.equals(student.name);
    }


    //putVal要用，同样的key(张三)，新值覆盖旧值（tulun111）
    @Override
    public int hashCode() {
        if (name != null ){
            return name.hashCode() + age;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
public class Teacher_1_14_CustomTye {
    public static void main(String[] args) {
    	
    	//不重写equals方法，会默认使用Object的equals逻辑，比的 是地址==
        //hash函数会调用  自己已重写的HashCode生成hash值      HashMap源码339行        306行equals
    	//HashMap源码635行putVal新值覆盖旧值逻辑： e.hash==hash && (e.key == key || e.key.equals(key))
        HashMap<Student, String> map = new HashMap<>();
        map.put(new Student("张三", 20), "hdjhshjh");
        map.put(new Student("张三", 20), "tulun111");
        //张三新值覆盖旧值？  两次new,是不同对象，有不同地址，hash值不一样
      
        //String和Integer就重写了equals方法
      
        //HashMap
        
        map.put(new Student("李四", 25), "jdshjd");
        map.put(new Student("王五", 22), "teiehff");
        map.put(new Student("小明", 18), "okeejjej");
        map.put(new Student("小李", 19), "fds1");
        Iterator< Map.Entry<Student, String> > itr= map.entrySet().iterator();//map里的entrySet()方法 （会返回所有结点组成的Set集合）重写了iterator();
        while(itr.hasNext()){
            Map.Entry<Student, String> next = itr.next();
            System.out.println(next.getKey()+":: "+next.getValue());
        }
    }
}


