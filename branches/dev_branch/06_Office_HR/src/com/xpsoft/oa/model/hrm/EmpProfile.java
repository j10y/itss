package com.xpsoft.oa.model.hrm;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class EmpProfile extends BaseModel {
	/* 18 */public static short CHECK_FLAG_NONE = 0;
	/* 19 */public static short CHECK_FLAG_PASS = 1;
	/* 20 */public static short CHECK_FLAG_NOT_PASS = 2;

	/* 22 */public static short DELETE_FLAG_NOT = 0;
	/* 23 */public static short DELETE_FLAG_HAD = 1;
	protected Long profileId;
	protected String profileNo;
	protected String fullname;
	protected String address;
	protected Date birthday;
	protected String homeZip;
	protected String sex;
	protected String marriage;
	protected String designation;
	protected String position;
	protected String phone;
	protected String mobile;
	protected String openBank;
	protected String bankNo;
	protected String qq;
	protected String email;
	protected String hobby;
	protected String religion;
	protected String party;
	protected String nationality;
	protected String race;
	protected String birthPlace;
	protected String eduDegree;
	protected String eduMajor;
	protected String eduCollege;
	protected Date startWorkDate;
	protected String eduCase;
	protected String awardPunishCase;
	protected String trainingCase;
	protected String workCase;
	protected String idCard;
	protected String photo;
	protected String standardMiNo;
	protected BigDecimal standardMoney;
	protected String standardName;
	protected String creator;
	protected Date createtime;
	protected String checkName;
	protected Date checktime;
	protected Short approvalStatus;
	protected String memo;
	protected String depName;
	protected Long depId;
	protected Short delFlag;
	protected String opprovalOpinion;
	protected Long userId;
	protected Long jobId;
	protected StandSalary standSalary;
	
	//社保、公积金、绩效系数
	protected BigDecimal provident;//公积金
	protected BigDecimal insurance;//保险
	protected BigDecimal perCoefficient;//绩效基数, performanceCoefficient
	//入职时间、离职时间
	protected Date accessionTime;//入职时间
	
	protected Date departureTime;//离职时间
	
	protected Date positiveTime;//转正时间
	
	protected Date realPositiveTime;//实际转正时间
	
	//是否离职
	private Integer isDepart;//
	
	private Integer organization;//编制 1是正编 0还是非编
	
	
	public Integer getOrganization() {
		return organization;
	}

	public void setOrganization(Integer organization) {
		this.organization = organization;
	}

	public Integer getIsDepart() {
		return isDepart;
	}

	public void setIsDepart(Integer isDepart) {
		this.isDepart = isDepart;
	}

	public Long getJobId() {
		/* 77 */return this.jobId;
	}

	public void setJobId(Long jobId) {
		/* 81 */this.jobId = jobId;
	}

	public EmpProfile() {
	}

	public EmpProfile(Long in_profileId) {
		/* 97 */setProfileId(in_profileId);
	}

	public StandSalary getStandSalary() {
		/* 110 */return this.standSalary;
	}

	public void setStandSalary(StandSalary in_standSalary) {
		/* 114 */this.standSalary = in_standSalary;
	}

	public Long getProfileId() {
		/* 123 */return this.profileId;
	}

	public void setProfileId(Long aValue) {
		/* 130 */this.profileId = aValue;
	}

	public String getProfileNo() {
		/* 138 */return this.profileNo;
	}

	public void setProfileNo(String aValue) {
		/* 146 */this.profileNo = aValue;
	}

	public String getFullname() {
		/* 154 */return this.fullname;
	}

	public void setFullname(String aValue) {
		/* 162 */this.fullname = aValue;
	}

	public String getAddress() {
		/* 170 */return this.address;
	}

	public void setAddress(String aValue) {
		/* 177 */this.address = aValue;
	}

	public Date getBirthday() {
		/* 185 */return this.birthday;
	}

	public void setBirthday(Date aValue) {
		/* 192 */this.birthday = aValue;
	}

	public String getHomeZip() {
		/* 200 */return this.homeZip;
	}

	public void setHomeZip(String aValue) {
		/* 207 */this.homeZip = aValue;
	}

	public String getSex() {
		/* 215 */return this.sex;
	}

	public void setSex(String aValue) {
		/* 222 */this.sex = aValue;
	}

	public String getMarriage() {
		/* 232 */return this.marriage;
	}

	public void setMarriage(String aValue) {
		/* 239 */this.marriage = aValue;
	}

	public String getDesignation() {
		/* 247 */return this.designation;
	}

	public void setDesignation(String aValue) {
		/* 254 */this.designation = aValue;
	}

	public String getPosition() {
		/* 283 */return this.position;
	}

	public void setPosition(String aValue) {
		/* 290 */this.position = aValue;
	}

	public String getPhone() {
		/* 298 */return this.phone;
	}

	public void setPhone(String aValue) {
		/* 305 */this.phone = aValue;
	}

	public String getMobile() {
		/* 313 */return this.mobile;
	}

	public void setMobile(String aValue) {
		/* 320 */this.mobile = aValue;
	}

	public String getOpenBank() {
		/* 328 */return this.openBank;
	}

	public void setOpenBank(String aValue) {
		/* 335 */this.openBank = aValue;
	}

	public String getBankNo() {
		/* 343 */return this.bankNo;
	}

	public void setBankNo(String aValue) {
		/* 350 */this.bankNo = aValue;
	}

	public String getQq() {
		/* 358 */return this.qq;
	}

	public void setQq(String aValue) {
		/* 365 */this.qq = aValue;
	}

	public String getEmail() {
		/* 373 */return this.email;
	}

	public void setEmail(String aValue) {
		/* 380 */this.email = aValue;
	}

	public String getHobby() {
		/* 388 */return this.hobby;
	}

	public void setHobby(String aValue) {
		/* 395 */this.hobby = aValue;
	}

	public String getReligion() {
		/* 403 */return this.religion;
	}

	public void setReligion(String aValue) {
		/* 410 */this.religion = aValue;
	}

	public String getParty() {
		/* 418 */return this.party;
	}

	public void setParty(String aValue) {
		/* 425 */this.party = aValue;
	}

	public String getNationality() {
		/* 433 */return this.nationality;
	}

	public void setNationality(String aValue) {
		/* 440 */this.nationality = aValue;
	}

	public String getRace() {
		/* 448 */return this.race;
	}

	public void setRace(String aValue) {
		/* 455 */this.race = aValue;
	}

	public String getBirthPlace() {
		/* 463 */return this.birthPlace;
	}

	public void setBirthPlace(String aValue) {
		/* 470 */this.birthPlace = aValue;
	}

	public String getEduDegree() {
		/* 478 */return this.eduDegree;
	}

	public void setEduDegree(String aValue) {
		/* 485 */this.eduDegree = aValue;
	}

	public String getEduMajor() {
		/* 493 */return this.eduMajor;
	}

	public void setEduMajor(String aValue) {
		/* 500 */this.eduMajor = aValue;
	}

	public String getEduCollege() {
		/* 508 */return this.eduCollege;
	}

	public void setEduCollege(String aValue) {
		/* 515 */this.eduCollege = aValue;
	}

	public Date getStartWorkDate() {
		/* 523 */return this.startWorkDate;
	}

	public void setStartWorkDate(Date aValue) {
		/* 530 */this.startWorkDate = aValue;
	}

	public String getEduCase() {
		/* 538 */return this.eduCase;
	}

	public void setEduCase(String aValue) {
		/* 545 */this.eduCase = aValue;
	}

	public String getAwardPunishCase() {
		/* 553 */return this.awardPunishCase;
	}

	public void setAwardPunishCase(String aValue) {
		/* 560 */this.awardPunishCase = aValue;
	}

	public String getTrainingCase() {
		/* 568 */return this.trainingCase;
	}

	public void setTrainingCase(String aValue) {
		/* 575 */this.trainingCase = aValue;
	}

	public String getWorkCase() {
		/* 583 */return this.workCase;
	}

	public void setWorkCase(String aValue) {
		/* 590 */this.workCase = aValue;
	}

	public String getIdCard() {
		/* 598 */return this.idCard;
	}

	public void setIdCard(String aValue) {
		/* 605 */this.idCard = aValue;
	}

	public String getPhoto() {
		/* 613 */return this.photo;
	}

	public void setPhoto(String aValue) {
		/* 620 */this.photo = aValue;
	}

	public String getStandardMiNo() {
		/* 628 */return this.standardMiNo;
	}

	public void setStandardMiNo(String aValue) {
		/* 635 */this.standardMiNo = aValue;
	}

	public BigDecimal getStandardMoney() {
		/* 643 */return this.standardMoney;
	}

	public void setStandardMoney(BigDecimal aValue) {
		/* 650 */this.standardMoney = aValue;
	}

	public String getStandardName() {
		/* 658 */return this.standardName;
	}

	public void setStandardName(String aValue) {
		/* 665 */this.standardName = aValue;
	}

	public Long getStandardId() {
		/* 672 */return getStandSalary() == null ? null : getStandSalary()
				.getStandardId();
	}

	public void setStandardId(Long aValue) {
		/* 679 */if (aValue == null) {
			/* 680 */this.standSalary = null;
			/* 681 */} else if (this.standSalary == null) {
			/* 682 */this.standSalary = new StandSalary(aValue);
			/* 683 */this.standSalary.setVersion(new Integer(0));
		} else {
			/* 685 */this.standSalary.setStandardId(aValue);
		}
	}

	public String getCreator() {
		/* 694 */return this.creator;
	}

	public void setCreator(String aValue) {
		/* 701 */this.creator = aValue;
	}

	public Date getCreatetime() {
		/* 709 */return this.createtime;
	}

	public void setCreatetime(Date aValue) {
		/* 716 */this.createtime = aValue;
	}

	public String getCheckName() {
		/* 724 */return this.checkName;
	}

	public void setCheckName(String aValue) {
		/* 731 */this.checkName = aValue;
	}

	public Date getChecktime() {
		/* 739 */return this.checktime;
	}

	public void setChecktime(Date aValue) {
		/* 746 */this.checktime = aValue;
	}

	public Short getApprovalStatus() {
		/* 757 */return this.approvalStatus;
	}

	public void setApprovalStatus(Short aValue) {
		/* 764 */this.approvalStatus = aValue;
	}

	public String getMemo() {
		/* 772 */return this.memo;
	}

	public void setMemo(String aValue) {
		/* 779 */this.memo = aValue;
	}

	public String getDepName() {
		/* 787 */return this.depName;
	}

	public void setDepName(String aValue) {
		/* 794 */this.depName = aValue;
	}

	public Long getDepId() {
		/* 802 */return this.depId;
	}

	public void setDepId(Long aValue) {
		/* 809 */this.depId = aValue;
	}

	public Short getDelFlag() {
		/* 819 */return this.delFlag;
	}

	public void setDelFlag(Short aValue) {
		/* 827 */this.delFlag = aValue;
	}

	public String getOpprovalOpinion() {
		/* 831 */return this.opprovalOpinion;
	}

	public void setOpprovalOpinion(String opprovalOpinion) {
		/* 835 */this.opprovalOpinion = opprovalOpinion;
	}

	public Long getUserId() {
		/* 839 */return this.userId;
	}

	public void setUserId(Long userId) {
		/* 843 */this.userId = userId;
	}
	
	public BigDecimal getProvident() {
		return provident;
	}

	public void setProvident(BigDecimal provident) {
		this.provident = provident;
	}

	public BigDecimal getInsurance() {
		return insurance;
	}

	public void setInsurance(BigDecimal insurance) {
		this.insurance = insurance;
	}

	public BigDecimal getPerCoefficient() {
		return perCoefficient;
	}

	public void setPerCoefficient(BigDecimal perCoefficient) {
		this.perCoefficient = perCoefficient;
	}

	public Date getAccessionTime() {
		return accessionTime;
	}

	public void setAccessionTime(Date accessionTime) {
		this.accessionTime = accessionTime;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
		if(departureTime!=null){
			this.setIsDepart(1);
		}else{
			this.setIsDepart(0);
		}
	}
	
	public Date getPositiveTime() {
		return positiveTime;
	}

	public void setPositiveTime(Date positiveTime) {
		this.positiveTime = positiveTime;
	}
	
	public Date getRealPositiveTime() {
		return realPositiveTime;
	}

	public void setRealPositiveTime(Date realPositiveTime) {
		this.realPositiveTime = realPositiveTime;
	}

	public boolean equals(Object object) {
		/* 850 */if (!(object instanceof EmpProfile)) {
			/* 851 */return false;
		}
		/* 853 */EmpProfile rhs = (EmpProfile) object;
		/* 854 */return new EqualsBuilder()
		/* 855 */.append(this.profileId, rhs.profileId)
		/* 856 */.append(this.profileNo, rhs.profileNo)
		/* 857 */.append(this.fullname, rhs.fullname)
		/* 858 */.append(this.address, rhs.address)
		/* 859 */.append(this.birthday, rhs.birthday)
		/* 860 */.append(this.homeZip, rhs.homeZip)
		/* 861 */.append(this.sex, rhs.sex)
		/* 862 */.append(this.marriage, rhs.marriage)
		/* 863 */.append(this.designation, rhs.designation)
		/* 864 */.append(this.position, rhs.position)
		/* 865 */.append(this.phone, rhs.phone)
		/* 866 */.append(this.mobile, rhs.mobile)
		/* 867 */.append(this.openBank, rhs.openBank)
		/* 868 */.append(this.bankNo, rhs.bankNo)
		/* 869 */.append(this.qq, rhs.qq)
		/* 870 */.append(this.email, rhs.email)
		/* 871 */.append(this.hobby, rhs.hobby)
		/* 872 */.append(this.religion, rhs.religion)
		/* 873 */.append(this.party, rhs.party)
		/* 874 */.append(this.nationality, rhs.nationality)
		/* 875 */.append(this.race, rhs.race)
		/* 876 */.append(this.birthPlace, rhs.birthPlace)
		/* 877 */.append(this.eduDegree, rhs.eduDegree)
		/* 878 */.append(this.eduMajor, rhs.eduMajor)
		/* 879 */.append(this.eduCollege, rhs.eduCollege)
		/* 880 */.append(this.startWorkDate, rhs.startWorkDate)
		/* 881 */.append(this.eduCase, rhs.eduCase)
		/* 882 */.append(this.awardPunishCase, rhs.awardPunishCase)
		/* 883 */.append(this.trainingCase, rhs.trainingCase)
		/* 884 */.append(this.workCase, rhs.workCase)
		/* 885 */.append(this.idCard, rhs.idCard)
		/* 886 */.append(this.photo, rhs.photo)
		/* 887 */.append(this.standardMiNo, rhs.standardMiNo)
		/* 888 */.append(this.standardMoney, rhs.standardMoney)
		/* 889 */.append(this.standardName, rhs.standardName)
		/* 890 */.append(this.creator, rhs.creator)
		/* 891 */.append(this.createtime, rhs.createtime)
		/* 892 */.append(this.checkName, rhs.checkName)
		/* 893 */.append(this.checktime, rhs.checktime)
		/* 894 */.append(this.approvalStatus, rhs.approvalStatus)
		/* 895 */.append(this.opprovalOpinion, rhs.opprovalOpinion)
		/* 896 */.append(this.memo, rhs.memo)
		/* 897 */.append(this.depName, rhs.depName)
		/* 898 */.append(this.depId, rhs.depId)
		/* 899 */.append(this.delFlag, rhs.delFlag)
		/* 900 */.append(this.userId, rhs.userId)		
		/* 895 */.append(this.perCoefficient, rhs.perCoefficient)
		/* 896 */.append(this.provident, rhs.provident)
		/* 897 */.append(this.insurance, rhs.insurance)
		/* 898 */.append(this.accessionTime, rhs.accessionTime)
		/* 899 */.append(this.departureTime, rhs.departureTime)
		.append(this.positiveTime, rhs.positiveTime)
		.append(this.realPositiveTime, rhs.realPositiveTime)
		/* 901 */.isEquals();
	}

	public int hashCode() {
		/* 908 */return new HashCodeBuilder(-82280557, -700257973)
		/* 909 */.append(this.profileId)
		/* 910 */.append(this.profileNo)
		/* 911 */.append(this.fullname)
		/* 912 */.append(this.address)
		/* 913 */.append(this.birthday)
		/* 914 */.append(this.homeZip)
		/* 915 */.append(this.sex)
		/* 916 */.append(this.marriage)
		/* 917 */.append(this.designation)
		/* 918 */.append(this.position)
		/* 919 */.append(this.phone)
		/* 920 */.append(this.mobile)
		/* 921 */.append(this.openBank)
		/* 922 */.append(this.bankNo)
		/* 923 */.append(this.qq)
		/* 924 */.append(this.email)
		/* 925 */.append(this.hobby)
		/* 926 */.append(this.religion)
		/* 927 */.append(this.party)
		/* 928 */.append(this.nationality)
		/* 929 */.append(this.race)
		/* 930 */.append(this.birthPlace)
		/* 931 */.append(this.eduDegree)
		/* 932 */.append(this.eduMajor)
		/* 933 */.append(this.eduCollege)
		/* 934 */.append(this.startWorkDate)
		/* 935 */.append(this.eduCase)
		/* 936 */.append(this.awardPunishCase)
		/* 937 */.append(this.trainingCase)
		/* 938 */.append(this.workCase)
		/* 939 */.append(this.idCard)
		/* 940 */.append(this.photo)
		/* 941 */.append(this.standardMiNo)
		/* 942 */.append(this.standardMoney)
		/* 943 */.append(this.standardName)
		/* 944 */.append(this.creator)
		/* 945 */.append(this.createtime)
		/* 946 */.append(this.checkName)
		/* 947 */.append(this.checktime)
		/* 948 */.append(this.approvalStatus)
		/* 949 */.append(this.memo)
		/* 950 */.append(this.depName)
		/* 951 */.append(this.depId)
		/* 952 */.append(this.delFlag)
		/* 953 */.append(this.opprovalOpinion)
		/* 954 */.append(this.userId)
		/* 954 */.append(this.perCoefficient)
		/* 896 */.append(this.provident)
		/* 897 */.append(this.insurance)
		/* 898 */.append(this.accessionTime)
		/* 899 */.append(this.departureTime)
		.append(this.positiveTime)
		.append(this.realPositiveTime)
		/* 955 */.toHashCode();
	}

	public String toString() {
		/* 962 */return new ToStringBuilder(this)
		/* 963 */.append("profileId", this.profileId)
		/* 964 */.append("profileNo", this.profileNo)
		/* 965 */.append("fullname", this.fullname)
		/* 966 */.append("address", this.address)
		/* 967 */.append("birthday", this.birthday)
		/* 968 */.append("homeZip", this.homeZip)
		/* 969 */.append("sex", this.sex)
		/* 970 */.append("marriage", this.marriage)
		/* 971 */.append("designation", this.designation)
		/* 972 */.append("position", this.position)
		/* 973 */.append("phone", this.phone)
		/* 974 */.append("mobile", this.mobile)
		/* 975 */.append("openBank", this.openBank)
		/* 976 */.append("bankNo", this.bankNo)
		/* 977 */.append("qq", this.qq)
		/* 978 */.append("email", this.email)
		/* 979 */.append("hobby", this.hobby)
		/* 980 */.append("religion", this.religion)
		/* 981 */.append("party", this.party)
		/* 982 */.append("nationality", this.nationality)
		/* 983 */.append("race", this.race)
		/* 984 */.append("birthPlace", this.birthPlace)
		/* 985 */.append("eduDegree", this.eduDegree)
		/* 986 */.append("eduMajor", this.eduMajor)
		/* 987 */.append("eduCollege", this.eduCollege)
		/* 988 */.append("startWorkDate", this.startWorkDate)
		/* 989 */.append("eduCase", this.eduCase)
		/* 990 */.append("awardPunishCase", this.awardPunishCase)
		/* 991 */.append("trainingCase", this.trainingCase)
		/* 992 */.append("workCase", this.workCase)
		/* 993 */.append("idCard", this.idCard)
		/* 994 */.append("photo", this.photo)
		/* 995 */.append("standardMiNo", this.standardMiNo)
		/* 996 */.append("standardMoney", this.standardMoney)
		/* 997 */.append("standardName", this.standardName)
		/* 998 */.append("creator", this.creator)
		/* 999 */.append("createtime", this.createtime)
		/* 1000 */.append("checkName", this.checkName)
		/* 1001 */.append("checktime", this.checktime)
		/* 1002 */.append("approvalStatus", this.approvalStatus)
		/* 1003 */.append("memo", this.memo)
		/* 1004 */.append("depName", this.depName)
		/* 1005 */.append("depId", this.depId)
		/* 1006 */.append("delFlag", this.delFlag)
		/* 1007 */.append("opprovalOpinion", this.opprovalOpinion)
		/* 1008 */.append("userId", this.userId)
		/* 895 */.append("perCoefficient", this.perCoefficient)
		/* 896 */.append("provident", this.provident)
		/* 897 */.append("insurance", this.insurance)
		/* 898 */.append("accessionTime", this.accessionTime)
		/* 899 */.append("departureTime", this.departureTime)
		/* 899 */.append("positiveTime", this.positiveTime)
		/* 899 */.append("realPositiveTime", this.realPositiveTime)
		/* 1009 */.toString();
	}
}
