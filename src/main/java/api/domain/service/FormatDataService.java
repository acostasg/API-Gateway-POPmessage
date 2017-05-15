package api.domain.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormatDataService {
    public static String DataFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
        DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
        Date date;
        try {
            date = format.parse(dateOfBirth);
        } catch (ParseException e) {
            date = new Date();
        }
        return date;
    }

    public static String getDateFromDate(Date dateOfBirth) {
        String DATE_FORMAT_NOW = "dd/mm/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(dateOfBirth);
    }
}
