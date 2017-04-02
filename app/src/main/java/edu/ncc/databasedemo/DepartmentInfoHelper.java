package edu.ncc.databasedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class DepartmentInfoHelper extends SQLiteOpenHelper {

    // The URI scheme used for content URIs
    public static final String SCHEME = "content";

    // The provider's authority
    public static final String AUTHORITY = "edu.ncc.databasedemo";

    public static final Uri CONTENT_URI = Uri.parse(SCHEME + "://" + AUTHORITY);

    // table name
    public static final String TABLE_NAME = "departments";

    // columns in the table
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String LOCATION = "location";
    public static final String PHONE = "phone";

    // database version and name
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "departmentInformation.db";

    public DepartmentInfoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME +
                " TEXT, " + LOCATION + " TEXT, " + PHONE + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
