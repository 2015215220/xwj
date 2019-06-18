package bysj.xwj.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "xwj.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
          db.execSQL("CREATE TABLE  x(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(20),  password VARCHAR(20))");//注册
          db.execSQL("CREATE TABLE  x_borrowtime(_id INTEGER PRIMARY KEY AUTOINCREMENT,  name VARCHAR(20),borrow_time VARCHAR(20))");//车名和借出时间
          db.execSQL("CREATE TABLE  x_returntime(_id INTEGER PRIMARY KEY AUTOINCREMENT,  name VARCHAR(20),return_time VARCHAR(20))");//车名和还车时间

//        db.execSQL("CREATE TABLE k(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(20),  time VARCHAR(20))");//钥匙
//        db.execSQL("CREATE TABLE g(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(20),  time VARCHAR(20))");//眼镜
//        db.execSQL("CREATE TABLE b(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(20),  time VARCHAR(20))");//钱包
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
