package collection;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/*
 * * TreeSet
* set接口的实现  ;基于哈希表和红黑树 ,而哈希表用来保证元素的唯一性、 红黑树保证元素的有序。有序可以是：
* 1）自然顺序
* 2）比较器接口所定义的顺序
* （TreeMap讲过这个顺序，类似）
*/

class Student2{
    private String name;
    private int age;

    public Student2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Student2{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
public class Teacher_1_20_TreeSet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//        System.out.println("TreeSet:自然顺序 =========");
//        //String实现Comparable接口 使得String类型的对象是可比较
//        TreeSet<String> treeset = new TreeSet<>();
//        //添加元素
//        treeset.add("图论");
//        treeset.add("电话");
//        treeset.add("中国");
//
//        //遍历元素，这里默认按照首字母由大到小顺序
//        Iterator<String> itr1 = treeset.iterator();
//        while(itr1.hasNext()){
//            System.out.println(itr1.next());
//        }
//        
//        System.out.println("==========");
//        for(String s: treeset){
//            System.out.println(s);
//        }
//        //删除元素
//        treeset.remove("电话");
//        System.out.println(treeset);
//        
        
        System.out.println("TreeSet:比较器顺序 =========");
        //new的时候传了一个比较器对象
        //传单独的比较器对象 ，实现Comparator接口      
        TreeSet<Student2> treeset1 = new TreeSet<Student2>(   new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                //按照姓名长度由短到长排序
                //如果姓名长度相同按照年龄排序
                int nameDiff = ((Student2)o1).getName().length() -  ((Student2)o2).getName().length();//获得差值
                if(nameDiff == 0 ){
                    return ((Student2)o1).getAge()- ((Student2)o2).getAge();
                }else{
                    return nameDiff;//差值为正，排在前面
                }
            }
        }   );
        //添加元素
        treeset1.add(new Student2("lisi", 22));//add（其实把值存到key里面，value是无效值---在源码254行）
        treeset1.add(new Student2("zhangsan", 28));
        treeset1.add(new Student2("zhaoliu", 23));
        treeset1.add(new Student2("wangwu", 20));
        treeset1.add(new Student2("zhangsan", 18));

        //遍历元素
        Iterator<Student2> itr2 = treeset1.iterator();
        while(itr2.hasNext()){
            System.out.println(itr2.next());
        }
        System.out.println("==========");
        for(Student2 s: treeset1){
            System.out.println(s);
        }
        //删除元素
        treeset1.remove(new Student2("zhangsan", 18));
        System.out.println(treeset1);
        
        
        
	}

}
