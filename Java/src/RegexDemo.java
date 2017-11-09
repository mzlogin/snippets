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

        final String pathRegex = "^.*_\\d{12,14}\\.sqlite$";
        Pattern ptn = Pattern.compile(pathRegex);
        isFind = ptn.matcher("/user/local/code_201806231119.sqlite").find();
        System.out.println(isFind);
        isFind = ptn.matcher("/user/local/code_20180623111932.sqlite").find();
        System.out.println(isFind);
        isFind = ptn.matcher("/user/local/code20180623111932.sqlite").find();
        System.out.println(isFind);
        isFind = ptn.matcher("/user/local/code_2018062311.sqlite").find();
        System.out.println(isFind);
        isFind = ptn.matcher("/user/local/code_201806231119asqlite").find();
        System.out.println(isFind);
        isFind = ptn.matcher("/user/local/code_20180623134529.sqlite-journal").find();
        System.out.println(isFind);
    }
}
