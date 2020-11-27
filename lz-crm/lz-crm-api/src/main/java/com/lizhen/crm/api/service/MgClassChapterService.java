package com.lizhen.crm.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.util.PageUtils;
import com.lizhen.crm.api.entity.VideoPrefix;
import com.lizhen.crm.api.entity2.MgClassChapter;

import java.util.List;

/**
 * 
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-18 23:04:52
 */
public interface MgClassChapterService extends IService<MgClassChapter> {

    PageUtils queryPage(RequestBase requestBase);

    List<VideoPrefix> getVideoPrefix();
}

