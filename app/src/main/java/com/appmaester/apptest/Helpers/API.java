package com.appmaester.apptest.Helpers;

import com.appmaester.apptest.Model.AnikPost;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Nik on 4/23/2018.
 */

public interface API {
    @Headers("Cache-Control: no-cache")
    @GET("posts")
    Call<AnikPost> getPosts(
            @Query("key") String key,
            @Query("pageToken") String token
    );
}
