package com.lizhen.crm.biz.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhen.common.util.PageUtils;
import com.lizhen.common.util.Query;
import com.lizhen.crm.api.entity.StPaperAnswer;
import com.lizhen.crm.api.service.StPaperAnswerService;
import com.lizhen.crm.kernel.dao.StPaperAnswerMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
public class StPaperAnswerServiceImpl extends ServiceImpl<StPaperAnswerMapper, StPaperAnswer> implements StPaperAnswerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StPaperAnswer> page = this.page(
                new Query<StPaperAnswer>().getPage(params),
                new QueryWrapper<StPaperAnswer>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void addPaperAnswer(StPaperAnswer stPaperAnswer) {
        LambdaQueryWrapper<StPaperAnswer> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StPaperAnswer::getProjectId,stPaperAnswer.getProjectId())
                    .eq(stPaperAnswer.getLessonId()!=null,StPaperAnswer::getLessonId,stPaperAnswer.getLessonId())
                    .eq(StPaperAnswer::getPaperId,stPaperAnswer.getPaperId())
                    .eq(StPaperAnswer::getStaffId,stPaperAnswer.getStaffId());
        this.remove(queryWrapper);
        this.save(stPaperAnswer);
    }

}