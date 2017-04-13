package com.xlg.pagination;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class PaginationContainerTest {
	private static final Log LOG = LogFactory.getLog(PaginationContainerTest.class);

	@Autowired
	TestTableController controller;
	
	@LocalServerPort
    int port;
	
	@Value("${default.page.size}")
	int pageSize;
	
    @Autowired
    TestRestTemplate restTemplate;
	
	@Test
	public void testContextLoad() {
		Assert.assertTrue(controller != null);
		Assert.assertTrue(pageSize > 0);
	}
	
	@Test
	public void testRestfulApi() {
		try {
			int page = 2;
			String resultResponse = this.restTemplate.getForObject("http://localhost:" + port + "/getTestTable?page=" + page, String.class);
			LOG.debug(String.format("resultResponse: %1s", resultResponse));
			ObjectMapper jsonMapper = new ObjectMapper();
			List<TestTableDto> results = jsonMapper.readValue(resultResponse, new TypeReference<List<TestTableDto>>(){});
			Assert.assertTrue(results.size() == pageSize);
			Assert.assertTrue(results.get(0).getTestId() == ((page - 1) * pageSize) + 1);
			Assert.assertTrue(results.get(results.size() - 1).getTestId() == (page * pageSize));
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testPaginatedResponseRestfulApi() {
		try {
			int page = 2;
			String resultResponse = this.restTemplate.getForObject("http://localhost:" + port + "/getPaginatedTestTable?page=" + page, String.class);
			LOG.debug(String.format("resultResponse: %1s", resultResponse));
			ObjectMapper jsonMapper = new ObjectMapper();
			PaginatedResponse<List<TestTableDto>> results = jsonMapper.readValue(resultResponse, new TypeReference<PaginatedResponse<List<TestTableDto>>>(){});
			Assert.assertTrue(results.getPageSize() == pageSize);
			Assert.assertTrue(results.getPageIndex() == page);
			Assert.assertTrue(results.getData().get(0).getTestId() == ((page - 1) * pageSize) + 1);
			Assert.assertTrue(results.getData().get(results.getData().size() - 1).getTestId() == (page * pageSize));
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
