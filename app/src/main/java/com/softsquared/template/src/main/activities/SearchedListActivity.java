package com.softsquared.template.src.main.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softsquared.template.R;
import com.softsquared.template.src.main.adapters.RecyclerPlaceListItemAdapter;
import com.softsquared.template.src.main.dialogs.SortDialog;
import com.softsquared.template.src.main.models.SearchResult;

import java.util.ArrayList;

public class SearchedListActivity extends AppCompatActivity {

    RecyclerView mRvPlaceList;
    LinearLayout mll_choose_sort;
    ImageButton mIbtn_searched_to_main;
    String cat_name = "";
    int searchFlag = -1;
    TextView mTv_cat_title, mOrder_principle;
    SortDialog mSortDialog;
    public Context mContext = this;
    ArrayList<SearchResult> mList = new ArrayList<>();
    ArrayList<SearchResult> mList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_list);
        searchFlag = -1;
        mll_choose_sort = findViewById(R.id.ll_choose_sort);
        mOrder_principle = findViewById(R.id.order_principle);
        Intent intent = getIntent();

        cat_name = intent.getExtras().getString("cat");
        searchFlag = intent.getExtras().getInt("flag");

        if (searchFlag == 1) {
            mList = (ArrayList<SearchResult>) intent.getSerializableExtra("searched_list");
            mList2 = (ArrayList<SearchResult>) intent.getSerializableExtra("searched_list2");
        } else if (searchFlag == 0) {
            mList = (ArrayList<SearchResult>) intent.getSerializableExtra("searched_list");
            mList2 = (ArrayList<SearchResult>) intent.getSerializableExtra("searched_list2");
        }

        mTv_cat_title = findViewById(R.id.tv_cat_title);
        mTv_cat_title.setText(cat_name);
        mRvPlaceList = findViewById(R.id.rv_place_list);
        mIbtn_searched_to_main = findViewById(R.id.ibtn_searched_to_main);
        mIbtn_searched_to_main.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvPlaceList.setLayoutManager(manager);
        mRvPlaceList.setAdapter(new RecyclerPlaceListItemAdapter(getApplicationContext(), mList));

        mll_choose_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSortDialog = new SortDialog(mContext, R.style.CustomDialog,
                        new SortDialog.ICustomDialogEventListener() {
                            @Override
                            public void customDialogEvent(int valueYouWantToSendBackToTheActivity) {
                                if (valueYouWantToSendBackToTheActivity == 1) {
                                    mRvPlaceList.setAdapter(new RecyclerPlaceListItemAdapter(getApplicationContext(), mList));
                                    mOrder_principle.setText("거리순");
                                } else if (valueYouWantToSendBackToTheActivity == 0) {
                                    mRvPlaceList.setAdapter(new RecyclerPlaceListItemAdapter(getApplicationContext(), mList2));
                                    mOrder_principle.setText("관련도순");
                                } else {
                                }
                            }
                        });
                mSortDialog.show();
            }
        });


        mRvPlaceList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();
                if (lastPosition == totalCount) {
                }
            }
        });

    }
}
