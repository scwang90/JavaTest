package com.common;

import org.junit.Test;

import java.io.*;
import java.util.Scanner;

/**
 *
 * Created by SCWANG on 2016/8/31.
 */
public class PipedStreamTester {

    @Test
    public void test() throws IOException {
        final PipedInputStream input = new PipedInputStream();
        PipedOutputStream output = new PipedOutputStream(input);
        new Thread(){
            @Override
            public void run() {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                    String line;
                    System.out.println("run-start;");
                    while ((line = reader.readLine()) != null) {
                        System.out.print("run:");
                        System.out.println(line);
                    }
                    System.out.println("run-end;");
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }.start();

        Scanner in = new Scanner(System.in);
        String line;
        PrintStream print = new PrintStream(output);
        while (!(line = in.next(".*")).equals("")) {
            print.println(line+"\n");
        }
        output.close();
    }

}
