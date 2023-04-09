package dev.alamalbank.amb.Util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;

import dev.alamalbank.amb.BuildConfig;
import dev.alamalbank.amb.ConfigActivity;
import dev.alamalbank.amb.Confirmation;
import dev.alamalbank.amb.PyesApp;
import dev.alamalbank.amb.R;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.android.FlutterActivityLaunchConfigs;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.SmsManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.onesignal.OSDeviceState;
import com.onesignal.OneSignal;


import org.mozilla.javascript.Scriptable;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import java.text.DecimalFormat;
import java.text.NumberFormat;

import static android.content.Context.TELEPHONY_SERVICE;
import static androidx.core.content.ContextCompat.startActivity;

public class Utils {

    public static final String APP_TAG = "RASTAG";
    public static final String DROP_LOGIN_TABLE = "DROP TABLE LOGIN";
    public static final String DROP_HOME_TABLE = "DROP TABLE HOME";
    public static final String DROP_STATIC_TABLE = "DROP TABLE STATIC_DATA_DTL";
    public static final String DROP_CURR_TABLE = "DROP TABLE CURRENCIES";
    public static final String DROP_IDCARDS_TABLE = "DROP TABLE IDCARDS_TYPE";
    public static final String DROP_MENUS_TABLE = "DROP TABLE MENUS";
    public static final String DROP_SERVICES_TABLE = "DROP TABLE SERVICES";
    public static final String DROP_SERVICE_COL_TABLE = "DROP TABLE SERVICE_COL";
    public static final String DROP_TELECOMS_TABLE = "DROP TABLE TELECOMS";
    public static final String DROP_TELECOM_INFO_TABLE = "DROP TABLE TELECOM_INFO";
    public static final String DROP_TELECOM_SERVICES_TABLE = "DROP TABLE TELECOM_SERVICES";
    public static final String DROP_TELECOM_SERVICES_DTL_TABLE = "DROP TABLE TELECOM_SERVICES_DTL";
    public static final String DROP_FAVORITE_TABLE = "DROP TABLE FAVORITE_SERVICES";
    public static final String DROP_LAST_ENTRY_TABLE = "DROP TABLE LAST_ENTRY";
    public static final String DROP_ACCOUNTS_SERVICES_TABLE = "DROP TABLE ACCOUNTS_SERVICES";
    public static final String DROP_ACCOUNTS_TABLE = "DROP TABLE ACCOUNTS";
    public static final String DROP_TRANSACTIONS_TABLE = "DROP TABLE TRANSACTIONS";
    public static final String DROP_LAST_SEND_CMD_TABLE = "DROP TABLE LASTSENDCMD";
    public static final String DROP_INBOX_TABLE = "DROP TABLE INBOX";
    public static final String DROP_OUTBOX_TABLE = "DROP TABLE OUTBOX";
    public static final String DROP_BRANCHES_INFO_TABLE = "DROP TABLE BRANCHES_INFO";
    public static final String DROP_OPEN_ACCOUNTS_TABLE = "DROP TABLE OPEN_ACCOUNTS";
    public static final String DROP_NOTIFICATIONS_TABLE = "DROP TABLE NOTIFICATIONS";


