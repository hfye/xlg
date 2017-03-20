package com.xlg.pagination;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.GenericFilterBean;

/**
 * This filter provides a convenient way to intercept the URL and inject the page index
 * the user is requesting into the framework, i.e.&nbsp;to propagate the page index in
 * the URL via query parameter into {@link java.lang.ThreadLocal}.
 *  
 * @author wernergiam
 *
 */
public class PaginationFilter extends GenericFilterBean {
	private static final Log LOG = LogFactory.getLog(PaginationFilter.class);
	
	/**
	 * The default parameter name to look for the page index.
	 */
	private static final String DEFAULT_PAGE_INDEX_PARAM = "page";
	
	/**
	 * A list of comma separated parameter name to look for as the page index. This is to assist
	 * existing application that uses different parameter
	 * name as the page index.
	 */
	private String pageIndexParams = "";
	private List<String> finalPageIndexParams = new LinkedList<String>(Arrays.asList(DEFAULT_PAGE_INDEX_PARAM));
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		try {
			for (String pageIndexParams : finalPageIndexParams) {
				String param = req.getParameter(pageIndexParams);
				
				if (StringUtils.isNotBlank(param)) {
					Integer index = Integer.parseInt(param);
					
					PagingConfig config = new PagingConfig();
					config.setPageIndex(index);
					
					PagingConfig.setPagingConfig(config);
					break;
				}
			}
			
			chain.doFilter(req, res);
		} catch (Exception e) {
			LOG.error("Error filtering pagination request. URI:" + req.getRequestURI(), e);
		} finally {
			PagingConfig.setPagingConfig(null);
		}
	}

	public String getPageIndexParams() {
		return pageIndexParams;
	}

	public void setPageIndexParams(String pageIndexParams) {
		this.pageIndexParams = pageIndexParams;
		if (StringUtils.isNotBlank(pageIndexParams)) {
			String[] entries = StringUtils.split(",");
			if (entries.length > 0) {
				this.finalPageIndexParams.addAll(Arrays.asList(entries));
			}
		}
	}
}
