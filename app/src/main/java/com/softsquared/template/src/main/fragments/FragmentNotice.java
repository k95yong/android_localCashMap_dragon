package com.softsquared.template.src.main.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.softsquared.template.R;
import com.softsquared.template.src.BaseFragment;
import com.softsquared.template.src.main.MainService;
import com.softsquared.template.src.main.activities.MainNavigationActivity;
import com.softsquared.template.src.main.adapters.NoticeRecyclerAdapter;
import com.softsquared.template.src.main.interfaces.MainActivityView;
import com.softsquared.template.src.main.interfaces.OnBackPressedListener;
import com.softsquared.template.src.main.models.CategorySearchResponse;
import com.softsquared.template.src.main.models.EventContentResponse;
import com.softsquared.template.src.main.models.EventResponse;
import com.softsquared.template.src.main.models.NoticeContentResponse;
import com.softsquared.template.src.main.models.NoticeResponse;
import com.softsquared.template.src.main.models.StoreSearchResponse;

import java.util.ArrayList;

public class FragmentNotice extends BaseFragment implements MainActivityView, OnBackPressedListener {
    public FragmentNotice(MainNavigationActivity mainNavigationActivity) {
        this.mainNavigationActivity = mainNavigationActivity;
    }

    RecyclerView rv_notice;
    ArrayList<NoticeResponse.Result> mList;
    Context mContext;
    MainNavigationActivity mainNavigationActivity;
    ImageButton mIbtn_back_from_notice;
    BottomNavigationView bottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mainNavigationActivity.setOnBackPressedListener(this);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_notification, container, false);
        rv_notice = rootView.findViewById(R.id.rv_notice_list);
        mIbtn_back_from_notice = rootView.findViewById(R.id.ibtn_back_from_notice);
        getNoticeList();
        this.bottomNavigationView = mainNavigationActivity.bottomNavigationView;
        mIbtn_back_from_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.bni_home);
            }
        });
        return rootView;
    }

    void getNoticeList() {
        MainService mainService = new MainService(this);
        mainService.getNotice();
    }

    @Override
    public void getNotice(NoticeResponse res) {
        if (res.getCode() == 100) {
            mList = res.getResult();
            Log.e("mList size:", mList.size() + "");
            LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            rv_notice.setLayoutManager(manager);
            NoticeRecyclerAdapter noticeRecyclerAdapter = new NoticeRecyclerAdapter(mContext, mList, mainNavigationActivity);
            rv_notice.setAdapter(noticeRecyclerAdapter);
        }
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

    }

    @Override
    public void onBackPressed() {
        bottomNavigationView.setSelectedItemId(R.id.bni_home);
    }
}
