package com.xlg.pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestTableController {
	
	@Autowired
	ITestTableDao testTableDao;
	
	@RequestMapping(value="/getTestTable")
	public @ResponseBody List<TestTableDto> getTestTable() {
		return testTableDao.selectAll();
	}

	@RequestMapping(value="/getPaginatedTestTable")
	public @ResponseBody PaginatedResponse<List<TestTableDto>> getPaginatedTestTable() {
		List<TestTableDto> results = testTableDao.selectAll();
		int total = testTableDao.countAll();
		int pageSize = 0;
		int pageIndex = 0;
		PagingConfig pagingConfig = PagingConfig.getPagingConfig();
		if (pagingConfig != null) {
			pageSize = pagingConfig.getPageSize();
			pageIndex = pagingConfig.getPageIndex();
		}
		return new PaginatedResponse<List<TestTableDto>>(pageSize, pageIndex, total, results);
	}
}
