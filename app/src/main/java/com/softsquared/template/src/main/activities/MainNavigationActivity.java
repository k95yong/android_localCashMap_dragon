package com.softsquared.template.src.main.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.softsquared.template.R;
import com.softsquared.template.src.BaseActivity;
import com.softsquared.template.src.BaseFragment;
import com.softsquared.template.src.main.fragments.FragmentBookmark;
import com.softsquared.template.src.main.fragments.FragmentEvent;
import com.softsquared.template.src.main.fragments.FragmentHome;
import com.softsquared.template.src.main.fragments.FragmentNotice;

public class MainNavigationActivity extends BaseActivity {
    public FragmentManager fragmentManager = getSupportFragmentManager();
    public FragmentBookmark fragmentBookmark;
    public FragmentEvent fragmentEvent;
    public FragmentNotice fragmentNotice;
    public FragmentHome fragmentHome;
    public FragmentTransaction transaction;
    public FrameLayout frameLayout;
    public BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);
        frameLayout = findViewById(R.id.main_frame_layout);
        transaction = fragmentManager.beginTransaction();
        fragmentBookmark = new FragmentBookmark(this);
        fragmentEvent = new FragmentEvent(this);
        fragmentNotice = new FragmentNotice(this);
        fragmentHome = new FragmentHome();
        bottomNavigationView = findViewById(R.id.bn_bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        bottomNavigationView.setSelectedItemId(R.id.bni_home);
    }
    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            transaction = fragmentManager.beginTransaction();
            switch(menuItem.getItemId())
            {
                case R.id.bni_bookmark:
                    transaction.replace(R.id.main_frame_layout, fragmentBookmark).commitAllowingStateLoss();
                    break;
                case R.id.bni_event:
                    transaction.replace(R.id.main_frame_layout, fragmentEvent).commitAllowingStateLoss();
                    break;
                case R.id.bni_notice:
                    transaction.replace(R.id.main_frame_layout, fragmentNotice).commitAllowingStateLoss();
                    break;
                case R.id.bni_home:
                    transaction.replace(R.id.main_frame_layout, fragmentHome).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}
