package com.tw.userapp.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

	
    public LocalDate   InstanttoLocalDate(Instant instant) {
    	DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd/MM/uuuu" ) ;	
    	return instant.atZone(ZoneId.systemDefault()).toLocalDate();
       
    }

    /*public void setStartDate(LocalDate startDate) {
        Date date = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.startDate = date.toInstant();
    } */

}
