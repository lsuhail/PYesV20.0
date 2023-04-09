package dev.alamalbank.amb;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import dev.alamalbank.amb.Util.BaseActivity;
import dev.alamalbank.amb.Util.SQLiteMain;
import dev.alamalbank.amb.Util.Utils;

import java.util.ArrayList;

public class TransactionReport extends BaseActivity {

    private final ArrayList<CharSequence> transactionData = new ArrayList<>();
    String GLOBAL_USER_NAME;
    Button b_back;
    TextView tv_report_title;
    String service_code, m_title;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        try {

            Bundle b = this.getIntent().getExtras();
            service_code = b.getString("service_code");
            m_title = b.getString("title");
            GLOBAL_USER_NAME = myApp.getGlobal_User_name();
            initComponent();
            build();
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, e.toString(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.transactionreport;
    }

    @Override
    public void initComponent() {

        Utils.setBankBackgroundLogo(this,myApp.getGlobal_Com_Id());

        b_back = findViewById(R.id.b_back);
        b_back.setTypeface(myFont);
        b_back.setOnClickListener(this);
        tv_report_title = findViewById(R.id.tv_report_title);
        tv_report_title.setText(m_title);
        tv_report_title.setTypeface(myFont);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.b_back) {
            overridePendingTransition(R.anim.no_animate, R.anim.slide_out);
            finish();
        }
    }

    private void gettransactiondata(String P_SQLSELECT) {
        Cursor ins;
        transactionData.clear();
        try {
            final SQLiteMain entry = new SQLiteMain(TransactionReport.this);
            entry.open();
            ins = entry.execute_Query(P_SQLSELECT);
            ins.moveToFirst();
            int rowCount = ins.getCount();
            int colCount = ins.getColumnCount();
            StringBuilder sval;
            if (rowCount > 0) {
                for (int i = 0; i < rowCount; i++) {
                    sval = new StringBuilder();
                    for (int j = 0; j < colCount; j++) {
                        sval.append(ins.getString(j)).append("\n");
                    }
                    transactionData.add(sval.toString());
                    ins.moveToNext();
                }
            }
            entry.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void build() {
        Cursor qry;

        try {
            final SQLiteMain entry = new SQLiteMain(TransactionReport.this);
            entry.open();
            qry = entry.execute_Query("SELECT COLUMN_NAME,COLUMN_CONTAINS_TYPE,COLUMN_LABEL_AR,COLUMN_LABEL_EN,SQL_SELECT FROM SERVICES_COLUMNS WHERE SERVICE_CODE='" + service_code + "'");
            qry.moveToFirst();
            int rowCount = qry.getCount();

            if (rowCount > 0) {
                for (int i = 0; i < rowCount; i++) {

                    gettransactiondata(qry.getString(qry.getColumnIndex("SQL_SELECT")));
                    ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, transactionData);
                    ListView lv_transaction = findViewById(R.id.lv_transaction);
                    lv_transaction.setAdapter(arrayAdapter);
                    lv_transaction.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    lv_transaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Utils.showConfirmationDialog(view.getContext(), parent.getItemAtPosition(position).toString(), getResources().getString(R.string.app_name));
                        }
                    });
                    lv_transaction.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            Utils.showConfirmationDialog(view.getContext(), parent.getItemAtPosition(position).toString(), getResources().getString(R.string.app_name));
                            return true;
                        }
                    });
                }
            } else {
                Utils.showToast(this, getResources().getString(R.string.no_services) + " << " + service_code + " >>");
                finish();
            }
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, e.toString(), getResources().getString(R.string.app_name));
        }

    }
}
