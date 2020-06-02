package com.softsquared.template.src.main.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.softsquared.template.R;
import com.softsquared.template.src.BaseFragment;
import com.softsquared.template.src.main.MainService;
import com.softsquared.template.src.main.activities.MainNavigationActivity;
import com.softsquared.template.src.main.interfaces.MainActivityView;
import com.softsquared.template.src.main.models.CategorySearchResponse;
import com.softsquared.template.src.main.models.EventContentResponse;
import com.softsquared.template.src.main.models.EventResponse;
import com.softsquared.template.src.main.models.NoticeContentResponse;
import com.softsquared.template.src.main.models.NoticeResponse;
import com.softsquared.template.src.main.models.StoreSearchResponse;

public class FragmentNoticeContent extends BaseFragment implements MainActivityView {
    MainNavigationActivity mainNavigationActivity;
    int content_no;
    ViewGroup rootView;
    ImageButton mIbtn_back_from_notice_content;
    BottomNavigationView bottomNavigationView;

    public FragmentNoticeContent(MainNavigationActivity mainNavigationActivity, int content_no) {
        this.mainNavigationActivity = mainNavigationActivity;
        this.content_no = content_no;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_notification_content, container, false);
        getNoticeContent(content_no);
        mIbtn_back_from_notice_content = rootView.findViewById(R.id.ibtn_back_from_notice_content);
        this.bottomNavigationView = mainNavigationActivity.bottomNavigationView;
        mIbtn_back_from_notice_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.bni_notice);
            }
        });

        return rootView;
    }

    void getNoticeContent(int no) {
        MainService mainService = new MainService(this);
        Log.e("notice_no", content_no + "");
        mainService.getNoticeContent(no);
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
        Log.e("getNoticeContentmsg", res.getMessage());
        if (res.getCode() == 100) {
            String title = res.getResult().getTitle();
            String time = res.getResult().getTime();
            String content = res.getResult().getContent();
            TextView tv_notice_content_title = rootView.findViewById(R.id.tv_notice_content_title);
            tv_notice_content_title.setText(title);
            TextView tv_notice_content_date = rootView.findViewById(R.id.tv_notice_content_date);
            tv_notice_content_date.setText(time);
            TextView notice_content_contents = rootView.findViewById(R.id.notice_content_contents);
            notice_content_contents.setText(content);
        }
    }

    @Override
    public void getEventContent(EventContentResponse res) {

    }
}
