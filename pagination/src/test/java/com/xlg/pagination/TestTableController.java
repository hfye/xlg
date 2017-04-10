package com.xlg.pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestTableController {
	
	@Autowired
	ITestTableDao testTableDao;
	
	@RequestMapping(value="/getTestTable")
	public @ResponseBody List<TestTableDto> getTestTable() {
		return testTableDao.selectAll();
	}
}
