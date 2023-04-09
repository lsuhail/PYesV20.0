package dev.alamalbank.amb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.cardview.widget.CardView;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import dev.alamalbank.amb.Util.BaseActivity;
import dev.alamalbank.amb.Util.Utils;

import java.io.File;
import java.util.Locale;

import static java.util.Calendar.*;

import java.util.Date;

public class OpenAccount extends BaseActivity {

    private static final int REQUEST_CAPTURE_IMAGE_Client = 1776;
    private static final int REQUEST_CAPTURE_IMAGE_CardF = 1777;
    private static final int REQUEST_CAPTURE_IMAGE_CardB = 1778;
    private static final int REQUEST_CAPTURE_IMAGE_OPEN = 1779;
    private static String client_photo_name = "", client_card_front = "", client_card_back = "", client_open_account = "";
    private static File mClientPhotoFilePath, mClientCardFilePathF, mClientCardFilePathB, mClientOpenAccountFilePath;
    private int currYear, currMonth, currDay, year, month, day;
    private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Date selectedDate = null;
            Date currentDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
            try {
                selectedDate = sdf.parse(selectedYear + "/" + selectedMonth + "/" + selectedDay);
                currentDate = sdf.parse(currYear + "/" + currMonth + "/" + currDay);
            } catch (Exception ex) {
                Log.v(Utils.APP_TAG, ex.getLocalizedMessage());
            }
            int diffYear = getDiffYears(currentDate, selectedDate);
            if ((Math.abs(diffYear) >= 18) && (Math.abs(diffYear) <= 90)) {
                ed_birth_date.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
            } else {
                ed_birth_date.setText("");
                Utils.showConfirmationDialog(OpenAccount.this, getResources().getString(R.string.birth_date_error) + "\n" + diffYear,
                        getResources().getString(R.string.app_name));
            }

        }
    };
    private String m_password = "";
    private String service_code = "";
    private String mClientsImagesPath;
    private String logMSG = "";
    private String m_title, m_account_no, m_client_mobile_no, m_client_name, mMainServicesCode, mDetailServicesCode;
    private boolean mEnableFavorite;
    private Button b_favorite_add;
    private ImageView b_date_packer;
    private ImageView iv_client_photo;
    private ImageView iv_client_card_front_photo;
    private ImageView iv_client_card_back_photo;
    private ImageView iv_open_account_photo;
    private TextView tv_client_name;
    private TextView tv_client_mobile_no;
    private TextView tv_email;
    private TextView tv_card_type;
    private TextView tv_card_no;
    private TextView tv_birth_place;
    private TextView tv_birth_date;
    private EditText ed_date, ed_email, ed_card_no, ed_birth_place, ed_birth_date, ed_mailto;
    private Spinner sp_account_no, sp_card_type;
    private AutoCompleteTextView ed_client_name, ed_client_mobile_no;

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

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
            mClientsImagesPath = myApp.getGlobal_STORAGE_PATH() + Utils.ClientsImagesPath;
            initComponent();
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, "OpenAccount\n" + e.toString(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.openaccount;
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
        Calendar calendar = Calendar.getInstance();
        currYear = calendar.get(YEAR);
        currMonth = calendar.get(MONTH);
        currDay = calendar.get(Calendar.DAY_OF_MONTH);
        year = currYear - 17;
        month = currMonth;
        day = currDay;
        initHeader();

        TextView tv_date = (TextView) Utils.populateView(OpenAccount.this, "TextView", R.id.tv_date, 0);
        assert tv_date != null;
        ed_date = (EditText) Utils.populateView(OpenAccount.this, "EditText", R.id.ed_date, InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);
        assert ed_date != null;
        ed_date.setEnabled(false);
        ed_date.setText(new StringBuilder().append(currDay).append("/").append(currMonth).append("/").append(currYear));
        ed_date.requestFocus();

        tv_client_name = (TextView) Utils.populateView(OpenAccount.this, "TextView", R.id.tv_client_name, 0);
        ed_client_name = (AutoCompleteTextView) Utils.populateView(OpenAccount.this, "AutoCompleteTextView", R.id.ed_client_name, InputType.TYPE_CLASS_TEXT);
        Utils.populateAutoCompAdapter(OpenAccount.this, ed_client_name, Utils.getArrayListFromTable(OpenAccount.this, "select last_entry from last_entry  order by type_id"));

        tv_client_mobile_no = (TextView) Utils.populateView(OpenAccount.this, "TextView", R.id.tv_client_mobile_no, 0);
        ed_client_mobile_no = (AutoCompleteTextView) Utils.populateView(OpenAccount.this, "AutoCompleteTextView", R.id.ed_client_mobile_no, InputType.TYPE_CLASS_TEXT);
        Utils.populateAutoCompAdapter(OpenAccount.this, ed_client_mobile_no, Utils.getArrayListFromTable(OpenAccount.this, "select last_entry from last_entry  order by type_id"));

        tv_email = (TextView) Utils.populateView(OpenAccount.this, "TextView", R.id.tv_email, 0);
        ed_email = (EditText) Utils.populateView(OpenAccount.this, "EditText", R.id.ed_email, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        tv_card_type = (TextView) Utils.populateView(OpenAccount.this, "TextView", R.id.tv_card_type, 0);
        sp_card_type = findViewById(R.id.sp_card_type);
        sp_card_type = (Spinner) Utils.populateView(OpenAccount.this, "Spinner", R.id.sp_card_type, 0);
        Utils.populateSpinnerAdapter(OpenAccount.this, sp_card_type, Utils.getArrayListFromDB(OpenAccount.this, "select card_type_name from card_type"), R.layout.spinner_item);

        tv_card_no = (TextView) Utils.populateView(OpenAccount.this, "TextView", R.id.tv_card_no, 0);
        ed_card_no = (EditText) Utils.populateView(OpenAccount.this, "EditText", R.id.ed_card_no, InputType.TYPE_CLASS_TEXT);

        tv_birth_place = (TextView) Utils.populateView(OpenAccount.this, "TextView", R.id.tv_birth_place, 0);
        ed_birth_place = (EditText) Utils.populateView(OpenAccount.this, "EditText", R.id.ed_birth_place, InputType.TYPE_CLASS_TEXT);

        tv_birth_date = (TextView) Utils.populateView(OpenAccount.this, "TextView", R.id.tv_birth_date, 0);
        ed_birth_date = (EditText) Utils.populateView(OpenAccount.this, "EditText", R.id.ed_birth_date, InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);
        assert ed_birth_date != null;
        ed_birth_date.setEnabled(false);

        TextView tv_mailto = (TextView) Utils.populateView(OpenAccount.this, "TextView", R.id.tv_mailto, 0);
        assert tv_mailto != null;
        ed_mailto = (EditText) Utils.populateView(OpenAccount.this, "EditText", R.id.ed_mailto, InputType.TYPE_CLASS_TEXT);
        String m_to_email = Utils.getColumnValue(OpenAccount.this, "select OPEN_ACCOUNT_EMAIL from home");
        ed_mailto.setText(m_to_email);
        ed_mailto.setEnabled(false);
        ImageView b_birth_date_packer = (ImageView) Utils.populateView(OpenAccount.this, "ImageView", R.id.b_birth_date_packer, 0);
        assert b_birth_date_packer != null;
        b_birth_date_packer.setOnClickListener(this);
        ImageView b_mailto = (ImageView) Utils.populateView(OpenAccount.this, "ImageView", R.id.b_mailto, 0);
        assert b_mailto != null;
        b_mailto.setOnClickListener(this);
        CardView cv_client_photo = (CardView) Utils.populateView(OpenAccount.this, "CardView", R.id.cv_client_photo, 0);
        assert cv_client_photo != null;
        CardView cv_client_card_front_photo = (CardView) Utils.populateView(OpenAccount.this, "CardView", R.id.cv_client_card_front_photo, 0);
        assert cv_client_card_front_photo != null;
        CardView cv_client_card_back_photo = (CardView) Utils.populateView(OpenAccount.this, "CardView", R.id.cv_client_card_back_photo, 0);
        assert cv_client_card_back_photo != null;
        CardView cv_open_account_photo = (CardView) Utils.populateView(OpenAccount.this, "CardView", R.id.cv_open_account_photo, 0);
        assert cv_open_account_photo != null;
        iv_client_photo = (ImageView) Utils.populateView(OpenAccount.this, "ImageView", R.id.iv_client_photo, 0);
        iv_client_card_front_photo = (ImageView) Utils.populateView(OpenAccount.this, "ImageView", R.id.iv_client_card_front_photo, 0);
        iv_client_card_back_photo = (ImageView) Utils.populateView(OpenAccount.this, "ImageView", R.id.iv_client_card_back_photo, 0);
        iv_open_account_photo = (ImageView) Utils.populateView(OpenAccount.this, "ImageView", R.id.iv_open_account_photo, 0);

        TextView tv_client_photo = (TextView) Utils.populateView(OpenAccount.this, "TextView", R.id.tv_client_photo, 0);
        assert tv_client_photo != null;
        TextView tv_client_card_front_photo = (TextView) Utils.populateView(OpenAccount.this, "TextView", R.id.tv_client_card_front_photo, 0);
        assert tv_client_card_front_photo != null;
        TextView tv_client_card_back_photo = (TextView) Utils.populateView(OpenAccount.this, "TextView", R.id.tv_client_card_back_photo, 0);
        assert tv_client_card_back_photo != null;
        TextView tv_open_account_photo = (TextView) Utils.populateView(OpenAccount.this, "TextView", R.id.tv_open_account_photo, 0);
        assert tv_open_account_photo != null;

    }

    @Override
    protected Dialog onCreateDialog(int id) {

        return new DatePickerDialog(this, datePickerListener, year, month, day);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap;
        try {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case REQUEST_CAPTURE_IMAGE_Client:
                        bitmap = Utils.setPictureDimensions(mClientPhotoFilePath.getAbsolutePath(), 50, 50);
                        if (bitmap != null) {
                            iv_client_photo.setImageBitmap(bitmap);
                        }
                        break;
                    case REQUEST_CAPTURE_IMAGE_CardF:
                        bitmap = Utils.setPictureDimensions(mClientCardFilePathF.getAbsolutePath(), 50, 50);
                        if (bitmap != null) {
                            iv_client_card_front_photo.setImageBitmap(bitmap);
                        }
                        break;
                    case REQUEST_CAPTURE_IMAGE_CardB:
                        bitmap = Utils.setPictureDimensions(mClientCardFilePathB.getAbsolutePath(), 50, 50);
                        if (bitmap != null) {
                            iv_client_card_back_photo.setImageBitmap(bitmap);
                        }
                        break;
                    case REQUEST_CAPTURE_IMAGE_OPEN:
                        bitmap = Utils.setPictureDimensions(mClientOpenAccountFilePath.getAbsolutePath(), 50, 50);
                        if (bitmap != null) {
                            iv_open_account_photo.setImageBitmap(bitmap);
                        }
                        break;
                }
            }
        } catch (ActivityNotFoundException anfe) {
            Utils.showConfirmationDialog(OpenAccount.this, anfe.toString(), getResources().getString(R.string.app_name));
        } catch (Exception e) {
            Utils.showConfirmationDialog(OpenAccount.this, e.toString(), getResources().getString(R.string.app_name));
        }
    }

    private void captureImage(Context mContext, File mFile, int mRequestCode) {
        if (myApp.getGlobal_OperationONOFF()) {
            if (notinfo_missing()) {
                try {
                    Uri mUriFileName;
                    Intent intentcap;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        intentcap = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        mUriFileName = FileProvider.getUriForFile(mContext, getPackageName() + ".provider", mFile);
                        intentcap.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        intentcap = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        mUriFileName = Uri.fromFile(mFile);
                    }
                    intentcap.putExtra(MediaStore.EXTRA_OUTPUT, mUriFileName);
                    startActivityForResult(intentcap, mRequestCode);
                    //Utils.showConfirmationDialog(this,mUriFileName.toString(),"Pyes");
                } catch (Exception e) {
                    Utils.showConfirmationDialog(mContext, e.toString(), getResources().getString(R.string.app_name));
                }
            } else {
                checkinfo();
            }
        } else {
            Utils.showConfirmationDialog(this, getResources().getString(R.string.new_operation_error), getResources().getString(R.string.app_name));
        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        try {
            m_account_no = Utils.convertArDigitToEn(sp_account_no.getItemAtPosition(sp_account_no.getSelectedItemPosition()).toString());
            m_account_no = m_account_no.substring(0, m_account_no.indexOf(":"));
            m_client_mobile_no = ed_client_mobile_no.getText().toString();
            if ((!Utils.is_Null(m_client_mobile_no)) && (m_client_mobile_no.length() == 9)) {
                m_client_mobile_no = Utils.convertArDigitToEn(ed_client_mobile_no.getText().toString());
            }
            m_client_name = Utils.convertArDigitToEn(ed_client_name.getText().toString());

            if (m_client_name.contains(":")) {
                m_client_name = m_client_name.substring(0, m_client_name.indexOf(":") - 1);
            }
            m_client_name = m_client_name.replace(":", "");
            ed_client_name.setText(m_client_name);
            int index = 0;
            if (m_client_mobile_no.contains(" : ")) {
                index = m_client_mobile_no.indexOf(" : ") + 3;
            }
            m_client_mobile_no = m_client_mobile_no.substring(index).trim();
            ed_client_mobile_no.setText(m_client_mobile_no);
            boolean mCameraFlage = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Utils.hasPermissions(OpenAccount.this, Utils.CAMERA_PERMISSION_CODE, Utils.CAMERA_PERMISSIONS)) {
                    ActivityCompat.requestPermissions(OpenAccount.this, Utils.CAMERA_PERMISSIONS, Utils.CAMERA_PERMISSION_CODE);
                } else {
                    mCameraFlage = true;
                }
            } else {
                mCameraFlage = true;
            }
            switch (v.getId()) {
                case R.id.cv_client_photo:
                    if (mCameraFlage) {
                        client_photo_name = m_client_mobile_no + "_1";
                        mClientPhotoFilePath = Utils.createDir(OpenAccount.this, mClientsImagesPath, client_photo_name + ".jpg");
                        captureImage(OpenAccount.this, mClientPhotoFilePath, REQUEST_CAPTURE_IMAGE_Client);
                    } else {
                        Utils.showConfirmationDialog(this, getResources().getString(R.string.CAMERA_permission_explanation), getResources().getString(R.string.app_name));
                    }

                    break;
                case R.id.cv_client_card_front_photo:
                    if (mCameraFlage) {
                        client_card_front = m_client_mobile_no + "_2";
                        mClientCardFilePathF = Utils.createDir(OpenAccount.this, mClientsImagesPath, client_card_front + ".jpg");
                        captureImage(OpenAccount.this, mClientCardFilePathF, REQUEST_CAPTURE_IMAGE_CardF);
                    } else {
                        Utils.showConfirmationDialog(this, getResources().getString(R.string.CAMERA_permission_explanation), getResources().getString(R.string.app_name));
                    }
                    break;
                case R.id.cv_client_card_back_photo:
                    if (mCameraFlage) {
                        client_card_back = m_client_mobile_no + "_3";
                        mClientCardFilePathB = Utils.createDir(OpenAccount.this, mClientsImagesPath, client_card_back + ".jpg");
                        captureImage(OpenAccount.this, mClientCardFilePathB, REQUEST_CAPTURE_IMAGE_CardB);
                    } else {
                        Utils.showConfirmationDialog(this, getResources().getString(R.string.CAMERA_permission_explanation), getResources().getString(R.string.app_name));
                    }
                    break;
                case R.id.cv_open_account_photo:

                    if (mCameraFlage) {
                        client_open_account = m_client_mobile_no + "_4";
                        mClientOpenAccountFilePath = Utils.createDir(OpenAccount.this, mClientsImagesPath, client_open_account + ".jpg");
                        captureImage(OpenAccount.this, mClientOpenAccountFilePath, REQUEST_CAPTURE_IMAGE_OPEN);
                    } else {
                        Utils.showConfirmationDialog(this, getResources().getString(R.string.CAMERA_permission_explanation), getResources().getString(R.string.app_name));
                    }
                    break;
                case R.id.b_mailto:
                    sendEmail();
                    break;
                case R.id.b_ok:
                    sendRequest();
                    break;
                case R.id.b_favorite_add:
                    if (Utils.addedToFavorite(OpenAccount.this, mMainServicesCode, mDetailServicesCode)) {
                        b_favorite_add.setVisibility(View.INVISIBLE);
                    }
                    break;
                case R.id.b_cancel:
                    if (myApp.getGlobal_OperationONOFF()) {
                        myApp.setGlobal_OperationONOFF(true);
                        overridePendingTransition(R.anim.no_animate, R.anim.slide_out);
                        finish();
                    } else {
                        Utils.showConfirmationDialog(this, getResources().getString(R.string.new_operation_error), getResources().getString(R.string.app_name));
                    }
                    break;

                case R.id.b_birth_date_packer:
                    showDialog(0);

                    break;
            }
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, e.toString(), getResources().getString(R.string.app_name));

        }
    }

    private boolean checkClientImage() {
        boolean flag = false;
        try {
            flag = client_photo_name != null
                    && client_card_front != null
                    && client_card_back != null
                    && client_open_account != null
                    && m_client_mobile_no.equalsIgnoreCase(client_photo_name.substring(0, client_photo_name.length() - 2))
                    && m_client_mobile_no.equalsIgnoreCase(client_card_front.substring(0, client_photo_name.length() - 2))
                    && m_client_mobile_no.equalsIgnoreCase(client_card_back.substring(0, client_photo_name.length() - 2))
                    && m_client_mobile_no.equalsIgnoreCase(client_open_account.substring(0, client_photo_name.length() - 2));
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, "checkClientImage \n" + e.toString());
        }
        return flag;
    }

    private boolean notinfo_missing() {
        try {
            String spacc = sp_account_no.getItemAtPosition(sp_account_no.getSelectedItemPosition()).toString();
            if ((spacc.equals("")) || (spacc.length() == 0)
                    || (ed_date.getText().toString().equals("")) || (ed_date.getText().toString().length() == 0)
                    || (ed_client_name.getText().toString().equals("")) || (ed_client_name.getText().toString().length() == 0)
                    || (!Utils.isNoOfPartCorrect(4, ed_client_name.getText().toString()))
                    || (ed_client_mobile_no.getText().toString().equals("")) || (ed_client_mobile_no.getText().toString().length() == 0)
                    || (ed_email.getText().toString().equals("")) || (ed_email.getText().toString().length() == 0)
                    || (sp_card_type.getItemAtPosition(sp_account_no.getSelectedItemPosition()).toString().equals("")) || (sp_card_type.getItemAtPosition(sp_account_no.getSelectedItemPosition()).toString().length() == 0)
                    || (ed_card_no.getText().toString().equals("")) || (ed_card_no.getText().toString().length() == 0)
                    || (ed_birth_place.getText().toString().equals("")) || (ed_birth_place.getText().toString().length() == 0)
                    || (ed_birth_date.getText().toString().equals("")) || (ed_birth_date.getText().toString().length() == 0)) {
                return false;
            }
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, "not_info_missing \n" + e.toString());
            return false;
        }
        return true;
    }

    private void checkinfo() {
        try {
            if ((ed_date.getText().toString().equals("")) || (ed_date.getText().toString().length() == 0)) {
                ed_date.requestFocus();
                ed_date.setError(getResources().getString(R.string.date_error));
            } else if ((ed_client_name.getText().toString().equals("")) || (ed_client_name.getText().toString().length() == 0)) {
                ed_client_name.requestFocus();
                ed_client_name.setError(getResources().getString(R.string.missing_info));
            } else if (!Utils.isNoOfPartCorrect(4, ed_client_name.getText().toString())) {
                ed_client_name.requestFocus();
                ed_client_name.setError(getResources().getString(R.string.error_no_of_part));
            } else if ((ed_client_mobile_no.getText().toString().equals("")) || (ed_client_mobile_no.getText().toString().length() == 0) || (!Utils.isValidMobile(OpenAccount.this, ed_client_mobile_no.getText().toString()))) {
                ed_client_mobile_no.requestFocus();
                ed_client_mobile_no.setError(getResources().getString(R.string.mobile_error));
            } else if ((ed_email.getText().toString().equals("")) || (ed_email.getText().toString().length() == 0) || (!Utils.isValidEmail(ed_email.getText().toString()))) {
                ed_email.requestFocus();
                ed_email.setError(getResources().getString(R.string.email_error));
            } else if ((ed_card_no.getText().toString().equals("")) || (ed_card_no.getText().toString().length() == 0)) {
                ed_card_no.requestFocus();
                ed_card_no.setError(getResources().getString(R.string.missing_info));
            } else if ((ed_birth_place.getText().toString().equals("")) || (ed_birth_place.getText().toString().length() == 0)) {
                ed_birth_place.requestFocus();
                ed_birth_place.setError(getResources().getString(R.string.missing_info));
            } else if ((ed_birth_date.getText().toString().equals("")) || (ed_birth_date.getText().toString().length() == 0)) {
                ed_birth_date.requestFocus();
                ed_birth_date.setError(getResources().getString(R.string.date_error));
            } else {
                Utils.showConfirmationDialog(this, getResources().getString(R.string.missing_info), getResources().getString(R.string.app_name));
            }
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, "Check info_v \n" + e.toString());
        }
    }

    private void sendRequest() {
        try {
            if (myApp.getGlobal_OperationONOFF()) {
                if (notinfo_missing()) {
                    if (checkClientImage()) {
                        if (Utils.isInternetConnected(OpenAccount.this)) {
                            String m_create_date = Utils.convertArDigitToEn(ed_date.getText().toString());
                            String m_client_email = Utils.convertArDigitToEn(ed_email.getText().toString());
                            String m_client_card_type = Utils.convertArDigitToEn(sp_card_type.getItemAtPosition(sp_card_type.getSelectedItemPosition()).toString());
                            String m_client_card_no = Utils.convertArDigitToEn(ed_card_no.getText().toString());
                            String m_client_birth_place = Utils.convertArDigitToEn(ed_birth_place.getText().toString());
                            String m_client_birth_date = Utils.convertArDigitToEn(ed_birth_date.getText().toString());
                            String sendMSG = service_code + ":"
                                    + m_account_no + ":"
                                    + m_create_date + ":"
                                    + m_client_name + ":"
                                    + m_client_mobile_no + ":"
                                    + m_client_email + ":"
                                    + m_client_card_type + ":"
                                    + m_client_card_no + ":"
                                    + m_client_birth_place + ":"
                                    + m_client_birth_date + ":"
                                    + m_password + ":"
                                    + "0";
                            logMSG = logMSG
                                    + getResources().getString(R.string.msg_open_account) + "\n"
                                    + tv_client_name.getText().toString() + " : " + m_client_name + "\n"
                                    + tv_client_mobile_no.getText().toString() + " : " + m_client_mobile_no + "\n"
                                    + tv_email.getText().toString() + " : " + m_client_email + "\n"
                                    + tv_card_type.getText().toString() + " : " + m_client_card_type + "\n"
                                    + tv_card_no.getText().toString() + " : " + m_client_card_no + "\n"
                                    + tv_birth_place.getText().toString() + " : " + m_client_birth_place + "\n"
                                    + tv_birth_date.getText().toString() + " : " + m_client_birth_date;

                            boolean flage = Utils.insertOpenAccount(OpenAccount.this, m_client_mobile_no, m_account_no + ","
                                    + m_create_date + "," + m_client_name + "," + m_client_mobile_no + ","
                                    + m_client_email + "," + m_client_card_type + ","
                                    + m_client_card_no + "," + m_client_birth_place + "," + m_client_birth_date + ",0");


                            Utils.confirmSendRequest(this, logMSG, getResources().getString(R.string.request_send_over_internet), "Internet", m_account_no, sendMSG, logMSG, sendMSG, true, "0",Utils.SEND_CMD);
                            sendEmail();
                        } else {
                            Utils.showConfirmationDialog(this, getResources().getString(R.string.internet_error), getResources().getString(R.string.app_name));
                        }
                    } else {
                        Utils.showConfirmationDialog(this, getResources().getString(R.string.images_name_fail), getResources().getString(R.string.app_name));
                    }
                } else {
                    checkinfo();
                }
            } else {
                Utils.showConfirmationDialog(this, getResources().getString(R.string.new_operation_error), getResources().getString(R.string.app_name));
            }
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, e.toString(), getResources().getString(R.string.app_name));
        }

    }

    private void sendEmail() {
        String emailAddress = "", emailSubject = "فتح حساب ",
                emailText = "طلب فتح حساب برقم تلفون " + ed_client_mobile_no.getText().toString() + "\n"
                        + " باسم العميل " + ed_client_name.getText().toString() + "\n"
                        + " بواسطة الوكيل رقم " + sp_account_no.getItemAtPosition(sp_account_no.getSelectedItemPosition()).toString() + "\n"
                        + " مرفق صورة العميل والبطاقة.";
        Uri uriClientPhotofilePath, uriClientCardFfilePath,
                uriClientCardBfilePath, uriClientOpenAccountfilePath;
        ArrayList<Uri> uris = new ArrayList<>();
        boolean falge = false;
        try {
            if (myApp.getGlobal_OperationONOFF()) {
                if (notinfo_missing()) {
                    if (ed_mailto.getText().toString() != null) {
                        setClipboard(this, ed_mailto.getText().toString());
                        emailAddress = ed_mailto.getText().toString();
                    }
                    String[] emailAddressList = {emailAddress};
                    Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);//ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, emailAddressList);
                    intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                    intent.putExtra(Intent.EXTRA_TEXT, emailText);
                    intent.setType("image/*");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        uriClientPhotofilePath = FileProvider.getUriForFile(OpenAccount.this, "dev.alamalbank.amb.provider", mClientPhotoFilePath);
                        uriClientCardFfilePath = FileProvider.getUriForFile(OpenAccount.this, "dev.alamalbank.amb.provider", mClientCardFilePathF);
                        uriClientCardBfilePath = FileProvider.getUriForFile(OpenAccount.this, "dev.alamalbank.amb.provider", mClientCardFilePathB);
                        uriClientOpenAccountfilePath = FileProvider.getUriForFile(OpenAccount.this, "dev.alamalbank.amb.provider", mClientOpenAccountFilePath);

                    } else {
                        uriClientPhotofilePath = Uri.fromFile(mClientPhotoFilePath);
                        uriClientCardFfilePath = Uri.fromFile(mClientCardFilePathF);
                        uriClientCardBfilePath = Uri.fromFile(mClientCardFilePathB);
                        uriClientOpenAccountfilePath = Uri.fromFile(mClientOpenAccountFilePath);
                    }
                    uris.add(uriClientPhotofilePath);
                    uris.add(uriClientCardFfilePath);
                    uris.add(uriClientCardBfilePath);
                    uris.add(uriClientOpenAccountfilePath);
                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                    startActivity(Intent.createChooser(intent, getResources().getString(R.string.Choice_email_app)));
                } else {
                    checkinfo();
                }
            } else {
                Utils.showConfirmationDialog(this, getResources().getString(R.string.new_operation_error), getResources().getString(R.string.app_name));
            }
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, e.toString(), getResources().getString(R.string.app_name));
        }


    }


    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }
}
