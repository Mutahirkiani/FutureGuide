package com.mutahir.futureguide;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by raja m afzal on 4/21/2018.
 */

public class JoinedCommunities extends AppCompatActivity {
    List<String> universities;
    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joined_communities);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.status_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Joined Communities");

        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid = mCurrentUser.getUid();
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        universities=new ArrayList<String>() ;
        final ListView list=(ListView)findViewById(R.id.listView);

       DatabaseReference dataReference = FirebaseDatabase.getInstance().getReference().child("JoinUniversities").child(current_uid);
        dataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    JoinedCommunitiesDatabase c = postSnapshot.getValue(JoinedCommunitiesDatabase.class);
                   final String user_Name = c.getName();
                   universities.add(user_Name);

                }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.customlist,R.id.textView2,universities);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String selectedFromList = (String) list.getItemAtPosition(position);
                String name=selectedFromList;
                Intent intent=new Intent(getApplicationContext(),Community.class);
                Intent user=intent.putExtra("name",name);
                startActivityForResult(intent,1);


            }
        });

    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        // do some stuff here
    }
}
