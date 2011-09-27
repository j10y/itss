package com.xpsoft.oa.kpi.service.Impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xp.commonpart.countjob.FormatFm;
import com.xp.commonpart.countjob.Modle;
import com.xp.commonpart.service.BaseService;
import com.xp.commonpart.service.SelectDataService;
import com.xp.commonpart.util.ContextHolder;
import com.xp.commonpart.util.DateUtil;
import com.xp.commonpart.util.PropertiesUtil;
import com.xpsoft.oa.kpi.service.CountJobService;
import com.xpsoft.oa.model.kpi.HrPaDatadictionary;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2user;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindex;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindexscore;
import com.xpsoft.oa.model.kpi.HrPaPisrule;
import com.xpsoft.oa.model.kpi.SalaryPayoff;

public class CountJobServiceImpl implements CountJobService{
	protected final transient Log logger = LogFactory.getLog(getClass());
	public boolean saveKpiItemScoreForUser(){
		return this.saveKpiItemScoreForUser(null,null,null);
	}
	public boolean saveSalarDetail(){
		return this.saveSalarDetail(null,null);
	}
	public boolean saveKpiItemScoreForUser(String userid,String depid,String pbc2userid){
		logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++操作用户ID："+userid+"用户PBC模板ID："+pbc2userid+"++++计算该用户的模板开始+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");	
		BaseService baseService =(BaseService) ContextHolder.getBean("baseService");		
		LinkedHashMap pmap=new LinkedHashMap();
		HrPaDatadictionary fre1=new HrPaDatadictionary();
		fre1.setId(Long.parseLong(PropertiesUtil.getProperties("hr.pamodle")));
		pmap.put("mode", fre1);
		pmap.put("publishStatus",Integer.parseInt(PropertiesUtil.getProperties("hr.papublishStatus")));
		//1.查出所有绩效考核指标
		List<HrPaPerformanceindex> hplist=baseService.findObjectListByParamAndOrder(HrPaPerformanceindex.class, pmap, null);
		Long starttime=new Date().getTime();
		String jobexcutedate=PropertiesUtil.getProperties("hr.jobexcutedate");
		String[] ejobexcutedate=jobexcutedate.split(",");
		Map jobexcutedatemap=new HashMap();
		for(String ej:ejobexcutedate){
			String [] ejs=ej.split("_");
			Map datemap=new HashMap();
			datemap.put("startdate", ejs[1]);
			datemap.put("excutefre", ejs[2]);
			datemap.put("offset", ejs[3]);//时间偏移量，0为计算当月，1为计算前一个月
			jobexcutedatemap.put(ejs[0], datemap);
		}
		int curmonth=new Date().getMonth()+1;
		int allcount=0;
		int succount=0;
		//2.遍历绩效考核指标
		for(HrPaPerformanceindex hp:hplist){
			//3.查询改绩效考核指标是否有人使用
			String pbcitemusersql="select hr_pa_kpiitem2user.id,app_user.depId from hr_pa_kpiitem2user,hr_pa_kpipbc2user,app_user,department " +
					"where hr_pa_kpiitem2user.pbcId=hr_pa_kpipbc2user.id and hr_pa_kpipbc2user.belongUser=app_user.userId " +
					"and app_user.depId=department.depId and hr_pa_kpipbc2user.publishStatus!=3 and hr_pa_kpiitem2user.piId="+hp.getId();
			if(depid!=null&&depid.length()>0){
				pbcitemusersql+=" and department.depId="+depid;
			}
			if(pbc2userid!=null&&pbc2userid.length()>0){
				pbcitemusersql+=" and hr_pa_kpipbc2user.id="+pbc2userid;
			}
			List pbcitemuserlist=selectDataService.getData(pbcitemusersql);
			//4.如果没有，则跳出该指标的人员模板查询，有则继续
			if(pbcitemuserlist==null||pbcitemuserlist.size()==0){
				//logger.error(hp.getPaName()+"没有人使用该绩效指标，跳出该指标的算分.");
				continue;//继续下一个绩效指标的得分
			}
			String frequency=hp.getPaFrequency()+"";//频率
			Map datemap=(Map) jobexcutedatemap.get(frequency);
			if(datemap!=null){
				int excutefre=Integer.parseInt(datemap.get("excutefre").toString());
				int startdate=Integer.parseInt(datemap.get("startdate").toString());
				int offset=Integer.parseInt(datemap.get("offset").toString());
				int curday=new Date().getDate();
				if(PropertiesUtil.getProperties("hr.isautoj").equals("true")){
					if(curday>=25){
						offset=0;
					}else if(curday<5){
						offset=1;
					}
				}
				if(curmonth%excutefre==startdate){
					LinkedHashMap hsmap=new LinkedHashMap();
					hsmap.put("piId", hp.getId());
					for(int i=0;i<pbcitemuserlist.size();i++){
						Map pbciumap=(Map) pbcitemuserlist.get(i);
						String depId=pbciumap.get("depId").toString();
						String getchildhp="select * from hr_pa_performanceindex where hr_pa_performanceindex.parentId="+hp.getId();
						List<Map> getchildhplist=selectDataService.getData(getchildhp);
						if(getchildhplist.size()>0){//说明需要从每月的考核中归集到年得考核
							//查看当月的月该年度考核相关的指标是否已审核
							String sd=DateUtil.convertDateToString(DateUtil.addMonths(new Date(), -(excutefre+offset)));
							String ed=DateUtil.convertDateToString(DateUtil.addMonths(new Date(), -offset));
							int count=0;
							Double result=0d;
							Double coefficientsub=0d;
							for(Map childhpmap:getchildhplist){
								String phid=childhpmap.get("id").toString();
								String getcurmonthsql="select hr_pa_kpiitem2usercmp.* from hr_pa_kpiitem2usercmp,hr_pa_kpipbc2usercmp " +
								"where hr_pa_kpiitem2usercmp.piId="+phid+" and hr_pa_kpipbc2usercmp.id=hr_pa_kpiitem2usercmp.pbcId "+
								"and DATE_FORMAT(hr_pa_kpipbc2usercmp.createDate,'%Y-%m')>DATE_FORMAT('"+sd+"','%Y-%m') " +
								"and DATE_FORMAT(hr_pa_kpipbc2usercmp.createDate,'%Y-%m')<=DATE_FORMAT('"+ed+"','%Y-%m') " ;
								List<Map> getcurmonthlist=selectDataService.getData(getcurmonthsql);
								if(getcurmonthlist!=null||getcurmonthlist.size()>0){
									for(Map getcurmonthmap:getcurmonthlist){
										count++;
										result=result+Double.parseDouble(getcurmonthmap.get("result")!=null?getcurmonthmap.get("result").toString():"0");
										coefficientsub=coefficientsub+Double.parseDouble(getcurmonthmap.get("coefficient")!=null?getcurmonthmap.get("coefficient").toString():"0");
									}
								}
							}
							HrPaKpiitem2user hrPaKpiitem2user=(HrPaKpiitem2user)baseService.findObjectById(HrPaKpiitem2user.class, "id", Long.parseLong(pbciumap.get("id").toString()));
							hrPaKpiitem2user.setResult(result/count);
							hrPaKpiitem2user.setCoefficient(coefficientsub/count);
							baseService.save(hrPaKpiitem2user, HrPaKpiitem2user.class, "id");
						}else{
							//5.查询改指标下的得分计算公式
							List<HrPaPerformanceindexscore> hslist=baseService.findObjectListByParamAndOrder(HrPaPerformanceindexscore.class, hsmap, null);
							//6.查询改指标下的得分计算公式，如果没有，则跳出遍历，继续下一个的指标的分计算
							if(hslist==null||hslist.size()==0){
								logger.error(hp.getPaName()+"无可计算的绩效指标得分，需要加上.");
								continue;//继续下一个绩效指标的得分
							}else{
								//7.获取改人的指标模板
								HrPaKpiitem2user hrPaKpiitem2user=(HrPaKpiitem2user)baseService.findObjectById(HrPaKpiitem2user.class, "id", Long.parseLong(pbciumap.get("id").toString()));
								allcount++;
								//8.遍历计算每个公式，通过任务目标和达成，判断，获取符合条件的指标得分
								for(HrPaPerformanceindexscore hs:hslist){
									LinkedHashMap psrmap=new LinkedHashMap();
									psrmap.put("pisId", hs.getId());
									//9.获取公式
									List psrlist=baseService.findObjectListByParamAndOrder(HrPaPisrule.class, psrmap, null);
									if(psrlist.size()>0){
										HrPaPisrule psr=(HrPaPisrule) psrlist.get(0);
										String formula=psr.getFormula();
										Set fp=this.getPram(formula);
										Iterator it=fp.iterator();
										boolean jp=false;
										//10.获取公式的达成和目标的字段，替换为真实的达成和目标
										while(it.hasNext()){
											String keyp=it.next().toString();
											int lastk=keyp.lastIndexOf("_");
											String key=keyp.substring(1, lastk);
											String flag=keyp.substring(lastk+1, keyp.length()-1);
											String sql="";
											String sd=DateUtil.convertDateToString(DateUtil.addMonths(new Date(), -(excutefre+offset)));
											String ed=DateUtil.convertDateToString(DateUtil.addMonths(new Date(), -offset));
											if(flag.equals("t")){
												 sql="select hr_pa_assessmentTasksAssigned.id,hr_pa_assessmentTasksAssigned.target as target " +
												 		"from hr_pa_assessmentCriteria,hr_pa_assessmentTasksAssigned " +
												 		"where hr_pa_assessmentCriteria.acKey='"+key+"' and hr_pa_assessmentTasksAssigned.acId=hr_pa_assessmentCriteria.id " +
												 		"and DATE_FORMAT(hr_pa_assessmentTasksAssigned.publishDate,'%Y-%m')>DATE_FORMAT('"+sd+"','%Y-%m') " +
														"and DATE_FORMAT(hr_pa_assessmentTasksAssigned.publishDate,'%Y-%m')<=DATE_FORMAT('"+ed+"','%Y-%m') " ;
												 String usersql=sql+" and hr_pa_assessmentTasksAssigned.userId="+hrPaKpiitem2user.getPbc2User().getBelongUser();
												 String categorysql=sql+" and hr_pa_assessmentTasksAssigned.deptId="+depId+" and hr_pa_assessmentTasksAssigned.category is not null";
												 List<Map> userlist=selectDataService.getData(usersql);
												 //先通过用户去查该指标的目标是否存在
												 if(userlist.size()>0){
													 Double target=0d;
													 for(Map map:userlist){
														 target=Double.parseDouble(map.get("target")!=null?map.get("target").toString():"0");
													 }
													 formula= formula.replace(keyp, target+"");
												 }else{//如果不存在是否有改指标通过品类来下达的目标
													 List<Map> categorylist=selectDataService.getData(categorysql);
													 if(categorylist.size()>0){
														 Double target=0d;
														 for(Map map:categorylist){
															 target=Double.parseDouble(map.get("target")!=null?map.get("target").toString():"0");
														 }
														 formula= formula.replace(keyp, target+"");
													 }else{//如果都没有，说明没有该指标的目标
														 logger.error("用户id："+hrPaKpiitem2user.getPbc2User().getBelongUser()+"指标："+hp.getPaName()+"符合原公式为 "+psr.getFormula()+",但是没有相应的考核任务额度"); 
														 jp=true;
														 break;
													 }
												 }
											}else if(flag.equals("r")){
												 sql="select hr_pa_acReached.id,hr_pa_acReached.result as result from hr_pa_assessmentCriteria,hr_pa_acReached " +
												"where hr_pa_assessmentCriteria.acKey='"+key+"' and hr_pa_acReached.acId=hr_pa_assessmentCriteria.id "+
												"and DATE_FORMAT(hr_pa_acReached.inputDate,'%Y-%m')>DATE_FORMAT('"+sd+"','%Y-%m') " +
												"and DATE_FORMAT(hr_pa_acReached.inputDate,'%Y-%m')<=DATE_FORMAT('"+ed+"','%Y-%m') " ;
												 String usersql=sql+" and hr_pa_acReached.userId="+hrPaKpiitem2user.getPbc2User().getBelongUser();
												 String categorysql=sql+" and hr_pa_acReached.deptId="+depId+" and hr_pa_acReached.category is not null";
												 List<Map> userlist=selectDataService.getData(usersql);
												 //先通过用户去查该指标的目标是否存在
												 if(userlist.size()>0){
													 Double result=0d;
													 for(Map map:userlist){
														 result=Double.parseDouble(map.get("result")!=null?map.get("result").toString():"0");
													 }
													 formula= formula.replace(keyp, result+"");
												 }else{//如果不存在是否有改指标通过品类来下达的目标
													 List<Map> categorylist=selectDataService.getData(categorysql);
													 if(categorylist.size()>0){
														 Double result=0d;
														 for(Map map:categorylist){
															 result=Double.parseDouble(map.get("result")!=null?map.get("result").toString():"0");
														 }
														 formula= formula.replace(keyp, result+"");
													 }else{//如果都没有，说明没有该指标的目标
														 logger.error("用户id："+hrPaKpiitem2user.getPbc2User().getBelongUser()+"指标："+hp.getPaName()+"符合原公式为 "+psr.getFormula()+",但是没有相应的指标达成数据"); 
														 jp=true;
														 break;
													 }
												 }
											}
										}
										if(jp==true){
											logger.error("用户id："+hrPaKpiitem2user.getPbc2User().getBelongUser()+"指标："+hp.getPaName()+"符合原公式为 "+psr.getFormula()+",但是没有相应的指标达成或考核任务的数据,无法计算该绩效指标得分"); 
											break;
										}
										boolean flag;
										try {
											FormatFm ff=new FormatFm();
											Modle m=ff.doFormat(formula);
											flag = ff.doFormula(m);
											if(flag==true){
												succount++;
												logger.info("用户id："+hrPaKpiitem2user.getPbc2User().getBelongUser()+"指标："+hp.getPaName()+"得分"+hs.getPisScore()+"符合原公式为 "+psr.getFormula()+"替换后为"+formula);
												hrPaKpiitem2user.setResult(hs.getPisScore().doubleValue());
												hrPaKpiitem2user.setCoefficient(hs.getCoefficient());
												baseService.save(hrPaKpiitem2user, HrPaKpiitem2user.class, "id");
												break;
											}
										} catch (Exception e) {
											e.printStackTrace();
											logger.error("用户id："+hrPaKpiitem2user.getPbc2User().getBelongUser()+"指标："+hp.getPaName()+"得分"+hs.getPisScore()+"符合原公式为 "+psr.getFormula()+"替换后为"+formula+",公式可能不符合约定格式，计算出错");
										}
									}
								}
							}
						}
					}
				}
			}
		}
		Long endtime=new Date().getTime();
		logger.info("共有考核项目:"+allcount+"个,成功："+succount+"个,失败："+(allcount-succount)+"个,共计时间"+((endtime-starttime)/1000)+"秒,零"+((endtime-starttime)%1000)+"毫秒");
		return true;
	}
	//是否可以计算
	public String isKpiItemScoreForUser(String userid,String depid,String pbc2userid){
		logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++操作用户ID："+userid+"用户PBC模板ID："+pbc2userid+"++++判断是否可以计算该用户的模板开始+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");	
		BaseService baseService =(BaseService) ContextHolder.getBean("baseService");		
		LinkedHashMap pmap=new LinkedHashMap();
		HrPaDatadictionary fre1=new HrPaDatadictionary();
		fre1.setId(Long.parseLong(PropertiesUtil.getProperties("hr.pamodle")));
		pmap.put("mode", fre1);
		pmap.put("publishStatus",Integer.parseInt(PropertiesUtil.getProperties("hr.papublishStatus")));
		//1.查出所有绩效考核指标
		List<HrPaPerformanceindex> hplist=baseService.findObjectListByParamAndOrder(HrPaPerformanceindex.class, pmap, null);
		Long starttime=new Date().getTime();
		String jobexcutedate=PropertiesUtil.getProperties("hr.jobexcutedate");
		String[] ejobexcutedate=jobexcutedate.split(",");
		Map jobexcutedatemap=new HashMap();
		for(String ej:ejobexcutedate){
			String [] ejs=ej.split("_");
			Map datemap=new HashMap();
			datemap.put("startdate", ejs[1]);
			datemap.put("excutefre", ejs[2]);
			datemap.put("offset", ejs[3]);//时间偏移量，0为计算当月，1为计算前一个月
			jobexcutedatemap.put(ejs[0], datemap);
		}
		int curmonth=new Date().getMonth()+1;
		int allcount=0;
		int succount=0;
		//2.遍历绩效考核指标
		StringBuffer datajson=new StringBuffer();
		boolean jp=false;
		datajson.append("data:[");
		for(HrPaPerformanceindex hp:hplist){
			//3.查询改绩效考核指标是否有人使用
			String pbcitemusersql="select hr_pa_kpiitem2user.id,app_user.fullname,hr_pa_kpipbc2user.pbcName,app_user.depId from hr_pa_kpiitem2user,hr_pa_kpipbc2user,app_user,department " +
					"where hr_pa_kpiitem2user.pbcId=hr_pa_kpipbc2user.id and hr_pa_kpipbc2user.belongUser=app_user.userId " +
					"and app_user.depId=department.depId and hr_pa_kpipbc2user.publishStatus!=3 and hr_pa_kpiitem2user.piId="+hp.getId();
			if(depid!=null&&depid.length()>0){
				pbcitemusersql+=" and department.depId="+depid;
			}
			if(pbc2userid!=null&&pbc2userid.length()>0){
				pbcitemusersql+=" and hr_pa_kpipbc2user.id="+pbc2userid;
			}
			List pbcitemuserlist=selectDataService.getData(pbcitemusersql);
			//4.如果没有，则跳出该指标的人员模板查询，有则继续
			if(pbcitemuserlist==null||pbcitemuserlist.size()==0){
				logger.info(hp.getPaName()+"没有人使用该绩效指标，跳出该指标的算分.");
				continue;//继续下一个绩效指标的得分
			}
			String frequency=hp.getPaFrequency()+"";
			Map datemap=(Map) jobexcutedatemap.get(frequency);
			if(datemap!=null){
				int excutefre=Integer.parseInt(datemap.get("excutefre").toString());
				int startdate=Integer.parseInt(datemap.get("startdate").toString());
				int offset=Integer.parseInt(datemap.get("offset").toString());
				int curday=new Date().getDate();
				if(PropertiesUtil.getProperties("hr.isautoj").equals("true")){
					if(curday>=25){
						offset=0;
					}else if(curday<5){
						offset=1;
					}
				}
				if(curmonth%excutefre==startdate){
					LinkedHashMap hsmap=new LinkedHashMap();
					hsmap.put("piId", hp.getId());
					for(int i=0;i<pbcitemuserlist.size();i++){
						Map pbciumap=(Map) pbcitemuserlist.get(i);
						String getchildhp="select * from hr_pa_performanceindex where hr_pa_performanceindex.parentId="+hp.getId();
						List<Map> getchildhplist=selectDataService.getData(getchildhp);
						String depId=pbciumap.get("depId").toString();
						if(getchildhplist!=null&&getchildhplist.size()>0){
							//查看当月的月该年度考核相关的指标是否已审核
							String sd=DateUtil.convertDateToString(DateUtil.addMonths(new Date(), -(1+offset)));
							String ed=DateUtil.convertDateToString(DateUtil.addMonths(new Date(), -offset));
							for(Map childhpmap:getchildhplist){
								String phid=childhpmap.get("id").toString();
								String getcurmonthsql="select * from hr_pa_kpiitem2usercmp,hr_pa_kpipbc2usercmp " +
								"where hr_pa_kpiitem2usercmp.piId="+phid+" and hr_pa_kpipbc2usercmp.id=hr_pa_kpiitem2usercmp.pbcId "+
								"and DATE_FORMAT(hr_pa_kpipbc2usercmp.createDate,'%Y-%m')>DATE_FORMAT('"+sd+"','%Y-%m') " +
								"and DATE_FORMAT(hr_pa_kpipbc2usercmp.createDate,'%Y-%m')<=DATE_FORMAT('"+ed+"','%Y-%m') " ;
								List<Map> getcurmonthlist=selectDataService.getData(getcurmonthsql);
								if(getcurmonthlist==null||getcurmonthlist.size()==0){
									datajson.append("{pbcName:\"\",paName:\""+hp.getPaName()+"\",desc:\"考核指标-"+hp.getPaName()+",需先计算发的归集指标,如月，季度等指标.\"},");
								}
							}
						}else{
							//5.查询改指标下的得分计算公式
							List<HrPaPerformanceindexscore> hslist=baseService.findObjectListByParamAndOrder(HrPaPerformanceindexscore.class, hsmap, null);
							//6.查询改指标下的得分计算公式，如果没有，则跳出遍历，继续下一个的指标的分计算
							if(hslist==null||hslist.size()==0){
								datajson.append("{pbcName:\"\",paName:\""+hp.getPaName()+"\",desc:\"考核指标-"+hp.getPaName()+"无可计算的绩效指标得分，需要加上.\"},");
							}else{
								//7.获取改人的指标模板
								HrPaKpiitem2user hrPaKpiitem2user=(HrPaKpiitem2user)baseService.findObjectById(HrPaKpiitem2user.class, "id", Long.parseLong(pbciumap.get("id").toString()));
								allcount++;
								//8.遍历计算每个公式，通过任务目标和达成，判断，获取符合条件的指标得分
								Set fp=new HashSet();
								String formula="";
								for(HrPaPerformanceindexscore hs:hslist){
									LinkedHashMap psrmap=new LinkedHashMap();
									psrmap.put("pisId", hs.getId());
									//9.获取公式
									List psrlist=baseService.findObjectListByParamAndOrder(HrPaPisrule.class, psrmap, null);
									if(psrlist.size()>0){
										HrPaPisrule psr=(HrPaPisrule) psrlist.get(0);
										formula+=psr.getFormula()+",";
									}
								}
								fp=this.getPram(formula);
								Iterator it=fp.iterator();
								while(it.hasNext()){
									String keyp=it.next().toString();
									int lastk=keyp.lastIndexOf("_");
									String key=keyp.substring(1, lastk);
									String flag=keyp.substring(lastk+1, keyp.length()-1);
									String sql="";
									String sd=DateUtil.convertDateToString(DateUtil.addMonths(new Date(), -(excutefre+offset)));
									String ed=DateUtil.convertDateToString(DateUtil.addMonths(new Date(), -offset));
									if(flag.equals("t")){
										 sql="select hr_pa_assessmentTasksAssigned.id,hr_pa_assessmentTasksAssigned.target as target " +
										 		"from hr_pa_assessmentCriteria,hr_pa_assessmentTasksAssigned " +
										 		"where hr_pa_assessmentCriteria.acKey='"+key+"' and hr_pa_assessmentTasksAssigned.acId=hr_pa_assessmentCriteria.id " +
										 		"and DATE_FORMAT(hr_pa_assessmentTasksAssigned.publishDate,'%Y-%m')>DATE_FORMAT('"+sd+"','%Y-%m') " +
												"and DATE_FORMAT(hr_pa_assessmentTasksAssigned.publishDate,'%Y-%m')<=DATE_FORMAT('"+ed+"','%Y-%m') " ;
										 String usersql=sql+" and hr_pa_assessmentTasksAssigned.userId="+hrPaKpiitem2user.getPbc2User().getBelongUser();
										 String categorysql=sql+" and hr_pa_assessmentTasksAssigned.deptId="+depId+" and hr_pa_assessmentTasksAssigned.category is not null";
										 List<Map> userlist=selectDataService.getData(usersql);
										 //先通过用户去查该指标的目标是否存在
										 if(userlist.size()>0){
										 }else{//如果不存在是否有改指标通过品类来下达的目标
											 List<Map> categorylist=selectDataService.getData(categorysql);
											 if(categorylist.size()>0){
											 }else{//如果都没有，说明没有该指标的目标
												 datajson.append("{pbcName:\""+pbciumap.get("pbcName")+"\",paName:\""+hp.getPaName()+"\",desc:\"用户-"+pbciumap.get("fullname")+"考核指标-"+hp.getPaName()+"标准-"+key+",但是没有相应的考核任务额度.\"},");
												 jp=true;
											 }
										 }
									}else if(flag.equals("r")){
										 sql="select hr_pa_acReached.id,hr_pa_acReached.result as result from hr_pa_assessmentCriteria,hr_pa_acReached " +
										"where hr_pa_assessmentCriteria.acKey='"+key+"' and hr_pa_acReached.acId=hr_pa_assessmentCriteria.id "+
										"and DATE_FORMAT(hr_pa_acReached.inputDate,'%Y-%m')>DATE_FORMAT('"+sd+"','%Y-%m') " +
										"and DATE_FORMAT(hr_pa_acReached.inputDate,'%Y-%m')<=DATE_FORMAT('"+ed+"','%Y-%m') " ;
										 String usersql=sql+" and hr_pa_acReached.userId="+hrPaKpiitem2user.getPbc2User().getBelongUser();
										 String categorysql=sql+" and hr_pa_acReached.deptId="+depId+" and hr_pa_acReached.category is not null";
										 List<Map> userlist=selectDataService.getData(usersql);
										 //先通过用户去查该指标的目标是否存在
										 if(userlist.size()>0){
										 }else{//如果不存在是否有改指标通过品类来下达的目标
											 List<Map> categorylist=selectDataService.getData(categorysql);
											 if(categorylist.size()>0){
											 }else{//如果都没有，说明没有该指标的目标
												 datajson.append("{pbcName:\""+pbciumap.get("pbcName")+"\",paName:\""+hp.getPaName()+"\",desc:\"用户-"+pbciumap.get("fullname")+"考核指标-"+hp.getPaName()+"标准-"+key+",但是没有相应的指标达成数据.\"},");
												 jp=true;
											 }
										 }
									}
								}
							}
						}
					}
				}
			}
		}
		Long endtime=new Date().getTime();
		//logger.info("共有考核项目:"+allcount+"个,成功："+succount+"个,失败："+(allcount-succount)+"个,共计时间"+((endtime-starttime)/1000)+"秒,零"+((endtime-starttime)%1000)+"毫秒");
		String json=datajson.toString();
		if(json.lastIndexOf(",")==json.length()-1){
			json=json.substring(0,json.length()-1);
		}
		json=json+"]";
		if(jp==true){
			return "{success:false,"+json+"}";
		}else{
			return "{success:true,"+json+"}";
		}
	}
	
