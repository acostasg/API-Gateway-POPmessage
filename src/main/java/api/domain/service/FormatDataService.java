package api.domain.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDataService {
public static String DataFormat(Date date){
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    return dateFormat.format(date);
}
}
