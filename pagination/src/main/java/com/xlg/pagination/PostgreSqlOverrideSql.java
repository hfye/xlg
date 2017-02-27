package com.xlg.pagination;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PostgreSqlOverrideSql implements IOverrideSql {
	private static final Log LOG = LogFactory.getLog(PostgreSqlOverrideSql.class);
	private String limitQuery = "LIMIT %1$d OFFSET %2$d";
	
	@Override
	public String overrideSql(String sql) {
		PagingConfig paging = PagingConfig.getPagingConfig();
		if (LOG.isDebugEnabled()) {
			LOG.debug("Paging config: " + paging);
		}
		String overrideSql = new String(sql);
		if (paging != null && paging.toPaginate()) {
			overrideSql += " " + String.format(limitQuery, paging.getPageSize(), paging.getBeginIndex());
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("Override Sql: " + overrideSql);
		}
		return overrideSql;
	}

	@Override
	public ResultSet scrollResultSet(ResultSet rs) throws SQLException {
		throw new UnsupportedOperationException();
	}
}
