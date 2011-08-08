import com.xpsoft.framework.util.HttpUtil;


public class Test {

	/**
	 * @Methods Name main
	 * @Create In Aug 5, 2011 By Administrator
	 * @param args void
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String aString ="<img alt=\"\" " +
				"align=\"middle\" " +
				"src=\"http://photocdn.sohu.com/20110804/Img315478682.jpg\"" +
				" style=\"padding-bottom: 0px; border-right-width: 0px; margin: 0px; padding-left: 0px; padding-right: 0px; border-top-width: 0px; border-bottom-width: 0px; border-left-width: 0px; padding-top: 0px\" />" +
				"中文啊</td></tr>" +
				"<tr>啊啊1<a>12ab</>";
		System.out.println(HttpUtil.clearHtml(aString));
	}

}
