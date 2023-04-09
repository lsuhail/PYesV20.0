package dev.alamalbank.amb;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import dev.alamalbank.amb.Util.FingerPrintHandler;
import dev.alamalbank.amb.Util.Utils;


public class ForceLogin extends Activity implements View.OnClickListener {
    static ForceLogin forcelogin;
    final Long m_STOP_TIME = Long.valueOf("30");
    Long m_current_NO_OF_TRY_LOGIN = Long.valueOf("1");
    Long m_NO_OF_TRY_LOGIN = Long.valueOf("3");
    Long m_LIMIT_STOP_TRY_LOGIN;
    TextView tv_forcelogin_title, tv_forcelogin_password, tv_fingerprint_note;
    EditText ed_forcelogin_password;
    Button b_forcelogin_login, b_forcelogin_logout;
    ImageView im_flogo;
    String m_language, m_password = "";
    PyesApp myApp;

    public static ForceLogin getInstance() {
        return forcelogin;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            forcelogin = ForceLogin.this;
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            myApp = (PyesApp) getApplication();
            m_language = Utils.getColumnValue(this, "select default_lang from home ");

            if (m_language == null) m_language = "ar";

            Utils.setLocal(this, m_language);

            setContentView(R.layout.forcelogin);

            myApp.setLastRunActivity(this);

            m_password = myApp.getGlobal_Password();

            String m_Temp_NO_OF_TRY_LOGIN = Utils.getColumnValue(this, "select NO_OF_TRY_LOGIN from login");
            if (m_Temp_NO_OF_TRY_LOGIN != null) {
                m_NO_OF_TRY_LOGIN = Long.valueOf(m_Temp_NO_OF_TRY_LOGIN);
            }
            initComponent();
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, "ForceLogin \n" + e.toString() + "\n NO of try LOGIN=" + m_NO_OF_TRY_LOGIN, getResources().getString(R.string.app_name));
        }
    }

    @Override
    protected void onPause() {
        PyesApp myApp = (PyesApp) getApplication();
        myApp.setLastPauseActivity(this);
        super.onPause();
    }

    public void initComponent() {
        Typeface myFont = Typeface.createFromAsset(getAssets(), "font/jannat_bold.otf");

        Utils.setBankBackgroundLogo(this,myApp.getGlobal_Com_Id());

        tv_forcelogin_title = (TextView) Utils.populateView(ForceLogin.this, "TextView", R.id.tv_forcelogin_title, 0);
        tv_forcelogin_password = (TextView) Utils.populateView(ForceLogin.this, "TextView", R.id.tv_forcelogin_password, 0);
        tv_fingerprint_note = (TextView) findViewById(R.id.tv_fingerprint_note);
        tv_fingerprint_note.setTypeface(myFont);


        ed_forcelogin_password = (EditText) Utils.populateView(ForceLogin.this, "EditText", R.id.ed_forcelogin_password, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        b_forcelogin_login = (Button) Utils.populateView(ForceLogin.this, "Button", R.id.b_forcelogin_login, 0);
        b_forcelogin_logout = (Button) Utils.populateView(ForceLogin.this, "Button", R.id.b_forcelogin_logout, 0);
        checkFingerprint();
    }

    @Override
    public void onBackPressed() {
        myApp.setGlobal_ExitFromApplication(true);
        overridePendingTransition(R.anim.no_animate, R.anim.slide_out);
        finish();
    }

    private void checkFingerprint() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) { // check 1: Your android must be greater or equal marshmallow api 6.
            tv_fingerprint_note.setText(getResources().getString(R.string.FINGERPRINT_Scan_Finger));
            FingerprintManager fingerprintManager;
            FingerprintManager.CryptoObject cryptoObject;
            KeyguardManager keyguardManager;
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            tv_fingerprint_note.setText(getResources().getString(R.string.FINGERPRINT_Scan_Finger));
            if (!fingerprintManager.isHardwareDetected()) {// check 2: Is Your phone has fingerprint scanner or not .
                tv_fingerprint_note.setText(getResources().getString(R.string.FINGERPRINT_Scanner_not_found));
            } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) { // check 3: You have permission to use fingerprint in app.
                tv_fingerprint_note.setText(getResources().getString(R.string.FINGERPRINT_permission));
            } else if (!keyguardManager.isKeyguardSecure()) { // check 4: Lock screen at least 1 type of lock.
                tv_fingerprint_note.setText(getResources().getString(R.string.FINGERPRINT_Add_finger));
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {// check 5: at least one fingerprint register in device.
                tv_fingerprint_note.setText(getResources().getString(R.string.FINGERPRINT_least_one_rolled));
            } else {
                tv_fingerprint_note.setText(getResources().getString(R.string.FINGERPRINT_Scan_Finger));
                FingerPrintHandler fingerprintHandler = new FingerPrintHandler(this);
                fingerprintHandler.startAuth(fingerprintManager, null);
            }
        } else {
            tv_fingerprint_note.setText("");
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        String m_force_login_password = Utils.convertArDigitToEn(ed_forcelogin_password.getText().toString().trim());
        myApp.setGlobal_Confirmation(false);
        switch (v.getId()) {
            case R.id.b_forcelogin_login:
                if (m_current_NO_OF_TRY_LOGIN <= m_NO_OF_TRY_LOGIN) {
                    if ( (m_force_login_password.equals(Utils.decoding(m_password)))) {
                        myApp.stopActivityTransitionTimer();
                        myApp.setGlobal_ExitFromApplication(false);
                        myApp.setLastPauseActivity(ForceLogin.this);
                        myApp.setGlobal_ForceLogin_Flag(false);
                        finish();
                    } else {
                        m_current_NO_OF_TRY_LOGIN++;
                        ed_forcelogin_password.requestFocus();
                        ed_forcelogin_password.setError(getResources().getString(R.string.login_userpwd_error));
                        //Utils.showToast(this, getResources().getString(R.string.login_userpwd_error));
                    }
                } else {
                    String strDate = Utils.getCurrentDateTime("yyyyMMddHHmmss");
                    m_LIMIT_STOP_TRY_LOGIN = Long.parseLong(strDate) + m_STOP_TIME;
                    Utils.runSQL(this, "update login set LIMIT_STOP_TRY_LOGIN='" + m_LIMIT_STOP_TRY_LOGIN + "'");
                    m_current_NO_OF_TRY_LOGIN = Long.valueOf("1");
                    myApp.setGlobal_ExitFromApplication(true);
                    finish();
                }
                break;
            case R.id.b_forcelogin_logout:
                myApp.setGlobal_ExitFromApplication(true);
                finish();
                overridePendingTransition(R.anim.no_animate, R.anim.slide_out);
                break;
        }
    }
}
