package com.retrofit.api;

import com.retrofit.model.HttpResult;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.List;

/**
 * GitHubService
 * Created by SCWANG on 2016/8/2.
 */
public interface GitHubService {

    @GET("users/{user}/repos")
    Call<List<HashMap>> listRepos(@Path("user") String user);

    @GET("users/{user}/repos")
    List<HashMap> listReposEx(@Path("user") String user);

    @POST("http://192.168.1.207:8080/api/android.ashx?$c=Profession&$m=GetAll$OPEN")
    Response<HttpResult<List<HashMap>>> getAllProfession();

    @POST("http://192.168.1.207:8080/api/android.ashx?$c=Profession&$m=GetAll$OPEN")
    HttpResult<List<HashMap>> getAllProfessionEx();

    @POST("http://192.168.1.207:8080/api/ios.ashx?$c=Job&$m=Publish")
    HttpResult<String> publishJob();

    @FormUrlEncoded
    @POST("?$c=User&$m=Login$OPEN")
    HttpResult<HashMap> userLogin(@Field("username") String username, @Field("password") String password);
}
