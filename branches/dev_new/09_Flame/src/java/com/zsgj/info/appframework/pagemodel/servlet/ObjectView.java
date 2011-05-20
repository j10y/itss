package com.zsgj.info.appframework.pagemodel.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.zsgj.info.appframework.extjs.servlet.CoderForFind;
import com.zsgj.info.appframework.extjs.servlet.ExtHtmlBuilder;
import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableQueryColumn;
import com.zsgj.info.appframework.pagemodel.PageManager;
import com.zsgj.info.appframework.pagemodel.entity.PageGroupPanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.appframework.pagemodel.entity.PageModelBtn;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanel;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelBtn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelRelation;
import com.zsgj.info.appframework.pagemodel.service.PageGroupPanelService;
import com.zsgj.info.appframework.pagemodel.service.PageModelService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelBtnService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.web.adapter.servlet.BaseServlet;

@SuppressWarnings("serial")
public class ObjectView extends BaseServlet {
	private PageManager pageManager = (PageManager) ContextHolder.getBean("pageManager");
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder.getBean("metaDataManager");
	private Service service = (Service) ContextHolder.getBean("baseService");	
	private PageModelService pageModelService = (PageModelService)ContextHolder.getBean("pageModelService");
	private PagePanelService pagePanelService = (PagePanelService)ContextHolder.getBean("pagePanelService");
//	private PagePanelColumnService pagePanelColumnService = (PagePanelColumnService)ContextHolder.getBean("pagePanelColumnService");
	private PagePanelBtnService pagePanelBtnService = (PagePanelBtnService)ContextHolder.getBean("pagePanelBtnService");
//	private ConfigItemService configItemService = (ConfigItemService) ContextHolder.getBean("configItemService");
	private PageGroupPanelService pgps = (PageGroupPanelService) ContextHolder.getBean("pageGroupPanelService");
//	private RequirementService rs = (RequirementService) ContextHolder.getBean("requirementService");
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gbk");
		response.setCharacterEncoding("gbk"); 
		String msg = "";
		String method = request.getParameter("method");
		if (StringUtils.isBlank(method)) {
			msg = "Error: no method specified.";
		} else {
			
			if (method.trim().equalsIgnoreCase("save")) {
				msg = forSave(request);
			} else if (method.trim().equalsIgnoreCase("saveSingleForm")) {
				msg = forSaveSingleForm(request);
			} else if (method.trim().equalsIgnoreCase("saveSingleFormForLook")) {
				msg = forSaveSingleFormForLook(request);
			} else if (method.trim().equalsIgnoreCase("query")) {
				msg = forQuery(request);
			} else if (method.trim().equalsIgnoreCase("html")) {
				msg = forHtmlQuery(request);
			} else if (method.trim().equalsIgnoreCase("head")) {
				msg = forHead(request);
			} else if (method.trim().equalsIgnoreCase("listHead")) {
				msg = forListHead(request);
			} else if (method.trim().equalsIgnoreCase("pageModel")) {
				msg = forModel(request); //��ȡ��̬������壬����forModel2����Ŀǰ�����ܱ���
			} else if (method.trim().equalsIgnoreCase("pageModelButton")) {
				msg = forModelButton(request);
			} else if (method.trim().equalsIgnoreCase("look")) {
				msg = forLook(request);
			} else if (method.trim().equalsIgnoreCase("pagePanelButton")) {
				msg = forPanelButton(request);
			} else if (method.trim().equalsIgnoreCase("gridTreeHead")) {
				msg = forGridTreeHead(request);
			} 
		}
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(msg);
		out.flush();
		out.close();
	}
	/**
	 * Ϊ�����б��ṩ��ͷ
	 * @Methods Name forGridTreeHead
	 * @Create In Mar 13, 2009 By lee
	 * @param request
	 * @return String
	 */
	private String forGridTreeHead(HttpServletRequest request){
		String panelName = request.getParameter("panelname");
    	PagePanel pagePanel = pagePanelService.findPagePanel(panelName);
    	if(pagePanel==null)return null;
//    	String className = pagePanel.getSystemMainTable().getClassName();
//    	List<UserTableSetting> utss = metaDataManager.getUserColumnForList(getClass(className));
//    	PagePanel panel=pagePanelService.findPagePanel(panelName);
		List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);
    	String json="";
        	for(PagePanelColumn ppcolumn : pagePanelColumns){  //�ĳɱ���
        		Column column = ppcolumn.getColumn();
        		String tableName = ppcolumn.getSystemMainTable().getTableName();
        		String propertyName = column.getPropertyName();	
        		json+="{name:"+"'"+tableName+"$"+propertyName+"'"+"},";
        	}
        	//json+="{name:'_id'},";
        	json+="{name:'_parent'},";
        	json+="{name:'_level'},";
        	json+="{name:'_lft'},";
        	json+="{name:'_rgt'},";
        	json+="{name:'_is_leaf'}";
    		json="["+json+"]";
		return json;
	}
	private String forModelButton(HttpServletRequest request) {
		String keyName = request.getParameter("modelName");
		PageModel model = pageModelService.findPageModel(keyName);
		String json="[";
		List<PageModelBtn> pmbs=pageModelService.findPageModelBtnByModel(model);
		if(pmbs.isEmpty()){
			json+="";
		}else{
			for(PageModelBtn pmb:pmbs){
				if(pmb.getIsDisplay()==1){
				json+="{";
				json += "\"btnName\":\""+pmb.getBtnName()+"\",";
				json += "\"container\":\""+pmb.getPageModel().getName()+"\",";
				json += "\"containerTable\":\""+pmb.getPageModel().getSystemMainTable().getTableName()+"\",";
				json += "\"method\":\""+pmb.getMethod()+"\",";
				json += "\"link\":\""+pmb.getLink()+"\",";
				json += "\"nextPageModel\":\""+(pmb.getNextPageModel()==null?"":pmb.getNextPageModel().getName())+"\",";
				json += "\"imageUrl\":\""+pmb.getImageUrl()+"\"";
				json+="},";
				}
			}
			if(json.length()>1)
			json = json.substring(0, json.length()-1);
		}
		json += "]";
		return json;
	}
	
	private String forPanelButton(HttpServletRequest request) {
		String keyName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(keyName);
		String json="[";
		List<PagePanelBtn> ppbs=pagePanelBtnService.findPanelBtnByPanel(panel);
		if(ppbs.isEmpty()){
			json+="";
		}else{
			for(PagePanelBtn ppb:ppbs){
				if(ppb.getIsDisplay()!=null&&ppb.getIsDisplay()==1){
				json+="{";
				json += "\"btnName\":\""+ppb.getBtnName()+"\",";
				json += "\"container\":\""+ppb.getPagePanel().getName()+"\",";
				json += "\"containerTable\":\""+ppb.getPagePanel().getSystemMainTable().getTableName()+"\",";
				json += "\"method\":\""+ppb.getMethod()+"\",";
				json += "\"link\":\""+ppb.getLink()+"\",";
				json += "\"nextPageModel\":\""+(ppb.getNextPageModel()==null?"":ppb.getNextPageModel().getName())+"\",";
				json += "\"imageUrl\":\""+ppb.getImageUrl()+"\"";
				json+="},";
				}
			}
			if(json.length()>1)
			json = json.substring(0, json.length()-1);
		}
		json += "]";
		return json;
	}

	private String genPagePanelJson(PageModelPanel pmp){
		String json = "";
		PagePanel panel = pmp.getPagePanel();
		String divFloat = pmp.getDivFloat();
		String fcolumnPropName = "";
		String pcolumnPropName = "";
		json+="{";
		json += "\"panelname\":\""+panel.getName()+"\",";
		json += "\"title\":\""+panel.getTitle()+"\",";
		if(panel.getGroupFlag()!=null&& panel.getGroupFlag().intValue()==0){
			json += "\"panelTableName\":\""+panel.getSystemMainTable().getTableCnName()+"\",";
			json += "\"clazz\":\""+panel.getSystemMainTable().getClassName()+"\",";
			//���model��������ڿɱ༭��壬��������ĳ�editorGrid����������������
			if(panel.getXtype().getName().equalsIgnoreCase("editorgrid")||panel.getXtype().getName().equalsIgnoreCase("grid")){
				SystemMainTable subTable = panel.getSystemMainTable();
				List<PageModelPanelTable>  pmpts = this.pageModelService.findPageModelPanelTableBySub(
							pmp.getPageModel(), panel, subTable);
				for(PageModelPanelTable pmpt : pmpts){
					//���������
					PagePanel parentPanel = pmpt.getParentPagePanel();
					//�������
					SystemMainTable parentTable = parentPanel.getSystemMainTable();
					SystemMainTableColumn parentPanelTablePColumn = pmpt.getParentPanelTablePColumn();
					//ConfigItem$id
					pcolumnPropName = parentTable.getTableName()+"$"+parentPanelTablePColumn.getPropertyName();
					SystemMainTableColumn subFc = pmpt.getSubPanelTableFColumn();
					fcolumnPropName = subFc.getPropertyName();
				}
				
			}
		}

		json += "\"fcolumnName\":\""+fcolumnPropName+"\",";
		json += "\"pcolumnName\":\""+pcolumnPropName+"\",";
		json += "\"xtype\":\""+panel.getXtype().getName()+"\",";
		json += "\"groupFlag\":\""+panel.getGroupFlag()+"\",";
		
		Integer readonlyFlag = pmp.getReadonly();
		if(readonlyFlag==null) readonlyFlag=0;
		json += "\"readonlyFlag\":\""+readonlyFlag+"\",";
	
		json += "\"queryFlag\":\""+panel.getQueryFlag()+"\",";
		json += "\"divFloat\":\""+divFloat+"\",";
		json += "\"order\":\""+pmp.getOrder()+"\"";
		Set<PagePanelRelation> childpprs = panel.getChildPagePanels();
		if(!childpprs.isEmpty()){   //�������������������
			json += ",\"childPagePanels\":[";
			for(PagePanelRelation ppr : childpprs){
				json+= this.genPagePanelJson(ppr);
				json+=",";
			}
			json = json.substring(0, json.length()-1);
			json += "]";
		}
		json+="}";
		return json;
	}
	
	/**
	 * �����������JSON����
	 * @Methods Name genPagePanelJson
	 * @Create In Apr 20, 2009 By sa
	 * @param panel
	 * @return String
	 */
	@SuppressWarnings("unused")
	private String genPagePanelJson(PagePanel panel){
		String json = "";
		//PagePanel panel = pmp.getPagePanel();
		//String divFloat = pmp.getDivFloat();
		String fcolumnPropName = "";
		String pcolumnPropName = "";
		json+="{";
		json += "\"panelname\":\""+panel.getName()+"\",";
		json += "\"title\":\""+panel.getTitle()+"\",";
		if(panel.getGroupFlag()!=null&& panel.getGroupFlag().intValue()==0){
			json += "\"panelTableName\":\""+panel.getSystemMainTable().getTableCnName()+"\",";
			json += "\"clazz\":\""+panel.getSystemMainTable().getClassName()+"\",";
			//�˴�ȱ������ж��Ĺ�ϵ����
//			if(panel.getXtype().getName().equalsIgnoreCase("editorgrid")||panel.getXtype().getName().equalsIgnoreCase("grid")){
//				SystemMainTable subTable = panel.getSystemMainTable();
//				List<PageModelPanelTable>  pmpts = this.pageModelService.findPageModelPanelTableBySub(
//							pmp.getPageModel(), panel, subTable);
//				for(PageModelPanelTable pmpt : pmpts){
//					//���������
//					PagePanel parentPanel = pmpt.getParentPagePanel();
//					//�������
//					SystemMainTable parentTable = parentPanel.getSystemMainTable();
//					SystemMainTableColumn parentPanelTablePColumn = pmpt.getParentPanelTablePColumn();
//					//ConfigItem$id
//					pcolumnPropName = parentTable.getTableName()+"$"+parentPanelTablePColumn.getPropertyName();
//					SystemMainTableColumn subFc = pmpt.getSubPanelTableFColumn();
//					fcolumnPropName = subFc.getPropertyName();
//				}
//				
//			}
		}

		json += "\"fcolumnName\":\""+fcolumnPropName+"\",";
		json += "\"pcolumnName\":\""+pcolumnPropName+"\",";
		json += "\"xtype\":\""+panel.getXtype().getName()+"\",";
		json += "\"groupFlag\":\""+panel.getGroupFlag()+"\",";
		
//		Integer readonlyFlag = pmp.getReadonly();
//		if(readonlyFlag==null) readonlyFlag=0;
//		json += "\"readonlyFlag\":\""+readonlyFlag+"\",";
	
		json += "\"queryFlag\":\""+panel.getQueryFlag()+"\",";
		//json += "\"divFloat\":\""+divFloat+"\"";
		//json += "\"order\":\""+pmp.getOrder()+"\"";
		Set<PagePanelRelation> childpprs = panel.getChildPagePanels();
		if(!childpprs.isEmpty()){   //�������������������
			json += ",\"childPagePanels\":[";
			for(PagePanelRelation ppr : childpprs){
				json+= this.genPagePanelJson(ppr);
				json+=",";
			}
			json = json.substring(0, json.length()-1);
			json += "]";
		}
		json+="}";
		return json;
	}
	
	
	
	private String genPagePanelJson(PagePanelRelation pmp){
		String json = "";
		PagePanel panel = pmp.getPagePanel();
		String fcolumnPropName = "";
		String pcolumnPropName = "";
		json+="{";
		json += "\"panelname\":\""+panel.getName()+"\",";
		json += "\"title\":\""+panel.getTitle()+"\",";
		if(panel.getGroupFlag()!=null&& panel.getGroupFlag().intValue()==0){
			json += "\"panelTableName\":\""+panel.getSystemMainTable().getTableCnName()+"\",";
			json += "\"clazz\":\""+panel.getSystemMainTable().getClassName()+"\",";
			List<PageGroupPanelTable> list = this.pgps.findGroupPanelTableBySub(pmp.getParentPagePanel(), panel);
			PageGroupPanelTable pgpt = null;
			if(list!=null && !list.isEmpty()){
				pgpt = list.iterator().next();
			}
			if(pgpt!=null){
				SystemMainTableColumn fcolumn = pgpt.getSubPanelTableFColumn();
				fcolumnPropName = fcolumn.getPropertyName();
				
				SystemMainTable parentPanelTable = pgpt.getParentPanelTable();
				String pptableName = parentPanelTable.getTableName();
				SystemMainTableColumn pc = pgpt.getParentPanelTablePColumn();
				pcolumnPropName = pc.getPropertyName();
				pcolumnPropName = pptableName +"$"+ pcolumnPropName;
				
			}
			
		}
		json += "\"fcolumnName\":\""+fcolumnPropName+"\",";
		json += "\"pcolumnName\":\""+pcolumnPropName+ "\",";
		json += "\"xtype\":\""+panel.getXtype().getName()+"\",";
		
		Integer readonlyFlag = pmp.getReadonly();
		if(readonlyFlag==null) readonlyFlag=0;
		json += "\"readonlyFlag\":\""+readonlyFlag+"\",";
		
		json += "\"groupFlag\":\""+panel.getGroupFlag()+"\",";
		json += "\"queryFlag\":\""+panel.getQueryFlag()+"\",";
		json += "\"order\":\""+pmp.getOrder()+"\"";
		
		Set<PagePanelRelation> childpprs = panel.getChildPagePanels();
		if(!childpprs.isEmpty()){
			json += ",\"childPagePanels\":[";
			for(PagePanelRelation ppr : childpprs){
				json+= this.genPagePanelJson(ppr);
				json+=",";
			}
			json = json.substring(0, json.length()-1);
			json += "]";
		}
		json+="}";
		return json;
	}

	private String forModel(HttpServletRequest request) {
		String keyName = request.getParameter("name");
//		String dataId = request.getParameter("dataId");
		//����model�Ĺؼ���ȡmodel���˷����ǿ����µķ������˼�����Ƶģ��ײ�������ȡ�������������
		PageModel model = pageModelService.findPageModel$$$$$(keyName);
		String json = "";
		json="{\"pageModel\":[{";
		json += "\"name\":\""+model.getName()+"\",";
		json += "\"title\":\""+model.getTitle()+"\",";
		json += "\"modelTableName\":\""+model.getSystemMainTable().getTableName()+"\",";
		json += "\"pagePathType\":\""+model.getPagePanelType().getName()+"\",";
		json += "\"pagePath\":\""+model.getPagePath()+"\"";
		json+="}],";
		//end
			
		List<PageModelPanel> pmps = model.getPagePanels();
		if(pmps.isEmpty()){
			json=""; //����ҳ��modelû��ѡ��panel
			return json;
		}//end��ť����
		
		//����panel
		json+="\"panels\":[";
		for(PageModelPanel item : pmps){ //����model�µĸ����
			//����pageModel�����json
			json+= this.genPagePanelJson(item); //����ݹ鷽��������������
			json+=",";
			
		}
		json = json.substring(0, json.length()-1);
		json += "]}";
		//*******************************************************************
		System.out.println(json);
		return json;
	}

	
