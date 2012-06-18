import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Test1 {
	
	private static final String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
	//private static final String regEx_sql = "select|update|delete|insert|exec|count|drop|truncate|'|\\(|\\)|net|char|from|delete%20from%20|select%20|update%20truncate%20|drop%20table%20|%20and%20|%20or%20|\"|net%20user|<|>|\\*";
	//private static final String regEx_sql = "select|update|delete|insert|exec|count|drop|truncate|'|\\(|\\)|net|char\\(|from|delete%20from%20|select%20|update%20|truncate%20|drop%20table%20|%20and%20|%20or%20|\"|net%20user|<|>";
	private static final String regEx_sql = "select[\\s]+|update[\\s]+|delete[\\s]+|insert[\\s]+|[\\s]+or[\\s]+|[\\s]+and[\\s]+|exec|drop|net[\\s]*user|declare|cast[\\s]*\\(|truncate|'|\\(|\\)|char\\(|delete%20from%20|select%20|update%20|truncate%20|drop%20table%20|%20and%20|%20or%20|\"|net%20user|<";

	/**
	 * @Methods Name main
	 * @Create In Jan 4, 2012 By Administrator
	 * @param args void
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "%E5%9F%BA%E9%87%91%E8%A1%8C%E4%B8%9A%E8%B5%84%E4%BA%A7%E5%87%80%E5%80%BC%E5%92%8C%E4%BB%BD%E9%A2%9D%E8%A7%84%E6%A8%A1%E5%88%86%E7%B1%BB%E6%B1%87%E6%80%BB%E8%A1%A8(%E8%A1%8C%E4%B8%9A%E8%A7%84%E6%A8%A1%E6%A6%9C%E5%8D%95)(%E7%BC%BAQDII%E5%9F%BA%E9%87%91)20111231-1.xls";
		try {
			System.out.println(URLDecoder.decode(s, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static boolean validateUrl(String url)
	{
		boolean flag = false;		

		Pattern validatePattern = null ;
		Matcher validateMatch = null ;
		
		try
		{
			validatePattern = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			validateMatch = validatePattern.matcher(url);
			flag = validateMatch.find();
			
			if(!flag)
			{
				validatePattern = Pattern.compile(regEx_sql, Pattern.CASE_INSENSITIVE);
				validateMatch = validatePattern.matcher(url);
				flag = validateMatch.find();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace() ;
		}
		
		return flag ;
	}
}
