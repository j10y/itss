package com.digitalchina.itil.event.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.itil.event.dao.CCTelSynDao;
import com.digitalchina.itil.event.entity.CCCallInfo;
import com.digitalchina.itil.event.entity.CCTblIVRSatisfy;
import com.digitalchina.itil.event.service.DataBaseConnection;

public class CCTelSynDaoImpl extends BaseDao implements CCTelSynDao {
	private  Connection conn=null;
	private  PreparedStatement stmt =null;
	private  ResultSet rs =null;
	
	public void insertCCTel2Native(String dateString) {
		//Date currentDate = DateUtil.getCurrentDate();
		String currentDateString = dateString; //DateUtil.convertDateTimeToString(currentDate);
		
		conn    = DataBaseConnection.getOracleConnection();//得到Oracle连接
		
//remove by lee for 暂时不再同步话务数据 in 20090814 begin			
//		//1.begin_同步LOG1_AGENTSKILLGCHANGE表
//		String SQLforOracle =" select AGENTID, DN, LOGINTIME, SKILLG, MILLISEC, LOGTAG "+
//							 " from LOG1_AGENTSKILLGCHANGE ";
//		if(currentDateString!=null){
//			SQLforOracle+=" where TO_CHAR(LOGINTIME,'yyyy-mm-dd HH')='" + currentDateString+"'";
//		}
//		
//		if(conn!=null){
//			try {
//			stmt = conn.prepareStatement(SQLforOracle);
//			rs =  stmt.executeQuery();//得到结果集
//		
//			while(rs.next()){
//				//从CC的oracle数据库取数据包装到实体
//				CCAgentSkillgChange casc = new CCAgentSkillgChange();
//				casc.setAgentId(rs.getString("AGENTID"));
//				casc.setDn(rs.getString("DN"));
//				casc.setLoginTime(rs.getDate("LOGINTIME"));
//				casc.setLogtag(rs.getString("LOGTAG"));
//				casc.setMillisec(rs.getString("MILLISEC"));
//				casc.setSkilllg(rs.getString("SKILLG"));
//				
//				//通过hibernate保存到本地SQLServer
//				super.save(casc);
//				
//			}
//			System.out.println(SQLforOracle+"同步LOG1_AGENTSKILLGCHANGE完成 ");
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//		//end_同步LOG1_AGENTSKILLGCHANGE表
//		
//		//2 begin_同步LOG1_AGENTSTATUSCHANGE表
//		SQLforOracle =" select AGENTID, OPERTYPE, STARTTIME, ENDTIME, TIMESPAN, MILLISEC, LOGTAG "+
//		 " from LOG1_AGENTSTATUSCHANGE ";
//		if(currentDateString!=null){
//			SQLforOracle+=" where TO_CHAR(STARTTIME,'yyyy-mm-dd HH')='" + currentDateString+"'";
//		}
//
//		try {
//			stmt = conn.prepareStatement(SQLforOracle);
//			rs =  stmt.executeQuery();//得到结果集
//		
//			while(rs.next()){
//				//从CC的oracle数据库取数据包装到实体
//				CCAgentStatusChange casc = new CCAgentStatusChange();
//				casc.setAgentId(rs.getString("AGENTID"));
//				casc.setOpertype(rs.getString("OPERTYPE"));
//				casc.setStartTime(rs.getDate("STARTTIME"));
//				casc.setEndTime(rs.getDate("ENDTIME"));
//				casc.setTimespan(rs.getInt("TIMESPAN"));
//				casc.setMillisec(rs.getString("MILLISEC"));
//				casc.setLogtag(rs.getString("LOGTAG"));
//				
//				//通过hibernate保存到本地SQLServer
//				super.save(casc);
//		
//			}
//			System.out.println("同步LOG1_AGENTSTATUSCHANGE完成 ");
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//		//end_同步LOG1_AGENTSTATUSCHANGE表
//		
//		//3 begin_同步LOG1_AGENTWORKSUM
//		SQLforOracle =" select AGENTID,SKILLG,GLEVEL,ACTIONTYPE,ORIGID,CALLERID," +
//					  " CALLEDID,RINGTIME,CONNECTTIME,ENDTIME,MILLISEC,CALLID "+
//					  " from LOG1_AGENTWORKSUM ";
//		if(currentDateString!=null){
//			SQLforOracle+=" where TO_CHAR(RINGTIME,'yyyy-mm-dd HH')='" + currentDateString+"'";
//		}//这里使用RINGTIME做同步不知对不对
//
//		try {
//			stmt = conn.prepareStatement(SQLforOracle);
//			rs =  stmt.executeQuery();//得到结果集
//		
//			while(rs.next()){
//				//从CC的oracle数据库取数据包装到实体
//				CCAgentWorkSum casc = new CCAgentWorkSum();
//				casc.setAgentId(rs.getString("AGENTID"));
//				casc.setSkillg(rs.getString("SKILLG"));
//				casc.setGlevel(rs.getInt("GLEVEL"));
//				casc.setActionType(rs.getString("ACTIONTYPE"));
//				casc.setOrigid(rs.getString("ORIGID"));
//				casc.setCalledrId(rs.getString("CALLERID"));
//				casc.setCalleddid(rs.getString("CALLEDID"));
//				casc.setRingTime(rs.getDate("RINGTIME"));
//				casc.setConnectTime(rs.getDate("CONNECTTIME"));
//				casc.setEndTime(rs.getDate("ENDTIME"));
//				casc.setMillisec(rs.getString("MILLISEC"));
//				casc.setCallId(rs.getString("CALLID"));
//				
//				//通过hibernate保存到本地SQLServer
//				super.save(casc);
//		
//			}
//			System.out.println("同步LOG1_AGENTWORKSUM完成 ");
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//		//end_同步LOG1_AGENTWORKSUM
//		
//		//4 begin_同步LOG1_SKILLGWORKSUM
//		SQLforOracle =" select SKILLG,GLEVEL,DN,RESULT,ROUTEVALUE,ORIGID,ENTERTIME,ASSIGNTIME,LEAVETIME,TAG,MILLISEC,CALLID" +
//		  			  " from LOG1_SKILLGWORKSUM ";
//		if(currentDateString!=null){
//			SQLforOracle+=" where TO_CHAR(ENTERTIME,'yyyy-mm-dd HH')='" + currentDateString+"'";
//		}//这里使用ENTERTIME做同步不知对不对，日后和CC工程师确认
//
//		try {
//			stmt = conn.prepareStatement(SQLforOracle);
//			rs =  stmt.executeQuery();//得到结果集
//			
//			while(rs.next()){
//				//从CC的oracle数据库取数据包装到实体
//				CCSkillGWorkSum casc = new CCSkillGWorkSum();
//
//				casc.setSkillg(rs.getString("SKILLG"));
//				casc.setGlevel(rs.getInt("GLEVEL"));
//				casc.setDn(rs.getString("DN"));
//				casc.setResult(rs.getString("RESULT"));
//				casc.setRouteValue(rs.getString("ROUTEVALUE"));
//				casc.setOrigid(rs.getString("ORIGID"));
//				casc.setEnterTime(rs.getDate("ENTERTIME"));
//				casc.setAssignTime(rs.getDate("ASSIGNTIME"));
//				casc.setLeaveTime(rs.getDate("LEAVETIME"));
//				casc.setTag(rs.getInt("TAG"));
//				casc.setMillisec(rs.getString("MILLISEC"));
//				casc.setCallid(rs.getString("CALLID"));
//				
//				//通过hibernate保存到本地SQLServer
//				super.save(casc);
//			
//			}
//			System.out.println("同步LOG1_SKILLGWORKSUM完成 ");
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		//end_同步LOG1_SKILLGWORKSUM
//		
//		//5 begin_同步LOG1_IVRWORKSUM
//		SQLforOracle =" select DN,ACTIONIO,ORIGID,CALLERID,CALLEDID,STARTTIME,ENDTIME,TIMESPAN,TAG,MILLISEC,CALLID" +
//		  " from LOG1_IVRWORKSUM ";
//		if(currentDateString!=null){
//		SQLforOracle+=" where TO_CHAR(STARTTIME,'yyyy-mm-dd HH') like '" + currentDateString+"%'";
//		System.out.print(SQLforOracle);
//		}//这里使用ENTERTIME做同步不知对不对，日后和CC工程师确认
//		
//		try {
//			stmt = conn.prepareStatement(SQLforOracle);
//			rs =  stmt.executeQuery();//得到结果集
//			
//			while(rs.next()){
//				//从CC的oracle数据库取数据包装到实体
//				CCIVRWorkSum casc = new CCIVRWorkSum();
//			
//				casc.setDn(rs.getString("DN"));
//				casc.setActionIO(rs.getString("ACTIONIO"));
//				casc.setOrigId(rs.getString("ORIGID"));
//				casc.setCalledrId(rs.getString("CALLERID"));
//				casc.setCalleddId(rs.getString("CALLEDID"));
//				casc.setStartTime(rs.getDate("STARTTIME"));
//				casc.setEndTime(rs.getDate("ENDTIME"));
//				casc.setTimeSpan(rs.getInt("TIMESPAN"));
//				casc.setTag(rs.getInt("TAG"));
//				casc.setMillisec(rs.getString("MILLISEC"));
//				casc.setCallId(rs.getString("CALLID"));
//				
//				//通过hibernate保存到本地SQLServer
//				super.save(casc);
//			
//			}
//			System.out.println("同步LOG1_IVRWORKSUM完成 ");
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//		//end__同步LOG1_IVRWORKSUM
//remove by lee for 暂时不再同步话务数据 in 20090814 end
			
		//6 begin_同步TBL_IVR_SATISFY
		String SQLforOracle =" select SERVICE_ID, HANDLE, AGENTCODE, AGENTDEVICE, CODE, ANI, TIME "+
							 " from TBL_IVR_SATISFY ";
		if(currentDateString!=null){
			SQLforOracle+="where SUBSTR(TIME,0, 13)='" + currentDateString+"'";
		}
		try {
			stmt = conn.prepareStatement(SQLforOracle);
			rs =  stmt.executeQuery();//得到结果集x`
		
			while(rs.next()){
				//从CC的oracle数据库取数据包装到实体
				CCTblIVRSatisfy casc = new CCTblIVRSatisfy();
				casc.setServiceId(rs.getString("SERVICE_ID"));
				casc.setHandle(rs.getString("HANDLE"));
				casc.setAgentcode(rs.getString("AGENTCODE"));
				casc.setAgentDevice(rs.getString("AGENTDEVICE"));
				casc.setCode(rs.getString("CODE"));
				casc.setAni(rs.getString("ANI"));
				casc.setTime(rs.getString("TIME"));
				
				//2010-09-07 add by huzh for 电话反馈之后又发邮件反馈的问题（同步话务满意度数据的同时修改CCCallInfo表中对应数据的mailFlag标记） begin
				if(rs.getString("CODE")!=null&&!"".equals(rs.getString("CODE"))){
					List<CCCallInfo> callList=super.findBy(CCCallInfo.class, "callId", rs.getString("HANDLE"));
					if(callList!=null&&callList.size()!=0){
						for(int i=0;i<callList.size();i++){
							CCCallInfo ccCallInfo=callList.get(i);
							ccCallInfo.setSatisSynFlag(CCCallInfo.TELFLAG_YES);
							super.save(ccCallInfo);
						}
					}
				}
				//2010-09-07 add by huzh for 电话反馈之后又发邮件反馈的问题（同步话务满意度数据的同时修改CCCallInfo表中对应数据的mailFlag标记） end
				//通过hibernate保存到本地SQLServer
				super.save(casc);
				
			}
			System.out.println("同步TBL_IVR_SATISFY完成 ");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		//end_同步TBL_IVR_SATISFY
		
		
		DataBaseConnection.closeConnection(rs, stmt, conn);
		
//		}
		
	}

}
