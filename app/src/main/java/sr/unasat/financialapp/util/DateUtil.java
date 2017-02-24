package sr.unasat.financialapp.util;

import java.util.Date;

/**
 * Created by Jair on 2/9/2017.
 */

public class DateUtil {
    private static int ordinal;

    private static int day_to_int(String dayStr){

        int dayInt ;
        switch (dayStr.toLowerCase()){
            case "mon":
                dayInt = 1;
                break;
            case "tue":
                dayInt = 2;
                break;
            case "wed":
                dayInt = 3;
                break;
            case "thu":
                dayInt = 4;
                break;
            case "fri":
                dayInt = 5;
                break;
            case "sat":
                dayInt = 6;
                break;
            case "sun":
                dayInt = 7;
                break;
            default:
                dayInt=0;
        }


        return dayInt;
    }

    public static String int_to_month(int month){
        String monthStr ;
        switch (month){
            case 1:
                monthStr = "january";
                break;
            case 2:
                monthStr = "february";
                break;
            case 3:
                monthStr = "march";
                break;
            case 4:
                monthStr = "april";
                break;
            case 5:
                monthStr = "may";
                break;
            case 6:
                monthStr = "june";
                break;
            case 7:
                monthStr = "july";
                break;
            case 8:
                monthStr = "august";
                break;
            case 9:
                monthStr = "september";
                break;
            case 10:
                monthStr = "october";
                break;
            case 11:
                monthStr = "november";
                break;
            case 12:
                monthStr = "december";
                break;
            default:
                monthStr=null;
        }


        return monthStr;
    }


    private static int month_to_int(String monthStr){

        int monthInt ;
        switch (monthStr.toLowerCase()){
            case "jan":
                monthInt = 1;
                ordinal =0;
                break;
            case "feb":
                monthInt = 2;
                ordinal =31;
                break;
            case "mar":
                monthInt = 3;
                ordinal =59;
                break;
            case "apr":
                monthInt = 4;
                ordinal =90;
                break;
            case "may":
                monthInt = 5;
                ordinal =120;
                break;
            case "jun":
                monthInt = 6;
                ordinal =151;
                break;
            case "jul":
                monthInt = 7;
                ordinal =181;
                break;
            case "aug":
                monthInt = 8;
                ordinal =212;
                break;
            case "sep":
                monthInt = 9;
                ordinal =243;
                break;
            case "oct":
                monthInt = 10;
                ordinal =273;
                break;
            case "nov":
                monthInt = 11;
                ordinal =304;
                break;
            case "dec":
                monthInt = 12;
                ordinal =334;
                break;
            default:
                monthInt=0;
        }


        return monthInt;
    }

    public static int[] convertDate(Date date){
        //for example : fri feb 10 04.32.27 est 2017
        String dateStr = String.valueOf(date);
        String[] array = dateStr.split("\\s");

        int weekday = day_to_int(array[0]);
        int month = month_to_int(array[1]);
        int day = Integer.valueOf(array[2]);
        int year = Integer.valueOf(array[5]);
        int weekYear;
        //Ordinal day: 244 + 26 = 270
        //Weekday: Friday = 5
        //270 − 5 + 10 = 275
        //275 / 7 = 39.28…
        //Result: Week 39

        //int theDay = ordinal + day;
        //int vat = theDay - weekday;
        //int vat2 = vat + 10;
        //double result = vat2/7;
         if (year % 4 == 0){

             if (month == 1 || month == 2){
                 weekYear =(int) Math.round((((ordinal + day) - weekday) + 10)/7);
             }else{
                 ordinal++;

             }
         }
        weekYear =(int) Math.round((((ordinal + day) - weekday) + 10)/7);

        return new int[] {year,month,weekYear,weekday,day};
    }

}
