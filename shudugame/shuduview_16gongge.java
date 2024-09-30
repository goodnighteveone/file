package com.example.shudugame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.result.contract.ActivityResultContracts;

public class shuduview_16gongge extends View {


    //单元格的宽度和高度
    private float width;
    private float height;
    private static int selectedX;
    private static int selectedY;
    private int grade;
    private sudugame_16gongge_algorithm game;
    private Bitmap bitmap;
    private Paint paint;
    private Bitmap bitmap2;

    public shuduview_16gongge(Context context, int grade) {
        super(context);
        this.grade = grade;
        game = new sudugame_16gongge_algorithm(grade);
        init();
    }


    private void init() {

        // 加载图片资源
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 设置透明度等样式
        paint.setAlpha(150); // 255时 100% 不透明

        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.img_2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //计算当前单元格的宽度和高度
        this.width = w / 16f;
        this.height = h / 18f;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(bitmap, 0, 0, paint);

        //深色画笔
        Paint bulePaint = new Paint();
        bulePaint.setColor(getResources().getColor(R.color.bule, null));

        Paint hilitePaint = new Paint();
        hilitePaint.setColor(getResources().getColor(R.color.shudu_hilite, null));

        //浅色画笔
        Paint lightPaint = new Paint();
        lightPaint.setColor(getResources().getColor(R.color.red, null));


        //画出十六宫格的16根浅色线
        for (int i = 0; i < 16; i++) {
            if (i % 4 == 0) {
                continue;
            }
            canvas.drawLine(0, i * height, getWidth(), i * height, lightPaint);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, lightPaint);
            canvas.drawLine(i * width, 0, i * width, getHeight() - height * 2, lightPaint);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight() - height * 2, lightPaint);
        }

        //画出十六宫格的4跟深色线
        for (int i = 0; i < 16; i++) {
            if (i % 4 != 0) {
                continue;
            }
            canvas.drawLine(0, i * height, getWidth(), i * height, bulePaint);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, bulePaint);
            canvas.drawLine(i * width, 0, i * width, getHeight() - height * 2, bulePaint);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight() - height * 2, bulePaint);
        }


        //绘制初始数字
        Paint numberPaint = new Paint();
        numberPaint.setColor(Color.BLACK);
        numberPaint.setStyle(Paint.Style.FILL);
        numberPaint.setTextSize(height * 0.75f);
        numberPaint.setTextAlign(Paint.Align.CENTER);

        /*以下几行设置数字居中*/
        //创建fontMetrics
        Paint.FontMetrics fontMetrics = numberPaint.getFontMetrics();
        //x轴偏移量
        float x = width / 2;
        //y轴偏移量
        float y = height / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2;
        //画出九宫格的初始化数据
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                //如果初始数独库数据不为空，则绘制
                if (!game.getTileString(i, j, sudugame_16gongge_algorithm.sudoku).equals(""))
                    canvas.drawText(game.getTileString(i, j, game.sudoku), i * width + x, j * height + y, numberPaint);
            }
        }

        Paint btnpaint = new Paint();
        btnpaint.setColor(Color.BLACK);
        btnpaint.setStyle(Paint.Style.FILL);
        btnpaint.setTextSize(height * 0.5f);
        btnpaint.setTextAlign(Paint.Align.CENTER);


        //生成用于绘制答案按钮背景的画笔
        Paint backgroundPaint = new Paint();
        //backgroundPaint.setStyle(Paint.Style.STROKE); // 设置为边框样式
        backgroundPaint.setColor(Color.BLACK); // 设置边框颜色
        backgroundPaint.setStrokeWidth(5); // 设置边框宽度


        BitmapShader shader = new BitmapShader(bitmap2, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        backgroundPaint.setShader(shader);
        //设置画笔的颜色
        backgroundPaint.setColor(getResources().getColor(R.color.bule, null));
        //绘制答案按钮的背景
        canvas.drawRect(0, getHeight() - height, width * 4, getHeight(), backgroundPaint);
        canvas.drawText("答案", 2 * width, getHeight() - height + y, btnpaint);
        //绘制返回主界面按钮的背景
        canvas.drawRect(0, getHeight() - height * 2, width * 8, getHeight() - height, backgroundPaint);
        canvas.drawText("返回主界面", 4 * width, getHeight() - height * 2 + y, btnpaint);
        //绘制重新游戏按钮的背景
        canvas.drawRect(4 * width, getHeight() - height, width * 8, getHeight(), backgroundPaint);
        canvas.drawText("重新游戏", 6 * width, getHeight() - height + y, btnpaint);
        //绘制继续游戏按钮的背景
        canvas.drawRect(8 * width, getHeight() - height, width * 12, getHeight(), backgroundPaint);
        canvas.drawText("继续游戏", 10 * width, getHeight() - height + y, btnpaint);
        //绘制一键通关按钮的背景
        canvas.drawRect(12 * width, getHeight() - height, width * 16, getHeight(), backgroundPaint);
        canvas.drawText("一键通关", 14 * width, getHeight() - height + y, btnpaint);


        //绘制所选数字
        Paint selectPaint = new Paint();
        selectPaint.setColor(Color.BLUE);
        selectPaint.setStyle(Paint.Style.FILL);
        selectPaint.setTextSize(height * 0.75f);
        selectPaint.setTextAlign(Paint.Align.CENTER);

        //画出九宫格的所选数据
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                //如果玩家所选数独库数据不为空，则绘制
                if (!game.getTileString(i, j, game.sudoku1).equals(""))
                    canvas.drawText(game.getTileString(i, j, game.sudoku1), i * width + x, j * height + y, selectPaint);
            }
        }

        super.onDraw(canvas);


    }

    //点击事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //如果点击不是按下则重置方法
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(event);
        }

        //获取点击格子的X和Y值
        selectedX = (int) (event.getX() / width);
        selectedY = (int) (event.getY() / height);
        if (event.getY() >= getHeight() - height && event.getX() <= width * 4) {
            game.getanswer();
            invalidate();
        } else if (event.getY() >= getHeight() - height && event.getX() > width * 4 && event.getX() <= width * 8) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("是否重新游戏?");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    game = new sudugame_16gongge_algorithm(grade);
                    invalidate();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            Dialog dialog = builder.create();
            dialog.show();


        } else if (event.getY() >= getHeight() - height && event.getX() > width * 8 && event.getX() <= width * 12) {
            game.continuegame();
            invalidate();
        } else if (event.getY() >= getHeight() - height && event.getX() > width * 12 && event.getX() <= width * 16) {
            passgame passGameDialog = new passgame(getContext());
            passGameDialog.show();
        } else if (event.getY() >= getHeight() - height * 2 && event.getY() < getHeight() - height && event.getX() >0 && event.getX() <= width * 8) {
            Intent intent=new Intent(getContext(),game_grade.class);
            getContext().startActivity(intent);

//            startActivity(intent);
        }
        //如果点击的不是初始数独的数据
        else if (game.sudoku[selectedY * 16 + selectedX] == 0) {
            //获取点击格子不可用数据
            int used[] = game.getUsedTiles(selectedX, selectedY);

            StringBuffer stringBuffer = new StringBuffer();
            //输出不可用数据
            for (int i = 0; i < used.length; i++) {
                stringBuffer.append(used[i]);
            }

            numberbuttondialog_16gongge keyDialog = new numberbuttondialog_16gongge(getContext(), used, this);
            keyDialog.show();
        }
        return true;
    }

    //设置用户所选的数字，用户按下数字键盘时调用
    public void setSelectedTile(int tile) {
        if (game.setTileIfValid(selectedX, selectedY, tile)) {
            //重绘整个画布
            invalidate();
            //判断游戏是否通关，如果通关则显示通关的dialog
            if (game.ifPassGame()) {
                passgame passGameDialog = new passgame(getContext());
                passGameDialog.show();
            }
        }
    }

    //删除用户所选数字
    public void setDeleteTile() {

        game.deleteTile(selectedX, selectedY);
        //重绘整个画布
        invalidate();
    }


}
