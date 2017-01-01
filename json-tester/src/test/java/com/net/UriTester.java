package com.net;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * UriTester
 * Created by SCWANG on 2016/5/24.
 */
public class UriTester {

    @Test
    public void decode() {
        String urlString = "http://192.168.21.77:8080/swp/mainPage?aa=11&bb%3D22";
        URI uri = URI.create(urlString);
        System.out.println(uri.getPath());
        System.out.println(uri.getQuery());//解码
//        URL url2 = new URL(urlString);
//        System.out.println(url2.getQuery());//不解码
    }

    @Test
    public void path() throws MalformedURLException {
        String urlString = "file://192.168.21.77:8080/swp/mainPage?aa=11&bb%3D22";
        URI uri = URI.create(urlString);
        System.out.println(uri.getPath());
        System.out.println(uri.getQuery());//解码
        URL url2 = new URL(urlString);
        System.out.println(url2.getQuery());//不解码
    }

    @Test
    public void json() throws IllegalAccessException, InstantiationException {
        Class.class.newInstance();
    }
}
