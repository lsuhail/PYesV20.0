package dev.alamalbank.amb;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import dev.alamalbank.amb.Util.BaseActivity;
import dev.alamalbank.amb.Util.Utils;

public class Change_PWD extends BaseActivity {

    private Button b_favorite_add;
    private EditText ed_old_password, ed_new_password, ed_confirm_new_password;
    private Spinner sp_account_no;
    private String mMainServicesCode, mDetailServicesCode, dsrvcode, m_title, m_password;
    private boolean mEnableFavorite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Bundle b = this.getIntent().getExtras();
            if (b != null) {
                dsrvcode = b.getString("service_code");
                m_title = b.getString("title");
                mEnableFavorite = b.getBoolean("enableFavorite");
            }
            mMainServicesCode = myApp.getGlobal_Last_Service_CODE();
            mDetailServicesCode = dsrvcode;
            m_password = myApp.getGlobal_Password();

            initComponent();

        } catch (Exception e) {
            Utils.showConfirmationDialog(this, e.toString(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.change_pwd;
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

            TextView tv_old_password = (TextView) Utils.populateView(this, "TextView", R.id.tv_old_password, 0);
            ed_old_password = (EditText) Utils.populateView(this, "EditText", R.id.ed_old_password, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            TextView tv_new_password = (TextView) Utils.populateView(this, "TextView", R.id.tv_new_password, 0);
            ed_new_password = (EditText) Utils.populateView(this, "EditText", R.id.ed_new_password, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ed_new_password.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    textChanged("NEW", s);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
            });

            TextView tv_confirm_new_password = (TextView) Utils.populateView(this, "TextView", R.id.tv_confirm_new_password, 0);
            ed_confirm_new_password = (EditText) Utils.populateView(this, "EditText", R.id.ed_confirm_new_password, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ed_confirm_new_password.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    textChanged("Confirm", s);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Utils.showConfirmationDialog(this, "Change Password.init \n" + e.toString(), "");
        }
    }

    private void textChanged(String edType, Editable editText) {
        String newS = editText.toString();
        newS = Utils.convertArDigitToEn(newS);

        if ((edType.equalsIgnoreCase("NEW")) && (!Utils.check_Password_Validation(newS))) {
            ed_new_password.requestFocus();
            ed_new_password.setError(getResources().getString(R.string.password_incorrect));
        } else if ((edType.equalsIgnoreCase("Confirm")) && (!Utils.check_Password_Validation(newS))) {
            ed_confirm_new_password.requestFocus();
            ed_confirm_new_password.setError(getResources().getString(R.string.password_incorrect));
        }
    }

    public void changePWD() {

        String sendMSG = "", logMSG = "";
        String m_account_no = Utils.convertArDigitToEn(sp_account_no.getItemAtPosition(sp_account_no.getSelectedItemPosition()).toString());
        m_account_no = m_account_no.substring(0, m_account_no.indexOf(":"));
        String m_ed_old_password = Utils.convertArDigitToEn(ed_old_password.getText().toString().trim());
        String m_ed_new_password = Utils.convertArDigitToEn(ed_new_password.getText().toString().trim());
        String m_ed_confirm_new_password = Utils.convertArDigitToEn(ed_confirm_new_password.getText().toString().trim());

        if (myApp.getGlobal_OperationONOFF()) {
            if      (
                        (m_account_no.length() != 0)
                    && (m_ed_old_password.length() != 0)
                    && (m_ed_new_password.length() != 0)
                    && (m_ed_confirm_new_password.length() != 0)) {

                if  (m_ed_old_password.equals(Utils.decoding(m_password))
                        && m_ed_new_password.equals(m_ed_confirm_new_password)) {
                    if (Utils.check_Password_Validation(m_ed_new_password)) {
                        sendMSG = dsrvcode + ":" + myApp.getGlobal_Com_Id() + ":" + m_account_no + ":" + m_password + ":" + Utils.encoding(m_ed_new_password) + ":0";
                        logMSG = logMSG + m_title;

                        sendMSG = Utils.convertArDigitToEn(sendMSG);
                        logMSG = Utils.convertArDigitToEn(logMSG);
                        myApp.setGlobal_OperationONOFF(true);

                        Log.e(Utils.APP_TAG,sendMSG+"\nOld= "+Utils.decoding(m_password)+"\nnew= "+m_ed_new_password);
                        myApp.setGlobal_TempVariable(Utils.encoding(m_ed_new_password));
                        myApp.setLastRunActivity(Change_PWD.this);
                        Utils.sendCommand(Change_PWD.this, m_account_no, sendMSG, logMSG, true, "0",Utils.SEND_CMD);


                    } else {
                        ed_new_password.requestFocus();
                        ed_new_password.setError(getResources().getString(R.string.password_incorrect));
                    }
                } else if ((!m_ed_old_password.equals(Utils.decoding(m_password)))) {
                    ed_old_password.requestFocus();
                    ed_old_password.setError(getResources().getString(R.string.old_password_error));
                } else {
                    ed_confirm_new_password.requestFocus();
                    ed_confirm_new_password.setError(getResources().getString(R.string.new_password_error));
                }

            } else {
                Utils.showConfirmationDialog(this, getResources().getString(R.string.missing_info), getResources().getString(R.string.app_name));
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
                changePWD();
                //Utils.getAccountBalance(this, myApp.getGlobal_User_Account());
                break;
            case R.id.b_favorite_add:
                if (Utils.addedToFavorite(Change_PWD.this, mMainServicesCode, mDetailServicesCode)) {
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