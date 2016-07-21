package monkey.rising.tomatogo.settings;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.config.Utils;

public class Picture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.configSP = getSharedPreferences("textSize",MODE_PRIVATE);
        int textSizeLevel = Utils.configSP.getInt("textSizeStatus",3);
        Utils.onActivityCreateSetTheme(this,textSizeLevel);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
         SharedPreferences pref=getSharedPreferences("color1",MODE_PRIVATE);
       int a=pref.getInt("background",MODE_PRIVATE);
        RelativeLayout layout=(RelativeLayout)findViewById(R.id.layout_picture);
        layout.setBackgroundColor(a);

    }
}
