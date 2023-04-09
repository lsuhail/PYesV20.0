package dev.alamalbank.amb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import dev.alamalbank.amb.Util.BaseActivity;
import dev.alamalbank.amb.Util.CaptureQR;
import dev.alamalbank.amb.Util.Utils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class Purchases extends BaseActivity {

    String m_password;
    private Button b_favorite_add;
    private TextView tv_pos_code;
    private TextView tv_pos_name;
    private TextView tv_pos_amount;
    private EditText ed_pos_code, ed_pos_name, ed_pos_amount;//ed_pos_password;
    private Spinner sp_account_no;
    private String mMainServicesCode;
    private String mDetailServicesCode;
    private String service_code;
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
            Utils.showConfirmationDialog(this, "Purchases\n" + e.toString(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.purchases;
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

            tv_pos_code = (TextView) Utils.populateView(Purchases.this, "TextView", R.id.tv_pos_code, 0);
            ed_pos_code = (EditText) Utils.populateView(Purchases.this, "EditText", R.id.ed_pos_code, InputType.TYPE_CLASS_NUMBER);

            tv_pos_name = (TextView) Utils.populateView(Purchases.this, "TextView", R.id.tv_pos_name, 0);
            ed_pos_name = (EditText) Utils.populateView(Purchases.this, "EditText", R.id.ed_pos_name, InputType.TYPE_CLASS_TEXT);

            tv_pos_amount = (TextView) Utils.populateView(Purchases.this, "TextView", R.id.tv_pos_amount, 0);

            ed_pos_amount = (EditText) Utils.populateView(Purchases.this, "EditText", R.id.ed_pos_amount, InputType.TYPE_CLASS_NUMBER);
            ed_pos_amount.addTextChangedListener(Utils.onTextChangedListener(ed_pos_amount));

           /* TextView tv_pos_password = (TextView) Utils.populateView(Purchases.this, "TextView", R.id.tv_pos_password, 0);
            assert tv_pos_password != null;

            ed_pos_password = (EditText) Utils.populateView(Purchases.this, "EditText", R.id.ed_pos_password, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            */
            ImageView b_qr_scan = (ImageView) Utils.populateView(Purchases.this, "ImageView", R.id.b_qr_scan, 0);
            assert b_qr_scan != null;
            b_qr_scan.setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
            Utils.showConfirmationDialog(this, "Purchases.init \n" + e.toString(), "");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String scanContent;
        if (requestCode == Utils.REQUEST_SCAN_MODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
                if (scanningResult.getContents() != null) {
                    scanContent = scanningResult.getContents();
                    String pos_no = scanContent.substring(0, scanContent.indexOf(":")).trim();
                    if (Utils.isNumeric(pos_no)) {
                        String pos_name = scanContent.substring(scanContent.indexOf(":") + 1).trim();
                        ed_pos_code.setText(pos_no);
                        ed_pos_name.setText(pos_name);
                    } else {
                        Utils.showToast(this, getResources().getString(R.string.pos_code_error));
                    }

                } else {
                    Utils.showToast(this, getResources().getString(R.string.pos_code_error));
                    ed_pos_code.setText("");
                    ed_pos_name.setText("");
                }
            } else {
                Utils.showToast(this, getResources().getString(R.string.pos_code_error));
                ed_pos_code.setText("");
                ed_pos_name.setText("");
            }
        }
    }

/*
    public void scanQRCode() {
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.setPrompt(getResources().getString(R.string.app_name) + " Scan ");
        scanIntegrator.setBeepEnabled(true);
        scanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        scanIntegrator.setCaptureActivity(CaptureQR.class);
        scanIntegrator.setOrientationLocked(true);
        scanIntegrator.setBarcodeImageEnabled(true);
        scanIntegrator.initiateScan();
    }
*/

    public void purchases() {
        String sendMSG, logMSG;
        String m_account_no = Utils.convertArDigitToEn(sp_account_no.getItemAtPosition(sp_account_no.getSelectedItemPosition()).toString());
        m_account_no = m_account_no.substring(0, m_account_no.indexOf(":"));
        String m_ed_pos_code = Utils.convertArDigitToEn(ed_pos_code.getText().toString());
        String m_ed_pos_amount = Utils.convertArDigitToEn(ed_pos_amount.getText().toString());
        // String m_ed_pos_password = Utils.convertArDigitToEn(ed_pos_password.getText().toString());
        if (myApp.getGlobal_OperationONOFF()) {

            if ((m_account_no.length() != 0)
                    && (m_ed_pos_code.length() != 0)
                    && (m_ed_pos_amount.length() != 0)) {
                // && (m_ed_pos_password.length() != 0)) {

                //if  m_ed_pos_password.equals(Utils.decoding(m_password))) {
                sendMSG = service_code + ":" + m_account_no + ":" + m_ed_pos_code + ":" + m_ed_pos_amount + ":" + m_password + ":0";
                logMSG = m_title + "\n" +
                        tv_pos_code.getText().toString() + " : " + m_ed_pos_code + "\n" +
                        tv_pos_name.getText().toString() + " : " + ed_pos_name.getText().toString() + "\n" +
                        tv_pos_amount.getText().toString() + " : " + m_ed_pos_amount;
                sendMSG = Utils.convertArDigitToEn(sendMSG);
                logMSG = Utils.convertArDigitToEn(logMSG);
                myApp.setGlobal_OperationONOFF(true);

                Utils.sendCommand(Purchases.this, m_account_no, sendMSG, logMSG, true, "0", Utils.SEND_CMD);

                myApp.setGlobal_TempVariable(m_ed_pos_amount);
                myApp.setLastRunActivity(Purchases.this);
                /*} else {
                    ed_pos_password.requestFocus();
                    ed_pos_password.setError(getResources().getString(R.string.password_error));
                }*/
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
                purchases();
                break;
            case R.id.b_favorite_add:
                if (Utils.addedToFavorite(Purchases.this, mMainServicesCode, mDetailServicesCode)) {
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
            case R.id.b_qr_scan:
          //      scanQRCode();
                break;
        }
    }
}