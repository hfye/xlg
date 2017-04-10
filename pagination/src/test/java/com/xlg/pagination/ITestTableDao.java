package com.xlg.pagination;

import java.util.List;

public interface ITestTableDao {

	int countAll();

	int insert(TestTableDto dto);

	List<TestTableDto> selectAll();

}