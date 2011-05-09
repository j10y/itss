package com.zsgj.info.appframework.pagemodel.servlet;

import java.util.List;

import com.zsgj.info.appframework.pagemodel.entity.PagePanelBtn;

public class CoderForButton {
	public static String encode(List<PagePanelBtn> pagePanelBtns){
		String json="";
		for(PagePanelBtn panelBtn:pagePanelBtns){		
			Long id = panelBtn.getId();			//�õ�id
			String btnName = panelBtn.getBtnName();//�õ�������			
			String link = panelBtn.getLink();	//�õ�����
			if(link==null){
				link="";
			}
			String nextPageModelName;			//�õ�nextPageModel������
			if(panelBtn.getNextPageModel()==null){
				nextPageModelName = "";
			}else{
				nextPageModelName = panelBtn.getNextPageModel().getName();
			}
			String btnTypeName;					//�õ���ť������
			if(panelBtn.getBtnType()==null){
				btnTypeName="";
			}else{
				btnTypeName=panelBtn.getBtnType().getCnName();
			}
			String method=panelBtn.getMethod();	//�õ���ť��Ӧ������
			String imageUrl=panelBtn.getImageUrl();//�õ���ť��Ӧͼ��
			Integer openWinFlag=panelBtn.getOpenWinFlag();//�õ��Ƿ�Ϊ���ڱ�ʾ
			Integer isDisplay=panelBtn.getIsDisplay();//�õ��Ƿ���ʾ��ʾ
			String order="";
			if(panelBtn.getOrder()!=null)
				order=panelBtn.getOrder().toString();//�õ���ť�����
			
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
