package dev.alamalbank.amb.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class DataUser_SharedPreferences {
    SharedPreferences shared;
    SharedPreferences.Editor edit;
    public DataUser_SharedPreferences(Context context){
        shared = context.getSharedPreferences("data_pys", Context.MODE_PRIVATE);
        edit = shared.edit();
    }



    boolean Application_guide;




    public void clear_data_user() {
        edit.clear();
        edit.commit();
    }



    public boolean isApplication_guide() {
        return shared.getBoolean("application_guide", false);
    }

    public void setApplication_guide(boolean application_guide) {
        edit.putBoolean("application_guide", application_guide);
        edit.commit();
    }


    public String getLOGIN() {
        return shared.getString("LOGIN", "");
    }
    public void setLOGIN(String SUB_ACC_Name) {
        edit.putString("LOGIN", SUB_ACC_Name);
        edit.commit();
    }
    public void setlogo(String SUB_ACC_Name) {
        edit.putString("logo", SUB_ACC_Name);
        edit.commit();
    }
    public String getlogo() {
        return shared.getString("logo", "");
    }
    public String getLang() {
        return shared.getString("logo", "");
    }
    public String getPrenter() {
        return shared.getString("Prenter", "");
    }
    public void setPrenter(String SUB_ACC_Name) {
        edit.putString("Prenter", SUB_ACC_Name);
        edit.commit();
    }



}