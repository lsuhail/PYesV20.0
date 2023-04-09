package dev.alamalbank.amb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import dev.alamalbank.amb.Util.BaseActivity;
import dev.alamalbank.amb.Util.Utils;

public class SendService extends BaseActivity {
    private Button b_favorite_add;
    private TextView tv_account_no;
    private Spinner sp_account_no;
    private LinearLayout la_detail_send_scrollview;
    private String mMainServicesCode, mDetailServicesCode, m_password, service_code, m_title;
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
            Utils.buildLayout(SendService.this, la_detail_send_scrollview, service_code, m_language);
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, e.toString(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.sendservice;
    }

    private void initHeader() {
        TextView tv_title = (TextView) Utils.populateView(this, "TextView", R.id.tv_title, 0);
        assert tv_title != null;
        tv_title.setText(m_title);
        tv_account_no = (TextView) Utils.populateView(this, "TextView", R.id.tv_account_no, 0);
        assert tv_account_no != null;
        sp_account_no = (Spinner) Utils.populateView(this, "Spinner", R.id.sp_account_no, 0);
        Utils.populateAccountSpinnerAdapter(this, sp_account_no, null);
        Button b_ok = (Button) Utils.populateView(this, "Button", R.id.b_ok, 0);
        assert b_ok != null;
        b_favorite_add = (Button) Utils.populateView(this, "Button", R.id.b_favorite_add, 0);
        if (!mEnableFavorite) {
            assert b_favorite_add != null;
            b_favorite_add.setVisibility(View.INVISIBLE);
        }
        Button b_cancel = (Button) Utils.populateView(this, "Button", R.id.b_cancel, 0);
        assert b_cancel != null;


    }

    @Override
    public void initComponent() {
        try {
            initHeader();
            la_detail_send_scrollview = findViewById(R.id.la_detail_send_scrollview);

            Utils.setBankBackgroundLogo(this,myApp.getGlobal_Com_Id());

        } catch (Exception e) {
            e.printStackTrace();
            Utils.showConfirmationDialog(this, "Detail Send.init \n" + e.toString(), "");
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        String m_Account = Utils.convertArDigitToEn(sp_account_no.getItemAtPosition(sp_account_no.getSelectedItemPosition()).toString());
        m_Account = m_Account.substring(0, m_Account.indexOf(":"));
        switch (v.getId()) {
            case R.id.b_ok:
                if (myApp.getGlobal_OperationONOFF()) {
                    Utils.sendService(SendService.this, la_detail_send_scrollview, m_title, service_code,
                            myApp.getGlobal_Com_Id(),
                            tv_account_no.getText().toString(),
                            m_Account,
                            m_password);
                } else {
                    Utils.showConfirmationDialog(this, getResources().getString(R.string.new_operation_error), getResources().getString(R.string.app_name));
                }
                break;
            case R.id.b_favorite_add:
                if (Utils.addedToFavorite(SendService.this, mMainServicesCode, mDetailServicesCode)) {
                    b_favorite_add.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.b_cancel:
                if (myApp.getGlobal_OperationONOFF()) {
                    overridePendingTransition(R.anim.no_animate, R.anim.slide_out);
                    finish();
                } else {
                    Utils.showConfirmationDialog(this, getResources().getString(R.string.new_operation_error), getResources().getString(R.string.app_name));
                }
                break;
        }
    }
}
