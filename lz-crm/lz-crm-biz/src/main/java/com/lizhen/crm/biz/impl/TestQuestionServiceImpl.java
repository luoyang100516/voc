package com.lizhen.crm.biz.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.util.PageUtils;
import com.lizhen.common.util.Query;
import com.lizhen.common.util.StringUtil;
import com.lizhen.crm.api.entity.TestQuestion;
import com.lizhen.crm.api.service.TestQuestionService;
import com.lizhen.crm.kernel.dao.TestQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TestQuestionServiceImpl extends ServiceImpl<TestQuestionMapper, TestQuestion> implements TestQuestionService {

    @Autowired
    TestQuestionMapper testQuestionMapper;

    @Override
    public PageUtils queryPage(RequestBase requestBase) {
        LambdaQueryWrapper<TestQuestion> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtil.isNotEmpty(requestBase.getName()),TestQuestion::getName,requestBase.getName());
        queryWrapper.eq(requestBase.getType()!=null,TestQuestion::getType,requestBase.getType());
        IPage<TestQuestion> page = this.page(
                new Query<TestQuestion>().getPage(requestBase),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void addQuestion(TestQuestion testQuestion) {
        this.save(testQuestion);
//        if(testQuestion.getId()!=null){
//            String idsStr = testQuestion.getIds();
//            List<Integer> ids = StringUtil.splitToIntList(idsStr,",");
//            testQuestionMapper.insertRelation(testQuestion.getId(),ids);
//        }
    }

    @Override
    @Transactional
    public void updateQuestionById(TestQuestion testQuestion) {
        this.updateById(testQuestion);
//        if(testQuestion.getIds()!=null){
//            testQuestionMapper.deleteRelation(testQuestion.getId());
//            String idsStr = testQuestion.getIds();
//            List<Integer> ids = StringUtil.splitToIntList(idsStr,",");
//            testQuestionMapper.insertRelation(testQuestion.getId(),ids);
//        }
    }

    @Override
    public List<TestQuestion> searchQuestion(RequestBase requestBase) {
//        if(requestBase.getId()!=null){
//            List<Integer> ids = testQuestionMapper.getQuestionIds(requestBase.getId());
//        }
        LambdaQueryWrapper<TestQuestion> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(TestQuestion::getStatus,1)
                    .and(requestBase.getName()!=null,wap->wap.like(TestQuestion::getLabel,requestBase.getName()).or().like(TestQuestion::getLesson,requestBase.getName()));
//                    .like(requestBase.getLabel()!=null,TestQuestion::getLabel,requestBase.getLabel())
//                    .like(requestBase.getLesson()!=null,TestQuestion::getLesson,requestBase.getLesson());
        return this.list(queryWrapper);
    }

    @Override
    public List<TestQuestion> getQuestions(Integer id) {
        List<Integer> ids = testQuestionMapper.selectIds(id);
        LambdaQueryWrapper<TestQuestion> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(TestQuestion::getId,ids);
        return testQuestionMapper.selectList(queryWrapper);
    }

}