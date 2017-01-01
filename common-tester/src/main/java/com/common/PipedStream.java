package com.common;

import java.io.*;
import java.util.Scanner;

/**
 *
 * Created by SCWANG on 2016/8/31.
 */
public class PipedStream {

    public static void main(String[] args) throws IOException {
        final PipedInputStream input = new PipedInputStream();
        PipedOutputStream output = new PipedOutputStream(input);


        Scanner in = new Scanner(System.in);
        String line;
        PrintStream print = new PrintStream(output);
        while (!(line = in.next(".*")).equals("0")) {
            print.println(line);
        }
        output.close();

        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("run-start;");
                    byte[] bytes = new byte[8192]; // 8k
                    for (int count; (count = input.read(bytes)) != -1; ) {
                        System.out.print("run:");
                        System.out.write(bytes, 0, count);
                    }
                    System.out.println("run-end;");
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
//                    String line;
//                    System.out.println("run-start;");
//                    while ((line = reader.readLine()) != null) {
//                        System.out.print("run:");
//                        System.out.println(line);
//                    }
//                    System.out.println("run-end;");
//                } catch (Throwable ex) {
//                    ex.printStackTrace();
//                }
            }
        }.start();
    }
}
