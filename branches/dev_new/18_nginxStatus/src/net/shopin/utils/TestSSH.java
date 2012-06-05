package net.shopin.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class TestSSH {

	private static Map<String, String> servers = new LinkedHashMap<String, String>();

	private static Map<String, String> startShell = new LinkedHashMap<String, String>();

	private static final Log logger = LogFactory.getLog("servicelog");
	
	static {
		// servers.put("100.31", "172.16.100.31,22,root,$linux12chriq");
		// startShell.put("100.31", "/opt/tomcat1;/opt/tomcat2");
		// servers.put("103.135", "172.16.103.135,22,root,$linux12chriq");

//		servers.put("200.59", "172.16.200.59,22,root,$mem590214");
//		servers.put("200.50", "172.16.200.50,22,root,$web50linux");
//		servers.put("200.17", "172.16.200.17,22,root,$web17linux");
//		servers.put("200.29", "172.16.200.29,22,root,$app290214");
//		servers.put("200.2", "172.16.200.2,22,root,$app20214");
		servers.put("200.62", "172.16.200.62,22,root,$linux12chrir");
		servers.put("200.63", "172.16.200.63,22,root,$linux12chril");
//		servers.put("200.120", "172.16.200.120,22,root,sanban-10214");
//		servers.put("200.83", "172.16.200.83,6699,root,$linux12chrih");

		// startShell.put("200.50",
		// "/usr/local/apache-tomcat-web;/usr/local/apache-tomcat-web2");
		// startShell.put("200.17",
		// "/usr/local/apache-tomcat-web;/usr/local/apache-tomcat-web2");
		// startShell.put("200.29",
		// "/usr/local/apache-tomcat-common;/usr/local/apache-tomcat-app");
		// startShell
		// .put("200.2",
		// "/usr/local/apache-tomcat-cache;/usr/local/apache-tomcat-app;/usr/local/apache-tomcat-extend");
		 startShell.put("200.62","/usr/local/apache-tomcat-order");
		 startShell.put("200.63","/usr/local/apache-tomcat-order");
		// startShell
		// .put("200.120",
		// "/usr/local/apache-tomcat-syn1;/usr/local/apache-tomcat-pricesynoci;/usr/local/apache-tomcat-web1;/usr/local/apache-tomcat-web2;/usr/local/apache-tomcat-web3");
		// startShell
		// .put("200.83",
		// "/usr/local/tomcat-paipai;/usr/local/tomcat-order;/usr/local/tomcat-back;/usr/local/tomcat-mw;/usr/local/tmall-job;/usr/local/tomcat-web");
		// startShell
		// .put("200.59",
		// "/usr/local/apache-tomcat-gghd;/usr/local/apache-tomcat-360web");//;/usr/local/apache-tomcat-swd
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws Exception {
		showAll();
		// killAll();
		// startAll();

		// showAll();

	}

	public static void showAll() throws Exception {
		for (String key : servers.keySet()) {
			String[] server = servers.get(key).split(",");

			SshExecuter se = SshExecuter.newInstance(server[0],
					Integer.valueOf(server[1]), server[2], server[3]);

			System.out.println("开始处理--IP:" + server[0] + " ");
			logger.info("开始处理--IP:" + server[0] + " ");
			SSHUtil.showAllTomcatProgress(se);

			se.close();
		}
	}

	public static void killAll() throws Exception {

		for (String key : servers.keySet()) {
			String[] server = servers.get(key).split(",");

			SshExecuter se = SshExecuter.newInstance(server[0],
					Integer.valueOf(server[1]), server[2], server[3]);

			System.out.println("开始处理--IP:" + server[0] + " ");
			logger.info("开始处理--IP:" + server[0] + " ");
			SSHUtil.showAllTomcatProgress(se);

			System.out.println("开始kill进程");
			logger.info("开始kill进程");
			SSHUtil.killAllTomcatProgress(se);
			logger.info("开始kill进程");
			System.out.println("进程已经全部杀死");

			System.out.println("显示剩余进程");
			logger.info("显示剩余进程");
			SSHUtil.showAllTomcatProgress(se);

			se.close();
		}
	}

	public static void startAll() throws Exception {

		for (String key : servers.keySet()) {
			String[] server = servers.get(key).split(",");

			SshExecuter se = SshExecuter.newInstance(server[0],
					Integer.valueOf(server[1]), server[2], server[3]);

			String[] shellary = startShell.get(key).split(";");

			for (String shell : shellary) {
				se.shell(shell + "/bin/startup.sh\n", "c:\\out.txt");
				Thread.sleep(1000);
			}

			se.close();
		}
	}
}
