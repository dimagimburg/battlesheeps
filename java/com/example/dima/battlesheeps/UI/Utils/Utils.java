package com.example.dima.battlesheeps.UI.Utils;

import java.util.HashMap;

public class Utils {

    public static HashMap<String, Integer> getCoordinateByPosition(int position, int BoardSize){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("x",(position % BoardSize));
        map.put("y",(position / BoardSize));
        return map;
    }

}
