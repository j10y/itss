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
			ids=list.get(0).get("id").toString();
		}
		return  ids;
	}	
	public String saveArchUnderTakesByArchId(String id,String userids){
		String ids=this.findArchUnderTakesByArchId(id);
		ArchUnderTakes aut=new ArchUnderTakes();
		Set users=new HashSet();
		if(ids.length()>0){
			aut=this.get(Long.parseLong(ids));
			if(aut.getUserIds()!=null&&aut.getUserIds().length()>0){
				String[] s=aut.getUserIds().split(",");
				for(int i=0;i<s.length;i++){
					users.add(s[i]);
				}
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
		if(newids.lastIndexOf(",")==newids.length()-1){
			newids=	newids.substring(0,newids.length()-1);
		}
		//ArchUnderTakes aut=new ArchUnderTakes();
		aut.setArchivesId(Long.parseLong(id));
		aut.setUserIds(newids);
		this.dao.save(aut);
		return newids;
	}

	public String findArchUnderTakesUpSignByArchId(String id) {
		// TODO Auto-generated method stub
		String sql="select * from arch_rec_undertakes where archivesid="+id;
		List<Map> list=this.findDataList(sql);
		String ids="";
		if(list!=null&&list.size()>0){
			ids=list.get(0).get("upSignUserIds").toString();
		}
		return  ids;
	}

	public String saveArchUnderTakesByArchIdAndSign(String id,
			String upsignUserIds) {
		// TODO Auto-generated method stub
		String ids=this.findArchUnderTakesByArchId(id);
		ArchUnderTakes aut=new ArchUnderTakes();
		if(ids==null||ids.length()==0){
			aut.setArchivesId(Long.parseLong(id));
			aut.setUpSignUserIds(upsignUserIds);
			this.save(aut);
		}
		return null;
	}
}
