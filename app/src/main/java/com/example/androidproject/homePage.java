package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

public class homePage extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    public static final String TAG=".homePage";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<databaseHelper> matchesList;
    Button logut;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_xml,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(getApplicationContext(), "item1 selected", Toast.LENGTH_SHORT).show();
                break;
                case R.id.item2:
                    Intent intent1=new Intent(getApplicationContext(),logout.class);
                    startActivity(intent1);
                Toast.makeText(getApplicationContext(), "item2 selected", Toast.LENGTH_SHORT).show();
                break;
                case R.id.item3:
                    Intent in=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(in);
                Toast.makeText(getApplicationContext(), "item3 selected", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Log.d(TAG, "onCreate: thisisd");
        Intent intent=getIntent();
        recyclerView=findViewById(R.id.gameList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        matchesList=new ArrayList<databaseHelper>();
        recyclerAdapter=new RecyclerAdapter(this,matchesList);
        recyclerView.setAdapter(recyclerAdapter);
        EventChanegeListener();

    }

    private void EventChanegeListener() {
        db.collection("Matches").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null){
                    Log.d(TAG, "error: "+error.getMessage());
                    return;
                }
//                DocumentChange dc= (DocumentChange) value.getDocumentChanges();

//                Log.d(TAG, "onEvent: here "+value.getDocumentChanges().get(1));
                for (DocumentChange dc:value.getDocumentChanges()){
                    Calendar calendar=Calendar.getInstance();
                    int mYear =(int) calendar.get(Calendar.YEAR)%100;
                    int mMonth = calendar.get(Calendar.MONTH)+1;
                    int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                    String date=mYear+"-0"+mMonth+"-0"+mDay;
//                    Log.d(TAG, "onEvent: "+mDay+"-"+mMonth+"-"+mYear);
                        if (dc.getType()==DocumentChange.Type.ADDED){
//                            if (dc.getDocument().getString())
                            String startingDate=dc.getDocument().getString("startindate");
                            String starting_time[]=startingDate.split("\\s+");
                            if (date.compareTo(starting_time[0])==0){
                                Log.d(TAG, "onEvent:"+date+" is equal "+starting_time[0]);
                            }else if (date.compareTo(starting_time[0])>0){// date occur after starting date
                                Log.d(TAG, "in if : "+date+" occur after "+starting_time[0]);
                            }else if (date.compareTo(startingDate)<0){//date occur before starting date
                                Log.d(TAG, "in if : "+date+" occur before "+starting_time[0]);
                                matchesList.add(dc.getDocument().toObject(databaseHelper.class));
                            }
//                            matchesList.add(dc.getDocument().toObject(databaseHelper.class));
//                            Log.d(TAG, "starting date: "+starting_time[0]);
                        }

                        recyclerAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}