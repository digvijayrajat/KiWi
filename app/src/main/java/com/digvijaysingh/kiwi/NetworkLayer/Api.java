package com.digvijaysingh.kiwi.NetworkLayer;

import com.digvijaysingh.kiwi.pojo.PojoSearch;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {


    @GET("api.php")
    Call<PojoSearch> getSearchResults(@Query("action") String action
            , @Query("format") String format
            , @Query("prop") String prop
            , @Query("generator") String generator
            , @Query("redirects") String redirects
            , @Query("formatversion") String formatversion
            , @Query("piprop") String piprop
            , @Query("pithumbsize") String pithumbsize
            , @Query("pilimit") String pilimit
            , @Query("wbptterms") String wbptterms
            , @Query("gpssearch") String gpssearch
            , @Query("gpslimit") String gpslimit
            , @Query("gpsoffset") String gpsoffset);


    /*https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&&pageids=10730502*/

    @GET("api.php")
    Call<ResponseBody> getDetails(@Query("action") String action
            , @Query("format") String format
            , @Query("prop") String prop
            , @Query("exintro") String exintro
            , @Query("pageids") String pageids
            , @Query("explaintext") String explaintext);
}