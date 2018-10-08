package de.max_lo.mensareloaded.helper;

import android.util.Log;
import java.time.LocalDate;

public class DateHelper {

    public static String getDateStringFromDaysSinceEpoch(long daysSinceEpoch) {
        // Todo test it and write tests
        LocalDate date = LocalDate.ofEpochDay(daysSinceEpoch);
        return date.toString();
    }
}
