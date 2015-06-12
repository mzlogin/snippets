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
		
		// 1. 查询到原始数据
		df.mRecords = df.getPrimitiveData();
		
		// 2. 获取当天所在周的日期跨度，周一到周天，如果要周天到周六，需要把两个+1去掉
		Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 3-1, 26);
		int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK); // 获取本周开始基准
		int current = calendar.get(Calendar.DAY_OF_WEEK); // 获取当天在本周内天数
		
		Calendar start = (Calendar)calendar.clone();
		start.add(Calendar.DAY_OF_WEEK, (current == min) ? -6 : (min - current + 1));	// 周开始日期
		
		Calendar end = (Calendar)calendar.clone();
		end.add(Calendar.DAY_OF_WEEK, (current == min) ? 0 : (7 - current + 1));		// 周结束日期
		
		// System.out.printf("start=%tF, end=%tF\n", start, end);
		
		// 会议室列表，当前写死，如有有必要，可以从数据库中取出形成数组
		String[] meetingRooms = {"huiyishi", "sffffff", "会议室11"};
		
		// 3. 格式化原始数据
		// 三维数组，三个维度分别为会议室、星期几、上下午
//        MeetingRecord[][][] formatData = new MeetingRecord[meetingRooms.length][7][2];
//        for (int i = 0; i < meetingRooms.length; i++) {
//            Calendar startTmp = Calendar.getInstance();
//            startTmp.set(Calendar.DATE, start.get(Calendar.DATE));
//            for (int n = 0; n < 7; n++) {
//                Calendar day = Calendar.getInstance();
//                day.set(Calendar.DATE, start.get(Calendar.DATE) + n);
//                for (MeetingRecord record : df.mRecords) {
//                    if (record.HYDD.equals(meetingRooms[i]) && isSameDay(record.KSSJ, day)) {
//                        Calendar cal = Calendar.getInstance();
//                        cal.setTime(record.KSSJ);
//                        int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
//                        if (hourOfDay < 12) {
//                            if (formatData[i][n][0] == null) {
//                                formatData[i][n][0] = new MeetingRecord(record);
//                            }
//                        } else {
//                            if (formatData[i][n][1] == null) {
//                                formatData[i][n][1] = new MeetingRecord(record);
//                            }
//                        }
//                    }
//                }
//            }
//        }

        MeetingRecord[][][] formatData = new MeetingRecord[meetingRooms.length][7][2];

        for (MeetingRecord record : df.mRecords) {
            boolean bMeetingRoomFind = false;
            // 获取会议室下标
            int meetingRoomPos = 0;
            for (; meetingRoomPos < meetingRooms.length; meetingRoomPos++) {
                if (record.HYDD.equals(meetingRooms[meetingRoomPos])) {
                    bMeetingRoomFind = true;
                    break;
                }
            }
            if (!bMeetingRoomFind) {
                continue;
            }

            // 获取星期几下标
//            Calendar calKSSJ = Calendar.getInstance();
//            calKSSJ.setTime(record.KSSJ);
//            long thisDay = getDays(calKSSJ);
//            long startDay = getDays(start);
//            int weekPos = (int)(thisDay - startDay);
//            if (weekPos > 6 || weekPos < 0) {
//                continue;
//            }

            // 获取上下午下标
//            int ampmPos= (calKSSJ.get(Calendar.HOUR_OF_DAY) < 12) ? 0 : 1;

//            if (formatData[meetingRoomPos][weekPos][ampmPos] == null) {
//                formatData[meetingRoomPos][weekPos][ampmPos] = new MeetingRecord(record);
//            }
//

            Calendar calKSSJ = Calendar.getInstance();
            calKSSJ.setTime(record.KSSJ);
            Calendar calJSSJ = Calendar.getInstance();
            calJSSJ.setTime(record.JSSJ);
            
            // 遍历本周每一个半天时间段
            for (int i = 0; i < 7; i++) {
                Calendar amStart = (Calendar)start.clone();
                amStart.add(Calendar.DAY_OF_WEEK, i);
                amStart.set(Calendar.HOUR_OF_DAY, 8);
                amStart.set(Calendar.MINUTE, 0);
                amStart.set(Calendar.SECOND, 0);
                amStart.set(Calendar.MILLISECOND, 0);

                Calendar amEnd = (Calendar)amStart.clone();
                amEnd.set(Calendar.HOUR_OF_DAY, 12);

                if (calKSSJ.compareTo(amEnd) < 0 && calJSSJ.compareTo(amStart) >= 0) {
                    if (formatData[meetingRoomPos][i][0] == null) {
                        formatData[meetingRoomPos][i][0] = new MeetingRecord(record);
                    }
                }

                Calendar pmEnd = (Calendar)amStart.clone();
                pmEnd.set(Calendar.HOUR_OF_DAY, 21);

                if (calKSSJ.compareTo(pmEnd) < 0 && calJSSJ.compareTo(amEnd) >= 0) {
                    if (formatData[meetingRoomPos][i][1] == null) {
                        formatData[meetingRoomPos][i][1] = new MeetingRecord(record);
                    }
                }
            }
        }

		// 4. 绘制网页，画每个格子时从原始数据中查询当前是否有会议
		
		String strHTML = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/><style>table { border-collapse: collapse; } th { padding: 0.375em 0.8125em; border: 1px solid #ddd; font-weight: bold; } td { padding: 0.375em 0.8125em; border: 1px solid #ddd; } tr:nth-child(2n) { background-color: #f8f8f8; } .spec { background-color: #ff0000; }</style></head><body><table><tr><td></td><td>星期一</td><td>星期二</td><td>星期三</td><td>星期四</td><td>星期五</td><td>星期六</td><td>星期天</td>";
		
		for (int i = 0; i < meetingRooms.length; i++) {
			// 上午
			strHTML += "<tr><td>上午</td>";
			for (int n = 0; n < 7; n++) {
				if (formatData[i][n][0] == null) {
					strHTML += "<td>" + "空闲" + "</td>";
				} else {
					// TODO 顺便根据元素里的内容处理链接和样式
					strHTML += "<td class=\"spec\">" + "有会" + "</td>";
				}
			}
			strHTML += "</tr>\n";
			
			// 下午
			strHTML += "<tr><td>下午</td>";
			for (int n = 0; n < 7; n++) {
				if (formatData[i][n][1] == null) {
					strHTML += "<td>" + "空闲" + "</td>";
				} else {
					// TODO 顺便根据元素里的内容处理链接和样式
					strHTML += "<td class=\"spec\">" + "有会" + "</td>";
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
			System.out.println("写文件内容操作出错"); 
			e.printStackTrace(); 
		} 
	}
	
	public List<MeetingRecord> getPrimitiveData() {
		List<MeetingRecord> records = new ArrayList<MeetingRecord>();

		records.add(new MeetingRecord(new String(""), 
				new String(""), 
				getDate("2015-03-24 15:00:00"),
				getDate("2015-03-24 17:00:00"),
				new String("huiyishi"),
				new String("")));
		
		records.add(new MeetingRecord(new String(""), 
				new String(""), 
				getDate("2015-03-24 14:30:00"),
				getDate("2015-03-24 17:00:00"),
				new String("sffffff"),
				new String("")));
		
		records.add(new MeetingRecord(new String(""), 
				new String(""), 
				getDate("2015-03-25 12:59:00"),
				getDate("2015-04-30 21:00:00"),
				new String("会议室11"),
				new String("")));
		
		
		return records;
	}
	
	public static boolean isSameDay(Date date, Calendar calendar) {
		boolean isSameDay = false;
		
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		
        long dateDay = getDays(calDate);
        long calDay = getDays(calendar);
        if (dateDay == calDay) {
            isSameDay = true;
        }
		
		return isSameDay;
	}
    
    public static long getDays(Calendar cal) {
        long days = (cal.getTimeInMillis() + cal.get(Calendar.ZONE_OFFSET)) / (24*3600*1000);
        return days;
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
