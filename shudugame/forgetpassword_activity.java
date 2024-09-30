package com.example.shudugame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class forgetpassword_activity extends AppCompatActivity implements View.OnClickListener {

    private String mphone;
    private String mverifycode;
    private EditText et_newpassword_first;
    private EditText et_newpassword_second;
    private EditText et_verifycode;
    private Button btn_getverifycode;
    private Button btn_confirm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginforget);

        et_newpassword_first = findViewById(R.id.et_input_newpassword);
        et_newpassword_second = findViewById(R.id.et_input_newpassword_again);
        et_verifycode = findViewById(R.id.et_verifycode);
        btn_getverifycode = findViewById(R.id.btn_get_verifycode);
        btn_confirm = findViewById(R.id.btn_confirm);

        mphone = getIntent().getStringExtra("phone");
        btn_getverifycode.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_get_verifycode) {
            mverifycode = String.format("%06d", new Random().nextInt(999999));
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("手机号:" + mphone + "\n验证码:" + mverifycode);
            builder.setPositiveButton("确定", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (view.getId() == R.id.btn_confirm) {
            if (et_newpassword_first.getText().toString().length() < 6) {
                et_newpassword_first.setText("");
                Toast.makeText(this, "密码长度不能小于6位", Toast.LENGTH_SHORT).show();
            } else if (!et_newpassword_first.getText().toString().equals(et_newpassword_second.getText().toString())) {
                Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                et_newpassword_first.setText("");
                et_newpassword_second.setText("");
            } else if (!et_verifycode.getText().toString().equals(mverifycode)) {
                Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
                et_verifycode.setText("");
            } else {
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("newpassword", et_newpassword_first.getText().toString());
                intent.putExtra("mphone", mphone);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        }

    }
}