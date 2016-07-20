package monkey.rising.tomatogo.TaskSystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.dataoperate.UserControl;

public class regisiter extends AppCompatActivity {
    Button reg;
    EditText name;
    EditText pw1;
    EditText pw2;
    UserControl userControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisiter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         reg=(Button)findViewById(R.id.reg) ;
        name=(EditText)findViewById(R.id.name);
        pw1=(EditText)findViewById(R.id.pw1);
        pw2=(EditText)findViewById(R.id.pw2);
        userControl=new UserControl(this);



        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pw1.getText().toString().equals(pw2.getText().toString())){

                    userControl.openDataBase();
                    userControl.loadUser();

                    if(userControl.IsUser(name.getText().toString(),pw1.getText().toString())==2){
                        userControl.insertDate(name.getText().toString(),pw1.getText().toString());
                        userControl.closeDb();
                    Toast.makeText(getApplicationContext(),"register",Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(regisiter.this,logactivity.class));
                    }else {
                        Toast.makeText(getApplicationContext(),"existed user",Toast.LENGTH_SHORT).show();
                    }
                }else {
                Toast.makeText(getApplicationContext(),"pw not consistent",Toast.LENGTH_SHORT).show();
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
