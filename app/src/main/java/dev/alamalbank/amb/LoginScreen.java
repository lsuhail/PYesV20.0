package dev.alamalbank.amb;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import dev.alamalbank.amb.Util.DataUser_SharedPreferences;
import dev.alamalbank.amb.Util.FingerPrintHandler;
import dev.alamalbank.amb.Util.FlutterSingleton;
import dev.alamalbank.amb.Util.RestClient;
import dev.alamalbank.amb.Util.SQLiteMain;
import dev.alamalbank.amb.Util.Utils;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.android.FlutterActivityLaunchConfigs;

import com.scwang.wave.MultiWaveHeader;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class LoginScreen extends Activity implements View.OnClickListener {
    DataUser_SharedPreferences shar;
    Long m_current_NO_OF_TRY_LOGIN = Long.valueOf("1"),
            m_NO_OF_TRY_LOGIN = Long.valueOf("3"),
            m_STOP_TIME = Long.valueOf("30"),
            m_LIMIT_STOP_TRY_LOGIN;
    MultiWaveHeader wave_header;
    TextView tv_login_client_name, tv_login_mobile_no, tv_login_email, tv_entity, tv_language, tv_login_password,
            tv_fingerprint_note, tv_Forgot_Password,tv_to_communicate, tv_app_info, tv_confirm_login_password, tv_login_verification_code;
    EditText ed_login_client_name, ed_login_mobile_no, ed_login_email, ed_login_password, ed_confirm_login_password, ed_login_verification_code;
    Button b_login, b_login_exit, b_FirstTimeCheck, b_reactivate;
    ImageView im_logo, im_blogo;
    Spinner sp_entity, sp_language;
    Cursor login, home;
    String mGlobalMobile_no = "", mGlobalSendMSG = "", mGlobalLogMSG = "";
    String m_language = "ar";
    boolean firstTime = true;
    PyesApp myApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        FlutterSingleton.getInstance(LoginScreen.this,"start").runFlutterIntent(LoginScreen.this,"Engine");

        super.onCreate(savedInstanceState);
        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            myApp = ((PyesApp) getApplication());

            try {
                m_language = (myApp.getGlobal_Lang() == null ? "ar" : myApp.getGlobal_Lang());
            } catch (Exception e) {
                Log.e(Utils.APP_TAG, "Login onCreate :\n" + e.toString());
            }
            Utils.setLocal(this, m_language);
            setContentView(R.layout.loginscreen);
            myApp.setGlobal_ExitFromApplication(false);
            myApp.setLastRunActivity(LoginScreen.this);
            myApp.setGlobal_Confirmation(false);
            initComponent();
            checkFirstLogin();
            Utils.setStoragePathMain(this);
            shar=new DataUser_SharedPreferences(this);
            Log.e(Utils.APP_TAG,Utils.getColumnValue(this,"select MESSAGE from LASTSENDCMD where _ID=(select max( _ID ) from LASTSENDCMD)"));
           // Log.e(Utils.APP_TAG,String.valueOf(Utils.isConditionCorrect("((X>N) && (X<M))", "1000", "1", "9")));
           /* ed_login_client_name.setText("رشاد");
            ed_login_mobile_no.setText("777584443");
            ed_login_email.setText("rashadabedi@gmail.com");
            ed_login_password.setText("1234");
            ed_confirm_login_password.setText("1234");
            ed_login_verification_code.setText("942059");

            //Log.e(Utils.APP_TAG,"VCode "+Utils.getColumnValue(this,"select verification_code from home "));

            ArrayList<NameValuePair> postParameters = new ArrayList<>();
            postParameters.clear();

            postParameters.add(new BasicNameValuePair("p_function",
                    "TR_PKG.WEB_PARSING('" + "777584443" + "','" + "" + "','" + "19.0" + "','" + "1" + "','" + "1" + "','" + "100:1:777584443:rashadabedi@gmail.com:null:MTIzNA==:942059" + "')"));

            //postParameters.add(new BasicNameValuePair("P_COM_ID","1"));

            RestClient.Execute(RestClient.RequestMethod.GET,"http://ftpamb.dyndns.org:8080/ords/pyesws/pyes/run/",null,postParameters);
            */
            LoginScreen.this.startActivity(
                    FlutterActivity.withCachedEngine("Engine")
                            .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                            .build(LoginScreen.this));
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());

        }
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.no_animate, R.anim.slide_out);
        finish();
        System.exit(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        PyesApp myApp = (PyesApp) getApplication();
        try {
            if (myApp.getGlobal_ExitFromApplication()) {
                finish();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            if ((requestCode == Utils.SMS_PERMISSION_CODE)) {
                if (!Utils.hasPermissions(LoginScreen.this, requestCode, permissions)) {
                    Utils.showToast(this, getResources().getString(R.string.sms_permission_required));
                    Utils.callAppSettings(this);
                } else {
                    sendCMD(mGlobalMobile_no, mGlobalSendMSG, mGlobalLogMSG);
                }
            } else if (!Utils.hasPermissions(LoginScreen.this, requestCode, permissions)) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, e.toString(), getResources().getString(R.string.app_name));
        }
    }

    public void sendCMD(String mMobile_no, String sendMSG, String logMSG) {

        if (Utils.isInternetConnected(LoginScreen.this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Utils.hasPermissions(this, Utils.SMS_PERMISSION_CODE, Utils.SMS_PERMISSIONS)) {
                    Utils.requestPermission(this, Utils.SMS_PERMISSION_CODE);
                } else {
                    Utils.confirmSendRequest(this, getResources().getString(R.string.request_send_over_internet), getResources().getString(R.string.app_name), "Internet", mMobile_no, sendMSG, logMSG, sendMSG, true, "0", Utils.SEND_CMD);
                }
            } else {
                Utils.confirmSendRequest(this, logMSG + "\n" + getResources().getString(R.string.request_send_over_internet), getResources().getString(R.string.app_name), "Internet", mMobile_no, sendMSG, logMSG, sendMSG, true, "0", Utils.SEND_CMD);
            }
        }
    }

    public void initComponent() {
        String string_setText;
        Typeface myFont = Typeface.createFromAsset(getAssets(), "font/jannat_bold.otf");
        myApp.setGlobal_Lang(m_language);

        Utils.setBankBackgroundLogo(this,myApp.getGlobal_Com_Id());

        String m_CS_phone = Utils.getColumnValue(this, "select cs_phone from home");
        String m_WhatsApp = Utils.getColumnValue(this, "select whats_app from home");
        Animation rtl1, rtl2, rtl3;
        rtl1 = AnimationUtils.loadAnimation(this, R.anim.slide_rtl);
        rtl1.setDuration(1000);
        rtl2 = AnimationUtils.loadAnimation(this, R.anim.slide_rtl);
        rtl2.setDuration(1200);
        rtl3 = AnimationUtils.loadAnimation(this, R.anim.slide_rtl);
        rtl3.setDuration(1400);
        wave_header = findViewById(R.id.wave_header);
        wave_header.setVelocity(1);
        wave_header.setProgress(1);
        wave_header.isRunning();
        wave_header.setGradientAngle(45);
        wave_header.setWaveHeight(40);
        wave_header.setStartColor(getResources().getColor(R.color.color_gold));
        wave_header.setCloseColor(getResources().getColor(R.color.color_turquoise));

        try {
            tv_login_client_name = (TextView) Utils.populateView(LoginScreen.this, "TextView", R.id.tv_login_client_name, 0);
            tv_login_client_name.setAnimation(rtl1);
            ed_login_client_name = (EditText) Utils.populateView(LoginScreen.this, "EditText", R.id.ed_login_client_name, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            ed_login_client_name.setAnimation(rtl1);
            tv_login_mobile_no = (TextView) Utils.populateView(LoginScreen.this, "TextView", R.id.tv_login_mobile_no, 0);
            tv_login_mobile_no.setAnimation(rtl1);
            ed_login_mobile_no = (EditText) Utils.populateView(LoginScreen.this, "EditText", R.id.ed_login_mobile_no, InputType.TYPE_CLASS_PHONE);
            ed_login_mobile_no.setAnimation(rtl1);

            tv_login_email = (TextView) Utils.populateView(LoginScreen.this, "TextView", R.id.tv_login_email, 0);
            tv_login_email.setAnimation(rtl1);
            ed_login_email = (EditText) Utils.populateView(LoginScreen.this, "EditText", R.id.ed_login_email, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
            ed_login_email.setAnimation(rtl1);

            tv_login_password = (TextView) Utils.populateView(LoginScreen.this, "TextView", R.id.tv_login_password, 0);
            tv_login_password.setAnimation(rtl1);
            ed_login_password = (EditText) Utils.populateView(LoginScreen.this, "EditText", R.id.ed_login_password, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ed_login_password.setAnimation(rtl1);
            ed_login_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Utils.MAX_PASSWORD_LENGTH)});
            ed_login_password.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    textChanged("NEW", s);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
            });

            tv_confirm_login_password = (TextView) Utils.populateView(LoginScreen.this, "TextView", R.id.tv_confirm_login_password, 0);
            tv_confirm_login_password.setAnimation(rtl1);
            ed_confirm_login_password = (EditText) Utils.populateView(LoginScreen.this, "EditText", R.id.ed_confirm_login_password, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ed_confirm_login_password.setAnimation(rtl1);
            ed_confirm_login_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Utils.MAX_PASSWORD_LENGTH)});
            ed_confirm_login_password.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    textChanged("Confirm", s);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
            });

            tv_login_verification_code = (TextView) Utils.populateView(LoginScreen.this, "TextView", R.id.tv_login_verification_code, 0);
            tv_login_verification_code.setAnimation(rtl1);
            ed_login_verification_code = (EditText) Utils.populateView(LoginScreen.this, "EditText", R.id.ed_login_verification_code, InputType.TYPE_CLASS_NUMBER);
            ed_login_verification_code.setAnimation(rtl1);

            tv_entity = (TextView) Utils.populateView(LoginScreen.this, "TextView", R.id.tv_entity, 0);
            tv_entity.setAnimation(rtl1);
            sp_entity = (Spinner) Utils.populateView(LoginScreen.this, "Spinner", R.id.sp_entity, 0);
            sp_entity.setAnimation(rtl1);
            ArrayList<String> entityCategories = new ArrayList<>();
            if (m_language.equalsIgnoreCase("ar")) {
                entityCategories.add("بنك الأمل-اليمن");
                entityCategories.add("بنك الإبداع-السودان");
            }else{
                entityCategories.add("Al Amal Bank-Yemen");
                entityCategories.add("Ebdaa Bank-Sudan");
            }
            Utils.populateSpinnerAdapter(LoginScreen.this, sp_entity, entityCategories, R.layout.spinner_entity);

            tv_language = (TextView) Utils.populateView(LoginScreen.this, "TextView", R.id.tv_language, 0);
            tv_language.setAnimation(rtl1);
            sp_language = (Spinner) Utils.populateView(LoginScreen.this, "Spinner", R.id.sp_language, 0);
            sp_language.setAnimation(rtl1);
            ArrayList<String> langCategories = new ArrayList<>();
            langCategories.add("عربي");
            langCategories.add("English");
            Utils.populateSpinnerAdapter(LoginScreen.this, sp_language, langCategories, R.layout.spinner_item);
            if (m_language.equalsIgnoreCase("ar")) {
                sp_language.setSelection(0);
            } else {
                sp_language.setSelection(1);
            }
            tv_to_communicate = (TextView) Utils.populateView(LoginScreen.this, "TextView", R.id.tv_to_communicate, 0);

            if (m_CS_phone != null) {
                string_setText = getResources().getString(R.string.to_communicate)
                        + getResources().getString(R.string.free_phone_after_update) + " : " + m_CS_phone + " "
                        + getResources().getString(R.string.whats_app_after_update) + " : " + m_WhatsApp;

            } else {
                string_setText = getResources().getString(R.string.to_communicate)
                        + getResources().getString(R.string.free_phone) + " "
                        + getResources().getString(R.string.whats_app);
            }
            //  tv_to_communicate.setText(string_setText);

            tv_app_info = (TextView) Utils.populateView(LoginScreen.this, "TextView", R.id.tv_app_info, 0);
            string_setText = "API Version: " + Build.VERSION.SDK_INT + "\n" + "App Version:" + BuildConfig.VERSION_NAME;
            tv_app_info.setText(string_setText);

            tv_fingerprint_note = (TextView) findViewById(R.id.tv_fingerprint_note);
            tv_fingerprint_note.setTypeface(myFont);

            tv_Forgot_Password = (TextView) findViewById(R.id.tv_Forgot_Password);
            tv_Forgot_Password.setTypeface(myFont);
            tv_Forgot_Password.setOnClickListener(this);

            im_logo = (ImageView) Utils.populateView(LoginScreen.this, "ImageView", R.id.im_logo, 0);

            b_login = (Button) Utils.populateView(LoginScreen.this, "Button", R.id.b_login, 0);
            b_login_exit = (Button) Utils.populateView(LoginScreen.this, "Button", R.id.b_login_exit, 0);
            b_FirstTimeCheck = (Button) Utils.populateView(LoginScreen.this, "Button", R.id.b_FirstTimeCheck, 0);
            b_reactivate = (Button) Utils.populateView(LoginScreen.this, "Button", R.id.b_reactivate, 0);

            b_login.setAnimation(rtl1);
            b_reactivate.setAnimation(rtl2);
            b_login_exit.setAnimation(rtl3);

        } catch (Exception e) {

            Utils.showConfirmationDialog(LoginScreen.this, e.toString(), getResources().getString(R.string.app_name));
        }

    }

    private void checkFingerprint() {
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) { // check 1: Your android must be greater or equal marshmallow api 6.
                tv_fingerprint_note.setText(getResources().getString(R.string.FINGERPRINT_Scan_Finger));
                // For device greater than marsh
                FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
                KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
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
                    FingerPrintHandler fingerprintHandler = new FingerPrintHandler(LoginScreen.this);
                    fingerprintHandler.startAuth(fingerprintManager, null);
                }
            } else {
                tv_fingerprint_note.setText("");
            }
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, " Finger error " + e.toString());
        }
    }

    private void textChanged(String edType, Editable editText) {
        //TODO: login remove comment
        if (firstTime) {
            String newS = editText.toString();
            newS = Utils.convertArDigitToEn(newS);

            if ((edType.equalsIgnoreCase("NEW")) && (!Utils.check_Password_Validation(newS))) {
                // ed_login_password.requestFocus();
                // ed_login_password.setError(getResources().getString(R.string.password_incorrect));
            } else if ((edType.equalsIgnoreCase("Confirm")) && (!Utils.check_Password_Validation(newS))) {
                // ed_confirm_login_password.requestFocus();
                //ed_confirm_login_password.setError(getResources().getString(R.string.password_incorrect));
            }
        }


    }

    private void checkFirstLogin() {
        try {
            final SQLiteMain entry = new SQLiteMain(LoginScreen.this);
            try {entry.open();}catch (Exception e){}
            home = entry.execute_Query("select firstLogIn from home");
            home.moveToFirst();
            if (home.getCount() == 0) {
                disable_enable("FirstLogin");
            } else {
                if (home.getString(0).equalsIgnoreCase("2")) {
                    disable_enable("NotFirstTime");
                    checkFingerprint();
                }else {
                    disable_enable("FirstLogin");
                }
            }

            login = entry.execute_Query("select NO_OF_TRY_LOGIN,STOP_TIME,LIMIT_STOP_TRY_LOGIN from login");
            login.moveToFirst();

            if (login.getCount() == 0) {
                entry.execute_SQL("insert into login( NO_OF_TRY_LOGIN ,STOP_TIME,LIMIT_STOP_TRY_LOGIN) values('" + m_NO_OF_TRY_LOGIN + "','" + m_STOP_TIME + "','')");
            } else {
                if (!login.getString(0).isEmpty()) {
                    m_NO_OF_TRY_LOGIN = Long.valueOf(login.getString(0).trim());
                } else {
                    entry.execute_SQL("update login set NO_OF_TRY_LOGIN='" + m_NO_OF_TRY_LOGIN + "'");
                }
                if (!login.getString(1).isEmpty()) {
                    m_STOP_TIME = Long.valueOf(login.getString(1));
                } else {
                    entry.execute_SQL("update login set STOP_TIME='" + m_STOP_TIME + "'");
                }
                if (!login.getString(2).isEmpty()) {
                    m_LIMIT_STOP_TRY_LOGIN = Long.valueOf(login.getString(2));
                    String strDate = Utils.getCurrentDateTime("yyyyMMddHHmmss");
                    if (Long.parseLong(strDate) <= m_LIMIT_STOP_TRY_LOGIN) {
                        Utils.showToast(this, getResources().getString(R.string.remain_login) + " ( " + (m_LIMIT_STOP_TRY_LOGIN - Long.parseLong(strDate)) + " " + getResources().getString(R.string.ms) + ")");
                        finish();
                    }
                }
            }
            entry.close();
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, e.toString(), getResources().getString(R.string.app_name));
        }
    }


    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        String mMobile_no = Utils.convertArDigitToEn(ed_login_mobile_no.getText().toString());
        String mDeviceSerial = Utils.getPlayerId(mMobile_no);
        String regDeviceSerial;
        boolean isPWSMatching;
        String mLoginPassword = Utils.convertArDigitToEn(ed_login_password.getText().toString());
        String strDate = Utils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
        myApp.setGlobal_Confirmation(false);

        switch (v.getId()) {
            case R.id.b_login:
                String mAccountNo = Utils.getColumnValue(this, "select account_no from accounts where is_default='1'");
                String mPassword = Utils.getColumnValue(this, "select pwd from home");

                isPWSMatching = mLoginPassword.equals(Utils.decoding(mPassword));

                regDeviceSerial = Utils.getColumnValue(this, "select device_serial from home");

                if (m_current_NO_OF_TRY_LOGIN <= m_NO_OF_TRY_LOGIN) {
                    if (isPWSMatching && regDeviceSerial.equals(mDeviceSerial)) {
                        m_language = sp_language.getItemAtPosition(sp_language.getSelectedItemPosition()).toString();
                        if (m_language.equalsIgnoreCase("English")) {
                            m_language = "en";
                        } else {
                            m_language = "ar";
                        }
                        Utils.runSQL(this, "update home set default_lang='" + m_language + "'");
                        myApp.setGlobal_Lang(m_language);
                        myApp.setGlobal_User_name(Utils.getColumnValue(this, "select client_name from home"));
                        if (m_language.equalsIgnoreCase("ar")) {
                            myApp.setGlobal_Com_name(Utils.getColumnValue(this, "select COMPANY_NAME_AR from HOME "));
                        }else {
                            myApp.setGlobal_Com_name(Utils.getColumnValue(this, "select COMPANY_NAME_EN from HOME "));
                        }

                        myApp.setGlobal_User_Account(mAccountNo);
                        myApp.setGlobal_Password(mPassword);

                        myApp.setGlobal_LastLogin(Utils.getColumnValue(this, "select lastlogin from home"));

                        boolean flag_isAppActive = Utils.isAppActive(LoginScreen.this);
                        boolean flag_isAPPCodeCorrectP1 = Utils.isAPPCodeCorrect(LoginScreen.this, mAccountNo, "PART1");
                        boolean flag_isAPPCodeCorrectP2 = Utils.isAPPCodeCorrect(LoginScreen.this, mAccountNo, "PART2");

                        if (flag_isAppActive && flag_isAPPCodeCorrectP1 && flag_isAPPCodeCorrectP2) {
                            ed_login_password.setText("");
                            Utils.runSQL(this, "update home set lastlogin='" + strDate + "'");
                            Utils.runIntent(this, "Main_Activity", m_language);
                        } else if (!flag_isAppActive) {
                            m_current_NO_OF_TRY_LOGIN++;
                            Utils.showConfirmationDialog(this, getResources().getString(R.string.system_stop), getResources().getString(R.string.app_name));
                        } else if ((!flag_isAPPCodeCorrectP1) || (!flag_isAPPCodeCorrectP2)) {
                            m_current_NO_OF_TRY_LOGIN++;
                            Utils.showConfirmationDialog(this, getResources().getString(R.string.registration_error), getResources().getString(R.string.app_name));
                        }
                    } else if (!isPWSMatching) {
                        m_current_NO_OF_TRY_LOGIN++;
                        ed_login_password.requestFocus();
                        ed_login_password.setError(getResources().getString(R.string.login_userpwd_error));
                    } else {
                        m_current_NO_OF_TRY_LOGIN++;
                        ed_login_password.requestFocus();
                        ed_login_password.setError(getResources().getString(R.string.login_license_error));
                    }
                } else {
                    String strDate2 = Utils.getCurrentDateTime("yyyyMMddHHmmss");
                    m_LIMIT_STOP_TRY_LOGIN = Long.parseLong(strDate2) + m_STOP_TIME;
                    Utils.runSQL(this, "update login set LIMIT_STOP_TRY_LOGIN='" + m_LIMIT_STOP_TRY_LOGIN + "'");
                    m_current_NO_OF_TRY_LOGIN = Long.valueOf("1");
                    finish();
                }
                break;
            case R.id.b_FirstTimeCheck:
                if (mDeviceSerial != null) {
                    m_current_NO_OF_TRY_LOGIN = Long.valueOf("1");
                    //TODO:Login Confirm Password
                    String mConfirmPassword = Utils.convertArDigitToEn(ed_confirm_login_password.getText().toString());
                    if (mLoginPassword.equals(mConfirmPassword) && Utils.check_Password_Validation(mLoginPassword)) {
                        if (Utils.isHasMultiSim(this)) {
                            simCardSelection(this, getResources().getString(R.string.sim_card_error), mMobile_no, mLoginPassword, ed_login_client_name.getText().toString(), mDeviceSerial, strDate);
                        } else {
                            firstTimeCheck(mMobile_no, mLoginPassword, ed_login_client_name.getText().toString(), mDeviceSerial, strDate, null);
                        }
                    } else {
                        ed_login_password.requestFocus();
                        ed_login_password.setError(getResources().getString(R.string.password_incorrect));
                    }
                } else {
                    Utils.showConfirmationDialog(this, getResources().getString(R.string.device_serial_error), getResources().getString(R.string.app_name));
                }
                break;
            case R.id.b_reactivate:
                confirmReActivate(this, getResources().getString(R.string.reactivate_message), getResources().getString(R.string.app_name));
                break;
            case R.id.tv_Forgot_Password:
                Utils.runService(this, myApp.getGlobal_User_Account(), "Forgot_PWD", getResources().getString(R.string.msg_change_password), "303", false, m_language);
                break;
            case R.id.b_login_exit:
                finish();
                overridePendingTransition(R.anim.no_animate, R.anim.slide_out);
                System.exit(0);
                break;
        }

    }

    public void simCardSelection(final Context context, String title, final String mMobile_no, final String mPassWord, final String mClientName, final String mDeviceSerial, final String mDateTime) {
        AlertDialog.Builder simCardDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.simcardselection, (ViewGroup) findViewById(R.id.simcardselection_layout));
        //final RadioGroup rg_sim = dialogView.findViewById(R.id.rg_sim);
        final RadioButton rgb_sim1 = dialogView.findViewById(R.id.rgb_sim1);
        final RadioButton rgb_sim2 = dialogView.findViewById(R.id.rgb_sim2);
        simCardDialog.setView(dialogView);
        simCardDialog.setTitle(title);
        simCardDialog.setPositiveButton(context.getResources().getString(R.string.okey), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                String mSimCard = null;
                if (rgb_sim1.isChecked()) {
                    mSimCard = "0";
                } else if (rgb_sim2.isChecked()) {
                    mSimCard = "1";
                }
                firstTimeCheck(mMobile_no, mPassWord, mClientName, mDeviceSerial, mDateTime, mSimCard);
            }
        });
        simCardDialog.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                dialogInterface.dismiss();
            }
        });
        simCardDialog.show();
    }

    private void firstTimeCheck(String mAccount_no, String mPassword, String mClientName,String mDeviceSerial, String mDateTime, String mSimCard) {
        boolean flag1, flag2;
        String m_SERVICE_CODE = "100";
        String m_COM_ID = String.valueOf(sp_entity.getSelectedItemPosition() + 1);
        String m_EMAIL = ed_login_email.getText().toString();
        String m_VERFIY_CODE = ed_login_verification_code.getText().toString();

        if ((m_COM_ID.length() != 0)&&(mClientName.length() != 0)&&(mAccount_no.length() != 0) && (m_EMAIL.length() != 0) && (mPassword.length() != 0)& (m_VERFIY_CODE.length() != 0)) {
            // Encoding Password firstTimeCheck
            String m_Password = Utils.encoding(mPassword);
            myApp.setGlobal_TempVariable(m_Password);
            myApp.setGlobal_Password(m_Password);
            sp_language.getSelectedItemPosition();
            m_language = sp_language.getItemAtPosition(sp_language.getSelectedItemPosition()).toString();
            if (m_language.equalsIgnoreCase("English")) {
                m_language = "en";
            } else {
                m_language = "ar";
            }
            String sendMSG = m_SERVICE_CODE + ":"
                    + m_COM_ID + ":"
                    + mAccount_no + ":"
                    + m_EMAIL + ":"
                    + mDeviceSerial + ":"
                    + m_Password + ":"
                    + m_VERFIY_CODE;

            String logMSG = getResources().getString(R.string.msg_check_account) + " " + mAccount_no;

            if (Utils.dropTables(this)) {
                if (!Utils.createTablesIfNotExists(this)) {
                    Utils.showConfirmationDialog(this, getResources().getString(R.string.database_create_error), getResources().getString(R.string.app_name));
                } else {
                    String mAccount_type = "PA";
                    String mAccount_curr = "1";
                    Utils.firstLogin(LoginScreen.this);
                    Utils.runSQL(this, "delete from accounts");
                    flag2 = Utils.runSQL(this, "insert into accounts(datetime ,account_no,account_type,account_curr,account_name,is_default,mobile_no) " +
                            "values('" + mDateTime + "','" + mAccount_no + "','" + mAccount_type + "','" + mAccount_curr + "','" + mClientName + "',1,'" + mAccount_no + "')");

                    myApp.setGlobal_Lang(m_language);
                    myApp.setGlobal_User_name(mClientName);
                    myApp.setGlobal_Com_Id(String.valueOf(sp_entity.getSelectedItemPosition() + 1));
                    myApp.setGlobal_Com_name(sp_entity.getItemAtPosition(sp_entity.getSelectedItemPosition()).toString());
                    myApp.setGlobal_User_Account(mAccount_no);
                    myApp.setGlobal_LastLogin(Utils.getColumnValue(this, "select lastlogin from home"));
                    flag1 = Utils.runSQL(this, "update home set " +
                            " company_id='" + myApp.getGlobal_Com_Id() +
                            "', client_name='" + mClientName +
                            "', verification_code ='" + m_VERFIY_CODE +
                            "', device_serial ='" + mDeviceSerial +
                            "', player_id ='" + mDeviceSerial +
                            "', sim ='" + mSimCard +
                            "', lastlogin ='" + mDateTime +
                            "', default_lang ='" + m_language + "'");
                    if (flag1 && flag2) {
                        mGlobalMobile_no = Utils.convertArDigitToEn(mAccount_no);
                        mGlobalSendMSG = Utils.convertArDigitToEn(sendMSG);
                        mGlobalLogMSG = Utils.convertArDigitToEn(logMSG);
                        sendCMD(mGlobalMobile_no, mGlobalSendMSG, mGlobalLogMSG);
                    } else {
                        Utils.runSQL(this, "delete from home");
                        Utils.runSQL(this, "delete from accounts");
                        Utils.showConfirmationDialog(this, getResources().getString(R.string.first_login_error), getResources().getString(R.string.app_name));
                    }
                }
            } else {
                Utils.showConfirmationDialog(this, getResources().getString(R.string.first_login_error), getResources().getString(R.string.app_name));
            }
        } else {
            Utils.runSQL(this, "delete from home");
            Utils.runSQL(this, "delete from accounts");
            Utils.showConfirmationDialog(this, getResources().getString(R.string.missing_info), getResources().getString(R.string.app_name));
        }
    }

    private void confirmReActivate(final Context context, String msg, String title) {
        final Dialog myDialog = new Dialog(context);
        final Typeface myFont = Typeface.createFromAsset(context.getAssets(), "font/jannat_bold.otf");
        final PyesApp myApp = ((PyesApp) context.getApplicationContext());
        myDialog.setCancelable(false);
        myDialog.setContentView(R.layout.custompopup);
        TextView tvTitle = myDialog.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(myFont);
        tvTitle.setText(title);
        TextView tvMessage = myDialog.findViewById(R.id.tvMessage);
        tvMessage.setTypeface(myFont);
        tvMessage.setText(msg);
        Button btnOk = myDialog.findViewById(R.id.btnOk);
        btnOk.setTypeface(myFont);
        Button btnCancel = myDialog.findViewById(R.id.btnCancel);
        btnCancel.setTypeface(myFont);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  disable_enable("ReActivate");
                shar.setLOGIN("");
                Intent mainactivity = new Intent(LoginScreen.this,ConfigActivity.class);
                startActivity(mainactivity);
                finish();
                if (Utils.dropTables(context)) {
                    if (!Utils.createTablesIfNotExists(context)) {
                        Utils.showConfirmationDialog(context, getResources().getString(R.string.database_create_error), getResources().getString(R.string.app_name));
                    }
                }
                myDialog.cancel();
                myDialog.dismiss();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myApp.setGlobal_OperationONOFF(true);
                myDialog.cancel();
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void disable_enable(String mType) {

        if (mType.equalsIgnoreCase("FirstLogin") || mType.equalsIgnoreCase("ReActivate")) {

            tv_entity.setVisibility(View.VISIBLE);
            sp_entity.setVisibility(View.VISIBLE);

            tv_login_client_name.setVisibility(View.VISIBLE);
            ed_login_client_name.setVisibility(View.VISIBLE);

            tv_login_mobile_no.setVisibility(View.VISIBLE);
            ed_login_mobile_no.setVisibility(View.VISIBLE);

            tv_login_email.setVisibility(View.VISIBLE);
            ed_login_email.setVisibility(View.VISIBLE);

            tv_confirm_login_password.setVisibility(View.VISIBLE);
            ed_confirm_login_password.setVisibility(View.VISIBLE);
            tv_login_verification_code.setVisibility(View.VISIBLE);
            ed_login_verification_code.setVisibility(View.VISIBLE);
            b_FirstTimeCheck.setVisibility(View.VISIBLE);

            tv_entity.setEnabled(true);
            sp_entity.setEnabled(true);
            tv_login_client_name.setEnabled(true);
            ed_login_client_name.setEnabled(true);
            ed_login_client_name.requestFocus();
            tv_login_mobile_no.setEnabled(true);
            ed_login_mobile_no.setEnabled(true);
            tv_login_email.setEnabled(true);
            ed_login_email.setEnabled(true);

            tv_confirm_login_password.setEnabled(true);
            ed_confirm_login_password.setEnabled(true);
            tv_login_verification_code.setEnabled(true);
            ed_login_verification_code.setEnabled(true);
            b_FirstTimeCheck.setEnabled(true);
            b_login.setEnabled(false);
            b_reactivate.setEnabled(false);
            b_login.setVisibility(View.GONE);
            b_reactivate.setVisibility(View.GONE);
            tv_fingerprint_note.setVisibility(View.GONE);
            tv_Forgot_Password.setVisibility(View.GONE);

            firstTime = true;
        } else {
            tv_entity.setEnabled(false);
            sp_entity.setEnabled(false);
            tv_login_client_name.setEnabled(false);
            ed_login_client_name.setEnabled(false);
            tv_login_mobile_no.setEnabled(false);
            ed_login_mobile_no.setEnabled(false);
            tv_login_email.setEnabled(false);
            ed_login_email.setEnabled(false);

            b_FirstTimeCheck.setEnabled(false);

            tv_entity.setVisibility(View.GONE);
            sp_entity.setVisibility(View.GONE);
            tv_login_client_name.setVisibility(View.GONE);
            ed_login_client_name.setVisibility(View.GONE);
            tv_login_mobile_no.setVisibility(View.GONE);
            ed_login_mobile_no.setVisibility(View.GONE);

            tv_login_email.setVisibility(View.GONE);
            ed_login_email.setVisibility(View.GONE);

            tv_confirm_login_password.setVisibility(View.GONE);
            ed_confirm_login_password.setVisibility(View.GONE);
            tv_login_verification_code.setVisibility(View.GONE);
            ed_login_verification_code.setVisibility(View.GONE);
            b_FirstTimeCheck.setVisibility(View.GONE);
            b_FirstTimeCheck.setEnabled(true);
            b_login.setVisibility(View.VISIBLE);
            b_reactivate.setVisibility(View.VISIBLE);
            b_login.setEnabled(true);
            b_reactivate.setEnabled(true);
            ed_login_password.requestFocus();
            tv_Forgot_Password.setVisibility(View.VISIBLE);
            tv_Forgot_Password.setEnabled(true);

            firstTime = false;
        }
    }
}
