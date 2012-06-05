package net.shopin.job;

import java.util.Date;

import net.shopin.utils.AppUtil;

/**
 * Created by IntelliJ IDEA. User: jptong Date: 12-3-13 Time: 下午5:30 To change
 * this template use File | Settings | File Templates.
 */
public class DefJob {
	public void defJob() {
		AppUtil.reloadResouse();
		System.out.println("----------------------------" + new Date()
				+ "--------------------------------");
	}
}
