/*     */ package com.htsoft.core.jbpm.jpdl;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Node
/*     */ {
/*     */   private String name;
/*     */   private String type;
/*     */   private Rectangle rectangle;
/*  11 */   private List<Transition> transitions = new ArrayList();
/*     */   private int x;
/*     */   private int y;
/*     */   private int width;
/*     */   private int height;
/*     */ 
/*     */   public Node(String name, String type)
/*     */   {
/*  19 */     this.name = name;
/*  20 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public Node(String name, String type, int x, int y, int w, int h) {
/*  24 */     this.name = name;
/*  25 */     this.type = type;
/*  26 */     this.x = x;
/*  27 */     this.y = y;
/*  28 */     this.width = w;
/*  29 */     this.height = h;
/*     */ 
/*  31 */     this.rectangle = new Rectangle();
/*  32 */     this.rectangle.setBounds(x, y, w, h);
/*     */   }
/*     */ 
/*     */   public int getCenterX()
/*     */   {
/*  37 */     return this.x + this.width / 2;
/*     */   }
/*     */ 
/*     */   public int getCenterY() {
/*  41 */     return this.y + this.height / 2;
/*     */   }
/*     */ 
/*     */   public int getX() {
/*  45 */     return this.x;
/*     */   }
/*     */ 
/*     */   public void setX(int x) {
/*  49 */     this.x = x;
/*     */   }
/*     */ 
/*     */   public int getY() {
/*  53 */     return this.y;
/*     */   }
/*     */ 
/*     */   public void setY(int y) {
/*  57 */     this.y = y;
/*     */   }
/*     */ 
/*     */   public int getWidth() {
/*  61 */     return this.width;
/*     */   }
/*     */ 
/*     */   public void setWidth(int width) {
/*  65 */     this.width = width;
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/*  69 */     return this.height;
/*     */   }
/*     */ 
/*     */   public void setHeight(int height) {
/*  73 */     this.height = height;
/*     */   }
/*     */ 
/*     */   public void addTransition(Transition tran) {
/*  77 */     this.transitions.add(tran);
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  81 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  85 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getType() {
/*  89 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type) {
/*  93 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public Rectangle getRectangle() {
/*  97 */     return this.rectangle;
/*     */   }
/*     */ 
/*     */   public void setRectangle(Rectangle rectangle) {
/* 101 */     this.rectangle = rectangle;
/*     */   }
/*     */ 
/*     */   public List<Transition> getTransitions() {
/* 105 */     return this.transitions;
/*     */   }
/*     */ 
/*     */   public void setTransitions(List<Transition> transitions) {
/* 109 */     this.transitions = transitions;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.jbpm.jpdl.Node
 * JD-Core Version:    0.6.0
 */