import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mazhuang on 2016/9/10.
 */
public class TraversalMap {
    // see https://github.com/giantray/stackoverflow-java-top-qa/blob/master/contents/iterate-through-a-hashmap.md
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Zero");
        map.put(1, "One");
        map.put(2, "Two");

        TraversalMap.byEntries(map);

        TraversalMap.byKeys(map);

        TraversalMap.byIterator(map);

        TraversalMap.byIteratorWithoutGeneric(map);
    }

    public static void byEntries(Map<Integer, String> map) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            print(entry.getKey(), entry.getValue());
        }
    }

    public static void byKeys(Map<Integer, String> map) {
        for (Integer key : map.keySet()) {
            // traversal keys or values is okay, but find value by key is lowefficiency
            print(key, map.get(key));
        }
    }

    public static void byIterator(Map<Integer, String> map) {
        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) { // byEntries is better
            Map.Entry<Integer, String> entry = iterator.next();
            print(entry.getKey(), entry.getValue());
        }
    }

    public static void byIteratorWithoutGeneric(Map<Integer, String> map) {
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            Integer key = (Integer) entry.getKey();
            String value = (String) entry.getValue();
            print(key, value);
        }
    }

    private static void print(Integer key, String value) {
        System.out.println("" + key + " => " + value);
    }
}
