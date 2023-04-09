package dev.alamalbank.amb;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import dev.alamalbank.amb.Util.BaseActivity;
import dev.alamalbank.amb.Util.Utils;

public class Change_Email extends BaseActivity {
    private EditText ed_change_email;// ed_email_password;
    private Button b_favorite_add;
    private Spinner sp_account_no;
    private String mMainServicesCode;
    private String mDetailServicesCode;
    private String service_code;
    private String m_password;
    private String m_title;
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
        return R.layout.change_email;
    }

    private void initHeader() {
        TextView tv_title = (TextView) Utils.populateView(this, "TextView", R.id.tv_title, 0);
        assert tv_title != null;
        tv_title.setText(m_title);
        TextView tv_account_no = (TextView) Utils.populateView(this, "TextView", R.id.tv_account_no, 0);
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

            Utils.setBankBackgroundLogo(this,myApp.getGlobal_Com_Id());

            TextView tv_email = (TextView) Utils.populateView(this, "TextView", R.id.tv_email, 0);
           // TextView tv_email_password = (TextView) Utils.populateView(this, "TextView", R.id.tv_email_password, 0);
            ed_change_email = (EditText) Utils.populateView(this, "EditText", R.id.ed_change_email, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            //ed_email_password = (EditText) Utils.populateView(this, "EditText", R.id.ed_email_password, InputType.TYPE_TEXT_VARIATION_PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
            Utils.showConfirmationDialog(this, "Change email.init \n" + e.toString(), "");
        }
    }

    public void changeEmail() {

        String sendMSG = "", logMSG = "";
        String m_account_no = Utils.convertArDigitToEn(sp_account_no.getItemAtPosition(sp_account_no.getSelectedItemPosition()).toString());
        m_account_no = m_account_no.substring(0, m_account_no.indexOf(":"));
      //  String m_ed_emailpassword = Utils.convertArDigitToEn(ed_email_password.getText().toString());
        String m_ed_changeemail = Utils.convertArDigitToEn(ed_change_email.getText().toString());

        String m_email = Utils.getColumnValue(this, "select email from accounts where account_no='" + m_account_no + "'");

        if (myApp.getGlobal_OperationONOFF()) {
            if ((m_account_no.length() != 0) && (m_ed_changeemail.length() != 0)){
                    //&& (m_ed_emailpassword.length() != 0)) {
                //if ((m_ed_emailpassword.equals(m_password) || m_ed_emailpassword.equals(Utils.encoding(m_password)))) {
                    sendMSG = service_code  + ":" + myApp.getGlobal_Com_Id() + ":" + m_account_no + ":" + m_ed_changeemail + ":" + m_password + ":0";
                    logMSG = logMSG + m_title + "\n" + getResources().getString(R.string.msg_to) + " : " + m_ed_changeemail;
                    sendMSG = Utils.convertArDigitToEn(sendMSG);
                    logMSG = Utils.convertArDigitToEn(logMSG);

                    myApp.setGlobal_OperationONOFF(false);
                    myApp.setGlobal_TempVariable(m_ed_changeemail);

                    Utils.sendCommand(Change_Email.this, m_account_no, sendMSG, logMSG, true, "0",Utils.SEND_CMD);

                /*} else {
                    ed_email_password.requestFocus();
                    ed_email_password.setError(getResources().getString(R.string.password_error));

                }*/
            } else {
                if (m_ed_changeemail.length() == 0) {
                    ed_change_email.requestFocus();
                    ed_change_email.setError(getResources().getString(R.string.missing_info));
               /* } else if (m_ed_emailpassword.length() == 0) {
                    ed_email_password.requestFocus();
                    ed_email_password.setError(getResources().getString(R.string.missing_info));*/
                }
            }
        } else {
            Utils.showConfirmationDialog(this, getResources().getString(R.string.new_operation_error), getResources().getString(R.string.app_name));
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_ok:
                changeEmail();
                break;
            case R.id.b_favorite_add:
                if (Utils.addedToFavorite(Change_Email.this, mMainServicesCode, mDetailServicesCode)) {
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