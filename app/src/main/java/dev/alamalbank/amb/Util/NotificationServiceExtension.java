package dev.alamalbank.amb.Util;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import dev.alamalbank.amb.PyesApp;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationReceivedEvent;
import com.onesignal.OneSignal.OSRemoteNotificationReceivedHandler;

@SuppressWarnings("unused")
public class NotificationServiceExtension implements OSRemoteNotificationReceivedHandler {

    @Override
    public void remoteNotificationReceived(Context context, OSNotificationReceivedEvent notificationReceivedEvent) {

        OSNotification notification = notificationReceivedEvent.getNotification();
        JSONObject data = notification.getAdditionalData();
        String notificationID = notification.getNotificationId();
        String title = notification.getTitle();
        String body = notification.getBody();

        if (body != null) {
            try {
                String strDate = Utils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
                Log.e("latef","insering remoteNotificationReceived");
                String  noID = Utils.getColumnValue(context, "select id from notifications where datetime ='" + strDate + "'");
                /*       if(noID==null){*/
                Utils.runSQL(context.getApplicationContext(), "INSERT into notifications (type,id,priority,datetime,title,note,status) values(1,'" + notificationID + "',1,'" + strDate + "','" + title + "','" + body + "',0)");
                ((PyesApp) context.getApplicationContext()).setGlobal_NotificationsCounter(((PyesApp) context.getApplicationContext()).getGlobal_NotificationsCounter() + 1);

                //    }//                Toast.makeText(context.getApplicationContext(), title + "\n" + body, Toast.LENGTH_LONG).show();

                //AmbMobileMenu.getInstance().onResume();

                  /* customKey = data.optString("customkey", null);
                   if (customKey != null) {
                       Log.e("RASTAG", "customKey set with value: " + customKey);
                   }*/
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        // Example of modifying the notification's accent color
        /*OSMutableNotification mutableNotification = notification.mutableCopy();
        mutableNotification.setExtender(builder -> {

            // Sets the accent color to Green on Android 5+ devices.
            // Accent color controls icon and action buttons on Android 5+. Accent color does not change app title on Android 10+
            builder.setColor(new BigInteger("FF00FF00", 16).intValue());
            // Sets the notification Title to Red
            Spannable spannableTitle = new SpannableString(notification.getTitle());
            spannableTitle.setSpan(new ForegroundColorSpan(Color.RED),0,notification.getTitle().length(),0);
            builder.setContentTitle(spannableTitle);
            // Sets the notification Body to Blue
            Spannable spannableBody = new SpannableString(notification.getBody());
            spannableBody.setSpan(new ForegroundColorSpan(Color.BLUE),0,notification.getBody().length(),0);
            builder.setContentText(spannableBody);
            //Force remove push from Notification Center after 30 seconds
            builder.setTimeoutAfter(30000);
            return builder;
        });
        */
        Log.i("OneSignalExample", "Received Notification Data: " + body);

        // If complete isn't call within a time period of 25 seconds, OneSignal internal logic will show the original notification
        // To omit displaying a notification, pass `null` to complete()
        notificationReceivedEvent.complete(notification);

    }
}
