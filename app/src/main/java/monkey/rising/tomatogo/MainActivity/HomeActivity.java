package monkey.rising.tomatogo.MainActivity;

import android.app.Service;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.TaskSystem.logactivity;
import monkey.rising.tomatogo.TaskSystem.tasklist;
import monkey.rising.tomatogo.dataoperate.Clock;
import monkey.rising.tomatogo.dataoperate.ClockControl;
import monkey.rising.tomatogo.dataoperate.Task;
import monkey.rising.tomatogo.dataoperate.TaskControl;
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
    private String m, s;
    private String username;
    private String curTask;
    private TextView userTextView;
    private TextView currentTaskTextView;
    private waterView waterView;
    private TextView mytext3;
    //private TextView mytext4;
    private NumberPicker minutePicker;
    private TextView mytext5;
    private ImageView taskImage;
    private ImageView settingsImage;
    private ImageView myimag3;
    private Vibrator vibrator;
    private SharedPreferences mySharedPreference;
    private ArrayList<String> path;
    private boolean isDoing;
    private String startTime;
    private Task currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.userTextView = (TextView) super.findViewById(R.id.mytext1);
        this.currentTaskTextView = (TextView) super.findViewById(R.id.mytext2);
        this.waterView = (waterView) super.findViewById(R.id.waterView);
        this.mytext3 = (TextView) super.findViewById(R.id.mytext3);
        this.minutePicker = (NumberPicker) super.findViewById(R.id.minutePicker);
        this.mytext5 = (TextView) super.findViewById(R.id.mytext5);
        this.taskImage = (ImageView) super.findViewById(R.id.myimag1);
        this.settingsImage = (ImageView) super.findViewById(R.id.myimag2);
        this.myimag3 = (ImageView) super.findViewById(R.id.myimag3);
        this.vibrator = (Vibrator) super.getApplication().getSystemService(Service.VIBRATOR_SERVICE);

        waterView.setFlowNum("选择番茄钟时间");
        waterView.setmWaterLevel(1.0f);
        //获取当前任务
//        Intent taskChangedIntent = getIntent();
//        Bundle taskExtras = taskChangedIntent.getExtras();
//        curTask = taskExtras.getString("task");
        if(curTask==null){
            curTask = "eat";
        }
        TaskControl tc = new TaskControl(this);
        tc.openDataBase();;
        tc.loadTask();
        tc.closeDb();
        currentTask = tc.findByTaskId(curTask);

        //获取用户名，未登录则默认名为“monkey”
        mySharedPreference = getSharedPreferences("share", MODE_PRIVATE);
        username =  mySharedPreference.getString("userid","monkey");
        if (username.equals("monkey")) {
            //未登录点击文字跳转至登录界面，否则跳转至个人信息界面
            userTextView.setText("登录/注册");
            userTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent logIntent = new Intent();
                    logIntent.setClass(HomeActivity.this, logactivity.class);
                    startActivity(logIntent);
                }
            });
        } else {
            userTextView.setText(username);
            userTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent settingIntent = new Intent();
                    settingIntent.setClass(HomeActivity.this, Settings.class);
                    startActivity(settingIntent);
                }
            });
        }

        currentTaskTextView.setText(curTask);
        currentTaskTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至当前任务编辑页面
//                Intent taskEditIntent = new Intent();
//                taskEditIntent.setClass(HomeActivity.this,QuickStartActivity.class);
//                startActivity(taskEditIntent);
            }
        });

        taskImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至备忘录
                Intent intentTask = new Intent();
                intentTask.setClass(HomeActivity.this, tasklist.class);
                Bundle bundle = new Bundle();
                bundle.putString("userid", username);
                intentTask.putExtras(bundle);
                startActivity(intentTask);
            }
        });
        settingsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至设置界面
                Intent intent4 = new Intent();
                intent4.setClass(HomeActivity.this, Settings.class);
                Bundle bundle = new Bundle();
                bundle.putString("useid", username);
                intent4.putExtras(bundle);
                startActivity(intent4);
            }
        });

        String[] gaps = {"5", "10", "15", "20", "25", "30"};
        minutePicker.setDisplayedValues(gaps);
        minutePicker.setMaxValue(gaps.length - 1);
        minutePicker.setMinValue(0);
        minutePicker.setValue(0);
        waterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isDoing) {
                    TomatoGo(Integer.parseInt(minutePicker.getDisplayedValues()[minutePicker.getValue()]));
                }else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
                    dialog.setTitle("半途而废？");
                    dialog.setMessage("确定放弃本次番茄钟？");
                    dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            waterView.stopWave();
                            isDoing = false;
                            waterView.setFlowNum("");
                            waterView.setmWaterLevel(1F);
                            ClockControl cc = new ClockControl(getApplicationContext());
                            cc.openDataBase();
                            cc.insertOneClock(startTime, username, curTask, (totalSec - recLen) / 60, totalSec / 60, false);
                            cc.closeDb();
                        }
                    });
                    dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();
                }
            }
        });
    }


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen--;
            if (recLen >= 0) {
                minute = recLen / 60;
                second = recLen % 60;
                m = Integer.toString(minute);
                s = Integer.toString(second);
                if (minute < 10)
                    m = "0" + minute;
                if (second < 10)
                    s = "0" + second;
                waterView.setFlowNum(m + ":" + s);
                rate = (float) recLen / totalSec;
                waterView.setmWaterLevel(rate);
                handler.postDelayed(this, 1000);
            } else {
                mySharedPreference = getSharedPreferences("Settings", MODE_PRIVATE);
                shake =  mySharedPreference.getBoolean("shake",false );
                if (shake) {
                    HomeActivity.this.vibrator.vibrate(new long[]{1000, 10, 1000, 100}, 0);//震动服务
                }
                bell =  mySharedPreference.getBoolean("bell", false);
                notification = mySharedPreference.getInt("notification", 2);
                if (bell) {
                    path = new ArrayList<>();
                    scannerMediaFile();
                    Uri uri = Uri.parse(path.get(notification));
                    MediaPlayer mp = MediaPlayer.create(HomeActivity.this, uri);
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
        AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
        dialog.setTitle("番茄完成");
        dialog.setMessage("你工作了" + totalSec/60 + " 分钟!");
        CheckBox cb = new CheckBox(getApplicationContext());
        cb.setText("任务：" + currentTask.getContent() + "完成");
        dialog.setView(cb);
        dialog.setPositiveButton("放松一下☺(5min)", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TomatoGo(5);
            }
        });
        dialog.setNegativeButton("继续工作", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TomatoGo(Integer.parseInt(minutePicker.getDisplayedValues()[minutePicker.getValue()]));
            }
        });
        dialog.show();
    }

    private void TomatoGo(int mt){
            startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            minute = mt;
            totalSec = 60 * minute;
            recLen = totalSec;
            waterView.setFlowNum("Start!");
            waterView.setmWaterLevel(1F);
            waterView.startWave();
            handler.postDelayed(runnable, 1000);
            isDoing = true;
    }
}

