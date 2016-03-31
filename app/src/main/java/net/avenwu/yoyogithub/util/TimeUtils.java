package net.avenwu.yoyogithub.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by aven on 3/31/16.
 */
public class TimeUtils {
    /**
     * {@link net.avenwu.yoyogithub.model.User#created_at}
     *
     * @param timeString
     * @return
     */
    public static String formatTime(String timeString) {
        //2012-04-08T00:58:02Z
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CHINA);
        try {
            Date date = format.parse(timeString);
            return DateFormat.getDateInstance().format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeString;
    }
}
