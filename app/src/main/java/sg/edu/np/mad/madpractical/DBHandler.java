package sg.edu.np.mad.madpractical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "User.db";
    public static final String TABLE_USERS = "User";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FOLLOWED = "followed";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "( " +
                COLUMN_ID +  " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_DESC + "TEXT, " +
                COLUMN_FOLLOWED + "BOOLEAN" + ")";
        db.execSQL(CREATE_USER_TABLE);

        List<User> userList = getUserList();
        insertData(db, userList);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private void insertData(SQLiteDatabase db, List<User> userList) {
        for (User user : userList) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, user.getName());
            values.put(COLUMN_DESC, user.getDescription());
            values.put(COLUMN_ID, user.getId());
            values.put(COLUMN_FOLLOWED, user.isFollowed() ? 1 : 0);

            db.insert(TABLE_USERS, null, values);
        }
    }

    private List<User> getUserList() {
        List<User> userList = new ArrayList<>();

        userList.add(new User("Name34567890", "Description45678901", 1, true));
        userList.add(new User("Name23456789", "Description34567890", 2, false));
        userList.add(new User("Name12345678", "Description23456789", 3, true));
        userList.add(new User("Name56789012", "Description67890123", 4, false));
        userList.add(new User("Name45678901", "Description56789012", 5, true));
        userList.add(new User("Name89012345", "Description78901234", 6, false));
        userList.add(new User("Name78901234", "Description67890123", 7, true));
        userList.add(new User("Name67890123", "Description56789012", 8, false));
        userList.add(new User("Name01234567", "Description89012345", 9, true));
        userList.add(new User("Name90123456", "Description78901234", 10, false));
        userList.add(new User("Name78901234", "Description67890123", 11, true));
        userList.add(new User("Name67890123", "Description56789012", 12, false));
        userList.add(new User("Name12345678", "Description23456789", 13, true));
        userList.add(new User("Name34567890", "Description45678901", 14, false));
        userList.add(new User("Name45678901", "Description56789012", 15, true));
        userList.add(new User("Name23456789", "Description34567890", 16, false));
        userList.add(new User("Name56789012", "Description67890123", 17, true));
        userList.add(new User("Name89012345", "Description78901234", 18, false));
        userList.add(new User("Name90123456", "Description78901234", 19, true));
        userList.add(new User("Name01234567", "Description89012345", 20, false));

        return userList;
    }

    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setName(cursor.getString(1));
                user.setDescription(cursor.getString(2));
                user.setFollowed(cursor.getInt(3) == 1);
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return userList;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_DESC, user.getDescription());
        values.put(COLUMN_FOLLOWED, user.isFollowed() ? 1 : 0);
        db.update(TABLE_USERS, values, COLUMN_ID + "=?", new String[]{String.valueOf(user.getId())});
    }
}
