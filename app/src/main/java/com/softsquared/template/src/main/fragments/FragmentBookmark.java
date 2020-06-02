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
import com.softsquared.template.src.BaseActivity;
import com.softsquared.template.src.BaseFragment;
import com.softsquared.template.src.main.PreferenceManager;
import com.softsquared.template.src.main.activities.MainNavigationActivity;
import com.softsquared.template.src.main.adapters.BookmarkRecyclerAdapter;
import com.softsquared.template.src.main.interfaces.OnBackPressedListener;

import java.util.ArrayList;

public class FragmentBookmark extends BaseFragment implements OnBackPressedListener {
    public FragmentBookmark(MainNavigationActivity mainNavigationActivity) {
        this.mainNavigationActivity = mainNavigationActivity;
    }

    MainNavigationActivity mainNavigationActivity;
    RecyclerView rv_bookmark;
    ArrayList<BaseActivity.Place> mList;
    Context mContext;
    ImageButton mIbtn_back_from_bookmark;
    BottomNavigationView bottomNavigationView;
    BookmarkRecyclerAdapter bookmarkRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_bookmark, container, false);
        rv_bookmark = rootView.findViewById(R.id.rv_bookmark_list);
        mIbtn_back_from_bookmark = rootView.findViewById(R.id.ibtn_back_from_bookmark);
        mList = PreferenceManager.getBookMarkList(mContext);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv_bookmark.setLayoutManager(manager);
        bookmarkRecyclerAdapter = new BookmarkRecyclerAdapter(mContext, mList);
        rv_bookmark.setAdapter(bookmarkRecyclerAdapter);

        bottomNavigationView = mainNavigationActivity.bottomNavigationView;
        mIbtn_back_from_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.bni_home);
            }
        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mainNavigationActivity.setOnBackPressedListener(this);
        Log.e("FragmentBookmark", "resumed");
        Log.e("ListSize in frag be:", mList.size() + "");
        mList = PreferenceManager.getBookMarkList(mContext);
        Log.e("ListSize in frag af:", mList.size() + "");
        bookmarkRecyclerAdapter = new BookmarkRecyclerAdapter(mContext, mList);

        bookmarkRecyclerAdapter.notifyDataSetChanged();
        rv_bookmark.setAdapter(bookmarkRecyclerAdapter);
    }

    @Override
    public void onBackPressed() {
        bottomNavigationView.setSelectedItemId(R.id.bni_home);
    }
}
