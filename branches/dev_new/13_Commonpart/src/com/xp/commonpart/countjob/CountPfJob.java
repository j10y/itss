package com.xp.commonpart.countjob;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.CronTriggerBean;
import com.xp.commonpart.service.BaseService;
import com.xp.commonpart.util.ContextHolder;
import com.xp.hr.scheduler.bean.TaskScheduler;
import com.xpsoft.oa.kpi.service.CountJobService;

public class CountPfJob {
	protected final transient Log logger = LogFactory.getLog(getClass());
	private Scheduler scheduler; 
    // 设值注入，通过setter方法传入被调用者的实例scheduler 
    public void setScheduler(Scheduler scheduler) { 
        this.scheduler = scheduler; 
   } 
   
	public String countPf(){
		logger.info("---------------------------------------------执行定时任务 countPf ,执行时间："+(new Date())+"---------------------------------");
		CountJobService countJobService= (CountJobService) ContextHolder.getBean("countJobService");
		countJobService.saveKpiItemScoreForUser();
		return null;
	}
	public String countSalJobDetail(){
		logger.info("---------------------------------------------执行定时任务 薪资计算 ,执行时间："+(new Date())+"---------------------------------");
		CountJobService countJobService= (CountJobService) ContextHolder.getBean("countJobService");
		countJobService.saveSalarDetail();
		return null;
	}
	public boolean saveKpiItemScoreForUser(String userid,String depid,String pbc2userid){
		CountJobService countJobService= (CountJobService) ContextHolder.getBean("countJobService");
		countJobService.saveKpiItemScoreForUser(userid, depid, pbc2userid);
		return true;
	}
	
	public boolean saveSalarDetail(String userid,String depid){
		CountJobService countJobService= (CountJobService) ContextHolder.getBean("countJobService");
		countJobService.saveSalarDetail(userid, depid);
		return true;
	}
	public String isKpiItemScoreForUser(String userid,String depid,String pbc2userid){
		CountJobService countJobService= (CountJobService) ContextHolder.getBean("countJobService");
		return countJobService.isKpiItemScoreForUser(userid, depid, pbc2userid);
	}
	public String resetTaskTime(String ctrigger,String cronexpress){
		Scheduler scheduler1= (Scheduler) ContextHolder.getBean("schedulerFactory");
		CronTriggerBean trigger;
		try {
			trigger = (CronTriggerBean) scheduler1.getTrigger(ctrigger, Scheduler.DEFAULT_GROUP);
			String originConExpression = trigger.getCronExpression(); 
			if(!originConExpression.equals(cronexpress)){
				trigger.setCronExpression(cronexpress);
				scheduler1.rescheduleJob(ctrigger, Scheduler.DEFAULT_GROUP, trigger); 
		     }
		} catch (SchedulerException e) {
			e.printStackTrace();
			return "success:false";
		}catch (ParseException e) {
			e.printStackTrace();
			return "success:false";
		}  
	   return "success:true";
	}
	public String defJob() throws SchedulerException, ParseException{
		BaseService baseService =(BaseService) ContextHolder.getBean("baseService");
		List<TaskScheduler> list=baseService.findObjectListByParamAndOrder(TaskScheduler.class, null, null);
		for(TaskScheduler ts:list){
			 CronTriggerBean trigger = (CronTriggerBean) scheduler.getTrigger(ts.getCronTrigger(), Scheduler.DEFAULT_GROUP);  
		     String originConExpression = trigger.getCronExpression(); 
		     if(!originConExpression.equals(ts.getRunTime())){
					trigger.setCronExpression(ts.getRunTime());
					scheduler.rescheduleJob(ts.getCronTrigger(), Scheduler.DEFAULT_GROUP, trigger); 
					logger.info(" -----------------------------start defJob-----"+originConExpression+(new Date()).getTime()+"---"+ts.getRunTime()+"-----------------------------");
		     }
		}
		CronTriggerBean trigger1 = (CronTriggerBean) scheduler.getTrigger("defJobTrigger", Scheduler.DEFAULT_GROUP);  
		trigger1.setCronExpression("0 0 0 ? 12 6L 2055");
		scheduler.rescheduleJob("defJobTrigger", Scheduler.DEFAULT_GROUP, trigger1);
		logger.info(" -----------------------------set job 0 0 0 ? 12 6L 2055 tostart again --------------------------------");
		return null;		
	}
	
}
