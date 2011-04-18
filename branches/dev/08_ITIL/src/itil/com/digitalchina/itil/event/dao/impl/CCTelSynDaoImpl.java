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
		
		conn    = DataBaseConnection.getOracleConnection();//�õ�Oracle����
		
//remove by lee for ��ʱ����ͬ���������� in 20090814 begin			
//		//1.begin_ͬ��LOG1_AGENTSKILLGCHANGE��
//		String SQLforOracle =" select AGENTID, DN, LOGINTIME, SKILLG, MILLISEC, LOGTAG "+
//							 " from LOG1_AGENTSKILLGCHANGE ";
//		if(currentDateString!=null){
//			SQLforOracle+=" where TO_CHAR(LOGINTIME,'yyyy-mm-dd HH')='" + currentDateString+"'";
//		}
//		
//		if(conn!=null){
//			try {
//			stmt = conn.prepareStatement(SQLforOracle);
//			rs =  stmt.executeQuery();//�õ������
//		
//			while(rs.next()){
//				//��CC��oracle���ݿ�ȡ���ݰ�װ��ʵ��
//				CCAgentSkillgChange casc = new CCAgentSkillgChange();
//				casc.setAgentId(rs.getString("AGENTID"));
//				casc.setDn(rs.getString("DN"));
//				casc.setLoginTime(rs.getDate("LOGINTIME"));
//				casc.setLogtag(rs.getString("LOGTAG"));
//				casc.setMillisec(rs.getString("MILLISEC"));
//				casc.setSkilllg(rs.getString("SKILLG"));
//				
//				//ͨ��hibernate���浽����SQLServer
//				super.save(casc);
//				
//			}
//			System.out.println(SQLforOracle+"ͬ��LOG1_AGENTSKILLGCHANGE��� ");
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//		//end_ͬ��LOG1_AGENTSKILLGCHANGE��
//		
//		//2 begin_ͬ��LOG1_AGENTSTATUSCHANGE��
//		SQLforOracle =" select AGENTID, OPERTYPE, STARTTIME, ENDTIME, TIMESPAN, MILLISEC, LOGTAG "+
//		 " from LOG1_AGENTSTATUSCHANGE ";
//		if(currentDateString!=null){
//			SQLforOracle+=" where TO_CHAR(STARTTIME,'yyyy-mm-dd HH')='" + currentDateString+"'";
//		}
//
//		try {
//			stmt = conn.prepareStatement(SQLforOracle);
//			rs =  stmt.executeQuery();//�õ������
//		
//			while(rs.next()){
//				//��CC��oracle���ݿ�ȡ���ݰ�װ��ʵ��
//				CCAgentStatusChange casc = new CCAgentStatusChange();
//				casc.setAgentId(rs.getString("AGENTID"));
//				casc.setOpertype(rs.getString("OPERTYPE"));
//				casc.setStartTime(rs.getDate("STARTTIME"));
//				casc.setEndTime(rs.getDate("ENDTIME"));
//				casc.setTimespan(rs.getInt("TIMESPAN"));
//				casc.setMillisec(rs.getString("MILLISEC"));
//				casc.setLogtag(rs.getString("LOGTAG"));
//				
//				//ͨ��hibernate���浽����SQLServer
//				super.save(casc);
//		
//			}
//			System.out.println("ͬ��LOG1_AGENTSTATUSCHANGE��� ");
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//		//end_ͬ��LOG1_AGENTSTATUSCHANGE��
//		
//		//3 begin_ͬ��LOG1_AGENTWORKSUM
//		SQLforOracle =" select AGENTID,SKILLG,GLEVEL,ACTIONTYPE,ORIGID,CALLERID," +
//					  " CALLEDID,RINGTIME,CONNECTTIME,ENDTIME,MILLISEC,CALLID "+
//					  " from LOG1_AGENTWORKSUM ";
//		if(currentDateString!=null){
//			SQLforOracle+=" where TO_CHAR(RINGTIME,'yyyy-mm-dd HH')='" + currentDateString+"'";
//		}//����ʹ��RINGTIME��ͬ����֪�Բ���
//
//		try {
//			stmt = conn.prepareStatement(SQLforOracle);
//			rs =  stmt.executeQuery();//�õ������
//		
//			while(rs.next()){
//				//��CC��oracle���ݿ�ȡ���ݰ�װ��ʵ��
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
//				//ͨ��hibernate���浽����SQLServer
//				super.save(casc);
//		
//			}
//			System.out.println("ͬ��LOG1_AGENTWORKSUM��� ");
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//		//end_ͬ��LOG1_AGENTWORKSUM
//		
//		//4 begin_ͬ��LOG1_SKILLGWORKSUM
//		SQLforOracle =" select SKILLG,GLEVEL,DN,RESULT,ROUTEVALUE,ORIGID,ENTERTIME,ASSIGNTIME,LEAVETIME,TAG,MILLISEC,CALLID" +
//		  			  " from LOG1_SKILLGWORKSUM ";
//		if(currentDateString!=null){
//			SQLforOracle+=" where TO_CHAR(ENTERTIME,'yyyy-mm-dd HH')='" + currentDateString+"'";
//		}//����ʹ��ENTERTIME��ͬ����֪�Բ��ԣ��պ��CC����ʦȷ��
//
//		try {
//			stmt = conn.prepareStatement(SQLforOracle);
//			rs =  stmt.executeQuery();//�õ������
//			
//			while(rs.next()){
//				//��CC��oracle���ݿ�ȡ���ݰ�װ��ʵ��
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
//				//ͨ��hibernate���浽����SQLServer
//				super.save(casc);
//			
//			}
//			System.out.println("ͬ��LOG1_SKILLGWORKSUM��� ");
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		//end_ͬ��LOG1_SKILLGWORKSUM
//		
//		//5 begin_ͬ��LOG1_IVRWORKSUM
//		SQLforOracle =" select DN,ACTIONIO,ORIGID,CALLERID,CALLEDID,STARTTIME,ENDTIME,TIMESPAN,TAG,MILLISEC,CALLID" +
//		  " from LOG1_IVRWORKSUM ";
//		if(currentDateString!=null){
//		SQLforOracle+=" where TO_CHAR(STARTTIME,'yyyy-mm-dd HH') like '" + currentDateString+"%'";
//		System.out.print(SQLforOracle);
//		}//����ʹ��ENTERTIME��ͬ����֪�Բ��ԣ��պ��CC����ʦȷ��
//		
//		try {
//			stmt = conn.prepareStatement(SQLforOracle);
//			rs =  stmt.executeQuery();//�õ������
//			
//			while(rs.next()){
//				//��CC��oracle���ݿ�ȡ���ݰ�װ��ʵ��
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
//				//ͨ��hibernate���浽����SQLServer
//				super.save(casc);
//			
//			}
//			System.out.println("ͬ��LOG1_IVRWORKSUM��� ");
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//		//end__ͬ��LOG1_IVRWORKSUM
//remove by lee for ��ʱ����ͬ���������� in 20090814 end
			
		//6 begin_ͬ��TBL_IVR_SATISFY
		String SQLforOracle =" select SERVICE_ID, HANDLE, AGENTCODE, AGENTDEVICE, CODE, ANI, TIME "+
							 " from TBL_IVR_SATISFY ";
		if(currentDateString!=null){
			SQLforOracle+="where SUBSTR(TIME,0, 13)='" + currentDateString+"'";
		}
		try {
			stmt = conn.prepareStatement(SQLforOracle);
			rs =  stmt.executeQuery();//�õ������x`
		
			while(rs.next()){
				//��CC��oracle���ݿ�ȡ���ݰ�װ��ʵ��
				CCTblIVRSatisfy casc = new CCTblIVRSatisfy();
				casc.setServiceId(rs.getString("SERVICE_ID"));
				casc.setHandle(rs.getString("HANDLE"));
				casc.setAgentcode(rs.getString("AGENTCODE"));
				casc.setAgentDevice(rs.getString("AGENTDEVICE"));
				casc.setCode(rs.getString("CODE"));
				casc.setAni(rs.getString("ANI"));
				casc.setTime(rs.getString("TIME"));
				
				//2010-09-07 add by huzh for �绰����֮���ַ��ʼ����������⣨ͬ��������������ݵ�ͬʱ�޸�CCCallInfo���ж�Ӧ���ݵ�mailFlag��ǣ� begin
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
				//2010-09-07 add by huzh for �绰����֮���ַ��ʼ����������⣨ͬ��������������ݵ�ͬʱ�޸�CCCallInfo���ж�Ӧ���ݵ�mailFlag��ǣ� end
				//ͨ��hibernate���浽����SQLServer
				super.save(casc);
				
			}
			System.out.println("ͬ��TBL_IVR_SATISFY��� ");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		//end_ͬ��TBL_IVR_SATISFY
		
		
		DataBaseConnection.closeConnection(rs, stmt, conn);
		
//		}
		
	}

}
