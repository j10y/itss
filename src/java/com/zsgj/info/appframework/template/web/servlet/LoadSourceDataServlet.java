package com.zsgj.info.appframework.template.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.template.service.TemplateService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;

/**
 * ����ģ��Դ����
 * @Class Name LoadSourceDataServlet
 * @author hp
 * @Create In Sep 9, 2008
 * TODO
 */
@SuppressWarnings("serial")
public class LoadSourceDataServlet extends HttpServlet {
	
	private TemplateService templateService = (TemplateService) getBean("templateService");
	private SystemMainTableService smtService = (SystemMainTableService) getBean("systemMainTableService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//����Id
		String smtId = request.getParameter("smtId");
		SystemMainTable smt = smtService.findSystemMainTable(smtId);
		List<Column>  columnList = templateService.findColumnByTemplateTable(smt);
		
//		request.setAttribute("list",columnList);
		String json ="";
		for(int i=0; i< columnList.size(); i++){
			Column item = columnList.get(i);
			Long id = item.getId();
			String name = item.getColumnCnName();
			//leaf��false,����ʹ������Ϊ���ӽ�㣬Ҳ����ʹ������ΪҶ�ӽ�������ק
			//��cls���������������������չ��
//			json += "{\"id\":"+id+",\"name\":\""+name+"\",\"leaf\":false},";
			//if(item instanceof SystemMainTableColumn){
				json += "{\"id\":"+id+",\"name\":\""+name+"\",\"leaf\":false,\"cls\":\"mainColumn\",\"icon\":\""+smtId+"\"},";
//			}else if(item instanceof SystemMainTableExtColumn){
//				json += "{\"id\":"+id+",\"name\":\""+name+"\",\"leaf\":false,\"cls\":\"extendColumn\",\"icon\":\""+smtId+"\"},";
//			}
		}
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		System.out.println("ģ��ϵͳ���� ��"+json);
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����spring����ķ���service
	 * @param name
	 * @return
	 */
	protected Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if(serviceBean==null) {
			throw new ServiceException("û������Ϊ��" + name + "�ķ��񣡣�");
		}
		return serviceBean;
	}
}
