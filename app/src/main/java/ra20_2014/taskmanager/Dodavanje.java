package ra20_2014.taskmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import java.util.Calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Dodavanje extends Activity {

    private  Button choose_date,choose_time,red,yellow,green,add,cancel;
    private  EditText name,desq;
    private  TextView entered_date,entered_time;
    private  Calendar myCalendar,convertCal;
    private  int currentDay,currentMonth,currentYear,currentHour,currentMin;
    private  int myYear,myDay,myMonth,myHour,myMinute;
    private  int priority,dayInWeek,id_zadatka;
    private  Task task;
    private CheckBox reminder;
    private String taskName,left,right,date;
    private boolean remind;
    private Intent main_intent;
    private long currentDateInMilliSec,taskDateInMilliSec;
    private TaskDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodavanje);
        Log.d("OnCreate","Dodavanje");

        //find all views
        name=(EditText)findViewById(R.id.imezadatka);
        desq=(EditText)findViewById(R.id.opiszadatka);
        entered_date=(TextView)findViewById(R.id.izabran_datum);
        entered_time=(TextView)findViewById(R.id.izabrano_vreme);
        choose_date=(Button)findViewById(R.id.izaberi_datum);
        choose_time=(Button)findViewById(R.id.izaberi_vreme);
        red=(Button)findViewById(R.id.button1);
        yellow=(Button)findViewById(R.id.button2);
        green=(Button)findViewById(R.id.button3);
        add=(Button)findViewById(R.id.button4);
        cancel=(Button)findViewById(R.id.button5);
        reminder=(CheckBox)findViewById(R.id.checkbox);


        main_intent= new Intent(Dodavanje.this,MainActivity.class);
        convertCal = Calendar.getInstance();
        myCalendar =Calendar.getInstance();
        task = new Task();
        priority=-1;

        db = new TaskDatabase(Dodavanje.this);


        //id_zadatka = -1;
        id_zadatka = getIntent().getIntExtra("id_taska",-1);
        if (id_zadatka != -1){
            add.setText("Sacuvaj");
            cancel.setText("Obrisi");

        }

        Toast.makeText(getApplication().getBaseContext(), "Citam task sa id: " + String.valueOf(id_zadatka), Toast.LENGTH_SHORT).show();


        if (id_zadatka!=-1) {
            Task task_database = db.readTask(id_zadatka);

                name.setText(task_database.getName());
                entered_date.setText(task_database.getDate());
                desq.setText(task_database.getDesq());
                //entered time kako uraditi, nzm sad

                reminder.setChecked(task_database.isReminder());
                if (task_database.getPriority() == 1) {
                    priority = 1;
                    red.setEnabled(false);
                    yellow.setEnabled(true);
                    green.setEnabled(true);

                    red.setBackgroundColor(getResources().getColor(R.color.red));
                    yellow.setBackgroundColor(getResources().getColor(R.color.greey));
                    green.setBackgroundColor(getResources().getColor(R.color.greey));
                } else if (task_database.getPriority() == 2) {
                    priority = 2;
                    red.setEnabled(true);
                    yellow.setEnabled(false);
                    green.setEnabled(true);

                    yellow.setBackgroundColor(getResources().getColor(R.color.yellow));
                    red.setBackgroundColor(getResources().getColor(R.color.greey));
                    green.setBackgroundColor(getResources().getColor(R.color.greey));
                } else if (task_database.getPriority() == 3) {
                    priority = 3;
                    red.setEnabled(true);
                    yellow.setEnabled(true);
                    green.setEnabled(false);

                    green.setBackgroundColor(getResources().getColor(R.color.green));
                    red.setBackgroundColor(getResources().getColor(R.color.greey));
                    yellow.setBackgroundColor(getResources().getColor(R.color.greey));
                }


        }





        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = 1;

                red.setEnabled(false);
                yellow.setEnabled(true);
                green.setEnabled(true);

                red.setBackgroundColor(getResources().getColor(R.color.red));
                yellow.setBackgroundColor(getResources().getColor(R.color.greey));
                green.setBackgroundColor(getResources().getColor(R.color.greey));

            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority=2;

                red.setEnabled(true);
                yellow.setEnabled(false);
                green.setEnabled(true);

                yellow.setBackgroundColor(getResources().getColor(R.color.yellow));
                red.setBackgroundColor(getResources().getColor(R.color.greey));
                green.setBackgroundColor(getResources().getColor(R.color.greey));


            }
        });

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority=3;
                red.setEnabled(true);
                yellow.setEnabled(true);
                green.setEnabled(false);

                green.setBackgroundColor(getResources().getColor(R.color.green));
                red.setBackgroundColor(getResources().getColor(R.color.greey));
                yellow.setBackgroundColor(getResources().getColor(R.color.greey));


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (id_zadatka != -1){
                    //znaci da nam je dugme obrisi i da treba da obrisemo taj zadatak iz baze
                    Toast.makeText(getApplication().getBaseContext(), "Brisem task sa id: " + String.valueOf(id_zadatka), Toast.LENGTH_LONG).show();

                    db.removeTask(id_zadatka);
                    //Intent i = new Intent(Dodavanje.this, MainActivity.class);
                    //startActivity(i);

                    //ako uradim ovo, onda obrise iz baze, zasto u usta ga jebem?
                        setResult(2);
                        finish();

                }
                else
                {
                    //Intent i = new Intent(Dodavanje.this, MainActivity.class);
                    //startActivity(i);

                    setResult(2);
                    finish();
                }
            }
        });




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_zadatka == -1) {
                    if (name.getText().toString().equals("")) {
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.unesite_ime), Toast.LENGTH_SHORT);
                        toast.show();
                    } else if (entered_date.getText().toString().equals("")) {
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.unesite_datum), Toast.LENGTH_SHORT);
                        toast.show();
                    } else if (entered_time.getText().toString().equals("")) {
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.unesite_vreme), Toast.LENGTH_SHORT);
                        toast.show();
                    } else if (priority == -1) {
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.unesite_prioritet), Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        long oneDayinMillis = 1000 * 60 * 60 * 24;

                        currentDateInMilliSec = myCalendar.getTimeInMillis();


                        convertCal.set(myYear, myMonth - 1, myDay, myHour, myMinute, 0);
                        dayInWeek = convertCal.get(Calendar.DAY_OF_WEEK);
                        taskDateInMilliSec = convertCal.getTimeInMillis();


                        if ((taskDateInMilliSec / oneDayinMillis) - (currentDateInMilliSec / oneDayinMillis) == 0)
                            date = getResources().getString(R.string.danas);
                        else if ((taskDateInMilliSec / oneDayinMillis) - (currentDateInMilliSec / oneDayinMillis) == 1)
                            date = getResources().getString(R.string.sutra);
                        else if ((taskDateInMilliSec / oneDayinMillis) - (currentDateInMilliSec / oneDayinMillis) == 2)
                            date = getResources().getString(R.string.prekosutra);
                        else if ((taskDateInMilliSec / oneDayinMillis) - (currentDateInMilliSec / oneDayinMillis) >= 2 && (taskDateInMilliSec / oneDayinMillis) - (currentDateInMilliSec / oneDayinMillis) <= 7) {
                            switch (dayInWeek) {
                                case 1:
                                    date = getResources().getString(R.string.nedelja);
                                    break;
                                case 2:
                                    date = getResources().getString(R.string.ponedeljak);
                                    break;
                                case 3:
                                    date = getResources().getString(R.string.utorak);
                                    break;
                                case 4:
                                    date = getResources().getString(R.string.sreda);
                                    break;
                                case 5:
                                    date = getResources().getString(R.string.cetvrtak);
                                    break;
                                case 6:
                                    date = getResources().getString(R.string.petak);
                                    break;
                                case 7:
                                    date = getResources().getString(R.string.subota);
                                    break;
                            }
                        } else {
                            date = entered_date.getText().toString();
                        }

                        if (taskDateInMilliSec - currentDateInMilliSec <= 1000 * 60 * 1 && reminder.isChecked()) {
                            reminder.setChecked(false);
                            Toast.makeText(getApplication().getBaseContext(), "Ne moze reminder,istice za manje od 15 minuta, promenite vreme", Toast.LENGTH_LONG).show();
                        } else {
                            taskName = name.getText().toString();
                            remind = reminder.isChecked();
                            task.setDate(date);
                            task.setName(taskName);
                            task.setPriority(priority);
                            task.setReminder(remind);
                            task.setDesq(desq.getText().toString());
                            task.setTime_in_msec(taskDateInMilliSec);

                            db.addTask(task);

                            Intent returnIntent = new Intent(Dodavanje.this, MainActivity.class);
                            //returnIntent.putExtra("dodajTask", task);
                            //setResult(Dodavanje.RESULT_OK, returnIntent);
                            startActivity(returnIntent);
                            //finish();
                        }
                    }
                }else {
                    //sad treba to samo da sacuvam, izmene

                    if (name.getText().toString().equals("")) {
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.unesite_ime), Toast.LENGTH_SHORT);
                        toast.show();
                    } else if (entered_date.getText().toString().equals("")) {
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.unesite_datum), Toast.LENGTH_SHORT);
                        toast.show();
                    } else if (entered_time.getText().toString().equals("")) {
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.unesite_vreme), Toast.LENGTH_SHORT);
                        toast.show();
                    } else if (priority == -1) {
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.unesite_prioritet), Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        long oneDayinMillis = 1000 * 60 * 60 * 24;

                        currentDateInMilliSec = myCalendar.getTimeInMillis();


                        convertCal.set(myYear, myMonth - 1, myDay, myHour, myMinute, 0);
                        dayInWeek = convertCal.get(Calendar.DAY_OF_WEEK);
                        taskDateInMilliSec = convertCal.getTimeInMillis();


                        if ((taskDateInMilliSec / oneDayinMillis) - (currentDateInMilliSec / oneDayinMillis) == 0)
                            date = getResources().getString(R.string.danas);
                        else if ((taskDateInMilliSec / oneDayinMillis) - (currentDateInMilliSec / oneDayinMillis) == 1)
                            date = getResources().getString(R.string.sutra);
                        else if ((taskDateInMilliSec / oneDayinMillis) - (currentDateInMilliSec / oneDayinMillis) == 2)
                            date = getResources().getString(R.string.prekosutra);
                        else if ((taskDateInMilliSec / oneDayinMillis) - (currentDateInMilliSec / oneDayinMillis) >= 2 && (taskDateInMilliSec / oneDayinMillis) - (currentDateInMilliSec / oneDayinMillis) <= 7) {
                            switch (dayInWeek) {
                                case 1:
                                    date = getResources().getString(R.string.nedelja);
                                    break;
                                case 2:
                                    date = getResources().getString(R.string.ponedeljak);
                                    break;
                                case 3:
                                    date = getResources().getString(R.string.utorak);
                                    break;
                                case 4:
                                    date = getResources().getString(R.string.sreda);
                                    break;
                                case 5:
                                    date = getResources().getString(R.string.cetvrtak);
                                    break;
                                case 6:
                                    date = getResources().getString(R.string.petak);
                                    break;
                                case 7:
                                    date = getResources().getString(R.string.subota);
                                    break;
                            }
                        } else {
                            date = entered_date.getText().toString();
                        }

                        Task t1 = db.readTask(id_zadatka);


                        if (t1.getTime_in_msec() - currentDateInMilliSec <= 1000 * 60 * 1 && reminder.isChecked()) {
                            reminder.setChecked(false);
                            Toast.makeText(getApplication().getBaseContext(), "Ne moze reminder,istice za manje od 15 minuta, promenite vreme", Toast.LENGTH_LONG).show();
                        } else {


                            Task t = db.readTask(id_zadatka);
                            taskName = name.getText().toString();
                            remind = reminder.isChecked();
                            t.setDate(date);
                            t.setName(taskName);
                            t.setDesq(desq.getText().toString());
                            t.setPriority(priority);
                            t.setReminder(remind);
                            t.setTime_in_msec(taskDateInMilliSec);

                            db.updateTask(t, id_zadatka);

                            Intent returnIntent = new Intent(Dodavanje.this, MainActivity.class);
                            //returnIntent.putExtra("dodajTask", task);
                            //setResult(Dodavanje.RESULT_OK, returnIntent);
                            startActivity(returnIntent);
                            //finish();

                        }
                    }
                }
            }
        });



        choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDay = myCalendar.get(Calendar.DAY_OF_MONTH);
                currentMonth = myCalendar.get(Calendar.MONTH);
                currentYear = myCalendar.get(Calendar.YEAR);
                currentHour = myCalendar.get(Calendar.HOUR_OF_DAY);
                currentMin = myCalendar.get(Calendar.MINUTE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Dodavanje.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        myDay = dayOfMonth;
                        myMonth = month + 1;
                        myYear = year;
                        entered_date.setText(myDay + "/" + myMonth +"/" + myYear);


                    }
                },currentYear,currentMonth,currentDay);
                datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });


        choose_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog timePickerDialog = new TimePickerDialog(Dodavanje.this,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        myHour = hourOfDay;
                        myMinute = minute;
                        entered_time.setText(myHour + ":" + myMinute);

                    }
                },currentHour,currentMin,true);

                timePickerDialog.show();
            }

        });

    }
}
