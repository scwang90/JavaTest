package com.retrofit.api;


import com.google.gson.Gson;
import com.retrofit.model.HttpResult;
import okhttp3.*;
import org.junit.Before;
import org.junit.Test;
import retrofit2.*;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

/**
 * GitHubServiceTest
 * Created by SCWANG on 2016/8/2.
 */
public class GitHubServiceTest {

    GitHubService service;

    @Before
    public void setup() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder().addHeader("User-Agent", "Retrofit-Sample-App").build();
                return chain.proceed(newRequest);
            }
        };

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(3, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);
        builder.cookieJar(new CookieJar() {
            public List<Cookie> list;
            @Override
            public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                this.list = list;
            }
            @Override
            public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                return list == null ? new ArrayList<>() : list;
            }
        });
        builder.cache(new Cache(new File(".\\okhttp"), 10 * 1024 * 1024));

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
//                .baseUrl(new HttpUrl.Builder().host("192.168.1.207").port(8080).scheme("http").build())
                .baseUrl("http://192.168.1.207:8080/api/ios.ashx/")
//                .baseUrl("https://api.github.com/")
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addCallAdapterFactory(new CallAdapter.Factory() {
                    @Override
                    public CallAdapter<?> get(final Type returnType, Annotation[] annotations, Retrofit retrofit) {
                        Class<?> rawType = getRawType(returnType);
                        if (rawType != Response.class) {
                            return null;
                        }
                        return new CallAdapter<Object>() {
                            public Type responseType() {
                                return returnType;
                            }
                            public <R> Object adapt(Call<R> call) {
                                try {
                                    return call.execute();
                                } catch (IOException e) {
                                    throw new RuntimeException("call.execute", e);
                                }
                            }
                        };
                    }
                })
                .addCallAdapterFactory(new CallAdapter.Factory() {
                    @Override
                    public CallAdapter<?> get(final Type returnType, Annotation[] annotations, Retrofit retrofit) {
                        Class<?> rawType = getRawType(returnType);
                        if (rawType == Observable.class || rawType == Call.class || rawType == Response.class) {
                            return null;
                        }
                        return new CallAdapter<Object>() {
                            public Type responseType() {
                                return returnType;
                            }

                            public <R> Object adapt(Call<R> call) {
                                try {
                                    Response<R> response = call.execute();
                                    if (response.code() != 200) {
                                        throw new RuntimeException("response.code() = " + response.code());
                                    }
                                    R body = response.body();
                                    if (body instanceof HttpResult) {
                                        if (!((HttpResult) body).success) {
                                            throw new RuntimeException(((HttpResult) body).msg);
                                        }
                                    }
                                    return body;
                                } catch (IOException e) {
                                    throw new RuntimeException("call.execute", e);
                                }
                            }
                        };
                    }
                })
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GitHubService.class);
    }

    @Test
    public void testListRepos() throws IOException {
        Response<List<HashMap>> repos = service.listRepos("scwang90").execute();
        System.out.println(new Gson().toJson(repos.headers()));
        System.out.println(new Gson().toJson(repos.body()));
    }

    @Test
    public void testListReposEx() throws IOException {
        List<HashMap> repos = service.listReposEx("scwang90");
        System.out.println(new Gson().toJson(repos));
    }

    @Test
    public void testGetAllProfession() throws IOException {
        Response<HttpResult<List<HashMap>>> repos = service.getAllProfession();
        System.out.println(new Gson().toJson(repos.headers()));
        System.out.println(new Gson().toJson(repos.body()));
    }

    @Test
    public void testGetAllProfessionEx() throws IOException {
        HttpResult<List<HashMap>> repos = service.getAllProfessionEx();
        System.out.println(new Gson().toJson(repos));
    }

    @Test
    public void testPublishJob() throws IOException {
        HttpResult<String> repos = service.publishJob();
        System.out.println(new Gson().toJson(repos));
    }

    @Test
    public void testUserLogin() throws IOException {
        HttpResult<HashMap> repos = service.userLogin("admin","000000");
        System.out.println(new Gson().toJson(repos.data));
        HttpResult<String> reposEx = service.publishJob();
        System.out.println(new Gson().toJson(reposEx));
    }

}
