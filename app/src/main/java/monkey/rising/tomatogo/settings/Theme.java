package monkey.rising.tomatogo.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import monkey.rising.tomatogo.R;

public class Theme extends AppCompatActivity {
    private RadioButton white;
    private RadioButton grey;

    private RadioButton yellow;
    private RadioButton lightblue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        final SharedPreferences.Editor editor = getSharedPreferences("color1", MODE_PRIVATE).edit();
        final SharedPreferences pref = getSharedPreferences("color1", MODE_PRIVATE);
        int a = pref.getInt("background", 0);
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        layout.setBackgroundColor(a);
        RadioGroup group = (RadioGroup) findViewById(R.id.radiogroup);
        white = (RadioButton) findViewById(R.id.white);
        yellow = (RadioButton) findViewById(R.id.yellow);
        grey = (RadioButton) findViewById(R.id.grey);
        lightblue = (RadioButton) findViewById(R.id.lightblue);


        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (white.getId() == i) {
                    editor.remove("background");
                    editor.commit();
                    editor.putInt("background", 0xffffffff);
                    editor.commit();
                    int c = pref.getInt("background", 0);
                    layout.setBackgroundColor(c);
                } else if (grey.getId() == i) {
                    editor.remove("background");
                    editor.commit();
                    editor.putInt("background", 0xffdfdfdf);
                    editor.commit();
                    int c = pref.getInt("background", 0);
                    layout.setBackgroundColor(c);
                } else if (lightblue.getId() == i) {
                    editor.remove("background");
                    editor.commit();
                    editor.putInt("background", 0xff00ddff);
                    editor.commit();
                    int c = pref.getInt("background", 0);
                    layout.setBackgroundColor(c);
                }
                else if(yellow.getId()==i)
                {
                    editor.remove("background");
                    editor.commit();
                    editor.putInt("background",0xFFF6FD23);
                    editor.commit();
                    int c=pref.getInt("background",0);
                    layout.setBackgroundColor(c);
                }
            }
        });
    }
    @Override
    public void onBackPressed(){

Intent intent = new Intent();
        setResult(2,intent);
        finish();
    }
}
