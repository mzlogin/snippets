package memcache;

/**
 * Created by mazhuang on 2017/5/18.
 */
public class Main {
    public static void main(String[] args) {

        String userId = "383";

        // will return null
        Object result = MemCache.getInstance().query(userId);

        System.out.println("result is " + result);

        MemCache.getInstance().set(userId, "hello, 383");

        // will return "hello, 383" and set it into memcache
        result = MemCache.getInstance().query(userId);

        System.out.println("new result is " + result);

        MemCache.releaseInstance();
    }
}
