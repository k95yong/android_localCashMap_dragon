package com.softsquared.template.src.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import com.softsquared.template.R;
import com.softsquared.template.src.BaseActivity;
import com.softsquared.template.src.main.adapters.GridCategoryAdapter;
import com.softsquared.template.src.main.fragments.FragmentHome;
import com.softsquared.template.src.main.interfaces.MainActivityView;
import com.softsquared.template.src.main.models.CategorySearchResponse;
import com.softsquared.template.src.main.models.EventContentResponse;
import com.softsquared.template.src.main.models.EventResponse;
import com.softsquared.template.src.main.models.NoticeContentResponse;
import com.softsquared.template.src.main.models.NoticeResponse;
import com.softsquared.template.src.main.models.StoreSearchResponse;

public class CategoryActivity extends BaseActivity implements MainActivityView {
    ImageButton ibtn_cancel;
    String mSelectedCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ibtn_cancel = findViewById(R.id.ibtn_cat_cancel);
        int img[] = {
                R.drawable.tools, R.drawable.bread, R.drawable.healthcare_and_medical, R.drawable.buildings,
                R.drawable.combs, R.drawable.clothes, R.drawable.hospital, R.drawable.gestures,
                R.drawable.guard, R.drawable.art_plate, R.drawable.gasstation, R.drawable.marketing,
                R.drawable.outlines, R.drawable.three, R.drawable.vintage, R.drawable.transportation,
                R.drawable.communications, R.drawable.travelling, R.drawable.golfer, R.drawable.rainbow,
                R.drawable.beverage, R.drawable.clean, R.drawable.house_life, R.drawable.pencels
        };

        ibtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GridCategoryAdapter adapter = new GridCategoryAdapter(
                getApplicationContext(),
                R.layout.grid_category_item,
                img, str);
        GridView gridView = findViewById(R.id.gv_category);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cur_cat = str[position];
                Intent intent = new Intent(getApplicationContext(), FragmentHome.class);
                intent.putExtra("cat", cur_cat);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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

    }
}
