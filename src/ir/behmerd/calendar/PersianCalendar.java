package ir.behmerd.calendar;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PersianCalendar {
	
	public PersianCalendar(){}
	
    public String convertToPersian(int Year, int Month, int Day)
    {
        return ConvertToPersianDate(Year, Month, Day);
    }

    public String convertToPersian(String FullDate)
    {
        String[] date = SplitDate(FullDate);
        return ConvertToPersianDate(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2]));
    }

    public String convertToGregorian(int Year, int Month, int Day)
    {
        return ConvertToGregorianDate(Year, Month, Day);
    }

    public String convertToGregorian(String FullDate)
    {
        String[] date = SplitDate(FullDate);
        return ConvertToGregorianDate(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2]));
    }

    public String getNow()
    {
        Calendar calendar = Calendar.getInstance();
        return  convertToPersian(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    public int getYear()
    {
        Calendar calendar = Calendar.getInstance();
        String date = convertToPersian(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
        String[] year = SplitDate(date);
        return Integer.valueOf(year[0]);
    }

    public int getMonth()
    {
        Calendar calendar = Calendar.getInstance();
        String date = convertToPersian(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
        String[] month = SplitDate(date);
        return Integer.valueOf(month[1]);
    }

    public int getDay()
    {
        Calendar calendar = Calendar.getInstance();
        String date = convertToPersian(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
        String[] day = SplitDate(date);
        return Integer.valueOf(day[2]);
    }

    public int getYearOf(int Year, int Month, int Day)
    {
        String date = convertToPersian(Year, Month, Day);
        String[] year = SplitDate(date);
        return Integer.valueOf(year[0]);
    }

    public int getMonthOf(int Year, int Month, int Day)
    {
        String date = convertToPersian(Year, Month, Day);
        String[] month = SplitDate(date);
        return Integer.valueOf(month[1]);
    }

    public int getDayOf(int Year, int Month, int Day)
    {
        String date = convertToPersian(Year, Month, Day);
        String[] day = SplitDate(date);
        return Integer.valueOf(day[2]);
    }

    public int getMaxDay(int year, int month)
    {
        int maxday;

        if(month>=1 && month<=6)
            maxday = 31;
        else if(month>=7 && month<=11)
            maxday = 30;
        else
            maxday = (PersianLeapYear(year)) ? 30 : 29;

        return maxday;
    }

    public int getDayOfWeek(String fullDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(StringToDate(convertToGregorian(fullDate),"MM/dd/yyyy"));
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        if(weekday==7)
            return 0;
        else
            return weekday;
    }
    

	private String[] SplitDate(String FullDate){
		String[] date = FullDate.split("/");
        if(date.length<2)
            date = FullDate.split("-");
        return date;
	}

    private boolean GregorianLeapYear(int Year)
    {
        if(Year%4 == 0)
        {
            if(Year%100 != 0)
                return true;
            else
                return (Year%400 == 0);
        }
        return false;
    }

    private boolean PersianLeapYear(int Year)
    {
        int d, y;
        d = Year % 100;
        y = Year % 10;
        d -= y;
        d /= 10;
        if ((d == 1 || d == 3 || d == 5 || d == 7 || d == 9) && (y == 1 || y == 5 || y == 9))
            return true;
        else
            return ((d == 0 || d == 2 || d == 4 || d == 6 || d == 8) && (y == 3 || y == 7));
    }
    
    private Date StringToDate(String date,String format)
    {
        if(date==null) 
        	return null;
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
        return simpledateformat.parse(date, position);
    }

    private String ConvertToPersianDate(int Year, int Month, int Day)
    {
        if (GregorianLeapYear(Year))
            return ConvertPersianDate_Leap(Year, Month, Day);
        else
            return ConvertPersianDate_NotLeap(Year, Month, Day, GregorianLeapYear(Year - 1));
    }

    private String ConvertToGregorianDate(int Year, int Month, int Day)
    {
        boolean L = false;
        if ((Month >= 1) && (Month <= 9))
            L = GregorianLeapYear(Year + 621);
        else if (Month == 10)
        {
            if (PersianLeapYear(Year))
            {
                if ((Day >= 1) && (Day <= 11))
                    L = GregorianLeapYear(Year + 621);
            }
            else
            {
                if ((Day >= 1) && (Day <= 10))
                    L = GregorianLeapYear(Year + 621);
            }
        }
        else
            L = GregorianLeapYear(Year + 622);
        if (L)
            return ConvertGregorianDate_Leap(Year, Month, Day);
        else
            return ConvertGregorianDate_NotLeap(Year, Month, Day, PersianLeapYear(Year));
    }

    private String ConvertPersianDate_NotLeap(int Year, int Month, int Day, boolean AL)
    {
        String datestr;
        switch(Month)
        {
            case 1:
                if(AL)
                {
                    if((Day >= 1) && (Day <= 19))
                    {
                        Day += 11;
                        Month += 9;
                    }
                    else if((Day >= 20) && (Day <= 31))
                    {
                        Day -= 19;
                        Month += 10;
                    }
                }
                else
                {
                    if((Day >= 1) && (Day <= 20))
                    {
                        Day += 10;
                        Month += 9;
                    }
                    else if((Day >= 21) && (Day <= 31))
                    {
                        Day -= 20;
                        Month += 10;
                    }
                }
                Year -= 622;
                break;
            case 2:
                if(AL)
                {
                    if((Day >= 1) && (Day <= 18))
                    {
                        Day += 12;
                        Month += 9;
                    }
                    else if((Day >= 19) && (Day <= 28))
                    {
                        Day -= 18;
                        Month += 10;
                    }
                }
                else
                {
                    if((Day >= 1) && (Day <= 19))
                    {
                        Day += 11;
                        Month += 9;
                    }
                    else if((Day >= 20) && (Day <= 28))
                    {
                        Day -= 19;
                        Month += 10;
                    }
                }
                Year -= 622;
                break;
            case 3:
                if((Day >= 1) && (Day <= 20))
                {
                    if(AL)
                    {
                        Day += 10;
                        Month += 9;
                    }
                    else
                    {
                        Day += 9;
                        Month += 9;
                    }
                    Year -= 622;
                }
                else if((Day >= 21) && (Day <= 31))
                {
                    Day -= 20;
                    Month -= 2;
                    Year -= 621;
                }
                break;
            case 4:
                if((Day >= 1) && (Day <= 20))
                {
                    Day += 11;
                    Month -= 3;
                }
                else if((Day >= 21) && (Day <= 30))
                {
                    Day -= 20;
                    Month -= 2;
                }
                Year -= 621;
                break;
            case 5:
                if((Day >= 1) && (Day <= 21))
                {
                    Day += 10;
                    Month -= 3;
                }
                else if((Day >= 22) && (Day <= 31))
                {
                    Day -= 21;
                    Month -= 2;
                }
                Year -= 621;
                break;
            case 6:
                if((Day >= 1) && (Day <= 21))
                {
                    Day += 10;
                    Month -= 3;
                }
                else if((Day >= 22) && (Day <= 30))
                {
                    Day -= 21;
                    Month -= 2;
                }
                Year -= 621;
                break;
            case 7:
                if((Day >= 1) && (Day <= 22))
                {
                    Day += 9;
                    Month -= 3;
                }
                else if((Day >= 23) && (Day <= 31))
                {
                    Day -= 22;
                    Month -= 2;
                }
                Year -= 621;
                break;
            case 8:
                if((Day >= 1) && (Day <= 22))
                {
                    Day += 9;
                    Month -= 3;
                }
                else if((Day >= 23) && (Day <= 31))
                {
                    Day -= 22;
                    Month -= 2;
                }
                Year -= 621;
                break;
            case 9:
                if((Day >= 1) && (Day <= 22))
                {
                    Day += 9;
                    Month -= 3;
                }
                else if((Day >= 23) && (Day <= 30))
                {
                    Day -= 22;
                    Month -= 2;
                }
                Year -= 621;
                break;
            case 10:
                if((Day >= 1) && (Day <= 22))
                {
                    Day += 8;
                    Month -= 3;
                }
                else if((Day >= 23) && (Day <= 31))
                {
                    Day -= 22;
                    Month -= 2;
                }
                Year -= 621;
                break;
            case 11:
                if((Day >= 1) && (Day <= 21))
                {
                    Day += 9;
                    Month -= 3;
                }
                else if((Day >= 22) && (Day <= 30))
                {
                    Day -= 21;
                    Month -= 2;
                }
                Year -= 621;
                break;
            case 12:
                if((Day >= 1) && (Day <= 21))
                {
                    Day += 9;
                    Month -= 3;
                }
                else if((Day >= 22) && (Day <= 31))
                {
                    Day -= 21;
                    Month -= 2;
                }
                Year -= 621;
                break;
        }

        datestr = String.valueOf(Year);
        datestr = datestr + "/";
        if(Month < 10)
            datestr = datestr + "0" + Month;
        else
            datestr = datestr + Month;

        datestr = datestr + "/";
        if(Day < 10)
            datestr = datestr + "0" + Day;
        else
            datestr = datestr + Day;

        return datestr;
    }

    private String ConvertPersianDate_Leap(int Year, int Month, int Day)
    {
        String datestr;
        switch(Month)
        {
            case 1:
                if((Day >= 1) && (Day <= 20))
                {
                    Day += 10;
                    Month += 9;
                }
                else if((Day >= 21) && (Day <= 31))
                {
                    Day -= 20;
                    Month += 10;
                }
                Year -= 622;
                break;
            case 2:
                if((Day >= 1) && (Day <= 19))
                {
                    Day += 11;
                    Month += 9;
                }
                else if((Day >= 20) && (Day <= 29))
                {
                    Day -= 19;
                    Month += 10;
                }
                Year -= 622;
                break;
            case 3:
                if((Day >= 1) && (Day <= 19))
                {
                    Day += 10;
                    Month += 9;
                    Year -= 622;
                }
                else if((Day >= 20) && (Day <= 31))
                {
                    Day -= 19;
                    Month -= 2;
                    Year -= 621;
                }
                break;
            case 4:
                if((Day >= 1) && (Day <= 19))
                {
                    Day += 12;
                    Month -= 3;
                }
                else if((Day >= 20) && (Day <= 30))
                {
                    Day -= 19;
                    Month -= 2;
                }
                Year -= 621;
                break;
            case 5:
                if((Day >= 1) && (Day <= 20))
                {
                    Day += 11;
                    Month -= 3;
                }
                else if((Day >= 21) && (Day <= 31))
                {
                    Day -= 20;
                    Month -= 2;
                }
                Year -= 621;
                break;
            case 6:
                if((Day >= 1) && (Day <= 20))
                {
                    Day += 11;
                    Month -= 3;
                }
                else if((Day >= 21) && (Day <= 30))
                {
                    Day -= 20;
                    Month -= 2;
                }
                Year -= 621;
                break;
            case 7:
                if((Day >= 1) && (Day <= 21))
                {
                    Day += 10;
                    Month -= 3;
                }
                else if((Day >= 22) && (Day <= 31))
                {
                    Day -= 21;
                    Month -= 2;
                }
                Year -= 621;
                break;
            case 8:
                if((Day >= 1) && (Day <= 21))
                {
                    Day += 10;
                    Month -= 3;
                }
                else if((Day >= 22) && (Day <= 31))
                {
                    Day -= 21;
                    Month -= 2;
                }
                Year -= 621;
                break;
            case 9:
                if((Day >= 1) && (Day <= 21))
                {
                    Day += 10;
                    Month -= 3;
                }
                else if((Day >= 22) && (Day <= 30))
                {
                    Day -= 21;
                    Month -= 2;
                }
                Year -= 621;
                break;
            case 10:
                if((Day >= 1) && (Day <= 21))
                {
                    Day += 9;
                    Month -= 3;
                }
                else if((Day >= 22) && (Day <= 31))
                {
                    Day -= 21;
                    Month -= 2;
                }
                Year -= 621;
                break;
            case 11:
                if((Day >= 1) && (Day <= 20))
                {
                    Day += 10;
                    Month -= 3;
                }
                else if((Day >= 21) && (Day <= 30))
                {
                    Day -= 20;
                    Month -= 2;
                }
                Year -= 621;
                break;
            case 12:
                if((Day >= 1) && (Day <= 20))
                {
                    Day += 10;
                    Month -= 3;
                }
                else if((Day >= 21) && (Day <= 31))
                {
                    Day -= 20;
                    Month -= 2;
                }
                Year -= 621;
                break;
        }


        datestr = String.valueOf(Year);
        datestr = datestr + "/";
        if(Month < 10)
            datestr = datestr + "0" + Month;
        else
            datestr = datestr + Month;

        datestr = datestr + "/";
        if(Day < 10)
            datestr = datestr + "0" + Day;
        else
            datestr = datestr + Day;

        return datestr;
    }

    private String ConvertGregorianDate_NotLeap(int Year, int Month, int Day, boolean L)
    {
        String datestr;
        switch(Month)
        {
            case 1:
                if((Day >= 1) && (Day <= 11))
                {
                    Day += 20;
                    Month += 2;
                }
                else if((Day >= 12) && (Day <= 31))
                {
                    Day -= 11;
                    Month += 3;
                }
                Year += 621;
                break;
            case 2:
                if((Day >= 1) && (Day <= 10))
                {
                    Day += 20;
                    Month += 2;
                }
                else if((Day >= 11) && (Day <= 31))
                {
                    Day -= 10;
                    Month += 3;
                }
                Year += 621;
                break;
            case 3:
                if((Day >= 1) && (Day <= 10))
                {
                    Day += 21;
                    Month += 2;
                }
                else if((Day >= 11) && (Day <= 31))
                {
                    Day -= 10;
                    Month += 3;
                }
                Year += 621;
                break;
            case 4:
                if((Day >= 1) && (Day <= 9))
                {
                    Day += 21;
                    Month += 2;
                }
                else if((Day >= 10) && (Day <= 31))
                {
                    Day -= 9;
                    Month += 3;
                }
                Year += 621;
                break;
            case 5:
                if((Day >= 1) && (Day <= 9))
                {
                    Day += 22;
                    Month += 2;
                }
                else if((Day >= 10) && (Day <= 31))
                {
                    Day -= 9;
                    Month += 3;
                }
                Year += 621;
                break;
            case 6:
                if((Day >= 1) && (Day <= 9))
                {
                    Day += 22;
                    Month += 2;
                }
                else if((Day >= 10) && (Day <= 31))
                {
                    Day -= 9;
                    Month += 3;
                }
                Year += 621;
                break;
            case 7:
                if((Day >= 1) && (Day <= 8))
                {
                    Day += 22;
                    Month += 2;
                }
                else if((Day >= 9) && (Day <= 30))
                {
                    Day -= 8;
                    Month += 3;
                }
                Year += 621;
                break;
            case 8:
                if((Day >= 1) && (Day <= 9))
                {
                    Day += 22;
                    Month += 2;
                }
                else if((Day >= 10) && (Day <= 30))
                {
                    Day -= 9;
                    Month += 3;
                }
                Year += 621;
                break;
            case 9:
                if((Day >= 1) && (Day <= 9))
                {
                    Day += 21;
                    Month += 2;
                }
                else if((Day >= 10) && (Day <= 30))
                {
                    Day -= 9;
                    Month += 3;
                }
                Year += 621;
                break;
            case 10:
                if(L)
                {
                    if((Day >= 12) && (Day <= 30))
                    {
                        Day -= 11;
                        Month -= 9;
                        Year += 622;
                    }
                }
                else
                {
                    if((Day >= 1) && (Day <= 10))
                    {
                        Day += 21;
                        Month += 2;
                        Year += 621;
                    }
                    else if((Day >= 11) && (Day <= 30))
                    {
                        Day -= 10;
                        Month -= 9;
                        Year += 622;
                    }
                }
                break;
            case 11:
                if(L)
                {
                    if((Day >= 1) && (Day <= 12))
                    {
                        Day += 19;
                        Month -= 10;
                    }
                    else if((Day >= 13) && (Day <= 30))
                    {
                        Day -= 12;
                        Month -= 9;
                    }
                }
                else
                {
                    if((Day >= 1) && (Day <= 11))
                    {
                        Day += 20;
                        Month -= 10;
                    }
                    else if((Day >= 12) && (Day <= 30))
                    {
                        Day -= 11;
                        Month -= 9;
                    }
                }
                Year += 622;
                break;
            case 12:
                if(L)
                {
                    if((Day >= 1) && (Day <= 10))
                    {
                        Day += 18;
                        Month -= 10;
                    }
                    else if((Day >= 11) && (Day <= 30))
                    {
                        Day -= 10;
                        Month -= 9;
                    }
                }
                else
                {
                    if((Day >= 1) && (Day <= 9))
                    {
                        Day += 19;
                        Month -= 10;
                    }
                    else if((Day >= 10) && (Day <= 29))
                    {
                        Day -= 9;
                        Month -= 9;
                    }
                }
                Year += 622;
                break;
        }

        if(Month < 10)
            datestr = "0" + String.valueOf(Month);
        else
            datestr = String.valueOf(Month);

        datestr = datestr + "/";
        if(Day < 10)
            datestr = datestr + "0" + Day;
        else
            datestr = datestr + Day;

        datestr = datestr + "/";
        datestr = datestr + Year;

        return datestr;
    }

    private String ConvertGregorianDate_Leap(int Year, int Month, int Day)
    {
        String datestr;
        switch(Month)
        {
            case 1:
                if((Day >= 1) && (Day <= 12))
                {
                    Day += 19;
                    Month += 2;
                }
                else if((Day >= 13) && (Day <= 31))
                {
                    Day -= 12;
                    Month += 3;
                }
                Year += 621;
                break;
            case 2:
                if((Day >= 1) && (Day <= 11))
                {
                    Day += 19;
                    Month += 2;
                }
                else if((Day >= 12) && (Day <= 31))
                {
                    Day -= 11;
                    Month += 3;
                }
                Year += 621;
                break;
            case 3:
                if((Day >= 1) && (Day <= 11))
                {
                    Day += 20;
                    Month += 2;
                }
                else if((Day >= 12) && (Day <= 31))
                {
                    Day -= 11;
                    Month += 3;
                }
                Year += 621;
                break;
            case 4:
                if((Day >= 1) && (Day <= 10))
                {
                    Day += 20;
                    Month += 2;
                }
                else if((Day >= 11) && (Day <= 31))
                {
                    Day -= 10;
                    Month += 3;
                }
                Year += 621;
                break;
            case 5:
                if((Day >= 1) && (Day <= 10))
                {
                    Day += 21;
                    Month += 2;
                }
                else if((Day >= 11) && (Day <= 31))
                {
                    Day -= 10;
                    Month += 3;
                }
                Year += 621;
                break;
            case 6:
                if((Day >= 1) && (Day <= 10))
                {
                    Day += 21;
                    Month += 2;
                }
                else if((Day >= 11) && (Day <= 31))
                {
                    Day -= 10;
                    Month += 3;
                }
                Year += 621;
                break;
            case 7:
                if((Day >= 1) && (Day <= 9))
                {
                    Day += 21;
                    Month += 2;
                }
                else if((Day >= 10) && (Day <= 30))
                {
                    Day -= 9;
                    Month += 3;
                }
                Year += 621;
                break;
            case 8:
                if((Day >= 1) && (Day <= 10))
                {
                    Day += 21;
                    Month += 2;
                }
                else if((Day >= 11) && (Day <= 30))
                {
                    Day -= 10;
                    Month += 3;
                }
                Year += 621;
                break;
            case 9:
                if((Day >= 1) && (Day <= 10))
                {
                    Day += 20;
                    Month += 2;
                }
                else if((Day >= 11) && (Day <= 30))
                {
                    Day -= 10;
                    Month += 3;
                }
                Year += 621;
                break;
            case 10:
                if((Day >= 1) && (Day <= 11) && (PersianLeapYear(Year)))
                {
                    Day += 20;
                    Month += 2;
                    Year += 621;
                }
                else if((Day >= 11) && (Day <= 30) && (!PersianLeapYear(Year)))
                {
                    Day -= 10;
                    Month -= 9;
                    Year += 622;
                }
                break;
            case 11:
                if((Day >= 1) && (Day <= 11))
                {
                    Day += 20;
                    Month -= 10;
                }
                else if((Day >= 12) && (Day <= 30))
                {
                    Day -= 11;
                    Month -= 9;
                }
                Year += 622;
                break;
            case 12:
                if((Day >= 1) && (Day <= 10))
                {
                    Day += 19;
                    Month -= 10;
                }
                else if((Day >= 11) && (Day <= 29))
                {
                    Day -= 10;
                    Month -= 9;
                }
                Year += 622;
                break;
        }

        if(Month < 10)
            datestr = "0" + String.valueOf(Month);
        else
            datestr = String.valueOf(Month);

        datestr = datestr + "/";
        if(Day < 10)
            datestr = datestr + "0" + Day;
        else
            datestr = datestr + Day;

        datestr = datestr + "/";
        datestr = datestr + Year;

        return datestr;
    }

}
