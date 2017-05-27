package ra20_2014.taskmanager;

import java.io.Serializable;

/**
 * Created by mgsti on 4/23/2017.
 */

public class Task implements Serializable {
    public String name,date,desq;
    public int priority;
    public boolean reminder,check;
    private long time_in_msec;

    public Task(String name, String date, int priority, boolean reminder, long time_in_msec,String desq) {
        this.name = name;
        this.date = date;
        this.priority = priority;
        this.reminder = reminder;
        this.time_in_msec = time_in_msec;
        this.desq = desq;
    }
    public Task(){
        this.name="";
        this.priority=0;
        this.date="";
        this.reminder=false;
        this.time_in_msec = 0;
        this.desq = "";

    }

    public String getDesq() {
        return desq;
    }

    public void setDesq(String desq) {
        this.desq = desq;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public long getTime_in_msec() {
        return time_in_msec;
    }

    public void setTime_in_msec(long time_in_msec) {
        this.time_in_msec = time_in_msec;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }



    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }
}