    public static final String CREATE_LOGIN_TABLE =
            "CREATE TABLE IF NOT EXISTS LOGIN  (_ID INTEGER PRIMARY KEY AUTOINCREMENT,NO_OF_TRY_LOGIN TEXT,STOP_TIME TEXT,LIMIT_STOP_TRY_LOGIN TEXT)";
    public static final String CREATE_HOME_TABLE =
            "CREATE TABLE IF NOT EXISTS HOME  (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "COMPANY_ID TEXT," +
                    "COMPANY_NAME_AR TEXT," +
                    "COMPANY_NAME_EN TEXT," +
                    "APPLICATION_NAME_AR TEXT," +
                    "APPLICATION_NAME_EN TEXT," +
                    "COMPANY_WEBSITE TEXT," +
                    "COMPANY_FACEBOOK TEXT," +
                    "WS_SCHEME TEXT," +
                    "WS_HOST TEXT," +
                    "WS_PORT TEXT," +
                    "WS_PATH TEXT," +
                    "APP_PATH TEXT," +
                    "XMLURL TEXT," +
                    "XMLPATH TEXT," +
                    "XMLFILENAME TEXT," +
                    "CLIENT_NAME TEXT," +
                    "PWD TEXT," +
                    "VERIFICATION_CODE TEXT," +
                    "EMAIL TEXT," +
                    "OPEN_ACCOUNT_EMAIL TEXT," +
                    "BACKUPPATH TEXT," +
                    "FIRSTLOGIN TEXT," +
                    "LASTLOGIN TEXT," +
                    "PLAYER_ID TEXT," +
                    "DEVICE_SERIAL TEXT," +
                    "APPCODEPART1 TEXT," +
                    "APPCODEPART2 TEXT," +
                    "SERVICES_VERSION TEXT," +
                    "CS_PHONE TEXT," +
                    "SIM TEXT," +
                    "WHATS_APP TEXT," +
                    "DEFAULT_LANG TEXT," +
                    "ACTIVE_STOP TEXT)";
    public static final String CREATE_STATIC_TABLE =
            "CREATE TABLE IF NOT EXISTS STATIC_DATA_DTL(_ID INTEGER PRIMARY KEY AUTOINCREMENT,COM_ID TEXT,STATIC_ID EXT,STATIC_DTL_ID TEXT,STATIC_DTL_NAME_AR TEXT,STATIC_DTL_NAME_EN TEXT,STATIC_DTL_VALUE TEXT)";
    public static final String CREATE_CURR_TABLE =
            "CREATE TABLE IF NOT EXISTS CURRENCIES(_ID INTEGER PRIMARY KEY AUTOINCREMENT,COM_ID TEXT,CUR_ID EXT,CUR_NAME_AR TEXT,CUR_NAME_EN TEXT)";
    public static final String CREATE_IDCARDS_TABLE =
            "CREATE TABLE IF NOT EXISTS IDCARDS_TYPE(_ID INTEGER PRIMARY KEY AUTOINCREMENT,COM_ID TEXT,TYPE_ID EXT,TYPE_NAME_AR TEXT,TYPE_NAME_EN TEXT)";
    public static final String CREATE_MENUS_TABLE =
            "CREATE TABLE IF NOT EXISTS MENUS  (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "COM_ID TEXT,MENU_ID TEXT,MENU_PARENT TEXT , MENU_ORD TEXT,MENU_NAME_AR TEXT,MENU_NAME_EN TEXT," +
                    "MENU_HELP_AR TEXT,MENU_HELP_EN TEXT,IS_LEAF TEXT,SERVICE_CODE TEXT,ACTIVITY_TYPE TEXT,MENU_ICON TEXT)";
    public static final String CREATE_SERVICES_TABLE =
            "CREATE TABLE IF NOT EXISTS SERVICES  (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "COM_ID TEXT,SERVICE_ID TEXT,SERVICE_CODE TEXT,SERVICE_TYPE TEXT,SERVICE_NAME_AR TEXT,SERVICE_NAME_EN TEXT,SEND_REQUEST_VIA TEXT)";
    public static final String CREATE_SERVICE_COL_TABLE =
            "CREATE TABLE IF NOT EXISTS SERVICE_COL  (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "COM_ID TEXT,SERVICE_CODE TEXT,FIELD_ID TEXT,FIELD_ORD TEXT,LABEL_AR TEXT,LABEL_EN TEXT,HINT_AR TEXT,HINT_EN TEXT,ITEM_TYPE TEXT,DATA_TYPE TEXT," +
                    "APPENDABLE TEXT,MIN_VALUE_LENGTH TEXT,MAX_VALUE_LENGTH TEXT,CONDITION TEXT,IS_ACC_ID TEXT,QUERY_STATEMENT TEXT,ICON_TYPE TEXT)";
    public static final String CREATE_TELECOMS_TABLE =
            "CREATE TABLE IF NOT EXISTS TELECOMS  (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "COM_ID TEXT,TELECOM_ID TEXT,TELECOM_NAME_AR TEXT,TELECOM_NAME_EN TEXT,MIN_AMT TEXT,MAX_AMT TEXT,HAS_SMS_CONNECTION TEXT,TELECOM_COLOR TEXT,TELECOM_LOGO TEXT)";
    public static final String CREATE_TELECOM_INFO_TABLE =
            "CREATE TABLE IF NOT EXISTS TELECOM_INFO  (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "COM_ID TEXT,TELECOM_ID TEXT,TELECOM_PREFIX TEXT,TELECOM_NUM_LENGTH TEXT,TELECOM_TBL TEXT,MIN_PHONE_NUM TEXT,MAX_PHONE_NUM TEXT,SERVICE_CODE TEXT,SERVICE_NAME TEXT)";
    public static final String CREATE_TELECOM_SERVICES_TABLE =
            "CREATE TABLE IF NOT EXISTS TELECOM_SERVICES  (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "COM_ID TEXT,TELECOM_ID TEXT,SERVICE_TYPE TEXT,LINETYPE_ID TEXT,SERVICE_ID TEXT,SERVICE_NAME_AR TEXT,SERVICE_NAME_EN TEXT)";
    public static final String CREATE_TELECOM_SERVICES_DTL_TABLE =
            "CREATE TABLE IF NOT EXISTS TELECOM_SERVICES_DTL (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "COM_ID TEXT,TELECOM_ID TEXT,SERVICE_TYPE TEXT,LINETYPE_ID TEXT,SERVICE_ID TEXT,WITH_ADVANCE_PAYMENT TEXT,SERVICE_DTL_ID TEXT," +
                    "SERVICE_DTL_NAME_AR TEXT,SERVICE_DTL_NAME_EN TEXT,SERVICE_DTL_VALUE TEXT)";
    public static final String CREATE_FAVORITE_TABLE =
            "CREATE TABLE IF NOT EXISTS FAVORITE_SERVICES  (_ID INTEGER PRIMARY KEY AUTOINCREMENT,PARENT_CODE TEXT,SERVICE_CODE TEXT)";
    public static final String CREATE_LAST_ENTRY_TABLE =
            "CREATE TABLE IF NOT EXISTS LAST_ENTRY  (_ID INTEGER PRIMARY KEY AUTOINCREMENT,TYPE_ID TEXT  ,LAST_ENTRY TEXT NOT NULL ) ";
    public static final String CREATE_ACCOUNTS_TABLE =
            "CREATE TABLE IF NOT EXISTS ACCOUNTS  (_ID INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ",DATETIME TEXT  " +
                    ",ACCOUNT_NO TEXT NOT NULL " +
                    ",ACCOUNT_TYPE TEXT " +
                    ",ACCOUNT_CURR TEXT NOT NULL " +
                    ",ACCOUNT_CLASSIFICATION TEXT " +
                    ",ACCOUNT_NAME TEXT NOT NULL" +
                    ",IS_DEFAULT TEXT NOT NULL" +
                    ",MOBILE_NO TEXT ,ACCOUNT_BALANCE TEXT) ";
    public static final String CREATE_ACCOUNTS_SERVICES_TABLE =
            "CREATE TABLE IF NOT EXISTS ACCOUNTS_SERVICES  (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "COM_ID TEXT NOT NULL ," +
                    "ACCOUNT_NO TEXT NOT NULL ," +
                    "SERVICE_CODE TEXT TEXT NOT NULL," +
                    "IS_MANDATORY TEXT NOT NULL) ";
    public static final String CREATE_TRANSACTIONS_TABLE =
            "CREATE TABLE IF NOT EXISTS TRANSACTIONS (_ID INTEGER PRIMARY KEY AUTOINCREMENT,DATETIME TEXT  ,MESSAGE TEXT NOT NULL) ";
    public static final String CREATE_LAST_SEND_CMD_TABLE =
            "CREATE TABLE IF NOT EXISTS LASTSENDCMD (_ID INTEGER PRIMARY KEY AUTOINCREMENT,DATETIME TEXT  ,MESSAGE TEXT NOT NULL) ";
    public static final String CREATE_INBOX_TABLE =
            "CREATE TABLE IF NOT EXISTS INBOX (_ID INTEGER PRIMARY KEY AUTOINCREMENT,DATETIME TEXT  ,MESSAGE TEXT NOT NULL) ";
    public static final String CREATE_OUTBOX_TABLE =
            "CREATE TABLE IF NOT EXISTS OUTBOX (_ID INTEGER PRIMARY KEY AUTOINCREMENT,DATETIME TEXT  ,MESSAGE TEXT NOT NULL) ";
    public static final String CREATE_BRANCHES_INFO_TABLE =
            "CREATE TABLE IF NOT EXISTS BRANCHES_INFO (_ID INTEGER PRIMARY KEY AUTOINCREMENT,COM_ID TEXT,TYPE TEXT NOT NULL ,ID TEXT NOT NULL,NAME_AR TEXT,ADDRESSE_AR TEXT,NAME_EN TEXT,ADDRESS_EN TEXT,LONGITUDE TEXT,LATITUDE TEXT) ";
    public static final String CREATE_OPEN_ACCOUNTS_TABLE =
            "CREATE TABLE IF NOT EXISTS OPEN_ACCOUNTS (_ID INTEGER PRIMARY KEY AUTOINCREMENT,AGENT_ACCOUNT_NO TEXT NOT NULL,OPEN_DATE DATE  ,"
                    + " CLIENT_NAME TEXT NOT NULL,CLIENT_MOBILE_NO TEXT NOT NULL,CLIENT_EMAIL TEXT NOT NULL,"
                    + " CLIENT_CARD_TYPE TEXT NOT NULL,CLIENT_CARD_NO TEXT NOT NULL,CLIENT_BIRTH_PLACE TEXT NOT NULL,CLIENT_BIRTH_DATE TEXT NOT NULL,POSTED TEXT NOT NULL) ";
    public static final String CREATE_NOTIFICATIONS_TABLE =
            "CREATE TABLE IF NOT EXISTS NOTIFICATIONS (_ID INTEGER PRIMARY KEY AUTOINCREMENT,TYPE TEXT NOT NULL ,ID TEXT NOT NULL,PRIORITY TEXT,DATETIME TEXT,TITLE TEXT,NOTE TEXT,STATUS TEXT) ";

    public static final int TIMER_SYNC_INFO = 60 * 1000;//  seconds
    public static final int MAX_PASSWORD_LENGTH = 8;
    public static final long MAX_ACTIVITY_TRANSITION_TIME_MS = 1000 * 60;
    public static final String APP_PATH = "/PYes/";
    public static final String ClientsImagesPath = "/PYes/ClientsPhoto";
    public static final int STORAGE_PERMISSION_CODE = 1001;
    public static final String[] STORAGE_PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final int SMS_PERMISSION_CODE = 1002;
    public static final String[] SMS_PERMISSIONS = {Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS};
    public static final int CAMERA_PERMISSION_CODE = 1003;
    public static final String[] CAMERA_PERMISSIONS = {Manifest.permission.CAMERA};
    public static final int INTERNET_PERMISSION_CODE = 1004;
    public static final String[] INTERNET_PERMISSIONS = {Manifest.permission.INTERNET};
    public static final int NET_PERMISSION_CODE = 1005;
    public static final String[] NET_PERMISSIONS = {Manifest.permission.ACCESS_NETWORK_STATE};
    public static final int CONTACTS_PERMISSION_CODE = 1006;
    public static final String[] CONTACTS_PERMISSIONS = {Manifest.permission.READ_CONTACTS};
    public static final int READ_PHONE_STATE_PERMISSION_CODE = 1007;
    public static final String[] READ_PHONE_STATE_PERMISSIONS = {Manifest.permission.READ_PHONE_STATE};
    public static final int REQUEST_SCAN_MODE = 49374;
    public static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    public static final String DOWNLOAD_SCAN = "market://search?q=pname:" + "com.google.zxing.client.android";
    public static final int UPDATE_APP = 0;
    public static final int REGISTER = 1;
    public static final int GET_APP_VERSION = 2;
    public static final int GET_XML_VERSION_UPDATE = 3;
    public static final int GET_XML_VERSION = 4;
    public static final int GET_CLIENT_SERVICES = 5;
    public static final int GET_STATIC_DATA = 6;
    public static final int GET_BRANCHESAGENT_SERVICES = 7;
    public static final int GET_TELECOM_SERVICES = 8;
    public static final int SEND_TAGS = 9;
    public static final int SEND_CMD = 10;
    public static final int DOWNLOAD_SERVICES_LOCAL = 11;
    public static final int DOWNLOAD_SERVICES_WEB = 12;
    public static final int LOAD_CONTACT_LIST = 13;
    public static final int LOAD_BRANCHES = 14;
    public static final int GET_BALANCE = 15;
    public static final int TEST = 16;
    public static NodeList listXml;
    public static String mRS = "";

    public static boolean firstLogin(Activity activity) {
        urlSet(activity);
        String m_COMPANY_ID = "'1'";
        String m_COMPANY_NAME_AR = "'بنك الأمل'";
        String m_COMPANY_NAME_EN = "'Al Amal Bank'";
        String m_APPLICATION_NAME_AR = "'بيس'";
        String m_APPLICATION_NAME_EN = "'PaYYes'";
        String m_COMPANY_WEBSITE = "'http://www.alamalbank.com/'";
        String m_COMPANY_FACEBOOK = "'https://www.facebook.com/alamal.bank'";
        String m_XMLFILENAME = "'pyesservices.xml'";
        String m_FirstLogIn = "1";
        String m_SQL_Insert = "insert into home(COMPANY_ID,COMPANY_NAME_AR,COMPANY_NAME_EN," +
                "APPLICATION_NAME_AR,APPLICATION_NAME_EN," +
                "COMPANY_WEBSITE," +
                "COMPANY_FACEBOOK," +
                "ws_scheme," +
                "ws_host," +
                "ws_port," +
                "ws_path," +
                "app_path," +
                "XMLURL," +
                "XMLFILENAME ," +
                "firstLogIn,active_stop) "
                + " values(" + m_COMPANY_ID + "," + m_COMPANY_NAME_AR + "," + m_COMPANY_NAME_EN + "," +
                m_APPLICATION_NAME_AR + "," + m_APPLICATION_NAME_EN + "," +
                m_COMPANY_WEBSITE + "," +
                m_COMPANY_FACEBOOK + ",'" +
                sGet(activity) + "','" + //ws_scheme
                hGet(activity) + "','" + //ws_host
                pGet(activity) + "','" + //ws_port
                uGet(activity) + "','" + //ws_path
                aGet(activity) + "','" + //app_path
                xGet(activity) + "'," +
                m_XMLFILENAME + "," +
                m_FirstLogIn + ",1)";
        String m_SQL_Update = "update home set"
                + " COMPANY_NAME_AR=" + m_COMPANY_NAME_AR + ","
                + " COMPANY_NAME_EN=" + m_COMPANY_NAME_EN + ","
                + " APPLICATION_NAME_AR=" + m_APPLICATION_NAME_AR + ","
                + " APPLICATION_NAME_EN=" + m_APPLICATION_NAME_EN + ","
                + " COMPANY_WEBSITE=" + m_COMPANY_WEBSITE + ","
                + " COMPANY_FACEBOOK=" + m_COMPANY_FACEBOOK + ","
                + " ws_scheme=" + uGet(activity) + ","
                + " ws_host=" + uGet(activity) + ","
                + " ws_port=" + uGet(activity) + ","
                + " ws_path=" + uGet(activity) + ","
                + " app_path=" + aGet(activity) + ","
                + " XMLURL='" + xGet(activity) + "',"
                + " XMLFILENAME=" + m_XMLFILENAME + ","
                + " firstLogIn=" + m_FirstLogIn;
        try {
            final SQLiteMain entry = new SQLiteMain(activity);
            entry.open();
            Cursor home = entry.execute_Query("select ws_scheme,ws_host,ws_port,ws_path,XMLPATH,XMLFILENAME,firstLogIn from home");
            home.moveToFirst();
            if (home.getCount() == 0) {
                entry.execute_SQL(m_SQL_Insert);
            } else if (home.getCount() == 1) {
                if ((home.getString(0).isEmpty()) || (home.getString(1).isEmpty() || (home.getString(2).isEmpty()))) {
                    entry.execute_SQL(m_SQL_Update);
                }
            } else {
                entry.execute_SQL("delete from home ");
                entry.execute_SQL(m_SQL_Insert);
            }
            entry.close();
            return (true);
        } catch (Exception e) {
            Utils.showConfirmationDialog(activity, "Utils.FirstLogin\n" + e.toString(), activity.getResources().getString(R.string.app_name));
            return (false);
        }
    }

    public static void onPause(Activity activity) {
        PyesApp myApp = (PyesApp) activity.getApplicationContext();
        myApp.setLastPauseActivity(activity);
        myApp.startActivityTransitionTimer();
    }

    public static void onStart(Activity activity) {
        PyesApp myApp = (PyesApp) activity.getApplicationContext();

        try {
            if (myApp.getGlobal_ExitFromApplication()) {
                activity.finish();
            } else if (myApp.getWasActivityInBackground()) {
                if (myApp.getLastPauseActivity().equals(activity)) {
                    Log.e(Utils.APP_TAG, "onStart -getLastPauseActivity: " + activity.getClass().getSimpleName() + "\n" +
                            myApp.getGlobal_ForceLogin_Flag());
                    if ((!activity.getClass().getSimpleName().equalsIgnoreCase("FORCELOGIN"))
                            && (!myApp.getGlobal_ForceLogin_Flag())) {
                        myApp.setGlobal_ForceLogin_Flag(true);
                        Utils.runIntent(activity, "FORCELOGIN", "ar");
                    }
                }
                myApp.stopActivityTransitionTimer();
            }
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());
        }
    }

    public static void onResume(Activity activity) {
        PyesApp myApp = (PyesApp) activity.getApplicationContext();
        try {
            if (myApp.getGlobal_ExitFromApplication()) {
                activity.finish();
            }
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());
        }
    }

    public static String getPlayerId(String mAccountNo) {
     /*   OSDeviceState status = OneSignal.getDeviceState();
        return status.getUserId();*/

      /* if(status.getUserId()!=null){
            Log.e("latef","getplayerid1"+":"+status.getUserId());

            return status.getUserId();

       }*/
    // Log.e("latef","getplayerid2"+":"+mAccountNo);
        return "9066c836-87e4-11ec-9ccb-2a3681798ada";

    }   public static String getPlayerId() {
     /*   OSDeviceState status = OneSignal.getDeviceState();
        return status.getUserId();*/

      /* if(status.getUserId()!=null){
            Log.e("latef","getplayerid1"+":"+status.getUserId());

            return status.getUserId();

       }*/
    // Log.e("latef","getplayerid2"+":"+mAccountNo);
        return "9066c836-87e4-11ec-9ccb-2a3681798ada";

    }

    public static void setLocal(Activity m_Activity, String m_Language) {

        Locale locale = new Locale(m_Language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        m_Activity.getBaseContext().getResources().updateConfiguration(config,
                m_Activity.getBaseContext().getResources().getDisplayMetrics());

    }

    public static boolean dropTables(Context context) {
        try {
            final SQLiteMain entry = new SQLiteMain(context);
            entry.open();
            entry.execute_SQL(Utils.DROP_LOGIN_TABLE);
            entry.execute_SQL(Utils.DROP_HOME_TABLE);
            entry.execute_SQL(Utils.DROP_STATIC_TABLE);
            entry.execute_SQL(Utils.DROP_IDCARDS_TABLE);
            entry.execute_SQL(Utils.DROP_CURR_TABLE);
            entry.execute_SQL(Utils.DROP_MENUS_TABLE);
            entry.execute_SQL(Utils.DROP_SERVICES_TABLE);
            entry.execute_SQL(Utils.DROP_SERVICE_COL_TABLE);
            entry.execute_SQL(Utils.DROP_TELECOMS_TABLE);
            entry.execute_SQL(Utils.DROP_TELECOM_INFO_TABLE);
            entry.execute_SQL(Utils.DROP_TELECOM_SERVICES_TABLE);
            entry.execute_SQL(Utils.DROP_TELECOM_SERVICES_DTL_TABLE);
            entry.execute_SQL(Utils.DROP_ACCOUNTS_SERVICES_TABLE);
            entry.execute_SQL(Utils.DROP_ACCOUNTS_TABLE);
            entry.execute_SQL(Utils.DROP_TRANSACTIONS_TABLE);
            entry.execute_SQL(Utils.DROP_LAST_SEND_CMD_TABLE);
            entry.execute_SQL(Utils.DROP_INBOX_TABLE);
            entry.execute_SQL(Utils.DROP_OUTBOX_TABLE);
            entry.execute_SQL(Utils.DROP_FAVORITE_TABLE);
            entry.execute_SQL(Utils.DROP_LAST_ENTRY_TABLE);
            entry.execute_SQL(Utils.DROP_BRANCHES_INFO_TABLE);
            entry.execute_SQL(Utils.DROP_OPEN_ACCOUNTS_TABLE);
            entry.execute_SQL(Utils.DROP_NOTIFICATIONS_TABLE);

            entry.close();
            return (true);
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());
            return (false);
        }

    }

    public static boolean createTablesIfNotExists(Context context) {
        try {
            final SQLiteMain entry = new SQLiteMain(context);
            entry.open();
            entry.execute_SQL(Utils.CREATE_LOGIN_TABLE);
            entry.execute_SQL(Utils.CREATE_HOME_TABLE);
            entry.execute_SQL(Utils.CREATE_STATIC_TABLE);
            entry.execute_SQL(Utils.CREATE_CURR_TABLE);
            entry.execute_SQL(Utils.CREATE_IDCARDS_TABLE);
            entry.execute_SQL(Utils.CREATE_MENUS_TABLE);
            entry.execute_SQL(Utils.CREATE_SERVICES_TABLE);
            entry.execute_SQL(Utils.CREATE_SERVICE_COL_TABLE);
            entry.execute_SQL(Utils.CREATE_TELECOMS_TABLE);
            entry.execute_SQL(Utils.CREATE_TELECOM_INFO_TABLE);
            entry.execute_SQL(Utils.CREATE_TELECOM_SERVICES_TABLE);
            entry.execute_SQL(Utils.CREATE_TELECOM_SERVICES_DTL_TABLE);
            entry.execute_SQL(Utils.CREATE_ACCOUNTS_TABLE);
            entry.execute_SQL(Utils.CREATE_ACCOUNTS_SERVICES_TABLE);
            entry.execute_SQL(Utils.CREATE_TRANSACTIONS_TABLE);
            entry.execute_SQL(Utils.CREATE_LAST_SEND_CMD_TABLE);
            entry.execute_SQL(Utils.CREATE_INBOX_TABLE);
            entry.execute_SQL(Utils.CREATE_OUTBOX_TABLE);
            entry.execute_SQL(Utils.CREATE_FAVORITE_TABLE);
            entry.execute_SQL(Utils.CREATE_LAST_ENTRY_TABLE);
            entry.execute_SQL(Utils.CREATE_BRANCHES_INFO_TABLE);
            entry.execute_SQL(Utils.CREATE_OPEN_ACCOUNTS_TABLE);
            entry.execute_SQL(Utils.CREATE_NOTIFICATIONS_TABLE);

            entry.close();
            return (true);
        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }
    }

    public static View populateView(final Activity myActivity, String myViewType, int myViewId, int myInputType) {
        Typeface myFont = Typeface.createFromAsset(myActivity.getAssets(), "font/jannat_bold.otf");
        TextView myTempnNOiewView = new TextView(myActivity);
        myTempnNOiewView.setTypeface(myFont);
        myTempnNOiewView.setInputType(myInputType);
        myTempnNOiewView.setBackgroundColor(myActivity.getResources().getColor(R.color.color_background));
        myTempnNOiewView.setTextColor(myActivity.getResources().getColor(R.color.color_red));
        try {
            if (myViewType.equalsIgnoreCase("RecyclerView")) {
                return (myActivity.<RecyclerView>findViewById(myViewId));}
                else if (myViewType.equalsIgnoreCase("LinearLayout")) {
                    return (myActivity.<LinearLayout>findViewById(myViewId));
            } else if (myViewType.equalsIgnoreCase("ScrollView")) {
                return (myActivity.<ScrollView>findViewById(myViewId));
            } else if (myViewType.equalsIgnoreCase("ListView")) {
                return (myActivity.<ListView>findViewById(myViewId));
            } else if (myViewType.equalsIgnoreCase("ImageView")) {
                return (myActivity.<ImageView>findViewById(myViewId));
            } else if (myViewType.equalsIgnoreCase("Web_View")) {
                return (myActivity.<WebView>findViewById(myViewId));
            } else if (myViewType.equalsIgnoreCase("Spinner")) {
                return (myActivity.<Spinner>findViewById(myViewId));
            } else if (myViewType.equalsIgnoreCase("TextView")) {
                TextView myTempTextViewView = myActivity.findViewById(myViewId);
                myTempTextViewView.setTypeface(myFont);
                myTempTextViewView.setInputType(myInputType);
                return (myTempTextViewView);
            } else if (myViewType.equalsIgnoreCase("RadioGroup")) {
                return (myActivity.<RadioGroup>findViewById(myViewId));
            } else if (myViewType.equalsIgnoreCase("RadioButton")) {
                RadioButton myTempRGBView = myActivity.findViewById(myViewId);
                myTempRGBView.setTypeface(myFont);
                return (myTempRGBView);
            } else if (myViewType.equalsIgnoreCase("TextInputLayout")) {
                TextInputLayout myTempTextLayoutView = myActivity.findViewById(myViewId);
                myTempTextLayoutView.setTypeface(myFont);
                return (myTempTextLayoutView);
            } else if (myViewType.equalsIgnoreCase("AutoCompleteTextView")) {
                AutoCompleteTextView myTempAutoViewName = myActivity.findViewById(myViewId);
                myTempAutoViewName.setTypeface(myFont);
                myTempAutoViewName.setInputType(myInputType);
                myTempAutoViewName.setMaxLines(1);
                myTempAutoViewName.setImeOptions(EditorInfo.IME_ACTION_NEXT); //Change the editor type integer associated with the text view, which will be reported to an IME with EditorInfo.imeOptions when it has focus.
                myTempAutoViewName.setThreshold(1);
                return (myTempAutoViewName);
            } else if (myViewType.equalsIgnoreCase("EditText")) {
                EditText myTempEditTextViewName = myActivity.findViewById(myViewId);
                myTempEditTextViewName.setTypeface(myFont);
                myTempEditTextViewName.setInputType(myInputType);
                return (myTempEditTextViewName);
            } else if (myViewType.equalsIgnoreCase("TextInputEditText")) {
                TextInputEditText myTempTextInputViewName = myActivity.findViewById(myViewId);
                myTempTextInputViewName.setTypeface(myFont);
                myTempTextInputViewName.setInputType(myInputType);
                return (myTempTextInputViewName);
            } else if (myViewType.equalsIgnoreCase("Button")) {
                Button myTempButtonViewName = myActivity.findViewById(myViewId);
                myTempButtonViewName.setTypeface(myFont);
                myTempButtonViewName.setOnClickListener((View.OnClickListener) myActivity);
                return (myTempButtonViewName);
            } else if (myViewType.equalsIgnoreCase("CardView")) {
                CardView myTempCardViewViewName = myActivity.findViewById(myViewId);
                myTempCardViewViewName.setOnClickListener((View.OnClickListener) myActivity);
                return (myTempCardViewViewName);
            } else {
                myTempnNOiewView.setText("No View");
                Utils.showToast(myActivity, "No View");
                return (myTempnNOiewView);
            }
        } catch (Exception e) {
            myTempnNOiewView.setText(e.toString());
            Utils.showConfirmationDialog(myActivity, "Utils.populateView\n"
                    + myActivity.getResources().getResourceName(myViewId) + "\n"
                    + e.toString(), myActivity.getResources().getString(R.string.app_name));
            return (myTempnNOiewView);
        }
    }

    public static void populateAutoCompAdapter(final Activity myActivity, AutoCompleteTextView myAutoComp, ArrayList<String> myArrayList) {
        try {
            if (Build.VERSION.SDK_INT > 15) {
                myAutoComp.setBackgroundResource(R.drawable.rounded_w);
                myAutoComp.setDropDownBackgroundDrawable(myActivity.getResources().getDrawable(R.drawable.l_background));
            }
            ArrayAdapter<String> contactListAdapter = new ArrayAdapter<>(myActivity, android.R.layout.simple_dropdown_item_1line, myArrayList);
            contactListAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
            myAutoComp.setAdapter(contactListAdapter);
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());
            Utils.showToast(myActivity, e.toString() + "\n" + myAutoComp.toString());
        }
    }

    public static void populateSpinnerAdapter(final Activity myActivity, Spinner mySpinner, ArrayList<String> myArrayList, int icon) {
        final Typeface myFont = Typeface.createFromAsset(myActivity.getAssets(), "font/jannat_bold.otf");
        try {
            if (Build.VERSION.SDK_INT > 15) {
                mySpinner.setBackgroundResource(R.drawable.rounded_w);
                mySpinner.setPopupBackgroundDrawable(myActivity.getResources().getDrawable(R.drawable.l_background));
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(myActivity, icon, myArrayList) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    ((TextView) v).setTypeface(myFont);
                    return v;
                }

                public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                    View v = super.getDropDownView(position, convertView, parent);
                    ((TextView) v).setTypeface(myFont);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        v.setBackground(myActivity.getResources().getDrawable(R.drawable.rounded_w));
                    }

                    return v;
                }
            };

            arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            mySpinner.setAdapter(arrayAdapter);

        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());
            Utils.showToast(myActivity, e.toString());
        }
    }

    public static void populateAccountSpinnerAdapter(final Activity myActivity, Spinner mySpinner, String whereClause) {
        final Typeface myFont = Typeface.createFromAsset(myActivity.getAssets(), "font/jannat_bold.otf");
        List<ListViewBean> listViewBean = Utils.getAccountInfo(myActivity, whereClause);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.clear();
        if (listViewBean.size() > 0) {
            for (int i = 0; i < listViewBean.size(); i++) {
                arrayList.add(listViewBean.get(i).getAccount_no() + ":" + listViewBean.get(i).getAccount_type() + ":" +
                        listViewBean.get(i).getAccount_curr());
            }
            try {
                if (Build.VERSION.SDK_INT > 15) {
                    mySpinner.setBackgroundResource(R.drawable.rounded_w);
                    mySpinner.setPopupBackgroundDrawable(myActivity.getResources().getDrawable(R.drawable.l_background));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(myActivity, R.layout.spinner_item, arrayList) {
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View v = super.getView(position, convertView, parent);
                        ((TextView) v).setTypeface(myFont);
                        return v;
                    }

                    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                        View v = super.getDropDownView(position, convertView, parent);
                        ((TextView) v).setTypeface(myFont);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            v.setBackground(myActivity.getResources().getDrawable(R.drawable.rounded_w));
                        }

                        return v;
                    }
                };

                arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                mySpinner.setAdapter(arrayAdapter);

            } catch (Exception e) {
                Log.e(Utils.APP_TAG, e.toString());
                Utils.showToast(myActivity, e.toString());
            }
        }
    }

    public static boolean addedToFavorite(Activity act, String mainServices, String detailService) {
        boolean flag = false;
        String mCount = Utils.getColumnValue(act, "select count(*) as cnt from favorite_services where parent_code='" + mainServices + "' and service_code='" + detailService + "'");
        if ((mCount != null) && (mCount.equalsIgnoreCase("0"))) {
            boolean runFlag = Utils.runSQL(act, "insert into favorite_services(PARENT_CODE,SERVICE_CODE)" +
                    "values('" + mainServices + "','" + detailService + "')");
            if (runFlag) {
                flag = true;
                Utils.showToast(act, act.getResources().getString(R.string.favorite_added_success));
            } else {
                flag = false;
                Utils.showToast(act, act.getResources().getString(R.string.favorite_added_not_success));
            }
        } else {
            Utils.showToast(act, act.getResources().getString(R.string.favorite_added_before));
        }
        return flag;
    }


    public static void callAppSettings(Activity act) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", act.getPackageName(), null);
        intent.setData(uri);
        act.startActivity(intent);
    }

    public static void requestPermission(Context context, int permissionCode) {

        switch (permissionCode) {
            case Utils.STORAGE_PERMISSION_CODE:
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    Utils.permissionNeededDialog(context, context.getResources().getString(R.string.STORAGE_permission_explanation) + "\n"
                                    + context.getResources().getString(R.string.accept_permission), context.getResources().getString(R.string.permission_needed),
                            Utils.STORAGE_PERMISSIONS, permissionCode);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, Utils.STORAGE_PERMISSIONS, permissionCode);
                }
                break;
            case Utils.SMS_PERMISSION_CODE:
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.SEND_SMS) ||
                        ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_SMS) ||
                        ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.RECEIVE_SMS)) {
                    Utils.permissionNeededDialog(context, context.getResources().getString(R.string.SMS_permission_explanation) + "\n"
                                    + context.getResources().getString(R.string.accept_permission), context.getResources().getString(R.string.permission_needed),
                            Utils.SMS_PERMISSIONS, permissionCode);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, Utils.SMS_PERMISSIONS, permissionCode);
                }
                break;
            case Utils.CAMERA_PERMISSION_CODE:
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                    Utils.permissionNeededDialog(context, context.getResources().getString(R.string.CAMERA_permission_explanation) + "\n"
                                    + context.getResources().getString(R.string.accept_permission), context.getResources().getString(R.string.permission_needed),
                            Utils.CAMERA_PERMISSIONS, permissionCode);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, Utils.CAMERA_PERMISSIONS, permissionCode);
                }
                break;
            case Utils.INTERNET_PERMISSION_CODE:
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.INTERNET)) {
                    Utils.permissionNeededDialog(context, context.getResources().getString(R.string.INTERNET_permission_explanation) + "\n"
                                    + context.getResources().getString(R.string.accept_permission), context.getResources().getString(R.string.permission_needed),
                            Utils.INTERNET_PERMISSIONS, permissionCode);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, Utils.INTERNET_PERMISSIONS, permissionCode);
                }
                break;
            case Utils.NET_PERMISSION_CODE:
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_NETWORK_STATE)) {
                    Utils.permissionNeededDialog(context, context.getResources().getString(R.string.NET_permission_explanation) + "\n"
                                    + context.getResources().getString(R.string.accept_permission), context.getResources().getString(R.string.permission_needed),
                            Utils.NET_PERMISSIONS, permissionCode);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, Utils.INTERNET_PERMISSIONS, permissionCode);
                }
                break;
            case Utils.CONTACTS_PERMISSION_CODE:
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_CONTACTS)) {
                    Utils.permissionNeededDialog(context, context.getResources().getString(R.string.CONTACTS_permission_explanation) + "\n"
                                    + context.getResources().getString(R.string.accept_permission), context.getResources().getString(R.string.permission_needed),
                            Utils.CONTACTS_PERMISSIONS, permissionCode);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, Utils.CONTACTS_PERMISSIONS, permissionCode);
                }
                break;
            case Utils.READ_PHONE_STATE_PERMISSION_CODE:
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_PHONE_STATE)) {
                    Utils.permissionNeededDialog(context, context.getResources().getString(R.string.READ_PHONE_STATE_permission_explanation) + "\n"
                                    + context.getResources().getString(R.string.accept_permission), context.getResources().getString(R.string.permission_needed),
                            Utils.READ_PHONE_STATE_PERMISSIONS, permissionCode);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, Utils.READ_PHONE_STATE_PERMISSIONS, permissionCode);
                }
                break;
          /*  case Utils.CALL_PHONE_PERMISSION_CODE:
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CALL_PHONE)) {
                    Utils.permissionneededDialog(context, context.getResources().getString(R.string.CALL_PHONE_permission_explanation) + "\n"
                                    + context.getResources().getString(R.string.accept_permission), context.getResources().getString(R.string.permission_needed),
                            Utils.CALL_PHONE_PERMISSIONS, permissionCode);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, Utils.CALL_PHONE_PERMISSIONS, permissionCode);
                }
                break;*/
        }
    }

    public static boolean hasPermissions(Context context, int requestCode, String[] permissions) {
        boolean flag = false;
        String permission = "";
        int i = 0;
        try {
            if ((requestCode == Utils.SMS_PERMISSION_CODE)
                    || (requestCode == Utils.STORAGE_PERMISSION_CODE)
                    || (requestCode == Utils.CAMERA_PERMISSION_CODE)
                    || (requestCode == Utils.NET_PERMISSION_CODE)
                    || (requestCode == Utils.INTERNET_PERMISSION_CODE)
                    || (requestCode == Utils.CONTACTS_PERMISSION_CODE)
                    || (requestCode == Utils.READ_PHONE_STATE_PERMISSION_CODE)
            ) {
                flag = true;
                while ((flag) && (i < permissions.length)) {
                    permission = permissions[i];
                    i++;
                    flag = ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
                }
            }
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, "hasPermission \n" + e.toString());
            flag = false;
        }
        return flag;
    }

    public static File getFileName(String p_Dir_name, String m_File_name) {
        File sd = Environment.getExternalStorageDirectory();
        File pyesDir = new File(sd + "/" + p_Dir_name);
        if (!pyesDir.exists()) {
            pyesDir.mkdir();
        }
        File file = new File(pyesDir + "/" + m_File_name);

        return file;

    }

    public static Bitmap setPictureDimensions(String path, int reqWidth, int reqHeight) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            final int height = options.outHeight;
            final int width = options.outWidth;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            int inSampleSize = 1;
            if (height > reqHeight) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            }
            int expectedWidth = width / inSampleSize;
            if (expectedWidth > reqWidth) {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
            options.inSampleSize = inSampleSize;
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(path, options);
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());
        }
        return null;
    }

    public static void setStoragePathMain(final Context context) {
        PyesApp myApp = (PyesApp) context.getApplicationContext();
        List<String> sdCardList;
        String storage = "";
        try {
            sdCardList = Utils.getSdCardPaths(context, false);
            storage = Utils.setStoragePath(context, sdCardList);
            if (!storage.equalsIgnoreCase("1")) {
                sdCardList = Utils.getStorageDirectories();
                storage = Utils.setStoragePath(context, sdCardList);
                if (!storage.equalsIgnoreCase("1")) {
                    myApp.setGlobal_STORAGE_PATH(Environment.getExternalStorageDirectory().getAbsolutePath());
                }
            }
        } catch (Exception ex) {
            sdCardList = Utils.getStorageDirectories();
            storage = Utils.setStoragePath(context, sdCardList);
            if (!storage.equalsIgnoreCase("1")) {
                myApp.setGlobal_STORAGE_PATH(Environment.getExternalStorageDirectory().getAbsolutePath());
            }
        }
    }

    public static List<String> getSdCardPaths(final Context context, final boolean includePrimaryExternalStorage) throws Exception {
        try {
            final List<String> result = new ArrayList<>();
            final File[] externalCacheDirs;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                externalCacheDirs = context.getExternalCacheDirs();
                if (externalCacheDirs == null || externalCacheDirs.length == 0) {
                    return null;
                }
                if (externalCacheDirs.length == 1) {
                    if (externalCacheDirs[0] == null) {
                        return null;
                    }
                    final String storageState = Environment.getStorageState(externalCacheDirs[0]);
                    if (!Environment.MEDIA_MOUNTED.equals(storageState)) {
                        return null;
                    }
                    if (!includePrimaryExternalStorage && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB && Environment.isExternalStorageEmulated()) {
                        return null;
                    }
                }

                if (includePrimaryExternalStorage || externalCacheDirs.length == 1) {
                    result.add(getRootOfInnerSdCardFolder(externalCacheDirs[0]));
                }
                for (int i = 1; i < externalCacheDirs.length; ++i) {
                    final File file = externalCacheDirs[i];
                    if (file == null) {
                        continue;
                    }
                    final String storageState = Environment.getStorageState(file);
                    if (Environment.MEDIA_MOUNTED.equals(storageState)) {
                        result.add(getRootOfInnerSdCardFolder(externalCacheDirs[i]));
                    }
                }
                if (result.isEmpty()) {
                    return null;
                }
            } else {
                return null;
            }
            return result;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static List<String> getStorageDirectories() {
        final List<String> rv = new ArrayList<>();
        final String rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE");
        if (!TextUtils.isEmpty(rawSecondaryStoragesStr)) {
            final String[] rawSecondaryStorages = rawSecondaryStoragesStr.split(File.pathSeparator);
            Collections.addAll(rv, rawSecondaryStorages);
        }
        return rv;
    }

    private static String getRootOfInnerSdCardFolder(File file) throws Exception {
        try {
            if (file == null) {
                return null;
            }
            final long totalSpace = file.getTotalSpace();
            while (true) {
                final File parentFile = file.getParentFile();
                if (parentFile == null || parentFile.getTotalSpace() != totalSpace) {
                    return file.getAbsolutePath();
                }
                file = parentFile;
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static String setStoragePath(final Context context, List<String> sdCardList) {
        String result = "";
        try {
            PyesApp myApp = (PyesApp) context.getApplicationContext();
            boolean mFlag = false;
            File mCheckDir;
            String mCurrentPath = "";
            if (!sdCardList.isEmpty()) {
                int i = 0;
                while ((i < sdCardList.size()) && (!mFlag)) {
                    if (!Utils.is_Null(sdCardList.get(i))) {
                        mCurrentPath = sdCardList.get(i);
                        mCheckDir = new File(mCurrentPath + Utils.ClientsImagesPath);
                        if (mCheckDir.exists()) {
                            mFlag = true;
                            myApp.setGlobal_STORAGE_PATH(mCurrentPath);
                        }
                    }
                    i++;
                }
                if (!mFlag) {
                    result = "2";
                } else {
                    result = "1";
                }
            } else {
                result = "2";
            }
            return (result);
        } catch (Exception e) {
            return ("2");
        }
    }

    public static boolean is_Null(String m_s) {
        return (m_s == null) || (m_s.equalsIgnoreCase("null")) || ("".equals(m_s)) || (m_s.length() == 0);
    }

    public static ArrayList<HashMap<String, String>> getBranchList(Context context) {
        String sql = "SELECT TYPE, ID ,NAME_AR , ADDRESSE_AR , NAME_EN , ADDRESS_EN , LONGITUDE , LATITUDE FROM BRANCHES_INFO ORDER BY TYPE ,CAST(ID AS INTEGER) ASC";
        String mWherAmI = "0";
        ArrayList<HashMap<String, String>> mainArrayList = new ArrayList<>();
        Cursor qry;
        try {
            final SQLiteMain entry = new SQLiteMain(context);
            entry.open();
            qry = entry.execute_Query(sql);
            qry.moveToFirst();
            int rowCount = qry.getCount();
            if (rowCount > 0) {
                for (int i = 0; i < rowCount; i++) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    mWherAmI = "0";
                    hashMap.put("type", qry.getString(qry.getColumnIndex("TYPE")));
                    mWherAmI = "1";
                    hashMap.put("id", qry.getString(qry.getColumnIndex("ID")));
                    mWherAmI = "2";
                    hashMap.put("name_ar", qry.getString(qry.getColumnIndex("NAME_AR")));
                    mWherAmI = "3";
                    hashMap.put("ADDRESSE_AR", qry.getString(qry.getColumnIndex("ADDRESSE_AR")));
                    mWherAmI = "4";
                    hashMap.put("name_en", qry.getString(qry.getColumnIndex("NAME_EN")));
                    mWherAmI = "5";
                    hashMap.put("address_en", qry.getString(qry.getColumnIndex("ADDRESS_EN")));
                    mWherAmI = "6";
                    hashMap.put("longitude", qry.getString(qry.getColumnIndex("LONGITUDE")));
                    mWherAmI = "7";
                    hashMap.put("latitude", qry.getString(qry.getColumnIndex("LATITUDE")));
                    mWherAmI = "8";
                    mainArrayList.add(hashMap);
                    qry.moveToNext();
                }
            }
            qry.close();
            entry.close();
        } catch (Exception e) {
            Utils.showConfirmationDialog(context, "Utils.getBranchList\n" + mWherAmI + "\n" + e.toString(), context.getResources().getString(R.string.app_name));
            Log.e(Utils.APP_TAG, "Utils.getBranchList\n" + mWherAmI + "\n" + e.toString());
        }
        return mainArrayList;
    }

    public static void addedServices(Context context) {
        PyesApp myApp = (PyesApp) context.getApplicationContext();
        int maxMenu_id = 0;
        String maxMenu_id_s = Utils.getColumnValue(context, "select MAX(CAST(menu_id AS INTEGER)) from MENUS where menu_parent is null or menu_parent='' ");

        if (!is_Null(maxMenu_id_s)) {
            maxMenu_id = Integer.parseInt(maxMenu_id_s);
        }

        String insert_statement = null;

        Utils.runSQL(context, "delete from accounts_services where is_mandatory='1'");
        maxMenu_id++;

        insert_statement =
                "insert into MENUS( COM_ID,MENU_PARENT,MENU_ID,MENU_ORD," +
                        "MENU_NAME_AR,MENU_NAME_EN,MENU_HELP_AR ,MENU_HELP_EN," +
                        "IS_LEAF,SERVICE_CODE,ACTIVITY_TYPE,MENU_ICON)" +
                        "values('" + myApp.getGlobal_Com_Id() + "','','" + maxMenu_id + "','50'," +
                        "'الإعدادات'" + ",'Settings'," + "'بيانات ملفك الشخصي واعدادات أخرى'" + ",'Your Profile and Other Settings'," +
                        "'0','','','settings')";
        Utils.runSQL(context, insert_statement);

        insert_statement =
                "insert into MENUS( COM_ID,MENU_PARENT,MENU_ID,MENU_ORD," +
                        "MENU_NAME_AR,MENU_NAME_EN,MENU_HELP_AR ,MENU_HELP_EN," +
                        "IS_LEAF,SERVICE_CODE,ACTIVITY_TYPE,MENU_ICON)" +
                        "values('" + myApp.getGlobal_Com_Id() + "','" + maxMenu_id + "','" + maxMenu_id + "_1" + "','1'," +
                        "'ملفك الشخصي'" + ",'Profile'," + "'بيانات ملفك الشخصي وحساباتك'" + ",'Your Profile and Your Accounts'," +
                        "'1','" + maxMenu_id + "_1" + "','Profile','profile')";

        Utils.runSQL(context, insert_statement);
        Utils.runSQL(context, "insert into accounts_services(com_id,account_no,service_code,is_mandatory) " +
                "values('" + myApp.getGlobal_Com_Id() + "','" + myApp.getGlobal_User_Account() + "','" + maxMenu_id + "_1" + "','1')");

        insert_statement =
                "insert into MENUS( COM_ID,MENU_PARENT,MENU_ID,MENU_ORD," +
                        "MENU_NAME_AR,MENU_NAME_EN,MENU_HELP_AR ,MENU_HELP_EN," +
                        "IS_LEAF,SERVICE_CODE,ACTIVITY_TYPE,MENU_ICON)" +
                        "values('" + myApp.getGlobal_Com_Id() + "','" + maxMenu_id + "','" + maxMenu_id + "_2" + "','2'," +
                        "'تنزيل الخدمات'" + ",'Download Services'," + "'تنزيل خدمات نظام بيس'" + ",'DownLoad Pyes services'," +
                        "'1','" + maxMenu_id + "_2" + "','DownloadServices','download')";
        Utils.runSQL(context, insert_statement);
        Utils.runSQL(context, "insert into accounts_services(com_id,account_no,service_code,is_mandatory) " +
                "values('" + myApp.getGlobal_Com_Id() + "','" + myApp.getGlobal_User_Account() + "','" + maxMenu_id + "_2" + "','1')");
        insert_statement =
                "insert into MENUS( COM_ID,MENU_PARENT,MENU_ID,MENU_ORD," +
                        "MENU_NAME_AR,MENU_NAME_EN,MENU_HELP_AR ,MENU_HELP_EN," +
                        "IS_LEAF,SERVICE_CODE,ACTIVITY_TYPE,MENU_ICON)" +
                        "values('" + myApp.getGlobal_Com_Id() + "','" + maxMenu_id + "','" + maxMenu_id + "_3" + "','3'," +
                        "'تحديث الخدمات المرتبطه بحسابك'" + ",'Update Your Services Only'," + "'تحديث الخدمات التي لديك صلاحية عليها'" + ",'Update the services you have access to'," +
                        "'1','" + maxMenu_id + "_2" + "','DownloadClientServices','4')";
        Utils.runSQL(context, insert_statement);
        Utils.runSQL(context, "insert into accounts_services(com_id,account_no,service_code,is_mandatory) " +
                "values('" + myApp.getGlobal_Com_Id() + "','" + myApp.getGlobal_User_Account() + "','" + maxMenu_id + "_3" + "','1')");

        insert_statement =
                "insert into MENUS( COM_ID,MENU_PARENT,MENU_ID,MENU_ORD," +
                        "MENU_NAME_AR,MENU_NAME_EN,MENU_HELP_AR ,MENU_HELP_EN," +
                        "IS_LEAF,SERVICE_CODE,ACTIVITY_TYPE,MENU_ICON)" +
                        "values('" + myApp.getGlobal_Com_Id() + "','" + maxMenu_id + "','" + maxMenu_id + "_4" + "','4'," +
                        "'تنزيل بيانات الفروع والوكلاء'" + ",'Download Branch and Agents Info'," + "'تنزيل بيانات الفروع والوكلاء للتعرف على مواقعهم في الخريطه'" + ",'DownLoad Branches and Agent GPS info'," +
                        "'1','" + maxMenu_id + "_2" + "','DownloadBranches','branches')";
        Utils.runSQL(context, insert_statement);
        Utils.runSQL(context, "insert into accounts_services(com_id,account_no,service_code,is_mandatory) " +
                "values('" + myApp.getGlobal_Com_Id() + "','" + myApp.getGlobal_User_Account() + "','" + maxMenu_id + "_4" + "','1')");
        insert_statement =
                "insert into MENUS( COM_ID,MENU_PARENT,MENU_ID,MENU_ORD," +
                        "MENU_NAME_AR,MENU_NAME_EN,MENU_HELP_AR ,MENU_HELP_EN," +
                        "IS_LEAF,SERVICE_CODE,ACTIVITY_TYPE,MENU_ICON)" +
                        "values('" + myApp.getGlobal_Com_Id() + "','" + maxMenu_id + "','" + maxMenu_id + "_5" + "','5'," +
                        "'تنزيل بيانات شركات الاتصالات'" + ",'Download Telecom Info'," + "'تنزيل أو تحديث بيانات شركات الاتصالات '" + ",'DownLoad Or Update Telecom Info.'," +
                        "'1','" + maxMenu_id + "_2" + "','DownloadTelecoms','11')";
        Utils.runSQL(context, insert_statement);
        Utils.runSQL(context, "insert into accounts_services(com_id,account_no,service_code,is_mandatory) " +
                "values('" + myApp.getGlobal_Com_Id() + "','" + myApp.getGlobal_User_Account() + "','" + maxMenu_id + "_5" + "','1')");

        insert_statement =
                "insert into MENUS( COM_ID,MENU_PARENT,MENU_ID,MENU_ORD," +
                        "MENU_NAME_AR,MENU_NAME_EN,MENU_HELP_AR ,MENU_HELP_EN," +
                        "IS_LEAF,SERVICE_CODE,ACTIVITY_TYPE,MENU_ICON)" +
                        "values('" + myApp.getGlobal_Com_Id() + "','" + maxMenu_id + "','" + maxMenu_id + "_6" + "','6'," +
                        "'إضافة جهات الاتصال'" + ",'Add Contact List'," + "'اضافة جهات الاتصال لديك من اجل سرعة اختيار الاسماء وارقام التلفون عند اجراء العمليات '," +
                        "',Add your contacts in order to quickly choose names and phone numbers when performing operations'," +
                        "'1','" + maxMenu_id + "_3" + "','LoadContactList','contact')";
        Utils.runSQL(context, insert_statement);
        Utils.runSQL(context, "insert into accounts_services(com_id,account_no,service_code,is_mandatory) " +
                "values('" + myApp.getGlobal_Com_Id() + "','" + myApp.getGlobal_User_Account() + "','" + maxMenu_id + "_6" + "','1')");

        insert_statement =
                "insert into MENUS( COM_ID,MENU_PARENT,MENU_ID,MENU_ORD," +
                        "MENU_NAME_AR,MENU_NAME_EN,MENU_HELP_AR ,MENU_HELP_EN," +
                        "IS_LEAF,SERVICE_CODE,ACTIVITY_TYPE,MENU_ICON)" +
                        "values('" + myApp.getGlobal_Com_Id() + "','" + maxMenu_id + "','" + maxMenu_id + "_7" + "','7'," +
                        "'حذف المفضلة'" + ",'Del Favorites'," + "'حذف بيانات المفضلة'" + ",'Delete Favorites Data'," +
                        "'1','" + maxMenu_id + "_4" + "','DelFavorites','delfavorites')";
        Utils.runSQL(context, insert_statement);
        Utils.runSQL(context, "insert into accounts_services(com_id,account_no,service_code,is_mandatory) " +
                "values('" + myApp.getGlobal_Com_Id() + "','" + myApp.getGlobal_User_Account() + "','" + maxMenu_id + "_7" + "','1')");

        insert_statement =
                "insert into MENUS( COM_ID,MENU_PARENT,MENU_ID,MENU_ORD," +
                        "MENU_NAME_AR,MENU_NAME_EN,MENU_HELP_AR ,MENU_HELP_EN," +
                        "IS_LEAF,SERVICE_CODE,ACTIVITY_TYPE,MENU_ICON)" +
                        "values('" + myApp.getGlobal_Com_Id() + "','" + maxMenu_id + "','" + maxMenu_id + "_8" + "','8'," +
                        "'حذف الإكمال التلقائي'" + ",'Del AutoComplete'," + "'حذف بيانات قائمة الإكمال التلقائي'" + ",'Delete Data of AutoComplete List'," +
                        "'1','" + maxMenu_id + "_5" + "','DelAutoComplete','autocomplete')";
        Utils.runSQL(context, insert_statement);
        Utils.runSQL(context, "insert into accounts_services(com_id,account_no,service_code,is_mandatory) " +
                "values('" + myApp.getGlobal_Com_Id() + "','" + myApp.getGlobal_User_Account() + "','" + maxMenu_id + "_8" + "','1')");
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        maxMenu_id++;
        insert_statement =
                "insert into MENUS( COM_ID,MENU_PARENT,MENU_ID,MENU_ORD," +
                        "MENU_NAME_AR,MENU_NAME_EN,MENU_HELP_AR ,MENU_HELP_EN," +
                        "IS_LEAF,SERVICE_CODE,ACTIVITY_TYPE,MENU_ICON)" +
                        "values('" + myApp.getGlobal_Com_Id() + "','','" + maxMenu_id + "','51'," +
                        "'من نحن'" + ",'About Us'," + "'التعرف على البنك من خلال صفحة البنك ومواقع فروعه ووكلائة'" + ",'Knowing the bank through web page and the locations of its branches and agents'," +
                        "'0','','','about_us')";
        Utils.runSQL(context, insert_statement);

        insert_statement =
                "insert into MENUS( COM_ID,MENU_PARENT,MENU_ID,MENU_ORD," +
                        "MENU_NAME_AR,MENU_NAME_EN,MENU_HELP_AR ,MENU_HELP_EN," +
                        "IS_LEAF,SERVICE_CODE,ACTIVITY_TYPE,MENU_ICON)" +
                        "values('" + myApp.getGlobal_Com_Id() + "','" + maxMenu_id + "','" + maxMenu_id + "_1" + "','1'," +
                        "'صفحة البنك'" + ",'Web Page'," + "'زيارة صفحة بنك الأمل على الإنترنت'" + ",'Visit Alamal bank web page '," +
                        "'1','" + maxMenu_id + "_1" + "','VisitAMBWebPage','website')";
        Utils.runSQL(context, insert_statement);
        Utils.runSQL(context, "insert into accounts_services(com_id,account_no,service_code,is_mandatory) " +
                "values('" + myApp.getGlobal_Com_Id() + "','" + myApp.getGlobal_User_Account() + "','" + maxMenu_id + "_1" + "','1')");

        insert_statement =
                "insert into MENUS( COM_ID,MENU_PARENT,MENU_ID,MENU_ORD," +
                        "MENU_NAME_AR,MENU_NAME_EN,MENU_HELP_AR ,MENU_HELP_EN," +
                        "IS_LEAF,SERVICE_CODE,ACTIVITY_TYPE,MENU_ICON)" +
                        "values('" + myApp.getGlobal_Com_Id() + "','" + maxMenu_id + "','" + maxMenu_id + "_2" + "','2'," +
                        "'فيس بوك'" + ",'Facebook'," + "'زيارة صفحة البنك على الفيس بوك'" + ",'Visit Alamal bank Facebook page'," +
                        "'1','" + maxMenu_id + "_2" + "','Facebook','facebook')";
        Utils.runSQL(context, insert_statement);
        Utils.runSQL(context, "insert into accounts_services(com_id,account_no,service_code,is_mandatory) " +
                "values('" + myApp.getGlobal_Com_Id() + "','" + myApp.getGlobal_User_Account() + "','" + maxMenu_id + "_2" + "','1')");

        insert_statement =
                "insert into MENUS( COM_ID,MENU_PARENT,MENU_ID,MENU_ORD," +
                        "MENU_NAME_AR,MENU_NAME_EN,MENU_HELP_AR ,MENU_HELP_EN," +
                        "IS_LEAF,SERVICE_CODE,ACTIVITY_TYPE,MENU_ICON)" +
                        "values('" + myApp.getGlobal_Com_Id() + "','" + maxMenu_id + "','" + maxMenu_id + "_3" + "','3'," +
                        "'فروعنا'" + ",'Our Branches'," + "'تعرف على فروعنا وموقعها على الخريطة وتحديد اقرب طريق اليها'" + ",'Learn about our branches and their location on the map and determine the closest road to it'," +
                        "'1','" + maxMenu_id + "_3" + "','LoadBranches','branches')";
        Utils.runSQL(context, insert_statement);
        Utils.runSQL(context, "insert into accounts_services(com_id,account_no,service_code,is_mandatory) " +
                "values('" + myApp.getGlobal_Com_Id() + "','" + myApp.getGlobal_User_Account() + "','" + maxMenu_id + "_3" + "','1')");
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        maxMenu_id++;
        insert_statement =
                "insert into MENUS( COM_ID,MENU_PARENT,MENU_ID,MENU_ORD," +
                        "MENU_NAME_AR,MENU_NAME_EN,MENU_HELP_AR ,MENU_HELP_EN," +
                        "IS_LEAF,SERVICE_CODE,ACTIVITY_TYPE,MENU_ICON)" +
                        "values('" + myApp.getGlobal_Com_Id() + "','','" + maxMenu_id + "','52'," +
                        "'مساعدة'" + ",'Help'," + "'التعرف على خدمات النظام وطريقة استخدامها'" + ",'Learn about app services and how to use them'," +
                        "'0','" + maxMenu_id + "','Help','help')";
        Utils.runSQL(context, insert_statement);
        Utils.runSQL(context, "insert into accounts_services(com_id,account_no,service_code,is_mandatory) " +
                "values('" + myApp.getGlobal_Com_Id() + "','" + myApp.getGlobal_User_Account() + "','" + maxMenu_id + "','1')");
    }

    public static ArrayList<String> getArrayListFromDB(final Activity myActivity, String mSql) {
        Cursor query;
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.clear();
        try {
            final SQLiteMain entry = new SQLiteMain(myActivity);
            entry.open();
            query = entry.execute_Query(mSql);
            query.moveToFirst();
            int rowCount = query.getCount();
            if (rowCount > 0) {
                for (int i = 1; i <= rowCount; i++) {
                    arrayList.add(query.getString(0));
                    if (!query.isLast()) {
                        query.moveToNext();
                    }
                }
            }
            entry.close();
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());
        }
        return arrayList;
    }

    public static ArrayList<NameValuePair> getArrayListFromDBNameValuePair(final Activity myActivity, String mSql) {
        Cursor query;
        ArrayList<NameValuePair> arrayList = new ArrayList<>();
        arrayList.clear();
        try {
            final SQLiteMain entry = new SQLiteMain(myActivity);
            entry.open();
            query = entry.execute_Query(mSql);
            query.moveToFirst();
            int rowCount = query.getCount();
            if (rowCount > 0) {
                for (int i = 0; i < rowCount; i++) {
                    arrayList.add(new BasicNameValuePair(query.getString(0), query.getString(1)));
                    if (!query.isLast()) {
                        query.moveToNext();
                    }
                }
            }
            entry.close();
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());
        }
        return arrayList;
    }

    public static List<ListViewBean> getAccountInfo(Context context, String whereClause) {
        ArrayList<ListViewBean> arrayListParse;
        arrayListParse = new ArrayList<>();
        String whereAmI = "0";
        arrayListParse.clear();
        String sql_statement;
        if (!Utils.is_Null(whereClause)) {

            sql_statement = "SELECT ACCOUNT_NO , ACCOUNT_TYPE ," +
                    " ACCOUNT_CURR , ACCOUNT_CLASSIFICATION ACCOUNT_CLASS ," +
                    " ACCOUNT_NAME , IS_DEFAULT ACCOUNT_DEFAULT , MOBILE_NO ACCOUNT_MOBILE , ACCOUNT_BALANCE " +
                    " FROM ACCOUNTS " + whereClause +
                    " ORDER BY _ID";
        } else {

            sql_statement = "SELECT ACCOUNT_NO , ACCOUNT_TYPE ," +
                    " ACCOUNT_CURR , ACCOUNT_CLASSIFICATION ACCOUNT_CLASS ," +
                    " ACCOUNT_NAME , IS_DEFAULT ACCOUNT_DEFAULT , MOBILE_NO ACCOUNT_MOBILE , ACCOUNT_BALANCE " +
                    " FROM ACCOUNTS " +
                    " ORDER BY _ID";
        }
        if (!Utils.is_Null(sql_statement)) {
            Cursor query;
            try {
                String old_value = null, new_value = null;
                final SQLiteMain entry = new SQLiteMain(context);
                entry.open();
                query = entry.execute_Query(sql_statement);
                query.moveToFirst();
                int rowCount = query.getCount();
                //  whereAmI=whereAmI+" rowCount "+rowCount;
                if (rowCount > 0) {
                    for (int i = 0; i < rowCount; i++) {
                        ListViewBean objBean = new ListViewBean();
                        objBean.setAccount_no(query.getString(query.getColumnIndex("ACCOUNT_NO")));
                        whereAmI = whereAmI + " 2 ";
                        objBean.setAccount_name(query.getString(query.getColumnIndex("ACCOUNT_NAME")));
                        whereAmI = whereAmI + " 3 ";
                        old_value = query.getString(query.getColumnIndex("ACCOUNT_TYPE"));
                        switch (old_value) {
                            case "PA":
                                new_value = context.getResources().getString(R.string.PA);
                                break;
                            case "CA":
                                new_value = context.getResources().getString(R.string.CA);
                                break;
                            case "SA":
                                new_value = context.getResources().getString(R.string.SA);
                                break;
                            case "DA":
                                new_value = context.getResources().getString(R.string.DA);
                                break;
                            case "LA":
                                new_value = context.getResources().getString(R.string.LA);
                                break;
                            default:
                                new_value = old_value;
                        }
                        whereAmI = whereAmI + " 4 ";
                        objBean.setAccount_type(new_value);
                        whereAmI = whereAmI + " 5 ";
                        old_value = query.getString(query.getColumnIndex("ACCOUNT_CURR"));
                        switch (old_value) {
                            case "1":
                                new_value = context.getResources().getString(R.string.C1);
                                break;
                            case "2":
                                new_value = context.getResources().getString(R.string.C2);
                                break;
                            case "3":
                                new_value = context.getResources().getString(R.string.C3);
                                break;
                            case "4":
                                new_value = context.getResources().getString(R.string.C4);
                                break;
                            case "5":
                                new_value = context.getResources().getString(R.string.C5);
                                break;
                            default:
                                new_value = old_value;

                        }
                        whereAmI = whereAmI + " 6 ";
                        objBean.setAccount_curr(new_value);
                        whereAmI = whereAmI + " 7 ";
                        objBean.setAccount_class(query.getString(query.getColumnIndex("ACCOUNT_CLASS")));
                        whereAmI = whereAmI + " 8 ";
                        old_value = query.getString(query.getColumnIndex("ACCOUNT_DEFAULT"));
                        switch (old_value) {
                            case "0":
                                new_value = context.getResources().getString(R.string.no);
                                break;
                            case "1":
                                new_value = context.getResources().getString(R.string.yes);
                                break;
                            default:
                                new_value = old_value;

                        }
                        whereAmI = whereAmI + " 9 ";
                        objBean.setAccount_default(new_value);
                        whereAmI = whereAmI + " 10 ";
                        objBean.setAccount_mobile(query.getString(query.getColumnIndex("ACCOUNT_MOBILE")));
                        whereAmI = whereAmI + " 11 ";
                        objBean.setAccount_balance(query.getString(query.getColumnIndex("ACCOUNT_BALANCE")));
                        whereAmI = whereAmI + " 12 ";
                        arrayListParse.add(objBean);
                        query.moveToNext();
                    }
                } else {
                    arrayListParse.clear();
                }
                query.close();
                entry.close();
            } catch (Exception e) {
                e.printStackTrace();
                Utils.showConfirmationDialog(context, "Utils.getAccountInfo\n Where Am I " + whereAmI + "\n" + e.toString(), context.getResources().getString(R.string.app_name));
            }
        } else {
            arrayListParse.clear();
        }
        return arrayListParse;
    }

    public static ArrayList<ListViewBean> getListViewBeanFromDB(Context context, String type, String column_value, String language) {
        int iconId;
        String whereIsError = "0";
        PyesApp myApp = (PyesApp) context.getApplicationContext();
        ArrayList<ListViewBean> arrayListParse;
        arrayListParse = new ArrayList<>();
        arrayListParse.clear();
        String sql_statement = null;
        if (type.equalsIgnoreCase("MENU")) {
            sql_statement =
                    "SELECT  MENU_PARENT PARENT,MENU_ID CHILD," +
                            "CASE '" + language.toUpperCase() + "' WHEN 'AR' THEN MENU_ID||' - '||MENU_NAME_AR " +
                            "ELSE MENU_ID||' - '||MENU_NAME_EN " +
                            "END MENU_NAME ," +
                            "CASE '" + language.toUpperCase() + "' WHEN 'AR' THEN MENU_HELP_AR " +
                            "ELSE MENU_HELP_EN " +
                            "END MENU_HELP ," +
                            "ACTIVITY_TYPE ACTIVITY_TYPE ," +
                            "IS_LEAF IS_LEAF," +
                            "SERVICE_CODE SERVICE_CODE," +
                            "'ic_'||MENU_ICON MENU_ICON " +
                            "FROM MENUS " +
                            "WHERE COM_ID='" + myApp.getGlobal_Com_Id() + "' " +
                            " AND MENU_PARENT='" + column_value + "' " +
                            "ORDER BY CAST(MENU_PARENT AS INTEGER),CAST(MENU_ID AS INTEGER)";
        } else if (type.equalsIgnoreCase("Favorites")) {
            sql_statement =
                    "SELECT m.MENU_ID PARENT,m.SERVICE_CODE CHILD," +
                            "CASE '" + language.toUpperCase() + "' WHEN 'AR' THEN m.MENU_ID||' - '||m.MENU_NAME_AR " +
                            "ELSE m.MENU_ID||' - '||m.MENU_NAME_EN " +
                            "END MENU_NAME ," +
                            "CASE '" + language.toUpperCase() + "' WHEN 'AR' THEN m.MENU_HELP_AR " +
                            "ELSE m.MENU_HELP_EN " +
                            "END MENU_HELP ," +
                            "m.ACTIVITY_TYPE ACTIVITY_TYPE," +
                            "'1' IS_LEAF," +
                            "'ic_'||m.MENU_ICON MENU_ICON " +
                            "FROM MENUS as m," +
                            "favorite_services as f," +
                            "accounts_services as s " +
                            " WHERE f.PARENT_CODE=m.MENU_ID AND f.SERVICE_CODE=m.SERVICE_CODE" +
                            " AND s.SERVICE_CODE = m.SERVICE_CODE AND s.account_no='" + myApp.getGlobal_User_Account() + "'" +
                            " ORDER BY M.MENU_ID,M.SERVICE_CODE,CAST(M.MENU_ORD AS INTEGER)";
        }
        if (sql_statement != null) {
            Cursor query;
            try {
                final SQLiteMain entry = new SQLiteMain(context);
                entry.open();
                query = entry.execute_Query(sql_statement);
                whereIsError = "1";
                query.moveToFirst();
                int rowCount = query.getCount();
                if (rowCount > 0) {
                    for (int i = 0; i < rowCount; i++) {
                        ListViewBean objBean = new ListViewBean();
                        whereIsError = "2";
                        objBean.setParentCode(query.getString(query.getColumnIndex("PARENT")));
                        whereIsError = "3";
                        objBean.setCode(query.getString(query.getColumnIndex("CHILD")));
                        whereIsError = "4";
                        //Log.e(Utils.APP_TAG,"Type: "+type+" Menu Name: "+query.getString(query.getColumnIndex("MENU_NAME"))+" Menu Icon: "+query.getString(query.getColumnIndex("MENU_ICON")));

                        iconId = context.getResources().getIdentifier(query.getString(query.getColumnIndex("MENU_ICON")), "drawable", context.getPackageName());
                        Log.e("latef",query.getString(query.getColumnIndex("MENU_ICON")));
                        if (iconId == 0) {
                            iconId = context.getResources().getIdentifier("go", "drawable", context.getPackageName());
                        }
                        objBean.setIcon(iconId);
                        whereIsError = "5";
                        objBean.setTitle(query.getString(query.getColumnIndex("MENU_NAME")));
                        whereIsError = "6";
                        objBean.setDescription(query.getString(query.getColumnIndex("MENU_HELP")));
                        whereIsError = "7";
                        objBean.setActivityType(query.getString(query.getColumnIndex("ACTIVITY_TYPE")));
                        whereIsError = "8";
                        objBean.setIsLeave(query.getString(query.getColumnIndex("IS_LEAF")));
                        whereIsError = "9";
                        arrayListParse.add(objBean);
                        whereIsError = "10";
                        query.moveToNext();
                    }
                } else {
                    arrayListParse.clear();
                }
                query.close();
                entry.close();

            } catch (Exception e) {
                e.printStackTrace();
                Utils.showConfirmationDialog(context, "Utils.getListViewBeanFromDB\n" + whereIsError + "\n" + e.toString(), context.getResources().getString(R.string.app_name));
                arrayListParse.clear();
            }
        } else {
            showToast(context, context.getResources().getString(R.string.no_services));
        }
        return arrayListParse;
    }

    public static boolean isMAPServiceOK(Activity act) {
        final int ERROR_DIALOG_REQUEST = 9001;
        try {
            int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(act);
            if (available == ConnectionResult.SUCCESS) {
                return true;
            } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
                Dialog dialoge = GoogleApiAvailability.getInstance().getErrorDialog(act, available, ERROR_DIALOG_REQUEST);
                dialoge.show();
            } else {
                Utils.showConfirmationDialog(act, act.getResources().getString(R.string.google_maps_error), act.getResources().getString(R.string.app_name));
            }
            return false;
        } catch (Exception e) {
            Utils.showConfirmationDialog(act, "Utils.isMapServiceOK\n" + e.toString(), act.getResources().getString(R.string.app_name));
            return false;
        }
    }

    public static String readXMLIntoDBFromURL(Context context, String tagName, String XMLVersion) {
        String result;
        String xmlFilename = Utils.getColumnValue(context, "select XMLURL from home");
        xmlFilename = xmlFilename + "services.xml";
        try {
            URL url = new URL(xmlFilename);
            URLConnection con = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            String fullStr = "";
            while ((inputLine = reader.readLine()) != null) {
                fullStr = fullStr.concat(inputLine + "\n");
            }
            InputStream istream = url.openStream();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(istream);
            doc.getDocumentElement().normalize();
            listXml = doc.getElementsByTagName(tagName);

            result = insertListIntoDatabase(context, listXml);
            if (result.equalsIgnoreCase("1")) {
                return (XMLVersion);
            } else {
                return ("-1:" + result);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ("-1");
        }


    }

    public static String readXMLIntoDatabase(Context context, String tagName) {
        String mainResult;
        String xmlFilename = "xml/services.xml";
        try {
            DocumentBuilderFactory docbf = DocumentBuilderFactory.newInstance();
            docbf.setNamespaceAware(false);
            docbf.setValidating(false);
            DocumentBuilder docb = docbf.newDocumentBuilder();
            Document doc = docb.parse(context.getResources().getAssets().open(xmlFilename));
            listXml = doc.getElementsByTagName(tagName);
            insertListIntoDatabase(context, listXml);
            mainResult = "1";
        } catch (Exception e) {
            mainResult = e.toString();
        }
        if (mainResult.equalsIgnoreCase("1")) {
            return "1";
        } else {
            return mainResult;
        }
    }

    private static String insertListIntoDatabase(Context context, NodeList listxml) {
        int clientCount = listxml.getLength();
        boolean flag;
        String nodeContentValue = "";
        String result = "1";
        try {
            for (int i = 0; i < clientCount; i++) {
                Node node = listxml.item(i);
                nodeContentValue = node.getTextContent();
                flag = runSQL(context, nodeContentValue);
                if (!flag) {
                    Log.e(Utils.APP_TAG, "Flag=false : node content=" + nodeContentValue);
                    return ("Flag=false : node content=" + nodeContentValue);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ("Insert into DB Error :Count=" + clientCount + " content=" + nodeContentValue);
        }
    }


    public static void addToLastEntry(Context context, String p_added_value) {
        try {
            Utils.runSQL(context, "insert into last_entry (type_id,last_entry) select '2','" + p_added_value + "' where not exists (select 0 from  last_entry where type_id='2' and last_entry='" + p_added_value + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setBankBackgroundLogo(Activity mActivity, String mBank_Id) {
        Log.e(Utils.APP_TAG, mActivity.getLocalClassName().toString() + " - " + mBank_Id);
        ImageView im_blogo = (ImageView) Utils.populateView(mActivity, "ImageView", R.id.im_blogo, 0);
        if (!Utils.is_Null(mBank_Id)) {
            if (Integer.parseInt(mBank_Id) == 2) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    im_blogo.setBackground(mActivity.getResources().getDrawable(R.drawable.ic_blogo_2));
                } else {
                    im_blogo.setImageResource(R.drawable.ic_blogo_2);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    im_blogo.setBackground(mActivity.getResources().getDrawable(R.drawable.ic_blogo_1));
                } else {
                    im_blogo.setImageResource(R.drawable.ic_blogo_1);
                }
            }
        }
    }

    public static boolean runSQL(Context context, String SQLSTAT) {
        try {
            final SQLiteMain entry = new SQLiteMain(context);
            entry.open();
            entry.execute_SQL(SQLSTAT);
            entry.close();
            return (true);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Utils.APP_TAG, "RunSql:" + e.toString());
            return (false);
        }
    }

    public static boolean isAPPCodeCorrect(Context context, String accountNo, String appPart) {
        boolean flag = false;
        String rs;
        if (appPart.equalsIgnoreCase("PART1")) {
            rs = Utils.getColumnValue(context, "select appcodepart1 from home");
            flag = !Utils.is_Null(rs);
        } else if (appPart.equalsIgnoreCase("PART2")) {
            rs = Utils.getColumnValue(context, "select appcodepart2 from home");
            flag = !Utils.is_Null(rs);
        }
        return flag;
    }

    public static boolean isAppActive(Context context) {
        boolean flag;
        String rs = Utils.getColumnValue(context, "select active_stop from home");
        if (rs != null) {
            flag = rs.equals("1");
        } else {
            flag = true;
        }
        return flag;
    }

    public static void urlSet(Context context) {
        PyesApp myapp = (PyesApp) context.getApplicationContext();
        myapp.setGlobal_S(context.getResources().getString(R.string.m_s));
        myapp.setGlobal_H(context.getResources().getString(R.string.m_h));
        myapp.setGlobal_P(context.getResources().getString(R.string.m_p));
        myapp.setGlobal_U(context.getResources().getString(R.string.m_u));
        myapp.setGlobal_A(context.getResources().getString(R.string.m_a));
        myapp.setGlobal_X(context.getResources().getString(R.string.m_x));
    }

    public static String sGet(Context context) {
        return ((PyesApp) context.getApplicationContext()).getGlobal_S();
    }

    public static String hGet(Context context) {
        return ((PyesApp) context.getApplicationContext()).getGlobal_H();
    }

    public static String pGet(Context context) {
        return ((PyesApp) context.getApplicationContext()).getGlobal_P();
    }

    public static String uGet(Context context) {
        return ((PyesApp) context.getApplicationContext()).getGlobal_U();
    }
    public static String aGet(Context context) {
        return ((PyesApp) context.getApplicationContext()).getGlobal_A();
    }
    public static String xGet(Context context) {
        return ((PyesApp) context.getApplicationContext()).getGlobal_X();
    }


    public static void call_MainMenu(Activity activity) {
          DataUser_SharedPreferences shar;
         shar=new DataUser_SharedPreferences(activity);
        PyesApp myApp = ((PyesApp) activity.getApplication());
        Spinner sp_language = activity.findViewById(R.id.sp_language);
        EditText ed_login_password = activity.findViewById(R.id.ed_login_password);
        String m_language = sp_language.getItemAtPosition(sp_language.getSelectedItemPosition()).toString();
        if (m_language.equalsIgnoreCase("English")) {
            m_language = "en";
        } else {
            m_language = "ar";
        }
        myApp.setGlobal_Lang(m_language);
        ///
        String mdevice_SERIAL = Utils.getPlayerId(myApp.getGlobal_User_Account());//Utils.getDeviceSerial(activity);

        String strDate = Utils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");

        String m_AccountNo = Utils.getColumnValue(activity, "select account_no from accounts where is_default='1'");
        String m_UserName = Utils.getColumnValue(activity, "select client_name from home");
        String m_UserPassword = Utils.getColumnValue(activity, "select pwd from home");
        String m_RegDeviceSerial = Utils.getColumnValue(activity, "select device_serial from home");
        String m_LastLogin = Utils.getColumnValue(activity, "select lastlogin from home");

        if ((m_UserName != null) && (m_UserPassword != null) && (m_RegDeviceSerial != null)) {
            myApp.setGlobal_User_Account(m_AccountNo);
            myApp.setGlobal_User_name(m_UserName);
            myApp.setGlobal_Password(m_UserPassword);
            myApp.setGlobal_LastLogin(m_LastLogin);
            if (m_RegDeviceSerial.equals(mdevice_SERIAL) &&
                    Utils.isAppActive(activity) &&
                    Utils.isAPPCodeCorrect(activity, myApp.getGlobal_User_Account(), "PART1") &&
                    Utils.isAPPCodeCorrect(activity, myApp.getGlobal_User_Account(), "PART2")) {
                ed_login_password.setText("");
                Utils.runSQL(activity, "update home set lastlogin='" + strDate + "'");
                Intent intent = new Intent();
                intent.setClassName("dev.alamalbank.amb", "dev.alamalbank.amb.Main_Activity");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shar.setLOGIN(m_UserName);
                activity.startActivity(intent);
            } else {
                Utils.showConfirmationDialog(activity, activity.getResources().getString(R.string.login_license_error), activity.getResources().getString(R.string.app_name));
            }
        } else {
            Utils.showConfirmationDialog(activity, activity.getResources().getString(R.string.login_license_error), activity.getResources().getString(R.string.app_name));
        }
    }

    public static String encoding(String mString) {
        String rs;
        try {
            rs = (mString != null) ? new String(Base64.encode(mString.getBytes(), Base64.NO_WRAP)) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return rs;
    }

    public static String decoding(String mString) {
        String rs;
        try {
            rs = (mString != null) ? new String(Base64.decode(mString, Base64.NO_WRAP)) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return rs;
    }

    public static boolean check_Password_Validation(String password) {
        //TODO:Delete comment if Bank Required Complex Password
        /*if ((!Utils.is_Null(password)) && (password.length() >= 4) && (password.length() <= 8)) {
            Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);
            return hasLetter.find() && hasDigit.find() && hasSpecial.find();
        } else
            return false;

         */


        return true;
    }

    public static String getCurrentDateTime(String mFormat) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(mFormat, Locale.ENGLISH);
        return Utils.convertArDigitToEn(sdf.format(c.getTime()));
    }


    public static void permissionNeededDialog(final Context context, String msg, String title,
                                              final String[] permissions, final int permissionCode) {
        final Dialog myDialog = new Dialog(context);
        final Typeface myFont = Typeface.createFromAsset(context.getAssets(), "font/jannat_bold.otf");
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
                ActivityCompat.requestPermissions((Activity) context,
                        permissions, permissionCode);
                myDialog.cancel();
                myDialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PyesApp myApp = ((PyesApp) context.getApplicationContext());
                myApp.setGlobal_OperationONOFF(true);
                myDialog.cancel();
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public static AlertDialog downloadScan(final Context context, String message, String title) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(context);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(context.getResources().getString(R.string.okey), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Utils.isInternetConnected(context)) {
                    Uri uri = Uri.parse(Utils.DOWNLOAD_SCAN);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        context.startActivity(intent);
                    } catch (ActivityNotFoundException anfe) {
                        anfe.printStackTrace();
                    }
                } else {
                    Utils.showConfirmationDialog(context, context.getResources().getString(R.string.internet_error), context.getResources().getString(R.string.app_name));
                }
            }
        });
        downloadDialog.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                dialogInterface.dismiss();
            }
        });
        return downloadDialog.show();
    }

    public static void runService(Context context, String account_no, String actionType, String
            title, String detailServiceCode, boolean enableFavorite, String language) {
        if ((actionType.equalsIgnoreCase("FORM")) || (actionType.equalsIgnoreCase("REPORT"))) {
            String isServiceFound = Utils.getColumnValue(context,
                    "SELECT  COUNT(*) FROM accounts_services as a,SERVICE_COL as d " +
                            "WHERE a.account_no='" + account_no + "' and d.SERVICE_CODE=a.SERVICE_CODE and d.SERVICE_CODE='" + detailServiceCode + "'");

            if ((!Utils.is_Null(isServiceFound)) && (Integer.parseInt(isServiceFound) > 0)) {
                if (actionType.equalsIgnoreCase("FORM")) {
                    Utils.runIntentWithExtra(context, "SendService", detailServiceCode, title, enableFavorite);
                } else if (actionType.equalsIgnoreCase("REPORT")) {
                    Utils.runIntentWithExtra(context, "TransactionReport", detailServiceCode, title, enableFavorite);
                }
            } else { // هذا الذي تم اضافته
                isServiceFound = Utils.getColumnValue(context,
                        "SELECT  COUNT(*) FROM accounts_services as a " +
                                "WHERE a.account_no='" + account_no + "' and a.SERVICE_CODE='" + detailServiceCode + "'");
                Log.e("check",isServiceFound+"   :   "+"SELECT  COUNT(*) FROM accounts_services as a " +
                        "WHERE a.account_no='" + account_no + "' and a.SERVICE_CODE='" + detailServiceCode + "'");
                if ((!Utils.is_Null(isServiceFound)) && (Integer.parseInt(isServiceFound) > 0)) {
                    if (actionType.equalsIgnoreCase("FORM")) {
                        Utils.runIntentWithExtra(context, "SendService", detailServiceCode, title, enableFavorite);
                    } else if (actionType.equalsIgnoreCase("REPORT")) {
                        Utils.runIntentWithExtra(context, "TransactionReport", detailServiceCode, title, enableFavorite);
                    }
                } else {

                    Utils.showToast(context, "runService" + "\n" + context.getResources().getString(R.string.service_permission_needed));
                }
            }
        }
        else if (!actionType.isEmpty()) {
            boolean flag = false;
            boolean runFlag;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Utils.hasPermissions(context, Utils.NET_PERMISSION_CODE, Utils.NET_PERMISSIONS)) {
                    Utils.requestPermission(context, Utils.NET_PERMISSION_CODE);
                } else {
                    flag = true;
                }
            } else {
                flag = true;
            }
            //TODO : Test all downloading
            switch (actionType) {
                case "DownloadServices":
                    if ((Utils.isInternetConnected(context))) {
                        Utils.checkAppUpToDate(context, "2", null, true);
                    } else {
                        Utils.showConfirmationDialog(context, context.getResources().getString(R.string.web_access_error), context.getResources().getString(R.string.app_name));
                    }
                    break;
                case "DownloadClientServices":
                    if ((Utils.isInternetConnected(context))) {
                        Utils.checkAppUpToDate(context, "6", "2", true);
                    } else {
                        Utils.showConfirmationDialog(context, context.getResources().getString(R.string.web_access_error), context.getResources().getString(R.string.app_name));
                    }
                    break;
                case "DownloadBranches":
                    if ((Utils.isInternetConnected(context))) {
                        Utils.checkAppUpToDate(context, "4", null, true);
                    } else {
                        Utils.showConfirmationDialog(context, context.getResources().getString(R.string.web_access_error), context.getResources().getString(R.string.app_name));
                    }
                    break;
                case "DownloadTelecoms":
                    if ((Utils.isInternetConnected(context))) {
                        Utils.checkAppUpToDate(context, "5", null, true);
                    } else {
                        Utils.showConfirmationDialog(context, context.getResources().getString(R.string.web_access_error), context.getResources().getString(R.string.app_name));
                    }
                    break;
                case "LoadContactList":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!Utils.hasPermissions(context, Utils.CONTACTS_PERMISSION_CODE, Utils.CONTACTS_PERMISSIONS)) {
                            Utils.requestPermission(context, Utils.CONTACTS_PERMISSION_CODE);
                        } else {
                            new Utils.runFunctionAsync(context, null, null, null, null, Utils.LOAD_CONTACT_LIST, true).execute();
                        }
                    } else {
                        new Utils.runFunctionAsync(context, null, null, null, null, Utils.LOAD_CONTACT_LIST, true).execute();
                    }
                    break;
                case "DelFavorites":
                    runFlag = Utils.runSQL(context, "delete from favorite_services");
                    if (runFlag) {
                        Utils.showToast(context, context.getResources().getString(R.string.delete_success));
                    } else {
                        Utils.showToast(context, context.getResources().getString(R.string.delete_not_success));
                    }
                    break;
                case "DelAutoComplete":
                    runFlag = Utils.runSQL(context, "delete from last_entry");
                    if (runFlag) {
                        Utils.showToast(context, context.getResources().getString(R.string.delete_success));
                    } else {
                        Utils.showToast(context, context.getResources().getString(R.string.delete_not_success));
                    }
                    break;
                case "VisitAMBWebPage":
                    if (flag) {
                        if ((Utils.isInternetConnected(context))) {
                            Utils.runIntent(context, "AMBWebPage", language);
                        } else {
                            Utils.showConfirmationDialog(context, context.getResources().getString(R.string.web_access_error), context.getResources().getString(R.string.app_name));
                        }
                    }
                    break;
                case "Help":
                    if (flag) {
                        if ((Utils.isInternetConnected(context))) {
                            Utils.runIntent(context, "AppWebPAGE", language);
                        } else {
                            Utils.showConfirmationDialog(context, context.getResources().getString(R.string.web_access_error), context.getResources().getString(R.string.app_name));
                        }
                    }
                    break;
                case "Facebook":
                    if (flag) {
                        if ((Utils.isInternetConnected(context))) {
                            Utils.runIntent(context, "Facebook", language);
                        } else {
                            Utils.showConfirmationDialog(context, context.getResources().getString(R.string.web_access_error), context.getResources().getString(R.string.app_name));
                        }
                    }
                    break;
                case "LoadBranches":
                    if (flag) {
                        if (Utils.isMAPServiceOK((Activity) context)) {
                            try {
                                new Utils.runFunctionAsync(context, null, null, null, null, Utils.LOAD_BRANCHES, true).execute();
                            } catch (Exception e) {
                                Log.e(Utils.APP_TAG, e.toString());
                                showConfirmationDialog(context, "LoadBranches \n" + e.toString(), context.getResources().getString(R.string.app_name));
                            }
                        } else {
                            Utils.showToast(context, "No Maps Services");
                        }
                    }
                    break;
                default:
                    //Utils.showToast(context, context.getResources().getString(R.string.service_permission_needed));
                    Utils.runIntentWithExtra(context, actionType.toUpperCase(), detailServiceCode, title, enableFavorite);
            }
        } else {
            Utils.showToast(context, context.getResources().getString(R.string.service_permission_needed));
        }
    }

    public static void runIntent(Context context, String intentName, String lang) {
        String url;
        Intent intent;
        if (intentName.equalsIgnoreCase("AMBWebPage")) {
            url = Utils.getColumnValue(context, "select COMPANY_WEBSITE from home");
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } else if (intentName.equalsIgnoreCase("Facebook")) {
            url = Utils.getFacebookPageURL(context);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } else if (intentName.equalsIgnoreCase("AppWebPAGE")) {
            url = "http://pyes-ye.com/" + lang;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } else if (intentName.equalsIgnoreCase("Policies")) {
            url = "http://pyes-ye.com/" + lang + "/pyes/policies";
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } else {
            intent = new Intent(context.getPackageName() + "." + intentName.toUpperCase());
            context.startActivity(intent);
        }
    }

    public static void runIntentWithExtra(Context context, String intentName, String
            serviceCode, String title, boolean enableFavorite) {
        Intent intent;
        try {
            Log.e(context.getPackageName() + "." + intentName.toUpperCase(),serviceCode+" "+title+" "+enableFavorite);
            intent = new Intent(context.getPackageName() + "." + intentName.toUpperCase());
            intent.putExtra("service_code", serviceCode);
            intent.putExtra("title", title);
            intent.putExtra("enableFavorite", enableFavorite);

            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showConfirmationDialog(context, "Utils.runIntentWithExtra\n" + context.getResources().getString(R.string.no_services) + ":" + serviceCode, context.getResources().getString(R.string.app_name));
        }

    }


    public static void showConfirmationDialog(Context context, String msg, String title) {
        final Dialog myDialog = new Dialog(context);
        final Typeface myFont = Typeface.createFromAsset(context.getAssets(), "font/jannat_bold.otf");
        final PyesApp myApp = ((PyesApp) context.getApplicationContext());
        myApp.setGlobal_OperationONOFF(true);
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
        btnCancel.setVisibility(View.GONE);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public static void showToast(Context context, String msg) {
        Typeface myFont = Typeface.createFromAsset(context.getAssets(), "font/jannat_bold.otf");
        Toast toast = new Toast(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast, null);
        TextView text = (TextView) view.findViewById(R.id.tv_toast_message);
        text.setTypeface(myFont);
        text.setText(msg);
        toast.setView(view);
        //toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void insertTransactionLog(Context context, String sendMSG, String
            logMSG, String lastSendCMD, boolean insertFlag) {
        String strDate = Utils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
        Utils.runSQL(context, "insert into transactions (datetime ,message ) values('" + strDate + "','" + sendMSG + "')");
        if (insertFlag) {
            Utils.runSQL(context, "insert into lastsendcmd (datetime ,message ) values('" + strDate + "','" + lastSendCMD + "')");
        }
        Utils.runSQL(context, "insert into outbox(datetime ,message  ) values('" + strDate + "','" + logMSG + "')");
    }

    public static boolean isHasMultiSim(Context context) {
        boolean flag = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            SubscriptionManager subs = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            if (subs != null) {
                if (subs.getActiveSubscriptionInfoCountMax() > 1) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String sms_SEND(Context context, String m_SMSProvider_Code, String m_Msg) {
        String SMS_SENT = "SMS_SENT";
        String SMS_DELIVERED = "SMS_DELIVERED";

        final PyesApp myApp = ((PyesApp) context.getApplicationContext());
        try {
            context.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent arg1) {
                    if (getResultCode() == Activity.RESULT_OK) {
                        mRS = context.getResources().getString(R.string.sms_send_complete);
                    } else {
                        mRS = context.getResources().getString(R.string.sms_send_not_complete);
                        myApp.setGlobal_OperationONOFF(true);
                    }
                    Utils.showToast(context, mRS);
                }
            }, new IntentFilter(SMS_SENT));
            context.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (getResultCode() == Activity.RESULT_OK) {
                        mRS = context.getResources().getString(R.string.sms_delivered_complete);
                    } else {
                        mRS = context.getResources().getString(R.string.sms_send_not_complete);
                    }
                    Utils.showToast(context, mRS);
                }
            }, new IntentFilter(SMS_DELIVERED));

            SmsManager smsManager = SmsManager.getDefault();
            PendingIntent sentPendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(SMS_SENT), 0);
            PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(SMS_DELIVERED), 0);
            if (Utils.isHasMultiSim(context)) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                    String sim = Utils.getColumnValue(context, "select sim from home");
                    if (sim != null) {
                        SmsManager.getSmsManagerForSubscriptionId(Integer.parseInt(sim))
                                .sendTextMessage(m_SMSProvider_Code, null, m_Msg, sentPendingIntent, deliveredPendingIntent);
                        mRS = context.getResources().getString(R.string.sms_send_complete);

                    } else {
                        mRS = context.getResources().getString(R.string.sim_card_error);
                    }
                }
            } else {
                smsManager.sendTextMessage(m_SMSProvider_Code, null, m_Msg, sentPendingIntent, deliveredPendingIntent);
            }

        } catch (SecurityException e) {
            mRS = context.getResources().getString(R.string.SMS_permission_explanation);

        } catch (Exception e) {
            mRS = "Utils.sms_send \n" + "Account no=" + m_SMSProvider_Code + "\n SMS Provider Code=" + m_SMSProvider_Code + "\n" + e.toString();
        }
        return mRS;
    }

    public static void sendCommand(Activity activity, String m_account_no, String
            sendMSG, String
                                           logMSG, boolean insertFlag, String trans_or_query, int requestType) {
        PyesApp myApp = ((PyesApp) activity.getApplication());
        TelephonyManager telephonyManager = ((TelephonyManager) activity.getSystemService(TELEPHONY_SERVICE));
        if (Utils.isInternetConnected(activity)) {
            Utils.confirmSendRequest(activity, logMSG, activity.getResources().getString(R.string.request_send_over_internet), "Internet", m_account_no, sendMSG, logMSG, sendMSG, insertFlag, trans_or_query, requestType);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    Utils.requestPermission(activity, Utils.READ_PHONE_STATE_PERMISSION_CODE);
                    myApp.setGlobal_OperationONOFF(true);
                } else {
                    if (telephonyManager.getNetworkType() != TelephonyManager.NETWORK_TYPE_UNKNOWN) {
                        Utils.confirmSendRequest(activity, logMSG, activity.getResources().getString(R.string.request_send_over_sms), "sms", m_account_no, sendMSG, logMSG, sendMSG, insertFlag, trans_or_query, requestType);
                    } else if (Utils.isHasMultiSim(activity)) {
                        Utils.confirmSendRequest(activity, logMSG, activity.getResources().getString(R.string.request_send_over_sms), "sms", m_account_no, sendMSG, logMSG, sendMSG, insertFlag, trans_or_query, requestType);
                    } else {
                        myApp.setGlobal_OperationONOFF(true);
                        Utils.showConfirmationDialog(activity, activity.getResources().getString(R.string.sp_error), activity.getResources().getString(R.string.app_name));
                    }
                }
            } else {
                Utils.confirmSendRequest(activity, logMSG, activity.getResources().getString(R.string.request_send_over_sms), "sms", m_account_no, sendMSG, logMSG, sendMSG, insertFlag, trans_or_query, requestType);
            }
        }
    }

    public static void confirmSendRequest(final Context context, String msg, String title,
                                          final String mCommandType, final String m_account_no, final String sendMSG,
                                          final String logMSG, final String lastSendCMD, final boolean insertFlag,
                                          final String trans_type, final int requestType) {
        //Parameter trans_type 0 :run default 1:query balance and Baqute of phone
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
                Utils.sendRequest(context, mCommandType, m_account_no, sendMSG, logMSG, lastSendCMD, insertFlag, trans_type, requestType);
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

    public static void sendRequest(Context context, String mSEND_OVER, String
            m_account_no, String sendMSG, String logMSG, String lastSendCMD, boolean insertFlag,
                                   String trans_type, int requestType) {
        PyesApp myApp = ((PyesApp) context.getApplicationContext());
        String rs;
        String m_mobile_no = Utils.getColumnValue(context, "select mobile_no from accounts where account_type='PA' and is_default='1'");
        try {
            if (mSEND_OVER.equalsIgnoreCase("Internet")) {
                Utils.insertTransactionLog(context, sendMSG, logMSG, lastSendCMD, insertFlag);
                new runFunctionAsync(context, m_account_no, sendMSG, trans_type, null, requestType, true).execute();
            } else if (mSEND_OVER.equalsIgnoreCase("sms")) {
                if (!Utils.is_Null(m_mobile_no)) {


                    if (sendMSG.length() < 70) {// max length of unicode msg send over sms
                        String m_SMSProvider_Code = Utils.getColumnValue(context, "select SERVICE_NO from service_provider where SP_PREFIX='" + m_mobile_no.substring(0, 2) + "'");
                        if (!Utils.is_Null(m_SMSProvider_Code)) {
                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (!Utils.hasPermissions(context, Utils.SMS_PERMISSION_CODE, Utils.SMS_PERMISSIONS)) {
                                        Utils.requestPermission(context, Utils.SMS_PERMISSION_CODE);
                                        myApp.setGlobal_OperationONOFF(true);
                                    } else {
                                        Utils.insertTransactionLog(context, sendMSG, logMSG, lastSendCMD, insertFlag);
                                        rs = Utils.sms_SEND(context, m_SMSProvider_Code, sendMSG);
                                        if (!Utils.is_Null(rs)) Utils.showToast(context, rs);
                                    }
                                } else {
                                    Utils.insertTransactionLog(context, sendMSG, logMSG, lastSendCMD, insertFlag);
                                    rs = Utils.sms_SEND(context, m_SMSProvider_Code, sendMSG);
                                    if (!Utils.is_Null(rs)) Utils.showToast(context, rs);
                                }
                            } catch (Exception e) {
                                myApp.setGlobal_OperationONOFF(true);
                                Utils.showToast(context, context.getResources().getString(R.string.sms_send_not_complete) + "\n" + e.toString());
                            }
                        } else {
                            myApp.setGlobal_OperationONOFF(true);
                            Utils.showConfirmationDialog(context, context.getResources().getString(R.string.sp_setting_error), context.getResources().getString(R.string.app_name));
                        }
                    } else {
                        myApp.setGlobal_OperationONOFF(true);
                        Utils.showConfirmationDialog(context, context.getResources().getString(R.string.sms_msg_length_error), context.getResources().getString(R.string.app_name));
                    }
                } else {
                    myApp.setGlobal_OperationONOFF(true);
                    Utils.showConfirmationDialog(context, context.getResources().getString(R.string.sms_msg_length_error), context.getResources().getString(R.string.app_name));
                }
            }
        } catch (Exception e) {
            myApp.setGlobal_OperationONOFF(true);
            Utils.showConfirmationDialog(context, "Utils.sendRequest\n" + e.toString(), context.getResources().getString(R.string.app_name));
            if (context.getClass().getName().equalsIgnoreCase("Confirmation")) {
                if (!((Activity) context).isFinishing()) {
                    ((Activity) context).finish();
                }
            }
        }
    }

    private static void closeConfirmationActivity(Context context, PyesApp myApp) {
        try {
            if (myApp.getGlobal_Confirmation() != null) {
                if (myApp.getGlobal_Confirmation()) {
                    myApp.setGlobal_Confirmation(false);
                    Confirmation.getInstance().finish();  //hide confirmation activity if is still active
                }
            }
        } catch (Exception e) {
            Utils.showConfirmationDialog(context, "Utils.CloseConfirmationActivity\n" + e.toString(), context.getResources().getString(R.string.app_name));
        }
    }


    public static int getScale(Context mContext) {
        int density = mContext.getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                return (10);//return "LDPI";
            case DisplayMetrics.DENSITY_MEDIUM:
                return (11);//return "MDPI";
            case DisplayMetrics.DENSITY_HIGH:
                return (12);//return "HDPI";
            case DisplayMetrics.DENSITY_XHIGH:
                return (16);//return "XHDPI";
            case DisplayMetrics.DENSITY_TV:
                return (16);//return "TV";
            case DisplayMetrics.DENSITY_XXHIGH:
                return (16);//return "XXHDPI";
            case DisplayMetrics.DENSITY_XXXHIGH:
                return (16);//return "XXXHDPI";
            default:
                return (16);//return "Unknown";
        }
    }

    public static String getColumnValue(Context context, String SQLSTAT) {
        Cursor qry;
        String result = "";
        try {
            final SQLiteMain entry = new SQLiteMain(context);
            entry.open();
            qry = entry.execute_Query(SQLSTAT);
            qry.moveToFirst();
            int rowCount = qry.getCount();
            if (rowCount == 1) {
                result = qry.getString(0);
            } else {
                result = null;
            }
            qry.close();
            entry.close();
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, "Utils.getColumnValue\n" + e.toString());
            result = null;
        }
        return (result);
    }

    public static ArrayList<String> getArrayListFromTable(Context context, String sql_statement) {
        Cursor ins;
        ArrayList<String> TempInfo = new ArrayList<>();
        TempInfo.clear();
        try {
            final SQLiteMain entry = new SQLiteMain(context);
            entry.open();
            ins = entry.execute_Query(sql_statement);
            ins.moveToFirst();
            int rowCount = ins.getCount();
            int colCount = ins.getColumnCount();
            if (rowCount > 0) {
                for (int i = 0; i < rowCount; i++) {
                    StringBuilder sval = new StringBuilder();
                    if (colCount == 1) {
                        sval = new StringBuilder(ins.getString(0));
                    } else {
                        for (int c = 0; c < colCount; c++) {
                            sval.append(ins.getColumnName(c)).append(": ").append(ins.getString(c)).append("\n");
                        }
                    }
                    TempInfo.add(sval.toString());
                    ins.moveToNext();
                }
            }
            entry.close();
        } catch (Exception e) {
            Utils.showToast(context, e.toString());
            TempInfo.clear();
            TempInfo.add(e.toString());
        }
        return (TempInfo);
    }

    public static ArrayList<String> getPhoneContactsList(Context context) {
        ArrayList<String> phoneContactList = new ArrayList<>();
        String id = "", name = "", phoneNumber = "";
        int len = 0;
        boolean isAccepted;
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                try {
                    id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)).trim().replace(":", "");
                    if (Integer.parseInt(cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            try {
                                phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                if ((phoneNumber.length() > 0) && (phoneNumber != null)) {
                                    phoneNumber = phoneNumber.replace(" ", "").replace(":", "").replace("(", "").replace(")", "").replace("-", "").replace("+", "");
                            /*phone No       len   statuse
                            201747          6      not Accepted
                            01201747        8      Accepted
                            777584443       9      Accepted
                            967777584443    12     Accepted
                            0967777584443   13     Accepted
                            00967777584443  14     Accepted
                            */
                                    len = phoneNumber.length();
                                    isAccepted = false;
                                    switch (len) {
                                        case 8:
                                            isAccepted = true;
                                            break;
                                        case 9:
                                            isAccepted = true;
                                            break;
                                        case 12:
                                            isAccepted = true;
                                            phoneNumber = phoneNumber.substring(3, len);
                                            break;
                                        case 13:
                                            isAccepted = true;
                                            phoneNumber = phoneNumber.substring(4, len);
                                            break;
                                        case 14:
                                            isAccepted = true;
                                            phoneNumber = phoneNumber.substring(5, len);
                                            break;
                                    }
                                    if (isAccepted) {
                                        len = phoneNumber.length();
                                        if (((phoneNumber.substring(0, 1).equalsIgnoreCase("0") && len == 8))
                                                || ((phoneNumber.substring(0, 2).equalsIgnoreCase("70") && len == 9))
                                                || ((phoneNumber.substring(0, 2).equalsIgnoreCase("71") && len == 9))
                                                || ((phoneNumber.substring(0, 2).equalsIgnoreCase("73") && len == 9))
                                                || ((phoneNumber.substring(0, 2).equalsIgnoreCase("77") && len == 9))) {
                                            phoneContactList.add(name + " : " + phoneNumber);
                                        }
                                    }
                                } else {
                                    Log.e(Utils.APP_TAG, name);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(Utils.APP_TAG, name + " " + e.toString());
                            }

                        }
                        pCur.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        cur.close();
        return (phoneContactList);
    }


    public static void addToNotifications(Context context, String priority, String
            title, String body) {
        try {
            PyesApp myApp = ((PyesApp) context.getApplicationContext());
            String mDateTime = Utils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
            String mNotificationID = "PyesAppNoteID";
            Utils.runSQL(context, "insert into notifications " +
                    "(type,id,priority,datetime,title,note,status) values(1," +
                    "'" + mNotificationID + "','" + priority + "','" + mDateTime + "','" + title + "','" + body + "',0)");
            myApp.setGlobal_NotificationsCounter(myApp.getGlobal_NotificationsCounter() + 1);
            Utils.showToast(context, body);
        } catch (Exception e) {
            Utils.showConfirmationDialog(context, "Utils.addToNotifications\n" + e.toString(), context.getResources().getString(R.string.app_name));
        }
    }


    public static void getAccountBalance(final Context context, String account_no) {
        if (!is_Null(account_no)) {
            if (Utils.isInternetConnected(context)) {
                PyesApp myApp = ((PyesApp) context.getApplicationContext());
                String mPassword = myApp.getGlobal_Password();
                String trans_type = "0";
                String mService = "607:" + myApp.getGlobal_Com_Id() + ":" + account_no + ":" + mPassword + ":0";

                new Utils.runFunctionAsync(context, account_no, mService, trans_type, null, Utils.GET_BALANCE, false).execute();
            }
        }
    }

    public static String sendOneSignalTags(Context context) {
        JSONObject tags = new JSONObject();
        try {
            PyesApp myApp = ((PyesApp) context.getApplicationContext());
            String mAccount_Classification = Utils.getColumnValue(context, "select account_classification from accounts where account_no='" + myApp.getGlobal_User_Account() + "'"),
                    mAppVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName,
                    mXMLVersion = "1";

            tags.put("client_type", mAccount_Classification);
            tags.put("App_Version", mAppVersion);
            tags.put("XML_Version", mXMLVersion);
            OneSignal.sendTags(tags);
            return "1";
        } catch (JSONException | PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return e.toString();
        }


    }

    public static String loadContactList(Context context) {
        try {
            ArrayList<String> ContactList;
            ContactList = Utils.getPhoneContactsList(context);
            Utils.runSQL(context, "delete from last_entry where type_id=1");
            for (int i = 0; i < ContactList.size(); i++) {
                Utils.runSQL(context, "insert into last_entry(type_id,last_entry) select '1','" + ContactList.get(i) + "' where not exists (select 0 from  last_entry where type_id='1' and last_entry='" + ContactList.get(i) + "')");
            }
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "2";
        }
    }

    public static String sendWebRequest(Context context, String account_no, String msg, String
            trans_query, int requestType, String template) {
        String result = null;
        String m_WEB_OR_SMS = "1";
        String m_LANG_ID = "1";
        PyesApp myApp = ((PyesApp) context.getApplicationContext());
        try {
            ArrayList<NameValuePair> postParameters = new ArrayList<>();
            postParameters.clear();
            String apk = Utils.getColumnValue(context, new String(Base64.decode(context.getResources().getString(R.string.apk), Base64.NO_WRAP)));

            String versionCode = String.valueOf(BuildConfig.VERSION_CODE);
            if (template.equalsIgnoreCase("run")) {
                postParameters.add(new BasicNameValuePair("p_function",
                        "TR_PKG.WEB_PARSING('" + account_no + "','" + apk + "','" + versionCode + "','" + m_WEB_OR_SMS + "','" + m_LANG_ID + "','" + msg + "')"));
                Log.e(Utils.APP_TAG, "call post");
                //result = WebService.executeHttpPost(context, postParameters, template);
                //result = WebService.executeHttp(context,template,"POST", postParameters);
                result = WebService.executeHttp(context, template, "GET", postParameters);
            } else {
                postParameters.add(new BasicNameValuePair("P_COM_ID", myApp.getGlobal_Com_Id()));
                Log.e(Utils.APP_TAG, "call get");
                result = WebService.executeHttp(context, template, "GET", postParameters);

            }

        } catch (Exception e) {
            myApp.setGlobal_OperationONOFF(true);
            String webError = context.getResources().getString(R.string.web_access_error);
            String code = msg.substring(0, 3);
            if (code.equals("110")) {
                result = "000:" + webError;
            } else {
                result = code + ":" + webError;
            }
        }
        return result;
    }


    public static void checkResponse(Context context, String account_no, String result) {

        Intent intentLaunch = new Intent();
        PyesApp myApp = ((PyesApp) context.getApplicationContext());
        try {

            String strDate = Utils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
            Utils.runSQL(context, "insert into transactions(datetime ,message ) values('" + strDate + "','" + result + "')");
            Utils.runSQL(context, "insert into inbox(datetime ,message ) values('" + strDate + "','" + result + "')");
            if ((result.length() == 1) && (result.equals("0"))) {// خطأ في تنفيذ الطلب عن طريق الوب
                myApp.setGlobal_last_receive_msg(context.getResources().getString(R.string.timeout_error));
                closeConfirmationActivity(context, myApp);
                myApp.setGlobal_GoTOPrevActivity(true);
                intentLaunch.setClassName("dev.alamalbank.amb", "dev.alamalbank.amb.Alert");
                intentLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentLaunch);
            } else if ((result.length() >= 3) && (result.substring(0, 3).equalsIgnoreCase("002"))) {// رسلة التأكيد
                myApp.setGlobal_Confirmation(false);
                intentLaunch.setClassName("dev.alamalbank.amb", "dev.alamalbank.amb.Confirmation");
                intentLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentLaunch);
            } else if ((result.length() >= 3) && (result.substring(0, 3).equalsIgnoreCase("003"))) {// خطا في التأكيد
                myApp.setGlobal_Confirmation(true);
                myApp.setGlobal_last_receive_msg(result);
                Confirmation.getInstance().enabledComponent(Confirmation.ed_confirmation_code);  //enable conferm code editbox
                intentLaunch.setClassName("dev.alamalbank.amb", "dev.alamalbank.amb.Alert");
                intentLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentLaunch);
            } else if ((result.length() >= 3) && (result.substring(0, 3).equalsIgnoreCase("001"))) {//قبول التسجيل عن طريق الرسائل القصيره
                String smsStr = result.substring(4);
                String accountNo = smsStr.substring(0, smsStr.indexOf(":"));
                smsStr = smsStr.substring(smsStr.indexOf(":") + 1);
                String mAccount_Classification = smsStr.substring(0, smsStr.indexOf(":"));
                smsStr = smsStr.substring(smsStr.indexOf(":") + 1);
                String code1 = smsStr;
                myApp.setGlobal_Confirmation(false);
                myApp.setGlobal_Password(myApp.getGlobal_TempVariable());
                Utils.runSQL(context, "update accounts set account_classification='" + mAccount_Classification + "' where account_no='" + accountNo + "'");
                Utils.runSQL(context, "update home set firstLogIn=2,pwd='" + myApp.getGlobal_TempVariable() + "',appcodepart1='" + code1 + "'");
                boolean isAppActive = Utils.isAppActive(context);
                boolean isAppKeyCorrect2 = Utils.isAPPCodeCorrect(context, accountNo, "PART2");
                if (isAppActive && isAppKeyCorrect2) {
                    DataUser_SharedPreferences shar;
                    shar=new DataUser_SharedPreferences(context);
                    shar.setLOGIN("ok");
                    intentLaunch.setClassName("dev.alamalbank.amb", "dev.alamalbank.amb.Main_Activity");
                    intentLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentLaunch);

                } else if (!isAppActive) {
                    Utils.showToast(context, context.getResources().getString(R.string.system_stop));
                } else {
                    Utils.showToast(context, "Utils.checkResponse-KeyP2 \n" + context.getResources().getString(R.string.registration_error));
                }
            } else if ((result.length() >= 3) && (result.substring(0, 3).equalsIgnoreCase("011"))) {//قبول التسجيل عن طريق الوب
                String internetStr = result.substring(4);
                String accountNo = internetStr.substring(0, internetStr.indexOf(":"));
                internetStr = internetStr.substring(internetStr.indexOf(":") + 1);
                String mAccount_Classification = internetStr.substring(0, internetStr.indexOf(":"));
                internetStr = internetStr.substring(internetStr.indexOf(":") + 1);
                String code1 = internetStr.substring(0, internetStr.indexOf(":"));
                internetStr = internetStr.substring(internetStr.indexOf(":") + 1);
                String code2 = internetStr;
                myApp.setGlobal_Confirmation(false);
                myApp.setGlobal_Password(myApp.getGlobal_TempVariable());
                Log.e(Utils.APP_TAG, code1 + " - " + code2);
                Utils.runSQL(context, "update accounts set account_classification='"
                        + mAccount_Classification + "' where account_no='" + accountNo + "'");
                Utils.runSQL(context, "update home set firstLogIn=2 , pwd='" + myApp.getGlobal_TempVariable() +
                        "',appcodepart1='" + code1 + // TODO: DELETE ACCCODEPART1 FROM checkResponse 011
                        "',appcodepart2='" + code2 + "'");
                boolean isAppActive = Utils.isAppActive(context);
                boolean isAppKeyCorrect1 = Utils.isAPPCodeCorrect(context, accountNo, "PART1");
                if (isAppActive && isAppKeyCorrect1) {
                    DataUser_SharedPreferences shar;
                    shar=new DataUser_SharedPreferences(context);
                    shar.setLOGIN("ok");
                    intentLaunch.setClassName("dev.alamalbank.amb", "dev.alamalbank.amb.Main_Activity");
                    intentLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentLaunch);
                    ((Activity) context).finish();
                } else if (!isAppActive) {
                    Utils.showToast(context, context.getResources().getString(R.string.system_stop));
                } else {
                    Utils.showConfirmationDialog(context, context.getResources().getString(R.string.please_wait), context.getResources().getString(R.string.app_name));
                }

            } else if ((result.length() >= 3) && (result.substring(0, 3).equalsIgnoreCase("012"))) {// قبول تغيير كلمة السر
                myApp.setGlobal_Confirmation(false);
                myApp.setGlobal_last_receive_msg(result);
                myApp.setGlobal_GoTOPrevActivity(true);
                Utils.runSQL(context, "update HOME SET PWD ='" + myApp.getGlobal_TempVariable() + "'");
                myApp.setGlobal_Password(myApp.getGlobal_TempVariable());
                intentLaunch.setClassName("dev.alamalbank.amb", "dev.alamalbank.amb.Alert");
                intentLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentLaunch);
            } else if ((result.length() >= 3) && (result.substring(0, 3).equalsIgnoreCase("013"))) {// قبول تغيير البريد الالكتروني
                myApp.setGlobal_Confirmation(false);
                myApp.setGlobal_last_receive_msg(result);
                myApp.setGlobal_GoTOPrevActivity(true);
                Utils.runSQL(context, "update HOME SET EMAIL ='" + myApp.getGlobal_TempVariable() + "'");
                intentLaunch.setClassName("dev.alamalbank.amb", "dev.alamalbank.amb.Alert");
                intentLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentLaunch);
            } else if ((result.length() >= 3) && (result.substring(0, 3).equalsIgnoreCase("000"))) {//رفض التسجيل
                myApp.setGlobal_Confirmation(false);
                myApp.setGlobal_last_receive_msg(result);
                Utils.runSQL(context, "delete from home ");
                Utils.runSQL(context, "delete from service_provider ");
                Utils.runSQL(context, "delete from accounts ");
                intentLaunch.setClassName("dev.alamalbank.amb", "dev.alamalbank.amb.Alert");
                intentLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentLaunch);
            } else if ((result.length() >= 3) && (result.substring(0, 3).equalsIgnoreCase("stp"))) {// توقيف النظام
                Utils.runSQL(context, "update home set active_stop = 0");
            } else if ((result.length() >= 3) && (result.substring(0, 3).equalsIgnoreCase("act"))) {// تفعيل النظام
                Utils.runSQL(context, "update home set active_stop = 1");
            } else if ((result.length() >= 3) && (result.substring(0, 3).equalsIgnoreCase("004"))) {
                // قبول تنفيذ الأومر والطلبات الأخرى
                myApp.setGlobal_Confirmation(false);
                myApp.setGlobal_last_receive_msg(result);
                myApp.setGlobal_GoTOPrevActivity(true);
                intentLaunch.setClassName("dev.alamalbank.amb", "dev.alamalbank.amb.Alert");
                intentLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentLaunch);
                Utils.getAccountBalance(context, account_no);
            } else if ((result.length() >= 3) && (result.substring(0, 3).equalsIgnoreCase("005"))) {
                Utils.showToast(context, result); //short

            } else {
                myApp.setGlobal_Confirmation(false);
                myApp.setGlobal_last_receive_msg(result);
                closeConfirmationActivity(context, myApp);
                Log.e(Utils.APP_TAG, result);
                intentLaunch.setClassName("dev.alamalbank.amb", "dev.alamalbank.amb.Alert");
                intentLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentLaunch);

            }
        } catch (Exception e) {
            try {
                if ((result.length() >= 3) && ((result.substring(0, 3).equalsIgnoreCase("001")) || (result.substring(0, 3).equalsIgnoreCase("000")))) {
                    Utils.runSQL(context, "delete from home ");
                    Utils.runSQL(context, "delete from service_provider ");
                    Utils.runSQL(context, "delete from accounts ");
                }
                closeConfirmationActivity(context, myApp);
                myApp.setGlobal_last_receive_msg(result + "\n" + e.toString());
                intentLaunch.setClassName("dev.alamalbank.amb", "dev.alamalbank.amb.Alert");
                intentLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentLaunch);
            } catch (Exception e2) {
                Utils.showToast(context, e.toString() + "\n" + e2.toString());
            }
        }
    }

    public static String getFacebookPageURL(Context context) {
        String FACEBOOK_URL = Utils.getColumnValue(context, "select COMPANY_FACEBOOK from home");
        String FACEBOOK_PAGE_ID = FACEBOOK_URL.substring("https://www.facebook.com/".length());
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    public static File createDir(Activity activity, String mPath, String mFile_name) {
        File file = null;

        File myDir;
        try {
            myDir = new File(mPath);
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            String fname = mFile_name;
            file = new File(myDir + "/" + fname);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            Utils.showConfirmationDialog(activity, e.toString(), "PYes");
        }

        return file;
    }


    public static boolean isNumeric(String str) {
        // return str.matches("-?\\d+(\\.\\d+)?");
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isValidMobile(Context context, String inMobile) {
        if (inMobile == null) {
            return false;
        }
        return (inMobile.length() == 9) && (Utils.runSQL(context, "select 0 from service_provider where SP_PREFIX='" + inMobile.substring(0, 1) + "'"));
    }

    public static boolean isValidEmail(String inEmail) {
        return !TextUtils.isEmpty(inEmail) && Patterns.EMAIL_ADDRESS.matcher(inEmail).matches();
    }

    public static boolean insertOpenAccount(Context context, String primary_key, String pvalues) {
        boolean flag;
        String result = Utils.getColumnValue(context, "select 0 from open_accounts where client_mobile_no='" + primary_key + "'");
        if (result == null) {
            flag = Utils.runSQL(context, "insert into open_accounts values(" + pvalues + ")");
        }
        return true;
    }

    public static TextWatcher onTextChangedListener(final EditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    long longValue;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longValue = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longValue);

                    //setting text after format to EditText
                    editText.setText(formattedString);
                    editText.setSelection(editText.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                editText.addTextChangedListener(this);
            }
        };
    }

    public static String convertArDigitToEn(String str) {
        if (str != null) {
            return (((((((((((str).replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0"));
        } else {
            return str;
        }
    }

    public static int isConditionCorrect(String p_Condition, String p_Value, String p_Min, String p_Max) {
        if ((!Utils.is_Null(p_Condition)) && (!Utils.is_Null(p_Min)) && (!Utils.is_Null(p_Max))) {
            org.mozilla.javascript.Context context = org.mozilla.javascript.Context.enter(); //
            context.setOptimizationLevel(-1); // this is required[2]
            Scriptable scope = context.initStandardObjects();
            String m_Condition = p_Condition.toUpperCase();
            String mValue=String.valueOf(p_Value.length());
            m_Condition = m_Condition.replaceAll("X", mValue.replaceAll(",",""));
            m_Condition = m_Condition.replaceAll("N", p_Min.replaceAll(",",""));
            m_Condition = m_Condition.replaceAll("M", p_Max.replaceAll(",","")); //"18 > 17 and 18 < 100"
            Log.e(Utils.APP_TAG, "Condition " + m_Condition);
           try {
               Object result = context.evaluateString(scope, m_Condition, "<cmd>", 1, null);
               Log.e(Utils.APP_TAG, "Condition Result is " + result);
               if (result.toString().trim().equalsIgnoreCase("true")) {
                   return 1;
               } else if (result.toString().trim().equalsIgnoreCase("false")) {
                   return 0;
               } else {
                   return 3;// means The syntax of conditional is not correct
               }
           }catch (Exception e){
               return 3;// means The syntax of conditional is not correct
           }

        } else {
            return 1;
        }
    }

    public static String getConditionFormat(Context context, String p_Condition, String p_Label, String p_Min, String p_Max) {
        if ((!Utils.is_Null(p_Condition)) && (!Utils.is_Null(p_Min)) && (!Utils.is_Null(p_Max))) {
            String m_condition_format = p_Condition;
            m_condition_format = m_condition_format.replace("N", p_Min);
            m_condition_format = m_condition_format.replace("M", p_Max);
            m_condition_format = m_condition_format.replace("X", p_Label);
            return m_condition_format;
        } else {
            return "";
        }
    }

    public static boolean isNoOfPartCorrect(int p_no_part, String p_str) {
        boolean flag;
        String temp = p_str.trim();
        int i = 0, index = temp.indexOf(" ");
        while (index != -1) {
            i++;
            temp = temp.substring(index).trim();
            index = temp.indexOf(" ");
        }
        flag = i >= (p_no_part - 1);
        return flag;
    }

    public static boolean isCountOfWordsCorrect(String p_Condition, String p_Value, String p_Min, String p_Max) {
        if ((!Utils.is_Null(p_Min)) && (!Utils.is_Null(p_Max))) {
            boolean flag;
            String temp = p_Value.trim();
            int i = 0, index = temp.indexOf(" ");
            while (index != -1) {
                i++;
                temp = temp.substring(index).trim();
                index = temp.indexOf(" ");
            }
            flag = (i >= (Integer.parseInt(p_Min) - 1)) && (i <= (Integer.parseInt(p_Max) - 1));
            return flag;
        } else {
            return true;
        }
    }

    @SuppressLint("RtlHardcoded")
    public static void buildLayout(final Activity act, LinearLayout
            la_detail_send_scrollview, String detailService, String m_language) {
        /*COLUMN_CONTAINS_TYPE
         * [ 0 ][ 1 ][ 2 ][ 3 ][ 4 ][ 5 ]
         * 0: View type (S - Spinner ,L - ListView ,E - EditText,V - TextView , I - Image)
         * 1: Data type (T - Text    ,N - Number ,P - Password ,C - Currencies ,A - Account ,W - Phone,M - Mobile,S - POS or Agent)
         * 2: Editable type (E - Enabled, D - Disabled)
         * 3: Required (R - Required, N - Not Required)
         * 4: Masked (M - Masked, N - Not Masked)
         * 5: IS is  append to lat_entry table (A - Append , N - Not Append
         */
        PyesApp myApp = ((PyesApp) act.getApplication());
        final Typeface myFont = Typeface.createFromAsset(act.getAssets(), "font/jannat_bold.otf");
        String[] componentArray;
        HashMap<Integer, String[]> componentMap = new HashMap<>();
        componentMap.clear();
        Cursor qry;
        String m_FIELD_ID, m_LABEL, m_HINT, m_ITEM_TYPE, m_DATA_TYPE, m_APPEND, m_MIN_LEN, m_MAX_LEN, m_CONDITION, m_IS_ACC_ID, m_QUERY, m_ICON;
        TextWatcher m_TextWatcher;
        int m_Gravity, m_Drawable_Icon, m_InputType;
        m_language = m_language.toUpperCase();
        m_Gravity = (m_language.equals("AR")) ? Gravity.RIGHT : Gravity.LEFT;

        la_detail_send_scrollview.removeAllViews();
        LinearLayout.LayoutParams linearParams;
        linearParams = new LinearLayout.LayoutParams(0, 0);
        linearParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        linearParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        linearParams.topMargin = 2;
        linearParams.rightMargin = 5;
        linearParams.leftMargin = 5;
        linearParams.bottomMargin = 2;
        linearParams.gravity = m_Gravity;
        try {
            final SQLiteMain entry = new SQLiteMain(act);
            entry.open();

            qry = entry.execute_Query(
                    "SELECT FIELD_ID FIELD_ID," +
                            " CASE '" + m_language + "' " +
                            "   WHEN 'AR' THEN LABEL_AR" +
                            "   ELSE LABEL_EN" +
                            "   END as LABEL," +
                            " CASE '" + m_language + "' " +
                            "   WHEN 'AR' THEN HINT_AR" +
                            "   ELSE HINT_EN" +
                            "   END as HINT," +
                            " ITEM_TYPE ITEM_TYPE," +
                            " DATA_TYPE DATA_TYPE," +
                            " APPENDABLE APPEND," +
                            " MIN_VALUE_LENGTH MIN_LEN," +
                            " MAX_VALUE_LENGTH MAX_LEN," +
                            " CONDITION CONDITION," +
                            " IS_ACC_ID IS_ACC_ID," +
                            " QUERY_STATEMENT QUERY," +
                            " ICON_TYPE ICON" +
                            " FROM SERVICE_COL " +
                            " WHERE SERVICE_CODE='" + detailService + "'" +
                            " ORDER BY FIELD_ORD");
            qry.moveToFirst();
            int rowCount = qry.getCount();
            if (rowCount > 0) {
                int notRequiredID = 1000;
                int RequiredID = 1;
                for (int i = 0; i < rowCount; i++) {
                    //------------------------

                    m_FIELD_ID = qry.getString(qry.getColumnIndex("FIELD_ID")).toUpperCase();
                    m_LABEL = qry.getString(qry.getColumnIndex("LABEL"));
                    m_HINT = qry.getString(qry.getColumnIndex("HINT"));
                    m_ITEM_TYPE = qry.getString(qry.getColumnIndex("ITEM_TYPE")).toUpperCase();
                    m_DATA_TYPE = qry.getString(qry.getColumnIndex("DATA_TYPE")).toUpperCase();
                    m_APPEND = qry.getString(qry.getColumnIndex("APPEND")).toUpperCase();
                    m_MIN_LEN = qry.getString(qry.getColumnIndex("MIN_LEN")).toUpperCase();
                    m_MAX_LEN = qry.getString(qry.getColumnIndex("MAX_LEN")).toUpperCase();
                    m_CONDITION = qry.getString(qry.getColumnIndex("CONDITION")).toUpperCase();
                    m_IS_ACC_ID = qry.getString(qry.getColumnIndex("IS_ACC_ID")).toUpperCase();
                    m_QUERY = qry.getString(qry.getColumnIndex("QUERY")).toUpperCase();
                    m_ICON = qry.getString(qry.getColumnIndex("ICON")).toUpperCase();


                    m_QUERY = m_QUERY.replaceAll("<LANG>", m_language);
                    m_QUERY = m_QUERY.replaceAll("\"", "'");
                    m_QUERY = m_QUERY.replaceAll("FROM DUAL", "");

                    componentArray = new String[8];
                    componentArray[0] = m_FIELD_ID;
                    componentArray[1] = m_ITEM_TYPE;
                    componentArray[2] = m_DATA_TYPE;
                    componentArray[3] = m_CONDITION;
                    componentArray[4] = m_MIN_LEN;
                    componentArray[5] = m_MAX_LEN;
                    componentArray[6] = m_IS_ACC_ID;
                    componentArray[7] = m_APPEND;

                    componentMap.put(RequiredID, componentArray);


                    switch (Integer.parseInt(m_ICON)) {
                        case 1:
                            m_Drawable_Icon = R.drawable.text;
                            break;
                        case 2:
                            m_Drawable_Icon = R.drawable.amount;
                            break;
                        case 3:
                            m_Drawable_Icon = R.drawable.password;
                            break;
                        case 4:
                            m_Drawable_Icon = R.drawable.amount;
                            break;
                        case 5:
                            m_Drawable_Icon = R.drawable.client;
                            break;
                        case 6:
                            m_Drawable_Icon = R.drawable.phone;
                            break;
                        case 7:
                            m_Drawable_Icon = R.drawable.mobile;
                            break;
                        case 8:
                            m_Drawable_Icon = R.drawable.mobile;
                            break;
                        case 9:
                            m_Drawable_Icon = R.drawable.client;
                            break;
                        default:
                            m_Drawable_Icon = R.drawable.text;
                    }

                    switch (m_DATA_TYPE) {
                        case "C":
                            m_InputType = InputType.TYPE_CLASS_NUMBER;
                            break;
                        case "D":
                            m_InputType = InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE;
                            break;
                        case "E":
                            m_InputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
                            break;
                        case "N":
                            m_InputType = InputType.TYPE_CLASS_NUMBER;
                            break;
                        default:
                            m_InputType = InputType.TYPE_CLASS_TEXT;
                    }


                    InputFilter[] filterArray = new InputFilter[1];
                    filterArray[0] = new InputFilter.LengthFilter(Integer.parseInt(m_MAX_LEN));

                    final TextView tv = new TextView(act);
                    tv.setLayoutParams(linearParams);
                    tv.setId(notRequiredID++);
                    tv.setGravity(m_Gravity);
                    tv.setTextSize(Utils.getScale(act));
                    tv.setPadding(0, 5, 0, 5);
                    tv.setTextColor(act.getResources().getColor(R.color.color_label));
                    tv.setTypeface(myFont);
                    tv.setText(m_LABEL); //<-- set label
                    la_detail_send_scrollview.addView(tv);
                    if (m_ITEM_TYPE.equals("S")) { // for list spinner
                        final Spinner sp = new Spinner(act);
                        sp.setLayoutParams(linearParams);
                        sp.setId(RequiredID);
                        RequiredID++;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            sp.setGravity(m_Gravity);
                        }
                        if (m_IS_ACC_ID.equals("Y")) {
                            Utils.populateAccountSpinnerAdapter(act, sp, m_QUERY);
                        } else {
                            Utils.populateSpinnerAdapter(act, sp, Utils.getArrayListFromDB(act, m_QUERY), R.layout.spinner_item);
                        }
                        la_detail_send_scrollview.addView(sp);
                        //STERNN
                    } else if (m_ITEM_TYPE.equals("A")) { // for autocomplete TextView
                        final AutoCompleteTextView auto = new AutoCompleteTextView(act);
                        auto.setLayoutParams(linearParams);
                        auto.setId(RequiredID);
                        RequiredID++;
                        auto.setInputType(InputType.TYPE_CLASS_TEXT);
                        auto.setMaxLines(1);
                        auto.setGravity(Gravity.CENTER);
                        auto.setTextSize(Utils.getScale(act));
                        auto.setHint(m_HINT); // <-- set hint
                        auto.setGravity(m_Gravity);
                        auto.setTypeface(myFont);
                        auto.setFilters(filterArray);
                        auto.setImeOptions(EditorInfo.IME_ACTION_NEXT); //Change the editor type integer associated with the text view, which will be reported to an IME with EditorInfo.imeOptions when it has focus.
                        auto.setTextColor(act.getResources().getColor(R.color.color_edit));
                        auto.setHintTextColor(act.getResources().getColor(R.color.color_grey));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            auto.setCompoundDrawablesRelativeWithIntrinsicBounds(m_Drawable_Icon, 0, 0, 0);
                        }
                        auto.setThreshold(1); // Specifies the minimum number of characters the user has to type in the edit box before the drop down list is shown.When threshold is less than or equals 0, a threshold of 1 is applied.
                        Utils.populateAutoCompAdapter(act, auto, Utils.getArrayListFromTable(act, "select last_entry from last_entry  order by type_id "));

                        la_detail_send_scrollview.addView(auto);
                    } else if (m_ITEM_TYPE.equals("E")) { // for EditView Text
                        final EditText edt = new EditText(act);
                        edt.setLayoutParams(linearParams);
                        edt.setId(RequiredID);
                        RequiredID++;
                        edt.setTypeface(myFont);
                        edt.setFilters(filterArray);
                        edt.setGravity(Gravity.CENTER);
                        edt.setTextSize(Utils.getScale(act));// * getResources().getDisplayMetrics().density);
                        // edt.setPadding(30, 5, 30, 5);
                        edt.setTextColor(act.getResources().getColor(R.color.color_edit));
                        edt.setHintTextColor(act.getResources().getColor(R.color.color_grey));
                        edt.setHint(m_HINT);
                        edt.setGravity(m_Gravity);
                        edt.setInputType(m_InputType);
                        if (m_DATA_TYPE.equals("C")) {
                            edt.addTextChangedListener(Utils.onTextChangedListener(edt));
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            edt.setCompoundDrawablesRelativeWithIntrinsicBounds(m_Drawable_Icon, 0, 0, 0);
                        }
                        edt.setBackgroundResource(R.drawable.rounded_w);
                        la_detail_send_scrollview.addView(edt);
                    }
                    qry.moveToNext();
                }
                myApp.setGlobal_ComponentMap(componentMap);
            } else {
                Utils.showToast(act, "Build Activity\n" + act.getResources().getString(R.string.no_services) + "\n" + detailService);
                act.finish();
            }
        } catch (Exception e) {
            Utils.showConfirmationDialog(act, e.toString(), act.getResources().getString(R.string.app_name));
        }
    }

    public static void sendService(Activity act, LinearLayout la_detail_send_scrollview, String
            m_title,
                                   String detailService, String m_Com_Id, String m_Account_Label, String
                                           m_Account, String m_password) {
        PyesApp myApp = ((PyesApp) act.getApplication());
        //boolean flag_password_required = false;
        String[] tempArray;
        String tempColName, tempITEM_TYPE, tempDATA_TYPE, tempCONDITION, tempMax, tempMin, tempIsAccountNo, tempAppend;
        StringBuilder sendMSG = new StringBuilder(detailService + ":" + m_Com_Id + ":" + m_Account + ":");
        StringBuilder logMSG = new StringBuilder(m_title + "\n" + m_Account_Label + ":" + m_Account + "\n");
        String tvval = "";
        String spval;
        String autoval;
        String edval;
        String reason_error;
        int m_ConditionResult;
        // String account_no = "";

        int count = la_detail_send_scrollview.getChildCount();
        int i = 0;
        boolean f = true;
        if (myApp.getGlobal_OperationONOFF()) {
            while ((f) && (i < count)) {
                View view = la_detail_send_scrollview.getChildAt(i);
                int viewId = view.getId();
                if (viewId < 1000) {
                    tempArray = myApp.getGlobal_ComponentMap().get(viewId);
                    tempColName = tempArray[0].trim();
                    tempITEM_TYPE = tempArray[1].trim();
                    tempDATA_TYPE = tempArray[2].trim();
                    tempCONDITION = tempArray[3].trim();
                    tempMin = tempArray[4].trim();
                    tempMax = tempArray[5].trim();
                    tempIsAccountNo = tempArray[6].trim();
                    tempAppend = tempArray[7].trim();


                    if (view instanceof Spinner) {
                        Spinner sp = (Spinner) view;
                        spval = Utils.convertArDigitToEn(sp.getItemAtPosition(sp.getSelectedItemPosition()).toString());
                        if (spval.isEmpty()) {
                            f = false;
                            reason_error = act.getResources().getString(R.string.value_not_empty);
                        } else {
                            if ((tempAppend.equals("Y"))) {
                                Utils.addToLastEntry(act, spval.trim());
                            }
                            if (tempIsAccountNo.equals("Y")) {
                                spval = spval.substring(0, spval.indexOf(":"));
                                //account_no = spval;
                            }
                            m_ConditionResult = Utils.isConditionCorrect(tempCONDITION, spval, tempMin, tempMax);//<--if Condition true
                            if (m_ConditionResult == 1) {
                                sendMSG.append(spval).append(":");
                                logMSG.append(spval).append("\n");
                                f = true;
                            } else {
                                f = false;
                                if (m_ConditionResult == 0) {//<--if Condition false
                                    reason_error = act.getResources().getString(R.string.value_condition) + Utils.getConditionFormat(act, tempCONDITION, tvval, tempMin, tempMax);
                                } else {//<--if Condition syntax is not correct
                                    reason_error = act.getResources().getString(R.string.condition_format_error);
                                }
                            }
                        }
                    } else if (view instanceof AutoCompleteTextView) {
                        AutoCompleteTextView auto = (AutoCompleteTextView) view;
                        autoval = Utils.convertArDigitToEn(auto.getText().toString());
                        if (autoval.isEmpty()) {
                            f = false;
                            reason_error = act.getResources().getString(R.string.value_not_empty);
                            auto.requestFocus();
                            auto.setError(reason_error);
                        } else {
                            if ((tempDATA_TYPE.equals("T"))) {
                                if (autoval.contains(":")) {
                                    autoval = autoval.substring(0, autoval.indexOf(":") - 1);
                                }
                                autoval = autoval.replace(":", " ");


                                if (Utils.isCountOfWordsCorrect(tempCONDITION, autoval, tempMin, tempMax)) { //if item data type is text then check number of words
                                    if ((tempAppend.equals("Y"))) {
                                        Utils.addToLastEntry(act, autoval.trim());
                                    }
                                    sendMSG.append(autoval.trim()).append(":");
                                    logMSG.append(autoval.trim()).append("\n");
                                    f = true;
                                } else {
                                    f = false;
                                    reason_error = act.getResources().getString(R.string.error_no_of_part) + "\n" +
                                            act.getResources().getString(R.string.no_of_part)
                                            + " " + tempMin + " " + act.getResources().getString(R.string.parts) + " " + act.getResources().getString(R.string.at_least);
                                    auto.requestFocus();
                                    auto.setError(reason_error);
                                }

                            } else {
                                int index = 0;
                                if (autoval.contains(" : ")) {
                                    index = autoval.indexOf(" : ") + 3;
                                }
                                autoval = autoval.substring(index);
                                if (Utils.isNumeric(autoval)) {
                                    if ((tempAppend.equals("Y"))) {
                                        Utils.addToLastEntry(act, autoval.trim());
                                    }
                                    m_ConditionResult = Utils.isConditionCorrect(tempCONDITION, autoval, tempMin, tempMax);//<--if Condition true
                                    if (m_ConditionResult == 1) {
                                        sendMSG.append(autoval).append(":");
                                        logMSG.append(autoval).append("\n");
                                        f = true;
                                    } else {
                                        f = false;
                                        if (m_ConditionResult == 0) {//<--if Condition false
                                            reason_error = act.getResources().getString(R.string.value_condition) + Utils.getConditionFormat(act, tempCONDITION, tvval, tempMin, tempMax);
                                        } else {//<--if Condition syntax is not correct
                                            reason_error = act.getResources().getString(R.string.condition_format_error);
                                        }
                                        auto.requestFocus();
                                        auto.setError(reason_error);
                                    }
                                } else {
                                    f = false;
                                    reason_error = act.getResources().getString(R.string.number_error);
                                    auto.requestFocus();
                                    auto.setError(reason_error);
                                }
                            }// end else of if is text or number
                        }// if is empty

                    } else if (view instanceof EditText) {
                        EditText ed = (EditText) view;
                        edval = Utils.convertArDigitToEn(ed.getText().toString());
                        if (edval.isEmpty()) {
                            f = false;
                            reason_error = act.getResources().getString(R.string.value_not_empty);
                            ed.requestFocus();
                            ed.setError(reason_error);
                        } else {
                            if ((tempAppend.equals("Y"))) {
                                Utils.addToLastEntry(act, edval.trim());
                            }
                            m_ConditionResult = Utils.isConditionCorrect(tempCONDITION, edval, tempMin, tempMax);//<--if Condition is true
                            if (m_ConditionResult == 1) {
                                if (tempDATA_TYPE.equals("C")) {
                                    sendMSG.append(edval.replaceAll(",", "")).append(":");
                                } else {
                                    sendMSG.append(edval).append(":");
                                }
                                f = true;
                            } else {
                                f = false;
                                if (m_ConditionResult == 0) {//<--if Condition is false
                                    reason_error = act.getResources().getString(R.string.value_condition) + Utils.getConditionFormat(act, tempCONDITION, tvval, tempMin, tempMax);
                                } else {//<--if Condition syntax is not correct
                                    reason_error = act.getResources().getString(R.string.condition_format_error);
                                }
                                ed.requestFocus();
                                ed.setError(reason_error);
                            }
                        }
                    }
                } else if (view instanceof TextView) {
                    TextView tv = (TextView) view;
                    tvval = Utils.convertArDigitToEn(tv.getText().toString());
                    if (!tvval.isEmpty()) {
                        logMSG.append(tvval).append(" : ");
                        f = true;
                    } else {
                        f = false;
                        reason_error = ""; /// add reason_error
                    }
                }

                i++;
            }
            if (f) {
                myApp.setGlobal_OperationONOFF(false);
                sendMSG.append(m_password).append(":0");
                sendMSG = new StringBuilder(Utils.convertArDigitToEn(sendMSG.toString()));
                logMSG = new StringBuilder(Utils.convertArDigitToEn(logMSG.toString()));
                sendCommand(act, m_Account, sendMSG.toString(), logMSG.toString(), true, "0", Utils.SEND_CMD);
            } else {
                myApp.setGlobal_OperationONOFF(true);
                Utils.showConfirmationDialog(act, act.getResources().getString(R.string.entered_data_error), act.getResources().getString(R.string.app_name));
            }
        } else {
            Utils.showConfirmationDialog(act, act.getResources().getString(R.string.new_operation_error), act.getResources().getString(R.string.app_name));
        }

    }

    public static boolean copyFile(String sourceFile, File destFile) {
        File filesourceFile = new File(sourceFile);
        if (!filesourceFile.exists()) {
            return false;
        }
        if (destFile.exists()) {
            destFile.delete();
        }
        try {
            FileChannel source = null;
            FileChannel destination = null;
            source = new FileInputStream(filesourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();

            if (destination != null && source != null) {
                destination.transferFrom(source, 0, source.size());
            }
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
            return true;
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());
            return false;
        }
    }

    public static void checkAppUpToDate(Context context, String mWhat, String mTrans_type_id, boolean withProgress) {
        if (Utils.isInternetConnected(context)) {
            PyesApp myApp = ((PyesApp) context.getApplicationContext());

            String mAccount = myApp.getGlobal_User_Account(), mPassword = myApp.getGlobal_Password();
            //String mTrans_type_id = "0";
            String mService = "306:" + myApp.getGlobal_Com_Id() + ":" + mAccount + ":" + mTrans_type_id + ":" + mPassword + ":0";
            /*
             mWha value:
                1 - get App version
                2 - download menu and service fields
                3 - download static data
                4 - download branch and agent info
                5 - download telecomold info
                6 - download customer granted services

             */
            switch (mWhat) {
                case "1":
                    new Utils.runFunctionAsync(context, mAccount, mService, mTrans_type_id, null, Utils.GET_APP_VERSION, withProgress).execute();
                    break;
                case "2":
                    new Utils.runFunctionAsync(context, null, null, null, null, Utils.DOWNLOAD_SERVICES_WEB, withProgress).execute();
                    //new Utils.runFunctionAsync(context, mAccount, mService, mTrans_type_id, null, Utils.GET_XML_VERSION_UPDATE, withProgress).execute();
                    break;
                case "3":
                    new Utils.runFunctionAsync(context, mAccount, null, null, null, Utils.GET_STATIC_DATA, withProgress).execute();
                    break;
                case "4":
                    new Utils.runFunctionAsync(context, mAccount, null, null, null, Utils.GET_BRANCHESAGENT_SERVICES, withProgress).execute();
                    break;
                case "5":
                    new Utils.runFunctionAsync(context, mAccount, null, null, null, Utils.GET_TELECOM_SERVICES, withProgress).execute();
                    break;
                case "6":
                    new Utils.runFunctionAsync(context, mAccount, mService, mTrans_type_id, null, Utils.GET_CLIENT_SERVICES, withProgress).execute();
                    break;
            }
        }
    }

    public static void processWebResult(Context serviceContext, String account_no,
                                        int requestType, String serviceResult) {
        PyesApp myApp = ((PyesApp) serviceContext.getApplicationContext());
        boolean isAlertResponse = false;
        try {
            if (serviceResult.length() >= 3) {
                if (serviceResult.substring(0, 3).equalsIgnoreCase("004")) {
                    isAlertResponse = true;
                }
            }

            if (requestType == Utils.GET_BALANCE) {
                Log.e(Utils.APP_TAG, " Balance : " + serviceResult);
                if (isAlertResponse) {
                    Utils.runSQL(serviceContext, "update accounts set account_balance='" + serviceResult.substring(4) + "' where account_no='" + account_no + "'");
                }
            } else if (requestType == Utils.REGISTER) {
                Utils.showToast(serviceContext, serviceContext.getResources().getString(R.string.registration_success));
            } else if (requestType == Utils.GET_APP_VERSION) {

                if (isAlertResponse && (Double.parseDouble(serviceResult.substring(4)) > (double) BuildConfig.VERSION_CODE)) {
                    // added to notifications
                    addToNotifications(serviceContext, "3", serviceContext.getResources().getString(R.string.app_notifications), serviceContext.getResources().getString(R.string.app_update_error));
                }

                Utils.checkAppUpToDate(serviceContext, "2", "2", true);

            } else if (requestType == Utils.GET_XML_VERSION_UPDATE) {
                String servicesVersion = Utils.getColumnValue(serviceContext, "select services_version from home");
                if (servicesVersion == null) {
                    servicesVersion = "0";
                }

                if (isAlertResponse && (Double.parseDouble(serviceResult.substring(4)) > Double.parseDouble(servicesVersion))) {
                    new Utils.runFunctionAsync(serviceContext, account_no, null, null, serviceResult.substring(4), Utils.DOWNLOAD_SERVICES_WEB, true).execute();
                } else {
                    Utils.showToast(serviceContext, serviceContext.getResources().getString(R.string.xml_updated_before));
                    Utils.checkAppUpToDate(serviceContext, "6", "3", true);
                }
            } else if (requestType == Utils.GET_XML_VERSION) {
                String servicesVersion = Utils.getColumnValue(serviceContext, "select services_version from home");
                if (servicesVersion != null) {
                    if (isAlertResponse && (Double.parseDouble(serviceResult.substring(4)) > Double.parseDouble(servicesVersion))) {
                        // added to notifications
                        addToNotifications(serviceContext, "3", serviceContext.getResources().getString(R.string.app_notifications), serviceContext.getResources().getString(R.string.xml_needed_update));
                    }
                }
                Utils.checkAppUpToDate(serviceContext, "6", "3", false);
            } else if (requestType == Utils.GET_CLIENT_SERVICES) {
                try {

                    boolean flag = true, flag_insert_services = true;
                    String listResult = serviceResult;
                    Log.e(Utils.APP_TAG, "Service Code : " + serviceResult);
                    ArrayList<String> list = new ArrayList<>();
                    if (!Utils.is_Null(listResult)) {
                        list = new ArrayList<>(Arrays.asList(listResult.split(",")));
                        int i = 0;
                        flag = Utils.runSQL(serviceContext, "delete from accounts_services where is_mandatory='0'");
                        if (flag) {
                            while ((i < list.size()) && (flag_insert_services)) {
                                Log.e(Utils.APP_TAG, "Service Code : " + list.get(i).trim());
                                flag_insert_services = Utils.runSQL(serviceContext, "insert into accounts_services(com_id,account_no,service_code,is_mandatory) values('" + myApp.getGlobal_Com_Id() + "','" + myApp.getGlobal_User_Account() + "','" + list.get(i).trim() + "','0')");
                                i++;
                            }
                        }
                    }
                    if (flag_insert_services) {
                        Utils.showToast(serviceContext, serviceContext.getResources().getString(R.string.account_services_success) + "\n" +
                                serviceContext.getResources().getString(R.string.install_success));

                    } else {
                        Utils.showToast(serviceContext, serviceContext.getResources().getString(R.string.account_services_error) + "\n" +
                                serviceContext.getResources().getString(R.string.install_not_success));
                        addToNotifications(serviceContext, "3", serviceContext.getResources().getString(R.string.app_notifications), serviceContext.getResources().getString(R.string.account_services_error));
                    }

                } catch (Exception e) {
                    Utils.showConfirmationDialog(serviceContext, e.toString(), serviceContext.getResources().getString(R.string.app_name));
                }
            } else if (requestType == Utils.SEND_TAGS) {
                Utils.showToast(serviceContext, serviceContext.getResources().getString(R.string.registration_notification_success));
            } else if (requestType == Utils.SEND_CMD) {
                myApp.setGlobal_last_receive_msg(serviceResult);
                Utils.checkResponse(serviceContext, account_no, serviceResult);

            } else if ((requestType == Utils.DOWNLOAD_SERVICES_WEB) ||
                    (requestType == Utils.GET_BRANCHESAGENT_SERVICES) ||
                    (requestType == Utils.GET_TELECOM_SERVICES) ||
                    (requestType == Utils.GET_STATIC_DATA)) {

                switch (requestType) {
                    case Utils.DOWNLOAD_SERVICES_WEB:
                        Utils.runSQL(serviceContext, "DELETE FROM SERVICE_COL");
                        Utils.runSQL(serviceContext, "DELETE FROM SERVICES");
                        Utils.runSQL(serviceContext, "DELETE FROM MENUS");
                        break;
                    case Utils.GET_BRANCHESAGENT_SERVICES:
                        Utils.runSQL(serviceContext, "DELETE FROM BRANCHES_INFO");
                        break;
                    case Utils.GET_TELECOM_SERVICES:
                        Utils.runSQL(serviceContext, "DELETE FROM TELECOM_SERVICES_DTL");
                        Utils.runSQL(serviceContext, "DELETE FROM TELECOM_SERVICES");
                        Utils.runSQL(serviceContext, "DELETE FROM TELECOM_INFO");
                        Utils.runSQL(serviceContext, "DELETE FROM TELECOMS");
                        break;
                    case Utils.GET_STATIC_DATA:
                        Utils.runSQL(serviceContext, "DELETE FROM STATIC_DATA_DTL");
                        Utils.runSQL(serviceContext, "DELETE FROM CURRENCIES");
                        Utils.runSQL(serviceContext, "DELETE FROM IDCARDS_TYPE");
                        break;
                }


                JSONObject jsonObj = new JSONObject(serviceResult);
                JSONArray items = jsonObj.getJSONArray("items");
                if ((items.length() >= 1)) {
                    String mDoInsert = Utils.doInsert(serviceContext, items);
                    if ((mDoInsert.substring(0, 1).equalsIgnoreCase("1"))) {

                        if ((requestType == Utils.DOWNLOAD_SERVICES_WEB)) {
                            Utils.showToast(serviceContext, serviceContext.getResources().getString(R.string.xml_updated_success));

                            Utils.addedServices(serviceContext); // <-- Added Mandatory services like About US,Download services

                            Utils.checkAppUpToDate(serviceContext, "3", "", true); //<-- 3 get Static Data

                        } else if ((requestType == Utils.GET_STATIC_DATA)) {
                            Utils.showToast(serviceContext, serviceContext.getResources().getString(R.string.update_complete));
                            Utils.checkAppUpToDate(serviceContext, "4", "", true); //<-- 4 get branch agents info
                        } else if ((requestType == Utils.GET_BRANCHESAGENT_SERVICES)) {
                            Utils.showToast(serviceContext, serviceContext.getResources().getString(R.string.updated_branches_success));
                            Utils.checkAppUpToDate(serviceContext, "5", "", true); //<-- 5 get telecomold services
                        } else {
                            Utils.showToast(serviceContext, serviceContext.getResources().getString(R.string.updated_telecom_success));
                            Utils.checkAppUpToDate(serviceContext, "6", "3", true); //<-- 6 get customer granted services
                        }

                    } else {
                        if ((requestType == Utils.DOWNLOAD_SERVICES_WEB)) {
                            Utils.showToast(serviceContext, mDoInsert + "\n" + serviceContext.getResources().getString(R.string.xml_updated_error));
                        } else if ((requestType == Utils.GET_BRANCHESAGENT_SERVICES)) {
                            Utils.showToast(serviceContext, mDoInsert + "\n" + serviceContext.getResources().getString(R.string.updated_branches_error));
                        } else {
                            Utils.showToast(serviceContext, mDoInsert + "\n" + serviceContext.getResources().getString(R.string.updated_telecom_error));
                        }
                    }
                } else {
                    Utils.showToast(serviceContext, serviceContext.getResources().getString(R.string.xml_updated_error));
                }

            } else if (requestType == Utils.DOWNLOAD_SERVICES_LOCAL) {
                if (serviceResult.equalsIgnoreCase("1")) {
                    Utils.showToast(serviceContext, serviceContext.getResources().getString(R.string.xml_updated_success));
                    //Added Mandatory services
                    Utils.addedServices(serviceContext);
                    Utils.checkAppUpToDate(serviceContext, "6", "3", true);
                } else {
                    Utils.showToast(serviceContext, serviceContext.getResources().getString(R.string.xml_updated_error));
                }
            } else if (requestType == Utils.LOAD_CONTACT_LIST) {
                if (serviceResult.equalsIgnoreCase("1")) {
                    Utils.showToast(serviceContext, serviceContext.getResources().getString(R.string.update_complete));
                } else {
                    Utils.showToast(serviceContext, serviceContext.getResources().getString(R.string.update_not_complete));
                }
            } else if (requestType == Utils.LOAD_BRANCHES) {
                try {
                    myApp.setGlobal_MapTitle((myApp.getGlobal_Lang().equalsIgnoreCase("ar") ? myApp.getGlobal_BranchesForMap().get(0).get("name_ar") : myApp.getGlobal_BranchesForMap().get(0).get("name_en")));
                    myApp.setGlobal_MapAddress((myApp.getGlobal_Lang().equalsIgnoreCase("ar") ? myApp.getGlobal_BranchesForMap().get(0).get("ADDRESSE_AR") : myApp.getGlobal_BranchesForMap().get(0).get("address_en")));
                    myApp.setGlobal_MapLong(myApp.getGlobal_BranchesForMap().get(0).get("longitude"));
                    myApp.setGlobal_MapLati(myApp.getGlobal_BranchesForMap().get(0).get("latitude"));
                    myApp.setGlobal_Position(String.valueOf(0));
                    Utils.runIntent(serviceContext, "MAPS", myApp.getGlobal_Lang());
                } catch (Exception e) {
                    Log.e(Utils.APP_TAG, "LOAD_BRANCHES \n" + e.toString());
                }

            }
        } catch (Exception e) {
            Utils.showConfirmationDialog(serviceContext,
                    "Utils.processWebResult\n"
                            + "Context = " + serviceContext + "\n"
                            // + "serviceResult = " + serviceResult + "\n"
                            + e.toString(), serviceContext.getResources().getString(R.string.app_name));
        }
    }

    public static String doInsert(Context context, JSONArray items) {
        boolean flag = true;
        try {
            for (int i = 0; i < items.length(); i++) {
                JSONObject c = items.getJSONObject(i);
                String cmd = c.getString("cmd");
                flag = runSQL(context, cmd);
                // Log.e(Utils.APP_TAG,cmd);
                if (!flag) {
                    Log.e(Utils.APP_TAG, "Flag=false : node content: " + cmd);
                    return "-1:" + "doInsert error";
                }
            }
            return "1";
        } catch (final JSONException e) {
            Log.e(Utils.APP_TAG, "Json parsing error: " + e.getMessage());
            return "-1:" + "Json parsing error";
        }
    }

    public static class runFunctionAsync extends AsyncTask<String, Integer, String> {
        final PyesApp myApp;
        final Context serviceContext;
        final int requestType;
        String account_no;
        String service;
        String trans_type;// 0:default run service 1:query of phone
        String xmlVersion;
        boolean withProgressDialog;
        ProgressDialog waitingDialog;
        String serviceResult;

        public runFunctionAsync(Context serviceContext, String account_no, String service, String trans_type,
                                String xmlVersion, int requestType, boolean withProgressDialog) {
            this.myApp = ((PyesApp) serviceContext.getApplicationContext());
            this.serviceContext = serviceContext;
            this.account_no = account_no;
            this.service = service;
            this.trans_type = trans_type;
            this.xmlVersion = xmlVersion;
            this.requestType = requestType;
            this.withProgressDialog = withProgressDialog;
            this.serviceResult = null;
        }

        @Override
        protected void onPreExecute() {
            if (withProgressDialog) {
                final Typeface myFont = Typeface.createFromAsset(serviceContext.getAssets(), "font/jannat_bold.otf");
                if (requestType != Utils.UPDATE_APP) {
                    waitingDialog = new ProgressDialog(serviceContext, R.style.ProgressDialog);
                    waitingDialog.getWindow().setBackgroundDrawable(serviceContext.getResources().getDrawable(R.drawable.l_turquoise));
                    waitingDialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                    if ((requestType == Utils.DOWNLOAD_SERVICES_WEB) || (requestType == Utils.DOWNLOAD_SERVICES_LOCAL)) {
                        waitingDialog.setMessage(serviceContext.getResources().getString(R.string.download_pyes_services));
                    } else if (requestType == Utils.GET_CLIENT_SERVICES) {
                        waitingDialog.setMessage(serviceContext.getResources().getString(R.string.update_progress));
                    } else if (requestType == Utils.GET_BRANCHESAGENT_SERVICES) {
                        waitingDialog.setMessage(serviceContext.getResources().getString(R.string.download_branches_services));
                    } else if (requestType == Utils.GET_TELECOM_SERVICES) {
                        waitingDialog.setMessage(serviceContext.getResources().getString(R.string.download_telecoms_services));
                    } else if (requestType == Utils.LOAD_CONTACT_LIST) {
                        waitingDialog.setMessage(serviceContext.getResources().getString(R.string.update_progress));
                    } else {
                        waitingDialog.setMessage(serviceContext.getResources().getString(R.string.request_progress));
                    }
                    TextView tv = new TextView(serviceContext);
                    tv.setTextColor(serviceContext.getResources().getColor(R.color.color_gold));
                    tv.setPadding(5, 5, 5, 5);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                    tv.setTextSize(20);
                    tv.setTypeface(myFont);
                    tv.setText(this.serviceContext.getResources().getString(R.string.app_name));
                    waitingDialog.setCustomTitle(tv);
                    waitingDialog.setIndeterminate(true);
                    try {
                        waitingDialog.setIndeterminateDrawable(serviceContext.getResources().getDrawable(R.drawable.progressbar_oval));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    waitingDialog = new ProgressDialog(this.serviceContext);
                    waitingDialog.setTitle(serviceContext.getResources().getString(R.string.app_name));
                    waitingDialog.setMessage(serviceContext.getResources().getString(R.string.download_and_install_pyes));
                    waitingDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    waitingDialog.setIndeterminate(false);
                    waitingDialog.setMax(100);
                }
                waitingDialog.setCancelable(false);
                waitingDialog.setCanceledOnTouchOutside(false);
                waitingDialog.show();
            }
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            waitingDialog.setProgress(values[0]);
        }

        @Override
        protected String doInBackground(String... option) {
            try {
                if ((requestType == Utils.GET_BALANCE)
                        || (requestType == Utils.REGISTER)
                        || (requestType == Utils.GET_APP_VERSION)
                        || (requestType == Utils.GET_XML_VERSION_UPDATE)
                        || (requestType == Utils.GET_XML_VERSION)
                        || (requestType == Utils.GET_CLIENT_SERVICES)
                        || (requestType == Utils.TEST)) {
                    serviceResult = Utils.sendWebRequest(serviceContext, account_no, service, trans_type, requestType, "run");
                } else if (requestType == Utils.SEND_TAGS) {
                    serviceResult = Utils.sendOneSignalTags(serviceContext);
                } else if (requestType == Utils.SEND_CMD) {
                    serviceResult = Utils.sendWebRequest(serviceContext, account_no, service, trans_type, requestType, "run");
                } else if (requestType == Utils.DOWNLOAD_SERVICES_LOCAL) {
                    serviceResult = Utils.readXMLIntoDatabase(serviceContext, "INSERTSQL");
                } else if (requestType == Utils.DOWNLOAD_SERVICES_WEB) {
                    serviceResult = Utils.sendWebRequest(serviceContext, account_no, service, trans_type, requestType, "get_services");
                } else if (requestType == Utils.GET_BRANCHESAGENT_SERVICES) {
                    serviceResult = Utils.sendWebRequest(serviceContext, account_no, service, trans_type, requestType, "get_br");
                } else if (requestType == Utils.GET_TELECOM_SERVICES) {
                    serviceResult = Utils.sendWebRequest(serviceContext, account_no, service, trans_type, requestType, "get_telecom");
                } else if (requestType == Utils.GET_STATIC_DATA) {
                    serviceResult = Utils.sendWebRequest(serviceContext, account_no, service, trans_type, requestType, "get_static");
                } else if (requestType == Utils.LOAD_CONTACT_LIST) {
                    serviceResult = Utils.loadContactList(serviceContext);
                } else if (requestType == Utils.LOAD_BRANCHES) {
                    myApp.setGlobal_BranchesForMap(Utils.getBranchList(serviceContext));
                    serviceResult = "1";
                }
                return serviceResult;
            } catch (Exception e) {
                e.printStackTrace();
                serviceResult = "2";
                return serviceResult;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (withProgressDialog) waitingDialog.dismiss();
                processWebResult(serviceContext, account_no, requestType, result);
            } catch (Exception e) {
                Utils.showConfirmationDialog(serviceContext,
                        "runFunctionAsync.onPostExecute\n"
                                + "Context = " + serviceContext + "\n"
                                + "Account = " + account_no + "\n"
                                + "requestType = " + requestType + "\n"
                                + "Result = " + result + "\n"
                                + e.toString(), serviceContext.getResources().getString(R.string.app_name));
            }
        }
    }

}
