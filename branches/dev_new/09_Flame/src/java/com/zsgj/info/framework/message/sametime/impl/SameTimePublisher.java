package com.zsgj.info.framework.message.sametime.impl;

import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import antlr.collections.impl.Vector;

import com.lotus.sametime.core.comparch.STSession;
import com.lotus.sametime.core.types.STGroup;
import com.lotus.sametime.core.types.STObject;
import com.lotus.sametime.core.types.STUser;
import com.lotus.sametime.lookup.GroupContentEvent;
import com.lotus.sametime.lookup.GroupContentGetter;
import com.lotus.sametime.lookup.GroupContentListener;
import com.lotus.sametime.lookup.LookupService;
import com.lotus.sametime.lookup.ResolveEvent;
import com.lotus.sametime.lookup.ResolveListener;
import com.lotus.sametime.lookup.Resolver;
import com.lotus.sametime.post.Post;
import com.lotus.sametime.post.PostEvent;
import com.lotus.sametime.post.PostListener;
import com.lotus.sametime.post.PostService;
import com.zsgj.info.framework.message.sametime.EventPublisher;
import com.zsgj.info.framework.message.sametime.SameTimeSessionFactory;
import com.zsgj.info.framework.service.BaseService;

/**
 * SameTime消息发布者
 * @Class Name SameTimePublisher
 * @Author zhangpeng
 * @Create In Feb 2, 2008
 */
public class SameTimePublisher extends BaseService implements EventPublisher, PostListener {
	
	private STSession session;
	private PostService postService;
	private GroupResolverServiceImpl groupResolver;
	private RecipientResolverServiceImpl recipientResolver;
	
	private Vector recipientInPerson;
	private Vector recipientGroup;
	
	private Post message;
	private HttpSession reqSession;
	
	public SameTimePublisher(STSession session, HttpSession reqSession){
		this.session = session;
		postService = (PostService)session.getCompApi(PostService.COMP_NAME);
		postService.registerPostType(SameTimeSessionFactory.SYSTEM_ALERT_EVENT_TYPE);
		postService.registerPostType(SameTimeSessionFactory.SYSTEM_REQUEST_EVENT_TYPE);
		
		recipientInPerson = new Vector();
		recipientGroup = new Vector();
		
		groupResolver = new GroupResolverServiceImpl();  
		recipientResolver = new RecipientResolverServiceImpl();
		
		this.reqSession = reqSession;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.digitalchina.pmcenter.common.base.sametime.EventPublisher#publishEvent(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void publishEvent(int eventType, String title, String body, String recipients){
		message = postService.createPost(eventType);
		message.addPostListener(this);
		message.setMessage(body);
		message.setTitle(title);
		
		StringTokenizer st = new StringTokenizer(recipients,"##");
		String[] recipient_list = new String[st.countTokens()];
		
		int count = 0;
		while(st.hasMoreTokens()){
			recipient_list[count++] = st.nextToken();
		}
		
		recipientResolver.resolveRecipients(recipient_list);
		Enumeration e = recipientGroup.elements();
		while(e.hasMoreElements()){
			STGroup stg = (STGroup)e.nextElement();
			groupResolver.resolverGroup(stg);
		}
		
		message.send();
		
		recipientInPerson = new Vector();
		recipientGroup = new Vector();
	}
	
	public void sendToUserFailed(PostEvent arg0) {
		// TODO Auto-generated method stub
		int reason = arg0.getReason();
		String recipient = arg0.getPostedUser().getDisplayName();
		String resp = this.getProperties("system.sametime.reperror","消息无法发送到:");
		reqSession.setAttribute("sameTimeResp", "" + resp + recipient);
	}

	public void userResponded(PostEvent arg0) {
		// TODO Auto-generated method stub
		String msgTitle = this.getProperties("system.sametime.repmessage" , "消息已经发送到:");
		reqSession.setAttribute("sameTimeResp", "" + msgTitle + arg0.getPostedUser().getDisplayName());
	}
	
