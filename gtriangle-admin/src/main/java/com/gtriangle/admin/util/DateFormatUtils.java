package com.gtriangle.admin.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DateFormatUtils {
	private DateFormatUtils() {
	}

	public static final String[] s = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	private static final long ONE_MINUTE = 60000L;
	private static final long ONE_HOUR = 3600000L;
	private static final String SECOND_LEVEL_AGO = "刚刚";
	private static final String ONE_MINUTE_AGO = "分钟前";
	private static final String ONE_HOUR_AGO = "小时前";

	/**
	 * 日期类型转字符串类型
	 *
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return DateFormat.getDateInstance().format(date);
	}

	/**
	 * 获取指定日期的当前星期（中国, 如：星期日,星期一,星期二）
	 * 
	 * @param sDate
	 *            要格式化的时间，如果为空则取当前日期
	 */
	public static String getWeekCS(String sDate) {
		Calendar c = GregorianCalendar.getInstance();
		if (!(sDate == null || sDate.equals(""))) {
			Date date = formatDateStr(sDate, "yyyy-MM-dd");
			c.setTime(date);
		}
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		return s[c.get(Calendar.DAY_OF_WEEK) - 1];
	}
	
	/**
	 * 获取当前日期是周几
	 * 周一至周日分别对应 1-7
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		if(week == 1){
			return 7;
		}else{
			return week - 1;
		}
	}

	/**
	 * 对传入的时间进行格式化
	 * 
	 * @param time
	 *            要格式化的时间，如果为null则取当前时间
	 * @param format
	 *            返回值的形式，为空则按"yyyy-MM-dd HH:mm:ss"格式化时间
	 * @return 格式化后的时间字串
	 */
	public static String formatDate(Date time, String format) {
		if (time == null) {
			time = new Date();
		} // 获取当前time
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(time);
		return dateString;
	}

	/**
	 * 对传入的时间字串进行格式化，并返回一个Date对象
	 * 
	 * @param time
	 *            要格式化的时间，如果为空则取当前时间
	 * @param format
	 *            返回值的形式，为空则按"yyyy-MM-dd HH:mm:ss"格式化时间
	 * @return 格式化后的时间
	 */
	public static Date formatDateStr(String time, String format) {
		if (time == null || time.equals("")) {
			// 获取当前time
			time = formatDate(null, null);
		}
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);// 从0开始解析
		Date strtodate = formatter.parse(time, pos);
		return strtodate;
	}

	/**
	 * 对传入的时间字串进行格式化
	 * 
	 * @param time
	 *            要格式化的时间，如果为空则取当前时间
	 * @param format
	 *            返回值的形式，为空则按"yyyy-MM-dd HH:mm:ss"格式化时间
	 * @return 格式化后的时间字串
	 */
	public static String formatDateStrToStr(String time, String format) {
		return formatDate(formatDateStr(time, format), format);
	}

	/**
	 * 获取指定日期的中国日期（yyyy年MM月dd日）
	 * 
	 * @param time
	 *            要格式化的时间，如果为null则取当前时间
	 * @return 格式化后的日期字串
	 */
	public static String getDateTimeCS(Date time) {
		return formatDate(time, "yyyy年MM月dd日 HH点mm分ss秒");
	}

	/**
	 * 获取指定日期的中国日期（yyyy年MM月dd日）
	 *
	 * @param time
	 *            要格式化的时间，如果为null则取当前时间
	 * @return 格式化后的日期字串
	 */
	public static String getDateCS(Date time) {
		return formatDate(time, "yyyy年MM月dd日");
	}

	/**
	 * 获取指定时间的长字符串形式 "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param time
	 *            要格式化的时间，如果为null则取当前时间
	 * @return 格式化后的时间字串
	 */
	public static String getLongStr(Date time) {
		return formatDate(time, null);
	}

	/**
	 * 获得d天后的现在时刻；长字符串形式 "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param d
	 *            //d天后
	 * @return 格式化后的时间字串
	 */
	public static String getLongStr(int d) {
		Calendar c = GregorianCalendar.getInstance();
		c.add(Calendar.DATE, d);// 获得d天后的现在时刻
		Date time = c.getTime();
		return formatDate(time, null);
	}

	/**
	 * 获取指定时间的短字符串形式 "yyyy-MM-dd"
	 * 
	 * @param time
	 *            要格式化的时间，如果为null则取当前时间
	 * @return 格式化后的时间字串
	 */
	public static String getShortStr(Date time) {
		return formatDate(time, "yyyy-MM-dd");
	}

	/**
	 * 将字符串转换为一般时间的长格式:yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 *            要格式化的时间，如果为空则取当前时间
	 * @return 格式化后的时间
	 */
	public static Date getLongDate(String strDate) {
		return formatDateStr(strDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将字符串转换为一般时间的短格式;yyyy-MM-dd
	 * 
	 * @param strDate
	 *            要格式化的时间，如果为空则取当前时间
	 * @return 格式化后的时间
	 */
	public static Date getShortDate(String strDate) {
		return formatDateStr(strDate, "yyyy-MM-dd");
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @param strDate
	 *            要格式化的时间，如果为空则取当前时间
	 * @return 格式化后的时间
	 */
	public static String getStrTimeShort(String strDate) {
		return formatDateStrToStr(strDate, "HH:mm:ss");
	}

	/**
	 * 将日期字符串加天数转换成新日期字符串
	 * 
	 * @param strDate
	 *            原日期字符串:
	 * @param days
	 *            增加的天数
	 * @return (yyyy-M-d)添加后的时间
	 */
	public static String addDayDate(String strDate, int days) {
		String tmpDate = formatDateStrToStr(strDate, "yyyy-MM-dd");
		String[] date = tmpDate.split("-"); // 将要转换的日期字符串拆分成年月日
		int year, month, day;
		year = Integer.parseInt(date[0]);
		month = Integer.parseInt(date[1]) - 1;
		day = Integer.parseInt(date[2]);
		GregorianCalendar d = new GregorianCalendar(year, month, day);
		d.add(Calendar.DATE, days);
		Date dd = d.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String adddate = df.format(dd);
		return adddate;
	}

	/**
	 * 将日期加月份转换成新日期字符串
	 *
	 * @param strDate 原日期:
	 * @param seconds 增加的月数
	 * @return (yyyy-M-d)添加后的时间
	 */
	public static Date addSecondDate(Date strDate,int seconds){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(strDate);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}


	/**
	 * 将日期加月份转换成新日期字符串
	 *
	 * @param strDate 原日期:
	 * @param months 增加的月数
	 * @return (yyyy-M-d)添加后的时间
	 */
	public static Date addMonthDate(Date strDate,int months){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(strDate);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}

	/**
	 * //获取当前的时间
	 */
	public static Date getNow() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 用当前日期作为文件名,一般不会重名 取到的值是从当前时间的字符串格式,带有微秒,建议作为记录id 。一秒是一千微秒。
	 * 如：20080403212345508
	 */
	public static String getDateId() {
		return formatDate(null, "yyyyMMddHHmmssSSS");
	}

	/**
	 * 提取一个月中的最后一天
	 * 
	 * @param day
	 * @return
	 */
	public static Date getLastDate(long day) {
		Date date = new Date();
		long date_3_hm = date.getTime() - 3600000 * 34 * day;
		Date date_3_hm_date = new Date(date_3_hm);
		return date_3_hm_date;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyy-MM-dd
	 */
	public static String getStringToday() {
		return formatDate(null, "yyyy-MM-dd");
	}

	/**
	 * 得到现在小时
	 */
	public static String getHour() {
		String dateString = formatDate(null, "yyyy-MM-dd HH:mm:ss");
		String hour = dateString.substring(11, 13);
		return hour;
	}

	/**
	 * 得到现在分钟
	 * 
	 * @return
	 */
	public static String getMinute() {
		String dateString = formatDate(null, "yyyy-MM-dd HH:mm:ss");
		String min = dateString.substring(14, 16);
		return min;
	}

	/**
	 * 二个小时时间间的差值,必须保证二个时间都是"HH:mm"的格式，返回字符型的分钟
	 */
	public static String getTwoHour(String st1, String st2) {
		String[] kk = null;
		String[] jj = null;
		kk = st1.split(":");
		jj = st2.split(":");
		if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0])) {
			return "0";
		} else {
			double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
			double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
			if ((y - u) > 0) {
				return y - u + "";
			} else {
				return "0";
			}
		}
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static Long getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return null;
		}
		return day;
	}

	/**
	 * 时间前推或后推分钟,其中JJ表示分钟.
	 * 
	 * @param sj1
	 *            指定时间
	 * @param jj
	 *            前推或后推分钟,其中JJ表示分钟
	 * @return
	 */
	public static String getPreTime(String sj1, String jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
		}
		return mydate1;
	}

	/**
	 * 判断是否润年
	 * 
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = formatDateStr(ddate, "yyyy-MM-dd");
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0) {
			return true;
		} else if ((year % 4) == 0) {
			if ((year % 100) == 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 获取一个月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getEndDateOfMonth(String date) {
		String str = formatDateStrToStr(date, "yyyy-MM-dd");
		str = date.substring(0, 8);
		String month = date.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
			str += "31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			str += "30";
		} else {
			if (isLeapYear(date)) {
				str += "29";
			} else {
				str += "28";
			}
		}
		return str;
	}

	/**
	 * 获取当前日期的所属月的第一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getFirstDayOfMonth(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date currentDate = sdf.parse(date);
		// 获取当前时间所属的月
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.set(Calendar.DATE, 1);
		return c.getTime();
	}

	/**
	 * 获取当前日期的所属月的最后一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getLastDayOfMonth(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date currentDate = sdf.parse(date);
		Calendar d = Calendar.getInstance();
		d.setTime(currentDate);
		// 增加一个月
		d.add(Calendar.MONTH, 1);
		// 设置成为下一个月的第一天
		d.set(Calendar.DAY_OF_MONTH, 1);
		// 将该日期减一天，即当月的最后一天
		d.add(Calendar.DATE, -1);
		return d.getTime();
	}

	/**
	 * 获取当前日期的所属天的开始时间
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getFirstTimeOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取当前日期的所属天的开始时间
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getLastTimeOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 获取当前日期的所属月的第一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getFirstDayTimeOfMonth(Date date) {
		// 获取当前时间所属的月
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		return getFirstTimeOfDay(c.getTime());
	}

	/**
	 * 获取当前日期的所属月的最后一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getLastDayTimeOfMonth(Date date) {
		Calendar d = Calendar.getInstance();
		d.setTime(date);
		// 增加一个月
		d.add(Calendar.MONTH, 1);
		// 设置成为下一个月的第一天
		d.set(Calendar.DAY_OF_MONTH, 1);
		// 将该日期减一天，即当月的最后一天
		d.add(Calendar.DATE, -1);
		return getLastTimeOfDay(d.getTime());
	}

	/**
	 * 获取当前日期的所属月的上一个月的第一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getFirstDayOfCuurentMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		return DateFormatUtils.getFirstTimeOfDay(c.getTime());
	}

	/**
	 * 获取当前日期的所属月的上一个月的最后一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getLastDayOfCurrentMonth(Date date) {
		Calendar d = Calendar.getInstance();
		d.setTime(date);
		d.add(Calendar.MONTH, 1);
		d.set(Calendar.DATE, 1);
		// 将当前日期减一天，即上个月的最后一天
		d.add(Calendar.DATE, -1);
		return DateFormatUtils.getLastTimeOfDay(d.getTime());
	}

	/**
	 * 获取当前日期的所属月的上一个月的第一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getFirstDayOfPreviousMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DATE, 1);
		return DateFormatUtils.getFirstTimeOfDay(c.getTime());
	}
	

	/**
	 * 获取当前日期的所属月的上一个月的最后一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getLastDayOfPreviousMonth(Date date) {
		Calendar d = Calendar.getInstance();
		d.setTime(date);
		// 设置当前日期的所属月的第一天
		d.set(Calendar.DATE, 1);
		// 将当前日期减一天，即上个月的最后一天
		d.add(Calendar.DATE, -1);
		return DateFormatUtils.getLastTimeOfDay(d.getTime());
	}

	/**
	 * 判断二个时间是否在同一个周
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
				return true;
			}
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
				return true;
			}
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 产生周序列,即得到当前时间所在的年度是第几周
	 * 
	 * @return
	 */
	public static String getSeqWeek() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1) {
			week = "0" + week;
		}
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year + week;
	}

	/**
	 * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
	 * 
	 * @param sdate
	 * @param num
	 *            0-6，分别表示周一到周六
	 * @return
	 */
	public static String getWeek(String sdate, String num) {
		// 再转换为时间
		Date dd = formatDateStr(sdate, "yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(dd);
		if (num.equals("1")) // 返回星期一所在的日期
		{
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		} else if (num.equals("2")) // 返回星期二所在的日期
		{
			c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		} else if (num.equals("3")) // 返回星期三所在的日期
		{
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		} else if (num.equals("4")) // 返回星期四所在的日期
		{
			c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		} else if (num.equals("5")) // 返回星期五所在的日期
		{
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		} else if (num.equals("6")) // 返回星期六所在的日期
		{
			c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		} else if (num.equals("0")) // 返回星期日所在的日期
		{
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		}
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals("")) {
			return 0;
		}
		if (date2 == null || date2.equals("")) {
			return 0;
		}
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param small
	 * @param big
	 * @return
	 */
	public static long getDays(Date small, Date big) {
		if (small == null) {
			return 0;
		}
		if (big == null) {
			return 0;
		}
		long day = (big.getTime() - small.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 获得两个日期之间的每一天
	 * 
	 * @param small
	 * @param big
	 * @return
	 */
	public static List<Date> getEveryDay(Date small, Date big) {
		List<Date> list = new ArrayList<Date>();
		Calendar c_begin = Calendar.getInstance();
		Calendar c_end = Calendar.getInstance();
		c_begin.setTime(small);
		c_end.setTime(big); // Calendar的月从0-11，所以5月是4.
		c_end.add(Calendar.DAY_OF_YEAR, 1); // 结束日期下滚一天是为了包含最后一天

		while (c_begin.before(c_end)) {
			// System.out.println("第"+count+"周 日期："+new
			// java.sql.Date(c_begin.getTime().getTime())+","+weeks[c_begin.get(Calendar.DAY_OF_WEEK)]);
			list.add(c_begin.getTime());
			c_begin.add(Calendar.DAY_OF_YEAR, 1);
		}
		return list;
	}

	/**
	 * 获得两个日期之间的每一天
	 * 
	 * @param small
	 * @param big
	 * @return
	 */
	public static List<String> getEveryDay(String pattern, Date small, Date big) {
		List<String> list = new ArrayList<String>();
		Calendar c_begin = Calendar.getInstance();
		Calendar c_end = Calendar.getInstance();
		c_begin.setTime(small);
		c_end.setTime(big); // Calendar的月从0-11，所以5月是4.
		c_end.add(Calendar.DAY_OF_YEAR, 1); // 结束日期下滚一天是为了包含最后一天

		while (c_begin.before(c_end)) {
			// System.out.println("第"+count+"周 日期："+new
			// java.sql.Date(c_begin.getTime().getTime())+","+weeks[c_begin.get(Calendar.DAY_OF_WEEK)]);
			list.add(formatDate(c_begin.getTime(), pattern));
			c_begin.add(Calendar.DAY_OF_YEAR, 1);
		}
		return list;
	}

	/**
	 * 获得两个日期之间的每一周
	 * 返回结果为每一周的周一的日期
	 * @param small
	 * @param big
	 */
	public static List<String> getEveryWeek(String pattern, Date small,Date big){
		List<String> list = new ArrayList<String>();
		List<Date> everyDay =  getEveryDay(small, big);
		if(ListUtil.isNotEmpty(everyDay)){
			for (Date currentDate : everyDay) {
				Date mondayDate = getFirstDayOfWeek(currentDate);
				String mondayDateStr = formatDate(mondayDate, pattern);
				if(!list.contains(mondayDateStr)){
					list.add(mondayDateStr);
				}
			}
		}
		return list;
	}

	/**
	 * 获得两个日期之间的每一周
	 * 返回结果为每一周的周一的日期
	 * @param small
	 * @param big
	 */
	public static List<Date> getEveryWeek(Date small,Date big){
		List<Date> list = new ArrayList<Date>();
		List<Date> everyDay =  getEveryDay(small, big);
		if(ListUtil.isNotEmpty(everyDay)){
			for (Date currentDate : everyDay) {
				Date mondayDate = getFirstDayOfWeek(currentDate);
				if(!list.contains(mondayDate)){
					list.add(mondayDate);
				}
			}
		}
		return list;
	}

	/**
	 * 获得两个日期之间的每个月
	 * 
	 * @param small
	 * @param big
	 * @return
	 */
	public static List<Date> getEveryMonth(Date small, Date big) {
		List<Date> list = new ArrayList<Date>();
		Calendar c_begin = Calendar.getInstance();
		Calendar c_end = Calendar.getInstance();
		c_begin.setTime(small);
		c_begin.set(Calendar.DAY_OF_MONTH, 1);
		c_end.setTime(big);
		c_end.set(Calendar.DAY_OF_MONTH, 1);
		c_end.add(Calendar.MONTH, 1); // 结束日期下滚一月是为了包含最后一月
		while (c_begin.before(c_end)) {
			// System.out.println("日期："+new
			// java.sql.Date(c_begin.getTime().getTime()));
			list.add(c_begin.getTime());
			c_begin.add(Calendar.MONTH, 1);
		}
		return list;
	}

	/**
	 * 获得两个日期之间的每个月
	 * 
	 * @param small
	 * @param big
	 * @return
	 */
	public static List<String> getEveryMonth(String pattern, Date small, Date big) {
		List<String> list = new ArrayList<String>();
		Calendar c_begin = Calendar.getInstance();
		Calendar c_end = Calendar.getInstance();
		c_begin.setTime(small);
		c_begin.set(Calendar.DAY_OF_MONTH, 1);
		c_end.setTime(big);
		c_end.set(Calendar.DAY_OF_MONTH, 1);
		c_end.add(Calendar.MONTH, 1); // 结束日期下滚一月是为了包含最后一月
		while (c_begin.before(c_end)) {
			// System.out.println("日期："+new
			// java.sql.Date(c_begin.getTime().getTime()));
			list.add(formatDate(c_begin.getTime(), pattern));
			c_begin.add(Calendar.MONTH, 1);
		}
		return list;
	}

	/**
	 * 获得两个日期之间的每年
	 * 
	 * @param small
	 * @param big
	 * @return
	 */
	public static List<String> getEveryYear(String pattern, Date small, Date big) {
		List<String> list = new ArrayList<String>();
		Calendar c_begin = Calendar.getInstance();
		Calendar c_end = Calendar.getInstance();
		c_begin.setTime(small);
		c_begin.set(Calendar.MONTH, 0);
		c_begin.set(Calendar.DAY_OF_MONTH, 1);
		c_end.setTime(big);
		c_end.set(Calendar.DAY_OF_MONTH, 1);
		c_end.set(Calendar.MONTH, 0);
		c_end.add(Calendar.YEAR, 1); // 结束日期下滚一月是为了包含最后一月
		while (c_begin.before(c_end)) {
			// System.out.println("日期："+new
			// java.sql.Date(c_begin.getTime().getTime()));
			list.add(formatDate(c_begin.getTime(), pattern));
			c_begin.add(Calendar.YEAR, 1);
		}
		return list;
	}

	/**
	 * 获得两个日期之前相差的月份<br>
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getMonthCount(Date start, Date end) {
		if (start.after(end)) {
			Date t = start;
			start = end;
			end = t;
		}
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);
		Calendar temp = Calendar.getInstance();
		temp.setTime(end);
		temp.add(Calendar.DATE, 1);

		int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

		if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) == 1)) {
			return year * 12 + month + 1;
		} else if ((startCalendar.get(Calendar.DATE) != 1) && (temp.get(Calendar.DATE) == 1)) {
			return year * 12 + month;
		} else if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) != 1)) {
			return year * 12 + month;
		} else {
			return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
		}
	}

	/**
	 * 取得数据库主键 生成格式为yyyyMMddhhmmssSSS+k位随机数
	 * 
	 * @param k
	 *            表示是取几位随机数，可以自己定
	 */
	public static String getSerialNo(int k) {
		return getDateId() + getRandom(k);
	}

	private static long seed = 1;

	/**
	 * 返回一个随机数
	 * 
	 * @param i
	 *            表示是取几位随机数，可以自己定
	 * @return
	 */
	public static String getRandom(int i) {
		Random jjj = new Random(seed);
		seed++;
		String jj = "";
		for (int k = 0; k < i; k++) {
			jj += jjj.nextInt(9);
		}
		return jj;
	}

	/**
	 * 
	 * @param date
	 */
	public static boolean isRightDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (date == null || date.equals("")) {
			return false;
		}
		if (date.length() > 10) {
			sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		try {
			Date dt = sdf.parse(date);
			return dt == null ? false : true;
		} catch (ParseException pe) {
			return false;
		}
	}

	/***************************************************************************
	 * //nd=1表示返回的值中包含年度 //yf=1表示返回的值中包含月份 //rq=1表示返回的值中包含日期 //format表示返回的格式 1
	 * 以年月日中文返回 2 以横线-返回 // 3 以斜线/返回 4 以缩写不带其它符号形式返回 // 5 以点号.返回
	 **************************************************************************/
	public static String getStringDateMonth(String sdate, String nd, String yf, String rq, String format) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		String s_nd = dateString.substring(0, 4); // 年份
		String s_yf = dateString.substring(5, 7); // 月份
		String s_rq = dateString.substring(8, 10); // 日期
		String sreturn = "";
		if (sdate == null || sdate.equals("") || !isRightDate(sdate)) { // 处理空值情况
			if (nd.equals("1")) {
				sreturn = s_nd;
				// 处理间隔符
				if (format.equals("1")) {
					sreturn = sreturn + "年";
				} else if (format.equals("2")) {
					sreturn = sreturn + "-";
				} else if (format.equals("3")) {
					sreturn = sreturn + "/";
				} else if (format.equals("5")) {
					sreturn = sreturn + ".";
				}
			}
			// 处理月份
			if (yf.equals("1")) {
				sreturn = sreturn + s_yf;
				if (format.equals("1")) {
					sreturn = sreturn + "月";
				} else if (format.equals("2")) {
					sreturn = sreturn + "-";
				} else if (format.equals("3")) {
					sreturn = sreturn + "/";
				} else if (format.equals("5")) {
					sreturn = sreturn + ".";
				}
			}
			// 处理日期
			if (rq.equals("1")) {
				sreturn = sreturn + s_rq;
				if (format.equals("1")) {
					sreturn = sreturn + "日";
				}
			}
		} else {
			// 是一个合法的日期值，则先将其转换为标准的时间格式
			sdate = formatDateStrToStr(sdate, "yyyy-MM-dd");
			s_nd = sdate.substring(0, 4); // 年份
			s_yf = sdate.substring(5, 7); // 月份
			s_rq = sdate.substring(8, 10); // 日期
			if (nd.equals("1")) {
				sreturn = s_nd;
				// 处理间隔符
				if (format.equals("1")) {
					sreturn = sreturn + "年";
				} else if (format.equals("2")) {
					sreturn = sreturn + "-";
				} else if (format.equals("3")) {
					sreturn = sreturn + "/";
				} else if (format.equals("5")) {
					sreturn = sreturn + ".";
				}
			}
			// 处理月份
			if (yf.equals("1")) {
				sreturn = sreturn + s_yf;
				if (format.equals("1")) {
					sreturn = sreturn + "月";
				} else if (format.equals("2")) {
					sreturn = sreturn + "-";
				} else if (format.equals("3")) {
					sreturn = sreturn + "/";
				} else if (format.equals("5")) {
					sreturn = sreturn + ".";
				}
			}
			// 处理日期
			if (rq.equals("1")) {
				sreturn = sreturn + s_rq;
				if (format.equals("1")) {
					sreturn = sreturn + "日";
				}
			}
		}
		return sreturn;
	}

	public static String getNextMonthDay(String sdate, int m) {
		sdate = formatDateStrToStr(sdate, "yyyy-MM-dd");
		int year = Integer.parseInt(sdate.substring(0, 4));
		int month = Integer.parseInt(sdate.substring(5, 7));
		month = month + m;
		if (month < 0) {
			month += 12;
			year--;
		} else if (month > 12) {
			month -= 12;
			year++;
		}
		String smonth = "";
		if (month < 10) {
			smonth = "0" + month;
		} else {
			smonth = "" + month;
		}
		return year + "-" + smonth + "-01";
	}

	/**
	 * 获得 指定日期 前后 多少天的某一天
	 * 
	 * @param today
	 * @param dayCount
	 * @return
	 */
	public static Date getSomeDay(Date today, int dayCount) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(today);
		startCalendar.add(Calendar.DATE, dayCount);
		return startCalendar.getTime();
	}

	/**
	 * 获得 指定日期 前后 多少天的某一天
	 * 
	 * @param today
	 * @param dayCount
	 * @return
	 */
	public static Date getSomeDay(String today, int dayCount) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(getShortDate(today));
		startCalendar.add(Calendar.DATE, dayCount);
		return startCalendar.getTime();
	}

	/**
	 * 获取指定时间的UTC时间
	 * 
	 * @param today
	 * @return
	 */
	public static Date getUTCTime(Date today) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(today);
		// 2、取得时间偏移量：
		final int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
		// 3、取得夏令时差：
		final int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return cal.getTime();
	}

	/**
	 * 获得 指定日期 最后一秒 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getDayLastTime(String date) {
		return date + " 23:59:59";
	}

	/**
	 * 获得 指定日期 第一秒 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getDayFirstTime(String date) {
		return date + " 00:00:00";
	}

	/**
	 * 获取指定所属时间的当前周的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        if(1 == dayWeek) {  
            cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值   
        return getFirstTimeOfDay(cal.getTime());
	        
    }

	/**
	 * 获取指定日期的所属周的最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        if(1 == dayWeek) {  
            cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值   
        cal.add(Calendar.DATE, 6);  
        return getLastTimeOfDay(cal.getTime());
    }

	public static String tranTime(Date date) {
		long delta = new Date().getTime() - date.getTime();
		if (delta < 1L * ONE_MINUTE) {
			return SECOND_LEVEL_AGO;
		}
		if (delta < 1L * ONE_HOUR) {
			long minutes = toMinutes(delta);
			return minutes + ONE_MINUTE_AGO;
		}
		if (delta < 24L * ONE_HOUR) {
			long hours = toHours(delta);
			return hours + ONE_HOUR_AGO+formatDate(date,"HH:mm");
		}
		if (delta < 48L * ONE_HOUR) {
			return "昨天"+formatDate(date,"MM-dd HH:mm");
		}
		if (delta < 96L * ONE_HOUR) {
			return "前天"+formatDate(date,"MM-dd HH:mm");
		}
		return formatDate(date,"yyyy-MM-dd HH:mm");
	}

	private static long toSeconds(long date) {
		return date / 1000L;
	}

	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}

	private static long toHours(long date) {
		return toMinutes(date) / 60L;
	}

}
