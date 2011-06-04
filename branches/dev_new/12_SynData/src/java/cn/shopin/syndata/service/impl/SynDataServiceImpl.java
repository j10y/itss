/**
 * @Probject Name: 12_SynData
 * @Path: cn.shopin.syndata.serviceSynDataService.java
 * @Create By Jack
 * @Create In 2011-5-24 03:25:49
 * TODO
 */
package cn.shopin.syndata.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.shopin.syndata.service.FilesService;
import cn.shopin.syndata.service.SynDataService;
import cn.shopin.syndata.utils.ContextHolder;
import cn.shopin.syndata.utils.PropertiesUtil;

/**
 * @Class Name SynDataService
 * @Author Jack
 * @Create In 2011-5-24
 */
public class SynDataServiceImpl implements SynDataService{
	private final Log logger = LogFactory.getLog("servicelog");
	
	public void doSyn(){
		
		String baseFileDir = PropertiesUtil.getProperties("system.file.basepath","c:/transport");
		FilesService fs = (FilesService)ContextHolder.getBean("filesService");
		List<String> fl = fs.readFolderByFile(baseFileDir);
		
		logger.info("Start SynData........");
		try {
			for(String item : fl){
				logger.info("Create Thread for " + item);
				SynDataRunnable sdr = new SynDataRunnable(item);
				Thread td = new Thread(sdr);
				td.start();
			}
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("SynError:" + e.getMessage());
		}
		logger.info("End SynData........");
	}
}
