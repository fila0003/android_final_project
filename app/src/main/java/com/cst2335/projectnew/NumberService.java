package com.cst2335.projectnew;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NumberService {

    @GET()
    Call<String> getStringResponse(@Url String url);
}
