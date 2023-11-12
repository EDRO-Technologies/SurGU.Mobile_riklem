package com.r1klem.chat4weeek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(splash.this);
        if (pref.getBoolean("is_start", false) != true) {
            Intent intent = new Intent(splash.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(splash.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}