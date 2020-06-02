package com.softsquared.template.src.main.interfaces;

import com.softsquared.template.src.main.models.CategorySearchResponse;
import com.softsquared.template.src.main.models.DefaultResponse;
import com.softsquared.template.src.main.models.EventContentResponse;
import com.softsquared.template.src.main.models.EventResponse;
import com.softsquared.template.src.main.models.NoticeContentResponse;
import com.softsquared.template.src.main.models.NoticeResponse;
import com.softsquared.template.src.main.models.StoreSearchResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MainRetrofitInterface {
    //    @GET("/test")
    @GET("/jwt")
    Call<DefaultResponse> getTest();

    @GET("/storeSearch")
    Call<StoreSearchResponse> getStoreSearch(@Query("la") double la, @Query("lo") double lo, @Query("name") String name);

    @GET("/categorySearch")
    Call<CategorySearchResponse> getCategorySearch(@Query("la") double la, @Query("lo") double lo, @Query("category") String category);

    @GET("/test/{number}")
    Call<DefaultResponse> getTestPathAndQuery(
            @Path("number") int number,
            @Query("content") final String content
    );

    @GET("/notice")
    Call<NoticeResponse> getNotice();

    @GET("/event")
    Call<EventResponse> getEvent();

    @POST("/test")
    Call<DefaultResponse> postTest(@Body RequestBody params);

    @GET("/notice/{noticeNo}")
    Call<NoticeContentResponse> getNoticeContent(@Path("noticeNo") int noticeNo);

    @GET("/event/{eventNo}")
    Call<EventContentResponse> getEventContent(@Path("eventNo") int noticeNo);
}
