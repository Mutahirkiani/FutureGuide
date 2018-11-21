package com.mutahir.futureguide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import de.hdodenhof.circleimageview.CircleImageView;



public class Community extends AppCompatActivity {
    DatabaseReference myRef;
    DatabaseReference myRef1;
    DatabaseReference myRef2;
    TextView name_University;
    DatabaseReference myRef3;
    TextView des_University;
    ImageView circleImageView2;
    DatabaseReference mRef5;
    String nameUser;
    String thumb_image;
    Button follow;
    DatabaseReference mRef4;
    private FirebaseUser mCurrentUser;
    RecyclerView mBlogList;
    DatabaseReference databaseReference;
    DatabaseReference mDatabaseLike;
    FirebaseAuth mAuth;
    DatabaseReference databaseReferenceUSers;
    Boolean mProcessLike = false;
    DatabaseReference mUserDatabase;
    TextView displayName;
    TextView followers;
    String name;
    Button button2;
    String name1;
    String image1;
    String status1;
    String thumb_image1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.options);
        setSupportActionBar(mToolbar);


        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        mAuth = FirebaseAuth.getInstance();


        name_University = (TextView) findViewById(R.id.name_University);
        des_University = (TextView) findViewById(R.id.des_University);
        circleImageView2 = (ImageView) findViewById(R.id.circleImageView2);
        button2=(Button)findViewById(R.id.button2);
        follow = (Button) findViewById(R.id.follow);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid = mCurrentUser.getUid();
        followers = (TextView) findViewById(R.id.followers);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(name).child("Posts");
        TextView follower=(TextView)findViewById(R.id.textView3);

        mBlogList = (RecyclerView) findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mUserDatabase.keepSynced(true);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name1 = dataSnapshot.child("name").getValue().toString();
                image1 = dataSnapshot.child("image").getValue().toString();
                status1 = dataSnapshot.child("status").getValue().toString();
                thumb_image1 = dataSnapshot.child("thumb_image").getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Community.this, NewPostActivity.class);
                Intent intent1 = intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        myRef = FirebaseDatabase.getInstance().getReference().child("Communities");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DatabaseCommunities c = postSnapshot.getValue(DatabaseCommunities.class);
                    String nameUniversity = c.getName();

                    if (name.equals(nameUniversity)) {
                        String descriptionUniversity = c.getDescription();
                        name_University.setText(nameUniversity);
                        des_University.setText(descriptionUniversity);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://futureguide-4a99e.appspot.com/").
                child("UniversitiesImages/" + name + "/uni.png");
        try {
            final File localFile = File.createTempFile("images", "png");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    //circleImageView2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    circleImageView2.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        } catch (IOException e) {
        }
        mRef4 = FirebaseDatabase.getInstance().getReference().child("Followers").child(name);
        mRef4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String id = ds.child("ID").getValue(String.class);

                    if (current_uid.equals(id)) {
                        follow.setVisibility(View.INVISIBLE);
                        button2.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        myRef2 = FirebaseDatabase.getInstance().getReference().child("Followers").child(name).child(current_uid);
        final DatabaseReference database=FirebaseDatabase.getInstance().getReference().child("JoinUniversities").child(current_uid).child(name);

            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    myRef2.child("name").setValue(name1);
                    myRef2.child("image").setValue(image1);
                    myRef2.child("status").setValue(status1);
                    myRef2.child("thumb_image").setValue(thumb_image1);
                    myRef2.child("ID").setValue(current_uid);

                    database.child("name").setValue(name);
                    follow.setVisibility(View.INVISIBLE);
                    button2.setVisibility(View.VISIBLE);
                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference myData=FirebaseDatabase.getInstance().getReference().child("Followers");
                    myData.child(name).child(current_uid).removeValue();
                    DatabaseReference myData1=FirebaseDatabase.getInstance().getReference().child("JoinUniversities");
                    myData1.child(current_uid).child(name).removeValue();
                    button2.setVisibility(View.INVISIBLE);
                    follow.setVisibility(View.VISIBLE);
                }
            });


        mRef5 = FirebaseDatabase.getInstance().getReference().child("Followers").child(name);
        mRef5.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                followers.setText(count + "");
            }
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}


