package com.xpsoft.framework.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.xpsoft.framework.util.PropertiesUtil;

/**
 * 分页标签 仅仅用于方便维护
 * @Class Name FenyeTag
 * @Author debby
 * @Create In Aug 20, 2010
 */
public class FenyeTag extends SimpleTagSupport{
	//当前页
	private int currentPage;
	//当前页总条数
	private int currentTotal;
	//点击方法
	private String onclick;
	
	/**
	 * 标签方法
	 */
	public void  doTag() throws IOException {
		//得到当前显示的每页行数
		Integer pageSize = Integer.valueOf(PropertiesUtil.getProperties("pageSize","10"));
		//首页
		String onclickFirst = "";
		//上一页
		String onclickPre = "";
		//下一页
		String onclickNext = "";
		
		String preOnclick = onclick.substring(0,onclick.indexOf("(")+1);
		String midOnclick = onclick.substring(onclick.indexOf("(")+1,onclick.indexOf(")"));
		String endOnclick = onclick.substring(onclick.indexOf(")"));
		if("".equals(midOnclick.trim())){
			onclickFirst = preOnclick + 1+endOnclick;
			onclickPre = preOnclick + (currentPage-1)+endOnclick;
			onclickNext = preOnclick + (currentPage+1)+endOnclick;
		}else{
			onclickFirst = preOnclick + 1 + "," + midOnclick + endOnclick;
			onclickPre = preOnclick + (currentPage-1) + "," + midOnclick + endOnclick;
			onclickNext = preOnclick + (currentPage+1) + "," + midOnclick + endOnclick;
		}
		// 得到out
		JspWriter out = getJspContext().getOut();
		StringBuffer tableString = new StringBuffer();
		if(currentTotal!=0){
			tableString.append("<div id=\"page\"><a href=\"javascript:;\"  ");
			if(currentPage==1){
				tableString.append("disabled=true");
				tableString.append("  onclick=\"\"");
			}else{
				tableString.append("  onclick=\""+onclickFirst+"\"");
			}
			tableString.append(">&nbsp;首页&nbsp;</a><a href=\"javascript:;\"  ");
			if(currentPage==1){
				tableString.append("disabled=true");
				tableString.append("  onclick=\"\"");
			}else{
				tableString.append("  onclick=\""+onclickPre+"\"");
			}
			
			tableString.append(">&nbsp;上一页&nbsp;</a><a href=\"javascript:;\" ");
			if(currentTotal!=pageSize+1){
				tableString.append("disabled=true");
				tableString.append("  onclick=\"\"");
			}else{
				tableString.append("  onclick=\""+onclickNext+"\"");
			}
			
			tableString.append(">&nbsp;下一页&nbsp;</a>&nbsp;当前第"+currentPage+"页/每页显示"+pageSize+"条&nbsp;</div>");
			out.write(tableString.toString());
		}else{
			tableString.append("<div id=\"page\">"+PropertiesUtil.getProperties("page.message")+"</div>");
			out.write(tableString.toString());
		}
		
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	
	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public int getCurrentTotal() {
		return currentTotal;
	}

	public void setCurrentTotal(int currentTotal) {
		this.currentTotal = currentTotal;
	}

	
	
}
