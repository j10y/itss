package com.digitalchina.info.appframework.pagemodel.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * ҳ������еĶ�����ť
 * @Class Name PageModelBtn
 * @Author sa
 * @Create In 2008-11-30
 */
@SuppressWarnings("serial")
public class PageModelBtn extends BaseObject {
	private Long id;
	private PageModel pageModel;
	private PageBtnType btnType;
	private String btnName;
	private String method; //���õ�js��������
	private String link; //��ť������

	private PageModel nextPageModel;
	private Integer openWinFlag; //�¿�window��־
	private String imageUrl; //ͼƬ·���������ͼƬ��ť
//	�Ƿ���ʾ
	private Integer isDisplay;
	
	private Integer order;

	private Integer valign;
	private Integer align; //��������
	
	public Integer getOpenWinFlag() {
		return openWinFlag;
	}
	public void setOpenWinFlag(Integer openWinFlag) {
		this.openWinFlag = openWinFlag;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getBtnName() {
		return btnName;
	}
	
	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}
	public PageBtnType getBtnType() {
		return btnType;
	}
	public void setBtnType(PageBtnType btnType) {
		this.btnType = btnType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public PageModel getNextPageModel() {
		return nextPageModel;
	}
	public void setNextPageModel(PageModel nextPageModel) {
		this.nextPageModel = nextPageModel;
	}
	public PageModel getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getAlign() {
		return align;
	}
	public void setAlign(Integer align) {
		this.align = align;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Integer getValign() {
		return valign;
	}
	public void setValign(Integer valign) {
		this.valign = valign;
	}
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	
}
