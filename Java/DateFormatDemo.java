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

        // Month and day string
        System.out.println(getMonthAndDay(cal));

        // 今天
		Calendar base = Calendar.getInstance();
        // 可以设定为某天
        base.set(2015, 3 - 1, 1);  // 月份从0开始，设3月要写2月
		int min = base.getActualMinimum(Calendar.DAY_OF_WEEK); // 获取本周开始基准
		int current = base.get(Calendar.DAY_OF_WEEK); // 获取当天在本周内天数

        // 本周
		Calendar thisWeekStart = (Calendar)base.clone();
		thisWeekStart.add(Calendar.DAY_OF_WEEK, (current == min) ? -6 : (min - current + 1));	// 周开始日期
		
		Calendar thisWeekEnd = (Calendar)base.clone();
		thisWeekEnd.add(Calendar.DAY_OF_WEEK, (current == min) ? 0 : (7 - current + 1));		// 周结束日期

        // 下周
		Calendar nextWeekStart = (Calendar)base.clone();
		nextWeekStart.add(Calendar.DAY_OF_WEEK, ((current == min) ? -6 : (min - current + 1)) + 7);	// 周开始日期
		
		Calendar nextWeekEnd = (Calendar)base.clone();
		nextWeekEnd.add(Calendar.DAY_OF_WEEK, ((current == min) ? 0 : (7 - current + 1)) + 7);		// 周结束日期

        // 上周
		Calendar preWeekStart = (Calendar)base.clone();
		preWeekStart.add(Calendar.DAY_OF_WEEK, ((current == min) ? -6 : (min - current + 1)) - 7);	// 周开始日期
		
		Calendar preWeekEnd = (Calendar)base.clone();
		preWeekEnd.add(Calendar.DAY_OF_WEEK, ((current == min) ? 0 : (7 - current + 1)) - 7);		// 周结束日期
    }
    
    public static String getMonthAndDay(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd", Locale.US);
        String monthAndDay = sdf.format(cal.getTime());
        return monthAndDay;
    }
}
