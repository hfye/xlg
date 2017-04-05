package com.xlg.pagination;

import java.util.Date;

public class TestTableDto {
	Date testDate;
	int testId;
	
	public TestTableDto() {
		super();
		// TODO Auto-generated constructor stub
	}

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
