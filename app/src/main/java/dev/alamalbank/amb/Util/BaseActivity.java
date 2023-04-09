package dev.alamalbank.amb.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.View;
import android.view.WindowManager;

import dev.alamalbank.amb.PyesApp;
import dev.alamalbank.amb.R;

public abstract class BaseActivity extends Activity implements View.OnClickListener {
    static BaseActivity baseActivity;
    public PyesApp myApp;
    public Typeface myFont;
    public String m_language = "ar";
    public int mSDK_INT;

    public static BaseActivity getInstance() {
        return baseActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            baseActivity = BaseActivity.this;
            myFont = Typeface.createFromAsset(getAssets(), "font/jannat_bold.otf");
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            myApp = ((PyesApp) getApplication());
            m_language = Utils.getColumnValue(this, "select default_lang from home ");

            if (m_language == null) m_language = "ar";

            if (myApp.getGlobal_User_name() == null) {
                myApp.setGlobal_Lang(m_language);
                myApp.setGlobal_User_name(Utils.getColumnValue(this, "select account_name from accounts "));
                myApp.setGlobal_User_Account(Utils.getColumnValue(this, "select account_no from accounts "));
                myApp.setGlobal_Password(Utils.getColumnValue(this, "select pwd from home "));
                myApp.setGlobal_LastLogin(Utils.getColumnValue(this, "select lastlogin from home"));
            } else {
                Utils.setLocal(this, m_language);
            }
            myApp.setGlobal_OperationONOFF(true);
            myApp.setGlobal_ExitFromApplication(false);
            myApp.setLastRunActivity(this);
            mSDK_INT = android.os.Build.VERSION.SDK_INT;
            setContentView(getLayoutResourceId());
            overridePendingTransition(R.anim.slide_in, R.anim.no_animate);
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, "Base Activity \n" + e.toString(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    public void onBackPressed() {
        if (myApp.getGlobal_OperationONOFF()) {
            finish();
            overridePendingTransition(R.anim.no_animate, R.anim.slide_out);
        } else {
            Utils.showConfirmationDialog(this, getResources().getString(R.string.new_operation_error), getResources().getString(R.string.app_name));
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onPause() {
        super.onPause();
        switch (getLayoutResourceId()) {
            case R.layout.openaccount:
            case R.layout.forcelogin:
                break;
            default:
                Utils.onPause(this);
        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onStart() {
        super.onStart();
        switch (getLayoutResourceId()) {
            case R.layout.openaccount:
            case R.layout.forcelogin:
                break;
            default:
                Utils.onStart(this);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onResume() {
        super.onResume();
        switch (getLayoutResourceId()) {
            case R.layout.openaccount:
            case R.layout.forcelogin:
                break;
            default:
                Utils.onResume(this);
        }
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
        /*Runtime.getRuntime().gc();
        System.gc();*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (!Utils.hasPermissions(BaseActivity.this, requestCode, permissions))
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    public void enabledComponent(View view) {
        view.setEnabled(true);
    }

    public abstract int getLayoutResourceId();

    public abstract void initComponent();
}
