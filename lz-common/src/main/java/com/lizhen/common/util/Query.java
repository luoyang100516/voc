/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.lizhen.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lizhen.common.request.RequestBase;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 查询参数
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Query<T> {

    /**
     * 当前页码
     */
    public final String CURRPAGE = "currPage";
    /**
     * 每页显示记录数
     */
    public final String PAGESIZE = "pageSize";
    /**
     * 默认排序字段
     */
    public final String ORDER_FIELD = "id";

    /**
     * 创建分页对象
     * @param params
     * @return
     */
    public IPage<T> getPage(Map<String, Object> params) {
        return this.getPage(params, null, null);
    }

    public IPage<T> getPage(RequestBase requestBase) {
        return new Page<>(requestBase.getCurrPage(),requestBase.getPageSize());
    }

    /**
     * 创建分页对象，按id升序
     * @param params
     * @return
     */
    public IPage<T> getPageOrderByIdAsc(Map<String, Object> params) {
        return this.getPage(params, null, true);
    }

    /**
     * 创建分页对象，按id降序
     * @param params
     * @return
     */
    public IPage<T> getPageOrderByIdDsc(Map<String, Object> params) {
        return this.getPage(params, null, false);
    }

    /**
     * 创建分页对象，用户自定义排序字段，升序
     * @param params
     * @param orderField
     * @return
     */
    public IPage<T> getPageOrderByUserDefineAsc(Map<String, Object> params, String orderField) {
        return this.getPage(params, orderField, true);
    }

    /**
     * 创建分页对象，用户自定义排序字段，降序
     * @param params
     * @param orderField
     * @return
     */
    public IPage<T> getPageOrderByUserDefineDsc(Map<String, Object> params, String orderField) {
        return this.getPage(params, orderField, false);
    }




    /**
     *
     * @description: 分页条件对象
     * @params: [params 分页参数, defaultOrderField 默认排序字段, isAsc 是否升序]
     * @throws:
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<T>
     * @author: savice
     * @time: Created in 2020/7/29 13:04
     */
    public IPage<T> getPage(Map<String, Object> params, String orderField, Boolean isAsc) {

        // 分页参数
        long currPage = 1;
        long pageSize = 10;
        if(params.get(CURRPAGE) != null){
            currPage = Long.parseLong(params.get(CURRPAGE).toString());
        }
        if(params.get(PAGESIZE) != null){
            pageSize = Long.parseLong(params.get(PAGESIZE).toString());
        }
        // 创建分页对象
        Page<T> page = new Page<>(currPage, pageSize);

        if (isAsc == null) {
            return page;
        }

        // 如果用户自定义的排序字段为空，默认按id排序
        if (StringUtils.isEmpty(orderField)) {
            // 如果指定为升序
            if(BooleanUtils.isTrue(isAsc)) {
                return page.addOrder(OrderItem.asc(ORDER_FIELD));
            } else {
                // 否则为降序
                return  page.addOrder(OrderItem.desc(ORDER_FIELD));
            }
        }

        // 如果什么都没有
        return page;
    }
}
