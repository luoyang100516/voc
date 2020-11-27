package com.lizhen.crm.biz.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.util.PageUtils;
import com.lizhen.common.util.Query;
import com.lizhen.common.util.StringUtil;
import com.lizhen.crm.api.dto.AnswerDto;
import com.lizhen.crm.api.dto.ExamResultDTO;
import com.lizhen.crm.api.dto.StaffExamQuestionDTO;
import com.lizhen.crm.api.dto.StaffExamResultDTO;
import com.lizhen.crm.api.entity.StPaperAnswer;
import com.lizhen.crm.api.entity.TestPaper;
import com.lizhen.crm.api.entity.TestQuestion;
import com.lizhen.crm.api.service.TestPaperService;
import com.lizhen.crm.kernel.dao.TestPaperMapper;
import com.lizhen.crm.kernel.dao.TestQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;


@Service
public class TestPaperServiceImpl extends ServiceImpl<TestPaperMapper, TestPaper> implements TestPaperService {

    @Autowired
    TestPaperMapper testPaperMapper;
    @Autowired
    TestQuestionMapper testQuestionMapper;

    @Override
    public PageUtils queryPage(RequestBase requestBase) {
        LambdaQueryWrapper<TestPaper> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtil.isNotEmpty(requestBase.getName()),TestPaper::getName,requestBase.getName());
        queryWrapper.eq(requestBase.getType()!=null,TestPaper::getType,requestBase.getType());
        queryWrapper.eq(requestBase.getStatus()!=null,TestPaper::getPaperStatus,requestBase.getStatus());
        IPage<TestPaper> page = this.page(
                new Query<TestPaper>().getPage(requestBase),
                queryWrapper
        );
//        List<TestPaper> list = page.getRecords();
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void addPaper(TestPaper testPaper) {
        if(testPaper.getIds()!=null){
            String idsStr = testPaper.getIds();
            List<Integer> ids = StringUtil.splitToIntList(idsStr,",");
            int score = testPaperMapper.getScore(ids);
            testPaper.setScore(score);
            testPaper.setQuestionCount(ids.size());
            this.save(testPaper);
            testPaperMapper.insertRelation(testPaper.getId(),ids);
        }else{
            this.save(testPaper);
        }
    }

    @Override
    @Transactional
    public void updatePaper(TestPaper testPaper) {
        if(testPaper.getIds()!=null){
            testPaperMapper.deleteRelation(testPaper.getId());
            String idsStr = testPaper.getIds();
            List<Integer> ids = StringUtil.splitToIntList(idsStr,",");
            int score = testPaperMapper.getScore(ids);
            testPaper.setScore(score);
            testPaper.setQuestionCount(ids.size());
            testPaperMapper.insertRelation(testPaper.getId(),ids);
        }
        this.updateById(testPaper);
    }

    @Override
    public TestPaper getPaper(RequestBase requestBase) {
        TestPaper paper = testPaperMapper.selectById(requestBase.getId());
        List<Integer> questionId = testPaperMapper.getQuestionId(requestBase.getId());
        if(questionId.size()!=0){
            List<TestQuestion> questions = testQuestionMapper.selectBatchIds(questionId);
            paper.setQuestionList(questions);
        }
        return paper;
    }

    @Override
    public List<ExamResultDTO> getExamList(RequestBase requestBase) {
        List<ExamResultDTO> examResultList = testPaperMapper.getExamList(requestBase);
        return examResultList;
    }

    @Override
    public List<StaffExamResultDTO> getExamResultList(StaffExamResultDTO resultDTO) {
        Integer type = resultDTO.getType();
        if(type.equals(1)){
            return testPaperMapper.getProjectPaperResult(resultDTO);
        }else if(type.equals(2)){
            return testPaperMapper.getProjectClassPaperResult(resultDTO);
        }else{
            return null;
        }
    }
    @Override
    public List<LinkedHashMap<String, Object>> getExportExamResultList(StaffExamResultDTO resultDTO) {
        Integer type = resultDTO.getType();
        if(type.equals(1)){
            return testPaperMapper.getExportProjectPaperResult(resultDTO);
        }else if(type.equals(2)){
            return testPaperMapper.getExportProjectClassPaperResult(resultDTO);
        }else{
            return null;
        }
    }

    @Override
    public List<StaffExamQuestionDTO> getStaffExamResultDetail(StaffExamResultDTO resultDTO) {
        List<StaffExamQuestionDTO> res = testPaperMapper.getPaperQuestionList(resultDTO);
        StPaperAnswer answers = testPaperMapper.getPaperAnswer(resultDTO);
        String answerJson = answers.getAnswer();
        List<AnswerDto> answerDTOList = JSONObject.parseArray(answerJson, AnswerDto.class);
        res.forEach(question->{
            answerDTOList.forEach(answerDto -> {
                if(question.getId().equals(answerDto.getId())){
                    question.setGetScore(answerDto.getScore());
                    question.setPutAnswer(answerDto.getAnswer());
                }
            });
        });
        return res;
    }

}