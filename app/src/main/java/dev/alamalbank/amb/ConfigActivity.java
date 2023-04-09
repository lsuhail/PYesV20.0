package dev.alamalbank.amb;

import static dev.alamalbank.amb.Util.REFERENCE_APP.Shar_Pre_Lang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import dev.alamalbank.amb.Util.DataUser_SharedPreferences;
import dev.alamalbank.amb.Util.FlutterSingleton;
import dev.alamalbank.amb.Util.LANG;
import dev.alamalbank.amb.Util.REFERENCE_APP;
import dev.alamalbank.amb.Util.Utils;
import dev.alamalbank.amb.adapter.SpinnerAdapter;
import dev.alamalbank.amb.model.Spennar;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.android.FlutterActivityLaunchConfigs;

import com.scwang.wave.MultiWaveHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConfigActivity extends AppCompatActivity {
    private LinearLayout parentLinearLayout;
    private String lang;
    int count=1;
LinearLayout lay_bottom,lay_bottom2;
    Spinner sp_entity;
    SpinnerAdapter adpsp_entity;
    List<Spennar> lstsp_entity=new ArrayList<>();
    Animation rtl1, rtl2, rtl3;
    PyesApp myApp;
    MultiWaveHeader wave_header;
    String m_language = "ar",mDeviceSerial="",mLoginPassword="",mMobile_no="",strDate="";
    String mGlobalMobile_no = "", mGlobalSendMSG = "", mGlobalLogMSG = "";
    EditText ed_login_mobile_no,ed_confirm_login_password,ed_login_password,ed_login_email,ed_login_verification_code,ed_login_client_name;
    Long m_current_NO_OF_TRY_LOGIN = Long.valueOf("1"),
            m_NO_OF_TRY_LOGIN = Long.valueOf("3"),
            m_STOP_TIME = Long.valueOf("30"),
            m_LIMIT_STOP_TRY_LOGIN;
     View rowView4;
    public Typeface myFont;

    Button next,/*b_login_exit,*/b_FirstTimeCheck,Previous,b_login,b_lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        new REFERENCE_APP(getBaseContext());
        new LANG(this);
        myFont = Typeface.createFromAsset(getAssets(), "font/jannat_bold.otf");

        setContentView(R.layout.activity_config);
        TextView loginText= findViewById(R.id.loginText);
        TextView createNewAccount= findViewById(R.id.createNewAccount);
        TextView noAccount= findViewById(R.id.noAccount);
        loginText.setTypeface(myFont);
        createNewAccount.setTypeface(myFont);
        noAccount.setTypeface(myFont);
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Latef","clicked");
                ConfigActivity.this.startActivity(
                        FlutterActivity.withCachedEngine("Engine")
                                .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                                .build(ConfigActivity.this));
            }
        });

        myApp = ((PyesApp) getApplication());
        rtl1 = AnimationUtils.loadAnimation(this, R.anim.slide_rtl);
        rtl1.setDuration(1000);
        rtl2 = AnimationUtils.loadAnimation(this, R.anim.slide_rtl);
        rtl2.setDuration(1200);
        rtl3 = AnimationUtils.loadAnimation(this, R.anim.slide_rtl);
        rtl3.setDuration(1400);
        LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView=inflater.inflate(R.layout.selectlang, null);
        final View rowView3=inflater.inflate(R.layout.infopwd, null);
        final View rowView2=inflater.inflate(R.layout.info, null);
    rowView4=inflater.inflate(R.layout.verificationcode, null);
        parentLinearLayout=(LinearLayout) findViewById(R.id.parent_linear_layout);
        next=findViewById(R.id.next);
        Previous=findViewById(R.id.Previous);
        b_lang=findViewById(R.id.lang);
        b_lang.setVisibility(View.VISIBLE);
       // b_login_exit=findViewById(R.id.b_login_exit);
        b_login=findViewById(R.id.b_FirstTimeCheck);
        lay_bottom=findViewById(R.id.lay_bottom);
        lay_bottom2=findViewById(R.id.lay_bottom2);
        ed_login_mobile_no=rowView2.findViewById(R.id.ed_login_mobile_no);
        ed_login_mobile_no.setAnimation(rtl1);
        ed_login_email=rowView2.findViewById(R.id.ed_login_email);
        ed_login_email.setAnimation(rtl1);
        ed_login_verification_code=rowView2.findViewById(R.id.ed_login_verification_code);
     //   ed_login_verification_code.setAnimation(rtl1);
        ed_confirm_login_password=rowView3.findViewById(R.id.ed_confirm_login_password);
        ed_confirm_login_password.setAnimation(rtl1);
        ed_login_password=rowView3.findViewById(R.id.ed_login_password);
        ed_login_password.setAnimation(rtl1);
        ed_login_client_name=rowView2.findViewById(R.id.ed_login_client_name);
        ed_login_client_name.setAnimation(rtl1);
        ed_login_verification_code=rowView3.findViewById(R.id.ed_login_verification_code);

         mDeviceSerial = Utils.getPlayerId(mMobile_no);
        wave_header = findViewById(R.id.wave_header);
        wave_header.setVelocity(1);
        wave_header.setProgress(1);
        wave_header.isRunning();
        wave_header.setGradientAngle(45);
        wave_header.setWaveHeight(40);
        wave_header.setStartColor(getResources().getColor(R.color.color_gold));
        wave_header.setCloseColor(getResources().getColor(R.color.color_turquoise));
