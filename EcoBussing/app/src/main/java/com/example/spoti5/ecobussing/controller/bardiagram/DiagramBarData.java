package com.example.spoti5.ecobussing.controller.bardiagram;



import android.support.v4.app.Fragment;

import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.SaveHandler;
import com.example.spoti5.ecobussing.model.profile.Company;
import com.example.spoti5.ecobussing.model.profile.User;
import com.example.spoti5.ecobussing.model.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by emilaxelsson on 26/10/15.
 */
public class DiagramBarData  {

    private BarData bardata;
    private double highestValue;
    private Fragment fragment;




    public DiagramBarData(Fragment fragment){
        this.fragment = fragment;
    }

    /**
     * Used to get the diagrams for the last seven days.
     *
     * @return a BarData-object with all the values for the days
     */
    public BarData getBarDataLastSevenDays(boolean isCompany, boolean moneyPoints, IProfile profile){

        //Date date = calendar.getTime();
        Calendar calendar = Calendar.getInstance();


        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<BarDataSet> dataSets = new ArrayList<>();


        //IUser currentUser = SaveHandler.getCurrentUser();

        ArrayList<BarEntry> barEntryList = new ArrayList<>();

        calendar.add(Calendar.DAY_OF_MONTH, + 1);

        highestValue = 0;

        for(int i = 0; i < 7; i++){

            calendar.add(Calendar.DAY_OF_MONTH, -1);

            int year = calendar.get(Calendar.YEAR);
            int month = 1 + calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            xVals.add(0, getWeekDayName(dayOfWeek));
            double value = 0;

            try{
                if(isCompany){

                    if(moneyPoints){
                        value = ((Company)profile).getPointsSavedDate(year, month, day);
                    }else{
                        value = ((Company)profile).getCO2SavedDate(year, month, day);
                    }

                }else{

                    if(moneyPoints){
                        value = ((User)profile).getMoneySavedDate(year, month, day);
                    }else{
                        value = profile.getCO2SavedDate(year, month, day);
                    }

                }

            }catch (Exception e){
                value = 0;
            }


            if(value > highestValue) highestValue = value;

            System.out.println(year + ", " + month + ", " + day);
            System.out.println(getWeekDayName(dayOfWeek) + ": " + value);


            barEntryList.add(new BarEntry((float) value, 6 - i));


        }
        BarDataSet bds = new BarDataSet(barEntryList, "");
        bds.setColor(fragment.getResources().getColor(R.color.secondary));
        //bds.setColor(getResources().getColor(R.color.secondary));
        dataSets.add(bds);

        return new BarData(xVals, dataSets);

    }


    /**
     * Used to get the diagrams for the last seven weeks.
     *
     * @return a BarData-object with all the values for the last seven weeks
     */
    public BarData getBarDataLastSevenWeeks(boolean isCompany, boolean moneyPoints, IProfile profile){
        Calendar calendar = Calendar.getInstance(Locale.GERMAN);


        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        IUser currentUser = SaveHandler.getCurrentUser();
        ArrayList<BarEntry> barEntryList = new ArrayList<>();

        highestValue = 0;

        int startWeeknumber = calendar.get(Calendar.WEEK_OF_YEAR);
        System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
        double value = 0;
        //boolean firstWeek = true;
        int place = 0;
        int i = startWeeknumber;
        while(i > startWeeknumber - 7 ){

            int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
            int year = calendar.get(Calendar.YEAR);
            int month = 1 + calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            //System.out.println(weekNumber);
            //System.out.println(month);
            //System.out.println(day);
            if(weekNumber < i){
                xVals.add(0, Integer.toString(calendar.get(Calendar.WEEK_OF_YEAR) + 1));
                barEntryList.add(new BarEntry((float) value, 6 - place));
                System.out.println(value);
                value = 0;
                place++;
                i--;
            }


            try{
                if(isCompany){
                    if(moneyPoints){
                        value += ((Company)profile).getPointsSavedDate(year, month, day);
                        System.out.println("valuepoints= " + value);
                    }else{
                        value += ((Company)profile).getCO2SavedDate(year, month, day);
                        System.out.println("valueco2= " + value);
                    }
                }else{
                    if(moneyPoints){
                        value += ((User)profile).getMoneySavedDate(year, month, day);
                    }else{
                        value += profile.getCO2SavedDate(year, month, day);
                    }
                }

            }catch (Exception e){
                value += 0;
            }

            if(value > highestValue) highestValue = value;


            calendar.add(Calendar.DAY_OF_MONTH, -1);

        }

        BarDataSet bds = new BarDataSet(barEntryList, "");
        bds.setColor(fragment.getResources().getColor(R.color.secondary));
        dataSets.add(bds);


        return new BarData(xVals, dataSets);

    }

