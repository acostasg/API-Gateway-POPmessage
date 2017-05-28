package api.domain.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormatDataService {

    private static final Locale LOCALE = Locale.ENGLISH;
    private static final String DD_MM_YYYY = "dd/mm/yyyy";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static String DataFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD, LOCALE);
        return dateFormat.format(date);
    }

    public static Date getDateExpiration() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 6);

        return cal.getTime();
    }

    public static Date getDateFromString(String dateOfBirth) {
        DateFormat format = new SimpleDateFormat(DD_MM_YYYY, LOCALE);
        Date date;
        try {
            date = format.parse(dateOfBirth);
        } catch (ParseException e) {
            date = new Date();
        }
        return date;
    }

    public static String getDateFromDate(Date dateOfBirth) {
        SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY, LOCALE);
        return sdf.format(dateOfBirth);
    }
}
