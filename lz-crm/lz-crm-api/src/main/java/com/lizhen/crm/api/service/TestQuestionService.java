package com.lizhen.crm.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.util.PageUtils;
import com.lizhen.crm.api.entity.TestQuestion;

import java.util.List;

/**
 * 试卷表
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-23 17:02:49
 */
public interface TestQuestionService extends IService<TestQuestion> {

    PageUtils queryPage(RequestBase requestBase);

    void addQuestion(TestQuestion testQuestion);

    void updateQuestionById(TestQuestion testQuestion);

    List<TestQuestion> searchQuestion(RequestBase requestBase);

    List<TestQuestion> getQuestions(Integer id);
}

