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

        // ����
		Calendar base = Calendar.getInstance();
        // �����趨Ϊĳ��
        base.set(2015, 3 - 1, 1);  // �·ݴ�0��ʼ����3��Ҫд2��

        // ����
        Calendar monday = DateCalcUtil.calc(base, DateCalcUtil.CalcType.GET_THIS_MONDAY);
        Calendar sunday = DateCalcUtil.calc(base, DateCalcUtil.CalcType.GET_THIS_SUNDAY);

        // ����
        Calendar nextMonday = DateCalcUtil.calc(base, DateCalcUtil.CalcType.GET_NEXT_MONDAY);
        Calendar nextSunday = DateCalcUtil.calc(base, DateCalcUtil.CalcType.GET_NEXT_SUNDAY);

        // ����
        Calendar previousMonday = DateCalcUtil.calc(base, DateCalcUtil.CalcType.GET_PREVIOUS_MONDAY);
        Calendar previousSunday = DateCalcUtil.calc(base, DateCalcUtil.CalcType.GET_PREVIOUS_SUNDAY);
    }

    public static String getMonthAndDay(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd", Locale.US);
        String monthAndDay = sdf.format(cal.getTime());
        return monthAndDay;
    }

    static class DateCalcUtil {
        public static enum CalcType { 
            GET_PREVIOUS_MONDAY,
            GET_PREVIOUS_SUNDAY,
            GET_THIS_MONDAY,
            GET_THIS_SUNDAY,
            GET_NEXT_MONDAY,
            GET_NEXT_SUNDAY
        }

        public static Calendar calc(Calendar base, CalcType calcType) {

            int min = base.getActualMinimum(Calendar.DAY_OF_WEEK); // ��ȡ��һ�ܿ�ʼ��׼
            int current = base.get(Calendar.DAY_OF_WEEK); // ��ȡ��������һ��������
            Calendar calendar = (Calendar)base.clone();

            int nCount = 0;

            switch (calcType) {
                case GET_PREVIOUS_MONDAY:
                    nCount = ((current == min) ? -6 : (min - current + 1)) - 7;
                    break;

                case GET_PREVIOUS_SUNDAY:
                    nCount = ((current == min) ? 0 : (7 - current + 1)) - 7;
                    break;

                case GET_THIS_MONDAY:
                    nCount = (current == min) ? -6 : (min - current + 1);
                    break;

                case GET_THIS_SUNDAY:
                    nCount = (current == min) ? 0 : (7 - current + 1);
                    break;

                case GET_NEXT_MONDAY:
                    nCount = ((current == min) ? -6 : (min - current + 1)) + 7;
                    break;

                case GET_NEXT_SUNDAY:
                    nCount = ((current == min) ? 0 : (7 - current + 1)) + 7;
                    break;

                default:
                    break;
            }

            calendar.add(Calendar.DAY_OF_WEEK, nCount);
            
            return calendar;
        }
    }
}
