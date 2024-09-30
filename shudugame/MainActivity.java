package com.example.shudugame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    int grade;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initializeGlobalVariables();
        setContentView(new shuduview_16gongge(this,grade));

        Intent intent = new Intent(this, backgroundmusic_Service.class);
        startService(intent);
    }

    protected void onstop()
    {
        super.onStop();
        Intent intent = new Intent(this, backgroundmusic_Service.class);
        stopService(intent);
    }
    private void initializeGlobalVariables() {
        // 初始化全局变量
        grade=getIntent().getIntExtra("grade",2);
    }
}

