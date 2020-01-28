package com.example.neva_sandbox;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class TestActivity  extends Activity {
    private WifiManager wifiManager;
    private ListView wifiList;
    WifiReceiver receiverWifi;

    private final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // DEFINE Objects & Variables
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        final Button buttonOnoff = findViewById(R.id.onoffBtn);
        Button buttonScan = findViewById(R.id.scanBtn);
        wifiList = findViewById(R.id.wifiLst);

        // INITIAL ACTIONS
        // Wifi On/Off Check
        if (!wifiManager.isWifiEnabled()) {
            wifiAlert();
        }
        // Set Wifi On/Off Button       + Add to Always Listening
        if (!wifiManager.isWifiEnabled()) {
            buttonOnoff.setText("WIFI ON");
        }
        else {
            buttonOnoff.setText("WIFI OFF");
        }

        // OBJECT FUNCTIONS
        // Wifi On/Off Button
        buttonOnoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wifiManager.isWifiEnabled()) {
                    buttonOnoff.setText("WIFI ON");
                    wifiManager.setWifiEnabled(true);
                }
                else {
                    buttonOnoff.setText("WIFI OFF");
                    wifiManager.setWifiEnabled(false);
                }
            }
        });
        // Scan Wifi Button
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(TestActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(TestActivity.this, new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    }, MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
                }

                wifiManager.startScan();

                StringBuilder sb;
                ListView wifiDeviceList = wifiList;

                sb = new StringBuilder();
                List<ScanResult> wifiList = wifiManager.getScanResults();
                ArrayList<String> deviceList = new ArrayList<>();
                for (ScanResult scanResult : wifiList) {
                    sb.append("\n").append(scanResult.SSID).append(" - ").append(scanResult.capabilities);
                    deviceList.add(scanResult.SSID + " - " + scanResult.capabilities);
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(TestActivity.this, android.R.layout.simple_list_item_1/*listview_btn_item*/, deviceList.toArray());
                wifiDeviceList.setAdapter(arrayAdapter);
            }
        });


    }
    // OTHER FUNCTIONS
    public void wifiAlert(){
        // Ref. https://ccdev.tistory.com/12
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wifi가 꺼져있습니다.")       // Set Title
                .setMessage("Wifi를 키시겠습니까?")    // Set Message
                .setCancelable(false)                  // Back is Cancel
                .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    // If Click Check
                    public void onClick(DialogInterface dialog, int whichButton){
                        wifiManager.setWifiEnabled(true);
                    }
                })

                .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                    // If Click Cancel
                    public void onClick(DialogInterface dialog, int whichButton){
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();      // Create Dialog Object
        dialog.show();                              // Alert Dialog
    }

//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//        receiverWifi = new WifiReceiver(wifiManager, wifiList);
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
//        registerReceiver(receiverWifi, intentFilter);
//    }
//    private void getWifi() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Toast.makeText(TestActivity.this, "version> = marshmallow", Toast.LENGTH_SHORT).show();
//            if (ContextCompat.checkSelfPermission(TestActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(TestActivity.this, "location turned off", Toast.LENGTH_SHORT).show();
//                ActivityCompat.requestPermissions(TestActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                        1);
//            } else {
//                Toast.makeText(TestActivity.this, "location turned on", Toast.LENGTH_SHORT).show();
//                wifiManager.startScan();
//            }
//        } else {
//            Toast.makeText(TestActivity.this, "scanning", Toast.LENGTH_SHORT).show();
//            wifiManager.startScan();
//        }
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(receiverWifi);
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(TestActivity.this, "permission granted", Toast.LENGTH_SHORT).show();
//                    wifiManager.startScan();
//                } else {
//                    Toast.makeText(TestActivity.this, "permission not granted", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                break;
//        }
//    }
}