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
 * �����ԭ�е�TaskInstance��Comment��������չ�ͼ�
 * ��Ϊ�����������ֻ��һ����Ϣ
 * ��չΪ�ڵ�����Ϣ�ڷֿ������˶���ֶ�
 * ��ϵͳĬ�����4000����Comment.Message�ֶΣ����������䣩
 * @Class Name CommentsInfo
 * @Author yang
 * @Create In Jun 20, 2008
 */
public class CommentsInfo {
	//ÿ�ڵ㵥����������comments�ĳ���Ϊ1
	List<CommentInfo> comments = new ArrayList<CommentInfo>();
	JSONObject values = null;
	
	//���commenInfo�е�ĳһ����Ϣ��
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
		//���������ֻȡ��һ��
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
