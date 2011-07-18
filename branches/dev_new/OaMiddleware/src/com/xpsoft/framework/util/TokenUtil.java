package com.xpsoft.framework.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 验证码处理工具类，包含如下功能： 1）产生验证码 2）保存验证码 3）校验验证码 4）生成图像验证码
 * 
 * @Class Name TokenUtil
 * @Author likang
 * @Create In Aug 7, 2010
 */
public class TokenUtil {
	public static final String TOKEN_KEY_NAME = "token";
	public static final String TOKEN_PARAMETER_NAME = "token";

	/**
	 * 根据给定的字符序列生成随即的验证码。
	 * @Methods Name generateToken
	 * @Create In Aug 9, 2010 By likang
	 * @param characters 验证码的字符取值范围
	 * @param length  生成的验证码的长度
	 * @return 指定长度的验证码字符串
	 */
	public static String generateToken(String characters, int length) {
		Random ran = new Random();
		char[] tokens = new char[length];
		for (int i = 0; i < length; i++) {
			char ch = characters.charAt(ran.nextInt(characters.length()));
			tokens[i] = ch;
		}

		return new String(tokens);
	}

	/**
	 * 生成验证码，并将其保存到当前的会话中。该方法主要应用于Struts1,Servlet,JSP环境。 如果会话不存在，自动创建会话。
	 * @Methods Name saveToken
	 * @Create In Aug 9, 2010 By likang
	 * @param session 当前的请求对象。
	 * @param characters  验证码的字符取值范围
	 * @param length 生成的验证码的长度
	 */
	public static void saveToken(HttpServletRequest request, String characters,
			int length) {
		HttpSession session = request.getSession();
		session.setAttribute(TOKEN_KEY_NAME, generateToken(characters, length));
	}

	/**
	 * 针对当前的请求进行验证码的校验，并且在校验完毕候从会话中清空验证码，将其作废。 该方法适用于Struts1,Servlet,JSP环境。
	 * 下面的情况标识校验通过： 1）请求中包含名字为TokenConstants.TOKEN_PARAMETER_NAME所指定的参数。
	 * 2）会话中包含名字为TokenConstants.TOKEN_KEY_NAME所指定的属性。
	 * 3）1参数的值和2属性的值（字符串类型）相等（忽略大小写）。
	 * @Methods Name isTokenValid
	 * @Create In Aug 9, 2010 By likang
	 * @param request HTTP请求对象。
	 * @return 验证通过返回true，否则返回false。
	 */
	public static boolean isTokenValid(HttpServletRequest request) {
		HttpSession session = request.getSession();

		boolean isLegal = true;
		String[] tokenParams = (String[]) request
				.getParameterValues(TOKEN_PARAMETER_NAME);
		if (tokenParams == null) {
			isLegal = false;
		} else {
			String token = tokenParams[0];
			if (token == null) {
				isLegal = false;
			} else {
				String sessionToken = (String) session
						.getAttribute(TOKEN_KEY_NAME);
				if (!token.equalsIgnoreCase(sessionToken)) {
					isLegal = false;
				}
			}
		}
		// 删除会话中的token信息
		session.removeAttribute(TOKEN_KEY_NAME);
		return isLegal;
	}

	/**
	 * 向客户端生成验证码图片。
	 * @Methods Name generateTokenImage
	 * @Create In Aug 9, 2010 By likang
	 * @param response HHTP 响应对象。
	 * @param session  包含会话属性信息的Map对象。
	 * @param width  验证码图片的宽度。
	 * @param height 验证码图片的高度。
	 * @throws IOException  执行操作时发生网络错误时。
	 */
	public static void generateTokenImage(HttpServletResponse response,
			HttpSession session, int width, int height) throws IOException {
		// 设置响应内容为图片格式
		response.setContentType("image/jpeg");

		// 禁止浏览器缓存
		response.addHeader("pragma", "NO-cache");
		response.addHeader("Cache-Control", "no-cache");
		response.addDateHeader("Expries", 0);

		// 绘制图片
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		// 以下填充背景颜色
		g.drawRect(0, 0, width - 1, height - 1);
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(1, 1, width - 1, height - 1);
		g.setColor(new Color(102, 102, 102));
		g.drawRect(0, 0, width - 1, height - 1);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 21));

		g.setColor(getRandColor(160, 200));

		// 画随机线
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int xl = random.nextInt(6) + 1;
			int yl = random.nextInt(12) + 1;
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 从另一方向画随机线
		for (int i = 0; i < 70; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int xl = random.nextInt(12) + 1;
			int yl = random.nextInt(6) + 1;
			g.drawLine(x, y, x - xl, y - yl);
		}

		// 绘制
		String sRand = "";
		String token = (String) session.getAttribute(TOKEN_KEY_NAME);
		g.drawString(token, 5, height - 2);
		for (int i = 0; i < token.length(); i++) {
			char ctmp = token.charAt(i);
			sRand += String.valueOf(ctmp);
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(String.valueOf(ctmp), 15 * i + 12, 17);
		}
		g.dispose();

		// 发送内容到客户端
		ServletOutputStream out = response.getOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image);
		out.close();
	}

	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}