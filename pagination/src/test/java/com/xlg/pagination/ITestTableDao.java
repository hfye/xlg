package com.xlg.pagination;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

public interface ITestTableDao {

	int countAll();

	int insert(TestTableDto dto);

	List<TestTableDto> selectAll();

}