//	private String forModelConfigItem(HttpServletRequest request) {
//		String keyName = request.getParameter("name");
//		String dataId = request.getParameter("dataId");
//		//����model�Ĺؼ���ȡmodel���˷����ǿ����µķ������˼�����Ƶģ��ײ�������ȡ�������������
//		PageModel model = pageModelService.findPageModel$$$$$(keyName);
//		String json = "";
//		json="{\"pageModel\":[{";
//		json += "\"name\":\""+model.getName()+"\",";
//		json += "\"title\":\""+model.getTitle()+"\",";
//		json += "\"modelTableName\":\""+model.getSystemMainTable().getTableName()+"\",";
//		json += "\"pagePathType\":\""+model.getPagePanelType().getName()+"\",";
//		json += "\"pagePath\":\""+model.getPagePath()+"\"";
//		json+="}],";
//		List<PageModelPanel> pmps = null;
//		
////		���������id���գ��������ǵ�����������ȡ��̬��panel
//		if(StringUtils.isNotBlank(dataId)&& !dataId.equals("0")){
//			pmps = configItemService.findPageModelPanel(keyName, dataId);
//		}else{
//			pmps = model.getPagePanels();
//		}
//		if(pmps.isEmpty()){
//			json=""; //����ҳ��modelû��ѡ��panel
//			return json;
//		}//end��ť����
//		
//		//����panel
//		json+="\"panels\":[";
//		for(PageModelPanel item : pmps){ //����model�µĸ����
//			//����pageModel�����json
//			json+= this.genPagePanelJson(item); //����ݹ鷽��������������
//			json+=",";
//			
//		}
//		json = json.substring(0, json.length()-1);
//		json += "]}";
//		//*******************************************************************
//		System.out.println(json);
//		return json;
//	}

	/**
	 * ����ʹ�õ�Ԫ����
	 * @Methods Name forSave
	 * @Create In Sep 9, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	private String forSave(HttpServletRequest request) {
		String json = "";
		String id = request.getParameter("id");

		String modelName = request.getParameter("modelname");
		String panelName = request.getParameter("panelname");
		PagePanel panel=pagePanelService.findPagePanel(panelName);
		List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);

		Map<String, Object> dataMap = null;
		if (StringUtils.isNotBlank(id)) {
			List<Map<String,Object>> listMap = null;
			//�˴���������Ӧ���������
			//listMap = configItemService.getRequireCIDataForEdit(modelName, panelName, id);
//			}else{
			listMap = pageManager.getPagePanelDataForEdit(modelName, panelName, id);
//			}
			 
			if(listMap!=null&& !listMap.isEmpty()){
				if(panel.getXtype().getName().equals("form")){
					dataMap = listMap.iterator().next();
					String webContext = request.getContextPath();
					dataMap.put("webContext", webContext);
					json = CoderForSave.encode(pagePanelColumns, dataMap, true);
					
				}else if(panel.getXtype().getName().equals("editorgrid")){
					int total = listMap.size();
					json = CoderForList.encode(pagePanelColumns, listMap, Long.valueOf(total));
				}
			}
			
		} else {
			dataMap = pageManager.getPagePanelDataForAdd(panelName);
			json = CoderForSave.encode(pagePanelColumns, dataMap , false);	
		}	
		
		return json;
	}
	
	/**
	 * ֻ��Ԥ������
	 * @Methods Name forLook
	 * @Create In 2009-3-13 By sa
	 * @param request
	 * @return String
	 */
	private String forLook(HttpServletRequest request) {
		String json = "";
		String id = request.getParameter("id");

		String modelName = request.getParameter("modelname");
		String panelName = request.getParameter("panelname");
		PagePanel panel=pagePanelService.findPagePanel(panelName);
		List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);

		Map<String, Object> dataMap = null;
		if (StringUtils.isNotBlank(id)) {
			List<Map<String,Object>> listMap = pageManager.getPagePanelDataForLook(modelName, panelName, id);
			 
			if(listMap!=null&& !listMap.isEmpty()){
				if(panel.getXtype().getName().equals("form")){
					dataMap = listMap.iterator().next();
					json = CoderForLook.encode(pagePanelColumns, dataMap, true);
					
				}else if(panel.getXtype().getName().equals("editorgrid")){
					int total = listMap.size();
					json = CoderForList.encode(pagePanelColumns, listMap, Long.valueOf(total));
				}
			}
			
		} else {
			dataMap = pageManager.getPagePanelDataForAdd(panelName);
			json = CoderForLook.encode(pagePanelColumns, dataMap , false);	
		}	
		
		return json;
	}
	
	/**
	 * ��ȡ��Form���Ԫ����
	 * @Methods Name forSaveSingleForm
	 * @Create 2009-3-4 By lee
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	private String forSaveSingleForm(HttpServletRequest request) {
		String json = "";
		String id = request.getParameter("id");
		String panelName = request.getParameter("panelname");
		PagePanel panel=pagePanelService.findPagePanel(panelName);
		SystemMainTable smt= panel.getSystemMainTable();
		String tableName = smt.getTableName();
		String className = smt.getClassName();
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);

		Map<String, Object> dataMap = null;
		if (StringUtils.isNotBlank(id)) {
			if(panel.getXtype().getName().equals("form")){
				Object obj = service.find(clazz, id, true);
				dataMap = metaDataManager.getEntityDataForEdit(obj,tableName);
				json = CoderForSave.encode(pagePanelColumns, dataMap, true);
					
			}else {
				json = null;
			}
			
		} else {
			dataMap = pageManager.getPagePanelDataForAdd(panelName);
			json = CoderForSave.encode(pagePanelColumns, dataMap , false);	
		}	
		
		return json;
	}
	/**
	 * ��ȡ��Form���Ԫ����
	 * @Methods Name forSaveSingleFormForLook
	 * @Create 2009-4-20 By daijf
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	private String forSaveSingleFormForLook(HttpServletRequest request) {
		String json = "";
		String id = request.getParameter("id");
		String panelName = request.getParameter("panelname");
		PagePanel panel=pagePanelService.findPagePanel(panelName);
		SystemMainTable smt= panel.getSystemMainTable();
		String tableName = smt.getTableName();
		String className = smt.getClassName();
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);

		Map<String, Object> dataMap = null;
		if (StringUtils.isNotBlank(id)) {
			if(panel.getXtype().getName().equals("form")){
				Object obj = service.find(clazz, id, true);
				dataMap = metaDataManager.getEntityDataForLook(obj,tableName);
				json = CoderForLook.encode(pagePanelColumns, dataMap, true);
					
			}else {
				json = null;
			}
			
		} else {
			dataMap = pageManager.getPagePanelDataForAdd(panelName);
			json = CoderForLook.encode(pagePanelColumns, dataMap , false);	
		}	
		
		return json;
	}
	/**
	 * ������������Ϣ
	 * @Methods Name forSaveConfigItem
	 * @Create In 2008-12-31 By sa
	 * @param request
	 * @return String
	 */
