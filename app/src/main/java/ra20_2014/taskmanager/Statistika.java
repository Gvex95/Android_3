package ra20_2014.taskmanager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Statistika extends Activity {

    public float h_percent=0;
    public float m_percent=0;
    public float l_percent=0;
    private float h_counter=0;
    private float m_counter=0;
    private float l_counter=0;
    private Button back;
    private RelativeLayout relativeLayout;
    private TaskDatabase db = new TaskDatabase(Statistika.this);
    private float broj_taskova = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MyView myView = new MyView(this);
        //setContentView(myView);
        Log.d("Statistika","1");
        setContentView(R.layout.activity_statistika);
        Log.d("Statistika","2");
        back = (Button)findViewById(R.id.back_button);
        Log.d("Statistika","3");
        relativeLayout = (RelativeLayout)findViewById(R.id.r_layout);
        Log.d("Statistika","4");
        MyView myView = new MyView(this);
        Log.d("Statistika","5");
        relativeLayout.addView(myView);

        Log.d("Statistika","6");
        Task[] tasks = db.readTasks();
        int i;
        Log.d("Statistika","7");
        if (tasks!=null) {
            for (i = 0; i < tasks.length; i++) {
                if (tasks[i].getPriority() == 1) {
                    h_counter++;
                } else if (tasks[i].getPriority() == 2) {
                    m_counter++;
                } else
                    l_counter++;
            }
           broj_taskova = tasks.length;
        }
        Log.d("Statistika","8");


        Log.d("Statistika","9");
        h_percent = (h_counter/broj_taskova)*100;
        m_percent = (m_counter/broj_taskova)*100;
        l_percent = (l_counter/broj_taskova)*100;

        Log.d("Statistika","10");

        //setContentView(R.layout.activity_statistika);
        Log.d("Statistika","11");
        myView.iscrtaj(h_percent,m_percent,l_percent);
        Log.d("Statistika","12");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Statistika.this,MainActivity.class);
                startActivity(i);
                Log.d("Statistika","13");
            }
        });
    }
}