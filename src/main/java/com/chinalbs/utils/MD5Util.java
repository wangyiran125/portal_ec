package com.chinalbs.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 从客户拷贝的MD5加密方法为了保证和客户一样
 * 
 * @author shijun
 * 
 */
public class MD5Util {
  public static String getMd5(String inputMessage) {
    if (inputMessage == null) {
      return null;
    }
    try {
      // Create MessageDigest object for MD5
      MessageDigest digest = MessageDigest.getInstance("MD5");
      // Update input message in message digest
      digest.update(inputMessage.toUpperCase().getBytes());
      // Converts message digest value in base 16 (hex)
      byte[] byteArray = digest.digest();
      StringBuffer md5StrBuff = new StringBuffer();
      for (int i = 0; i < byteArray.length; i++) {
        if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
          md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
        else
          md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
      }
      return md5StrBuff.toString().toUpperCase();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }
}
