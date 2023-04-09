package dev.alamalbank.amb;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import dev.alamalbank.amb.Util.BaseActivity;
import dev.alamalbank.amb.Util.Utils;
import dev.alamalbank.amb.adapter.RecyclerViewAdapter;


import java.util.ArrayList;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.nightonke.boommenu.BoomMenuButton;

public class TeleCom extends BaseActivity {

    final String mCAT_PARENT = "10";
    String mServiceTypeValue = "1", mLineTypeValue = "1", mBouquetTypeValue = "1", mBouquetDtlValue = "0", mAdvancePaymentValue = "0";
    String mTelecomId = "", mTelecomPrefix = "", mTelecomName = "", mTelecomColor = "#FFFFFF";
    String mServices_type = "1";
    String mWithAdvancePayment = "0";
    String mOffer_Desc;
    private LinearLayout ll_Telecom, ll_ServiceType, ll_LineType, ll_BouqueType, ll_AdvancePayment, ll_BouquetDtl, ll_Amount;
    private TextView tv_title, tv_account_no, tv_phone_no, tv_ServiceType, tv_LineType, tv_BouquetType, tv_AdvancePayment, tv_BouquetDtl, tv_amount;
    private AutoCompleteTextView oauto_ed_phone_no,auto_ed_phone_no;
    private EditText ed_amount;
    private Spinner sp_BouquetType,sp_account_no, sp_BouquetDtl;
    private RadioGroup rgServiceType, rgLineType, rgAdvancePayment;
    private RadioButton rbInternetOrBalance, rbInternet, rbBalance, rgPrepaid, rgPostpaid, rdAdvancePaymentNo, rdAdvancePaymentYes;
    private Button b_ok, b_favorite_add, b_cancel, b_getInfo;
    private ArrayList<String> mSpinnerBouquetTypeArrayList, mSpinnerBouquetDtlArrayList;
    private ArrayList<NameValuePair> mBouquetTypeArrayList, mBouquetDtlArrayList;
    private String mMainServicesCode;
    private String mDetailServicesCode;
    private String service_code;
    private boolean mEnableFavorite;
    private String m_password;
    Animation animSlideDown;
    TextInputLayout PhoneNumberInput;
    ImageView ivLogo;
    RecyclerView recyclerView;
    public RecyclerViewAdapter bundleAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            Bundle b = this.getIntent().getExtras();
            service_code = b.getString("service_code");
            String m_title = b.getString("title");
            mEnableFavorite = b.getBoolean("enableFavorite");
            mMainServicesCode = myApp.getGlobal_Last_Service_CODE();
            mDetailServicesCode = service_code;
            m_password = myApp.getGlobal_Password();
            animSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);

            initComponent();
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, "onCreate\n" + e.toString(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.telecom;
    }

    private void initHeader() {
        try {
            tv_title = (TextView) Utils.populateView(this, "TextView", R.id.tv_title, 0);
            tv_title.setText("");
            tv_account_no = (TextView) Utils.populateView(this, "TextView", R.id.tv_account_no, 0);
            assert tv_account_no != null;
            sp_account_no = (Spinner) Utils.populateView(this, "Spinner", R.id.sp_account_no, 0);
            Utils.populateAccountSpinnerAdapter(this, sp_account_no, null);
            b_ok = (Button) Utils.populateView(this, "Button", R.id.b_ok, 0);
            assert b_ok != null;
            b_favorite_add = (Button) Utils.populateView(this, "Button", R.id.b_favorite_add, 0);
            if (!mEnableFavorite) {
                assert b_favorite_add != null;
                b_favorite_add.setVisibility(View.INVISIBLE);
            }
            b_cancel = (Button) Utils.populateView(this, "Button", R.id.b_cancel, 0);

            assert b_cancel != null;
          //  ll_logos.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, "initHeader \n" + e.toString());
        }
    }
    public void pubulate(ArrayList bundls) {
        if(bundls.isEmpty()){
            ll_Amount.setVisibility(View.VISIBLE);
        }else {
            ll_Amount.setVisibility(View.INVISIBLE);

        }
        recyclerView = (RecyclerView) findViewById(R.id.re_BouquetType);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerViewAdapter(bundls));
    }
    @Override
    public void initComponent() {
        try {
            mBouquetTypeArrayList = new ArrayList<>();
            mBouquetDtlArrayList = new ArrayList<>();
            mSpinnerBouquetTypeArrayList = new ArrayList<>();
            mSpinnerBouquetDtlArrayList = new ArrayList<>();

            initHeader();

            Utils.setBankBackgroundLogo(this,myApp.getGlobal_Com_Id());

            ll_Telecom = (LinearLayout) Utils.populateView(TeleCom.this, "LinearLayout", R.id.ll_Telecom, 0);
            assert ll_Telecom != null;

           /* tv_phone_no = (TextView) Utils.populateView(this, "TextView", R.id.tv_phone_no, 0);
            assert tv_phone_no != null;*/
           // oauto_ed_phone_no = (AutoCompleteTextView) Utils.populateView(TeleCom.this, "AutoCompleteTextView", R.id.auto_ed_phone_no, InputType.TYPE_CLASS_TEXT);
            auto_ed_phone_no = (AutoCompleteTextView) Utils.populateView(TeleCom.this, "AutoCompleteTextView", R.id.etPhoneNumber, InputType.TYPE_CLASS_TEXT);
            PhoneNumberInput = (TextInputLayout) Utils.populateView(TeleCom.this, "TextInputLayout", R.id.PhoneNumberInput, 0);
            ivLogo = (ImageView) Utils.populateView(TeleCom.this, "ImageView", R.id.ivLogo, 0);
            Utils.populateAutoCompAdapter(TeleCom.this, auto_ed_phone_no, Utils.getArrayListFromTable(TeleCom.this, "select last_entry from last_entry  order by type_id "));
            auto_ed_phone_no.requestFocus();
            auto_ed_phone_no.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    textChanged(s);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
            });


            ll_ServiceType = (LinearLayout) Utils.populateView(TeleCom.this, "LinearLayout", R.id.ll_ServiceType, 0);
            assert ll_ServiceType != null;

         /*   tv_ServiceType = (TextView) Utils.populateView(this, "TextView", R.id.tv_ServiceType, 0);
            assert tv_ServiceType != null;*/
            rgServiceType = (RadioGroup) Utils.populateView(this, "RadioGroup", R.id.rgServiceType, 0);
            rbInternetOrBalance = (RadioButton) Utils.populateView(this, "RadioButton", R.id.rbInternetOrBalance, 0);
            rbInternet = (RadioButton) Utils.populateView(this, "RadioButton", R.id.rbInternet, 0);
            rbBalance = (RadioButton) Utils.populateView(this, "RadioButton", R.id.rbBalance, 0);
            rgServiceType.clearCheck();
            rgServiceType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rbInternetOrBalance:
                            mServiceTypeValue = "-1";
                            break;
                        case R.id.rbInternet:
                            tv_title.setText(mTelecomName + "-" + getResources().getString(R.string.internet_bouquet));
                            mServiceTypeValue = "1";
                            break;
                        case R.id.rbBalance:
                            tv_title.setText(mTelecomName + "-" + getResources().getString(R.string.balance));
                            mServiceTypeValue = "2";
                            break;
                    }
                    if (!mServiceTypeValue.equalsIgnoreCase("-1")) {

                        b_getInfo.setVisibility(View.VISIBLE);


                        ll_LineType.setVisibility(View.VISIBLE);
                        ll_BouqueType.setVisibility(View.VISIBLE);
                        ll_AdvancePayment.setVisibility(View.VISIBLE);
/*
                        ll_BouquetDtl.setVisibility(View.VISIBLE);
*/
                        ll_Amount.setVisibility(View.VISIBLE);

                        b_getInfo.startAnimation(animSlideDown);
                        ll_LineType.startAnimation(animSlideDown);
                        ll_BouqueType.startAnimation(animSlideDown);
                        ll_AdvancePayment.startAnimation(animSlideDown);
/*
                        ll_BouquetDtl.startAnimation(animSlideDown);
*/
                        ll_Amount.startAnimation(animSlideDown);


                        popList(sp_BouquetType, "BouquetType");
                        popList(sp_BouquetDtl, "BouquetDtl");

                    }


                }

            });

            b_getInfo = (Button) Utils.populateView(TeleCom.this, "Button", R.id.b_getInfo, 0);
            assert b_getInfo != null;
            b_getInfo.setVisibility(View.INVISIBLE);
            ll_ServiceType.setVisibility(View.INVISIBLE);


            ll_LineType = (LinearLayout) Utils.populateView(TeleCom.this, "LinearLayout", R.id.ll_LineType, 0);
            assert ll_LineType != null;
            ll_LineType.setVisibility(View.INVISIBLE);
         /*   tv_LineType = (TextView) Utils.populateView(this, "TextView", R.id.tv_LineType, 0);
            assert tv_LineType != null;*/
            rgLineType = (RadioGroup) Utils.populateView(this, "RadioGroup", R.id.rgLineType, 0);
            rgPrepaid = (RadioButton) Utils.populateView(this, "RadioButton", R.id.rgPrepaid, 0);
            rgPostpaid = (RadioButton) Utils.populateView(this, "RadioButton", R.id.rgPostpaid, 0);
            rgLineType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rgPrepaid:
                            mLineTypeValue = "2";
                            break;
                        case R.id.rgPostpaid:
                            mLineTypeValue = "1";
                            break;
                    }
                 //   pubulate(sp_BouquetType,mBouquetTypeArrayList );
                    popList(sp_BouquetDtl, "BouquetDtl");
                    popList(sp_BouquetType, "BouquetType");


                }

            });

            ll_BouqueType = (LinearLayout) Utils.populateView(TeleCom.this, "LinearLayout", R.id.ll_BouqueType, 0);
            assert ll_BouqueType != null;
            ll_BouqueType.setVisibility(View.INVISIBLE);
         /*   tv_BouquetType = (TextView) Utils.populateView(this, "TextView", R.id.tv_BouquetType, 0);
            assert tv_BouquetType != null;*/
            sp_BouquetType = (Spinner) Utils.populateView(this, "Spinner", R.id.sp_BouquetType, 0);
              popList(sp_BouquetType, "BouquetType");
            sp_BouquetType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    mBouquetTypeValue = mBouquetTypeArrayList.get(sp_BouquetType.getSelectedItemPosition()).getValue();
                    Log.e(Utils.APP_TAG, "mBouquetTypeValue = " + mBouquetTypeValue);
                    popList(sp_BouquetDtl, "BouquetDtl");

                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                    mBouquetTypeValue = "0";
                    popList(sp_BouquetDtl, "BouquetDtl");

                }
            });
            ll_AdvancePayment = (LinearLayout) Utils.populateView(TeleCom.this, "LinearLayout", R.id.ll_AdvancePayment, 0);
            assert ll_AdvancePayment != null;
            ll_AdvancePayment.setVisibility(View.INVISIBLE);
            tv_AdvancePayment = (TextView) Utils.populateView(this, "TextView", R.id.tv_AdvancePayment, 0);
            assert tv_AdvancePayment != null;
            rgAdvancePayment = (RadioGroup) Utils.populateView(this, "RadioGroup", R.id.rgAdvancePayment, 0);
            rdAdvancePaymentNo = (RadioButton) Utils.populateView(this, "RadioButton", R.id.rdAdvancePaymentNo, 0);
            rdAdvancePaymentYes = (RadioButton) Utils.populateView(this, "RadioButton", R.id.rdAdvancePaymentYes, 0);
            rgAdvancePayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rdAdvancePaymentNo:
                            mAdvancePaymentValue = "0";
                            break;
                        case R.id.rdAdvancePaymentYes:
                            mAdvancePaymentValue = "1";
                            break;
                    }
                    Log.e(Utils.APP_TAG, "rgAdvancePayment = " + mAdvancePaymentValue);
                    popList(sp_BouquetDtl, "BouquetDtl");


                }

            });


          /*  ll_BouquetDtl = (LinearLayout) Utils.populateView(TeleCom.this, "LinearLayout", R.id.ll_BouquetDtl, 0);
            assert ll_BouquetDtl != null;
            ll_BouquetDtl.setVisibility(View.INVISIBLE);
            tv_BouquetDtl = (TextView) Utils.populateView(this, "TextView", R.id.tv_BouquetDtl, 0);
            assert tv_BouquetDtl != null;
            sp_BouquetDtl = (Spinner) Utils.populateView(this, "Spinner", R.id.sp_BouquetDtl, 0);
            sp_BouquetDtl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    mBouquetDtlValue = mBouquetDtlArrayList.get(sp_BouquetDtl.getSelectedItemPosition()).getValue();
                    Log.e(Utils.APP_TAG, "mBouquetDtlValue = " + mBouquetDtlValue);
                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                    mBouquetDtlValue = "0";
                }
            });
*/

            ll_Amount = (LinearLayout) Utils.populateView(TeleCom.this, "LinearLayout", R.id.ll_Amount, 0);
            assert ll_Amount != null;
            ll_Amount.setVisibility(View.INVISIBLE);
            tv_amount = (TextView) Utils.populateView(this, "TextView", R.id.tv_amount, 0);
            ed_amount = (EditText) Utils.populateView(this, "EditText", R.id.ed_amount, InputType.TYPE_CLASS_NUMBER);
            assert ed_amount != null;
            ed_amount.addTextChangedListener(Utils.onTextChangedListener(ed_amount));

        } catch (Exception e) {
            Utils.showConfirmationDialog(TeleCom.this, "initComponent \n" + e.toString(), getResources().getString(R.string.app_name));

        }
    }


    private void popList(Spinner p_Spinner, String p_ListType) {
        String mSql = "";
        if (p_ListType.equalsIgnoreCase("BouquetType")) {
            mSql = "select case '" + m_language.toUpperCase() + "' when 'AR' then service_name_ar else service_name_en end service_name , service_id service_id " +
                    " from telecom_services " +
                    " where com_id ='" + myApp.getGlobal_Com_Id() + "' " +
                    " and telecom_id='" + mTelecomId + "' " +
                    " and service_type='" + mServiceTypeValue + "' " +
                    " and linetype_id ='" + mLineTypeValue + "' " +
                    " order by com_id,telecom_id,service_type,linetype_id,service_id ";
        } else if (p_ListType.equalsIgnoreCase("BouquetDtl")) {
            mSql = "select case '" + m_language.toUpperCase() + "' when 'AR' then service_dtl_name_ar else service_dtl_name_en end service_name , service_dtl_value  " +
                    " from telecom_services_dtl " +
                    " where com_id ='" + myApp.getGlobal_Com_Id() + "' " +
                    " and telecom_id='" + mTelecomId + "' " +
                    " and service_type='" + mServiceTypeValue + "' " +
                    " and linetype_id ='" + mLineTypeValue + "' " +
                    " and service_id ='" + mBouquetTypeValue + "' " +
                    " and with_advance_payment ='" + mAdvancePaymentValue + "' " +
                    " order by com_id,telecom_id,service_type,linetype_id,service_id,service_dtl_id ";
        }

        try {
            if (p_ListType.equalsIgnoreCase("BouquetType")) {
                mBouquetTypeArrayList.clear();
                mBouquetTypeArrayList = Utils.getArrayListFromDBNameValuePair(TeleCom.this, mSql);
                mSpinnerBouquetTypeArrayList.clear();
                for (int i = 0; i < mBouquetTypeArrayList.size(); i++) {
                    mSpinnerBouquetTypeArrayList.add(mBouquetTypeArrayList.get(i).getName());
                }
                if (mSpinnerBouquetTypeArrayList.size() > 0) {
                    Utils.populateSpinnerAdapter(TeleCom.this, p_Spinner, mSpinnerBouquetTypeArrayList, R.layout.spinner_item);

                } else {
                    p_Spinner.setAdapter(null);

                }
                if(mSpinnerBouquetTypeArrayList.size()<=1){
                    ll_BouqueType.setVisibility(View.INVISIBLE);
                }

            } else if (p_ListType.equalsIgnoreCase("BouquetDtl")) {
                mBouquetDtlArrayList.clear();
                mBouquetDtlArrayList = Utils.getArrayListFromDBNameValuePair(TeleCom.this, mSql);
                mSpinnerBouquetDtlArrayList.clear();
                for (int i = 0; i < mBouquetDtlArrayList.size(); i++) {
                    mSpinnerBouquetDtlArrayList.add(mBouquetDtlArrayList.get(i).getName());
                }
                if (mSpinnerBouquetDtlArrayList.size() > 0) {
     //               Utils.populateSpinnerAdapter(TeleCom.this, p_Spinner, mSpinnerBouquetDtlArrayList, R.layout.spinner_item);
                    pubulate(mSpinnerBouquetDtlArrayList);
               /*     bmb1 = (BoomMenuButton) TeleCom.this.findViewById(R.id.bmb1);
                    bmb3 = (BoomMenuButton) TeleCom.this.findViewById(R.id.bmb3);
                    bmb1.setVisibility(View.INVISIBLE);
                    bmb3.setVisibility(View.INVISIBLE);*/
                } else {
                 //   p_Spinner.setAdapter(null);
                    pubulate(mSpinnerBouquetDtlArrayList);

                }

            }

        } catch (Exception e) {
            Utils.showConfirmationDialog(TeleCom.this, "popList\n" + e.toString(), getResources().getString(R.string.error));
            // Log.e(Utils.APP_TAG, "p_ListType = " + p_ListType + "\n" + e.toString());
        }
    }


    private void textChanged(Editable s) {

        String newS = s.toString();
        newS = Utils.convertArDigitToEn(newS);
        String mTextColor = "#FFFFFF";
        mTelecomId = "";
        mTelecomColor = "";
        ll_Telecom.setVisibility(View.INVISIBLE);
        tv_title.setVisibility(View.INVISIBLE);
        mServiceTypeValue = "-1";
        rgServiceType.check(R.id.rbInternetOrBalance);
        mLineTypeValue = "1";
        rgLineType.check(R.id.rgPostpaid);
        mAdvancePaymentValue = "0";
        rgAdvancePayment.check(R.id.rdAdvancePaymentNo);

        //rgServiceType.check(R.id.rbInternetOrBalance);
        rgServiceType.clearCheck();
        if(s.toString().length()>=1) {
            b_getInfo.setVisibility(View.INVISIBLE);
        }
        ll_ServiceType.setVisibility(View.VISIBLE);

        ll_LineType.setVisibility(View.INVISIBLE);
        ll_BouqueType.setVisibility(View.INVISIBLE);
        ll_AdvancePayment.setVisibility(View.INVISIBLE);
      /*  ll_BouquetDtl.setVisibility(View.INVISIBLE);*/
        ll_Amount.setVisibility(View.INVISIBLE);

        try {
            boolean f = false;
            if (newS.length() >= 1) {
                int m_index = newS.indexOf(":");
                if (m_index > 0) {
                    newS = newS.substring(m_index + 1).trim();

                }

                mTelecomId = Utils.getColumnValue(this, "select telecom_id from telecom_info where com_id='" + myApp.getGlobal_Com_Id() + "' and telecom_prefix =substr('" + newS + "',1,length(telecom_prefix))");
                if (!Utils.is_Null(mTelecomId)) {
                    /*
                      ServiceType : Internet     |       Balance                        -> Radio Group 0 1 2
                      LineType    : Prepaid      |       Postpaid                       -> Radio Group 1 2
                      ServiceId   : [ Name:1  , Name:2 , ]                      -> Spinner
                      -------------------------------------------------------------------------------------------
                      WithAdvancePayment: No : Yes                                      -> Radio Group 0 1
                      ServiceDtlId   : [ Name:1  , Name:2 , ]                   -> Spinner
                     */
/*
            options    متسلف            بوابة الجنوب      offlineSms
                        is_Quary          is_auto_Quary

                       bagat     |       amount |        fiction

                       bagat
                       list
                       action  payonly activate_only payfull&activate paysub&activate(enter amount)

                       amount
                       is_fictions_avalible
                       is_text_editable

                       fiction
                       list

                      LineType    : Prepaid      |       Postpaid                       -> Radio Group 1 2
                      ServiceId   : [ Name:1  , Name:2 , ]                      -> Spinner
                      -------------------------------------------------------------------------------------------
                      WithAdvancePayment: No : Yes                                      -> Radio Group 0 1
                      ServiceDtlId   : [ Name:1  , Name:2 , ]                   -> Spinner
                     */
                    mTelecomName = Utils.getColumnValue(this, "select telecom_name_ar from telecoms where com_id='" + myApp.getGlobal_Com_Id() + "' and telecom_id='" + mTelecomId + "'");
                    mTelecomColor = Utils.getColumnValue(this, "select telecom_color from telecoms where com_id='" + myApp.getGlobal_Com_Id() + "' and telecom_id='" + mTelecomId + "'");
                    tv_title.setText(mTelecomName);

                    if (mTelecomColor.equalsIgnoreCase("#ffffff")) {
                        mTelecomColor = "#07830C";
                    }

                    Drawable unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.l_orange_temp);
/*
                    PhoneNumberInput.color(mTelecomColor);
*/
                   // ivLogo.setImageDrawable(unwrappedDrawable);
                    Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                    DrawableCompat.setTint(wrappedDrawable, Color.parseColor(mTelecomColor));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                        tv_title.setBackground(getResources().getDrawable(R.drawable.l_orange_temp));
                        b_getInfo.setBackground(getResources().getDrawable(R.drawable.l_orange_temp));
                    } else {
                        tv_title.setBackgroundColor(Color.parseColor(mTelecomColor));
                        b_getInfo.setBackgroundColor(Color.parseColor(mTelecomColor));
                    }

                    tv_account_no.setTextColor(Color.parseColor(mTelecomColor));
                   // tv_phone_no.setTextColor(Color.parseColor(mTelecomColor));
                  //  tv_ServiceType.setTextColor(Color.parseColor(mTelecomColor));
                   // tv_LineType.setTextColor(Color.parseColor(mTelecomColor));
                  //  tv_BouquetType.setTextColor(Color.parseColor(mTelecomColor));
                    tv_AdvancePayment.setTextColor(Color.parseColor(mTelecomColor));
/*
                    tv_BouquetDtl.setTextColor(Color.parseColor(mTelecomColor));
*/
                    tv_amount.setTextColor(Color.parseColor(mTelecomColor));

                    tv_title.setTextColor(Color.parseColor(mTextColor));
                    b_getInfo.setTextColor(Color.parseColor(mTextColor));
                    ll_Telecom.setVisibility(View.VISIBLE);
                    tv_title.setVisibility(View.VISIBLE);

                }
                Log.e(Utils.APP_TAG, "Telecom Id= " + mTelecomId + " Telecom Name= " + mTelecomName + " Telecom Color= " + mTelecomColor);
            }
        } catch (Exception e) {
            Log.e(Utils.APP_TAG, "textChanged \n" + e.toString());
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        String account_no = Utils.convertArDigitToEn(sp_account_no.getItemAtPosition(sp_account_no.getSelectedItemPosition()).toString());
        account_no = account_no.substring(0, account_no.indexOf(":"));
        switch (v.getId()) {
            case R.id.b_getInfo:
                //sendQueryRequest(account_no);
                break;
            case R.id.b_ok:
                if (myApp.getGlobal_OperationONOFF()) {
                    //sendRequest(account_no);
                } else {
                    Utils.showConfirmationDialog(this, getResources().getString(R.string.new_operation_error), getResources().getString(R.string.app_name));
                }
                break;
            case R.id.b_favorite_add:
                if (Utils.addedToFavorite(TeleCom.this, mMainServicesCode, mDetailServicesCode)) {
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
/*
    public boolean sendQueryRequest(String account_no) {
        try {
            String m_ed_phone_no = Utils.convertArDigitToEn(auto_ed_phone_no.getText().toString());
            int m_index = m_ed_phone_no.indexOf(":");
            if (m_index != -1) {
                m_ed_phone_no = m_ed_phone_no.substring(m_index + 1).trim();
            }

            if ((m_ed_phone_no.length() != 0) && (((m_ed_phone_no.length() == 8) && (m_ed_phone_no.startsWith("0")))
                    || ((m_ed_phone_no.length() == 9) &&
                    (m_ed_phone_no.startsWith("70") ||
                            m_ed_phone_no.startsWith("71") ||
                            m_ed_phone_no.startsWith("73") ||
                            m_ed_phone_no.startsWith("77"))))) {
                if (((m_ed_phone_no.length() != 0) && (((m_ed_phone_no.length() == 8) && (m_ed_phone_no.startsWith("0")))))
                        && (!(radioPhone.isChecked() || radioInternet.isChecked()))) {
                    Utils.showConfirmationDialog(this, getResources().getString(R.string.phone_or_internet_error), getResources().getString(R.string.app_name));
                    radioPhone.requestFocus();
                    return false;
                }

                if (account_no.length() != 0) {
                    String m_password = myApp.getGlobal_Password();
                    String sendMSG = "999:" + account_no + ":" + m_ed_phone_no + ":" + service_code + ":" + m_password + ":0",
                            logMSG = "999-" + getResources().getString(R.string.telecom_balance_query) + "\n" +
                                    tv_title.getText().toString() + "\n" +
                                    tv_account_no.getText().toString() + " : " + account_no + "\n" +
                                    tv_phone_no.getText().toString() + " : " + m_ed_phone_no;


                    sendMSG = Utils.convertArDigitToEn(sendMSG);
                    logMSG = Utils.convertArDigitToEn(logMSG);
                    myApp.setGlobal_OperationONOFF(false);
                    if (Utils.isInternetConnected(TeleComOld.this)) {
                        Utils.confirmSendRequest(this, logMSG, getResources().getString(R.string.request_send_over_internet), "Internet", account_no, sendMSG, logMSG, sendMSG, true, "1",Utils.SEND_CMD);
                        Utils.addToLastEntry(this, m_ed_phone_no.trim());
                        Utils.populateAutoCompAdapter(TeleComOld.this, auto_ed_phone_no, Utils.getArrayListFromTable(TeleComOld.this, "select last_entry from last_entry  order by type_id"));
                    } else {
                        myApp.setGlobal_OperationONOFF(true);
                        Utils.showConfirmationDialog(TeleComOld.this, getResources().getString(R.string.internet_error), getResources().getString(R.string.app_name));
                    }
                    return true;
                } else {
                    Utils.showConfirmationDialog(this, getResources().getString(R.string.msg_check_account), getResources().getString(R.string.app_name));
                    return false;
                }
            } else {
                myApp.setGlobal_OperationONOFF(true);
                Utils.showConfirmationDialog(this, getResources().getString(R.string.phone_length_and_start_with_zero) + "\n" +
                        getResources().getString(R.string.msg_or) + "\n" +
                        getResources().getString(R.string.mobile_length_and_start_with_zero), getResources().getString(R.string.app_name));
                return false;
            }
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, e.toString(), getResources().getString(R.string.app_name));
            return false;
        }
    }

    public boolean sendRequest(String account_no) {
        try {
            boolean flag = false;
            String sendMSG = "", logMSG = "";
            String m_ed_phone_no = Utils.convertArDigitToEn(auto_ed_phone_no.getText().toString());
            String m_ed_amount = Utils.convertArDigitToEn(ed_phone_amount.getText().toString());
            String m_ed_password = Utils.convertArDigitToEn(ed_password.getText().toString());
            if (myApp.getGlobal_OperationONOFF()) {
                int m_index = m_ed_phone_no.indexOf(":");
                if (m_index != -1) {
                    m_ed_phone_no = m_ed_phone_no.substring(m_index + 1).trim();
                }
                if ((m_ed_phone_no.length() != 0) && (((m_ed_phone_no.length() == 8) && (m_ed_phone_no.startsWith("0")))
                        || ((m_ed_phone_no.length() == 9) &&
                        (m_ed_phone_no.startsWith("70") ||
                                m_ed_phone_no.startsWith("71") ||
                                m_ed_phone_no.startsWith("73") ||
                                m_ed_phone_no.startsWith("77"))))) {
                    if ((account_no.length() != 0) && (m_ed_phone_no.length() != 0) && (m_ed_password.length() != 0)) {

                        if (service_code.equals("111") || service_code.equals("112")) {
                            if (!(radioPhone.isChecked() || radioInternet.isChecked())) {
                                Utils.showConfirmationDialog(this, getResources().getString(R.string.phone_or_internet_error), getResources().getString(R.string.app_name));
                                radioPhone.requestFocus();
                                return false;
                            }
                            if ((m_ed_phone_no.length() != 8) || (!m_ed_phone_no.startsWith("0"))) {
                                Utils.showConfirmationDialog(this, getResources().getString(R.string.phone_length_and_start_with_zero), getResources().getString(R.string.app_name));
                                auto_ed_phone_no.requestFocus();
                                return false;
                            }
                        } else {
                            if ((m_ed_phone_no.length() != 9) || (!m_ed_phone_no.startsWith("7"))) {
                                Utils.showConfirmationDialog(this, getResources().getString(R.string.mobile_length_and_start_with_zero), getResources().getString(R.string.app_name));
                                auto_ed_phone_no.requestFocus();
                                return false;
                            }
                        }
                        sendMSG = service_code + ":" + account_no + ":" + m_ed_phone_no + ":";
                        logMSG = logMSG + tv_title.getText().toString() + "\n" + getResources().getString(R.string.msg_from) + " : " + account_no + "\n"
                                + getResources().getString(R.string.msg_to) + " : " + m_ed_phone_no + "\n";
                        if (!(m_ed_password.equals(m_password) || m_ed_password.equals(Utils.encoding(m_password)))) {
                            myApp.setGlobal_OperationONOFF(true);
                            ed_password.setError(getResources().getString(R.string.password_error));
                            return false;
                        } else {

                            switch (service_code) {
                                case "111":// internet adsl payment
                                case "112":// phone payment

                                    if ((ed_phone_amount.getText().length() > 0) && (Integer.parseInt(ed_phone_amount.getText().toString().trim().replaceAll(",", "")) >= 100)) {
                                        sendMSG = sendMSG + m_ed_amount.replaceAll(",", "") + ":" + m_password + ":0";
                                        logMSG = logMSG + getResources().getString(R.string.amount) + " : " + m_ed_amount;
                                        flag = true;
                                    } else {
                                        ed_phone_amount.requestFocus();
                                        ed_phone_amount.setError(getResources().getString(R.string.missing_info));
                                    }
                                    break;
                                case "101": // yemen mobile balance payment
                                    String m_ed_ym_amount = Utils.convertArDigitToEn(ed_ym_amount.getText().toString());
                                    if ((m_ed_ym_amount.length() > 0) && (Integer.parseInt(m_ed_ym_amount.trim().replaceAll(",", "")) >= 100)) {
                                        sendMSG = sendMSG + m_ed_ym_amount.replaceAll(",", "") + ":" + m_password + ":0";
                                        logMSG = logMSG + getResources().getString(R.string.amount) + " : " + m_ed_ym_amount;
                                        flag = true;
                                    } else {
                                        ed_ym_amount.requestFocus();
                                        ed_ym_amount.setError(getResources().getString(R.string.missing_info));
                                    }
                                    break;
                                case "115": // yemen mobile prepayed payment
                                    String m_sp_ym_offer = sp_ym_offer.getItemAtPosition(sp_ym_offer.getSelectedItemPosition()).toString();

                                    if (m_sp_ym_offer.length() != 0) {

                                        sendMSG = sendMSG + m_sp_ym_offer.substring(0, m_sp_ym_offer.indexOf(":")) + ":" + m_password + ":0";
                                        logMSG = logMSG + getResources().getString(R.string.amount) + " : " + m_sp_ym_offer;
                                        flag = true;
                                    } else {
                                        sp_ym_offer.requestFocus();

                                    }
                                    break;
                                case "116": // mtn mobile prepayed payment
                                    String m_sp_mtn_baq_offer = sp_mtn_offer.getItemAtPosition(sp_mtn_offer.getSelectedItemPosition()).toString();

                                    if (m_sp_mtn_baq_offer.length() != 0) {

                                        sendMSG = sendMSG + m_sp_mtn_baq_offer.substring(0, m_sp_mtn_baq_offer.indexOf(":")) + ":" + m_password + ":0";
                                        logMSG = logMSG + getResources().getString(R.string.amount) + " : " + m_sp_mtn_baq_offer;
                                        flag = true;
                                    } else {
                                        sp_ym_offer.requestFocus();

                                    }
                                    break;
                                case "102": // sabafon payment
                                    String m_sp_sf_Line_Type = sp_sf_Line_Type.getItemAtPosition(sp_sf_Line_Type.getSelectedItemPosition()).toString();
                                    String m_sp_sf_offer = sp_sf_offer.getItemAtPosition(sp_sf_offer.getSelectedItemPosition()).toString();

                                    if ((m_sp_sf_Line_Type.length() != 0) && (m_sp_sf_offer.length() != 0)) {

                                        sendMSG = sendMSG + m_sp_sf_Line_Type + ":" + m_sp_sf_offer + ":" + m_password + ":0";
                                        logMSG = logMSG + tv_sf_line_type.getText() + " : " + m_sp_sf_Line_Type + "\n"
                                                + tv_sf_offer.getText() + " " + m_sp_sf_offer;
                                        flag = true;
                                    } else {
                                        sp_sf_Line_Type.requestFocus();
                                    }
                                    break;
                                case "103": // MTN payment
                                    String m_sp_mtn_offer = sp_mtn_offer.getItemAtPosition(sp_mtn_offer.getSelectedItemPosition()).toString();

                                    if ((m_sp_mtn_offer.length() != 0)) {

                                        sendMSG = sendMSG + m_sp_mtn_offer + ":" + m_password + ":0";
                                        logMSG = logMSG + tv_sf_line_type.getText() + " : " + m_sp_mtn_offer;
                                        flag = true;
                                    } else {
                                        sp_mtn_offer.requestFocus();
                                    }

                                    break;
                                case "104": // Y payment
                                    String m_sp_y_offer = sp_y_offer.getItemAtPosition(sp_y_offer.getSelectedItemPosition()).toString();

                                    if ((m_sp_y_offer.length() != 0)) {

                                        sendMSG = sendMSG + m_sp_y_offer + ":" + m_password + ":0";
                                        logMSG = logMSG + tv_sf_line_type.getText() + " : " + m_sp_y_offer;
                                        flag = true;
                                    } else {
                                        sp_y_offer.requestFocus();
                                    }
                                    break;
                                default:
                                    break;
                            }
                            if (flag) {

                                sendMSG = Utils.convertArDigitToEn(sendMSG);
                                logMSG = Utils.convertArDigitToEn(logMSG);
                                myApp.setGlobal_OperationONOFF(false);

                                Utils.sendCommand(TeleComOld.this, account_no, sendMSG, logMSG, true, "0",Utils.SEND_CMD);
                                Utils.addToLastEntry(this, m_ed_phone_no.trim());
                                Utils.populateAutoCompAdapter(TeleComOld.this, auto_ed_phone_no, Utils.getArrayListFromTable(TeleComOld.this, "select last_entry from last_entry  order by type_id"));

                            } else {// flag
                                myApp.setGlobal_OperationONOFF(true);
                                Utils.showConfirmationDialog(this, getResources().getString(R.string.missing_info), getResources().getString(R.string.app_name));
                            }
                            return true;
                        }
                    } else {

                        if (m_ed_phone_no.length() == 0) {
                            auto_ed_phone_no.requestFocus();
                            auto_ed_phone_no.setError(getResources().getString(R.string.phone_error));
                        } else {
                            ed_password.requestFocus();
                            ed_password.setError(getResources().getString(R.string.password_error));
                        }

                        return false;
                    }
                } else {
                    Utils.showConfirmationDialog(this, getResources().getString(R.string.phone_length_and_start_with_zero) + "\n" +
                            getResources().getString(R.string.msg_or) + "\n" +
                            getResources().getString(R.string.mobile_length_and_start_with_zero), getResources().getString(R.string.app_name));
                    return false;
                }
            } else {
                Utils.showConfirmationDialog(this, getResources().getString(R.string.new_operation_error), getResources().getString(R.string.app_name));
                return false;
            }


        } catch (Exception e) {
            Utils.showConfirmationDialog(this, e.toString(), getResources().getString(R.string.app_name));
            return false;
        }
    }
    */

}