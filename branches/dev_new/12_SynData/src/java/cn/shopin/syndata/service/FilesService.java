/**
 * @Probject Name: 12_SynData
 * @Path: cn.shopin.syndata.serviceFilesService.java
 * @Create By Jack
 * @Create In 2011-5-24 обнГ03:45:33
 * TODO
 */
package cn.shopin.syndata.service;

import java.util.List;

/**
 * @Class Name FilesService
 * @Author Jack
 * @Create In 2011-5-24
 */
public interface FilesService {

	public List<String> readFolderByFile(String filePath);
}
