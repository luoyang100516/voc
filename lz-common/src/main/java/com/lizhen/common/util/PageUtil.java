package com.lizhen.common.util;

import java.io.Serializable;
import java.util.List;

/**
 * @param
 * @author XingZengZhi
 * @date 2018/9/4 11:10
 * @description 分页工具类
 * @return
 */
public class PageUtil<T> implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer pageNumber;
    private Integer pageSize;
    private Integer pageTotal;
    private List<T> pageData;

    public PageUtil() {
    }

    public PageUtil(Integer pageNumber, Integer pageSize) {

        if (pageNumber != null && pageSize != null) {
            this.pageNumber = (pageNumber > 0 ? pageNumber - 1 : 0) * pageSize;
            this.pageSize = pageSize;
        }
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }

}
