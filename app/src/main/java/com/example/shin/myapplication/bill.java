package com.example.shin.myapplication;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by shin on 2015-08-17.
 */
public class bill extends Activity {

    MyView mView;
    SeekBar sbar;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.root);

        sbar = (SeekBar)findViewById(R.id.seekBar);
        sbar.setProgress(0);

        LinearLayout root = (LinearLayout)findViewById(R.id.root);
        mView = new MyView(this);
        root.addView(mView);

    }

    class MyInterpolator implements TimeInterpolator{
        public float getInterpolation(float input){
            return 1-input;
        }
    }


    class MyView extends View {
        Ball mBall;
        ArrayList<Vertex> arVertex= new ArrayList<Vertex>();
        int mWidth, mHeight;
        int fricAcc = 2;
        Handler mHandler;
        int vecPwr;

        public MyView(Context context){
            super(context);
            mBall = new Ball();
            mBall.setX(100);
            mBall.setY(50);
            mBall.setRad(50);
            mHandler = new Handler();
        }
        private Runnable r = new Runnable(){

            public void run(){
                invalidate();
            }
        };

        protected void onDraw(Canvas canvas)
        {
            Paint pnt = new Paint();
            pnt.setColor(mBall.getClr());
            pnt.setAntiAlias(true);

            mWidth = mView.getWidth();
            mHeight = mView.getHeight();
            vecPwr = (int)Math.sqrt(mBall.getVecX()*mBall.getVecX() + mBall.getVecY()*mBall.getVecY());


            // 공 좌표 이동 계산
            if(mBall.getVecX() < 0)
            {

                mBall.setX(mBall.getX() + mBall.getVecX() - fricAcc*mBall.getVecX()/vecPwr);
                mBall.setVecX(mBall.getVecX() - fricAcc*mBall.getVecX()/vecPwr);
            }else if(mBall.getVecX() > 0)
            {
                mBall.setX(mBall.getX() + mBall.getVecX() - fricAcc*mBall.getVecX()/vecPwr);
                mBall.setVecX(mBall.getVecX() - fricAcc*mBall.getVecX()/vecPwr);
            }

            if (mBall.getVecY() < 0) {
                mBall.setY(mBall.getY() + mBall.getVecY() - fricAcc*mBall.getVecY()/vecPwr);
                mBall.setVecY(mBall.getVecY() - fricAcc*mBall.getVecY()/vecPwr);
            }else if (mBall.getVecY() > 0) {
                mBall.setY(mBall.getY() + mBall.getVecY() - fricAcc*mBall.getVecY()/vecPwr);
                mBall.setVecY(mBall.getVecY() - fricAcc*mBall.getVecY()/vecPwr);
            }

            // 공 반사각 계산

            if(mBall.getX()+mBall.getRad() > mWidth || mBall.getX()-mBall.getRad() < 0)
            {
                mBall.vecX *= -1;
            }
            if(mBall.getY()+mBall.getRad() > mWidth || mBall.getY()-mBall.getRad() < 0)
            {
                mBall.vecY *= -1;
            }


            canvas.drawCircle(mBall.getX(),mBall.getY(),mBall.getRad(),pnt);
            mHandler.postDelayed(r,100);
        }

        public void startAnim(TimeInterpolator inter)
        {


        }


        public boolean onTouchEvent(MotionEvent ev)
        {
            if(ev.getAction()==MotionEvent.ACTION_DOWN)
            {

                if(mBall.getX()-50 <= ev.getX())
                {
                    if(mBall.getX()+50 >= ev.getX())
                    {
                        if(mBall.getY()-50 <= ev.getY())
                        {
                            if(mBall.getY()+50 >= ev.getY())
                            {
                                //공을 터치하고 움직인 경우
                                arVertex.add(new Vertex(ev.getX(), ev.getY(), false));
                            }
                        }
                    }
                    return true;
                }else
                {
                    arVertex.clear();
                }
            }
            if(ev.getAction()==MotionEvent.ACTION_MOVE)
            {
                arVertex.add(new Vertex(ev.getX(), ev.getY(), false));


                if(arVertex.size() >=2 &&mBall.getX()-50 <= arVertex.get(arVertex.size()-2).x)
                {
                    if(mBall.getX()+50 >= arVertex.get(arVertex.size()-2).x)
                    {
                        if(mBall.getY()-50 <= arVertex.get(arVertex.size()-2).y)
                        {
                            if(mBall.getY()+50 >= arVertex.get(arVertex.size()-2).y)
                            {
                                //공을 터치하고 움직인 경우
                                float tempX = arVertex.get(arVertex.size()-2).x - arVertex.get(arVertex.size()-1).x;
                                float tempY = arVertex.get(arVertex.size()-2).y - arVertex.get(arVertex.size()-1).y;

                                //vector값 입력
                                mBall.setVecX((int)tempX);
                                mBall.setVecY((int)tempY);

                                tempX = tempX * tempX;
                                tempY = tempY * tempY;

                                mBall.pwr = (int)Math.sqrt(tempX+tempY);

                                if(mBall.pwr <= 20)
                                {
                                    mBall.pwr = 20;
                                }else if(mBall.pwr <= 40)
                                {
                                    mBall.pwr = 40;
                                }else if(mBall.pwr <= 60)
                                {
                                    mBall.pwr = 60;
                                }else  if(mBall.pwr <= 80)
                                {
                                    mBall.pwr = 80;
                                }else if(mBall.pwr <=100)
                                {
                                    mBall.pwr = 100;
                                }
                                sbar.setProgress(mBall.pwr);
                                arVertex.clear();
                            }
                        }
                    }
                    return true;
                }else
                {
                    arVertex.clear();
                }
            }
            return false;
        }


        public class Vertex {
            Vertex(float ax, float ay, boolean ad)
            {
                x= ax;
                y= ay;
                Draw = ad;
            }
            float x;
            float y;
            boolean Draw;
        }

    }


}
