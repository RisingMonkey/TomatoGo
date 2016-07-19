package monkey.rising.tomatogo.settings;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import monkey.rising.tomatogo.R;

public class Label extends AppCompatActivity {
  private RelativeLayout bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        bg=(RelativeLayout)findViewById(R.id.layout);
        bg.setBackgroundColor(Color.BLUE);
    }
}
