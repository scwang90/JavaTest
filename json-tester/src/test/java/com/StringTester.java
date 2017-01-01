package com;

import org.junit.Test;

/**
 * StringTester
 * Created by SCWANG on 2016/6/12.
 */
public class StringTester {

    @Test
    public void subString() {

        String result = "123,456",id = result;
        if (result.indexOf(',') >= 0) {
            id = result.substring(0, result.indexOf(','));
        }
        System.out.println(id);
    }

    @Test
    public void matches() {
        String result = "123.p5300|456";
        for (String item : result.split("\\|")) {
            System.out.println(item + "-" + item.matches("[^.]+\\.[a-zA-Z]\\w{1,3}"));
        }
    }
}
