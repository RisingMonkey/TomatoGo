package monkey.rising.tomatogo.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import monkey.rising.tomatogo.R;

public class Skin extends AppCompatActivity {
private Button picture;

    private Button theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skin);
        picture = (Button)findViewById(R.id.picture);
        theme = (Button)findViewById(R.id.theme);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Skin.this,Picture.class);
                startActivity(intent);
            }
        });
        theme.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                Intent intent = new Intent(Skin.this,Theme.class);
                startActivity(intent);
            }

        });
    }
}
