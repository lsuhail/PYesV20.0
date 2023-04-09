package dev.alamalbank.amb.Util;


import android.content.Context;
import android.content.SharedPreferences;

public class REFERENCE_APP {

    public static SharedPreferences preferences_slid;
    public static SharedPreferences.Editor editor_slid;


    public REFERENCE_APP(Context context) {
        preferences_slid = context.getSharedPreferences("SLID", context.MODE_PRIVATE);
        editor_slid = preferences_slid.edit();
    }


    public static void Shar_Pre_Lang(final String s){

        editor_slid.putString("LANG", s);
        editor_slid.commit();
    }


    public static void Shar_Pre_UP(final String u,final String p){

        editor_slid.putString("UN", u);
        editor_slid.putString("PS", p);
        editor_slid.commit();
    }



}
