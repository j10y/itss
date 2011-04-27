package com.zsgj.itil.service.action;
//package com.zsgj.itil.service.action;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.zsgj.info.framework.dao.support.Page;
//import com.zsgj.info.framework.util.BeanUtil;
//import com.zsgj.info.framework.util.HttpUtil;
//import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
//import com.zsgj.itil.service.entity.SCIRelationShip;
//import com.zsgj.itil.service.entity.ServiceCatalogue;
//import com.zsgj.itil.service.entity.ServiceItem;
//import com.zsgj.itil.service.entity.ServicePortfolio;
//import com.zsgj.itil.service.service.SCIRelationShipService;
//import com.zsgj.itil.service.service.ServiceCatalogueService;
//import com.zsgj.itil.service.service.ServicePortfolioService;
//
//public class SCIRelationShipAction_before_update_sujs  extends BaseAction{
//	private SCIRelationShipService sciRelationShipService = (SCIRelationShipService) getBean("sciRelationShipService");
//	private ServiceCatalogueService serviceCatalogueService = (ServiceCatalogueService) getBean("serviceCatalogueService");
//	private ServicePortfolioService servicePortfolioService = (ServicePortfolioService) getBean("servicePortfolioService");
//	
//	/**
//	 * 保存服务目录同时生成服务目录对应的根服务目录关系
//	 * @Methods Name saveRootSCIRelationShip
//	 * @Create In 2008-1-14 By lee
//	 * @return String
//	 * @throws Exception
//	 */
//	public String saveRootSCIRelationShip() throws Exception {
//		HttpServletRequest request = super.getRequest();
//		String id = request.getParameter("id");
//		String servicePortfolioId = request.getParameter("sp");
//		String typeFlag = request.getParameter("typeFlag");
//		String name = request.getParameter("name");
//		String descn = request.getParameter("descn");
//		String valid = request.getParameter("validDate");
//		String begin = request.getParameter("beginDate");
//		String end = request.getParameter("endDate");
//		
//		ServicePortfolio sp = servicePortfolioService.findServicePortfolioById(servicePortfolioId);
//		SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
//		Date validDate = null;
//		if(valid!="")
//			validDate = df.parse(valid);		
//		Date beginDate = null;
//		if(begin!="")
//			beginDate = df.parse(begin);
//		Date endDate = null;
//		if(end!="")
//			endDate = df.parse(end);
//		ServiceCatalogue newServiceCatalogue = new ServiceCatalogue();
//		if(!id.equals("")){
//			newServiceCatalogue.setId(Long.valueOf(id));
//		}
//		newServiceCatalogue.setName(name);
//		newServiceCatalogue.setDescn(descn);
//		if(typeFlag!=""){
//			newServiceCatalogue.setTypeFlag(Integer.valueOf(typeFlag));
//		}else{
//			newServiceCatalogue.setTypeFlag(null);
//		}
//		newServiceCatalogue.setSp(sp);
//		newServiceCatalogue.setRootFlag(Integer.valueOf(1));
//		newServiceCatalogue.setValidDate(validDate);
//		newServiceCatalogue.setBeginDate(beginDate);
//		newServiceCatalogue.setEndDate(endDate);
//		ServiceCatalogue serviceCatalogue = serviceCatalogueService.save(newServiceCatalogue);
//		String parentSCIRelationShipId=request.getParameter("parentSCIRelationShipId");
//		SCIRelationShip textRS = sciRelationShipService.findRootRelationShipByRootCata(serviceCatalogue);
//		if(textRS==null){
//			SCIRelationShip newRelationShip = new SCIRelationShip();
//			SCIRelationShip parentRelationShip = null;
//			Integer order = 0;
//			if(parentSCIRelationShipId!=null&&!parentSCIRelationShipId.equals("")){
//				parentRelationShip = sciRelationShipService.findSCIRelationShipById(parentSCIRelationShipId);
//				List<SCIRelationShip> childs = sciRelationShipService.findChildRelationShipByParent(parentRelationShip);
//				order = childs.size();
//				newRelationShip.setRootServiceCatalogue(parentRelationShip.getRootServiceCatalogue());
//			}else{
//				newRelationShip.setRootServiceCatalogue(serviceCatalogue);
//			}
//			newRelationShip.setParentRelationShip(parentRelationShip);
//			newRelationShip.setServiceCatalogue(serviceCatalogue);
//			newRelationShip.setTypeFlag("cata");
//			newRelationShip.setOrder(Integer.valueOf(order)+1);
//			SCIRelationShip newRS = sciRelationShipService.save(newRelationShip);
//			textRS = newRS;
//		}
//		String curId = serviceCatalogue.getId().toString();
//		String rootId = textRS.getId().toString();
//		HttpServletResponse response = super.getResponse();
//		response.setContentType("text/plain");
//		PrintWriter out = response.getWriter();
//		out.println("{id:"+curId+"}");
//		out.flush();
//		out.close();
//		return null;
//	}
//	/**
//	 * 保存子服务目录同时生成服务目录对应的根服务目录关系
//	 * @Methods Name saveRootSCIRelationShip
//	 * @Create In 2008-1-14 By lee
//	 * @return String
//	 * @throws Exception
//	 */
//	public String saveChildSCIRelationShip() throws Exception {
//		HttpServletRequest request = super.getRequest();
//		String id = request.getParameter("id");
////		String servicePortfolioId = request.getParameter("sp");
////		String typeFlag = request.getParameter("typeFlag");
//		String name = request.getParameter("name");
//		String descn = request.getParameter("descn");
////		String valid = request.getParameter("validDate");
////		String begin = request.getParameter("beginDate");
////		String end = request.getParameter("endDate");
//		if(id==null||id.equals("")){
//			String parentRelationShipId = request.getParameter("parentId");
//			
//			SCIRelationShip parentRelationShip = sciRelationShipService.findSCIRelationShipById(parentRelationShipId);
//			
//			ServiceCatalogue rootServiceCatalogue = parentRelationShip.getRootServiceCatalogue();
//			ServiceCatalogue newServiceCatalogue = new ServiceCatalogue();
//			newServiceCatalogue.setName(name);
//			newServiceCatalogue.setDescn(descn);
//			newServiceCatalogue.setSp(rootServiceCatalogue.getSp());
//			newServiceCatalogue.setTypeFlag(rootServiceCatalogue.getTypeFlag());
//			newServiceCatalogue.setBeginDate(rootServiceCatalogue.getBeginDate());
//			newServiceCatalogue.setEndDate(rootServiceCatalogue.getEndDate());
//			newServiceCatalogue.setBeginDate(rootServiceCatalogue.getBeginDate());
//			newServiceCatalogue.setRootFlag(Integer.valueOf(0));
//			ServiceCatalogue childServiceCatalogue = serviceCatalogueService.save(newServiceCatalogue);
//			
//			List childs = sciRelationShipService.findChildRelationShipByParent(parentRelationShip);
//			Integer order = 1;
//			if(childs!=null){
//				order = childs.size()+1;
//			}
//			SCIRelationShip newRelationShip = new SCIRelationShip();
//			newRelationShip.setRootServiceCatalogue(rootServiceCatalogue);
//			newRelationShip.setParentRelationShip(parentRelationShip);
//			newRelationShip.setServiceCatalogue(childServiceCatalogue);
//			newRelationShip.setTypeFlag("cata");
//			newRelationShip.setOrder(order);
//			sciRelationShipService.save(newRelationShip);
//			
//		}else{
//			ServiceCatalogue childServiceCatalogue = serviceCatalogueService.findServiceCatalogueById(id);
//			childServiceCatalogue.setName(name);
//			childServiceCatalogue.setDescn(descn);
//			serviceCatalogueService.save(childServiceCatalogue);
//		}
//
//		return null;
//	}
//	/**
//	 * 加载舒服目录树数据
//	 * @Methods Name loadSCIRelationShip
//	 * @Create In 2008-1-14 By lee
//	 * @param request
//	 * @return String
//	 * @throws Exception
//	 */
//	public String loadSCIRelationShip()throws Exception{
//		HttpServletRequest request = super.getRequest();
//		String parentId = request.getParameter("id");
//		String rootId = request.getParameter("rootId");
//		ServiceCatalogue serviceCatalogue = serviceCatalogueService.findServiceCatalogueById(rootId);
//		SCIRelationShip rootSCIRelationShip = sciRelationShipService.findRootRelationShipByRootCata(serviceCatalogue);
//		if(parentId.equals("")){
//			parentId = rootSCIRelationShip.getId().toString();
//		}
//		if("".endsWith(rootId)){
//			request.setAttribute("list", null);
//		}else{
//			SCIRelationShip parentRelationShip = sciRelationShipService.findSCIRelationShipById(parentId);
//			List<SCIRelationShip> childs = sciRelationShipService.findChildRelationShipByParent(parentRelationShip);
//			request.setAttribute("list", childs);
//		}
//		return "forRelationShipData";
//	}
//	/**
//	 * 树的子递归
//	 * @param parent
//	 * @param childNum
//	 * @param para
//	 * @return
//	 */
//	
//	@SuppressWarnings("unchecked")
//	private List initSCIRelationShipChild(SCIRelationShip parent,int childNum ,List para){
//		Set<SCIRelationShip> child = parent.getChildRelationShips();
//		List list=new ArrayList();
//		List subList=new ArrayList();
//		List paramete=new ArrayList();
//		int parent1=((Long)para.get(0)).intValue();
//		int level=((Integer)para.get(1)).intValue()+1;
//		int lft=((Integer)para.get(2)).intValue()+1;
//		int rgt=0;
//		String temp = "";
//		for(SCIRelationShip item : child){
//			if(!item.getChildRelationShips().isEmpty()){
//				paramete.add(item.getId());
//				paramete.add(level);
//				paramete.add(lft);
//				subList=this.initSCIRelationShipChild(item,item.getChildRelationShips().size(),paramete);
//				
//				String typeName = null;
//				String typeFlag = item.getTypeFlag();
//				if(typeFlag.equalsIgnoreCase(SCIRelationShip.SCI_TYPE_CATALOGUE)){
//					typeName = "目录";
//
//				}else{
//					typeName = "服务项";
//				}
////				begin
//				temp+="{id:'"+item.getId()+"',";
//				temp+="name:'"+item.getName()+"',";
//				temp+="typeFlag:'"+typeName+"',";
//				//temp+="typeFlag:'"+item.getTypeFlag()+"',";
//				temp+="rootName:'"+item.getRootServiceCatalogue().getName()+"',";
//				temp += "_parent:"+parent1+",";
//				temp += "_level:"+level+",";
//				temp += "_lft:"+lft+",";
//				int subNum=0;
//				if(subList.get(1)!=null){ //if(subList.size()!=0){
//				   subNum=((Integer) subList.get(1)).intValue();
//				   childNum+=((Integer) subList.get(1)).intValue();
//				}
//				rgt=lft+subNum*2+1;
//				temp += "_rgt:"+rgt+",";
//				temp += "_is_leaf:"+false+"},"; 
//				if(subList.get(0)!=null){ //subList.size()!=0 && 
//					temp +=subList.get(0);
//				 }
//				//end
//			}
//			else{
//				
//				String typeName = null;
//				String typeFlag = item.getTypeFlag();
//				if(typeFlag.equalsIgnoreCase(SCIRelationShip.SCI_TYPE_CATALOGUE)){
//					typeName = "目录";
//
//				}else{
//					typeName = "服务项";
//				}
////				begin
//				temp+="{id:'"+item.getId()+"',";
//				temp+="name:'"+item.getName()+"',";
//				temp+="typeFlag:'"+typeName+"',";
//				temp+="rootName:'"+item.getRootServiceCatalogue().getName()+"',";
//				temp += "_parent:"+parent1+",";
//				temp += "_level:"+level+",";
//				temp += "_lft:"+lft+",";
//				rgt=lft+1;
//				temp += "_rgt:"+rgt+",";
//				temp += "_is_leaf:"+true+"},";
//				//end
//			}
//				
//			lft=rgt+1;
//				
//		}
//		list.add(temp);
//		list.add(childNum);
//		return list;
//	}
///**
// * 树的子递归
// * @param relationShip
// * @return
// */
//	@SuppressWarnings("unchecked")
//	private String genJson(SCIRelationShip relationShip){//第一次传入根节点
//		int parent=0;
//		int level=1;
//		int lft=1;
//		int rgt=1;
//		String json = "[";
//		List subList=new ArrayList();
//		List paramete=new ArrayList();
//		if(!relationShip.getChildRelationShips().isEmpty()){
//			paramete.add(relationShip.getId());
//			paramete.add(level);
//			paramete.add(lft);
//			subList=this.initSCIRelationShipChild(relationShip,relationShip.getChildRelationShips().size(),paramete);
//			
//			String typeName = null;
//			String typeFlag = relationShip.getTypeFlag();
//			if(typeFlag.equalsIgnoreCase(SCIRelationShip.SCI_TYPE_CATALOGUE)){
//				typeName = "目录";
//
//			}else{
//				typeName = "服务项";
//			}
////			begin
//			json+="{id:'"+relationShip.getId()+"',";
//			json+="name:'"+relationShip.getName()+"',";
//			json+="typeFlag:'"+typeName+"',";
//			json+="rootName:'"+relationShip.getRootServiceCatalogue().getName()+"',";
//			json += "_parent:"+parent+",";
//			json += "_level:"+level+",";
//			json += "_lft:"+lft+",";
//			int subNum=0;
//			if(subList.get(1)!=null){
//				subNum=((Integer) subList.get(1)).intValue();
//			}
//			
//			rgt=lft+subNum*2+1;
//			json += "_rgt:"+rgt+",";
//			json += "_is_leaf:"+false+"},"; 
//			if(subList.get(0)!=null){ //subList.size()!=0 && 
//				json +=subList.get(0);
//			 }
//			//end
//		}
//		else{//多个根才会执行此代码
//			
//			String typeName = null;
//			String typeFlag = relationShip.getTypeFlag();
//			if(typeFlag.equalsIgnoreCase(SCIRelationShip.SCI_TYPE_CATALOGUE)){
//				typeName = "目录";
//
//			}else{
//				typeName = "服务项";
//			}
////			begin
//			json+="{id:'"+relationShip.getId()+"',";
//			json+="name:'"+relationShip.getName()+"',";
//			json+="typeFlag:'"+typeName+"',";
//			json+="rootName:'"+relationShip.getRootServiceCatalogue().getName()+"',";
//			json += "_parent:"+parent+",";
//			json += "_level:"+level+",";
//			json += "_lft:"+lft+",";
//			rgt=lft+1;
//			json += "_rgt:"+rgt+",";
//			json += "_is_leaf:"+true+"},";
//			//end
//		}
//		lft=rgt+1;
//			
//			
//		json = json.substring(0, json.length()-1);
//		json += "]";
//		return json;
//	}
///**
// * 查询树的所有信息入口
// * @return
// * @throws Exception
// */
//	public String listRelationShips() throws Exception {
//		@SuppressWarnings("unused")
//		String json="";
//		String serviceCatalogueId = super.getRequest().getParameter("serviceCatalogueId");
//		if(serviceCatalogueId==null){
//			return null;	
//		}else{
//		   SCIRelationShip sciRelationShip = sciRelationShipService.findRootRelationShip(serviceCatalogueId);
//		   if(sciRelationShip!=null){
//		   json+= this.genJson(sciRelationShip);
//		   }
//		}
//		try {
//			super.getResponse().setCharacterEncoding("utf-8");
//			super.getResponse().getWriter().write(json);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}	
//		
//		
//		return null;
//	}
//	
//	public String removeRelationShip() throws Exception {
//		String dataId = super.getRequest().getParameter("dataId");
//		
//		return null;
//	}
//	
//	public String list() throws Exception {
//		return null;
//	}
//	
//	public String listCI() throws Exception {
//		int pageSize = 10;
//		HttpServletRequest request = super.getRequest();
//		String paramName = request.getParameter("start");
//		String searchFactor = HttpUtil.ConverUnicode(request.getParameter("searchFactor"));
//		int pageNo = this.confirmPageNo(paramName, 1);	
//		String dataId = request.getParameter("dataId");
//		ServicePortfolio spId=null;
//		if(dataId.equals("")){
//			return null;
//		}
//		spId = sciRelationShipService.findServiceItemById(dataId).getSp();//.getId().toString();
//		if(spId==null){
//			return null;
//		}
//		Page page = sciRelationShipService.findServiceItemByPage(searchFactor,spId,pageNo, pageSize);
//		List<ServiceItem> list = page.list();
//		
//		Long total = page.getTotalCount();//这是查询出所有的记录
//		String json = "";
//		for(int i=0; i< list.size(); i++){
//			ServiceItem serviceItem = (ServiceItem)list.get(i);			
//			Long id = serviceItem.getId();		
//			String name = serviceItem.getName();
//			json += "{\"id\":\""+id+"\",\"name\":\""+name+"\"},";
//		}
//		if(json.length()==0){
//			json = "{success:true,rowCount:"+"1"+",data:[" + json.substring(0, json.length()) + "]}";
//		}else{
//			json = "{success:true,rowCount:"+total+",data:[" + json.substring(0, json.length()-1) + "]}";
//		}		
//
//		System.out.println("创建用户时,发往前台的部门数据： "+json);	
//		try {	
//		HttpServletResponse response = super.getResponse();
//		response.setCharacterEncoding("utf-8");
//		PrintWriter pw = response.getWriter();	
//		pw.write(json);		
//		} catch (IOException e) {
//		e.printStackTrace();
//		}
//		return null;
//	}
//	public int confirmPageNo(String paramName ,int size){
//		
//		if(paramName == null || paramName.equals("")){
//			return size;
//		}
//		return Integer.parseInt(paramName);
//	}
//	/**
//	 * 获取修改页面时服务目录关系根节点数据
//	 * @Methods Name getRootRelationShipData
//	 * @Create In 2008-1-14 By lee
//	 * @return String
//	 * @throws Exception
//	 */
//	public String getRootRelationShipData(){
//		HttpServletRequest request = super.getRequest();
//		String rootCataId = request.getParameter("rootCataId");
//		ServiceCatalogue rootCata = serviceCatalogueService.findServiceCatalogueById(rootCataId);
//		SCIRelationShip rootRelationShip = sciRelationShipService.findRootRelationShipByRootCata(rootCata);
//		String rootId = rootRelationShip.getId().toString();
//		String rootText = rootCata.getName();
//		request.setAttribute("dataId", rootCataId);
//		request.setAttribute("rootId", rootId);
//		request.setAttribute("rootText", rootText);
//		return "toModifyPage";
//	}
//	/**
//	 * 获取修改关系节点ID，并返回对应数据
//	 * @Methods Name findChildForEdit
//	 * @Create In 2008-1-14 By lee
//	 * @return String
//	 * @throws Exception 
//	 * @throws Exception
//	 */
//	public String findChildForEdit() throws Exception{
//		HttpServletRequest request = super.getRequest();
//		String childId = request.getParameter("childId");
//		SCIRelationShip childRelationShip = sciRelationShipService.findSCIRelationShipById(childId);
//		String typeFlag = childRelationShip.getTypeFlag();
//		String json="{";
//		if(typeFlag.equals("cata")){
//			ServiceCatalogue childServiceCatalogue = childRelationShip.getServiceCatalogue();
//			String id = childServiceCatalogue.getId().toString();
//			String name = childServiceCatalogue.getName();
//			String descn = childServiceCatalogue.getDescn();
//			json+="type:\"cata\",";
//			json+="id:"+id+",";
//			json+="name:\""+name+"\",";
//			json+="descn:\""+descn+"\"}";
//		}
//		if(typeFlag.equals("item")){
//			ServiceItem childServiceItem = childRelationShip.getServiceItem();
//			String id = childServiceItem.getId().toString();
//			json+="type:\"item\",";
//			json+="id:"+id+"}";
//		}
//		HttpServletResponse response = super.getResponse();
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("text/plain");
//		PrintWriter out = response.getWriter();
//		out.println(json);
//		out.flush();
//		out.close();
//		return null;
//	}
//}
