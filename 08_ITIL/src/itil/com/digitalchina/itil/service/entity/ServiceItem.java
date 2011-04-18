package com.digitalchina.itil.service.entity;

import java.util.Date;

import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.config.entity.CIBaseData;
/**
 * 1������������ʽ�Ƴ��󣬽������Ŀ¼�����򣬷�����һֱ�ڷ�������С�
 * 2��ͬ��һ������ķ�������Թ�����ͬ�ķ���Ŀ¼���ڲ�ͬ�ķ���Ŀ¼�У�
 * ���ܾ��в�ͬ�����Կ��ܾ��в�ͬ�����ԡ�
 * 3������ĳɱ����ṩ�����������гɱ�Ԫ����ɣ�����Ӧ��ϵͳ����Ա�ȣ�
 * 4��ͬ��������ڷ��񼶱�Ĳ�ͬ������в�ͬ�ķ���ɱ����Ӷ���Ϊ��ͬ�ķ�����
 * @Class Name ServiceItem
 * @Author sa
 * @Create In 2008-11-9
 */
public class ServiceItem extends CIBaseData{
	//add by lee for ����ɾ����� in 20091119 begin
	public static int DELETE_TRUE = 1;//ɾ��
	public static int DELETE_FALSE = 0;//δɾ��
	//add by lee for ����ɾ����� in 20091119 end
	//����ID
	private Long id;
	//������
	private String serviceItemCode;
	//����Ŀ¼����
	private String serviceCataCode;	
	//�����ķ���������
	private ServiceItemType serviceItemType;
	//��������
	private String name;
	//�����������
//	private ServicePortfolio sp; //remove by lee for �������� in 20091121
	//��������Ŀ¼
	//private ServiceCatalogue sc;
	//״̬,���ڷ�������е��ĸ�״̬��
	private ServiceStatus serviceStatus;
	//��Ч��
	private Date beginDate;
	//��ֹ��Ч��
	private Date endDate;
	//����
	private String descn;
	//����۸�
	private Double servePrice;
	//����ɱ�
	private Double serveCost;
	//�Ʒѷ�ʽ	
	private CostWay costWay;
	//�����׼
	private String serviceStandard;
	//�������
	private String serviceEntry;
	//�û�ʹ���ֲἰ��������ļ�
	private String serviceFile;
	//������ 	
	private UserInfo serviceManager;
	//�ϼ�������
	private ServiceItem parentServiceItem;
	//ɾ����־
	private Integer deleteFlag = DELETE_FALSE;//add by lee for ����Ĭ�� in 20091121
	//�������ͣ�������/���Եȣ�
	private ServiceType serviceType;
	
	private Integer official;//add by lee for �����Ƿ���ʽ�жϣ��û��¼�֧���� in 20100519

	public String getUniquePropName() {
		return "name";
	}
	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public ServiceItem getParentServiceItem() {
		return parentServiceItem;
	}
	public void setParentServiceItem(ServiceItem parentServiceItem) {
		this.parentServiceItem = parentServiceItem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((serviceItemCode == null) ? 0 : serviceItemCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ServiceItem other = (ServiceItem) obj;
		if (serviceItemCode == null) {
			if (other.serviceItemCode != null)
				return false;
		} else if (!serviceItemCode.equals(other.serviceItemCode))
			return false;
		return true;
	}

	public CostWay getCostWay() {
		return costWay;
	}
	public void setCostWay(CostWay costWay) {
		this.costWay = costWay;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public ServiceCatalogue getSc() {
//		return sc;
//	}
//	public void setSc(ServiceCatalogue sc) {
//		this.sc = sc;
//	}
	public Double getServeCost() {
		return serveCost;
	}
	public void setServeCost(Double serveCost) {
		this.serveCost = serveCost;
	}
	public Double getServePrice() {
		return servePrice;
	}
	public void setServePrice(Double servePrice) {
		this.servePrice = servePrice;
	}
	public String getServiceEntry() {
		return serviceEntry;
	}
	public void setServiceEntry(String serviceEntry) {
		this.serviceEntry = serviceEntry;
	}
	public String getServiceFile() {
		return serviceFile;
	}
	public void setServiceFile(String serviceFile) {
		this.serviceFile = serviceFile;
	}
	public UserInfo getServiceManager() {
		return serviceManager;
	}
	public void setServiceManager(UserInfo serviceManager) {
		this.serviceManager = serviceManager;
	}
	public String getServiceStandard() {
		return serviceStandard;
	}
	public void setServiceStandard(String serviceStandard) {
		this.serviceStandard = serviceStandard;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
//	public ServicePortfolio getSp() {
//		return sp;
//	}
//	public void setSp(ServicePortfolio sp) {
//		this.sp = sp;
//	}

	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public ServiceItemType getServiceItemType() {
		return serviceItemType;
	}
	public void setServiceItemType(ServiceItemType serviceItemType) {
		this.serviceItemType = serviceItemType;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceItemCode() {
		return serviceItemCode;
	}

	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
	}

	public String getServiceCataCode() {
		return serviceCataCode;
	}

	public void setServiceCataCode(String serviceCataCode) {
		this.serviceCataCode = serviceCataCode;
	}

	public Integer getOfficial() {
		return official;
	}

	public void setOfficial(Integer official) {
		this.official = official;
	}
}	
