package com.imageupload.example.Components.boardServiceMethod;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class createTime {
    
    private Timestamp regDate;
    private Timestamp nowDate;

    public createTime(Timestamp regDate) throws ParseException {
        this.regDate = regDate;
        nowDate = new Timestamp(new Date().getTime());
    }

    public String getTimeDiff() throws ParseException{
        
        Map<String, String> displayDate = new LinkedHashMap<>();

        long diffInMillies = Math.abs(nowDate.getTime() - regDate.getTime());
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(diffInMillies));

        String years = (cal.get(Calendar.YEAR) - 1970) + "";
        String months = cal.get(Calendar.MONTH) + "";
        String days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + "";
        String hours = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS) + "";
        String minutes = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS) + "";
        String seconds = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS) + "";
        
        displayDate.put("년 전", years);
        displayDate.put("달 전", months);
        displayDate.put("일 전", days);
        displayDate.put("시간 전", hours);
        displayDate.put("분 전", minutes);
        displayDate.put("초 전", seconds);

        for(String value : displayDate.keySet()){
            if(!displayDate.get(value).equals("0")){
                return displayDate.get(value) + value;
            }
        }

        return "0초 전";
    }

}
