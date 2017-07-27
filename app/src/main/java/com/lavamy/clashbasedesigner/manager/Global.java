package com.lavamy.clashbasedesigner.manager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Global {

    public static float screenRatio;

    public static int screenWidth;
    public static int screenHeight;

    public static int currentPage = 0;

    public static String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }
}
