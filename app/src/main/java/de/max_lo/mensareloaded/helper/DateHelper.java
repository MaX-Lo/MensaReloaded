package de.max_lo.mensareloaded.helper;

import java.time.LocalDate;
import java.util.Date;

public class DateHelper {

    public static String getDateStringFromDaysSinceEpoch(long daysSinceEpoch) {
        // Todo test it and write tests
        String date;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDate.ofEpochDay(daysSinceEpoch).toString();
        } else {
            Date d = new Date();
            d.setTime(daysSinceEpoch * 86400000);
            date = d.toString();
        }
        return date;
    }

    public static long getDaysSinceEpoch() {
        return (System.currentTimeMillis()) / 86400000;
    }
}
