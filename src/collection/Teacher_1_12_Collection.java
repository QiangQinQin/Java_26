package collection;

import java.util.*;


/*
 * 集合框架库
 */
public class Teacher_1_12_Collection {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //Collection<Integer> coll=new ArrayList<>();//Collection是父接口
        Collection<Integer> coll = new LinkedList<>();
        coll.add(10);
        coll.add(20);
        coll.add(30);
        System.out.println(coll.remove(10));
        System.out.println(coll.contains(10));//返回布尔值
        System.out.println(coll.isEmpty());
        System.out.println(coll.size());

        Collections.sort(Arrays.asList(10,30,20));
        Iterator<Integer> iterator = coll.iterator();//返回迭代器对象
        //利用迭代器对象遍历当前的集合
        //不知道他是数组时，就可以用迭代器将他遍历
        while (iterator.hasNext()) {
            //判断当前迭代对象ArrayList集合是否含有下一个可迭代的对象
            Integer next = iterator.next();
            System.out.println(next);
            //iterator.remove(); //即输完就把该next删除
        }

//		Collections是一个工具类，sort是其中的静态方法，是用来对List类型进行排序的
// https://www.cnblogs.com/yw0219/p/7222108.html?utm_source=itdadao&utm_medium=referral
       // 1.基本类型（String  Integer 已经实现Comparable接口）
        List<Integer> intList = Arrays.asList(2, 3, 1);
        Collections.sort(intList);// 默认是由小到大的正序

        // 自定义比较器
        Collections.sort(intList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                // 返回值为int类型，大于0表示正序，小于0表示逆序
                return o2 - o1;
            }
        });

       // 2.自定义数据类型（需要类继承继 Comparable接口 ）
        List<Emp> empList;
        Emp emp1 = new Emp(2, "Guan YunChang");
        Emp emp2 = new Emp(3, "Zhang Fei");
        Emp emp3 = new Emp(1, "Liu Bei");
        empList = Arrays.asList(emp1, emp2, emp3);// 讲元素为(emp1, emp2, emp3的数组 转换成 List，并用 emplist指向

        Collections.sort(empList, new Comparator<Emp>() {
            @Override
            public int compare(Emp o1, Emp o2) {
                /*按员工编号正序排序*/
                return o1.getEmpno() - o2.getEmpno();
                /*按员工编号逆序排序*/
                //return o2.getEmpno()-o1.getEmpno();
                /*按员工姓名正序排序*/
                //return o1.getEname().compareTo(o2.getEname());
                /*按员工姓名逆序排序*/
                //return o2.getEname().compareTo(o1.getEname());
            }
        });
    }

    //继承Comparable接口 并 重写compareTo方法
    public static class Emp implements Comparable<Emp> {
        private int empno;
        private String ename;

        public int getEmpno() {
            return empno;
        }

        public void setEmpno(int empno) {
            this.empno = empno;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public Emp(int empno, String ename) {
            super();
            this.empno = empno;
            this.ename = ename;
        }

        @Override
        public String toString() {
            return "empno:\t" + empno + "\tename:\t" + ename;
        }

        @Override
        public int compareTo(Emp emp) {
            /*按员工编号正序排序*/
            //return this.getEmpno()-emp.getEmpno();
            /*按员工编号逆序排序*/
            //return emp.getEmpno()-this.getEmpno();
            /*按员工姓名正序排序*/
            //return this.getEname().compareTo(emp.getEname());
            /*按员工姓名逆序排序*/
            return emp.getEname().compareTo(this.getEname());
        }
    }
}
