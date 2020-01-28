package com.example.neva_sandbox;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class TestActivity  extends Activity {
    private WifiManager wifiManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // DEFINE Objects & Variables
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        final Button buttonOnoff = findViewById(R.id.onoffBtn);

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
}