package com.example.shudugame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class interface_activity extends AppCompatActivity {
    private Button btn_login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface);

        btn_login = findViewById(R.id.btn_login2);
        btn_login.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_login2) {
            Intent intent = new Intent(interface_activity.this, Mainactivity_login.class);
            startActivity(intent);
        }
    }

}