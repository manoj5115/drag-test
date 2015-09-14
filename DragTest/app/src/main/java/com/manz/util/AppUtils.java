package com.manz.util;

import com.manz.drag.R;

import java.util.HashMap;
import java.util.InputMismatchException;

/**
 * Created by manoj on 14/9/15.
 */
public class AppUtils {

    static HashMap<String, Integer> resourceMap;

    public static Integer getLayout(String activity){
        initResourceMap();
        Integer resId = resourceMap.get(activity);
        return resId;
    }

    private static void initResourceMap() {
        if(resourceMap == null){
            resourceMap = new HashMap<String, Integer>(){
                {
                    put("Activity1", R.layout.activity1);
                    put("Activity2", R.layout.activity2);
                }
            };
        }
    }
}
