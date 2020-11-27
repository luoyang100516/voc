package com.lizhen.crm.biz.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.util.PageUtils;
import com.lizhen.common.util.Query;
import com.lizhen.common.util.StringUtil;
import com.lizhen.common.util.UploadUtil;
import com.lizhen.crm.api.dto.StaffPaperDTO;
import com.lizhen.crm.api.entity.DeClassLesson;
import com.lizhen.crm.api.entity.StPaperAnswer;
import com.lizhen.crm.api.entity.StViewRecord;
import com.lizhen.crm.api.entity.TestPaper;
import com.lizhen.crm.api.entity2.MgClassChapter;
import com.lizhen.crm.api.entity2.MgClassLesson;
import com.lizhen.crm.api.service.DeClassLessonService;
import com.lizhen.crm.kernel.dao.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;


@Service
public class DeClassLessonServiceImpl extends ServiceImpl<DeClassLessonMapper, DeClassLesson> implements DeClassLessonService {


    @Autowired
    MgClassLessonMapper mgClassLessonMapper;

    @Autowired
    MgClassChapterMapper mgClassChapterMapper;

    @Autowired
    DeProjectBaseMapper deProjectBaseMapper;

    @Autowired
    TestPaperMapper testPaperMapper;

    @Autowired
    StPaperAnswerMapper stPaperAnswerMapper;

    @Autowired
    StViewRecordMapper stViewRecordMapper;

    @Override
    public PageUtils queryPage(RequestBase requestBase) {
        LambdaQueryWrapper<DeClassLesson> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtil.isNotEmpty(requestBase.getName()),DeClassLesson::getName,requestBase.getName())
                .like(StringUtil.isNotEmpty(requestBase.getLecName()),DeClassLesson::getLecName,requestBase.getLecName())
                .like(StringUtil.isNotEmpty(requestBase.getLabel()),DeClassLesson::getLabel,requestBase.getLabel())
//                .eq(requestBase.getType()!=null,DeClassLesson::getType,requestBase.getType())
                .eq(DeClassLesson::getMerchantId,requestBase.getMerchantId())
                .eq(DeClassLesson::getStatus,1);
        IPage<DeClassLesson> page = this.page(
                new Query<DeClassLesson>().getPage(requestBase),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public void addMerchantLesson(RequestBase requestBase) {
        if(requestBase.getIds()!=null){
            String idsStr = requestBase.getIds();
            List<Integer> ids = StringUtil.splitToIntList(idsStr,",");
            LambdaQueryWrapper<MgClassLesson> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.in(MgClassLesson::getId,ids);
            List<MgClassLesson> lessons = mgClassLessonMapper.selectList(queryWrapper);
            List<DeClassLesson> deClassLessonList= copyList(lessons,requestBase.getMerchantId());
            this.saveBatch(deClassLessonList);
        }

    }

    @Override
    public DeClassLesson getLessonDetail(RequestBase requestBase) {
        LambdaQueryWrapper<DeClassLesson> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DeClassLesson::getId,requestBase.getId())
                .eq(DeClassLesson::getMerchantId,requestBase.getMerchantId());
        return this.getOne(queryWrapper);
    }

