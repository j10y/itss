package com.xpsoft.framework.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.xpsoft.framework.util.PropertiesUtil;

public class FenyeTagecount extends SimpleTagSupport{

	//当前页
	private int currentPage;
	//当前页总页数
	private int pageTotal;
	//点击方法
	private String onclick;
	
	/**
	 * 标签方法
	 */
	public void  doTag() throws IOException {
		
		//得到当前显示的每页行数
		Integer pageSize = Integer.valueOf(PropertiesUtil.getProperties("pageSize","5"));
		//首页
		String onclickFirst = "";
		//上一页
		String onclickPre = "";
		//下一页
		String onclickNext = "";
		//末页
		String onclickEnd = "";
		
		String preOnclick = onclick.substring(0,onclick.indexOf("(")+1);
		String midOnclick = onclick.substring(onclick.indexOf("(")+1,onclick.indexOf(")"));
		String endOnclick = onclick.substring(onclick.indexOf(")"));
		if("".equals(midOnclick.trim())){
			onclickFirst = preOnclick + 1+endOnclick;
			onclickPre = preOnclick + (currentPage-1)+endOnclick;
			onclickNext = preOnclick + (currentPage+1)+endOnclick;
			onclickEnd = preOnclick + (pageTotal)+endOnclick;
		}else{
			onclickFirst = preOnclick + 1 + "," + midOnclick + endOnclick;
			onclickPre = preOnclick + (currentPage-1) + "," + midOnclick + endOnclick;
			onclickNext = preOnclick + (currentPage+1) + "," + midOnclick + endOnclick;
			onclickEnd = preOnclick + (pageTotal) + "," + midOnclick + endOnclick;
		}
		// 得到out
		JspWriter out = getJspContext().getOut();
		StringBuffer tableString = new StringBuffer();
		if(pageTotal!=0){
			tableString.append("<div class=\"countPage\"><a href=\"javascript:;\"  ");
			if(currentPage==1){
				tableString.append("disabled=true");
				tableString.append("  ");
			}else{
				tableString.append("  onclick=\""+onclickFirst+"\"");
			}
			tableString.append(">&nbsp;"+PropertiesUtil.getProperties("page.indexpage")+"&nbsp;</a><a href=\"javascript:;\"  ");
			if(currentPage==1){
				tableString.append("disabled=true");
				tableString.append("  ");
			}else{
				tableString.append("  onclick=\""+onclickPre+"\"");
			}
			
			tableString.append(">&nbsp;"+PropertiesUtil.getProperties("page.uppage")+"&nbsp;</a><a href=\"javascript:;\" ");
			if(currentPage==pageTotal){
				tableString.append("disabled=true");
				tableString.append("  ");
			}else{
				tableString.append("  onclick=\""+onclickNext+"\"");
			}
			tableString.append(">&nbsp;"+PropertiesUtil.getProperties("page.nextpage")+"&nbsp;</a><a href=\"javascript:;\" ");
			if(currentPage==pageTotal){
				tableString.append("disabled=true");
				tableString.append("  ");
			}else{
				tableString.append("  onclick=\""+onclickEnd+"\"");
			}
			
			//tableString.append(">&nbsp;"+PropertiesUtil.getProperties("page.endpage")+"&nbsp;</a>&nbsp;"+PropertiesUtil.getProperties("page.cruuen")+currentPage+PropertiesUtil.getProperties("page.page")+"/"+pageTotal+PropertiesUtil.getProperties("page.page")+"&nbsp;"+PropertiesUtil.getProperties("page.pageshow")+pageSize+PropertiesUtil.getProperties("page.tiao")+"&nbsp;</div>");
			tableString.append(">&nbsp;"+PropertiesUtil.getProperties("page.endpage")+"&nbsp;</a>&nbsp;"+PropertiesUtil.getProperties("page.cruuen")+currentPage+PropertiesUtil.getProperties("page.page")+"/"+pageTotal+PropertiesUtil.getProperties("page.page")+"&nbsp;</div>");
		}else{
			tableString.append("<div class=\"countPage\">"+PropertiesUtil.getProperties("page.message")+"</div>");
		}
		out.write(tableString.toString());
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
	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}
}
