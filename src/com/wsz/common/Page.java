package com.wsz.common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 分页
 * @author wsz
 *	2016-10-23
 */
public class Page implements Serializable {
	private static int DEFAULT_PAGE_SIZE = 10;

	private int pageSize ; // 每页的记录数

	private long start; // 当前页第一条数据在List中的位置,从0开始,该变量暂时没用，调用时直接写0就行

	private Object rows; // 当前页中存放的记录,类型一般为List

	private long total; // 总记录数
	private long  currentPageNo; // 当前页
	private long totalPage; // 总页数
	
	private boolean hasNextPage = false;//是否有下一页
	private boolean hasPreviousPage = false;//是否有上一页
	/**
	 * 构造方法，只构造空页.
	 */
	public Page() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList(), 1);
	}

	/**
	 * 默认构造方法.初始化所有属性值
	 * 
	 * @param start 本页数据在数据库中的起始位置
	 * @param total 数据库中总记录条数
	 * @param pageSize 本页容量
	 * @param data 本页包含的数据
	 * @param pageNo 当前页
	 */
	public Page(long start, long total, int pageSize, Object data, long pageNo) {
		setParam(start, total, pageSize, data, pageNo);
	}

	public void setParam(long start, long total, int pageSize, Object data, long pageNo) {
		this.pageSize = pageSize;
		this.start = start;
		this.total = total;
		this.rows = data;
		this.currentPageNo = pageNo;
		this.totalPage = countTotalPage();
		this.hasNextPage = this.currentPageNo < this.totalPage;
		this.hasPreviousPage = this.currentPageNo > 1;
	}

	/**
	 * 计算总页数.
	 */
	private long countTotalPage() {
		if (total % pageSize == 0)
			return total / pageSize;
		else
			return total / pageSize + 1;
	}

	public static int getDefaultPageSize() {
		return DEFAULT_PAGE_SIZE;
	}

	public static void setDefaultPageSize(int defaultPageSize) {
		DEFAULT_PAGE_SIZE = defaultPageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(long currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}
}
