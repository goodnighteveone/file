package com.example.shudugame;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class passgame extends Dialog {

    Button button;

    public passgame(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("提示");
        //设置布局
        setContentView(R.layout.pass);

        //点击确定按钮，对话框消失
        button = findViewById(R.id.btn_passGame);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
