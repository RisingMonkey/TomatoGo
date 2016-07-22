package monkey.rising.tomatogo.TaskSystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import monkey.rising.tomatogo.MainActivity.HomeActivity;
import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.TaskSystem.slideview.RemoveDirection;
import monkey.rising.tomatogo.TaskSystem.slideview.RemoveListener;
import monkey.rising.tomatogo.config.Utils;
import monkey.rising.tomatogo.dataoperate.Task;
import monkey.rising.tomatogo.dataoperate.TaskControl;
import monkey.rising.tomatogo.settings.Settings;

public class tasklist extends AppCompatActivity implements RemoveListener{
   private slideview slide;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> data=new ArrayList<String>();
    String userid;
    Button button;
    TaskControl taskControl;
    ImageView taskImage;
    ImageView settingsImage;
    ImageView imageView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.configSP = getSharedPreferences("Settings",MODE_PRIVATE);
        boolean screenOn = Utils.configSP.getBoolean("lightOn",false);
        boolean fullScreen = Utils.configSP.getBoolean("fullScreen",true);
        if (screenOn){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        if(fullScreen){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        Utils.configSP = getSharedPreferences("textSize",MODE_PRIVATE);
        int textSizeLevel = Utils.configSP.getInt("textSizeStatus",3);
        Utils.onActivityCreateSetTheme(this,textSizeLevel);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
        taskImage = (ImageView) super.findViewById(R.id.imageView2);
        settingsImage = (ImageView) super.findViewById(R.id.imageView3);
        imageView = (ImageView) super.findViewById(R.id.imageView1);
        button=(Button)findViewById(R.id.add) ;
        slide=(slideview) findViewById(R.id.tasklist) ;
        slide.setRemoveListener(this);
        taskControl=new TaskControl(this);
        taskControl.openDataBase();
        taskControl.loadTask();
        taskControl.closeDb();
        Intent intent=getIntent();
        userid=intent.getStringExtra("userid");

        for (Task task:taskControl.findtaskbyuser(userid)) {
            data.add(task.getContent());
        }
        arrayAdapter=new ArrayAdapter<String>(this,R.layout.item,R.id.item,data);
        slide.setAdapter(arrayAdapter);

        ArrayList<String> str=new ArrayList<>();

        for (Task task:
           taskControl.findtaskbyuser(userid) ) {
            if(!task.isDone)
            str.add(task.getContent());

        }

        slide.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),taskControl.findtaskbyuser(userid).get(position).timeexpexted,Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(tasklist.this,EditTask.class);
                intent1.putExtra("id",taskControl.findtaskbyuser(userid).get(position).getId());
                startActivity(intent1);
            }
        });

        settingsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至设置界面
                Intent intent4 = new Intent();
                intent4.setClass(tasklist.this, Settings.class);
                Bundle bundle = new Bundle();
                bundle.putString("userid", userid);
                intent4.putExtras(bundle);
                startActivity(intent4);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tasklist.this, HomeActivity.class));
            }
        });
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           Intent intent1=new Intent(tasklist.this,AddTask.class);
            intent1.putExtra("userid",userid);
            startActivity(intent1);
        }
    });

    }

    @Override
    public void removeItem(RemoveDirection direction, int position) {

        arrayAdapter.remove(arrayAdapter.getItem(position));
        taskControl.openDataBase();
        taskControl.deletedata(taskControl.findtaskbyuser(userid).get(position).getId());
        taskControl.closeDb();
        switch (direction){
            case RIGHT:
                Toast.makeText(this,"已删除",Toast.LENGTH_SHORT).show();
                break;
            case LEFT:
                Toast.makeText(this,"已删除",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
