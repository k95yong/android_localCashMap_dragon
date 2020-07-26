package com.softsquared.template.src.main.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.softsquared.template.R;
import com.softsquared.template.src.main.MainService;
import com.softsquared.template.src.main.PreferenceManager;
import com.softsquared.template.src.main.interfaces.MainActivityView;
import com.softsquared.template.src.main.models.CategorySearchResponse;
import com.softsquared.template.src.main.models.EventContentResponse;
import com.softsquared.template.src.main.models.EventResponse;
import com.softsquared.template.src.main.models.NoticeContentResponse;
import com.softsquared.template.src.main.models.NoticeResponse;
import com.softsquared.template.src.main.models.StoreSearchResponse;

public class PopDialog extends Dialog implements MainActivityView {
    Button mBtn_close, mBtn_not_open_today;
    private String mStrSDFormatDay;
    private Context context;
    private SharedPreferences SPreferences;
    ImageView mIv_event_content;
    MainService mainService;

    public PopDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_dialog);
        mBtn_close = findViewById(R.id.btn_close);
        mainService = new MainService(this);
        mIv_event_content = findViewById(R.id.iv_event_content);
        mBtn_not_open_today = findViewById(R.id.btn_not_open_today);

        mBtn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBtn_not_open_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.setBoolean(context, "pop_up_flag", false);
                dismiss();
            }
        });
        mainService.getEventContent(1);

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
        if (res.getCode() == 100) {

            String content = res.getResult().getImage();
//            Glide.with(context).load(content).override(800, 800).into(mIv_event_content);
            Glide.with(context).load(content).into(mIv_event_content);
        }
    }
}
