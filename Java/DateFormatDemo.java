import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

public class DateFormatDemo {
    public static void main(String[] args) {
        formatDemo();
        dateCalc();
    }

    public static void formatDemo() {
        // see http://developer.android.com/reference/java/text/SimpleDateFormat.html
        String format = new String("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        System.out.println(sdf.format(new Date()));

        Calendar cal = Calendar.getInstance();
        System.out.println(sdf.format(cal.getTime()));

        // Month and day string
        System.out.println(getMonthAndDay(cal));
    }

    public static String getMonthAndDay(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd", Locale.US);
        String monthAndDay = sdf.format(cal.getTime());
        return monthAndDay;
    }

    public static void dateCalc() {
        // ����
        Calendar base = Calendar.getInstance();
        // �����趨Ϊĳ��
        base.set(2015, 3 - 1, 1);  // �·ݴ�0��ʼ����3��Ҫд2��

        // ����
        Calendar monday = DateCalcUtil.calc(base, DateCalcUtil.GET_THIS_MONDAY);
        Calendar sunday = DateCalcUtil.calc(base, DateCalcUtil.GET_THIS_SUNDAY);

        // ����
        Calendar nextMonday = DateCalcUtil.calc(base, DateCalcUtil.GET_NEXT_MONDAY);
        Calendar nextSunday = DateCalcUtil.calc(base, DateCalcUtil.GET_NEXT_SUNDAY);

        // ����
        Calendar previousMonday = DateCalcUtil.calc(base, DateCalcUtil.GET_PREVIOUS_MONDAY);
        Calendar previousSunday = DateCalcUtil.calc(base, DateCalcUtil.GET_PREVIOUS_SUNDAY);
    }

    static class DateCalcUtil {
        public static final int GET_PREVIOUS_MONDAY = 0;
        public static final int GET_PREVIOUS_SUNDAY = 1;
        public static final int GET_THIS_MONDAY = 2;
        public static final int GET_THIS_SUNDAY = 3;
        public static final int GET_NEXT_MONDAY = 4;
        public static final int GET_NEXT_SUNDAY = 5;

        public static Calendar calc(Calendar base, int calcType) {

            int min = base.getActualMinimum(Calendar.DAY_OF_WEEK); // ��ȡ��һ�ܿ�ʼ��׼
            int current = base.get(Calendar.DAY_OF_WEEK); // ��ȡ��������һ��������
            Calendar calendar = (Calendar)base.clone();

            int nCount = (current == min) ? -6 : (min - current + 1);

            switch (calcType) {
                case GET_PREVIOUS_MONDAY:
                    nCount -= 7;
                    break;

                case GET_PREVIOUS_SUNDAY:
                    nCount -= 1;
                    break;

                case GET_THIS_MONDAY:
                    break;

                case GET_THIS_SUNDAY:
                    nCount += 6;
                    break;

                case GET_NEXT_MONDAY:
                    nCount += 7;
                    break;

                case GET_NEXT_SUNDAY:
                    nCount += 13;
                    break;

                default:
                    break;
            }

            calendar.add(Calendar.DAY_OF_WEEK, nCount);
            
            return calendar;
        }
    }
}
