package com.zsgj.info.framework.workflow.info;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.jbpm.graph.exe.Comment;
/**
 * 本类对原有的TaskInstance的Comment进行了扩展和简化
 * 简化为单任务情况下只有一条信息
 * 扩展为在单条信息内分开保存了多个字段
 * （系统默认最大4000见表Comment.Message字段，不够可扩充）
 * @Class Name CommentsInfo
 * @Author yang
 * @Create In Jun 20, 2008
 */
public class CommentsInfo {
	//每节点单任务的情况下comments的长度为1
	List<CommentInfo> comments = new ArrayList<CommentInfo>();
	JSONObject values = null;
	
	//获得commenInfo中的某一段信息，
	public String getValue(String key) {
		String value = (String)values.get(key);
		return value;
	}
		
	public CommentsInfo(List<Comment> comments) {
		if(comments!=null) {
			for(Comment comment:comments) {
				this.comments.add(new CommentInfo(comment));
			}
		}
		//单条情况下只取第一条
		if(comments!=null&&!comments.isEmpty()) {
			String message = comments.get(0).getMessage();
			values = JSONObject.fromObject(message);
		}
		else {
			values = new JSONObject();
		}
	}	
	
	class CommentInfo{
		String actorId;
		long id;
		String message;
		Date time;
		
		public CommentInfo() {		
		}
		
		public CommentInfo(Comment comment) {
			setActorId(comment.getActorId());
			setId(comment.getId());
			setMessage(comment.getMessage());
			setTime(comment.getTime());
		}
	
		public String messageInfo() {
			String message = "";
			message += getMessage()==null?"":getMessage();
			if(message.trim().length()!=0) {
				String actorId = getActorId()==null?"unknown":getActorId();
				String time = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG).format(getTime());
				time += SimpleDateFormat.getTimeInstance(SimpleDateFormat.LONG).format(getTime());
				message += "["+actorId+":"+time+"]";
			}
			return message; 
		}
		public String getActorId() {
			return actorId;
		}
	
		public void setActorId(String actorId) {
			this.actorId = actorId;
		}
	
		public long getId() {
			return id;
		}
	
		public void setId(long id) {
			this.id = id;
		}
	
		public String getMessage() {
			return message;
		}
	
		public void setMessage(String message) {
			this.message = message;
		}
	
		public Date getTime() {
			return time;
		}
	
		public void setTime(Date time) {
			this.time = time;
		}
	}

	public JSONObject getValues() {
		return values;
	}

	public void setValues(JSONObject values) {
		this.values = values;
	}
}
