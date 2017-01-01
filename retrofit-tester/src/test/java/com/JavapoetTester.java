package com;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import groovy.transform.ToString;
import org.junit.Test;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by SCWANG on 2016/8/16.
 */
public class JavapoetTester {


    enum TestEnum{
        aa{
            @Override
            public String toString() {
                return "1-"+super.toString();
            }
        },
        bb{
            @Override
            public String toString() {
                return "2-"+super.toString();
            }
        }
    }

    @Test
    public void testEnum() throws IOException {
        System.out.println(TestEnum.aa);
        System.out.println(TestEnum.bb);
    }


    @Test
    public void test() throws IOException {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                .build();

        javaFile.writeTo(System.out);
    }

    @Test
    public void testChinese() {
        System.out.println("00000".matches(".*[\\u4e00-\\u9fa5]+.*"));
        System.out.println("000你好000".matches(".*[\\u4e00-\\u9fa5]+.*"));
        System.out.println("000你好00你好0".matches(".*[\\u4e00-\\u9fa5]+.*"));
    }

    @Test
    public void testMatches() {
        String digitNumStr = "11A11、22A22、33A33、44B44、55B55";
        //String digitNumStr = "11A11";
        Pattern digitNumP = Pattern.compile("(?<twoDigit>\\d{2})[A-Z]\\k<twoDigit>");
        Matcher foundDigitNum = digitNumP.matcher(digitNumStr);

        // Find all matches
        while (foundDigitNum.find()) {
            // Get the matching string
            String digitNumList = foundDigitNum.group();
            System.out.println(digitNumList);
        }
    }

}
