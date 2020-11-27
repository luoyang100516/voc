package com.lizhen.crm.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lizhen.common.util.PageUtils;
import com.lizhen.crm.api.entity.StPaperAnswer;

import java.util.Map;

/**
 * 
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-11 20:57:37
 */
public interface StPaperAnswerService extends IService<StPaperAnswer> {

    PageUtils queryPage(Map<String, Object> params);

    void addPaperAnswer(StPaperAnswer stPaperAnswer);
}

