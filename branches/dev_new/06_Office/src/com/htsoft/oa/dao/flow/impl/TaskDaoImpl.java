 package com.htsoft.oa.dao.flow.impl;
 
 import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.jdbc.core.RowMapper;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.oa.dao.flow.TaskDao;
import com.htsoft.oa.model.flow.JbpmTask;
import com.htsoft.oa.model.system.AppRole;
import com.htsoft.oa.model.system.AppUser;
 
 public class TaskDaoImpl extends BaseDaoImpl<TaskImpl>
   implements TaskDao
 {
   public TaskDaoImpl()
   {
/*  24 */     super(TaskImpl.class);
   }
 
   public List<TaskImpl> getTasksByUserId(String userId, PagingBean pb)
   {
/*  32 */     AppUser user = (AppUser)getHibernateTemplate().load(AppUser.class, new Long(userId));
/*  33 */     Iterator rolesIt = user.getRoles().iterator();
/*  34 */     StringBuffer groupIds = new StringBuffer();
/*  35 */     int i = 0;
/*  36 */     while (rolesIt.hasNext()) {
/*  37 */       if (i++ > 0) groupIds.append(",");
/*  38 */       groupIds.append("'" + ((AppRole)rolesIt.next()).getRoleId().toString() + "'");
     }
 
/*  46 */     StringBuffer hqlSb = new StringBuffer();
/*  47 */     hqlSb.append("select task from org.jbpm.pvm.internal.task.TaskImpl task left join task.participations pt where task.assignee=?");
/*  48 */     hqlSb.append(" or ( task.assignee is null and pt.type = 'candidate' and ((pt.userId=?)");
 
/*  50 */     if (user.getRoles().size() > 0) {
/*  51 */       hqlSb.append(" or (pt.groupId in (" + groupIds.toString() + "))");
     }
/*  53 */     hqlSb.append("))");
/*  54 */     hqlSb.append(" order by task.createTime desc");
 
/*  56 */     return findByHql(hqlSb.toString(), new Object[] { userId, userId }, pb);
   }
 
   public List<JbpmTask> getByActivityNameVarKeyLongVal(String activityName, String varKey, Long value)
   {
/*  67 */     String sql = "select task.DBID_ taskId, task.ASSIGNEE_ assignee from jbpm4_task task join jbpm4_variable var on task.EXECUTION_=var.EXECUTION_ where  task.ACTIVITY_NAME_=? and var.KEY_=? and var.LONG_VALUE_=?";
/*  68 */     Collection jbpmtask = this.jdbcTemplate.query(sql, new Object[] { activityName, varKey, value }, 
/*  69 */       new RowMapper()
     {
       public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
/*  72 */         JbpmTask task = new JbpmTask();
/*  73 */         Long taskId = Long.valueOf(rs.getLong("taskId"));
/*  74 */         String assignee = rs.getString("assignee");
/*  75 */         task.setAssignee(assignee);
/*  76 */         task.setTaskId(taskId);
/*  77 */         return task;
       }
     });
/*  81 */     return new ArrayList(jbpmtask);
   }
 
   public List<Long> getGroupByTask(Long taskId) {
/*  85 */     String sql = "select pa.GROUPID_ groupId from jbpm4_participation pa  where pa.TYPE_ = 'candidate'and pa.TASK_=?";
/*  86 */     Collection groupIds = this.jdbcTemplate.query(sql, new Object[] { taskId }, 
/*  87 */       new RowMapper()
     {
       public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
/*  90 */         String groupId = rs.getString("groupId");
/*  91 */         return groupId;
       }
     });
/*  95 */     return new ArrayList(groupIds);
   }
 
   public List<Long> getUserIdByTask(Long taskId) {
/*  99 */     String hql = "from org.jbpm.pvm.internal.task.TaskImpl task where task.superTask.id=?";
/* 100 */     Object[] objs = { taskId };
/* 101 */     List<TaskImpl> taskList = findByHql(hql, objs);
/* 102 */     List list = new ArrayList();
/* 103 */     for (TaskImpl task : taskList) {
/* 104 */       list.add(new Long(task.getAssignee()));
     }
/* 106 */     return list;
   }
 }
