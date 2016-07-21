package monkey.rising.tomatogo.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.dataoperate.TaskControl;

public class Label extends AppCompatActivity {
    private ListView type;
    private TaskControl taskControl;
    private ArrayList<String> types=new ArrayList<String>();
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        taskControl=new TaskControl(this);
        taskControl.openDataBase();
        taskControl.loadTask();
        taskControl.closeDb();
        final SharedPreferences pref=getSharedPreferences("color1",MODE_PRIVATE);
        int a=pref.getInt("background",0);
        final RelativeLayout layout =(RelativeLayout)findViewById(R.id.layout_label);
        layout.setBackgroundColor(a);
        type=(ListView) findViewById(R.id.type);
        SharedPreferences shared=getSharedPreferences("share",MODE_PRIVATE);
        userid=shared.getString("userid","monkey");
        types=taskControl.gettype(userid);
        type.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,types));
    }
}
