package dev.alamalbank.amb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import dev.alamalbank.amb.Util.BaseActivity;
import dev.alamalbank.amb.Util.CustomLayoutManager;
import dev.alamalbank.amb.Util.ListViewBean;
import dev.alamalbank.amb.Util.RecyclerAdapter;
import dev.alamalbank.amb.Util.RecyclerItemClickListener;
import dev.alamalbank.amb.Util.Utils;
import com.scwang.wave.MultiWaveHeader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main_Activity extends BaseActivity {

    public static final int SD_REQUEST = 9999;
    public int mainServices_Position = 0, favorites_Position = 0, mainServicesListViewBeansSize = 0, favoritesListViewBeansSize = 0;
    MultiWaveHeader wave_header;
    RecyclerView mainMenuRecyclerView, favoritesRecyclerView;
    ArrayList<ListViewBean> mainMenuListViewBeans = new ArrayList<>();
    ArrayList<ListViewBean> favoritesListViewBeans = new ArrayList<>();
    RecyclerAdapter mainMenuRecyclerAdapter, favoritesRecyclerAdapter;
    Timer mainService_timer, favorites_timer;
    TimerTask mainServices_timerTask, favorites_timerTask;
    CustomLayoutManager mainMenuLayoutManager, favoritesLayoutManager;
    TableLayout tableLayout;
    TableRow prevRowSelection = null;
    HorizontalScrollView horizontalScrollView;
    private TextView tv_username, tv_b_notifications_counter;
    private ImageView user_image;
    private String m_LastLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            m_LastLogin = myApp.getGlobal_LastLogin();
            myApp.setGlobal_ForceLogin_Flag(false);

            wave_header = findViewById(R.id.wave_MainActivity_Header);
            wave_header.setVelocity(1);
            wave_header.setProgress(1);
            wave_header.isRunning();
            wave_header.setGradientAngle(45);
            wave_header.setWaveHeight(40);
            wave_header.setStartColor(getResources().getColor(R.color.color_gold));
            wave_header.setCloseColor(getResources().getColor(R.color.color_turquoise));

            initComponent();
            mainMenuRecyclerViewFunc();
            favoritesRecyclerViewFunc();
            //checkOneSignalPlayerID();
            // Utils.getAccountBalance(this, myApp.getGlobal_User_Account());
            syncInfo();
            checkServices(this);
            initTableLayout();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Utils.hasPermissions(Main_Activity.this, Utils.STORAGE_PERMISSION_CODE, Utils.STORAGE_PERMISSIONS)) {
                    Utils.requestPermission(Main_Activity.this, Utils.STORAGE_PERMISSION_CODE);
                } else {
                    ReadUserImage();
                }
            } else {
                ReadUserImage();
            }
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, "Main.OnCreate : \n" + e.toString(), getResources().getString(R.string.app_name));
        }
    }

    private void mainMenuRecyclerViewFunc() {
        mainMenuRecyclerView = findViewById(R.id.mainMenuRecyclerView);
        mainMenuRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mainMenuRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (mainMenuListViewBeans.get(position).getIsLeave().equalsIgnoreCase("0")) {
                            Utils.runService(Main_Activity.this, myApp.getGlobal_User_Account(), "Services", mainMenuListViewBeans.get(position).getTitle(), mainMenuListViewBeans.get(position).getCode(), false, m_language);
                        } else {
                            Utils.runService(Main_Activity.this, myApp.getGlobal_User_Account(), mainMenuListViewBeans.get(position).getActivityType(), mainMenuListViewBeans.get(position).getTitle(), mainMenuListViewBeans.get(position).getCode(), false, m_language);
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Utils.showToast(Main_Activity.this, mainMenuListViewBeans.get(position).getDescription());
                    }
                })
        );
        mainMenuLayoutManager = new CustomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mainMenuRecyclerView.setLayoutManager(mainMenuLayoutManager);
        refreshMainServicesRecyclerView();
        mainMenuRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 1) {
                    mainServices_StopAutoScrollBanner();
                } else if (newState == 0) {
                    mainServices_RunAutoScrollBanner();
                }
            }
        });
    }

    private void favoritesRecyclerViewFunc() {
        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView);
        favoritesRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, favoritesRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (favoritesListViewBeans.get(position).getIsLeave().equalsIgnoreCase("0")) {
                            Utils.runService(Main_Activity.this, myApp.getGlobal_User_Account(),
                                    favoritesListViewBeans.get(position).getActivityType(),
                                    favoritesListViewBeans.get(position).getTitle(),
                                    favoritesListViewBeans.get(position).getCode(), false, m_language);
                        } else {
                            Utils.runService(Main_Activity.this, myApp.getGlobal_User_Account(),
                                    favoritesListViewBeans.get(position).getActivityType(),
                                    favoritesListViewBeans.get(position).getTitle(),
                                    favoritesListViewBeans.get(position).getCode(), false, m_language);
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Utils.showToast(Main_Activity.this, favoritesListViewBeans.get(position).getDescription());
                    }
                })
        );
        favoritesLayoutManager = new CustomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        favoritesRecyclerView.setLayoutManager(favoritesLayoutManager);
        refreshFavoritesRecyclerView();
        favoritesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 1) {
                    favorites_StopAutoScrollBanner();
                } else if (newState == 0) {
                    favorites_RunAutoScrollBanner();
                }
            }
        });
    }

    private void mainServices_StopAutoScrollBanner() {
        if (mainService_timer != null && mainServices_timerTask != null) {
            mainServices_timerTask.cancel();
            mainService_timer.cancel();
            mainService_timer = null;
            mainServices_timerTask = null;
            mainServices_Position = mainMenuLayoutManager.findFirstCompletelyVisibleItemPosition();
        }
    }

    private void favorites_StopAutoScrollBanner() {
        if (favorites_timer != null && favorites_timerTask != null) {
            favorites_timerTask.cancel();
            favorites_timer.cancel();
            favorites_timer = null;
            favorites_timerTask = null;
            favorites_Position = favoritesLayoutManager.findFirstCompletelyVisibleItemPosition();
        }
    }

    private void mainServices_RunAutoScrollBanner() {
        if (mainService_timer == null && mainServices_timerTask == null) {
            mainService_timer = new Timer();
            mainServices_timerTask = new TimerTask() {
                @Override
                public void run() {

                    if (mainServices_Position == (mainServicesListViewBeansSize - 1)) {
                        mainServices_Position = 0;
                    } else {
                        mainServices_Position = mainServicesListViewBeansSize - 1;
                    }
                    mainMenuRecyclerView.smoothScrollToPosition(mainServices_Position);

                }
            };
            mainService_timer.schedule(mainServices_timerTask, 10000, 15000);
        }
    }

    private void favorites_RunAutoScrollBanner() {
        if (favorites_timer == null && favorites_timerTask == null) {
            favorites_timer = new Timer();
            favorites_timerTask = new TimerTask() {
                @Override
                public void run() {

                    if (favorites_Position == (favoritesListViewBeansSize - 1)) {
                        favorites_Position = 0;
                    } else {
                        favorites_Position = favoritesListViewBeansSize - 1;
                    }
                    favoritesRecyclerView.smoothScrollToPosition(favorites_Position);

                }
            };
            favorites_timer.schedule(favorites_timerTask, 11000, 20000);
        }
    }

    @Override
    public void onClick(View v) {
        boolean flage = false;
        switch (v.getId()) {
            case R.id.b_notifications:
                Utils.runIntent(this, "Web_View", m_language);
                //Utils.runIntent(this, "Notifications", m_language);
                break;
            case R.id.b_favorite:
                Utils.runIntent(this, "Favorites", m_language);
                break;
            case R.id.b_our_services:
                Utils.runIntent(this, "Services", m_language);
                break;
            case R.id.tv_Terms_Of_Use:
                Utils.runIntent(this, "Policies", m_language);
                break;
            case R.id.user_image:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Utils.hasPermissions(Main_Activity.this, Utils.STORAGE_PERMISSION_CODE, Utils.STORAGE_PERMISSIONS)) {
                        Utils.requestPermission(Main_Activity.this, Utils.STORAGE_PERMISSION_CODE);
                    } else {
                        flage = true;
                    }
                } else {
                    flage = true;
                }
                if (flage) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, SD_REQUEST);
                }
                break;
            case R.id.b_exit:
                PyesApp myApp = (PyesApp) getApplication();
                myApp.setGlobal_ExitFromApplication(true);
                finish();
                overridePendingTransition(R.anim.no_animate, R.anim.slide_out);
                break;
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.main_activity;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initComponent() {
        String m_CS_phone = Utils.getColumnValue(this, "select cs_phone from home");
        String m_WhatsAPP = Utils.getColumnValue(this, "select whats_app from home");


        try {

            Utils.setBankBackgroundLogo(Main_Activity.this,myApp.getGlobal_Com_Id());

            tv_username = findViewById(R.id.tv_username);
            tv_username.setTypeface(myFont);

            tv_username.setText(getResources().getString(R.string.user_hello) + " : " + myApp.getGlobal_User_name());

            TextView tv_lastLogin = findViewById(R.id.tv_lastLogin);
            tv_lastLogin.setTypeface(myFont);
            if (m_LastLogin != null) {
                tv_lastLogin.setText(getResources().getString(R.string.lastlogin) + m_LastLogin);
            } else {
                tv_lastLogin.setText("");
            }

            user_image = (ImageView) Utils.populateView(Main_Activity.this, "ImageView", R.id.user_image, 0);
            assert user_image != null;
            user_image.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View arg0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!Utils.hasPermissions(Main_Activity.this, Utils.STORAGE_PERMISSION_CODE, Utils.STORAGE_PERMISSIONS)) {
                            Utils.requestPermission(Main_Activity.this, Utils.STORAGE_PERMISSION_CODE);
                        } else {
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, SD_REQUEST);
                        }
                    } else {
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, SD_REQUEST);
                    }
                    return true;
                }
            });

            TextView tv_Terms_Of_Use = findViewById(R.id.tv_Terms_Of_Use);
            tv_Terms_Of_Use.setTypeface(myFont);
            tv_Terms_Of_Use.setOnClickListener(this);

            TextView tv_to_communicate = findViewById(R.id.tv_to_communicate);
            tv_to_communicate.setTypeface(myFont);
            if (m_CS_phone != null) {
                tv_to_communicate.setText(getResources().getString(R.string.to_communicate)
                        + getResources().getString(R.string.free_phone_after_update) + " : " + m_CS_phone + " "
                        + getResources().getString(R.string.whats_app_after_update) + " : " + m_WhatsAPP);
            } else {
                tv_to_communicate.setText(getResources().getString(R.string.to_communicate)
                        + getResources().getString(R.string.free_phone) + " "
                        + getResources().getString(R.string.whats_app));
            }

            ImageView b_our_services = (ImageView) Utils.populateView(Main_Activity.this, "ImageView", R.id.b_our_services, 0);
            assert b_our_services != null;
            b_our_services.setOnClickListener(this);

            tv_b_notifications_counter = findViewById(R.id.tv_b_notifications_counter);
            tv_b_notifications_counter.setTypeface(myFont);
            if (myApp.getGlobal_NotificationsCounter() != 0) {
                tv_b_notifications_counter.setText(String.valueOf(myApp.getGlobal_NotificationsCounter()));
                tv_b_notifications_counter.setVisibility(View.VISIBLE);
            } else {
                tv_b_notifications_counter.setText("0");
                tv_b_notifications_counter.setVisibility(View.GONE);
            }


            CardView b_notifications = (CardView) Utils.populateView(Main_Activity.this, "CardView", R.id.b_notifications, 0);
            assert b_notifications != null;

            ImageView b_favorite = (ImageView) Utils.populateView(Main_Activity.this, "ImageView", R.id.b_favorite, 0);
            b_favorite.setOnClickListener(this);

            ImageView b_exit = (ImageView) Utils.populateView(Main_Activity.this, "ImageView", R.id.b_exit, 0);
            b_exit.setOnClickListener(this);
            horizontalScrollView = findViewById(R.id.horizontalScrollView);
            tableLayout = findViewById(R.id.tableLayout);

        } catch (Exception e) {
            Utils.showConfirmationDialog(this, "Main.initComponent \n" + e.toString(), getResources().getString(R.string.app_name));
        }

    }

    @Override
    public void onBackPressed() {
        myApp.setGlobal_ExitFromApplication(true);
        mainServices_StopAutoScrollBanner();
        favorites_StopAutoScrollBanner();
        finish();
        overridePendingTransition(R.anim.no_animate, R.anim.slide_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //sliderHandler.postDelayed(sliderRunnable, 10000); //10 seconds
        refreshMainServicesRecyclerView();
        refreshFavoritesRecyclerView();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Utils.hasPermissions(Main_Activity.this, Utils.STORAGE_PERMISSION_CODE, Utils.STORAGE_PERMISSIONS)) {
                    Utils.requestPermission(Main_Activity.this, Utils.STORAGE_PERMISSION_CODE);
                } else {
                    ReadUserImage();
                }
            } else {
                ReadUserImage();
            }
            tv_username.setText(getResources().getString(R.string.user_hello) + " : " + myApp.getGlobal_User_name());

        } catch (Exception e) {
            e.printStackTrace();
            Utils.showConfirmationDialog(this, "Main.OnResume \n" + e.toString(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //sliderHandler.removeCallbacks(sliderRunnable);
        mainServices_StopAutoScrollBanner();
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

    /// new update in 2020/12/25 by Rashad
    private void refreshMainServicesRecyclerView() {
        try {
            mainMenuListViewBeans.clear();
            mainMenuListViewBeans = Utils.getListViewBeanFromDB(this, "MENU", "", m_language);
            mainMenuRecyclerAdapter = new RecyclerAdapter(Main_Activity.this, mainMenuListViewBeans);
            mainMenuRecyclerView.setAdapter(mainMenuRecyclerAdapter);
            mainMenuRecyclerAdapter.notifyDataSetChanged();
            if (mainMenuListViewBeans != null) {
                mainServicesListViewBeansSize = mainMenuListViewBeans.size();
                if (mainServicesListViewBeansSize > 0) {
                    mainServices_Position = 0;
                    mainMenuRecyclerView.scrollToPosition(mainServices_Position);
                    mainServices_RunAutoScrollBanner();
                }
            }

        } catch (Exception e) {
            Utils.showConfirmationDialog(this, "Main.refreshRecyclerView \n" + e.toString(), getResources().getString(R.string.app_name));
        }

    }

    private void refreshFavoritesRecyclerView() {
        try {
            favoritesListViewBeans.clear();
            favoritesListViewBeans = Utils.getListViewBeanFromDB(this, "Favorites", "", m_language);
            favoritesRecyclerAdapter = new RecyclerAdapter(Main_Activity.this, favoritesListViewBeans);
            favoritesRecyclerView.setAdapter(favoritesRecyclerAdapter);
            favoritesRecyclerAdapter.notifyDataSetChanged();
            if (favoritesListViewBeans != null) {
                favoritesListViewBeansSize = favoritesListViewBeans.size();
                if (favoritesListViewBeansSize > 0) {
                    favorites_Position = 0;
                    favoritesRecyclerView.scrollToPosition(favorites_Position);
                    favorites_RunAutoScrollBanner();
                }
            }

        } catch (Exception e) {
            Utils.showConfirmationDialog(this, "Main.refreshFavoritesRecyclerView \n" + e.toString(), getResources().getString(R.string.app_name));
        }

    }

    private void checkServices(Context context) {
        try {
            String isServicesFound = Utils.getColumnValue(context, "select count(*) from menus");
            if (isServicesFound.equalsIgnoreCase("0")) {
                Utils.showToast(context, getResources().getString(R.string.account_services_error));
                if ((Utils.isInternetConnected(context))) {
                    //Utils.runService(this, myApp.getGlobal_User_Account(), "DownloadServices", null, null, false, m_language);
                    Utils.checkAppUpToDate(Main_Activity.this, "2", null,true);
                } else {
                    Utils.showConfirmationDialog(context, getResources().getString(R.string.web_access_error), getResources().getString(R.string.app_name));
                }
            }
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, "Main.checkServices: \n" + e.toString(), getResources().getString(R.string.app_name));
        }
    }

    private void syncInfo() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            initTableLayout();
                            if (myApp.getGlobal_NotificationsCounter() != 0) {
                                tv_b_notifications_counter.setText(String.valueOf(myApp.getGlobal_NotificationsCounter()));
                                tv_b_notifications_counter.setVisibility(View.VISIBLE);
                            } else {
                                tv_b_notifications_counter.setText("0");
                                tv_b_notifications_counter.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, Utils.TIMER_SYNC_INFO, Utils.TIMER_SYNC_INFO);
    }


    private void checkOneSignalPlayerID() {
        String playerID = Utils.getColumnValue(this, "select player_id from home");
        if (playerID == null) {
            playerID = Utils.getPlayerId(myApp.getGlobal_User_Account());
            if (playerID == null) {
                Utils.showConfirmationDialog(this, getResources().getString(R.string.note_player_id_err), getResources().getString(R.string.app_name));
            } else {
                if ((Utils.isInternetConnected(this)) && (!(myApp.getGlobal_User_Account() == null))) {
                    String trans_type_id = "0";
                    String mService = "308:" + myApp.getGlobal_User_Account() + ":" + playerID + ":" + myApp.getGlobal_Password() + ":0";
                    String gURL = Utils.getColumnValue(this, "select WEB_SERVICE_URL from home");
                    new Utils.runFunctionAsync(this,  myApp.getGlobal_User_Account(), mService, trans_type_id, null, Utils.REGISTER, false).execute();
                    Utils.runSQL(this, "update home set player_id='" + playerID + "'");
                    new Utils.runFunctionAsync(this,  myApp.getGlobal_User_Account(), mService, trans_type_id, null, Utils.SEND_TAGS, false).execute();
                }
            }
        }
    }

    private void ReadUserImage() {
        File file = new File(Utils.getFileName("PYes", "userimage.jpg").getAbsolutePath());
        if (file.exists()) {
            Bitmap resourceUserImage = Utils.setPictureDimensions(file.getAbsolutePath(), 1000, 1000);
            user_image.setImageBitmap(resourceUserImage);
        } else {
            user_image.setImageResource(R.drawable.profile_photo);
        }
    }

    private void initTableLayout() {
        final Animation animation;
        animation = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        List<ListViewBean> account_info = Utils.getAccountInfo(this, null);
        int account_info_size = account_info.size();
        if (account_info_size > 0) {
            int textColor = R.color.color_orange;
            tableLayout.removeAllViews();
            TableRow.LayoutParams rowLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
            rowLayoutParams.topMargin = 20;
            rowLayoutParams.bottomMargin = 20;
            // Header
            final TableRow header = new TableRow(this);
            header.setBackgroundResource(R.color.color_trans);//R.drawable.l_background);
            header.setLayoutParams(rowLayoutParams);
            header.addView(textView(getResources().getString(R.string.account_no), true, textColor));
            header.addView(textView(getResources().getString(R.string.account_name), true, textColor));
            header.addView(textView(getResources().getString(R.string.account_type), true, textColor));
            header.addView(textView(getResources().getString(R.string.account_balance), true, textColor));
            header.addView(textView(getResources().getString(R.string.account_curr), true, textColor));
            tableLayout.addView(header);
            prevRowSelection = header;

            //detail

            for (int i = 0; i < account_info_size; i++) {
                final TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(rowLayoutParams);
                if (i == 0) {
                    //tableRow.setBackgroundResource(R.drawable.l_turquoise);
                    prevRowSelection = tableRow;
                    textColor = R.color.color_black;
                } else {
                  //  tableRow.setBackgroundResource(R.drawable.l_background);
                    textColor = R.color.color_label;
                }
                String balance = account_info.get(i).getAccount_balance();
                /*if (!Utils.is_Null(balance) && (!balance.equalsIgnoreCase("0"))) {
                    //TODO: change return value of balance from server side and change the next statement
                    balance = balance.substring(balance.indexOf("مبلغ") + 4, balance.length() - 5);
                }*/
                tableRow.addView(textView(account_info.get(i).getAccount_no(), false, textColor));
                tableRow.addView(textView(account_info.get(i).getAccount_name(), false, textColor));
                tableRow.addView(textView(account_info.get(i).getAccount_type(), false, textColor));
                tableRow.addView(textView(balance, false, textColor));
                tableRow.addView(textView(account_info.get(i).getAccount_curr(), false, textColor));
                tableRow.addView(imageView(account_info.get(i).getAccount_no()));
                tableLayout.addView(tableRow);
                tableRow.setAnimation(animation);


            }
            horizontalScrollView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                }
            }, 100L);
        }
    }

    private TextView textView(String label, boolean is_header, int textColor) {
        final TextView tv = new TextView(this);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
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
        tv.setTextColor(getResources().getColor(textColor));
        if (!is_header) {
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TableRow curRow = ((TableRow) v.getParent());
                    if (prevRowSelection != null) {
                        prevRowSelection.setBackgroundResource(R.drawable.l_background);
                        changeItemsColor(prevRowSelection, R.color.color_turquoise);
                    }
                    curRow.setBackgroundResource(R.drawable.l_turquoise);
                    changeItemsColor(curRow, R.color.color_white);
                    prevRowSelection = curRow;
                }
            });
        }
        return tv;
    }

    private ImageView imageView(final String account_no) {
        final ImageView iv = new ImageView(this);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.leftMargin = 5;
        layoutParams.topMargin = 5;
        layoutParams.rightMargin = 5;
        layoutParams.bottomMargin = 5;
        iv.setLayoutParams(layoutParams);
        iv.setPadding(5, 5, 5, 5);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.sync));
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableRow curRow = ((TableRow) v.getParent());
                if (prevRowSelection != null) {
                    prevRowSelection.setBackgroundResource(R.drawable.l_background);
                    changeItemsColor(prevRowSelection, R.color.color_turquoise);
                }
                curRow.setBackgroundResource(R.drawable.l_turquoise);
                changeItemsColor(curRow, R.color.color_white);
                prevRowSelection = curRow;
               // Utils.getAccountBalance(Main_Activity.this, account_no);
            }
        });

        return iv;
    }

    private void changeItemsColor(TableRow tableRow, int itemColor) {
        int tableRowChildCount = tableRow.getChildCount();
        int i = 0;
        while (i < tableRowChildCount) {
            View view = tableRow.getChildAt(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setTextColor(getResources().getColor(itemColor));
            }
            i++;
        }
    }

}