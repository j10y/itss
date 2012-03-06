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
		ComQueryService comqueryService=(ComQueryService) ContextHolder.getBean("comqueryService");
		//comqueryService.connectDataBase();
		//logger.info(" -----------------------------set job 0 0 0 ? 12 6L 2055 tostart defJob again --------------------------------");
		return null;		
	}
	
	public String jobForWeb() throws SchedulerException, ParseException{
		ComQueryService comqueryService=(ComQueryService) ContextHolder.getBean("comqueryService");
		//comqueryService.checkWebStatus();
		//logger.info(" -----------------------------set job 0 0 0 ? 12 6L 2055 tostart jobForWeb again --------------------------------");
		return null;		
	}
}