/*        b_login_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.no_animate, R.anim.slide_out);
                System.exit(0);
            }
        });*/
        b_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                b_lang.setVisibility(View.GONE);
                parentLinearLayout.removeAllViews();
                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
                next.setVisibility(View.INVISIBLE);
                Previous.setVisibility(View.INVISIBLE);
                b_login.setVisibility(View.INVISIBLE);
                //b_login_exit.setVisibility(View.INVISIBLE);

            }});
        Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==0)
                {

                    count=count-1;
                }else  if(count==1)
                {
                    parentLinearLayout.removeAllViews();
                    parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
                    next.setVisibility(View.VISIBLE);
                    Previous.setVisibility(View.INVISIBLE);
                    b_login.setVisibility(View.INVISIBLE);
                    count=count-1;
                }
                else  if(count==2)
                {
                    lay_bottom.setVisibility(View.VISIBLE);
                    lay_bottom2.setVisibility(View.GONE);
                    b_lang.setVisibility(View.VISIBLE);
                    parentLinearLayout.removeAllViews();
                    next.setVisibility(View.VISIBLE);
                    Previous.setVisibility(View.INVISIBLE);
                    b_login.setVisibility(View.INVISIBLE);
                    parentLinearLayout.addView(rowView2, parentLinearLayout.getChildCount());
                    count=count-1;

                }
            }
        });
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  verificationcode();
               strDate = Utils.getCurrentDateTime("yyyyMMddHHmmss");
                mLoginPassword = Utils.convertArDigitToEn(ed_login_password.getText().toString());
                mMobile_no = Utils.convertArDigitToEn(ed_login_mobile_no.getText().toString());
                Log.e("latef","mDeviceSerial!=null"+":"+mDeviceSerial);
                if (mDeviceSerial != null) {
                    m_current_NO_OF_TRY_LOGIN = Long.valueOf("1");
                    //TODO:Login Confirm Password
                    String mConfirmPassword = Utils.convertArDigitToEn(ed_confirm_login_password.getText().toString());
                    if (mLoginPassword.equals(mConfirmPassword) && Utils.check_Password_Validation(mLoginPassword)) {
                        if (Utils.isHasMultiSim(ConfigActivity.this)) {
                            simCardSelection(ConfigActivity.this, getResources().getString(R.string.sim_card_error), mMobile_no, mLoginPassword, ed_login_client_name.getText().toString(), mDeviceSerial, strDate);
                        } else {
                            firstTimeCheck(mMobile_no, mLoginPassword, ed_login_client_name.getText().toString(), mDeviceSerial, strDate, null);
                        }
                    } else {
                        ed_login_password.requestFocus();
                        ed_login_password.setError(getResources().getString(R.string.password_incorrect));
                    }
                } else {
                    Utils.showConfirmationDialog(ConfigActivity.this, getResources().getString(R.string.device_serial_error), getResources().getString(R.string.app_name));
                }
            }
        });
      //  b_FirstTimeCheck=findViewById(R.id.b_FirstTimeCheck);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==0)
                {
                    next.setVisibility(View.VISIBLE);
                    Previous.setVisibility(View.VISIBLE);
                    parentLinearLayout.removeAllViews();
                    parentLinearLayout.addView(rowView2, parentLinearLayout.getChildCount());
                    count=count+1;
                }else  if(count==1)
                {
                    lay_bottom.setVisibility(View.GONE);
                    b_lang.setVisibility(View.GONE);
                    lay_bottom2.setVisibility(View.VISIBLE);
                    parentLinearLayout.removeAllViews();
                    parentLinearLayout.addView(rowView3, parentLinearLayout.getChildCount());
                    next.setVisibility(View.INVISIBLE);
                    Previous.setVisibility(View.VISIBLE);
                    b_login.setVisibility(View.VISIBLE);
                    count=count+1;
                }
                else  if(count==2)
                {
                    b_lang.setVisibility(View.GONE);

                    //  count=count+1;
                }


            }
        });
        sp_entity=rowView2.findViewById(R.id.sp_entity);


        sp_entity.setAnimation(rtl1);





      /*  b_FirstTimeCheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                parentLinearLayout.removeAllViews();
                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount()-1);
            }
        });*/
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView2, parentLinearLayout.getChildCount());


        final Button btnlngE=(Button)rowView.findViewById(R.id.btnlngE);
        final Button btnlngA=(Button)rowView.findViewById(R.id.btnlngA);
        lang = REFERENCE_APP.preferences_slid.getString("LANG", null);

        Log.e("latef", Resources.getSystem().getConfiguration().locale.getLanguage());
        if(lang==null){
        if(Resources.getSystem().getConfiguration().locale.getLanguage() =="ar" ){
            Shar_Pre_Lang("ar");
            lang="ar";
            Locale locale = new Locale("ar");
            Locale.setDefault(locale);
            Resources resources = ConfigActivity.this.getResources();
            Configuration config = resources.getConfiguration();
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        }else{
            Shar_Pre_Lang("en");
            lang="en";
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Resources resources = ConfigActivity.this.getResources();
            Configuration config = resources.getConfiguration();
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        }}
         if (lang.equals("ar")) {
            btnlngA.setBackgroundResource(R.drawable.btn_circle);
            lstsp_entity.add(new Spennar("1","بنك الأمل-اليمن"));
            lstsp_entity.add(new Spennar("2","بنك الإبداع-السودان"));


        } else if (lang.equals("en")) {
            btnlngE.setBackgroundResource(R.drawable.btn_circle);
            lstsp_entity.add(new Spennar("1","Al Amal Bank-Yemen"));
            lstsp_entity.add(new Spennar("2","Ebdaa Bank-Sudan"));
        }


        adpsp_entity=new SpinnerAdapter(ConfigActivity.this,lstsp_entity);
        sp_entity.setAdapter(adpsp_entity);


        //   Utils.populateSpinnerAdapter(ConfigActivity.this, sp_entity, entityCategories, R.layout.spinner_entity);
        btnlngE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shar_Pre_Lang("en");
                restartActivity(ConfigActivity.this);
            }
        });
        btnlngA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shar_Pre_Lang("ar");
                restartActivity(ConfigActivity.this);
            }
        });
        FlutterSingleton.getInstance(ConfigActivity.this,"start").runFlutterIntent(ConfigActivity.this,"Engine");

    }
    public static void restartActivity(Activity activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            activity.finish();
            activity.startActivity(activity.getIntent());
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

        /*    sp_language.getSelectedItemPosition();

            m_language = sp_language.getItemAtPosition(sp_language.getSelectedItemPosition()).toString();
            if (m_language.equalsIgnoreCase("English")) {
                m_language = "en";

            } else {
                m_language = "ar";
            }*/

            String sendMSG = m_SERVICE_CODE + ":"
                    + m_COM_ID + ":"
                    + mAccount_no + ":"
                    + m_EMAIL + ":"
                    + mDeviceSerial + ":"
                    + m_Password + ":"
                    + m_VERFIY_CODE;
            Log.e("latef",sendMSG);
            String logMSG = getResources().getString(R.string.msg_check_account) + " " + mAccount_no;

            if (Utils.dropTables(this)) {
                if (!Utils.createTablesIfNotExists(this)) {
                    Utils.showConfirmationDialog(this, getResources().getString(R.string.database_create_error), getResources().getString(R.string.app_name));
                } else {
                    String mAccount_type = "PA";
                    String mAccount_curr = "1";
                    Utils.firstLogin(ConfigActivity.this);
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
    public void verificationcode()
    {
        lay_bottom.setVisibility(View.GONE);
        lay_bottom2.setVisibility(View.GONE);
        parentLinearLayout.removeAllViews();
        parentLinearLayout.addView(rowView4, parentLinearLayout.getChildCount());
    }
    public void sendCMD(String mMobile_no, String sendMSG, String logMSG) {

        if (Utils.isInternetConnected(ConfigActivity.this)) {
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

}