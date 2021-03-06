package com.example.szenzorok;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class Ball extends View {
    public float x;
    public float y;
    private final int r;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //konstruktorletrehozas
    public Ball(Context context,float x,float y, int r)
    {
        super(context);
        mPaint.setColor(0xFF00FF00);//zold
        this.x = x;
        this.y = y;
        this.r = r;//sugár mekkora legyen a kor


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x,y,r,mPaint);

    }
}