	public boolean saveSalarDetail(String userid,String depid){
		logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++部门ID："+depid+"++++++薪资计算开始+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		Long starttime=new Date().getTime();
		SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");
		//1.查询调用此方法的人的具体信息
		String usersql="select app_user.fullname,department.depName from app_user,department where app_user.userId="+userid+" and app_user.depId=department.depId";
		List userlist=selectDataService.getData(usersql);
		String sysfullname="系统默认";
		if(userlist!=null&&userlist.size()>0){
			Map userMap=(Map) userlist.get(0);
			sysfullname=userMap.get("fullname").toString();
		}
		//2.查询传入部门的人员档案信息，如果部门为空就全部人员
		String sqlemp="select emp_profile.provident as provident," +
				"emp_profile.insurance as insurance," +
				"emp_profile.perCoefficient as perCoefficient," +
				"emp_profile.depId as depId," +
				"emp_profile.fullname as fullname," +
				"emp_profile.standardMoney as standardMoney," +
				"emp_profile.profileNo as profileNo," +
				"emp_profile.standardId as standardId," +
				"app_user.fullname as fullname," +
				"emp_profile.idCard as idCard," +
				"app_user.userId as userId" +
				" from emp_profile,app_user " +
				"where emp_profile.departureTime is null and emp_profile.userId=app_user.userId and emp_profile.delFlag=0 and emp_profile.approvalStatus=1";
		if(depid!=null&&depid.length()>0){
			sqlemp=sqlemp+" and app_user.depId="+depid;
		}
		//3.获取在职人员的档案
		List<Map> empprolist=selectDataService.getData(sqlemp);
		Integer allcount=empprolist.size();
		Integer succount=0;
		//从资源配置文件获取不同频率的相关数据
		String jobexcutedate=PropertiesUtil.getProperties("hr.jobexcutedate");
		String[] ejobexcutedate=jobexcutedate.split(",");
		Map jobexcutedatemap=new HashMap();
		for(String ej:ejobexcutedate){
			String [] ejs=ej.split("_");
			Map datemap=new HashMap();
			datemap.put("startdate", ejs[1]);//开始执行时间
			datemap.put("excutefre", ejs[2]);//执行频率
			datemap.put("offset", ejs[3]);//时间偏移量，0为计算当月，1为计算前一个月
			jobexcutedatemap.put(ejs[0], datemap);
		}
		
		//遍历档案人员的具体薪资信息
		for(Map emppro:empprolist){
			//公积金
			Double provident=emppro.get("provident")!=null?Double.parseDouble(emppro.get("provident").toString()):0d;
			//保险
			Double insurance=emppro.get("insurance")!=null?Double.parseDouble(emppro.get("insurance").toString()):0d;
			//绩效基数
			Double perCoefficient=emppro.get("perCoefficient")!=null?Double.parseDouble(emppro.get("perCoefficient").toString()):0d;
			
			//用户id
			Long userId=emppro.get("userId")!=null?Long.parseLong(emppro.get("userId").toString()):0;
			String fullname=emppro.get("fullname")!=null?emppro.get("fullname").toString():"";
			String profileNo=emppro.get("profileNo")!=null?emppro.get("profileNo").toString():"";
			Long standardId=emppro.get("standardId")!=null?Long.parseLong(emppro.get("standardId").toString()):0;
			String idCard=emppro.get("idCard")!=null?emppro.get("idCard").toString():"";
			//薪酬标准金额
			Double standardMoney=emppro.get("standardMoney")!=null?Double.parseDouble(emppro.get("standardMoney").toString()):0d;
			
			Long depId=emppro.get("depId")!=null?Long.parseLong(emppro.get("depId").toString()):0;
			//通过userid获取pbc模板的相应月份绩效得分，如果没有，就说明为0
			int offset=0;
			int curday=new Date().getDate();
			String curd=DateUtil.convertDateToString(new Date());
			//绩效系数
			double factorValue=0;
			if(PropertiesUtil.getProperties("hr.isautoj").equals("true")){
				if(curday>=25){
					offset=0;
				}else if(curday<5){
					offset=1;
				}
			}
			String sd=DateUtil.convertDateToString(DateUtil.addMonths(new Date(), -(1+offset)));
			String ed=DateUtil.convertDateToString(DateUtil.addMonths(new Date(), -offset));
			String kpipbcsql="select hr_pa_kpipbc2usercmp.* from hr_pa_kpipbc2usercmp " +
					"where hr_pa_kpipbc2usercmp.belongUser="+userId+
					" and DATE_FORMAT(hr_pa_kpipbc2usercmp.createDate,'%Y-%m')>DATE_FORMAT('"+sd+"','%Y-%m') " +
					" and DATE_FORMAT(hr_pa_kpipbc2usercmp.createDate,'%Y-%m')<=DATE_FORMAT('"+ed+"','%Y-%m') order by hr_pa_kpipbc2usercmp.modifyDate desc" ;
			//获取个人的pbc模板的分数
			List<Map> kpipbclist=selectDataService.getData(kpipbcsql);
			if(kpipbclist.size()>0){
				Map kpipbcmap=kpipbclist.get(0);
				String totalScore=kpipbcmap.get("totalScore").toString();
				factorValue=Double.parseDouble(kpipbcmap.get("coefficient").toString());
				String frequency=kpipbcmap.get("frequency").toString();
				Map datemap=(Map) jobexcutedatemap.get(frequency);
				if(datemap!=null){
					int excutefre=Integer.parseInt(datemap.get("excutefre").toString());
					perCoefficient=perCoefficient*excutefre;//获取该频率的基数是每隔几个月的，基数就是月基数*月数
				}
			}else{
				logger.info("该月没有此人"+emppro.get("fullname")+"相应的PBC模板，因此绩效系数为0");
			}
			//获取该人的奖惩信息
			String rpsql="select hr_sr_rewardspunishmentstype.typeName,hr_sr_rewardsPunishments.remark,(case when hr_sr_rewardspunishmentstype.operation=1 then hr_sr_rewardsPunishments.amount else 0 end) as jl,(case when hr_sr_rewardspunishmentstype.operation=0 then hr_sr_rewardsPunishments.amount else 0 end) as cf from hr_sr_rewardsPunishments,hr_sr_rewardspunishmentstype " +
					"where userId="+userId+" and hr_sr_rewardsPunishments.rpType=hr_sr_rewardspunishmentstype.typeId " +
					"and DATE_FORMAT(hr_sr_rewardsPunishments.createDate,'%Y-%m')>DATE_FORMAT('"+sd+"','%Y-%m') " +
					"and DATE_FORMAT(hr_sr_rewardsPunishments.createDate,'%Y-%m')<=DATE_FORMAT('"+ed+"','%Y-%m') " ;
			List<Map> rplist=selectDataService.getData(rpsql);
			//惩罚金额
			Double rpamount=0d;
			Double encourageAmount=0d;
			Double deductAmount=0d;
			String encourageStr="";
			String deductStr="";
			for(Map rpmap:rplist){
				//rpamount+=(Double.parseDouble(rpmap.get("jl")!=null?rpmap.get("jl").toString():"0")-Double.parseDouble(rpmap.get("cf")!=null?rpmap.get("cf").toString():"0"));
				encourageAmount+=Double.parseDouble(rpmap.get("jl")!=null?rpmap.get("jl").toString():"0");
				if(Double.parseDouble(rpmap.get("jl")!=null?rpmap.get("jl").toString():"0")>0){
					encourageStr+=rpmap.get("typeName")+":"+Double.parseDouble(rpmap.get("jl")!=null?rpmap.get("jl").toString():"0")+"("+rpmap.get("remark")+") ";
				}
				deductAmount+=Double.parseDouble(rpmap.get("cf")!=null?rpmap.get("cf").toString():"0");
				if(Double.parseDouble(rpmap.get("cf")!=null?rpmap.get("cf").toString():"0")>0){
					deductStr+=rpmap.get("typeName")+":"+Double.parseDouble(rpmap.get("cf")!=null?rpmap.get("cf").toString():"0")+"("+rpmap.get("remark")+") ";
				}
			}
			rpamount=encourageAmount-deductAmount;
			//薪资发放登记表
			//实发工资=基本工资+绩效奖金-五险一金+奖励-罚款-个人所得税
			//绩效奖金=绩效奖金基数×绩效系数
			//个人所得税=（基本工资+绩效奖金-五险一金+奖励-罚款-个人所得税起征点）*算法
			String incomesql="select * from hr_sr_incometax order by publishDate desc";
			List<Map> incomelist=selectDataService.getData(incomesql);
			if(incomelist!=null&&incomelist.size()>0){
				Map incomemap=incomelist.get(0);
				//个税起征点
				Double basicAmount=incomemap.get("basicAmount")!=null?Double.parseDouble(incomemap.get("basicAmount").toString()):0d;
				//基本工资+绩效奖金-五险一金+奖励-罚款-个人所得税起征点
				double ksgz=standardMoney+perCoefficient*factorValue-provident-insurance+rpamount-basicAmount;
				String incomeitemsql="select * from hr_sr_incometaxitem where lowerAmount<"+ksgz+"and limitAmount>="+ksgz;
				List<Map> incometaxitmelist=selectDataService.getData(incomeitemsql);
				DecimalFormat myformat = new DecimalFormat();
				myformat.applyPattern("###.00");
				if(incometaxitmelist!=null&&incometaxitmelist.size()>0){
					Map intaxitemmap=incometaxitmelist.get(0);
					Double taxValue=intaxitemmap.get("taxValue")!=null?Double.parseDouble(intaxitemmap.get("taxValue").toString()):0d;
					Double deductValue=intaxitemmap.get("deductValue")!=null?Double.parseDouble(intaxitemmap.get("deductValue").toString()):0d;
					Double selftax=ksgz*taxValue-deductValue;
					selftax=Double.parseDouble(myformat.format(selftax));
					Double realincome=standardMoney+perCoefficient*factorValue-provident-insurance+rpamount-selftax;
					SalaryPayoff salaryPayoff=new SalaryPayoff();
					salaryPayoff.setStandAmount(new BigDecimal(myformat.format(standardMoney)));
					salaryPayoff.setFullname(fullname);
					salaryPayoff.setUserId(userId);
					salaryPayoff.setProfileNo(profileNo);
					salaryPayoff.setStandardId(standardId);
					salaryPayoff.setIdNo(idCard);
					salaryPayoff.setEncourageAmount(new BigDecimal(myformat.format(encourageAmount)));
					salaryPayoff.setEncourageDesc(encourageStr);
					salaryPayoff.setDeductAmount(new BigDecimal(myformat.format(deductAmount)));
					salaryPayoff.setDeductDesc(deductStr);
					salaryPayoff.setAchieveAmount(new BigDecimal(myformat.format(perCoefficient*factorValue)));
					salaryPayoff.setAcutalAmount(new BigDecimal(myformat.format(realincome)));
					String memo="标准金额："+standardMoney+"+ (绩效基数为："+perCoefficient+" *绩效系数为："+factorValue+")+"+encourageStr+deductStr+"公积金：-"+provident+"保险：-"+insurance+"个人所得税:-"+selftax+"=实际工资："+myformat.format(realincome)+"(注：该员工工资的税率为:"+taxValue+",数算扣除数为："+deductValue+")";
					salaryPayoff.setMemo("系统自动计算,"+memo);
					salaryPayoff.setRegister(sysfullname);
					salaryPayoff.setRegTime(new Date());
					salaryPayoff.setCheckStatus(SalaryPayoff.CHECK_FLAG_NONE);
					salaryPayoff.setStartTime(DateUtil.getFirstDayOfMonth(DateUtil.convertStringToDate(ed)));
					salaryPayoff.setEndTime(DateUtil.getLastDayOfMonth(DateUtil.convertStringToDate(ed)));
					salaryPayoff.setProvident(new BigDecimal(myformat.format(provident)));
					salaryPayoff.setInsurance(new BigDecimal(myformat.format(insurance)));
					salaryPayoff.setSelftax(new BigDecimal(myformat.format(selftax)));
					salaryPayoff.setTaxableAmount(new BigDecimal(myformat.format(ksgz)));
					salaryPayoff.setIssuedAmount(new BigDecimal(myformat.format(standardMoney+perCoefficient*factorValue+encourageAmount)));
					salaryPayoff.setPerCoefficient(new BigDecimal(myformat.format(factorValue)));
					salaryPayoff.setPerNumber(new BigDecimal(myformat.format(perCoefficient)));
					BaseService baseService =(BaseService) ContextHolder.getBean("baseService");	
					try {
						baseService.save(salaryPayoff, SalaryPayoff.class, "recordId");
						succount++;
						logger.info(emppro.get("fullname")+"本月工资单,"+memo);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error("计算"+emppro.get("fullname")+"的工资时出错,请检查相关数据！");
					}
				}else {
					Double selftax=0d;
					selftax=Double.parseDouble(myformat.format(selftax));
					Double realincome=standardMoney+perCoefficient*factorValue-provident-insurance+rpamount-selftax;
					SalaryPayoff salaryPayoff=new SalaryPayoff();
					salaryPayoff.setStandAmount(new BigDecimal(myformat.format(standardMoney)));
					salaryPayoff.setFullname(fullname);
					salaryPayoff.setUserId(userId);
					salaryPayoff.setProfileNo(profileNo);
					salaryPayoff.setStandardId(standardId);
					salaryPayoff.setIdNo(idCard);
					salaryPayoff.setEncourageAmount(new BigDecimal(myformat.format(encourageAmount)));
					salaryPayoff.setEncourageDesc(encourageStr);
					salaryPayoff.setDeductAmount(new BigDecimal(myformat.format(deductAmount)));
					salaryPayoff.setDeductDesc(deductStr);
					salaryPayoff.setAchieveAmount(new BigDecimal(myformat.format(perCoefficient*factorValue)));
					salaryPayoff.setAcutalAmount(new BigDecimal(myformat.format(realincome)));
					String memo="标准金额："+standardMoney+"+ (绩效基数为："+perCoefficient+" *绩效系数为："+factorValue+")+"+encourageStr+deductStr+"公积金：-"+provident+"保险：-"+insurance+"个人所得税:-"+selftax+"=实际工资："+realincome+"(注：该员工工资的税率为:0)";
					salaryPayoff.setMemo("系统自动计算,"+memo);
					salaryPayoff.setRegister(sysfullname);
					salaryPayoff.setRegTime(new Date());
					salaryPayoff.setCheckStatus(SalaryPayoff.CHECK_FLAG_NONE);
					salaryPayoff.setStartTime(DateUtil.getFirstDayOfMonth(DateUtil.convertStringToDate(ed)));
					salaryPayoff.setEndTime(DateUtil.getLastDayOfMonth(DateUtil.convertStringToDate(ed)));
					salaryPayoff.setProvident(new BigDecimal(myformat.format(provident)));
					salaryPayoff.setInsurance(new BigDecimal(myformat.format(insurance)));
					salaryPayoff.setSelftax(new BigDecimal(myformat.format(selftax)));
					salaryPayoff.setTaxableAmount(new BigDecimal(0));
					salaryPayoff.setIssuedAmount(new BigDecimal(myformat.format(standardMoney+perCoefficient*factorValue+encourageAmount)));
					salaryPayoff.setPerCoefficient(new BigDecimal(myformat.format(factorValue)));
					BaseService baseService =(BaseService) ContextHolder.getBean("baseService");	
					try {
						baseService.save(salaryPayoff, SalaryPayoff.class, "recordId");
						succount++;
						logger.info(emppro.get("fullname")+"本月工资单,"+memo);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error("计算"+emppro.get("fullname")+"的工资时出错,请检查相关数据！");
					}
				}
			}else{
				logger.error("找不到相关税率扣除相关信息");
				break;
			}			
		}
		Long endtime=new Date().getTime();
		logger.info("共有档案人员:"+allcount+"个,成功："+succount+"个,失败："+(allcount-succount)+"个,共计时间"+((endtime-starttime)/1000)+"秒,零"+((endtime-starttime)%1000)+"毫秒");
		return true;
	}
	private Set getPram(String formula){
		Set set=new HashSet();
		String regex="\\{[^\\{\\}]+\\}";//匹配{}
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(formula);
		while(matcher.find()){
			String	group=matcher.group();
			set.add(group);
		}
		return set;
	}
	
