package net.avenwu.yoyogithub.util;

import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by aven on 3/31/16.
 */
public class TimeUtils {

    static String[] DATE_FORMAT = {"yyyy-MM-dd'T'HH:mm:ss'Z'"};
    static SparseArray<WeakReference<SimpleDateFormat>> sDateFormat = new SparseArray<>();

    public static SimpleDateFormat newFormatter(int formatIndex) {
        WeakReference<SimpleDateFormat> reference = sDateFormat.get(formatIndex);
        if (reference == null || reference.get() == null) {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT[formatIndex], Locale.CHINA);
            sDateFormat.put(formatIndex, new WeakReference<>(format));
            return format;
        }
        return reference.get();
    }

    /**
     * eg:2012-04-08T00:58:02Z
     *
     * @param timeString
     * @return
     */
    public static Date toGitHubDate(String timeString) {
        try {
            return newFormatter(0).parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Calendar.getInstance().getTime();
    }

    /**
     * {@link net.avenwu.yoyogithub.model.User#created_at}
     *
     * @param timeString
     * @return
     */
    public static String formatTimeASJoined(String timeString) {
        Date date = toGitHubDate(timeString);
        return "Joined on " + DateFormat.getDateInstance().format(date);
    }

    public static String formatTimeAsRecently(String time) {
        Date date = toGitHubDate(time);
        Date currentDate = Calendar.getInstance().getTime();
        long timeDiff = currentDate.getTime() - date.getTime();
        String timeString;
        if (timeDiff < TimeUnit.MINUTES.toMillis(3)) {
            timeString = "Updated just now";
        } else if (timeDiff < TimeUnit.HOURS.toMillis(1)) {
            timeString = "Updated " + timeDiff / TimeUnit.HOURS.toMillis(1) + " minutes ago";
        } else if (timeDiff < TimeUnit.DAYS.toMillis(8)) {
            timeString = "Updated " + timeDiff / TimeUnit.DAYS.toMillis(1) + " days ago";
        } else {
            timeString = "Updated on " + DateFormat.getDateInstance().format(date);
        }
        return timeString;
    }
}
