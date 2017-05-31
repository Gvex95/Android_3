package ra20_2014.taskmanager;

import android.content.Context;
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

/**
 * Created by mgsti on 4/23/2017.
 */

public  class TaskAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Task> mTasks;
    private TaskDatabase db;


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
        final Task task = (Task) getItem(position);
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
        holder.date.setText(task.getData());
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
