package com.digitalchina.info.framework.orm.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.SQLWarningException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.ResultSetSupportingSqlParameter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.nativejdbc.NativeJdbcExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Jdbc模板类，目的是原有部分功能使用Jdbc开发，现有合同管理
 * 模块会使用到部门原有模块，为了调用原有模块时使用Spring管理的
 * 数据库连接，需要使用Spring对Jdbc的封装。
 * 
 * 扩展一个JdbcTemplate的目的是解除Dao和Spring Api之间的耦合，
 * 避免spring的jdbc orm直接渗透进入各个Dao代码中
 * 
 * 在各个Dao需要使用Spring对jdbc的封装时，除继承BaseDao外，另
 * 需注入此MyJdbcTemplate的实例给jdbcTemplate属性。
 * jdbcTemplate属性从BaseDao中继承而来。
 * 
 * @Class Name MyJdbcTemplate
 * @Author xiaofeng
 * @Create In 2007-10-25
 */
public class MyJdbcTemplate extends JdbcTemplate{

	public java.sql.Connection getCollection() throws SQLException{
		return getDataSource().getConnection();
	}
	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#applyStatementSettings(java.sql.Statement)
	 */
	protected void applyStatementSettings(Statement arg0) throws SQLException {
		
		super.applyStatementSettings(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#batchUpdate(java.lang.String, org.springframework.jdbc.core.BatchPreparedStatementSetter)
	 */
	public int[] batchUpdate(String arg0, BatchPreparedStatementSetter arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.batchUpdate(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#batchUpdate(java.lang.String[])
	 */
	public int[] batchUpdate(String[] arg0) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.batchUpdate(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#call(org.springframework.jdbc.core.CallableStatementCreator, java.util.List)
	 */
	public Map call(CallableStatementCreator arg0, List arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.call(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#createConnectionProxy(java.sql.Connection)
	 */
	protected Connection createConnectionProxy(Connection arg0) {
		// TODO Auto-generated method stub
		return super.createConnectionProxy(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#execute(org.springframework.jdbc.core.CallableStatementCreator, org.springframework.jdbc.core.CallableStatementCallback)
	 */
	public Object execute(CallableStatementCreator arg0, CallableStatementCallback arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.execute(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#execute(org.springframework.jdbc.core.ConnectionCallback)
	 */
	public Object execute(ConnectionCallback arg0) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.execute(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#execute(org.springframework.jdbc.core.PreparedStatementCreator, org.springframework.jdbc.core.PreparedStatementCallback)
	 */
	public Object execute(PreparedStatementCreator arg0, PreparedStatementCallback arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.execute(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#execute(org.springframework.jdbc.core.StatementCallback)
	 */
	public Object execute(StatementCallback arg0) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.execute(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#execute(java.lang.String, org.springframework.jdbc.core.CallableStatementCallback)
	 */
	public Object execute(String arg0, CallableStatementCallback arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.execute(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#execute(java.lang.String, org.springframework.jdbc.core.PreparedStatementCallback)
	 */
	public Object execute(String arg0, PreparedStatementCallback arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.execute(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#execute(java.lang.String)
	 */
	public void execute(String arg0) throws DataAccessException {
		// TODO Auto-generated method stub
		super.execute(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#extractOutputParameters(java.sql.CallableStatement, java.util.List)
	 */
	protected Map extractOutputParameters(CallableStatement arg0, List arg1) throws SQLException {
		// TODO Auto-generated method stub
		return super.extractOutputParameters(arg0, arg1);
	}

	

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#getColumnMapRowMapper()
	 */
	protected RowMapper getColumnMapRowMapper() {
		// TODO Auto-generated method stub
		return super.getColumnMapRowMapper();
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#getFetchSize()
	 */
	public int getFetchSize() {
		// TODO Auto-generated method stub
		return super.getFetchSize();
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#getMaxRows()
	 */
	public int getMaxRows() {
		// TODO Auto-generated method stub
		return super.getMaxRows();
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#getNativeJdbcExtractor()
	 */
	public NativeJdbcExtractor getNativeJdbcExtractor() {
		// TODO Auto-generated method stub
		return super.getNativeJdbcExtractor();
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#getQueryTimeout()
	 */
	public int getQueryTimeout() {
		// TODO Auto-generated method stub
		return super.getQueryTimeout();
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#getSingleColumnRowMapper(java.lang.Class)
	 */
	protected RowMapper getSingleColumnRowMapper(Class arg0) {
		// TODO Auto-generated method stub
		return super.getSingleColumnRowMapper(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#handleWarnings(java.sql.SQLWarning)
	 */
	protected void handleWarnings(SQLWarning arg0) throws SQLWarningException {
		// TODO Auto-generated method stub
		super.handleWarnings(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#isIgnoreWarnings()
	 */
	public boolean isIgnoreWarnings() {
		// TODO Auto-generated method stub
		return super.isIgnoreWarnings();
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#isSkipResultsProcessing()
	 */
	public boolean isSkipResultsProcessing() {
		// TODO Auto-generated method stub
		return super.isSkipResultsProcessing();
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#processResultSet(java.sql.ResultSet, org.springframework.jdbc.core.ResultSetSupportingSqlParameter)
	 */
	protected Map processResultSet(ResultSet arg0, ResultSetSupportingSqlParameter arg1) throws SQLException {
		// TODO Auto-generated method stub
		return super.processResultSet(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#query(org.springframework.jdbc.core.PreparedStatementCreator, org.springframework.jdbc.core.PreparedStatementSetter, org.springframework.jdbc.core.ResultSetExtractor)
	 */
	public Object query(PreparedStatementCreator arg0, PreparedStatementSetter arg1, ResultSetExtractor arg2) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.query(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#query(org.springframework.jdbc.core.PreparedStatementCreator, org.springframework.jdbc.core.ResultSetExtractor)
	 */
	public Object query(PreparedStatementCreator arg0, ResultSetExtractor arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.query(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#query(org.springframework.jdbc.core.PreparedStatementCreator, org.springframework.jdbc.core.RowCallbackHandler)
	 */
	public void query(PreparedStatementCreator arg0, RowCallbackHandler arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		super.query(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#query(org.springframework.jdbc.core.PreparedStatementCreator, org.springframework.jdbc.core.RowMapper)
	 */
	public List query(PreparedStatementCreator arg0, RowMapper arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.query(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#query(java.lang.String, java.lang.Object[], int[], org.springframework.jdbc.core.ResultSetExtractor)
	 */
	public Object query(String arg0, Object[] arg1, int[] arg2, ResultSetExtractor arg3) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.query(arg0, arg1, arg2, arg3);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#query(java.lang.String, java.lang.Object[], int[], org.springframework.jdbc.core.RowCallbackHandler)
	 */
	public void query(String arg0, Object[] arg1, int[] arg2, RowCallbackHandler arg3) throws DataAccessException {
		// TODO Auto-generated method stub
		super.query(arg0, arg1, arg2, arg3);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#query(java.lang.String, java.lang.Object[], int[], org.springframework.jdbc.core.RowMapper)
	 */
	public List query(String arg0, Object[] arg1, int[] arg2, RowMapper arg3) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.query(arg0, arg1, arg2, arg3);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#query(java.lang.String, java.lang.Object[], org.springframework.jdbc.core.ResultSetExtractor)
	 */
	public Object query(String arg0, Object[] arg1, ResultSetExtractor arg2) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.query(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#query(java.lang.String, java.lang.Object[], org.springframework.jdbc.core.RowCallbackHandler)
	 */
	public void query(String arg0, Object[] arg1, RowCallbackHandler arg2) throws DataAccessException {
		// TODO Auto-generated method stub
		super.query(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#query(java.lang.String, java.lang.Object[], org.springframework.jdbc.core.RowMapper)
	 */
	public List query(String arg0, Object[] arg1, RowMapper arg2) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.query(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#query(java.lang.String, org.springframework.jdbc.core.PreparedStatementSetter, org.springframework.jdbc.core.ResultSetExtractor)
	 */
	public Object query(String arg0, PreparedStatementSetter arg1, ResultSetExtractor arg2) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.query(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#query(java.lang.String, org.springframework.jdbc.core.PreparedStatementSetter, org.springframework.jdbc.core.RowCallbackHandler)
	 */
	public void query(String arg0, PreparedStatementSetter arg1, RowCallbackHandler arg2) throws DataAccessException {
		// TODO Auto-generated method stub
		super.query(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#query(java.lang.String, org.springframework.jdbc.core.PreparedStatementSetter, org.springframework.jdbc.core.RowMapper)
	 */
	public List query(String arg0, PreparedStatementSetter arg1, RowMapper arg2) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.query(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#query(java.lang.String, org.springframework.jdbc.core.ResultSetExtractor)
	 */
	public Object query(String arg0, ResultSetExtractor arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.query(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#query(java.lang.String, org.springframework.jdbc.core.RowCallbackHandler)
	 */
	public void query(String arg0, RowCallbackHandler arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		super.query(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#query(java.lang.String, org.springframework.jdbc.core.RowMapper)
	 */
	public List query(String arg0, RowMapper arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.query(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForInt(java.lang.String, java.lang.Object[], int[])
	 */
	public int queryForInt(String arg0, Object[] arg1, int[] arg2) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForInt(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForInt(java.lang.String, java.lang.Object[])
	 */
	public int queryForInt(String arg0, Object[] arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForInt(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForInt(java.lang.String)
	 */
	public int queryForInt(String arg0) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForInt(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForList(java.lang.String, java.lang.Class)
	 */
	public List queryForList(String arg0, Class arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForList(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForList(java.lang.String, java.lang.Object[], java.lang.Class)
	 */
	public List queryForList(String arg0, Object[] arg1, Class arg2) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForList(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForList(java.lang.String, java.lang.Object[], int[], java.lang.Class)
	 */
	public List queryForList(String arg0, Object[] arg1, int[] arg2, Class arg3) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForList(arg0, arg1, arg2, arg3);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForList(java.lang.String, java.lang.Object[], int[])
	 */
	public List queryForList(String arg0, Object[] arg1, int[] arg2) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForList(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForList(java.lang.String, java.lang.Object[])
	 */
	public List queryForList(String arg0, Object[] arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForList(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForList(java.lang.String)
	 */
	public List queryForList(String arg0) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForList(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForLong(java.lang.String, java.lang.Object[], int[])
	 */
	public long queryForLong(String arg0, Object[] arg1, int[] arg2) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForLong(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForLong(java.lang.String, java.lang.Object[])
	 */
	public long queryForLong(String arg0, Object[] arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForLong(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForLong(java.lang.String)
	 */
	public long queryForLong(String arg0) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForLong(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForMap(java.lang.String, java.lang.Object[], int[])
	 */
	public Map queryForMap(String arg0, Object[] arg1, int[] arg2) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForMap(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForMap(java.lang.String, java.lang.Object[])
	 */
	public Map queryForMap(String arg0, Object[] arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForMap(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForMap(java.lang.String)
	 */
	public Map queryForMap(String arg0) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForMap(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForObject(java.lang.String, java.lang.Class)
	 */
	public Object queryForObject(String arg0, Class arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForObject(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForObject(java.lang.String, java.lang.Object[], java.lang.Class)
	 */
	public Object queryForObject(String arg0, Object[] arg1, Class arg2) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForObject(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForObject(java.lang.String, java.lang.Object[], int[], java.lang.Class)
	 */
	public Object queryForObject(String arg0, Object[] arg1, int[] arg2, Class arg3) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForObject(arg0, arg1, arg2, arg3);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForObject(java.lang.String, java.lang.Object[], int[], org.springframework.jdbc.core.RowMapper)
	 */
	public Object queryForObject(String arg0, Object[] arg1, int[] arg2, RowMapper arg3) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForObject(arg0, arg1, arg2, arg3);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForObject(java.lang.String, java.lang.Object[], org.springframework.jdbc.core.RowMapper)
	 */
	public Object queryForObject(String arg0, Object[] arg1, RowMapper arg2) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForObject(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForObject(java.lang.String, org.springframework.jdbc.core.RowMapper)
	 */
	public Object queryForObject(String arg0, RowMapper arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForObject(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForRowSet(java.lang.String, java.lang.Object[], int[])
	 */
	public SqlRowSet queryForRowSet(String arg0, Object[] arg1, int[] arg2) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForRowSet(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForRowSet(java.lang.String, java.lang.Object[])
	 */
	public SqlRowSet queryForRowSet(String arg0, Object[] arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForRowSet(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#queryForRowSet(java.lang.String)
	 */
	public SqlRowSet queryForRowSet(String arg0) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.queryForRowSet(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#setFetchSize(int)
	 */
	public void setFetchSize(int arg0) {
		// TODO Auto-generated method stub
		super.setFetchSize(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#setIgnoreWarnings(boolean)
	 */
	public void setIgnoreWarnings(boolean arg0) {
		// TODO Auto-generated method stub
		super.setIgnoreWarnings(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#setMaxRows(int)
	 */
	public void setMaxRows(int arg0) {
		// TODO Auto-generated method stub
		super.setMaxRows(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#setNativeJdbcExtractor(org.springframework.jdbc.support.nativejdbc.NativeJdbcExtractor)
	 */
	public void setNativeJdbcExtractor(NativeJdbcExtractor arg0) {
		// TODO Auto-generated method stub
		super.setNativeJdbcExtractor(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#setQueryTimeout(int)
	 */
	public void setQueryTimeout(int arg0) {
		// TODO Auto-generated method stub
		super.setQueryTimeout(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#setSkipResultsProcessing(boolean)
	 */
	public void setSkipResultsProcessing(boolean arg0) {
		// TODO Auto-generated method stub
		super.setSkipResultsProcessing(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#update(org.springframework.jdbc.core.PreparedStatementCreator, org.springframework.jdbc.support.KeyHolder)
	 */
	public int update(PreparedStatementCreator arg0, KeyHolder arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.update(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#update(org.springframework.jdbc.core.PreparedStatementCreator, org.springframework.jdbc.core.PreparedStatementSetter)
	 */
	protected int update(PreparedStatementCreator arg0, PreparedStatementSetter arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.update(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#update(org.springframework.jdbc.core.PreparedStatementCreator)
	 */
	public int update(PreparedStatementCreator arg0) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.update(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#update(java.lang.String, java.lang.Object[], int[])
	 */
	public int update(String arg0, Object[] arg1, int[] arg2) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.update(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#update(java.lang.String, java.lang.Object[])
	 */
	public int update(String arg0, Object[] arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.update(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#update(java.lang.String, org.springframework.jdbc.core.PreparedStatementSetter)
	 */
	public int update(String arg0, PreparedStatementSetter arg1) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.update(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.JdbcTemplate#update(java.lang.String)
	 */
	public int update(String arg0) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.update(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.support.JdbcAccessor#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		// TODO Auto-generated method stub
		super.afterPropertiesSet();
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.support.JdbcAccessor#getDataSource()
	 */
	public DataSource getDataSource() {
		// TODO Auto-generated method stub
		return super.getDataSource();
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.support.JdbcAccessor#getExceptionTranslator()
	 */
	public synchronized SQLExceptionTranslator getExceptionTranslator() {
		// TODO Auto-generated method stub
		return super.getExceptionTranslator();
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.support.JdbcAccessor#isLazyInit()
	 */
	public boolean isLazyInit() {
		// TODO Auto-generated method stub
		return super.isLazyInit();
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.support.JdbcAccessor#setDataSource(javax.sql.DataSource)
	 */
	public void setDataSource(DataSource arg0) {
		// TODO Auto-generated method stub
		super.setDataSource(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.support.JdbcAccessor#setDatabaseProductName(java.lang.String)
	 */
	public void setDatabaseProductName(String arg0) {
		// TODO Auto-generated method stub
		super.setDatabaseProductName(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.support.JdbcAccessor#setExceptionTranslator(org.springframework.jdbc.support.SQLExceptionTranslator)
	 */
	public void setExceptionTranslator(SQLExceptionTranslator arg0) {
		// TODO Auto-generated method stub
		super.setExceptionTranslator(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.support.JdbcAccessor#setLazyInit(boolean)
	 */
	public void setLazyInit(boolean arg0) {
		// TODO Auto-generated method stub
		super.setLazyInit(arg0);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
