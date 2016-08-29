package com.mobops.chadaga.coursereview.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobops.chadaga.coursereview.Objects.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sathwik on 12-08-2016.
 */
public class DatabaseMain extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "coursesManager";
    // events table name
    private static final String TABLE = "courses";
    // events Table Columns names
    private static final String KEY_NAME = "courseName";
    private static final String KEY_NO = "courseNumber";
    private static final String KEY_PROF = "courseProfessor";

    public DatabaseMain(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_NAME + " TEXT,"
                + KEY_NO + " TEXT,"
                + KEY_PROF + " TEXT" + ")";
        db.execSQL(CREATE_EVENTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);

        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, course.getCourseName());
        values.put(KEY_NO, course.getCourseNo());
        values.put(KEY_PROF, course.getCourseProfs());

        // Inserting Row
        db.insert(TABLE, null, values);
        db.close(); // Closing database connection
    }

    public List<Course> getCourses(String query) {
        List<Course> contactList = new ArrayList<Course>();
//        String selectQuery = "SELECT  * FROM " + TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        // looping through all rows and adding to list
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {
                    Course course = new Course();
                    course.setCourseName(cursor.getString(0));
                    course.setCourseNo(cursor.getString(1));
                    course.setCourseProfs(cursor.getString(2));
                    // Adding contact to list
                    contactList.add(course);
                } while (cursor.moveToNext());
            }
        cursor.close();
        // return contact list
        return contactList;
    }

    // Getting courses Count
    public long getCoursesCount(String query) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        long count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

}