	/**
	 * 查询联系人内类
	 * @Class Name RecipientResolverServiceImpl
	 * @Author Iceman
	 * @Create In Feb 1, 2008
	 */
	public class RecipientResolverServiceImpl implements ResolveListener {
		
		private Resolver resolver;
		
		private int waitFlag;
		
		private LookupService lookupService;
		
		public RecipientResolverServiceImpl(){
			lookupService = (LookupService)session.getCompApi(LookupService.COMP_NAME);
			resolver = lookupService.createResolver(false, false, true, true);
			resolver.addResolveListener(this);
		}
		
		public void resolveRecipients(String[] recipients){
			waitFlag = recipients.length;
			resolver.resolve(recipients);
			
			long totalSlept = 0;
			while(waitFlag > 0 && totalSlept < SameTimeSessionFactory.ResolutionTimeOut){
				try {
					Thread.sleep(SameTimeSessionFactory.ResolutionPeriod);
					totalSlept += SameTimeSessionFactory.ResolutionPeriod;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error(e.getMessage());
				}finally{
					waitFlag = 0;
				}	
			}
		}
		
		public void resolveConflict(ResolveEvent arg0) {
			// TODO Auto-generated method stub
			STObject[] recipients = (STObject[])arg0.getResolvedList();
			for(int i=0; i<recipients.length; i++){
				addRecipient(recipients[i]);
			}
			waitFlag--;
		}

		public void resolveFailed(ResolveEvent arg0) {
			// TODO Auto-generated method stub
			logger.error(arg0.getResolved().getName() + "fault!!");
			waitFlag--;
		}

		public void resolved(ResolveEvent arg0) {
			// TODO Auto-generated method stub
			STObject sto = (STObject)arg0.getResolved();
			addRecipient(sto);
			waitFlag--;
		}
		
		public void addRecipient(STObject sto){
			
		}
	}
	
	/**
	 * 查询组信息内类
	 * @Class Name GroupResolverServiceImpl
	 * @Author Iceman
	 * @Create In Feb 1, 2008
	 */
	public class GroupResolverServiceImpl implements GroupContentListener {
		
		private GroupContentGetter gcg ;
		
		private int waitFlag;
		
		private LookupService lookupService;
		
		public GroupResolverServiceImpl(){
			lookupService = (LookupService)session.getCompApi(LookupService.COMP_NAME);
			gcg = lookupService.createGroupContentGetter();
			gcg.addGroupContentListener(this);
			waitFlag = 0;
		}
		
		public void resolverGroup(STGroup group){
			waitFlag++;
			gcg.queryGroupContent(group);
			long totalSlept = 0;
			while(waitFlag > 0 && totalSlept < SameTimeSessionFactory.ResolutionTimeOut){
				try {
					Thread.sleep(SameTimeSessionFactory.ResolutionPeriod);
					totalSlept += SameTimeSessionFactory.ResolutionPeriod;
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error(e.getMessage());
				} finally{
					waitFlag = 0;
				}
			}
		}
		
		public void groupContentQueried(GroupContentEvent arg0) {
			// TODO Auto-generated method stub
			STObject[] sto = arg0.getGroupContent();
			for(int i=0; i<sto.length; i++){
				if(sto[i].getClass().getName().endsWith("STUser")){
					STUser stu = (STUser)sto[i];
					if(!recipientInPerson.equals(stu.getId())){
						message.addUser(stu);
						recipientInPerson.appendElement(stu.getId());
					}
				}else if(sto.getClass().getName().endsWith("STGroup")){
					STGroup stg = (STGroup)sto[i];
					resolverGroup(stg);
				}
			}
			
			waitFlag--;
		}

		public void queryGroupContentFailed(GroupContentEvent arg0) {
			// TODO Auto-generated method stub
			logger.error(arg0.getGroup().getName() + "queryGroupContentFailed ! ");
			waitFlag--;
		}

	}
}
