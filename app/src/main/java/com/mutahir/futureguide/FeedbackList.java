package com.mutahir.futureguide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by raja m afzal on 2/25/2018.
 */

public class FeedbackList extends AppCompatActivity {

    RecyclerView myFeedback;
    DatabaseReference  databaseReference;
    int count1;
    List<String> ratings;
    TextView rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedbacklist);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");

        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = currentUser.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Feedbacks").child(name);

        myFeedback = (RecyclerView) findViewById(R.id.blog_list);
        myFeedback.setHasFixedSize(true);
        myFeedback.setLayoutManager(new LinearLayoutManager(this));

        ratings=new ArrayList<String>();

        final Button feedback = (Button) findViewById(R.id.feedback);


        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Reviews.class);
                Intent intent1=intent.putExtra("name",name);
                startActivity(intent);
            }
        });
        count1=-1;
       DatabaseReference dataReference = FirebaseDatabase.getInstance().getReference().child("Ratings").child(name);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double count = 0;
                count1 = (int) dataSnapshot.getChildrenCount();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    double rating = ds.child("rating").getValue(Double.class);
                    count = count + rating;
                }
                double count2=count/count1;

                Log.d("TAG", count + "");
                TextView textRating=(TextView)findViewById(R.id.textView4);
                RatingBar overAllRating=(RatingBar)findViewById(R.id.ovelAllRating);
                TextView users=(TextView)findViewById(R.id.textView5) ;
                textRating.setText(count2+"");
                overAllRating.setRating((float) count2);
                users.setText(count1+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        dataReference.addListenerForSingleValueEvent(eventListener);

    }
   @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<databaseReview,FeedbackViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<databaseReview, FeedbackViewHolder>(
                databaseReview.class,
                R.layout.singlefeddback,
                FeedbackViewHolder.class,
                databaseReference)
        {
            @Override
            protected void populateViewHolder(FeedbackViewHolder viewHolder, databaseReview model, int position) {
                final String postKey = getRef(position).getKey();
                viewHolder.setDescription(model.getDescription());
                viewHolder.setName(model.getName());
                viewHolder.setuserImage(getApplicationContext(),model.getuserImage());
                viewHolder.setRating(model.getRating());
                viewHolder.setRatingText(model.getRating());
            }
        };
        myFeedback.setAdapter(firebaseRecyclerAdapter);
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public FeedbackViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public  void setDescription(String desc){
            TextView Feedback_description = (TextView)mView.findViewById(R.id.post_description);
            Feedback_description.setText(desc);
        }
        public  void setName(String name){
            TextView userNAme = (TextView)mView.findViewById(R.id.displayName);
            userNAme.setText(name);
        }
        public  void setuserImage(Context context ,String image){
            CircleImageView mDisplayImage = (CircleImageView)mView.findViewById(R.id.settings_image);
            Picasso.with(context).load(image).into(mDisplayImage);
        }
        public  void setRating(String rating){
            RatingBar ratingBar = (RatingBar)mView.findViewById(R.id.ratingBar2);
            ratingBar.setRating(Float.parseFloat(rating));
        }
        public  void setRatingText(String ratingText){
            TextView userNAme = (TextView) mView.findViewById(R.id.textView);
            userNAme.setText(ratingText);
        }
    }
}
