package com.retrofit.api

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.GsonConverterFactory
import retrofit2.Response
import retrofit2.Retrofit

import java.lang.annotation.Annotation
import java.lang.reflect.Type

/**
 * TestCloudServicesCsApiTest
 * Created by SCWANG on 2016/8/2.
 */
class TestCloudServicesCsApiTest extends GroovyTestCase {

    TestCloudServicesCsApi service;

    @Override
    protected void setUp() throws Exception {
        super.setUp()
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.207:8090/")
                .addCallAdapterFactory( { Type returnType, Annotation[] annotations,
                                          Retrofit retrofit ->
                        return new CallAdapter<Object>() {
                            @Override
                            Type responseType() {
                                return returnType
                            }
                            @Override
                            def <R> Object adapt(Call<R> call) {
                                return call.execute().body()
                            }
                        }
                })
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        service = retrofit.create(TestCloudServicesCsApi.class);
    }

    void testSubmitContract() {
        Map<String, Object> map = new HashMap<>()
        map.put("TestOrgManageNo", "123")
        map.put("ServiceId", "123")
        Call<List<HashMap>> contract = service.SubmitContract(Collections.singletonMap("Param",map));
        Response<List<HashMap>> repos = contract.execute();
        println new Gson().toJson(repos.body());
    }

    void testGetIpInfo() {
        //
        def info = service.getIpInfo("202.202.33.33");
        println info;
    }

    void testMap() {
        Map<String, Object> map = new HashMap<>()
        map.put("name","scwang")
        map.put("sex","")
        println new Gson().toJson(map)
    }
}
