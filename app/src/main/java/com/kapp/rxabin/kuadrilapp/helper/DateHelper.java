package com.kapp.rxabin.kuadrilapp.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xabinrodriguez on 30/10/17.
 */


public class DateHelper {

    // DD/MM/YYYY --> [DD, MM, YYYY}]
    public static String[] Convert(String date){
        return date.split("/");
    }

    public static String getEsMonthFull(String key){

        Map<String,String> map = new HashMap<String,String>();
        map.put("01","Enero"); map.put("02","Febrero"); map.put("03", "Marzo");
        map.put("04","Abril"); map.put("05","Mayo"); map.put("06","Junio");
        map.put("07","Julio"); map.put("08", "Agosto"); map.put("09","Septiembre");
        map.put("10","Octubre"); map.put("11", "Noviembre"); map.put("12", "Diciembre");

        return map.get(key);

    }

    public static String getEuMonthFull(String key){

        Map<String,String> map = new HashMap<String,String>();
        map.put("01","Urtarrila"); map.put("02","Otsaila"); map.put("03", "Marzo");
        map.put("04","Apirila"); map.put("05","Maiatza"); map.put("06","Ekaina");
        map.put("07","Uztaila"); map.put("08", "Abuztua"); map.put("09","Iraila");
        map.put("10","Urria"); map.put("11", "Azaroa"); map.put("12", "Abendua");

        return map.get(key);

    }

    public static boolean isOver(String date){

        String aux = date + " 23:59:59";
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date startDate = new Date();
        try {
            startDate = df.parse(aux);
            String newDateString = df.format(startDate);
            System.out.println(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date now = new Date();
        if (now.equals(startDate)) return false;
        else return startDate.before(now);
    }

    public static String eusDate(String date){
        String[] s = Convert(date);
        return getEuMonthFull(s[1]) + "k " + s[0] + ", " + s[2];

    }

    public static String espDate(String date){
        String[] s = Convert(date);
        return s[0] + " de " + getEsMonthFull(s[1])+ ", " + s[2];
    }

}
