package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Logindatabase extends SQLiteOpenHelper {

    private SQLiteDatabase wdb = null;
    private SQLiteDatabase rdb = null;
    private static Logindatabase db = null;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user2.db";
    private static final String Table_Name = "user";
    private static final String sql = "CREATE TABLE " + Table_Name + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "phone VARCHAR NOT NULL," +
            "password VARCHAR NOT NULL," +
            "remember INTEGER NOT NULL );";

    //private static final String remember = "remember";


    public Logindatabase(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //利用单例模式获取数据库帮助器的唯一实例
    public static Logindatabase getInstance(Context context) {
        if (db == null) {
            db = new Logindatabase(context);
        }
        return db;
    }


    public void openwritabledatabase() {
        if (wdb == null || !wdb.isOpen()) {
            wdb = db.getWritableDatabase();
        }
    }

    public void openreadabledatabase() {
        if (rdb == null || !rdb.isOpen()) {
            rdb = db.getReadableDatabase();
        }
    }

    public void closeDatabase() {
        if (wdb != null) {
            wdb.close();
            wdb = null;
        }
        if (rdb != null) {
            rdb.close();
            rdb = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {
        //创建数据库里的表,设置表的属性
//        String sql = "CREATE TABLE " + Table_Name + " (" +
//                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                "phone VARCHAR NOT NULL," +
//                "password VARCHAR NOT NULL," +
//                "remember INTEGER NOT NULL) ; ";

        //建表
        sdb.execSQL(sql);
    }

    public void dropTable() {
        wdb.execSQL("DROP TABLE IF EXISTS " + Table_Name);
    }


    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public loginmassage querytop() {
        loginmassage info = new loginmassage();
        String sql = "SELECT * FROM " + Table_Name + " WHERE remember = 1 ORDER BY _id DESC limit 1";
        Cursor cursor = rdb.rawQuery(sql, null);
        if (cursor.moveToNext()) ;
        {
            info.id = cursor.getInt(0);
            info.phone = cursor.getString(1);
            info.password = cursor.getString(2);
            info.remember = (cursor.getInt(3) == 0) ? false : true;

        }
        return info;
    }

    public loginmassage querybyphone(String phone2) {
        loginmassage info = null;
        Cursor cursor = rdb.query(Table_Name, null, "phone=?", new String[]{phone2}, null, null, null);
        if (cursor.moveToNext())
        {
            info = new loginmassage();
            info.id = cursor.getInt(0);
            info.phone = cursor.getString(1);
            info.password = cursor.getString(2);
            info.remember = (cursor.getInt(3) == 0) ? false : true;

        }
        cursor.close();
        return info;
    }

    public void save(loginmassage info) {
        //如果存在则先删除，再添加
        try {
            wdb.beginTransaction();
            delete(info);
            insert(info);
            wdb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            wdb.endTransaction();
        }
    }

    public void update(loginmassage info, String newpassword) {
        ContentValues values = new ContentValues();
        values.put("phone", info.phone);
        values.put("password", newpassword);
        values.put("remember", info.remember);
        wdb.update(Table_Name, values, "phone=?", new String[]{info.phone});
    }
    public long delete(loginmassage info) {
        return wdb.delete(Table_Name, "phone=?", new String[]{info.phone});
    }

    public long insert(loginmassage info) {
        ContentValues values = new ContentValues();
        values.put("phone", info.phone);
        values.put("password", info.password);
        values.put("remember", info.remember);

        return wdb.insert(Table_Name, null, values);
//执行插入记录动作，该语句返回插入记录的行号
//如果第三个参数values为Nul1或者元素个数为0，由 Finsert()方法要求必须添加一条除了主键之外其它字殿
//为了满足SQL语法的需要， insert语句必须给定- -个字段名，如: insert into person (name) values (NULL)
//倘若不给定字段名，insert语句就成了这样: insert into person() values()， 显然这不满足标准SQL
//如果第三个参数values 不为Nu11并且元素的个数大于0，可以把第二个参数设置为null。

    }

}
