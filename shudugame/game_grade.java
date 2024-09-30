package com.example.shudugame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import database.loginmassage;

public class game_grade extends AppCompatActivity {

    private Button btn_primary;
    private Button btn_middle;
    private Button btn_height;
    private Button btn_ultimate;
    ActivityResultLauncher<Intent> register;
    int grade;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_grade);

        btn_primary=(Button)findViewById(R.id.primary);
        btn_middle=(Button)findViewById(R.id.middle);
        btn_height=(Button)findViewById(R.id.height);
        btn_ultimate=(Button)findViewById(R.id.ultimate);

        btn_primary.setOnClickListener(this::onClick);
        btn_middle.setOnClickListener(this::onClick);
        btn_height.setOnClickListener(this::onClick);
        btn_ultimate.setOnClickListener(this::onClick);

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {

            }
        });
    }


    public void onClick(View v){
        if(v.getId()==R.id.primary)
        {
            grade=1;
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("grade", grade);
            register.launch(intent);
        }
        else if(v.getId()==R.id.middle)
        {
            grade=2;
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("grade", grade);
            register.launch(intent);
        }
        else if(v.getId()==R.id.height)
        {
            grade=3;
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("grade", grade);
            register.launch(intent);
        }
        else if(v.getId()==R.id.ultimate)
        {
            grade=4;
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("grade", grade);
            register.launch(intent);
        }
    }
}
