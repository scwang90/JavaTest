package com.common;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

/**
 * Jsoup
 * Created by SCWANG on 2017/3/29.
 */
public class JsoupTester {

    @Test
    public void testQiNiu() throws IOException {
        String url = "http://7xsws2.com1.z0.glb.clouddn.com/backs/list.html";
        Document doc = Jsoup.connect(url).get();
        System.out.println(doc.text());
    }
}
