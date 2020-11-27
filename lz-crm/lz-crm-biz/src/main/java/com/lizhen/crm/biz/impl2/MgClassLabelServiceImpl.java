package com.lizhen.crm.biz.impl2;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhen.common.util.PageUtils;
import com.lizhen.common.util.Query;
import com.lizhen.crm.api.entity2.MgClassLabel;
import com.lizhen.crm.api.service.MgClassLabelService;
import com.lizhen.crm.kernel.dao.MgClassLabelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


@Service
public class MgClassLabelServiceImpl extends ServiceImpl<MgClassLabelMapper, MgClassLabel> implements MgClassLabelService {

    @Autowired
    private MgClassLabelMapper mgClassLabelMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MgClassLabel> page = this.page(
                new Query<MgClassLabel>().getPage(params),
                new QueryWrapper<MgClassLabel>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<MgClassLabel> getLabelList(MgClassLabel mgClassLabel) {

        return mgClassLabelMapper.getLabelList(mgClassLabel);
    }

}