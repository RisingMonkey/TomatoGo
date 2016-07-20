package monkey.rising.tomatogo.dataoperate;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lizhangfang on 2016/7/19.
 */
public class ClockControl {
    ArrayList<Clock> clocks=new ArrayList<>();
    private SQLiteDatabase db;
    private CDH cdh;
    private Context context;
    public ClockControl(Context context){
        this.context=context;
    }
    public void openDataBase(){
        cdh=new CDH(context,"Clock.db",null,1);
        try{
            db=cdh.getWritableDatabase();
        }catch (SQLException e){
            db=cdh.getReadableDatabase();
        }
    }
    public void closeDb(){
        if(db!=null){
            db.close();
        }
    }




    public ArrayList<Clock> getbyType(String type,String user){
        ArrayList<Clock> clocks=new ArrayList<Clock>();
        for (Clock clock:findbyuser(user)) {
            if(clock.getType().equals(type)){
                clocks.add(clock);
            }
        }
        return clocks;

    }
    public  void loadclock(){
        Cursor cursor=db.query("clock",new String[]{"id","username","taskid","lasttime","timeexp","isdone","content","type"},null,null,null,null,null);
        covertoClock(cursor);
    }

    public  int getDayByMonth(int year,int month){
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month-1);
        c.set(Calendar.DATE,1);
        c.roll(Calendar.DATE,-1);
        int day=c.get(Calendar.DATE);
        return day;

    }


    public ArrayList<Integer> countbymonth(String day,String user) throws ParseException {

        ArrayList<Integer> result=new ArrayList<>();
        Calendar c=Calendar.getInstance();
        Date d=new SimpleDateFormat("yyyy-MM").parse(day);
        c.setTime(d);
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        c.set(Calendar.DAY_OF_MONTH,1);
        for (int i=0;i< getDayByMonth(year,month);i++){
            Date date=c.getTime();
            String s=new SimpleDateFormat("yyyy-MM-dd").format(date);
            int j=findbyday(s,user).size();
            result.add(j);
        }
        return result;
    }

    public void covertoClock(Cursor cursor){
        int resultCounts=cursor.getCount();
        if(resultCounts==0||!cursor.moveToFirst()) {
            return;
        }
        for (int i=0;i<resultCounts;i++){
            Clock clock= new Clock(cursor.getString(0),cursor.getString(cursor.getColumnIndex("username")),cursor.getString(cursor.getColumnIndex("taskid")),cursor.getInt(cursor.getColumnIndex("lasttime"))
            ,cursor.getInt(cursor.getColumnIndex("timeexp")),cursor.getString(cursor.getColumnIndex("content")),cursor.getString(cursor.getColumnIndex("type")));
            if(cursor.getString(cursor.getColumnIndex("isdone")).equals("true")){
                clock.setIsdone();
            }
            clocks.add(clock);
            cursor.moveToNext();
        }
    }

    public ArrayList<Clock> findbymonth(String day,String user){
        ArrayList<Clock> cLockbymonth=new ArrayList<>();
        Date d=null;
        try {
            for (Clock clock:findbyuser(user)) {
                d=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(clock.getId());
                String s=new SimpleDateFormat("yyyy-MM").format(d);
                if (s.equals(day)){
                    cLockbymonth.add(clock);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return cLockbymonth;
    }


    public ArrayList<Clock> findbyday(String day,String user){
        ArrayList<Clock> clocksbyday=new ArrayList<>();
        Date d=null;
        try {
            for (Clock clock:findbyuser(user)) {
                d=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(clock.getId());
                String s=new SimpleDateFormat("yyyy-MM-dd").format(d);
                if (s.equals(day)){
                    clocksbyday.add(clock);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return clocksbyday;
    }

    public ArrayList<Clock> findbyuser(String user){
        ArrayList<Clock> clocksbyuser=new ArrayList<>();
        for (Clock clock:clocks) {
            if(clock.getUserid().equals(user)){
                clocksbyuser.add(clock);
            }
        }
        return clocksbyuser;
    }

    public long insertOneClock(String id,String userid,String taskid,int lasttime,int timeexp,String taskContent,String type){
        ContentValues values=new ContentValues();
        values.put("id",id);
        values.put("username",userid);
        values.put("taskid",taskid);
        values.put("lasttime",lasttime);
        values.put("timeexp",timeexp);
        values.put("isdone","false");
        values.put("content",taskContent);
        values.put("type",type);
        return db.insert("clock",null,values);
    }
}