package demo;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

public class TestPy {

	/**
	 * @Methods Name main
	 * @Create In Jun 14, 2011 By Administrator
	 * @param args void
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HanyuPinyinOutputFormat hpoFormat = new HanyuPinyinOutputFormat();
		//小写
		hpoFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		//无四声音
		hpoFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		//ü用v代替
		hpoFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		String[] pinyinArray;
		try {
			pinyinArray = PinyinHelper.toHanyuPinyinStringArray('虐',hpoFormat);
			for (int i = 0; i < pinyinArray.length; i++) {
				System.out.print(pinyinArray[i]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
