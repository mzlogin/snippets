import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexDemo {
    public static void main(String[] args) {
        RegexTest();

        indexOfRegexTest();
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

    public static void indexOfRegexTest() {
        Pattern PATTERN = Pattern.compile("\\w");
        String src = "你1好a";

        Matcher matcher = PATTERN.matcher(src);

        if (matcher.find()) {
            System.out.println(String.format("%d, %d, %s", matcher.start(), matcher.end(), src.substring(matcher.start(), matcher.end())));
        }
    }
}
