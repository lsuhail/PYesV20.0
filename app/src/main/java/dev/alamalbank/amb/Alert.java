package dev.alamalbank.amb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dev.alamalbank.amb.Util.*;

import java.util.Locale;

public class Alert extends Activity implements OnClickListener {

    String m_last_receive_msg;
    PyesApp myApp;
    private String m_language;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            myApp = (PyesApp) getApplication();
            m_language = Utils.getColumnValue(this, "select default_lang from home ");
            if (m_language == null) m_language = "ar";
            Locale locale = new Locale(m_language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
            setContentView(R.layout.alert);
            m_last_receive_msg = myApp.getGlobal_last_receive_msg();
            myApp.setGlobal_OperationONOFF(true);
            initcomponent();
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, "Alert \n" + e.toString(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onPause() {
        super.onPause();
        PyesApp myApp = (PyesApp) getApplication();
        myApp.setLastPauseActivity(this);
        myApp.startActivityTransitionTimer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        PyesApp myApp = (PyesApp) getApplication();
        try {
            if (myApp.getGlobal_ExitFromApplication()) {
                finish();
            } else if (myApp.getWasActivityInBackground()) {
                if (myApp.getLastPauseActivity().equals(this)) {
                    if ((!this.getClass().getSimpleName().equalsIgnoreCase("FORCELOGIN"))
                            && (!myApp.getGlobal_ForceLogin_Flag())) {
                        myApp.setGlobal_ForceLogin_Flag(true);
                        Utils.runIntent(this, "FORCELOGIN", m_language);
                    }
                }
                myApp.stopActivityTransitionTimer();

            }
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        PyesApp myApp = (PyesApp) getApplication();
        try {
            if (myApp.getGlobal_ExitFromApplication()) {
                finish();
            }
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * تجهيز العناصر والربط البرمجي لها
     */
    private void initcomponent() {
        Typeface myFont = Typeface.createFromAsset(getAssets(), "font/jannat_bold.otf");
        TextView tv_alert_title = findViewById(R.id.tv_alert_title);
        tv_alert_title.setText(getResources().getString(R.string.app_name));
        tv_alert_title.setTypeface(myFont);
        EditText ed_alert_message = findViewById(R.id.ed_alert_message);
        ed_alert_message.setText(m_last_receive_msg);
        ed_alert_message.setTypeface(myFont);
        ed_alert_message.setEnabled(false);
        Button b_alert_ok = findViewById(R.id.b_alert_ok);
        b_alert_ok.setTypeface(myFont);
        b_alert_ok.setOnClickListener(this);


    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {

        if (v.getId() == R.id.b_alert_ok) {
            finish();
        }
    }
}