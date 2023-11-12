package com.r1klem.chat4weeek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class besedaactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beseda);

        ImageButton chatb = findViewById(R.id.chatb);
        ImageButton besedb = findViewById(R.id.besedb);
        ImageButton menub = findViewById(R.id.menub);

        chatb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(besedaactivity.this, MainActivity.class));
            }
        });
        menub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(besedaactivity.this, menuActivity.class));
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

                            | View.SYSTEM_UI_FLAG_FULLSCREEN

                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}