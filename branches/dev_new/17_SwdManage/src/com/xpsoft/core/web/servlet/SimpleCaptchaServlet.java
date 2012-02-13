/*    */ package com.xpsoft.core.web.servlet;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import nl.captcha.Captcha;
/*    */ import nl.captcha.Captcha.Builder;
/*    */ import nl.captcha.servlet.CaptchaServletUtil;
/*    */ 
/*    */ public class SimpleCaptchaServlet extends HttpServlet
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private static final String PARAM_HEIGHT = "height";
/*    */   private static final String PARAM_WIDTH = "width";
/* 30 */   protected int _width = 200;
/* 31 */   protected int _height = 50;
/*    */ 
/*    */   public void init() throws ServletException
/*    */   {
/* 35 */     if (getInitParameter("height") != null) {
/* 36 */       this._height = Integer.valueOf(getInitParameter("height")).intValue();
/*    */     }
/*    */ 
/* 39 */     if (getInitParameter("width") != null)
/* 40 */       this._width = Integer.valueOf(getInitParameter("width")).intValue();
/*    */   }
/*    */ 
/*    */   public void doGet(HttpServletRequest req, HttpServletResponse resp)
/*    */     throws ServletException, IOException
/*    */   {
/* 48 */     Captcha captcha = new Captcha.Builder(this._width, this._height)
/* 49 */       .addText()
/* 50 */       .addBackground()
/* 52 */       .addNoise()
/* 54 */       .build();
/*    */ 
/* 56 */     CaptchaServletUtil.writeImage(resp, captcha.getImage());
/* 57 */     req.getSession().setAttribute("simpleCaptcha", captcha);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.web.servlet.SimpleCaptchaServlet
 * JD-Core Version:    0.6.0
 */