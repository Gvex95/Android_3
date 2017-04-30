package ra20_2014.taskmanager;


import android.app.Activity;
import android.os.Bundle;

public class Statistika extends Activity {

    public float h_percent=0;
    public float m_percent=69;
    public float l_percent=37;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView myView = new MyView(this);
        setContentView(myView);


        //setContentView(R.layout.activity_statistika);
        //MyView myView = (MyView) findViewById(R.id.view);

        myView.neka_funkcija(h_percent,m_percent,l_percent);
    }
}