package dev.alamalbank.amb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import dev.alamalbank.amb.Util.BaseActivity;
import dev.alamalbank.amb.Util.ListViewBean;
import dev.alamalbank.amb.Util.Utils;

import java.io.File;
import java.util.List;

import static dev.alamalbank.amb.Main_Activity.SD_REQUEST;

public class Profile extends BaseActivity {
    TableRow prevRowSelection;
    HorizontalScrollView horizontalScrollView;
    private TextView tv_account_email;
    private TextView tv_account_password;
    private EditText ed_client_name;
    private ImageView im_user_photo;
    private RadioGroup radioSIM;
    private RadioButton radioSIM1;
    private String m_title;
    private String mSim;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Bundle b = this.getIntent().getExtras();
            if (b != null) {
                m_title = b.getString("title");
            }
            initComponent();
            getClientData();
            initTableLayout();
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, "Profile\n" + e.toString(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.profile;
    }

    @Override
    public void initComponent() {
        try {
            TextView tv_title = (TextView) Utils.populateView(Profile.this, "TextView", R.id.tv_title, 0);
            tv_title.setText(m_title);
            TextView tv_client_name = (TextView) Utils.populateView(Profile.this, "TextView", R.id.tv_client_name, 0);
            assert tv_client_name != null;
            ed_client_name = (EditText) Utils.populateView(Profile.this, "EditText", R.id.ed_client_name, InputType.TYPE_CLASS_TEXT);
            TextView tv_account_email_label = (TextView) Utils.populateView(Profile.this, "TextView", R.id.tv_account_email_label, 0);
            assert tv_account_email_label != null;
            tv_account_email = (TextView) Utils.populateView(Profile.this, "TextView", R.id.tv_account_email, 0);
            TextView tv_account_sim_label = (TextView) Utils.populateView(Profile.this, "TextView", R.id.tv_account_sim_label, 0);
            assert tv_account_sim_label != null;
            TextView tv_account_password_label = (TextView) Utils.populateView(Profile.this, "TextView", R.id.tv_account_password_label, 0);
            assert tv_account_password_label != null;
            tv_account_password = (TextView) Utils.populateView(Profile.this, "TextView", R.id.tv_account_password, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            TextView tv_device_license_cancel = (TextView) Utils.populateView(Profile.this, "TextView", R.id.tv_device_license_cancel, 0);
            assert tv_device_license_cancel != null;
            im_user_photo = (ImageView) Utils.populateView(Profile.this, "ImageView", R.id.im_user_photo, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Utils.hasPermissions(Profile.this, Utils.STORAGE_PERMISSION_CODE, Utils.STORAGE_PERMISSIONS)) {
                    Utils.requestPermission(Profile.this, Utils.STORAGE_PERMISSION_CODE);
                } else {
                    ReadUserImage();
                }
            } else {
                ReadUserImage();
            }

            ImageView b_open_gallery = (ImageView) Utils.populateView(Profile.this, "ImageView", R.id.b_open_gallery, 0);
            assert b_open_gallery != null;
            b_open_gallery.setOnClickListener(this);
            ImageView b_save_name = (ImageView) Utils.populateView(Profile.this, "ImageView", R.id.b_save_name, 0);
            assert b_save_name != null;
            b_save_name.setOnClickListener(this);
            ImageView b_chang_email = (ImageView) Utils.populateView(Profile.this, "ImageView", R.id.b_chang_email, 0);
            assert b_chang_email != null;
            b_chang_email.setOnClickListener(this);
            ImageView b_save_sim = (ImageView) Utils.populateView(Profile.this, "ImageView", R.id.b_save_sim, 0);
            assert b_save_sim != null;
            b_save_sim.setOnClickListener(this);

            ImageView b_chang_pass = (ImageView) Utils.populateView(Profile.this, "ImageView", R.id.b_chang_pass, 0);
            assert b_chang_pass != null;
            b_chang_pass.setOnClickListener(this);

            ImageView b_cancel_device_license = (ImageView) Utils.populateView(Profile.this, "ImageView", R.id.b_cancel_device_license, 0);
            assert b_cancel_device_license != null;
            b_cancel_device_license.setOnClickListener(this);

            radioSIM = (RadioGroup) Utils.populateView(Profile.this, "RadioGroup", R.id.radioSIM, 0);
            radioSIM1 = (RadioButton) Utils.populateView(Profile.this, "RadioButton", R.id.radioSIM1, 0);
            RadioButton radioSIM2 = (RadioButton) Utils.populateView(Profile.this, "RadioButton", R.id.radioSIM2, 0);

            if (!Utils.isHasMultiSim(this)) {
                radioSIM1.setEnabled(false);
                radioSIM2.setEnabled(false);
                b_save_sim.setEnabled(false);
            }

            Button b_back = (Button) Utils.populateView(Profile.this, "Button", R.id.b_back, 0);
            assert b_back != null;
            horizontalScrollView = findViewById(R.id.horizontalScrollView);
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, e.toString(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SD_REQUEST && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            File file = Utils.getFileName("PYes", "userimage.jpg");
            if (file.exists()) {
                file.delete();
            }
            if (Utils.copyFile(picturePath, file)) {
                ReadUserImage();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getClientData();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent;
        boolean enableFavorite;
        String mCount;
        String m_service_code;
        String title;

        switch (v.getId()) {
            case R.id.b_open_gallery:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Utils.hasPermissions(Profile.this, Utils.STORAGE_PERMISSION_CODE, Utils.STORAGE_PERMISSIONS)) {
                        Utils.requestPermission(Profile.this, Utils.STORAGE_PERMISSION_CODE);
                    } else {
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, SD_REQUEST);
                    }
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, SD_REQUEST);
                }
                break;
            case R.id.b_save_name:
                String client_name = ed_client_name.getText().toString().trim();
                if ((!Utils.is_Null(client_name)) && (!client_name.equals(myApp.getGlobal_User_name()))) {
                    Utils.runSQL(Profile.this, "update home set client_name='" + client_name + "'");
                    Utils.showConfirmationDialog(Profile.this, getResources().getString(R.string.save_complete), getResources().getString(R.string.amb_name));
                    myApp.setGlobal_User_name(client_name);
                }
                break;
            case R.id.b_chang_email:
                m_service_code = "304";
                title = m_service_code + "-" + getResources().getString(R.string.change_email);
                mCount = Utils.getColumnValue(Profile.this, "select count(*) as cnt from favorite_services where service_code='" + m_service_code + "'");
                enableFavorite = (mCount != null) && (mCount.equalsIgnoreCase("0"));
                intent = new Intent("dev.alamalbank.amb.CHANGE_EMAIL");
                intent.putExtra("service_code", m_service_code);
                intent.putExtra("title", title);
                intent.putExtra("enableFavorite", enableFavorite);
                startActivity(intent);
                break;

            case R.id.b_save_sim:
                if (((mSim == null) || mSim.equalsIgnoreCase("0")) && (!radioSIM1.isChecked())) {
                    Utils.runSQL(Profile.this, "update home set sim=1");
                } else {
                    Utils.runSQL(Profile.this, "update accounts set sim=0");
                }
                Utils.showConfirmationDialog(Profile.this, getResources().getString(R.string.save_complete), getResources().getString(R.string.amb_name));
                break;
            case R.id.b_chang_pass:
                m_service_code = "303";
                title = m_service_code + "-" + getResources().getString(R.string.change_pwd);
                mCount = Utils.getColumnValue(Profile.this, "select count(*) as cnt from favorite_services where service_code='" + m_service_code + "'");
                enableFavorite = (mCount != null) && (mCount.equalsIgnoreCase("0"));
                intent = new Intent("dev.alamalbank.amb.CHANGE_PWD");
                intent.putExtra("service_code", m_service_code);
                intent.putExtra("title", title);
                intent.putExtra("enableFavorite", enableFavorite);
                startActivity(intent);
                break;
            case R.id.b_cancel_device_license:
                m_service_code = "307";
                title = m_service_code + "-" + getResources().getString(R.string.cancel_device_license);
                mCount = Utils.getColumnValue(Profile.this, "select count(*) as cnt from favorite_services where service_code='" + m_service_code + "'");
                enableFavorite = (mCount != null) && (mCount.equalsIgnoreCase("0"));
                Utils.runService(this, myApp.getGlobal_User_Account(), "FORM", title, m_service_code, enableFavorite, m_language);

                break;
            case R.id.b_back:
                myApp.setGlobal_OperationONOFF(true);
                overridePendingTransition(R.anim.no_animate, R.anim.slide_out);
                finish();
                break;
        }
    }

    private void getClientData() {
        ed_client_name.setText(myApp.getGlobal_User_name());
        tv_account_email.setText(Utils.getColumnValue(Profile.this, "select email from home"));
        mSim = Utils.getColumnValue(Profile.this, "select sim from home");
        if ((mSim == null) || mSim.equalsIgnoreCase("0")) {
            radioSIM.check(R.id.radioSIM1);
        } else {
            radioSIM.check(R.id.radioSIM2);
        }
        tv_account_password.setText(myApp.getGlobal_Password());
    }

    private void ReadUserImage() {
        File file = new File(Utils.getFileName("PYes", "userimage.jpg").getAbsolutePath());
        if (file.exists()) {
            Bitmap resourceUserImage = Utils.setPictureDimensions(file.getAbsolutePath(), 1000, 1000);
            im_user_photo.setImageBitmap(resourceUserImage);
        } else {
            im_user_photo.setImageResource(R.drawable.profile_photo);
        }
    }

    private void initTableLayout() {
        List<ListViewBean> account_info = Utils.getAccountInfo(this, null);
        if ((account_info.size() > 0)) {
            TableLayout tableLayout = findViewById(R.id.tableLayout);
            tableLayout.removeAllViews();
            LayoutParams rowLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            // Header
            final TableRow header = new TableRow(this);
            header.setBackgroundColor(getResources().getColor(R.color.color_gold));
            header.setLayoutParams(rowLayoutParams);
            header.addView(textView(getResources().getString(R.string.account_no), true));
            header.addView(textView(getResources().getString(R.string.account_type), true));
            header.addView(textView(getResources().getString(R.string.account_curr), true));
            header.addView(textView(getResources().getString(R.string.account_classification), true));
            header.addView(textView(getResources().getString(R.string.account_name), true));
            header.addView(textView(getResources().getString(R.string.is_default), true));
            header.addView(textView(getResources().getString(R.string.mobile_no), true));
            tableLayout.addView(header);
            prevRowSelection = header;
            for (int i = 0; i < account_info.size(); i++) {
                final TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(rowLayoutParams);
                if (i == 0) {
                    tableRow.setBackgroundResource(R.color.color_turquoise);
                    prevRowSelection = tableRow;
                } else {
                    tableRow.setBackgroundColor(getResources().getColor(R.color.color_white));
                }
                tableRow.addView(textView(account_info.get(i).getAccount_no(), false));
                tableRow.addView(textView(account_info.get(i).getAccount_type(), false));
                tableRow.addView(textView(account_info.get(i).getAccount_curr(), false));
                tableRow.addView(textView(account_info.get(i).getAccount_class(), false));
                tableRow.addView(textView(account_info.get(i).getAccount_name(), false));
                tableRow.addView(textView(account_info.get(i).getAccount_default(), false));
                tableRow.addView(textView(account_info.get(i).getAccount_mobile(), false));
                tableLayout.addView(tableRow);
            }
            horizontalScrollView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                }
            }, 100L);
        }
    }

    private TextView textView(String label, boolean is_header) {
        final TextView tv = new TextView(this);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.leftMargin = 5;
        layoutParams.topMargin = 5;
        layoutParams.rightMargin = 5;
        layoutParams.bottomMargin = 5;

        tv.setLayoutParams(layoutParams);
        tv.setGravity(Gravity.CENTER);
        tv.setTypeface(myFont);
        tv.setTextSize(Utils.getScale(this) - 2);
        tv.setPadding(5, 5, 5, 5);
        tv.setText(label);
        if (is_header) {
            tv.setTextColor(getResources().getColor(R.color.color_white));
            tv.setBackgroundResource(R.color.color_gold);
        } else {
            tv.setTextColor(getResources().getColor(R.color.color_edit));
            tv.setBackgroundResource(R.color.color_trans);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TableRow curRow = ((TableRow) v.getParent());
                    if (prevRowSelection.toString().length() != 0) {
                        prevRowSelection.setBackgroundResource(R.color.color_white);
                    }
                    curRow.setBackgroundResource(R.color.color_turquoise);
                    prevRowSelection = curRow;
                }
            });
        }
        return tv;
    }
}