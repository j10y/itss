package com.xpsoft.oa.service.system.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.oa.dao.system.FileAttachDao;
import com.xpsoft.oa.model.system.FileAttach;
import com.xpsoft.oa.service.system.FileAttachService;
import java.io.File;
import org.apache.commons.logging.Log;

public class FileAttachServiceImpl extends BaseServiceImpl<FileAttach>
		implements FileAttachService {
	private FileAttachDao dao;

	public FileAttachServiceImpl(FileAttachDao dao) {
		super(dao);
		this.dao = dao;
	}

	public void removeByPath(String filePath) {
		FileAttach fileAttach = this.dao.getByPath(filePath);

		//String fullFilePath = AppUtil.getAppAbsolutePath() + "/attachFiles/"
		//		+ filePath;
		
		String fullFilePath = filePath;

		this.logger.info("file:" + fullFilePath);

		File file = new File(fullFilePath);

		if (file.exists()) {
			file.delete();
		}
		if (fileAttach != null)
			this.dao.remove(fileAttach);
	}

	public FileAttach getByPath(String filePath) {
		return this.dao.getByPath(filePath);
	}
}
