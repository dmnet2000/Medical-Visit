package it.dmnet.medical.visit.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Utils {
    public static Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(dateString);
    }

    public static LocalDate convertStringToLocalDate(String dateString) throws ParseException {

        return  LocalDate.parse(dateString);
    }

}