    /**
     * Used to get the diagrams for the last seven months.
     *
     * @return a BarData-object with all the values for the last seven months
     */
    public BarData getBarDataLastSevenMonths(boolean isCompany, boolean moneyPoints, IProfile profile){
        Calendar calendar = Calendar.getInstance();


        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        IUser currentUser = SaveHandler.getCurrentUser();
        ArrayList<BarEntry> barEntryList = new ArrayList<>();

        highestValue = 0;

        calendar.add(Calendar.MONTH, + 1);

        for(int i = 0; i < 7; i++){

            calendar.add(Calendar.MONTH, -1);
            int year = calendar.get(Calendar.YEAR);
            int month = 1 + calendar.get(Calendar.MONTH);

            double value = 0;

            try{
                if(isCompany){
                    if(moneyPoints){
                        value = ((Company)profile).getPointsSavedMonth(year, month);
                        System.out.println("month value " + i+ ": " + value);
                    }else{
                        value = ((Company)profile).getCO2SavedMonth(year, month);
                    }
                }else{
                    if(moneyPoints){
                        value = ((User)profile).getMoneySavedMonth(year, month);
                    }else{
                        value = profile.getCO2SavedMonth(year, month);
                    }

                }

            }catch (Exception e){
                value = 0;
            }

            xVals.add(0, getMonthName(month));

            if(value > highestValue) highestValue = value;

            barEntryList.add(new BarEntry((float) value, 6 - i));

        }

        BarDataSet bds = new BarDataSet(barEntryList, "");
        bds.setColor(fragment.getResources().getColor(R.color.secondary));
        dataSets.add(bds);

        //setMaxYAxis();

        return new BarData(xVals, dataSets);

    }


    /**
     * Gets the month name first letters
     * @param month, the month you want to get the name on
     * @return a string with the first two letters of the month-name
     */
    public String getMonthName(int month){

        String monthName = "";

        switch (month-1){
            case Calendar.JANUARY:
                monthName = "Ja";
                break;
            case Calendar.FEBRUARY:
                monthName = "Fe";
                break;
            case Calendar.MARCH:
                monthName = "Ma";
                break;
            case Calendar.APRIL:
                monthName = "Ap";
                break;
            case Calendar.MAY:
                monthName = "Ma";
                break;
            case Calendar.JUNE:
                monthName = "Ju";
                break;
            case Calendar.JULY:
                monthName = "Ju";
                break;
            case Calendar.AUGUST:
                monthName = "Au";
                break;
            case Calendar.SEPTEMBER:
                monthName = "Se";
                break;
            case Calendar.OCTOBER:
                monthName = "Oc";
                break;
            case Calendar.NOVEMBER:
                monthName = "No";
                break;
            case Calendar.DECEMBER:
                monthName = "De";
                break;

        }

        return monthName;
    }

    /**
     * Used to get the two first days of the weekday.
     *
     * @param weekDay, the day in week
     * @return two letters of the weekday-name
     */
    public String getWeekDayName(int weekDay){
        String dayName = "";

        switch(weekDay) {
            case Calendar.MONDAY:
                dayName = "Må";
                break;
            case Calendar.TUESDAY:
                dayName = "Ti";
                break;
            case Calendar.WEDNESDAY:
                dayName = "On";
                break;
            case Calendar.THURSDAY:
                dayName = "To";
                break;
            case Calendar.FRIDAY:
                dayName = "Fr";
                break;
            case Calendar.SATURDAY:
                dayName = "Lö";
                break;
            case Calendar.SUNDAY:
                dayName = "Sö";
                break;
        }
        return dayName;
    }

    public double getHighestValue() {
        return highestValue;
    }
}
