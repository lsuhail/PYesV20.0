package dev.alamalbank.amb;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import dev.alamalbank.amb.Util.BaseActivity;
import dev.alamalbank.amb.Util.ListViewBean;
import dev.alamalbank.amb.Util.ListViewRowAdapter;
import dev.alamalbank.amb.Util.SQLiteMain;
import dev.alamalbank.amb.Util.Utils;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.List;

public class Notifications extends BaseActivity {

    String GLOBAL_USER_NAME;
    Button b_back;
    TextView tv_notifications_title;
    ListView lv_notifications;
    String m_title, m_language;
    private List<ListViewBean> arrayList;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        try {

            m_title = getResources().getString(R.string.notifications_label);
            GLOBAL_USER_NAME = myApp.getGlobal_User_name();
            initComponent();
            build();

            myApp.setGlobal_NotificationsCounter(0);

            OneSignal.clearOneSignalNotifications();

        } catch (Exception e) {
            Utils.showConfirmationDialog(this, "Note\n" + e.toString(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.notifications;
    }

    @Override
    public void initComponent() {

        Utils.setBankBackgroundLogo(this,myApp.getGlobal_Com_Id());

        lv_notifications = findViewById(R.id.lv_notifications);
        b_back = findViewById(R.id.b_back);
        b_back.setTypeface(myFont);
        b_back.setOnClickListener(this);
        tv_notifications_title = findViewById(R.id.tv_notifications_title);
        tv_notifications_title.setText(m_title);
        tv_notifications_title.setTypeface(myFont);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.b_back) {
            overridePendingTransition(R.anim.no_animate, R.anim.slide_out);
            finish();
        }
    }

    private void confirmSendRequest(final Context context, String title, String msg, final ListViewRowAdapter listViewRowAdapter, final int position, final String noteId) {
        final Dialog myDialog = new Dialog(context);
        myDialog.setCancelable(false);
        myDialog.setContentView(R.layout.custompopup);
        TextView tvTitle = myDialog.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(myFont);
        tvTitle.setText(title);
        TextView tvMessage = myDialog.findViewById(R.id.tvMessage);
        tvMessage.setTypeface(myFont);
        tvMessage.setText(msg);
        Button btnOk = myDialog.findViewById(R.id.btnOk);
        btnOk.setTypeface(myFont);
        Button btnCancel = myDialog.findViewById(R.id.btnCancel);
        btnCancel.setTypeface(myFont);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utils.RUNSQL(context, "delete from notifications");
                Utils.runSQL(context, "delete from notifications where _id='" + noteId + "'");
                listViewRowAdapter.remove(listViewRowAdapter.getItem(position));
                myDialog.cancel();
                myDialog.dismiss();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public List<ListViewBean> getNotificationsdata(Context context, String P_SQLSELECT) {
        List<ListViewBean> arrayList = new ArrayList<>();

        arrayList.clear();
        Cursor qry;
        try {
            final SQLiteMain entry = new SQLiteMain(context);
            entry.open();
            qry = entry.execute_Query(P_SQLSELECT);
            qry.moveToFirst();
            int rowCount = qry.getCount();
            if (rowCount > 0) {
                for (int i = 0; i < rowCount; i++) {
                    ListViewBean objBean = new ListViewBean();
                    objBean.setmsgId(qry.getString(qry.getColumnIndex("id")));
                    objBean.setmsgPriority(qry.getString(qry.getColumnIndex("priority")));
                    objBean.setmsgTitle(qry.getString(qry.getColumnIndex("title")));
                    objBean.setmsgBody(qry.getString(qry.getColumnIndex("note")));
                    objBean.setmsgDateTime(qry.getString(qry.getColumnIndex("datetime")));
                    objBean.setmsgStatus(qry.getString(qry.getColumnIndex("status")));
                    arrayList.add(objBean);
                    qry.moveToNext();
                }
            } else {
                arrayList.clear();
            }
            qry.close();
            entry.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showConfirmationDialog(context, e.toString(), context.getResources().getString(R.string.app_name));
        }
        return arrayList;
    }


    private void build() {
        arrayList = getNotificationsdata(this, "select _id id,priority,datetime,title,note,status from notifications order by _id desc");

        if (null != arrayList && arrayList.size() != 0) {
            final ListViewRowAdapter notelistAdapter = new ListViewRowAdapter(
                    this, R.layout.notifications_list, arrayList, "note", m_language);
            lv_notifications.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            lv_notifications.setAdapter(notelistAdapter);
            lv_notifications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String mId = arrayList.get(position).getmsgId();
                    //String mPriority = arrayList.get(position).getmsgPriority();
                    String mTitle = arrayList.get(position).getmsgTitle();
                    String mBody = arrayList.get(position).getmsgBody();
                    String mDateTime = arrayList.get(position).getmsgDateTime();
                    String mStatus = arrayList.get(position).getmsgStatus();

                    if (view.getId() == R.id.note_delete) {

                        confirmSendRequest(view.getContext(), getResources().getString(R.string.delete), getResources().getString(R.string.are_you_sure),
                                notelistAdapter, position, mId);

                    } else {
                        LinearLayout ll = view.findViewById(R.id.ll_2_id);
                        ll.setBackgroundResource(R.color.color_background);
                        Utils.showConfirmationDialog(view.getContext(), mBody + "\n" + mDateTime, mTitle);
                        if (mStatus.equalsIgnoreCase("0")) {
                            Utils.runSQL(view.getContext(), "Update notifications set status=1 where _id='" + mId + "'");
                        }
                    }

                    notelistAdapter.notifyDataSetChanged();
                }
            });
            notelistAdapter.notifyDataSetChanged();
        } else {
            Utils.showToast(this, getResources().getString(R.string.no_notifications));
            finish();
        }
    }

}
