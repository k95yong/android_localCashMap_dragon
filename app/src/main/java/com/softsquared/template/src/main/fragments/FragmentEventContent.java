package com.softsquared.template.src.main.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.softsquared.template.R;
import com.softsquared.template.src.BaseFragment;
import com.softsquared.template.src.main.MainService;
import com.softsquared.template.src.main.activities.MainNavigationActivity;
import com.softsquared.template.src.main.interfaces.MainActivityView;
import com.softsquared.template.src.main.interfaces.OnBackPressedListener;
import com.softsquared.template.src.main.models.CategorySearchResponse;
import com.softsquared.template.src.main.models.EventContentResponse;
import com.softsquared.template.src.main.models.EventResponse;
import com.softsquared.template.src.main.models.NoticeContentResponse;
import com.softsquared.template.src.main.models.NoticeResponse;
import com.softsquared.template.src.main.models.StoreSearchResponse;

public class FragmentEventContent extends BaseFragment implements MainActivityView , OnBackPressedListener {
    MainNavigationActivity mainNavigationActivity;
    int content_no;
    ViewGroup rootView;
    ImageButton mIbtn_back_from_event_content;
    BottomNavigationView bottomNavigationView;
    FragmentEventContent fragmentEventContent = this;
    LinearLayout mLl_event_image;
    int currentX, currentY;
    public FragmentEventContent(MainNavigationActivity mainNavigationActivity, int content_no) {
        this.mainNavigationActivity = mainNavigationActivity;
        this.content_no = content_no;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_event_content, container, false);
        getEventContent(content_no);
        mIbtn_back_from_event_content = rootView.findViewById(R.id.ibtn_back_from_event_content);
        mLl_event_image = rootView.findViewById(R.id.ll_eventImage);
        this.bottomNavigationView = mainNavigationActivity.bottomNavigationView;
        mIbtn_back_from_event_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.bni_event);
            }
        });

        return rootView;
    }

    void getEventContent(int no) {
        MainService mainService = new MainService(this);
        Log.e("event_no", content_no + "");
        mainService.getEventContent(no);
    }

    @Override
    public void validateSuccess(String text) {

    }

    @Override
    public void validateFailure(String message) {

    }

    @Override
    public void getStoreSearchResult(StoreSearchResponse res) {

    }

    @Override
    public void getNotice(NoticeResponse res) {

    }

    @Override
    public void getCategorySearchResult(CategorySearchResponse res) {

    }

    @Override
    public void getEvent(EventResponse res) {

    }

    @Override
    public void getNoticeContent(NoticeContentResponse res) {

    }

    @Override
    public void getEventContent(EventContentResponse res) {
        Log.e("getNoticeContentmsg", res.getMessage());
        if (res.getCode() == 100) {
            String title = res.getResult().getTitle();
            String time = res.getResult().getTime();
            String content = res.getResult().getImage();
            TextView tv_event_content_title = rootView.findViewById(R.id.tv_event_content_title);
            tv_event_content_title.setText(title);
            TextView tv_event_content_date = rootView.findViewById(R.id.tv_event_content_date);
            tv_event_content_date.setText(time);
            Log.e("contentImg", content);
            ImageView iv_event_content_contents = rootView.findViewById(R.id.iv_event_content_contents);
//            Glide.with(rootView).load(content).override(1000, 1200).into(iv_event_content_contents);
            Glide.with(rootView).load(content).into(iv_event_content_contents);
        }
    }

    @Override
    public void onBackPressed() {
        bottomNavigationView.setSelectedItemId(R.id.bni_event);
        removeContent();
    }
    public void removeContent(){
        if(mainNavigationActivity.transaction != null && fragmentEventContent != null){
            mainNavigationActivity.transaction.remove(fragmentEventContent);
            Log.e("removeContent", "event에서 실행됨");
        }
    }
}
