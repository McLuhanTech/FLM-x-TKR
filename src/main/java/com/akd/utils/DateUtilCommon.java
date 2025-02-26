package com.akd.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class DateUtilCommon {

    private static final int MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;  // 时间计算格式

    public static final String FMT_yyyyMMdd = "yyyyMMdd";
    public static final String FMT_yyyyMMddHHmm = "yyyyMMddHHmm";
    public static final String FMT_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String FMT_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    public static final String FMT_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String FMT_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String FMT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String FMT_YYYY_MM_DD_DIAGONAL = "yyyy/MM/dd";

    /**
     * 字符日期串转换成DATE对象
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date str2date(String dateStr, String pattern) {
        if (StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(pattern)) {
            return null;
        }
        if(dateStr.contains(".")){
            dateStr= dateStr.replace(".", "-");
        }
        if(dateStr.contains("/")){
            dateStr= dateStr.replace("/", "-");
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(dateStr);
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            return null;
        }
    }

    public static String getSimpleDateStr(Date date){
        if(date == null) return "";
        return date2str(date, FMT_yyyy_MM_dd);
    }

    public static String getDefaultDateStr(Date date){
        if(date == null) return "";
        return date2str(date, FMT_yyyy_MM_dd_HH_mm_ss);
    }

    public static Date getDefaultDate(String dateStr){
        if(StringUtils.isEmpty(dateStr))
            return new Date();
        return str2date(dateStr,FMT_yyyy_MM_dd_HH_mm_ss);
    }


    /**
     * DATE对象转换成字符日期串
     *
     * @param date
     * @param pattern
     * @return String
     */
    public static String date2str(Date date, String pattern) {
        if (date == null || StringUtils.isEmpty(pattern)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            return null;
        }
    }

    /**
     * 返回系统当前的完整日期时间 <br>
     * 格式 1：2008-05-02 13:12:44 <br>
     * 格式 2：2008/05/02 13:12:44 <br>
     * 格式 3：2008年5月2日 13:12:44 <br>
     * 格式 4：2008年5月2日 13时12分44秒 <br>
     * 格式 5：2008年5月2日 星期五 13:12:44 <br>
     * 格式 6：2008年5月2日 星期五 13时12分44秒 <br>
     * 格式 7：20080502 <br>
     * 格式 8：20080502131244 <br>
     * 格式 9：2008-05-02 <br>
     * 格式 10：2008_05 <br>
     * 格式 11：2008 <br>
     * 格式 12：200805 <br>
     * 格式 13：2008-05 <br>
     * 格式 13：13 <br>
     * 格式 default：yyyyMMddHHmmss:20080502131244 <br>
     *
     * @param formatType (formatType) :格式代码号
     * @return 字符串
     */
    public static String get(int formatType, Date date) {
        SimpleDateFormat sdf = null;
        switch (formatType) {
        case 1:
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            break;
        case 2:
            sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            break;
        case 3:
            sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            break;
        case 4:
            sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
            break;
        case 5:
            sdf = new SimpleDateFormat("yyyy年MM月dd日 E HH:mm:ss");
            break;
        case 6:
            sdf = new SimpleDateFormat("yyyy年MM月dd日 E HH时mm分ss秒");
            break;
        case 7:
            sdf = new SimpleDateFormat("yyyyMMdd");
            break;
        case 8:
            sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            break;
        case 9:
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            break;
        case 10:
            sdf = new SimpleDateFormat("yyyy_MM");
            break;
        case 11:
            sdf = new SimpleDateFormat("yyyy");
            break;
        case 12:
            sdf = new SimpleDateFormat("yyyyMM");
            break;
        case 13:
            sdf = new SimpleDateFormat("yyyy-MM");
            break;
        case 14:
            sdf = new SimpleDateFormat("yyyy年MM月dd日");
            break;
        case 15:
            sdf = new SimpleDateFormat("MM月dd日");
            break;
        case 16:
            sdf = new SimpleDateFormat("HH");
            break;
        case 17:
            sdf = new SimpleDateFormat("yyyy/MM/dd");
            break;
        default:
            sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            break;
        }
        sdf.setLenient(false);
        return sdf.format(date);
    }
    
    public static Date get(int formatType, Date date, String pattern) {
		String dateStr = get(formatType, date);
		return str2dateWithLenientFalse(dateStr, pattern);
		
	}

    /**
     * 获取当前时间0点
     * @param date
     * @return
     */
    public static Date getZeroOClick(Date date) {
        if(date == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前时间24点
     * @param date
     * @return
     */
    public static Date get24Clock(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 加 months 月
     * @param date
     * @param months
     * @return
     */
    public static Date addMonth(Date date, int months) {
        if(date == null) return null;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }


    /**
     * 加 days 天
     * @param date
     * @param days
     * @return
     */
    public static Date addDay(Date date, int days) {
        if(date == null) return null;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 加 hours 小时
     * @param date
     * @param hours
     * @return
     */
    public static Date addHour(Date date,int hours){
        if(date == null) return null;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * 加 minute 分钟
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinute(Date date, int minute){
        if(date == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 加 seconds 秒
     * @param date
     * @param seconds
     * @return
     */
    public static Date addSecond(Date date, int seconds) {
        if(date == null) return null;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 字符日期串转换成DATE对象
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date str2dateWithLenientFalse(String dateStr, String pattern) {
        if (StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(pattern)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setLenient(false);
            return sdf.parse(dateStr);
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            return null;
        }
    }

    /**
     * 获取该月的第一天 0点
     *
     * @param date
     * @return
     */
    public static Date getMonthStartTime(Date date) {
        if (date == null)
            date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 获取当月的指定天
     * @param date
     * @param day
     * @return
     */
    public static Date getCurMonthSpecialDay(Date date,Integer day){
        if (date == null)
            date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Integer maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if(day == 0 || maxDay.compareTo(day) < 0 ){
            day = maxDay;
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 获取当月的指定天
     * @param date
     * @param day
     * @return
     */
    public static Date getNextMonthSpecialDay(Date date,Integer day){
        if (date == null)
            date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        Integer maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if(day == 0 || maxDay.compareTo(day) < 0 ){
            day = maxDay;
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 取得两个时间之间相差的天数,参数类型为Date
     * @param startDate 开始的时间
     * @param endDate 结束的时间
     * @return ( endDate - startDate +1 )
     */
    public static int getDaysBetween(Date startDate, Date endDate) {
        if(null == startDate || null == endDate){return 0;}
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(startDate);
        calendarStart.set(calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DATE), 0, 0, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(endDate);
        calendarEnd.set(calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DATE), 0, 0, 0);
        calendarEnd.set(Calendar.MILLISECOND, 0);

        long startTime = calendarStart.getTimeInMillis();
        long endTime = calendarEnd.getTimeInMillis();
        long totalDate = Math.abs((endTime - startTime)) / (MILLIS_IN_A_DAY);
        return (int) totalDate + 1;
    }


    /**
     * 取得两个时间之间相差的天数,参数类型为Date
     * @param startDate 开始的时间
     * @param endDate 结束的时间
     * @return ( endDate - startDate )
     */
    public static int getDaysBetweenY(Date startDate, Date endDate) {
        if(null == startDate || null == endDate){return 0;}
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(startDate);
        calendarStart.set(calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DATE), 0, 0, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(endDate);
        calendarEnd.set(calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DATE), 0, 0, 0);
        calendarEnd.set(Calendar.MILLISECOND, 0);

        long startTime = calendarStart.getTimeInMillis();
        long endTime = calendarEnd.getTimeInMillis();
        long totalDate = (endTime - startTime) / (MILLIS_IN_A_DAY);
        return (int) totalDate ;
    }


    public static String getDaysDelta(Date startDate,Date endDate){
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(startDate);
        calendarStart.set(calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DATE), 0, 0, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(endDate);
        calendarEnd.set(calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DATE), 0, 0, 0);
        calendarEnd.set(Calendar.MILLISECOND, 0);

        long startTime = calendarStart.getTimeInMillis();
        long endTime = calendarEnd.getTimeInMillis();
        long totalDate = (endTime - startTime) / (MILLIS_IN_A_DAY);
        return String.valueOf(totalDate);
    }


}
