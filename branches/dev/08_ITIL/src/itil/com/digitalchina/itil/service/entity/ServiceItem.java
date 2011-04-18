package com.digitalchina.itil.service.entity;

import java.util.Date;

import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.config.entity.CIBaseData;
/**
 * 1）服务项在正式推出后，进入服务目录，否则，服务项一直在服务组合中。
 * 2）同样一个具体的服务项，可以归属不同的服务目录，在不同的服务目录中，
 * 可能具有不同的属性可能具有不同的属性。
 * 3）服务的成本由提供此项服务的所有成本元素组成，包括应用系统、人员等；
 * 4）同类服务，由于服务级别的不同，会具有不同的服务成本，从而作为不同的服务处理
 * @Class Name ServiceItem
 * @Author sa
 * @Create In 2008-11-9
 */
public class ServiceItem extends CIBaseData{
	//add by lee for 增加删除标记 in 20091119 begin
	public static int DELETE_TRUE = 1;//删除
	public static int DELETE_FALSE = 0;//未删除
	//add by lee for 增加删除标记 in 20091119 end
	//服务ID
	private Long id;
	//服务编号
	private String serviceItemCode;
	//服务目录编码
	private String serviceCataCode;	
	//所属的服务项类型
	private ServiceItemType serviceItemType;
	//服务名称
	private String name;
	//所属服务组合
//	private ServicePortfolio sp; //remove by lee for 废弃属性 in 20091121
	//所属服务目录
	//private ServiceCatalogue sc;
	//状态,（在服务组合中的哪个状态）
	private ServiceStatus serviceStatus;
	//有效期
	private Date beginDate;
	//终止有效期
	private Date endDate;
	//描述
	private String descn;
	//服务价格
	private Double servePrice;
	//服务成本
	private Double serveCost;
	//计费方式	
	private CostWay costWay;
	//服务标准
	private String serviceStandard;
	//服务入口
	private String serviceEntry;
	//用户使用手册及其他相关文件
	private String serviceFile;
	//服务经理 	
	private UserInfo serviceManager;
	//上级服务项
	private ServiceItem parentServiceItem;
	//删除标志
	private Integer deleteFlag = DELETE_FALSE;//add by lee for 增加默认 in 20091121
	//服务类型，（常规/个性等）
	private ServiceType serviceType;
	
	private Integer official;//add by lee for 增加是否正式判断，用户事件支持组 in 20100519

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
