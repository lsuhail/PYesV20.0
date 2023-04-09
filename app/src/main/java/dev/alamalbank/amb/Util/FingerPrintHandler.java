package dev.alamalbank.amb.Util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;

import androidx.core.app.ActivityCompat;

import android.widget.TextView;

import dev.alamalbank.amb.ForceLogin;
import dev.alamalbank.amb.PyesApp;
import dev.alamalbank.amb.R;

@TargetApi(Build.VERSION_CODES.M)
public class FingerPrintHandler extends FingerprintManager.AuthenticationCallback {
    private final PyesApp myApp;
    private final Context context;


    public FingerPrintHandler(Context mContext) {
        context = mContext;
        myApp = ((PyesApp) context.getApplicationContext());

    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {

        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            this.update(context.getResources().getString(R.string.FINGERPRINT_permission), false);//"Permission not granted to use fingerprint scanner in this App.",false);
            return;
        }
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    //onAuthenticationError is called when a fatal error has occurred. It provides the error code and error message as its parameters//
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        //I’m going to display the results of fingerprint authentication as a series of toasts.
        //Here, I’m creating the message that’ll be displayed if an error occurs//
        this.update(context.getResources().getString(R.string.FINGERPRINT_Authentication_error), false);
    }

    @Override
    //onAuthenticationFailed is called when the fingerprint doesn’rotate_scale match with any of the fingerprints registered on the device//
    public void onAuthenticationFailed() {
        this.update(context.getResources().getString(R.string.FINGERPRINT_Authentication_failed), false);
    }

    @Override
    //onAuthenticationHelp is called when a non-fatal error has occurred. This method provides additional information about the error,
    //so to provide the user with as much feedback as possible I’m incorporating this information into my toast//
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update(context.getResources().getString(R.string.FINGERPRINT_Authentication_help), false);
    }

    @Override
    //onAuthenticationSucceeded is called when a fingerprint has been successfully matched to one of the fingerprints stored on the user’s device//
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {
        this.update(context.getResources().getString(R.string.FINGERPRINT_Authentication_success), true);
    }

    private void update(String message, boolean authenticationResult) {

        if (context.getClass().toString().equalsIgnoreCase("class dev.alamalbank.amb.LoginScreen")) {
            TextView tv_fingerprint_note = ((Activity) context).findViewById(R.id.tv_fingerprint_note);
            tv_fingerprint_note.setText(message);
            if (!authenticationResult) {
                tv_fingerprint_note.setTextColor(Color.RED);
            } else {
                tv_fingerprint_note.setTextColor(Color.GREEN);
                Utils.call_MainMenu((Activity) context);
            }
        } else if (context.getClass().toString().equalsIgnoreCase("class dev.alamalbank.amb.ForceLogin")) {
            TextView tv_force_login_fingerprint_note = ((Activity) context).findViewById(R.id.tv_fingerprint_note);
            tv_force_login_fingerprint_note.setText(message);
            if (!authenticationResult) {
                tv_force_login_fingerprint_note.setTextColor(Color.RED);
            } else {
                tv_force_login_fingerprint_note.setTextColor(Color.GREEN);
                myApp.stopActivityTransitionTimer();
                myApp.setGlobal_ExitFromApplication(false);
                myApp.setLastPauseActivity(context);
                ForceLogin.getInstance().finish();
            }
        }
    }
}