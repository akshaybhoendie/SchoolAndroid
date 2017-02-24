package sr.unasat.financialapp.util;

import java.util.Date;

/**
 * Created by Jair on 2/9/2017.
 */

public class DateUtil {
    private static int original;

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
                monthInt = 1;original=0;
                break;
            case "feb":
                monthInt = 2;original=31;
                break;
            case "mar":
                monthInt = 3;original=59;
                break;
            case "apr":
                monthInt = 4;original=90;
                break;
            case "may":
                monthInt = 5;original=120;
                break;
            case "jun":
                monthInt = 6;original=151;
                break;
            case "jul":
                monthInt = 7;original=181;
                break;
            case "aug":
                monthInt = 8;original=212;
                break;
            case "sep":
                monthInt = 9;original=243;
                break;
            case "oct":
                monthInt = 10;original=273;
                break;
            case "nov":
                monthInt = 11;original=304;
                break;
            case "dec":
                monthInt = 12;original=334;
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
        //Ordinal day: 244 + 26 = 270
        //Weekday: Friday = 5
        //270 − 5 + 10 = 275
        //275 / 7 = 39.28…
        //Result: Week 39

        //int theDay = original + day;
        //int vat = theDay - weekday;
        //int vat2 = vat + 10;
        //double result = vat2/7;

        int weekYear =(int) Math.round((((original + day) - weekday) + 10)/7);

        return new int[] {year,month,weekYear,weekday,day};
    }




}
