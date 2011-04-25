package com.digitalchina.info.appframework.metadata;

public class ColumnDTO{
		private Long id;
		private String columnName;
		private String columnCnName;
		public String getColumnCnName() {
			return columnCnName;
		}
		public void setColumnCnName(String columnCnName) {
			this.columnCnName = columnCnName;
		}
		public String getColumnName() {
			return columnName;
		}
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
	}