package com.example.planned;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        viewPager = findViewById(R.id.fragment_container);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setUpAdapter(viewPager);

        ActionBar aBar;
        aBar= getSupportActionBar();
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#FF03DAC5"));
        assert aBar != null;
        aBar.setBackgroundDrawable(cd);

    }

    private void setUpAdapter (ViewPager viewPager){
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPageAdapter.addFragment(new HomeFragment());
        viewPageAdapter.addFragment(new CalendarFragment());
        viewPageAdapter.addFragment(new CategoriesFragment());
        viewPageAdapter.addFragment(new HelpFragment());
        viewPager.setAdapter(viewPageAdapter);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.d("DEBUG", "Item clicked"+item.getItemId());
            switch (item.getItemId()){
                case R.id.nav_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.nav_calendar:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.nav_categories:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.nav_help:
                    viewPager.setCurrentItem(3);
                    return true;
                default:
                    return false;
            }
        }
    };

    public void onAddClick(View view){
        Intent intent = new Intent(this, ActivityDetails.class);
        startActivity(intent);
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "plannEDReminderChannel";
            String description = "Channel for plannED reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyplannED", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}