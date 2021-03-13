package concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author mazhuang
 * @date 2021/3/13
 */
public class ConcurrentTest {
    public static void main(String[] args) {
//        List<Integer> resultList = new ArrayList<>();
        List<Integer> resultList = Collections.synchronizedList(new ArrayList<>());
        List<Integer> paramList = new ArrayList<>();
        int length = 10000;
        for (int i = 0; i < length; i++) {
            paramList.add(i);
        }
        final Object o = new Object();
        paramList.parallelStream().forEach(v -> {
//            synchronized (o) {
                resultList.add(v);
//            }
        });
    }
}
