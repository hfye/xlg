package com.xlg.pagination;

import java.util.LinkedList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestTableController {
	
	@RequestMapping(value="/getTestTable")
	public List<TestTableDto> getTestTable() {
		return new LinkedList<>();
	}
}
