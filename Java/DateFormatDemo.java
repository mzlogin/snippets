import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

public class DateFormatDemo {
    public static void main(String[] args) {
        BySimpleDateFormat();
    }

    public static void BySimpleDateFormat() {
        // see http://developer.android.com/reference/java/text/SimpleDateFormat.html
        String format = new String("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        System.out.println(sdf.format(new Date()));

        Calendar cal = Calendar.getInstance();
        System.out.println(sdf.format(cal.getTime()));
    }
}
