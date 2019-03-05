package com.starsgroupchina.credit.server.utils;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final ThreadLocal<DateFormat> yyyyMMddFormatter = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    /**
     * 整数位为月数差，小数位为天数
     * @param start
     * @param end
     * @return
     */
    public static float betweenMonth(LocalDate start, LocalDate end){
        //因Period的between不包含end，所以需要+1天
        end.plusDays(1);
        Period period = Period.between(start, end);
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();
        float between = years > 0 ? years * 12 : years;
        between = between + months;
        return  between;
    }

    public static LocalDateTime timestampReversDate(Long date) {
        Instant instant = Instant.ofEpochMilli(date);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 字符串格式：yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatCurrentDateStr(Date date) {
        return date == null ? null : yyyyMMddFormatter.get().format(date);
    }

    /**
     * 字符串格式：yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date, String patten) {
        return date == null ? null : new SimpleDateFormat(patten).format(date);
    }

    /**
     * 字符串格式：yyyy-MM-dd
     *
     * @param dateString
     * @return
     */
    public static Date parseCurrentDate(String dateString) {
        try {
            return StringUtils.isBlank(dateString) ? null : yyyyMMddFormatter.get().parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取当天0点0分0秒
     * @return
     */
    public static Date getNowDayInitial(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero;
    }

    /**
     * 计算2个日期之间相差的  相差多少年月日
     * 比如：2011-02-02 到  2017-03-02 相差 6年，1个月，0天
     * @param fromDate
     * @param toDate
     * @return
     */
    public static DayCompare dayComparePrecise(Date fromDate,Date toDate){
        Calendar  from  =  Calendar.getInstance();
        from.setTime(fromDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(toDate);
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int fromDay = from.get(Calendar.DAY_OF_MONTH);
        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int toDay = to.get(Calendar.DAY_OF_MONTH);
        int year = toYear  -  fromYear;
        int month = toMonth  - fromMonth;
        int day = toDay  - fromDay;
        return DayCompare.builder().day(day).month(month).year(year).build();
    }
    /**
     * 计算2个日期之间相差的  以年、月、日为单位，各自计算结果是多少
     * 比如：2011-02-02 到  2017-03-02
     *                                以年为单位相差为：6年
     *                                以月为单位相差为：73个月
     *                                以日为单位相差为：2220天
     * @param fromDate
     * @param toDate
     * @return
     */
    public static DayCompare dayCompare(Date fromDate,Date toDate){
        if (fromDate==null){
            return DayCompare.builder().day(0).month(0).year(0).build();
        }
        Calendar  from  =  Calendar.getInstance();
        from.setTime(fromDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(toDate);
        //只要年月
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int year = toYear  -  fromYear;
        int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth);
        int day = (int) ((to.getTimeInMillis()  -  from.getTimeInMillis())  /  (24  *  3600  *  1000));
        return DayCompare.builder().day(day).month(month).year(year).build();
    }
    @Data
    @Builder
    public static class DayCompare{
        private int year;
        private int month;
        private int day;
    }
}
