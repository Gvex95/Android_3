package ra20_2014.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

    private Button new_task,statistic;
    private Intent intent1, intent2;
    private ListView list;
    private static TaskAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find all buttons
        new_task=(Button)findViewById(R.id.button1);
        statistic=(Button)findViewById(R.id.button2);
        intent1 = new Intent(MainActivity.this,Dodavanje.class);
        intent2 = new Intent(MainActivity.this,Statistika.class);

        list = (ListView) findViewById(R.id.lista);
        adapter = new TaskAdapter(this);


        new_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1.putExtra("Levi", getText(R.string.dodaj));
                intent1.putExtra("Desni",getText(R.string.otkazi));
                startActivityForResult(intent1,1);
            }
        });
        statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                intent1.putExtra("Levi",getText(R.string.sacuvaj));
                intent1.putExtra("Desni",getText(R.string.obrisi));
                startActivityForResult(intent1,2);
                return true;
            }
        });
        list.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                Task t = (Task) data.getSerializableExtra("dodajTask");
                adapter.addTask(t);
            }
        }else if(requestCode == 2) {

            if (resultCode == Activity.RESULT_OK) {

            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }

    }


}
