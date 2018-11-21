package com.mutahir.futureguide.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;


import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.mutahir.futureguide.JoinedCommunities;
import com.mutahir.futureguide.MainActivity;
import com.mutahir.futureguide.MapsActivity;
import com.mutahir.futureguide.R;
import com.mutahir.futureguide.SettingsActivity;
import com.mutahir.futureguide.UsersActivity;

/**
 * Created by Zubair on 11/25/2017.
 */

public class BottomNavigationViewHelper {

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        //Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.mainActivity:
                        Intent intent1 = new Intent(context, MainActivity.class);//ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        break;

                    case R.id.maps:
                        Intent intent2  = new Intent(context, MapsActivity.class);//ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        break;

                    case R.id.joinedCommunities:
                        Intent intent3 = new Intent(context, JoinedCommunities.class);//ACTIVITY_NUM = 2
                        context.startActivity(intent3);
                        break;

                    case R.id.allUsers:
                        Intent intent4 = new Intent(context, UsersActivity.class);//ACTIVITY_NUM = 3
                        context.startActivity(intent4);
                        break;

                    case R.id.profile:
                        Intent intent5 = new Intent(context, SettingsActivity.class);//ACTIVITY_NUM = 4
                        context.startActivity(intent5);
                        break;
                }


                return false;
            }
        });
    }
}
