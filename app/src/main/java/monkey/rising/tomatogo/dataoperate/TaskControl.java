package monkey.rising.tomatogo.dataoperate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by lizhangfang on 2016/7/13.
 */
public class TaskControl {
    ArrayList<Task> tasks=new ArrayList<>();
    private SQLiteDatabase db;
    private BDH tdh;
    private Context context;
    public TaskControl(Context context){
        this.context=context;
    }

    public void openDataBase(){
        tdh=new BDH(context,"data.db",null,1);
        try{
            db=tdh.getWritableDatabase();
        }catch (SQLException e){
            db=tdh.getReadableDatabase();
        }
    }

    public void closeDb(){
        if(db!=null){
            db.close();
        }
    }

    public long deletedata(String id){
        return db.delete("task","id='"+id+"'",null);
    }

    public long insertData(String id,String type,String user,String content,String time,String exptime,String starttime,int priority){
        ContentValues values=new ContentValues();
        values.put("id",id);
        values.put("type",type);
        values.put("user",user);
        values.put("content",content);
        values.put("time",time);
        values.put("timeexpected",exptime);
        values.put("starttime",starttime);
        values.put("priority",priority);
        values.put("isdone","false");
        return db.insert("task",null,values);


    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }


    public void AddTask(String id,String type,String user,String content,String time,String exptime,String starttime,int priority){
        tasks.add(new Task(id,type,user,content,time,exptime,starttime,priority));
    }


   public void UpdateTask(String type,String user,String content,String time,String id,int piority){
       for (Task task:tasks){
           if(task.getTime().equals(time)){
               if(task.getUserid().equals(user)){
                   if(task.getId().equals(id)){
                   task.setContent(content);
                   task.setType(type);
                    task.setPriority(piority);
                   }
               }
           }
       }
   }

    public long updatedate(String id,String type,String user,String content,String time,String exptime,String starttime,int priority){
        ContentValues values=new ContentValues();
        values.put("type",type);
        values.put("user",user);
        values.put("content",content);
        values.put("time",time);
        values.put("timeexpected",exptime);
        values.put("starttime",starttime);
        values.put("priority",priority);
        values.put("isdone","false");
        return db.update("task",values,"id='"+id+"'",null);
    }
    public void DeleteTask(String time,String userid){
        for (Task task:tasks){
            if(task.getTime().equals(time)){
                if(task.getUserid().equals(userid)){
          tasks.remove(task);
                }
            }
        }
    }

    public boolean Istype(ArrayList<String> list,String s){
        for(String string:list){
            if(string.equals(s)){
                return false;
            }
        }
        return true;
    }

    public ArrayList<Task> findtaskbyuser(String userid){
        ArrayList<Task> taskbyid=new ArrayList<>();
        for (Task task:tasks){
            if (userid.equals(task.getUserid())){
            taskbyid.add(task);
        }
        }
        return taskbyid ;
    }

 public ArrayList<String>gettype(String user){
     ArrayList<String> types=new ArrayList<>();
     types.add("学习");
     types.add("运动");
     types.add("娱乐");
     types.add("工作");
     types.add("休息");
     types.add("其他");
     Log.e("isempty:",""+tasks.isEmpty());
     for (Task task : tasks) {
         if (task.getUserid() != null) {
             if (task.getUserid().equals(user)) {
                 if (Istype(types, task.getType())) {
                     types.add(task.getType());
                 }
             }
         }
     }
     return types;
  }

    public void loadTask(){
        Cursor result=db.query("task",new String[]{"id","type","user","content","time","timeexpected","starttime","priority","isdone"},null,null,null,null,null);
        covertoTree(result);
    }

   public void covertoTree(Cursor cursor){
       int resultCounts=cursor.getCount();
       if(resultCounts==0||!cursor.moveToFirst()){
           return ;
       }
       for(int i=0;i<resultCounts;i++){
           Task task=new Task(cursor.getString(0),cursor.getString(cursor.getColumnIndex("type")),cursor.getString(cursor.getColumnIndex("user")),cursor.getString(cursor.getColumnIndex("content")), cursor.getString(cursor.getColumnIndex("time")),
            cursor.getString(cursor.getColumnIndex("timeexpected")),cursor.getString(cursor.getColumnIndex("starttime")),cursor.getInt(cursor.getColumnIndex("priority")));
           if(cursor.getString(cursor.getColumnIndex("isdone")).equals("true")){
               task.reverseIsdone();
           }
           tasks.add(task);
           cursor.moveToNext();
       }
   }

    public Task findByTaskId(String taskId){
        for(Task t:tasks){
            if(t.getId().equals(taskId))
                return t;
        }
        return null;
    }

}
