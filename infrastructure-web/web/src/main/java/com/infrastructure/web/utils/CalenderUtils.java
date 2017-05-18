package com.infrastructure.web.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by admin on 2016/2/15.
 */
public class CalenderUtils {

    /**
     * 得到某年某月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, year);

        cal.set(Calendar.MONTH, month - 1);

        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));


        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 得到某年某月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, year);

        cal.set(Calendar.MONTH, month - 1);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);

        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

    }

    public static String getDayOfYear() {
        //得到当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String time = "";
        if (month < 10) {
            if (day < 10) {
                time = year + "-0" + month + "-0" + day;
            } else {
                time = year + "-0" + month + "-" + day;
            }
        } else {
            time = year + "-" + month + day;
        }
        return time;
    }

    public static String getPieDayOfYear(String allStartDate,String allEndDate) {
        String monthYear = "";
        String yyEnd = allEndDate.substring(0,4);
        String yyStart = allStartDate.substring(0,4);
        String mmEnd = allEndDate.substring(5,6);
        String mmStart = allStartDate.substring(5,6);
        if(mmEnd.equals("0")){
            mmEnd = allEndDate.substring(6,7);
        }else {
            mmEnd = allEndDate.substring(5,7);
        }
        if(mmStart.equals("0")){
            mmStart = allStartDate.substring(6,7);
        }else {
            mmStart = allStartDate.substring(5,7);
        }
        //隔年查询时
        if(Integer.parseInt(yyEnd) > Integer.parseInt(yyStart)){
            monthYear += yyStart+"年"+mmStart+"月至"+yyEnd+"年"+mmEnd+"月";
        }else {
            if(mmStart.equals(mmEnd)){
                monthYear += yyStart+"年"+mmStart+"月";
            }else {
                monthYear += yyStart+"年"+mmStart+"月至"+mmEnd+"月";
            }
        }
        return monthYear;
    }
}
