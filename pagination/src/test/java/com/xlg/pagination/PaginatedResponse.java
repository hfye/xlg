package com.xlg.pagination;

public class PaginatedResponse<T> {
	private int pageSize;
	private int pageIndex;
	private int total;
	private T data;
	
	public PaginatedResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaginatedResponse(int pageSize, int pageIndex, int total, T data) {
		super();
		this.pageSize = pageSize;
		this.pageIndex = pageIndex;
		this.total = total;
		this.data = data;
	}

	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageIndex() {
		return pageIndex;
	}
	
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
}
