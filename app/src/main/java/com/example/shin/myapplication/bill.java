package com.example.shin.myapplication;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

/**
 * Created by shin on 2015-08-17.
 */
public class bill extends Activity {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));

        
    }

    class MyInterpolator implements TimeInterpolator{
        public float getInterpolation(float input){
            return 1-input;
        }
    }

    class MyView extends View {
        Ball mBall;

        public MyView(Context context){
            super(context);
            mBall = new Ball();
            mBall.setX(100);
            mBall.setY(50);
            mBall.setRad(20);
        }

        public void startAnim(TimeInterpolator inter)
        {

        }


        protected void onDraw(Canvas canvas)
        {
            Paint pnt = new Paint();
            pnt.setColor(mBall.getClr());
            pnt.setAntiAlias(true);
            canvas.drawCircle(mBall.getX(),mBall.getY(),mBall.getRad(),pnt);
        }

    }


}
