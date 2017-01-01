package com.common;

import org.junit.Test;

/**
 *
 * Created by SCWANG on 2016/11/29.
 */
@SuppressWarnings("unused")
public class Base64Tester {

    public static final String varbase64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    @Test
    public void Base64() throws Exception {
        String pw = "OdKSpLKxgRaYGq==";
        String templete = "ImFBYkJjQ2REZUVmRmdHaEhpSWpKa0tsTG1Nbk5vT3BQcVFyUnNTdFR1VXZWd1d4WHlZelo5ODc2NTQzMjEwI0Ai";

        System.out.println(encodeBase64(varbase64EncodeChars.getBytes("utf-16")));
//
        templete = decodeBase64ToUTF8(templete).substring(1, 65);


        String utf8 = encodeBase64UTF8(pw, templete);
        System.out.println(utf8);

        pw = decodeBase64ToUTF8(utf8, templete);
        System.out.println(pw);
    }

    @Test
    public void decodeBase64() throws Exception {
        String pw = "OdKSpLKxgRaYGq==";
        String templete = "ImFBYkJjQ2REZUVmRmdHaEhpSWpKa0tsTG1Nbk5vT3BQcVFyUnNTdFR1VXZWd1d4WHlZelo5ODc2NTQzMjEwI0Ai";
        System.out.println(encodeBase64(varbase64EncodeChars.getBytes("utf-16")));
        templete = decodeBase64ToUTF8(templete).substring(1, 65);

        pw = decodeBase64ToUTF8(pw, templete);
        System.out.println(pw);
    }

    String decodeBase64ToUTF8(String password) throws Exception {
        return new String(decodeBase64(password, varbase64EncodeChars),"utf8");
    }
    String decodeBase64ToUTF8(String password, String templete) throws Exception {
        return new String(decodeBase64(password, templete),"utf8");
    }

    byte[] decodeBase64(String password) throws Exception {
        return decodeBase64(password, varbase64EncodeChars);
    }

    byte[] decodeBase64(String password, String templete) throws Exception {
        checkTemplete(templete);
        checkPassword(password);

        byte[] pbytes = password.getBytes("ASCII");
        byte[] tbytes = templete.getBytes("ASCII");

        byte[] codes = new byte[pbytes.length];
        for (int i = 0; i < pbytes.length; i++) {
            if (pbytes[i] == '=') {
                pbytes[i] = 0;
            } else {
                for (int j = 0; j < tbytes.length; j++) {
                    if (pbytes[i] == tbytes[j]) {
                        codes[i] = (byte) j;
                        break;
                    }
                }
            }
        }

        byte[] targets = new byte[3* codes.length / 4];
        for (int i = 0; i < codes.length ; i++) {
            if (i % 4 == 0) {
                targets[3*i / 4] = (byte) (((codes[i] &  Integer.valueOf("00111111", 2)) << 2) | ((codes[i + 1] & Integer.valueOf("00110000", 2)) >> 4));
                targets[3*i / 4 + 1] = (byte) (((codes[i + 1] & Integer.valueOf("00001111", 2)) << 4) | ((codes[i + 2] & Integer.valueOf("00111100", 2)) >> 2));
                targets[3*i / 4 + 2] = (byte) (((codes[i + 2] & Integer.valueOf("00000011", 2)) << 6) | ((codes[i + 3] & Integer.valueOf("00111111", 2))));
            }
        }

        return targets;
    }

    String encodeBase64UTF8(String password) throws Exception {
        return encodeBase64(password.getBytes("UTF-8"), varbase64EncodeChars);
    }

    String encodeBase64UTF8(String password, String templete) throws Exception {
        return encodeBase64(password.getBytes("UTF-8"), templete);
    }

    String encodeBase64(byte[] password) throws Exception {
        return encodeBase64(password, varbase64EncodeChars);
    }

    String encodeBase64(byte[] password, String templete) throws Exception {
        checkTemplete(templete);

        int oldlength = password.length;
        if (password.length % 3 > 0) {
            byte[] bytes = new byte[password.length + (3 - password.length % 3)];
            System.arraycopy(password, 0, bytes, 0, password.length);
            password = bytes;
        }


        byte[] targets = new byte[4 * password.length / 3];

        for (int i = 0; i < password.length; i++) {
            if (i % 3 == 0) {
                targets[4 * i / 3] = (byte) (((password[i]&0b11111100)>>2));
                targets[4 * i / 3 + 1] = (byte) (((password[i]&0b00000011)<<4)|((password[i+1]&0b11110000)>>4));
                targets[4 * i / 3 + 2] = (byte) (((password[i+1]&0b00001111)<<2)|((password[i+2]&0b11000000)>>6));
                targets[4 * i / 3 + 3] = (byte) (((password[i+2]&0b00111111)));
            }
        }

        byte[] tbytes = templete.getBytes("ASCII");
        for (int i = 0; i < targets.length; i++) {
            if (i <= oldlength * 4 / 3) {
                targets[i] = tbytes[targets[i]];
            } else {
                targets[i] = '=';
            }
        }

        return new String(targets,"ASCII");
    }

    private void checkPassword(String password) throws Exception {
        if (password == null || password.length() % 4 != 0) {
            throw new Exception("password不是由Base64加密");
        }
    }

    private void checkTemplete(String templete) throws Exception {
        if (templete == null || templete.length() != 64) {
            throw new Exception("Base64模版不合法");
        }
    }
}
