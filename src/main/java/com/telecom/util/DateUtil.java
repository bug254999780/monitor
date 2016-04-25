package com.telecom.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具处理类
 * 
 * @author Administrator
 * 
 */
public class DateUtil {
	public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
	public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String FULL_DATE_FORMAT_Minute = "yyyy-MM-dd HH:mm";

	private static GregorianCalendar calendar = new GregorianCalendar();
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat();

	public static String toString(Date d) {
		return toString(d, SIMPLE_DATE_FORMAT);
	}

	public static String toString(Date d, String format) {
		dateFormatter.applyPattern(format);
		return dateFormatter.format(d);
	}

	

	/**
	 * convert string to Date. return default error value if string invalid
	 */
	public static Date toDate(String year, String month, String date,
			String hour, String minute, String second) {
		Date result = null;

		int nYear = Integer.parseInt(year);
		int nMonth = Integer.parseInt(month);
		int nDay = Integer.parseInt(date);

		int nHour = (hour == null) ? 0 : Integer.parseInt(hour);
		int nMinute = (minute == null) ? 0 : Integer.parseInt(minute);
		int nSecond = (second == null) ? 0 : Integer.parseInt(second);

		boolean valid = false;
		if (nYear >= 1900 && nYear <= 2500 && 1 <= nMonth && nMonth <= 12
				&& 1 <= nDay && nDay <= 31 && 0 <= nHour && nHour <= 23
				&& 0 <= nMinute && nMinute <= 59 && 0 <= nSecond
				&& nSecond <= 59) {
			valid = true;

			switch (nMonth) {
			case 4:
			case 6:
			case 9:
			case 11:
				if (nDay == 31) {
					valid = false;
				}
				break;
			case 2:
				if (nDay > 29) {
					valid = false;
				} else if (!isLeapYear(nYear) && (nDay == 29)) {
					valid = false;
				}
				break;
			}
		}

		if (valid) {
			try {
				GregorianCalendar cal = new GregorianCalendar(nYear,
						nMonth - 1, nDay, nHour, nMinute, nSecond);
				result = cal.getTime();
			} catch (Exception e) {
			}
		}

		return (result);
	}

	public static Date toDate(String year, String month, String date) {
		return toDate(year, month, date, null, null, null);
	}

