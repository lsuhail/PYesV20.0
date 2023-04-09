package dev.alamalbank.amb;

import static dev.alamalbank.amb.Util.FlutterSingleton.flutterEngine;
import static dev.alamalbank.amb.Util.REFERENCE_APP.Shar_Pre_Lang;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Locale;

import dev.alamalbank.amb.Util.DataUser_SharedPreferences;
import dev.alamalbank.amb.Util.FlutterSingleton;
import dev.alamalbank.amb.Util.LANG;
import dev.alamalbank.amb.Util.REFERENCE_APP;
import dev.alamalbank.amb.Util.Utils;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.android.FlutterActivityLaunchConfigs;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

public class Start extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        new REFERENCE_APP(Start.this);
        new LANG(this);

        try {
            HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.start);
            Animation animTogether = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.rotate_scale);
            ImageView logo_start = findViewById(R.id.logo_start);
            logo_start.setAnimation(animTogether);


            animTogether.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation arg0) {
                    FlutterSingleton inst = FlutterSingleton.getInstance(Start.this,"/start");

                }

                @Override
                public void onAnimationRepeat(Animation arg0) {
                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    /*finish();
                  */
                    DataUser_SharedPreferences shar;
                    shar=new DataUser_SharedPreferences(Start.this);
                    try {



                        if ( shar.getLOGIN() !="")
                        {

                            Intent mainactivity = new Intent(Start.this,LoginScreen.class);
 startActivity(mainactivity);


                            finish();

                        }
                        else
                        { /*Start.this.startActivity(
                                FlutterActivity.withCachedEngine("Engine")
                                        .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                                        .build(Start.this));*/

                            Intent mainactivity = new Intent(Start.this,ConfigActivity.class);
                            startActivity(mainactivity);


                            finish();
                        }


                    } catch (Exception e) {
                        Log.e(Utils.APP_TAG, e.toString());

                    }
                }
            });


        } catch (Exception e) {
            Log.e(Utils.APP_TAG, e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
