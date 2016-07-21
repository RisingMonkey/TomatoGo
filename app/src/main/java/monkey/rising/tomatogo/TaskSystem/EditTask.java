package monkey.rising.tomatogo.TaskSystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import monkey.rising.tomatogo.MainActivity.HomeActivity;
import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.dataoperate.Task;
import monkey.rising.tomatogo.dataoperate.TaskControl;

import java.util.ArrayList;
import java.util.List;

public class EditTask extends AppCompatActivity {
   EditText content;
    EditText type;
    EditText exptime;
    Spinner spinner;
    String userid;
    String mytype;
    Button submit;
    Button start;
    TaskControl taskControl;
    private List<String> list=new ArrayList<String>();
   private ArrayAdapter<String> adapter;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Intent intent1=getIntent();
        id=intent1.getStringExtra("id");
        taskControl=new TaskControl(this);
        taskControl.openDataBase();
      taskControl.loadTask();
        taskControl.closeDb();
        submit=(Button)findViewById(R.id.submit) ;
        start=(Button)findViewById(R.id.start) ;
        spinner=(Spinner)findViewById(R.id.spinner2);
       content=(EditText)findViewById(R.id.Content);
        type=(EditText)findViewById(R.id.type);
        exptime=(EditText)findViewById(R.id.expectedtime) ;
         for (Task task:taskControl.getTasks()) {
            if(task.getId().equals(id)){
                content.setText(task.getContent());
                mytype=task.getType();
                exptime.setText(task.getTimeexpexted());
                exptime.setEnabled(false);
                userid=task.getUserid();
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskControl.openDataBase();
                taskControl.updatedate(id,type.getText().toString(),userid,content.getText().toString(),"null",exptime.getText().toString(),"null",1);
                taskControl.closeDb();
                Intent i=new Intent(EditTask.this,tasklist.class);
                i.putExtra("userid",userid);
                startActivity(i);

            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTask.this, HomeActivity.class);
                intent.putExtra("taskid",id);
                startActivity(intent);
            }
        });
       list=taskControl.gettype(userid);


        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               if(list.get(position).equals("其他")){
                    type.setEnabled(true);
                    type.setText(mytype);
                }else {
                    type.setEnabled(false);
                    type.setText(list.get(position));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
