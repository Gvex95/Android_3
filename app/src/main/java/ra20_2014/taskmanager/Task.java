package ra20_2014.taskmanager;

import java.io.Serializable;

/**
 * Created by mgsti on 4/23/2017.
 */

public class Task implements Serializable {
    public String name,date;
    public int priority;
    public boolean check,reminder;

    public Task(String name, String date, int priority, boolean check, boolean reminder) {
        this.name = name;
        this.date = date;
        this.priority = priority;
        this.check = check;
        this.reminder = reminder;
    }
    public Task(){
        this.name="";
        this.priority=0;
        this.date="";
        this.check=false;
        this.reminder=false;

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

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }
}
