package com.xpsoft.framework.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用来以图片的形式来生成验证码。
 * 生成验证码后先将其保存到用户的当前会话中，然后在以图片的形式发送到客户端。
 * 验证码的验证要通过TokenFilter来完成。
 * 该Servlet包含如下的初始化参数，来定制验证码的信息：
 * <ul>
 * <li>characters:生成验证码所有的字符序列，默认为数字和字母</li>
 * <li>length:生成的验证码的长度，默认为4位</li>
 * <li>width:验证码图片的显示宽度，默认为80像素</li>
 * <li>height:验证码图片的高度，默认为30像素</li>
 * </ul>
 * 
 * 该Servlet需要在应用的web.xml进行如下部署:
 * <servlet>
 *   <servlet-name>ImageTokenServlet</servlet-name>
 *   <servlet-class>com.xpsoft.framework.util.ImageTokenServlet</servlet-class>
 * </servlet>
 * <servlet-mapping>
 *   <servlet-name>ImageTokenServlet</servlet-name>
 *   <url-pattern>/token/image.jpg</url-pattern>
 * </servlet-mapping>
 * 
 * 然后在客户端网页中进行如下引用：
 * <img src="/yourContextPath/token/image.jpg"></img>
 * @Class Name ImageTokenServlet
 * @Author likang
 * @Create In Aug 7, 2010
 */
public class ImageTokenServlet extends HttpServlet {
    private int width = 80;
    private int height = 20;
    //用户修改为只用数字"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String characters = "0123456789";
    private int length = 4;
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //产生tokens并将其保存在当前的会话中        
        TokenUtil.saveToken(request, characters, length);

        //生成图片响应
        TokenUtil.generateTokenImage(response, request.getSession(), width, height);
    }
   
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
    
    public void init() {
        String initParam = getInitParameter("characters");
        if (initParam != null) {
            this.characters = initParam;
        }

        initParam = getInitParameter("length");
        if (initParam != null) {
            this.length = Integer.parseInt(initParam);
        }

        initParam = getInitParameter("width");
        if (initParam != null) {
            this.width = Integer.parseInt(initParam);
        }

        initParam = getInitParameter("height");
        if (initParam != null) {
            this.height = Integer.parseInt(initParam);
        }
    }
}
