package com.xp.commonpart.countjob;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.CronTriggerBean;

import com.xp.commonpart.bean.TaskScheduler;
import com.xp.commonpart.service.BaseService;
import com.xp.commonpart.service.ComQueryService;
import com.xp.commonpart.util.ContextHolder;

public class CountPfJob {
	protected final transient Log logger = LogFactory.getLog(getClass());
	private Scheduler scheduler; 
    // 设值注入，通过setter方法传入被调用者的实例scheduler 
    public void setScheduler(Scheduler scheduler) { 
        this.scheduler = scheduler; 
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
//		BaseService baseService =(BaseService) ContextHolder.getBean("baseService");
//		List<TaskScheduler> list=baseService.findObjectListByParamAndOrder(TaskScheduler.class, null, null);
//		for(TaskScheduler ts:list){
//			 CronTriggerBean trigger = (CronTriggerBean) scheduler.getTrigger(ts.getCronTrigger(), Scheduler.DEFAULT_GROUP);  
//		     String originConExpression = trigger.getCronExpression(); 
//		     if(!originConExpression.equals(ts.getRunTime())){
//					trigger.setCronExpression(ts.getRunTime());
//					scheduler.rescheduleJob(ts.getCronTrigger(), Scheduler.DEFAULT_GROUP, trigger); 
//					logger.info(" -----------------------------start defJob-----"+originConExpression+(new Date()).getTime()+"---"+ts.getRunTime()+"-----------------------------");
//		     }
//		}
		ComQueryService comqueryService=(ComQueryService) ContextHolder.getBean("comqueryService");
		comqueryService.connectDataBase();
//		CronTriggerBean trigger1 = (CronTriggerBean) scheduler.getTrigger("defJobTrigger", Scheduler.DEFAULT_GROUP);  
//		trigger1.setCronExpression("0 0 0 ? 12 6L 2055");
//		scheduler.rescheduleJob("defJobTrigger", Scheduler.DEFAULT_GROUP, trigger1);
//		logger.info(" -----------------------------set job 0 0 0 ? 12 6L 2055 tostart again --------------------------------");
		return null;		
	}
	
}
