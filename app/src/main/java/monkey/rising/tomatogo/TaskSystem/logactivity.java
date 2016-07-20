package monkey.rising.tomatogo.TaskSystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.dataoperate.UserControl;

public class logactivity extends AppCompatActivity {
       public Button log;
    public Button reg;
    public EditText id;
    public EditText pw;
   public Switch aSwitch;
    public UserControl userControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        log=(Button)findViewById(R.id.log) ;
        reg=(Button)findViewById(R.id.register);
        id=(EditText)findViewById(R.id.userid);
        pw=(EditText)findViewById(R.id.pw) ;
        aSwitch=(Switch) findViewById(R.id.switch1);
        userControl=new UserControl(this);
        userControl.openDataBase();
        userControl.loadUser();
        userControl.closeDb();


      reg.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
           Intent intent2=new Intent(logactivity.this,regisiter.class);

              startActivity(intent2);
          }
      });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userControl.IsUser(id.getText().toString(),pw.getText().toString())==1){
                    Toast.makeText(getApplicationContext(),"LOG IN",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(logactivity.this,tasklist.class);
                    intent.putExtra("userid",id.getText().toString());
                    startActivity(intent);

                }
                else { Toast.makeText(getApplicationContext(),"LOG fail",Toast.LENGTH_SHORT).show();
                }
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    pw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else {
                    pw.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
