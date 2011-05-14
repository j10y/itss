package com.metarnet.messageConf.action;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.metarnet.common.action.BaseAction;
import com.metarnet.common.context.ContextHolder;
import com.metarnet.common.entity.Page;
import com.metarnet.common.service.BaseService;
import com.metarnet.common.service.SelectDataService;
import com.metarnet.common.util.DateUtil;
import com.metarnet.messageConf.entity.MessageStatus;

public class MessageConfAction extends BaseAction{
	public ActionForward toMessageConf(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return  mapping.findForward("login");
	}
	public ActionForward toTest(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return  mapping.findForward("test");
	}
	public ActionForward getMessageList(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");
		String json="";
		String messageType=request.getParameter("messageType");
		Map rolesmap=(Map) request.getSession().getAttribute("rolesmap");
		String islowlevel=rolesmap.get("islowlevel").toString();
		if(islowlevel.equals("0")){
			String sql="select * from pis_message where type='"+messageType+"' order by messageid desc,messageid desc";
			//List<Map> list=selectDataService.getData(sql);
			Map mappage=selectDataService.getListForPage(request, sql);
			List<Map> list=(List) mappage.get("resoultList");
			Page page=(Page) mappage.get("p");
			json+="{totalProperty:"+page.getTotal()+",root:[";
			for(Map map:list){
				json+="{num:'"+map.get("name")+"',message:'"+map.get("value")+"',messageen:'"+map.get("en_value")+"'},";
			}
			if(list!=null&&list.size()>0){
				json=json.substring(0,json.length()-1);
			}
			json+="]}";
		}else{
			
			String sFilename =request.getRealPath("config")+"/UrgentInfo.xml";
			SAXBuilder SaxB = new SAXBuilder();
			  Document Doc = SaxB.build(sFilename);
			  Element infos = Doc.getRootElement(); 
			  List infolist = infos.getChildren("Item");
			  String start=request.getParameter("start");
			  String limit=request.getParameter("limit");
			  int statsnum=0;
			  if(start!=null){
				  statsnum= Integer.parseInt(start);
			  }
			  int i=0;
			  int m=statsnum;
			  json+="{totalProperty:"+infolist.size()+",root:[";
			  for (Iterator iter = infolist.iterator(); iter.hasNext();) {
				  	Element info = (Element) iter.next();
				  	String name =info.getChildTextTrim("name");
				  	String value =info.getChildTextTrim("value");
				  	String valueen =info.getChildTextTrim("valueen");
				  	if(start!=null&&limit!=null){
				  		int limitnum=Integer.parseInt(limit);
				  		if(m==i&&m<statsnum+limitnum){
				  			m++;
				  			json+="{num:'"+name+"',message:'"+value+"',messageen:'"+valueen+"'},";
				  		}
				  	}else{
				  		json+="{num:'"+name+"',message:'"+value+"',messageen:'"+valueen+"'},";
				  	}
				  	i++;
			  }
			  if(infolist!=null&&infolist.size()>0){
					json=json.substring(0,json.length()-1);
				}
			json+="]}";
		}
		json=json.replace("\n", " ").replace("\r", "");
		try {
        	response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			out.print(json);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
	} 
	
	public ActionForward saveMessageStatus(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map userMap=(Map) request.getSession().getAttribute("usermap");
		BaseService baseService = (BaseService) ContextHolder.getBean("baseService");
		String json="true";
		String id=request.getParameter("id");
		String messagetype=request.getParameter("messagetype");
		if(messagetype!=null){
			messagetype=java.net.URLDecoder.decode(messagetype,"UTF-8");
		}
		String descrption=request.getParameter("descrption");
		if(descrption!=null){
			descrption=java.net.URLDecoder.decode(descrption,"UTF-8");
		}
		String messagename=request.getParameter("messagename");
		if(messagename!=null){
			messagename=java.net.URLDecoder.decode(messagename,"UTF-8");
		}
		String messagestatus=request.getParameter("messagestatus");
		if(messagestatus!=null){
			messagestatus=java.net.URLDecoder.decode(messagestatus,"UTF-8");
		}
		
		String playerips=request.getParameter("playerip");
		if(playerips!=null){
			playerips=java.net.URLDecoder.decode(playerips,"UTF-8");
		}
		String [] playeripss=playerips.split(",");
		String starttime=request.getParameter("starttime");
		Date starttime1=DateUtil.getStringToDateFull(starttime);
		String endtime=request.getParameter("endtime");
		Date endtime1=DateUtil.getStringToDateFull(endtime);
		String createdate=request.getParameter("createdate");
		Date createdate1=DateUtil.getStringToDateFull(createdate);
		String messageflag=request.getParameter("messageflag");
		if(messageflag!=null){
			messageflag=java.net.URLDecoder.decode(messageflag,"UTF-8");
		}
		String messagelevel=request.getParameter("messagelevel");
		Integer meslev=null;
		if(messagelevel!=null){
			meslev=Integer.parseInt(messagelevel);
		}
		String isplans=request.getParameter("isplan");
		Integer isplan=0;
		if(isplans!=null&&isplans.length()>0){
			isplan=Integer.parseInt(isplans);
		}
		String planname=request.getParameter("planname");
		if(planname!=null&&planname.length()>0){
			planname=java.net.URLDecoder.decode(planname,"UTF-8");
		}
		Map rolesmap=(Map) request.getSession().getAttribute("rolesmap");
		String islowlevel=rolesmap.get("islowlevel").toString();
		try {
			if(islowlevel.equals("0")){
				for(String playerip:playeripss){
					MessageStatus ms=new MessageStatus();
					if(id!=null&&id.length()>0){
						Long idl=Long.parseLong(id);
						ms.setId(idl);
					}
					String username=request.getParameter("username");
					ms.setDescrption(descrption);
					ms.setStarttime(starttime1);
					ms.setEndtime(endtime1);
					ms.setPlayerip(playerip);
					ms.setMessageflag(messageflag);
					ms.setMessagelevel(meslev);
					ms.setCreatedate(createdate1);
					ms.setMessagename(messagename);
					ms.setMessageStatus(messagestatus);
					ms.setMessagetype(messagetype);
					ms.setUsername(username);
					ms.setIsplan(isplan);
					ms.setPlanname(planname);
					baseService.save(ms, MessageStatus.class, "id");
				}
			}else{
			}
			//撤销操作
			if(messageflag.equals("2")){
				//修改时间
				List<MessageStatus> mscallist=baseService.findObjectByPar(MessageStatus.class, "id", Long.parseLong(id));
				MessageStatus mscal=mscallist.get(0);
				Date endtime_1=new Date();
				mscal.setEndtime(endtime_1);
				baseService.save(mscal, MessageStatus.class, "id");
				List<MessageStatus> mslist=baseService.findObjectByPar(MessageStatus.class, "playerip", playerips.split(",")[0]);
				for(MessageStatus ms:mslist){
				   if(ms.getMessagetype().equals(messagetype)){
					 ms.setMessageflag("2");
					 baseService.save(ms, MessageStatus.class, "id");
				   }
				}
			}//删除操作
			else if(messageflag.equals("3")){
				List<MessageStatus> msdellist=baseService.findObjectByPar(MessageStatus.class, "id", Long.parseLong(id));
				MessageStatus msdel=msdellist.get(0);
				baseService.remove(msdel);
				List<MessageStatus> mslist=baseService.findObjectByPar(MessageStatus.class, "playerip", playerips.split(",")[0]);
				for(MessageStatus ms:mslist){
				  if(ms.getMessagetype().equals(messagetype)){
					 ms.setMessageflag("3");
					 baseService.save(ms, MessageStatus.class, "id");
				  }
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			json="false";
		}
		
		try {
        	response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			out.print(json);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
	} 
	public ActionForward removeAllMessageStatus(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response){
		String createdate=request.getParameter("createdate");
		String messagestatus=request.getParameter("messagestatus");
		
		String isplan=request.getParameter("isplan");
		String messagelevel=request.getParameter("messagelevel");
		String messagetype=request.getParameter("messagetype");
		
		String json="true";
		SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");
		BaseService baseService = (BaseService) ContextHolder.getBean("baseService");
		String sql="select * from pis_message_status ";
		if(createdate!=null&&createdate.length()>0&&messagestatus!=null&&messagestatus.length()>0&&isplan!=null&&isplan.length()>0&&messagelevel!=null&&messagelevel.length()>0&&messagetype!=null&&messagetype.length()>0){
			sql+="where TO_DATE(to_char(createdate,'yyyy-mm-dd'),'yyyy-mm-dd')=TO_DATE('"+createdate+"','yyyy-mm-dd')";
			sql+=" and messagestatus='"+messagestatus+"'";
			sql+=" and isplan="+isplan+"";
			sql+=" and messagelevel='"+messagelevel+"'";
			sql+=" and messagetype='"+messagetype+"'";
			try {
				List<Map> list=selectDataService.getData(sql);
				Set setip=new HashSet();
				for(Map map:list){
					List<MessageStatus> msdellist=baseService.findObjectByPar(MessageStatus.class, "id", Long.parseLong(map.get("id").toString()));
					MessageStatus msdel=msdellist.get(0);
					baseService.remove(msdel);
					setip.add(map.get("playerip").toString());
				}
				Iterator it=setip.iterator();
				while(it.hasNext()){
					Object ob=it.next();
					List<MessageStatus> mslist=baseService.findObjectByPar(MessageStatus.class, "playerip",ob.toString());
					for(MessageStatus ms:mslist){
						 ms.setMessageflag("3");
						 baseService.save(ms, MessageStatus.class, "id");
					}
				}
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json="false";
			}
		}else{
			json="false";
		}
		try {
        	response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			out.print(json);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ActionForward getMessageStatusList(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");
		String messagestatus=request.getParameter("messagestatus");
		String messageflag=request.getParameter("messageflag");
		String messageType=request.getParameter("messagetype");
		String createdate=request.getParameter("createdate");
		String isplan=request.getParameter("isplan");
		String planname=request.getParameter("planname");
		
		String[] messageflags=null;
		if(messageflag!=null&&messageflag.length()>0){
			messageflags=messageflag.split(",");
		}
		String[] messagestatuss=null;
		if(messagestatus!=null&&messagestatus.length()>0){
			messagestatuss=messagestatus.split(",");
		}
		String json="";
		Map rolesmap=(Map) request.getSession().getAttribute("rolesmap");
		String islowlevel=rolesmap.get("islowlevel")+"";
		if(islowlevel.equals("0")){
			String sql="select * from pis_message_status where 1=1 ";
			if(messageType!=null&&messageType.length()>0){
				sql+=" and messagetype='"+messageType+"'";
			}
			if(createdate!=null&&createdate.length()>0){
				sql+=" and TO_DATE(to_char(createdate,'yyyy-mm-dd'),'yyyy-mm-dd')=TO_DATE('"+createdate+"','yyyy-mm-dd')";
			}
//			if(messagestatus!=null&&messagestatus.length()>0){
//				sql+=" and messagestatus='"+messagestatus+"'";
//			}
			if(messagestatuss!=null&&messagestatuss.length>=1){
				sql+=" and ( messagestatus='"+messagestatuss[0]+"' ";
				for(int i=1;i<messagestatuss.length;i++){
					sql+=" or messagestatus='"+messagestatuss[i]+"' ";
				}
				sql+=" ) ";
			}
			if(messageflags!=null&&messageflags.length>=1){
				sql+=" and ( messageflag='"+messageflags[0]+"' ";
				for(int i=1;i<messageflags.length;i++){
					sql+=" or messageflag='"+messageflags[i]+"' ";
				}
				sql+=" ) ";
			}
			if(isplan!=null&&isplan.length()>0){
				sql+=" and isplan="+isplan+" ";
			}
			if(planname!=null&&planname.length()>0){
				planname=java.net.URLDecoder.decode(planname,"UTF-8");
				sql+=" and planname='"+planname+"' ";
			}
			sql+=" order by starttime desc,createdate desc,id desc";
			List<Map> list=selectDataService.getData(sql);
			json+="{totalProperty:"+list.size()+",root:[";
			for(Map map:list){
				String messagetype="";
				if(map.get("messagetype")!=null&&map.get("messagetype").toString().equals(1)){
					messagetype="紧急";
				}else{
					messagetype="普通";
				}
				//json+="{num:'"+map.get("messagename")+"',type:'"+messagetype+"',message:'"+map.get("descrption")+"',operator:'"+map.get("username")+"'},";
				json+="{id:'"+map.get("id")
				+"',messagetype:'"+messagetype
				+"',descrption:'"+map.get("descrption")
				+"',username:'"+map.get("username")
				+"',playerip:'"+map.get("playerip")
				+"',messagename:'"+map.get("messagename")
				+"',createdate:'"+map.get("createdate")
				+"',starttime:'"+map.get("starttime")
				+"',endtime:'"+map.get("endtime")
				+"',messagestatus:'"+map.get("messagestatus")
				+"',messageflag:'"+map.get("messageflag")
				+"',messagelevel:'"+map.get("messagelevel")
				+"',isplan:'"+map.get("isplan")
				+"',planname:'"+map.get("planname")
				+"'},";
			}
			if(list!=null&&list.size()>0){
				json=json.substring(0,json.length()-1);
			}
			json+="]}";
		}else{
			json+="{totalProperty:"+0+",root:[";
			json+="]}";
		}
		
		json=json.replace("\n", " ").replace("\r", "");
		try {
        	response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			out.print(json);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
	} 
	public ActionForward getMessageStatusListForPage(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");
		String messagestatus=request.getParameter("messagestatus");
		String messageflag=request.getParameter("messageflag");
		String messageType=request.getParameter("messagetype");
		String createdate=request.getParameter("createdate");
		String isplan=request.getParameter("isplan");
		String planname=request.getParameter("planname");
		String messagelevel=request.getParameter("messagelevel");

		String[] messageflags=null;
		if(messageflag!=null&&messageflag.length()>0){
			messageflags=messageflag.split(",");
		}
		String[] messagestatuss=null;
		if(messagestatus!=null&&messagestatus.length()>0){
			messagestatuss=messagestatus.split(",");
		}
		String json="";
		Map rolesmap=(Map) request.getSession().getAttribute("rolesmap");
		String islowlevel=rolesmap.get("islowlevel")+"";
		if(islowlevel.equals("0")){
			String sql="select * from pis_message_status where 1=1 ";
			if(messageType!=null&&messageType.length()>0){
				sql+=" and messagetype='"+messageType+"'";
			}
			if(messagelevel!=null&&messagelevel.length()>0){
				sql+=" and messagelevel='"+messagelevel+"'";
			}
			if(createdate!=null&&createdate.length()>0){
				sql+=" and TO_DATE(to_char(createdate,'yyyy-mm-dd'),'yyyy-mm-dd')=TO_DATE('"+createdate+"','yyyy-mm-dd')";
			}
//			if(messagestatus!=null&&messagestatus.length()>0){
//				sql+=" and messagestatus='"+messagestatus+"'";
//			}
			if(messagestatuss!=null&&messagestatuss.length>=1){
				sql+=" and ( messagestatus='"+messagestatuss[0]+"' ";
				for(int i=1;i<messagestatuss.length;i++){
					sql+=" or messagestatus='"+messagestatuss[i]+"' ";
				}
				sql+=" ) ";
			}
			if(messageflags!=null&&messageflags.length>=1){
				sql+=" and ( messageflag='"+messageflags[0]+"' ";
				for(int i=1;i<messageflags.length;i++){
					sql+=" or messageflag='"+messageflags[i]+"' ";
				}
				sql+=" ) ";
			}
			if(isplan!=null&&isplan.length()>0){
				sql+=" and isplan="+isplan+" ";
			}
			if(planname!=null&&planname.length()>0){
				planname=java.net.URLDecoder.decode(planname,"UTF-8");
				sql+=" and planname='"+planname+"' ";
			}
			sql+=" order by starttime desc,createdate desc,id desc";
			//List<Map> list=selectDataService.getData(sql);
			Map mappage=selectDataService.getListForPage(request, sql);
			List<Map> list=(List) mappage.get("resoultList");
			Page page=(Page) mappage.get("p");
			json+="{totalProperty:"+page.getTotal()+",root:[";
			for(Map map:list){
				String messagetype="";
				if(map.get("messagetype")!=null&&map.get("messagetype").toString().equals(1)){
					messagetype="紧急";
				}else{
					messagetype="普通";
				}
				//json+="{num:'"+map.get("messagename")+"',type:'"+messagetype+"',message:'"+map.get("descrption")+"',operator:'"+map.get("username")+"'},";
				json+="{id:'"+map.get("id")
				+"',messagetype:'"+messagetype
				+"',descrption:'"+map.get("descrption")
				+"',username:'"+map.get("username")
				+"',playerip:'"+map.get("playerip")
				+"',messagename:'"+map.get("messagename")
				+"',createdate:'"+map.get("createdate")
				+"',starttime:'"+map.get("starttime")
				+"',endtime:'"+map.get("endtime")
				+"',messagestatus:'"+map.get("messagestatus")
				+"',messageflag:'"+map.get("messageflag")
				+"',messagelevel:'"+map.get("messagelevel")
				+"',isplan:'"+map.get("isplan")
				+"',planname:'"+map.get("planname")
				+"'},";
			}
			if(list!=null&&list.size()>0){
				json=json.substring(0,json.length()-1);
			}
			json+="]}";
		}else{
			json+="{totalProperty:"+0+",root:[";
			json+="]}";
		}
		
		json=json.replace("\n", " ").replace("\r", "");
		try {
        	response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			out.print(json);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
	} 
}