package com.example.tripexpense;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNav {
    public static void setNavigateMenuId(Activity activity, int id){
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(id);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Class targetNavigateTo;
                switch (item.getItemId()){
                    case R.id.expenseMainActivity2:
                        targetNavigateTo = ExpenseMainActivity.class;
                        break;
                    case R.id.mainActivity:
                        targetNavigateTo = MainActivity.class;
                        break;
                    case R.id.searchActivity:
                        targetNavigateTo = SearchActivity.class;
                        break;
                    case R.id.uploadActivity:
                        targetNavigateTo = UploadActivity.class;
                        break;
                    default:
                        return false;
                }
                Intent intent = new Intent(activity.getApplicationContext(), targetNavigateTo);
                activity.startActivity(intent);
                activity.overridePendingTransition(0,0);
                return true;
            }
        });

    }

}
