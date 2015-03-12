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
		int min = base.getActualMinimum(Calendar.DAY_OF_WEEK); // ��ȡ���ܿ�ʼ��׼
		int current = base.get(Calendar.DAY_OF_WEEK); // ��ȡ�����ڱ���������

        // ����
		Calendar thisWeekStart = (Calendar)base.clone();
		thisWeekStart.add(Calendar.DAY_OF_WEEK, (current == min) ? -6 : (min - current + 1));	// �ܿ�ʼ����
		
		Calendar thisWeekEnd = (Calendar)base.clone();
		thisWeekEnd.add(Calendar.DAY_OF_WEEK, (current == min) ? 0 : (7 - current + 1));		// �ܽ�������

        // ����
		Calendar nextWeekStart = (Calendar)base.clone();
		nextWeekStart.add(Calendar.DAY_OF_WEEK, ((current == min) ? -6 : (min - current + 1)) + 7);	// �ܿ�ʼ����
		
		Calendar nextWeekEnd = (Calendar)base.clone();
		nextWeekEnd.add(Calendar.DAY_OF_WEEK, ((current == min) ? 0 : (7 - current + 1)) + 7);		// �ܽ�������

        // ����
		Calendar preWeekStart = (Calendar)base.clone();
		preWeekStart.add(Calendar.DAY_OF_WEEK, ((current == min) ? -6 : (min - current + 1)) - 7);	// �ܿ�ʼ����
		
		Calendar preWeekEnd = (Calendar)base.clone();
		preWeekEnd.add(Calendar.DAY_OF_WEEK, ((current == min) ? 0 : (7 - current + 1)) - 7);		// �ܽ�������
    }
    
    public static String getMonthAndDay(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd", Locale.US);
        String monthAndDay = sdf.format(cal.getTime());
        return monthAndDay;
    }
}
