/*    */ package com.xpsoft.core.jbpm.jpdl;
/*    */ 
/*    */ import java.awt.Point;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Transition
/*    */ {
/*    */   private Point labelPosition;
/* 10 */   private List<Point> lineTrace = new LinkedList();
/*    */   private String label;
/*    */   private String to;
/*    */ 
/*    */   public Transition(String label, String to)
/*    */   {
/* 15 */     this.label = label;
/* 16 */     this.to = to;
/*    */   }
/*    */ 
/*    */   public void addLineTrace(Point lineTrace) {
/* 20 */     if (lineTrace != null)
/* 21 */       this.lineTrace.add(lineTrace);
/*    */   }
/*    */ 
/*    */   public Point getLabelPosition()
/*    */   {
/* 26 */     return this.labelPosition;
/*    */   }
/*    */ 
/*    */   public void setLabelPosition(Point labelPosition) {
/* 30 */     this.labelPosition = labelPosition;
/*    */   }
/*    */ 
/*    */   public List<Point> getLineTrace() {
/* 34 */     return this.lineTrace;
/*    */   }
/*    */ 
/*    */   public void setLineTrace(List<Point> lineTrace) {
/* 38 */     this.lineTrace = lineTrace;
/*    */   }
/*    */ 
/*    */   public String getLabel() {
/* 42 */     return this.label;
/*    */   }
/*    */ 
/*    */   public void setLabel(String label) {
/* 46 */     this.label = label;
/*    */   }
/*    */ 
/*    */   public String getTo() {
/* 50 */     return this.to;
/*    */   }
/*    */ 
/*    */   public void setTo(String to) {
/* 54 */     this.to = to;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.jbpm.jpdl.Transition
 * JD-Core Version:    0.6.0
 */