package com.softsquared.template.src.main.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.softsquared.template.R;
import com.softsquared.template.src.BaseActivity;
import com.softsquared.template.src.main.fragments.FragmentBookmark;
import com.softsquared.template.src.main.fragments.FragmentEvent;
import com.softsquared.template.src.main.fragments.FragmentHome;
import com.softsquared.template.src.main.fragments.FragmentNotice;
import com.softsquared.template.src.main.interfaces.OnBackPressedListener;
import com.softsquared.template.src.main.models.SearchResult;

import java.util.ArrayList;

public class MainNavigationActivity extends BaseActivity {
    public FragmentManager fragmentManager = getSupportFragmentManager();
    public FragmentBookmark fragmentBookmark;
    public FragmentEvent fragmentEvent;
    public FragmentNotice fragmentNotice;
    public FragmentHome fragmentHome;
    public FragmentTransaction transaction;
    public FrameLayout frameLayout;
    public BottomNavigationView bottomNavigationView;
    OnBackPressedListener listener;
    MainNavigationActivity mainNavigationActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);
        mainNavigationActivity = this;
        frameLayout = findViewById(R.id.main_frame_layout);
        transaction = fragmentManager.beginTransaction();
        bottomNavigationView = findViewById(R.id.bn_bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        bottomNavigationView.setSelectedItemId(R.id.bni_home);
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            transaction = fragmentManager.beginTransaction();
            switch (menuItem.getItemId()) {
                case R.id.bni_bookmark:
                    if(fragmentBookmark == null){
                        fragmentBookmark = new FragmentBookmark(mainNavigationActivity);
                        transaction.add(R.id.main_frame_layout, fragmentBookmark).commit();
                    }else{
                        transaction = fragmentManager.beginTransaction();
                        transaction.show(fragmentBookmark).commit();
                    }
                    if(fragmentEvent != null){
                        transaction = fragmentManager.beginTransaction();
                        transaction.hide(fragmentEvent).commit();
                    }
                    if(fragmentNotice != null){
                        transaction = fragmentManager.beginTransaction();
                        transaction.hide(fragmentNotice).commit();
                    }
                    if(fragmentHome != null){
                        transaction = fragmentManager.beginTransaction();
                        transaction.hide(fragmentHome).commit();
                    }
                    mainNavigationActivity.setOnBackPressedListener(fragmentBookmark);
                    break;
                case R.id.bni_event:
                    if(fragmentEvent == null){
                        fragmentEvent = new FragmentEvent(mainNavigationActivity);
                        transaction.add(R.id.main_frame_layout, fragmentEvent).commit();
                    }else{
                        transaction = fragmentManager.beginTransaction();
                        transaction.show(fragmentEvent).commit();
                    }
                    if(fragmentBookmark != null){
                        transaction = fragmentManager.beginTransaction();
                        transaction.hide(fragmentBookmark).commit();
                    }
                    if(fragmentNotice != null){
                        transaction = fragmentManager.beginTransaction();
                        transaction.hide(fragmentNotice).commit();
                    }
                    if(fragmentHome != null){
                        transaction = fragmentManager.beginTransaction();
                        transaction.hide(fragmentHome).commit();
                    }
                    mainNavigationActivity.setOnBackPressedListener(fragmentEvent);
                    break;
                case R.id.bni_notice:
                    if(fragmentNotice == null){
                        fragmentNotice = new FragmentNotice(mainNavigationActivity);
                        transaction.add(R.id.main_frame_layout, fragmentNotice).commit();
                    }else{
                        transaction = fragmentManager.beginTransaction();
                        transaction.show(fragmentNotice).commit();
                    }
                    if(fragmentBookmark != null){
                        transaction = fragmentManager.beginTransaction();
                        transaction.hide(fragmentBookmark).commit();
                    }
                    if(fragmentEvent != null){
                        transaction = fragmentManager.beginTransaction();
                        transaction.hide(fragmentEvent).commit();
                    }
                    if(fragmentHome != null){
                        transaction = fragmentManager.beginTransaction();
                        transaction.hide(fragmentHome).commit();
                    }
                    mainNavigationActivity.setOnBackPressedListener(fragmentNotice);
                    break;
                case R.id.bni_home:
                    if(fragmentHome == null){
                        fragmentHome = new FragmentHome();
                        transaction.add(R.id.main_frame_layout, fragmentHome).commit();
                    }else{
                        transaction = fragmentManager.beginTransaction();
                        transaction.show(fragmentHome).commit();
                    }
                    if(fragmentBookmark != null){
                        transaction = fragmentManager.beginTransaction();
                        transaction.hide(fragmentBookmark).commit();
                    }
                    if(fragmentEvent != null){
                        transaction = fragmentManager.beginTransaction();
                        transaction.hide(fragmentEvent).commit();
                    }
                    if(fragmentNotice != null){
                        transaction = fragmentManager.beginTransaction();
                        transaction.hide(fragmentNotice).commit();
                    }
                    mainNavigationActivity.setOnBackPressedListener(fragmentHome);
                    break;
            }
            return true;
        }
    }


    public void setOnBackPressedListener(OnBackPressedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBackPressed() {
        if (listener != null) {
            listener.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }


}
