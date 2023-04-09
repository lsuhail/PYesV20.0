package dev.alamalbank.amb;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import dev.alamalbank.amb.Util.BaseActivity;
import dev.alamalbank.amb.Util.ListViewBean;
import dev.alamalbank.amb.Util.ListViewRowAdapter;
import dev.alamalbank.amb.Util.Utils;

import java.util.List;

public class Services extends BaseActivity implements OnItemClickListener {
    private TextView tv_title;
    private Button b_back;
    private List<ListViewBean> listViewBean;
    private ListView services_list;
    private String title = "";
    private String prev_parent_code = "";
    private String parent_code = "";
    private String service_code = "";
    private String is_leave = "0";
    private String activityType = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Bundle b = this.getIntent().getExtras();
            if (b != null) {
                parent_code = b.getString("service_code");
                title = b.getString("title");
            }
            myApp.setGlobal_Last_Service_CODE(parent_code);
            myApp.setGlobal_ExitFromApplication(false);
            myApp.setGlobal_GoTOPrevActivity(false);
            myApp.setLastRunActivity(Services.this);
            initComponent();
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, "MainServices\n" + e.toString(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.services;
    }

    @Override
    public void initComponent() {

        Utils.setBankBackgroundLogo(this,myApp.getGlobal_Com_Id());

        tv_title = findViewById(R.id.tv_title);
        tv_title.setTypeface(myFont);
        if (Utils.is_Null(title)) title = getResources().getString(R.string.main_services);
        tv_title.setText(title);

        services_list = findViewById(R.id.services_list);
        b_back = findViewById(R.id.b_back);
        b_back.setTypeface(myFont);
        b_back.setOnClickListener(this);
        b_back.setEnabled(false);
        b_back.setVisibility(View.INVISIBLE);

        Button b_home = findViewById(R.id.b_home);
        b_home.setTypeface(myFont);
        b_home.setOnClickListener(this);

        pup_list();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.b_home) {
            overridePendingTransition(R.anim.no_animate, R.anim.slide_out);
            finish();
        } else if (v.getId() == R.id.b_back) {
            parent_code = Utils.getColumnValue(this, "select menu_parent from menus where menu_id='" + parent_code + "'");
            is_leave = Utils.getColumnValue(this, "select IS_LEAF from menus where menu_id='" + parent_code + "'");
            if (Utils.is_Null(is_leave)) is_leave = "0";
            if (!Utils.is_Null(parent_code)) {
                if (m_language.equalsIgnoreCase("ar")) {
                    title = Utils.getColumnValue(this, "select menu_name_ar from menus where menu_id='" + parent_code + "'");
                } else {
                    title = Utils.getColumnValue(this, "select menu_name_en from menus where menu_id='" + parent_code + "'");
                }
            } else {
                title = getResources().getString(R.string.main_services);
            }
            pup_list();
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        prev_parent_code = listViewBean.get(position).getParentCode();
        myApp.setGlobal_Last_Service_CODE(prev_parent_code);
        parent_code = listViewBean.get(position).getCode();
        title = listViewBean.get(position).getTitle();
        is_leave = listViewBean.get(position).getIsLeave();
        activityType = listViewBean.get(position).getActivityType();
        pup_list();
    }

    private void pup_list() {

        if (is_leave.equalsIgnoreCase("0")) {
            List<ListViewBean> tempArrayList = Utils.getListViewBeanFromDB(Services.this, "MENU", parent_code, m_language);
            if (null != tempArrayList && !tempArrayList.isEmpty()) {
                listViewBean = tempArrayList;
                tv_title.setText(title);
                ListViewRowAdapter servicesListAdapter = new ListViewRowAdapter(this, R.layout.list_row, listViewBean, "services", m_language);
                services_list.setAdapter(servicesListAdapter);
                services_list.setOnItemClickListener(this);
                servicesListAdapter.notifyDataSetChanged();
            } else {
                Utils.showToast(this, getResources().getString(R.string.no_services));
            }
        } else {
            boolean enableFavorite;
            service_code = Utils.getColumnValue(this, "select service_code from menus where menu_id='" + parent_code + "'");
            myApp.setGlobal_Last_Service_CODE(parent_code);

            if (!Utils.is_Null(service_code)) {
                String mCount = Utils.getColumnValue(Services.this,
                        "select count(*) as cnt " +
                                 "from favorite_services " +
                                 "where parent_code='" + parent_code +"' "+
                                 "and service_code='" + service_code + "'");
                enableFavorite = (mCount != null) && (mCount.equalsIgnoreCase("0"));
                Utils.runService(this, myApp.getGlobal_User_Account(), activityType, title, service_code, enableFavorite, m_language);
            } else {
                Utils.showToast(this, getResources().getString(R.string.no_services));
            }
            parent_code = prev_parent_code;
        }

        if ((!Utils.is_Null(parent_code))) {
            b_back.setVisibility(View.VISIBLE);
            b_back.setEnabled(true);
        } else {
            b_back.setVisibility(View.INVISIBLE);
        }

    }
}
