package monkey.rising.tomatogo.dataoperate;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lizhangfang on 2016/7/19.
 */
public class CDH extends SQLiteOpenHelper {
    public static final String sql="create table clock("+"id primary key text,"+"username text,"+"taskid text,"+"lasttime integer,"+"timeexp integer,"+"isdone text,"
            +"content text,"+"type text)";
    public CDH(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
