package com.findcup.pydeal.utils;

import com.sun.org.glassfish.external.statistics.Statistic;
import sun.text.resources.cldr.ti.FormatData_ti_ER;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /**
     * 格式化Date
     *
     * @param date
     * @return
     *
     */
    public static String getDateString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }
}
