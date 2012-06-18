import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.xpsoft.framework.util.HttpUtil;


public class Test {

	/**
	 * @Methods Name main
	 * @Create In Aug 5, 2011 By Administrator
	 * @param args void
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
//		Double aDouble = 1238079392730.83157d;
		String target = "loginName=admin&taskId=1&registerCode=1402324324245&results=1&count=1";
		System.out.println(URLEncoder.encode(target, "utf-8"));
	}

}
//registerCode=430703600137545&type=1&pBarcode=6921168509256&mDate=20110705
//registerCode=1402324324245&type=1&pBarcode=691028374733423&mDate=20110111&loginName=hwh