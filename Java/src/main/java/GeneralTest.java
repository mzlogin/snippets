import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mazhuang on 2016/9/15.
 */
public class GeneralTest {
    public static void main(String[] args) {
        instanceofTest();
    }

    public static void instanceofTest() {
        // null installof class won't crash
        boolean is = (null instanceof String);
        System.out.println("null installof String is " + is);
    }

    public static void wildcardTest() {
        List<?> list = new ArrayList<>();
        list.add(null);
//        list.add("hello");
//        list.add(new String("hello"));
//        list.add(new Integer(1));

        Map<String, List<?>> map = new HashMap<>();
        List<String> stringArr = new ArrayList<>();
        List<Integer> integerArr = new ArrayList<>();
        map.put("string", stringArr);
        map.put("integer", integerArr);

        List<Object> list1 = new ArrayList<>();
        list1.add(null);
        list1.add(new Object());
        list1.add(new String("hello"));
        list1.add(new Integer(1));

        List<? extends Object> list2 = new ArrayList<>();
        list2.add(null);
//        list2.add(new Object());
//        list2.add(new String("hello"));
//        list2.add(new Integer(1));
    }
}
