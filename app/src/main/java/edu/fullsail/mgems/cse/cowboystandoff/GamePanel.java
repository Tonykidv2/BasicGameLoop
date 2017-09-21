package edu.fullsail.mgems.cse.cowboystandoff;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by TheNinjaFS1 on 9/20/17.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread thread = null;
    Paint p = new Paint();
    public GamePanel(Context context)
    {
        super(context);

        getHolder().addCallback(this);

        if(thread == null)
            thread = new MainThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(thread == null)
            thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;

        while(true)
        {
            try {

                thread.setRunning(false);
                thread.join();
                retry = false;
            }
            catch (Exception e){e.printStackTrace();}
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return super.onTouchEvent(event);
    }

    public void update()
    {

    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        p.setColor(Color.BLACK);
        p.setTextSize(50);
        canvas.drawText(Double.toString(thread.averageFPS), getWidth()/2, getHeight()/ 2,p);
        canvas.drawText(Double.toString(thread.waitTime), getWidth()/2, getHeight()/ 4,p);
    }

}
