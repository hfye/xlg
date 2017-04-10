package com.xlg.pagination;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class PaginationContainerTest {
	private static final Log LOG = LogFactory.getLog(PaginationContainerTest.class);

	@Autowired
	TestTableController controller;
	
	@LocalServerPort
    int port;
	
    @Autowired
    TestRestTemplate restTemplate;
	
	@Test
	public void testContextLoad() {
		Assert.assertTrue(controller != null);
	}
	
	@Test
	public void testRestfulApi() {
		String resultResponse = this.restTemplate.getForObject("http://localhost:" + port + "/getTestTable?page=2", String.class);
		LOG.debug(String.format("resultResponse: %1s", resultResponse));
	}
}
