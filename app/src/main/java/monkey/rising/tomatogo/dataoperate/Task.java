package monkey.rising.tomatogo.dataoperate;

/**
 * Created by lizhangfang on 2016/7/13.
 */
public class Task {
    public String id;
    public String type;
    public String userid;
    public String content;
    public String time;
    public String timeexpexted;
    public  String starttime;
    public boolean isDone;
    public int priority;

    Task(String id,String type,String userid,String content,String time,String timeexpexted,String starttime,int priority){
       this.id=id;
        this.type=type;
        this.userid=userid;
        this.content=content;
        this.time=time;
        this.timeexpexted=timeexpexted;
        this.isDone=false;
        this.starttime=starttime;
        this.priority=priority;
    }

    public  void  reverseIsdone(){
        this.isDone=true;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setTimeexpexted(String timeexpexted) {
        this.timeexpexted = timeexpexted;
    }

    public String getTimeexpexted() {
        return timeexpexted;
    }

    public String getId() {
        return id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
