package com.common;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

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

    @Test
    public void testTime() {
        System.out.println(System.currentTimeMillis());
    }

    public boolean isChinesePunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        System.out.println(ub);
        return ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                || ub == Character.UnicodeBlock.VERTICAL_FORMS;
    }

    public boolean hasChinesePunctuation(String str) {
        for (char c : str.toCharArray()) {
            if (isChinesePunctuation(c)) {
                return true;
            }
        }
        return false;
    }
    //使用UnicodeBlock方法判断
    public boolean isChineseBlock(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT) {
            System.out.println(ub);
            return true;
        } else {
            return false;
        }
    }
    public boolean hasChineseBlock(String str) {
        for (char c : str.toCharArray()) {
            if (isChinesePunctuation(c)) {
                return true;
            }
        }
        return false;
    }
    @Test
    public void testChar() {
        String value = "，";
        System.out.println(hasChinesePunctuation(value));
        System.out.println(hasChineseBlock(value));
    }
    @Test
    public void testChars() {
        Set<Character.UnicodeBlock> set = new HashSet<>();
        String value = "!@#$%^&*()_+-={}[];'\\:\"|<>?,./`~" +
                "！@#￥%……&*（）——+=-0987654321【】{}；‘、：“|，。/《》？·~";
        for (char c : value.toCharArray()) {
            System.out.printf("%c - %s\n", c, Character.UnicodeBlock.of(c));
            set.add(Character.UnicodeBlock.of(c));
        }
        for (Character.UnicodeBlock block : set) {
            System.out.println("Character.UnicodeBlock." + block);
        }
    }
}
