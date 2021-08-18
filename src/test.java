import java.io.FilterInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author QiangQin
 * * @date 2021/8/18
 */
public class test {
    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add("java");
        list.add("aaa");
        list.add("java");
        list.add("java");
        list.add("bbb");

//        list.removeAll("java");
//        list.remove("java");
//        for(int i=0;i<list.size();i++){
        for(int i=list.size()-1;i>=0;i--){
            if("java".equals(list.get(i)))
                list.remove(i);
        }
        System.out.println(list);
    }

//    List<Integer> a;
//new Map<Integer,Integer>;
//    new Set<Integer>
//    FilterInputStream
}
