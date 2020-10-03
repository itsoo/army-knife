package com.cupshe.ak;

/**
 * 分页查询 DTO
 *
 * @author zxy
 */
public class QueryPageDTO {

    private Integer pageNum;
    private Integer pageSize;

    // ~ getter and setter ~ //

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
