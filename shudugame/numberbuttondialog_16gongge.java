package com.example.shudugame;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class numberbuttondialog_16gongge extends Dialog {


    //用来存放代表对话框当中9个按钮的对象
    private final View keys[] = new View[16];
    private Button button;
    //已经不能使用的数字
    private final int used[];

    private shuduview_16gongge sdv;

    /**
     * 构造函数
     * @param context 上下文对象
     * @param used 已经不能使用的数字
     */
    public numberbuttondialog_16gongge(Context context, int[] used, shuduview_16gongge sdv) {
        super(context);
        this.used = used;
        this.sdv = sdv;
    }



    //当一个Dialog第一次显示的时候会调用onCreate方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置对话框标题
        setTitle("请选择数字");
        //用于为Diaglog设置布局文件
        setContentView(R.layout.keypad_16gongge);
        findViews();
        //为对话框所有按钮设置监听器
        setListeners();
        //把不能使用的数字按钮设为不可见
        for(int i = 0;i < used.length;i++){
            if(used[i] != 0)
                if(used[i] >= 17)
                {
                    keys[used[i] - 8].setVisibility(View.INVISIBLE);
                }
                else
                    keys[used[i] - 1].setVisibility(View.INVISIBLE);
        }
    }


    //遍历给按钮设置监听器
    private void setListeners() {
        for(int i = 0;i < keys.length;i++){
            final int t = i+1;
            keys[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    returnResult(t);
                }
            });
        }
        //删除按钮的监听器
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用shuduview的setDeleteTile方法
                sdv.setDeleteTile();
                dismiss();
            }
        });
    }

    //通知shuduView对象刷新九宫格数据
    private void returnResult(int tile) {

        sdv.setSelectedTile(tile);
        //取消对话框的显示
        dismiss();
    }

    //获取全部的按钮控件
    private void findViews() {
        keys[0] = findViewById(R.id.keypad_1);
        keys[1] = findViewById(R.id.keypad_2);
        keys[2] = findViewById(R.id.keypad_3);
        keys[3] = findViewById(R.id.keypad_4);
        keys[4] = findViewById(R.id.keypad_5);
        keys[5] = findViewById(R.id.keypad_6);
        keys[6] = findViewById(R.id.keypad_7);
        keys[7] = findViewById(R.id.keypad_8);
        keys[8] = findViewById(R.id.keypad_9);
        keys[9] = findViewById(R.id.keypad_A);
        keys[10] = findViewById(R.id.keypad_B);
        keys[11] = findViewById(R.id.keypad_C);
        keys[12] = findViewById(R.id.keypad_D);
        keys[13] = findViewById(R.id.keypad_E);
        keys[14] = findViewById(R.id.keypad_F);
        keys[15] = findViewById(R.id.keypad_G);

        button = findViewById(R.id.delete);
    }
}