//	private String forSaveConfigItem(HttpServletRequest request) {
//		String json = "";
//		String id = request.getParameter("id");
//
//		String modelName = request.getParameter("modelname");
//		String panelName = request.getParameter("panelname");
//		PagePanel panel=pagePanelService.findPagePanel(panelName);
//		List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);
//
//		Map<String, Object> dataMap = null;
//		if (StringUtils.isNotBlank(id)) {
//			List<Map<String,Object>> listMap = configItemService.getPagePanelDataForEdit(modelName, panelName, id);
//			if(listMap!=null&& !listMap.isEmpty()){
//				if(panel.getXtype().getName().equals("form")){
//					dataMap = listMap.iterator().next();
//					json = CoderForSave.encode(pagePanelColumns, dataMap, true);
//					
//				}else if(panel.getXtype().getName().equals("editorgrid")){
//					int total = listMap.size();
//					json = CoderForList.encode(pagePanelColumns, listMap, Long.valueOf(total));
//				}
//			}
//			
//		} else {
//			dataMap = pageManager.getPagePanelDataForAdd(panelName);
//			json = CoderForSave.encode(pagePanelColumns, dataMap , false);	
//		}	
//		
//		return json;
//	}
//	
	
//	private String forSaveRequireConfigItem(HttpServletRequest request) {
//		String json = "";
//		String id = request.getParameter("id");
//
//		String modelName = request.getParameter("modelname");
//		String panelName = request.getParameter("panelname");
//		PagePanel panel=pagePanelService.findPagePanel(panelName);
//		List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);
//
//		Map<String, Object> dataMap = null;
//		if (StringUtils.isNotBlank(id)) {
//			List<Map<String,Object>> listMap = configItemService.getRequireCIDataForEdit(modelName, panelName, id);
//			if(listMap!=null&& !listMap.isEmpty()){
//				if(panel.getXtype().getName().equals("form")){
//					dataMap = listMap.iterator().next();
//					json = CoderForSave.encode(pagePanelColumns, dataMap, true);
//					
//				}else if(panel.getXtype().getName().equals("editorgrid")){
//					int total = listMap.size();
//					json = CoderForList.encode(pagePanelColumns, listMap, Long.valueOf(total));
//				}
//			}
//			
//		} else {
//			dataMap = pageManager.getPagePanelDataForAdd(panelName);
//			json = CoderForSave.encode(pagePanelColumns, dataMap , false);	
//		}	
//		
//		return json;
//	}

	/**
	 * ʹ��EXTJS������Ʋ�ѯ���ʱ��Ҫʹ�õ�Ԫ���ݺ͹�������
	 * @Methods Name forQuery
	 * @Create In Aug 30, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	private String forQuery(HttpServletRequest request) {
		String json = "";
//		String id = request.getParameter("id");
		String panelName = request.getParameter("panelname");
		PagePanel panel=pagePanelService.findPagePanel(panelName);
		SystemMainTable smt = panel.getSystemMainTable();
		String className = smt.getClassName();
		Class clazz = this.getClass(className);
		List<UserTableQueryColumn> userQueryColumns = metaDataManager.getUserColumnForQuery(clazz);
		Map queryMap = this.pageManager.getPagePanelDataForQuery(panelName);
		json = CoderForFind.encode(queryMap, userQueryColumns);
		return json;
	}
	
	/**
	 * ʹ��Html���Ʋ�ѯ���ʱ��Ҫʹ�õ�Ԫ���ݺ͹�������
	 * @Methods Name forHtmlQuery
	 * @Create In 2009-1-2 By sa
	 * @param request
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	private String forHtmlQuery(HttpServletRequest request) {
		String json = "";
		String className = request.getParameter("clazz");
		Class clazz = getClass(className);
		List<UserTableQueryColumn> userQueryColumns = metaDataManager.getUserColumnForQuery(clazz);
		Map queryMap = metaDataManager.getEntityDataForAdd(clazz);
		json = ExtHtmlBuilder.genQueryHtml(queryMap, userQueryColumns);
		return json;
	}

	/**
	 * GridPanel��ͷʹ�õ�Ԫ����, ����Ҫ����
	 * @Methods Name forHead
	 * @Create In 2009-1-2 By sa
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	private String forListHead(HttpServletRequest request) {
		String json = "";
		String panelName = request.getParameter("panelname");
		//PagePanel panel=pagePanelService.findPagePanel(panelName);
		List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);
		json = CoderForHead.encode(pagePanelColumns);

		return json;
	}
	
	/**
	 * EditorGridPanel��ͷ����Ҫ�������ݣ��������б���Ƿ��б�
	 * @Methods Name forHead
	 * @Create In 2009-1-2 By sa
	 * @param request
	 * @return String
	 */
	private String forHead(HttpServletRequest request) {
		String json = "";
		String panelName = request.getParameter("panelname");
		//PagePanel panel=pagePanelService.findPagePanel(panelName);
		List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);
		
		Map<String, Object> dataMap = null;
		dataMap = pageManager.getPagePanelDataForAdd(panelName);//editorGridʹ��forAdd�Ƿ����
		json = CoderForHead.encode(pagePanelColumns, dataMap);	
		return json;
	}

	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.print("������" + className + "����ȷ��");
			e.printStackTrace();
		}
		return clazz;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}


