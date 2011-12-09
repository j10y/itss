package com.xpsoft.oa.model.bandpoor;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;

public class InfoPoor extends BaseModel{
	public static final Integer STATUS_CREATE = new Integer(1);//新建
	public static final Integer STATUS_DELETE = new Integer(0);//删除
	public static final Integer STATUS_PASS = new Integer(2);//审批通过
	public static final Integer STATUS_RETURN = new Integer(3);//打回
	public static final Integer STATUS_MODIFY = new Integer(4);//修改
	public static final Integer TYPE_SCORE = new Integer(1);//可评分
	public static final Integer TYPE_UNSCORE = new Integer(2);//不可评分
	public static final Integer SCOUCE_DIRCOLLECTION = new Integer(1);//定向采集
	public static final Integer SCOUCE_UNDIRCOLLECTION = new Integer(2);//不定向采集
	public static final Integer SCOUCE_AUTOCOLLECTION = new Integer(2);//自动采集
	
	private Long id;
	
	private String bandName;
	
	private Band bandId;
	
	private ProClass proClassId;
	
	private String proClassName;
	
	private SaleStore saleStoreid;
	
	private String saleSroteDesc;
	
	private String saleStoreName;
	
	private Floor floorNumId;
	
	private String floorNumName;
	
	private BandStyle bandStyleId;
	
	private String bandStyleName;
	
	private MainPrice mainPriceId;
	
	private String mainPriceName;
	
	private String mainProductName;
	
	private String mainProductPicture;
	
	private String webSite;
	
	private BusinessArea bandBusinessAreaId;
	
	private String bandBusinessAreaName;
	
	private BandChannel bandChannelID;
	
	private String bandChannelName;
	
	private String contactUser;
	
	private String contactPhone;
	
	private String bandDesc;
	
	private String companyNature;//公司性质
	
	private String companyAddress;//公司地址
	
	private String productLine;//生产线
	
	private Integer infoStatus;//信息状态，0删除1新建2审批通过3打回4审批通过之后修改
	
	private Integer infoType;//信息类型，1可评分2不可评分
	
	private Integer infoSource;//信息来源1定向采集2不定向采集3自动采集
	
	private Date creatDate;
	
	private AppUser creatUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBandName() {
		return bandName;
	}

	public void setBandName(String bandName) {
		this.bandName = bandName;
	}

	public Band getBandId() {
		return bandId;
	}

	public void setBandId(Band bandId) {
		this.bandId = bandId;
	}

	public ProClass getProClassId() {
		return proClassId;
	}

	public void setProClassId(ProClass proClassId) {
		this.proClassId = proClassId;
	}

	public String getProClassName() {
		return proClassName;
	}

	public void setProClassName(String proClassName) {
		this.proClassName = proClassName;
	}

	public SaleStore getSaleStoreid() {
		return saleStoreid;
	}

	public void setSaleStoreid(SaleStore saleStoreid) {
		this.saleStoreid = saleStoreid;
	}

	public String getSaleSroteDesc() {
		return saleSroteDesc;
	}

	public void setSaleSroteDesc(String saleSroteDesc) {
		this.saleSroteDesc = saleSroteDesc;
	}

	public String getSaleStoreName() {
		return saleStoreName;
	}

	public void setSaleStoreName(String saleStoreName) {
		this.saleStoreName = saleStoreName;
	}

	public Floor getFloorNumId() {
		return floorNumId;
	}

	public void setFloorNumId(Floor floorNumId) {
		this.floorNumId = floorNumId;
	}

	public String getFloorNumName() {
		return floorNumName;
	}

	public void setFloorNumName(String floorNumName) {
		this.floorNumName = floorNumName;
	}

	public BandStyle getBandStyleId() {
		return bandStyleId;
	}

	public void setBandStyleId(BandStyle bandStyleId) {
		this.bandStyleId = bandStyleId;
	}

	public String getBandStyleName() {
		return bandStyleName;
	}

	public void setBandStyleName(String bandStyleName) {
		this.bandStyleName = bandStyleName;
	}

	public MainPrice getMainPriceId() {
		return mainPriceId;
	}

	public void setMainPriceId(MainPrice mainPriceId) {
		this.mainPriceId = mainPriceId;
	}

	public String getMainPriceName() {
		return mainPriceName;
	}

	public void setMainPriceName(String mainPriceName) {
		this.mainPriceName = mainPriceName;
	}

	public String getMainProductName() {
		return mainProductName;
	}

	public void setMainProductName(String mainProductName) {
		this.mainProductName = mainProductName;
	}

	public String getMainProductPicture() {
		return mainProductPicture;
	}

