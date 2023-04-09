package dev.alamalbank.amb.Util;

import static dev.alamalbank.amb.Util.REFERENCE_APP.Shar_Pre_Lang;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import dev.alamalbank.amb.ForceLogin;
import dev.alamalbank.amb.LoginScreen;
import dev.alamalbank.amb.PyesApp;
import dev.alamalbank.amb.R;
import dev.alamalbank.amb.Start;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.android.FlutterActivityLaunchConfigs;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class FlutterSingleton {
    private static volatile SQLiteMain entry;
    private static volatile FlutterSingleton INSTANCE = null;

    private FlutterSingleton() {}

    private static final String CHANNEL = "dev.alamalbank.amb.channel/door";

    public static volatile FlutterEngine flutterEngine = null;

    private static  String data  = null;

    public static FlutterSingleton getInstance(Context context, String route) {
        // Check if the instance is already created
        if( flutterEngine == null) {
            // synchronize the block to ensure only one thread can execute at a time
            synchronized (FlutterSingleton.class) {
                // check again if the instance is already created
                if (INSTANCE == null) {
                    // create the singleton instance
                    INSTANCE = new FlutterSingleton();
                }
                if (flutterEngine == null) {

                    // Instantiate a FlutterEngine.
                    flutterEngine = new FlutterEngine(context);
                    // Configure an initial route.
                    flutterEngine.getNavigationChannel().setInitialRoute(route);
                    // Start executing Dart code to pre-warm the FlutterEngine.
                    flutterEngine.getDartExecutor().executeDartEntrypoint(
                            DartExecutor.DartEntrypoint.createDefault()
                    );

                    FlutterEngineCache
                            .getInstance()
                            .put("Engine", flutterEngine);

                   entry = new SQLiteMain(context);
                    setupMethodChannel(context);

                }
            }
        }
        return INSTANCE;
    }
    public  void runFlutterIntent(Context lunchContext,String engine) {

        try {
            lunchContext.startActivity(
                    FlutterActivity.withCachedEngine(engine)
                            .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                            .build(lunchContext)
            );
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showConfirmationDialog(lunchContext, "Utils.runFlutterIntent\n" + lunchContext.getResources().getString(R.string.no_services) + ":" , lunchContext.getResources().getString(R.string.app_name));
        }

    }

    public void setData(String s){
        data = s;
    }
    static Long m_current_NO_OF_TRY_LOGIN = Long.valueOf("1");
    static Long m_NO_OF_TRY_LOGIN = Long.valueOf("3");
    static Long m_STOP_TIME = Long.valueOf("30");
    static Long m_LIMIT_STOP_TRY_LOGIN;
    static Cursor login, home, homeData;

    private static HashMap checkFirstLogin(Context contest) {
        HashMap returnData= new HashMap<>();
            try {
                entry.fopen();

            }catch (Exception e){

            }
        try {



            home = entry.execute_Query("select firstLogIn from home");
            home.moveToFirst();
            Log.e("latef",home.toString());
            if (home.getCount() == 0) {

                returnData.put("IsLogin",false);
            }else {
                homeData = entry.execute_Query("select * from home");
                homeData.moveToFirst();
               for(int i = 0; i< homeData.getColumnCount();i++){
                   if(homeData.getType(i)==Cursor.FIELD_TYPE_STRING){
                   returnData.put( homeData.getColumnName(i),homeData.getString(i));
               }else if(homeData.getType(i)==Cursor.FIELD_TYPE_INTEGER){
                       returnData.put( homeData.getColumnName(i),homeData.getInt(i));

                   }else if(homeData.getType(i)==Cursor.FIELD_TYPE_FLOAT){
                       returnData.put( homeData.getColumnName(i),homeData.getFloat(i));

                   }
               }
                String mAccountNo = Utils.getColumnValue(contest, "select account_no from accounts where is_default='1'");

                returnData.put("PhoneNumber",mAccountNo);
                returnData.put("HomeData",true);
                returnData.put("IsLogin",true);

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
                        Utils.showToast(contest, contest.getResources().getString(R.string.remain_login) + " ( " + (m_LIMIT_STOP_TRY_LOGIN - Long.parseLong(strDate)) + " " + contest.getResources().getString(R.string.ms) + ")");

                    }
                }
            }
            entry.close();
        } catch (Exception e) {
            Log.e("latefE",e.toString());
           // Utils.showConfirmationDialog(contest, e.toString(), contest.getResources().getString(R.string.app_name));
        }
        return returnData;
    }
    static String strDate = Utils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
    static String mDeviceSerial = Utils.getPlayerId("");
    private static HashMap ForceLogin(Context contest, String pass) {
        PyesApp myApp = (PyesApp) contest.getApplicationContext();

        HashMap  returnData = new HashMap<>();

        try {
            String mAccountNo = Utils.getColumnValue(contest, "select account_no from accounts where is_default='1'");
            String mPassword = Utils.getColumnValue(contest, "select pwd from home");

            returnData.put("isPWSMatching", pass.equals(Utils.decoding(mPassword)));

            returnData.put("regDeviceSerial", Utils.getColumnValue(contest, "select device_serial from home"));

            if (m_current_NO_OF_TRY_LOGIN <= m_NO_OF_TRY_LOGIN) {
                if ((boolean) returnData.get("isPWSMatching") && returnData.get("regDeviceSerial").equals(mDeviceSerial)) {
                    String m_language = REFERENCE_APP.preferences_slid.getString("LANG", "ar");
                    myApp.setGlobal_Lang(m_language);
                    myApp.setGlobal_User_name(Utils.getColumnValue(contest, "select client_name from home"));
                    if (m_language.equalsIgnoreCase("ar")) {
                        myApp.setGlobal_Com_name(Utils.getColumnValue(contest, "select COMPANY_NAME_AR from HOME "));
                    } else {
                        myApp.setGlobal_Com_name(Utils.getColumnValue(contest, "select COMPANY_NAME_EN from HOME "));
                    }

                    myApp.setGlobal_User_Account(mAccountNo);
                    myApp.setGlobal_Password(mPassword);

                    myApp.setGlobal_LastLogin(Utils.getColumnValue(contest, "select lastlogin from home"));

                    boolean flag_isAppActive = Utils.isAppActive(contest);
                    boolean flag_isAPPCodeCorrectP1 = Utils.isAPPCodeCorrect(contest, mAccountNo, "PART1");
                    boolean flag_isAPPCodeCorrectP2 = Utils.isAPPCodeCorrect(contest, mAccountNo, "PART2");

                    if (flag_isAppActive && flag_isAPPCodeCorrectP1 && flag_isAPPCodeCorrectP2) {
                        Utils.runSQL(contest, "update home set lastlogin='" + strDate + "'");
                      //  Utils.runIntent(contest, "Main_Activity", m_language);
                    } else if (!flag_isAppActive) {
                        m_current_NO_OF_TRY_LOGIN++;
                        Utils.showConfirmationDialog(contest, contest.getResources().getString(R.string.system_stop), contest.getResources().getString(R.string.app_name));
                    } else if ((!flag_isAPPCodeCorrectP1) || (!flag_isAPPCodeCorrectP2)) {
                        m_current_NO_OF_TRY_LOGIN++;
                        Utils.showConfirmationDialog(contest, contest.getResources().getString(R.string.registration_error), contest.getResources().getString(R.string.app_name));
                    }
                } else if (!(boolean) returnData.get("isPWSMatching")) {
                    m_current_NO_OF_TRY_LOGIN++;
                    returnData.put("isPWSMatching", contest.getResources().getString(R.string.login_userpwd_error));
                } else {
                    m_current_NO_OF_TRY_LOGIN++;
                    returnData.put("isPWSMatching", contest.getResources().getString(R.string.login_license_error));
                }
            } else {
                String strDate2 = Utils.getCurrentDateTime("yyyyMMddHHmmss");
                m_LIMIT_STOP_TRY_LOGIN = Long.parseLong(strDate2) + m_STOP_TIME;
                Utils.runSQL(contest, "update login set LIMIT_STOP_TRY_LOGIN='" + m_LIMIT_STOP_TRY_LOGIN + "'");
                m_current_NO_OF_TRY_LOGIN = Long.valueOf("1");
            }

        } catch (Exception e) {
            Utils.showConfirmationDialog(contest, e.toString(), contest.getResources().getString(R.string.app_name));
        }
        return returnData;
    }

    private static void setupMethodChannel(Context context) {

        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL).setMethodCallHandler(
                new MethodChannel.MethodCallHandler() {
                    @Override
                    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
                        if(call.method.equals("initial")){
                            HashMap returnData= new HashMap<>();

                            String lang;
                            DataUser_SharedPreferences shar;
                            shar=new DataUser_SharedPreferences(context);

                            lang = REFERENCE_APP.preferences_slid.getString("LANG", null);

                            Log.e("latef", Resources.getSystem().getConfiguration().locale.getLanguage());
                            if(lang==null){
                                if(Resources.getSystem().getConfiguration().locale.getLanguage() =="ar" ){
                                    Shar_Pre_Lang("ar");
                                    lang="ar";
                                    Locale locale = new Locale("ar");
                                    Locale.setDefault(locale);
                                    Resources resources = context.getResources();
                                    Configuration config = resources.getConfiguration();
                                    config.setLocale(locale);
                                    resources.updateConfiguration(config, resources.getDisplayMetrics());
                                }
                                else {
                                    Shar_Pre_Lang("en");
                                    lang="en";
                                    Locale locale = new Locale("en");
                                    Locale.setDefault(locale);
                                    Resources resources = context.getResources();
                                    Configuration config = resources.getConfiguration();
                                    config.setLocale(locale);
                                    resources.updateConfiguration(config, resources.getDisplayMetrics());
                                }}
                            returnData.put("lang",lang);
                            HashMap check = checkFirstLogin(context);
                            Log.e("latef",check.toString());
                            returnData.put("IsLogin",shar.getLOGIN() !="");
                            if(check.isEmpty()||!check.containsKey("HomeData")||shar.getLOGIN() ==""){
                            returnData.put("IsLogin",false);
                            }else{
                                returnData.putAll(check);
                                PyesApp myApp = (PyesApp) context.getApplicationContext();
                                myApp.setGlobal_ExitFromApplication(false);
                                myApp.setLastRunActivity(context);
                                myApp.setGlobal_Confirmation(false);
                                Utils.setStoragePathMain(context);
                            }
                            Log.e("latef",returnData.toString());
                            result.success(returnData);

                        }
                        else if(call.method.equals("convertArDigitToEn")){
                        String res   = Utils.convertArDigitToEn(call.arguments.toString());
                        result.success(res);
                        }else if(call.method.equals("changLang")){
                            Shar_Pre_Lang(call.arguments.toString());

                        }else if(call.method.equals("login")){


                            HashMap  res=  ForceLogin(context,call.arguments.toString());
                            result.success(res);

                        }

                    }});
        /*new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler(
                        (call, result) -> {
                            // This method is invoked on the main thread.
                                Log.e("latefFlutter","outDoor");
                                Utils utils = new Utils();
                                if(call.method.equals("initial")){
                                    HashMap returnData= new HashMap<>();

                                    String lang;
                                    DataUser_SharedPreferences shar;
                                    shar=new DataUser_SharedPreferences(context);

                                    lang = REFERENCE_APP.preferences_slid.getString("LANG", null);

                                    Log.e("latef", Resources.getSystem().getConfiguration().locale.getLanguage());
                                    if(lang==null){
                                        if(Resources.getSystem().getConfiguration().locale.getLanguage() =="ar" ){
                                            Shar_Pre_Lang("ar");
                                            lang="ar";
                                            Locale locale = new Locale("ar");
                                            Locale.setDefault(locale);
                                            Resources resources = context.getResources();
                                            Configuration config = resources.getConfiguration();
                                            config.setLocale(locale);
                                            resources.updateConfiguration(config, resources.getDisplayMetrics());
                                        }
                                        else {
                                            Shar_Pre_Lang("en");
                                            lang="en";
                                            Locale locale = new Locale("en");
                                            Locale.setDefault(locale);
                                            Resources resources = context.getResources();
                                            Configuration config = resources.getConfiguration();
                                            config.setLocale(locale);
                                            resources.updateConfiguration(config, resources.getDisplayMetrics());
                                        }}
                                    returnData.put("lang",lang);
                                    returnData.put("IsLogin",shar.getLOGIN() !="");
                                    result.success(returnData);

                                }
                               */
        /* try {
                                        Class[] parameterTypes =  utils.getClass().getMethod(call.method).getParameterTypes();
                                        Method method =utils.getClass().getMethod(call.method,parameterTypes);
                                        returnData= method.invoke(utils, call.arguments);
                                        result.success(returnData);
                                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                    result.notImplemented();
                                }*//*

                        }
                );
*/
    }
}
