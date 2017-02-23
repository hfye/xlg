package com.xlg.pagination;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ProxyDataSource implements DataSource, ApplicationContextAware {
	//Start of the implementation
	@SuppressWarnings("unused")
	private static final Log LOG = LogFactory.getLog(ProxyDataSource.class);
	private static final String PROXY_PAGE_CONNECTION = "proxyPageConnection";
	private DataSource dataSource;
	private ApplicationContext applicationContext;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * @return
	 * @throws SQLException
	 * @see javax.sql.DataSource#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		return getProxyConnection(null, null);
	}

	/**
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 * @see javax.sql.DataSource#getConnection(java.lang.String, java.lang.String)
	 */
	public Connection getConnection(String username, String password)
			throws SQLException {
		return getProxyConnection(username, password);
	}
	
	private Connection getProxyConnection(String username, String password) throws SQLException {
		Connection connection = null;
		ProxyConnection proxyConnection = (ProxyConnection) applicationContext.getBean(PROXY_PAGE_CONNECTION); 
		if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {
			connection = dataSource.getConnection();
		} else {
			connection = dataSource.getConnection(username, password);
		}
		proxyConnection.setConnection(connection);
		return proxyConnection;
	}
	//End of the implementation
	
	//Delegate to underlying connection instance
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout();
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return dataSource.getParentLogger();
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return dataSource.isWrapperFor(iface);
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		dataSource.setLogWriter(out);
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		dataSource.setLoginTimeout(seconds);
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return dataSource.unwrap(iface);
	}
}
