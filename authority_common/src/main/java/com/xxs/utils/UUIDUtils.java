package com.xxs.utils;

import java.util.UUID;

public class UUIDUtils {

    public static String getPwd(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String getID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
