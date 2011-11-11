package com.xp.shopalarm.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.helpers.ThreadLocalMap;

import com.xp.commonpart.service.SelectDataService;
import com.xp.commonpart.util.ContextHolder;
import com.xp.commonpart.util.JDBCUtil;
import com.xp.commonpart.util.PropertiesUtil;

public class NonBlockingServer {

	//private SelectDataService selectDataService = null;
	private SelectDataService selectDataService;
	
	

	public SelectDataService getSelectDataService() {
		return selectDataService;
	}

	public void setSelectDataService(SelectDataService selectDataService) {
		this.selectDataService = selectDataService;
	}

	public Selector sel = null;
	public ServerSocketChannel server = null;
	public SocketChannel socket = null;
	
	public int port = 4900;
	
	String result = null;
	private String url = null;
	private String username = null;
	private String pwd = null;
	
	private String alarmFinalEndHour = "22";
	
	public String getAlarmFinalEndHour() {
		return alarmFinalEndHour;
	}

	public void setAlarmFinalEndHour(String alarmFinalEndHour) {
		this.alarmFinalEndHour = alarmFinalEndHour;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public NonBlockingServer() {
		System.out.println("Inside default ctor");
	}

	public NonBlockingServer(int port) {
		System.out.println("Inside the other ctor");
		this.port = port;
	}

	public void initializeOperations() throws IOException, UnknownHostException {
		System.out.println("Inside initialization");
		sel = Selector.open();
		server = ServerSocketChannel.open();
		server.configureBlocking(false);
		InetAddress ia = InetAddress.getLocalHost();
		InetSocketAddress isa = new InetSocketAddress(ia, port);
		server.socket().bind(isa);
	}

	public void startServer() throws IOException {
		System.out.println("Inside startserver");
		initializeOperations();
		System.out.println("Abt to block on select()");
		SelectionKey acceptKey = server.register(sel, SelectionKey.OP_ACCEPT);

		while (acceptKey.selector().select() > 0) {

			Set readyKeys = sel.selectedKeys();
			Iterator it = readyKeys.iterator();

			/**
			 * 刚启动如果没有订单号发来，频率为5秒
			 */
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			while (it.hasNext()) {
				SelectionKey key = (SelectionKey) it.next();
				it.remove();

				/**
				 * 检查是否存在开通短信通知门店  频率为30秒
				 */
				if (!checkShopAlarm()) {
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					continue;// 没有门店开通短信通知
				}

					
				if (key.isAcceptable()) {
					System.out.println("Key is Acceptable");
					ServerSocketChannel ssc = (ServerSocketChannel) key
							.channel();
					socket = (SocketChannel) ssc.accept();
					socket.configureBlocking(false);
					SelectionKey another = socket.register(sel,
							SelectionKey.OP_READ | SelectionKey.OP_WRITE);
				}
				 

				if (key.isReadable()) {
					System.out.println("Key is readable");
					String orderNo = readMessage(key);
					if (orderNo.trim().length() > 0) {
						try {
							processMessage(socket, orderNo.trim());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				if (key.isWritable()) {
					System.out.println("The key is writable");
					String ret = readMessage(key);
					socket = (SocketChannel) key.channel();
					if (result.length() > 0) {
						writeMessage(socket, ret);
					}
				}
				 
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void writeMessage(SocketChannel socket, String ret) {
		System.out.println("Inside the loop");

		if (ret.equals("quit") || ret.equals("shutdown")) {
			return;
		}
		
		try {

			String s = "This is context from server!-----------------------------------------";
			Charset set = Charset.forName("us-ascii");
			CharsetDecoder dec = set.newDecoder();
			CharBuffer charBuf = dec.decode(ByteBuffer.wrap(s.getBytes()));
			System.out.println(charBuf.toString());
			int nBytes = socket.write(ByteBuffer.wrap((charBuf.toString())
					.getBytes()));
			System.out.println("nBytes = " + nBytes);
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String readMessage(SelectionKey key) {
		int nBytes = 0;
		socket = (SocketChannel) key.channel();
		ByteBuffer buf = ByteBuffer.allocate(1024);
		try {
			nBytes = socket.read(buf);
			buf.flip();
			Charset charset = Charset.forName("us-ascii");
			CharsetDecoder decoder = charset.newDecoder();
			CharBuffer charBuffer = decoder.decode(buf);
			result = charBuffer.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 处理订单
	 * 
	 * @param socket
	 * @param orderNo
	 */
	public void processMessage(SocketChannel socket, String orderNo) {
		//selectDataService = (SelectDataService) ContextHolder.getBean("selectDataService");
		/**
		 * 获取开启短信通知的门店SID SELECT shopInfoSid FROM shop_alarm sa WHERE
		 * sa.isAlarm=1
		 */
		String sql = "SELECT shopInfoSid, defaultUserName, defaultUserPhone FROM shop_alarm sa WHERE HOUR(SYSDATE())<" + alarmFinalEndHour + " AND SYSDATE() <= sa.endTime AND sa.isAlarm=1";
		List<ListOrderedMap> list = selectDataService.getData(sql);
		StringBuffer sb = new StringBuffer(0);
		Map<String, String> defaultUserMap = new HashMap();
		for (ListOrderedMap map : list) {
			sb.append(map.get("shopInfoSid").toString()).append(",");
			if (map.get("defaultUserPhone") != null && StringUtils.isNotEmpty(map.get("defaultUserPhone").toString())) {//未维护手机号码不发送
				defaultUserMap.put(map.get("shopInfoSid").toString(),
						(map.get("defaultUserName")!=null && StringUtils.isNotEmpty(map.get("defaultUserName").toString()) ? map.get("defaultUserName").toString() : "未维护姓名") 
						+ "###"
						+ map.get("defaultUserPhone").toString());
			}
		}
		if (sb.length() <= 0) {
			return;
		} else {
			if (sb.lastIndexOf(",") == sb.length() - 1) {
				sb = new StringBuffer(sb.substring(0, sb.length() - 1));
			}
		}

		/**
		 * 查询单品数据：通过开通短信提醒的门店号和单品订单号获取 SELECT
		 * s.SALE_CODE_SID,s.SALE_CODE,s.SHOP_SID
		 * ,s.SALE_NUM_SUM,s.SALE_MONEY_SUM FROM SALES s INNER JOIN ORDERS o
		 * ON(s.ORDERS_SID=o.SID) WHERE o.ORDER_NO=? AND s.SALE_TYPE=1 AND
		 * s.SHOP_SID in (?)
		 * 
		 */
		String querySales = "SELECT s.SALE_CODE_SID,s.SALE_CODE,s.SHOP_SID,s.SALE_NUM_SUM,s.SALE_MONEY_SUM, pl.PRODUCT_NAME, " 
				+ " sd.SALE_PRICE, sd.SALE_SUM, s.SALE_NO"
				+ " FROM SALES s "
				+ "	INNER JOIN SALES_DETAIL sd ON(sd.SALES_SID=s.SID) "
				+ "	INNER JOIN ORDERS o ON(s.ORDERS_SID=o.SID) "
				+ " INNER JOIN PRO_DETAIL pd ON(sd.PRO_DETAIL_SID=pd.SID) "
				+ "	INNER JOIN PRODUCT_LIST pl ON(pd.PRODUCT_SID=pl.SID) "
				+ "WHERE s.SHOP_SID in("
				+ sb.toString() + ")" 
				+ " AND  o.ORDER_NO='"
				+ orderNo
				+ "'";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String, List> map = new HashMap<String, List>();
		conn = JDBCUtil.getConnection(url, username, pwd);
		StringBuffer saleCodeSidSb = new StringBuffer(0);
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querySales);
			Map<String, String> dataMap = null;

			/**
			 * 组织数据 MAP<String,list> key=saleCodeSid list为数据
			 */
			while (rs.next()) {
				dataMap = new HashMap();
				String saleCodeSid = rs.getString(1);//销售编码ID
				String saleCode = rs.getString(2);//销售编码
				String shopSid = rs.getString(3);//门店ID
				String saleNumSum = rs.getString(4);//销售单总数量
				String saleMoneySum = rs.getString(5);//销售单总金额
				String productName = rs.getString(6);//商品名称
				String salePrice = rs.getString(7);//单价
				String saleSum = rs.getString(8);//售出数量
				String saleNo = rs.getString(9);//销售单号
				dataMap.put("saleCodeSid", saleCodeSid);

				saleCodeSidSb.append(saleCodeSid).append(",");

				dataMap.put("saleCode", saleCode);
				dataMap.put("shopSid", shopSid);
				dataMap.put("saleNumSum", saleNumSum);
				dataMap.put("saleMoneySum", saleMoneySum);
				dataMap.put("productName", productName);
				dataMap.put("salePrice", salePrice);
				dataMap.put("saleSum", saleSum);
				dataMap.put("saleNo", saleNo);
				if (map.containsKey(saleCodeSid)) {
					map.get(saleCodeSid).add(dataMap);
				} else {
					List<Map<String, String>> dataList = new ArrayList();
					dataList.add(dataMap);
					map.put(saleCodeSid, dataList);
				}
			}
			
			if(map.size()<=0){
				return;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, stmt, null);
		}

		String queryUserSql = "SELECT su.SID, su.SALE_CODE_SID, su.REAL_NAME, su.USER_PHONE FROM SYS_USER su "
				+ "WHERE su.USER_PHONE IS NOT NULL "
				+ "	AND su.SALE_CODE_SID in (SELECT s.SALE_CODE_SID"
				+ "			FROM SALES s "
				+ "				INNER JOIN ORDERS o ON(s.ORDERS_SID=o.SID) "
				+ "			WHERE s.SHOP_SID in("
				+ sb.toString()
				+ ") AND o.ORDER_NO='"
				+ orderNo
				+ "')"
				+ " AND su.SUPPLY_BIT=0";
		Map<String, List> danpinUserMap = new HashMap(0);

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(queryUserSql);
			int size = rs.getFetchSize();
			if (size <= 0) {
				return;
			}

			Map<String, String> dataMap = null;
			while (rs.next()) {
				String sid = rs.getString(1);
				String saleCodeSid = rs.getString(2);
				String realName = rs.getString(3);
				String userPhone = rs.getString(4);
				dataMap = new HashMap();
				dataMap.put("sid", sid);
				dataMap.put("saleCodeSid", saleCodeSid);
				dataMap.put("realName", realName!=null&&!realName.equals("") ? realName : "未维护姓名");
				dataMap.put("userPhone", userPhone);
				if (danpinUserMap.containsKey(saleCodeSid)) {
					danpinUserMap.get(saleCodeSid).add(dataMap);
				} else {
					List<Map<String, String>> dataList = new ArrayList();
					dataList.add(dataMap);
					danpinUserMap.put(saleCodeSid, dataList);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, stmt, null);
		}
		/**
		 * for循环根据 根据SALE_CODE查询有电话号码的导购，组装短信通知导购和默认通知人
		 */
		for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, List> entry = (Map.Entry) iter.next();

			String key = entry.getKey();// saleCodeSid，可以通过saleCodeSid找到导购信息
			Map<String, String> phoneMap = new HashMap();

			/**
			 * 取得导购的联系方式
			 */
			int i=0;
			List<Map<String, String>> dataList = danpinUserMap.get(key);
			if (dataList != null && dataList.size() > 0) {
				for(Map<String, String> tmpMap : dataList){
					phoneMap.put(tmpMap.get("userPhone"), tmpMap.get("realName"));//真实导购信息
					//phoneMap.put("18601043637", tmpMap.get("realName"));//测试
					System.out.println("---------------------------SALE_CODE_SID(" + key + ") 第" + ++i + "---------------------------");
				}
			}

			/**
			 * 取得默认接受人的电话
			 * key(saleCodeId)-->shopId
			 */
			String saleCodeSql = "SELECT SHOP_SID FROM SALE_CODE sd WHERE sd.SID=" + key;
			String value = null;
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(saleCodeSql);
				
				while (rs.next()) {
					value = defaultUserMap.get(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBCUtil.close(rs, stmt, null);
			}
			
			if (value != null && !value.equals("")) {
				String[] userPhoneArray = value.split("###");
				phoneMap.put(userPhoneArray[1], userPhoneArray[0]);//默认发送人
			}
			
			List<Map<String, String>> valueList = entry.getValue();// 对应的货品组织信息
			StringBuffer content = new StringBuffer("网络下单提示 订单[" + orderNo + "]");
			content.append(" - 销售编码(" + valueList.get(0).get("saleCode") + ") - 销售单号[");
			content.append(valueList.get(0).get("saleNo"));
			content.append("] 共售出" + valueList.get(0).get("saleNumSum") + "件商品*总金额" + valueList.get(0).get("saleMoneySum") + "元");
			content.append(" 详细信息：{");
			for(Map<String, String> tmpMap : valueList){
				/*dataMap.put("saleCode", saleCode);
				dataMap.put("shopSid", shopSid);
				dataMap.put("saleNumSum", saleNumSum);
				dataMap.put("saleMoneySum", saleMoneySum);
				dataMap.put("productName", productName);*/
				content.append("商品("  + tmpMap.get("productName") + ")*售出" + tmpMap.get("saleSum")+ "件*单价" + tmpMap.get("salePrice") + "元|");
			}
			content.append("}【上品折扣】");
			/**
			 * 发送短信
			 */
			System.out.println("**********----------------------**********");
			System.out.println(phoneMap);
			System.out.println(content.toString());
			System.out.println("**********----------------------**********");
			sendSMS(phoneMap, content.toString());
		}
		
		JDBCUtil.close(null, null, conn);

	}

	/**
	 * 检查是否有门店开启短信
	 */
	public boolean checkShopAlarm() {
		//selectDataService = (SelectDataService) ContextHolder.getBean("selectDataService");
		int count = 0;
		String sql = "SELECT COUNT(*) COUN FROM shop_alarm sa WHERE HOUR(SYSDATE())<" + alarmFinalEndHour + " AND SYSDATE() <= sa.endTime AND sa.isAlarm=1";
		List<ListOrderedMap> list = selectDataService.getData(sql);
		count = Integer.valueOf(list.get(0).get("COUN").toString());
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 发短信
	 * 
	 * @param phoneMap
	 *            [{phone,name}]
	 * @param content
	 */
	public void sendSMS(Map<String, String> phoneMap, String content) {
		for (Iterator iter = phoneMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry) iter.next();
			String phone = entry.getKey();
			URL U = null;
			try {
				String url = "http://219.238.160.81/interface/limitnew.asp?username=spsy&password=123456&message=" + URLEncoder.encode(content, "GBK") + "&phone=" + phone + "&epid=6181&linkid=";
				U = new URL(url);
				URLConnection connection = U.openConnection();
				connection.connect();
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while((line = in.readLine()) != null) {
					//System.out.println(line);
				}
				in.close();
			} catch(Exception e) {
				try {
					Thread.sleep(500);
					String url = "http://219.238.160.81/interface/limitnew.asp?username=spsy&password=123456&message=" + URLEncoder.encode(content, "GBK") + "&phone=" + phone + "&epid=6181&linkid=";
					U = new URL(url);
					URLConnection connection = U.openConnection();
					connection.connect();
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String line;
					while((line = in.readLine()) != null) {
						//System.out.println(line);
					}
					in.close();
				} catch(Exception e1) {
					//this.logger.error("向手机号为【" + phone + "】的供应商发送短信失败！");
					System.out.println(e1.getMessage());
				}
			}
		}
	}
	
	/**
	 * Spring默认初始化调用方法
	 */
	public void init(){
		
		/**
		 * 启动SocketServer线程
		 */
		new Thread(new Runnable(){
			
			public void run() {
				try {
					startServer();
					System.out.println("the nonBlocking server is started!");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("the nonBlocking server started failure!");
					System.exit(-1);
				}
			}			
		}, "shopAlarmThread").start();
		
		/**
		 * 启动SocketServer监控线程，每5分钟检查一次
		 */
//		new Thread(new Runnable(){
//			
//			@Override
//			public void run() {
//				/**
//				 * 首先休眠5分钟等待SocketServer启动完毕
//				 */
//				try {
//					Thread.sleep(1000*60*5);//每5分钟查看1000*60*5
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				
//				/**
//				 * 监控shopAlarmThread是否宕机
//				 */
//				while(true){
//					if (condition) {//通过SocketClient每五分钟链接一下Server端
//						try {
//							startServer();
//							System.out
//									.println("the nonBlocking server is started!");
//						} catch (IOException e) {
//							e.printStackTrace();
//							System.out
//									.println("the nonBlocking server started failure!");
//							System.exit(-1);
//						}
//					}
//					try {
//						Thread.sleep(1000*60*5);//每5分钟查看1000*60*5
//					} catch (InterruptedException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
//				
//			}			
//		}, "shopAlarmThreadObserver").start();
		
		
	}

	public static void main(String args[]) {
		NonBlockingServer nb;
		if (args.length < 1) {
			nb = new NonBlockingServer();
		} else {
			int port = Integer.parseInt(args[0]);
			nb = new NonBlockingServer(port);
		}

		try {
			nb.startServer();
			System.out.println("the nonBlocking server is started!");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}
}
