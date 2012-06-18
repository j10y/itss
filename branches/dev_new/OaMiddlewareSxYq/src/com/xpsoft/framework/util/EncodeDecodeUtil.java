/*
 * 版权所有(C) 中软万维网络技术有限公司 2000-2009
 * Copyright 2000-2008 CSS WEB TECHNOLOGY CO.,LTD.
 * 创建日期 2009-9-27
 */
package com.xpsoft.framework.util;


/**
 * 双向加密解密类.
 * 包括Blowfish加密实现和简单HEX字符串实现.
 * HEX字符串实现可以和javascript代码互调.
 *                                                
 * @author <a href="mailto:congyy@cssweb.com.cn">丛洋洋</a>                             
 * @createTime   2009-9-27                        
 */

public class EncodeDecodeUtil {
        
        //      加密解密用的key.
        private static final java.security.Key SPEC_KEY = new javax.crypto.spec.SecretKeySpec("YHZQ2010@CSSWEB".getBytes(), "Blowfish");
        
        /**
         * 利用Blowfish加密字符串.
         * 加密之后的字符串为HEX数字,方便在URL中通过get方式传播.
         * 
         * @param text
         *                  要加密的文本,一般为含有中文的字符串.
         * @return  String
         *                  HEX字符串.
         */
        public static String encrypt(String text){
                if(text==null || text.length()==0){
                     return "";
                }
                try {
                        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("Blowfish");
                        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, SPEC_KEY);//初始化为加密模式
                        byte[] encrypted = cipher.doFinal(text.getBytes("UTF8"));
                        return new String(encodeHex(encrypted, true));
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
        }
        
        
        /**
         * 对应Blowfish解密字符串.
         * 
         * @param encrypted
         *                      加密之后的文本,HEX字符串.
         * @return  String
         *                      原文.
         */
        public static String decrypt(String encrypted)throws Exception{
                if(encrypted==null || encrypted.length()==0){
                        return "";
                }
                int len = encrypted.length();
                byte[] encbytes=new byte[len/2];
                for ( int i = 0,j=0; i < len; i += 2 ) {
                        encbytes[j++]=(byte)Integer.parseInt(encrypted.substring(i, i + 2),16);
                }
                javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("Blowfish");
                cipher.init(javax.crypto.Cipher.DECRYPT_MODE, SPEC_KEY);//初始化为解密模式
                return new String(cipher.doFinal(encbytes),"UTF8");
        }

       private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
       private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
       private static final char[] encodeHex(byte[] data, boolean toLowerCase) {
               char[] toDigits = toLowerCase ? DIGITS_LOWER : DIGITS_UPPER;
               int l = data.length;
               char[] out = new char[l << 1];
               for (int i = 0, j = 0; i < l; i++) {
                   out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
                   out[j++] = toDigits[0x0F & data[i]];
               }
               return out;
     }
       public static void main(String[] args) throws Exception{
               String s =encrypt("admin!@#$%^&*()_+~陈建峰");
      }
}

