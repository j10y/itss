/*     */ package com.xpsoft.core.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ 
/*     */ public class StringUtil
/*     */ {
/*     */   public static String convertQuot(String orgStr)
/*     */   {
/*  24 */     return orgStr.replace("'", "\\'").replace("\"", "\\\"");
/*     */   }
/*     */ 
/*     */   public static synchronized String encryptSha256(String inputStr) {
/*     */     try {
/*  29 */       MessageDigest md = MessageDigest.getInstance("SHA-256");
/*     */ 
/*  31 */       byte[] digest = md.digest(inputStr.getBytes("UTF-8"));
/*     */ 
/*  33 */       return new String(Base64.encodeBase64(digest));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*  38 */     return null;
/*     */   }
/*     */ 
/*     */   public static String htmlEntityToString(String dataStr)
/*     */   {
/*  48 */     int start = 0;
/*  49 */     int end = 0;
/*  50 */     StringBuffer buffer = new StringBuffer();
/*  51 */     while (start > -1) {
/*  52 */       int system = 10;
/*  53 */       if (start == 0) {
/*  54 */         int t = dataStr.indexOf("&#");
/*  55 */         if (start != t) start = t;
/*     */       }
/*  57 */       end = dataStr.indexOf(";", start + 2);
/*  58 */       String charStr = "";
/*  59 */       if (end != -1) {
/*  60 */         charStr = dataStr.substring(start + 2, end);
/*     */ 
/*  62 */         char s = charStr.charAt(0);
/*  63 */         if ((s == 'x') || (s == 'X')) {
/*  64 */           system = 16;
/*  65 */           charStr = charStr.substring(1);
/*     */         }
/*     */       }
/*     */       try
/*     */       {
/*  70 */         char letter = (char)Integer.parseInt(charStr, system);
/*  71 */         buffer.append(new Character(letter).toString());
/*     */       } catch (NumberFormatException e) {
/*  73 */         e.printStackTrace();
/*     */       }
/*     */ 
/*  77 */       start = dataStr.indexOf("&#", end);
/*  78 */       if (start - end > 1) {
/*  79 */         buffer.append(dataStr.substring(end + 1, start));
/*     */       }
/*     */ 
/*  83 */       if (start == -1) {
/*  84 */         int length = dataStr.length();
/*  85 */         if (end + 1 != length) {
/*  86 */           buffer.append(dataStr.substring(end + 1, length));
/*     */         }
/*     */       }
/*     */     }
/*  90 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   public static String stringToHtmlEntity(String str)
/*     */   {
/* 100 */     StringBuffer sb = new StringBuffer();
/* 101 */     for (int i = 0; i < str.length(); i++) {
/* 102 */       char c = str.charAt(i);
/*     */ 
/* 104 */       switch (c) {
/*     */       case '\n':
/* 106 */         sb.append(c);
/* 107 */         break;
/*     */       case '<':
/* 110 */         sb.append("&lt;");
/* 111 */         break;
/*     */       case '>':
/* 114 */         sb.append("&gt;");
/* 115 */         break;
/*     */       case '&':
/* 118 */         sb.append("&amp;");
/* 119 */         break;
/*     */       case '\'':
/* 122 */         sb.append("&apos;");
/* 123 */         break;
/*     */       case '"':
/* 126 */         sb.append("&quot;");
/* 127 */         break;
/*     */       default:
/* 130 */         if ((c < ' ') || (c > '~')) {
/* 131 */           sb.append("&#x");
/* 132 */           sb.append(Integer.toString(c, 16));
/* 133 */           sb.append(';');
/*     */         } else {
/* 135 */           sb.append(c);
/*     */         }
/*     */       }
/*     */     }
/* 139 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String stringToUnicode(String s)
/*     */   {
/* 145 */     String unicode = "";
/* 146 */     char[] charAry = new char[s.length()];
/* 147 */     for (int i = 0; i < charAry.length; i++) {
/* 148 */       charAry[i] = s.charAt(i);
/* 149 */       unicode = unicode + "\\u" + Integer.toString(charAry[i], 16);
/*     */     }
/* 151 */     return unicode;
/*     */   }
/*     */ 
/*     */   public static String unicodeToString(String unicodeStr) {
/* 155 */     StringBuffer sb = new StringBuffer();
/* 156 */     String[] str = unicodeStr.toUpperCase().split("\\\\U");
/* 157 */     for (int i = 0; i < str.length; i++)
/* 158 */       if (!str[i].equals("")) {
/* 159 */         char c = (char)Integer.parseInt(str[i].trim(), 16);
/* 160 */         sb.append(c);
/*     */       }
/* 162 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 182 */     String vm = "abc.em";
/* 183 */     System.out.println(stringToHtmlEntity(vm));
/*     */   }
/*     */ 
/*     */   public static String html2Text(String inputString)
/*     */   {
/* 189 */     String htmlStr = inputString;
/* 190 */     String textStr = "";
/*     */     try
/*     */     {
/* 199 */       String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
/*     */ 
/* 201 */       String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
/*     */ 
/* 203 */       String regEx_html = "<[^>]+>";
/*     */ 
/* 205 */       Pattern p_script = Pattern.compile(regEx_script, 
/* 206 */         2);
/* 207 */       Matcher m_script = p_script.matcher(htmlStr);
/* 208 */       htmlStr = m_script.replaceAll("");
/*     */ 
/* 210 */       Pattern p_style = Pattern.compile(regEx_style, 
/* 211 */         2);
/* 212 */       Matcher m_style = p_style.matcher(htmlStr);
/* 213 */       htmlStr = m_style.replaceAll("");
/*     */ 
/* 215 */       Pattern p_html = Pattern.compile(regEx_html, 
/* 216 */         2);
/* 217 */       Matcher m_html = p_html.matcher(htmlStr);
/* 218 */       htmlStr = m_html.replaceAll("");
/*     */ 
/* 220 */       textStr = htmlStr;
/*     */     }
/*     */     catch (Exception e) {
/* 223 */       System.err.println("Html2Text: " + e.getMessage());
/*     */     }
/*     */ 
/* 226 */     return textStr;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.util.StringUtil
 * JD-Core Version:    0.6.0
 */