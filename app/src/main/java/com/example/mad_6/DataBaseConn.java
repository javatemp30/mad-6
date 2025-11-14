package com.example.mad_6;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
public class DataBaseConn extends SQLiteOpenHelper {
    public DataBaseConn(Context context) {
        super(context, "Medicinedb", null, 2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MDTable(" +
                "MedicineName TEXT, " +
                "med_date TEXT, " +
                "med_time TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS MDTable");
        onCreate(db);
    }
    public boolean insertvalues(String medname, String meddate, String medtime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("MedicineName", medname);
        cv.put("med_date", meddate);
        cv.put("med_time", medtime);
        long res = db.insert("MDTable", null, cv);
        if(res==-1)
            return false;
        else
            return true;
    }
    public Cursor FetchData(String date, String time) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = new String[] { "MedicineName" };
        String sel = "med_date = ? AND med_time = ?";
        String[] args = new String[] { date.trim(), time.trim() };
        return db.query("MDTable", cols, sel, args, null, null, null);
    }
}

