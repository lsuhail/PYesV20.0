package dev.alamalbank.amb;


import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dev.alamalbank.amb.Util.BaseActivity;
import dev.alamalbank.amb.Util.Utils;

import java.net.URLEncoder;

public class Web_View extends BaseActivity {

    private String mMainServicesCode;
    private String mDetailServicesCode;
    private String service_code;
    private String m_password;
    private String m_title;
    private WebView wv_web_view;
    private boolean mEnableFavorite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Bundle b = this.getIntent().getExtras();
            if (b != null) {
                service_code = b.getString("service_code");
                m_title = b.getString("title");
                mEnableFavorite = b.getBoolean("enableFavorite");
            }
            mMainServicesCode = myApp.getGlobal_Last_Service_CODE();
            mDetailServicesCode = service_code;
            m_password = myApp.getGlobal_Password();
            initComponent();
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, e.toString(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.web_view;
    }



    @Override
    public void initComponent() {
        try {

            wv_web_view = (WebView) findViewById(R.id.wv_web_view);
            WebSettings webSettings=wv_web_view.getSettings();
            webSettings.setJavaScriptEnabled(true);
            //wv_web_view.setWebViewClient(new WebViewClient());
            String apk = Utils.getColumnValue(this, new String(Base64.decode(getResources().getString(R.string.apk), Base64.NO_WRAP)));
            String scheme = Utils.getColumnValue(this, "select ws_scheme from home");
            String host = Utils.getColumnValue(this, "select ws_host from home");
            String port = Utils.getColumnValue(this, "select ws_port from home");
            String path = Utils.getColumnValue(this, "select app_path from home") + "account-statement1";
            String url = scheme + "://" + host + ":" + port + path ;

            //StringBuffer buffer=new StringBuffer("http://ftpamb.dyndns.org:8080/ords/mcm/r/pyes/account-statement1");
            StringBuffer buffer=new StringBuffer(url);
            buffer.append("?P800_COM_ID="+ URLEncoder.encode(myApp.getGlobal_Com_Id()));
            buffer.append("?P800_CODE="+ URLEncoder.encode(apk));
            Log.e(Utils.APP_TAG,"http://ftpamb.dyndns.org:8080/ords/mcm/r/pyes/account-statement1");
            Log.e(Utils.APP_TAG,buffer.toString());
          //  buffer.append("&cs="+URLEncoder.encode("1Yk-kcnYm--drtrj3m760rSf6whpYuUBDmYVEzglq0HztX5HChBYXHDFLrOtLAlgfvM1POuF49cWJI1tbIVILQw"));
            //buffer.append("act="+URLEncoder.encode("readFileAndPrint"));
            wv_web_view.loadUrl(buffer.toString());
            //wv_web_view.loadUrl("http://82.114.179.249:8080/ords/mcm/r/pyes/account-statement/");


        } catch (Exception e) {
            e.printStackTrace();
            Utils.showConfirmationDialog(this, "Web_View.init \n" + e.toString(), "");
        }
    }
    @Override
    public void onBackPressed() {
        if (wv_web_view.canGoBack() ) {
            wv_web_view.goBack();
        }else{
            super.onBackPressed();
        }
    }
    @Override
    public void onClick(View v) {

    }
}