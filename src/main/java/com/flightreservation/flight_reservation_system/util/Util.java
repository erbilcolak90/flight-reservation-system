package com.flightreservation.flight_reservation_system.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    private static final String FLIGHT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static Date parseFlightDate(String inputDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FLIGHT_DATE_FORMAT);
        try {
            return dateFormat.parse(inputDate);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid flight date format", e);
        }
    }
}
