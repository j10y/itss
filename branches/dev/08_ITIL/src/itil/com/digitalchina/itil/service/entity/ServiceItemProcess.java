package com.digitalchina.itil.service.entity;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.workflow.entity.VirtualDefinitionInfo;

/**
 * ��������������ʵ��
 * 
 * @Class Name ServiceItemProcess
 * @Author sa
 * @Create In 2009-2-23
 */
public class ServiceItemProcess extends BaseObject {
	public static Integer PROCESS_TYPE_CREATE=0;
	public static Integer PROCESS_TYPE_MODIFY=1;
	public static Integer PROCESS_TYPE_REMOVE=2;
	
	private Integer sidProcessType; //��������
	private String definitionName;//��������
	private PagePanel pagePanel;//����������
	private PageModel pageModel;//�������ҳ��ģ��
	private PagePanel pageListPanel;
	
	private Long id; //�Զ����
	private ServiceItem serviceItem; // ����������
	private VirtualDefinitionInfo processInfo; // ��������
	private SystemMainTable reqTable; // ������������
	private PageModel endPageModel;//���̽����鿴ҳ��
	private String buttonName; //����������ť����
	private String agreement; //�û�Э��
	private Integer status;	//״̬���Ƿ����1Ϊ����0Ϊ������

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceItem getServiceItem() {
		return serviceItem;
	}

	public void setServiceItem(ServiceItem serviceItem) {
		this.serviceItem = serviceItem;
	}

	public String getAgreement() {
		return agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public Integer getSidProcessType() {
		return sidProcessType;
	}

	public void setSidProcessType(Integer sidProcessType) {
		this.sidProcessType = sidProcessType;
	}

	public String getDefinitionName() {
		return definitionName;
	}

	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}

	public PagePanel getPagePanel() {
		return pagePanel;
	}

	public void setPagePanel(PagePanel pagePanel) {
		this.pagePanel = pagePanel;
	}

	public PageModel getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}

	public PagePanel getPageListPanel() {
		return pageListPanel;
	}

	public void setPageListPanel(PagePanel pageListPanel) {
		this.pageListPanel = pageListPanel;
	}

	public PageModel getEndPageModel() {
		return endPageModel;
	}

	public void setEndPageModel(PageModel endPageModel) {
		this.endPageModel = endPageModel;
	}

	public SystemMainTable getReqTable() {
		return reqTable;
	}

	public void setReqTable(SystemMainTable reqTable) {
		this.reqTable = reqTable;
	}

	public VirtualDefinitionInfo getProcessInfo() {
		return processInfo;
	}

	public void setProcessInfo(VirtualDefinitionInfo processInfo) {
		this.processInfo = processInfo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}