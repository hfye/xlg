package com.xlg.pagination;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestTableDao implements ITestTableDao {
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	/* (non-Javadoc)
	 * @see com.xlg.pagination.ITestTableDao#count()
	 */
	@Override
	public int countAll() {
		return jdbcTemplate.queryForObject("select count(*) from test_table", new HashMap<>(), int.class);
	}
	
	/* (non-Javadoc)
	 * @see com.xlg.pagination.ITestTableDao#insert(com.xlg.pagination.TestTableDto)
	 */
	@Override
	public int insert(TestTableDto dto) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("testDate", dto.getTestDate());
		params.put("testId", dto.getTestId());
		return jdbcTemplate.update("insert into test_table values(:testDate, :testId)", params);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<TestTableDto> selectAll() {
		return jdbcTemplate.query("select test_date, test_id from test_table order by test_date, test_id", new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new TestTableDto(rs.getDate(1), rs.getInt(2));
			}
		});
	}
}
