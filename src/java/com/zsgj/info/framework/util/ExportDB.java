package com.zsgj.info.framework.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class ExportDB {

	public static void main(String[] args) {
		
		//��ȡ�����ļ�
		Configuration cfg = new Configuration().configure();
		
		//����SchemaExport����
		SchemaExport export = new SchemaExport(cfg);
		
		//�������ݿ��
		export.create(true,true);
	}

}
