package com.digitalchina.info.framework.workflow.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * 根据gpd.xml和processdefinition.xml绘制processimage.jpg
 * @Class Name DrawProcessDefination
 * @Author yang
 * @Create In 2008-4-7
 */
public class DrawProcessDefination {
	String pdnXml = "";
	String gpdXml = "";

	/**
	 * @Methods Name main
	 * @Create In 2008-4-7 By yang
	 * @param args 
	 * @ReturnType void
	 */
	public static void main(String[] args) {
		int width = 100;
        int height = 100;
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        // set background:
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, width, height);
        // set fore color:
        g.setColor(Color.RED);
        // start draw:
        g.drawLine(0, 0, 99, 199);
        // end draw:
        g.dispose();
        bi.flush();
        // encode:
        JPEGImageEncoder encoder = null;
		try {
			OutputStream out = new FileOutputStream("c:\\t.jpg");
			encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
			param.setQuality(1.0f, false);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(bi);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ImageFormatException e) {
			e.printStackTrace();
		}catch(IOException ioe) {
            ioe.printStackTrace();
        }       
	}
}
