/*    */ package com.htsoft.core.jbpm.jpdl;
/*    */ 
/*    */ import java.awt.Point;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ public class GeometryUtils
/*    */ {
/*    */   public static double getSlope(int x1, int y1, int x2, int y2)
/*    */   {
/* 17 */     return (y2 - y1) / (x2 - x1);
/*    */   }
/*    */ 
/*    */   public static double getYIntercep(int x1, int y1, int x2, int y2)
/*    */   {
/* 30 */     return y1 - x1 * getSlope(x1, y1, x2, y2);
/*    */   }
/*    */ 
/*    */   public static Point getRectangleCenter(Rectangle rect)
/*    */   {
/* 39 */     return new Point((int)rect.getCenterX(), (int)rect.getCenterY());
/*    */   }
/*    */ 
/*    */   public static Point getRectangleLineCrossPoint(Rectangle rectangle, Point p1, int grow)
/*    */   {
/* 50 */     Rectangle rect = rectangle.getBounds();
/* 51 */     rect.grow(grow, grow);
/* 52 */     Point p0 = getRectangleCenter(rect);
/*    */ 
/* 54 */     if (p1.x == p0.x) {
/* 55 */       if (p1.y < p0.y) {
/* 56 */         return new Point(p0.x, rect.y);
/*    */       }
/* 58 */       return new Point(p0.x, rect.y + rect.height);
/*    */     }
/*    */ 
/* 61 */     if (p1.y == p0.y) {
/* 62 */       if (p1.x < p0.x) {
/* 63 */         return new Point(rect.x, p0.y);
/*    */       }
/* 65 */       return new Point(rect.x + rect.width, p0.y);
/*    */     }
/*    */ 
/* 68 */     double slope = getSlope(p0.x, p0.y, rect.x, rect.y);
/* 69 */     double slopeLine = getSlope(p0.x, p0.y, p1.x, p1.y);
/* 70 */     double yIntercep = getYIntercep(p0.x, p0.y, p1.x, p1.y);
/*    */ 
/* 72 */     if (Math.abs(slopeLine) > slope - 0.01D) {
/* 73 */       if (p1.y < rect.y) {
/* 74 */         return new Point((int)Math.round((rect.y - yIntercep) / slopeLine), rect.y);
/*    */       }
/* 76 */       return new Point((int)Math.round((rect.y + rect.height - yIntercep) / slopeLine), rect.y + rect.height);
/*    */     }
/*    */ 
/* 79 */     if (p1.x < rect.x) {
/* 80 */       return new Point(rect.x, (int)Math.round(slopeLine * rect.x + yIntercep));
/*    */     }
/* 82 */     return new Point(rect.x + rect.width, (int)Math.round(slopeLine * (rect.x + rect.width) + yIntercep));
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.jbpm.jpdl.GeometryUtils
 * JD-Core Version:    0.6.0
 */