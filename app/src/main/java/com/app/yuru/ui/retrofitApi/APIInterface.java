package com.app.yuru.ui.retrofitApi;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface {
//    @Headers({"Authorization", "Bearer " + "101|wyQ7GkJ54ZTBye4NzTOQfV0Cma7kEh3s73e1msGx"})
    @GET("getYoutubeDetailsByLink")
    Call<LibraryResponse> getLibraryDetails();


}