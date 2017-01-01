package com;

import com.jsontester.util.AfMD5;
import org.junit.Test;

/**
 * Md5Tester
 * Created by SCWANG on 2016/6/2.
 */
public class Md5Tester {

    @Test
    public void md5() {
        System.out.println(AfMD5.getMD5("000000"));;
    }

}
