/**
 * @Probject Name: 12_SynData
 * @Path: cn.shopin.syndata.serviceSynDataJob.java
 * @Create By Jack
 * @Create In 2011-5-25 上午11:58:20
 * TODO
 */
package cn.shopin.syndata.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.shopin.syndata.utils.ContextHolder;

/**
 * @Class Name SynDataJob
 * @Author Jack
 * @Create In 2011-5-25
 */
public class SynDataJob extends QuartzJobBean {

	private final Log logger = LogFactory.getLog("servicelog");
	private SynDataService sds = null;
	/**
	 * @Return the SynDataService sds
	 */
	public SynDataService getSds() {
		return sds;
	}
	/**
	 * @Param SynDataService sds to set
	 */
	public void setSds(SynDataService sds) {
		this.sds = sds;
	}
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		logger.info("***************Start Run SYSData Application ******************");
		sds.doSyn();
	}

}