	public void setMainProductPicture(String mainProductPicture) {
		this.mainProductPicture = mainProductPicture;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public BusinessArea getBandBusinessAreaId() {
		return bandBusinessAreaId;
	}

	public void setBandBusinessAreaId(BusinessArea bandBusinessAreaId) {
		this.bandBusinessAreaId = bandBusinessAreaId;
	}

	public String getBandBusinessAreaName() {
		return bandBusinessAreaName;
	}

	public void setBandBusinessAreaName(String bandBusinessAreaName) {
		this.bandBusinessAreaName = bandBusinessAreaName;
	}

	public BandChannel getBandChannelID() {
		return bandChannelID;
	}

	public void setBandChannelID(BandChannel bandChannelID) {
		this.bandChannelID = bandChannelID;
	}

	public String getBandChannelName() {
		return bandChannelName;
	}

	public void setBandChannelName(String bandChannelName) {
		this.bandChannelName = bandChannelName;
	}

	public String getContactUser() {
		return contactUser;
	}

	public void setContactUser(String contactUser) {
		this.contactUser = contactUser;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getBandDesc() {
		return bandDesc;
	}

	public void setBandDesc(String bandDesc) {
		this.bandDesc = bandDesc;
	}

	public String getCompanyNature() {
		return companyNature;
	}

	public void setCompanyNature(String companyNature) {
		this.companyNature = companyNature;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public Integer getInfoStatus() {
		return infoStatus;
	}

	public void setInfoStatus(Integer infoStatus) {
		this.infoStatus = infoStatus;
	}

	public Integer getInfoType() {
		return infoType;
	}

	public void setInfoType(Integer infoType) {
		this.infoType = infoType;
	}

	public Integer getInfoSource() {
		return infoSource;
	}

	public void setInfoSource(Integer infoSource) {
		this.infoSource = infoSource;
	}

	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public AppUser getCreatUser() {
		return creatUser;
	}

	public void setCreatUser(AppUser creatUser) {
		this.creatUser = creatUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((bandBusinessAreaId == null) ? 0 : bandBusinessAreaId
						.hashCode());
		result = prime
				* result
				+ ((bandBusinessAreaName == null) ? 0 : bandBusinessAreaName
						.hashCode());
		result = prime * result
				+ ((bandChannelID == null) ? 0 : bandChannelID.hashCode());
		result = prime * result
				+ ((bandChannelName == null) ? 0 : bandChannelName.hashCode());
		result = prime * result
				+ ((bandDesc == null) ? 0 : bandDesc.hashCode());
		result = prime * result + ((bandId == null) ? 0 : bandId.hashCode());
		result = prime * result
				+ ((bandName == null) ? 0 : bandName.hashCode());
		result = prime * result
				+ ((bandStyleId == null) ? 0 : bandStyleId.hashCode());
		result = prime * result
				+ ((bandStyleName == null) ? 0 : bandStyleName.hashCode());
		result = prime * result
				+ ((companyAddress == null) ? 0 : companyAddress.hashCode());
		result = prime * result
				+ ((companyNature == null) ? 0 : companyNature.hashCode());
		result = prime * result
				+ ((contactPhone == null) ? 0 : contactPhone.hashCode());
		result = prime * result
				+ ((contactUser == null) ? 0 : contactUser.hashCode());
		result = prime * result
				+ ((creatDate == null) ? 0 : creatDate.hashCode());
		result = prime * result
				+ ((creatUser == null) ? 0 : creatUser.hashCode());
		result = prime * result
				+ ((floorNumId == null) ? 0 : floorNumId.hashCode());
		result = prime * result
				+ ((floorNumName == null) ? 0 : floorNumName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((infoSource == null) ? 0 : infoSource.hashCode());
		result = prime * result
				+ ((infoStatus == null) ? 0 : infoStatus.hashCode());
		result = prime * result
				+ ((infoType == null) ? 0 : infoType.hashCode());
		result = prime * result
				+ ((mainPriceId == null) ? 0 : mainPriceId.hashCode());
		result = prime * result
				+ ((mainPriceName == null) ? 0 : mainPriceName.hashCode());
		result = prime * result
				+ ((mainProductName == null) ? 0 : mainProductName.hashCode());
		result = prime
				* result
				+ ((mainProductPicture == null) ? 0 : mainProductPicture
						.hashCode());
		result = prime * result
				+ ((proClassId == null) ? 0 : proClassId.hashCode());
		result = prime * result
				+ ((proClassName == null) ? 0 : proClassName.hashCode());
		result = prime * result
				+ ((productLine == null) ? 0 : productLine.hashCode());
		result = prime * result
				+ ((saleSroteDesc == null) ? 0 : saleSroteDesc.hashCode());
		result = prime * result
				+ ((saleStoreName == null) ? 0 : saleStoreName.hashCode());
		result = prime * result
				+ ((saleStoreid == null) ? 0 : saleStoreid.hashCode());
		result = prime * result + ((webSite == null) ? 0 : webSite.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final InfoPoor other = (InfoPoor) obj;
		if (bandBusinessAreaId == null) {
			if (other.bandBusinessAreaId != null)
				return false;
		} else if (!bandBusinessAreaId.equals(other.bandBusinessAreaId))
			return false;
		if (bandBusinessAreaName == null) {
			if (other.bandBusinessAreaName != null)
				return false;
		} else if (!bandBusinessAreaName.equals(other.bandBusinessAreaName))
			return false;
		if (bandChannelID == null) {
			if (other.bandChannelID != null)
				return false;
		} else if (!bandChannelID.equals(other.bandChannelID))
			return false;
		if (bandChannelName == null) {
			if (other.bandChannelName != null)
				return false;
		} else if (!bandChannelName.equals(other.bandChannelName))
			return false;
		if (bandDesc == null) {
			if (other.bandDesc != null)
				return false;
		} else if (!bandDesc.equals(other.bandDesc))
			return false;
		if (bandId == null) {
			if (other.bandId != null)
				return false;
		} else if (!bandId.equals(other.bandId))
			return false;
		if (bandName == null) {
			if (other.bandName != null)
				return false;
		} else if (!bandName.equals(other.bandName))
			return false;
		if (bandStyleId == null) {
			if (other.bandStyleId != null)
				return false;
		} else if (!bandStyleId.equals(other.bandStyleId))
			return false;
		if (bandStyleName == null) {
			if (other.bandStyleName != null)
				return false;
		} else if (!bandStyleName.equals(other.bandStyleName))
			return false;
		if (companyAddress == null) {
			if (other.companyAddress != null)
				return false;
		} else if (!companyAddress.equals(other.companyAddress))
			return false;
		if (companyNature == null) {
			if (other.companyNature != null)
				return false;
		} else if (!companyNature.equals(other.companyNature))
			return false;
		if (contactPhone == null) {
			if (other.contactPhone != null)
				return false;
		} else if (!contactPhone.equals(other.contactPhone))
			return false;
		if (contactUser == null) {
			if (other.contactUser != null)
				return false;
		} else if (!contactUser.equals(other.contactUser))
			return false;
		if (creatDate == null) {
			if (other.creatDate != null)
				return false;
		} else if (!creatDate.equals(other.creatDate))
			return false;
		if (creatUser == null) {
			if (other.creatUser != null)
				return false;
		} else if (!creatUser.equals(other.creatUser))
			return false;
		if (floorNumId == null) {
			if (other.floorNumId != null)
				return false;
		} else if (!floorNumId.equals(other.floorNumId))
			return false;
		if (floorNumName == null) {
			if (other.floorNumName != null)
				return false;
		} else if (!floorNumName.equals(other.floorNumName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (infoSource == null) {
			if (other.infoSource != null)
				return false;
		} else if (!infoSource.equals(other.infoSource))
			return false;
		if (infoStatus == null) {
			if (other.infoStatus != null)
				return false;
		} else if (!infoStatus.equals(other.infoStatus))
			return false;
		if (infoType == null) {
			if (other.infoType != null)
				return false;
		} else if (!infoType.equals(other.infoType))
			return false;
		if (mainPriceId == null) {
			if (other.mainPriceId != null)
				return false;
		} else if (!mainPriceId.equals(other.mainPriceId))
			return false;
		if (mainPriceName == null) {
			if (other.mainPriceName != null)
				return false;
		} else if (!mainPriceName.equals(other.mainPriceName))
			return false;
		if (mainProductName == null) {
			if (other.mainProductName != null)
				return false;
		} else if (!mainProductName.equals(other.mainProductName))
			return false;
		if (mainProductPicture == null) {
			if (other.mainProductPicture != null)
				return false;
		} else if (!mainProductPicture.equals(other.mainProductPicture))
			return false;
		if (proClassId == null) {
			if (other.proClassId != null)
				return false;
		} else if (!proClassId.equals(other.proClassId))
			return false;
		if (proClassName == null) {
			if (other.proClassName != null)
				return false;
		} else if (!proClassName.equals(other.proClassName))
			return false;
		if (productLine == null) {
			if (other.productLine != null)
				return false;
		} else if (!productLine.equals(other.productLine))
			return false;
		if (saleSroteDesc == null) {
			if (other.saleSroteDesc != null)
				return false;
		} else if (!saleSroteDesc.equals(other.saleSroteDesc))
			return false;
		if (saleStoreName == null) {
			if (other.saleStoreName != null)
				return false;
		} else if (!saleStoreName.equals(other.saleStoreName))
			return false;
		if (saleStoreid == null) {
			if (other.saleStoreid != null)
				return false;
		} else if (!saleStoreid.equals(other.saleStoreid))
			return false;
		if (webSite == null) {
			if (other.webSite != null)
				return false;
		} else if (!webSite.equals(other.webSite))
			return false;
		return true;
	}

	
}
