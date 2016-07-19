package monkey.rising.tomatogo.settings;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import  monkey.rising.tomatogo.R;

public class Settings extends AppCompatActivity {
    private Button skin;
    private Button label;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        skin = (Button) findViewById(R.id.skin);
        label = (Button) findViewById(R.id.label);
        skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, Skin.class);
                startActivity(intent);
            }
        });
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, Label.class);
                startActivity(intent);

            }
        });

    }}

