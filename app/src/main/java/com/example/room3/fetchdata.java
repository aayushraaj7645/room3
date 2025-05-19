package com.example.room3;

import static com.example.room3.appDatabase.MIGRATION_1_2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class fetchdata extends AppCompatActivity {
    RecyclerView recycler_view;
    appDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fetchdata);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        db = Room.databaseBuilder(getApplicationContext(),
                        appDatabase.class, "mineDatabase")
                .addMigrations(MIGRATION_1_2)
                .build();


        getroomdata();
    }
    public void getroomdata(){
      new Thread(()-> {


          List<User> user = db.userDao().getAll();


          runOnUiThread(() -> {
              myAdapter adapter = new myAdapter(user);
              recycler_view.setAdapter(adapter);

          });
      }).start();



}
}