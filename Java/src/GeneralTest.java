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
}
