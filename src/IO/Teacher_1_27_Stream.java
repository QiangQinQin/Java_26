package IO;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Stream （和输入输出的流不一样，jdk1.8之后出现,函数作为某个方法的参数）
 * 
 * 使用函数式编程模式，对集合进行操作
 *
 * 1）中间操作    会使用流方法再次返回一个流，我们可以链接多个中间操作，同时不用加分号
 * 2）终端操作    会流操作的结束动作，返回一个非流的结果 forEach（本页最后几行）
 *
 * Stream用来计算数据 ，主要和CPU打交道
 * 而Collection负责存储数据， 主要和内存打交道
 *
 * Stream操作步骤
 * 1）获取一个stream
 * 2) 使用stream操作数据  ：中间操作的中间链    和 一个终止操作(执行完中间操作的中间链返回最终结果)
 */
//自定义类型
class Student{
    private Integer id;
    private String name;
    private Integer age;
    private Double score;

    public Student(Integer id, String name, Integer age, Double score) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Double getScore() {
        return score;
    }

    //自动生成的
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) &&
                Objects.equals(name, student.name) &&
                Objects.equals(age, student.age) &&
                Objects.equals(score, student.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, score);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }
}
public class Teacher_1_27_Stream {
	
    public static List<Student> getStudents(){
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(1, "小白", 25, 89.5));
        students.add(new Student(2, "小李", 18, 99.5));
        students.add(new Student(3, "小蔡", 20, 69.5));
        students.add(new Student(4, "小花", 22, 55.5));
        students.add(new Student(5, "小名", 23, 90.5));
        students.add(new Student(3, "小蔡", 20, 69.5));  //不会覆盖
        return students;
    }
    
    public static void main(String[] args) {
        //步骤1创建stream
        //方法1：通过一个集合的stream方法创建流
        List<Student> students = getStudents();
        System.out.println(students);

        Stream<Student> stream = students.stream();
        //方法2：Stream.of
        Stream<Student> stream1 = Stream.of(new Student(1, "小白", 25, 89.5),
                  new Student(2, "小李", 18, 99.5),
                  new Student(3, "小蔡", 20, 69.5));
        //方法3：通过数组创建stream
        Student[] studentsArr = (Student[]) students.toArray(new Student[students.size()]);//转化为数组，并强转
        Stream<Student> stream2 = Arrays.stream(studentsArr);//数组转化为流
        
        
        
        //步骤2：Stream操作数据
        /**
         * filter： 接受一个lambda表达式，从流中进行过滤
         * limit: 截断流，筛选出前N条数据
         * skip： 跳过 前N个 元素
         * distinct: 筛选，（通过流生成元素的hashCode() 和 equals()  去除重复的元素）
         */
        //过滤出年龄小于22的同学
        stream.filter(student -> student.getAge() < 22)//参数是随便的。student 或 stu 都可以
               .forEach(System.out::println); //foreach执行完，流就关闭了
        stream.limit(3).forEach(System.out::println); //stream has already been operated upon or closed
        stream.skip(2).forEach(System.out::println);
        stream.distinct().forEach(System.out::println);

        /**
         * map
         *  接受lambda表达式
         *  可将元素转换为其他形式，接受一个函数作为参数，该函数会被应用到流里的每个元素上，并且映射成一个新元素
         **/
        List<String> list = Arrays.asList("djh", "wkjd", "c3",  "b1", "c1", "c2");//数组转化为list
        //获取集合的流
        list.stream()  //需要函数作为参数，就可以用lamuda表达式
                .filter(  (s) -> s.startsWith("c")  ) //执行过滤，过滤出以c开头的字符串 c3 c1 c2
                .map(  (s) -> s.toUpperCase()  ) // 作用在流里c1 c2 c3每个元素身上，执行转大写操作  C3 C1 C2
                .sorted() //排序  //C1 C2 C3
                .forEach(System.out::println); //for循环打印        看源码，表示需要传一个方法
        /**
         * sorted ->自然排序
         * sorted -> 定制排序
         */
//        根据成绩从小到大进行排序
        stream.sorted( (s1, s2)-> (int) (s1.getScore()-s2.getScore()) )
                          .forEach(System.out::println);//strea里的s1 s2,传一个比较器

        /**
         *  终端 操作
         * 匹配和查找的方法
         * allMatch 检查是否匹配所有元素
         * anyMathch 检查是否至少匹配一个元素
         * noneMath 检查是否没有匹配所有元素
         * findFirst 返回第一个元素
         * count 返回流中元素的总个数
         * max 返回流中最大值
         * min 返回流中最小值
         */
        boolean b1 = stream.allMatch(student -> student.getAge() > 20);
        System.out.println(b1);
        System.out.println(stream.findFirst());
        System.out.println(stream.count());
        System.out.println(stream.max((s1, s2)-> (int)(s1.getScore()-s2.getScore())));//传比较方法
    }
}


             

