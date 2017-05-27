package ra20_2014.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by isi24 on 25-May-17.
 */

public class TaskDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "taskovi.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tabela";

    private static final String COLUMN_TASK_NAME = "ime_zadatka";
    private static final String COLUMN_TASK_DATE = "datum_zadatka";
    private static final String COLUMN_TASK_PRIORITY = "prioritet_zadatka";
    private static final String COLUMN_TASK_MSEC = "vreme_setovanja_zadatka";
    private static final String COLUMN_TASK_DESQ = "opis_zadatka";
    private static final String COLUMN_TASK_REMINDER = "podsetnik_zadatka";
    private static final String COLUMN_TASK_CHECKED = "zavrsen_zadatak";
    private static final String COLUMN_TASK_ID = "id_zadatka";
    public TaskDatabase(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //pravimo nasu malu bazu
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_TASK_NAME + " TEXT, " +
                COLUMN_TASK_DATE + " TEXT, " +
                COLUMN_TASK_PRIORITY + " INTEGER, " +
                COLUMN_TASK_DESQ + " TEXT, " +
                COLUMN_TASK_MSEC + " LONG, " +
                COLUMN_TASK_REMINDER + " INTEGER, " +
                COLUMN_TASK_CHECKED + " INTEGER, " +
                COLUMN_TASK_ID + " INTEGER PRIMARY KEY );" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(db);
    }
    public void addTask(Task t){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASK_NAME, t.getName());
        cv.put(COLUMN_TASK_DATE, t.getDate());
        cv.put(COLUMN_TASK_DESQ, t.getDesq());
        cv.put(COLUMN_TASK_PRIORITY,t.getPriority());

        if (t.isReminder()) {
            cv.put(COLUMN_TASK_REMINDER, 1);
        }else
            cv.put(COLUMN_TASK_REMINDER, 0);


        if (t.isCheck()) {
            cv.put(COLUMN_TASK_CHECKED, 1);
        }else
            cv.put(COLUMN_TASK_CHECKED, 0);


        cv.put(COLUMN_TASK_MSEC, t.getTime_in_msec());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME,null,cv);
        db.close();
    }
    public void updateTask(Task t, int id_taska){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASK_NAME, t.getName());
        cv.put(COLUMN_TASK_DATE, t.getDate());
        cv.put(COLUMN_TASK_DESQ, t.getDesq());
        cv.put(COLUMN_TASK_PRIORITY,t.getPriority());
        if (t.isReminder()) {
            cv.put(COLUMN_TASK_REMINDER, 1);
        }else
            cv.put(COLUMN_TASK_REMINDER, 0);

        if (t.isCheck()) {
            cv.put(COLUMN_TASK_CHECKED, 1);
        }else
            cv.put(COLUMN_TASK_CHECKED, 0);


        cv.put(COLUMN_TASK_MSEC, t.getTime_in_msec());

        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_NAME,cv,COLUMN_TASK_ID + "=?", new String[] {String.valueOf(id_taska+1)});
        db.close();
    }

    public void removeTask(int koga_brisem){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,COLUMN_TASK_ID + "=?", new String[] {String.valueOf(koga_brisem+1)});
        db.close();
    }

    public Task readTask(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,COLUMN_TASK_ID + "=?",
                new String[] {String.valueOf(id+1)},null,null,null);
        cursor.moveToFirst();
        Task task = createTask(cursor);
        close();
        return task;

    }
    public Task[] readTasks(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        if (cursor.getCount() <= 0){
            return null;
        }
        Task[] tasks = new Task[cursor.getCount()];
        int i = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            tasks[i++] = createTask(cursor);
        }
        close();
        return tasks;

    }

    private Task createTask(Cursor cursor){
        String name = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME));
        String date = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DATE));
        String desq = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESQ));
        int priority = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_PRIORITY));
        long msec = cursor.getLong(cursor.getColumnIndex(COLUMN_TASK_MSEC));
        int reminder_i = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_REMINDER));
        boolean reminder;
        if (reminder_i == 1)
            reminder=true;
        else
            reminder = false;


        int checked_i = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_CHECKED));
        boolean checked;
        if (checked_i == 1)
            checked=true;
        else
            checked = false;
        return new Task(name,date,priority,reminder,msec,desq,checked);

    }
}
