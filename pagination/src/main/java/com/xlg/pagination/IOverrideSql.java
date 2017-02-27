package com.xlg.pagination;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IOverrideSql {
	public abstract String overrideSql(String sql);

	public ResultSet scrollResultSet(ResultSet rs) throws SQLException;
}
