package com.lizhen.crm.api.entity;

import lombok.Data;

/**
 * Created by xsj on 2019/7/31.
 */
@Data
public class PageSize {

    //页号
    private int page = 1;

    //每页显示的记录数
    private int pageSize = 10 ;

    //查询开始的索引
    private int start;

    public void setStart(int start) {
        this.start = ( page-1 )* pageSize ;
    }
}
