import java.util.*;

/**
 * Created by mazhuang on 2016/9/13.
 */
public class CollectionsOperate {
    public static void main(String[] args) {
        deleteFromMap();
        deleteFromList();
    }

    public static void deleteFromMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "Zero");
        map.put(1, "One");
        map.put(2, "Two");

        // ok, but not in traversal
        map.remove(1);

        // HashMap doesn't implements Interable interface, so cannot use for-each directly
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            // error! java.util.ConcurrentModificationException if not break
            // map.remove(entry.getKey());
            // break;
        }

        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            // the only right way to remove element from collections when traversal
            iterator.next();
            iterator.remove();
        }
    }

    public static void deleteFromList() {
        String[] array = {"Hello", "World"};
        List<String> list = Arrays.asList(array); // !!! Arrays$ArrayList<>, it's different from ArrayList<>

        for (String str : list) {
            // error! java.lang.UnsupportedOperationException
            // Arrays$ArrayList<>.remove not implemented
            // list.remove(0);
        }

        list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        list.add("Hi");

        for (String str : list) {
            // error! java.util.ConcurrentModificationException if not break
            // list.remove(str);
            // break;
        }

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            // java.lang.IllegalStateException without iterator.next() before
            iterator.remove();
        }
    }
}
