package monkey.rising.tomatogo.dataoperate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lizhangfang on 2016/7/17.
 */
public class UDH extends SQLiteOpenHelper {

    public static final String Create_user="create table user("+"id text primary key autoincrement,"+"pw text)";



    public UDH(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
