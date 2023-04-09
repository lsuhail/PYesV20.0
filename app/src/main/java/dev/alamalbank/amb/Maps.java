package dev.alamalbank.amb;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import dev.alamalbank.amb.Util.MapCustomWindow;
import dev.alamalbank.amb.Util.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class Maps extends FragmentActivity implements View.OnClickListener, OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    final float CAMERA_ZOOM = 15.2f;
    final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    public PyesApp myApp;
    public Typeface myFont;
    public String m_language = "ar";
    public int mSDK_INT;
    Spinner sp_branches;
    Button b_back;
    private boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;

    public void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    public void getLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ActivityCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                // && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ) {
                if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    initMap();
                } else {
                    ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
                }
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            mLocationPermissionGranted = true;
            initMap();
        }
    }

    public void moveCamera(LatLng latLng, String mTitle, String mSnippet, float zoom) {
        mMap.setInfoWindowAdapter(new MapCustomWindow(Maps.this));
        MarkerOptions mOptions = new MarkerOptions().position(latLng);
        mOptions.title(mTitle);
        mOptions.snippet(mSnippet);
        mOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mMap.addMarker(mOptions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ActivityCompat.checkSelfPermission(Maps.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                //&& (ActivityCompat.checkSelfPermission(Maps.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ) {
                return;
            }
        }
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionGranted = false;
                        return;
                    }
                }
                mLocationPermissionGranted = true;
                initMap();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            myApp = ((PyesApp) getApplication());
            try {
                m_language = (myApp.getGlobal_Lang() == null ? "ar" : myApp.getGlobal_Lang());
            } catch (Exception e) {
                Log.e(Utils.APP_TAG, e.toString());
            }
            Utils.setLocal(this, m_language);
            myApp.setGlobal_OperationONOFF(true);
            myApp.setGlobal_ExitFromApplication(false);
            myApp.setLastRunActivity(this);
            mSDK_INT = android.os.Build.VERSION.SDK_INT;
            setContentView(R.layout.maps);
            overridePendingTransition(R.anim.slide_in, R.anim.no_animate);
        } catch (Exception e) {
            Utils.showConfirmationDialog(this, "Maps \n" + e.toString(), getResources().getString(R.string.app_name));
        }
        initComponent();
    }

    public void initComponent() {
        try {
            getLocationPermission();
            b_back = findViewById(R.id.b_back);
            b_back.setTypeface(myFont);
            b_back.setOnClickListener(this);
            sp_branches = findViewById(R.id.sp_branches);

            ArrayList<String> listBranches = new ArrayList<>();
            listBranches = getArrayFromArrayMap(myApp.getGlobal_BranchesForMap(), m_language);
            if ((null != listBranches && listBranches.size() != 0)) {
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, listBranches) {
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View v = super.getView(position, convertView, parent);
                        ((TextView) v).setTypeface(myFont);
                        return v;
                    }

                    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                        View v = super.getDropDownView(position, convertView, parent);
                        ((TextView) v).setTypeface(myFont);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            v.setBackground(getResources().getDrawable(R.drawable.rounded_w));
                        }
                        return v;
                    }
                };

                dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                sp_branches.setAdapter(dataAdapter);
                sp_branches.setSelection(Integer.parseInt(myApp.getGlobal_Position()));
                sp_branches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        try {
                            double longitude = Double.parseDouble(myApp.getGlobal_BranchesForMap().get(position).get("longitude"));//Utils.mBranchGPS[position][0]);
                            double latitude = Double.parseDouble(myApp.getGlobal_BranchesForMap().get(position).get("latitude"));//Utils.mBranchGPS[position][1]);
                            LatLng latLng = new LatLng(latitude, longitude);
                            myApp.setGlobal_MapTitle((m_language.equalsIgnoreCase("ar") ? myApp.getGlobal_BranchesForMap().get(position).get("name_ar") : myApp.getGlobal_BranchesForMap().get(position).get("name_en")));
                            myApp.setGlobal_MapAddress((m_language.equalsIgnoreCase("ar") ? myApp.getGlobal_BranchesForMap().get(position).get("ADDRESSE_AR") : myApp.getGlobal_BranchesForMap().get(position).get("address_en")));
                            moveCamera(latLng, myApp.getGlobal_MapTitle(), myApp.getGlobal_MapAddress(), CAMERA_ZOOM);
                        } catch (Exception e) {
                            Log.e(Utils.APP_TAG, e.toString());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });
            } else {
                Utils.showToast(this, getResources().getString(R.string.no_services));
                //finish();
            }
        } catch (Exception e) {
            Utils.showConfirmationDialog(this,"Map initComponent \n"+ e.toString(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (!Utils.is_Null(myApp.getGlobal_MapLati()) &&
                !Utils.is_Null(myApp.getGlobal_MapLong()) &&
                !Utils.is_Null(myApp.getGlobal_MapTitle()) &&
                !Utils.is_Null(myApp.getGlobal_MapAddress())) {
            double latitude = Double.parseDouble(myApp.getGlobal_MapLati());
            double longitude = Double.parseDouble(myApp.getGlobal_MapLong());
            LatLng latLng = new LatLng(latitude, longitude);
            moveCamera(latLng, myApp.getGlobal_MapTitle(), myApp.getGlobal_MapAddress(), CAMERA_ZOOM);
        } else {
            LatLng latLng = new LatLng(13.11, 14.11);
            moveCamera(latLng, "Please Download Branches Info", "Rashad Al Abedi", CAMERA_ZOOM);
        }

    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        if (v.getId() == R.id.b_back) {
            overridePendingTransition(R.anim.no_animate, R.anim.slide_out);
            finish();
        }
    }

    public ArrayList<String> getArrayFromArrayMap(ArrayList<HashMap<String, String>> mArrayMap, String mLang) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < mArrayMap.size(); i++) {
            arrayList.add((mLang.equalsIgnoreCase("ar")) ? mArrayMap.get(i).get("name_ar") : mArrayMap.get(i).get("name_en"));
        }
        return arrayList;
    }
}
