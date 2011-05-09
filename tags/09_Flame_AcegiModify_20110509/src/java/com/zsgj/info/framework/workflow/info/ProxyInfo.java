package com.zsgj.info.framework.workflow.info;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProxyInfo {
	static private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private String proxyId = null;
	private String proxyBegin = null;
	private String proxyEnd = null;
	
	//���˴����Ƿ���Ч
	public String getRightActorId() {		
		if(getProxyId()!=null&&!getProxyId().trim().equals("")){
			Date now = new Date();
			Date begin = dateProxyBegin();
			Date end = dateProxyEnd();
			if(begin!=null&&begin.before(now)) {//�Ѿ���ʼ
				if(end==null||end.after(now)) {//δ���������������
					return getProxyId();
				}
			}
		}
		return null;
	}
	
	public String getProxyId() {
		return proxyId;
	}
	public void setProxyId(String proxyId) {
		this.proxyId = proxyId;
	}
	public Date dateProxyBegin() {
		if(proxyBegin==null||proxyBegin.trim().equals("")) {
			return null;
		}
		try {
			return sdf.parse(proxyBegin);
		} catch (ParseException e) {
			//e.printStackTrace();
			return new Date(proxyBegin);
			//return null;
		}
	}

	public void setProxyBegin(Date proxyBegin) {		
		try {
			this.proxyBegin = sdf.format(proxyBegin);
		} catch (RuntimeException e) {
			this.proxyBegin = "";
		}
	}

	public Date dateProxyEnd() {
		if(proxyEnd==null||proxyEnd.trim().equals("")) {
			return null;
		}
		try {
			return sdf.parse(proxyEnd);
		} catch (ParseException e) {
			//e.printStackTrace();
			return new Date(proxyEnd);
		}
	}

	public void setProxyEnd(Date proxyEnd) {
		try {
			this.proxyEnd = sdf.format(proxyEnd);
		} catch (RuntimeException e) {
			this.proxyEnd = "";
		}
	}
	
	public String getProxyBegin() {
		return proxyBegin;
	}

	public void setProxyBegin(String proxyBegin) {
		this.proxyBegin = proxyBegin;
	}

	public String getProxyEnd() {
		return proxyEnd;
	}

	public void setProxyEnd(String proxyEnd) {
		this.proxyEnd = proxyEnd;
	}
}
