package com.kapp.rxabin.kuadrilapp.helper;

import com.kapp.rxabin.kuadrilapp.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xabinrodriguez on 7/11/17.
 */

public class EventHelper {

    public static String getType(String type){

        if (type.equalsIgnoreCase("café") || type.equalsIgnoreCase("kafetxoa")){
            return "cafe";
        }
        else if (type.equalsIgnoreCase("comida") || type.equalsIgnoreCase("jatordua")){
            return "food";
        }
        else if (type.equalsIgnoreCase("paseo") || type.equalsIgnoreCase("paseoa")){
            return "walk";
        }
        else if (type.equalsIgnoreCase("deportes") || type.equalsIgnoreCase("kirolak")){
            return "sports";
        }
        else if (type.equalsIgnoreCase("videojuegos") || type.equalsIgnoreCase("bideojokuak")){
            return "videogames";
        }
        else if (type.equalsIgnoreCase("cañas") || type.equalsIgnoreCase("kañak")){
            return "drink";
        }
        else if (type.equalsIgnoreCase("música") || type.equalsIgnoreCase("musika")){
            return "music";
        }
        else if(type.equalsIgnoreCase("trabajo") || type.equalsIgnoreCase("lana")){
            return "work";
        }
        else if (type.equalsIgnoreCase("estudio") || type.equalsIgnoreCase("ikasketa")){
            return "study";
        }
        else return "unknown";

    }

    public static int getIcon(String type){
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("food", R.drawable.ico_food); map.put("walk",R.drawable.ico_walk); map.put("sports",R.drawable.ico_sport); map.put("videogames",R.drawable.ico_games);
        map.put("cafe",R.drawable.ico_cafe); map.put("drink",R.drawable.ico_beer);  map.put("music",R.drawable.ico_music); map.put("work",R.drawable.ico_work);
        map.put("study",R.drawable.ico_study); map.put("unknown",R.drawable.ico_unkown);

        return map.get(type);

    }


    public static String getName(String type, String lang){
        Map<String,String> mapEu = new HashMap<String,String>();
        mapEu.put("food", "Jatordua"); mapEu.put("walk","Paseoa"); mapEu.put("sports","Kirolak"); mapEu.put("videogames","Bideojokuak");
        mapEu.put("cafe","Kafetxoa"); mapEu.put("drink","Kañak");  mapEu.put("music","Musika"); mapEu.put("work","Lana");
        mapEu.put("study","Ikasketa"); mapEu.put("unknown","Ezezaguna");

        Map<String,String> mapEs = new HashMap<String,String>();
        mapEs.put("food", "Comida"); mapEs.put("walk","Paseo"); mapEs.put("sports","Deportes"); mapEs.put("videogames","Videojuegos");
        mapEs.put("cafe","Café"); mapEs.put("drink","Cañas");  mapEs.put("music","Música"); mapEs.put("work","Trabajo");
        mapEs.put("study","Estudio"); mapEs.put("unknown","Desconocido");

        if (lang.equals("es")) return mapEs.get(type);
        else return mapEu.get(type);
    }

}
