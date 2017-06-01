package ra20_2014.taskmanager;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mgsti on 4/23/2017.
 */

public  class TaskAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Task> mTasks;
    private TaskDatabase db;
    private String date;
    private int dayInWeek;


    public TaskAdapter(Context context){
        mContext=context;
        mTasks=new ArrayList<Task>();
    }
    public void addTask(Task t){
        mTasks.add(t);
        notifyDataSetChanged();
    }
    public ArrayList<Task> getTasks (){
        return mTasks;
    }

    @Override
    public int getCount() {
        return mTasks.size();
    }

    public void update(Task[] tasks){
        mTasks.clear();
        if(tasks != null){
            for(Task t: tasks){
                mTasks.add(t);
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public Object getItem(int position) {
        Object rv = null;
        try {
            rv = mTasks.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return rv;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

            View view = convertView;
            if (view == null){
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.one_row,null);
                ViewHolder holder = new ViewHolder ();

                holder.name = (TextView) view.findViewById(R.id.ime_zadatka);
                holder.priority =  view.findViewById(R.id.prioritet);
                holder.date = (TextView) view.findViewById(R.id.datum);
                holder.check = (CheckBox) view.findViewById(R.id.zavrsen_zadatak);
                holder.reminder = (RadioButton) view.findViewById(R.id.podsetnik);
            view.setTag(holder);

        }
        db = new TaskDatabase(mContext);
        final Task task = db.readTask(position);
        final ViewHolder holder = (ViewHolder) view.getTag();
        //prioritet
        holder.name.setText(task.name);
        switch (task.priority){
            case 1:
                holder.priority.setBackgroundColor(Color.RED);
                break;
            case 2:
                holder.priority.setBackgroundColor(Color.YELLOW);
                break;
            case 3:
                holder.priority.setBackgroundColor(Color.GREEN);
                break;
        }
        //checkbox
        holder.check.setChecked(task.check);
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public  void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    task.setCheck(true);
                    holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    db = new TaskDatabase(mContext);
                    db.getWritableDatabase();
                    db.updateTask(task,position);
                    //holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                }else{
                    task.setCheck(false);
                    holder.name.setPaintFlags(0);
                    db = new TaskDatabase(mContext);
                    db.getWritableDatabase();
                    db.updateTask(task,position);
                    //holder.name.setPaintFlags(0);
                }
            }
        });
        if (task.isCheck()){
            holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else
        {
            holder.name.setPaintFlags(0);
        }

        long oneDayinMillis = 1000 * 60 * 60 * 24;
        long taskDateInMilliSec = task.getTimeInMsec();
        Calendar c = Calendar.getInstance();
        long currentDateInMilliSec = c.getTimeInMillis();

        Calendar convertCalendar = Calendar.getInstance();
        convertCalendar.set(task.getGodina(),task.getMesec(),task.getDan(),task.getSat(),task.getMinut());
        dayInWeek = convertCalendar.get(Calendar.DAY_OF_WEEK);


        if ((taskDateInMilliSec / oneDayinMillis) - (currentDateInMilliSec / oneDayinMillis) == 0)
            date = mContext.getResources().getString(R.string.danas);
        else if ((taskDateInMilliSec / oneDayinMillis) - (currentDateInMilliSec / oneDayinMillis) == 1)
            date = mContext.getResources().getString(R.string.sutra);
        else if ((taskDateInMilliSec / oneDayinMillis) - (currentDateInMilliSec / oneDayinMillis) == 2)
            date = mContext.getResources().getString(R.string.prekosutra);
        else if ((taskDateInMilliSec / oneDayinMillis) - (currentDateInMilliSec / oneDayinMillis) >= 2 && (taskDateInMilliSec / oneDayinMillis) - (currentDateInMilliSec / oneDayinMillis) <= 7) {
            switch (dayInWeek) {
                case 1:
                    date = mContext.getResources().getString(R.string.nedelja);
                    break;
                case 2:
                    date = mContext.getResources().getString(R.string.ponedeljak);
                    break;
                case 3:
                    date = mContext.getResources().getString(R.string.utorak);
                    break;
                case 4:
                    date = mContext.getResources().getString(R.string.sreda);
                    break;
                case 5:
                    date = mContext.getResources().getString(R.string.cetvrtak);
                    break;
                case 6:
                    date = mContext.getResources().getString(R.string.petak);
                    break;
                case 7:
                    date = mContext.getResources().getString(R.string.subota);
                    break;
            }
        } else {
            date = Integer.toString(task.getDan()) + "/" + Integer.toString(task.getMesec()) + "/" + Integer.toString(task.getGodina());
        }


        holder.date.setText(date);
        holder.reminder.setChecked(task.reminder);

        return  view;
    }
    private class ViewHolder {

        public TextView name = null;
        public View priority = null;
        public TextView date = null;
        public CheckBox check = null;
        public RadioButton reminder = null;


    }
}
