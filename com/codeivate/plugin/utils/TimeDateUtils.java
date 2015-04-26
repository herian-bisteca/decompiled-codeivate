package com.codeivate.plugin.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;














public class TimeDateUtils
{
  public static int getSegment(Calendar cal)
  {
    DateFormat timeFormatHour = new SimpleDateFormat("HH");
    DateFormat timeFormatMinute = new SimpleDateFormat("mm");
    int segment = 12 * Integer.parseInt(timeFormatHour.format(cal.getTime()));
    segment += Integer.parseInt(timeFormatMinute.format(cal.getTime())) / 5;
    return segment;
  }
  

  public static String getDurationString(int seconds)
  {
    int hours = seconds / 3600;
    int minutes = seconds % 3600 / 60;
    seconds %= 60;
    
    return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
  }
  
  private static String twoDigitString(int number)
  {
    if (number == 0) {
      return "00";
    }
    
    if (number / 10 == 0) {
      return "0" + number;
    }
    
    return String.valueOf(number);
  }
}