//	/**
//	 * ����ʹ�õ�Ԫ����
//	 * 
//	 * @Methods Name forAdd
//	 * @Create In Aug 30, 2008 By yang
//	 * @param request
//	 * @return
//	 * @ReturnType String
//	 */
//	@SuppressWarnings("unchecked")
//	private String forAdd(HttpServletRequest request) {
//		String json = "";
//		String pClazz = request.getParameter("clazz");
//		Class<Object> clazz = getClass(pClazz);
//		List<UserTableSetting> columns = metaDataManager
//				.getUserColumnForEdit(clazz);
//		Map<String, Object> addMap = metaDataManager.getEntityDataForAdd(clazz);
//		json = CoderForAdd.encode(addMap, columns);
//		return json;
//	}
//
//	/**
//	 * �޸�ʹ�õ�Ԫ����
//	 * 
//	 * @Methods Name forEdit
//	 * @Create In Aug 30, 2008 By yang
//	 * @param request
//	 * @return
//	 * @ReturnType String
//	 */
//	private String forEdit(HttpServletRequest request) {
//		String json = "";
//		String id = request.getParameter("id");
//		String className = request.getParameter("clazz");
//		Class clazz = getClass(className);
//		List<UserTableSetting> columns = metaDataManager
//				.getUserColumnForEdit(clazz);
//		if (StringUtils.isNotBlank(id)) {
//			Object detail = (Object) service.find(clazz, id, true);
//			Map<String, Object> editMap = metaDataManager.getEntityDataForEdit(
//					clazz, id);
//			json = CoderForEdit.encode(editMap, columns);
//		} else {
//			json = "no id is specified.";
//		}
//
//		return json;
//	}
//	
//	if (StringUtils.isNotBlank(id)) {
//		dataMap = pageManager.getPagePanelDataForEdit(panelName, id);
//		json = CoderForHead.encode(pagePanelColumns, dataMap, true);
//	} else {
//		dataMap = pageManager.getPagePanelDataForAdd(panelName);
//		json = CoderForHead.encode(pagePanelColumns, dataMap , false);	
//	}
}
