package com.example.yeell.testcustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * 学习用的自定义view
 * Created by yeell on 2017/5/24.
 */

public class CustomView extends View {


    public interface OnTouchBarListener {
        void currentPostion(int position);

        void currentStr(String str);

        void show();

        void hide();
    }

    public OnTouchBarListener mOnTouchBarListener;

    public void setOnTouchBarListener(OnTouchBarListener onTouchBarListener) {
        this.mOnTouchBarListener = onTouchBarListener;
    }

    private Context mContext;
    private int mAllHeight = 0;
    private int mAllWidth = 0;
    private int width = 0;
    private Paint paint;
    private float y = 0;
    private String[] letterStrings = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "S", "Y", "Z"};

    public CustomView(Context context) {
        super(context);
        init(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        Point size = new Point();
        ((WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        int height = size.y;
        mAllHeight = height - getStatusBarHeight();
        mAllWidth = size.x;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setTextSize(30);//3CAC48
        paint.setColor(Color.parseColor("#ffffff"));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, mContext.getApplicationContext().getResources().getDisplayMetrics());
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            //warp_content
            mAllHeight = height;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            //match_parent  和 定死高度
            mAllHeight = height;
        }
        setMeasuredDimension(width, mAllHeight);

        y = mAllHeight / letterStrings.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        float x = getX();
        for (int i = 0; i < letterStrings.length; i++) {
            canvas.drawText(letterStrings[i], (getWidth() / 2) - 5, 60 + y * i, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (mOnTouchBarListener == null) {
            return super.onTouchEvent(event);
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getRawX();
                float my = event.getY();
                if (getX() <= x && x <= getX() + getWidth()) {
                    int positon = (int) ((my + 60) / y) - 1;
                    if (positon > 26) {
                        positon = 26;
                    }
                    if (positon < 0) {
                        positon = 0;
                    }
                    String currentPostion = letterStrings[positon];
                    Log.e("currentPostion", currentPostion);
                    mOnTouchBarListener.currentPostion(positon);
                    mOnTouchBarListener.currentStr(currentPostion);
                    mOnTouchBarListener.show();
                }
                break;
            case MotionEvent.ACTION_UP:
                mOnTouchBarListener.hide();
                break;
        }
        return true;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
