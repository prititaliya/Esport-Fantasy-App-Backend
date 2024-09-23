package com.example.androidproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

public class BGMIList extends Fragment {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    public static final String TAG=".BGMI";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<databaseHelper> matchesList;
    Button logut;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_b_g_m_i_list,container,false);
        recyclerView= view.findViewById(R.id.BGMIgameList);
        if (recyclerView != null){
            recyclerView.setHasFixedSize(true);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        matchesList=new ArrayList<databaseHelper>();
        recyclerAdapter=new RecyclerAdapter(getContext(),matchesList);
        recyclerView.setAdapter(recyclerAdapter);
        EventChanegeListener();
        return inflater.inflate(R.layout.fragment_b_g_m_i_list, container, false);
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