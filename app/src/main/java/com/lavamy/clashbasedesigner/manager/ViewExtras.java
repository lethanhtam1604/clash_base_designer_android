package com.lavamy.clashbasedesigner.manager;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;


public class ViewExtras {

    public static void setBackgroundDrawable(View view, Drawable drawable) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    public static void changeColorView(Context context, View view, int colorID, int drawableID) {
        Drawable drawable = getDrawable(context, drawableID);
        drawable.setColorFilter(ContextCompat.getColor(context, colorID), PorterDuff.Mode.SRC_ATOP);
        ViewExtras.setBackgroundDrawable(view, drawable);
    }

    public static Drawable getDrawable(Context context, int drawableID) {
        return ContextCompat.getDrawable(context, drawableID);
    }

    public static int getColor(Context context, int colorID) {
        return ContextCompat.getColor(context, colorID);
    }
}
