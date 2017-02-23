package com.xlg.pagination;

public class PagingConfig {
	private boolean paginate = false;
	private int pageIndex = 0; //Start from 1
	private int pageSize = 0;
	
	public boolean toPaginate() {
		if (isPaginate() && getPageIndex() > 0 && getPageSize() > 0) {
			return true;
		}
		return false;
	}
	
	public int getBeginIndex() {
		return 0;
	}
	
	public int getEndIndex() {
		return getPageIndex() * getPageSize();
	}
	
	public int getSkip() {
		return (getPageIndex() - 1) * getPageSize();
	}

	/**
	 * @return the paginate
	 */
	public boolean isPaginate() {
		return paginate;
	}
	
	/**
	 * @param paginate the paginate to set
	 */
	public void setPaginate(boolean paginate) {
		this.paginate = paginate;
	}
	
	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Paging [pageIndex=" + pageIndex + ", pageSize=" + pageSize
				+ ", paginate=" + paginate + "]";
	}
}
