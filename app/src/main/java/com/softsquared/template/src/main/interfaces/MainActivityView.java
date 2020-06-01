package com.softsquared.template.src.main.interfaces;

import com.softsquared.template.src.main.models.EventContentResponse;
import com.softsquared.template.src.main.models.EventResponse;
import com.softsquared.template.src.main.models.NoticeContentResponse;
import com.softsquared.template.src.main.models.NoticeResponse;
import com.softsquared.template.src.main.models.StoreSearchResponse;
import com.softsquared.template.src.main.models.CategorySearchResponse;

import java.util.ArrayList;

public interface MainActivityView {

    void validateSuccess(String text);

    void validateFailure(String message);

    void getStoreSearchResult(StoreSearchResponse res);

    void getNotice(NoticeResponse res);

    void getCategorySearchResult(CategorySearchResponse res);

    void getEvent(EventResponse res);

    void getNoticeContent(NoticeContentResponse res);

    void getEventContent(EventContentResponse res);
}

