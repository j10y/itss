 package com.htsoft.core.jbpm.jpdl;
 
 import java.awt.BasicStroke;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics2D;
 import java.awt.Point;
 import java.awt.Rectangle;
 import java.awt.RenderingHints;
 import java.awt.Stroke;
 import java.awt.font.FontRenderContext;
 import java.awt.geom.Rectangle2D;
 import java.awt.image.BufferedImage;
 import java.io.File;
 import java.io.IOException;
 import java.net.URL;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import javax.imageio.ImageIO;
 
 public class JpdlModelDrawer
 {
   public static final int RECT_OFFSET_X = -4;
   public static final int RECT_OFFSET_Y = -8;
   public static final int RECT_ROUND = 25;
   public static final int DEFAULT_FONT_SIZE = 12;
   public static final Color DEFAULT_STROKE_COLOR = Color.decode("#03689A");
   public static final Stroke DEFAULT_STROKE = new BasicStroke(2.0F);
 
/*  25 */   public static final Color DEFAULT_LINE_STROKE_COLOR = Color.decode("#808080");
/*  26 */   public static final Stroke DEFAULT_LINE_STROKE = new BasicStroke(1.0F);
 
/*  28 */   public static final Color DEFAULT_FILL_COLOR = Color.decode("#F6F7FF");
 
/*  30 */   private static final Map<String, Object> nodeInfos = JpdlModel.getNodeInfos();
 
   public BufferedImage draw(JpdlModel jpdlModel) throws IOException {
/*  33 */     Rectangle dimension = getCanvasDimension(jpdlModel);
/*  34 */     BufferedImage bi = new BufferedImage(dimension.width, dimension.height, 2);
/*  35 */     Graphics2D g2 = bi.createGraphics();
/*  36 */     g2.setColor(Color.WHITE);
/*  37 */     g2.fillRect(0, 0, dimension.width, dimension.height);
/*  38 */     g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*  39 */     Font font = new Font("宋体", 0, 12);
 
/*  41 */     g2.setFont(font);
/*  42 */     Map nodes = jpdlModel.getNodes();
/*  43 */     Set activityNames = jpdlModel.getActivityNames();
/*  44 */     drawNode(nodes, activityNames, g2, font);
/*  45 */     drawTransition(nodes, g2);
/*  46 */     return bi;
   }
 
   public Rectangle getCanvasDimension(JpdlModel jpdlModel)
   {
/*  55 */     Rectangle rectangle = new Rectangle();
/*  56 */     Rectangle rect = new Rectangle();
/*  57 */     for (Node node : jpdlModel.getNodes().values()) {
/*  58 */       rect = node.getRectangle();
/*  59 */       if (rect.getMaxX() > rectangle.getMaxX()) {
/*  60 */         rectangle.width = (int)Math.round(rect.getMaxX());
       }
/*  62 */       if (rect.getMaxY() > rectangle.getMaxY()) {
/*  63 */         rectangle.height = (int)Math.round(rect.getMaxY());
       }
/*  65 */       for (Transition transition : node.getTransitions()) {
/*  66 */         List<Point> trace = transition.getLineTrace();
/*  67 */         for (Point point : trace) {
/*  68 */           if (rectangle.getMaxX() < point.x) {
/*  69 */             rectangle.width = point.x;
           }
/*  71 */           if (rectangle.getMaxY() < point.y) {
/*  72 */             rectangle.height = point.y;
           }
         }
       }
     }
/*  77 */     rectangle.width += 60;
/*  78 */     rectangle.height += 20;
/*  79 */     return rectangle;
   }
 
   private void drawTransition(Map<String, Node> nodes, Graphics2D g2)
     throws IOException
   {
/*  87 */     g2.setStroke(DEFAULT_LINE_STROKE);
/*  88 */     g2.setColor(DEFAULT_LINE_STROKE_COLOR);
/*  89 */     for (Node node : nodes.values())
/*  90 */       for (Transition transition : node.getTransitions()) {
/*  91 */         String to = transition.getTo();
/*  92 */         Node toNode = (Node)nodes.get(to);
/*  93 */         List trace = transition.getLineTrace();
 
/*  96 */         int len = trace.size() + 2;
/*  97 */         trace.add(0, new Point(node.getCenterX(), node.getCenterY()));
/*  98 */         trace.add(new Point(toNode.getCenterX(), toNode.getCenterY()));
 
/* 100 */         int[] xPoints = new int[len];
/* 101 */         int[] yPoints = new int[len];
 
/* 103 */         for (int i = 0; i < len; i++) {
/* 104 */           xPoints[i] = ((Point)trace.get(i)).x;
/* 105 */           yPoints[i] = ((Point)trace.get(i)).y;
         }
 
/* 108 */         int taskGrow = 4;
/* 109 */         int smallGrow = -2;
/* 110 */         int grow = 0;
/* 111 */         if (nodeInfos.get(node.getType()) != null)
/* 112 */           grow = -2;
         else {
/* 114 */           grow = 4;
         }
 
/* 117 */         Point p = GeometryUtils.getRectangleLineCrossPoint(node.getRectangle(), new Point(xPoints[1], yPoints[1]), grow);
/* 118 */         if (p != null) {
/* 119 */           xPoints[0] = p.x;
/* 120 */           yPoints[0] = p.y;
         }
/* 122 */         if (nodeInfos.get(toNode.getType()) != null)
/* 123 */           grow = -2;
         else {
/* 125 */           grow = 4;
         }
/* 127 */         p = GeometryUtils.getRectangleLineCrossPoint(toNode.getRectangle(), new Point(xPoints[(len - 2)], yPoints[(len - 2)]), grow);
/* 128 */         if (p != null) {
/* 129 */           xPoints[(len - 1)] = p.x;
/* 130 */           yPoints[(len - 1)] = p.y;
         }
 
/* 133 */         g2.drawPolyline(xPoints, yPoints, len);
/* 134 */         drawArrow(g2, xPoints[(len - 2)], yPoints[(len - 2)], xPoints[(len - 1)], yPoints[(len - 1)]);
/* 135 */         String label = transition.getLabel();
 
/* 137 */         if ((label == null) || (label.length() <= 0))
           continue;
         int cy;
         int cx;
         if (len % 2 == 0) {
/* 140 */           cx = (xPoints[(len / 2 - 1)] + xPoints[(len / 2)]) / 2;
/* 141 */           cy = (yPoints[(len / 2 - 1)] + yPoints[(len / 2)]) / 2;
         } else {
/* 143 */           cx = xPoints[(len / 2)];
/* 144 */           cy = yPoints[(len / 2)];
         }
/* 146 */         Point labelPoint = transition.getLabelPosition();
/* 147 */         if (labelPoint != null) {
/* 148 */           cx += labelPoint.x;
/* 149 */           cy += labelPoint.y;
         }
/* 151 */         cy += 12;
/* 152 */         g2.setColor(Color.BLUE);
/* 153 */         g2.drawString(label, cx, cy);
/* 154 */         g2.setColor(DEFAULT_LINE_STROKE_COLOR);
       }
   }
 
   private void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2)
   {
/* 161 */     double len = 8.0D;
/* 162 */     double slopy = Math.atan2(y2 - y1, x2 - x1);
/* 163 */     double cosy = Math.cos(slopy);
/* 164 */     double siny = Math.sin(slopy);
/* 165 */     int[] xPoints = { 0, x2 };
/* 166 */     int[] yPoints = { 0, y2 };
/* 167 */     double a = 8.0D * siny; double b = 8.0D * cosy;
/* 168 */     double c = 4.0D * siny; double d = 4.0D * cosy;
/* 169 */     xPoints[0] = (int)Math.round(x2 - (b + c));
/* 170 */     yPoints[0] = (int)Math.round(y2 - (a - d));
/* 171 */     xPoints[1] = (int)Math.round(x2 - (b - c));
/* 172 */     yPoints[1] = (int)Math.round(y2 - (d + a));
 
/* 174 */     g2.fillPolygon(xPoints, yPoints, 2);
   }
 
   private void drawNode(Map<String, Node> nodes, Set activityNames, Graphics2D g2, Font font)
     throws IOException
   {
/* 182 */     for (Node node : nodes.values()) {
/* 183 */       String name = node.getName();
/* 184 */       if (nodeInfos.get(node.getType()) != null) {
/* 185 */         BufferedImage bi2 = ImageIO.read(getClass().getResourceAsStream("/icons/48/" + nodeInfos.get(node.getType())));
/* 186 */         g2.drawImage(bi2, node.getX(), node.getY(), null);
       } else {
/* 188 */         int x = node.getX();
/* 189 */         int y = node.getY();
/* 190 */         int w = node.getWidth();
/* 191 */         int h = node.getHeight();
/* 192 */         g2.setColor(DEFAULT_FILL_COLOR);
/* 193 */         g2.fillRoundRect(x, y, w, h, 25, 25);
 
/* 195 */         if (activityNames.contains(name))
/* 196 */           g2.setColor(Color.RED);
         else {
/* 198 */           g2.setColor(DEFAULT_STROKE_COLOR);
         }
 
/* 201 */         g2.setStroke(DEFAULT_STROKE);
 
/* 203 */         g2.drawRoundRect(x, y, w, h, 25, 25);
 
/* 205 */         FontRenderContext frc = g2.getFontRenderContext();
/* 206 */         Rectangle2D r2 = font.getStringBounds(name, frc);
/* 207 */         int xLabel = (int)(node.getX() + (node.getWidth() - r2.getWidth()) / 2.0D);
/* 208 */         int yLabel = (int)(node.getY() + (node.getHeight() - r2.getHeight()) / 2.0D - r2.getY());
/* 209 */         g2.setStroke(DEFAULT_LINE_STROKE);
/* 210 */         g2.setColor(Color.black);
/* 211 */         g2.drawString(name, xLabel, yLabel);
       }
     }
   }
 
   public static void main(String[] args) throws Exception {
/* 217 */     JpdlModel jpdlModel = new JpdlModel(Thread.currentThread().getContextClassLoader().getResource("jpdl/buyCar.jpdl.xml").openStream());
/* 218 */     jpdlModel.getActivityNames().add("经理审批");
 
/* 220 */     ImageIO.write(new JpdlModelDrawer().draw(jpdlModel), "png", new File("d:/buyCar.png"));
   }
 }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.jbpm.jpdl.JpdlModelDrawer
 * JD-Core Version:    0.6.0
 */