package org.idchavan.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
  
	public List<String> getFinalcialYear(int fromYear, int toYear) {

        List<String> finalcialYears = new ArrayList<String>(); 
        if (("" + fromYear).length() < 4) {
            throw new IllegalArgumentException("The value shoud be 4 digit of fromYear. Eg. 2017. The value is given ["+fromYear+"]");
        } else if (("" + toYear).length() < 4) {
            throw new IllegalArgumentException("The value shoud be 4 digit of toYear. Eg. 2017. The value is given ["+toYear+"]");
        }
        
        Calendar fromYearCal = Calendar.getInstance();
        fromYearCal.set(Calendar.YEAR, fromYear);
        fromYearCal.set(Calendar.MONTH, Calendar.MARCH);

        Calendar toYearCal = Calendar.getInstance();
        toYearCal.set(Calendar.YEAR, toYear);
        toYearCal.set(Calendar.MONTH, Calendar.MARCH);

        SimpleDateFormat sdfFromYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfToYear = new SimpleDateFormat("yy");
        
        int endYear = Integer.parseInt(sdfToYear.format(toYearCal.getTime()));
        
        for (int increaseYear = 0; increaseYear < endYear; increaseYear++) {
            if( increaseYear == 0 ){
                toYearCal.set(Calendar.YEAR, fromYearCal.get(Calendar.YEAR));
                toYearCal.add(Calendar.YEAR, 1);
            }else{
                fromYearCal.add(Calendar.YEAR, 1);
                toYearCal.set(Calendar.YEAR, fromYearCal.get(Calendar.YEAR));
                toYearCal.add(Calendar.YEAR, 1);
            }
            if( fromYearCal.get(Calendar.YEAR) == toYear ){
                break;
            }
            finalcialYears.add( sdfFromYear.format(fromYearCal.getTime()) + "-" + sdfToYear.format(toYearCal.getTime()) );
        }
        System.out.println(finalcialYears);
        return finalcialYears;
    }


}
