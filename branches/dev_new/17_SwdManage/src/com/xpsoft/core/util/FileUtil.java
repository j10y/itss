/*     */package com.xpsoft.core.util;

/*     */
/*     */import java.io.BufferedReader;
/*     */
import java.io.File;
/*     */
import java.io.FileInputStream;
/*     */
import java.io.FileNotFoundException;
/*     */
import java.io.FileOutputStream;
/*     */
import java.io.IOException;
/*     */
import java.io.InputStreamReader;
/*     */
import java.io.OutputStreamWriter;
/*     */
import java.text.SimpleDateFormat;
/*     */
import java.util.Date;
/*     */
import org.apache.commons.logging.Log;
/*     */
import org.apache.commons.logging.LogFactory;

/*     */
/*     */public class FileUtil
/*     */{
	/* 22 */private static Log logger = LogFactory.getLog(FileUtil.class);

	/*     */
	/*     */public static String generateFilename(String originalFilename)
	/*     */{
		/* 26 */SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMM");
		/* 27 */String filePre = dirSdf.format(new Date());
		/*     */
		/* 29 */String fileExt = "";
		/* 30 */int lastIndex = originalFilename.lastIndexOf('.');
		/*     */
		/* 32 */if (lastIndex != -1) {
			/* 33 */fileExt = originalFilename.substring(lastIndex);
			/*     */}
		/*     */
		/* 36 */String filename = filePre + "/" + UUIDGenerator.getUUID()
				+ fileExt;
		/*     */
		/* 38 */return filename;
		/*     */}

	/*     */
	/*     */public static void writeFile(String filePath, String data)
	/*     */{
		/* 47 */FileOutputStream fos = null;
		/* 48 */OutputStreamWriter writer = null;
		/*     */try {
			/* 50 */fos = new FileOutputStream(new File(filePath));
			/* 51 */writer = new OutputStreamWriter(fos, "UTF-8");
			/* 52 */writer.write(data);
			/*     */} catch (Exception ex) {
			/* 54 */logger.error(ex.getMessage());
			/*     */try
			/*     */{
				/* 57 */if (writer != null) {
					/* 58 */writer.close();
					/*     */}
				/* 60 */if (fos != null)
					/* 61 */fos.close();
				/*     */}
			/*     */catch (Exception localException1)
			/*     */{
				/*     */}
			/*     */}
		/*     */finally
		/*     */{
			/*     */try
			/*     */{
				/* 57 */if (writer != null) {
					/* 58 */writer.close();
					/*     */}
				/* 60 */if (fos != null)
					/* 61 */fos.close();
				/*     */}
			/*     */catch (Exception localException2)
			/*     */{
				/*     */}
			/*     */}
		/*     */}

	/*     */
	/*     */public static String readFile(String filePath)
	/*     */{
		/* 74 */StringBuffer buffer = new StringBuffer();
		/*     */try
		/*     */{
			/* 77 */File file = new File(filePath);
			/* 78 */FileInputStream fis = null;
			/* 79 */BufferedReader breader = null;
			/*     */try {
				/* 81 */fis = new FileInputStream(file);
				/* 82 */InputStreamReader isReader = new InputStreamReader(fis,
						"UTF-8");
				/* 83 */breader = new BufferedReader(isReader);
				/*     */String line;
				/* 85 */while ((line = breader.readLine()) != null)
				/*     */{
					/* 86 */buffer.append(line);
					/* 87 */buffer.append("\r\n");
					/*     */}
				/* 89 */breader.close();
				/* 90 */isReader.close();
				/* 91 */fis.close();
				/*     */}
			/*     */catch (FileNotFoundException e) {
				/* 94 */logger.error(e.getMessage());
				/*     */} catch (IOException e) {
				/* 96 */logger.error(e.getMessage());
				/*     */}
			/*     */} catch (Exception e) {
			/* 99 */logger.error(e.getMessage());
			/*     */}
		/* 101 */return buffer.toString();
		/*     */}
	/*     */
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.xpsoft.core.util.FileUtil JD-Core Version: 0.6.0
 */