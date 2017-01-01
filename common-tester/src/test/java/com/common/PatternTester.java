package com.common;

import org.junit.Test;

import java.nio.charset.Charset;
import java.util.regex.Pattern;

/**
 * 正在表达式验证
 * Created by SCWANG on 2016/12/2.
 */
public class PatternTester {

    @Test
    public void name(){
        String text = "一二三四五六七11";
        Pattern numex = Pattern.compile("\\d");
        if (numex.matcher(text).find()) {
            System.out.println("名称中不能有数字");
        }
        boolean hasch = Pattern.compile("\\d").matcher(text).find();
        boolean hasen = Pattern.compile("[a-zA-Z]").matcher(text).find();
        if (hasch && hasen) {
            System.out.println("中文名称不能有混有英文");
        }
        System.out.println(text.getBytes(Charset.forName("gbk")).length);
        if (text.getBytes(Charset.forName("gbk")).length > 16) {
            System.out.println("名称不能超过8个汉字或16个字符");
        }
    }

}
