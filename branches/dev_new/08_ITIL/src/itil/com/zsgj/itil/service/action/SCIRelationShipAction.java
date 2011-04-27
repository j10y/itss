package com.zsgj.itil.service.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.itil.actor.entity.Customer;
import com.zsgj.itil.actor.entity.CustomerType;
import com.zsgj.itil.config.entity.ConfigItem;
import com.zsgj.itil.service.entity.SCIRelationShip;
import com.zsgj.itil.service.entity.SCIRelationShipType;
import com.zsgj.itil.service.entity.ServiceCatalogue;
import com.zsgj.itil.service.entity.ServiceCatalogueContract;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemSLA;
import com.zsgj.itil.service.entity.ServiceItemSatisfaction;
import com.zsgj.itil.service.entity.ServiceItemType;
import com.zsgj.itil.service.service.SCIRelationShipService;
import com.zsgj.itil.service.service.ServiceCatalogueService;
import com.zsgj.itil.service.service.ServicePortfolioService;

public class SCIRelationShipAction  extends BaseAction{
	private SCIRelationShipService sciRelationShipService = (SCIRelationShipService) getBean("sciRelationShipService");
	private ServiceCatalogueService serviceCatalogueService = (ServiceCatalogueService) getBean("serviceCatalogueService");
	private ServicePortfolioService servicePortfolioService = (ServicePortfolioService) getBean("servicePortfolioService");
	/**
	 * 保存服务目录同时生成服务目录对应的根服务目录关系
	 * @Methods Name saveRootSCIRelationShip
	 * @Create In 2008-1-14 By lee
	 * @return String
	 * @throws Exception
	 */
	public String saveRootSCIRelationShip() throws Exception {
		HttpServletRequest request = super.getRequest();
		String id = request.getParameter("id");
		String servicePortfolioId = request.getParameter("sp");
		String name = request.getParameter("name");
		String descn = request.getParameter("descn");
		String valid = request.getParameter("validDate");
		String begin = request.getParameter("beginDate");
		String end = request.getParameter("endDate");
		String customer = request.getParameter("customer");
		String customerType = request.getParameter("customerType");
		String oldCatalogueId = request.getParameter("oldCatalogueId");
		String status = request.getParameter("status");
		String flag = request.getParameter("flag");
		//ServicePortfolio sp = servicePortfolioService.findServicePortfolioById(servicePortfolioId);//remove by lee for 废弃属性 in 20091121
		SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
		Date validDate = null;
		if(valid!="")
			validDate = df.parse(valid);		
		Date beginDate = null;
		if(begin!="")
			beginDate = df.parse(begin);
		Date endDate = null;
		if(end!="")
			endDate = df.parse(end);
		ServiceCatalogue newServiceCatalogue = new ServiceCatalogue();
		if(!id.equals("")){
			newServiceCatalogue.setId(Long.valueOf(id));
		}
		CustomerType custType = null;
		if(StringUtils.isNotBlank(customerType)){
			custType = (CustomerType) super.getService().find(CustomerType.class, customerType);
		}
		newServiceCatalogue.setCustomerType(custType);
		Customer cu=new Customer();
		if(StringUtils.isNotBlank(customer)){
			cu.setId(Long.valueOf(customer));
		}
		newServiceCatalogue.setCustomer(cu);
		
		
		newServiceCatalogue.setName(name);
		newServiceCatalogue.setDescn(descn);
//		if(typeFlag!=""){
//			newServiceCatalogue.setTypeFlag(Integer.valueOf(typeFlag));
//		}else{
//			newServiceCatalogue.setTypeFlag(null);
//		}
		//newServiceCatalogue.setSp(sp);//remove by lee for 废弃属性 in 20091121
		newServiceCatalogue.setRootFlag(Integer.valueOf(1));
		newServiceCatalogue.setValidDate(validDate);
		newServiceCatalogue.setBeginDate(beginDate);
		newServiceCatalogue.setEndDate(endDate);
		if("".equals(oldCatalogueId)||oldCatalogueId==null){
			newServiceCatalogue.setOldCatalogueId(null);
		}else{
			newServiceCatalogue.setOldCatalogueId(Long.valueOf(oldCatalogueId));
			
		}
		if("issue".equals(flag)){
			newServiceCatalogue.setStatus(ServiceCatalogue.STATUS_DRAFT);
		}else if("modify".equals(flag)){
			newServiceCatalogue.setStatus(ServiceCatalogue.STATUS_ALTER_DRAFT);
		}else{
			newServiceCatalogue.setStatus(ServiceCatalogue.STATUS_FINISHED);
		}
		ServiceCatalogue serviceCatalogue = serviceCatalogueService.save(newServiceCatalogue);
		String parentSCIRelationShipId=request.getParameter("parentSCIRelationShipId");
		SCIRelationShip textRS = sciRelationShipService.findRootRelationShipByRootCata(serviceCatalogue);
		if(textRS==null){
			SCIRelationShip newRelationShip = new SCIRelationShip();
			SCIRelationShip parentRelationShip = null;
			Integer order = 0;
			if(parentSCIRelationShipId!=null&&!parentSCIRelationShipId.equals("")){
				parentRelationShip = sciRelationShipService.findSCIRelationShipById(parentSCIRelationShipId);
				List<SCIRelationShip> childs = sciRelationShipService.findChildRelationShipByParent(parentRelationShip);
				order = childs.size();
				newRelationShip.setRootServiceCatalogue(parentRelationShip.getRootServiceCatalogue());
			}else{
				newRelationShip.setRootServiceCatalogue(serviceCatalogue);
			}
			newRelationShip.setParentRelationShip(parentRelationShip);
			newRelationShip.setServiceCatalogue(serviceCatalogue);
			newRelationShip.setTypeFlag("cata");
			newRelationShip.setOrder(Integer.valueOf(order)+1);
			SCIRelationShip newRS = sciRelationShipService.save(newRelationShip);
			textRS = newRS;
		}
		String curId = serviceCatalogue.getId().toString();
		String rootId = textRS.getId().toString();
		//++++++++++++++++sujs增加的保存服务目录合同的代码begin++++++++++++++++++++
		if(!curId.equals("")){
			sciRelationShipService.saveServiceCatalogueIdToContract(curId);
		}
		//++++++++++++++++sujs增加的保存服务目录合同的代码end++++++++++++++++++++
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println("{id:"+curId+"}");
		out.flush();
		out.close();
		return null;
	}
	
	
	/**废弃的方法
	 * 保存服务目录同时生成服务目录对应的根服务目录关系,并且是拷贝原来的关系
	 * @Methods Name saveRootSCIRelationShipAlter
	 * @Create In Apr 8, 2009 By Administrator
	 * @return
	 * @throws Exception String
	 */
	public String saveRootSCIRelationShipAlter() throws Exception {
		HttpServletRequest request = super.getRequest();
		String id = request.getParameter("id");//此id铁定为空，因为要保存一条新的记录
		String oldId = request.getParameter("oldId");//原id
		String servicePortfolioId = request.getParameter("sp");
		String name = request.getParameter("name");
		String descn = request.getParameter("descn");
		String valid = request.getParameter("validDate");
		String begin = request.getParameter("beginDate");
		String end = request.getParameter("endDate");
		String customer = request.getParameter("customer");
		String customerType = request.getParameter("customerType");
		String status = request.getParameter("status");
		String flag = request.getParameter("flag");
		//ServicePortfolio sp = servicePortfolioService.findServicePortfolioById(servicePortfolioId);//remove by lee for 废弃属性 in 20091121
		SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
		Date validDate = null;
		if(valid!="")
			validDate = df.parse(valid);		
		Date beginDate = null;
		if(begin!="")
			beginDate = df.parse(begin);
		Date endDate = null;
		if(end!="")
			endDate = df.parse(end);
		ServiceCatalogue newServiceCatalogue = new ServiceCatalogue();
		if(!id.equals("")){
			newServiceCatalogue.setId(Long.valueOf(id));
		}
		CustomerType custType = null;
		if(StringUtils.isNotBlank(customerType)){
			custType = (CustomerType) super.getService().find(CustomerType.class, customerType);
		}
		newServiceCatalogue.setCustomerType(custType);
		Customer cu=new Customer();
		if(StringUtils.isNotBlank(customer)){
			cu.setId(Long.valueOf(customer));
		}
		newServiceCatalogue.setCustomer(cu);
		
		
		newServiceCatalogue.setName(name);
		newServiceCatalogue.setDescn(descn);
//		if(typeFlag!=""){
//			newServiceCatalogue.setTypeFlag(Integer.valueOf(typeFlag));
//		}else{
//			newServiceCatalogue.setTypeFlag(null);
//		}
		//newServiceCatalogue.setSp(sp);//remove by lee for 废弃属性 in 20091121
		newServiceCatalogue.setRootFlag(Integer.valueOf(1));
		newServiceCatalogue.setValidDate(validDate);
		newServiceCatalogue.setBeginDate(beginDate);
		newServiceCatalogue.setEndDate(endDate);
		if("iusse".equals(flag)){
			newServiceCatalogue.setStatus(ServiceCatalogue.STATUS_DRAFT);
		}else{
			newServiceCatalogue.setStatus(ServiceCatalogue.STATUS_FINISHED);
		}
		ServiceCatalogue serviceCatalogue = serviceCatalogueService.save(newServiceCatalogue);
		String parentSCIRelationShipId=request.getParameter("parentSCIRelationShipId");//始终都为空，就没传过来这个参数
		SCIRelationShip textRS = sciRelationShipService.findRootRelationShipByRootCata(serviceCatalogue);
		if(textRS==null){
			SCIRelationShip newRelationShip = new SCIRelationShip();
			SCIRelationShip parentRelationShip = null;
			Integer order = 0;
			if(parentSCIRelationShipId!=null&&!parentSCIRelationShipId.equals("")){
				parentRelationShip = sciRelationShipService.findSCIRelationShipById(parentSCIRelationShipId);
				List<SCIRelationShip> childs = sciRelationShipService.findChildRelationShipByParent(parentRelationShip);
				order = childs.size();
				newRelationShip.setRootServiceCatalogue(parentRelationShip.getRootServiceCatalogue());
			}else{
				newRelationShip.setRootServiceCatalogue(serviceCatalogue);
			}
			newRelationShip.setParentRelationShip(parentRelationShip);
			newRelationShip.setServiceCatalogue(serviceCatalogue);
			newRelationShip.setTypeFlag("cata");
			newRelationShip.setOrder(Integer.valueOf(order)+1);
			SCIRelationShip newRS = sciRelationShipService.save(newRelationShip);
			textRS = newRS;
		}
		//通过老的id得到旗下所有的子在service
		ServiceCatalogue oldCatalogue = (ServiceCatalogue) super.getService().find(ServiceCatalogue.class, oldId, true);
		//SCIRelationShip oldParentRelationShip = sciRelationShipService.findRootRelationShipByRootCata(oldCatalogue);
		List<SCIRelationShip>  sList = sciRelationShipService.getChildSCIRelationShipsByServiceCata(oldCatalogue);
		for(SCIRelationShip ship :sList){
			if(ship!=null){
				SCIRelationShip newSCIRelationShip  = new SCIRelationShip(); 
				//BeanUtils.copyProperties(ship, newSCIRelationShip);
				//newSCIRelationShip.setId(null);
				newSCIRelationShip.setServiceCatalogue(ship.getServiceCatalogue());
				newSCIRelationShip.setServiceItem(ship.getServiceItem());
				newSCIRelationShip.setServiceItemFee(ship.getServiceItemFee());
				newSCIRelationShip.setTypeFlag(ship.getTypeFlag());
				newSCIRelationShip.setOrder(ship.getOrder());
				newSCIRelationShip.setRootServiceCatalogue(newServiceCatalogue);
				newSCIRelationShip.setParentRelationShip(textRS);
				super.getService().save(newSCIRelationShip);
				}
		}
		
		String curId = serviceCatalogue.getId().toString();
		String rootId = textRS.getId().toString();
		//++++++++++++++++sujs增加的保存服务目录合同的代码begin++++++++++++++++++++
		if(!curId.equals("")){
			sciRelationShipService.saveServiceCatalogueIdToContract(curId);
		}
		//++++++++++++++++sujs增加的保存服务目录合同的代码end++++++++++++++++++++
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println("{id:"+curId+"}");
		out.flush();
		out.close();
		return null;
	}
	/**
	 * 保存服务项价格
	 * @Methods Name saveSCIRelationShip
	 * @Create In Jan 16, 2009 By Administrator
	 * @return
	 * @throws Exception String
	 */
	public String saveSCIRelationShip() throws Exception{
		HttpServletRequest request = super.getRequest();
		String id = request.getParameter("id");
		String serviceItemFee = request.getParameter("serviceItemFee");
		String dispFlag = request.getParameter("dispFlag");
		SCIRelationShip sciRelationShip = serviceCatalogueService.findSCIRelationShip(id);
		if(serviceItemFee==null||serviceItemFee==""){
			serviceItemFee="0";
		}
		sciRelationShip.setServiceItemFee(Double.valueOf(serviceItemFee));
		
		sciRelationShip.setDispFlag(Integer.parseInt(dispFlag));
		sciRelationShipService.save(sciRelationShip);
		return null;
		
	}
	/**
	 * 保存子服务目录同时生成服务目录对应的根服务目录关系
	 * @Methods Name saveChildSCIRelationShip
	 * @Create In 2008-1-14 By lee
	 * @return String
	 * @throws Exception
	 */
	public String saveChildSCIRelationShip() throws Exception {
		HttpServletRequest request = super.getRequest();
		String id = request.getParameter("id");
		String serviceItemFee = request.getParameter("serviceItemFee");
		String dispFlag = request.getParameter("dispFlag");
		if(serviceItemFee==""||"".equals(serviceItemFee)){
			serviceItemFee="0";
		}
//		String servicePortfolioId = request.getParameter("sp");
//		String typeFlag = request.getParameter("typeFlag");
		String name = request.getParameter("name");
		String descn = request.getParameter("descn");
//		String valid = request.getParameter("validDate");
//		String begin = request.getParameter("beginDate");
//		String end = request.getParameter("endDate");
		if(id==null||id.equals("")){
			String parentRelationShipId = request.getParameter("parentId");
			
			SCIRelationShip parentRelationShip = sciRelationShipService.findSCIRelationShipById(parentRelationShipId);
			
			ServiceCatalogue rootServiceCatalogue = parentRelationShip.getRootServiceCatalogue();
			ServiceCatalogue newServiceCatalogue = new ServiceCatalogue();
			newServiceCatalogue.setName(name);
			newServiceCatalogue.setDescn(descn);
			//newServiceCatalogue.setSp(rootServiceCatalogue.getSp());//remove by lee for 废弃属性 in 20091121
			//newServiceCatalogue.setTypeFlag(rootServiceCatalogue.getTypeFlag());
			newServiceCatalogue.setBeginDate(rootServiceCatalogue.getBeginDate());
			newServiceCatalogue.setEndDate(rootServiceCatalogue.getEndDate());
			newServiceCatalogue.setBeginDate(rootServiceCatalogue.getBeginDate());
			newServiceCatalogue.setRootFlag(Integer.valueOf(0));
			ServiceCatalogue childServiceCatalogue = serviceCatalogueService.save(newServiceCatalogue);
			
			List childs = sciRelationShipService.findChildRelationShipByParent(parentRelationShip);
			Integer order = 1;
			if(childs!=null){
				order = childs.size()+1;
			}
			SCIRelationShip newRelationShip = new SCIRelationShip();
			newRelationShip.setRootServiceCatalogue(rootServiceCatalogue);
			newRelationShip.setParentRelationShip(parentRelationShip);
			newRelationShip.setServiceCatalogue(childServiceCatalogue);
			newRelationShip.setTypeFlag("cata");
			newRelationShip.setOrder(order);
			sciRelationShipService.save(newRelationShip);
			
		}else{
			String parentRelationShipId = request.getParameter("parentId");
			SCIRelationShip oRelationShip = sciRelationShipService.findSCIRelationShipById(parentRelationShipId);
			ServiceCatalogue childServiceCatalogue = serviceCatalogueService.findServiceCatalogueById(id);
			oRelationShip.setServiceItemFee(Double.valueOf(serviceItemFee));
			//modify by guoxl in 2009/5/11 begin 编辑时设置服务目录例外的值
			oRelationShip.setDispFlag(Integer.parseInt(dispFlag));
			//modify by guoxl in 2009/5/11 end
			childServiceCatalogue.setName(name);
			childServiceCatalogue.setDescn(descn);
			
			serviceCatalogueService.save(childServiceCatalogue);
		}

		return null;
	}
	/**
	 * 加载舒服目录树数据
	 * @Methods Name loadSCIRelationShip
	 * @Create In 2008-1-14 By lee
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	public String loadSCIRelationShip()throws Exception{
		HttpServletRequest request = super.getRequest();
		String parentId = request.getParameter("id");
		String rootId = request.getParameter("rootId");
		ServiceCatalogue serviceCatalogue = serviceCatalogueService.findServiceCatalogueById(rootId);
		SCIRelationShip rootSCIRelationShip = sciRelationShipService.findRootRelationShipByRootCata(serviceCatalogue);
		if(parentId.equals("")){
			parentId = rootSCIRelationShip.getId().toString();
		}
		if("".endsWith(rootId)){
			request.setAttribute("list", null);
		}else{
			SCIRelationShip parentRelationShip = sciRelationShipService.findSCIRelationShipById(parentId);
			List<SCIRelationShip> childs = sciRelationShipService.findChildRelationShipByParent(parentRelationShip);
			request.setAttribute("list", childs);
		}
		return "forRelationShipData";
	}
	/**
	 * 树的子递归
	 * @param parent
	 * @param childNum
	 * @param para
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	private List initSCIRelationShipChild(SCIRelationShip parent,int childNum ,List para){
		Set<SCIRelationShip> child = parent.getChildRelationShips();
		List list=new ArrayList();
		List subList=new ArrayList();
		List paramete=new ArrayList();
		int parent1=((Long)para.get(0)).intValue();
		int level=((Integer)para.get(1)).intValue()+1;
		int lft=((Integer)para.get(2)).intValue()+1;
		int rgt=0;
		String temp = "";
		for(SCIRelationShip item : child){
			//System.out.println("child name: " + item.getName());
			
			if(!item.getChildRelationShips().isEmpty()){
				paramete.clear();
				paramete.add(item.getId());
				paramete.add(level);
				paramete.add(lft);
				subList=this.initSCIRelationShipChild(item,item.getChildRelationShips().size(),paramete);
				temp+="{id:'"+item.getId()+"',";
				//modify by guoxl in 2009/5/11 begin
				if(Integer.valueOf(0).equals(item.getDispFlag())){
					temp+="name:'<font color=red>"+item.getName()+"</font>',";
				}else{
					temp+="name:'"+item.getName()+"',";
				}
				//modify by guoxl in 2009/5/11 end
				//temp+="name:'"+item.getName()+"',";
				String typeName = null;
				String typeFlag = item.getTypeFlag();
				if(typeFlag.equalsIgnoreCase(SCIRelationShip.SCI_TYPE_CATALOGUE)){
					typeName = "目录";
				}else{
					typeName = "服务项";
				}
				temp+="typeFlag:'"+typeName+"',";
				//temp+="typeFlag:'"+item.getTypeFlag()+"',";
				temp+="rootName:'"+item.getRootServiceCatalogue().getName()+"',";
				temp += "_parent:"+parent1+",";
				temp += "_level:"+level+",";
				temp += "_lft:"+lft+",";
				int subNum=0;
				if(!subList.get(1).equals("")){
				   subNum=((Integer) subList.get(1)).intValue();
				   childNum+=((Integer) subList.get(1)).intValue();
				}
				rgt=lft+subNum*2+1;
				temp += "_rgt:"+rgt+",";
				temp += "_is_leaf:"+false+"},"; 
				if(!subList.get(0).equals("")){
					temp +=subList.get(0);
				 }
				}
				else{
					temp+="{id:'"+item.getId()+"',";
					//modify by guoxl in 2009/5/11 begin
					if(Integer.valueOf(0).equals(item.getDispFlag())){
						temp+="name:'<font color=red>"+item.getName()+"</font>',";
					}else{
						temp+="name:'"+item.getName()+"',";
					}
//					temp+="name:'"+item.getName()+"',";
					//modify by guoxl in 2009/5/11 end
					String typeName = null;
					String typeFlag = item.getTypeFlag();
					if(typeFlag.equalsIgnoreCase(SCIRelationShip.SCI_TYPE_CATALOGUE)){
						typeName = "目录";
					}else{
						typeName = "服务项";
					}
					temp+="typeFlag:'"+typeName+"',";
				//	temp+="typeFlag:'"+item.getTypeFlag()+"',";
					temp+="rootName:'"+item.getRootServiceCatalogue().getName()+"',";
					temp += "_parent:"+parent1+",";
					temp += "_level:"+level+",";
					temp += "_lft:"+lft+",";
					rgt=lft+1;
					temp += "_rgt:"+rgt+",";
					temp += "_is_leaf:"+true+"},"; 
					 }
				
				  lft=rgt+1;
				
		}
		list.add(temp);
		list.add(childNum);
		return list;
	}
/**
 * 树的子递归
 * @param relationShip
 * @return
 */
	@SuppressWarnings("unchecked")
	private String genJson(SCIRelationShip relationShip){
		int parent=0;
		int level=1;
		int lft=1;
		int rgt=1;
		String json = "";
		List subList=new ArrayList();
		List paramete=new ArrayList();
		if(!relationShip.getChildRelationShips().isEmpty()){//先看看根关系是否有子
			paramete.add(relationShip.getId());
			paramete.add(level);
			paramete.add(lft);
			subList=this.initSCIRelationShipChild(relationShip,relationShip.getChildRelationShips().size(),paramete);
			json+="{id:'"+relationShip.getId()+"',";
			//modify by guoxl in 2009/5/11 begin
			if(Integer.valueOf(0).equals(relationShip.getDispFlag())){
				json+="name:'<font color=red>"+relationShip.getName()+"',";
			}else{
				json+="name:'"+relationShip.getName()+"</font>',";
			}
			//json+="name:'"+relationShip.getName()+"',";
			//modify by guoxl in 2009/5/11 end
			String typeName = null;
			String typeFlag = relationShip.getTypeFlag();
			if(typeFlag.equalsIgnoreCase(SCIRelationShip.SCI_TYPE_CATALOGUE)){
				typeName = "目录";
			}else{
				typeName = "服务项";
			}
			json+="typeFlag:'"+typeName+"',";
			json+="rootName:'"+relationShip.getRootServiceCatalogue().getName()+"',";
			json += "_parent:"+parent+",";
			json += "_level:"+level+",";
			json += "_lft:"+lft+",";
			int subNum=0;
			if(!subList.get(1).equals("")){
			   subNum=((Integer) subList.get(1)).intValue();//得到值得个数，第二个参数是存的子的总个数
			}
			rgt=lft+subNum*2+1;
			json += "_rgt:"+rgt+",";
			json += "_is_leaf:"+false+"},"; 
			if(!subList.get(0).equals("")){
				json +=subList.get(0);
			 }
			}
			else{
				json+="{id:'"+relationShip.getId()+"',";
				//modify by guoxl in 2009/5/11 begin
				if(Integer.valueOf(0).equals(relationShip.getDispFlag())){
					json+="name:'<font color=red>"+relationShip.getName()+"',";
				}else{
					json+="name:'"+relationShip.getName()+"</font>',";
				}
				//json+="name:'"+relationShip.getName()+"',";
				//modify by guoxl in 2009/5/11 begin
				String typeName = null;
				String typeFlag = relationShip.getTypeFlag();
				if(typeFlag.equalsIgnoreCase(SCIRelationShip.SCI_TYPE_CATALOGUE)){
					typeName = "目录";
				}else{
					typeName = "服务项";
				}
				json+="typeFlag:'"+typeName+"',";
				json+="rootName:'"+relationShip.getRootServiceCatalogue().getName()+"',";
				json += "_parent:"+parent+",";
				json += "_level:"+level+",";
				json += "_lft:"+lft+",";
				rgt=lft+1;
				json += "_rgt:"+rgt+",";
				json += "_is_leaf:"+true+"},"; 
				 }
			  lft=rgt+1;
		if(json.equals("")){
			json="[]";
		}else{
			json = json.substring(0, json.length()-1);
			json="["+json+"]";
		}
		return json;
	}
/**
 * 查询树的所有信息入口
 * @return
 * @throws Exception
 */
	public String listRelationShips() throws Exception {
		@SuppressWarnings("unused")
		String json="";
		String serviceCatalogueId = super.getRequest().getParameter("serviceCatalogueId");
		if(serviceCatalogueId==null){
			return null;	
		}else{
		   SCIRelationShip sciRelationShip = sciRelationShipService.findRootRelationShip(serviceCatalogueId);
		   if(sciRelationShip!=null){
		   json+= this.genJson(sciRelationShip);
		   }
		}
		try {
			super.getResponse().setCharacterEncoding("utf-8");
			super.getResponse().getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		
		return null;
	}
	
	public String removeRelationShip() throws Exception {
		String dataId = super.getRequest().getParameter("dataId");
		
		return null;
	}
	
	public String list() throws Exception {
		return null;
	}
	
	public String listCI() throws Exception {
		HttpServletRequest request = super.getRequest();
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo=start/pageSize+1;
		String searchName = HttpUtil.ConverUnicode(request.getParameter("searchName"));
		String searchType = HttpUtil.ConverUnicode(request.getParameter("searchType"));
		//String dataId = request.getParameter("dataId");
//		ServicePortfolio spId=null;
//		if(dataId.equals("")){
//			return null;
//		}
//		spId = sciRelationShipService.findServiceItemById(dataId).getSp();//.getId().toString();
//		if(spId==null){
//			return null;
//		}
		ServiceItemType serviceItemType = null;
		if(StringUtils.isNotBlank(searchType)){
			serviceItemType = (ServiceItemType) getService().find(ServiceItemType.class, searchType);
		}
		Page page = sciRelationShipService.findServiceItemByPage(searchName,serviceItemType,pageNo, pageSize);
		List<ServiceItem> list = page.list();
		
		Long total = page.getTotalCount();//这是查询出所有的记录
		String json = "";
		for(int i=0; i< list.size(); i++){
			ServiceItem serviceItem = (ServiceItem)list.get(i);			
			Long id = serviceItem.getId();
			String name = serviceItem.getName();
			String serviceType = "";
			if(serviceItem.getServiceType()!=null){serviceType=serviceItem.getServiceType().getName();}
			json += "{\"id\":\""+id+"\",\"name\":\""+name+"\",\"serviceType\":\""+serviceType+"\"},";
		}
		if(json.length()==0){
			json = "{success:true,rowCount:"+"1"+",data:[" + json.substring(0, json.length()) + "]}";
		}else{
			json = "{success:true,rowCount:"+total+",data:[" + json.substring(0, json.length()-1) + "]}";
		}		

		//System.out.println("创建用户时,发往前台的部门数据： "+json);	
		try {	
		HttpServletResponse response = super.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();	
		pw.write(json);		
		} catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}
	public int confirmPageNo(String paramName ,int size){
		
		if(paramName == null || paramName.equals("")){
			return size;
		}
		return Integer.parseInt(paramName);
	}
	/**
	 * 通过根服务项关系ID获取修改页面时服务目录关系根节点数据
	 * @Methods Name getRootRelationShipInfo
	 * @Create In Mar 13, 2009 By Administrator
	 * @return String
	 */
	public String getRootRelationShipInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String rootCataId = request.getParameter("rootCataId");
		String typeFlag = request.getParameter("type");
		ServiceCatalogue rootCata = serviceCatalogueService.findServiceCatalogueById(rootCataId);
		SCIRelationShip rootRelationShip = sciRelationShipService.findRootRelationShipByRootCata(rootCata);
		String rootId = rootRelationShip.getId().toString();
		String rootText = rootCata.getName();
		String json="{success:true,rootId:'"+rootId+"',rootText:'"+rootText+"',dataId:'"+rootCataId+"'}";
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取修改页面时服务目录关系根节点数据
	 * @Methods Name getRootRelationShipData
	 * @Create In 2008-1-14 By lee
	 * @return String
	 * @throws Exception
	 */
	public String getRootRelationShipData(){
		HttpServletRequest request = super.getRequest();
		String rootCataId = request.getParameter("rootCataId");
		String oldCataId = request.getParameter("oldSerCataId");
		String typeFlag = request.getParameter("type");
		ServiceCatalogue rootCata = serviceCatalogueService.findServiceCatalogueById(rootCataId);
		SCIRelationShip rootRelationShip = sciRelationShipService.findRootRelationShipByRootCata(rootCata);
		String rootId = rootRelationShip.getId().toString();
		String rootText = rootCata.getName();
		Integer dispFlag = rootRelationShip.getDispFlag();
		request.setAttribute("dataId", rootCataId);
		request.setAttribute("rootId", rootId);
		request.setAttribute("rootText", rootText);
		request.setAttribute("oldCataId", oldCataId);
		request.setAttribute("dispFlag", dispFlag);
		if("issue".equals(typeFlag)){
			return "issuePage";
		}else if("query".equals(typeFlag)){
			return "queryPage";
		}else if("submit".equals(typeFlag)){
			return "submitPage ";
		}else if("modify".equals(typeFlag)){
			return "modifyPage";
		}else if("keep".equals(typeFlag)){
			return "keepPage";
		}else if("back".equals(typeFlag)){
			return "backPage";
		}else{
			return "toModifyPage";
		}
	}
	/**
	 * 双击进去页面时判断是否有草稿
	 * @Methods Name getRootRelationShipDataByIsSketch
	 * @Create In Apr 10, 2009 By Administrator
	 * @return String
	 */
	public String getRootRelationShipDataByIsSketch(){//sketch(草稿的意思)
		HttpServletRequest request = super.getRequest();
		String rootCataId = request.getParameter("rootCataId");//主id
		ServiceCatalogue serviceCatalogue = (ServiceCatalogue) super.getService().findUnique(ServiceCatalogue.class, "oldCatalogueId", Long.valueOf(rootCataId));
		String serviceCataId = "";
		String typeFlag = "";
		if(serviceCatalogue==null){//为无草稿
			serviceCataId = rootCataId;		
			typeFlag = "readOnly";
		}else{//有草稿
			if(serviceCatalogue.getStatus()==4){
				serviceCataId = serviceCatalogue.getId().toString();
				typeFlag  = "editor";
				
			}else{
				serviceCataId = serviceCatalogue.getId().toString();
				typeFlag = "readOnly";
				request.setAttribute("existFlag", "exist");
			}
			
		}
		
		//String oldCataId = request.getParameter("oldSerCataId");
		//String typeFlag = request.getParameter("type");
		
		ServiceCatalogue rootCata = serviceCatalogueService.findServiceCatalogueById(serviceCataId);
		SCIRelationShip rootRelationShip = sciRelationShipService.findRootRelationShipByRootCata(rootCata);
		String rootId = rootRelationShip.getId().toString();
		String rootText = rootCata.getName();
		request.setAttribute("dataId", serviceCataId);
		request.setAttribute("rootId", rootId);
		request.setAttribute("rootText", rootText);
		request.setAttribute("oldCataId", rootCataId);
		
		
		if("readOnly".equals(typeFlag)){
			return "readOnlyPage";
		}else if("editor".equals(typeFlag)){
		request.setAttribute("alterFlag", "alter");
			return "editorPage";
		}
		return null;
		
		
	}
	/**
	 * 删除服务目录变更草稿
	 * @Methods Name removeServiceCataSketch
	 * @Create In Apr 13, 2009 By Administrator
	 * @return String
	 */
	public String removeServiceCataSketch(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String sketchId = request.getParameter("sketchId");//变更草稿的主id
		sciRelationShipService.removeServiceCataByRootId(sketchId);
		String json ="{success:true}";
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 双击进去就保存三个面板里面的数据，并且显示的是变更的数据
	 * @Methods Name getRootRelationShipDataAlter
	 * @Create In Apr 9, 2009 By Administrator
	 * @return String
	 */
	public String getRootRelationShipDataAlter(){
		HttpServletRequest request = super.getRequest();
		String rootCataId = request.getParameter("rootCataId");
		ServiceCatalogue serviceCatalogue1 = (ServiceCatalogue) super.getService().findUnique(ServiceCatalogue.class, "oldCatalogueId", Long.valueOf(rootCataId));
		if(serviceCatalogue1==null){
		//String oldCataId = request.getParameter("oldSerCataId");
		String typeFlag = request.getParameter("type");
		ServiceCatalogue rootCata = serviceCatalogueService.findServiceCatalogueById(rootCataId);
		List<SCIRelationShip> sciReList = sciRelationShipService.findServiceCataShipByRoot(rootCata);//得到根下面得所有子服务目录
		
		ServiceCatalogueContract serviceCatalogueContract  = (ServiceCatalogueContract) super.getService().findUnique(ServiceCatalogueContract.class, "serviceCatalogue", rootCata);
		List<ServiceItemSLA> serSLAs = super.getService().find(ServiceItemSLA.class, "serviceCatalogue", rootCata); 
		SCIRelationShip rootSCIReShip = sciRelationShipService.findRootRelationShipByRootCata(rootCata);//得到根关系
		List<SCIRelationShip> childsList = sciRelationShipService.getChildSCIRelationShipsByServiceCata(rootCata);//得到子关系
		ServiceCatalogue newServiceCatalogue = new ServiceCatalogue();
		try{
			//copy服务目录
			BeanUtils.copyProperties(rootCata, newServiceCatalogue);
			newServiceCatalogue.setId(null);
			newServiceCatalogue.setOldCatalogueId(rootCata.getId());
			newServiceCatalogue.setStatus(ServiceCatalogue.STATUS_ALTER_DRAFT);
			super.getService().save(newServiceCatalogue);
			//copy子服务目录
			for(SCIRelationShip scRelationShip:sciReList){
				ServiceCatalogue newChildServiceCatalogue = new ServiceCatalogue();
				ServiceCatalogue oldChildServiceCatalogue = scRelationShip.getServiceCatalogue();
				BeanUtils.copyProperties(oldChildServiceCatalogue, newChildServiceCatalogue);
				newChildServiceCatalogue.setId(null);
				newChildServiceCatalogue.setOldCatalogueId(oldChildServiceCatalogue.getId());
				newChildServiceCatalogue.setStatus(null);
				super.getService().save(newChildServiceCatalogue);
			}
			//copy服务合同
			ServiceCatalogueContract newServiceCatalogueContract = new ServiceCatalogueContract();
			BeanUtils.copyProperties(serviceCatalogueContract, newServiceCatalogueContract);
			newServiceCatalogueContract.setServiceCatalogue(newServiceCatalogue);
			newServiceCatalogueContract.setId(null);
			super.getService().save(newServiceCatalogueContract);
			//copy服务SLA
			if(serSLAs.size()>0){
				for(ServiceItemSLA serSLA:serSLAs){
					if(serSLA!=null){
						ServiceItemSLA  sSLA = new ServiceItemSLA();
						BeanUtils.copyProperties(serSLA, sSLA);
						sSLA.setId(null);
						sSLA.setServiceCatalogue(newServiceCatalogue);
						super.getService().save(sSLA);
					}
				}
			}
			//copy服务目录关系
			SCIRelationShip sRelationShip = new SCIRelationShip();//保存根服务目录关系
			//BeanUtils.copyProperties(rootSCIReShip, sRelationShip);
			sRelationShip.setParentRelationShip(null);
			sRelationShip.setServiceItem(rootSCIReShip.getServiceItem());
			sRelationShip.setServiceItemFee(rootSCIReShip.getServiceItemFee());
			sRelationShip.setTypeFlag(rootSCIReShip.getTypeFlag());
			sRelationShip.setOrder(rootSCIReShip.getOrder());
			sRelationShip.setRootServiceCatalogue(newServiceCatalogue);
			sRelationShip.setServiceCatalogue(newServiceCatalogue);
			//add by guoxl in 2009/5/11 begin 因为增加例外标识
			sRelationShip.setDispFlag(rootSCIReShip.getDispFlag());
			//add by guoxl in 2009/5/11 end
			sRelationShip.setId(null);
			super.getService().save(sRelationShip);
			
			List<SCIRelationShipType> sciRootReTypeList = super.getService().find(SCIRelationShipType.class, "sciRelationShip", rootSCIReShip);
			if(sciRootReTypeList.size()>0){
				for(SCIRelationShipType type:sciRootReTypeList){
					SCIRelationShipType scRelationShipType = new SCIRelationShipType();
					scRelationShipType.setSciRelationShip(sRelationShip);
					scRelationShipType.setServiceType(type.getServiceType());
					super.getService().save(scRelationShipType);
				}
			}
			if(childsList.size()>0){//保存子服务目录关系
				for(SCIRelationShip ship:childsList){
					//if(ship.getServiceCatalogue()!=null){//是一个子服务目录
						SCIRelationShip sciRelShip = new SCIRelationShip();
						sciRelShip.setRootServiceCatalogue(newServiceCatalogue);
						ServiceCatalogue serviceCata =(ServiceCatalogue) super.getService().findUnique(ServiceCatalogue.class, "oldCatalogueId", ship.getParentRelationShip().getServiceCatalogue().getId());
						SCIRelationShip parentRelationShip = (SCIRelationShip) super.getService().findUnique(SCIRelationShip.class, "serviceCatalogue", serviceCata);
						sciRelShip.setParentRelationShip(parentRelationShip);
						if(ship.getServiceCatalogue()!=null){
							ServiceCatalogue serviceCatalogue = (ServiceCatalogue) super.getService().findUnique(ServiceCatalogue.class,"oldCatalogueId",ship.getServiceCatalogue().getId());
							sciRelShip.setServiceCatalogue(serviceCatalogue);
						}else{
							sciRelShip.setServiceCatalogue(null);
						}
						sciRelShip.setServiceItem(ship.getServiceItem());
						sciRelShip.setServiceItemFee(ship.getServiceItemFee());
						sciRelShip.setTypeFlag(ship.getTypeFlag());
						//add by guoxl in 2009/5/11 begin 因为增加例外标识
						sciRelShip.setDispFlag(ship.getDispFlag());
						//add by guoxl in 2009/5/11 end
						sciRelShip.setOrder(ship.getOrder());
						sciRelShip.setId(null);
						super.getService().save(sciRelShip);
						List<SCIRelationShipType> sciReTypeList = super.getService().find(SCIRelationShipType.class, "sciRelationShip", ship);
						if(sciReTypeList.size()>0){
							for(SCIRelationShipType type:sciReTypeList){
								SCIRelationShipType scRelationShipType = new SCIRelationShipType();
								scRelationShipType.setSciRelationShip(sciRelShip);
								scRelationShipType.setServiceType(type.getServiceType());
								super.getService().save(scRelationShipType);
							}
						}
					}
				}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		//}
		SCIRelationShip rootRelationShip = sciRelationShipService.findRootRelationShipByRootCata(newServiceCatalogue);
		String newrootCataId = newServiceCatalogue.getId().toString();
		String rootId = rootRelationShip.getId().toString();
		String rootText = newServiceCatalogue.getName();
		
		request.setAttribute("dataId", newrootCataId);
		request.setAttribute("rootId", rootId);
		request.setAttribute("rootText", rootText);
		request.setAttribute("oldCataId", rootCataId);
		
		}else{
			ServiceCatalogue serviceCatalogue = (ServiceCatalogue) super.getService().findUnique(ServiceCatalogue.class, "oldCatalogueId", Long.valueOf(rootCataId));
			String newrootCataId = serviceCatalogue.getId().toString();
			SCIRelationShip sciReShip = sciRelationShipService.findRootRelationShipByRootCata(serviceCatalogue);
			String rootId = sciReShip.getId().toString();
			String rootText = serviceCatalogue.getName();
			
			request.setAttribute("dataId", newrootCataId);
			request.setAttribute("rootId", rootId);
			request.setAttribute("rootText", rootText);
			request.setAttribute("oldCataId", rootCataId);
			
		}
//		try {
//			super.getResponse().sendRedirect("/itil/user/serviceCataModify/sciRelationShipModifyForm.jsp?dataId="
//					+newrootCataId+"&rootId="+rootId+"&rootText="+rootText+"&oldCataId="+rootCataId);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
		return "editorPage";
	
	}
/*	*//**
	 * 获取修改关系节点ID，并返回对应数据
	 * @Methods Name findChildForEdit
	 * @Create In 2008-1-14 By lee
	 * @return String
	 * @throws Exception 
	 * @throws Exception
	 */
	public String findChildForEdit() throws Exception{
		HttpServletRequest request = super.getRequest();
		String childId = request.getParameter("childId");
		String target = request.getParameter("target");
		SCIRelationShip childRelationShip = sciRelationShipService.findSCIRelationShipById(childId);
		String typeFlag = childRelationShip.getTypeFlag();
		String json="{";
		if(typeFlag.equals(SCIRelationShip.SCI_TYPE_CATALOGUE)){
			ServiceCatalogue childServiceCatalogue = childRelationShip.getServiceCatalogue();
			String id = childServiceCatalogue.getId().toString();
			String name = childServiceCatalogue.getName();
			Double serviceItemFee = childRelationShip.getServiceItemFee();
			String descn = childServiceCatalogue.getDescn();
			// add by guoxl in 2009/5/11 begin 
			Integer dispFlag = childRelationShip.getDispFlag();
			// add by guoxl in 2009/5/11 end
			json+="type:\"cata\",";
			json+="id:"+id+",";
			json+="name:\""+name+"\",";
			json+="descn:\""+descn+"\",";
			// add by guoxl in 2009/5/11 begin 
			json+="dispFlag:"+dispFlag+",";
			// add by guoxl in 2009/5/11 end 
			json+="serviceItemFee:\""+serviceItemFee+"\"}";
		}
		if(typeFlag.equals(SCIRelationShip.SCI_TYPE_ITEM)){
			ServiceItem childServiceItem = childRelationShip.getServiceItem();
			String id = childServiceItem.getId().toString();
//			if(target.equals("configItem")){
//			ConfigItem conf = this.configItemService.findConfigItemByExtendId(id);
//			id = conf.getId().toString();
//			}
			json+="type:\"item\",";
			json+="id:"+id+"}";
		}
		HttpServletResponse response = super.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}
	/**
	 * 查找SCIRelationShip获得服务项价格
	 * @Methods Name findSCIRelationShip
	 * @Create In Jan 16, 2009 By Allen
	 * @return
	 * @throws Exception String
	 */
	public String findSCIRelationShip()throws Exception{
		HttpServletRequest request = super.getRequest();
		String childId = request.getParameter("childId");
		SCIRelationShip childRelationShip = sciRelationShipService.findSCIRelationShipById(childId);
		String json="{";
			String id = childRelationShip.getId().toString();
			ServiceItem serviceItem = childRelationShip.getServiceItem();
			Double serviceItemFee = childRelationShip.getServiceItemFee();
			Integer dispFlag = childRelationShip.getDispFlag();
			json+="type:\"cata\",";
			json+="id:"+id+",";
			json+="serviceItem:\""+serviceItem.getName()+"\",";
			json+="serviceItemFee:\""+serviceItemFee+"\",";
			json+="dispFlag:\""+dispFlag+"\"}";
		HttpServletResponse response = super.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
		
	}
	
	/**
	 *通过服务目录Id获得服务合同id
	 * by sujs 2009-3-1
	 * 
	 */
	 public String getServiceCatalogueContractId()throws Exception{
		HttpServletRequest request = super.getRequest();
		String serviceCataId = request.getParameter("serviceCataId");
		String contractID=sciRelationShipService.getServiceCatalogueContractId(serviceCataId);
		HttpServletResponse response = super.getResponse();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(contractID);
			out.flush();
			out.close();
			return null;
	 }
	 /**
	  * 通过服务目录Id获取所有的服务项信息然后保存到ServiceItemSLA表里
	  * @Methods Name saveServiceItemSLAbaseData
	  * @Create In Mar 1, 2009 By Administrator
	  * @return String
	 * @throws IOException 
	  */
	 public String saveServiceItemSLAbaseData() throws IOException{
		 HttpServletRequest request = super.getRequest();
		 String serviceCataId = request.getParameter("serviceCatalogueId");
		 sciRelationShipService.saveServiceItemSLAfromservicelogueId(serviceCataId);
		 String comback="ok";
		 HttpServletResponse response = super.getResponse();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(comback);
			out.flush();
			out.close();
			return null;
		 
	 }
	 /**
	  * 保存服务目录合同方法
	  * @Methods Name saveServiceCatalogueContract
	  * @Create In Mar 2, 2009 By sujs
	  * @return String
	 * @throws IOException 
	  */
	 public String saveServiceCatalogueContract() throws IOException{
		 MetaDataManager metaDataManager = (MetaDataManager) ContextHolder.getBean("metaDataManager");
		 HttpServletRequest request = super.getRequest();
		 String serviceCataId = request.getParameter("info");
		 JSONObject panelJO = JSONObject.fromObject(serviceCataId);
		 Iterator panelIter = panelJO.keys();
		 Map<String,Object> recordMap = new HashMap<String,Object>();
		 while(panelIter.hasNext()) {
			String panelName = (String) panelIter.next();
			String panelData = panelJO.getString(panelName); 
			recordMap.put(panelName, panelData);
		 }
		 metaDataManager.saveEntityData(ServiceCatalogueContract.class, recordMap);
		 String comback="ok";
		 HttpServletResponse response = super.getResponse();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(comback);
			out.flush();
			out.close();
			return null;
	 }
	 /**
	  * 保存ServiceItemSLA信息数据
	  * @Methods Name saveServiceItemSLA
	  * @Create In Mar 2, 2009 By Sujs
	  * @return String
	 * @throws IOException 
	  */
	@SuppressWarnings("unchecked")
	public String saveServiceItemSLA() throws IOException{
		 MetaDataManager metaDataManager = (MetaDataManager) ContextHolder.getBean("metaDataManager");
		 HttpServletRequest request = super.getRequest();
		 String serviceCataId = request.getParameter("info");
		 if(serviceCataId!=null&&!serviceCataId.equals("")){
			 String [] serviceItemSALData= serviceCataId.split(";");
			 for(int i=0;i<serviceItemSALData.length;i++){
				 JSONObject panelJO = JSONObject.fromObject(serviceItemSALData[i]);
				 Iterator panelIter = panelJO.keys();
				 Map<String,Object> recordMap = new HashMap<String,Object>();
				 while(panelIter.hasNext()) {
					String panelName = (String) panelIter.next();
					String [] panelNames= panelName.split("\\$");
					String panelData = panelJO.getString(panelName); 
					recordMap.put(panelNames[1], panelData);
				 }
				 try {
					metaDataManager.saveEntityData(ServiceItemSLA.class,
							recordMap);
				} catch (Exception e) {
					e.printStackTrace();
					//e.printStackTrace();
				}  
			 }
		 }
		 String comback="ok";
		 HttpServletResponse response = super.getResponse();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(comback);
			out.flush();
			out.close();
			return null;
		
	}
	/**
	 * 保存ServiceItemSLA信息数据(最终版)
	 * @Methods Name saveNewServiceItemSLA
	 * @Create In Apr 14, 2009 By Administrator
	 * @return
	 * @throws IOException String
	 */
	@SuppressWarnings("unchecked")
	public String saveNewServiceItemSLA() throws IOException{
		ServiceCatalogue serviceCatalogue=null;
		String serviceItemName = null;
		ServiceItem serviceItem = null;
		ServiceItemSatisfaction satisfaction = null;
		 MetaDataManager metaDataManager = (MetaDataManager) ContextHolder.getBean("metaDataManager");
		 HttpServletRequest request = super.getRequest();
		 String serviceCataInfo = request.getParameter("info");
		 String serviceCataId = request.getParameter("serviceCataId");
		 if(serviceCataInfo!=null&&!serviceCataInfo.equals("")){
			 String [] serviceItemSALData= serviceCataInfo.split(";");
			 for(int i=0;i<serviceItemSALData.length;i++){
				 JSONObject panelJO = JSONObject.fromObject(serviceItemSALData[i]);
				 Iterator panelIter = panelJO.keys();
				 Map<String,Object> recordMap = new HashMap<String,Object>();
				 while(panelIter.hasNext()) {
					String panelName = (String) panelIter.next();
					String [] panelNames= panelName.split("\\$");
					String panelData = panelJO.getString(panelName); 
					recordMap.put(panelNames[1], panelData);
				 }
				 try {
				if(serviceCataId==null){
						 serviceCatalogue =(ServiceCatalogue) super.getService().findUnique(ServiceCatalogue.class, "name", (String)recordMap.get("serviceCatalogue"));
				 }else{
					 	serviceCatalogue =	(ServiceCatalogue) super.getService().find(ServiceCatalogue.class, serviceCataId, true);
				 }
				 serviceItemName =(String) recordMap.get("serviceItemName");
				 serviceItem = (ServiceItem) super.getService().findUnique(ServiceItem.class, "name", (String)recordMap.get("serviceItemName"));
				
				 String provideTimeStr = (String) recordMap.get("provideTime");
				 Double provideTime = null;
				 Double problemHandleTime = null;
				 if(StringUtils.isNotBlank(provideTimeStr)){
					 provideTime = Double.valueOf(provideTimeStr);
				 }
				 
				 String problemHandleTimeStr = (String) recordMap.get("problemHandleTime");
				 if(StringUtils.isNotBlank(problemHandleTimeStr)){
					 problemHandleTime = Double.valueOf(problemHandleTimeStr);
				 }
				 
				 if(Pattern.matches("^\\+?[0-9][0-9]*$", (String)recordMap.get("satisfaction"))){
					 satisfaction = (ServiceItemSatisfaction) super.getService().find(ServiceItemSatisfaction.class, (String)recordMap.get("satisfaction"), true);
					 
				 }else{
					 
					 satisfaction = (ServiceItemSatisfaction) super.getService().findUnique(ServiceItemSatisfaction.class, "name", (String)recordMap.get("satisfaction"));
				 }
				
				 if(recordMap.get("id")!=null){
					 ServiceItemSLA serviceItemSLA = (ServiceItemSLA) super.getService().find(ServiceItemSLA.class, (String)recordMap.get("id"), true);
					 serviceItemSLA.setServiceCatalogue(serviceCatalogue);
					 serviceItemSLA.setServiceItemName(serviceItemName);
					 serviceItemSLA.setServiceItem(serviceItem);
					 serviceItemSLA.setProvideTime(provideTime);
					 serviceItemSLA.setProblemHandleTime(problemHandleTime);
					 serviceItemSLA.setSatisfaction(satisfaction);
					 super.getService().save(serviceItemSLA);
				 }else{
					 ServiceItemSLA serviceItemSLA = new ServiceItemSLA();
					 serviceItemSLA.setServiceCatalogue(serviceCatalogue);
					 serviceItemSLA.setServiceItemName(serviceItemName);
					 serviceItemSLA.setServiceItem(serviceItem);
					 serviceItemSLA.setProvideTime(provideTime);
					 serviceItemSLA.setProblemHandleTime(problemHandleTime);
					 serviceItemSLA.setSatisfaction(satisfaction);
					 super.getService().save(serviceItemSLA);
					 
				 }
				 } catch (Exception e) {
						e.printStackTrace();
			}
				 
//				 try {
//					metaDataManager.saveEntityData(ServiceItemSLA.class,
//							recordMap);
//				} catch (Exception e) {
//					e.printStackTrace();
//					//e.printStackTrace();
//				}  
			 }
		 }
		 String comback="ok";
		 HttpServletResponse response = super.getResponse();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(comback);
			out.flush();
			out.close();
			return null;
		
	}
	/**
	 * 删除服务目录(逻辑删除)
	 * @Methods Name removeServiceCatalogue
	 * @Create In Mar 26, 2009 By Administrator
	 * @return
	 * @throws IOException String
	 */
	public String removeServiceCatalogue()throws IOException{
		HttpServletRequest request = super.getRequest();
		 HttpServletResponse response = super.getResponse();
		 String[] ids =  request.getParameterValues("dataId");
		 String json = "{success:true}";
		 for(int i=0;i<ids.length;i++){
			 ServiceCatalogue serviceCatalogue = (ServiceCatalogue) super.getService().find(ServiceCatalogue.class, ids[i]);
			 serviceCatalogue.setStatus(ServiceCatalogue.STATUS_DELETE);
			 super.getService().save(serviceCatalogue);
		 }
		 response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
	}
	/**
	 * 获取登录用户服务目录
	 * 
	 * @Methods Name listMyServiceCata
	 * @Create In Nov 18, 2009 By lee
	 * @return String
	 */
	public String listMyServiceCata() {
		@SuppressWarnings("unused")
		UserInfo curUser = UserContext.getUserInfo();
		String json = sciRelationShipService.getUserServiceCataJson(curUser);
		try {
			super.getResponse().setCharacterEncoding("utf-8");
			super.getResponse().getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
