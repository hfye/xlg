package com.xlg.pagination;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author wernergiam
 *
 */
public class SqlServerOverrideSql implements IOverrideSql {
	private static final Log LOG = LogFactory.getLog(SqlServerOverrideSql.class);
	private String limitQuery = ""
			+ "select * from ("
			+ "select *, row_number() over(%1$s) as rowNumber from (%2$s) numbered"
			+ ") numberedProj "
			+ "where rowNumber > %3$d and rowNumber <= %4$d";
	private static final Pattern orderByClausePattern = Pattern.compile("order\\sby.+$", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL); 

	@Override
	public String overrideSql(String sql) {
		PagingConfig paging = ProxyConnection.getPagingConfig();
		if (LOG.isDebugEnabled()) {
			LOG.debug("Paging config: " + paging);
		}
		String overrideSql = new String(sql);
		if (paging != null && paging.toPaginate()) {
			String orderByClause = orderByClause(sql);
			String stripOrderByClause = stripOrderByClause(sql, orderByClause);
			overrideSql = String.format(limitQuery, orderByClause, stripOrderByClause, paging.getBeginIndex(), paging.getEndIndex());
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("Override Sql: " + overrideSql);
		}
		return overrideSql;
	}
	
	private String orderByClause(String sql) {
		String result = "";
		Matcher matcher = orderByClausePattern.matcher(sql);
		if (matcher.find()) {
			result = matcher.group();
		}
		return result;
	}

	private String stripOrderByClause(String sql, String orderByClause) {
		String result = new String(sql);
		return result.replace(orderByClause, "");
	}
	
	@Override
	public ResultSet scrollResultSet(ResultSet rs) throws SQLException {
		throw new UnsupportedOperationException();
	}
}
