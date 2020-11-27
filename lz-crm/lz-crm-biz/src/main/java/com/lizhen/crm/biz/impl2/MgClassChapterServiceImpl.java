package com.lizhen.crm.biz.impl2;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.util.PageUtils;
import com.lizhen.common.util.Query;
import com.lizhen.crm.api.entity.VideoPrefix;
import com.lizhen.crm.api.entity2.MgClassChapter;
import com.lizhen.crm.api.service.MgClassChapterService;
import com.lizhen.crm.kernel.dao.MgClassChapterMapper;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MgClassChapterServiceImpl extends ServiceImpl<MgClassChapterMapper, MgClassChapter> implements MgClassChapterService {

    @Override
    public PageUtils queryPage(RequestBase requestBase) {
        LambdaQueryWrapper<MgClassChapter> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(MgClassChapter::getLessonId,requestBase.getId());

        IPage<MgClassChapter> page = this.page(
                new Query<MgClassChapter>().getPage(requestBase),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<VideoPrefix> getVideoPrefix() {
        List<VideoPrefix> videoPrefixList = this.baseMapper.getVideoPrefix();
        return addChildren(0,videoPrefixList);
    }

    private static List<VideoPrefix> addChildren(Integer pid, List<VideoPrefix> list) {
        return list.stream().filter(videoPrefix -> videoPrefix.getParentId().equals(pid)).map(dep -> {
            dep.setChildren(addChildren(dep.getId(), list));
            return dep;
        })
        .collect(Collectors.toList());
    }
}