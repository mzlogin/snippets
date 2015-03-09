import java.util.regex.Pattern;

public class RegexDemo {
    public static void main(String[] args) {
        RegexTest();
    }

    public static void RegexTest() {
        final String REGEX_STR = "^android$";
        final Pattern PATTERN = Pattern.compile(REGEX_STR);

        boolean isFind = PATTERN.matcher("android").find();

        System.out.println(isFind);
    }
}
