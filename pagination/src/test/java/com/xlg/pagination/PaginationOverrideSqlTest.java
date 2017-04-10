package com.xlg.pagination;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A test class which show case how pagination works under the hood without the filter and aspect.
 * 
 * @author wernergiam
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ImportResource(locations={"classpath:META-INF/pagination_test.xml"})
public class PaginationOverrideSqlTest {
	private static final Log LOG = LogFactory.getLog(PaginationOverrideSqlTest.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	ITestTableDao testTableDao;
	
//	@Autowired
//	private TestRestTemplate restTemplate;
	
	@Before
	public void setUp() {
		try {
			Assert.assertTrue(dataSource != null);
			Assert.assertTrue(jdbcTemplate != null);
			int count = testTableDao.countAll();
			if (count == 0) {
				Date today = new Date();
				TestTableDto dto = new TestTableDto();
				dto.setTestDate(today);
				for (int i = 0; i < 50; i++) {
					dto.setTestId(i + 1);
					testTableDao.insert(dto);
				}
				
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			Assert.fail();
		}
	}
	
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
			
			dtos = testTableDao.selectAll();
			
			Assert.assertTrue(dtos.size() == config.getPageSize());
			Assert.assertTrue(dtos.get(0).getTestId() == (((config.getPageIndex() - 1) * config.getPageSize()) + 1));
			Assert.assertTrue(dtos.get(dtos.size() - 1).getTestId() == (((config.getPageIndex() - 1) * config.getPageSize()) + config.getPageSize()));

			config.setPageIndex(2);
			config.setPageSize(10);
			config.setPaginate(true);
			PagingConfig.setPagingConfig(config);
			
			dtos = testTableDao.selectAll();
			
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
	
//	@Test
//	public void testRestfulApi() {
//		String resultResponse = this.restTemplate.getForObject("/getTestTable", String.class);
//		LOG.debug(String.format("resultResponse: %1", resultResponse));
//	}
}
