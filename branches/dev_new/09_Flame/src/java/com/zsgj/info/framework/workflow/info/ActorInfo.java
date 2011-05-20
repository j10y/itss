package com.zsgj.info.framework.workflow.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zsgj.info.framework.workflow.entity.TaskPreAssign;
/**
 * 考虑到单节点多人和单人多代理情况，修改此结构，考虑到将来可能会有不同代理不同时段的情况
 * @Class Name ActorInfo
 * @Author yang
 * @Create In Nov 20, 2008
 */
@SuppressWarnings("unchecked")
public class ActorInfo{
	
//	static private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private String actorId = null;
	private List<ProxyInfo> proxies = new ArrayList();//目前为单一数据行
	
	//判断是否存在代理现象，若存在适用代理则使用,同时带入默认审批人。格式是a|b|c...
	public String getRightActorId() {	
		String rightActorId = actorId;
		for(ProxyInfo proxy: proxies) {
			String proxyId = proxy.getRightActorId();
			if(proxyId!=null) {
				rightActorId += "|"+proxyId;
			}
		}
		return rightActorId;
	}
	
	/**
	 * 转化成list形式
	 * @Methods Name toList
	 * @Create In Nov 20, 2008 By yang
	 * @return 
	 * @ReturnType List
	 */
	public List toList() {
		List<ActorInfo> actorList = new ArrayList<ActorInfo>();
		actorList.add(this);
		return actorList;
	}
	
	/**
	 * 从json数据构造审批人信息{actorId:'',proxies:[{proxyId:'',proxyBegin:'',proxyEnd:''},{proxyId'',proxyBegin:'',proxyEnd:''}]}
	 * 可能没有代理信息，以解决存储空间不够的问题
	 * @Methods Name fromJSON
	 * @Create In Nov 20, 2008 By yang
	 * @param json
	 * @return 
	 * @ReturnType ActorInfo
	 */
	private  static ActorInfo fromJSON(String json) {
		JSONObject jo = JSONObject.fromObject(json);
		ActorInfo actorInfo = new ActorInfo((String)jo.get("actorId"));
		if(jo.has("proxies")) {//可能没有代理信息，以解决存储空间不够的问题
			JSONArray ja = jo.getJSONArray("proxies");
			if(ja.size()>0) {
	//			actorInfo.proxies = new ArrayList();
				for(int i=0;i<ja.size();i++) {
					ProxyInfo proxy = new ProxyInfo();
					JSONObject jop = ja.getJSONObject(i);
					proxy.setProxyId(jop.getString("proxyId"));
					proxy.setProxyBegin(jop.getString("proxyBegin"));
					proxy.setProxyEnd(jop.getString("proxyEnd"));
					actorInfo.proxies.add(proxy);
				}
			}	
		}
		return actorInfo;
	}
	
	/**
	 * 预指派生成ActorInfo
	 * @Methods Name fromTaskPreAssign
	 * @Create In Jan 6, 2009 By yang
	 * @param tpa
	 * @return 
	 * @ReturnType ActorInfo
	 */
	public static ActorInfo fromTaskPreAssign(TaskPreAssign tpa) {
		ActorInfo actorInfo = new ActorInfo(tpa.getActorId());
		ProxyInfo proxy = new ProxyInfo();
		proxy.setProxyId(tpa.getProxyId());
		proxy.setProxyBegin(tpa.getProxyBegin());
		proxy.setProxyEnd(tpa.getProxyEnd());
		actorInfo.proxies.add(proxy);
		return actorInfo;
	}
	/**
	 * 获得actorInfo数组
	 * @Methods Name getArrayFromJSON
	 * @Create In Nov 20, 2008 By yang
	 * @param json
	 * @return 
	 * @ReturnType ActorInfo[]
	 */
	public static ActorInfo[] getArrayFromJSON(String json) {
		ActorInfo[] actorInfos = null;
		JSONArray ja = JSONArray.fromObject(json);
		if(ja.size()>0) {
			actorInfos = new ActorInfo[ja.size()];
			for(int i=0;i<ja.size();i++) {
				actorInfos[i] = fromJSON(ja.getString(i));
			}
		}		
		return actorInfos;
	}
	

	/**
	 * 根据预指派行数据，整理出可以存入工作流上下文的数据结构
	 * @Methods Name buildPreAssignMap
	 * @Create In Nov 20, 2008 By yang
	 * @param preAssigns
	 * @return 
	 * @ReturnType Map<String,ActorInfo[]>
	 */
	@SuppressWarnings("deprecation")
	public static Map<String,List<ActorInfo>> buildPreAssignMap(List<TaskPreAssign> preAssigns) {
		Map<String,Map<String,ActorInfo>> mapPreAssign = new HashMap<String,Map<String,ActorInfo>>();
		for(TaskPreAssign preAssign: preAssigns) {
			String taskName = preAssign.getTaskName();
			if(taskName==null||taskName.trim().equals("")) {
				continue;
			}
			taskName = taskName.trim();
			Map<String,ActorInfo> actorInfos = mapPreAssign.get(taskName);
			//没有节点则创建
			if(actorInfos==null) {
				actorInfos = new HashMap<String,ActorInfo>();	
			}
			//没有相应审批人则创建
			String actorId = preAssign.getActorId();
			ActorInfo actorInfo = actorInfos.get(actorId);
			if(actorInfo==null) {
				actorInfo = new ActorInfo(actorId);
			}
			//加入代理信息
			ProxyInfo proxyInfo = new ProxyInfo();
			proxyInfo.setProxyBegin(preAssign.getProxyBegin()==null?"":preAssign.getProxyBegin().toGMTString());
			proxyInfo.setProxyEnd(preAssign.getProxyEnd()==null?"":preAssign.getProxyEnd().toGMTString());
			proxyInfo.setProxyId(preAssign.getProxyId());
			actorInfo.getProxies().add(proxyInfo);
			actorInfos.put(actorId,actorInfo);	
			mapPreAssign.put(taskName, actorInfos);
		}
		Map<String,List<ActorInfo>> mapPreAssignReturn = new HashMap<String,List<ActorInfo>>();
		Set<String> taskKeySet = mapPreAssign.keySet();
		for(String taskKey: taskKeySet) {
			Map<String,ActorInfo> actors = mapPreAssign.get(taskKey);
			Set<String> actorIdSet = actors.keySet();
			List<ActorInfo> actorArray = new ArrayList<ActorInfo>();
			for(String actorId: actorIdSet) {
				actorArray.add(actors.get(actorId));
			}
			mapPreAssignReturn.put(taskKey, actorArray);
		}
		return mapPreAssignReturn;
	}
	
	public ActorInfo(String actorId) {
		this.actorId = actorId;
	}
	
	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public List<ProxyInfo> getProxies() {
		return proxies;
	}

	public void setProxies(List<ProxyInfo> proxies) {
		this.proxies = proxies;
	}
}
