package ra20_2014.taskmanager;


import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Statistika extends Activity {

    private TaskDatabase db = new TaskDatabase(Statistika.this);


    public float h_percent=0;
    public float m_percent=0;
    public float l_percent=0;
    private float h_counter=0;
    private float m_counter=0;
    private float l_counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView myView = new MyView(this);
        setContentView(myView);
        Task[] tasks = db.readTasks();
        int i;
        for (i=0;i<tasks.length;i++){
            if (tasks[i].getPriority() == 1){
                h_counter++;
            }else if (tasks[i].getPriority() == 2){
                m_counter++;
            }else
                l_counter++;
        }


        float broj_taskova = tasks.length;
        h_percent = (h_counter/broj_taskova)*100;
        m_percent = (m_counter/broj_taskova)*100;
        l_percent = (l_counter/broj_taskova)*100;



        //setContentView(R.layout.activity_statistika);
        //MyView myView = (MyView) findViewById(R.id.view);

        myView.neka_funkcija(h_percent,m_percent,l_percent);
    }
}