    @Override
    public void updateLesson(DeClassLesson deClassLesson) {
        LambdaQueryWrapper<DeClassLesson> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DeClassLesson::getId,deClassLesson.getId())
                    .eq(DeClassLesson::getMerchantId,deClassLesson.getMerchantId());
        this.update(deClassLesson,queryWrapper);
    }

    @Override
    public Map<String ,Object> getStaffLessonDetail(RequestBase requestBase) {
        LambdaQueryWrapper<DeClassLesson> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DeClassLesson::getId,requestBase.getId())
                .eq(DeClassLesson::getMerchantId,requestBase.getMerchantId());
        DeClassLesson lesson = this.baseMapper.selectOne(queryWrapper);
        LambdaQueryWrapper<MgClassChapter> wrapper = new LambdaQueryWrapper();
        wrapper.eq(MgClassChapter::getLessonId,lesson.getClassId());
        List<MgClassChapter> chapterList = mgClassChapterMapper.selectList(wrapper);
        addLastWatchTime(chapterList,requestBase.getStaffId());
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("lesson",lesson);
        resMap.put("chapterList",chapterList);
        return resMap;
    }
    private void addLastWatchTime(List<MgClassChapter> chapterList,Integer staffId){
        if (chapterList == null || chapterList.size()==0) {
            return;
        }
        chapterList.forEach(chapter ->{
            StViewRecord record = stViewRecordMapper.selectRecord(staffId,chapter.getId());
            if (record != null) {
                chapter.setLastTime(record.getVideoTime());
            }
        });
    }

    @Override
    public String getChapterVideo(RequestBase requestBase) throws IOException {
        MgClassChapter classChapter = mgClassChapterMapper.selectById(requestBase.getId());
        return UploadUtil.getVideoUrl(classChapter.getVideoUrl() );
    }

//    @Override
//    public PageUtils paperList(RequestBase requestBase) {
//        List<Integer> paperIds = deProjectBaseMapper.getPaperIds(requestBase.getId());
//        if(paperIds.size()==0){
//            return null;
//        }
//        LambdaQueryWrapper<TestPaper> queryWrapper = new LambdaQueryWrapper();
//            queryWrapper.in(TestPaper::getId,paperIds);
//        IPage<TestPaper> page = testPaperMapper.selectPage(
//                new Query<TestPaper>().getPage(requestBase),
//                queryWrapper
//        );
//        return getStaffPaper(page,requestBase.getId());
//    }

    @Override
    public List<TestPaper> paperList(Integer staffId) {
        return testPaperMapper.getStaffPaperList(staffId);
    }

    PageUtils getStaffPaper(IPage<TestPaper> page,Integer staffId){
        PageUtils resPage = new PageUtils();
        LambdaQueryWrapper<StPaperAnswer> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StPaperAnswer::getStaffId,staffId);
        List<StPaperAnswer> answers = stPaperAnswerMapper.selectList(queryWrapper);
        List<TestPaper> list = page.getRecords();
        List<StaffPaperDTO> paperDto = new ArrayList<>();
        list.forEach(paper->{
            StaffPaperDTO dto = new StaffPaperDTO();
            dto.setType(paper.getType());
            dto.setName(paper.getName());
            dto.setId(paper.getId());
            answers.forEach(answer->{
                if(paper.getId().equals(answer.getPaperId())){
                    dto.setScore(answer.getScore());
                    dto.setStatus(1);
                }
            });
            paperDto.add(dto);
        });
        resPage.setCurrPage((int) page.getCurrent());
        resPage.setPageSize((int) page.getSize());
        resPage.setTotalCount((int) page.getTotal());
        resPage.setTotalPage((int) page.getTotal());
        resPage.setList(paperDto);
        return resPage;
    }

    public List<DeClassLesson> copyList(List<MgClassLesson> lessons,Integer merchantId){
        Set<Integer> ids = this.baseMapper.selectLessonIds(merchantId);
        List<DeClassLesson> res = new ArrayList<>();
        lessons.forEach(lesson->{
            if(!ids.contains(lesson.getId())){
                DeClassLesson deClassLesson = DeClassLesson.builder()
                        .classId(lesson.getId())
                        .name(lesson.getName())
                        .videoTime(lesson.getVideoTime())
                        .classTime(lesson.getClassTime())
                        .minutes(lesson.getMinutes())
                        .chapterCount(lesson.getChapterCount())
                        .description(lesson.getDescription())
                        .imageUrl(lesson.getImageUrl())
                        .label(lesson.getLabel())
                        .lecId(lesson.getLecId())
                        .lecName(lesson.getLecName())
                        .merchantId(merchantId)
                        .type(lesson.getType())
                        .build();
                res.add(deClassLesson);
            }
        });
        return res;
    }

}