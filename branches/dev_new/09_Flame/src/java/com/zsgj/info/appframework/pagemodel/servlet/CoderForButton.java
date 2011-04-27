package com.zsgj.info.appframework.pagemodel.servlet;

import java.util.List;

import com.zsgj.info.appframework.pagemodel.entity.PagePanelBtn;

public class CoderForButton {
	public static String encode(List<PagePanelBtn> pagePanelBtns){
		String json="";
		for(PagePanelBtn panelBtn:pagePanelBtns){		
			Long id = panelBtn.getId();			//得到id
			String btnName = panelBtn.getBtnName();//得到中文名			
			String link = panelBtn.getLink();	//得到链接
			if(link==null){
				link="";
			}
			String nextPageModelName;			//得到nextPageModel中文名
			if(panelBtn.getNextPageModel()==null){
				nextPageModelName = "";
			}else{
				nextPageModelName = panelBtn.getNextPageModel().getName();
			}
			String btnTypeName;					//得到按钮类型名
			if(panelBtn.getBtnType()==null){
				btnTypeName="";
			}else{
				btnTypeName=panelBtn.getBtnType().getCnName();
			}
			String method=panelBtn.getMethod();	//得到按钮对应方法名
			String imageUrl=panelBtn.getImageUrl();//得到按钮对应图案
			Integer openWinFlag=panelBtn.getOpenWinFlag();//得到是否为窗口标示
			Integer isDisplay=panelBtn.getIsDisplay();//得到是否显示标示
			String order="";
			if(panelBtn.getOrder()!=null)
				order=panelBtn.getOrder().toString();//得到按钮排序号
			
			json += "{\"id\":"+id+"," +
					"\"btnType\":\""+btnTypeName+"\"," +
					"\"btnName\":\""+btnName+"\"," +
					"\"method\":\""+method+"\"," +
					"\"link\":\""+link+"\"," +
					"\"nextPageModel\":\""+nextPageModelName+"\"," +
					"\"openWinFlag\":"+openWinFlag+"," +
					"\"imageUrl\":\""+imageUrl+"\"," +
					"\"order\":\""+order+"\"," +
					"\"isDisplay\":"+isDisplay+
					"},";
			}//end for
			if(!json.equals("")){
				json = "{data:[" + json.substring(0, json.length()-1) + "]}";
			}else{
				json = "{data:[]}";
			}
			
		return json;
	}
}
