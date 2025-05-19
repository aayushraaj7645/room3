package com.example.room3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {

    // this is the main activity of the application
  private EditText firstname ;

    private EditText middlename ;
    private EditText lastname;
     private Button save;
    private TextView checker;
    private Button fetcher;

     appDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        firstname = findViewById(R.id.firstname);
        middlename = findViewById(R.id.middlename);
        lastname = findViewById(R.id.lastname);
        save = findViewById(R.id.save);
        checker = findViewById(R.id.checker);
        fetcher = findViewById(R.id.fetcher);


        //output.setMovementMethod(new android.text.method.ScrollingMovementMethod());



        db = Room.databaseBuilder(getApplicationContext(),
                appDatabase.class, "mineDatabase").build();


      save.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
               String fname = firstname.getText().toString();
               String mname = middlename.getText().toString();
               String lname = lastname.getText().toString();


               new Thread(()->
               {
                   if(fname.isEmpty() || mname.isEmpty() || lname.isEmpty())
                   {
                       runOnUiThread(()-> {
                           checker.setText("fill all fields");
                       });
                   }
                   else
                   {
                       Boolean check = db.userDao().isUserExists(fname);
                       if (!check) {
                           User user = new User(fname, mname, lname);
                           db.userDao().insert(user);
                           runOnUiThread(()-> {
                               checker.setText("user saved");
                           });
                       } else {
                           runOnUiThread(() -> {
                               checker.setText("exists");
                           });
                       }


                       runOnUiThread(() -> {
                           firstname.setText("");
                           middlename.setText("");
                           lastname.setText("");

                       });
                   }
               }).start();


               }

      });


      fetcher.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivities(new Intent[]{new Intent(getApplicationContext(), fetchdata.class)});

          }
      });




    }
}