package com.xpsoft.oa.action.hrm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.Job;
import com.xpsoft.oa.model.hrm.JobSalaryRelation;
import com.xpsoft.oa.model.hrm.StandSalary;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.service.hrm.JobSalaryRelationService;

import flexjson.JSONSerializer;

public class JobSalaryRelationAction extends BaseAction {

	@Resource
	private JobSalaryRelationService jobSalaryRelationService;
	private JobSalaryRelation jobSalaryRelation;
	private Long relationId;

	public Long getrelationId() {
		/* 38 */return this.relationId;
	}

	public void setrelationId(Long relationId) {
		/* 42 */this.relationId = relationId;
	}

	public JobSalaryRelation getJobSalaryRelation() {
		/* 46 */return this.jobSalaryRelation;
	}

	public void setJobSalaryRelation(JobSalaryRelation jobSalaryRelation) {
		/* 50 */this.jobSalaryRelation = jobSalaryRelation;
	}

	public String list() {
		/* 58 */QueryFilter filter = new QueryFilter(getRequest());
		/* 59 */List list = this.jobSalaryRelationService.getAll(filter);

		/* 62 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 63 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 66 */JSONSerializer serializer = new JSONSerializer();
		/* 67 */buff
				.append(serializer.exclude(
						new String[] { "class", "department.appUser" })
						.serialize(list));

		/* 69 */buff.append("}");

		/* 71 */this.jsonString = buff.toString();

		/* 73 */return "success";
	}

	public String multiDel() {
		/* 81 */String[] ids = getRequest().getParameterValues("ids");
		/* 82 */if (ids != null) {
			/* 83 */for (String id : ids) {
				/* 84 */JobSalaryRelation removeJobSalaryRelation = (JobSalaryRelation) this.jobSalaryRelationService
						.get(new Long(id));
				/* 85 */removeJobSalaryRelation
						.setDeleteFlag(JobSalaryRelation.DELFLAG_HAD);
				/* 86 */this.jobSalaryRelationService
						.save(removeJobSalaryRelation);
			}
		}

		/* 90 */this.jsonString = "{success:true}";

		/* 92 */return "success";
	}

	public String get() {
		/* 100 */JobSalaryRelation JobSalaryRelation = (JobSalaryRelation) this.jobSalaryRelationService
				.get(this.relationId);

		/* 103 */JSONSerializer json = new JSONSerializer();

		/* 105 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 106 */sb.append(json.exclude(new String[] { "class" }).serialize(
				JobSalaryRelation));
		/* 107 */sb.append("}");
		/* 108 */setJsonString(sb.toString());

		/* 110 */return "success";
	}

	public String save() {
		/* 116 */this.jobSalaryRelation
				.setDeleteFlag(JobSalaryRelation.DELFLAG_NOT);
		Department department = new Department();
		department.setDepId(Long.valueOf(getRequest().getParameter(
				"jobSalaryRelation.department.depId")));
		Job job = new Job();
		job.setJobId(Long.valueOf(getRequest().getParameter(
				"jobSalaryRelation.job.jobId")));
		StandSalary standSalary = new StandSalary();
		standSalary.setStandardId(Long.valueOf(getRequest().getParameter(
				"jobSalaryRelation.standSalary.standardId")));
		this.jobSalaryRelation.setDeleteFlag(JobSalaryRelation.DELFLAG_NOT);
		this.jobSalaryRelation.setDepartment(department);
		this.jobSalaryRelation.setJob(job);
		this.jobSalaryRelation.setStandSalary(standSalary);
		this.jobSalaryRelationService.save(this.jobSalaryRelation);
		setJsonString("{success:true}");
		return "success";
	}

	public String recovery() {
		/* 150 */String[] ids = getRequest().getParameterValues("ids");
		/* 151 */if (ids != null) {
			/* 152 */for (String id : ids) {
				/* 153 */JobSalaryRelation deleteJobSalaryRelation = (JobSalaryRelation) this.jobSalaryRelationService
						.get(new Long(id));
				/* 154 */deleteJobSalaryRelation
						.setDeleteFlag(JobSalaryRelation.DELFLAG_NOT);
				/* 155 */this.jobSalaryRelationService
						.save(deleteJobSalaryRelation);
			}
		}
		/* 158 */this.jsonString = "{success:true}";
		/* 159 */return "success";
	}
	
	public String comboSalary() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<JobSalaryRelation> list = this.jobSalaryRelationService.getAll(filter);
		Set set = new HashSet();
		for(JobSalaryRelation relation : list){
			StandSalary salary = relation.getStandSalary();
			salary.getStandardId();
			salary.getStandardNo();
			salary.getStandardName();
			salary.getTotalMoney();
			salary.getSetdownTime();
			salary.getStatus();
			set.add(salary);
		}
		StringBuffer buff = new StringBuffer();
		buff.append("[");
		Iterator<StandSalary> iterator = set.iterator();
		while(iterator.hasNext()){
			StandSalary salary = iterator.next();
			buff.append("{");
			buff.append("'standardId':'").append(salary.getStandardId()).append("',");
			buff.append("'standardNo':'").append(salary.getStandardNo()).append("',");
			buff.append("'standardName':'").append(salary.getStandardName()).append("',");
			buff.append("'totalMoney':'").append(salary.getTotalMoney()).append("',");
			buff.append("'setdownTime':'").append(salary.getSetdownTime()).append("',");
			buff.append("'perCoefficient':'").append(salary.getPerCoefficient()).append("',");
			buff.append("'yearTotalMoney':'").append(salary.getYearTotalMoney()).append("',");
			buff.append("'baseMoney':'").append(salary.getBaseMoney()).append("',");
			buff.append("'status':'").append(salary.getStatus()).append("'");
			if(iterator.hasNext()){
				buff.append("},");
			}else{
				buff.append("}");
			}
		}
		buff.append("]");
		this.jsonString = buff.toString();
		return "success";
	}
}
