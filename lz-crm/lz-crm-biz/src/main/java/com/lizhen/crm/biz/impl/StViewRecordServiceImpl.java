package com.lizhen.crm.biz.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.util.PageUtils;
import com.lizhen.common.util.Query;
import com.lizhen.crm.api.entity.StViewRecord;
import com.lizhen.crm.api.service.StViewRecordService;
import com.lizhen.crm.kernel.dao.StViewRecordMapper;

import java.util.Map;


@Service
public class StViewRecordServiceImpl extends ServiceImpl<StViewRecordMapper, StViewRecord> implements StViewRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StViewRecord> page = this.page(
                new Query<StViewRecord>().getPage(params),
                new QueryWrapper<StViewRecord>()
        );

        return new PageUtils(page);
    }

    @Override
    public StViewRecord getViewRecord(RequestBase requestBase) {
        return this.baseMapper.selectRecord(requestBase.getStaffId(),requestBase.getId());
    }

}