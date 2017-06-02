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
    private NativeClass nativna_klasa;
    private float crveni_precrtani = 0;
    private float zuti_precrtani = 0;
    private float zeleni_precrtani = 0;

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
        nativna_klasa = new NativeClass();

        if (tasks!=null) {
            for (i = 0; i < tasks.length; i++) {
                    if (tasks[i].getPriority() == 1) {
                        h_counter++;
                    } else if (tasks[i].getPriority() == 2) {
                        m_counter++;
                    } else {
                        l_counter++;
                    }

                if (tasks[i].isCheck() && tasks[i].getPriority() == 1){
                    crveni_precrtani++;
                }if (tasks[i].isCheck() && tasks[i].getPriority() == 2){
                    zuti_precrtani++;
                }if (tasks[i].isCheck() && tasks[i].getPriority() == 3){
                    zeleni_precrtani++;
                }

            }

        }
        Log.d("Statistika","8");


        Log.d("Statistika","9");
        Log.d("TAG->BROJ_TASKOVA",String.valueOf(crveni_precrtani));
        Log.d("TAG->BROJ_TASKOVA",String.valueOf(zuti_precrtani));
        Log.d("TAG->BROJ_TASKOVA",String.valueOf(zeleni_precrtani));
        Log.d("TAG->BROJ_TASKOVA",String.valueOf(h_counter));
        Log.d("TAG->BROJ_TASKOVA",String.valueOf(m_counter));
        Log.d("TAG->BROJ_TASKOVA",String.valueOf(l_counter));


        //h_percent = (crveni_precrtani/h_counter)*100;
        //m_percent = (zuti_precrtani/m_counter)*100;
        //l_percent = (zeleni_precrtani/l_counter)*100;

        h_percent = nativna_klasa.calculatePercent(crveni_precrtani,h_counter);
        m_percent = nativna_klasa.calculatePercent(zuti_precrtani,m_counter);
        l_percent = nativna_klasa.calculatePercent(zeleni_precrtani,l_counter);


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