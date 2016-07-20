package monkey.rising.tomatogo.settings;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import monkey.rising.tomatogo.R;

public class Label extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        final SharedPreferences pref=getSharedPreferences("color1",MODE_PRIVATE);
        int a=pref.getInt("background",0);
        final RelativeLayout layout =(RelativeLayout)findViewById(R.id.layout_label);
        layout.setBackgroundColor(a);

    }
}
