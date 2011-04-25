package com.digitalchina.info.framework.orm;

import java.sql.ResultSetMetaData;

public interface JdbcMetaDataAware {

	ResultSetMetaData getResultSetMetaData(String tableName);
}
