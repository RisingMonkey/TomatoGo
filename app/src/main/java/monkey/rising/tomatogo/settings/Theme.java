package monkey.rising.tomatogo.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import monkey.rising.tomatogo.R;

public class Theme extends AppCompatActivity {
private RadioGroup radiogroup;
    private RadioButton white;
    private RadioButton black;
    private RadioButton grey;
    private RadioButton lightblue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        radiogroup=(RadioGroup)this.findViewById(R.id.radiogroup);
        white =(RadioButton)findViewById(R.id.white);
        black =(RadioButton)findViewById(R.id.black);
        grey =(RadioButton)findViewById(R.id.grey);
        lightblue=(RadioButton)findViewById(R.id.lightblue);

    }}

