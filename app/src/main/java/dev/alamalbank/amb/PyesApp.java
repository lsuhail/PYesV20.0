package dev.alamalbank.amb;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import dev.alamalbank.amb.Util.Utils;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OSNotificationReceivedEvent;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class PyesApp extends Application {

    private Timer mActivityTransitionTimer;
    private TimerTask mActivityTransitionTimerTask;
    private boolean wasActivityInBackground;
    private String Global_Lang;
    private String Global_Com_name;
    private String Global_Com_Id;
    private String Global_User_name;
    private String Global_User_Account;
    private String Global_Password;
    private String Global_LastLogin;
    private String Global_TempVariable;
    private String Global_Last_Service_CODE;
    private String Global_last_receive_msg;
    private Context LastPauseActivity;
    private Context LastRunActivity;
    private Boolean Global_Confirmation;
    private Boolean Global_ForceLogin_Flag;
    private Boolean Global_ExitFromApplication;
    private Boolean Global_OperationONOFF;
    private HashMap<Integer, String[]> Global_ComponentMap;
    private ArrayList<HashMap<String, String>> Global_BranchesForMap;
    private String Global_S;
    private String Global_H;
    private String Global_P;
    private String Global_U;
    private String Global_A;
    private String Global_X;
    private String Global_STORAGE_PATH;
    private String Global_MapTitle;
    private String Global_MapAddress;
    private String Global_MapLong;
    private String Global_MapLati;
    private String Global_Position;
    private int Global_NotificationsCounter = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            if (Utils.createTablesIfNotExists(context)) {
               init();
         /*       OneSignal.startInit(this)
                        .setNotificationReceivedHandler(new ExampleNotificationReceivedHandler())
                        .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                        .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                        .unsubscribeWhenNotificationsAreDisabled(true)
                        .init();*/

                String m_language = Utils.getColumnValue(this, "select DEFAULT_LANG from HOME ");
                if (m_language == null) m_language = "ar";
                setGlobal_Lang(m_language);
                setGlobal_Com_Id(Utils.getColumnValue(this, "select COMPANY_ID from HOME "));
                setGlobal_Com_name(Utils.getColumnValue(this, "select COMPANY_NAME_AR from HOME "));
            } else {
                Utils.showToast(context, "PYesApp.OnCreate\n" + getResources().getString(R.string.database_create_error));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showToast(context, "PYesApp.OnCreate\n" + e.toString());
        }
    }

    private Boolean init() {
        try {
            OneSignal.initWithContext(this);
            OneSignal.setAppId("a8b694bc-0346-4f99-b744-a6e3ba6ec4dc");
            OneSignal.setNotificationWillShowInForegroundHandler(new ExampleNotificationReceivedHandler());
            OneSignal.setNotificationOpenedHandler(new ExampleNotificationOpenedHandler());
            OneSignal.promptForPushNotifications();
            return true;
        }
        catch (Exception e) {
            return true;

        }
    }

    public void startActivityTransitionTimer() {
        this.mActivityTransitionTimer = new Timer();
        this.mActivityTransitionTimerTask = new TimerTask() {
            public void run() {
                setWasActivityInBackground(true);
            }
        };
        this.mActivityTransitionTimer.schedule(mActivityTransitionTimerTask,
                Utils.MAX_ACTIVITY_TRANSITION_TIME_MS);
    }

    public void stopActivityTransitionTimer() {
        if (this.mActivityTransitionTimerTask != null) {
            this.mActivityTransitionTimerTask.cancel();
        }
        if (this.mActivityTransitionTimer != null) {
            this.mActivityTransitionTimer.cancel();
        }
        setWasActivityInBackground(false);
    }

    public boolean getWasActivityInBackground() {
        return wasActivityInBackground;
    }

    public void setWasActivityInBackground(boolean wasActivityInBackground) {
        this.wasActivityInBackground = wasActivityInBackground;
    }

    public String getGlobal_STORAGE_PATH() {
        return Global_STORAGE_PATH;
    }

    public void setGlobal_STORAGE_PATH(String Global_STORAGE_PATH) {
        this.Global_STORAGE_PATH = Global_STORAGE_PATH;
    }

    public String getGlobal_Lang() {
        return Global_Lang;
    }

    public void setGlobal_Lang(String Global_Lang) {
        this.Global_Lang = Global_Lang;
    }

    public String getGlobal_User_Account() {
        return Global_User_Account;
    }

    public void setGlobal_User_Account(String Global_User_Account) {
        this.Global_User_Account = Global_User_Account;
    }

    public String getGlobal_Com_Id() {
        return Global_Com_Id;
    }

    public void setGlobal_Com_Id(String Global_Com_Id) {
        this.Global_Com_Id = Global_Com_Id;
    }
    public String getGlobal_Com_name() {
        return Global_Com_name;
    }

    public void setGlobal_Com_name(String Global_Com_name) {
        this.Global_Com_name = Global_Com_name;
    }
    public String getGlobal_User_name() {
        return Global_User_name;
    }

    public void setGlobal_User_name(String Global_User_name) {
        this.Global_User_name = Global_User_name;
    }
    public String getGlobal_Password() {
        return Global_Password;
    }

    public void setGlobal_Password(String Global_Password) {
        this.Global_Password = Global_Password;
    }

    public String getGlobal_LastLogin() {
        return Global_LastLogin;
    }

    public void setGlobal_LastLogin(String Global_LastLogin) {
        this.Global_LastLogin = Global_LastLogin;
    }

    public String getGlobal_TempVariable() {
        return Global_TempVariable;
    }

    public void setGlobal_TempVariable(String Global_TempVariable) {
        this.Global_TempVariable = Global_TempVariable;
    }

    public String getGlobal_Last_Service_CODE() {
        return Global_Last_Service_CODE;
    }

    public void setGlobal_Last_Service_CODE(String Global_Last_Service_CODE) {
        this.Global_Last_Service_CODE = Global_Last_Service_CODE;
    }

    public String getGlobal_last_receive_msg() {
        return Global_last_receive_msg;
    }

    public void setGlobal_last_receive_msg(String Global_last_receive_msg) {
        this.Global_last_receive_msg = Global_last_receive_msg;
    }

    public Context getLastPauseActivity() {
        return LastPauseActivity;
    }

    public void setLastPauseActivity(Context LastPauseActivity) {
        this.LastPauseActivity = LastPauseActivity;
    }

    public Context getLastRunActivity() {
        return LastRunActivity;
    }

    public void setLastRunActivity(Context LastRunActivity) {
        this.LastRunActivity = LastRunActivity;
    }

    public Boolean getGlobal_Confirmation() {
        return Global_Confirmation;
    }

    public void setGlobal_Confirmation(Boolean Global_Confirmation) {
        this.Global_Confirmation = Global_Confirmation;
    }

    public Boolean getGlobal_ForceLogin_Flag() {
        return Global_ForceLogin_Flag;
    }

    public void setGlobal_ForceLogin_Flag(Boolean Global_ForceLogin_Flag) {
        this.Global_ForceLogin_Flag = Global_ForceLogin_Flag;
    }

    public void setGlobal_GoTOPrevActivity(Boolean Global_GoTOPrevActivity) {
    }

    public Boolean getGlobal_ExitFromApplication() {
        return Global_ExitFromApplication;
    }

    public void setGlobal_ExitFromApplication(Boolean Global_ExitFromApplication) {
        this.Global_ExitFromApplication = Global_ExitFromApplication;
    }

    public Boolean getGlobal_OperationONOFF() {
        return Global_OperationONOFF;
    }

    public void setGlobal_OperationONOFF(Boolean Global_OperationONOFF) {
        this.Global_OperationONOFF = Global_OperationONOFF;
    }

    public HashMap<Integer, String[]> getGlobal_ComponentMap() {
        return Global_ComponentMap;
    }

    public void setGlobal_ComponentMap(HashMap<Integer, String[]> Global_ComponentMap) {
        this.Global_ComponentMap = Global_ComponentMap;
    }

    public ArrayList<HashMap<String, String>> getGlobal_BranchesForMap() {
        return Global_BranchesForMap;
    }

    public void setGlobal_BranchesForMap(ArrayList<HashMap<String, String>> Global_BranchesForMap) {
        this.Global_BranchesForMap = Global_BranchesForMap;
    }

    public String getGlobal_S() {
        return Utils.decoding(Global_S);
    }

    public void setGlobal_S(String Globel_S) {
        this.Global_S = Globel_S;
    }

    public String getGlobal_H() {
        return Utils.decoding(Global_H);
    }

    public void setGlobal_H(String Globel_H) {
        this.Global_H = Globel_H;
    }

    public String getGlobal_P() {
        return Utils.decoding(Global_P);
    }

    public void setGlobal_P(String Globel_P) {
        this.Global_P = Globel_P;
    }


    public String getGlobal_U() {
        return Utils.decoding(Global_U);
    }

    public void setGlobal_U(String Globel_U) {
        this.Global_U = Globel_U;
    }

    public String getGlobal_A() {
        return Utils.decoding(Global_A);
    }

    public void setGlobal_A(String Globel_A) {
        this.Global_A = Globel_A;
    }

    public String getGlobal_X() {
        return Utils.decoding(Global_X);
    }

    public void setGlobal_X(String Global_X) {
        this.Global_X = Global_X;
    }

    public String getGlobal_Position() {
        return Global_Position;
    }

    public void setGlobal_Position(String Global_Position) {
        this.Global_Position = Global_Position;
    }

    public String getGlobal_MapTitle() {
        return Global_MapTitle;
    }

    public void setGlobal_MapTitle(String Global_MapTitle) {
        this.Global_MapTitle = Global_MapTitle;
    }

    public String getGlobal_MapAddress() {
        return Global_MapAddress;
    }

    public void setGlobal_MapAddress(String Global_MapAddress) {
        this.Global_MapAddress = Global_MapAddress;
    }

    public String getGlobal_MapLong() {
        return Global_MapLong;
    }

    public void setGlobal_MapLong(String Global_MapLong) {
        this.Global_MapLong = Global_MapLong;
    }

    public String getGlobal_MapLati() {
        return Global_MapLati;
    }

    public void setGlobal_MapLati(String Global_MapLati) {
        this.Global_MapLati = Global_MapLati;
    }

    public int getGlobal_NotificationsCounter() {
        return Global_NotificationsCounter;
    }

    public void setGlobal_NotificationsCounter(int Global_NotificationsCounter) {
        this.Global_NotificationsCounter = Global_NotificationsCounter;
    }
    private class ExampleNotificationOpenedHandler implements OneSignal.OSNotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.


        @Override
        public void notificationOpened(OSNotificationOpenedResult osNotificationOpenedResult) {
            OSNotificationAction.ActionType actionType = osNotificationOpenedResult.getAction().getType();
            JSONObject data = osNotificationOpenedResult.getNotification().getAdditionalData();
            String launchUrl = osNotificationOpenedResult.getNotification().getLaunchURL(); // update docs launchUrl

            String customKey;
            String openURL = null;
            Object activityToLaunch = Main_Activity.class;

            if (data != null) {
                Log.i("RASTAG", data.toString());
                customKey = data.optString("customkey", null);
                openURL = data.optString("openURL", null);

                if (customKey != null)
                    Log.i("RASTAG", "customkey set with value: " + customKey);

                if (openURL != null)
                    Log.i("RASTAG", "openURL to webview with URL value: " + openURL);
            }

            if (actionType == OSNotificationAction.ActionType.ActionTaken) {
                Log.i("RASTAG", "Button pressed with id: " + osNotificationOpenedResult.getAction().getActionId());

                if (osNotificationOpenedResult.getAction().getActionId().equals("id1")) {
                    Log.i("RASTAG", "button id called: " + osNotificationOpenedResult.getAction().getActionId());
                    activityToLaunch = Main_Activity.class;//GreenActivity.class;
                } else
                    Log.i("OneSignalExample", "button id called: " + osNotificationOpenedResult.getAction().getActionId());
            }
          /*  // The following can be used to open an Activity of your choice.
            // Replace - getApplicationContext() - with any Android Context.
            // Intent intent = new Intent(getApplicationContext(), YourActivity.class);
            Intent intent = new Intent(getApplicationContext(), (Class<?>) activityToLaunch);
            // intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("openURL", openURL);
            Log.i("RASTAG", "openURL = " + openURL);
            // startActivity(intent);
            startActivity(intent);
*/
            // Add the following to your AndroidManifest.xml to prevent the launching of your main Activity
            //   if you are calling startActivity above.
        /*
           <application ...>
             <meta-data android:name="com.onesignal.NotificationOpened.DEFAULT" android:value="DISABLE" />
           </application>
        */
        }
    }

    private class ExampleNotificationReceivedHandler implements OneSignal.OSNotificationWillShowInForegroundHandler {

        @Override
        public void notificationWillShowInForeground(OSNotificationReceivedEvent osNotificationReceivedEvent) {
            JSONObject data = osNotificationReceivedEvent.getNotification().getAdditionalData();
            String notificationID = osNotificationReceivedEvent.getNotification().getNotificationId();
            String title =osNotificationReceivedEvent.getNotification().getTitle();
            String body = osNotificationReceivedEvent.getNotification().getBody();

            /*String smallIcon = notification.payload.smallIcon;
            String largeIcon = notification.payload.largeIcon;
            String bigPicture = notification.payload.bigPicture;
            String smallIconAccentColor = notification.payload.smallIconAccentColor;
            String sound = notification.payload.sound;
            String ledColor = notification.payload.ledColor;
            int lockScreenVisibility = notification.payload.lockScreenVisibility;
            String groupKey = notification.payload.groupKey;
            String groupMessage = notification.payload.groupMessage;
            String fromProjectNumber = notification.payload.fromProjectNumber;
            String rawPayload = notification.payload.rawPayload;
            */

            String customKey;
            //if (data!=null)
            if (body != null) {
                try {
                    String strDate = Utils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
                    Utils.runSQL(getApplicationContext(), "insert into notifications (type,id,priority,datetime,title,note,status) values(1,'" + notificationID + "',1,'" + strDate + "','" + title + "','" + body + "',0)");

                    Toast.makeText(getApplicationContext(), title + "\n" + body, Toast.LENGTH_LONG).show();
                    setGlobal_NotificationsCounter(getGlobal_NotificationsCounter() + 1);

                    //AmbMobileMenu.getInstance().onResume();

                  /* customKey = data.optString("customkey", null);
                   if (customKey != null) {
                       Log.e("RASTAG", "customKey set with value: " + customKey);
                   }*/
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


}


