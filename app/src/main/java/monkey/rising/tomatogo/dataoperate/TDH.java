package monkey.rising.tomatogo.dataoperate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lizhangfang on 2016/7/17.
 */
public class TDH extends SQLiteOpenHelper {
    public static final String sql="Create table task("+"id text primary key ,"+"type text,"+"user text,"+"content text,"+"time text,"+"timeexpected text,"
            +"starttime text,"+"priority integer,"+"isdone text)";
    public TDH(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
