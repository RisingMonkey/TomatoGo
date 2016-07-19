package monkey.rising.tomatogo.dataoperate;

import java.io.Serializable;

/**
 * Created by lizhangfang on 2016/7/13.
 */
public class User implements Serializable{
    public String id;
    public String pw;
    User(String id,String pw){
        this.id=id;
        this.pw=pw;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }
}
