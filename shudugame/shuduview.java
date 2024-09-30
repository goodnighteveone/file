package com.example.shudugame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

public class shuduview extends View {

    //单元格的宽度和高度
    private  float width;
    private float height;
    private static int selectedX;
    private static int selectedY;

    private gamelogic game = new gamelogic();
    private Bitmap bitmap;
    private Paint paint;

    public shuduview(Context context) {
        super(context);
        init();
    }


    private void init() {
        // 加载图片资源
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 设置透明度等样式
        paint.setAlpha(150); // 100% 不透明
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh){
        //计算当前单元格的宽度和高度
        this.width = w / 9f;
        this.height = h / 9f;
        super.onSizeChanged(w,h,oldw,oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(bitmap, 0, 0, paint);


//        //生成用于绘制背景色的画笔
//        Paint backgroundPaint = new Paint();
//        //设置画笔的颜色
//        backgroundPaint.setColor(getResources().getColor(R.color.shudu_background,null));
//        //绘制背景色
//        canvas.drawRect(0,0,getWidth(),getHeight(),backgroundPaint);

        //深色画笔
        Paint drakPaint = new Paint();
        drakPaint.setColor(getResources().getColor(R.color.shudu_dark,null));

        Paint hilitePaint = new Paint();
        hilitePaint.setColor(getResources().getColor(R.color.shudu_hilite,null));

        //浅色画笔
        Paint lightPaint = new Paint();
        lightPaint.setColor(getResources().getColor(R.color.shudu_light,null));

        //画出九宫格的9根浅色线
        for(int i = 0;i < 9;i++) {
            canvas.drawLine(0,i * height,getWidth(),i * height,lightPaint);
            canvas.drawLine(0,i * height + 1,getWidth(),i * height + 1,lightPaint);
            canvas.drawLine(i * width,0,i * width,getHeight(),lightPaint);
            canvas.drawLine(i * width + 1,0,i * width + 1,getHeight(),lightPaint);
        }

        //画出九宫格的3跟深色线
        for(int i = 0;i < 9;i++){
            if(i%3!=0){
                continue;
            }
            canvas.drawLine(0,i * height,getWidth(),i * height,drakPaint);
            canvas.drawLine(0,i * height + 1,getWidth(),i * height + 1,drakPaint);
            canvas.drawLine(i * width,0,i * width,getHeight(),drakPaint);
            canvas.drawLine(i * width + 1,0,i * width + 1,getHeight(),drakPaint);
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
        for(int i = 0;i < 9;i++){
            for(int j = 0;j < 9;j++){
                //如果初始数独库数据不为空，则绘制
                if(!game.getTileString(i,j,gamelogic.sudoku).equals(""))
                    canvas.drawText(game.getTileString(i,j,game.sudoku),i * width + x,j * height + y,numberPaint);
            }
        }

        //绘制所选数字
        Paint selectPaint = new Paint();
        selectPaint.setColor(Color.BLUE);
        selectPaint.setStyle(Paint.Style.FILL);
        selectPaint.setTextSize(height * 0.75f);
        selectPaint.setTextAlign(Paint.Align.CENTER);

        //画出九宫格的所选数据
        for(int i = 0;i < 9;i++){
            for(int j = 0;j < 9;j++){
                //如果玩家所选数独库数据不为空，则绘制
                if(!game.getTileString(i,j,game.sudoku1).equals(""))
                    canvas.drawText(game.getTileString(i,j,game.sudoku1),i * width + x,j * height + y,selectPaint);
            }
        }

        super.onDraw(canvas);


    }

    //点击事件
    @Override
    public  boolean onTouchEvent(MotionEvent event){
        //如果点击不是按下则重置方法
        if(event.getAction()!=MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(event);
        }


        //获取点击格子的X和Y值
        selectedX = (int) (event.getX() / width);
        selectedY = (int) (event.getY() / height);

        //如果点击的不是初始数独的数据
        if(game.sudoku[selectedY * 9 + selectedX] == 0){
            //获取点击格子不可用数据
            int used[] = game.getUsedTiles(selectedX,selectedY);

            StringBuffer stringBuffer = new StringBuffer();
            //输出不可用数据
            for(int i = 0;i < used.length;i++){
                stringBuffer.append(used[i]);
            }

            numberbuttondialog keyDialog = new numberbuttondialog(getContext(),used,this);
            keyDialog.show();
        }
        return true;
    }

    //设置用户所选的数字，用户按下数字键盘时调用
    public void setSelectedTile(int tile) {
        if(game.setTileIfValid(selectedX,selectedY,tile)){
            //重绘整个画布
            invalidate();
            //判断游戏是否通关，如果通关则显示通关的dialog
            if(game.ifPassGame()){
                passgame passGameDialog = new passgame(getContext());
                passGameDialog.show();
            }
        }
    }

    //删除用户所选数字
    public void setDeleteTile() {

        game.deleteTile(selectedX,selectedY);
        //重绘整个画布
        invalidate();
    }


}
