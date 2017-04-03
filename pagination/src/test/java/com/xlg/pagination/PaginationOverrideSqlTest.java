package com.xlg.pagination;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A test class which show case how pagination works under the hood without the filter and aspect.
 * 
 * @author wernergiam
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:META-INF/pagination_test.xml"})
@SpringBootTest
public class PaginationOverrideSqlTest {
	private static final Log LOG = LogFactory.getLog(PaginationOverrideSqlTest.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() {
		try {
			Assert.assertTrue(dataSource != null);
			Assert.assertTrue(jdbcTemplate != null);
			int count = jdbcTemplate.queryForObject("select count(*) from test_table", new HashMap<>(), int.class);
			if (count == 0) {
				Date today = new Date();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("testDate", today);
				for (int i = 0; i < 50; i++) {
					params.put("testId", new Integer(i + 1));
					jdbcTemplate.update("insert into test_table values(:testDate, :testId)", params);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			Assert.fail();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testPaging() {
		try {
			LOG.debug("testPaging start");
			List<TestTableDto> dtos = new LinkedList<>();
			PagingConfig config = new PagingConfig();
			config.setPageIndex(1);
			config.setPageSize(10);
			config.setPaginate(true);
			PagingConfig.setPagingConfig(config);
			
			dtos = jdbcTemplate.query("select test_date, test_id from test_table order by test_date, test_id", new RowMapper() {
	
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new TestTableDto(rs.getDate(1), rs.getInt(2));
				}
			});
			
			Assert.assertTrue(dtos.size() == config.getPageSize());
			Assert.assertTrue(dtos.get(0).getTestId() == (((config.getPageIndex() - 1) * config.getPageSize()) + 1));
			Assert.assertTrue(dtos.get(dtos.size() - 1).getTestId() == (((config.getPageIndex() - 1) * config.getPageSize()) + config.getPageSize()));

			config.setPageIndex(2);
			config.setPageSize(10);
			config.setPaginate(true);
			PagingConfig.setPagingConfig(config);
			
			dtos = jdbcTemplate.query("select test_date, test_id from test_table order by test_date, test_id", new RowMapper() {
	
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new TestTableDto(rs.getDate(1), rs.getInt(2));
				}
			});
			
			Assert.assertTrue(dtos.size() == config.getPageSize());
			Assert.assertTrue(dtos.get(0).getTestId() == (((config.getPageIndex() - 1) * config.getPageSize()) + 1));
			Assert.assertTrue(dtos.get(dtos.size() - 1).getTestId() == (((config.getPageIndex() - 1) * config.getPageSize()) + config.getPageSize()));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			Assert.fail();
		} finally {
			LOG.debug("testPaging end");
		}
	}
	
	public class TestTableDto {
		Date testDate;
		int testId;
		
		public TestTableDto(Date testDate, int testId) {
			super();
			this.testDate = testDate;
			this.testId = testId;
		}
		
		public Date getTestDate() {
			return testDate;
		}
		
		public void setTestDate(Date testDate) {
			this.testDate = testDate;
		}
		
		public int getTestId() {
			return testId;
		}
		
		public void setTestId(int testId) {
			this.testId = testId;
		}
	}
}
