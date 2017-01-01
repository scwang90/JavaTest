package com;

import com.jsontester.util.AfJsoner;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * ListTester
 * Created by SCWANG on 2016/6/3.
 */
public class ListTester {

    @Test
    public void add() {
        Stack<Object> list = new Stack<>();
        list.push("1");
        list.push("2");
        list.push("3");
        System.out.println(AfJsoner.toJson(list));
    }

}
