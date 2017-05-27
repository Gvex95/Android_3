package ra20_2014.taskmanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by Milan on 4/25/2017.
 */

public class MyView extends View {
    private Paint background = new Paint();
    private Paint h_priority = new Paint();
    private Paint m_priority = new Paint();
    private Paint l_priority = new Paint();
    private Paint text = new Paint();
    private Paint text_percente = new Paint();
    private RectF  h_priority_rectangle = new RectF();
    private RectF  m_priority_rectangle = new RectF();
    private RectF  l_priority_rectangle = new RectF();


    private int curr_h=0;
    public int curr_m=0;
    public int curr_l=0;

    public MyView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        background.setColor(Color.rgb(2, 214, 242));
        h_priority.setColor(Color.RED);
        m_priority.setColor(Color.YELLOW);
        l_priority.setColor(Color.GREEN);
        text.setColor(Color.BLACK);

        text.setTextSize(48);
        text_percente.setTextSize(90);

        h_priority_rectangle.set(30*width/100, 15*height/100, 70*width/100, 40*height/100);
        m_priority_rectangle.set(4*width/100, 50*height/100, 44*width/100, 75*height/100);
        l_priority_rectangle.set(54*width/100, 50*height/100, 94*width/100, 75*height/100);

        canvas.drawArc(h_priority_rectangle,0,360,true,background);
        canvas.drawArc(m_priority_rectangle,0,360,true,background);
        canvas.drawArc(l_priority_rectangle,0,360,true,background);

        int start_angle =270;




            canvas.drawArc(h_priority_rectangle, start_angle, curr_h*360/100, true, h_priority);
            canvas.drawText(String.valueOf(curr_h)+"%",45*width/100, 29*height/100  ,text_percente);
            canvas.drawText("High priority tasks",35*width/100,45*height/100,text);



            canvas.drawArc(m_priority_rectangle,start_angle,curr_m*360/100,true,m_priority);
             canvas.drawText("Medium priority tasks",9*width/100,80*height/100,text);
             canvas.drawText(String.valueOf(curr_m)+"%",19*width/100, 64*height/100  ,text_percente);


            canvas.drawArc(l_priority_rectangle,start_angle,curr_l*360/100,true,l_priority);
            canvas.drawText(String.valueOf(curr_l)+"%",69*width/100, 64*height/100  ,text_percente);
            canvas.drawText("Low priority tasks",59*width/100,80*height/100,text);

    }


    public void neka_funkcija(final float h_percent, final float m_percent, final float l_percent){
        new Thread(new Runnable() {

            @Override
            public void run() {
                while(curr_h<=h_percent || curr_m<= m_percent || curr_l<= l_percent){
                    postInvalidate();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(curr_h< h_percent)
                        curr_h++;
                    if(curr_m< m_percent)
                        curr_m++;
                    if(curr_l< l_percent)
                        curr_l++;
                }

            }
        }).start();
    }



}