	public String removeTargetAndRequire(){
		SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");
		
		try {
			String uncmppbcsql="select hr_pa_pisrule.formula from hr_pa_kpipbc2user,hr_pa_kpiitem2user,hr_pa_pisrule,hr_pa_performanceindex,hr_pa_performanceindexscore " +
					"where hr_pa_kpiitem2user.pbcId=hr_pa_kpipbc2user.id " +
					"and hr_pa_kpiitem2user.piId=hr_pa_performanceindex.id " +
					"and hr_pa_performanceindex.id=hr_pa_performanceindexscore.piId " +
					"and hr_pa_performanceindexscore.id=hr_pa_pisrule.pisId";
			List<Map> uncmppbclist=selectDataService.getData(uncmppbcsql);
			String uncmpformula="";
			for(Map uncmpmap:uncmppbclist){
				uncmpformula+=uncmpmap.get("formula").toString()+",";
			}
			int offset=0;
			int curday=new Date().getDate();
			String curd=DateUtil.convertDateToString(new Date());
			//绩效系数
			double factorValue=0;
			if(PropertiesUtil.getProperties("hr.isautoj").equals("true")){
				if(curday>=25){
					offset=0;
				}else if(curday<5){
					offset=1;
				}
			}
			Set uncmpformulaset=this.getPram(uncmpformula);
			Iterator it=uncmpformulaset.iterator();
			String targetStr="";
			String requireStr="";
			String sd=DateUtil.convertDateToString(DateUtil.addMonths(new Date(), -(1+offset)));
			String ed=DateUtil.convertDateToString(DateUtil.addMonths(new Date(), -offset));
			while(it.hasNext()){
				String keyp=it.next().toString();
				int lastk=keyp.lastIndexOf("_");
				String key=keyp.substring(1, lastk);
				String flag=keyp.substring(lastk+1, keyp.length()-1);
				String sql="";
				if(flag.equals("t")){
					 sql="select distinct hr_pa_assessmentTasksAssigned.id " +
					 		"from hr_pa_assessmentCriteria,hr_pa_assessmentTasksAssigned " +
					 		"where hr_pa_assessmentCriteria.acKey='"+key+"' and hr_pa_assessmentTasksAssigned.acId=hr_pa_assessmentCriteria.id " +
					 		"and DATE_FORMAT(hr_pa_assessmentTasksAssigned.publishDate,'%Y-%m')>DATE_FORMAT('"+sd+"','%Y-%m') " +
							"and DATE_FORMAT(hr_pa_assessmentTasksAssigned.publishDate,'%Y-%m')<=DATE_FORMAT('"+ed+"','%Y-%m') " ;
					 List<Map> targetlist=selectDataService.getData(sql);
					 for(Map tarmap:targetlist){
						 targetStr+= tarmap.get("id")+",";
					 }
					 
				}else if(flag.equals("r")){
					 sql="select distinct hr_pa_acReached.id " +
					 "from hr_pa_assessmentCriteria,hr_pa_acReached " +
					"where hr_pa_assessmentCriteria.acKey='"+key+"' and hr_pa_acReached.acId=hr_pa_assessmentCriteria.id "+
					"and DATE_FORMAT(hr_pa_acReached.inputDate,'%Y-%m')>DATE_FORMAT('"+sd+"','%Y-%m') " +
					"and DATE_FORMAT(hr_pa_acReached.inputDate,'%Y-%m')<=DATE_FORMAT('"+ed+"','%Y-%m') " ;
					 List<Map> requirelist=selectDataService.getData(sql);
					 for(Map reqmap:requirelist){
						 requireStr+= reqmap.get("id")+",";
					 }
				}
			}
			if(targetStr.length()>0&&targetStr.lastIndexOf(",")==targetStr.length()-1){
				 targetStr=targetStr.substring(0,targetStr.length()-1);
			 }
			if(requireStr.length()>0&&requireStr.lastIndexOf(",")==requireStr.length()-1){
				 requireStr=requireStr.substring(0,requireStr.length()-1);
			 }
			//当目标与达成计算完成后，删除表中的数据，放入历史表中
			String deletetasksql="delete from hr_pa_assessmenttasksassigned "+
				"where DATE_FORMAT(hr_pa_assessmentTasksAssigned.publishDate,'%Y-%m')>DATE_FORMAT('"+sd+"','%Y-%m') " +
				"and DATE_FORMAT(hr_pa_assessmentTasksAssigned.publishDate,'%Y-%m')<=DATE_FORMAT('"+ed+"','%Y-%m')";
			
			String savetasksql="insert into hr_pa_assessmenttasksassigned_hist select * from hr_pa_assessmenttasksassigned " +
				"where DATE_FORMAT(hr_pa_assessmentTasksAssigned.publishDate,'%Y-%m')>DATE_FORMAT('"+sd+"','%Y-%m') " +
				"and DATE_FORMAT(hr_pa_assessmentTasksAssigned.publishDate,'%Y-%m')<=DATE_FORMAT('"+ed+"','%Y-%m')";
			if(targetStr.length()>0){
				savetasksql+=" and hr_pa_assessmenttasksassigned.id not in("+targetStr+")";
				deletetasksql+=" and hr_pa_assessmenttasksassigned.id not in("+targetStr+")";
			}
			String deleteacrsql="delete from hr_pa_acreached " +
				"where DATE_FORMAT(hr_pa_acReached.inputDate,'%Y-%m')>DATE_FORMAT('"+sd+"','%Y-%m') " +
				"and DATE_FORMAT(hr_pa_acReached.inputDate,'%Y-%m')<=DATE_FORMAT('"+ed+"','%Y-%m') " ;
			
			String saveacrsql="insert into hr_pa_acreached_hist select * from hr_pa_acreached " +
				"where DATE_FORMAT(hr_pa_acReached.inputDate,'%Y-%m')>DATE_FORMAT('"+sd+"','%Y-%m') " +
				"and DATE_FORMAT(hr_pa_acReached.inputDate,'%Y-%m')<=DATE_FORMAT('"+ed+"','%Y-%m') " ;
			if(requireStr.length()>0){
				saveacrsql+=" and hr_pa_acreached.id not in("+requireStr+")";
				deleteacrsql+=" and hr_pa_acreached.id not in("+requireStr+")";
			}
			selectDataService.saveRealTable(savetasksql);
			selectDataService.saveRealTable(saveacrsql);
			selectDataService.remove(deletetasksql);
			selectDataService.remove(deleteacrsql);
			logger.info("移除历史数据成功！");
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("移除历史数据出错！");
			return "false";
		}
		return "true";
	}
}
