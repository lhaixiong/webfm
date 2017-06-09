package com.lhx.webfm.helper;

import com.lhx.webfm.util.MyPropsUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class DBHelper {
	private static final Logger log= LoggerFactory.getLogger(DBHelper.class);
	private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL=new ThreadLocal<>();
	private static final String DRIVER;
	private static final String URL;
	private static final String USERNAME;
	private static final String PASSWORD;

	static {
		Properties conf= MyPropsUtil.loadProps("app.properties");
		DRIVER=conf.getProperty("jdbc.driver");
		URL=conf.getProperty("jdbc.url");
		USERNAME=conf.getProperty("jdbc.username");
		PASSWORD=conf.getProperty("jdbc.password");

		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			log.error("load jdbc driver error",e);
		}
	}

	/**
	 * 开启事务
	 */
	public static void beginTransation(){
		Connection conn=getConnection();
		if (conn != null) {
			try {
				conn.setAutoCommit(false);
			}catch (Exception e){
				log.error("begin transaction failure",e);
				throw new RuntimeException(e);
			}finally {
				CONNECTION_THREAD_LOCAL.set(conn);
			}
		}
	}
	/**
	 * 提交事务
	 */
	public static void commitTransation(){
		Connection conn=getConnection();
		if (conn != null) {
			try {
				conn.commit();
				conn.close();
			}catch (Exception e){
				log.error("commit transaction failure",e);
				throw new RuntimeException(e);
			}finally {
				CONNECTION_THREAD_LOCAL.remove();
			}
		}
	}
	/**
	 * 回滚事务
	 */
	/**
	 * 提交事务
	 */
	public static void rollbackTransation(){
		Connection conn=getConnection();
		if (conn != null) {
			try {
				conn.rollback();
				conn.close();
			}catch (Exception e){
				log.error("rollback transaction failure",e);
				throw new RuntimeException(e);
			}finally {
				CONNECTION_THREAD_LOCAL.remove();
			}
		}
	}

	public static Connection getConnection(){
		Connection conn=CONNECTION_THREAD_LOCAL.get();
		if (conn == null) {
			try {
				conn= DriverManager.getConnection(URL,USERNAME,PASSWORD);
			} catch (SQLException e) {
				log.error("get connection error",e);
			}finally {
				CONNECTION_THREAD_LOCAL.set(conn);
			}
		}
		log.info("获得数据库连接...");
		return conn;
	}
	public static void closeConnection(){
		Connection conn=CONNECTION_THREAD_LOCAL.get();
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("close connection error", e);
			}finally {
				CONNECTION_THREAD_LOCAL.remove();
			}
		}
		log.info("关闭数据库连接...");
	}

	public static <T> List<T> queryForColumnList(Connection conn, String sql,
			String columnName, Class<T> c) throws SQLException {
		List<T> resultList = null;
		String dbName = conn.getCatalog();
		long start = System.currentTimeMillis();
		QueryRunner qr = new QueryRunner();
		resultList = qr.query(conn, sql, new ColumnListHandler<T>(columnName));
		long end = System.currentTimeMillis();
		long r = end - start;
		if (r >= 5000L) {
			log.info("查询数据库:" + dbName + ",sql:" + trimSql(sql)
					+ ",用时:" + r + "ms(慢查询)");
		} else {
			log.info( "查询数据库:" + dbName + ",sql:" + trimSql(sql)
					+ ",用时:" + r + "ms");
		}
		return resultList;
	}

	public static Map<String, Object> queryForOneMap(Connection conn,
			String sql) throws SQLException {
		Map<String, Object> resultMap = null;
		String dbName = conn.getCatalog();
		long start = System.currentTimeMillis();
		QueryRunner qr = new QueryRunner();
		resultMap = qr.query(conn, sql, new MapHandler());
		long end = System.currentTimeMillis();
		long r = end - start;
		if (r >= 5000L) {
			log.info( "查询数据库:" + dbName + ",sql:" + trimSql(sql)
					+ ",用时:" + r + "ms(慢查询)");
		} else {
			log.info( "查询数据库:" + dbName + ",sql:" + trimSql(sql)
					+ ",用时:" + r + "ms");
		}
		return resultMap;
	}

	public static List<Map<String, Object>> queryForListMap(Connection conn,
			String sql) throws SQLException {
		List<Map<String, Object>> resultList = null;
		String dbName = conn.getCatalog();
		long start = System.currentTimeMillis();
		QueryRunner qr = new QueryRunner();
		resultList = qr.query(conn, sql, new MapListHandler());
		long end = System.currentTimeMillis();
		long r = end - start;
		if (r >= 5000L) {
			log.info( "查询数据库:" + dbName + ",sql:" + trimSql(sql)
					+ ",用时:" + r + "ms(慢查询)");
		} else {
			log.info( "查询数据库:" + dbName + ",sql:" + trimSql(sql)
					+ ",用时:" + r + "ms");
		}
		return resultList;
	}

	public static Long queryForLong(Connection conn, String sql)
			throws SQLException {
		Long result = 0L;
		String dbName = conn.getCatalog();
		long start = System.currentTimeMillis();
		QueryRunner qr = new QueryRunner();
		Object[] oArray = qr.query(conn, sql, new ArrayHandler());
		long end = System.currentTimeMillis();
		long r = end - start;
		if (r >= 5000L) {
			log.info( "查询数据库:" + dbName + ",sql:" + trimSql(sql)
					+ ",用时:" + r + "ms(慢查询)");
		} else {
			log.info( "查询数据库:" + dbName + ",sql:" + trimSql(sql)
					+ ",用时:" + r + "ms");
		}
		if (null == oArray || 0 == oArray.length) {
			return result;
		}
		if (null == oArray[0]) {
			return result;
		}
		String longStr = String.valueOf(oArray[0]);
		if (null == longStr || "null".equalsIgnoreCase(longStr)) {
			return result;
		}
		result = new Long(trimPoint(longStr));
		return result;
	}

	public static Integer queryForInteger(Connection conn, String sql) throws SQLException {
		Integer result = 0;
		String dbName = conn.getCatalog();
		long start = System.currentTimeMillis();
		QueryRunner qr = new QueryRunner();
		Object[] oArray = qr.query(conn, sql, new ArrayHandler());
		long end = System.currentTimeMillis();
		long r = end - start;
		if (r >= 5000L) {
			log.info( "查询数据库:" + dbName + ",sql:" + trimSql(sql)
					+ ",用时:" + r + "ms(慢查询)");
		} else {
			log.info( "查询数据库:" + dbName + ",sql:" + trimSql(sql)
					+ ",用时:" + r + "ms");
		}
		if (null == oArray || 0 == oArray.length) {
			return result;
		}
		if (null == oArray[0]) {
			return result;
		}
		String IntegerStr = String.valueOf(oArray[0]);
		if (null == IntegerStr || "null".equalsIgnoreCase(IntegerStr)) {
			return result;
		}
		result = new Integer(trimPoint(IntegerStr));
		return result;
	}

	public static Float queryForFloat(Connection conn, String sql) throws SQLException {
		Float result = 0F;
		String dbName = conn.getCatalog();
		long start = System.currentTimeMillis();
		QueryRunner qr = new QueryRunner();
		Object[] oArray = qr.query(conn, sql, new ArrayHandler());
		long end = System.currentTimeMillis();
		long r = end - start;
		if (r >= 5000L) {
			log.info("查询数据库:" + dbName + ",sql:" + trimSql(sql)
					+ ",用时:" + r + "ms(慢查询)");
		} else {
			log.info( "查询数据库:" + dbName + ",sql:" + trimSql(sql)
					+ ",用时:" + r + "ms");
		}
		if (null == oArray || 0 == oArray.length) {
			return result;
		}
		if (null == oArray[0]) {
			return result;
		}
		String FloatStr = String.valueOf(oArray[0]);
		if (null == FloatStr || "null".equalsIgnoreCase(FloatStr)) {
			return result;
		}
		result = new Float(FloatStr);
		return result;
	}

	public static Double queryForDouble(Connection conn, String sql) throws SQLException {
		Double result = 0D;
		String dbName = conn.getCatalog();
		long start = System.currentTimeMillis();
		QueryRunner qr = new QueryRunner();
		Object[] oArray = qr.query(conn, sql, new ArrayHandler());
		long end = System.currentTimeMillis();
		long r = end - start;
		if (r >= 5000L) {
			log.info("查询数据库:" + dbName + ",sql:" + trimSql(sql)
					+ ",用时:" + r + "ms(慢查询)");
		} else {
			log.info("查询数据库:" + dbName + ",sql:" + trimSql(sql)
					+ ",用时:" + r + "ms");
		}
		if (null == oArray || 0 == oArray.length) {
			return result;
		}
		if (null == oArray[0]) {
			return result;
		}
		String doubleStr = String.valueOf(oArray[0]);
		if (null == doubleStr || "null".equalsIgnoreCase(doubleStr)) {
			return result;
		}
		result = new Double(doubleStr);
		return result;
	}

	private static String trimPoint(String str) {
		if (str.indexOf(".") != -1) {
			str = str.substring(0, str.indexOf("."));
		}
		return str;
	}

	private static String trimSql(String sql) {
		if (null != sql && sql.length() > 500) {
			sql = sql.substring(0, 500) + ".....";
		}
		return sql;
	}
}