	public static Date toDate(String dateStr, String format)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(dateStr);
	}


	public static java.sql.Timestamp toTimestamp(String strDate)
			throws ParseException {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date1 = dateFormatter.parse(strDate);
		java.sql.Timestamp datetime = new java.sql.Timestamp(date1.getTime());
		return datetime;
	}

	public static java.sql.Timestamp toTimestamp(java.util.Date date) {
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());
		return datetime;
	}

	public static java.sql.Date toSQLDate(Date ud) {
		if (ud != null) {
			return new java.sql.Date(ud.getTime());
		}

		return null;
	}

	public static java.sql.Date getSQLDate() {
		return new java.sql.Date(System.currentTimeMillis());
	}

	public static boolean isLeapYear(int year) {
		return ((year % 400) == 0 || ((year % 4) == 0 && (year % 100) != 0));
	}

	public static Date today() {
		GregorianCalendar gc = new GregorianCalendar();
		gc = new GregorianCalendar(gc.get(Calendar.YEAR), gc
				.get(Calendar.MONTH), gc.get(Calendar.DATE));

		return gc.getTime();
	}

	public static Date sqlToday() {
		return toSQLDate(today());
	}

	public static String get23DateString() {
		return toString(new Date(), "yyyy-MM-dd HH:mm:ss-SSS");
	}

	/**
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date add(Date date, int day) {
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);

		return calendar.getTime();
	}

	public static int minus(Date date1, Date date2) {
		long day1 = date1.getTime() / 1000 / 3600;
		long day2 = date2.getTime() / 1000 / 3600;

		return (int) (day1 - day2);
	}

	public static Date toYMD(Date date) {
		calendar.setTime(date);
		calendar = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar
				.get(Calendar.MONTH), calendar.get(Calendar.DATE));

		return calendar.getTime();

	}

	/*
	 * compareTimer() 比较两个时间差param:String类型(两个时间),int(按单位比较 Calendar.YEAR:年
	 * Calendar.MONTH:月 Calendar.DATE:日 Calendar.HOUR:时 Calendar.MINUTE:分)
	 * return:String 正确返回：差值 错误：null
	 */
	public static int compareTimer(Date startTime, Date endTime, int a) {
		int i = 0;
		try {
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(startTime);
			c2.setTime(endTime);
			if (c1.equals(c2)) {
				return i;
			}
			while (c1.after(c2)) {
				if (a == Calendar.YEAR)
					c2.add(Calendar.YEAR, 1);
				if (a == Calendar.MONTH)
					c2.add(Calendar.MONTH, 1);
				if (a == Calendar.DATE)
					c2.add(Calendar.DATE, 1);
				if (a == Calendar.HOUR)
					c2.add(Calendar.HOUR, 1);
				if (a == Calendar.MINUTE)
					c2.add(Calendar.MINUTE, 1);
				i--;
			}
			while (c1.before(c2)) {
				if (a == Calendar.YEAR)
					c1.add(Calendar.YEAR, 1);
				if (a == Calendar.MONTH)
					c1.add(Calendar.MONTH, 1);
				if (a == Calendar.DATE)
					c1.add(Calendar.DATE, 1);
				if (a == Calendar.HOUR)
					c1.add(Calendar.HOUR, 1);
				if (a == Calendar.MINUTE)
					c1.add(Calendar.MINUTE, 1);
				i++;
			}

		} catch (Exception e) {
		}
		return i;
	}

	public static Timestamp getTimestamp() {
		Date now = new Date();
		return DateUtil.toTimestamp(now);
	}

	// 日期处理，得到时间差（毫秒）
	public static long retTimeInMillis(String str, String nowtime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期式
		Calendar c1;
		Calendar c2;
		try {
			Date date1 = dateFormat.parse(str);
			c1 = Calendar.getInstance();
			c1.clear();
			c1.setTime(date1);
			c2 = Calendar.getInstance();
			Date date2 = dateFormat.parse(nowtime);
			c2.clear();
			c2.setTime(date2);
			return c2.getTimeInMillis() - c1.getTimeInMillis();
		} catch (Exception e) {
			// TODO: handle exception
			return 0L;
		}

	}

	/**
	 * 
	 * 
	 * @Title: starttime
	 * 
	 * @Description: TODO(返回当天起始时间)
	 * 
	 * @param @return 设定文件
	 * 
	 * @return String 返回类型
	 * 
	 * @throws
	 */
	public static String starttime() {
		Calendar c = Calendar.getInstance();
		String time = c.get(c.YEAR) + "-" + (c.get(c.MONTH) + 1) + "-"
				+ c.get(Calendar.DATE) + " 00:00:00";
		return time;
	}

	public static String endtime() {
		Calendar c = Calendar.getInstance();
		String time = c.get(c.YEAR) + "-" + (c.get(c.MONTH) + 1) + "-"
				+ c.get(c.DATE) + " 23:59:59";
		return time;
	}

	/**
	 * @author kz
	 * @Title: lastFridayTime
	 * @Description: TODO(得到上周五的日期 格式 YYYY-MM-DD 00:00:00)
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String lastFridayTimeForString() {
		Calendar calendar1 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 5;
		int offset1 = 1 - dayOfWeek;
		calendar1.add(Calendar.DATE, offset1 - 7);
		String time = calendar1.get(calendar1.YEAR) + "-"
				+ (calendar1.get(calendar1.MONTH) + 1) + "-"
				+ calendar1.get(Calendar.DAY_OF_MONTH) + " 00:00:00";
		return time;
	}

	/**
	 * @author kz
	 * @Title: lastFridayTimeForDate
	 * @Description: TODO(得到上周五的日期)
	 * @param @return 设定文件
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date lastFridayTimeForDate() {
		Calendar calendar1 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 5;
		int offset1 = 1 - dayOfWeek;
		calendar1.add(Calendar.DATE, offset1 - 7);
		String time = calendar1.get(calendar1.YEAR) + "-"
				+ (calendar1.get(calendar1.MONTH) + 1) + "-"
				+ calendar1.get(Calendar.DAY_OF_MONTH) + " 00:00:00";
		Date date=null;
		try {
			date = DateUtil.toDate(time, FULL_DATE_FORMAT);
		} catch (Exception e) {
		}
		return date;
	}

	/**
	 * @author kz
	 * @Title: thisThursdayTime
	 * @Description: TODO(得到周四的日期 格式 YYYY-MM-DD 23:59:59)
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String thisThursdayTimeForString() {
		Calendar calendar1 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 5;
		int offset1 = 7 - dayOfWeek;
		calendar1.add(Calendar.DATE, offset1 - 7);
		String time = calendar1.get(calendar1.YEAR) + "-"
				+ (calendar1.get(calendar1.MONTH) + 1) + "-"
				+ calendar1.get(Calendar.DAY_OF_MONTH) + " 23:59:59";
		return time;
	}

	/**
	 * @author kz
	 * @Title: thisThursdayTimeForDate
	 * @Description: TODO(得到周四的日期)
	 * @param @return 设定文件
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date thisThursdayTimeForDate() {
		Calendar calendar1 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 5;
		int offset1 = 7 - dayOfWeek;
		calendar1.add(Calendar.DATE, offset1 - 7);
		String time = calendar1.get(calendar1.YEAR) + "-"
				+ (calendar1.get(calendar1.MONTH) + 1) + "-"
				+ calendar1.get(Calendar.DAY_OF_MONTH) + " 23:59:59";
		Date date=null;
		try {
			date = DateUtil.toDate(time, FULL_DATE_FORMAT);
		} catch (Exception e) {
		}
		return date;
	}
	/**
	 * @author xqm
	 * @Title: theFirstDayOfThatMonth
	 * @Description: TODO(得到本月第一天)
	 * @param @return 设定文件
	 * @return Date 返回类型
	 * @throws
	 */
	public static String theFirstDayOfThatMonth() {
		Calendar cale  = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String theFirstDayOfThatMonth;
		// 获取本月的第一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		theFirstDayOfThatMonth = format.format(cale.getTime());
		return theFirstDayOfThatMonth;
	}
	/**
	 * @author xqm
	 * @Title: theFirstDayOfThatMonth
	 * @Description: TODO(得到下个月第一天)
	 * @param @return 设定文件
	 * @return Date 返回类型
	 * @throws
	 */
	public static String theLastDayOfThatMonth() {
		Calendar cale  = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String theLastDayOfThatMonth;
		// 获取下个月的第一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		theLastDayOfThatMonth = format.format(cale.getTime());
		return theLastDayOfThatMonth;
	}
	/**
	 * @author xqm
	 * @Title: theFirstDayOfThatMonth
	 * @Description: TODO(得到上个月月第一天)
	 * @param @return 设定文件
	 * @return Date 返回类型
	 * @throws
	 */
	public static String theFirstDayOfLastMonth() {
		Calendar cale  = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String theFirstDayOfLastMonth;
		// 获取上个月的第一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, -1);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		theFirstDayOfLastMonth = format.format(cale.getTime());
		return theFirstDayOfLastMonth;
	}
	/**
	 * 判断2个日期是否连续
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean DateForM(String str1,String str2){
		try {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = df.parse(str1);
		Date date2 = df.parse(str2);
		GregorianCalendar gc=new GregorianCalendar(); 
		gc.setTime(date1);
		gc.add(5, +1);
		if(gc.getTime().compareTo(date2)==0){
			return true;
		}
		return false;
		} catch (ParseException e) {
		return false;
		}
	}
	/**
	 * @author kz
	* @Title: thisTime
	* @Description: TODO(YYYY_MM_DD_HH_MI)
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String thisTime(int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(c.MINUTE, +i);
		String time = c.get(c.YEAR) + "_" + (c.get(c.MONTH) + 1) + "_"
				+ c.get(Calendar.DATE) + "_"+c.get(c.HOUR_OF_DAY)+"_"+(c.get(c.MINUTE));
		return time;
	}
	// 日期处理，得到时间差（毫秒）
		public static long retTimeInMillis(String nowtime) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期式
			Calendar c2;
			try {
				c2 = Calendar.getInstance();
				Date date2 = dateFormat.parse(nowtime);
				c2.clear();
				c2.setTime(date2);
				//System.out.println(c2.getTimeInMillis());
				return c2.getTimeInMillis();
			} catch (Exception e) {
				return 0L;
			}

		}
	public static void main(String args[]) {
		/*
		 * Date start = new Date(); start.setHours(start.getHours() - 12); try {
		 * Thread.sleep(6000); } catch (InterruptedException e) {
		 * e.printStackTrace(); } Date end = new Date(); int en =
		 * compareTimer(start, end, Calendar.MINUTE); System.out.println(en);
		 */
		// try {
		// System.out.println(toString(new Date(),"yyyy-MM-dd-HH-mm-ss-SSS"));
		// System.out.println(DateUtil.get23DateString());
		// Thread.sleep(1000);
		// System.out.println(toString(new Date(),"yyyy-MM-dd-HH-mm-ss-SSS"));
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		/*
		 * Calendar calendar1 = Calendar.getInstance(); Calendar calendar2 =
		 * Calendar.getInstance(); int
		 * dayOfWeek=calendar1.get(Calendar.DAY_OF_WEEK)-5; int
		 * offset1=1-dayOfWeek; int offset2=7-dayOfWeek;
		 * calendar1.add(Calendar.DATE, offset1-7); calendar2.add(Calendar.DATE,
		 * offset2-7); String starttime = calendar1.get(calendar1.YEAR) + "-" +
		 * (calendar1.get(calendar1.MONTH) + 1) + "-" +
		 * calendar1.get(Calendar.DAY_OF_MONTH) + " 00:00:00";
		 * System.out.println(DateUtil.toString(calendar1.getTime(),
		 * DateUtil.SIMPLE_DATE_FORMAT));//last Monday
		 * System.out.println(DateUtil.toString(calendar2.getTime(),
		 * DateUtil.SIMPLE_DATE_FORMAT));//last Sunday
		 * System.out.println(starttime);
		 */
//		System.out.println(DateUtil.lastFridayTimeForString());
	 //System.out.println(DateUtil.thisThursdayTime());*/
		
		//lastFridayTimeForDate();
		/*Date dats=DateUtil.lastFridayTimeForDate();
		System.out.println(dats.getDate());*/
		/*System.out.println(DateUtil.thisTime(0));
		System.out.println(DateUtil.thisTime(-6));
		System.out.println(DateUtil.thisTime(-7));
		System.out.println(DateUtil.thisTime(-8));*/
	  //System.out.println(DateUtil.theFirstDayOfLastMonth());
	  //System.out.println(DateUtil.theFirstDayOfThatMonth());
	  //System.out.println(DateUtil.theLastDayOfThatMonth());
		long a =retTimeInMillis("2015-11-09 19:16:45.0");
		long b =retTimeInMillis("2015-11-09 19:16:45.0");
		long c =retTimeInMillis("2015-11-09 19:16:45.0");
		//long b=retTimeInMillis("");
		System.out.println(c-1447056551475L);
		//System.out.println(c-b);
		
		
	}

}