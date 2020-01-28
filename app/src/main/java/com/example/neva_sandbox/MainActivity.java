package com.example.neva_sandbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Go to Test Activity
        // Ref. https://coding-factory.tistory.com/203
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }
}
