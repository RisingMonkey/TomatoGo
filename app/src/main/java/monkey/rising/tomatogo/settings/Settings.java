package monkey.rising.tomatogo.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import monkey.rising.tomatogo.MainActivity.HomeActivity;
import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.config.AchievementView;
import monkey.rising.tomatogo.config.ConfigView;
import monkey.rising.tomatogo.config.Utils;
import monkey.rising.tomatogo.statisticView.StatisticActivity;

public class Settings extends AppCompatActivity {


    @InjectView(R.id.nickname)
    Button nickname;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            final SharedPreferences pref = getSharedPreferences("color1", MODE_PRIVATE);
            int a = pref.getInt("background", 0);
            final RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout_settings);
            layout.setBackgroundColor(a);
        }
    }

    private Button skin;
    private Button label;
    private Button setting;
    private Button achievement;
    private Button tomato;
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.configSP = getSharedPreferences("Settings", MODE_PRIVATE);
        boolean screenOn = Utils.configSP.getBoolean("lightOn", false);
        boolean fullScreen = Utils.configSP.getBoolean("fullScreen", true);
        if (screenOn) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        if (fullScreen) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        Utils.configSP = getSharedPreferences("textSize", MODE_PRIVATE);
        int textSizeLevel = Utils.configSP.getInt("textSizeStatus", 3);
        Utils.onActivityCreateSetTheme(this, textSizeLevel);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ButterKnife.inject(this);

        final SharedPreferences pref = getSharedPreferences("color1", MODE_PRIVATE);
        int a = pref.getInt("background", 0);
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout_settings);
        layout.setBackgroundColor(a);
        skin = (Button) findViewById(R.id.skin);
        label = (Button) findViewById(R.id.label);
        setting = (Button) findViewById(R.id.setting);
        achievement = (Button) findViewById(R.id.achievement);
        tomato = (Button) findViewById(R.id.tomato);
        exitButton = (Button) findViewById(R.id.exit);
        skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, Skin.class);
                startActivityForResult(intent, 2);

            }
        });
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, Label.class);

                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, ConfigView.class);
                startActivity(intent);
            }
        });
        achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, AchievementView.class);
                startActivity(intent);
            }
        });
        tomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, StatisticActivity.class);
                startActivity(intent);
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        String uid = sharedPreferences.getString("userid", "monkey");
        nickname.setText(uid);
        if (uid.equals("monkey"))
            exitButton.setVisibility(View.INVISIBLE);
        else
            exitButton.setVisibility(View.VISIBLE);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("share", AppCompatActivity.MODE_PRIVATE);
                SharedPreferences.Editor e = sharedPreferences.edit();
                e.putString("userid", "monkey");
                e.commit();
                Intent intent = new Intent(Settings.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}

