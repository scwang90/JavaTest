package com.retrofit.api;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.Map;

/**
 * TestCloudServicesCsApi
 * Created by SCWANG on 2016/8/2.
 */
public interface TestCloudServicesCsApi {
    @POST("api/customer-service.ashx?$c=Contract&$m=SubmitContract")
    Call<Object> SubmitContract(@Body HashMap body);

    @GET("http://ip.taobao.com/service/getIpInfo.php")
    Map<String,Object> getIpInfo(@Query("ip") String ip);
}
