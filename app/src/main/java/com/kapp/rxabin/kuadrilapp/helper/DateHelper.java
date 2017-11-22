package com.kapp.rxabin.kuadrilapp.helper;

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

    public static String getEsMonth(String key){

        Map<String,String> map = new HashMap<String,String>();
        map.put("01","Ene"); map.put("02","Feb"); map.put("03", "Mar");
        map.put("04","Abr"); map.put("05","May"); map.put("06","Jun");
        map.put("07","Jul"); map.put("08", "Ago"); map.put("09","Sep");
        map.put("10","Oct"); map.put("11", "Nov"); map.put("12", "Dic");

        return map.get(key);

    }

    public static String getEsMonthFull(String key){

        Map<String,String> map = new HashMap<String,String>();
        map.put("01","Enero"); map.put("02","Febrero"); map.put("03", "Marzo");
        map.put("04","Abril"); map.put("05","Mayo"); map.put("06","Junio");
        map.put("07","Julio"); map.put("08", "Agosto"); map.put("09","Septiembre");
        map.put("10","Octubre"); map.put("11", "Noviembre"); map.put("12", "Diciembre");

        return map.get(key);

    }

    public static String getEuMonth(String key){

        Map<String,String> map = new HashMap<String,String>();
        map.put("01","Urt"); map.put("02","Ots"); map.put("03", "Mar");
        map.put("04","Api"); map.put("05","Mai"); map.put("06","Eka");
        map.put("07","Uzt"); map.put("08", "Abu"); map.put("09","Ira");
        map.put("10","Urr"); map.put("11", "Aza"); map.put("12", "Abe");

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

    public static String eusDate(String date){
        String[] s = Convert(date);
        return getEuMonthFull(s[1]) + "k " + s[0] + ", " + s[2];

    }

    public static String espDate(String date){
        String[] s = Convert(date);
        return s[0] + " de " + getEsMonthFull(s[1])+ ", " + s[2];
    }

    //Returns DD-MM-YYYY
    /*public static String toEsFormat(String date){
        String[] data = Convert(date);
        return data[2]+"-"+data[1]+"-"+data[0];
    }*/

    /*public static String toEsFormatNews(String date){
        String[] data = date.split("-");
        return data[2]+"-"+data[1]+"-"+data[0];
    }*/

    //Returns YYYY-MM-DD
    /*public static String toEuFormatNotifs(String date){
        String[] data = date.split("-");
        return data[2]+"-"+data[1]+"-"+data[0];
    }*/

    //Returns "monthName DD"
    /*public static String newsDateEu(String date){
        String[] s = Convert(date);
        return getEuMonthFull(s[1]) + " " + s[2];
    }*/

    //Returns "DD monthName"
    /*public static String newsDateEs(String date){
        String[] s = Convert(date);
        return s[2] + " " + getEsMonthFull(s[1]);
    }*/

}
