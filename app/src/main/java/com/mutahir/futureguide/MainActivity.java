package com.mutahir.futureguide;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.mutahir.futureguide.Utils.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {

    ImageView chatImageView;
    private static final int ACTIVITY_NUM = 0;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private DatabaseReference mUserRef;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView uidTextView;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    Spinner mSpinner;
    DatabaseReference myRef;
    ListView mListView;
    TextView listText;
    ArrayList<String> universityNameList;
    String[] programs;
    ArrayAdapter<String> universities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        chatImageView = (ImageView)findViewById(R.id.imageView2);
        chatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplication(),"Pressed",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this,ChatMainActivity.class);
                startActivity(intent);
            }
        });


        setupBottomNavigationView();

       mToolbar = (Toolbar) findViewById(R.id.status_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Future Guide");


        listText=(TextView)findViewById(R.id.listText) ;
        universityNameList=new ArrayList<String>();
        mListView=(ListView)findViewById(R.id.mListView);
        programs=getResources().getStringArray(R.array.programs);
        Collections.sort(Arrays.asList(programs), String.CASE_INSENSITIVE_ORDER);
        mSpinner=(Spinner)findViewById(R.id.mSpinner);
        final ArrayAdapter<String> programAdp=new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_dropdown_item_1line,programs);
        mSpinner.setAdapter(programAdp);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parentView, View selectedItemView, final int position, long id) {

                final String program = parentView.getItemAtPosition(position).toString();
                if (parentView.getItemAtPosition(position).toString().equals("---Select---")) {
                    universityNameList.clear();
                    myRef= FirebaseDatabase.getInstance().getReference();
                    myRef.keepSynced(true);
                    myRef.child("Universities").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            listText.setText("All Recognized Universities Of Pakistan");
                            listText.setTextSize(15);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Electrical Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Electrical Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Software Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Software Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Chemical Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Chemical Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Civil Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Civil Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Materials Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Materials Engineering ").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Mechanical Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Mechanical Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Mechatronics Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Mechatronics Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Metallurgical Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Metallurgical Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Mining Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Mining Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Industrail Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Industrail Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Geological Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Geological Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Environmental Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Environmental Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Electronics Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Electronics Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Computer System Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Computer System Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Computer Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Computer Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Bio-Medical Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Bio-Medical Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Agricultural Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Agricultural Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Automotive Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("AUTOMOTIVE ENGINEERING").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Aerospace Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("AEROSPACE ENGINEERING").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Artitecture Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Architectural Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Petroleum and Gas Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Petroleum & Gas Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (parentView.getItemAtPosition(position).toString().equals("Telecommunication Engineering")){
                    universityNameList.clear();
                    DatabaseReference eeDatabase=FirebaseDatabase.getInstance().getReference();
                    eeDatabase.child("Programs").child("Telecommunication Engineering").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.add(uniNames);
                            universities.notifyDataSetChanged();
                            Collections.sort(universityNameList, String.CASE_INSENSITIVE_ORDER);
                            listText.setText("Universities Offering " + program);
                            listText.setTextSize(15);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String uniNames=dataSnapshot.getValue(String.class);
                            universityNameList.remove(uniNames);
                            universities.notifyDataSetChanged();
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else {
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        universities=new ArrayAdapter<String>(getApplicationContext(),R.layout.customlist,R.id.textView2,universityNameList);/*{@Override
        public View getView(int position, View convertView, ViewGroup parent){
            // Get the Item from ListView
            View view = super.getView(position, convertView, parent);

            // Initialize a TextView for ListView each Item
            TextView tv = (TextView) view.findViewById(android.R.id.text1);

            // Set the text color of TextView (ListView Item)
            tv.setTextColor(Color.BLACK);

            // Generate ListView Item using TextView
            return view;
        }
        };;*/
        mListView.setAdapter(universities);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedFromList = (String) mListView.getItemAtPosition(position);
                String name=selectedFromList;
                Intent intent=new Intent(getApplicationContext(),Community.class);
                Intent user=intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Error Loading Cuurent",Toast.LENGTH_SHORT);
        }


    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {

            sendToStart();

        }
    }


    private void sendToStart() {

        Intent startIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();

    }

    private void setupBottomNavigationView(){

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


}