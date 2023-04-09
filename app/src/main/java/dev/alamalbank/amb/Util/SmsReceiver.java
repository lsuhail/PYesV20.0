package dev.alamalbank.amb.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import dev.alamalbank.amb.PyesApp;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        try {
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];
                PyesApp myapp = ((PyesApp) context.getApplicationContext());
                String mGetOriginatingAddress = "";
                StringBuilder mGetMessageBody = new StringBuilder();
                String mMobile_no = Utils.getColumnValue(context, "select mobile_no from accounts");
                //TODO :Save Service no and Service Name For All Telecom
                String mLocalSMSProvider = Utils.getColumnValue(context, "select SERVICE_NO from service_provider where SP_PREFIX='" + mMobile_no.substring(0, 2) + "'");
                String mLocalSMSProvidershorName = "ALAMALBANK"; // must be save in the database and select it
                for (int i = 0; i < msgs.length; i++) {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    mGetOriginatingAddress = msgs[i].getOriginatingAddress().trim();
                    mGetMessageBody.append(msgs[i].getMessageBody());

                }

                if ((mMobile_no.length() > 0)) {
                    mGetOriginatingAddress = mGetOriginatingAddress.replace("+", "").replace(" ", "").trim();
                    if (((mGetOriginatingAddress.equalsIgnoreCase(mLocalSMSProvider))
                            || (mGetOriginatingAddress.trim().equalsIgnoreCase("ALAMALBANK"))
                            || (mGetOriginatingAddress.trim().equalsIgnoreCase("AMALBANK"))
                            || (mGetOriginatingAddress.trim().equalsIgnoreCase("777584443"))
                            || (mGetOriginatingAddress.trim().equalsIgnoreCase("967777584443"))
                    )) {
                        myapp.setGlobal_last_receive_msg(mGetMessageBody.toString().trim());
                        Utils.checkResponse(context, null, mGetMessageBody.toString().trim());

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showToast(context, e.toString());
        }
    }

}