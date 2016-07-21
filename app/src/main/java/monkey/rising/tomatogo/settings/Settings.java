package monkey.rising.tomatogo.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import  monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.config.AchievementView;
import monkey.rising.tomatogo.config.ConfigView;
import monkey.rising.tomatogo.config.Utils;
import monkey.rising.tomatogo.statisticView.StatisticActivity;

public class Settings extends AppCompatActivity {


@Override protected void onActivityResult(int requestCode,int resultCode,Intent data)
{
    if(requestCode==2)
    {
        final SharedPreferences pref=getSharedPreferences("color1",MODE_PRIVATE);
        int a=pref.getInt("background",0);
        final RelativeLayout layout =(RelativeLayout)findViewById(R.id.layout_settings);
        layout.setBackgroundColor(a);
    }
}
        private Button skin;
        private Button label;
        private Button setting;
        private Button achievement;
        private Button tomato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.configSP = getSharedPreferences("textSize",MODE_PRIVATE);
        int textSizeLevel = Utils.configSP.getInt("textSizeStatus",3);
        Utils.onActivityCreateSetTheme(this,textSizeLevel);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        final SharedPreferences pref=getSharedPreferences("color1",MODE_PRIVATE);
        int a=pref.getInt("background",0);
        final RelativeLayout layout =(RelativeLayout)findViewById(R.id.layout_settings);
        layout.setBackgroundColor(a);
        skin = (Button) findViewById(R.id.skin);
        label = (Button) findViewById(R.id.label);
        setting = (Button)findViewById(R.id.setting);
        achievement = (Button)findViewById(R.id.achievement);
        tomato = (Button)findViewById(R.id.tomato);
        skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, Skin.class);
       startActivityForResult(intent,2);

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
                Intent intent = new Intent(Settings.this,ConfigView.class);
                startActivity(intent);
            }
        });
        achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(Settings.this, AchievementView.class);
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

    }}

