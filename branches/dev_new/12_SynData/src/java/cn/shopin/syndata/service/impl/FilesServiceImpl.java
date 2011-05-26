/**
 * @Probject Name: 12_SynData
 * @Path: cn.shopin.syndata.serviceFilesService.java
 * @Create By Jack
 * @Create In 2011-5-24 03:21:15
 * TODO
 */
package cn.shopin.syndata.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.shopin.syndata.service.FilesService;

/**
 * @Class Name FilesService
 * @Author Jack
 * @Create In 2011-5-24
 */
public class FilesServiceImpl implements FilesService{

	public List<String> readFolderByFile(String filePath) {
		File file = new File(filePath);
		File[] tempFile = file.listFiles();
		List<String> fileList = new ArrayList<String>();
		for (File item : tempFile) {
			if (item.isFile()) {
				System.out.println("File : " + item.getName());
				fileList.add(filePath + "/" + item.getName());
			}
			if (item.isDirectory()) {
				System.out.println("Directory : " + item.getName());
			}
		}
		
		return fileList;
	}
}
