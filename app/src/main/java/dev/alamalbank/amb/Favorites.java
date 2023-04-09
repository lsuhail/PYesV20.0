package dev.alamalbank.amb;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

public class Favorites extends BaseActivity implements OnItemClickListener {

    private ListView servicesList;
    private ArrayList<ListViewBean> arrayList;
    private String m_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            m_title = getResources().getString(R.string.favorite_title);
            initComponent();
            arrayList = Utils.getListViewBeanFromDB(Favorites.this, "FAVORITES", "", m_language);
            if (null != arrayList && !arrayList.isEmpty()) {
                ListViewRowAdapter servicesListAdapter = new ListViewRowAdapter(this, R.layout.list_row, arrayList, "services", m_language);
                servicesList.setAdapter(servicesListAdapter);
                servicesList.setOnItemClickListener(this);
                servicesListAdapter.notifyDataSetChanged();
            } else {
                Utils.showToast(this, getResources().getString(R.string.no_favorites));
                finish();
            }
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, "Favorites \n" + e.toString(), getResources().getString(R.string.app_name));
        }

    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.favorites;
    }

    @Override
    public void initComponent() {

        Utils.setBankBackgroundLogo(this,myApp.getGlobal_Com_Id());

        TextView tv_detail_title = findViewById(R.id.tv_detail_title);
        tv_detail_title.setTypeface(myFont);
        tv_detail_title.setText(m_title);
        servicesList = findViewById(R.id.dserviceslist);
        Button b_dservicesback = findViewById(R.id.b_dservicesback);
        b_dservicesback.setTypeface(myFont);
        b_dservicesback.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.b_dservicesback) {
            overridePendingTransition(R.anim.no_animate, R.anim.slide_out);
            finish();
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Utils.runService(this, myApp.getGlobal_User_Account(), arrayList.get(position).getActivityType(), arrayList.get(position).getTitle(), arrayList.get(position).getCode(), false, m_language);
    }

}
