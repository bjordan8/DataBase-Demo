package edu.ncc.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DepartmentInfoSource {

    private SQLiteDatabase database;
    private DepartmentInfoHelper deptHelper;

    private String[] allColumns = { DepartmentInfoHelper._ID, DepartmentInfoHelper.NAME,
            DepartmentInfoHelper.LOCATION, DepartmentInfoHelper.PHONE, };

    public DepartmentInfoSource(Context context) {
        deptHelper = new DepartmentInfoHelper(context);
    }

    public void open() throws SQLException {
        database = deptHelper.getWritableDatabase();
    }

    public void close() {
        deptHelper.close();
    }

    public DepartmentEntry addDept(String name, String phone, String location) {
        ContentValues values = new ContentValues();
        values.put(DepartmentInfoHelper.NAME, name);
        values.put(DepartmentInfoHelper.LOCATION, location);
        values.put(DepartmentInfoHelper.PHONE, phone);
        long insertId = database.insert(DepartmentInfoHelper.TABLE_NAME, null, values);
        Cursor cursor = database.query(DepartmentInfoHelper.TABLE_NAME, allColumns, DepartmentInfoHelper._ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        DepartmentEntry entry = cursorToEntry(cursor);
        cursor.close();
        return entry;
    }

    public void deleteDept(DepartmentEntry dept) {
        long id = dept.getId();
        database.delete(DepartmentInfoHelper.TABLE_NAME, DepartmentInfoHelper._ID
                + " = " + id, null);
    }

    public List<DepartmentEntry> getAllDepartments() {
        List<DepartmentEntry> dpts = new ArrayList<>();
        DepartmentEntry entry;
        Cursor cursor = database.query(DepartmentInfoHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            entry = cursorToEntry(cursor);
            dpts.add(entry);
            cursor.moveToNext();
        }
        cursor.close();
        return dpts;
    }

    private DepartmentEntry cursorToEntry(Cursor cursor) {
        DepartmentEntry entry = new DepartmentEntry();
        entry.setId(cursor.getLong(0));
        entry.setName(cursor.getString(1));
        entry.setLocation(cursor.getString(2));
        entry.setPhone(cursor.getString(3));
        return entry;
    }

    public List<DepartmentEntry> query(int choice) {
        List<DepartmentEntry> dpts = new ArrayList<>();
        DepartmentEntry entry;
        Cursor cursor;
        if (choice == 1) {
            cursor = database.query(DepartmentInfoHelper.TABLE_NAME,
                    allColumns, DepartmentInfoHelper.NAME + " LIKE ? ", new String[]{"%Dean%"},
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                entry = cursorToEntry(cursor);
                dpts.add(entry);
                cursor.moveToNext();
            }
            cursor.close();
        } else if (choice == 2) {
            cursor = database.query(DepartmentInfoHelper.TABLE_NAME,
                    allColumns, DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? OR " +
                            DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? OR " +
                            DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? OR " +
                            DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? OR " +
                            DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? OR " +
                            DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? ",
                    new String[] { "%Tower Plaza%", "%Tower 2%", "%Tower 3%", "%Tower 4%", "%Tower 5%", "%Tower 6%", "%Tower 7%", "%Tower 8%",
                   "%Tower 9%", "%Tower 10%", "%Tower 11%", "%Tower 12%"},
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                entry = cursorToEntry(cursor);
                dpts.add(entry);
                cursor.moveToNext();
            }
            cursor.close();
        }  else if (choice == 3){
            cursor = database.query(DepartmentInfoHelper.TABLE_NAME,
                    allColumns, DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? OR " +
                            DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? OR " +
                            DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? OR " +
                            DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? ",
                    new String[] { "%Building A%", "%Building B%", "%Building C%","%Building D%","%Cluster A%","%Cluster B%", "%Cluster C%", "%C Cluster%",
                    "%Cluster %"},
                    null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                entry = cursorToEntry(cursor);
                dpts.add(entry);
                cursor.moveToNext();
            }
            cursor.close();
        } else if (choice == 4) {
            cursor = database.query(DepartmentInfoHelper.TABLE_NAME,
                    allColumns, DepartmentInfoHelper.NAME + " LIKE ? ", new String[]{"%Center%"},
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                entry = cursorToEntry(cursor);
                dpts.add(entry);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return dpts;
    }

    public List<DepartmentEntry> chooseQuery(String search) {
        List<DepartmentEntry> dpts = new ArrayList<>();
        DepartmentEntry entry;
        Cursor cursor = database.query(DepartmentInfoHelper.TABLE_NAME,
                allColumns, DepartmentInfoHelper.NAME + " LIKE ? ", new String[] { "%" + search + "%" },
                null, null, null);
        /*Cursor cursor = database.query(DepartmentInfoHelper.TABLE_NAME,
                allColumns, DepartmentInfoHelper.LOCATION + " LIKE ? OR  " + DepartmentInfoHelper.LOCATION + " LIKE ? ", new String[] { "%Cluster F%", "%F Cluster%" },
                null, null, null);*/
        /*Cursor cursor = database.query(DepartmentInfoHelper.TABLE_NAME,
                allColumns, DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? ",
                new String[] { "%Cluster F%", "%F Cluster%", "%Cluster E%" },
                null, null, null);*/
       /* Cursor cursor = database.query(DepartmentInfoHelper.TABLE_NAME,
                allColumns, DepartmentInfoHelper.LOCATION + " LIKE ? ",
                new String[] { "%Rice circle%" },
                null, null, DepartmentInfoHelper.LOCATION);
*/
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            entry = cursorToEntry(cursor);
            dpts.add(entry);
            cursor.moveToNext();
        }
        cursor.close();
        return dpts;
    }
}