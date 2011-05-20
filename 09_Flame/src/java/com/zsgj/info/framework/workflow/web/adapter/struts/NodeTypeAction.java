package com.zsgj.info.framework.workflow.web.adapter.struts;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.info.framework.workflow.DefinitionService;
import com.zsgj.info.framework.workflow.entity.ConfigModel;
import com.zsgj.info.framework.workflow.entity.ConfigUnit;
import com.zsgj.info.framework.workflow.entity.NodeType;

@SuppressWarnings("serial")
public class NodeTypeAction extends BaseAction {
	
	DefinitionService ds = (DefinitionService)ContextHolder.getBean("definitionService");
	MetaDataManager metaDataManager = (MetaDataManager) getBean("metaDataManager");
	
	public ActionForward listAll(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
//		//String actor = request.getParameter("actor");
//	  	//List list = ts.listAllTasks();
//	  	request.setAttribute("tasks", list);
//	  	request.setAttribute("length", list.size());
		return mapping.findForward("success");
	}
	/**
	 * ��ʾ���нڵ���������
	 * @Methods Name showAllNodeType
	 * @Create In Feb 16, 2009 By guangsa
	 * @return String
	 */
	public String showAllNodeType(){
		List list = super.getService().findAll(NodeType.class);		
		String json = "";
		for(int i=0; i< list.size(); i++){
		NodeType nodeType = (NodeType)list.get(i);			
		Long id = nodeType.getId();
		String name = nodeType.getName();
		String desc = nodeType.getDescription();
		String pattern = nodeType.getNamePattern();
		String link = nodeType.getLink(); 
		json += "{\"id\":"+id+",\"link\":\""+link+"\",\"pattern\":\""+pattern+"\",\"desc\":\""+desc+"\",\"name\":\""+name+"\"},";
		}
		if(json.length()!=0){
			json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		}		
		System.out.println("�����û�ʱ,����ǰ̨��module���ݣ� "+json);	

		try {			
		super.getResponse().setCharacterEncoding("utf-8");
		PrintWriter pw = super.getResponse().getWriter();	
		pw.write(json);		
		} catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}
	/**
	 * ���������齨�������Ϣ
	 * @Methods Name modifyNodeTypeMessage
	 * @Create In Feb 16, 2009 By guangsa
	 * @return String
	 */
	public String modifyNodeTypeMessage(){
		String nodeTypeId = super.getRequest().getParameter("nodeId");
		NodeType nodeType = (NodeType)super.getService().find(NodeType.class, nodeTypeId);
		
		List listProperty = new ArrayList();		
		HashMap<String , Object> unit = new HashMap<String , Object>();
		unit.put("nodeTypeName", nodeType.getName()!=null ? nodeType.getName():"");
		unit.put("description", nodeType.getDescription()!=null ? nodeType.getDescription():"");
		//unit.put("url", nodeType.getLink()!=null ? nodeType.getLink():"");	
		unit.put("mark", nodeType.getNamePattern()!=null ? nodeType.getNamePattern():"");
		listProperty.add(unit);
			
//		JSONArray jsonObject = JSONArray.fromObject(listProperty);
		JSONObject jsonObject = JSONObject.fromObject(listProperty.get(0));
		System.out.println(jsonObject.toString());
		
		try {
			String json = "{success:" + true + ",list:"+ jsonObject.toString() + "}";
			super.getResponse().setCharacterEncoding("utf-8");
			super.getResponse().getWriter().write(json);
			super.getResponse().getWriter().flush();
						
		} catch (IOException e) {
			
			e.printStackTrace(); 
		}		 
		return null;
	}
	/**
	 * �����ڵ����ͺ����õ�Ԫ��ϵ
	 * �ص����ڣ�1.Ҫ��˭�Ľڵ����ж��Ƿ��Ѿ��ж�
	 * 			2.�����ԭ�������ϻ�ȥ�ˣ���ô��ɾ���Ϲ�ȥ��
	 * @Methods Name nodeTypeAndConfigUnit
	 * @Create In Feb 17, 2009 By guangsa
	 * @return String
	 */
	public String saveNodeTypeAndConfigUnit() {
		boolean mark = false;
		boolean sign = false;
		String nodeId = super.getRequest().getParameter("id");
		NodeType nodeType = (NodeType)super.getService().find(NodeType.class,nodeId, true);
		//�����Ǹ��ڵ����͵Ĺ�ϵ����(���õ�Ԫ��ڵ�����)
		List<ConfigModel> dataList = super.getService().find(ConfigModel.class, "nodeType", nodeType.getName());
				
		EntityUtil entityUtil = new EntityUtil();
		String data = super.getRequest().getParameter("dataForm");
		Map dataMap = entityUtil.getDataMap(data);
		String proxyArg = dataMap.get("proxySelect").toString();
		List<ConfigUnit> alreadyUnitlist = new ArrayList();
		//�Ȱѻ�ȡ���ķŵ�һ�����ϵ���
		if (!proxyArg.equals("")) {
			String ids[] = proxyArg.split(",");
			ConfigUnit configUnit = new ConfigUnit();
			for (String id : ids) {				
				configUnit = (ConfigUnit) super.getService().find(ConfigUnit.class, id, true);
				alreadyUnitlist.add(configUnit);
			}			
		}
		//�ȴӻ�ȡ���ĵ��������ݿ�Ĳ��ң�û�ҵ��ʹ�
		for(int i=0;i<alreadyUnitlist.size();i++){
			for(int j=0;j<dataList.size();j++){
				if(dataList.get(j).getConfigUnit().getName().equals(alreadyUnitlist.get(i).getName())){
					mark=true;
				}
			}
			if(mark!=true){
				ConfigModel configModel = new ConfigModel();
				configModel.setConfigUnit(alreadyUnitlist.get(i));
				configModel.setNodeType(nodeType.getName());
				super.getService().save(configModel);
			}
			mark=false;
		}
		//Ȼ���ڴ����ݿ⵽��ȡ���Ĳ��ң�û�ҵ���ɾ��
		List<ConfigModel> newdataList = super.getService().find(ConfigModel.class, "nodeType", nodeType.getName());
		for(int i=0;i<newdataList.size();i++){
			for(int j=0;j<alreadyUnitlist.size();j++){
				System.out.println(j);
				if(newdataList.get(i).getConfigUnit().getName().equals(alreadyUnitlist.get(j).getName())){
					sign=true;
				}
			}
			if(sign!=true){
				super.getService().remove(ConfigModel.class, newdataList.get(i).getId()+"");
			}
			sign=false;
		}
		return null;
	}
	/**
	 * ��ѡ�����Ѿ�ѡ�񷽷�
	 * @Methods Name searchSendPrizeOrganization
	 * @Create In Feb 16, 2009 By guangsa
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public String searchAlreadySelectConfigUnit() throws Exception {
		
		String id = super.getRequest().getParameter("id");
		NodeType nodeType = (NodeType)super.getService().find(NodeType.class, id);		
		List<ConfigModel> configModellist = super.getService().find(ConfigModel.class, "nodeType", nodeType.getName());		
		List<ConfigUnit> dataList = super.getService().findAll(ConfigUnit.class);
		try {
			String json = "";
			for(int i = 0;i<configModellist.size();i++){
				for(int j = 0;j<dataList.size();j++){
					if(configModellist.get(i).getConfigUnit().getName().equals(dataList.get(j).getName())){
						dataList.remove(j);				
					}
				}
			}
			for(ConfigUnit data : dataList){
				json += "[";
				json += "'" + data.getId() + "',";
				json += "'" + data.getName() + "'";
				json += "],";
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json = "[" + json + "]";
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter writer = super.getResponse().getWriter();
			writer.write(json);
			writer.flush();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter writer = super.getResponse().getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}

	}	
	/**
	 * ��ѡ����û��ѡ������õ�Ԫ����
	 * @Methods Name searchNoChooseSelectConfigUnit
	 * @Create In Feb 17, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public String searchNoChooseSelectConfigUnit() throws Exception {
		String id = super.getRequest().getParameter("id");
		NodeType nodeType = (NodeType)super.getService().find(NodeType.class, id);
		if (id.length() != 0) {
			List<ConfigModel> configModellist = super.getService().find(ConfigModel.class, "nodeType", nodeType.getName());
			try {
				String json = "";
				for(ConfigModel data : configModellist){
					ConfigUnit configUnit = data.getConfigUnit();
					json += "[";
					json += "'" + configUnit.getId() + "',";
					json += "'" + configUnit.getName() + "'";
					json += "],";
				}			
				if (json.endsWith(",")) {
					json = json.substring(0, json.length() - 1);
				}
				json = "[" + json + "]";
				super.getResponse().setCharacterEncoding("utf-8");
				PrintWriter writer = super.getResponse().getWriter();
				writer.write(json);
				writer.flush();
				return null;
			} catch (RuntimeException e) {
				e.printStackTrace();
				super.getResponse().setCharacterEncoding("utf-8");
				PrintWriter writer = super.getResponse().getWriter();
				writer.write("{success:false}");
				writer.flush();
				return null;
			}
		}
		return null;
	}
}
