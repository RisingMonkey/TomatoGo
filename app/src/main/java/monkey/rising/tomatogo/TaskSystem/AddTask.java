package monkey.rising.tomatogo.TaskSystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.dataoperate.TaskControl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {
    private EditText content;
    String user;
    private EditText type;
    private EditText time;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private EditText test;
    private Button submit;
    private Button back;
    private String id;

    private List<String> list=new ArrayList<String>();
    public TaskControl taskControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        content=(EditText)findViewById(R.id.content);
        type=(EditText)findViewById(R.id.type);
        time=(EditText)findViewById(R.id.timeexp);
        submit=(Button)findViewById(R.id.submit);
        back=(Button)findViewById(R.id.back) ;
        taskControl=new TaskControl(this);
        taskControl.openDataBase();
        taskControl.loadTask();
        taskControl.closeDb();
        Intent intent=getIntent();
        user=intent.getStringExtra("userid");


        test=(EditText)findViewById(R.id.id);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        id=format.format(new java.util.Date());
       test.setText(id);

        setSupportActionBar(toolbar);
        list=taskControl.gettype(user);
        spinner=(Spinner)findViewById(R.id.spinner) ;
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(list.get(position).equals("其他")){
                    type.setEnabled(true);
                }else {
                    type.setEnabled(false);
                }
                type.setText(list.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.getText()!=null&&content.getText()!=null&&time.getText()!=null){
                taskControl.openDataBase();
                 taskControl.insertData(id,type.getText().toString(),user,content.getText().toString(),"null",time.getText().toString(),"null",1);
                taskControl.closeDb();
                Intent intent=new Intent(AddTask.this,tasklist.class);
                 intent.putExtra("userid",user);
                 startActivity(intent);

            }else {
                    Toast.makeText(getApplicationContext()," 请完善信息",Toast.LENGTH_SHORT).show();}
        }});

       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               finish();
           }
       });




    }

}
