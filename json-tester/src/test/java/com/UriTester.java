package com;

import org.junit.Test;

import java.net.URI;

/**
 * UriTester
 * Created by SCWANG on 2016/6/13.
 */
public class UriTester {

    @Test
    public void create() {
        String service = "www.baidu.com/scwang";
        URI uri = URI.create("http://" + service);
        System.out.println(uri.getPort());
        System.out.println(uri.getHost());
    }

}
