package com.dxs.stc.mvp.api;

import com.dxs.stc.mvp.bean.Movie;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * created by hl at 2018/5/14
 * com.naple.hldemo.mvp.api
 */
public interface BookApi {

    @GET("v2/movie/top250")
    Observable<Movie> getTopMovie(@Query("start") int start, @Query("count") int count);
}
