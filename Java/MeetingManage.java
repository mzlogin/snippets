import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MeetingManage {
	
	List<MeetingRecord> mRecords;
	
	public static void main(String[] args) {
		MeetingManage df = new MeetingManage();
		
		// 1. ��ѯ��ԭʼ����
		df.mRecords = df.getPrimitiveData();
		
		// 2. ��ȡ���������ܵ����ڿ�ȣ���һ�����죬���Ҫ���쵽��������Ҫ������+1ȥ��
		Calendar calendar = Calendar.getInstance();
		int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK); // ��ȡ���ܿ�ʼ��׼
		int current = calendar.get(Calendar.DAY_OF_WEEK); // ��ȡ�����ڱ���������
		
		Calendar start = Calendar.getInstance();
		start.add(Calendar.DAY_OF_WEEK, min - current + 1);	// �ܿ�ʼ����
		
		Calendar end = Calendar.getInstance();
		end.add(Calendar.DAY_OF_WEEK, 7 - current + 1);		// �ܽ�������
		
		// System.out.printf("start=%tF, end=%tF\n", start, end);
		
		// �������б���ǰд���������б�Ҫ�����Դ����ݿ���ȡ���γ�����
		String[] meetingRooms = {"huiyishi", "sffffff", "������11"};
		
		// 3. ��ʽ��ԭʼ����
		// ��ά���飬����ά�ȷֱ�Ϊ�����ҡ����ڼ���������
		MeetingRecord[][][] formatData = new MeetingRecord[meetingRooms.length][7][2];
		for (int i = 0; i < meetingRooms.length; i++) {
			Calendar startTmp = Calendar.getInstance();
			startTmp.set(Calendar.DATE, start.get(Calendar.DATE));
			for (int n = 0; n < 7; n++) {
				Calendar day = Calendar.getInstance();
				day.set(Calendar.DATE, start.get(Calendar.DATE) + n);
				for (MeetingRecord record : df.mRecords) {
					if (record.HYDD.equals(meetingRooms[i]) && isSameDay(record.KSSJ, day)) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(record.KSSJ);
						int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
						if (hourOfDay < 12) {
							if (formatData[i][n][0] == null) {
								formatData[i][n][0] = new MeetingRecord(record);
							}
						} else {
							if (formatData[i][n][1] == null) {
								formatData[i][n][1] = new MeetingRecord(record);
							}
						}
					}
				}
			}
		}
		
		// 4. ������ҳ����ÿ������ʱ��ԭʼ�����в�ѯ��ǰ�Ƿ��л���
		
		String strHTML = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/><style>table { border-collapse: collapse; } th { padding: 0.375em 0.8125em; border: 1px solid #ddd; font-weight: bold; } td { padding: 0.375em 0.8125em; border: 1px solid #ddd; } tr:nth-child(2n) { background-color: #f8f8f8; } .spec { background-color: #ff0000; }</style></head><body><table><tr><td></td><td>����һ</td><td>���ڶ�</td><td>������</td><td>������</td><td>������</td><td>������</td><td>������</td>";
		
		for (int i = 0; i < meetingRooms.length; i++) {
			// ����
			strHTML += "<tr><td>����</td>";
			for (int n = 0; n < 7; n++) {
				if (formatData[i][n][0] == null) {
					strHTML += "<td>" + "����" + "</td>";
				} else {
					// TODO ˳�����Ԫ��������ݴ������Ӻ���ʽ
					strHTML += "<td class=\"spec\">" + "�л�" + "</td>";
				}
			}
			strHTML += "</tr>\n";
			
			// ����
			strHTML += "<tr><td>����</td>";
			for (int n = 0; n < 7; n++) {
				if (formatData[i][n][1] == null) {
					strHTML += "<td>" + "����" + "</td>";
				} else {
					// TODO ˳�����Ԫ��������ݴ������Ӻ���ʽ
					strHTML += "<td class=\"spec\">" + "�л�" + "</td>";
				}
			}
			strHTML += "</tr>\n";
		}
		
		strHTML += "</table></body></html>";

		try { 
			File f = new File("hello.html"); 
			if (!f.exists()) { 
				f.createNewFile(); 
			} 
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8"); 
			BufferedWriter writer=new BufferedWriter(write);   
			writer.write(strHTML); 
			writer.close(); 
		} catch (Exception e) { 
			System.out.println("д�ļ����ݲ�������"); 
			e.printStackTrace(); 
		} 
	}
	
	public List<MeetingRecord> getPrimitiveData() {
		List<MeetingRecord> records = new ArrayList<MeetingRecord>();

		records.add(new MeetingRecord(new String(""), 
				new String(""), 
				getDate("2015-03-11 19:00:00"),
				getDate("2015-03-11 20:00:00"),
				new String("huiyishi"),
				new String("")));
		
		records.add(new MeetingRecord(new String(""), 
				new String(""), 
				getDate("2015-03-12 15:00:00"),
				getDate("2015-03-12 16:00:00"),
				new String("sffffff"),
				new String("")));
		
		records.add(new MeetingRecord(new String(""), 
				new String(""), 
				getDate("2015-03-13 10:00:00"),
				getDate("2015-03-13 11:00:00"),
				new String("������11"),
				new String("")));
		
		
		return records;
	}
	
	public static boolean isSameDay(Date date, Calendar calendar) {
		boolean isSameDay = false;
		
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		
		if (calDate.get(Calendar.DATE) == calendar.get(Calendar.DATE)) {
			isSameDay = true;
		}
		
		return isSameDay;
	}
	
	public static Date getDate(String s) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
			date = sdf.parse(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return date;
	}
	
	static class MeetingRecord {
		String CBRDH;
		String ZBDW;
		Date KSSJ;
		Date JSSJ;
		String HYDD;
		String HYS;
		
		public MeetingRecord() {
			CBRDH = new String();
			ZBDW = new String();
			KSSJ = new Date();
			JSSJ = new Date();
			HYDD = new String();
			HYS = new String();
		}
		
		public MeetingRecord(MeetingRecord record) {
			CBRDH = new String(record.CBRDH);
			ZBDW = new String(record.ZBDW);
			KSSJ = (Date)record.KSSJ.clone();
			JSSJ = (Date)record.JSSJ.clone();
			HYDD = new String(record.HYDD);
			HYS = new String(record.HYS);
		}
		
		public MeetingRecord(String CBRDH,
				String ZBDW,
				Date KSSJ,
				Date JSSJ,
				String HYDD,
				String HYS) {
			this.CBRDH = CBRDH;
			this.ZBDW = ZBDW;
			this.KSSJ = KSSJ;
			this.JSSJ = JSSJ;
			this.HYDD = HYDD;
			this.HYS = HYS;
		}
	}
}
