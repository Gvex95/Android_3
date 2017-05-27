package ra20_2014.taskmanager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import ra20_2014.taskmanager.NotificationService.LocalBinder;
public class MainActivity extends Activity {

    private Button new_task,statistic;
    private Intent intent1, intent2;
    private ListView list;
    private static TaskAdapter adapter;
    private  boolean mBound;
    NotificationService mService;
    private TaskDatabase db;
    private String TAG = "Main";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find all buttons
        Log.d(TAG,"1");
        new_task=(Button)findViewById(R.id.button1);
        statistic=(Button)findViewById(R.id.button2);
        Log.d(TAG,"2");
        intent1 = new Intent(MainActivity.this,Dodavanje.class);
        intent2 = new Intent(MainActivity.this,Statistika.class);
        db = new TaskDatabase(MainActivity.this);

        list = (ListView) findViewById(R.id.lista);
        adapter = new TaskAdapter(this);
        Log.d(TAG,"3");

        new_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1.putExtra("id_taska",-1);
                startActivity(intent1);
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
                intent1.putExtra("id_taska",position);
                startActivity(intent1);
                return true;
            }
        });
        list.setAdapter(adapter);

    }



    @Override

    protected void onResume() {
        super.onResume();
        Log.d(TAG, "9");
        Task[] tasks = db.readTasks();
        Log.d(TAG, "10");
        adapter.update(tasks);

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {

            }
            if(resultCode == Activity.RESULT_OK) {

            }
        }else if(requestCode == 2) {

            if (resultCode == Activity.RESULT_OK) {
                onResume();
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }

    }



    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalBinder binder = (LocalBinder) service;
            mService = binder.getService();
            binder.getTasks(adapter.getTasks());
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    //prebaciti ovo posle na onCreate,trebalo bi da radi tamo, ako sad imam bazu
    @Override
    protected void onStart() {
        super.onStart();
        //bind to service
        Intent i = new Intent(this,NotificationService.class);
        bindService(i,mConnection,BIND_AUTO_CREATE);
        //Toast.makeText(getApplication().getBaseContext(), "Service is bounded", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //unbind from service
        unbindService(mConnection);
        //Toast.makeText(getApplication().getBaseContext(), "Service is unbounded", Toast.LENGTH_SHORT).show();
        mBound = false;
    }
}
