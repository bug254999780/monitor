package com.telecom.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Util {

	public static String encrypt(String str) {
        String result = "";
        str = str+"telecom";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();//32
        } catch (NoSuchAlgorithmException e) {
        }
        return result;
    }
	
	public static String encrypt16(String str, int bit) {
        String result = "";
        str = str+"telecom";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
        }
        return result;
    }
	
	public static void main(String[] args) {
		System.out.println(encrypt(""));
	}
	
}
