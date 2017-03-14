package com.xlg.pagination;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A interface to defined the api needed to be implemented to override the sql for
 * optimise pagination query.
 * @author wernergiam
 *
 */
public interface IOverrideSql {
	/**
	 * 
	 * @param sql	the sql to intercept and override with pagination component
	 * @return		return a sql optimise with pagination query for specific db
	 */
	public abstract String overrideSql(String sql);

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	@Deprecated
	public ResultSet scrollResultSet(ResultSet rs) throws SQLException;
}
