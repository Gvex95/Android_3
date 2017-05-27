package ra20_2014.taskmanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;


public class NotificationService extends Service {
    private final IBinder binder = new LocalBinder();
    private ArrayList<Task> tasks = null;
    private Calendar calendar;
    private long msec_now;
    private long msec_task;
    NotificationManager notificationManager = null;
    NotificationCompat.Builder builder = null;
	private int id = 0;

    public NotificationService() {
        final Handler mHandler = new Handler();
        final Runnable nit = new Runnable() {
            @Override
            public void run() {
               try{
                   calendar = Calendar.getInstance();
                   msec_now = calendar.getTimeInMillis();
                   if (tasks != null){
                       for (Task t : tasks){
                           if (t.isReminder()){
                                msec_task = t.getTime_in_msec();
                                if (msec_task - msec_now < 1000*60*1){
                                    notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    builder = new NotificationCompat.Builder(NotificationService.this)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle("Task Manager")
                                    .setContentText("15 min do isteka zadatka: " + t.getName())
                                    .setTicker("Baki, otvori notifikaciju!!!")
                                    .setWhen(System.currentTimeMillis());
                                    /* da klik na notifikaciju vrati nazad u main activity
                                    Intent i = new Intent(NotificationService.this,MainActivity.class);
                                    PendingIntent pi = PendingIntent.getActivity(NotificationService.this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
                                    builder.setContentIntent(pi);
                                    */
                                    //dodati zvuk za notifikaciju

                                    notificationManager.notify(id,builder.build());
									id++;
                                    t.setReminder(false);


                                }
                           }
                       }
                   }
               } finally {
                   mHandler.postDelayed(this,10000);
               }
            }
        };
        nit.run();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder{
        NotificationService getService(){
            return NotificationService.this;
        }
        void getTasks(ArrayList<Task> t){
            tasks = t;
        }
    }
}