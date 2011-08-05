package com.xpsoft.oa.service.archive.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.archive.ArchUnderTakesDao;
import com.xpsoft.oa.model.archive.ArchUnderTakes;
import com.xpsoft.oa.service.archive.ArchUnderTakesService;

public class ArchUnderTakesServiceImpl extends BaseServiceImpl<ArchUnderTakes>
		implements ArchUnderTakesService {
	private ArchUnderTakesDao dao;

	public ArchUnderTakesServiceImpl(ArchUnderTakesDao dao) {
		super(dao);
		this.dao = dao;
	}
	
	public String findArchUnderTakesByArchId(String id){
		String sql="select * from arch_rec_undertakes where archivesid="+id;
		List<Map> list=this.findDataList(sql);
		String ids="";
		if(list!=null&&list.size()>0){
			for(Map map:list){
				ids+=map.get("userids").toString()+",";
			}
			ids=ids.substring(0,ids.length()-1);
		}
		return  ids;
	}	
	public String saveArchUnderTakesByArchId(String id,String userids){
		String ids=this.findArchUnderTakesByArchId(id);
		Set users=new HashSet();
		if(ids!=null&&ids.length()>0){
			String[] s=ids.split(",");
			for(int i=0;i<s.length;i++){
				users.add(s[i]);
			}
		}
		if(userids!=null&&userids.length()>0){
			String[] s=userids.split(",");
			for(int i=0;i<s.length;i++){
				users.add(s[i]);
			}
		}
		String newids="";
		Iterator it=users.iterator();
		if(it.hasNext()){
			newids+=it.next().toString();
			if(it.hasNext()){
				newids+=",";
			}
		}
		ArchUnderTakes aut=new ArchUnderTakes();
		aut.setArchivesId(Long.parseLong(id));
		aut.setUserIds(newids);
		this.dao.save(aut);
		return newids;
	}
}
