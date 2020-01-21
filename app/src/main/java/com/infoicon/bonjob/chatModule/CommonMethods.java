package com.infoicon.bonjob.chatModule;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by infoicona on 26/5/17.
 */

public class CommonMethods {
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
    private static DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.FRENCH);

    public static String getCurrentTime() {
        Date today = Calendar.getInstance().getTime();
        return timeFormat.format(today);
    }

    public static String getCurrentDate() {
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    public static String getConvertedTime(Date date) {
        Date today = date;
        return timeFormat.format(today);
    }

    public static String getConvertedDate(Date date) {
        Date today = date;
        return dateFormat.format(today);
    }

    public static long getDateInMillis(String dates) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(dates));
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
