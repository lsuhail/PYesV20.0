package dev.alamalbank.amb;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dev.alamalbank.amb.Util.BaseActivity;
import dev.alamalbank.amb.Util.SQLiteMain;
import dev.alamalbank.amb.Util.Utils;

public class Confirmation extends BaseActivity {
    public static EditText ed_confirmation_message, ed_confirmation_code;
    final int m_max_try_confirm_count = 3;
    String m_last_receive_msg;
    int m_try_confirm_count = 1;
    String confirmation_code;
    private TextView tv_confirmation_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            m_last_receive_msg = myApp.getGlobal_last_receive_msg();
            myApp.setGlobal_Confirmation(true);
            if ((m_last_receive_msg.length() >= 3) && (m_last_receive_msg.substring(0, 3).equalsIgnoreCase("002"))) {
                confirmation_code = m_last_receive_msg.substring(4);
                confirmation_code = confirmation_code.substring(confirmation_code.indexOf(":") + 1);
            }
            initComponent();

        } catch (Exception e) {
            Utils.showToast(this, e.toString());
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.confirmation;
    }

    @Override
    public void initComponent() {
        tv_confirmation_title = findViewById(R.id.tv_confirmation_title);
        tv_confirmation_title.setTypeface(myFont);
        tv_confirmation_title.setText(getResources().getString(R.string.confirmation));
        TextView tv_confirmation_message = findViewById(R.id.tv_confirmation_message);
        tv_confirmation_message.setTypeface(myFont);
        ed_confirmation_message = findViewById(R.id.ed_confirmation_message);
        tv_confirmation_message.setTypeface(myFont);
        ed_confirmation_message.setText(m_last_receive_msg);

        ed_confirmation_message.setEnabled(false);
        TextView tv_confirmation_code = findViewById(R.id.tv_confirmation_code);
        tv_confirmation_code.setTypeface(myFont);
        ed_confirmation_code = findViewById(R.id.ed_confirmation_code);
        ed_confirmation_code.setTypeface(myFont);
        ed_confirmation_code.setInputType(InputType.TYPE_CLASS_NUMBER);
        // set max length of confirmation code
        ed_confirmation_code.setFilters(new InputFilter[]{new InputFilter.LengthFilter(confirmation_code.length())});

        Button b_confirmation_ok = findViewById(R.id.b_confirmation_ok);
        b_confirmation_ok.setTypeface(myFont);
        Button b_confirmation_cancel = findViewById(R.id.b_confirmation_cancel);
        b_confirmation_cancel.setTypeface(myFont);
        b_confirmation_ok.setOnClickListener(this);
        b_confirmation_cancel.setOnClickListener(this);


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        String lastmsg = "",
                m_account_no = Utils.getColumnValue(Confirmation.this, "select account_no from accounts where is_default='1' "),
                confermationmsg = tv_confirmation_title.getText().toString() + "\n";
        Cursor m_qr;
        switch (v.getId()) {
            case R.id.b_confirmation_ok:
                if (myApp.getGlobal_OperationONOFF()) {
                    if (m_try_confirm_count <= m_max_try_confirm_count) {
                        if (myApp.getGlobal_Confirmation() && (ed_confirmation_code.getText().toString().length() != 0)
                                && (confirmation_code.equals(ed_confirmation_code.getText().toString().trim()))) {
                            try {
                                final SQLiteMain entry = new SQLiteMain(Confirmation.this);
                                entry.open();
                                //TODO: Confirmation Last Send Command
                                m_qr = entry.execute_Query("select MESSAGE from LASTSENDCMD where _ID=(select max( _ID ) from LASTSENDCMD)");
                                m_qr.moveToFirst();
                                lastmsg = m_qr.getString(m_qr.getColumnIndex("MESSAGE"));
                                lastmsg = lastmsg.substring(0, lastmsg.length() - 1);
                                lastmsg = lastmsg + ed_confirmation_code.getText().toString();// add confirmation code to the last send message

                                confermationmsg = confermationmsg + lastmsg;

                                m_account_no = Utils.convertArDigitToEn(m_account_no);
                                confermationmsg = Utils.convertArDigitToEn(confermationmsg);
                                lastmsg = Utils.convertArDigitToEn(lastmsg);

                                myApp.setGlobal_OperationONOFF(false);

                                Utils.sendCommand(Confirmation.this, m_account_no, lastmsg, confermationmsg, false, "0",Utils.SEND_CMD);

                                entry.close();

                            } catch (Exception e) {
                                //Utils.showToast(Confirmation.this, e.toString());
                                Utils.showConfirmationDialog(this, e.toString(), getResources().getString(R.string.app_name));
                            }
                            ed_confirmation_code.setText("");
                            ed_confirmation_code.setEnabled(false);
                            m_try_confirm_count++;
                        } else if (!confirmation_code.equals(ed_confirmation_code.getText().toString().trim())) {
                            Utils.showConfirmationDialog(this, getResources().getString(R.string.confirmation_code_error), getResources().getString(R.string.app_name));

                        } else {

                            finish();
                        }
                    } else {
                        myApp.setGlobal_OperationONOFF(true);
                        finish();
                    }
                } else {
                    Utils.showConfirmationDialog(this, getResources().getString(R.string.new_operation_error), getResources().getString(R.string.app_name));
                }
                break;
            case R.id.b_confirmation_cancel:
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