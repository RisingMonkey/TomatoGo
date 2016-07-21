package monkey.rising.tomatogo.MainActivity;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.security.Provider;
import java.util.ArrayList;

import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.TaskSystem.logactivity;
import monkey.rising.tomatogo.TaskSystem.tasklist;
import monkey.rising.tomatogo.settings.Settings;

public class HomeActivity extends AppCompatActivity {
    private int recLen;
    private int second;
    private int minute;
    private int totalSec;
    private float rate;
    private boolean bell;
    private int notification;
    private boolean shake;
    private String m,s;
    private String username;
    private String task;
    private TextView mytext1;
    private TextView mytext2;
    private waterView waterView;
    private TextView mytext3;
    private TextView mytext4;
    private NumberPicker minutePicker;
    private TextView mytext5;
    private ImageView myimag1;
    private ImageView myimag2;
    private ImageView myimag3;
    private Vibrator vibrator;
    private SharedPreferences mySharedPreference;
    private ArrayList<String> path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.mytext1=(TextView)super.findViewById(R.id.mytext1);
        this.mytext2=(TextView)super.findViewById(R.id.mytext2);
        this.waterView=(waterView)super.findViewById(R.id.waterView);
        this.mytext3=(TextView)super.findViewById(R.id.mytext3);
        this.mytext4=(TextView) super.findViewById(R.id.mytext4);
        this.minutePicker=(NumberPicker)super.findViewById(R.id.minutePicker);
        this.mytext5=(TextView)super.findViewById(R.id.mytext5);
        this.myimag1=(ImageView)super.findViewById(R.id.myimag1);
        this.myimag2=(ImageView)super.findViewById(R.id.myimag2);
        this.myimag3=(ImageView)super.findViewById(R.id.myimag3);
        this.vibrator=(Vibrator)super.getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        mytext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent1=new Intent();
                intent1.setClass(HomeActivity.this,logactivity.class);
                startActivity(intent1);
            }
        });
        /*Intent intent5 = getIntent();
        Bundle bundle1 = intent5.getExtras();
        mytext1.setText(bundle1.getString(username));*/
        mytext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent();
                //intent2.setClass(HomeActivity.this,thirdActivity.class);
                startActivity(intent2);
            }
        });
        /*Intent intent6 = getIntent();
        Bundle bundle2 = intent6.getExtras();
        mytext2.setText(bundle1.getString(task));*/
        myimag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent();
                intent3.setClass(HomeActivity.this,tasklist.class);
                Bundle bundle = new Bundle();
                bundle.putString("昵称", username);
                intent3.putExtras(bundle);
                startActivity(intent3);
            }
        });
        myimag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4=new Intent();
                intent4.setClass(HomeActivity.this,Settings.class);
                Bundle bundle = new Bundle();
                bundle.putString("昵称", username);
                intent4.putExtras(bundle);
                startActivity(intent4);
            }
        });
        String[] gaps = {"5","10","15","20","25","30"};
        minutePicker.setDisplayedValues(gaps);
        minutePicker.setMaxValue(gaps.length - 1);
        minutePicker.setMinValue(0);
        minutePicker.setValue(0);
        waterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int minute2 = Integer.parseInt(minutePicker.getDisplayedValues()[minutePicker.getValue()]);
                minute= minute2;
                totalSec = 60 * minute;
                recLen = totalSec;
                waterView.setFlowNum("Start!");
                waterView.setmWaterLevel(1F);
                waterView.startWave();
                handler.postDelayed(runnable, 1000);
            }
        });
    }
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen--;
            if(recLen >= 0){
                minute = recLen / 60;
                second = recLen % 60;
                m = Integer.toString(minute);
                s = Integer.toString(second);
                if(minute < 10)
                    m = "0" + minute;
                if(second < 10)
                    s = "0" + second;
                waterView.setFlowNum(m + ":" + s);
                mytext4.setText(m+":"+s);
                rate = (float)recLen / totalSec;
                waterView.setmWaterLevel(rate);
                handler.postDelayed(this, 1000);
            }else{
                mySharedPreference=getSharedPreferences("Settings",MODE_PRIVATE);
                shake=mySharedPreference.getBoolean("shake",false);
                if(shake){
                    HomeActivity.this.vibrator.vibrate(new long[]{1000,10,1000,100},0);//震动服务
                }
                mySharedPreference=getSharedPreferences("Setting",MODE_PRIVATE);
                bell=mySharedPreference.getBoolean("bell",false);
                notification=mySharedPreference.getInt("notification",2);
                if(bell){
                    path =new ArrayList<>();
                    Uri uri=Uri.parse(path.get(notification));
                    MediaPlayer mp = MediaPlayer.create(HomeActivity.this,uri);
                    mp.start();
                }
            }
        }
    };
    private void scannerMediaFile() {
        ContentResolver cr = this.getContentResolver();
        Cursor cursor = cr.query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.TITLE}, "is_notification != ?",
                new String[]{"0"}, "_id asc");
        if (cursor == null) {
            return;
            }
        while (cursor.moveToNext()) {
            path.add(cursor.getString(1));
        }
    }
}

