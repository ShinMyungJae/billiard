package com.example.shin.myapplication;

import android.content.Context;
import android.graphics.Color;

/**
 * Created by shin on 2015-08-17.
 */
public class Ball {

    int x;
    int y;
    int rad;
    int clr = Color.BLACK;
    int pwr;



    public void Ball()
    {
        x=0;
        y=0;
        rad=20;
    }

    void setX(int ax){x=ax;}
    void setY(int ay){y=ay;}
    void setRad(int arad){rad=arad;}
    void setClr(int aclr){clr=aclr;}
    void setPwr(int apwr){pwr=apwr;}
    int getX(){return x;}
    int getY(){return y;}
    int getClr(){return clr;}
    int getRad(){return rad;}
    int getPwr(){return pwr;}
}
