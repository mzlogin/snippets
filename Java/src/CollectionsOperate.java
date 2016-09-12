import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mazhuang on 2016/9/13.
 */
public class CollectionsOperate {
    public static void main(String[] args) {
        deleteFromMap();
    }

    public static void deleteFromMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "Zero");
        map.put(1, "One");
        map.put(2, "Two");

        // ok, but not in traversal
        map.remove(1);

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            // error! java.util.ConcurrentModificationException
            // map.remove(entry.getKey());
        }

        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            // the only right way to remove element from collections when traversal
            iterator.next();
            iterator.remove();
        }
    }
}
