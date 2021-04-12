package com.heap.commonutils;

import java.util.HashMap;

public class DefaultUtil {

    public static String DEFAULT_COLOR="#33ccff";

    public static int DEFAULT_SHAPE=0;

    public static String DEFAULT_TYPE="NULL";

    public static String DEFAULT_DESCRIPTION="";

    public static double DEFAULT_R=40;

    public static HashMap<String,Boolean> DEFAULT_TYPE_COLORS=new HashMap<String, Boolean>(){
        {
                    put("#0000FF", false);
                    put("#FF00FF", false);
                    put("#4B0082", false);
                    put("#238E23", false);
                    put("#FFFF00", false);
                    put("#FF6EC7", false);
                    put("#A8A8A8", false);
                    put("#FF2400", false);
                    put("#CC3299", false);
                    put("#5C4033", false);
                    put("#FF0000", false);
                    put("#E6E8FA", false);
                    put("#38B0DE", false);
                    put("#D8D8BF", false);
                    put("#32CD32", false);
        }
    };

    public static String useDefaultColor(){
        for(String key:DEFAULT_TYPE_COLORS.keySet()){
            Boolean isUse=DEFAULT_TYPE_COLORS.get(key);
            if(!isUse){
                DEFAULT_TYPE_COLORS.put(key,true);
                return key;
            }
        }
        return DEFAULT_COLOR;
    }

    public static void releaseColor(String color){
        DEFAULT_TYPE_COLORS.put(color,false);
    }

}
