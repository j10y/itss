package com.digitalchina.info.framework.util;

import java.io.File;
import java.io.FileWriter;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;

public class Dom4jUtil {
	public int formatXMLFile(SystemMainTable smt, String filename) {
		int returnValue = 0;
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File(filename));
			XMLWriter output = null;
			/** 格式化输出,类型IE浏览一样 */
			OutputFormat format = OutputFormat.createPrettyPrint();
			/** 指定XML字符集编码 */
			format.setEncoding("GBK");
			output = new XMLWriter(new FileWriter(new File(filename)), format);
			output.write(document);
			output.close();
			/** 执行成功,需返回1 */
			returnValue = 1;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;

	}
}
