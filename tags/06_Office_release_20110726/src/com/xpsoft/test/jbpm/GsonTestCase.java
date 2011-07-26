/*    */ package com.xpsoft.test.jbpm;
/*    */ 
/*    */ import com.xpsoft.core.util.XmlUtil;
/*    */ import java.io.PrintStream;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class GsonTestCase
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 26 */     test();
/*    */   }
/*    */ 
/*    */   public static void test()
/*    */   {
/* 31 */     String path = "L:/devtools/workspace/joffice/test/com/xpsoft/test/jbpm/jbpmdef.xml";
/*    */ 
/* 33 */     String defXml = XmlUtil.load(path).getRootElement().asXML();
/*    */ 
/* 35 */     System.out.println("xml:" + defXml);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.jbpm.GsonTestCase
 * JD-Core Version:    0.6.0
 */