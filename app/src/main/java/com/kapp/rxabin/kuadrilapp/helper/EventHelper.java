package com.kapp.rxabin.kuadrilapp.helper;

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


}
