package com.xpsoft.oa.action.scheduler;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.scheduler.TaskScheduler;
import com.xpsoft.oa.service.scheduler.TaskSchedulerService;

import flexjson.JSONSerializer;


public class TaskSchedulerAction extends BaseAction{
	@Resource
	TaskSchedulerService taskSchedulerService;
	TaskScheduler taskScheduler;
	
	public TaskSchedulerService getTaskSchedulerService() {
		return taskSchedulerService;
	}
	public void setTaskSchedulerService(TaskSchedulerService taskSchedulerService) {
		this.taskSchedulerService = taskSchedulerService;
	}
	public TaskScheduler getTaskScheduler() {
		return taskScheduler;
	}
	public void setTaskScheduler(TaskScheduler taskScheduler) {
		this.taskScheduler = taskScheduler;
	}
	public String list()
	   {
	     QueryFilter filter = new QueryFilter(getRequest());
	     List list = this.taskSchedulerService.getAll(filter);
	
	     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
	      .append(filter.getPagingBean().getTotalItems()).append(",result:");
	     Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	     buff.append(gson.toJson(list));
	     buff.append("}"); 
	     this.jsonString = buff.toString();	 
	     return "success";
	   }
	public String multiDel(){
		String[] ids = getRequest().getParameterValues("ids");
		     if (ids != null) {
		       for (String id : ids) {
		         this.taskSchedulerService.remove(new Long(id));
		       }
		     }
	     this.jsonString = "{success:true}";
	     return "success";
	}
	public String get(){
		String id=getRequest().getParameter("id");
		taskScheduler=taskSchedulerService.get(Long.parseLong(id));
		JSONSerializer json = new JSONSerializer();
	    StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(json.serialize(taskScheduler));
		sb.append("}");
		setJsonString(sb.toString());
	    return "success";
	}
	public String save(){
		String cycle=taskScheduler.getCycle();
		String month=taskScheduler.getMonth();
		String day=taskScheduler.getDay();
		String datetime=taskScheduler.getDatetime();
		String taskName=taskScheduler.getTaskName();
		String[] times=datetime.split(":");
		for(int i=0;i<times.length;i++){
			times[i]=Integer.parseInt(times[i])+"";
		}
		String runTime="";
		String desc="";
		if(cycle.equals("每年")){
			if(month.equals("最后一个月")){
				if(day.equals("最后一天")){
					runTime=times[2]+" "+times[1]+" "+times[0]+" L 12 ?";
					desc="每年/最后一个月/最后一天/"+datetime;
				}else{
					runTime=times[2]+" "+times[1]+" "+times[0]+" "+day+" 12 ?";
					desc="每年/最后一个月/第"+day+"天/"+datetime;
				}
			}else{
				if(day.equals("最后一天")){
					runTime=times[2]+" "+times[1]+" "+times[0]+" L "+month+" ?";
					desc="每年/"+month+"月份/最后一天/"+datetime;
				}else{
					runTime=times[2]+" "+times[1]+" "+times[0]+" "+day+" "+month+" ?";
					desc="每年/"+month+"月份/第"+day+"天/"+datetime;
				}
			}
		}else if(cycle.equals("每半年")){
			if(month.equals("最后一个月")){
				if(day.equals("最后一天")){
					runTime=times[2]+" "+times[1]+" "+times[0]+" L 6,12 ?";
					desc="每半年/最后一个月(即6,12月份)/最后一天/"+datetime;
				}else{
					runTime=times[2]+" "+times[1]+" "+times[0]+" "+day+" 6,12 ?";
					desc="每半年/最后一个月(即6,12月份)/第"+day+"天/"+datetime;
				}
			}else{
				if(day.equals("最后一天")){
					runTime=times[2]+" "+times[1]+" "+times[0]+" L "+month+","+(Integer.parseInt(month)+6)+" ?";
					desc="每半年/"+month+","+(Integer.parseInt(month)+6)+"月份/最后一天/"+datetime;
				}else{
					runTime=times[2]+" "+times[1]+" "+times[0]+" "+day+" "+month+","+(Integer.parseInt(month)+6)+" ?";
					desc="每半年/"+month+","+(Integer.parseInt(month)+6)+"月份/第"+day+"天/"+datetime;
				}
			}
		}else if(cycle.equals("每季度")){
			if(month.equals("最后一个月")){
				if(day.equals("最后一天")){
					runTime=times[2]+" "+times[1]+" "+times[0]+" L 3,6,9,12 ?";
					desc="每季度/最后一个月(即3,6,9,12月份)/最后一天/"+datetime;
				}else{
					runTime=times[2]+" "+times[1]+" "+times[0]+" "+day+" 3,6,9,12 ?";
					desc="每季度/最后一个月(即3,6,9,12月份)/第"+day+"天/"+datetime;
				}
			}else{
				if(day.equals("最后一天")){
					runTime=times[2]+" "+times[1]+" "+times[0]+" L "+month+","+(Integer.parseInt(month)+3)+","+(Integer.parseInt(month)+6)+","+(Integer.parseInt(month)+9)+" ?";
					desc="每季度/"+month+","+(Integer.parseInt(month)+3)+","+(Integer.parseInt(month)+6)+","+(Integer.parseInt(month)+9)+"月份/最后一天/"+datetime;
				}else{
					runTime=times[2]+" "+times[1]+" "+times[0]+" "+day+" "+month+","+(Integer.parseInt(month)+3)+","+(Integer.parseInt(month)+6)+","+(Integer.parseInt(month)+9)+" ?";
					desc="每季度/"+month+","+(Integer.parseInt(month)+3)+","+(Integer.parseInt(month)+6)+","+(Integer.parseInt(month)+9)+"月份/第"+day+"天/"+datetime;
				}
			}
		}else if(cycle.equals("每月")){
			if(day.equals("最后一天")){
				runTime=times[2]+" "+times[1]+" "+times[0]+" L * ?";
				desc="每月/最后一天/"+datetime;
			}else{
				runTime=times[2]+" "+times[1]+" "+times[0]+" "+day+" * ?";
				desc="每月/第"+day+"天/"+datetime;
			}
		}else if(cycle.equals("每天")){
			runTime=times[2]+" "+times[1]+" "+times[0]+" * * ?";
			desc="每天/"+datetime;
		}else if(cycle.equals("失效")){
			runTime="0 0 0 ? 12 6L 2055";
			desc="取消执行任务";
		}
		if(taskScheduler.getId()!=null){
			taskScheduler=taskSchedulerService.get(taskScheduler.getId());
		}else{
			taskScheduler=new TaskScheduler();
		}
		taskScheduler.setRunTime(runTime);
		taskScheduler.setDesc(desc);
		taskScheduler.setTaskName(taskName);
		boolean flag=this.setJobTime(taskScheduler.getCronTrigger(), taskScheduler.getRunTime());
		if(flag==true){
			taskSchedulerService.save(taskScheduler);
			this.jsonString = new String("{success:true}");
		}else{
			this.jsonString = new String("{success:false}");
		}
		 return "success";
	}
	
	public boolean setJobTime(String ctrigger,String cronexpress){
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(
					 AppUtil.getPropertity("job.server.url")));
			call.setOperationName(AppUtil.getPropertity("job.server.resetTaskTime"));
			String translateText = (String) call
					.invoke(new Object[] { ctrigger,cronexpress});
			System.out.println(translateText);
		} catch (ServiceException e) {
			e.printStackTrace();
			System.out.println("Service 获取 Call对象失败!");
			return false;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("new java.net.URL(url)错误!");
			return false;
		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.println("远程错误!");
			return false;
		}
		return true;
	}	
}
