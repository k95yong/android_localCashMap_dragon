package com.softsquared.template.src.main;

import android.util.Log;

import com.softsquared.template.src.main.interfaces.MainActivityView;
import com.softsquared.template.src.main.interfaces.MainRetrofitInterface;
import com.softsquared.template.src.main.models.CategorySearchResponse;
import com.softsquared.template.src.main.models.DefaultResponse;
import com.softsquared.template.src.main.models.EventContentResponse;
import com.softsquared.template.src.main.models.EventResponse;
import com.softsquared.template.src.main.models.NoticeContentResponse;
import com.softsquared.template.src.main.models.NoticeResponse;
import com.softsquared.template.src.main.models.StoreSearchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softsquared.template.src.ApplicationClass.getRetrofit;

public class MainService {
    public final MainActivityView mMainActivityView;

    public MainService(final MainActivityView mainActivityView) {
        this.mMainActivityView = mainActivityView;
    }

    void getTest() {
        final MainRetrofitInterface mainRetrofitInterface = getRetrofit().create(MainRetrofitInterface.class);
        mainRetrofitInterface.getTest().enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                final DefaultResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    mMainActivityView.validateFailure(null);
                    return;
                }
                mMainActivityView.validateSuccess(defaultResponse.getMessage());
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                mMainActivityView.validateFailure(null);
            }
        });
    }

    public void getStoreSearch(double lat, double lon, String name) {
        final MainRetrofitInterface mainRetrofitInterface = getRetrofit().create(MainRetrofitInterface.class);
        mainRetrofitInterface.getStoreSearch(lat, lon, name).enqueue(new Callback<StoreSearchResponse>() {

            @Override
            public void onResponse(Call<StoreSearchResponse> call, Response<StoreSearchResponse> response) {
                StoreSearchResponse storeSearchResponse = response.body();
                if (storeSearchResponse == null) {
                    mMainActivityView.validateFailure(null);
                    return;
                }
                mMainActivityView.getStoreSearchResult(storeSearchResponse);
                if (storeSearchResponse.getCode() == 100) {
                    Log.e("storeSearchResponseMsg", storeSearchResponse.getMessage() + "");
                } else {
                    Log.e("storeSearchResponse", "code error");
                }
            }

            @Override
            public void onFailure(Call<StoreSearchResponse> call, Throwable t) {
                mMainActivityView.validateFailure(null);
                Log.e("getStoreSearch :: ", t.getLocalizedMessage());
            }
        });
    }

    public void getCategorySearch(double lat, double lon, String category) {
        Log.e("categorySearchResponse", "lat : " + lat + " ,lon : " + lon + " category : " + category);

        final MainRetrofitInterface mainRetrofitInterface = getRetrofit().create(MainRetrofitInterface.class);
        mainRetrofitInterface.getCategorySearch(lat, lon, category).enqueue(new Callback<CategorySearchResponse>() {
            public void onResponse(Call<CategorySearchResponse> call, Response<CategorySearchResponse> response) {
                CategorySearchResponse categorySearchResponse = response.body();
                if (response.isSuccessful()) {
                    if (categorySearchResponse == null) {
                        mMainActivityView.validateFailure(null);
                        return;
                    }
                    mMainActivityView.getCategorySearchResult(categorySearchResponse);
                    if (categorySearchResponse.getCode() == 100) {

                    } else {
                        Log.e("categorySearchResponse", categorySearchResponse.getMessage());
                    }
                } else {
                    Log.e("categorySearchResponse", "error : " + response.code());
                }

            }

            @Override
            public void onFailure(Call<CategorySearchResponse> call, Throwable t) {
                mMainActivityView.validateFailure(null);
                Log.e("getCategorySearch :: ", t.getLocalizedMessage());
            }
        });
    }

    public void getNotice() {
        final MainRetrofitInterface mainRetrofitInterface = getRetrofit().create(MainRetrofitInterface.class);
        mainRetrofitInterface.getNotice().enqueue(new Callback<NoticeResponse>() {
            @Override
            public void onResponse(Call<NoticeResponse> call, Response<NoticeResponse> response) {
                NoticeResponse noticeResponse = response.body();
                if (noticeResponse == null) {
                    mMainActivityView.validateFailure(null);
                    Log.e("onFailure", "notice null");
                    return;
                }
                mMainActivityView.getNotice(noticeResponse);
                Log.e("onSuccess", "notice good");
            }

            @Override
            public void onFailure(Call<NoticeResponse> call, Throwable t) {
                mMainActivityView.validateFailure(null);
                Log.e("Notice fail :: ", t.getLocalizedMessage());
            }
        });
    }

    public void getEvent() {
        final MainRetrofitInterface mainRetrofitInterface = getRetrofit().create(MainRetrofitInterface.class);
        mainRetrofitInterface.getEvent().enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                EventResponse eventResponse = response.body();
                if (eventResponse == null) {
                    mMainActivityView.validateFailure(null);
                    Log.e("onFailure", "event null");
                    return;
                }
                mMainActivityView.getEvent(eventResponse);
                Log.e("onSuccess", "event good");
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                mMainActivityView.validateFailure(null);
                Log.e("Event fail : ", t.getLocalizedMessage());
            }
        });
    }

    public void getNoticeContent(int no) {
        Log.e("gnc_notice_no", no + "");
        final MainRetrofitInterface mainRetrofitInterface = getRetrofit().create(MainRetrofitInterface.class);
        mainRetrofitInterface.getNoticeContent(no).enqueue(new Callback<NoticeContentResponse>() {
            @Override
            public void onResponse(Call<NoticeContentResponse> call, Response<NoticeContentResponse> response) {
                NoticeContentResponse noticeContentResponse = response.body();
                if (noticeContentResponse == null) {
                    mMainActivityView.validateFailure(null);
                    Log.e("onFailure", "notice content null");
                    return;
                }
                mMainActivityView.getNoticeContent(noticeContentResponse);
                Log.e("onSuccess", "notice content good");
            }

            @Override
            public void onFailure(Call<NoticeContentResponse> call, Throwable t) {
                mMainActivityView.validateFailure(null);
                Log.e("Notice content fail : ", t.getLocalizedMessage());
            }
        });
    }

    public void getEventContent(int no) {
        Log.e("gnc_notice_no", no + "");
        final MainRetrofitInterface mainRetrofitInterface = getRetrofit().create(MainRetrofitInterface.class);
        mainRetrofitInterface.getEventContent(no).enqueue(new Callback<EventContentResponse>() {
            @Override
            public void onResponse(Call<EventContentResponse> call, Response<EventContentResponse> response) {
                EventContentResponse eventContentResponse = response.body();
                if (eventContentResponse == null) {
                    mMainActivityView.validateFailure(null);
                    Log.e("onFailure", "event content null");
                    return;
                }
                mMainActivityView.getEventContent(eventContentResponse);
                Log.e("onSuccess", "event content good");
            }

            @Override
            public void onFailure(Call<EventContentResponse> call, Throwable t) {
                mMainActivityView.validateFailure(null);
                Log.e("Event content fail : ", t.getLocalizedMessage());
            }
        });
    }
}
