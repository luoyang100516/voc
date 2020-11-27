package com.lizhen.crm.biz.impl2;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.util.PageUtils;
import com.lizhen.common.util.Query;
import com.lizhen.common.util.StringUtil;
import com.lizhen.crm.api.entity2.MgClassChapter;
import com.lizhen.crm.api.entity2.MgClassLesson;
import com.lizhen.crm.api.service.MgClassLessonService;
import com.lizhen.crm.kernel.dao.MgClassChapterMapper;
import com.lizhen.crm.kernel.dao.MgClassLessonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class MgClassLessonServiceImpl extends ServiceImpl<MgClassLessonMapper, MgClassLesson> implements MgClassLessonService {

    @Autowired
    MgClassChapterMapper mgClassChapterMapper;

    @Override
    public PageUtils queryPage(RequestBase requestBase) {
        LambdaQueryWrapper<MgClassLesson> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtil.isNotEmpty(requestBase.getName()),MgClassLesson::getName,requestBase.getName())
                    .like(StringUtil.isNotEmpty(requestBase.getLecName()),MgClassLesson::getLecName,requestBase.getLecName())
                    .like(StringUtil.isNotEmpty(requestBase.getLabel()),MgClassLesson::getLabel,requestBase.getLabel())
//                    .eq(requestBase.getType()!=null,MgClassLesson::getType,requestBase.getType())
                    .eq(requestBase.getStatus()!=null,MgClassLesson::getStatus,requestBase.getStatus());
        IPage<MgClassLesson> page = this.page(
                new Query<MgClassLesson>().getPage(requestBase),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public List<MgClassLesson> searchLesson(RequestBase requestBase) {
        LambdaQueryWrapper<MgClassLesson> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtil.isNotEmpty(requestBase.getName()),MgClassLesson::getName,requestBase.getName());
        return this.list(queryWrapper);
    }

    @Override
    @Transactional
    public void addLesson(MgClassLesson mgClassLesson) {
        String chapterJson = mgClassLesson.getChapterJson();
        if (chapterJson != null) {
            List<MgClassChapter> list = JSONObject.parseArray(chapterJson,MgClassChapter.class);
            AtomicReference<Long> videoTime = new AtomicReference<>(0L);
            list.forEach(chapter-> videoTime.updateAndGet(v -> v + chapter.getVideoTime()));
            mgClassLesson.setVideoTime(videoTime.get());
            mgClassLesson.setClassTime(getClassTime(videoTime.get()));
            mgClassLesson.setMinutes(getMinutes(videoTime.get()));
            mgClassLesson.setChapterCount(list.size());
            this.save(mgClassLesson);
            list.forEach(chapter->{
                chapter.setLessonId(mgClassLesson.getId());
                mgClassChapterMapper.insert(chapter);
            });
        }else{
            this.save(mgClassLesson);
        }
    }

    @Override
    @Transactional
    public void updateLesson(MgClassLesson mgClassLesson) {
        String chapterJson = mgClassLesson.getChapterJson();
        if(chapterJson!=null){
            mgClassChapterMapper.delete(new LambdaQueryWrapper<MgClassChapter>().eq(MgClassChapter::getLessonId,mgClassLesson.getId()));
            List<MgClassChapter> list = JSONObject.parseArray(chapterJson,MgClassChapter.class);
            AtomicReference<Long> videoTime = new AtomicReference<>(0L);
            list.forEach(chapter-> {
                videoTime.updateAndGet(v -> v + chapter.getVideoTime());
                chapter.setLessonId(mgClassLesson.getId());
                mgClassChapterMapper.insert(chapter);
            });
            mgClassLesson.setVideoTime(videoTime.get());
            mgClassLesson.setClassTime(getClassTime(videoTime.get()));
            mgClassLesson.setChapterCount(list.size());
        }
        this.updateById(mgClassLesson);
    }

    @Override
    public MgClassLesson getClassLesson(Integer id) {
        MgClassLesson lesson = this.getById(id);
        LambdaQueryWrapper<MgClassChapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MgClassChapter::getLessonId,id);
        List<MgClassChapter> classChapters = mgClassChapterMapper.selectList(queryWrapper);
        lesson.setChapterList(classChapters);
        return lesson;
    }

    public static BigDecimal getClassTime(Long videoTime){
        BigDecimal oneClass = new BigDecimal(45*60*1000);
        BigDecimal videoTimes = new BigDecimal(videoTime);
        return  videoTimes.divide(oneClass,1,BigDecimal.ROUND_DOWN);
    }

    public static Integer getMinutes(Long videoTime){
        long res = videoTime/(60*1000);
        return Math.toIntExact(res);
    }

    public static void main(String[] args) {
            Long a = 1020000L;
        System.out.println(a/(60*1000));
//        MgClassChapter classChapter1 = new MgClassChapter();
//        classChapter1.setCode(1);
//        classChapter1.setName("zj1");
//        classChapter1.setVideoUrl("视频地址");
//        classChapter1.setVideoTime(200000L);
//        MgClassChapter classChapter2 = new MgClassChapter();
//        classChapter2.setCode(2);
//        classChapter2.setName("zj2");
//        classChapter2.setVideoUrl("视频地址2");
//        classChapter2.setVideoTime(500000L);
//        List<MgClassChapter> list = new ArrayList<>();
//        list.add(classChapter1);
//        list.add(classChapter2);
//        System.out.println(JSONObject.toJSONString(list));
//        Long a = 90*60*1000L;
//        System.out.println(getClassTime(a));
    }

}