package ra20_2014.taskmanager;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by mgsti on 4/23/2017.
 */

public class Task implements Serializable {
    public String name,desq,data;
    public int priority,godina,mesec,dan,sat,minut;
    public boolean reminder,check;

    public Task(String name, int priority, boolean reminder, long time_in_msec,String desq,boolean check,int godina,int mesec,int dan,int sat,int minut,String data) {
        this.name = name;
        this.godina = godina;
        this.mesec = mesec;
        this.dan = dan;
        this.sat = sat;
        this.minut = minut;
        this.priority = priority;
        this.reminder = reminder;
        this.desq = desq;
        this.check = check;
        this.data = data;
    }
    public Task(){
        this.name="";
        this.priority=0;
        this.godina =0;
        this.mesec =0;
        this.dan =0;
        this.sat =0;
        this.minut =0;
        this.reminder=false;
        this.desq = "";
        this.check = false;
        this.data = "";

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTimeInMsec(){
        Calendar c = Calendar.getInstance();
        c.set(godina,mesec,dan,sat,minut);
        long msec = c.getTimeInMillis();
        return msec;
    }

    public int getGodina() {
        return godina;
    }

    public void setGodina(int godina) {
        this.godina = godina;
    }

    public int getMesec() {
        return mesec;
    }

    public void setMesec(int mesec) {
        this.mesec = mesec;
    }

    public int getDan() {
        return dan;
    }

    public void setDan(int dan) {
        this.dan = dan;
    }

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }

    public int getMinut() {
        return minut;
    }

    public void setMinut(int minut) {
        this.minut = minut;
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
