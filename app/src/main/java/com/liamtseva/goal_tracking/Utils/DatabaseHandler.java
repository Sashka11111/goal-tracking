package com.liamtseva.goal_tracking.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.liamtseva.goal_tracking.Model.GoalModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "GoalTrackerDatabase";
    private static final String GOALS = "Goals";
    private static final String ID = "id";
    private static final String GOAL = "goal";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + GOALS + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GOAL + " TEXT, "
            + STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + GOALS);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertGoal(GoalModel goal){
        ContentValues cv = new ContentValues();
        cv.put(GOAL, goal.getGoal());
        cv.put(STATUS, 0);
        db.insert(GOALS, null, cv);
    }

    @SuppressLint("Range")
    public List<GoalModel> getAllGoals(){
        List<GoalModel> goalList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(GOALS, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        GoalModel goal = new GoalModel();
                        goal.setId(cur.getInt(cur.getColumnIndex(ID)));
                        goal.setGoal(cur.getString(cur.getColumnIndex(GOAL)));
                        goal.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        goalList.add(goal);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return goalList;
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(GOALS, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateGoal(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(GOAL, task);
        db.update(GOALS, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteGoal(int id){
        db.delete(GOALS, ID + "= ?", new String[] {String.valueOf(id)});
    }
}
