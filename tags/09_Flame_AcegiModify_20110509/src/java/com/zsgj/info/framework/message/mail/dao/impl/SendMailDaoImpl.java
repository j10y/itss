package com.zsgj.info.framework.message.mail.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;


import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.message.mail.dao.SendMailDao;
import com.zsgj.info.framework.security.entity.UserInfo;

public class SendMailDaoImpl extends BaseDao implements SendMailDao {

	public Map getMapFromInform(Map map) {
		// TODO Auto-generated method stub
		String toActorIds=null;
		String email=null;
		if(map.get("toActorId")!=null){
		toActorIds=(String) map.get("toActorId");
		}
		if(map.get("email")!=null){
		email=(String) map.get("email");
		}
		UserInfo user=null;
		List userList=new ArrayList();
		Map newMap=new HashMap();
		try {
			if(toActorIds!=null&&email==null){
			String []toActorIdss=toActorIds.split(","); 
			for(String toActorId:toActorIdss){
					Criteria criteria = createCriteria(UserInfo.class);
					criteria.add(Restrictions.eq("userName", toActorId));
					user=(UserInfo) criteria.uniqueResult();
					userList.add(user);
				}
			}else if(email!=null){
				user.setEmail(email);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("通过Id查审批人发生异常");
		}
		newMap.put("UserInfo", userList);
//		newMap.put("type", map.get("type"));
//		newMap.put("object",  map.get("object"));
		return newMap;
	}

}
