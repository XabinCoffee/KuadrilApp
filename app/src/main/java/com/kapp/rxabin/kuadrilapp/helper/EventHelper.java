package com.kapp.rxabin.kuadrilapp.helper;

import com.kapp.rxabin.kuadrilapp.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xabinrodriguez on 7/11/17.
 */

public class EventHelper {

    public static String getType(String type){
        if (type.equalsIgnoreCase("comida") || type.equalsIgnoreCase("jatordua")){
            return "restaurant";
        }
        else if (type.equalsIgnoreCase("partido") || type.equalsIgnoreCase("partidua")){
            return "match";
        }
        else if (type.equalsIgnoreCase("videojuegos") || type.equalsIgnoreCase("bideojokoak")){
            return "gaming";
        }
        else if (type.equalsIgnoreCase("deporte") || type.equalsIgnoreCase("kirolak")){
            return "sports";
        }
        else if (type.equalsIgnoreCase("bar") || type.equalsIgnoreCase("kafetxoa")){
            return "coffee";
        }
        else if(type.equalsIgnoreCase("traguito") || type.equalsIgnoreCase("tragotxoa")){
            return "drink";
        }
        else if (type.equalsIgnoreCase("muchos tragos") || type.equalsIgnoreCase("trago asko")){
            return "heavy drink";
        }
        else return "unknown";

    }

    public static int getIcon(String type){
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("restaurant", R.drawable.ico_restaurant); map.put("match",R.drawable.ico_football); map.put("gaming",R.drawable.ico_gaming); map.put("sports",R.drawable.ico_sports);
        map.put("coffee",R.drawable.ico_coffee); map.put("drink",R.drawable.ico_beer); map.put("heavy drink",R.drawable.ico_bigbeer); map.put("unknown",R.drawable.ico_unknown);

        return map.get(type);

    }


}
