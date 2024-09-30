package com.example.shudugame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ViewUtils;

import java.util.Random;

import database.Logindatabase;
import database.loginmassage;

public class Mainactivity_login extends AppCompatActivity implements View.OnFocusChangeListener {

    private TextView tv_password;
    private EditText et_password;
    private Button btn_forget_password;
    private CheckBox ck_remember_password;
    private EditText et_phone;
    private RadioButton rb_password;
    private RadioButton rb_verifycode;
    private Button btn_login;
    String verifycode;
    private Logindatabase mhelper = Logindatabase.getInstance(this);
    ActivityResultLauncher<Intent> register;
    private Button btn_register;
    String newpassword;
    String mphone;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // EdgeToEdge.enable(this);
        tv_password = findViewById(R.id.tv_password);
        btn_forget_password = findViewById(R.id.btn_forget_password);
        ck_remember_password = findViewById(R.id.ck_remember_password);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        rb_password = findViewById(R.id.rb_password);
        RadioGroup rg = findViewById(R.id.rg_login);
        rb_verifycode = findViewById(R.id.rb_verifycode);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        et_phone.addTextChangedListener(new MyTextWatcher(et_phone, 11));
        et_password.addTextChangedListener(new MyTextWatcher(et_password, 6));
        et_password.setOnFocusChangeListener(this);
        rg.setOnCheckedChangeListener(this::onCheckedChanged);
        btn_forget_password.setOnClickListener(this::onClick);
        btn_login.setOnClickListener(this::onClick);
        btn_register.setOnClickListener(this::onClick);
        mhelper.openwritabledatabase();
        mhelper.openreadabledatabase();
        reload();

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                Intent intent = o.getData();
                if (intent != null && o.getResultCode() == RESULT_OK) {
                    newpassword=intent.getStringExtra("newpassword");
                    mphone=intent.getStringExtra("mphone");
                    loginmassage temp=mhelper.querybyphone(mphone);
                    mhelper.update(temp,newpassword);
                }
            }
        });


    }

    private void reload() {
        loginmassage info = mhelper.querytop();
        if (info != null && info.remember) {
            et_phone.setText(info.phone);
            et_password.setText(info.password);
            ck_remember_password.setChecked(info.remember);
        }

    }

    public void onClick(View v) {
        String phone = et_phone.getText().toString();
        if (phone.length() < 11) {
            Toast.makeText(this, "手机号长度必须为11位", Toast.LENGTH_SHORT).show();
            return;
        }
        if (v.getId() == R.id.btn_forget_password) {
            if (rb_password.isChecked()) {
                Intent intent = new Intent(this, forgetpassword_activity.class);
                intent.putExtra("phone", phone);
                register.launch(intent);
            } else if (rb_verifycode.isChecked()) {
                verifycode = String.format("%06d", new Random().nextInt(999999));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("验证码");
                builder.setMessage(verifycode);
                builder.setPositiveButton("确定", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        } else if (v.getId() == R.id.btn_login) {
            if (rb_password.isChecked()) {
                loginmassage info = mhelper.querybyphone(phone);
                if (info == null) {
                    Toast.makeText(this, "该用户不存在", Toast.LENGTH_SHORT).show();
                } else if (!et_password.getText().toString().equals(info.password)) {
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                } else {
                    loginsuccess();
                }

            } else if (rb_verifycode.isChecked()) {
                if (!et_password.getText().toString().equals(verifycode)) {
                    Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    loginsuccess();
                }
            }
        } else if (v.getId() == R.id.btn_register) {
            loginmassage info = new loginmassage();
            info.phone = et_phone.getText().toString();
            info.password = et_password.getText().toString();
            info.remember = ck_remember_password.isChecked();
            mhelper.save(info);
        }
    }

    private void loginsuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
        builder.setMessage("是否进入游戏？");
        builder.setPositiveButton("确定", (dialog, which) -> {
            Intent intent = new Intent(this, game_grade.class);
            startActivity(intent);
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        //把数据保存到数据库
        loginmassage info = new loginmassage();
        info.phone = et_phone.getText().toString();
        info.password = et_password.getText().toString();
        info.remember = ck_remember_password.isChecked();
        mhelper.save(info);
    }


    protected void onstart() {
        super.onStart();

    }

    protected void onstop() {
        super.onStop();
        mhelper.closeDatabase();
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_password) {
            tv_password.setText(R.string.login_password);
            et_password.setHint(R.string.input_password);
            btn_forget_password.setText(R.string.forget_password);
            ck_remember_password.setVisibility(View.VISIBLE);
        } else if (checkedId == R.id.rb_verifycode) {
            tv_password.setText(R.string.verifycode);
            et_password.setHint(R.string.input_verifycode);
            btn_forget_password.setText(R.string.get_verifycode);
            ck_remember_password.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (view.getId() == R.id.et_password && b) {
            loginmassage info = mhelper.querybyphone(et_phone.getText().toString());
            if (info != null) {
                et_password.setText(info.password);
                ck_remember_password.setChecked(info.remember);
            } else {
                et_password.setText("");
                ck_remember_password.setChecked(false);
            }
        }
    }


    // Implement TextWatcher methods here
    private class MyTextWatcher implements TextWatcher {
        private EditText et;
        private int maxLength;

        public MyTextWatcher(EditText et, int maxLength) {
            this.et = et;
            this.maxLength = maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() == maxLength) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
            }

        }
    }


}



