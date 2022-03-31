package pl.karinawojtek.ztiserver.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FromStringToDateFormatter {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    private SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);

    public Date parseDate(String dateText) throws ParseException {
        Date date = dateFormatter.parse(dateText);
        return date;
    }

}
