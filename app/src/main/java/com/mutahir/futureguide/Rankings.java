package com.mutahir.futureguide;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by raja m afzal on 4/23/2018.
 */

public class Rankings extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rankings);

        Intent intent=getIntent();
        final String name=intent.getStringExtra("name");

        final TextView rank=(TextView)findViewById(R.id.textView4);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Rankings");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String rank1 = dataSnapshot.child(name).getValue().toString();

                if ( !Character.isDigit(rank1.charAt(0)) )
                {
                    rank.setTextSize(26);
                }
                rank.setText(rank1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
