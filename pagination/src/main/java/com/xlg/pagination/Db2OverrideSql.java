package com.xlg.pagination;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A concrete implementation done for db2.
 * @author wernergiam
 *
 */
public class Db2OverrideSql implements IOverrideSql {
	private static final Log LOG = LogFactory.getLog(Db2OverrideSql.class);
	private String limitQuery = "FETCH FIRST %d ROWS ONLY OPTIMIZE FOR %d ROWS";
	private String totalQuery = "select count(*) as __totalCount from (%1$s) __totalSql";

	@Override
	public String overrideSql(String sql) {
		PagingConfig paging = PagingConfig.getPagingConfig();
		if (LOG.isDebugEnabled()) {
			LOG.debug("Paging config: " + paging);
		}
		String overrideSql = new String(sql);
		if (paging != null && paging.toPaginate()) {
			overrideSql += " " + String.format(limitQuery, paging.getEndIndex(), paging.getEndIndex());
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("Override Sql: " + overrideSql);
		}
		return overrideSql;
	}
	
	@Override
	public String totalSql(String sql) {
		PagingConfig paging = PagingConfig.getPagingConfig();
		if (LOG.isDebugEnabled()) {
			LOG.debug("Paging config: " + paging);
		}
		String totalSql = new String(sql);
		if (paging != null && paging.toPaginate() && paging.isIncludeTotal()) {
			totalSql = String.format(totalQuery, sql);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("Total Sql: " + totalSql);
		}
		return totalSql;
	}



	@Override
	public ResultSet scrollResultSet(ResultSet rs) throws SQLException {
		if (rs == null) {
			return rs;
		} else {
			PagingConfig paging = PagingConfig.getPagingConfig();
			if (paging != null && paging.toPaginate()) {
				if (rs.getType() != ResultSet.TYPE_FORWARD_ONLY) {
					if (paging.getSkip() > 0) {
						rs.absolute(paging.getSkip());
					}
				} else {
					for (int i = 0; i < paging.getSkip(); i++) {
						if (!rs.next()) {
							return rs;
						}
			        }
				}
			}
		}
		return rs;
	}
}
