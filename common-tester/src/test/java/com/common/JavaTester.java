package com.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 *
 * Created by SCWANG on 2016/9/8.
 */
public class JavaTester {

    public interface $$ {
        <TT extends InputStream> TT view(Class<TT> clazz , int... indexs);
    }

    public void test$$(){
        $$ ss = new $$(){
            @Override
            public <TT extends InputStream> TT view(Class<TT> clazz, int... indexs) {
                return null;
            }
        };
        FileInputStream view = ss.view(FileInputStream.class);
    }

}
