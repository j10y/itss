<%@ page pageEncoding="UTF-8"%>
<%
	String basePath=request.getContextPath();
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.xpsoft.core.util.AppUtil"%>

<%@page import="com.xpsoft.oa.service.hrm.EmpProfileHistService"%>
<%@page import="com.xpsoft.oa.model.hrm.EmpProfileHist"%>

<%
	String profileHistId = request.getParameter("profileHistId");
	EmpProfileHistService empProfileHistService = (EmpProfileHistService)AppUtil.getBean("empProfileHistService");
	EmpProfileHist empProfileHist = empProfileHistService.get(new Long(profileHistId));
	request.setAttribute("empProfileHist",empProfileHist);
%>
<table class="table-info" style="table-layout: fixed;" cellpadding="0" cellspacing="1" width="98%" align="center">
	
		<tr>
			<td rowspan="7" width="15%">
				&nbsp;<img title="${empProfileHist.fullname}" width="88" height="120" src="${empProfileHist.photo}">
			</td>
		</tr>
		<tr >
			<th width="15%">
				档案号
			</th >
			<td colspan="3">
					${empProfileHist.profileNo}
					<c:if test="${empProfileHist.approvalStatus==1}"><img title="通过审核" src="<%=basePath%>/images/flag/customer/effective.png"/></c:if>
				   <c:if test="${empProfileHist.approvalStatus==2}"><img title="没通过审核" src="<%=basePath%>/images/flag/customer/invalid.png"/></c:if>
			</td>
		</tr>
		
		<tr>
			<th width="15%">
				姓名
			</th>
			<td width="30%">
				${empProfileHist.fullname }
			</td>
			<th width="15%">
				宗教信仰
			</th>
			<td width="30%">
				${empProfileHist.religion }
			</td>
		</tr>
		
		<tr>
			<th width="20%">
				身份证号
			</th>
			<td>
				${empProfileHist.idCard }
			</td>
			<th width="20%">
				政治面貌
			</th>
			<td>
				${empProfileHist.party }
			</td>
		</tr>
		
		<tr>
			<th width="20%">
				出生日期
			</th>
			<td>
			<fmt:formatDate value="${empProfileHist.birthday}" pattern="yyyy-MM-dd"/>
			</td>
			<th width="20%">
				国籍
			</th>
			<td>
				${empProfileHist.nationality }
			</td>
		</tr>
		
		<tr>
			<th width="20%">
				性别
			</th>
			<td>
				${empProfileHist.sex }
			</td>
			<th width="20%">
				民族
			</th>
			<td>
				${empProfileHist.race }
			</td>
		</tr>
		
		<tr>
			<th width="20%">
				婚姻状况
			</th>
			<td>
				${empProfileHist.marriage }
			</td>
			<th width="20%">
				出生地
			</th>
			<td>
				${empProfileHist.birthPlace }
			</td>
		</tr>
</table>
<table class="table-info" style="table-layout: fixed;word-spacing: normal;" cellpadding="0" cellspacing="1" width="98%" align="center">
		<tr >
			<th width="15%">
				家庭地址
			</th>
			<td>
				${empProfileHist.address }
			</td>
			<th width="15%">
				电话号码
			</th>
			<td>
				${empProfileHist.phone }
			</td>
		</tr>
		<tr>
			<th width="15%">
				家庭邮编
			</th>
			<td>
				${empProfileHist.homeZip }
			</td>
			<th  width="20%">
				QQ号码
			</th>
			<td>
				${empProfileHist.qq }
			</td>
		</tr>
		<tr>
			
			<th width="20%">
				手机号
			</th>
			<td>
				${empProfileHist.mobile}
			</td>
			<th width="20%">
				电子邮箱
			</th>
			<td>
				${empProfileHist.email }
			</td>
		</tr>
		<tr>
			<th width="20%">
				学历
			</th>
			<td>
				${empProfileHist.eduDegree }
			</td>
			<th width="20%">
				毕业院校
			</th>
			<td>
				${empProfileHist.eduCollege }
			</td>
		</tr>
		
		<tr>
			<th width="20%">
				专业
			</th>
			<td>
				${empProfileHist.eduMajor }
			</td>
			<th width="20%">
				参加工作时间
			</th>
			<td>
				<fmt:formatDate value="${empProfileHist.startWorkDate}" pattern="yyyy-MM-dd"/>
			</td>
		</tr>
		
		<tr>
			<th width="20%">
				教育背景
			</th>
			<td colspan="3">
				${empProfileHist.eduCase }
			</td>
		</tr>
		<tr>
			<th width="20%">
				职称
			</th>
			<td>
				${empProfileHist.designation }
			</td>
			<th width="20%">
				职位
			</th>
			<td>
				${empProfileHist.position }
			</td>
		</tr>
		
		<tr>
			<th width="20%">
				所属部门或公司
			</th>
			<td>
				${empProfileHist.depName }
			</td>
			<th width="20%">
				薪酬标准单名称
			</th>
			<td>
				${empProfileHist.standardName }
			</td>
		</tr>
		
		<tr>
			<th width="20%">
				开户银行
			</th>
			<td>
				${empProfileHist.openBank }
			</td>
			<th width="20%">
				薪酬标准号
			</th>
			<td>
				${empProfileHist.standardMiNo }
			</td>
		</tr>
		
		<tr>
			<th width="20%">
				银行账号
			</th>
			<td>
				${empProfileHist.bankNo }
			</td>
			<th width="20%">
				薪酬标准金额
			</th>
			<td>
				${empProfileHist.standardMoney }
			</td>
		</tr>
		
		<tr>
			<th width="80">
				培训情况
			</th>
			<td colspan="3">
				<div  style="width:700; overflow: auto;"><p>${empProfileHist.trainingCase }</p></div>
			</td>
		</tr>
		
		<tr>
			<th width="20%">
				奖惩情况
			</th>
			<td colspan="3">
				${empProfileHist.awardPunishCase }
			</td>
		</tr>
		
		<tr>
			<th width="20%">
				工作经历
			</th>
			<td colspan="3">
				${empProfileHist.workCase }
			</td>
		</tr>
		<tr>
			<th width="20%">
				个人爱好
			</th>
			<td colspan="3">
				${empProfileHist.hobby }
			</td>
		</tr>
		
		<tr>
			<th width="20%">
				备注
			</th>
			<td colspan="3">
				${empProfileHist.memo }
			</td>
		</tr>
</table>
<table class="table-info" style="table-layout: fixed;" cellpadding="0" cellspacing="1" width="98%" align="center">
 <tr>
		<th width="15%">
			建档人
		</th>
		<td>
			${empProfileHist.creator }
		</td>
		<th width="15%">
			建档时间
		</th>
		<td>
			<fmt:formatDate value="${empProfileHist.createtime}" pattern="yyyy-MM-dd"/>
		</td>
	</tr>
 <tr>
		<th width="15%">
			最后修改人
		</th>
		<td>
			${empProfileHist.modifiedUser.fullname }
		</td>
		<th width="15%">
			最后修改时间
		</th>
		<td>
			<fmt:formatDate value="${empProfileHist.modifiedDate}" pattern="yyyy-MM-dd"/>
		</td>
	</tr>
</table>	
	
	