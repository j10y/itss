package com.xpsoft.framework;

import com.xpsoft.framework.util.AES;
import com.xpsoft.framework.util.Base64;

public class TestAESBase64 {
	
	public static void main(String[] args){
		String str1 = "22122010322095322570022301022b4516099c31d4ee3aae6fc9a3cadbac583128902532998822";
	    String AES_Key = "BBFAD6D0A3ACD2BBB4AECAFDC2EBD7F7";//key钥匙
		String str2 = "";
		try{
		byte[] msgby = str1.getBytes();
		byte[] keyby = AES_Key.getBytes("ASCII");
		byte[] enbyt = AES.encrypt(msgby, keyby);
		char[] msg64 = Base64.encode(enbyt);
		str2 = new String(msg64);
		str2 = str2.replace("+", "*");
		        } catch (Exception e) {
		         e.printStackTrace();
		        }
		System.out.println(str1);
		System.out.println(str2);
		//把加密串解密
		//str2="IGeQEdlw7RrMoSr4y60bu7afM1zI1ewvkCS4B9kiVo0X*uOsnd*DzDn3hEKi6Kg79vQDpSEUYGKdUcD8snuBZCO0xMNl30sX3dSgpsxX2bE=";
		try {
		str2 = str2.replace("*", "+");
		            byte[] msgby = Base64.decode(str2.toCharArray());
		            byte[] keyby = AES_Key.getBytes("ASCII");
		            byte[] debyt = AES.decrypt(msgby, keyby);
		            str1 = new String(debyt);
		            boolean isLogin = "1".equals(str1.substring(2,3));   //是否登录
		            String branch = str1.substring(5,9);   //营业部
		            String fundid = str1.substring(11,27); //柜台客户号
		            String token  = str1.substring(29,76); //UUID
		            String time   = str1.substring(63,76); //时间戳
		            //处理fundid
		            StringBuffer fdtemp = new StringBuffer();
		            fdtemp.append(fundid.substring(0,4));
		            fdtemp.append(fundid.substring(6,10));
		            fdtemp.append(fundid.substring(12,16));
		            fundid = fdtemp.reverse().toString();
		            System.out.println("fundid={"+fundid+"};isLogin={"+isLogin+"};branch={"+branch+"};token={"+token+"};time={"+time+"};");
		        } catch (Exception e) {
		         e.printStackTrace();
		        }
		System.out.println(str2);
		System.out.println(str1);//加密前的字符串
		}

}
