package com.example.shin.myapplication;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
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

        public MyView(Context context){
            super(context);
            mBall = new Ball();
            mBall.setX(100);
            mBall.setY(50);
            mBall.setRad(20);
        }

        public boolean onTouchEvent(MotionEvent ev)
        {
            if(ev.getAction()==MotionEvent.ACTION_DOWN)
            {

                if(mBall.getX()-20 <= ev.getX())
                {
                    if(mBall.getX()+20 >= ev.getX())
                    {
                        if(mBall.getY()-20 <= ev.getY())
                        {
                            if(mBall.getY()+20 >= ev.getY())
                            {
                                //공을 터치하고 움직인 경우
                                arVertex.add(new Vertex(ev.getX(),ev.getY(),false));
                                Toast tst = Toast.makeText(bill.this,"Ball Touch",Toast.LENGTH_LONG);
                                tst.show();
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


                if(arVertex.size() >=2 &&mBall.getX()-20 <= arVertex.get(arVertex.size()-2).x)
                {
                    if(mBall.getX()+20 >= arVertex.get(arVertex.size()-2).x)
                    {
                        if(mBall.getY()-20 <= arVertex.get(arVertex.size()-2).y)
                        {
                            if(mBall.getY()+20 >= arVertex.get(arVertex.size()-2).y)
                            {
                                //공을 터치하고 움직인 경우

                                float tempX = arVertex.get(arVertex.size()-2).x - arVertex.get(arVertex.size()-1).x;
                                float tempY = arVertex.get(arVertex.size()-2).y - arVertex.get(arVertex.size()-1).y;

                                tempX = tempX * tempX;
                                tempY = tempY * tempY;

                                mBall.pwr = (int)Math.sqrt(tempX+tempY);
                                sbar.setProgress(mBall.pwr);


                                arVertex.clear();
                                Toast toast = Toast.makeText(bill.this,Integer.toString(mBall.pwr),Toast.LENGTH_LONG);
                                toast.show();

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
