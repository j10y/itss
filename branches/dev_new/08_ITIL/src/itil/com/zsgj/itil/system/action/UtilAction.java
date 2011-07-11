package com.zsgj.itil.system.action;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.BeanUtils;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.util.EntityBeanUtils;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.itil.system.service.UtilService;

/**
 * @Class Name UtilAction
 * @Author awen
 * @Create In 2011-7-6
 */
public class UtilAction extends BaseDispatchAction {

	private UtilService utilService = (UtilService) getBean("utilService");

	/**
	 * comboģ����ѯ��ILIKE���Դ�Сд��matchModeΪANY��ͨ�ù��߷���<br>
	 * ע�⣺<br>
	 * 1,classNameΪcom.zsgj.itil.**��ʽ��entity�ַ������Ҵ�entity������Hibernateʵ��ӳ��<br> �ǿ�
	 * 2,propertyNameΪ��ѯ�ֶ����� nameField������ͬ�����Ǻ���propertyName�������������ʽ����ֶ�ƥ�䣬���Էֿ��� valueFieldһ�㲻ͬ<br> �ǿ� 
	 * 3,propertyValueΪ��ѯ�ֶ�ֵ<br>
	 * 4,isLike Ĭ��Ϊtrue�����ַ���ilikeƥ��<br>
	 * 5,orderBy �����ֶ�<br>
	 * 6,isAsc Ĭ������<br>
	 * 7,start<br>
	 * 8,pageSize Ĭ��Ϊ10<br>
	 * 9,nameField �ǿ�,����|�Ļ���ʾ����ֶηָ��username|realname<br>
	 * 10,valueField �ǿ�<br>
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchComboMessage(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer json = new StringBuffer();
		try {
			String className = request.getParameter("className");
			if(className==null||className.equals("")){
				throw new IllegalArgumentException("parameter className not assigned");
			}			
			Class clazz = Class.forName(className);			
			Method nameFileGetter = null;
			Method valueFileGetter = null;
			
			int start = request.getParameter("start") != null && !"".equals(request.getParameter("start")) ? Integer.valueOf(request.getParameter("start")) : 1;
			String propertyName = request.getParameter("propertyName");//��ѯ�ֶ�
			String protertyValue = request.getParameter("propertyValue");//��ѯ�ֶ�ֵ
			
			
			if(propertyName==null||propertyName.equals("")){
				throw new IllegalArgumentException("parameter propertyName not assigned");
			}
			
			String orderBy = request.getParameter("orderBy");//�����ֶ�
			boolean isLike = request.getParameter("isLike")!=null&&request.getParameter("isLike").equals("") ? Boolean.valueOf(request.getParameter("isLike")) : true;
			boolean isAsc = request.getParameter("isAsc")!=null&&request.getParameter("isAsc").equals("") ? Boolean.valueOf(request.getParameter("isAsc")) : true;
			
			String nameField = request.getParameter("nameField");//��ʾ�ֶ�
			String valueField = request.getParameter("valueField");//ֵ�ֶ�
			if(nameField==null||nameField.equals("")){
				throw new IllegalArgumentException("parameter nameField not assigned");
			}
			if(valueField==null||valueField.equals("")){
				throw new IllegalArgumentException("parameter valueField not assigned");
			}
			if(request.getParameter("pageSize")!=null && !request.getParameter("pageSize").equals("")){
				pageSize = Integer.valueOf(request.getParameter("pageSize"));
				
			}
			
			Page page = utilService.searchComboMessage(clazz, propertyName, protertyValue, isLike, orderBy, isAsc, start, pageSize);
			List list = page.list();
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<list.size(); i++){
				StringBuffer name = new StringBuffer();
				if(!nameField.contains("|")){
					name.append(EntityBeanUtils.forceGetProperty(list.get(i), nameField).toString());
				}else{
					String [] nameFields = nameField.split("\\|");
					for(int j=0; j<nameFields.length; j++){
						name.append(EntityBeanUtils.forceGetProperty(list.get(i), nameFields[j]).toString());
						if(j<nameFields.length-1){
							name.append("/");
						}
					}
				}
				
				String value = EntityBeanUtils.forceGetProperty(list.get(i), valueField).toString();
				sb.append("{");
//				sb.append(nameField + ":'" + name.toString() + "',");
//				sb.append(valueField + ":'" + value + "'");
				sb.append("dataName:'" + name.toString() + "',");
				sb.append("dataValue:'" + value + "'");
				if(i<list.size()-1){
					sb.append("},");
				}else{
					sb.append("}");					
				}
			}
			long total = page.getTotalCount().longValue();
			json.append("{success:true,rowCount:'" + total + "',data:[" + sb.toString() + "]}");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.append("{success:false,msg:'" + e.getMessage() + "'}");
		}
		
		response.setContentType("text/json;charset=gbk");
		response.getWriter().write(json.toString());		
		response.getWriter().close();		
		return null;
	}
}
